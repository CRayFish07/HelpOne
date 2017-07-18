package com.project.rdc.onehelp.entity;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class FocusItem {
    int imgId;
    String name;

    public FocusItem(int imgId, String name) {
        this.imgId = imgId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (obj instanceof FocusItem){
            FocusItem focusItem = (FocusItem)obj;
            return focusItem.imgId == imgId && focusItem.name == name;
        }
        return super.equals(obj);
    }
}
