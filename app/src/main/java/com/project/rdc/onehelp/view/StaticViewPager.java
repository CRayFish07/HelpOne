package com.project.rdc.onehelp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Time:2016.11.13 16:51
 * Created By:ThatNight
 */

public class StaticViewPager extends ViewPager {



    private boolean isScroll=true;


    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    public StaticViewPager(Context context) {
        super(context);
    }

    public StaticViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isScroll){
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!isScroll){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }


}
