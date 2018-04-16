package cairo_university.si_channel2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
    public class Student_Survey_frag extends Fragment {

    ListView LV;
    Query_Manager QM;

    public Student_Survey_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_student__survey_frag, container, false);
        LV=(ListView)v.findViewById(R.id.listView6);
        QM = Query_Manager.Create_QM();

        //// TODO: 5/23/2016 : get grade of the student with id == id
        
        //// TODO: 5/23/2016 : get survey by grade year == grade year you get from the previous step

        final JSONArray JA = QM.select_surveys_stud(MainActivity.G_year);
        student_survey_list_adapter A = new student_survey_list_adapter(Home.X, JA);
        LV.setAdapter(A);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Survey_ID=0; //// TODO: 5/23/2016 : json_return[position]['ID'];==> el mafrod ya3ny hna en ana 3ayz el id bta3 el 7aga elly ma3molaha selection
                //// el 7aga de hya fe position fe json elly rage3ly fa haro7 lel position da w a5od mno ID

                JSONObject JO = null;
                try {
                    JO = JA.getJSONObject(position);
                    Survey_ID = Integer.parseInt(JO.getString("ID"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray check = QM.select_Q_Ans(Survey_ID , MainActivity.ID);

                //// TODO: e3mel check hna en el student mamalash el survey de 2abl kda w 7otaha makan true elly ta7t de
                //// todo : bte3melha b enak te3mel check bel user id w el answers w el survey id law la2et maraga3etsh null yob2a hoa 3amalha 2abl kda
                if (check.length() == 0)
                {
                    Toast.makeText(Home.X,"You had already filled this survey!",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent I=new Intent(Home.X,Question_Viewer.class);
                I.putExtra("SURVEY",Survey_ID);
                startActivity(I);
            }
        });

        return v;
    }
}
