package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Edit_Book extends AppCompatActivity {

    Query_Manager QM;
    EditText NAME;
    EditText Edition;
    EditText Publish_year;
    Integer BOOK_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__book);

        Intent intent =this.getIntent();
        BOOK_ID=intent.getIntExtra("BOOK_ID",0);


        QM=new Query_Manager(this.getApplicationContext());
        NAME=(EditText)findViewById(R.id.Edit_book_name);
        Edition=(EditText)findViewById(R.id.Edit_book_edition);
        Publish_year=(EditText)findViewById(R.id.editText5);


        Cursor c=QM.Select_BOOK_byid(BOOK_ID);
        c.moveToFirst();

        NAME.setText(c.getString(c.getColumnIndex("BOOK")));
        Edition.setText(""+c.getInt(c.getColumnIndex("_id")));    //qosen 3lashan t7awel el integer l string
        Publish_year.setText(""+c.getInt(c.getColumnIndex("PUBLISH_YEAR")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit__book, menu);
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

    public void Submit(View V)
    {
        if (NAME.getText().toString().isEmpty()) {
            Toast.makeText(this.getApplicationContext(), "Book must have a Name !", Toast.LENGTH_LONG).show();
            return;
        }

        QM.EDITBOOK(BOOK_ID,NAME.getText().toString(),Integer.parseInt(Publish_year.getText().toString()),Integer.parseInt(Edition.getText().toString()));
        onBackPressed();
    }
}
