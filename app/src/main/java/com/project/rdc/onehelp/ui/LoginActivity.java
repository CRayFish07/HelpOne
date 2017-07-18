package com.project.rdc.onehelp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.entity.UserDatabase;
import com.project.rdc.onehelp.presenter.LoginPresenter;
import com.project.rdc.onehelp.ui.view.ILoginView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


public class LoginActivity extends AppCompatActivity implements ILoginView {


    private static final String TAG = "LoginActivity";
    @InjectView(R.id.tb_main_title)
    TextView mTbMainTitle;
    @InjectView(R.id.edit_name)
    EditText mEditName;
    @InjectView(R.id.edit_password)
    EditText mEditPassword;
    @InjectView(R.id.ibtn_login_back)
    ImageButton mIbtnLoginBack;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    @InjectView(R.id.pb_login_progress)
    ProgressBar mPbLoginProgress;

    private String mUserId;
    private String mAccount = null;
    private String mPassword;

    private LoginPresenter mPresenter;
    private InputMethodManager im;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mTbMainTitle.setText("登录");
        mPresenter = new LoginPresenter(this);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                return findUserById(s);
            }
        }, true);
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
    }


    public UserInfo findUserById(String userId) {

        return null;
    }

    @Override
    public String getName() {
        mAccount = mEditName.getText().toString();
        return mAccount;
    }

    @Override
    public String getPassword() {
        mPassword = mEditPassword.getText().toString();
        return mPassword;
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent();
        intent.putExtra("isLogin", true);
        LoginActivity.this.setResult(1, intent);
        LoginActivity.this.finish();
    }

    @Override
    public void loginFailed() {
        setButtonClick(true, "密码错误");
        setProgressBar(View.INVISIBLE);
    }

    @Override
    public void setProgressBar(int visiblity) {
        mPbLoginProgress.setVisibility(visiblity);
    }

    @Override
    public void connectRongServer(String token, final String userName) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            @Override
            public void onSuccess(String userId) {
                mUserId = userId;
                Log.e(TAG, "onSuccess: " + userId);
                saveUserData(userId);

                loginSuccess();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "onSuccess: " + errorCode);
            }

            @Override
            public void onTokenIncorrect() {

            }
        });
    }

    @Override
    public void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setButtonClick(boolean click, String text) {
        mBtnLogin.setText(text);
        mBtnLogin.setEnabled(click);
    }


    @OnClick({R.id.ibtn_login_back, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_login_back:
                finish();
                break;
            case R.id.btn_login:
                mPresenter.login();
                im.hideSoftInputFromWindow(mEditName.getWindowToken(), 0);
                break;
        }
    }


    public void saveUserData(final String userId) {
        BmobQuery<UserDatabase> query = new BmobQuery<>();
        query.getObject(userId, new QueryListener<UserDatabase>() {
            @Override
            public void done(UserDatabase userDatabase, BmobException e) {
                if (e == null) {
                    editor = mPreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("user_id", userId);
                    editor.putString("user_account", userDatabase.getAccount());
                    editor.putString("user_icon", userDatabase.getUserIcon());
                    editor.putString("user_name", userDatabase.getUserName());
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, userDatabase.getUserName(), Uri.parse(userDatabase.getUserIcon())));
                    editor.apply();
                }
            }
        });


    }
}
