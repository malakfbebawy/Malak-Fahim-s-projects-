package com.example.malakfahim.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    EditText[]e;
    Query_Manager qm;
    static String user="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e=new EditText[2];
        e[0]=(EditText)findViewById(R.id.username_entry);
        e[1]=(EditText)findViewById(R.id.password_entry);
        qm=new Query_Manager(this.getApplicationContext());
        qm.Backup();       //mn el application to sd card
    }

    public void signUp(View view)
    {
        try
        {
            Intent intent = new Intent(this,Sign_up.class);
            startActivity(intent);

        }
        catch (Exception e){


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        String passw=e[1].getText().toString();
        Cursor c=qm.isUser(USER, passw);
        if(c.moveToFirst())
        {
            try {
                user = USER;
                Toast.makeText(this.getApplicationContext(), "Welcome to 5edma", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this.getApplicationContext(), Home.class);
                startActivity(intent);

            }
            catch (Exception e){}




        }
        else
        {
            Toast.makeText(this.getApplicationContext(),"wrong user name or password ",Toast.LENGTH_LONG).show();
        }

       c.close();
    }

    public static String getUser()
    {
        return user;
    }







}
