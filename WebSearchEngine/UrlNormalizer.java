/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amr
 */
public class UrlNormalizer {

    URL unNormalized;

    public UrlNormalizer(String url) {
        try {
            unNormalized = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UrlNormalizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String normalize() {
        try {

            String str = unNormalized.toString();
            str = str.replaceAll(" ", "%20");
            unNormalized = new URL(str);

        } catch (MalformedURLException ex) {
            Logger.getLogger(UrlNormalizer.class.getName()).log(Level.SEVERE, null, ex);
        }

        String scheme = null, host = null, path = null, query = null;
        int port = -1;
        try {
            scheme = unNormalized.toURI().getScheme();
            host = unNormalized.getHost();
            path = unNormalized.toURI().getRawPath();
            query = unNormalized.toURI().getRawQuery();
            port = unNormalized.getPort();
        } catch (URISyntaxException ex) {
            Logger.getLogger(UrlNormalizer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // remove these special cases
        if (path != null) {
            path = path.replaceAll("/../", "/");
            path = path.replaceAll("/./", "/");
        }

        // check if eliminating the first Domain will affect the IP of host    
        // ex:   check if  "www.google.com" is the same as "google.com"
        try {

            String[] domain_names = host.split("\\.");

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < domain_names.length; i++) {
                sb.append(domain_names[i]).append(".");
            }
            sb.deleteCharAt(sb.length() - 1);

            InetAddress A1 = InetAddress.getByName(host);
            InetAddress A2 = InetAddress.getByName(sb.toString());

            if (A1.getHostAddress().equals(A2.getHostAddress())) {
                host = sb.toString();
            }

        } catch (UnknownHostException ex) {
            //    Logger.getLogger(URL_NORMALIZER.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("url bayez: " + unNormalized);
        }

        // remove index or default
        if (path.length() != 1) {
            String[] directories = path.split("/");

            // this condition handles the situation that path ends with index or default 
            if (directories.length > 0 && (directories[directories.length - 1].startsWith("index") || directories[directories.length - 1].startsWith("default"))) {
                StringBuilder temp = new StringBuilder(path);
                temp.delete(temp.length() - directories[directories.length - 1].length(), path.length());
                path = temp.toString();
            }
        }

        // reconstruct URL  --->   scheme+ authority/host + path +query
        String Modified_url = scheme + "://" + host;

        Modified_url += (port == -1 || port == unNormalized.getDefaultPort()) ? "" : ":" + port;

        Modified_url +=  (path.length() == 0)? "/":path;

        Modified_url += (query != null) ? "?" + query : "";

        return Modified_url;
    }

    // ok ---> same host
    // frag: remove fragment
    // ok ---> path: remove duplicated slashes
    // path: add / at the end if needed
    // ok ---> path: remove /../ 
    // query : sort params 
}
