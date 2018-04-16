package Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryServlet extends HttpServlet {

    queryManager qm = null;

    private ArrayList<Double> getUrlsFromRS(ResultSet selectDocsHasWord, String Column) {
        ArrayList<Double> temp = new ArrayList<>();
        try {
            while (selectDocsHasWord.next()) {
                temp.add(selectDocsHasWord.getDouble(Column));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (qm == null) {
            qm = new queryManager();
        }
        Map<Double, Double> resultPages = new HashMap<>(); // key : id of URL ,value : Rank

        String Query = request.getParameter("QUERY").trim().toLowerCase();
        PorterStemmer PS = new PorterStemmer();
        ArrayList<String> words;
        Double _max_pages = qm.getNumOfPages();

        ArrayList<Double> arr;
        Double numberOfDocsHasWord;
        Double IDF;
        Double U;
        Double numberOfOcurrence;
        Double numberOfWords;
        Double TF;

        if (Query.length() > 2 && Query.charAt(0) == '\"' && Query.charAt(Query.length() - 1) == '\"') {
            /*Phrase Search*/
            Query = Query.substring(1, Query.length() - 1);
            numberOfDocsHasWord = qm.selectDocsHasPhrases(Query);
            IDF = Math.log10(_max_pages / numberOfDocsHasWord);
            arr = getUrlsFromRS(qm.selectSimilarPhrases(Query), "webID");

            for (double docId : arr) {
                /*for each Document calculate tf*/
                numberOfOcurrence = qm.getNumOfPhraseOccurence(docId, Query);
                numberOfWords = qm.getNumOfPhrases(docId);
                TF = numberOfOcurrence / numberOfWords;
                resultPages.put(docId, (IDF * TF * qm.getPageRank(docId)));
            }
            Query = "\"" + Query + "\"";
        } else {
            /*Word Search*/
            words = PS.StemText(Query);
            for (String word : words) {
                //For Every word
                numberOfDocsHasWord = qm.numberOfDocsContainingWord(word);
                IDF = Math.log10(_max_pages / numberOfDocsHasWord);
                arr = getUrlsFromRS(qm.selectDocsHasWord(word), "ID_doc");
                for (int i = 0; i < arr.size(); i++) {
                    //For every document has this word
                    U = arr.get(i);
                    numberOfOcurrence = qm.getNumOfOccurence(U, word);
                    numberOfWords = qm.getNumOfWords(U);
                    TF = numberOfOcurrence / numberOfWords;
                    if (resultPages.get(U) == null) {
                        resultPages.put(U, (IDF * TF * qm.getPageRank(U)));
                    } else {
                        resultPages.put(U, resultPages.get(U) + (IDF * TF * qm.getPageRank(U)));
                    }
                }
            }
        }

        resultPages = sortByValue(resultPages);
        setResult(response, resultPages, Query);
    }

    private void setResult(HttpServletResponse response, Map<Double, Double> res, String Query) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Searching result</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align=\"center\" >Search Results</h1>\n"
                    + "<div style=\"position: absolute; left:50px;padding: 10px;\">\n"
                    + "<form action=\"QueryServlet\" method=\"GET\" id=\"q\">\n"
                    + "<input autofocus autocomplete=\"off\" value = \"" + Query + "\" type=\"text\" name=\"QUERY\" size=\"35\"/>\n"
                    + "<input type=\"submit\" value=\"Search!\" style=\"font-size : 15px; font-weight: bold;\"/>\n"
                    + "</form>");

            if (!res.isEmpty()) {
                for (Map.Entry<Double, Double> entry : res.entrySet()) {
                    String Url = qm.getURL(entry.getKey());
                    out.println("<h3 style=\"margin:0;\"><a href = \"" + Url + "\" target=\"_blank\">" + qm.getTitle(entry.getKey()) + "</a> Rank = " + entry.getValue() + "</h3>\n"
                            + "<cite style=\" font-size:14px; color:green; font-style: normal; \">" + Url + "</cite>");
                }
            } else {
                if (Query.length() > 2 && Query.charAt(0) == '\"' && Query.charAt(Query.length() - 1) == '\"') {
                    out.println("<p>Your search  - <B>" + Query + "</B> - did not match any documents.Try use it without quotes <a href = \"http://localhost:8080/Phase2/QueryServlet?QUERY=" + Query.substring(1, Query.length() - 1) + "\">here</a> </p>");
                } else {
                    out.println("<p>Your search  - <B>" + Query + "</B> - did not match any documents.</p>");
                }
            }
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public Map<Double, Double> sortByValue(Map<Double, Double> map) {
        List<Map.Entry<Double, Double>> list
                = new LinkedList<Map.Entry<Double, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Double, Double>>() {
            public int compare(Map.Entry<Double, Double> o1, Map.Entry<Double, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<Double, Double> result = new LinkedHashMap<Double, Double>();
        for (Map.Entry<Double, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
