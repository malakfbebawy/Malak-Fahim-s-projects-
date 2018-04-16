package cairo_university.si_channel2;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;


/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_info extends Fragment {

    Query_Manager QM;
    ListView LV;
    Cursor C;
    Button B;
    Spinner Sp;


    public Admin_info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        QM=Query_Manager.Create_QM();
        View view = inflater.inflate(R.layout.fragment_admin_info, container, false);

        JSONArray my_posts = QM.Select_home_posts_by_IA(Integer.parseInt(MainActivity.getUser()));
        LV=(ListView)view.findViewById(R.id.listView4);

        IA_info_adapter I=new IA_info_adapter(Home.X,my_posts);
        LV.setAdapter(I);

        // Inflate the layout for this fragment

        Sp=(Spinner)view.findViewById(R.id.spinner);
        Sp.setSelection(0);
        Integer arr[]=new Integer[4];
        for (int i=0;i<4;i++)
            arr[i]=i+1;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(Home.X, android.R.layout.simple_spinner_dropdown_item, arr);
        Sp.setAdapter(adapter);
        final EditText ET=(EditText)view.findViewById(R.id.editText4);

        B=(Button)view.findViewById(R.id.button3);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ET.getText().toString().isEmpty())
                {
                    Toast.makeText(Home.X,"you should add a content", Toast.LENGTH_LONG).show();
                    return;
                }
                QM.Insert_home_post(Integer.parseInt(MainActivity.getUser().toString()) ,Integer.parseInt(Sp.getSelectedItem().toString()),"Date",ET.getText().toString());
                JSONArray JO= QM.Select_home_posts_by_IA(Integer.parseInt(MainActivity.getUser()));
                IA_info_adapter I=new IA_info_adapter(Home.X,JO);
                LV.setAdapter(I);
            }
        });

        return view;
    }


}
