package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;



public class Comments_Adapter extends CursorAdapter {

    public Comments_Adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.comments_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.c_username);
        TextView comment = (TextView) view.findViewById(R.id.c_comment);

        // extrtact propeities from cursor

        String a_name = cursor.getString(cursor.getColumnIndexOrThrow("USER_NAME"))+": ";
        String a_comment=cursor.getString(cursor.getColumnIndexOrThrow("_id"));

        name.setText(a_name);
        comment.setText(a_comment);



    }
}
