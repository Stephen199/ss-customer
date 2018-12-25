package com.yonyou.sh.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.sh.common.R;
import com.yonyou.sh.common.bean.HttpResponse;
import com.yonyou.sh.common.utils.AppManager;
import com.yonyou.sh.common.utils.UIUtils;
import com.yonyou.sh.common.views.BaseEmptyView;

import java.util.Calendar;
/**
 * 作者：邵帅
 * 时间：2018/12/21 2:48 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public abstract class BaseActivity<P extends IBasePresenter> extends BasePresenterActivity<P> implements View.OnClickListener, IBaseViewControl {
    public Context mContext;
    public Activity activity;
    /**
     * 是否随系统字体改变
     **/
    private boolean isResourceSystem = false;
    /**
     * 当前Activity渲染的视图View
     **/
    public View mContextView = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private AppManager appManager;
    protected LayoutInflater layoutInflater;
    private FrameLayout flContent;
    private ViewGroup.LayoutParams layoutParams;
    private BaseEmptyView baseEmptyView;
    private RelativeLayout rlTitleBar;
    private TextView tvTitle;
    public TextView tvRight;
    private ImageView ivClose;
    private View titleBarView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        activity = this;
        layoutInflater = LayoutInflater.from(this);
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        appManager = AppManager.getInstance();
        appManager.addActivity(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initParam(bundle);
        }

        setContentView();
        initView(mContextView);
        initListener();
        doBusiness();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (!isResourceSystem) {
            Configuration config = new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return res;
    }

    public boolean isCustomView(){
        return false;
    }

    public boolean isShowEmptyView(){
        return false;
    }

    public void setContentView() {
        View mView = bindView();
        if (null == mView) {
            if (bindLayout() == 0) {
                throw new IllegalArgumentException("Layout resource id is invalid");
            }
            mContextView = layoutInflater.inflate(bindLayout(), null);
        } else {
            mContextView = mView;
        }

        if (isCustomView()) {
            super.setContentView(mContextView);
        }else {
            setContentView(mContextView);
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(R.layout.activity_base);
        initBaseView(view);
        initEmptyView();
    }

    private void initBaseView(View view) {
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        rlTitleBar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        if (getTitleBarId() != 0) {
            titleBarView = layoutInflater.inflate(getTitleBarId(), null);
        }
        if (titleBarView != null && getTitleBarId() == R.layout.layout_base_titlebar) {
            ivClose = (ImageView) titleBarView.findViewById(R.id.iv_close);
            tvTitle = (TextView) titleBarView.findViewById(R.id.tv_title);
            tvRight = (TextView) titleBarView.findViewById(R.id.tv_right);
        }

        if (titleBarView != null) {
            rlTitleBar.setVisibility(View.VISIBLE);
            rlTitleBar.addView(titleBarView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2px(mContext, 60)));
            UIUtils.setStatusBarColor(this, getResources().getColor(R.color.base_title_bar_color));
            UIUtils.setStatusBarFontColor(this, false);
        }else {
            rlTitleBar.setVisibility(View.GONE);
        }

        if (view != null) {
            flContent.addView(view, layoutParams);
        }
    }

    private void initEmptyView() {
        if (isShowEmptyView()) {
            baseEmptyView = new BaseEmptyView(this);
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

    public int getTitleBarId(){
        return R.layout.layout_base_titlebar;
    }

    /**
     * 初始化标题
     * @param title
     */
    public void initTitleBar(String title){
        if (!TextUtils.isEmpty(title) && tvTitle != null) {
            tvTitle.setText(title);
        }

        if (ivClose != null) {
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * @param title
     * @param rightText 右边标题
     * @param listener
     */
    public void initTitleBar(String title, String rightText, final OnRightClickListener listener){
        initTitleBar(title);

        if (!TextUtils.isEmpty(title) && tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(rightText);

            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onRightClick(v);
                    }
                }
            });
        }
    }

    /**
     * 当右上角是图标时调用这个方法
     * @param title
     * @param resourceDrawable 图片资源
     * @param listener
     */
    public void initTitleBar(String title, Drawable resourceDrawable, final OnRightClickListener listener) {
        initTitleBar(title, "", listener);

        if (resourceDrawable != null && tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            resourceDrawable.setBounds(0, 0, resourceDrawable.getMinimumWidth(),
                    resourceDrawable.getMinimumHeight());
            tvRight.setCompoundDrawables(null, null, resourceDrawable, null);
        }
    }

    //右上角按钮的点击事件
    public interface OnRightClickListener {
        void onRightClick(View view);
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
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

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
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
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
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appManager.removeActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK != resultCode) {
            return;
        }
    }
}
