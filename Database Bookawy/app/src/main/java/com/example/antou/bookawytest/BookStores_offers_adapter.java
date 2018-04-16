package com.example.antou.bookawytest;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class BookStores_offers_adapter extends CursorAdapter {
    public BookStores_offers_adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.bookstoresoffers_item_view, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView Offered_book = (TextView) view.findViewById(R.id.item_offered_Book_name);
        TextView Price = (TextView) view.findViewById(R.id.item_Price_store);
        TextView Item_Store_location = (TextView) view.findViewById(R.id.item_store_location);



        // Extract properties from cursor

        String Offered_Book_name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
        String BookStoreName=cursor.getString(cursor.getColumnIndexOrThrow("STORE_NAME"));
        String Loc=cursor.getString(cursor.getColumnIndexOrThrow("LOC"));
        int PriceV=cursor.getInt(cursor.getColumnIndexOrThrow("PRICE"));


        Offered_book.setText(Offered_Book_name);
        Price.setText(PriceV+"");
        Item_Store_location.setText(BookStoreName+", "+Loc);



        // Populate fields with extracted properties



    }

}
