package com.example.antou.bookawytest;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BookStore_review extends AppCompatActivity {
    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store_review);
        Intent intent=this.getIntent();
        String Store_Name= intent.getStringExtra("BOOK_STORE_NAME");//string = getstring b 3add el primary ba2olo ya5od el store name lama ydos 3la 7aga
        String Location= intent.getStringExtra("LOCATION");
        Query_Manager QM=new Query_Manager(this.getApplicationContext());
        c= QM.Select_store_bynameandloc(Store_Name, Location);        ////7asb sql el username elly 5ato mn click ab3atoo ll queri
        InitializeActivity4();
    }


    private void InitializeActivity4() {
        TextView Store_NAME=(TextView)findViewById(R.id.textView32);
        TextView address=(TextView)findViewById(R.id.textView33);
        TextView location=(TextView)findViewById(R.id.textView34);
        TextView Time=(TextView)findViewById(R.id.textView35);


        c.moveToFirst();
        String name= c.getString(c.getColumnIndex("_id"));  //esm el column b3d el eliase
        String addr= c.getString(c.getColumnIndex( "ACTUAL_ADDRESS"));
        String loc= c.getString(c.getColumnIndex("LOCATION"));
        String time=c.getString(c.getColumnIndex("WORKING_TIME_SD"));


        Store_NAME.setText(name);
        address.setText(addr);
        location.setText(loc);
        Time.setText(time);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_store_review, menu);
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
