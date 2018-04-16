package cairo_university.si_channel2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class stat_viewer extends AppCompatActivity {
    Button B;
    int Survey_ID;
    boolean essay;
    int ques_num=0;
    TextView Question,stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_viewer);

        Question=(TextView)findViewById(R.id.ques);
        stats=(TextView)findViewById(R.id.stat);
        B=(Button)findViewById(R.id.button11);


        Intent I=getIntent();
        Survey_ID=I.getIntExtra("SURVEY",0);

        //// TODO: 5/23/2016 : select questions survey id == survey_id w 7otaha fe el json array
        JSONArray JA;//7otto b2a hna w rabena yostor
        updateQues_stat();
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ques_num++;
                updateQues_stat();
            }
        });

    }

    void updateQues_stat()
    {
//        JSONObject JO=JA.getJSONObject(ques_num);
//
//
//        // 7otly el Q_string w el Avail_answer fe el strings elly gaya de
//
//        String Q_string=JO.getString("q_string");
//        String avail_answer=JO.getString("avai_ans");
//
//        Question.setText(Q_string);
//
//        //// TODO: 5/23/2016 : select all answers for the question with id==ques_num && survey_id==Survey_ID
//        JSONArray json;//7ot hna w rabena yostor bardo
//
//        if (avail_answer.isEmpty())
//        {
//            String Answers="";
//            for (int i=0;i<json.length();i++) {
//                JSONObject JO = json.getJSONObject(i);
//                Answers+=String.valueOf(i)+"- ";
//                Answers+=JO.getString("Answer");
//                Answers+="\n";
//            }
//            stats.setText(Answers);
//        }
//        else
//        {
//            float A=0;
//            float B=0;
//            float C=0;
//            float D=0;
//            for (int i=0;i<json.length();i++) {
//                JSONObject JO = json.getJSONObject(i);
//                String temp=JO.getString("Answer");
//                if(temp.equals("A"))
//                    A++;
//                else if (temp.equals("B"))
//                    B++;
//                else if (temp.equals("C"))
//                    C++;
//                else if (temp.equals("D"))
//                    D++;
//            }
//            A/=json.length();
//            B/=json.length();
//            C/=json.length();
//            D/=json.length();
//
//            String answers="A statistics: "+String.valueOf(A)+"\n"+"B statistics: "+String.valueOf(B)
//                    +"\n"+"C statistics: "+String.valueOf(C)+"\n"+"D statistics: "+String.valueOf(D);
//            stats.setText(answers);
//            }
//
    }

}
