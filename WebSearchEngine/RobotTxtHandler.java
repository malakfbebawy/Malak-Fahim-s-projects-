/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotTxtHandler implements Serializable {

    private String userAgent = "Googlebot";
    private URL base;
    private BufferedReader robotData;
    private ArrayList<String> Disallow;
    private Integer crawlDelay = 500;  // default value
    private long last_time_stamp = 0;

    /* Preparing the URL to Connect to*/
    public RobotTxtHandler(URL url) {
        try {
            Disallow = new ArrayList<>();
            base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));
            String hostId = base + "/robots.txt";
            URL hostURL = new URL(hostId);
            URLConnection connection = hostURL.openConnection();
            robotData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            extractData();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RobotTxtHandler(int c_delay, ArrayList<String> DisA) {
        crawlDelay = c_delay;
        Disallow = DisA;
    }

    private void extractData() {
        String inputLine;
        String temp;
        String[] inputLineArr;
        try {
            while ((inputLine = robotData.readLine()) != null) {
                if (inputLine.equals("User-agent: *") || inputLine.equals("User-agent: " + userAgent)) {
                    while ((inputLine = robotData.readLine()) != null && !inputLine.matches("User-agent:(.*)")) {
                        inputLineArr = inputLine.split(" ");
                        if (inputLineArr[0].equals("Disallow:")) {
                            if (inputLineArr.length < 2) {
                                // accept all (do nothing)
                            } else if (inputLineArr[1].equals("/")) {
                                temp = (base + inputLineArr[1].trim() + "(.*)");
                                Disallow.add(temp);
                            } else {
                                temp = (base + inputLineArr[1].trim());
                                temp = temp.replace("*", "(.*)").replace("?", "\\?").replace("+", "\\+");
                                temp += "(.*)";
                                Disallow.add(temp);
                            }
                        } else if (inputLineArr[0].equals("Crawl-delay:")) {
                            crawlDelay = Integer.parseInt(inputLineArr[1]);
                        }
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Closing the BufferReader */
        try {
            robotData.close();
        } catch (IOException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getDisallowed() {
        return Disallow;
    }

    public Integer getCrawlDelay() {
        return crawlDelay;
    }

    public synchronized void wait_for_crawl_delay(long current_time) {
        if (crawlDelay == 0) { // when equal =0  , this means that we shouldn't care about it
            return;
        }

        if (current_time - last_time_stamp > crawlDelay) {
            last_time_stamp = current_time;
            return;
        } else {
            try {
                Thread.sleep(crawlDelay);
            } catch (InterruptedException ex) {
                Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            last_time_stamp = current_time;
        }
    }
}
