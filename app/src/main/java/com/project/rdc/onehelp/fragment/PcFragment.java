package com.project.rdc.onehelp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.adapter.PcContentAdapter;
import com.project.rdc.onehelp.entity.PcContentItem;
import com.project.rdc.onehelp.ui.PublishedActivity;
import com.project.rdc.onehelp.ui.SuccessfulActivity;
import com.project.rdc.onehelp.ui.WaitingActivity;
import com.project.rdc.onehelp.view.ListViewForScrollView;

import java.util.ArrayList;

import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;


public class PcFragment extends Fragment {

    private static final String TAG = "PcFragment";

    private PcContentAdapter pcContentAdapter;
    private Object mUserData;
    private SharedPreferences mPreferences;
    private TextView mTextView;
    private SwipeRefreshLayout mSr;
    private SimpleDraweeView mSdv;
    private ListViewForScrollView mLvfs;

    private TextView tvName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pc, container, false);
        InitView(view);
        InitData();

        ButterKnife.inject(this, view);
        return view;
    }

    public void InitView(View view) {
        mTextView = (TextView) view.findViewById(R.id.tb_main_title);
        tvName=(TextView)view.findViewById(R.id.tv_pc_name);
        mTextView.setText("我的");
        mSr = (SwipeRefreshLayout) view.findViewById(R.id.sr_pc_content);
        mSr.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.refresh_color);
        mSdv = (SimpleDraweeView) view.findViewById(R.id.sdv_pc_icon);
        mLvfs = (ListViewForScrollView) view.findViewById(R.id.lvfs_pc_content);
        mPreferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
    }

    public void InitData() {
        if (mPreferences.getBoolean("isLogin", false)) {
            tvName.setText(mPreferences.getString("user_name", "用户"));
            String iconUrl=mPreferences.getString("user_icon", null);
            Log.d(TAG, "InitData: "+iconUrl);
            mSdv.setImageURI(Uri.parse(iconUrl));
        }



        mSr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getUserData();
                        //mSdv.setImageURI(Uri.parse("http://www.qqtn.com/up/2014-6/14020448429414396.jpg"));//设置头像
                        mSr.setRefreshing(false);
                    }
                });
            }
        });



        PcContentItem pcContentItem1 = new PcContentItem(getResources().getDrawable(R.drawable.iv_pc_successful), "已完成");
        PcContentItem pcContentItem2 = new PcContentItem(getResources().getDrawable(R.drawable.iv_pc_waiting), "待完成");
        PcContentItem pcContentItem3 = new PcContentItem(getResources().getDrawable(R.drawable.iv_pc_published), "已发布");
        ArrayList<PcContentItem> arrayList = new ArrayList<PcContentItem>();
        arrayList.add(pcContentItem1);
        arrayList.add(pcContentItem2);
        arrayList.add(pcContentItem3);
        pcContentAdapter = new PcContentAdapter(getContext(), arrayList);
        mLvfs.setAdapter(pcContentAdapter);

        mLvfs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + position);
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), SuccessfulActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), WaitingActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), PublishedActivity.class));
                        break;
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void getUserData() {
        if (mPreferences.getBoolean("isLogin", false)) {
            tvName.setText(mPreferences.getString("user_name", "用户"));
            String iconUrl=mPreferences.getString("user_icon", null);
            Log.d(TAG, "InitData: "+iconUrl);
            mSdv.setImageURI(Uri.parse(iconUrl));
        }else {
            mSdv.setImageURI(Uri.parse("http://www.qqtn.com/up/2014-6/14020448429414396.jpg"));
        }

    }
}
