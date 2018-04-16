package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by antou on 11/30/2015.
 */
public class Query_Manager {
    String Sql;
    DataBase_Manager DBM;


    public Query_Manager(Context C) {
        this.DBM = new DataBase_Manager(C);
    }

    public int Insert_User(String User_name, String Name, String Telephone, String Email, String Pass, String dob) {
        Sql = "Insert Into USER(USER_NAME,NAME,PASSWORD,TELEPHONE,EMAIL,DOB)Values"
                + "('" + User_name + "','" + Name + "','" + Pass + "'," + Telephone + ",'" + Email + "','" + dob + "')";
        return (int)DBM.ExecuteInsert(Sql);

    }

    public Cursor isUser(String USER, String PASS) {
        Sql = "Select * from user where USER_NAME = '" + USER + "' AND PASSWORD = '" + PASS + "'";
        return DBM.ExecuteSelect(Sql);
    }

    public Cursor getFriendsOffers(String user_name) {
        Sql = "select BOOK.ID as _id,USER_NAME" +
                " ,NAME,PRICE,TIME,OFF_REQ from O_R,BOOK,FOLLOWERS where BOOK_ID=ID AND USER_NAME!='" + user_name + "' AND  Follower_id='" + user_name + "' And Followed_id=User_name";
        return DBM.ExecuteSelect(Sql);
    }

    public Cursor getOffers(String user_name) {
        Sql = "select BOOK.ID as _id,USER_NAME" +
                " ,NAME,PRICE,TIME,OFF_REQ from O_R,BOOK where BOOK_ID=ID AND USER_NAME!='" + user_name + "'";
        return DBM.ExecuteSelect(Sql);
    }


    public Cursor Select_Author_byname(String Authorfromuser) {
        Sql = "select ID as _id , NAME , NATION from Author where NAME = '" + Authorfromuser + "'";

        return DBM.ExecuteSelect(Sql);
    }

    public Cursor Select_Bookstore_byname(String Bookstorefromuser) {
        Sql = "select BOOK_STORE_NAME as _id , LOCATION , WORKING_TIME_SD , WORKING_TIME_FD" +
                " from BOOK_STORE where BOOK_STORE_NAME = '" + Bookstorefromuser + "'";

        return DBM.ExecuteSelect(Sql);
    }

    public Cursor getOffersByBookName(String Book_Name) {
        Sql = "select BOOK.ID as _id,USER_NAME" +
                " ,NAME,PRICE,TIME,OFF_REQ from O_R,BOOK where BOOK_ID=ID And BOOK.NAME='" + Book_Name + "'";
        return DBM.ExecuteSelect(Sql);
    }


    public Cursor Select_Book_byname(String Bookfromuser) {


        Sql = "select ID as _id,NAME,PUBLISH_YEAR,EDITION from BOOK where NAME = '" + Bookfromuser + "'";

        return DBM.ExecuteSelect(Sql);
    }


    public Cursor Select_BOOK_byid(Integer x) {
        Sql = "select Author.NAME as AUTHOR,BOOK.NAME as BOOK ,PUBLISH_YEAR,EDITION as _id from BOOK,WRITES,AUTHOR where BOOK.ID=BOOK_ID AND AUTHOR_ID=AUTHOR.ID AND BOOK.ID =" + x;

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor Select_bookstores_offers(String Book_name) {
        Sql = "select STORE_NAME,LOC,BOOK_ID as _id,PRICE,NAME from BOOK_STORE_OFFERS ,BOOK where BOOK_ID=ID AND BOOK.NAME='" + Book_name + "'";

        return DBM.ExecuteSelect(Sql);
    }

    public Cursor Select_User_and_MUTUAL(String User_name)  //Select User name and mutual friends of his
    {
        Sql = "select USER_NAME as _id , count(*) as count  from USER,FOLLOWERS x Inner join (select * from FOLLOWERS" +
                " where FOLLOWER_ID='" + MainActivity.getUser() + "' ) y ON x.FOLLOWED_ID=y.FOLLOWED_ID where x.FOLLOWER_ID='" + User_name + "' and USER.USER_NAME=x.FOLLOWER_ID";

        return DBM.ExecuteSelect(Sql);
    }


    public Cursor Select_Author_byid(Integer y) {
        Sql = "select Author.NAME as AUTHOR,ID,NATION,ABOUT from AUTHOR where  AUTHOR.ID =" + y;    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }


    public Cursor Select_user_byusername(String z) {
        Sql = "select USER.NAME as USER,DOB,EMAIL,TELEPHONE,PASSWORD from USER where  USER_NAME ='" + z + "'";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor Select_store_bynameandloc(String m, String n) {
        Sql = "select BOOK_STORE_NAME as _id ,ACTUAL_ADDRESS,LOCATION,\n" +
                "WORKING_TIME_SD from BOOK_STORE where  BOOK_STORE_NAME ='" + m + "' AND LOCATION='" + n + "' ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    /////////////////////////////////////NEW////////////////////////

    public Cursor Select_Ratingbybookid(Integer bookid) {
        Sql = "select USER_NAME as USER,BOOK_ID as _id,RATE from RATE where BOOK_ID =" + bookid;    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }


    public Cursor Show_userlike(String username) {
        Sql = "select USER_NAME,FIELD_NAME as _id from USER_LIKE_FIELD where USER_NAME ='" + username + "'";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor Show_BOOKfield(Integer Bookid)  //in book review
    {
        Sql = "select FIELD_NAME as _id from WRITTEN_IN where BOOK_ID =" + Bookid;    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor Show_STOREOFFERS(String storename, String loc)//for a certain store
    {
        Sql = "select BOOK.NAME as Bookname, PRICE as _id,ID from BOOK_STORE_OFFERS,BOOK where BOOK_STORE_OFFERS.BOOK_ID = BOOK.ID AND STORE_NAME ='" + storename + "' AND LOC ='" + loc + "' " +
                "order by BOOK.NAME ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }




    public Cursor TOPRATINGBOOK() {
        Sql = "select avg(RATE) as RATE ,NAME as _id\n" +
                "from RATE ,BOOK\n" +
                "where BOOK_ID=ID\n" +
                "GROUP BY BOOK_ID\n" +
                " HAVING AVG(RATE)>=4\n" +
                "order by avg(RATE) desc";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }




    public Integer EDITBOOK(Integer BOOKid, String bookname, Integer pubyear, Integer edition) {
        Sql = "UPDATE BOOK set NAME ='" + bookname + "',PUBLISH_YEAR=" + pubyear + ",EDITION=" + edition + " where ID=" + BOOKid;
        return DBM.ExcuteUpdateDelete(Sql);
    }

    public Integer EDITUSER(String username, String name, String pass, String phone, String mail, String dob) {
        Sql = "UPDATE USER set NAME ='" + name + "',PASSWORD='" + pass + "',TELEPHONE='" + phone + "',EMAIL='" + mail + "',DOB='" + dob + "' where USER_NAME='" + username+"'";
        return DBM.ExcuteUpdateDelete(Sql);
    }

    public Integer EDITAUTHOR(Integer AUTHORid, String AUTHORname, String nation,String About) {
        Sql = "UPDATE AUTHOR set NAME ='" + AUTHORname + "',NATION='" + nation + "',ABOUT='"+About+"' where ID="+AUTHORid;
        return DBM.ExcuteUpdateDelete(Sql);
    }

    public int Insert_BOOK(String BOOKName) {
        Sql = "Insert Into BOOK(NAME)Values"
                + "('" + BOOKName + "')";
        return (int)DBM.ExecuteInsert(Sql);

    }

    public int Insert_AUTHOR(String AUTHORName) {
        Sql = "Insert Into AUTHOR(NAME)Values"
                + "('" + AUTHORName + "')";
        return (int)DBM.ExecuteInsert(Sql);

    }

    public int Insert_OFFERSANDREQ(String username, Integer bookid, Integer price, String time, Integer offreq) {
        Sql = "Insert Into O_R(USER_NAME,BOOK_ID,PRICE,TIME,OFF_REQ)Values"
                + "('" + username + "'," + bookid + "," + price + ",'" + time + "'," + offreq + ")";
        return (int)DBM.ExecuteInsert(Sql);

    }

    public Cursor Showmufollowers(String username) {
        Sql = "select FOLLOWER_ID as _id from FOLLOWERS where FOLLOWED_ID='" + username + "' ORDER BY FOLLOWER_ID ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor Showallbooksofanauthor(int Author_id) {
        Sql = "select BOOK.NAME as _id , BOOK.ID as ID from BOOK,AUTHOR,WRITES where BOOK.ID=WRITES.BOOK_ID AND AUTHOR.ID=WRITES.AUTHOR_ID AND AUTHOR.ID=" + Author_id + " ORDER BY BOOK.NAME";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor ShowallFIELDOFAUTHOR(int Author_ID) {
        Sql = "select DISTINCT FIELD_NAME as _id from AUTHOR,WRITES,WRITTEN_IN where WRITTEN_IN.BOOK_ID=WRITES.BOOK_ID AND AUTHOR.ID=WRITES.AUTHOR_ID AND AUTHOR.ID=" + Author_ID + " ORDER BY FIELD_NAME";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor Show_avgrating_abook(int BOOK_ID) {
        Sql = "select avg(RATE) as _id from RATE,BOOK where BOOK.ID=RATE.BOOK_ID AND ID=" + BOOK_ID;    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }


    public void Backup() {
        DBM.Backup();
    }

    public int Insert_field(String field) {
        Sql = "INSERT INTO FIELD VALUES('" + field + "')";
        return (int)DBM.ExecuteInsert(Sql);
    }

    public int Insert_into_written_in(int id, String field) {
        Sql = "Insert into WRITTEN_IN VALUES (" + id + ",'" + field + "');";
        return (int)DBM.ExecuteInsert(Sql);
    }

    public int Insert_to_Writes(int book_id, int Author_id) {
        Sql = "Insert into WRITES VALUES(" + book_id + "," + Author_id + ")";

        return (int)DBM.ExecuteInsert(Sql);
    }

    public int UPDATE_LASTTIME_LOGIN(String Lasttime) {
        Sql = "UPDATE USER set LAST_TIME_LOGIN='" + Lasttime + "' where USER_NAME='" + MainActivity.getUser() + "'";
        return (int)DBM.ExcuteUpdateDelete(Sql);

    }


    public Cursor Show_myoffersandreq(String userName) {
        Sql = "select USER_NAME,BOOK_ID,NAME,PRICE,TIME,OFF_REQ,EDITION as _id from O_R,BOOK where BOOK.ID=O_R.BOOK_ID AND USER_NAME='" + userName + "'";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public int Delete_offerandreq(String USERNAME, Integer id) {
        Sql = "Delete from O_R WHERE USER_NAME='" + USERNAME + "' AND BOOK_ID=" + id + " ";
        return DBM.ExcuteUpdateDelete(Sql);
    }


    public int ADD_RATE(String username, Integer Bookid, Integer rate) {
        Sql = "Insert Into RATE(USER_NAME,BOOK_ID,RATE)Values"
                + "('" + username + "'," + Bookid + "," + rate + ") ";

        return (int)DBM.ExecuteInsert(Sql);

    }

    public int UPDATE_RATE(String username, Integer Bookid, Integer rate) {
        Sql = "UPDATE RATE SET RATE=" + rate + " where USER_NAME ='" + username + "' AND BOOK_ID=" + Bookid;

        return DBM.ExcuteUpdateDelete(Sql);
    }


    public Cursor ShowRATEOFUSER(String username, Integer Bookid) {
        Sql = "select RATE as _id from RATE where USER_NAME='" + username + "' AND BOOK_ID=" + Bookid;    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public int ADD_COMMENT(String username, Integer Bookid, String comment) {
        Sql = "Insert Into RATE(USER_NAME,BOOK_ID,COMMENT)Values"
                + "('" + username + "'," + Bookid + ",'" + comment + "') ";

        return (int)DBM.ExecuteInsert(Sql);

    }


    public int UPDATE_COMMENT(String username, Integer Bookid, String comment) {
        Sql = "UPDATE RATE SET COMMENT='" + comment + "' where USER_NAME ='" + username + "' AND BOOK_ID=" + Bookid;

        return (int)DBM.ExcuteUpdateDelete(Sql);
    }


    public Cursor ShowCOMMENTOFUSER(String username, Integer Bookid) {
        Sql = "select COMMENT as _id from RATE where USER_NAME='" + username + "' AND BOOK_ID=" + Bookid;    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }


    public Cursor ShowallCOMMENTOFbook(Integer Bookid) {
        Sql = "select USER_NAME ,COMMENT as _id from RATE where BOOK_ID=" + Bookid;    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }


    public int INSERT_FOLLOWER(String Follower, String Followed) {
        Sql = "Insert Into FOLLOWERS(FOLLOWER_ID,FOLLOWED_ID)Values"
                + "('" + Follower + "','" + Followed + "') ";

        return (int)DBM.ExecuteInsert(Sql);

    }


    public Cursor SELECT_FOLLOWERS(String Follower, String Followed) {
        Sql = "select FOLLOWER_ID as _id from FOLLOWERS where FOLLOWER_ID='" + Follower + "' AND FOLLOWED_ID='" + Followed + "'";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public int Delete_FOLLOWER(String Follower, String Followed) {
        Sql = "DELETE FROM FOLLOWERS WHERE FOLLOWER_ID='" + Follower + "' AND FOLLOWED_ID='" + Followed + "'";    //el cursor lazm m3ah wa7da 3la el akal eliase


        return DBM.ExcuteUpdateDelete(Sql);
    }

    public Cursor Select_like(String username, String Fieldname)
    {
        Sql="Select * from USER_LIKE_FIELD where USER_NAME='"+username+"' and FIELD_NAME='"+Fieldname+"'";
        return DBM.ExecuteSelect(Sql);
    }


    public int ADD_LIKE( String username,String Fieldname)
    {
        Sql="Insert Into USER_LIKE_FIELD(USER_NAME,FIELD_NAME)Values"
                +"('"+username+"','"+Fieldname+"') ";

        return (int)DBM.ExecuteInsert(Sql);

    }

    public int Delete_Like(String username,String Fieldname )
    {
        Sql="DELETE FROM USER_LIKE_FIELD WHERE USER_NAME='"+username+"' AND FIELD_NAME='"+Fieldname+"'";    //el cursor lazm m3ah wa7da 3la el akal eliase
        return DBM.ExcuteUpdateDelete(Sql);
    }

    public Cursor SELECT_allbookbyfieldname(String fieldname )
    {
        Sql="select NAME , EDITION,PUBLISH_YEAR,BOOK.ID as _id from BOOK,WRITTEN_IN  where FIELD_NAME='"+fieldname+"' AND BOOK.ID=WRITTEN_IN.BOOK_ID ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }
    public Cursor GET_FIELDNAME(String fieldname )
    {
        Sql="select NAME as _id from FIELD where NAME='"+fieldname+"' ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor Select_Admin(String s, String s1) {
        Sql="select ADMIN_LOGIN_NAME as _id ,NAME,LAST_TIME_LOGIN," +
                "BookStoreName,LOCATION from ADMIN where ADMIN_LOGIN_NAME='"+s+"' AND PASSWORD='"+s1+"'";

        return DBM.ExecuteSelect(Sql);
    }




    public int ADD_storeoffersbyadmin( String storename,String LOC ,Integer Bookid,Integer price)
    {
        Sql="Insert Into BOOK_STORE_OFFERS(STORE_NAME,LOC,BOOK_ID,PRICE)Values"
                +"('"+storename+"','"+LOC+"',"+Bookid+","+price+") ";

        return (int)DBM.ExecuteInsert(Sql);

    }


    public Integer UPDATE_offerbyadmin(String storename,String LOC ,Integer Bookid,Integer price)
    {
        Sql="UPDATE BOOK_STORE_OFFERS set PRICE ="+price+" where STORE_NAME='"+storename+"' AND LOC='"+LOC+"' AND BOOK_ID="+Bookid+"";
        return DBM.ExcuteUpdateDelete(Sql);
    }



    public int Delete_offerbyadmin(String storename,String LOC,Integer id)
    {
        Sql="Delete from BOOK_STORE_OFFERS WHERE STORE_NAME='"+storename+"' AND LOC='"+LOC+"' AND BOOK_ID="+id+"";
        return  DBM.ExcuteUpdateDelete(Sql);
    }

    public Cursor getLastTimeSighIN() {
        Sql="select LAST_TIME_LOGIN from USER where USER_NAME='"+MainActivity.getUser()+"'";
        return DBM.ExecuteSelect(Sql);
    }


    public Cursor SELECT_numofALLUSERS( )
    {
        Sql="select count(USER_NAME) as _id from USER ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor SELECT_numofALLoffandreq( )
    {
        Sql="select count(BOOK_ID) as _id from O_R ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor SELECT_numofALLBOOK( )
    {
        Sql="select count(ID) as _id from BOOK ";    //el cursor lazm m3ah wa7da 3la el akal eliase

        return DBM.ExecuteSelect(Sql);

    }

    public Cursor SELECT_AVGPRICEINUSERSOFFERS( )
    {
        Sql="select AVG(PRICE) as _id from O_R WHERE OFF_REQ=1";

        return DBM.ExecuteSelect(Sql);
    }

    public Cursor SELECT_AVGPRICEINSTORESOFFERS( )
    {
        Sql="select AVG(PRICE) as _id from BOOK_STORE_OFFERS";

        return DBM.ExecuteSelect(Sql);

    }
}
