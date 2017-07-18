package com.project.rdc.onehelp.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.project.rdc.onehelp.R;

/**
 * Time:2016.11.13 14:57
 * Created By:ThatNight
 */

public class MainThemeUtils {


    private static final int[] APPCOMPAT_CHECK_ATTRS = {R.attr.colorPrimary};

    public static void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !a.hasValue(0);
        if (a != null) {
            a.recycle();
        }
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme "
                    + "(or descendant) with the design library.");
        }
    }
}
