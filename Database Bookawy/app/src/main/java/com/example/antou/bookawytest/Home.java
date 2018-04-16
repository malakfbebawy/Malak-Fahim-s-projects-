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


public class Home extends AppCompatActivity {

    ListView list;
    Query_Manager QM;
    String User_Name;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TV = (TextView) findViewById(R.id.NoOffers);           //
        QM = new Query_Manager(this.getApplicationContext());

        list = (ListView) findViewById(R.id.listView);
        User_Name = MainActivity.getUser();
        final Cursor c = QM.getFriendsOffers(User_Name);
        c.moveToFirst();

        if (c.getCount() != 0) {
            HomeAdapter adapter = new HomeAdapter(this, c, 0);
            list.setAdapter(adapter);



            list.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    c.moveToPosition(position);
                    final Integer BOOK_ID = c.getInt(c.getColumnIndex("_id")); //BOOK.ID as _id
                    final String User_name = c.getString(c.getColumnIndex("USER_NAME"));

                    new AlertDialog.Builder(Home.this)
                            .setTitle("User or Book")
                            .setMessage("Do you Want to see user or The Book ?")

                            .setPositiveButton("The USER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(getApplicationContext(),User_Review.class);
                                    intent.putExtra("USER_NAME",User_name); //ya3ny el intent ll profile bta3 el user elly 3ard el book
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

    public Integer State;

    public void Add_offer(View v)
    {
        new AlertDialog.Builder(Home.this)
                .setTitle("Offer or Request ?")
                .setMessage("")
                .setPositiveButton("Offer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        State=1;
                        Intent intent=new Intent(getApplicationContext(),Add_offer_or_request.class);
                        intent.putExtra("STATE",State);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Request", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        State=0;
                        Intent intent=new Intent(getApplicationContext(),Add_offer_or_request.class);
                        intent.putExtra("STATE",State);
                        startActivity(intent);
                        }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override            //el 7agat elly fo2 {public ,profile ,toprated ,search}
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch(item.getItemId()){
            case R.id.menu_profile:
                intent=new Intent(this.getApplicationContext(),User_Review.class);
                intent.putExtra("USER_NAME",MainActivity.getUser());
                startActivity(intent);
                return true;
            case R.id.menu_Public:
                intent=new Intent(this.getApplicationContext(),Public_home.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent=new Intent(this.getApplicationContext(),Search.class);
                startActivity(intent);
                return true;
            case R.id.menu_Top:
                intent=new Intent(this.getApplicationContext(),Top_Rated.class);
                startActivity(intent);
                return true;


        }
        return false;
    }


    //same code in onCreate just in case he entered the activity from onresume

    @Override
    protected void onResume ()
    {
        super.onResume();
        TV = (TextView) findViewById(R.id.NoOffers);
        QM = new Query_Manager(this.getApplicationContext());

        list = (ListView) findViewById(R.id.listView);
        User_Name = MainActivity.getUser();
        final Cursor c = QM.getFriendsOffers(User_Name);
        c.moveToFirst();

        if (c.getCount() != 0) {
            HomeAdapter adapter = new HomeAdapter(this, c, 0);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    c.moveToPosition(position);
                    final Integer BOOK_ID = c.getInt(c.getColumnIndex("_id")); //BOOK.ID as _id
                    final String User_name = c.getString(c.getColumnIndex("USER_NAME"));

                    new AlertDialog.Builder(Home.this)
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


        }
        else
        {
            TV.setText("No Offers for Today");

        }


    }

}
