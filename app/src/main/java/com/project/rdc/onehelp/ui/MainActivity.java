package com.project.rdc.onehelp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.adapter.MainFragmentAdapter;
import com.project.rdc.onehelp.fragment.ChatFragment;
import com.project.rdc.onehelp.fragment.FocusFragment;
import com.project.rdc.onehelp.fragment.MainFragment;
import com.project.rdc.onehelp.fragment.PcFragment;
import com.project.rdc.onehelp.view.MainNavigateTabBar;
import com.project.rdc.onehelp.view.StaticViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<Fragment> fragments;
    private MainFragment mMainFragment;
    private FocusFragment mFocusFragment;
    private ChatFragment mChatFragment;
    private PcFragment mPcFragment;
    private boolean isLogin = false;

    private StaticViewPager vpage;
    private FragmentPagerAdapter adapter;
    private boolean[] fragmentsUpdateFlag = {false, false, false, false};
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;


    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_CITY = "关注";
    private static final String TAG_PAGE_PUBLISH = "发布";
    private static final String TAG_PAGE_MESSAGE = "消息";
    private static final String TAG_PAGE_PERSON = "我的";
    private MainNavigateTabBar navigateBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initView(savedInstanceState);
            initFragments();
        }
        mSharedPreferences = this.getSharedPreferences("user_info", MODE_PRIVATE);

    }

    private void initView(Bundle save) {
        navigateBar = (MainNavigateTabBar) findViewById(R.id.main_tab_mainTabBar);
        navigateBar.onRestoreInstanceState(save);
        navigateBar.addTab(new MainNavigateTabBar.TabParam(R.mipmap.iv_tab_main_home, R.mipmap.iv_tab_main_homefill, TAG_PAGE_HOME));
        navigateBar.addTab(new MainNavigateTabBar.TabParam(R.mipmap.iv_tab_main_location, R.mipmap.iv_tab_main_locationfill, TAG_PAGE_CITY));
        navigateBar.addTab(new MainNavigateTabBar.TabParam(0, 0, TAG_PAGE_PUBLISH));
        navigateBar.addTab(new MainNavigateTabBar.TabParam(R.mipmap.iv_tab_main_mark, R.mipmap.iv_tab_main_markfill, TAG_PAGE_MESSAGE));
        navigateBar.addTab(new MainNavigateTabBar.TabParam(R.mipmap.iv_tab_main_my, R.mipmap.iv_tab_main_myfill, TAG_PAGE_PERSON));

    }


    /**
     * 初始化用到的Fragment
     */
    private void initFragments() {
        vpage = (StaticViewPager) findViewById(R.id.vpage_main);
        mMainFragment = new MainFragment();
        mFocusFragment = new FocusFragment();
        mChatFragment = new ChatFragment();
        mPcFragment = new PcFragment();

        fragments = new ArrayList<>();
        fragments.add(mMainFragment);
        fragments.add(mFocusFragment);
        fragments.add(mChatFragment);
        fragments.add(mPcFragment);


        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, fragmentsUpdateFlag);
        vpage.setAdapter(adapter);
        vpage.setScroll(false);

        vpage.setOffscreenPageLimit(4);//缓存2个


        navigateBar.setViewPager(vpage);
        navigateBar.setLogin(isLogin);
        navigateBar.setTabSelectListener(new MainNavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(MainNavigateTabBar.ViewHolder holder) {
                if (holder.getTabIndex() == 2 && !isLogin) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (data != null) {
                    isLogin = data.getBooleanExtra("isLogin", false);
                    if (isLogin) {
                        adapter.notifyDataSetChanged();
                    }
                    navigateBar.setLogin(isLogin);
                    navigateBar.showFragment(navigateBar.getViewHolderList().get(2));
                    //fragmentsUpdateFlag[2]=true;
                    //initFragments();
                }
                break;
            case 2:
                mMainFragment.onActivityResult(requestCode,resultCode,data);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        editor = mSharedPreferences.edit();
        //editor.putBoolean("firstEnter", true);
        editor.putBoolean("isLogin",false);
        editor.apply();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        navigateBar.onSaveInstanceState(outState);
    }


    public void onClickPublish(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, PublishActivity.class);
        startActivity(intent);
    }
}
