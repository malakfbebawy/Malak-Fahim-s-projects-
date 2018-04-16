package Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class queryManager {

    ResultSet myRes = null;       //for select query
    int res;    //for insert query
    boolean flag; //for delete query
    String sql;
    databaseManager db;
    int targetDB;
    
    public queryManager() {
        this.db = databaseManager.getInstance();
    }
    private void setTarget(){
        sql = "SELECT num FROM search_engine.working_db where DB = \"DB\";";
        try {
            myRes = db.select(sql);
            myRes.next();
            targetDB = myRes.getInt("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ResultSet selectSimilarPhrases(String Query) {
        setTarget();
        sql = "SELECT webID FROM phrases"+(((targetDB == 2) ? "2" : "") )+" where phrase like \"%"+Query+"%\";";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectDocsHasWord(String word) {
        setTarget();

        sql = "SELECT distinct ID_doc from search_engine.doc_words"+(((targetDB == 2) ? "2" : "") )+" where word = \""+word+"\";";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    Double numberOfDocsContainingWord(String word) {
        /*TODO : Determining which database to use*/
        setTarget();

        sql = "SELECT count(distinct ID_doc) as num from search_engine.doc_words"+(((targetDB == 2) ? "2" : "") )+" where word = \""+word+"\";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    }
    Double getDocId(String U){
        setTarget();
        sql = "SELECT docId"+(((targetDB == 2) ? "2" : "") )+" from search_engine.document"+(((targetDB == 2) ? "2" : "") )+" where Url =\""+U+"\";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("docId"+(((targetDB == 2) ? "2" : "")));
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1.0;
    }
    Double getNumOfOccurence(Double U, String word) {
        /*TODO : Determining which database to use*/
        setTarget();

        sql = "SELECT count(*) as num from search_engine.doc_words"+(((targetDB == 2) ? "2" : "") )+" where ID_doc = "+U+" and word = \""+word+"\";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    }

    Double getNumOfWords(Double U) {
        /*TODO : Determining which database to use*/
        setTarget();

        sql = "SELECT count(*) as num from search_engine.doc_words"+(((targetDB == 2) ? "2" : "") )+" where ID_doc = "+U+";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    }

    Double getPageRank(Double U) {
        setTarget();
         sql = "SELECT page_rank from rank"+(((targetDB == 2) ? "2" : "") )+" where docId = "+U+";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("page_rank");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    }

    Double getNumOfPages() {
        setTarget();
         sql = "SELECT count(*) as num from document"+(((targetDB == 2) ? "2" : "") )+";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    
    }

    String getTitle(Double url) {
       setTarget();
         sql = "SELECT title from titles"+(((targetDB == 2) ? "2" : "") )+" where doc_id = "+url+";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getString("title");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }

    String getURL(Double key) {
        setTarget();
        sql = "SELECT Url from search_engine.document"+(((targetDB == 2) ? "2" : "") )+" where docId"+(((targetDB == 2) ? "2" : ""))+" = "+key+";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getString("Url");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    Double selectDocsHasPhrases(String Query) {
        setTarget();
        sql = "SELECT count(*) as num FROM phrases"+(((targetDB == 2) ? "2" : "") )+" where phrase like \"%"+Query+"%\";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    }

    Double getNumOfPhraseOccurence(double aDouble, String Query) {
        setTarget();
        sql = "SELECT count(*) as num FROM phrases"+(((targetDB == 2) ? "2" : "") )+" where phrase like \"%"+Query+"%\" and webID = "+aDouble+";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    }

    Double getNumOfPhrases(double aDouble) {
        setTarget();
        sql = "SELECT count(*) as num FROM phrases"+(((targetDB == 2) ? "2" : "") )+" where webID = "+aDouble+";";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getDouble("num");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Double.NaN;
    }
}