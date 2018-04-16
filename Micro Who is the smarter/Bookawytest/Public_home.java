package com.example.antou.bookawytest;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Public_home extends AppCompatActivity {
    ListView list;
    Query_Manager QM;
    String User_Name;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_home);

        TV = (TextView) findViewById(R.id.NoOffers2);
        QM = new Query_Manager(this.getApplicationContext());

        list = (ListView) findViewById(R.id.listView2);
        User_Name = MainActivity.getUser();
        final Cursor c = QM.getOffers(User_Name);
        c.moveToFirst();

        if (c.getCount() != 0)
        {
            HomeAdapter adapter = new HomeAdapter(this, c, 0);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    c.moveToPosition(position);
                    final Integer BOOK_ID = c.getInt(c.getColumnIndex("_id")); //BOOK.ID as _id
                    final String User_name = c.getString(c.getColumnIndex("USER_NAME"));

                    new AlertDialog.Builder(Public_home.this)
                            .setTitle("User or Book")
                            .setMessage("Do you Want to see user or The Book ?")
                            .setPositiveButton("The USER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(getApplicationContext(),User_Review.class);
                                    intent.putExtra("USER_NAME",User_name);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("The BOOk", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(getApplicationContext(),Book_review.class);
                                    intent.putExtra("BOOK_ID",BOOK_ID);
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            });


        } else {
            TV.setText("No Offers for Today");

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_public_home, menu);
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
