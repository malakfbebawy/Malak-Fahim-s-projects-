package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by antou on 12/26/2015.
 */
public class Follower_Adpater extends CursorAdapter {
    public Follower_Adpater(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.follower_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView user_name = (TextView) view.findViewById(R.id.textView52);

        // Extract properties from cursor

        String Name = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

        user_name.setText(Name);
    }



}
