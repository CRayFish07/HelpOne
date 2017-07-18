package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.project.rdc.onehelp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

public class LocationListAdapter extends CommonAdapter<PoiInfo> {

    public LocationListAdapter(Context context, List<PoiInfo> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, PoiInfo poiInfo, int position) {
        TextView name = viewHolder.getView(R.id.location_name);
        TextView address = viewHolder.getView(R.id.location_address);
        name.setText(poiInfo.name);
        address.setText(poiInfo.address);
    }
}
