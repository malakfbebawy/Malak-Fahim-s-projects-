package cairo_university.si_channel2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.zip.Inflater;

/**
 * Created by AMR on 5/6/2016.
 */
public class Comment_Adapter extends BaseAdapter {

    JSONArray comments;
    Context home;
    public Comment_Adapter(JSONArray c, Context ctxt)
    {
        comments =c;
        home = ctxt;
    }

    @Override
    public int getCount() {
        return comments.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)home).getLayoutInflater();
        View Item = inflater.inflate(R.layout.comment_item_view, null, true);

        TextView Name = (TextView)Item.findViewById(R.id.textView4);
        TextView Content = (TextView)Item.findViewById(R.id.textView5);

        try {

            Name.setText(comments.getJSONObject(position).getString("Name")+" :");
            Content.setText(comments.getJSONObject(position).getString("content"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Item;
    }
}
