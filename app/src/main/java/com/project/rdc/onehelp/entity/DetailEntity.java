package com.project.rdc.onehelp.entity;

import android.util.SparseArray;

import java.util.List;

import cn.bmob.v3.BmobObject;
/**
 * Created by Administrator on 2016/11/10 0010.
 */
/*
*详情内容*/
public class DetailEntity extends BmobObject{

    private String mTaskStatus;//任务状态
    private String mTitle;//标题
    private String mDetail;//详情
    private String mCategory;//类别
    private String mID;//用户id
    private List<String> imgUrlList;//上传的图片url
    private String headIconUrl;//头像的Url
    private String money;//悬赏金额
    private String mlocation;//地理位置
    private String mCityLocation;//城市定位
    private String mdateline;//截至时间

    SparseArray<DetailEntity> mEntitySparseArray = new SparseArray<>();//存储用户信息的稀疏数组

    public DetailEntity() {
        super();
    }

    public DetailEntity(String taskStatus, String title, String detail, String category,String mID,
                        String money,String dateline,String location,List<String> imgUrlList, String headIconUrl) {
        mTaskStatus = taskStatus;
        mTitle = title;
        mDetail = detail;
        mCategory = category;
        mlocation = location;
        mdateline = dateline;
        this.money = money;
        this.imgUrlList = imgUrlList;
        this.headIconUrl = headIconUrl;
    }

    public String getmCityLocation() {
        return mCityLocation;
    }

    public void setmCityLocation(String mCityLocation) {
        this.mCityLocation = mCityLocation;
    }

    public String getID() {
        return mID;
    }

    public String getMoney() {
        return money;
    }

    public String getMlocation() {
        return mlocation;
    }

    public String getMdateline() {
        return mdateline;
    }

    public String getCityLocation() {
        return mCityLocation;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setLocation(String location) {
        this.mlocation = location;
    }

    public void setDateline(String dateline) {
        this.mdateline = dateline;
    }

    public String getTaskStatus() {
        return mTaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        mTaskStatus = taskStatus;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }

  /*  public void saveDetailEntity(){
        mEntitySparseArray.append(mID, new DetailEntity(mTaskStatus, mTitle, mDetail, mCategory,money,mdateline,mlocation,imgUrlList, headIconUrl));//保存用户信息到稀疏数组中
    }

    public DetailEntity loadDetailEntity(){
        DetailEntity detailEntity = mEntitySparseArray.get(mID, null);//根据id加载
        return detailEntity;
    }
*/
}
