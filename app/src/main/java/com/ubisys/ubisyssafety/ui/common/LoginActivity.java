package com.ubisys.ubisyssafety.ui.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ubisys.ubisyssafety.R;
import com.ubisys.ubisyssafety.constant.BaseParams;
import com.ubisys.ubisyssafety.constant.Constant;
import com.ubisys.ubisyssafety.utils.CommonUtils;
import com.ubisys.ubisyssafety.utils.NetUtils;
import com.ubisys.ubisyssafety.utils.PermissionsManager;
import com.ubisys.ubisyssafety.utils.PermissionsResultAction;
import com.ubisys.ubisyssafety.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ubisyssafety.ubisys.baselibray.http.EngineCallBack;
import ubisyssafety.ubisys.baselibray.http.HttpUtils;
import ubisyssafety.ubisys.baselibray.utils.SharedPreferUtils;
import ubisyssafety.ubisys.framelibrary.framebase.FrameBaseActivity;

/**
 * 登陆页面
 */
public class LoginActivity extends FrameBaseActivity {


    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.et_login_name)
    EditText etLoginName;
    @Bind(R.id.iv2)
    ImageView iv2;
    @Bind(R.id.et_login_pwd)
    EditText etLoginPwd;
    @Bind(R.id.iv_login)
    ImageView ivLogin;
    @Bind(R.id.bt_find_pwd)
    Button btFindPwd;
    private String currentUsername, currentPassword;
    private String imei;
    private String msgs;
    protected String token;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        requestAllPermission();
        etLoginName.setText(SharedPreferUtils.getInstance().getString(this, "userphone", ""));
    }

    private void requestAllPermission() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(String permission) {

            }
        });
    }

    @Override
    protected void initEvent() {
        etLoginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etLoginPwd.setText(null);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 请求服务器拿到用户信息
     */
    private void doLogin() {
        if (!NetUtils.isNetWorkConnected(LoginActivity.this)) {
            ToastUtils.showToast(LoginActivity.this, "当前网络不可用");
            return;
        }
        currentUsername = etLoginName.getText().toString().trim();
        currentPassword = etLoginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(currentUsername)) {
            ToastUtils.showToast(LoginActivity.this, "请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            ToastUtils.showToast(LoginActivity.this, "请输入密码！");
            return;
        }
        startProgressDialog();
        HttpUtils.with(LoginActivity.this)
                .url(Constant.LOGIN_URL)
                .addParam("mobile", currentUsername)
                .addParam("password", currentPassword)
                .addParam("devicetype", BaseParams.Devicetype)
                .addParam("imei", getImei())
                .cache(false)
                .post()
                .execute(new EngineCallBack() {
                    @Override
                    public void onPreExecute(Context context, Map<String, Object> params) {
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                    @Override
                    public void onSuccess(String result)  {
                        JSONObject js= null;
                        try {
                            js = new JSONObject(result);
                            if (js.getString("status").equals("1")){
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "userphone",
                                        currentUsername );
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "usertoken",
                                        js.getString("token"));
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "userid",
                                        js.getString("userid"));
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "userName",
                                        js.getString("username"));
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "picPath",
                                        js.getString("picpath"));
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "isHeadteacher",
                                        js.getString("state"));
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "schoolList",
                                        js.getJSONArray("sclist").toString());
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "classidlist",
                                        js.getJSONArray("classidlist").toString());
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "classidall",
                                        js.getJSONArray("classidall").toString());
                                SharedPreferUtils.getInstance().put(getApplicationContext(), "subjectlist",
                                        js.getJSONArray("subjectlist").toString());
                                mHandler.sendEmptyMessage(1);
                            }else {
                                msgs=js.getString("msg");
                                mHandler.sendEmptyMessage(2);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 获取手机的mini号
     * @return
     */
    private String getImei() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    5);
        } else {
            imei = CommonUtils.getIMEI(LoginActivity.this);
        }
        return imei;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImei();
            } else {
                Toast.makeText(LoginActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.iv_login, R.id.bt_find_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login:
                doLogin();
                break;
            case R.id.bt_find_pwd:
                break;
        }
    }

    private void getContacts() {
        token = SharedPreferUtils.getInstance().get(LoginActivity.this, "usertoken");
        String time = Long.toString(new Date().getTime());
        HttpUtils.with(LoginActivity.this).url(Constant.CONTACT_URL)
                .post()
                .addParam("token", token)
                .addParam("timestamp", time)
                .cache(true)
                .execute(new EngineCallBack() {
            @Override
            public void onPreExecute(Context context, Map<String, Object> params) {

            }
            @Override
            public void onError(Exception e) {
                stopProgressDialog();
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onSuccess(String result) {
                stopProgressDialog();
                Log.e("sss","result->"+result);
//                 存储通讯录
                mHandler.sendEmptyMessage(3);
            }
        });
    }
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    getContacts();
                    break;
                case 2:
                    stopProgressDialog();
                    ToastUtils.showToast(LoginActivity.this,msgs);
                    break;
                case 3:
                    startActivity(MainActivity.class);
                    break;
            }
        }
    };

}
