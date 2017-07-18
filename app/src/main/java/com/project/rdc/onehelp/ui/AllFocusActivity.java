package com.project.rdc.onehelp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.application.App;
import com.project.rdc.onehelp.entity.FocusState;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class AllFocusActivity extends AppCompatActivity {

    private static final String TAG = "AllFocusActivity";
    private static Map<Integer, Boolean> mIsFollowedMap = new HashMap<>();//存储关注状态

    private static FocusState[] mFocusStates = new FocusState[1];//内部类map状态，外传
    private static String id[] = new String[1];

    @InjectView(R.id.iv_focus_repairing)
    ImageView mIvFocusRepairing;
    @InjectView(R.id.iv_focus_delivering)
    ImageView mIvFocusDelivering;
    @InjectView(R.id.iv_focus_shopping)
    ImageView mIvFocusShopping;
    @InjectView(R.id.iv_focus_doing_sth)
    ImageView mIvFocusDoingSth;
    @InjectView(R.id.iv_focus_using_car)
    ImageView mIvFocusUsingCar;
    @InjectView(R.id.iv_focus_rent_house)
    ImageView mIvFocusRentHouse;
    @InjectView(R.id.iv_focus_education)
    ImageView mIvFocusEducation;
    @InjectView(R.id.iv_focus_photo)
    ImageView mIvFocusPhoto;
    @InjectView(R.id.iv_focus_painting)
    ImageView mIvFocusPainting;
    @InjectView(R.id.iv_focus_part_time)
    ImageView mIvFocusPartTime;
    @InjectView(R.id.iv_focus_housework)
    ImageView mIvFocusHousework;
    @InjectView(R.id.iv_focus_others)
    ImageView mIvFocusOthers;

    @InjectView(R.id.ll_focus_root_view)
    LinearLayout mLlFocusRootView;
    @InjectView(R.id.tb_main_title)
    TextView mTbMainTitle;
    //    @InjectView(R.id.tb_main_top)
//    Toolbar mTbMainTop;
    @InjectView(R.id.ibtn_login_back)
    ImageButton mIbtnLoginBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_all_focus);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ButterKnife.inject(this);
        mLlFocusRootView.setVisibility(View.INVISIBLE);//未加载完成的时设置为不可见

        initData();
    }

    /*初始化关注状态，默认为不关注*/
    private void initMap() {
        mIsFollowedMap.put(R.id.iv_focus_using_car, false);
        mIsFollowedMap.put(R.id.iv_focus_doing_sth, false);
        mIsFollowedMap.put(R.id.iv_focus_shopping, false);
        mIsFollowedMap.put(R.id.iv_focus_delivering, false);
        mIsFollowedMap.put(R.id.iv_focus_repairing, false);
        mIsFollowedMap.put(R.id.iv_focus_rent_house, false);

        mIsFollowedMap.put(R.id.iv_focus_education, false);
        mIsFollowedMap.put(R.id.iv_focus_photo, false);
        mIsFollowedMap.put(R.id.iv_focus_painting, false);
        mIsFollowedMap.put(R.id.iv_focus_housework, false);
        mIsFollowedMap.put(R.id.iv_focus_others, false);
        mIsFollowedMap.put(R.id.iv_focus_part_time, false);

        Log.e(TAG, "initMap: mIsFollowedMap.toString()  "+mIsFollowedMap.toString() );
    }

    /*初始化关注的数据*/
    private void initData() {
        initMap();//初始化关注
        Log.e(TAG, "initData: App.getFocusStateObjectId() "+App.getFocusStateObjectId() );
        SharedPreferences spf = App.getAppContext().getSharedPreferences("focusObjOd",MODE_PRIVATE);//先从sharePreference中获取，若取不到就去App中去
        String s = spf.getString("objectId",App.getFocusStateObjectId());

        BmobQuery<FocusState> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(s, new QueryListener<FocusState>() {
            @Override
            public void done(FocusState focusState, BmobException e) {
                if (e == null) {
                    mFocusStates[0] = focusState;///把关注状态外传
                    onRequestSuccess(focusState);//请求成功
                } else {
                    Toast.makeText(AllFocusActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "done: ", new Exception("获取关注信息失败"));
                }
            }
        });
        //将返回键 独立出来处理,点击就上传数据
        mIbtnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadFocusData();
            }
        });
    }

    /*设置按钮的图标*/
    private void setIvBtnIcon(ImageView iv) {
        iv.setImageResource(R.drawable.iv_follow_tick);
        iv.setBackgroundResource(R.drawable.bg_round_btn_lt_gray);
    }

    /*请求数据成功*/
    private void onRequestSuccess(FocusState focusState) {
//        mIsFollowedMap.putAll(focusState.getFocusStateMap());
        initView(focusState);

    }

    /*初始化视图*/
    private void initView(FocusState focusState) {
        mIsFollowedMap.putAll(focusState.getFocusStateMap());

        Log.e(TAG, "initView: focusState.getFocusStateMap() "+focusState.getFocusStateMap() );
        Log.e(TAG, "initView: mIsFollowedMap"+ mIsFollowedMap);

        //遍历关注map
        for (Map.Entry<Integer, Boolean> entry : mIsFollowedMap.entrySet()) {
            if (entry.getValue()) {//如果是关注的
                Log.e(TAG, "initView: mIsFollowedMap entry.getValue()" + entry.getValue());
                switch (entry.getKey()) {//根据键值对图标进行设置
                    case R.id.iv_focus_repairing:
                        setIvBtnIcon(mIvFocusRepairing);
                        break;
                    case R.id.iv_focus_delivering:
                        Log.e(TAG, "initView: case R.id.iv_focus_delivering:\n");
                        setIvBtnIcon(mIvFocusDelivering);
                        break;
                    case R.id.iv_focus_shopping:
                        Log.e(TAG, "initView: case R.id.iv_focus_shopping:\n");
                        setIvBtnIcon(mIvFocusShopping);
                        break;
                    case R.id.iv_focus_doing_sth:
                        Log.e(TAG, "initView: case R.id.iv_focus_shopping:\n");
                        setIvBtnIcon(mIvFocusDoingSth);
                        break;
                    case R.id.iv_focus_using_car:
                        setIvBtnIcon(mIvFocusUsingCar);
                        break;
                    case R.id.iv_focus_rent_house:
                        setIvBtnIcon(mIvFocusRentHouse);
                        break;
                    case R.id.iv_focus_education:
                        setIvBtnIcon(mIvFocusEducation);
                        break;
                    case R.id.iv_focus_photo:
                        setIvBtnIcon(mIvFocusPhoto);
                        break;
                    case R.id.iv_focus_painting:
                        setIvBtnIcon(mIvFocusPainting);
                        break;
                    case R.id.iv_focus_part_time:
                        setIvBtnIcon(mIvFocusPartTime);
                        break;
                    case R.id.iv_focus_housework:
                        setIvBtnIcon(mIvFocusHousework);
                        break;
                    case R.id.iv_focus_others:
                        setIvBtnIcon(mIvFocusOthers);
                        break;
                }
            }
        }



        mTbMainTitle.setText("选择关注");
        mLlFocusRootView.setVisibility(View.VISIBLE);
//            mIsFollowedMap.putAll(mFocusStates[0].getFocusStateMap());//更新到服务器的关注状态
        Toast.makeText(AllFocusActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
    }

    /*上传关注数据到后端云*/

    private String upLoadFocusData() {

//        mIsFollowedMap.putAll(mFocusStates[0].getFocusStateMap());
        Log.e(TAG, "upLoadFocusData: mIsFollowedMap.toString()  "+ mIsFollowedMap.toString() );
        FocusState focusState = new FocusState();
        focusState.setFocusStateMap(mIsFollowedMap);
        focusState.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    onUpLoadSuccess(objectId);
                    id[0] = objectId;
                } else {
                    onUploadFailed(e);
                }
            }
        });
        return id[0];
    }

    /*上传成功*/
    public void onUpLoadSuccess(String objectId) {
        App.setFocusStateObjectId(objectId);
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("focusObjOd",MODE_PRIVATE).edit();
        editor.putString("objectId",objectId);
        editor.commit();
//        Log.e(TAG, "onUpLoadSuccess: objectId = "+objectId );
//        Log.e(TAG, "onUpLoadSuccess: App.getFocusStateObjectId() = "+App.getFocusStateObjectId() );
    }

    /*上传失败*/
    public void onUploadFailed(BmobException e) {
        Toast.makeText(AllFocusActivity.this, "失败：" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        upLoadFocusData();
        Intent intent = new Intent();
        intent.putExtra("objId", id[0]);
    }

    @OnClick({R.id.iv_focus_repairing, R.id.iv_focus_delivering, R.id.iv_focus_shopping, R.id.iv_focus_doing_sth,
            R.id.iv_focus_using_car, R.id.iv_focus_rent_house, R.id.iv_focus_education, R.id.iv_focus_photo,
            R.id.iv_focus_painting, R.id.iv_focus_part_time, R.id.iv_focus_housework,R.id.iv_focus_others})
    public void onClick(View view) {
        /*因为有了这个判断 要注意绑定的监听id*/
        if (mIsFollowedMap.get(view.getId())) {//如果已经关注(打钩状态)
            //把关注取消
            view.setBackgroundResource(R.drawable.bg_round_btn_lt_green);
            ((ImageView) view).setImageResource(R.drawable.iv_add_follow_white);
            mIsFollowedMap.put(view.getId(), false);
        } else {
            view.setBackgroundResource(R.drawable.bg_round_btn_lt_gray);
            ((ImageView) view).setImageResource(R.drawable.iv_follow_tick);
            mIsFollowedMap.put(view.getId(), true);
        }
        Log.e(TAG, "onClick: ========= mIsFollowedMap.toString() "+ mIsFollowedMap.toString() );
//
//        switch (view.getId()) {
//            case R.id.iv_focus_repairing:
//                break;
//            case R.id.iv_focus_delivering:
//                break;
//            case R.id.iv_focus_shopping:
//                break;
//            case R.id.iv_focus_doing_sth:
//                break;
//            case R.id.iv_focus_using_car:
//                break;
//            case R.id.iv_focus_rent_house:
//                break;
//            case R.id.iv_focus_education:
//                break;
//            case R.id.iv_focus_photo:
//                break;
//            case R.id.iv_focus_painting:
//                break;
//            case R.id.iv_focus_part_time:
//                break;
//            case R.id.iv_focus_housework:
//                break;
//            case R.id.iv_focus_others:
//                break;
//        }
    }

}
