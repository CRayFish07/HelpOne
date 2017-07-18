package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.entity.FocusItem;

import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

import static com.baidu.platform.comapi.d.c.F;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class FocusGridViewBaseAdapter extends BaseAdapter {
    private List<FocusItem> mDataList;
    private Set<FocusItem> mFocusItemSet;
    private int layoutId;
    private Context mContext;

    public FocusGridViewBaseAdapter(Context context, int layoutId, Set<FocusItem> mFocusItemSet) {
        mContext = context;
        this.layoutId = layoutId;
        this.mFocusItemSet = mFocusItemSet;
    }

    @Override
    public int getCount() {
        if (null != mFocusItemSet){
            return mFocusItemSet.size();
        }else{
            return 0;
        }
    }

    @Nullable
    @Override
    public FocusItem getItem(int position) {
        return mDataList.get(position);//??
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).getImgId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FocusItem item = getItem(position);
        View view;
        FocusGridViewBaseAdapter.ItemViewHolder itemViewHolder;
        if (convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_focus_item, null);
            itemViewHolder = new FocusGridViewBaseAdapter.ItemViewHolder();
            itemViewHolder.itemIcon = (ImageView) view.findViewById(R.id.iv_focus_item);
            itemViewHolder.tvItemName = (TextView)view.findViewById(R.id.tv_focus_item);
            view.setTag(itemViewHolder);
        }else {
            view = convertView;
            itemViewHolder = (FocusGridViewBaseAdapter.ItemViewHolder) convertView.getTag();
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
