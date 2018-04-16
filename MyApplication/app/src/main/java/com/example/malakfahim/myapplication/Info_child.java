package com.example.malakfahim.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Malak Fahim on 9/3/2016.
 */
public class Info_child extends AppCompatActivity
{
    Query_Manager qm;
    EditText e1;
    Integer ma5dom_id;
    String ABOUT;
    private Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_child);
        qm=new Query_Manager(this.getApplicationContext());
        Intent intent=this.getIntent();
        ma5dom_id=intent.getIntExtra("ID", 0);
        e1=(EditText)findViewById(R.id.textview20);




    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_child, menu);
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


    public void Done_info(View view)
    {
        String ab=" ";
        String allabout=" ";
        c= qm.get_about_by_ID(ma5dom_id);
        c.moveToFirst();
        ab=c.getString(c.getColumnIndex("_id"));
        if(ab.equals("null"))
            ab="";
        ABOUT=e1.getText().toString();
        allabout=ab+ABOUT;
        Integer res;
        res=qm.INSERT_info_ma5dom(allabout,ma5dom_id);
        TextView tv=(TextView)findViewById(R.id.textView25);
        c= qm.get_about_by_ID(ma5dom_id);
        c.moveToFirst();
        String abc=c.getString(c.getColumnIndex("_id"));

        tv.setText(abc);

    }


    @Override
    protected void onResume()       //same code in onCreate just in case he entered the activity from onresume
    {                               //faydit on resume hena hia lama tekteb el info w tdos back lama tigy ted5ol tany 3ashan te3mel info tla2y el info el adima zahra
        super.onResume();
        qm=new Query_Manager(this.getApplicationContext());
        Intent intent=this.getIntent();
        ma5dom_id=intent.getIntExtra("ID", 0);
        c= qm.get_about_by_ID(ma5dom_id);
        c.moveToFirst();
        String ab=c.getString(c.getColumnIndex("_id"));
        if(ab==null)
            ab="";
        ABOUT=e1.getText().toString();
        String allabout=ab+ABOUT;
        Integer res;
        res=qm.INSERT_info_ma5dom(allabout, ma5dom_id);
        TextView tv=(TextView)findViewById(R.id.textView25);
        c= qm.get_about_by_ID(ma5dom_id);
        c.moveToFirst();
        String abc=c.getString(c.getColumnIndex("_id"));

        tv.setText(abc);

    }
}
