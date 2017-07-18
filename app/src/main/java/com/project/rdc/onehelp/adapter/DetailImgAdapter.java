package com.project.rdc.onehelp.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/11/12 0012.
 */

public class DetailImgAdapter extends NineGridImageViewAdapter<String>{

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, String s) {
        Picasso.with(context)
                .load(s)
                .into(imageView);
    }

}

