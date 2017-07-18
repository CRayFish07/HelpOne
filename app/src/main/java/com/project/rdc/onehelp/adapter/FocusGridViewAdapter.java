package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.entity.FocusItem;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class FocusGridViewAdapter extends ArrayAdapter<FocusItem> {
    private List<FocusItem> mDataList;
    int layoutId;

    public FocusGridViewAdapter(Context context, int layoutId, List<FocusItem> dataList) {
        super(context, layoutId, dataList);
        this.layoutId = layoutId;
        this.mDataList = dataList;
    }

//    public FocusGridViewAdapter(Context context, int layoutId, Set<FocusItem> focusItemSet) {
//        super(context, layoutId, focusItemSet);
//        this.layoutId = layoutId;
//    }

    @Override
    public int getCount() {
        if (null != mDataList){
            return mDataList.size();
        }else{
            return 0;
        }
    }

    @Nullable
    @Override
    public FocusItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).getImgId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FocusItem item = getItem(position);
        View view;
        ItemViewHolder itemViewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_focus_item, null);
            itemViewHolder = new ItemViewHolder();
            itemViewHolder.itemIcon = (ImageView) view.findViewById(R.id.iv_focus_item);
            itemViewHolder.tvItemName = (TextView)view.findViewById(R.id.tv_focus_item);
            view.setTag(itemViewHolder);
        }else {
            view = convertView;
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }
        itemViewHolder.itemIcon.setImageResource(item.getImgId());
        itemViewHolder.tvItemName.setText(item.getName());
        return view;
    }
    class ItemViewHolder {
        ImageView itemIcon;
        TextView tvItemName;
    }

}
