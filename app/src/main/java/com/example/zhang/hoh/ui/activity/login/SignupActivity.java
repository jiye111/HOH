package com.example.zhang.hoh.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.activity.BaseMVPCompatActivity;
import com.example.sdk.utils.BitmapUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.contract.login.SignupContract;
import com.example.zhang.hoh.presenter.login.SignupPresenter;
import com.example.zhang.hoh.utils.LocalManageUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SignupActivity extends BaseMVPCompatActivity<SignupContract.SignupPresenter> implements SignupContract.SignupView {
    @BindView(R.id.login_signup_user_et)
    EditText etUser;
    @BindView(R.id.login_signup_password_et)
    EditText etPassword;
    @BindView(R.id.login_signup_repassword_et)
    EditText etRepassword;
    @BindView(R.id.login_signup_btn)
    Button btnLogin;
    @BindView(R.id.login_signup_bg_ll)
    LinearLayout llBg;

    @OnClick(R.id.login_signup_btn)
    public void login() {
       mPresenter.pSignup();
    }

    @OnClick(R.id.login_signup_back_iv)
    public void backToSignin(){
        finish();
    }

    @OnClick(R.id.login_signup_change_btn)
    public void changeLanguage(){
        String currentLanguage=LocalManageUtil.getSelectLanguage(this);
        int selecLanguage=currentLanguage.equals("ENGLISH")?0:1;
        LocalManageUtil.saveSelectLanguage(this, selecLanguage);
        reStart(this);
    }

    public static void reStart(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //初始化布局
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.login_signup_activity;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SignupPresenter.newInstance();
    }


    @Override
    public Map<String, String> getParamters() {
        Map<String, String> maps = new HashMap();
        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();
        String repassword = etRepassword.getText().toString();
        maps.put("user", user);
        maps.put("password", password);
        maps.put("repassword", repassword);
        return maps;
    }


}
