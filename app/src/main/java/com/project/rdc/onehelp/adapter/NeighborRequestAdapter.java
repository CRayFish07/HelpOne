package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.entity.DetailEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by PC on 2016/11/11.
 */

public class NeighborRequestAdapter extends RecyclerView.Adapter<NeighborRequestAdapter.RequestViewHolder> {

//    private List<NeighborRequestBean> mBeanList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private DetailImgAdapter mImgAdapter;
    private NeighborItemClickListener mListener;
    private List<DetailEntity> mBeanList;

    @Override
    public int getItemCount() {
        return  mBeanList.size();
    }

    public NeighborRequestAdapter(Context context, List<DetailEntity> beanList) {
        super();
        mContext = context;
        mBeanList = beanList;
        mImgAdapter = new DetailImgAdapter();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_neighbor_request, parent, false);
        return new RequestViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        DetailEntity requestBean = mBeanList.get(position);
        holder.mSdvUserIcon.setImageURI(Uri.parse(requestBean.getHeadIconUrl()));
//        if (requestBean.getUerSex().equals("女")) {
//            holder.mIvUserSex.setImageResource(R.drawable.iv_sex_woman);
//        } else {
//            holder.mIvUserSex.setImageResource(R.drawable.iv_sex_man);
//        }
        holder.mTvUserName.setText(requestBean.getID());
        holder.mTvPublishPlace.setText(requestBean.getMlocation());
        holder.mTvPublishType.setText(requestBean.getCategory());
        holder.mTvPublishTitle.setText(requestBean.getTitle());
        holder.mTvPublishTime.setText(requestBean.getMdateline());
        holder.mTvPublishMoney.setText(requestBean.getMoney());
        holder.mNgivPublishPic.setAdapter(mImgAdapter);
        holder.mNgivPublishPic.setImagesData(requestBean.getImgUrlList());
    }

    /**
     * 为Adapter暴露一个Item点击监听的公开方法
     *
     */
    public void setOnItemClickListener(NeighborItemClickListener listener) {
        this.mListener = listener;
    }

    class RequestViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.sdv_user_icon)
        SimpleDraweeView mSdvUserIcon;
//        @InjectView(R.id.iv_user_sex)
//        ImageView mIvUserSex;
        @InjectView(R.id.tv_user_name)
        TextView mTvUserName;
        @InjectView(R.id.tv_publish_place)
        TextView mTvPublishPlace;
        @InjectView(R.id.tv_publish_type)
        TextView mTvPublishType;
        @InjectView(R.id.tv_publish_title)
        TextView mTvPublishTitle;
        @InjectView(R.id.tv_publish_time)
        TextView mTvPublishTime;
        @InjectView(R.id.tv_publish_money)
        TextView mTvPublishMoney;
        @InjectView(R.id.ngiv_publish_pic)
        NineGridImageView mNgivPublishPic;

        public RequestViewHolder(View itemView,final NeighborItemClickListener listener) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,getAdapterPosition());
                }
            });
        }
    }

}
