package cairo_university.si_channel2;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Disc_frag extends Fragment {

    Query_Manager QM;
    Cursor C;
    ListView LV;
    EditText ET;
    Button B;

    public Disc_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        QM=Query_Manager.Create_QM();
        final JSONArray JA =QM.select_disc();
        View view = inflater.inflate(R.layout.fragment_disc_frag, container, false);
        LV=(ListView)view.findViewById(R.id.listView2);
        ET=(EditText)view.findViewById(R.id.editText);
        B=(Button)view.findViewById(R.id.button);
//        //Create the adapter
//        C.moveToFirst();
        stud_info_adapter I=new stud_info_adapter(JA,Home.X);
        LV.setAdapter(I);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Home.X,"AAAAAA"+ position,Toast.LENGTH_LONG).show();
                Intent I=new Intent(Home.X,Post_Comment.class);
                try {
                    I.putExtra("POST_ID",JA.getJSONObject(position).getInt("ID"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(I);
            }
        });

//

//        LV.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                C.moveToPosition(i);
//                int id =C.getInt(C.getColumnIndex("ID"));
//                Intent I=new Intent(Home.X,Post_Comment.class);
//                I.putExtra("POST_ID",id);
//                startActivity(I);
//            }
//        });
//        B.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                post(view);
//                return;
//            }
//        });
        return view;
    }

    public void post(View view)
    {
//        String x=ET.getText().toString();
//        if (x.isEmpty())
//            return;
//        QM.InsertPost(x,System.currentTimeMillis(),MainActivity.getUser());
//        C=QM.Select_Disc();
//        C.moveToFirst();
//        Information_adapter I=new Information_adapter(Home.X,C,0);
//        LV.setAdapter(I);
//        ET.setText("");
//        return;
//

    }
}
