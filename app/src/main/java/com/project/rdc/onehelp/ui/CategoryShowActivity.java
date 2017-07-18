package com.project.rdc.onehelp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.rdc.onehelp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

public class CategoryShowActivity extends AppCompatActivity {

    @InjectView(R.id.tb_main_title)
    TextView mTbMainTitle;
    @InjectView(R.id.imgbtn_category_back)
    ImageButton mImgbtnCategoryBack;
    @InjectView(R.id.rv_neighbor_list)
    RecyclerView mRvNeighborList;
    @InjectView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_catalog);
        ButterKnife.inject(this);
        switchCategory();
    }

    private void switchCategory(){
        Intent intent = getIntent();
        int categoryNum = intent.getIntExtra("catalog", 0);
        switch (categoryNum) {
            case R.drawable.ic_repairing:
                mTbMainTitle.setText("維修");
                break;
            case R.drawable.ic_delivering:
                mTbMainTitle.setText("代送");
                break;
            case R.drawable.ic_shopping:
                mTbMainTitle.setText("代送");
                break;
            case R.drawable.ic_help_do_sth:
                mTbMainTitle.setText("办事");
                break;
            case R.drawable.ic_car:
                mTbMainTitle.setText("用车");
                break;
            case R.drawable.ic_rent_house:
                mTbMainTitle.setText("租房");
                break;
            case R.drawable.ic_education:
                mTbMainTitle.setText("教育");
                break;
            case R.drawable.ic_photo:
                mTbMainTitle.setText("摄影");
                break;
            case R.drawable.ic_part_time:
                mTbMainTitle.setText("兼职");
                break;
            case R.drawable.ic_housework:
                mTbMainTitle.setText("家政");
                break;
            case R.drawable.ic_other:
                mTbMainTitle.setText("其他");
                break;
        }
    }

    @OnClick({R.id.tb_main_title, R.id.imgbtn_category_back, R.id.rv_neighbor_list, R.id.srl_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_main_title:
                break;
            case R.id.imgbtn_category_back:
                break;
            case R.id.rv_neighbor_list:
                break;
            case R.id.srl_refresh:
                break;
        }
    }
}
