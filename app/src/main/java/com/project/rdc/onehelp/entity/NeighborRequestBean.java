package com.project.rdc.onehelp.entity;

import java.util.List;

/**
 * Created by PC on 2016/11/11.
 */

public class NeighborRequestBean {
    private String uerIcon;
    private String uerSex;
    private String uerName;
    private String publishPlace;
    private String publishType;
    private String publishTitle;
    private String publishTime;
    private String publishMoney;
    private List<String> publishPic;

    public String getPublishMoney() {
        return publishMoney;
    }

    public void setPublishMoney(String publishMoney) {
        this.publishMoney = publishMoney;
    }

    public List<String> getPublishPic() {
        return publishPic;
    }

    public void setPublishPic(List<String> publishPic) {
        this.publishPic = publishPic;
    }

    public void setUerIcon(String uerIcon) {
        this.uerIcon = uerIcon;
    }

    public void setUerSex(String uerSex) {
        this.uerSex = uerSex;
    }

    public void setUerName(String uerName) {
        this.uerName = uerName;
    }

    public void setPublishPlace(String publishPlace) {
        this.publishPlace = publishPlace;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public void setPublishTitle(String publishTitle) {
        this.publishTitle = publishTitle;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setMoney(String money) {
        this.publishMoney = money;
    }

    public String getUerIcon() {
        return uerIcon;
    }

    public String getUerSex() {
        return uerSex;
    }

    public String getUerName() {
        return uerName;
    }

    public String getPublishPlace() {
        return publishPlace;
    }

    public String getPublishType() {
        return publishType;
    }

    public String getPublishTitle() {
        return publishTitle;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getMoney() {
        return publishMoney;
    }
}
