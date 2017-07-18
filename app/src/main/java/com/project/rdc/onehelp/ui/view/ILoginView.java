package com.project.rdc.onehelp.ui.view;

/**
 * Time:2016.11.14 13:29
 * Created By:ThatNight
 */

public interface ILoginView {

    String getName();

    String getPassword();

    public void loginSuccess();

    public void loginFailed();

    public void setProgressBar(int visiblity);

    public void connectRongServer(String token, final String userName);

    public void toast(String toast);

    public void setButtonClick(boolean click,String text);

}
