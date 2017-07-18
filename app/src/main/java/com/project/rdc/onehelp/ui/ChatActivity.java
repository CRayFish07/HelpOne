package com.project.rdc.onehelp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.rdc.onehelp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.rong.imlib.model.Conversation;

public class ChatActivity extends FragmentActivity implements View.OnClickListener {
    @InjectView(R.id.tb_main_title)
    TextView mTbMainTitle;
    @InjectView(R.id.ibtn_chat_back)
    ImageButton mIbtnChatBack;
    @InjectView(R.id.ibtn_chat_more)
    ImageButton mIbtnChatMore;
    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mTbMainTitle.setText(getIntent().getData().getQueryParameter("title"));
    }


    @OnClick({R.id.ibtn_chat_back, R.id.ibtn_chat_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_chat_back:
                finish();
                break;
            case R.id.ibtn_chat_more:
                break;
        }
    }
}
