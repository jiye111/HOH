package com.example.zhang.hoh.ui.activity.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.activity.BaseMVPCompatActivity;
import com.example.sdk.utils.BitmapUtils;
import com.example.sdk.utils.ToastUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.contract.login.SigninContract;
import com.example.zhang.hoh.presenter.login.SigninPresenter;
import com.example.zhang.hoh.ui.activity.MainActivity;
import com.example.zhang.hoh.utils.LocalManageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

//登陆
public class SigninActivity extends BaseMVPCompatActivity<SigninContract.SigninPresenter> implements SigninContract.SigninView {
    @BindView(R.id.login_signin_user_et)
    EditText etUser;
    @BindView(R.id.login_signin_password_et)
    EditText etPassword;
    @BindView(R.id.login_signin_btn)
    Button btnLogin;
    @BindView(R.id.login_signin_text)
    TextView tvSignup;
    @BindView(R.id.login_signin_bg_ll)
    LinearLayout llBg;

    @OnClick(R.id.login_signin_btn)
    public void login() {
        // mPresenter.pSignin();
        this.toMainActivity();
    }

    @OnClick(R.id.login_signin_text)
    public void toSignup() {
        startActivity(SignupActivity.class);
    }

    private String[] permissions=new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private List<String> mPermissionList=new ArrayList<>();
    private final int mRequestCode=100;

    @Override
    protected int getLayoutId() {
        return R.layout.login_signin_activity;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>(1024, 768) {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                llBg.setBackground(resource);
            }
        };

        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResoure(getResources(), R.drawable.login_signin_bg
                , 300, 400);
        Glide.with(this)
                .load(bitmap)
                .into(simpleTarget);

        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            //requestPermission();
            initPermission();
        }

    }

    private void initPermission() {
        mPermissionList.clear();//清空已经允许的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (int i = 0;i<permissions.length;i++){
            if (ContextCompat.checkSelfPermission(this,permissions[i])!=
                    PackageManager.PERMISSION_GRANTED){
                mPermissionList.add(permissions[i]);//添加还未授予的权限到mPermissionList中
            }
        }
        //申请权限
        if (mPermissionList.size()>0){//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this,permissions,mRequestCode);
        }else {
            //权限已经都通过了，可以将程序继续打开了
            ToastUtils.showToast("全部权限申请好了");
        }

    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(SigninActivity.this, Manifest.
                permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this, new String[]{
                    Manifest.permission.INTERNET}, 1);
        }
        if (ContextCompat.checkSelfPermission(SigninActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        if (ContextCompat.checkSelfPermission(SigninActivity.this, Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        }

        if (ContextCompat.checkSelfPermission(SigninActivity.this, Manifest.
                permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this, new String[]{
                    Manifest.permission.ACCESS_NETWORK_STATE}, 3);
        }

        if (ContextCompat.checkSelfPermission(SigninActivity.this, Manifest.
                permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this, new String[]{
                    Manifest.permission.ACCESS_WIFI_STATE}, 3);
        }

        if (ContextCompat.checkSelfPermission(SigninActivity.this, Manifest.
                permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i]==-1){
                        ToastUtils.showToast("你有权限未授予");
                    }
                }
            default:
                break;
        }
    }

    @Override
    protected void ActiveFromBackstage() {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SigninPresenter.newInstance();
    }

    @Override
    public Map<String, String> getParamters() {
        Map<String, String> maps = new HashMap();
        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();
        maps.put("user", user);
        maps.put("password", password);
        return maps;
    }

    @Override
    public void toMainActivity() {
        startActivity(MainActivity.class);
        finish();
    }
}
