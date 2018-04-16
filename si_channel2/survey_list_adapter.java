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
 * Created by AMR on 5/23/2016.
 */
public class survey_list_adapter extends BaseAdapter {

    private JSONArray sur_list;
    private Context home;

    public survey_list_adapter(JSONArray JA, Context home)
    {
        sur_list = JA;
        this.home =home;
    }


    @Override
    public int getCount() {
        return sur_list.length();
    }

    @Override
    public Object getItem(int position)
    {
        return  null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)home).getLayoutInflater();
        View Item = inflater.inflate(R.layout.ia_survey_item, null, true);

        TextView Description = (TextView)Item.findViewById(R.id.textView12);
        TextView Grade = (TextView)Item.findViewById(R.id.textView13);
        try {

            Description.setText(sur_list.getJSONObject(position).getString("Description"));
            Grade.setText(sur_list.getJSONObject(position).getString("Grade"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Item;
    }

}
