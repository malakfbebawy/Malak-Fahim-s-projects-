package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class BOOK_of_Author extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_of__author);
        Intent intent=this.getIntent();
        ListView LV=(ListView)findViewById(R.id.listView10);
        int Author_id=intent.getIntExtra("AUTHOR_ID", 0);
        Query_Manager QM=new Query_Manager(this.getApplicationContext());
        final Cursor c=QM.Showallbooksofanauthor(Author_id);
        Follower_Adpater FA=new Follower_Adpater(this.getApplicationContext(),c,0);
        LV.setAdapter(FA);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c.moveToPosition(i);
                Intent intent=new Intent(getApplicationContext(),Book_review.class);
                intent.putExtra("BOOK_ID",c.getInt(c.getColumnIndex("ID")));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_of__author, menu);
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
