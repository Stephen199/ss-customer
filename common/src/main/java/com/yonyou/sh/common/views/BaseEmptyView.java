package com.yonyou.sh.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.sh.common.R;

/**
 * 作者：邵帅
 * 时间：2018/12/21 3:14 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class BaseEmptyView extends RelativeLayout {

    public final static int LAYOUT_HIDE = 0;//隐藏view
    public final static int LAYOUT_EMPTY = 1;//数据空
    public final static int LAYOUT_FAILED = 2;//加载失败
    public final static int LAYOUT_LOAD = 3;//正在加载
    public final static int LAYOUT_NET_ERROR = 4;//网络异常
    public final static int LAYOUT_NET_TIMEOUT = 5;//网络超时
    private ProgressBar progressBar;
    private int currentType;
    private LinearLayout llEmpty;
    private ImageView ivEmpty;
    private TextView tvEmpty;
    private TextView tvReload;

    public BaseEmptyView(Context context) {
        this(context, null);
    }

    public BaseEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_base_empty_view, this);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        llEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        tvReload = (TextView) view.findViewById(R.id.tv_reload);

        tvReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadListener != null) {
                    onReloadListener.onReload();
                }
            }
        });
    }

    public void setEmptyType(int type){
        if (currentType == type) {
            return;
        }

        switch (type) {
            case LAYOUT_HIDE:
                currentType = LAYOUT_HIDE;
                setVisibility(GONE);
                break;
            case LAYOUT_LOAD:
                currentType = LAYOUT_LOAD;
                setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                llEmpty.setVisibility(GONE);
                break;
            case LAYOUT_EMPTY:
                currentType = LAYOUT_EMPTY;
                setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                tvReload.setVisibility(GONE);
                llEmpty.setVisibility(VISIBLE);
                ivEmpty.setImageResource(R.drawable.img_empty);
                tvEmpty.setText(getResources().getString(R.string.empty_text));
                break;
            case LAYOUT_NET_ERROR:
                currentType = LAYOUT_NET_ERROR;
                setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                tvReload.setVisibility(GONE);
                llEmpty.setVisibility(VISIBLE);
                ivEmpty.setImageResource(R.drawable.img_net_error);
                tvEmpty.setText(getResources().getString(R.string.net_error));
                break;
            case LAYOUT_NET_TIMEOUT:
                currentType = LAYOUT_NET_TIMEOUT;
                setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                llEmpty.setVisibility(VISIBLE);
                tvReload.setVisibility(VISIBLE);
                ivEmpty.setImageResource(R.drawable.img_net_error);
                tvEmpty.setText(getResources().getString(R.string.net_timeout));
                break;
        }
    }

    private OnReloadListener onReloadListener;

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface OnReloadListener {
        void onReload();
    }
}
