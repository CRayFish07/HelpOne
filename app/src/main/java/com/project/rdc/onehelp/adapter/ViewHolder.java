package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/15.
 */

public class ViewHolder {

    private int position;
    private View convertView;
    private SparseArray<View> views;

    public ViewHolder(Context context, ViewGroup parents, int layoutId, int position){

        this.position = position;
        this.views = new SparseArray<View>();
        convertView = LayoutInflater.from(context).inflate(layoutId,parents,false);
        convertView.setTag(this);
    }

    public static ViewHolder get(Context context,View convertView,ViewGroup parents,int layoutId,int position){
        if(convertView == null){
            return new ViewHolder(context,parents,layoutId,position);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.position = position;
            return viewHolder;
        }
    }

    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T)view;
    }
    public View getConvertView(){
        return convertView;
    }
}
