package com.yonyou.module.carnet.bean;

import java.io.Serializable;
/**
 * 作者：shaoshuai
 * 时间：2018/11/15 3:55 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class ControlCarMenu implements Serializable {
    private String title;
    private int icon;
    private int selected;

    public ControlCarMenu(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public ControlCarMenu(String title, int icon, int selected) {
        this.title = title;
        this.icon = icon;
        this.selected = selected;
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

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
