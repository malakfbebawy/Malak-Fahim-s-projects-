package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by antou on 12/23/2015.
 */
public class BookStore_adapter extends CursorAdapter {
    public BookStore_adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.bookstores_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView BookStoreName = (TextView) view.findViewById(R.id.item_Book_store_name);
        TextView Location = (TextView) view.findViewById(R.id.Location);
        TextView S_F = (TextView) view.findViewById(R.id.StartingAndFinishing);



        // Extract properties from cursor

        String BSN = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        String Loc=cursor.getString(cursor.getColumnIndexOrThrow("LOCATION"));
        String StartingAndFinishingString="FROM "+cursor.getString(cursor.getColumnIndexOrThrow("WORKING_TIME_SD"))
                +" to "+cursor.getString(cursor.getColumnIndexOrThrow("WORKING_TIME_FD"));




        // Populate fields with extracted properties
        BookStoreName.setText(BSN);
        Location.setText(Loc);
        S_F.setText(StartingAndFinishingString);
    }
}
