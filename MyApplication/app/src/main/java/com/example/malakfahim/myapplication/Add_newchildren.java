package com.example.malakfahim.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Malak Fahim on 8/22/2016.
 */
public class Add_newchildren extends AppCompatActivity
{
    Query_Manager qm;
    EditText e1name;
    EditText e2year;
    EditText e3priest;
    EditText e4dob;
    EditText e5phone;
    EditText e6adress;




    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newchildren);

        e1name=(EditText)findViewById(R.id.name);
        e2year=(EditText)findViewById(R.id.year);
        e3priest=(EditText)findViewById(R.id.priest);
        e4dob=(EditText)findViewById(R.id.dob);
        e5phone=(EditText)findViewById(R.id.phone);
        e6adress=(EditText)findViewById(R.id.adress);
        qm=new Query_Manager(this.getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_newchildren, menu);
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

    public void add(View view)
    {

        String name = e1name.getText().toString();
        String year = e2year.getText().toString();
        String priest = e3priest.getText().toString();
        String dateofbirth = e4dob.getText().toString();
        String phone=e5phone.getText().toString();
        String address=e6adress.getText().toString();
        int ret=qm.INSERT_ma5dom(name,year,priest,dateofbirth,phone,address);
        qm.Backup();

        if(ret==-1 || name.isEmpty())
        {
            new AlertDialog.Builder(Add_newchildren.this)
                    .setTitle("Error")
                    .setMessage("Sorry,You must fill all data")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        else
        {
            Toast.makeText(this.getApplicationContext(),"Successfully added",Toast.LENGTH_LONG).show();
            onBackPressed();   //3ashan el app yerga3 ll activity elly 2ablo
        }

    }


}
