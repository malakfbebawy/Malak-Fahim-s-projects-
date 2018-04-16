package WebSearchEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class crawlThread extends Thread {

    webCrawler crawler;
    httpRequestHandler http_handler;
    Map<String, RobotTxtHandler> robots;
    queryManager qm;
    
    public crawlThread(webCrawler c) {
        crawler = c;
        http_handler = new httpRequestHandler();
       robots = c.get_robots();
       qm = new queryManager();
    }

    @Override
    public void run() {

        while (true) {
            //1- check internet connectivity, if not connected work should be finished
            if (!http_handler.checkInternetConnectivity()) {
                System.err.println("no Internet Connection!");
                break;
            }

            //2- pop from queue
            String top = crawler.popUrl();
            if (top == null) {
                continue;
            }

            //3- check server status
            if (!http_handler.checkServerResponse(top)) {
                continue;
            }

            //4- check robot disallow
            if (!crawler.checkRobotTxt(top)) {
                System.out.println("refused in the Robots.txt");
                continue;
            }

            //5- download, extract page body and save it
            try {  
                URL url = new URL(top);
                robots.get(url.getHost()).wait_for_crawl_delay(System.currentTimeMillis());
            } catch (MalformedURLException ex) {
                Logger.getLogger(crawlThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            boolean downloaded = http_handler.downloadPage(top);
            if (!downloaded) {
                System.out.println("Error occured while downloading");
                continue;
            }
            Document page_body = http_handler.getDoc();
            boolean added = crawler.addPage(top.toString(), page_body);  // return false if page is not added

            // if !added means that crawling limit is reached , so we have to break
            if (!added) {
                break;
            }

            //6- extract links and push them to crawling queue
            ArrayList<URL> links_in_page = (ArrayList) http_handler.getUrls();
            //System.out.println("sizeeee: "+ links_in_page.size());
            for (int i = 0; i < links_in_page.size(); i++) {
                if (http_handler.valid(links_in_page.get(i))) {
                    
                     UrlNormalizer normalizer = new UrlNormalizer(links_in_page.get(i).toString());
                    try {
                        links_in_page.set(i, new URL(normalizer.normalize()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(crawlThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    crawler.pushUrl(links_in_page.get(i).toString());
                    crawler.addEdge(top, links_in_page.get(i).toString());
                }
            }
            
            //qm.optimizedInsertIntoEdge(top, links_in_page);
            
        }
        crawler.finish();
    }
}
