package cairo_university.si_channel2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by antou on 4/23/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> Frags=new ArrayList<>();
    ArrayList<String>TabTitles=new ArrayList<>();

    public void add_fragements(Fragment F,String s)
    {
        this.Frags.add(F);
        TabTitles.add(s);

    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return Frags.get(position);
    }

    @Override
    public int getCount() {
        return Frags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TabTitles.get(position);
    }
}
