package com.project.rdc.onehelp.http;

import android.util.Log;
import android.view.View;

import com.project.rdc.onehelp.constant.Finaldata;
import com.project.rdc.onehelp.entity.UserDatabase;
import com.project.rdc.onehelp.ui.view.ILoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.bmob.v3.BmobRealTimeData.TAG;

/**
 * Time:2016.11.11 11:48
 * Created By:ThatNight
 */

public class HttpUserInfo {

    private static String mresult;


    public static void getUserInfo(String url, final String account, final String userId, final ILoginView loginView) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8,TimeUnit.SECONDS)
                .build();

        Random r = new Random();
        String Nonce = (r.nextInt(10000) + 10000) + "";
        String Timestamp = (System.currentTimeMillis() / 1000) + "";

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userId", userId);
        builder.add("name", account);
        builder.add("portraitUri", "http://www.rongcloud.cn/docs/assets/img/logo_s.png");
        Request request = new Request.Builder().url(url).post(builder.build())
                .addHeader("App-Key", Finaldata.APP_KEY)
                .addHeader("Nonce", Nonce)
                .addHeader("Timestamp", Timestamp)
                .addHeader("Signature", encryptToSHA(Finaldata.APP_SECRECT + Nonce + Timestamp)).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e.getCause().equals(SocketTimeoutException.class)){
                    loginView.setButtonClick(true,"连接超时");
                    loginView.setProgressBar(View.INVISIBLE);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    getUserJson(response.body().string(), userId, account, loginView);
                }
            }
        });
    }

    public static String getUserJson(String response, String userId, String userAcount, ILoginView loginView) {

        String result = "";

        try {
            JSONObject resultJson = new JSONObject(response);
            if (resultJson.getString("code").equals("200")) {
                result += resultJson.getString("token");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("token", "getUserJson: " + response + result);


        UserDatabase userDatabase = new UserDatabase();
        userDatabase.setToken(result);
        userDatabase.setUserName("用户"+userId);
        userDatabase.setUserIcon("http://www.qqtn.com/up/2014-6/14020448429414396.jpg");
        userDatabase.update(userId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.d(TAG, "done: " + "update");
            }
        });
        loginView.connectRongServer(result, userAcount);
        return result;
    }

    public static String getMresult() {
        return mresult;
    }


    public static abstract class HttpCallback {
        public abstract void onSuccess(String response);


    }


    //以下为MD5转密
    public static String encryptToSHA(String info) {
        byte[] digesta = null;

        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String rs = byte2Hex(digesta);
        return rs;
    }

    public static String byte2Hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs += "0" + stmp;
            } else {
                hs += stmp;
            }
        }
        return hs;
    }
}
