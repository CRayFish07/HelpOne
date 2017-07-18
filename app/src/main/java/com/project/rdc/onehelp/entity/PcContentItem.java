package com.project.rdc.onehelp.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by zjz on 2016/11/16.
 */

public class PcContentItem {
    private String name;
    private Drawable drawable;

    public PcContentItem(Drawable drawable , String name) {
        this.drawable = drawable;
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
