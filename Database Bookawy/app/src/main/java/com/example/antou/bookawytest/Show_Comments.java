package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Show_Comments extends AppCompatActivity {

   public int Book_id;
    ListView LV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__comments);
        Intent intent=this.getIntent();
        Book_id=intent.getIntExtra("BOOK_ID",0);
        Query_Manager QM=new Query_Manager(this.getApplicationContext());
        LV=(ListView)findViewById(R.id.listView5);
        Cursor c=QM.ShowallCOMMENTOFbook(Book_id);

        Comments_Adapter CA=new Comments_Adapter(this,c,0);
        LV.setAdapter(CA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show__comments, menu);
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
