package com.project.rdc.onehelp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.rdc.onehelp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishedActivity extends AppCompatActivity {


    @InjectView(R.id.tb_main_title)
    TextView mTbMainTitle;
    @InjectView(R.id.ibtn_published_back)
    ImageButton mIbtnPublishedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mTbMainTitle.setText("已发布");
    }

    @OnClick(R.id.ibtn_published_back)
    public void onClick() {
    }
}
