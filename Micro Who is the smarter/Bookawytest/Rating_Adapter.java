package com.example.antou.bookawytest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by antou on 12/26/2015.
 */
public class Rating_Adapter extends CursorAdapter {
    public Rating_Adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.rating_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView Tv=(TextView)view.findViewById(R.id.textView50);
        RatingBar RB=(RatingBar) view.findViewById(R.id.ratingBar2);

        Tv.setText(cursor.getString(cursor.getColumnIndex("USER")));
        RB.setRating(cursor.getInt(cursor.getColumnIndex("RATE")));
        RB.setEnabled(false);
    }


}
