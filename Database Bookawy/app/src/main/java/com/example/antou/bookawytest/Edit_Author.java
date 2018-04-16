package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_Author extends AppCompatActivity {

    Query_Manager QM;
    int Author_ID;
    EditText NAME;
    EditText Nationality;
    EditText About;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__author);
        QM=new Query_Manager(this.getApplicationContext());
        Intent intent =this.getIntent();
        Author_ID=intent.getIntExtra("AUTHOR_ID",0); //to get author id
        Cursor c=QM.Select_Author_byid(Author_ID);
        c.moveToFirst();

        NAME=(EditText)findViewById(R.id.Edit_author_name);
        Nationality=(EditText)findViewById(R.id.Edit_author_Nation);
        About=(EditText)findViewById(R.id.Edit_author_About);


        NAME.setText(c.getString(c.getColumnIndex("AUTHOR")));
        Nationality.setText(c.getString(c.getColumnIndex("NATION")));
        About.setText(c.getString(c.getColumnIndex("ABOUT")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit__author, menu);
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
            Toast.makeText(this.getApplicationContext(), "Author must have a Name !", Toast.LENGTH_LONG).show();
            return;
        }

        QM.EDITAUTHOR(Author_ID,NAME.getText().toString(),Nationality.getText().toString(),About.getText().toString());

        onBackPressed();


    }

}
