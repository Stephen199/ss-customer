package com.yonyou.sh.customerss.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yonyou.sh.common.base.BaseActivity;
import com.yonyou.sh.common.base.BaseFragment;
import com.yonyou.sh.common.base.IBasePresenter;
import com.yonyou.sh.common.bean.TabEntity;
import com.yonyou.sh.customerss.R;
import com.yonyou.sh.customerss.ui.fragment.HomeFragment;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 作者：邵帅
 * 时间：2018/12/24 11:59 AM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class MainActivity extends BaseActivity {

    private CommonTabLayout commonTab;
    private int[] menuIconNorIds = {R.drawable.ic_main_tab_home_normal, R.drawable.ic_main_tab_mine_nomal};
    private int[] menuIconPreIds = {R.drawable.ic_main_tab_home_press, R.drawable.ic_main_tab_mine_press};
    private HashMap<Integer, BaseFragment> savedFragment = new HashMap<>();

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initParam(Bundle params) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        commonTab = findViewById(R.id.common_tab);
        initBottomTab();
        initFragment();
        switchFragment(0);
    }

    private void initBottomTab() {
        String[] tabTitles = getResources().getStringArray(R.array.main_tab_titles);
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < menuIconNorIds.length; i++) {
            mTabEntities.add(new TabEntity(tabTitles[i], menuIconPreIds[i], menuIconNorIds[i]));
        }
        commonTab.setTabData(mTabEntities);
    }

    @Override
    public int getTitleBarId() {
        return 0;
    }

    @Override
    public void onViewClick(View v) {

    }

    @Override
    public void initListener() {
        commonTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_content, getFragment(0));
        transaction.add(R.id.fl_content, getFragment(1));
        transaction.commitAllowingStateLoss();
    }

    private BaseFragment getFragment(int position) {
        BaseFragment fragment = savedFragment.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new HomeFragment();
                    break;
            }
            savedFragment.put(position, fragment);
        }
        return fragment;
    }

    private void switchFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(getFragment(0));
        transaction.hide(getFragment(1));
        Fragment fragment = getFragment(position);
        if (fragment != null) {
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void doBusiness() {

    }
}
