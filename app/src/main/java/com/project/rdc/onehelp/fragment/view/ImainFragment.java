              package com.project.rdc.onehelp.fragment.view;

import com.project.rdc.onehelp.entity.DetailEntity;

import java.util.List;

/**
 * Created by PC on 2016/11/18.
 */

public interface ImainFragment {

    void freshData(List<DetailEntity> list);

    void loadMoreData(List<DetailEntity> list);
}
