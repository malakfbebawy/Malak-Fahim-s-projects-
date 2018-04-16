package com.example.malakfahim.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Malak Fahim on 8/9/2016.
 */
public class Home extends AppCompatActivity  //ay intent lazem yet7at gwa try w catch
                                             //ay activity te3melo 7otto fl manifest
{
    Query_Manager qm;


    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        qm=new Query_Manager(this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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


    public void add_newchildren(View view)
    {
        try
        {
            Intent in=new Intent(this,Add_newchildren.class);
            startActivity(in);
        }
        catch(Exception e){}
    }

    public void My_children(View view)
    {
        try
        {
            Intent inten=new Intent(this,Show_mychildren.class);
            startActivity(inten);
        }
        catch(Exception e){}
    }


}
