package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.entity.PcContentItem;

import java.util.ArrayList;

/**
 * Created by zjz on 2016/11/16.
 */

public class PcContentAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PcContentItem> mList;
    public PcContentAdapter(Context context , ArrayList<PcContentItem> mList ) {
        this.mList = mList;
        mContext = context;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_pc_content,null);
        TextView textView = (TextView) v.findViewById(R.id.tv_pc_contentItem);
        textView.setText(mList.get(position).getName());
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_pc_contentItem_left);
        imageView.setImageDrawable(mList.get(position).getDrawable());
        return v;
    }
}
