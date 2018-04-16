package cairo_university.si_channel2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Inst extends AppCompatActivity {


    Query_Manager QM;
    EditText ET[];
    Button B;
    Spinner Sp;
    Context C;
    AppCompatActivity A;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__inst);
        Intent I=this.getIntent();
        final String id=I.getStringExtra("ID");
        A=this;
        ET=new EditText[2];
        ET[0]=(EditText)findViewById(R.id.Pass_Edit);
        ET[1]=(EditText)findViewById(R.id.Name_edit);
        Sp=(Spinner)findViewById(R.id.Type_entry);

        C=this.getApplicationContext();
        String[] X=new String[2];

        X[0]="Professor"; X[1]="TA";

        Sp.setSelection(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, X);
        Sp.setAdapter(adapter);
        B=(Button)findViewById(R.id.Submit);
        QM=Query_Manager.Create_QM();
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ET[0].getText().toString().isEmpty() || ET[1].getText().toString().isEmpty())
                {
                    Toast.makeText(C, "Fields can't be left empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long y = QM.Insert_Ad_ins(ET[1].getText().toString(),Integer.parseInt(id), Sp.getSelectedItem().toString(), ET[0].getText().toString());

                if (y == 0)
                    Toast.makeText(C, "This id is already exist", Toast.LENGTH_SHORT).show();
                else Toast.makeText(C, "Done Adding!", Toast.LENGTH_SHORT).show();

                Add_Inst.super.onBackPressed();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add__inst, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
