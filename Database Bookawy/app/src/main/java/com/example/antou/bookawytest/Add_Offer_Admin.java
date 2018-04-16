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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Offer_Admin extends AppCompatActivity {

    public int state;
    private EditText ET;        //Edit text ya3ny text te2dar tekteb fih lakn Text view ya3ny view haye3red 7aga w mesh hate2der tekteb gwah
    ListView LV;
    Cursor c;
    Query_Manager QM;
    EditText price;
    EditText Field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__offer__admin);


        ET=(EditText)findViewById(R.id.Admin_Search);
        LV=(ListView)findViewById(R.id.Admin_Search_LV);
        QM=new Query_Manager(this.getApplicationContext());
        price=(EditText)findViewById(R.id.Admin_Price);
        Field=(EditText)findViewById(R.id.Admin_Field);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (price.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You have to Enter a price", Toast.LENGTH_SHORT).show();
                    return;
                }

                c.moveToPosition(pos);
                int id=c.getInt(c.getColumnIndex("_id"));


                int i=QM.ADD_storeoffersbyadmin(AdminLog.BSname,AdminLog.BSloc,id,(int)Float.parseFloat(price.getText().toString()));
                if(i==-1) {
                    new AlertDialog.Builder(Add_Offer_Admin.this)
                            .setTitle("Invalid")
                            .setMessage("You already offering this book before")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                onBackPressed();
            }
        });
    }

    public void Search(View V)
    {
        if(ET.getText().toString().isEmpty()) {
            Toast.makeText(this.getApplicationContext(), "You have to write Something !", Toast.LENGTH_SHORT).show();
            return;
        }

        c = QM.Select_Book_byname(ET.getText().toString());
        if (c.getCount() == 0) {
            Toast.makeText(this.getApplicationContext(), "No Such A book !", Toast.LENGTH_SHORT).show();
            return;
        }
        Book_Adapter adapter0 = new Book_Adapter(this, c, 0);
        LV.setAdapter(adapter0);
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add__offer__admin, menu);
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

    public void add_new(View v)
    {
        if (price.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You have to Enter a price",Toast.LENGTH_SHORT).show();
            return;
        }
        int id =QM.Insert_BOOK(ET.getText().toString());
        Intent intent=new Intent(getApplicationContext(),Choose_Author_by_admin.class);

        String[] fields;
        if (!Field.getText().toString().isEmpty()) {
            fields = Field.getText().toString().split(",");
        }
        else {
            fields = new String[]{"others"};
        }

        for(int i=0;i<fields.length;i++)
        {
            QM.Insert_field(fields[i]);
            QM.Insert_into_written_in(id,fields[i]);
        }



        QM.ADD_storeoffersbyadmin(AdminLog.BSname,AdminLog.BSloc,(int)id,Integer.parseInt(price.getText().toString()));

        intent.putExtra("BOOK_ID",(int)id);
        startActivity(intent);
    }
}
