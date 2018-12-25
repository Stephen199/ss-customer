package com.yonyou.sh.customerss.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yonyou.module.carnet.ui.activity.MapActivity;
import com.yonyou.module.carnet.util.TextUtil;
import com.yonyou.sh.common.base.BaseFragment;
import com.yonyou.sh.common.base.IBasePresenter;
import com.yonyou.sh.common.constant.RouterPath;
import com.yonyou.sh.customerss.R;

/**
 * 作者：邵帅
 * 时间：2018/12/24 11:58 AM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class HomeFragment extends BaseFragment {

    private Button btn;
    private Button btnMap;
    private String loaction = "121.219121,31.361401";

    @Override
    public void initParam(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        btn = view.findViewById(R.id.btn);
        btnMap = view.findViewById(R.id.btn_map);
    }

    @Override
    public void onViewClick(View v) {
        int id = v.getId();
        if (id == R.id.btn) {
            ARouter.getInstance().build(RouterPath.MAIN_ACTIVITY_CARCHANGE)
                    .withString("name", "888")
                    .withInt("age", 11)
                    .navigation();
        } else if (id == R.id.btn_map) {
            startActivity(new Intent(mContext, MapActivity.class).putExtra("location", loaction));
        }
    }

    @Override
    public void initListener() {
        btn.setOnClickListener(this);
        btnMap.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }
}
