package WebSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrawlerDataLoader {

    queryManager qm;

    public CrawlerDataLoader() {
        qm = new queryManager();
    }

    int getCrawledCount() {
        int result = 0;

        ResultSet rs = qm.getCrawlingCount();
        try {
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(CrawlerDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    Queue getToVisitUrls() {

        ResultSet rs = qm.selectdUrlFromToVisit();
        Queue<String> q = new ConcurrentLinkedQueue<>();
        try {
            while (rs.next()) {
                q.add(rs.getString("doc_url"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrawlerDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return q;
    }

    Set getVisitedUrls() {

        ResultSet rs = qm.selectdUrlFromVisited();
        Set<String> url_visited = new ConcurrentSkipListSet();

        try {
            while (rs.next()) {
                url_visited.add(rs.getString("Url"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrawlerDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url_visited;

    }

    Map<String, RobotTxtHandler> getRobotHandlers() {
        Map<String, Integer> hosts = getHostsFromRobotHandler_1();
        Map<String, RobotTxtHandler> robots = new ConcurrentHashMap();

        for (Map.Entry<String, Integer> entrySet : hosts.entrySet()) {
            String host = entrySet.getKey();
            Integer c_delay = entrySet.getValue();

            ArrayList<String> disallowed;
            disallowed = getDisallowedUrls(host);
            RobotTxtHandler H = new RobotTxtHandler(c_delay, disallowed);
            robots.put(host, H);
        }
        return robots;
    }

    private ArrayList getDisallowedUrls(String host) {
        queryManager qmm = new queryManager();
        ResultSet rs = qmm.selectDisallowedURLByHost(host);
        ArrayList disallowed_urls = new ArrayList();

        try {
            while (rs.next()) {
                disallowed_urls.add(rs.getString("url_disallowed"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrawlerDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return disallowed_urls;
    }

    private Map getHostsFromRobotHandler_1() {

        ResultSet rs = qm.selectHostFromRobotHandler_1();
        Map<String, Integer> Hosts = new ConcurrentHashMap();

        try {
            while (rs.next()) {
                Hosts.put(rs.getString("host"), rs.getInt("crawl_delay"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrawlerDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Hosts;

    }
    
/*    Map<String, ArrayList<String>> getEdges()
    {
        Map<String, ArrayList<String>> Edges = new ConcurrentHashMap();
         ArrayList<String> Sources = qm.selectDistictSrc();
         
         for (Iterator<String> iterator = Sources.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            ArrayList<String> destinationPerSrc = qm.selectdstbysrc(next);
            Edges.put(next, destinationPerSrc);
                 }

         return Edges;
    }*/
}
