package com.project.rdc.onehelp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.rdc.onehelp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WaitingActivity extends AppCompatActivity {


    @InjectView(R.id.tb_main_title)
    TextView mTbMainTitle;
    @InjectView(R.id.ibtn_wait_back)
    ImageButton mIbtnWaitBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mTbMainTitle.setText("待完成");
    }


    @OnClick({R.id.ibtn_wait_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_wait_back:
                finish();
                break;
        }
    }
}
