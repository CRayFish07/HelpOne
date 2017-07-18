package com.project.rdc.onehelp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.entity.DetailEntity;
import com.project.rdc.onehelp.utils.DateTimePickerDialogUtils;
import com.project.rdc.onehelp.utils.MoneyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PublishActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.iv_publish_back)
    ImageButton mIvPublishBack;
    private TextView finishView;
    private GridView gridView;
    private EditText title;
    private EditText content;
    private LinearLayout LocationLayout;
    private LinearLayout MoneyLayout;
    private LinearLayout DatelineLayout;
    private LinearLayout CategoryLayout;
    private TextView dateline_textview;
    private TextView money_textview;
    private TextView location_textview;
    private TextView category_textview;
    private TextView tvTitle;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter adapter;
    private String imagepath;
    private List<String> imagespath = new ArrayList<String>();
    private Bitmap bitmap;
    private String city;
    private final int IMAGE_OPEN = 1;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.inject(this);
        init();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.publish_add);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("image", bitmap);
        imageItem.add(map);
        adapter = new SimpleAdapter(this, imageItem, R.layout.publish_addimage_item, new String[]{
                "image"}, new int[]{R.id.image});
        adapter.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView imageView = (ImageView) view;
                    imageView.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (imageItem.size() == 10 && position == 0) {
                    Toast.makeText(PublishActivity.this, "图片最多添加9张", Toast.LENGTH_SHORT).show();
                } else if (position == 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_OPEN);
                } else {
                    deleteImage(position);
                }
            }
        });
        IntentFilter categoryFilter = new IntentFilter(CategoryActivity.action);
        IntentFilter locationFilter = new IntentFilter(PublishLocationActivity.action);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case CategoryActivity.action:
                        category_textview.setText(intent.getExtras().getString("category"));
                        break;
                    case PublishLocationActivity.action:
                        location_textview.setText(intent.getExtras().getString("location"));
                        city = intent.getExtras().getString("city");
                        break;
                }
            }
        };
        registerReceiver(broadcastReceiver, categoryFilter);
        registerReceiver(broadcastReceiver, locationFilter);

    }

    private void init() {

        finishView = (TextView) findViewById(R.id.tv_publish_finish);
        gridView = (GridView) findViewById(R.id.image_gridview);
        title = (EditText) findViewById(R.id.title_edit_text);
        content = (EditText) findViewById(R.id.content_edit_text);
        LocationLayout = (LinearLayout) findViewById(R.id.layout_location);
        MoneyLayout = (LinearLayout) findViewById(R.id.layout_money);
        DatelineLayout = (LinearLayout) findViewById(R.id.layout_dateline);
        CategoryLayout = (LinearLayout) findViewById(R.id.layout_category);
        dateline_textview = (TextView) findViewById(R.id.tv_dateline);
        money_textview = (TextView) findViewById(R.id.tv_money);
        location_textview = (TextView) findViewById(R.id.tv_location);
        category_textview = (TextView) findViewById(R.id.tv_category);
        tvTitle = (TextView) findViewById(R.id.tb_main_title);
        tvTitle.setText("发布");


        finishView.setOnClickListener(this);
        LocationLayout.setOnClickListener(this);
        MoneyLayout.setOnClickListener(this);
        DatelineLayout.setOnClickListener(this);
        CategoryLayout.setOnClickListener(this);
        dateline_textview.setOnClickListener(this);
        money_textview.setOnClickListener(this);
    }

    private void deleteImage(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PublishActivity.this);
        builder.setTitle("提示");
        builder.setIcon(R.drawable.publish_delete);
        builder.setMessage("确定删除这张照片？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (position >= 0 && position <= 9)
                    imagespath.remove(position);
                imageItem.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (cursor == null) {
                    return;
                }
                cursor.moveToFirst();
                imagepath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                imagespath.add(imagepath);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(imagepath)) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            HashMap<String, Object> map = new HashMap<>();
            map.put("image", bitmap);
            imageItem.add(map);
            adapter = new SimpleAdapter(this, imageItem, R.layout.publish_addimage_item, new String[]{
                    "image"}, new int[]{R.id.image});
            adapter.setViewBinder(new ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data, String textRepresentation) {
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView imageView = (ImageView) view;
                        imageView.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            imagepath = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_publish_back:
                finish();
                break;
            case R.id.tv_publish_finish:
                publish();
                finish();
                break;
            case R.id.layout_location:
            case R.id.tv_location:
            case R.id.iv_go_location:
                location();
                break;
            case R.id.layout_money:
            case R.id.tv_money:
            case R.id.iv_go_money:
                setMoney();
                break;
            case R.id.layout_dateline:
            case R.id.tv_dateline:
            case R.id.iv_go_dateline:
                setDateline();
                break;
            case R.id.layout_category:
            case R.id.tv_category:
            case R.id.iv_go_category:
                choose();
                break;
            default:
                break;
        }
    }

    private void choose() {
        Intent intent = new Intent();
        intent.setClass(PublishActivity.this, CategoryActivity.class);
        startActivity(intent);
    }

    private void setDateline() {
        DateTimePickerDialogUtils dateTimePickerDialogUtils = new DateTimePickerDialogUtils(PublishActivity.this, null);
        dateTimePickerDialogUtils.datetimePickerDialog(dateline_textview);
    }

    private void setMoney() {
        MoneyUtils moneyUtils = new MoneyUtils(PublishActivity.this);
        moneyUtils.setMoney(money_textview);
    }

    private void location() {
        Intent intent = new Intent();
        intent.setClass(PublishActivity.this, PublishLocationActivity.class);
        startActivity(intent);
    }

    private void publish() {
        final String[] images = new String[imagespath.size()];
        imagespath.toArray(images);
        Bmob.uploadBatch(images, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if (list.size() == images.length) {
                    DetailEntity detailEntity = new DetailEntity();
                    detailEntity.setTitle(title.getText().toString());
                    detailEntity.setDetail(content.getText().toString());
                    detailEntity.setCategory(category_textview.getText().toString());
                    detailEntity.setLocation(location_textview.getText().toString());
                    detailEntity.setDateline(dateline_textview.getText().toString());
                    detailEntity.setMoney(money_textview.getText().toString());
                    detailEntity.setTaskStatus("待完成");
                    detailEntity.setHeadIconUrl("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
                    detailEntity.setObjectId("1111");
                    detailEntity.setmCityLocation(city);
                    detailEntity.setImgUrlList(list1);
                    detailEntity.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(PublishActivity.this, "successed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PublishActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
