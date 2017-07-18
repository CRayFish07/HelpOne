package com.project.rdc.onehelp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Time:2016.11.12 17:19
 * Created By:ThatNight
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    FragmentManager fm;
    List<Fragment> fragments;
    boolean[] fragmentsUpdateFlag;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragments,boolean[] fragmentsUpdateFlag) {
        super(fm);
        this.fm=fm;
        this.fragments=fragments;
        this.fragmentsUpdateFlag=fragmentsUpdateFlag;
    }




//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment fragment= (Fragment) super.instantiateItem(container,position);
//
//        String framengtTag=fragment.getTag();
//        if(fragmentsUpdateFlag[position]){
//            FragmentTransaction ft=fm.beginTransaction();
//            ft.remove(fragment);
//            fragment=fragments.get(position);
//            ft.add(container.getId(),fragment,framengtTag);
//            ft.attach(fragment);
//            ft.commitAllowingStateLoss();
//            fragmentsUpdateFlag[position]=false;
//        }
//        return fragment;
//    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=fragments.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
