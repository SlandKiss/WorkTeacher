package com.ubisys.ubisyssafety.ui.common;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ubisys.ubisyssafety.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ubisyssafety.ubisys.baselibray.base.BaseActivity;
import ubisyssafety.ubisys.baselibray.utils.SharedPreferUtils;
import ubisyssafety.ubisys.framelibrary.statusbar.FillSystemUtil;

/**
 * 引导页面
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.viewpager_welcome)
    ViewPager viewpagerWelcome;
    @Bind(R.id.layout_container)
    LinearLayout layoutContainer;

    private List<View> totalList;
    private TypedArray arr;
    private WelcomePagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FillSystemUtil(this).fillSystemBarTM();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        totalList = new ArrayList<View>();
        initViewPager();
    }

    @Override
    protected void initEvent() {

    }

    private void initViewPager() {
        arr = getResources().obtainTypedArray(R.array.arrImageIds);
        for (int i = 0; i < arr.length(); i++) {
            ImageView ima = new ImageView(this);
            ima.setScaleType(ImageView.ScaleType.FIT_XY);
            ima.setImageDrawable(arr.getDrawable(i));
            totalList.add(ima);
        }
        View view_intent = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.layout_lijitiya, null);
        Button bt_intent = (Button) view_intent.findViewById(R.id.button_lijitiyan);
        bt_intent.setOnClickListener(this);
        totalList.add(view_intent);
        adapter = new WelcomePagerAdapter(totalList);
        viewpagerWelcome.setOffscreenPageLimit(2);
        viewpagerWelcome.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.button_lijitiyan:
                startActivity(LoginActivity.class);
                SharedPreferUtils.getInstance().putBoolean(WelcomeActivity.this, "firstcomein", true);
                finish();
                break;
        }
    }
}
