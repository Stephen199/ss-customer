package com.yonyou.sh.common.base;

import android.view.View;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:41 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public interface ISwipeRefreshView {

    boolean isEnableRefresh();

    /**
     * 刷新数据
     */
    void onRefreshBegin();

    /**
     * BaseRecyclerActivity中已重写此方法
     * @return
     */
    View getRecyclerContentView();
}
