package com.yonyou.module.carnet.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonyou.module.carnet.R;
import com.yonyou.sh.common.base.BaseActivity;
import com.yonyou.sh.common.base.IBasePresenter;
import com.yonyou.sh.common.constant.RouterPath;
import com.yonyou.sh.common.widget.ShareDialog;

/**
 * 作者：邵帅
 * 时间：2018/11/20 5:54 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：车辆切换
 */
@Route(path = RouterPath.MAIN_ACTIVITY_CARCHANGE)
public class CarChangeActivity extends BaseActivity {

    private TextView tvName;
    private TextView tvAge;
    private Button button;
    private String name;
    private int age;
    private ShareDialog shareDialog;


    private void initData() {
        initTitleBar(getString(R.string.title_car_change));
        tvRight.setText("确认");
        tvName.setText(name);
        tvAge.setText(age + "");
    }

    @Override
    public void initParam(Bundle params) {
        name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 2);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_car_change;
    }

    @Override
    public void initView(View view) {
        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        button = findViewById(R.id.button);
        initData();
    }

    @Override
    public void onViewClick(View v) {
        int id = v.getId();
        if (id == R.id.button) {
            showShare();
        }
    }

    private void showShare() {
        shareDialog = new ShareDialog(this);
        shareDialog.setData("https://www.baidu.com/", "百度分享", "我在百度分享了一些东西", "http://d.hiphotos.baidu.com/image/pic/item/caef76094b36acaf6d1e855571d98d1000e99c98.jpg");
        shareDialog.show();
    }

    @Override
    public void initListener() {
        tvRight.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }
}
