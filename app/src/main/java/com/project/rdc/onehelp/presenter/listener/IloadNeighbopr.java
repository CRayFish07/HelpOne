package com.project.rdc.onehelp.presenter.listener;

import com.project.rdc.onehelp.entity.DetailEntity;

import java.util.List;

/**
 * Created by PC on 2016/11/18.
 */

public interface IloadNeighbopr {

    //加载获取附近界面数据
    void loadNeighborData(String cityName);

    //刷新附近界面
    void freshFragment(List<DetailEntity> list);

    //加载下一页附近界面数据
    void loadMoreNeighborData(String cityName);

}
