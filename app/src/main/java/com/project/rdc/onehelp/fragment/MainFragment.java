package com.project.rdc.onehelp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.adapter.NeighborItemClickListener;
import com.project.rdc.onehelp.adapter.NeighborRequestAdapter;
import com.project.rdc.onehelp.entity.DetailEntity;
import com.project.rdc.onehelp.fragment.view.ImainFragment;
import com.project.rdc.onehelp.presenter.LoadNeighborImpe;
import com.project.rdc.onehelp.presenter.listener.IloadNeighbopr;
import com.project.rdc.onehelp.ui.DetailActivity;
import com.project.rdc.onehelp.ui.LocationActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainFragment extends Fragment implements ImainFragment{
    private static final String TAG = "MainFragment";
    private static final String STORE_PARAM = "param";
    @InjectView(R.id.rv_neighbor_list)
    RecyclerView mNeighborList;
    @InjectView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    Toolbar mTb;
    @InjectView(R.id.tv_location_select)
    TextView mTvLocationSelect;
    @InjectView(R.id.rl_location_select)
    RelativeLayout mRlLocationSelect;

    private String mParam;
    private Activity mActivity;
    private String curLocation = "广州市";
//    private List<NeighborRequestBean> mBeanList;
    private NeighborRequestAdapter mRequestAdapter;
    private LocationClient mLocationClient = null;
    private BDLocationListener mLocationListener;
    private IloadNeighbopr mIloadNeighbopr;
    private List<DetailEntity> mBeanList;


    public static Fragment newInstance(String param) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(STORE_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(STORE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        mTb = (Toolbar) view.findViewById(R.id.tb_main_top);
        Log.e(TAG, "onCreateView: ");
        mIloadNeighbopr = new LoadNeighborImpe(this);
        initLocation();
        initRefresh();
        return view;
    }

    private void initRefresh(){
        Log.e(TAG, "initRefresh: ");
        mNeighborList.setLayoutManager(new LinearLayoutManager(mActivity));
        mSrlRefresh.setColorSchemeColors(Color.RED);
        mSrlRefresh.setRefreshing(true);
        mIloadNeighbopr.loadNeighborData(curLocation);
//        mSrlRefresh.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlRefresh.setRefreshing(true);
                mIloadNeighbopr.loadNeighborData(curLocation);
            }
        });
    }

    /**
     * 定位初始化
     */
    private void initLocation(){
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setTimeOut(10000);
        option.setScanSpan(10000);
        option.setAddrType("all");
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.rl_location_select)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(mActivity, LocationActivity.class);
        intent.putExtra("locationName",mTvLocationSelect.getText().toString());
        mActivity.startActivityForResult(intent,2);
    }

    /**
     *刷新界面
     */
    @Override
    public void freshData(List<DetailEntity> list) {
        if(mRequestAdapter == null){
            Log.e(TAG, "freshData:   " + "null");
            mBeanList = list;
            mRequestAdapter = new NeighborRequestAdapter(mActivity, list);
            mNeighborList.setAdapter(mRequestAdapter);
            mRequestAdapter.setOnItemClickListener(new NeighborItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(mActivity, DetailActivity.class);
                    intent.putExtra("ID",mBeanList.get(position).getID());
                    startActivity(intent);
                }
            });
            mSrlRefresh.setRefreshing(false);
        }else {
            Log.e(TAG, "freshData:     "+"not null" );
            mSrlRefresh.setRefreshing(false);
            mRequestAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 加载更多显示
     */
    @Override
    public void loadMoreData(List<DetailEntity> list) {

    }

    /**
     *接受定位搜索界面传来数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2 && resultCode == 1){
            curLocation = data.getStringExtra("locationSelected");
            mTvLocationSelect.setText(curLocation);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 定位附近监听器
     */
    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.e(TAG, "onReceiveLocation: "+bdLocation.getAddrStr());
            if(bdLocation == null)
                return;
            curLocation = bdLocation.getCity();
            mTvLocationSelect.setText(curLocation);
            Log.e(TAG, "onReceiveLocation: "+curLocation );
        }
    }
}