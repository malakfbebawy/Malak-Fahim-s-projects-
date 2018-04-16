package cairo_university.si_channel2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
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
public class IA_info_adapter extends BaseAdapter{

    JSONArray my_posts;
    Context home;

    public IA_info_adapter(Context A,JSONArray p)
    {
        my_posts = p;
        home = A;
    }

    @Override
    public int getCount() {
        return my_posts.length();
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)home).getLayoutInflater();
        View Item = inflater.inflate(R.layout.my_info_item_view, null, true);

        TextView content = (TextView)Item.findViewById(R.id.textView7);
        TextView Time = (TextView)Item.findViewById(R.id.textView8);
        TextView Grade = (TextView)Item.findViewById(R.id.textView9);

        try {

            content.setText(my_posts.getJSONObject(position).getString("Content"));
            Time.setText(my_posts.getJSONObject(position).getString("Creation_date"));
            Grade.setText(my_posts.getJSONObject(position).getString("Grade_year"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Item;
    }
}
