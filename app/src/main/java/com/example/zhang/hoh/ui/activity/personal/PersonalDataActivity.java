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

public class PersonalDataActivity extends BaseCompatActivity {

    @BindView(R.id.personal_data_header_iv)
    ImageView header;
    @BindView(R.id.personal_data_back_btn)
    ImageButton ibBack;

    @OnClick(R.id.personal_data_back_btn)
    void Finish(){
        finish();
    }


    @Override
    public int getLayoutId() {
        return R.layout.personal_data_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this).load(R.drawable.personal_head).apply(requestOptions).into(header);
    }

    @Override
    protected void ActiveFromBackstage() {

    }


}
