package com.yonyou.module.carnet.bean;
/**
 * 作者：shaoshuai
 * 时间：2018/11/20 4:33 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：指示灯bean
 */
public class IndicatorLight {
    private String title;
    private int icon;
    private String statue;
    private String lightenReason;
    private String extinguishWay;

    public IndicatorLight(String title, int icon, String statue, String lightenReason, String extinguishWay) {
        this.title = title;
        this.icon = icon;
        this.statue = statue;
        this.lightenReason = lightenReason;
        this.extinguishWay = extinguishWay;
    }

    public IndicatorLight(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getLightenReason() {
        return lightenReason;
    }

    public void setLightenReason(String lightenReason) {
        this.lightenReason = lightenReason;
    }

    public String getExtinguishWay() {
        return extinguishWay;
    }

    public void setExtinguishWay(String extinguishWay) {
        this.extinguishWay = extinguishWay;
    }
}
