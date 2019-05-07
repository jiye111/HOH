package com.example.zhang.hoh.ui.activity.personal;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.base.activity.BaseCompatActivity;
import com.example.zhang.hoh.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalSettingActivity extends BaseCompatActivity {

    @OnClick(R.id.personal_setting_back_btn)
    void Finish(){
        finish();
    }


    @Override
    public int getLayoutId() {
        return R.layout.personal_setting_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void ActiveFromBackstage() {

    }


}
