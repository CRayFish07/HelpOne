package com.project.rdc.onehelp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.project.rdc.onehelp.R;

public class WellComeActivity extends AppCompatActivity {

    private boolean firstEnter = true;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(WellComeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        isFirstTime();

    }

    private void isFirstTime() {
        mSharedPreferences = this.getSharedPreferences("isFirst", MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        firstEnter = mSharedPreferences.getBoolean("firstEnter", true);
        if (firstEnter) {
            editor.putBoolean("firstEnter", false);
            editor.apply();
            getWindow().setBackgroundDrawableResource(R.drawable.iv_login_bg2);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏，实现全屏
            new Handler().postDelayed(runnable, 2000);
        } else {
            Intent intent = new Intent(WellComeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
