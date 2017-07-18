package com.project.rdc.onehelp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Time:2016.11.18 16:57
 * Created By:ThatNight
 */

public class PcTaskAdapter extends RecyclerView.Adapter<PcTaskAdapter.TaskHolder> {

    //List<>



    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TaskHolder extends RecyclerView.ViewHolder{

        public TaskHolder(View itemView) {
            super(itemView);
        }

    }
}
