package com.ubisys.ubisyssafety.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ubisys.ubisyssafety.ui.common.MainActivity;
import com.ubisys.ubisyssafety.R;
import com.ubisys.ubisyssafety.ui.common.LoginActivity;
import com.ubisys.ubisyssafety.ui.common.WelcomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import ubisyssafety.ubisys.baselibray.utils.SharedPreferUtils;
import ubisyssafety.ubisys.framelibrary.framebase.FrameBaseActivity;

public class SplashActivity extends FrameBaseActivity {

    @Bind(R.id.iv_splash_logo)
    ImageView ivSplashLogo;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.splash_root)
    RelativeLayout splashRoot;

    private String errorLog;
    private static final int sleepTime = 2000;
    private boolean isFirst;
    private AlphaAnimation animation;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
    @Override
    protected void initView() {
        errorLog = SharedPreferUtils.getInstance().get(SplashActivity.this, "crashError");
        if (!TextUtils.isEmpty(errorLog)) {
            postError(errorLog);
        }
        tvVersion.setText(getVersion());
        animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        splashRoot.startAnimation(animation);
        isFirst = SharedPreferUtils.getInstance().getBoolean(SplashActivity.this, "firstcomein", false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                获取字典表
                getDictionary();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                token = SharedPreferUtils.getInstance().get(SplashActivity.this, "usertoken");
                if (!isFirst) {
                    startThenKill(WelcomeActivity.class);
                }else {
                    if (!TextUtils.isEmpty(token.trim())) {
//                     上次登录过  这次处理一些登录前要获取的信息

                        startThenKill(MainActivity.class);
                    }else {
                        startThenKill(LoginActivity.class);
                    }
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    //  向服务器获取字典表的接口
    private void getDictionary() {


    }

    //  向服务器发送错误的日志信息
    private void postError(String errorLog) {

    }

    /**
     * 获取应用的版本信息
     * @return
     */
    private String getVersion() {
        PackageManager pm = getPackageManager();// 获取包管理器
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);// 获取当前应用信息
            String versionCode = packageInfo.versionName;// 版本号
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有找到包名的异常
            e.printStackTrace();
        }
        return null;
    }
}
