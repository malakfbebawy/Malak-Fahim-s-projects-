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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Choose_Author_by_admin extends AppCompatActivity {


    EditText Name;
    ListView LV;
    Query_Manager QM;
    Cursor c;
    int Book_ID;

    int times=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__author_by_admin);

        Name=(EditText)findViewById(R.id.Admin_search_Author);
        LV=(ListView)findViewById(R.id.Admin_search_Author_LV);
        QM=new Query_Manager(this.getApplicationContext());
        Intent intent=this.getIntent();
        Book_ID=intent.getIntExtra("BOOK_ID",0);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                c.moveToPosition(pos);
                QM.Insert_to_Writes(Book_ID, c.getInt(c.getColumnIndex("_id")));

                //dialog to tell him that it is finished adding this author

                new AlertDialog.Builder(Choose_Author_by_admin.this)
                        .setTitle("Done!")
                        .setMessage("Adding Author Done .. if you want to add another author to the same book continue. Or just press finish")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                times++;
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }

    public void Search(View V)
    {
        c=QM.Select_Author_byname(Name.getText().toString());
        if (c.getCount() == 0)
        {
            Toast.makeText(this.getApplicationContext(), "No Such an Author !", Toast.LENGTH_SHORT).show();
            return;
        }
        Authors_adapter adapter1 = new Authors_adapter(this,c,0);
        LV.setAdapter(adapter1);
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose__author, menu);
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
    public void add_new(View V)
    {
        if (Name.getText().toString().isEmpty())
        {
            Toast.makeText(this.getApplicationContext(),"You have to write something to add !",Toast.LENGTH_SHORT).show();
            return;
        }

        String Author_name=Name.getText().toString();
        //Author_name and Book_ID
        int Author_id= QM.Insert_AUTHOR(Author_name);
        QM.Insert_to_Writes(Book_ID, (int)Author_id);


        new AlertDialog.Builder(Choose_Author_by_admin.this)
                .setTitle("Done!")
                .setMessage("Adding Author Done .. if you want to add another author to the same book continue. Or just press finish")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        times++;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    public void Finish(View v)
    {
        if (times==0) {
            Toast.makeText(this.getApplicationContext(), "You have to add at least one Author", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(this.getApplicationContext(),Admin_home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //clear activity stack
        startActivity(intent);
    }

}
