package com.example.malakfahim.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Malak Fahim on 8/22/2016.
 */
public class Show_mychildren extends AppCompatActivity
{
    ListView lv;
    Query_Manager qm;
    String khadem_username;
    TextView tv;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mychildren);
        qm=new Query_Manager(this.getApplicationContext());
        lv=(ListView)findViewById(R.id.listView);
        tv=(TextView)findViewById(R.id.textView1);
        khadem_username=MainActivity.getUser();
        final Cursor c=qm.getchildren();
        c.moveToFirst();
        if(c.getCount()!=0)
        {
            Show_children_adapter adapter=new Show_children_adapter(this,c,0);
            lv.setAdapter(adapter);




            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
                {
                    c.moveToPosition(position);

                    final Integer ma5dom_id = c.getInt(c.getColumnIndex("_id")); //hat id el ma5dom elly das 3leh
                    //final String ma5dom_name=c.getString(c.getColumnIndex("Name"));  //hat esm el ma5dom elly das 3leh

                        Intent intent = new Intent(getApplicationContext(), Profile_ma5dom.class);
                        intent.putExtra("ID", ma5dom_id);  //ro7 ll name elly das 3leh
                        startActivity(intent);





                }

            });

        }

        else
        {
            tv.setText("No children yet");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_mychildren, menu);
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






    @Override
    protected void onResume()       //same code in onCreate just in case he entered the activity from onresume
    {
        super.onResume();
        qm=new Query_Manager(this.getApplicationContext());
        lv=(ListView)findViewById(R.id.listView);
        tv=(TextView)findViewById(R.id.textView1);
        khadem_username=MainActivity.getUser();
        final Cursor c=qm.getchildren();
        c.moveToFirst();
        if(c.getCount()!=0)
        {
            Show_children_adapter adapter=new Show_children_adapter(this,c,0);
            lv.setAdapter(adapter);



            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
                {
                    c.moveToPosition(position);

                    final Integer ma5dom_id = c.getInt(c.getColumnIndex("_id")); //hat id el ma5dom elly das 3leh
                    //final String ma5dom_name=c.getString(c.getColumnIndex("Name"));  //hat esm el ma5dom elly das 3leh

                    Intent intent = new Intent(getApplicationContext(), Profile_ma5dom.class);
                    intent.putExtra("ID", ma5dom_id);  //ro7 ll name elly das 3leh
                    startActivity(intent);



                }

            });

        }

        else
        {
            tv.setText("No children yet");
        }
    }
}
