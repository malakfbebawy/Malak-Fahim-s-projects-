package cairo_university.si_channel2;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Home extends AppCompatActivity {

    TabLayout TL;
    ViewPager VP;
    static Context X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        X = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (MainActivity.getType() == 0) {
            TL = (TabLayout) findViewById(R.id.tabLayout);
            VP = (ViewPager) findViewById(R.id.viewPager);
            ViewPagerAdapter VPA = new ViewPagerAdapter(getSupportFragmentManager());
            VPA.add_fragements(new Info_Frags(), "Information");
            VPA.add_fragements(new Student_Survey_frag(), "Survey");
            VPA.add_fragements(new Disc_frag(), "Discussion");
            VP.setAdapter(VPA);
            TL.setupWithViewPager(VP);
        }
        else
        {
            TL = (TabLayout) findViewById(R.id.tabLayout);
            VP = (ViewPager) findViewById(R.id.viewPager);
            ViewPagerAdapter VPA = new ViewPagerAdapter(getSupportFragmentManager());
            VPA.add_fragements(new Admin_info() , "My Information");
            VPA.add_fragements(new IA_Survey_frag(), "Survey");

            if(MainActivity.IA_type.equals("Admin"))
            VPA.add_fragements(new Add_Delete_user(), "Control Users");

            VP.setAdapter(VPA);
            TL.setupWithViewPager(VP);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//        return true;
//    }

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
