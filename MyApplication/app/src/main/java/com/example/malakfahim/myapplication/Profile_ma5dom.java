package com.example.malakfahim.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Malak Fahim on 8/25/2016.
 */
public class Profile_ma5dom extends AppCompatActivity
{
  Query_Manager qm;
    Integer MA5DOM_ID;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_ma5dom);

        qm=new Query_Manager(this.getApplicationContext());
        Intent intent = this.getIntent();
        MA5DOM_ID = intent.getIntExtra("ID", 0);             //ma5dom_id

         c=qm.getma5dom_by_ID(MA5DOM_ID);

         initialization();
    }

    private void initialization ()
    {
        TextView NAME=(TextView)findViewById(R.id.textView7);
        TextView ID=(TextView)findViewById(R.id.textView1);
        TextView PHONE=(TextView)findViewById(R.id.textView5);
        TextView ADDRESS=(TextView)findViewById(R.id.textView8);
        TextView DOB=(TextView)findViewById(R.id.textView10);
        TextView YEAR=(TextView)findViewById(R.id.textView12);
        TextView ABE3TRAF=(TextView)findViewById(R.id.textView14);


        c.moveToFirst();

        String nm=c.getString(c.getColumnIndex("Name"));
        Integer i=c.getInt(c.getColumnIndex("_id"));
        String ph=c.getString(c.getColumnIndex("phone"));
        String add=c.getString(c.getColumnIndex("address"));
        String doob=c.getString(c.getColumnIndex("dateofbirth"));
        String yr=c.getString(c.getColumnIndex("year"));
        String ab=c.getString(c.getColumnIndex("abe3traf"));


        NAME.setText(nm);
        ID.setText(i+"");
        PHONE.setText(ph);
        ADDRESS.setText(add);
        DOB.setText(doob);
        YEAR.setText(yr);
        ABE3TRAF.setText(ab);


    }

    public void About_child(View view)
    {

        c.moveToFirst();


        Intent intent=new Intent(this,Info_child.class);
        intent.putExtra("ID", c.getInt(c.getColumnIndex("_id")));
        startActivity(intent);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_ma5dom, menu);
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




}
