package com.project.rdc.onehelp.presenter;

import android.util.Log;

import com.project.rdc.onehelp.entity.DetailEntity;
import com.project.rdc.onehelp.fragment.view.ImainFragment;
import com.project.rdc.onehelp.http.NeighborData;
import com.project.rdc.onehelp.presenter.listener.IloadNeighbopr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2016/11/18.
 */

public class LoadNeighborImpe implements IloadNeighbopr{
    private static final String TAG = "LoadNeighborImpe";
    private NeighborData mNeighborData;
    private ImainFragment mImainFragment;
    private List<DetailEntity> mList;

    public LoadNeighborImpe(ImainFragment imainFragment){
        mNeighborData = new NeighborData(this);
        mImainFragment = imainFragment;
        mList = new ArrayList<>();
        Log.e(TAG, "LoadNeighborImpe: ");
    }

    @Override
    public void loadNeighborData(String cityName) {
        Log.e(TAG, "loadNeighborData:  " + cityName);
        mNeighborData.getNeighborData(cityName);
    }

    @Override
    public void loadMoreNeighborData(String cityName) {
        mList = mNeighborData.loadMoreData(cityName);
        mImainFragment.loadMoreData(mList);
    }

    @Override
    public void freshFragment(List<DetailEntity> list) {
        mImainFragment.freshData(list);
    }
}
