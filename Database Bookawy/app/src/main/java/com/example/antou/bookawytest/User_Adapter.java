package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by antou on 12/24/2015.
 */
public class User_Adapter extends CursorAdapter {
    public User_Adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.user_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView name = (TextView) view.findViewById(R.id.item_Name);
        TextView MUTUAL = (TextView) view.findViewById(R.id.item_mutual);

        if (cursor.getString(cursor.getColumnIndexOrThrow("_id"))==null) {
            name.setText(Search.GetSearchUser());
            MUTUAL.setText("Mutual Friends: 0");

            return;
        }


        // Extract properties from cursor

        String Name = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        String Mutual_num="Mutual Friends: "+cursor.getInt(cursor.getColumnIndexOrThrow("count"));

        name.setText(Name);
        MUTUAL.setText(Mutual_num);


    }

}
