package com.example.zhang.hoh.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.activity.BaseMVPCompatActivity;
import com.example.sdk.utils.BitmapUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.contract.login.SigninContract;
import com.example.zhang.hoh.presenter.login.SigninPresenter;
import com.example.zhang.hoh.ui.activity.MainActivity;
import com.example.zhang.hoh.utils.LocalManageUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

//登陆
public class SigninActivity  extends BaseMVPCompatActivity<SigninContract.SigninPresenter> implements SigninContract.SigninView  {
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
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>(1024,768) {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                llBg.setBackground(resource);
            }
        };

        Bitmap bitmap= BitmapUtils.decodeSampledBitmapFromResoure(getResources(),R.drawable.login_signin_bg
                ,300,400);


        Glide.with(this)
              .load(bitmap)
              .into(simpleTarget);
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
