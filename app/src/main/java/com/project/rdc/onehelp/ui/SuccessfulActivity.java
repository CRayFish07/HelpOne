package com.project.rdc.onehelp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.rdc.onehelp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SuccessfulActivity extends AppCompatActivity {


    @InjectView(R.id.tb_main_title)
    TextView mTbMainTitle;
    @InjectView(R.id.ibtn_success_back)
    ImageButton mIbtnSuccessBack;
    @InjectView(R.id.rv_success_list)
    RecyclerView mRvSuccessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mTbMainTitle.setText("已完成");
    }

    @OnClick(R.id.ibtn_success_back)
    public void onClick() {
        finish();
    }

}
