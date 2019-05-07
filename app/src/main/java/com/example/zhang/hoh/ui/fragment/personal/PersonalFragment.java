package com.example.zhang.hoh.ui.fragment.personal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.contract.personal.PersonalContract;
import com.example.zhang.hoh.presenter.personal.PersonalPresenter;
import com.example.zhang.hoh.ui.activity.personal.PersonalDataActivity;
import com.example.zhang.hoh.ui.activity.personal.PersonalSettingActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalFragment extends BaseMVPCompatFragment<PersonalContract.PersonalPresenter> implements PersonalContract.PersonalView {

    @BindView(R.id.personal_main_userImg_iv)
    ImageView header;

    @OnClick(R.id.personal_main_userImg_iv)
    void toPersonalData(){
        startActivity(new Intent(getActivity(),PersonalDataActivity.class));
    }
    @OnClick(R.id.personal_main_setting_btn)
    void toPersonalSetting(){
        startActivity(new Intent(getActivity(),PersonalSettingActivity.class));
    }

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return PersonalPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this).load(R.drawable.personal_head).apply(requestOptions).into(header);
    }
}
