package com.example.administrator.myvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.myvideo.bean.TabEntity;
import com.example.administrator.myvideo.fragment.CareMainFragment;
import com.example.administrator.myvideo.fragment.RainMainFragment;
import com.example.administrator.myvideo.util.AppConstant;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CommonTabLayout tabLayout;

    private String[] mTitles = { "雨滴","直播" };
    private int[] mIconUnselectIds = { R.mipmap.ic_home_normal,
            R.mipmap.ic_care_normal };
    private int[] mIconSelectIds = { R.mipmap.ic_home_selected,

            R.mipmap.ic_care_selected };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private RainMainFragment newsMainFragment;
    private CareMainFragment careMainFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tabLayout = (CommonTabLayout) findViewById(R.id.tab_layout);

        initTab();
        initFragment(savedInstanceState);

    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i],
                    mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        // 点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            newsMainFragment = (RainMainFragment) getSupportFragmentManager()
                    .findFragmentByTag("newsMainFragment");
            careMainFragment = (CareMainFragment) getSupportFragmentManager()
                    .findFragmentByTag("careMainFragment");

            currentTabPosition = savedInstanceState
                    .getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            newsMainFragment = new RainMainFragment();
            careMainFragment = new CareMainFragment();

            transaction.add(R.id.fl_body, newsMainFragment, "newsMainFragment");
            transaction.add(R.id.fl_body, careMainFragment, "careMainFragment");
        }
        transaction.commit();
        switchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void switchTo(int position) {
        Log.e("切换选项卡", "主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        switch (position) {
            // 首页
            case 0:
                transaction.hide(careMainFragment);
                transaction.show(newsMainFragment);
                transaction.commitAllowingStateLoss();
                break;

            // 关注
            case 1:
                transaction.hide(newsMainFragment);
                transaction.show(careMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 崩溃前保存位置
        if (tabLayout != null) {
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION,
                    tabLayout.getCurrentTab());
        }
    }

}
