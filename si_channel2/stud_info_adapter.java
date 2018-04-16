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

/**
 * Created by AMR on 5/6/2016.
 */
public class stud_info_adapter extends BaseAdapter {

    JSONArray home_posts;
    Context home;
    public stud_info_adapter(JSONArray JA, Context c)
    {
        home_posts = JA;
        home = c;
    }

    @Override
    public int getCount() {
        return home_posts.length();
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
        View Item = inflater.inflate(R.layout.discussion_item_view, null, true);

        TextView Name = (TextView)Item.findViewById(R.id.I_name);
        TextView Content = (TextView)Item.findViewById(R.id.P_content);
        TextView Date = (TextView)Item.findViewById(R.id.Date);

        try {

            Name.setText(home_posts.getJSONObject(position).getString("Name"));
            Content.setText(home_posts.getJSONObject(position).getString("Content"));
            Date.setText(home_posts.getJSONObject(position).getString("Creation_date"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Item;
    }
}
