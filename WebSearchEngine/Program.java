package WebSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

public class Program {

    int _max_threads;
    queryManager qm;
    public Program(int t) {
        this._max_threads = t;
        qm = new queryManager();
        qm.workingDbCheck();
    }

    void runSearchEngine() {
        //Crawling
        int _max_pages = 5000;
        int save_rate = 100;

        
        //data used in ranking
        Map<String, ArrayList<String>> Graph = new HashMap();
        Map<String, Integer> out_degree = new HashMap();
        
        //data used in Indexing
        Map<String, Document> pages;
        

        while (true) {
            // check database server first 
            try {
                databaseManager.getInstance();
            } catch (Exception e) {
                System.err.println("Check database server, no connection");
                break;
            }

            CrawlerDataLoader loader = new CrawlerDataLoader();

            int crawled_count = loader.getCrawledCount();

            if (crawled_count < _max_pages) {

                // check internet connection
                httpRequestHandler h = new httpRequestHandler();
                if (!h.checkInternetConnectivity()) {
                    System.err.println("no internet connection , try again later");
                    break;
                }

                Queue<String> to_visit = loader.getToVisitUrls();
                Set<String> visited = loader.getVisitedUrls();
                Map<String, RobotTxtHandler> robots = loader.getRobotHandlers();
                Map<String, ArrayList<String>> Edges = qm.selectALLEdges();
            

                if (to_visit.isEmpty()) {
                    to_visit.add("https://wikipedia.org/wiki/Main_Page");
                    to_visit.add("http://dmoztools.net/");
                }

                Boolean crawling_finished = Boolean.FALSE;   //to check if the crwaler has comletely finsihed or not
                // create crawler
                webCrawler crawler = new webCrawler(_max_threads, _max_pages, save_rate);

                //set crawling data
                crawler.setMainData(to_visit, visited, robots, crawled_count, Edges);

                crawling_finished = crawler.startThreads();

                if (!crawling_finished) // if crawler was interrupted
                {
                    System.err.println("error occurred, This crawling phase hasn't fisnished yet, start the program later");
                    break;
                }
                
            }else
            {
                // ranker data
                Graph = set_graph_and_outdegree(out_degree);
                Indexer_Ranker indexer = new Indexer_Ranker(Graph, out_degree,_max_pages);

      
                /*Indexer Part*/
                System.out.println("Indexer has started from scratch");
                pages = this.getPortionOfDownloadedPages();
                indexer.setTarget();

                int count = 0;
                while (!pages.isEmpty()) {
                    indexer.setDataMap(pages);
                    indexer.Execute_Indexer();
                    pages = this.getPortionOfDownloadedPages();
                    count += 200;
                    System.out.println(count + " pages successfully indexed");
                }
                System.out.println("Started ranking");
                indexer.Execute_Ranker();
                System.out.println("Finished Ranking");
                indexer.finish();
            }
        }
    }

    Map<String, Document> getPortionOfDownloadedPages() {
        return new queryManager().selectAndDeletePagesbyLimit(200);
    }
    
    
    Map<String, ArrayList<String>> set_graph_and_outdegree(Map<String, Integer> outdegree)
    {
        ArrayList<String> crawled_urls = qm.selectUrlsFromDownloadedpages();
        ResultSet res= qm.selectALLEdges2();
        Map<String, ArrayList<String>>inlinks = new HashMap();
        
        
        try {
            while(res.next())
            {
                if(! outdegree.containsKey(res.getString("from_url")))
                    outdegree.put(res.getString("from_url"), 0);
                
                outdegree.put(res.getString("from_url"), outdegree.get(res.getString("from_url")) + 1);
                
                if(crawled_urls.contains(res.getString("to_url")))
                {
                    if(! inlinks.containsKey(res.getString("to_url")))
                        inlinks.put( res.getString("to_url"), new ArrayList() ) ;
                    inlinks.get(res.getString("to_url")).add(res.getString("from_url"));
                }
            }
            
            for (Iterator<String> iterator = crawled_urls.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                
                if(! inlinks.containsKey(next) )
                    inlinks.put(next, new ArrayList());
                
                if(! outdegree.containsKey(next))
                    outdegree.put(next, 0);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inlinks;
    }
}