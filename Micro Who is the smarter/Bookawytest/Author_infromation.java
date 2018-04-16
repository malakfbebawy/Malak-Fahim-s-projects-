package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Author_infromation extends AppCompatActivity {

    public Query_Manager QM;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_infromation);
        Intent intent=this.getIntent();

        int author_id =intent.getIntExtra("AUTHOR_ID",0);
        QM=new Query_Manager(this.getApplicationContext());
        Cursor c=QM.Select_Author_byid(author_id);
        c.moveToFirst();

        TV=(TextView)findViewById(R.id.textView44);
        TV.setText(c.getString(c.getColumnIndex("ABOUT")));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_author_infromation, menu);
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
