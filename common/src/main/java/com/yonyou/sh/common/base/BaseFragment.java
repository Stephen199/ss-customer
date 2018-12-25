package com.yonyou.sh.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.yonyou.sh.common.bean.HttpResponse;
import com.yonyou.sh.common.views.BaseEmptyView;
import java.util.Calendar;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:44 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public abstract class BaseFragment<P extends IBasePresenter> extends BasePresenterFragment<P> implements View.OnClickListener, IBaseViewControl{
    public Context mContext;
    public Fragment fragment;
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    public String mParam1;
    public String mParam2;

    /**
     * 当前Fragment渲染的视图View
     **/
    protected View mContextView;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();
    protected FrameLayout flContent;
    private BaseEmptyView baseEmptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        Bundle bundle = getArguments();
        if (null != bundle) {
            initParam(bundle);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flContent = new FrameLayout(mContext);
        flContent.addView(getContentView(inflater, container));
        initEmptyView();
        return flContent;
    }

    protected View getContentView(LayoutInflater inflater, ViewGroup container){
        View mView = bindView();
        if (null == mView) {
            if (bindLayout() == 0) {
                throw new IllegalArgumentException("Layout resource id is invalid");
            }
            mContextView = inflater.inflate(bindLayout(), container, false);
        } else {
            mContextView = mView;
        }
        return mContextView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragment = this;

        initView(view);
        initListener();
        doBusiness();
    }

    public boolean isShowEmptyView(){
        return false;
    }

    protected void initEmptyView() {
        if (isShowEmptyView()) {
            baseEmptyView = new BaseEmptyView(mContext);
            baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_LOAD);
            if (flContent != null) {
                flContent.addView(baseEmptyView);
            }

            baseEmptyView.setOnReloadListener(new BaseEmptyView.OnReloadListener() {
                @Override
                public void onReload() {
                    baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_LOAD);
                    doBusiness();
                }
            });
        }
    }

    @Override
    public void showEmptyView(int type) {
        if (baseEmptyView == null)
            return;

        switch (type) {
            case BaseEmptyView.LAYOUT_LOAD:
                baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_LOAD);
                break;
            case BaseEmptyView.LAYOUT_EMPTY:
                baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_EMPTY);
                break;
            case BaseEmptyView.LAYOUT_NET_ERROR:
                baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_NET_ERROR);
                break;
            case BaseEmptyView.LAYOUT_HIDE:
                if (flContent.getChildCount() > 1) {
                    baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_HIDE);
                }
                break;
            default:
                if (flContent.getChildCount() > 1) {
                    baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_HIDE);
                }
                break;
        }
    }

    @Override
    public void showErrorView(HttpResponse response) {
        if (response == null || baseEmptyView == null) {
            return;
        }

        switch (response.getResultCode()) {
            case HttpResponse.NETWORK_ERROR:
                baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_NET_ERROR);
                break;
            case HttpResponse.NETWORK_TIME_OUT:
                baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_NET_TIMEOUT);
                break;
            default:
                if (flContent.getChildCount() > 1) {
                    baseEmptyView.setEmptyType(BaseEmptyView.LAYOUT_HIDE);
                }
                break;
        }
    }

    /**
     * [绑定视图]
     * @return
     */
    public View bindView(){
        return null;
    }

    /**
     * [绑定控件]
     * @param resId
     */
    protected <T extends View> T $(int resId) {
        if (null != mContextView) {
            return (T) mContextView.findViewById(resId);
        }
        return null;
    }

    public static final int MIN_CLICK_DELAY_TIME = 1000;

    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onViewClick(v);
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mContext, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * [简化Toast]
     */
    /*public void showToast(String msg) {
        Snackbar.make(mContextView, msg, Snackbar.LENGTH_SHORT).show();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            if (isVisibleToUser) {
                //HMTAgent.onResume(getContext(), getPageId());
            } else {
                //HMTAgent.onPause(getContext(), getPageId());
            }
        }
    }
}
