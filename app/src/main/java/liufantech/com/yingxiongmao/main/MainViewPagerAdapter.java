package liufantech.com.yingxiongmao.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by HL0521 on 2015/10/10.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragmentList;
    private Context mcontext;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragmentList, Context mcontext) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

}
