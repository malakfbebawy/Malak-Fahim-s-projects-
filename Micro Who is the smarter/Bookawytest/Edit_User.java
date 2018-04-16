package com.example.antou.bookawytest;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Edit_User extends AppCompatActivity {

    EditText User_Name;
    EditText Name;
    EditText Tele;
    EditText Email;
    EditText Password;
    EditText Confirm;

    public String[] days;
    public String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public String[] Years;

    Spinner[] Sp;
    Query_Manager QM;
    String User_name;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__user);
        initializeActivity();
        Intent intent = this.getIntent();
        User_name = intent.getStringExtra("USER_NAME");

        QM = new Query_Manager(this.getApplicationContext());
        c = QM.Select_user_byusername(User_name);
        c.moveToFirst();

        User_Name.setText(User_name);
        Name.setText(c.getString(c.getColumnIndex("USER")));
        Tele.setText(c.getString(c.getColumnIndex("TELEPHONE")));
        Email.setText(c.getString(c.getColumnIndex("EMAIL")));
        Password.setText(c.getString(c.getColumnIndex("PASSWORD")));
        Confirm.setText(c.getString(c.getColumnIndex("PASSWORD")));

        initializeSpinners();
    }

    private void initializeSpinners() {

        String DOB = c.getString(c.getColumnIndex("DOB"));
        String[] TrimmedDOB = DOB.split("-");

        int select = 0;
        //January","February","March","April","May","June","July","August","September","October","November","December"
        Sp[0].setSelection(Integer.parseInt(TrimmedDOB[0]) - 1);
        if (TrimmedDOB[1].equals("January"))
            select = 0;
        else if (TrimmedDOB[1].equals("February"))
            select = 1;
        else if (TrimmedDOB[1].equals("March"))
            select = 2;
        else if (TrimmedDOB[1].equals("April"))
            select = 3;
        else if (TrimmedDOB[1].equals("May"))
            select = 4;
        else if (TrimmedDOB[1].equals("June"))
            select = 5;
        else if (TrimmedDOB[1].equals("July"))
            select = 6;
        else if (TrimmedDOB[1].equals("August"))
            select = 7;
        else if (TrimmedDOB[1].equals("September"))
            select = 8;
        else if (TrimmedDOB[1].equals("October"))
            select = 9;
        else if (TrimmedDOB[1].equals("November"))
            select = 10;
        else if (TrimmedDOB[1].equals("December"))
            select = 11;


        Sp[1].setSelection(select);

        Sp[2].setSelection(Integer.parseInt(TrimmedDOB[2]) - 1970);
    }

    private void initializeActivity() {
        Sp = new Spinner[3];
        Years = new String[51];
        days = new String[31];

        Name = (EditText) findViewById(R.id.name);
        User_Name = (EditText) findViewById(R.id.User_name);
        Tele = (EditText) findViewById(R.id.Tele);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Confirm = (EditText) findViewById(R.id.Confirm_Password);

        Sp = new Spinner[3];
        for (int i = 1; i < 32; i++)
            days[i - 1] = "" + i;

        Sp[0] = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        Sp[0].setAdapter(adapter);

        //second one
        Sp[1] = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Months);
        Sp[1].setAdapter(adapter2);

        //third one
        for (int i = 1970; i <= 2020; i++)
            Years[i - 1970] = "" + i;
        Sp[2] = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Years);
        Sp[2].setAdapter(adapter3);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit__user, menu);
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

    public void Submit(View V) {


        if (Name.getText().toString().isEmpty() || Tele.getText().toString().isEmpty() || Email.getText().toString().isEmpty() || Password.getText().toString().isEmpty()
                || Confirm.getText().toString().isEmpty() || User_Name.getText().toString().isEmpty()) {
            new AlertDialog.Builder(Edit_User.this)
                    .setTitle("Error")
                    .setMessage("Sorry, all data must be filled")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }


        if (!Password.getText().toString().equals(Confirm.getText().toString())) {
            new AlertDialog.Builder(Edit_User.this)
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

        if(!isValidEmail(Email.getText().toString()))
        {
            new AlertDialog.Builder(Edit_User.this)
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




        String User = User_Name.getText().toString();
        String Na = Name.getText().toString();
        String Telephone = Tele.getText().toString();
        String email = Email.getText().toString();
        String Pass = Password.getText().toString();

        String dob = Sp[0].getSelectedItem().toString() + "-" + Sp[1].getSelectedItem().toString() + "-" + Sp[2].getSelectedItem().toString();


        long res = QM.EDITUSER(User, Na, Pass, Telephone, email, dob);

        if (res == 0)
            new AlertDialog.Builder(Edit_User.this)
                    .setTitle("Error")
                    .setMessage("Sorry, this user name is already taken")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        else {
            onBackPressed();

        }

    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}