package com.project.rdc.onehelp.presenter;

import android.util.Log;
import android.view.View;

import com.project.rdc.onehelp.constant.Finaldata;
import com.project.rdc.onehelp.entity.UserDatabase;
import com.project.rdc.onehelp.http.HttpUserInfo;
import com.project.rdc.onehelp.ui.view.ILoginView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.BmobRealTimeData.TAG;

/**
 * Time:2016.11.14 13:26
 * Created By:ThatNight
 */

public class LoginPresenter {

    private String mUserId;
    private String mUserName;
    private String mUserPwd;
    ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        super();
        this.loginView = loginView;
    }

    public void login() {
        loginView.setProgressBar(View.VISIBLE);
        loginView.setButtonClick(false,"");
        initData(loginView.getName(), loginView.getPassword());
    }


    private void initData(final String userAccount, final String password) {
        UserDatabase user = new UserDatabase(userAccount, password);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String userId, BmobException e) {
                if (e == null) {
                    getToken(userId, userAccount);
                    mUserId = userId;
                } else {
                    //已注册
                    Log.d("login", "done: " + e.getMessage());
                    hasUserInfo(userAccount, password);
                }
            }
        });
    }

    private void hasUserInfo(final String account, final String password) {
        Log.d(TAG, "hasUserInfo: " + account + "  ");
        BmobQuery<UserDatabase> query = new BmobQuery<>();
        query.addWhereEqualTo("account", account);
        query.findObjects(new FindListener<UserDatabase>() {
            @Override
            public void done(List<UserDatabase> list, BmobException e) {
                if (e == null) {
                    UserDatabase newUser = list.get(0);
                    mUserId = newUser.getObjectId();
                    if (newUser.getPassword().equals(password)) {
                        getToken(mUserId, account);
                    } else {
                        loginView.loginFailed();
                    }
                } else {
                    Log.d(TAG, "done: ." + e.getMessage() + "\n");
                }
            }
        });
    }


    private void getToken(String userId, String userName) {
        HttpUserInfo.getUserInfo(Finaldata.TOKEN_GET, userName, userId, loginView);
    }


}
