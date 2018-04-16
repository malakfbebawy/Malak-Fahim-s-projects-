package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class Book_Adapter extends CursorAdapter {

    public Book_Adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.book_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView Book_name = (TextView) view.findViewById(R.id.item_book_name);
        TextView Publish = (TextView) view.findViewById(R.id.item_puplish);
        TextView Edition = (TextView) view.findViewById(R.id.item_edition);


        // Extract properties from cursor

        String BN = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
        int P=cursor.getInt(cursor.getColumnIndexOrThrow("PUBLISH_YEAR"));
        int E=cursor.getInt(cursor.getColumnIndexOrThrow("EDITION"));




        // Populate fields with extracted properties
        Book_name.setText(BN);
        Publish.setText(P+"");
        Edition.setText(E+"");

    }
}
