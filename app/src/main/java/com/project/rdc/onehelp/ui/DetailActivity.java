package com.project.rdc.onehelp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.adapter.DetailImgAdapter;
import com.project.rdc.onehelp.presenter.DetailPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class DetailActivity extends AppCompatActivity {

    //    @InjectView(R.id.imageView)
//    ImageView mImageView;
//    @InjectView(R.id.textView)
//    TextView mTextView;
    @InjectView(R.id.task_status_tv)
    TextView mTaskStatusTv;//任务状态
    @InjectView(R.id.ic_head_iv)
    RoundedImageView mIcHeadIv;//头像
    @InjectView(R.id.title_tv)
    TextView mTitleTv;//标题
    @InjectView(R.id.detail_tv)
    TextView mDetailTv;//详情
    @InjectView(R.id.ninegridimgeview)
    NineGridImageView mNinegridimgeview;
    @InjectView(R.id.textView2)
    TextView mTextView2;
    @InjectView(R.id.btn_detail_chat)
    Button mBtnDetailChat;//联系求助者
    @InjectView(R.id.btn_confirm)
    Button mBtnConfirm;//确定接单


    private boolean isLogin=false;
    private NineGridImageViewAdapter<String> mAdapter;//图片适配器
    private DetailPresenter mDetailPresenter;

    private String[] IMG_URL_LIST = {//测试图片列表
            "http://ac-QYgvX1CC.clouddn.com/36f0523ee1888a57.jpg",
            "http://ac-QYgvX1CC.clouddn.com/07915a0154ac4a64.jpg",
            "http://ac-QYgvX1CC.clouddn.com/9ec4bc44bfaf07ed.jpg",
            "http://ac-QYgvX1CC.clouddn.com/fa85037f97e8191f.jpg",
            "http://ac-QYgvX1CC.clouddn.com/de13315600ba1cff.jpg",
            "http://ac-QYgvX1CC.clouddn.com/15c5c50e941ba6b0.jpg",
            "http://ac-QYgvX1CC.clouddn.com/10762c593798466a.jpg",
            "http://ac-QYgvX1CC.clouddn.com/eaf1c9d55c5f9afd.jpg",
            "http://ac-QYgvX1CC.clouddn.com/ad99de83e1e3f7d4.jpg",
            "http://ac-QYgvX1CC.clouddn.com/233a5f70512befcc.jpg",
    };
    List<String> imgUrlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        mDetailPresenter = new DetailPresenter();

        mAdapter = new DetailImgAdapter();
        mNinegridimgeview.setAdapter(mAdapter);
        imgUrlList.addAll(Arrays.asList(IMG_URL_LIST));//图片网址的字符串数组先转换为List再添加到图片URLList中
        mNinegridimgeview.setImagesData(imgUrlList);//给 NineGridImageView 设置图片数据：


    }

    @OnClick({R.id.btn_detail_chat, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_detail_chat:
                if(RongIM.getInstance()!=null){
                    RongIM.getInstance().startPrivateChat(this,"72d08d500e","私聊");
                }else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_confirm:
                break;
        }
    }
}
