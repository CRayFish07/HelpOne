package com.project.rdc.onehelp.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Map;

import cn.bmob.v3.Bmob;
import io.rong.imkit.RongIM;

/**
 * Time:2016.11.10 10:55
 * Created By:ThatNight
 */

public class App extends Application {

    private static App context;

    private static String sFocusStateObjectId = "f145c62443";//（临时测试）关注状态类的id

    public static Map<Integer,Boolean> staticMap;

    public static Map<Integer, Boolean> getStaticMap() {
        return staticMap;
    }

    public static void setStaticMap(Map<Integer, Boolean> staticMap) {
        App.staticMap = staticMap;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        RongIM.init(this);
        Fresco.initialize(this);
        Bmob.initialize(this, "2105138c34fb22154fd9af29c6631262");
    }

    public static App getAppContext() {
        return context;
    }

    public static String getFocusStateObjectId() {
        return sFocusStateObjectId;
    }

    public static void setFocusStateObjectId(String focusStateObjectId) {
        App.sFocusStateObjectId = focusStateObjectId;
    }
}
