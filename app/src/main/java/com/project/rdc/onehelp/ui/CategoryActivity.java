package com.project.rdc.onehelp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.project.rdc.onehelp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */
public class CategoryActivity extends Activity {

    public static final String action = "chooseCategory";

    private GridView gridview;
    private List<Map<String, Object>> list;
    private SimpleAdapter adapter;
    private int[] images = {R.drawable.ic_car, R.drawable.ic_delivering, R.drawable.ic_education,
            R.drawable.ic_foucs_painting, R.drawable.ic_other,
            R.drawable.ic_part_time, R.drawable.ic_photo, R.drawable.ic_rent_house,
            R.drawable.ic_repairing,
            R.drawable.ic_help_do_sth};
    private String[] images_name = {"用车", "帮买", "教育", "绘画", "帮忙", "其他", "摄影", "租房", "维修","其他"};
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        init();
        adapter = new SimpleAdapter(this, list, R.layout.publish_category_item,
                new String[]{"image", "name"}, new int[]{R.id.image, R.id.text});
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(action);
                intent.putExtra("category", images_name[position]);
                sendBroadcast(intent);
                Toast.makeText(CategoryActivity.this, "img" + position, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void init() {
        gridview = (GridView) findViewById(R.id.gridview);
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            map.put("name", images_name[i]);
            list.add(map);
        }
        backButton = (ImageView) findViewById(R.id.iv_category_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
