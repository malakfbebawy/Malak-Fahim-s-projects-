package cairo_university.si_channel2;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;


/**
 * A simple {@link Fragment} subclass.
 */
public class Info_Frags extends Fragment {
    Query_Manager QM;
    Cursor C;
    ListView LV;
    public Info_Frags() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        QM=Query_Manager.Create_QM();
        JSONArray JA = QM.select_home_post_by_stud2(MainActivity.G_year);
        View view = inflater.inflate(R.layout.fragment_info__frags, container, false);

        LV=(ListView)view.findViewById(R.id.listView);
        stud_info_adapter adapter = new stud_info_adapter(JA,Home.X);
        LV.setAdapter(adapter);

        return view;
    }


}
