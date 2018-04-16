package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Show_Followers extends AppCompatActivity {

    Query_Manager QM;
    ListView LV;
    TextView Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__followers);
        Intent intent=this.getIntent();
        String USER_NAME=intent.getStringExtra("USER_NAME");
        QM=new Query_Manager(getApplicationContext());
        LV =(ListView)findViewById(R.id.listView8);
        Tv=(TextView)findViewById(R.id.textView51);
        final Cursor c=QM.Showmufollowers(USER_NAME);
        Follower_Adpater FA=new Follower_Adpater(getApplicationContext(),c,0);
        LV.setAdapter(FA);
        Tv.setText(USER_NAME + " Followers");

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c.moveToPosition(i);
                Intent intent5=new Intent(getApplicationContext(),User_Review.class);
                intent5.putExtra("USER_NAME", c.getString(c.getColumnIndex("_id")));
                startActivity(intent5);
                return;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show__followers, menu);
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
