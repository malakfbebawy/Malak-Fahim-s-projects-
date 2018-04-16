/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class databaseManager {

    Connection myCon = null;
    Statement myStatement = null;
    ResultSet myRes = null;       //for select query
    int res;    //for insert query
    boolean flag; //for delete query
    String userDatabase = "root";   //username and password elly katabtohom w enta bt install mysql
    String passDatabase = "root";

    static private databaseManager DBM = null;

    private databaseManager() throws SQLException {
        myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine", userDatabase, passDatabase);
        //Create a statement
        myStatement = myCon.createStatement();
    }

    static public databaseManager getInstance() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(databaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (DBM == null) {
            try {
                DBM = new databaseManager();
                return DBM;
            } catch (SQLException ex) {
                    Logger.getLogger(databaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return DBM;
        }
        return null;
    }

    ResultSet select(String sql) throws SQLException {
        myRes = myStatement.executeQuery(sql);
        return myRes;
    }

    int insertOrUpdate(String sql) {
        try {
            res = myStatement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(databaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    boolean delete(String sql) throws SQLException {
        flag = myStatement.execute(sql);
        return flag;
    }

    void finish() {
        try {
            if (myCon != null) {
                myCon.close();
            }
            if (myRes != null) {
                myRes.close();
            }
            if (myStatement != null) {
                myStatement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(databaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
