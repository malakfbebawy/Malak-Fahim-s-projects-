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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class User_Review extends AppCompatActivity {
    private Cursor c;
    private ListView LV;
    private Switch S;
    private String User_name;
    Button B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__review);
        B=(Button)findViewById(R.id.button5);



        Intent intent=this.getIntent();
        User_name= intent.getStringExtra("USER_NAME");    //string = getstring b 3add el primary ba2olo ya5od el user name lama ydos 3la ay 7d 3amel offer
        final String USERNAME=User_name;

        if (User_name.equals(MainActivity.getUser()))
        {
            B.setEnabled(true);
        }
        else{
            B.setEnabled(false);
        }


        final Query_Manager QM=new Query_Manager(this.getApplicationContext());


        S=(Switch)findViewById(R.id.switch1);
        if (USERNAME.equals(MainActivity.getUser())) {
            S.setChecked(false);
            S.setEnabled(false);
        }
            else {
            Cursor x = QM.SELECT_FOLLOWERS(MainActivity.getUser(), USERNAME);
            if (x.getCount() == 0)
                S.setChecked(false);
            else
                S.setChecked(true);
            S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        QM.INSERT_FOLLOWER(MainActivity.getUser(), USERNAME);
                        return;
                    } else {
                        QM.Delete_FOLLOWER(MainActivity.getUser(), USERNAME);
                    }
                }
            });
        }
       c= QM.Select_user_byusername(USERNAME);        //7asb sql el username elly 5ato mn click ab3atoo ll queri
        InitializeActivity3();


        c=QM.Show_myoffersandreq(MainActivity.getUser());
        LV=(ListView)findViewById(R.id.listView);
        HomeAdapter adapter = new HomeAdapter(this, c, 0);
        LV.setAdapter(adapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final Integer pos=i;

                new AlertDialog.Builder(User_Review.this)
                        .setTitle("Warning")
                        .setMessage("Do you Want to delete this?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                c.moveToPosition(pos);
                                int Book_id = c.getInt(c.getColumnIndex("BOOK_ID"));

                                QM.Delete_offerandreq(MainActivity.getUser(), Book_id);

                             //Select AGAIN AFTER DELETION
                                c=QM.Show_myoffersandreq(MainActivity.getUser());
                                LV = (ListView) findViewById(R.id.listView);
                                HomeAdapter adapter = new HomeAdapter(getApplicationContext(), c, 0);
                                LV.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });
    }


    private void InitializeActivity3() {
        TextView NAME=(TextView)findViewById(R.id.textView23);
        TextView Dateofbirth=(TextView)findViewById(R.id.textView24);
        TextView Email=(TextView)findViewById(R.id.textView25);
        TextView phone=(TextView)findViewById(R.id.textView27);


        c.moveToFirst();
        String NM= c.getString(c.getColumnIndex("USER"));  //esm el column b3d el eliase
        String DOB= c.getString(c.getColumnIndex("DOB"));
        String mail= c.getString(c.getColumnIndex("EMAIL"));
        String PN=c.getString(c.getColumnIndex("TELEPHONE"));

        NAME.setText(NM);         // yozher esm el user elly 3amel offer aw request
        Dateofbirth.setText(DOB);
        Email.setText(mail);
        phone.setText(PN);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user__review, menu);
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

    public void ShowFollowers(View v)
    {
        Intent intent=new Intent(getApplicationContext(),Show_Followers.class);
        intent.putExtra("USER_NAME",User_name);
        startActivity(intent);
    }

    public void Liked_fields(View V)
    {
        Intent intent=new Intent(getApplicationContext(),Show_liked_fields.class);
        intent.putExtra("USER_NAME",User_name);
        startActivity(intent);
    }

    public void Edit_User(View V)
    {
        Intent intent=new Intent(this.getApplicationContext(),Edit_User.class);
        intent.putExtra("USER_NAME",User_name);
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        B=(Button)findViewById(R.id.button5);



        Intent intent=this.getIntent();
        User_name= intent.getStringExtra("USER_NAME");    //string = getstring b 3add el primary ba2olo ya5od el user name lama ydos 3la ay 7d 3amel offer
        final String USERNAME=User_name;

        if (User_name.equals(MainActivity.getUser()))
        {
            B.setEnabled(true);
        }
        else{
            B.setEnabled(false);
        }


        final Query_Manager QM=new Query_Manager(this.getApplicationContext());


        S=(Switch)findViewById(R.id.switch1);
        if (USERNAME.equals(MainActivity.getUser())) {
            S.setChecked(false);
            S.setEnabled(false);
        }
        else {
            Cursor x = QM.SELECT_FOLLOWERS(MainActivity.getUser(), USERNAME);
            if (x.getCount() == 0)
                S.setChecked(false);
            else
                S.setChecked(true);
            S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        QM.INSERT_FOLLOWER(MainActivity.getUser(), USERNAME);
                        return;
                    } else {
                        QM.Delete_FOLLOWER(MainActivity.getUser(), USERNAME);
                    }
                }
            });
        }
        c= QM.Select_user_byusername(USERNAME);        //7asb sql el username elly 5ato mn click ab3atoo ll queri
        InitializeActivity3();


        c=QM.Show_myoffersandreq(MainActivity.getUser());
        LV=(ListView)findViewById(R.id.listView);
        HomeAdapter adapter = new HomeAdapter(this, c, 0);
        LV.setAdapter(adapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if (User_name!=MainActivity.getUser())return;

                final Integer pos=i;

                new AlertDialog.Builder(User_Review.this)
                        .setTitle("Warning")
                        .setMessage("Do you Want to delete this?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                c.moveToPosition(pos);
                                int Book_id = c.getInt(c.getColumnIndex("BOOK_ID"));

                                QM.Delete_offerandreq(MainActivity.getUser(), Book_id);

                                //Select AGAIN AFTER DELETION
                                c=QM.Show_myoffersandreq(MainActivity.getUser());
                                LV = (ListView) findViewById(R.id.listView);
                                HomeAdapter adapter = new HomeAdapter(getApplicationContext(), c, 0);
                                LV.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });

    }

}