package cairo_university.si_channel2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    EditText[]e;
    Query_Manager QM;
    static String User;
    static int Type; // 0: for students 1: Inst and admins
    static String IA_type;
    static int ID;
    static int G_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User="";
        e=new EditText[2];
        e[0]=(EditText)findViewById(R.id.username_entry);
        e[1]=(EditText)findViewById(R.id.password_entry);
        QM=Query_Manager.Create_QM();
        //QM.Backup();

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//      //  getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void Sign_in(View view)
    {
        String USER=e[0].getText().toString();
        String Pass=e[1].getText().toString();

        if(USER.equals("") || Pass.equals(""))
        {
            Toast.makeText(this,"fields should be filled",Toast.LENGTH_LONG).show();
            return;
        }

        JSONArray JA = QM.select_stud(Integer.parseInt(USER), Pass);

        if (JA.length()!=0)
        //change last time log in
        {
            Toast.makeText(this.getApplicationContext(), "student Successfully signed in :D", Toast.LENGTH_LONG).show();
            User=USER;
            Type=0;
            IA_type = "";
            try {
                G_year = Integer.parseInt(JA.getJSONObject(0).getString("Grade_year"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            //    Toast.makeText(this, "you are a student ",Toast.LENGTH_LONG);
            Intent intent =new Intent(this.getApplicationContext(),Home.class);
            startActivity(intent);
        }
        else
        {
            JA = QM.select_IA(Integer.parseInt(USER), Pass);
            if (JA.length()!=0)
            {
                User=USER;
                try {
                    IA_type = JA.getJSONObject(0).getString("Type");
                    ID = JA.getJSONObject(0).getInt("ID");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                Type=1;
                Toast.makeText(this.getApplicationContext(), "ins Successfully signed in :D", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(this.getApplicationContext(),Home.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this.getApplicationContext(), "User Name or password is wrong", Toast.LENGTH_LONG).show();
        }

       // C.close();
    }

    public static int getType() {return Type;}
    public static String getUser()
    {
        return User;
    }
}
