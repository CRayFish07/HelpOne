package com.project.rdc.onehelp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.rdc.onehelp.R;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


public class ChatFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mLlSystem;
    private Toolbar mToolbar;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mToolbar= (Toolbar) view.findViewById(R.id.tb_main_top);
        mTextView=(TextView)view.findViewById(R.id.tb_main_title);
        mTextView.setText("消息");
        init();
        return view;

    }

    public void init() {

        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commitAllowingStateLoss();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
