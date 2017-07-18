package com.project.rdc.onehelp.http;

import android.util.Log;

import com.project.rdc.onehelp.entity.DetailEntity;
import com.project.rdc.onehelp.presenter.LoadNeighborImpe;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by PC on 2016/11/18.
 */

public class NeighborData {
    private static final String TAG = "NeighborData";
    private List<DetailEntity> mList; //附近界面数据List
    private int page = 0;  //当前页数
    private int onePageSum = 20;  //一页的数据条数
    private LoadNeighborImpe  loadPresenter;

    public NeighborData(LoadNeighborImpe loadPresenter){
        mList = new ArrayList<>();
        this.loadPresenter = loadPresenter;
    }

    /**
     * 获取附近界面定位城市的数据
     * @param cityName 定位城市
     * @return   定位城市数据
     */
    public void getNeighborData(String cityName){
        page = 0;
        BmobQuery<DetailEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("mCityLocation",cityName);
        query.setLimit(onePageSum);
        query.findObjects(new FindListener<DetailEntity>() {
            @Override
            public void done(List<DetailEntity> list, BmobException e) {
                Log.e(TAG, "done:     有执行");
                if(e == null){
                    mList = list;
                    Log.e(TAG, "done:  获取数据 " + list.size());
                    loadPresenter.freshFragment(list);
                }else {
                    Log.e(TAG, "done: "+"bmob失败"+e.getMessage());
                }
            }
        });
        Log.e(TAG, "getNeighborData: "+ mList.size());
    }

    /**
     * 加载更多数据方法
     */
    public List<DetailEntity> loadMoreData(String cityName){
        ++page;
        BmobQuery<DetailEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("mCityLocation",cityName);
        query.setLimit(onePageSum);
        query.setSkip(onePageSum * page);
        query.findObjects(new FindListener<DetailEntity>() {
            @Override
            public void done(List<DetailEntity> list, BmobException e) {
                if(e == null){
                    mList = list;
                }else {
                    Log.e(TAG, "done: "+"bmob失败"+e.getMessage());
                }
            }
        });
        return mList;
    }
}
