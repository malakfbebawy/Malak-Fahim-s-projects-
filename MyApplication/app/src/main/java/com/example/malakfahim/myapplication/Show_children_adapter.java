package com.example.malakfahim.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Malak Fahim on 8/22/2016.
 */


public class Show_children_adapter extends CursorAdapter
{
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)              //to be compatable with API
    public Show_children_adapter(Context context, Cursor cursor, int flags)
    {
        super(context, cursor,0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.children_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // Find fields to populate in inflated template
        TextView Name = (TextView) view.findViewById(R.id.name);

        // Extract properties from cursor

        String nm = cursor.getString(cursor.getColumnIndexOrThrow("Name"));


        // Populate fields with extracted properties
        Name.setText(nm);

    }

}


