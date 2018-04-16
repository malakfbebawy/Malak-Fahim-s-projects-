package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Author_review extends AppCompatActivity {
    private Cursor c;
    Integer AUTHOR_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_review);

        Intent intent = this.getIntent();
        AUTHOR_ID = intent.getIntExtra("AUTHOR_ID", 0);
        Query_Manager QM = new Query_Manager(this.getApplicationContext());
        c = QM.Select_Author_byid(AUTHOR_ID);
        InitializeActivity2();
    }

    private void InitializeActivity2() {
        TextView Author_NAME = (TextView) findViewById(R.id.textView18);
        TextView Author_ID = (TextView) findViewById(R.id.textView2);
        TextView Author_Nation = (TextView) findViewById(R.id.textView21);


        c.moveToFirst();
        String BN = c.getString(c.getColumnIndex("AUTHOR"));
        Integer I = c.getInt(c.getColumnIndex("ID"));
        String AN = c.getString(c.getColumnIndex("NATION"));

        Author_NAME.setText(BN);
        Author_ID.setText(I + "");
        Author_Nation.setText(AN);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_author_review, menu);
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

    public void more_info(View v) {
        Intent intent = new Intent(this.getApplicationContext(), Author_infromation.class);
        intent.putExtra("AUTHOR_ID", AUTHOR_ID);
        startActivity(intent);

    }

    public void GET_BOOKS(View V) {
        Intent intent = new Intent(this.getApplicationContext(), BOOK_of_Author.class);
        intent.putExtra("AUTHOR_ID", AUTHOR_ID);
        startActivity(intent);
    }

    public void Show_fields(View V) {
        Intent intent = new Intent(this.getApplicationContext(), Fields_of_Author.class);
        intent.putExtra("AUTHOR_ID", AUTHOR_ID);
        startActivity(intent);
    }

    public void Edit_Author(View V) {

        Intent intent = new Intent(this.getApplicationContext(), Edit_Author.class);
        intent.putExtra("AUTHOR_ID", AUTHOR_ID);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Query_Manager QM = new Query_Manager(this.getApplicationContext());
        c = QM.Select_Author_byid(AUTHOR_ID);
        InitializeActivity2();

    }
}