package com.yonyou.sh.common.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yonyou.sh.common.R;
import com.yonyou.sh.common.views.VerticalSwipeRefreshLayout;

/**
 * 作者：邵帅
 * 时间：2018/12/21 3:42 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public abstract class BaseRefreshFragment<P extends IBasePresenter> extends BaseFragment<P> implements ISwipeRefreshView, SwipeRefreshLayout.OnRefreshListener {

    protected VerticalSwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefreshing;//是否正在刷新

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        swipeRefreshLayout = new VerticalSwipeRefreshLayout(mContext);
        swipeRefreshLayout.setColorSchemeResources(R.color.common_color_blue, R.color.common_color_dark_blue);

        if (isEnableRefresh()) {
            swipeRefreshLayout.setOnRefreshListener(this);
        } else {
            swipeRefreshLayout.setEnabled(false);
            swipeRefreshLayout.setRefreshing(false);
        }

        if (bindLayout() == 0 && getRecyclerContentView() == null) {
            throw new IllegalArgumentException("content view is null");
        }

        View view = getRecyclerContentView() == null ? inflater.inflate(bindLayout(), null)
                : getRecyclerContentView();

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipeRefreshLayout.addView(view, params);
        return swipeRefreshLayout;
    }

    /**
     * 是否可以刷新，默认可以刷新
     * 如果不希望刷新，请复写返回false
     *
     * @return
     */
    @Override
    public boolean isEnableRefresh() {
        return true;
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            onRefreshBegin();
        }
    }

    protected void setRefreshComplete() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        isRefreshing = false;
    }

    @Override
    public View getRecyclerContentView() {
        return null;
    }
}
