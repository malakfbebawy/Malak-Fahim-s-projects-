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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Search extends AppCompatActivity {

    Spinner Sp;
    ListView LV;
    Button B;
    Cursor c;
    Query_Manager QM;
    EditText ET;
    static String Search_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Sp = (Spinner) findViewById(R.id.spinner);
        B = (Button) findViewById(R.id.button2);
        LV = (ListView) findViewById(R.id.listView3);
        QM = new Query_Manager(this.getApplicationContext());
        ET = (EditText) findViewById(R.id.editText);

        String[] arr = {"Books", "Authors", "BookStores", "User Offers & Requests", "Book_Stores_offers", "Users","Fields"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        Sp.setAdapter(adapter);
        Sp.setSelection(0);

        Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
            LV.setAdapter(null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }


        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                c.moveToPosition(position);
                switch (Sp.getSelectedItemPosition())
                {
                    case 0:
                        Intent intent=new Intent(getApplicationContext(),Book_review.class);
                        intent.putExtra("BOOK_ID",c.getInt(c.getColumnIndex("_id")));
                        startActivity(intent);
                        return;

                    case 1:
                        Intent intent2=new Intent(getApplicationContext(),Author_review.class);
                        intent2.putExtra("AUTHOR_ID",c.getInt(c.getColumnIndex("_id")));
                        startActivity(intent2);
                        return;

                    case 2:
                        Intent intent3=new Intent(getApplicationContext(),BookStore_review.class);
                        intent3.putExtra("BOOK_STORE_NAME",c.getString(c.getColumnIndex("_id")));
                        intent3.putExtra("LOCATION",c.getString(c.getColumnIndex("LOCATION")));
                        startActivity(intent3);
                        return;

                    case 3:
                        final Integer BOOK_ID = c.getInt(c.getColumnIndex("_id")); //BOOK.ID as _id
                        final String User_name = c.getString(c.getColumnIndex("USER_NAME"));

                        new AlertDialog.Builder(Search.this)
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
                        return;

                    case 4:
                        final Integer BOOK_ID2 = c.getInt(c.getColumnIndex("_id")); //BOOK.ID as _id
                        final String BOOKSTORE = c.getString(c.getColumnIndex("STORE_NAME"));
                        final String LOCATION = c.getString(c.getColumnIndex("LOC"));

                        new AlertDialog.Builder(Search.this)
                                .setTitle("User or Book")
                                .setMessage("Do you Want to see user or The Book ?")
                                .setPositiveButton("The Book Store", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(getApplicationContext(),BookStore_review.class);
                                        intent.putExtra("BOOK_STORE_NAME",BOOKSTORE);
                                        intent.putExtra("LOCATION",LOCATION);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("The BOOK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(getApplicationContext(),Book_review.class);
                                        intent.putExtra("BOOK_ID",BOOK_ID2);
                                        startActivity(intent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return;

                    case 5:
                        Intent intent5=new Intent(getApplicationContext(),User_Review.class);
                       if (c.getInt(c.getColumnIndex("count"))!=0)
                        intent5.putExtra("USER_NAME", c.getString(c.getColumnIndex("_id")));
                        else
                           intent5.putExtra("USER_NAME", Search_user);
                        startActivity(intent5);
                        return;
                    case 6:
                        Intent intent6=new Intent(getApplicationContext(),Field_review.class);
                        intent6.putExtra("FIELD_NAME",c.getString(c.getColumnIndex("_id")));
                        startActivity(intent6);
                        return;

                }
            }

        });
    }

    public void Search(View V) {
        if (ET.getText().toString() == "") {
            Toast.makeText(this.getApplicationContext(),"Nothing to search about!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (Sp.getSelectedItemPosition()) {
            case 0:
                c = QM.Select_Book_byname(ET.getText().toString());
                if (c.getCount() == 0) {
                    Toast.makeText(this.getApplicationContext(), "No Such A book !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Book_Adapter adapter0 = new Book_Adapter(this, c, 0);
                LV.setAdapter(adapter0);
                return;

            case 1:
                c = QM.Select_Author_byname(ET.getText().toString());
                if (c.getCount() == 0)
                {
                    Toast.makeText(this.getApplicationContext(), "No Such an Author !", Toast.LENGTH_SHORT).show();
                    LV.setAdapter(null);
                    return;
                }
                Authors_adapter adapter1 = new Authors_adapter(this,c,0);
                LV.setAdapter(adapter1);
                return;


            case 2:
                c=QM.Select_Bookstore_byname(ET.getText().toString());
                if (c.getCount()==0) {
                    Toast.makeText(this.getApplicationContext(), "No Such a bookstore !", Toast.LENGTH_SHORT).show();
                    LV.setAdapter(null);
                    return;
                }
                    BookStore_adapter adapter2 = new BookStore_adapter(this,c,0);
                LV.setAdapter(adapter2);
                return;

            case 3:
                c=QM.getOffersByBookName(ET.getText().toString());//Get offers according to the name of the book
                if (c.getCount()==0) {
                    Toast.makeText(this.getApplicationContext(), "No Such A book !", Toast.LENGTH_SHORT).show();
                    LV.setAdapter(null);
                    return;
                }
                    HomeAdapter adapter3 = new HomeAdapter(this,c,0);
                LV.setAdapter(adapter3);
                return;

            case 4:
                c=QM.Select_bookstores_offers(ET.getText().toString());//Get offers according to the name of the book
                if (c.getCount()==0) {
                    Toast.makeText(this.getApplicationContext(), "No Such A book !", Toast.LENGTH_SHORT).show();
                    LV.setAdapter(null);
                    return;
                }
                BookStores_offers_adapter adapter4 = new BookStores_offers_adapter(this,c,0);
                LV.setAdapter(adapter4);
                return;


            case 5:
                c=QM.Select_User_and_MUTUAL(ET.getText().toString());//Get offers according to the name of the book
                Search_user=ET.getText().toString();
                if (QM.Select_user_byusername(ET.getText().toString()).getCount()==0) {
                    Toast.makeText(this.getApplicationContext(), "No Such A user !", Toast.LENGTH_SHORT).show();
                    LV.setAdapter(null);
                    return;
                }
                User_Adapter adapter5 = new User_Adapter(this,c,0);
                LV.setAdapter(adapter5);
                return;

            case 6:
                c = QM.GET_FIELDNAME(ET.getText().toString());//Get offers according to the name of the book
                if (c.getCount()==0) {
                    Toast.makeText(this.getApplicationContext(), "No Such A Field !", Toast.LENGTH_SHORT).show();
                    LV.setAdapter(null);
                    return;
                }
                Follower_Adpater adapter6 = new Follower_Adpater(this,c,0);
                LV.setAdapter(adapter6);
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    static String GetSearchUser()
    {
        return Search_user;
    }
}
