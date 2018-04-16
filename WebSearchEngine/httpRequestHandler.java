package WebSearchEngine;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class httpRequestHandler {

    private Document Doc;

    boolean checkServerResponse(String s) {

        HttpURLConnection con = null;
        try {
            URL url = new URL(s);
            con = (HttpURLConnection) url.openConnection();
            
            con.setReadTimeout(5000); con.setConnectTimeout(5000);//if more than 5 secs this will send out a false connection (set for realy bad internet connection)

            int code = con.getResponseCode();
            String type = con.getContentType();

            if (type == null || code != HttpURLConnection.HTTP_OK) {
                return false;
            } else if (type.startsWith("text/html")) {
                return true;
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Connection lost with server due to timeout");

        } catch (SocketException e) // request sent then internet connection lost
        {
            System.out.println("Connection lost with server");
        } catch (UnknownHostException e) // request can't be sent
        {
            System.out.println("Site is unreachable");
        } catch (IOException ex) {
        
        }
        if (con != null) {
            con.disconnect();
        }

        return false;
    }

    // check Internet connection
    boolean checkInternetConnectivity() {
        try {
            HttpURLConnection con = (HttpURLConnection) (new URL("https://www.google.com.eg")).openConnection();
            con.setConnectTimeout(5000);
            con.connect();
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    boolean valid(URL url) {
        try {
            URI u = url.toURI();
        } catch (Exception e) {
            return false;
        }

        if (!(url.getProtocol()).equals("http") && !(url.getProtocol()).equals("https")) {
            return false;
        }
        if (url.getAuthority() == null) {
            return false;
        }

        return true;
    }

    boolean downloadPage(String url) {
        try {
            Doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    ArrayList<URL> getUrls() {
        ArrayList<URL> myList = new ArrayList<>();
        String urll = "";
        Elements links = Doc.select("a[href]");

        for (Element link : links) {
            try {
                urll = link.absUrl("href");
                if (!urll.isEmpty()) {
                    myList.add(new URL(urll));
                    if (myList.size() == 50) {
                        break;
                    }
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return myList;
    }

    Document getDoc() {
        return Doc;
    }
}
