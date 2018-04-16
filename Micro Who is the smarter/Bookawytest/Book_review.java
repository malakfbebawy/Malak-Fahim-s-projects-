package com.example.antou.bookawytest;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class Book_review extends AppCompatActivity {

    private Cursor c;
    RatingBar RB;
    Query_Manager QM;
    Integer BOOK_ID;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_review);
        QM = new Query_Manager(this.getApplicationContext());
        Intent intent = this.getIntent();

        BOOK_ID = intent.getIntExtra("BOOK_ID", 0);
        TV=(TextView)findViewById(R.id.textView59);
        Cursor P=QM.Show_BOOKfield(BOOK_ID);
        if(P.getCount()!=0) {
            P.moveToFirst();
            String TVTEXT = P.getString(P.getColumnIndex("_id"));

            while (P.moveToNext())
                TVTEXT += ", " + P.getString(P.getColumnIndex("_id"));

            TV.setText(TVTEXT);
        }
        //initialize rate
        RB = (RatingBar) findViewById(R.id.ratingBar);
        Cursor cc = QM.ShowRATEOFUSER(MainActivity.getUser(), BOOK_ID);
        cc.moveToFirst();
        if (cc.getCount() == 0) RB.setRating(0);
        else RB.setRating((float) cc.getInt(cc.getColumnIndex("_id")));
        ///////////////////////////////////////

        RB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rate = (int) v;
                int id = QM.UPDATE_RATE(MainActivity.getUser(), BOOK_ID, rate);
                if (id == 0)
                    QM.ADD_RATE(MainActivity.getUser(), BOOK_ID, rate);

            }
        });
        c = QM.Select_BOOK_byid(BOOK_ID);
        InitializeActivity();


    }


    private void InitializeActivity() {
        TextView Book_NAME = (TextView) findViewById(R.id.textView8);
        TextView Book_Edition = (TextView) findViewById(R.id.textView9);
        TextView Book_Publish = (TextView) findViewById(R.id.textView13);
        TextView AuthorName = (TextView) findViewById(R.id.textView15);


        c.moveToFirst();
        String bn = c.getString(c.getColumnIndex("BOOK"));
        Integer P = c.getInt(c.getColumnIndex("PUBLISH_YEAR"));
        Integer E = c.getInt(c.getColumnIndex("_id"));

        String Author_Name = c.getString(c.getColumnIndex("AUTHOR"));
        while (c.moveToNext())
            Author_Name += "," + c.getString(c.getColumnIndex("AUTHOR"));


        Book_NAME.setText(bn);
        Book_Edition.setText(P + "");
        Book_Publish.setText(E + "");
        AuthorName.setText(Author_Name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_review, menu);
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

    public void Add_Edit_Comment(View C)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.comment_custom_dialog);
        dialog.setTitle("Your Comment:");

        // set the custom dialog components - text, image and button
        final EditText q = (EditText) dialog.findViewById(R.id.comment);

        Cursor t = QM.ShowCOMMENTOFUSER(MainActivity.getUser(), BOOK_ID);
        t.moveToFirst();
        if (t.getCount() == 0) q.setText("");
        else if (t.getString(t.getColumnIndex("_id")) != null)
            q.setText(t.getString(t.getColumnIndex("_id")));

        Button dialogButton = (Button) dialog.findViewById(R.id.button12);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int r = QM.UPDATE_COMMENT(MainActivity.getUser(), BOOK_ID, q.getText().toString());
                if (r == 0)
                    QM.ADD_COMMENT(MainActivity.getUser(), BOOK_ID, q.getText().toString());
            }
        });

        dialog.show();
    }


    public void Show_Comments(View C) {
        Intent intent = new Intent(getApplicationContext(), Show_Comments.class);
        intent.putExtra("BOOK_ID", BOOK_ID);
        startActivity(intent);
    }

    public void ShowRating(View V)
    {
        Intent intent=new Intent(this.getApplicationContext(),Book_Rating.class);
        intent.putExtra("BOOK_ID",BOOK_ID);
        startActivity(intent);

    }

    public void Edit_Book(View V)
    {
        Intent intent=new Intent(this.getApplicationContext(),Edit_Book.class);
        intent.putExtra("BOOK_ID",BOOK_ID);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        QM = new Query_Manager(this.getApplicationContext());
        Intent intent = this.getIntent();
        BOOK_ID = intent.getIntExtra("BOOK_ID", 0);
        TV=(TextView)findViewById(R.id.textView59);
        Cursor P=QM.Show_BOOKfield(BOOK_ID);
        if(P.getCount()!=0) {
            P.moveToFirst();
            String TVTEXT = P.getString(P.getColumnIndex("_id"));

            while (P.moveToNext())
                TVTEXT += ", " + P.getString(P.getColumnIndex("_id"));

            TV.setText(TVTEXT);
        }
        //initialize rate
        RB = (RatingBar) findViewById(R.id.ratingBar);
        Cursor cc = QM.ShowRATEOFUSER(MainActivity.getUser(), BOOK_ID);
        cc.moveToFirst();
        if (cc.getCount() == 0) RB.setRating(0);
        else RB.setRating((float) cc.getInt(cc.getColumnIndex("_id")));
        ///////////////////////////////////////

        RB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rate = (int) v;
                int id = QM.UPDATE_RATE(MainActivity.getUser(), BOOK_ID, rate);
                if (id == 0)
                    QM.ADD_RATE(MainActivity.getUser(), BOOK_ID, rate);

            }
        });
        c = QM.Select_BOOK_byid(BOOK_ID);
        InitializeActivity();



    }

}
