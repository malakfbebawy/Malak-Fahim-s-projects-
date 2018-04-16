package cairo_university.si_channel2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by AMR on 5/2/2016.
 */
public class Query_Manager {

    DB_Manager model_class;
    private String result;

    static int clones=0;
    static Query_Manager QM;
    public Query_Manager()
    {
        model_class = new DB_Manager();
    }

    public static  Query_Manager  Create_QM()
    {
        if(clones ==0) {
            clones++;
            QM = new  Query_Manager();
        }
            return QM;
    }


    int Insert_student(int ID, String name, int Grade, String password){
        String SQL = "insert into student values('"+ name +"'," +ID+ "," +Grade+ ",'" +password+ "');";

        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;
    }


    int Insert_Ad_ins(String name, int ID, String Type, String password)  // password and type are limited to 10 chars
    {
        String SQL = "insert into i_a values('"+name+"',"+ID+",'"+Type+"','"+password+"');";
        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;    }


    int Insert_home_post(int IA_ID, int Grade_year, String Date, String Content)
    {
        String SQL = "insert into home_post(IA_ID, Grade_year, Creation_date, Content) values("+IA_ID+","+Grade_year+",'"+Date+"','"+Content+"');";
        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if(result.contains("success"))
            return 1;
        else
            return 0;
    }

    int Insert_disc_post(int stud_ID, String C_date, String content)
    {
        String SQL = "insert into disc_post(stud_id, creation_date, content) values("+stud_ID+",'"+C_date+"','"+content+"');";
        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;
    }


    int Insert_Question(int surv_id, int qnum, String Quest, String Ans)
    {
        String SQL = "insert into survey_quest values("+surv_id+","+qnum+",'"+Quest+"','"+Ans+"');";
        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;    }

    int Insert_comment(int s_id, int P_id, String content)
    {
        String SQL="insert into comments(s_id, P_id, content) values("+s_id+","+P_id+",'"+content+"');";
        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;
    }

    int Insert_survey(int A_I_ID, int Grade, String Description)
    {
        String SQL = "insert into survey(Ins_Adm_Id, Grade, Description) values("+A_I_ID+","+Grade+",'"+Description+"');";
        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("result = "+ result +"k");
        result = result.substring(0, result.length()-1);
        return Integer.parseInt(result);
    }

    int Insert_Ans(int Q_num, int surv_ID, int stud_ID, String Answer)
    {
        String SQL = "insert into stud_ans values("+Q_num+","+surv_ID+","+stud_ID+",'"+Answer+"');";
        try {
            result = new DB_Manager().execute(SQL, "insert").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////SELECTION/////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    JSONArray select_studs()
    {
        String SQL= "select * from student;";
        try {
            result = new DB_Manager().execute(SQL, "select", "student").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }


    JSONArray select_stud(int ID, String pass)
    {
        String SQL= "select * from student where ID="+ID+" and PassWord='"+pass+"';";
        try {
            result = new DB_Manager().execute(SQL, "select", "student").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }


    JSONArray select_IA(int ID, String pass)
    {
        String SQL= "select * from i_a where ID="+ID+" and PASSWORD='"+pass+"';";
        try {
            result = new DB_Manager().execute(SQL, "select", "i_a").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }


    JSONArray Select_home_posts_by_IA(int ID)
    {
        String SQL= "select * from home_post where IA_ID= "+ID+" order by ID asc;";
        try {
            result = new DB_Manager().execute(SQL, "select", "home_post").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }


    JSONArray select_home_post_by_stud(int Grade)
    {
        String SQL = "select * from home_post where Grade_year="+Grade+" order by ID asc;";
        try {
            result= new DB_Manager().execute(SQL, "select", "home_post").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_home_post_by_stud2(int Grade)
    {
        String SQL = "SELECT i_a.Name, home_post.IA_ID, home_post.Grade_year, home_post.Creation_date, home_post.Content, home_post.ID FROM i_a, home_post WHERE i_a.ID = home_post.IA_ID AND home_post.Grade_year = "+Grade+ " ORDER BY home_post.ID;";
        try{
        result= new DB_Manager().execute(SQL, "select", "home_post_IA").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_disc_posts()
    {
        String SQL = "select * from disc_post order by creation_date;";
        try {
            result = new DB_Manager().execute(SQL, "select", "disc_post").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_disc()
    {
        String SQL = "SELECT disc_post.creation_date, disc_post.content , student.Name, disc_post.ID FROM disc_post, student WHERE student.ID = disc_post.stud_id ORDER by disc_post.ID";

        try {
            result = new DB_Manager().execute(SQL, "select" ,"disc_post_stud").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  parse_JSON(result);
    }

    JSONArray select_disc_by_ID(int ID)
    {
        String SQL = "select * from disc_post where ID = "+ID+";";
        try {
            result = new DB_Manager().execute(SQL, "select", "disc_post").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_disc_by_ID2(int ID)
    {
        String SQL = "SELECT disc_post.creation_date, disc_post.content , student.Name, disc_post.ID " +
                "FROM disc_post, student " +
                "WHERE student.ID = disc_post.stud_id and disc_post.ID= "+ID+";";
        try {
            result = new DB_Manager().execute(SQL, "select", "disc_post_stud").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_comments(int P_id)
    {
        String SQL = "SELECT comments.ID, comments.s_id, comments.P_id, comments.content , student.Name " +
                "FROM comments, student " +
                "WHERE student.ID = comments.s_id and P_id = "+P_id+" " +
                "ORDER BY comments.ID;";
        try {
            result = new DB_Manager().execute(SQL, "select", "comments").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_surveys_IA(int IA_ID)
    {
        String SQL = "select Description, Grade from survey where Ins_Adm_ID="+IA_ID+";";
        try {
            result = new DB_Manager().execute(SQL, "select", "survey").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_surveys_stud(int Grade)
    {
        String SQL = "select survey.ID, survey.Ins_Adm_ID , survey.Description , i_a.Name, i_a.Type" +
                " FROM survey , i_a " +
                " WHERE survey.Ins_Adm_ID = i_a.ID AND survey.Grade = "+Grade+" ;";
        try {
            result = new DB_Manager().execute(SQL, "select", "survey_ia").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_surveys_stud2(int Grade)
    {
        String SQL = "select * from survey where Grade="+Grade+";";
        try {
            result = new DB_Manager().execute(SQL, "select").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_Q_Ans(int surv_ID, int stud_ID)
    {
        String SQL = "select * from stud_ans where surv_ID = "+surv_ID+" and stud_ID = "+stud_ID+";";
        try {
            result = new DB_Manager().execute(SQL, "select", "stud_ans").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

    JSONArray select_Quests(int surv_ID)
    {
        String SQL = "select * from survey_quest where surv_ID = "+ surv_ID+" order by Q_num;";

        try {
            result = new DB_Manager().execute(SQL, "select", "survey_quest").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return parse_JSON(result);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////DELETION////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    int Delete_student(int ID)
    {
        String SQL = "Delete from student where ID="+ID+";";
        try {
            result = new DB_Manager().execute(SQL, "Delete").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;    }

    int delete_inst(int ID)
    {
        String SQL = "Delete from i_a where ID="+ID+";";
        try {
            result = new DB_Manager().execute(SQL, "Delete").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(result.contains("success"))
            return 1;
        else
            return 0;    }

    public void setResult( String R)
    {
        result = R;
    }


    JSONArray parse_JSON(String s)
    {
        try {
            JSONObject JO=new JSONObject(s);
            JSONArray JA = JO.getJSONArray("server_response");
            return JA;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
