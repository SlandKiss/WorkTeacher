package ubisyssafety.ubisys.baselibray.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import ubisyssafety.ubisys.baselibray.R;
import ubisyssafety.ubisys.baselibray.dialog.DialogTools;
import ubisyssafety.ubisys.baselibray.utils.AppManager;
import ubisyssafety.ubisys.baselibray.utils.ToastUtil;

/**
 * Created by Administrator on 2017/7/7.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Auto-generated method stub

        AppManager.instance.addActivity(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        this.initView();
        this.initEvent();
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当模式为singleTop和SingleInstance会回调到这里
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.instance.removeActivity(this);
        ButterKnife.unbind(this);
        BaseApplication.getRefWatcher(this).watch(this);
    }
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     */
    protected void startThenKill(Class<?> clazz) {
        startThenKill(clazz, null);
    }
    /**
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void startThenKill(Class<?> clazz, Bundle bundle) {
        startActivity(clazz, bundle);
        finish();
    }

    /**
     * 开启加载效果
     */
    public void startProgressDialog() {
        DialogTools.showWaittingDialog(this);
    }

    /**
     * 关闭加载
     */
    public void stopProgressDialog() {
        DialogTools.closeWaittingDialog();
    }

    /**
     * 显示错误的dialog
     */
    public void showErrorHint(String errorContent) {
        View errorView = LayoutInflater.from(this).inflate(R.layout.app_error_tip, null);
        TextView tvContent = (TextView) errorView.findViewById(R.id.content);
        tvContent.setText(errorContent);
        new ToastUtil(errorView);
    }

    /**
     * 显示普通的toast
     *
     * @return
     */
    public void showToast(String str) {
        ToastUtil.sToastUtil.shortDuration(str).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
    }

    //获取布局
    protected abstract int getLayoutId();

    //初始化布局和监听
    protected abstract void initView();


    protected abstract void initEvent();

}
