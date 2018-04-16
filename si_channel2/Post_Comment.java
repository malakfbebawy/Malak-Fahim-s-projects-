package cairo_university.si_channel2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class Post_Comment extends AppCompatActivity {

    Query_Manager QM;
    JSONArray JA;
    JSONArray comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__comment);
        Intent I=this.getIntent();
        final int id =I.getIntExtra("POST_ID",0);
        QM=Query_Manager.Create_QM();

        JA = QM.select_disc_by_ID2(id);
        comments = QM.select_comments(id);
        System.out.println("OM EL ID:  "+ id);
        initializepost();
        initializeComments();

        Button b=(Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ET=(EditText)findViewById(R.id.editText2);
                if (ET.getText().toString().isEmpty())
                    return;

                QM.Insert_comment(Integer.parseInt(MainActivity.getUser()),id,ET.getText().toString());
                comments = QM.select_comments(id);
                initializeComments();
                ET.setText("");
            }
        });
    }

    private void initializeComments() {
        ListView LV=(ListView)findViewById(R.id.listView3);
        Comment_Adapter I=new Comment_Adapter(comments, Home.X);
        LV.setAdapter(I);
    }

    private void initializepost() {

        TextView TV[];
        TV=new TextView[3];

        TV[0]=(TextView)findViewById(R.id.textView);
        TV[1]=(TextView)findViewById(R.id.textView2);
        TV[2]=(TextView)findViewById(R.id.textView3);
        try {
            TV[0].setText(JA.getJSONObject(0).getString("Name"));
            TV[1].setText(JA.getJSONObject(0).getString("Content"));
            TV[2].setText(JA.getJSONObject(0).getString("Creation_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//
//        double d=C.getDouble(C.getColumnIndexOrThrow("Date_time"));
//        double x=System.currentTimeMillis();
//        int Creation= (int) ((x-d)/60000);
//        TV[2].setText(Creation+" minutes ago.");

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_post__comment, menu);
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
