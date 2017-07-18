package com.project.rdc.onehelp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TODO 初始化一些东西
    }
//一些子类都有可能用到的方法
    public void showMessage(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    protected void showDialog(){

    }
    protected void dismissDialog(){

    }
}
