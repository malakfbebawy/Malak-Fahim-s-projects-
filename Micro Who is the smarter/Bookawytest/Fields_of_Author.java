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

public class Fields_of_Author extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_of__author);
        Intent intent=this.getIntent();
        int Author_id=intent.getIntExtra("AUTHOR_ID", 0);
        Query_Manager QM=new Query_Manager(this.getApplicationContext());
        final Cursor c=QM.ShowallFIELDOFAUTHOR(Author_id);
        ListView LV=(ListView)findViewById(R.id.listView11);
        Follower_Adpater FA=new Follower_Adpater(this.getApplicationContext(),c,0);
        LV.setAdapter(FA);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent6=new Intent(getApplicationContext(),Field_review.class);
                intent6.putExtra("FIELD_NAME",c.getString(c.getColumnIndex("_id")));
                startActivity(intent6);
                return;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fields_of__author, menu);
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
