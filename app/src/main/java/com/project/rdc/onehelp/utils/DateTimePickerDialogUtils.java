package com.project.rdc.onehelp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.rdc.onehelp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/11/12.
 */
public class DateTimePickerDialogUtils implements DatePicker.OnDateChangedListener,TimePicker.OnTimeChangedListener{
    private DatePicker datePicker;
    private TimePicker timePicker;
    private AlertDialog.Builder builder;
    private String dateline;
    private String initdatetime;
    private Activity activity;

    public DateTimePickerDialogUtils(Activity activity, String initdatetime) {
        this.activity = activity;
        this.initdatetime = initdatetime;
    }

    public void initDateTime(DatePicker datePicker){
        Calendar calendar = Calendar.getInstance();
        /*initdatetime = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) +
                "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" + calendar.get(Calendar.HOUR_OF_DAY) +
                "时" + calendar.get(Calendar.MINUTE) + "分";
        datePicker.init(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,this);
        timePicker.setCurrentHour(Calendar.HOUR_OF_DAY);
        timePicker.setCurrentMinute(Calendar.MINUTE);*/
        initdatetime = "2016年1月1日" +  calendar.get(Calendar.HOUR_OF_DAY) +
                "时" + calendar.get(Calendar.MINUTE) + "分";
        datePicker.init(2016,1,1,this);
        //timePicker.setCurrentHour(Calendar.HOUR_OF_DAY);
        //timePicker.setCurrentMinute(Calendar.MINUTE);
    }

    public AlertDialog.Builder datetimePickerDialog(final TextView textView){
        LinearLayout datetimeLayout = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.dateline_layout,null);
        datePicker = (DatePicker)datetimeLayout.findViewById(R.id.datePicker);
        //timePicker = (TimePicker)datetimeLayout.findViewById(R.id.timePicker);
        initDateTime(datePicker);
        //timePicker.setIs24HourView(true);
        //timePicker.setOnTimeChangedListener(this);
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("请设置好截至时间");
        //builder.setIcon(R.drawable.clock);
        builder.setView(datetimeLayout);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dateline != null) {
                    dialog.dismiss();
                    textView.setText(dateline);
                } else {
                    dialog.dismiss();
                    textView.setText(initdatetime);
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
        onDateChanged(null,0,0,0);
        return builder;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        dateline = simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null,0,0,0);
    }
}
