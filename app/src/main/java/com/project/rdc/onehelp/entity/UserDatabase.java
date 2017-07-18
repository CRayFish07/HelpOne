package com.project.rdc.onehelp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Time:2016.11.11 11:22
 * Created By:ThatNight
 */

public class UserDatabase extends BmobObject {
    private String account;
    private String userName;
    private String password;
    private String token;
    private String userIcon;


    public UserDatabase() {
        super();
    }


    public UserDatabase(String account, String password) {
        this.account= account;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
