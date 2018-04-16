package cairo_university.si_channel2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Delete_user extends Fragment {


    public Add_Delete_user() {
        // Required empty public constructor
    }
    Query_Manager QM;
    Spinner Sp;
    Button add;
    Button Delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add__delete_user, container, false);
        QM=Query_Manager.Create_QM();
        Sp=(Spinner)view.findViewById(R.id.spinner2);
        Sp.setSelection(0);
        String arr[]=new String[2];
        arr[0]="Student";
        arr[1]="Instructor";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Home.X, android.R.layout.simple_spinner_dropdown_item, arr);
        Sp.setAdapter(adapter);
        add=(Button)view.findViewById(R.id.button4);
        Delete=(Button) view.findViewById(R.id.button5);
         final EditText ET=(EditText)view.findViewById(R.id.editText3);


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ET.getText().toString().isEmpty())
                {
                    Toast.makeText(Home.X,"you should add an ID to delete", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(ET.getText().toString().equals(MainActivity.getUser()))
                    Toast.makeText(Home.X,"Can't Delete Yourself",Toast.LENGTH_SHORT).show();
                else
                {
                    int y=0;
                    if (Sp.getSelectedItemPosition() == 0)
                        y= QM.Delete_student(Integer.parseInt(ET.getText().toString()));
                    else
                        y= QM.delete_inst(Integer.parseInt(ET.getText().toString()));

                    if(y == 1)
                        Toast.makeText(Home.X, "person deleted successfully", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Home.X , "This person does not exist", Toast.LENGTH_LONG).show();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ET.getText().toString().isEmpty())
                {
                    Toast.makeText(Home.X,"You should add an ID",Toast.LENGTH_SHORT).show();
                }
                else if (Sp.getSelectedItemPosition() == 0)
                {
                    Intent I=new Intent(Home.X,Add_student.class);
                    I.putExtra("ID",ET.getText().toString());
                    startActivity(I);
                }
                else
                {
                    Intent I=new Intent(Home.X,Add_Inst.class);
                    I.putExtra("ID",ET.getText().toString());
                    startActivity(I);
                }
            }
        });

        return view;
    }


}
