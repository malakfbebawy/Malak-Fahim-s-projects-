package com.example.antou.bookawytest;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    EditText[]e;
    Query_Manager QM;
    static String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User="";
        e=new EditText[2];
        e[0]=(EditText)findViewById(R.id.username_entry);
        e[1]=(EditText)findViewById(R.id.password_entry);
        QM=new Query_Manager(this.getApplicationContext());
        QM.Backup();
    }


    public void signUp(View view){
        try {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
        catch(Exception e)
        {

        }
    }

    public void AdminLog(View view){
        Intent intent=new Intent(this,AdminLog.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Sign_in(View view)
    {
        String USER=e[0].getText().toString();
        String Pass=e[1].getText().toString();

        Cursor C=QM.isUser(USER, Pass);


        if (C.moveToFirst())
        //change last time log in
        {
            User=USER;
            Cursor T = QM.getLastTimeSighIN();
            T.moveToFirst();
            String string=T.getString(T.getColumnIndex("LAST_TIME_LOGIN"));
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           try {
               Date date1 = dateFormat.parse(string);
               Date date = new Date();
               QM.UPDATE_LASTTIME_LOGIN(dateFormat.format(date));
              long diff=date.getTime()-date1.getTime();

               Toast.makeText(this.getApplicationContext(), "Welcom again :D", Toast.LENGTH_SHORT).show();
               Toast.makeText(this.getApplicationContext(), "Your last log in was "+GetWhatTowrite(diff)+" later", Toast.LENGTH_LONG).show();
           }
           catch (Exception e)
           {
               Date date = new Date();
               Toast.makeText(this.getApplicationContext(), "First Time at Bookawy, Enjoy it", Toast.LENGTH_LONG).show();
               QM.UPDATE_LASTTIME_LOGIN(dateFormat.format(date));
           }

            Intent intent =new Intent(this.getApplicationContext(),Home.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this.getApplicationContext(), "User Name or password is wrong", Toast.LENGTH_LONG).show();

        C.close();
    }

    private String GetWhatTowrite(long diff) {
        long days=diff/(1000*60*60*24);
        long hours=diff/(1000*60*60);
        long mins=diff/(1000*60);

        if (days!=0)
            return days+" days";
        else if (hours!=0)
            return hours+" hours";
        else
            return mins+" minutes";
    }

    public static String getUser()
    {
        return User;
    }

    public void Report(View V)
    {
        Intent intent=new Intent(this.getApplicationContext(),Reporting.class);
        startActivity(intent);

    }

}
