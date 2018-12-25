package com.yonyou.sh.common.base;

import com.yonyou.sh.common.bean.HttpResponse;

/**
 * 作者：邵帅
 * 时间：2018/12/21 2:51 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public interface IBaseView {

    /**
     * 显示loading,默认loading不可强制退出(按返回键无效)
     */
    void showProgress();

    /**
     * 显示loading,默认loading不可强制退出(按返回键无效)
     * @param text
     */
    void showProgress(String text);

    /**
     * 显示loading
     * @param text
     * @param cancle 是否可按返回键退出
     */
    void showProgress(String text, boolean cancle);

    /**
     * 关闭loading
     */
    void dismissProgress();

    /**
     * @param text
     */
    void showToast(String text);

    /**
     * 请求失败时调用
     * @param response
     */
    void showErrorView(HttpResponse response);

    /**
     * 请求成功后调用
     * @param type
     */
    void showEmptyView(int type);
}
