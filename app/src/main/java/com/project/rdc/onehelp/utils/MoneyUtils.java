package com.project.rdc.onehelp.utils;

import com.project.rdc.onehelp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/12.
 */
public class MoneyUtils {
    private AlertDialog.Builder builder;
    private EditText digit_edittext;
    private Activity activity;

    public MoneyUtils(Activity activity) {
        this.activity = activity;
    }

    public AlertDialog.Builder setMoney(final TextView textView){
        final LinearLayout moneyLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.money_layout,null);
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("设置悬赏金额");
        builder.setView(moneyLayout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                digit_edittext = (EditText) moneyLayout.findViewById(R.id.digitEdit);
                if (!digit_edittext.getText().toString().equals("")) {
                    dialog.dismiss();
                    textView.setText(digit_edittext.getText().toString() + "元");
                } else {
                    dialog.dismiss();
                    textView.setText("0元");
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
        return builder;
    }
}
