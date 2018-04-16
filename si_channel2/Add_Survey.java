package cairo_university.si_channel2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Survey extends AppCompatActivity {

    Query_Manager QM;
    EditText []ET;
    Button[]B;
    int surv_id;
    int Q_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__survey);

        Q_count =0;
        surv_id = this.getIntent().getExtras().getInt("surv_ID");
        System.out.println(" da el surv ID "+surv_id);
        QM = Query_Manager.Create_QM();
        ET=new EditText[5];
        ET[0]=(EditText)findViewById(R.id.editText7);  //question
        ET[1]=(EditText)findViewById(R.id.editText8);  //answers
        ET[2]=(EditText)findViewById(R.id.editText9);
        ET[3]=(EditText)findViewById(R.id.editText10);
        ET[4]=(EditText)findViewById(R.id.editText11);

        B=new Button[2];
        B[0]=(Button)findViewById(R.id.button8);  //Next
        B[1]=(Button)findViewById(R.id.button9);  //finish

        B[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]x=new String[5];
                x[0]=ET[0].getText().toString();
                x[1]=ET[1].getText().toString();
                x[2]=ET[2].getText().toString();
                x[3]=ET[3].getText().toString();
                x[4]=ET[4].getText().toString();
                if (x[0].isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You have to add header to the question!"
                            , Toast.LENGTH_LONG).show();
                    return;
                }

                else if (x[1].isEmpty()&&x[2].isEmpty()&&x[3].isEmpty()&&x[4].isEmpty())  //if essay quests
                {
                    //// TODO: 5/23/2016 : Insert x[0] as the question header and insert available answers as empty text
                        QM.Insert_Question(surv_id,++Q_count,x[0],"");
                }
                else if (x[1].isEmpty()||x[2].isEmpty()||x[3].isEmpty()||x[4].isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Four answers myst be available or remove them to add essay question"
                            , Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    //// TODO: 5/23/2016 : Insert x[0] as the question header and insert available answers as x[1]+","+x[2]+","+x[3]+","+x[4];
                        QM.Insert_Question(surv_id, ++Q_count, x[0], x[1]+","+x[2]+","+x[3]+","+x[4]);
                }

                ET[0].setText("");
                ET[1].setText("");
                ET[2].setText("");
                ET[3].setText("");
                ET[4].setText("");
            }
        });

        B[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B[0].performClick();
                onBackPressed();
            }
        });

    }

}
