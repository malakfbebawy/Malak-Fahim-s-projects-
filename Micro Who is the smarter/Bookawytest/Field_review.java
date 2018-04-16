package com.example.antou.bookawytest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Field;

public class Field_review extends AppCompatActivity {

    TextView TV;
    ListView LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_review);

        Intent intent=this.getIntent();
        final String Field_name=intent.getStringExtra("FIELD_NAME");
        TV=(TextView)findViewById(R.id.textView37);
        TV.setText(Field_name +" Books");
        LV=(ListView)findViewById(R.id.listView15);
        final Query_Manager QM=new Query_Manager(this.getApplicationContext());
        final Cursor c=QM.SELECT_allbookbyfieldname(Field_name);
        Book_Adapter BA=new Book_Adapter(this.getApplicationContext(),c,0);
        LV.setAdapter(BA);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c.moveToPosition(i);
                Intent intent=new Intent(getApplicationContext(),Book_review.class);
                intent.putExtra("BOOK_ID",c.getInt(c.getColumnIndex("_id")));
                startActivity(intent);
            }
        });


        Switch S=(Switch)findViewById(R.id.switch2);
        Cursor c2=QM.Select_like(MainActivity.getUser(),Field_name);
        if (c2.getCount()==0) S.setChecked(false);
        else S.setChecked(true);
        S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    QM.ADD_LIKE(MainActivity.getUser(),Field_name);

                }
                else
                {
                    QM.Delete_Like(MainActivity.getUser(),Field_name);
                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_field_review, menu);
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
