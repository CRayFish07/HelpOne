package com.project.rdc.onehelp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rdc.onehelp.R;
import com.project.rdc.onehelp.adapter.FocusGridViewAdapter;
import com.project.rdc.onehelp.application.App;
import com.project.rdc.onehelp.entity.FocusItem;
import com.project.rdc.onehelp.entity.FocusState;
import com.project.rdc.onehelp.ui.AllFocusActivity;
import com.project.rdc.onehelp.ui.CategoryShowActivity;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static android.content.Context.MODE_PRIVATE;

public class FocusFragment extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "FocusFragment";
    private static List<FocusItem> mDataList = new ArrayList<>();//存储关注项的列表

    private static FocusGridViewAdapter mFocusGridViewAdapter;//适配器

    private GridView mGridView;
    private Toolbar mToolbar;
    private TextView mTextView;
    private final static int INIT_DATA_DONE = 1235;
    private static final int REQUEST_CODE = 1118;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_DATA_DONE:
                    Log.e(TAG, "收到msg,加载数据之后数据项:" + FocusFragment.mDataList.size());
                    mFocusGridViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: execute" );
        View v = inflater.inflate(R.layout.fragment_foucs, container, false);
        initView(v);
//        initData();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: execute" );
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e(TAG, "onResume: App.staticMap.toString() "+App.staticMap.toString() );
        FocusFragment.mDataList.clear();
        initData();
    }

    private void initView(View v) {
        Log.e(TAG, "initView: execute" );
        mToolbar = (Toolbar) v.findViewById(R.id.tb_main_top);
        mTextView = (TextView) v.findViewById(R.id.tb_main_title);
        mTextView.setText("关注");
        mGridView = (GridView) v.findViewById(R.id.gv_focus);
        mFocusGridViewAdapter = new FocusGridViewAdapter(getContext(), R.layout.layout_focus_item, mDataList);
        mGridView.setAdapter(mFocusGridViewAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /*初始化数据*/
    private void initData() {
        Log.e(TAG, "initData: execute" );
        SharedPreferences spf = App.getAppContext().getSharedPreferences("focusObjOd",MODE_PRIVATE);
        String focusObjectId = spf.getString("objectId", App.getFocusStateObjectId());
        Log.e(TAG, "initData: App.getFocusStateObjectId() "+ App.getFocusStateObjectId());
        Log.e(TAG, "initData: getSharedPreferences(\"focusObjOd\",MODE_PRIVATE);  "+focusObjectId );

        BmobQuery<FocusState> focusStateQuery = new BmobQuery<>();
        focusStateQuery.getObject(focusObjectId, new QueryListener<FocusState>() {
            @Override
            public void done(FocusState focusState, BmobException e) {
                Log.e(TAG, "done: execute");
                if (e == null) {
                    onLoadDataSuccess(focusState);
                } else {
                    onLoadDataFailed();
                }
            }
        });
    }

    private void onLoadDataFailed() {
        if (mDataList.isEmpty()) {
            mDataList.add(new FocusItem(R.drawable.iv_add_focus, "添加"));
        }
        Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT);
    }

    /*更新数据成功*/
    private void onLoadDataSuccess(FocusState focusState) {
        Log.e(TAG, "onLoadDataSuccess: execute" );
        List<FocusItem> mDataList = new ArrayList<>();
        mDataList.clear();
        Log.e(TAG, "onLoadDataSuccess: part mDataList.isEmpty() "+mDataList.isEmpty() );
        Map<Integer, Boolean> mFocusStateMap = focusState.getFocusStateMap();//或许有问题
        Log.e(TAG, "onLoadDataSuccess: mFocusStateMap.toString() "+mFocusStateMap.toString() );
        FocusItem focusItem;
        Iterator<Map.Entry<Integer, Boolean>> iterator = mFocusStateMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Boolean> entry = iterator.next();
            if (entry.getValue()) {
                switch (entry.getKey()) {
                    case R.id.iv_focus_repairing:
                        Log.e(TAG, "done: repairing");
                        focusItem = new FocusItem(R.drawable.ic_repairing, "维修");
                        mDataList.add(focusItem);
                        Log.e(TAG, "done: repairing done");
                        break;
                    case R.id.iv_focus_delivering:
                        Log.e(TAG, "done: delivering");
                        focusItem = new FocusItem(R.drawable.ic_delivering, "帮送");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_shopping:
                        Log.e(TAG, "done: shopping");
                        focusItem = new FocusItem(R.drawable.ic_shopping, "帮买");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_doing_sth:
                        Log.e(TAG, "done: doing sth ");
                        focusItem = new FocusItem(R.drawable.ic_help_do_sth, "办事");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_using_car:
                        Log.e(TAG, "done: going out ");
                        focusItem = new FocusItem(R.drawable.ic_car, "用车");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_part_time:
                        Log.e(TAG, "done: iv_focus_part_time");
                        focusItem = new FocusItem(R.drawable.ic_part_time, "兼职");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_rent_house:
                        Log.e(TAG, "done: iv_focus_rent_house");
                        focusItem = new FocusItem(R.drawable.ic_rent_house, "租房");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_education:
                        Log.e(TAG, "done: iv_focus_education");
                        focusItem = new FocusItem(R.drawable.ic_education, "教育");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_photo:
                        Log.e(TAG, "done: iv_focus_photo");
                        focusItem = new FocusItem(R.drawable.ic_photo, "摄影");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_painting:
                        Log.e(TAG, "done: iv_focus_painting");
                        focusItem = new FocusItem(R.drawable.ic_foucs_painting, "绘画");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_housework:
                        Log.e(TAG, "done: iv_focus_housework");
                        focusItem = new FocusItem(R.drawable.ic_housework, "家政");
                        mDataList.add(focusItem);
                        break;
                    case R.id.iv_focus_others:
                        Log.e(TAG, "done: iv_focus_others");
                        focusItem = new FocusItem(R.drawable.ic_other, "其他");
                        mDataList.add(focusItem);
                        break;
                    default:
                        Log.e(TAG, "onLoadDataSuccess: default ");
                }
            }
        }
        Log.e(TAG, "onLoadDataSuccess: mDataList.isEmpty() 11" + mDataList.isEmpty() + mDataList.toString());
        //添加“新增 关注项”
        FocusItem focusItemAdd = new FocusItem(R.drawable.iv_add_focus, "添加");
        if (!mDataList.contains(focusItemAdd)) {
            mDataList.add(focusItemAdd);
        }

        FocusFragment.mDataList.clear();
        FocusFragment.mDataList.addAll(mDataList);//此处关键，切记不可用 = 号，用等号改变了此变量的引用，但是适配器持有的是原变量

        Message msg = new Message();
        msg.what = INIT_DATA_DONE;
        mHandler.sendMessage(msg);
    }
    /*根据选择的类型来显示*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (((FocusItem) mGridView.getItemAtPosition(position)).getImgId()) {//根据drawable id来显示
            case R.drawable.iv_add_focus:
                startActivityForResult(new Intent(getContext(), AllFocusActivity.class),REQUEST_CODE );
                break;
            case R.drawable.ic_repairing:
                startActById(R.drawable.ic_repairing);
                break;
            case R.drawable.ic_delivering:
                startActById(R.drawable.ic_delivering);
                break;
            case R.drawable.ic_shopping:
                startActById(R.drawable.ic_shopping);
                break;
            case R.drawable.ic_help_do_sth:
                startActById(R.drawable.ic_help_do_sth);
                break;
            case R.drawable.ic_car:
                startActById(R.drawable.ic_car);
                break;
            case R.drawable.ic_rent_house:
                startActById(R.drawable.ic_rent_house);
                break;
            case R.drawable.ic_education:
                startActById(R.drawable.ic_education);
                break;
            case R.drawable.ic_photo:
                startActById(R.drawable.ic_photo);
                break;
            case R.drawable.ic_part_time:
                startActById(R.drawable.ic_part_time);
                break;
            case R.drawable.ic_housework:
                startActById(R.drawable.ic_housework);
                break;
            case R.drawable.ic_other:
                startActById(R.drawable.ic_other);
                break;

            default:
        }
    }

    private void startActById(int id){
        Intent intent = new Intent(getContext(), CategoryShowActivity.class);
        intent.putExtra("catalog",id);
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String objId = data.getStringExtra("objId");
//        initData();
    }
}
