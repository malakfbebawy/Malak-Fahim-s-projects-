


package com.example.malakfahim.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Malak Fahim on 8/9/2016.
 */
public class Sign_up extends AppCompatActivity
{
    private Spinner[]sp;
    private String[]days;
    private String[]Months;
    private String[]years;
    private EditText[]e;
    Query_Manager qm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sp=new Spinner[3];      //bt7adad 3add el element fl array of spinner
        days=new String[31];     //bt7adad 3add el element fl array of string
        Months=new String[12];
        years=new String[73];
        e=new EditText[5];
        initializeditors();
        initializespinners();
        qm=new Query_Manager(this.getApplicationContext());
    }
    private void initializeditors()
    {
        e[0]=(EditText)findViewById(R.id.User_name);
        e[1]=(EditText)findViewById(R.id.name);
        e[2]=(EditText)findViewById(R.id.Tele);
        e[3]=(EditText)findViewById(R.id.Password);
        e[4]=(EditText)findViewById(R.id.Confirm_Password);
    }

    private void initializespinners()
    {
        for(int i=1; i<=31; i++)
        {
            days[i-1]=""+i;
        }
        //days
        sp[0]=(Spinner)findViewById(R.id.spinner1);
        sp[0].setSelection(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        sp[0].setAdapter(adapter);

        //months
        for(int i=1; i<=12; i++)
        {
            Months[i-1]=""+i;
        }

        sp[1]=(Spinner)findViewById(R.id.spinner2);
        sp[1].setSelection(0);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Months);
        sp[1].setAdapter(adapter2);

        //years
        for(int i=1950; i<=2022; i++)
        {
            years[i-1950]=""+i;
        }
        sp[2]=(Spinner)findViewById(R.id.spinner3);
        sp[2].setSelection(0);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        sp[2].setAdapter(adapter3);
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

    public void Click_Signup(View view)
    {
        if(e[0].getText().toString().isEmpty() || e[1].getText().toString().isEmpty() ||  e[2].getText().toString().isEmpty() ||
                e[3].getText().toString().isEmpty() ||  e[4].getText().toString().isEmpty() )
        {
            new AlertDialog.Builder(Sign_up.this)
                    .setTitle("Eror").setMessage("You must fill all data")
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


        if(!e[3].getText().toString().equals(e[4].getText().toString()))
        {

            new AlertDialog.Builder(Sign_up.this)
                    .setTitle("Error")
                    .setMessage("Password does not match with confirm password!")
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
        String name=e[1].getText().toString();
        String Telephone =e[2].getText().toString();
        String Pass=e[3].getText().toString();
        String dop=sp[0].getSelectedItem().toString()+"-"+sp[1].getSelectedItem().toString()+"-"+sp[2].getSelectedItem().toString();
        int result= qm.Insert_User(User_Name,name, Telephone,Pass,dop);
        qm.Backup(); //tsama3 mn el database elly 3la el mobile ll database  fl firefox
        if(result==-1)
        {
            new AlertDialog.Builder(Sign_up.this)
                    .setTitle("Error")
                    .setMessage("Sorry, change the user name because it is already taken")
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
            Toast.makeText(this.getApplicationContext(), "You registered in 5edma application successfully", Toast.LENGTH_LONG).show();
            onBackPressed();
        }



    }
}
