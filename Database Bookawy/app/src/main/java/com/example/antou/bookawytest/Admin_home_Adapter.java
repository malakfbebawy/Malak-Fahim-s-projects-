package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
/**
 * Created by antou on 12/27/2015.
 */
public class Admin_home_Adapter extends CursorAdapter {


    public Admin_home_Adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.admin_home_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView book_id = (TextView) view.findViewById(R.id.a_book);
        TextView book_price = (TextView) view.findViewById(R.id.a_price);


        String name = cursor.getString(cursor.getColumnIndexOrThrow("Bookname"));
        int price = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));


        book_id.setText("" + name);
        book_price.setText("" + price);
    }
    }
