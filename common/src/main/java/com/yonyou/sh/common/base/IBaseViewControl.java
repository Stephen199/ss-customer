package com.yonyou.sh.common.base;

import android.os.Bundle;
import android.view.View;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:43 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public interface IBaseViewControl {
    /**
     * [初始化参数]
     *
     * @param params
     */
    void initParam(Bundle params);

    /**
     * [绑定布局]
     *
     * @return
     */
    int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    void initView(final View view);

    /**
     * View点击
     **/
    void onViewClick(View v);

    /**
     * 设置点击事件监听
     */
    void initListener();

    /**
     * [业务操作]
     */
    void doBusiness();
}
