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
import android.widget.ListView;

public class Admin_home extends AppCompatActivity {

    ListView LV;
    Query_Manager QM;
    public  Cursor c;
    public Admin_home_Adapter A;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        LV=(ListView)findViewById(R.id.listView13);
        QM=new Query_Manager(this.getApplicationContext());
        c=QM.Show_STOREOFFERS(AdminLog.BSname,AdminLog.BSloc);
        A=new Admin_home_Adapter(this,c,0);
        LV.setAdapter(A);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c.moveToPosition(i);
                final Integer BOOK_ID = c.getInt(c.getColumnIndex("ID"));

                new AlertDialog.Builder(Admin_home.this)
                        .setTitle("Delete")
                        .setMessage("delete this offer ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                QM.Delete_offerbyadmin(AdminLog.BSname,AdminLog.BSloc,BOOK_ID);
                                c=QM.Show_STOREOFFERS(AdminLog.BSname,AdminLog.BSloc);
                                A=new Admin_home_Adapter(getApplicationContext(),c,0);
                                LV.setAdapter(A);
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

    public void Add_Offer_Admin(View C)
    {
        Intent intent =new Intent(this.getApplicationContext(),Add_Offer_Admin.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
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

    @Override
    protected void onResume()
    {
        super.onResume();

        LV=(ListView)findViewById(R.id.listView13);
        QM=new Query_Manager(this.getApplicationContext());
        c=QM.Show_STOREOFFERS(AdminLog.BSname,AdminLog.BSloc);
        A=new Admin_home_Adapter(this,c,0);
        LV.setAdapter(A);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c.moveToPosition(i);
                final Integer BOOK_ID = c.getInt(c.getColumnIndex("ID"));

                new AlertDialog.Builder(Admin_home.this)
                        .setTitle("Delete")
                        .setMessage("delete this offer ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                QM.Delete_offerbyadmin(AdminLog.BSname, AdminLog.BSloc, BOOK_ID);
                                c = QM.Show_STOREOFFERS(AdminLog.BSname, AdminLog.BSloc);
                                A = new Admin_home_Adapter(getApplicationContext(), c, 0);
                                LV.setAdapter(A);
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
