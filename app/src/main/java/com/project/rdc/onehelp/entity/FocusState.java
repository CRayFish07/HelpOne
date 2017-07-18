package com.project.rdc.onehelp.entity;

import java.util.Map;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class FocusState extends BmobObject {

    private Map<Integer,Boolean> mFocusStateMap;//关注状态

    public FocusState(){
    }

    public Map<Integer, Boolean> getFocusStateMap() {
        return mFocusStateMap;
    }

    public void setFocusStateMap(Map<Integer, Boolean> focusStateMap) {
        mFocusStateMap = focusStateMap;
    }

    @Override
    public String toString() {
        return mFocusStateMap.toString();
    }

}
