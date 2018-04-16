package com.example.antou.bookawytest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUp extends AppCompatActivity
{

    private Spinner []Sp;   //for lists array of spinners
    private String []days;   //array of string
    private String[]Months={"January","February","March","April","May","June","July","August","September","October","November","December"};
    private String[]Years;
    private EditText []e;
    Query_Manager QM;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Sp=new Spinner[3];
        Years=new String[51];
        days=new String [31];
        initialize_Spinners();
        e=new EditText[6];
        initialize_Editors();
        QM=new Query_Manager(this.getApplicationContext());
    }

    private void initialize_Editors() {
        e[0]=(EditText)findViewById(R.id.User_name);
        e[1]=(EditText)findViewById(R.id.name);
        e[2]=(EditText)findViewById(R.id.Tele);
        e[3]=(EditText)findViewById(R.id.Email);
        e[4]=(EditText)findViewById(R.id.Password);
        e[5]=(EditText)findViewById(R.id.Confirm_Password);
    }

        private void initialize_Spinners()
    {
        //first one
        for (int i=1;i<32;i++)
            days[i-1]=""+i;                //byemla el array bta3 el days

        Sp[0]=(Spinner)findViewById(R.id.spinner1);
        Sp[0].setSelection(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        Sp[0].setAdapter(adapter);

        //second one
        Sp[1]=(Spinner)findViewById(R.id.spinner2);       //mesh bnemla el array bta3 el months l2en e7na already malieno fo2
        Sp[1].setSelection(0);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,Months);
        Sp[1].setAdapter(adapter2);

        //third one
        for(int i=1970;i<=2020;i++)
            Years [i-1970]=""+i;
        Sp[2]=(Spinner)findViewById(R.id.spinner3);
        Sp[2].setSelection(0);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,Years);
        Sp[2].setAdapter(adapter3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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


    public void Click_Signup(View view) {
        // check en el email maktob correctly


        if (e[0].getText().toString().isEmpty() || e[1].getText().toString().isEmpty() || e[2].getText().toString().isEmpty() || e[3].getText().toString().isEmpty()
                || e[4].getText().toString().isEmpty() || e[5].getText().toString().isEmpty())
        {
            new AlertDialog.Builder(SignUp.this)
                    .setTitle("Error")
                    .setMessage("Sorry, all data must be filled")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // continue with delete
                        }
                    }
                    )
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }


        if (!e[4].getText().toString().equals(e[5].getText().toString()))
        {
            new AlertDialog.Builder(SignUp.this)
                    .setTitle("Error")
                    .setMessage("Password does not match!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }

        if(!isValidEmail(e[3].getText().toString()))
        {
            new AlertDialog.Builder(SignUp.this)
                    .setTitle("Error")
                    .setMessage("Email is not correct")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }


        String User_Name=e[0].getText().toString();
        String Name=e[1].getText().toString();
        String Telephone =e[2].getText().toString();
        String email =e[3].getText().toString();
        String Pass=e[4].getText().toString();

        String dob=Sp[0].getSelectedItem().toString()+"-"+Sp[1].getSelectedItem().toString()+"-"+Sp[2].getSelectedItem().toString();


       int res= QM.Insert_User(User_Name,Name, Telephone,email,Pass,dob);

        if (res== -1)
            new AlertDialog.Builder(SignUp.this)
                    .setTitle("Error")
                    .setMessage("Sorry, this user name is already taken")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        else
        {
            Toast.makeText(this.getApplicationContext(), "Successfully registered in Bookawy :)", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }
    public final static boolean isValidEmail(CharSequence target)
    {
        if (target == null)
        {
            return false;
        }
        else
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
 }

