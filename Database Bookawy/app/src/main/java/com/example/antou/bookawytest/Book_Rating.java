package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class Book_Rating extends AppCompatActivity {

    private int BOOK_ID;
    TextView TV;
    private Query_Manager QM;
    ListView LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__rating);

        QM=new Query_Manager(getApplicationContext());
        TV=(TextView)findViewById(R.id.textView49);

        Intent intent= this.getIntent();
        BOOK_ID=intent.getIntExtra("BOOK_ID",0);  //3lashan ye3red el rating bta3 el ktab elly dost 3la show rating bta3to

        Cursor T=QM.Show_avgrating_abook(BOOK_ID);
        T.moveToFirst();
        TV.setText("Average is :" + T.getFloat(T.getColumnIndex("_id")));

        LV=(ListView)findViewById(R.id.listView7);
        Cursor c=QM.Select_Ratingbybookid(BOOK_ID);
        Rating_Adapter RA=new Rating_Adapter(this,c,0);
        LV.setAdapter(RA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book__rating, menu);
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
