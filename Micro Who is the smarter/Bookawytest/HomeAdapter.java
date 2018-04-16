package com.example.antou.bookawytest;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class HomeAdapter extends CursorAdapter
{


    public HomeAdapter(Context context, Cursor cursor, int flags)
    {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.offers_item_view, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // Find fields to populate in inflated template
        TextView Book_name = (TextView) view.findViewById(R.id.item_bookname);
        TextView User_Name = (TextView) view.findViewById(R.id.item_username);
        TextView Price = (TextView) view.findViewById(R.id.item_price);
        TextView dateV = (TextView) view.findViewById(R.id.item_date);


        // Extract properties from cursor

        String BN = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
        String UN=cursor.getString(cursor.getColumnIndexOrThrow("USER_NAME"));
        int P=cursor.getInt(cursor.getColumnIndexOrThrow("PRICE"));
        String date =cursor.getString(cursor.getColumnIndexOrThrow("TIME"));
        int state=cursor.getInt(cursor.getColumnIndexOrThrow("OFF_REQ"));

        if (state==0)
            Book_name.setTextColor(Color.RED);
        else
            Book_name.setTextColor(Color.GREEN);



        // Populate fields with extracted properties
        Book_name.setText(BN);
        User_Name.setText(UN);
        Price.setText(P+"");
        dateV.setText(date);

    }
}
