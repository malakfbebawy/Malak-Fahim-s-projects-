package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLog extends AppCompatActivity {

    EditText NAME;
    EditText PASS;
    Query_Manager QM;
    public static String BSname;
    public static String BSloc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log);

        NAME=(EditText)findViewById(R.id.Ausername_entry);
        PASS=(EditText)findViewById(R.id.Apassword_entry);
        QM=new Query_Manager(this.getApplicationContext());
    }

    public void Submit(View V)
    {
        Cursor c=QM.Select_Admin(NAME.getText().toString(),PASS.getText().toString());
        if (c.getCount()==0) {
            Toast.makeText(this.getApplicationContext(), "UserName or Password are wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(this.getApplicationContext(), "Welcome To Bookawy", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this.getApplicationContext(),Admin_home.class);
            c.moveToFirst();
            BSname=c.getString(c.getColumnIndex("BookStoreName"));
            BSloc=c.getString(c.getColumnIndex("LOCATION"));

            startActivity(intent);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_log, menu);
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
