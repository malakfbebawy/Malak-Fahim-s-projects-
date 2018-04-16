package com.example.antou.bookawytest;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Reporting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        Query_Manager QM=new Query_Manager(this.getApplicationContext());
        TextView[]T=new TextView[6];

        T[0]=(TextView)findViewById(R.id.textView60);
        T[1]=(TextView)findViewById(R.id.textView61);
        T[2]=(TextView)findViewById(R.id.textView62);
        T[3]=(TextView)findViewById(R.id.textView63);
        T[4]=(TextView)findViewById(R.id.textView64);
        T[5]=(TextView)findViewById(R.id.textView65);


        Cursor c=QM.SELECT_numofALLUSERS();
        c.moveToFirst();
        T[0].setText("Num of Users: "+c.getInt(c.getColumnIndex("_id")));

        Cursor cc=QM.SELECT_numofALLoffandreq();
        cc.moveToFirst();
        T[1].setText("Num of offers and Reqs: " + cc.getInt(cc.getColumnIndex("_id")));

        Cursor ccc=QM.SELECT_numofALLBOOK();
        ccc.moveToFirst();
        T[2].setText("Num of Books: " + ccc.getInt(ccc.getColumnIndex("_id")));

        Cursor cccc=QM.SELECT_AVGPRICEINUSERSOFFERS();
        cccc.moveToFirst();
        T[3].setText("Average of price in users offers: " + cccc.getFloat(cccc.getColumnIndex("_id")));


        Cursor c4=QM.SELECT_AVGPRICEINSTORESOFFERS();
        c4.moveToFirst();
        T[4].setText("Average of price in book stores offers " + c4.getFloat(c4.getColumnIndex("_id")));

        c.moveToFirst();
        cc.moveToFirst();
        float x=(float)c.getInt(c.getColumnIndex("_id"));
        float Y=cc.getInt(cc.getColumnIndex("_id"));
        float q;
        if (Y!=0)
            q=x/Y;
        else q=0;

        T[5].setText("Average of Active Users: " +q);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reporting, menu);
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
