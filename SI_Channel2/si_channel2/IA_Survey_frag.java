package cairo_university.si_channel2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class IA_Survey_frag extends Fragment {

    Query_Manager QM;
    ListView LV;
    Button B;
    Spinner Sp;
    EditText Survey_name;

    public IA_Survey_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_survey_frag, container, false);
        QM=Query_Manager.Create_QM();
        LV=(ListView)v.findViewById(R.id.listView5);
        B=(Button)v.findViewById(R.id.button7);
        Sp=(Spinner)v.findViewById(R.id.spinner4);
        Survey_name = (EditText) v.findViewById(R.id.editText12);

        Integer []arr = new Integer[4];
        for (int i=0;i<4;i++)
            arr[i]=i+1;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(Home.X, android.R.layout.simple_spinner_dropdown_item, arr);

        Sp.setSelection(0);
        Sp.setAdapter(adapter);



        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gradeyear=(int)(long)(Sp.getSelectedItemId()+1);
                //// TODO: 5/22/2016 : Insert Survey with Grader year
                int surv_ID = QM.Insert_survey(Integer.parseInt(MainActivity.getUser()), Integer.parseInt(Sp.getSelectedItem().toString()), Survey_name.getText().toString());

                String id=MainActivity.getUser();
                JSONArray JA = QM.select_surveys_IA(Integer.parseInt(id));

                survey_list_adapter SL_adapter = new survey_list_adapter(JA, Home.X);
                LV.setAdapter(SL_adapter);

                Intent I=new Intent(Home.X ,Add_Survey.class);
                I.putExtra("surv_ID", surv_ID);
                startActivity(I);
                System.out.println("EEEEEELLLL surv ID aho "+ surv_ID);
            }
        });

        String id=MainActivity.getUser();
        //// TODO: 5/22/2016 : Add function to return json of survey by maker id == id
/*change1*/ final JSONArray JA = QM.select_surveys_IA(Integer.parseInt(id));

            survey_list_adapter SL_adapter = new survey_list_adapter(JA, Home.X);
            LV.setAdapter(SL_adapter);

        //// TODO: 5/22/2016 : Add implementation of the adapter and the layout then assign it to the ListView
        //LV.setAdapter(your new adapter);

//        LV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int Survey_ID=0; //// TODO: 5/23/2016 : json_return[position]['ID'];==> el mafrod ya3ny hna en ana 3ayz el id bta3 el 7aga elly ma3molaha selection
//                //// el 7aga de hya fe position fe json elly rage3ly fa haro7 lel position da w a5od mno ID
//                //da nafs el kalam elly enta 3amalto fe el survey bta3et el student
//
//
//
//                Intent I=new Intent(Home.X,stat_viewer.class);
//                I.putExtra("SURVEY",Survey_ID);
//                startActivity(I);
//
//            }
//
//        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                int Survey_ID =0;
                try {
                    JA.getJSONObject(position).get("surv_ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent I=new Intent(Home.X,stat_viewer.class);
                I.putExtra("SURVEY",Survey_ID);
                startActivity(I);
//
            }
        });

        return v;
    }


}
