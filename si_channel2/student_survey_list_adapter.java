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
 * Created by AMR on 5/23/2016.
 */
public class student_survey_list_adapter extends BaseAdapter {

    JSONArray surveys;
    Context Home;

    public  student_survey_list_adapter(Context home, JSONArray JA)
    {
        Home =home;
        surveys = JA;
    }

    @Override
    public int getCount() {
        return surveys.length();
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

        LayoutInflater I = ((Activity)Home).getLayoutInflater();
        View v = I.inflate(R.layout.student_survey_list_item, null, true);

        TextView Surv_name = (TextView)v.findViewById(R.id.textView19);
        TextView IA_name = (TextView)v.findViewById(R.id.textView20);


        try {
            Surv_name.setText(surveys.getJSONObject(position).getString("Description"));
            IA_name.setText( surveys.getJSONObject(position).getString("IA_Type") + surveys.getJSONObject(position).getString("Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
