package WebSearchEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

public class webCrawler {

    // Crawler's variables and counters
    int maxRunningThreads;
    int runningThreadsCount = 0;       //number of running threads
    int maxCrawledPages;               // max number of pages to be crawled
    int savingRate;                     // number of pages to be crawled between two checkpoints
    int crawledPagesCount;             // number of crawled pages till now
    Boolean crawlingFinished = false;   // if the crawling phase has completely finished, this boolean will be set "true"

    // Crawler's main Data dtructures
    Queue<String> toVisit;
    Set<String> visited;
    Map<String, RobotTxtHandler> RobotHandlers;    // key = hostname , value = RobotTxtobject
    Map<String,ArrayList<String>>Edges;                 // links between pages

    //to_visit: Queue that contains urls to be crawled in future
    //visited: Set that contains URLS popped from to_visit queue
    //RobotHandlers: Map , each host name has a robot handler object 
    //      contain all needed robotTxtHandlers for all websites,
    //      robot.txt will be fetched only one time per website, not for
    //      every page
    //Auxiliary data structures, used only to track changes between check points
    Set<String> visitedPerCP;                  // visited URLs to be added to database at each checkpoint
    Queue<String> toVisitPerCP;               // URLS that are pushed in queue after the last checkpoint ,but not popped
    Map<String, RobotTxtHandler> RobotsPerCP;  // handlers inserted in Robot handler map after the last checkpoint
    Map<String, Document> crawledPerCP;        // crawled pages inserted in crawled_pages map after last checkpoint
    Map<String, ArrayList<String>> EdgesPerCP;         

    public webCrawler(int _max_threads, int _max_pages, int save_rate) {

        maxRunningThreads = _max_threads;
        maxCrawledPages = _max_pages;
        savingRate = save_rate;

        //initialize Main data structure
        toVisit = new ConcurrentLinkedQueue();
        visited = new ConcurrentSkipListSet();
        RobotHandlers = new ConcurrentHashMap();
        Edges = new ConcurrentHashMap();

        //initialize Auxiliary Data structure
        visitedPerCP = new ConcurrentSkipListSet();
        toVisitPerCP = new ConcurrentLinkedQueue();
        RobotsPerCP = new ConcurrentHashMap();
        crawledPerCP = new ConcurrentHashMap();
        EdgesPerCP = new ConcurrentHashMap();
    }

    boolean checkRobotTxt(String urlString) {

        synchronized (RobotsPerCP) {
            try {
                URL url = new URL(urlString);
                URL base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));

                if (!RobotHandlers.containsKey(base.getHost())) //first create handler if it does not exist
                {
                    RobotTxtHandler H = new RobotTxtHandler(base);
                    RobotHandlers.put(base.getHost(), H);
                    RobotsPerCP.put(base.getHost(), H);
                }

                RobotTxtHandler RH = RobotHandlers.get(base.getHost());

                /*return true if it is not in disallowed or even if it is in
                 *disallowed but allowed for this agent
                 */
                ArrayList<String> Disallowed = RH.getDisallowed();

                for (String dis : Disallowed) {
                    if (urlString.matches(dis)) {
                        return false;
                    }
                }

                return true;

            } catch (MalformedURLException ex) {
                Logger.getLogger(webCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;//wrong URL
        }

    }

    // set main data before crawling, if you have a saved version
    void setMainData(Queue<String> TV, Set<String> V, Map<String, RobotTxtHandler> RH, int cc, Map<String, ArrayList<String>> Edges) {
        toVisit = TV;
        visited = V;
        RobotHandlers = RH;
        crawledPagesCount = cc;
        this.Edges = Edges;
    }

    // used to insert a page in carawled_per_CP Map, preparing it to be inserted in DB
    boolean addPage(String url, Document page) {

        synchronized (crawledPerCP) {

            if (crawledPagesCount < maxCrawledPages) {

                crawledPerCP.put(url, page);
                crawledPagesCount++;
                System.out.println(url);
                System.out.println("crawl count =" + crawledPagesCount);

                //if you crawled the agreed upon number "saving rate" , then save data in DB            
                if (crawledPerCP.size() == savingRate) {

                    synchronized (toVisitPerCP) {
                        synchronized (visitedPerCP) {
                            synchronized (RobotsPerCP) {
                                synchronized (EdgesPerCP) {
                                try {
                                    System.out.println("Waiting ...");
                                    updateDB();
                                    System.out.println("DB updated");
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
                }
                return true;
            } else {
                crawlingFinished = Boolean.TRUE;

                queryManager qm = new queryManager();
                qm.flushVisited();
                qm.flushToVisit();
                qm.flushRobot_2();
                qm.flushRobot_1();

            }
            return false;
        }
    }
    
     Map<String, RobotTxtHandler> get_robots()
    {
        return RobotHandlers;
    }
    
    public boolean addEdge(String src, String dst){
       synchronized(Edges){

            if(! Edges.containsKey(src))
            {
                Edges.put(src, new ArrayList());
            }
           
           
           if(  Edges.get(src).contains(dst)  || src.equals(dst))
            {
                return false;
            }
         
           


            
           if( ! EdgesPerCP.containsKey(src))
            {
                EdgesPerCP.put(src, new ArrayList());
            }
            
            Edges.get(src).add(dst);
            EdgesPerCP.get(src).add(dst);
       }
       return true;
    }

    //used to push urls into to_visit queue, to_visit_per_CP
    public boolean pushUrl(String url) {

        synchronized (toVisit) {
            synchronized (visited) {
                synchronized (toVisitPerCP) {
                    
                    if (!toVisit.contains(url) && !visited.contains(url)) {
                        toVisitPerCP.add(url);
                        return toVisit.add(url);
                    }
                    return false;
                }
            }
        }
    }

    // used to pop a url from to_visit queue to start its processing in a thread
    String popUrl() {

        synchronized (toVisit) {
            synchronized (visited) {
                synchronized (visitedPerCP) {
                    String url = toVisit.poll();
                    if (url != null) {
                        visited.add(url);
                        visitedPerCP.add(url);
                    }
                    return url;
                }
            }
        }
    }

    //called by a thread when it finishes its work
    synchronized void finish() {
        runningThreadsCount--;
        if (runningThreadsCount == 0) {
            System.out.println("Crawler done\n");
        }
    }

    // used to start The crawling threads
    boolean startThreads() {

        Thread ts[] = new Thread[maxRunningThreads];

        for (int i = 0; i < maxRunningThreads; i++) {
            ts[i] = new crawlThread(this);
            ts[i].start();
            runningThreadsCount++;
            System.out.println("new thread created: " + runningThreadsCount);
        }

        for (int i = 0; i < maxRunningThreads; i++) {
            try {
                ts[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(webCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return crawlingFinished;
    }

    // used to update the DB at each CP
    void updateDB() throws SQLException {
        queryManager qm = new queryManager();

        //////////////////// update visited set in the DB /////////////////////////
        qm.optimizedInsertIntoVisited(visitedPerCP);

        /////////////update to_visit queue in the DB/////////////////////
        qm.optimizedInsertIntoToVisit(toVisitPerCP);
        qm.optimizedDeleteFromToVisit(visitedPerCP);

        ///////////////////update Robot handlers in DB/////////////////
        if (!RobotsPerCP.isEmpty()) {
            qm.optimizedInsertIntoRobots(RobotsPerCP);
        }

        //////////////////update downloaded pages in DB//////////////////
        qm.optimizedInsertIntoDownloadedPage(crawledPerCP);

        
        /////////////////update Edge table /////////////////////////////
       /* for (Map.Entry<String, ArrayList<String>> entrySet : EdgesPerCP.entrySet()) {
            String key = entrySet.getKey();
            ArrayList<String> value = entrySet.getValue();
            if(value.size() > 0)
                qm.optimizedInsertIntoEdge(key, value);
        }
*/
        qm.optimizedInsertIntoEdge(EdgesPerCP);
        //clear auxiliary data 
        visitedPerCP.clear();
        toVisitPerCP.clear();
        RobotsPerCP.clear();
        crawledPerCP.clear();
        EdgesPerCP.clear();
    }
    
    
    
}
