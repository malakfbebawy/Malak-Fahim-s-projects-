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
public class Authors_adapter extends CursorAdapter {
    public Authors_adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.authors_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView Author_name = (TextView) view.findViewById(R.id.item_author_name);
        TextView Nation_TV = (TextView) view.findViewById(R.id.item_Nation);


        // Extract properties from cursor

        String AN = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
        String Nation=cursor.getString(cursor.getColumnIndexOrThrow("NATION"));




        // Populate fields with extracted properties
        Author_name.setText(AN);
        Nation_TV.setText(Nation);

    }
}
