package com.example.zhang.hoh.model.login;

import android.support.annotation.NonNull;

import com.example.sdk.net.RetrofitClient;
import com.example.sdk.utils.AppUtils;
import com.example.zhang.hoh.contract.login.SigninContract;
import com.example.zhang.hoh.contract.login.SignupContract;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public class SigninModel implements SigninContract.SigninModel {

    @NonNull
    public static SigninModel newInstance(){ return  new SigninModel();}

    @Override
    public Flowable<ResponseBody> mlogin(Map<String, String> maps) {
        return RetrofitClient.getInstance(AppUtils.getContext())
                .createBaseApi()
                .getRegisterData(maps);
    }
}
