package cairo_university.si_channel2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Question_Viewer extends AppCompatActivity {

    Button B;
    TextView TV;
    RadioGroup RG;
    RadioButton[]RB;
    EditText Et;
    Query_Manager QM;

    int Survey_ID;

    JSONArray JA;
    String Q_string;
    String avail_answer;

    int ques_num=1;

    Boolean essay = false;

    RelativeLayout RL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question__viewer);

        RG=(RadioGroup)findViewById(R.id.radiogroup);
        RB=new RadioButton[4];
        RB[0]=(RadioButton)findViewById(R.id.radioButton);
        RB[1]=(RadioButton)findViewById(R.id.radioButton2);
        RB[2]=(RadioButton)findViewById(R.id.radioButton3);
        RB[3]=(RadioButton)findViewById(R.id.radioButton4);
        Et=(EditText)findViewById(R.id.editText12);
        TV=(TextView)findViewById(R.id.textView18);
        B=(Button)findViewById(R.id.button10);
        RL=(RelativeLayout)findViewById(R.id.container);
        QM = Query_Manager.Create_QM();

        Intent I=getIntent();
        Survey_ID=I.getIntExtra("SURVEY", 0);

        //// TODO: 5/23/2016 : select questions survey id == survey_id w 7otaha fe el json array
        JA = QM.select_Quests(Survey_ID);  //7otto b2a hna w rabena yostor

        updateQuestion();

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Stud_id = MainActivity.getUser();
                String Answer = "";

                if (essay)
                    Answer = Et.getText().toString();
                else {
                    int selected = RG.getCheckedRadioButtonId();
                    if (selected == RB[0].getId())
                        Answer = "A";
                    else if (selected == RB[1].getId())
                        Answer = "B";
                    else if (selected == RB[2].getId())
                        Answer = "C";
                    else if (selected == RB[3].getId())
                        Answer = "D";
                }

                //// TODO: 5/23/2016 : insert answer (ques_num,Survey_ID,Stud_id,Answer)


                QM.Insert_Ans(ques_num, Survey_ID, Integer.parseInt(MainActivity.getUser()), Answer);


                if (ques_num  == JA.length()) {
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Thank you for having this survey done! :D", Toast.LENGTH_LONG).show();
                }
                ques_num++;
                updateQuestion();
            }
        });
    }

    public void updateQuestion()
    {
//        if (essay)    //suspicious
//        {
////            if(RB[0].getParent() != null)
////                ((ViewGroup)RB[0].getParent()).removeView(RB[0]);
//            RL.addView(RB[0]);
//            RL.addView(RB[1]);
//            RL.addView(RB[2]);
//            RL.addView(RB[3]);
//        }
//
//        else
//        {
//            if(Et.getParent() != null)
//                ((ViewGroup)Et.getParent()).removeView(Et);
//            RL.addView(Et);
//        }
        //// TODO: 5/23/2016 : bos hna la akon 3amel 7aga 3;alat

        JSONObject JO= null;
        try {
            JO = JA.getJSONObject(ques_num-1);
            Q_string= JO.getString("q_string");
            avail_answer=JO.getString("avail_ans");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        TV.setText(Q_string);

        if (avail_answer.isEmpty()) {
            essay=true;
            RB[0].setEnabled(false);
            RB[1].setEnabled(false);
            RB[2].setEnabled(false);
            RB[3].setEnabled(false);
            Et.setEnabled(true);

//            RL.removeView(RB[0]);
//            RL.removeView(RB[1]);
//            RL.removeView(RB[2]);
//            RL.removeView(RB[3]);

        }
        else {
            essay=false;
            //RL.removeView(Et);

            RB[0].setEnabled(true);
            RB[1].setEnabled(true);
            RB[2].setEnabled(true);
            RB[3].setEnabled(true);
            Et.setEnabled(false);

            String[]answers=avail_answer.split(",");
            RB[0].setText(answers[0]);
            RB[1].setText(answers[1]);
            RB[2].setText(answers[2]);
            RB[3].setText(answers[3]);

        }
    }


}
