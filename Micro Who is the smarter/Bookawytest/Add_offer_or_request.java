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
import java.util.List;

public class Add_offer_or_request extends AppCompatActivity {

    public int state;
    private EditText ET;
    ListView LV;
    Cursor c;
    Query_Manager QM;
    EditText price;
    EditText Field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer_or_request);
        Intent intent=this.getIntent();
        state=intent.getIntExtra("STATE",0);

        ET=(EditText)findViewById(R.id.editText2);
        LV=(ListView)findViewById(R.id.listView4);
        QM=new Query_Manager(this.getApplicationContext());
        price=(EditText)findViewById(R.id.editText3);
        Field=(EditText)findViewById(R.id.editText4);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (price.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You have to Enter a price",Toast.LENGTH_SHORT).show();
                    return;
                }

                c.moveToPosition(pos);
                int id=c.getInt(c.getColumnIndex("_id"));

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                int i=QM.Insert_OFFERSANDREQ(MainActivity.getUser(), id, (int)Float.parseFloat(price.getText().toString()), dateFormat.format(date), state);
                if(i==-1) {
                    new AlertDialog.Builder(Add_offer_or_request.this)
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
        getMenuInflater().inflate(R.menu.menu_add_offer_or_request, menu);
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
        Intent intent=new Intent(getApplicationContext(),Choose_Author.class);

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


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
            if(state==1)
        QM.Insert_OFFERSANDREQ(MainActivity.getUser(), (int)id, Integer.parseInt(price.getText().toString()), dateFormat.format(date), state);
            else
        QM.Insert_OFFERSANDREQ(MainActivity.getUser(), (int) id, 0, dateFormat.format(date), state);

        intent.putExtra("BOOK_ID",(int)id);
        startActivity(intent);
    }
}
