package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> datas;
    protected int LayoutId;
    protected LayoutInflater layoutInflater;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        LayoutId = layoutId;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = ViewHolder.get(context,convertView,parent,LayoutId,position);
        convert(viewHolder,getItem(position),position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder,T t,int position);
}
