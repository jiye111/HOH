package com.example.zhang.hoh.presenter.login;

import android.support.annotation.NonNull;

import com.example.sdk.net.WanAndroidResponseBean;
import com.example.sdk.utils.LogUtils;
import com.example.zhang.hoh.contract.login.SigninContract;
import com.example.zhang.hoh.contract.login.SignupContract;
import com.example.zhang.hoh.model.login.SigninModel;
import com.example.zhang.hoh.model.login.SignupModel;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class SigninPresenter extends SigninContract.SigninPresenter {

    @NonNull
    public static SigninPresenter newInstance() {
        return new SigninPresenter();
    }

    @Override
    public SigninContract.SigninModel getModel() {
        return SigninModel.newInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void pSignin() {
        if (mIModel == null || mIView == null) {
            return;
        }
        mRxManager.register(mIModel.mlogin(mIView.getParamters())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) throws Exception {
                                   WanAndroidResponseBean responseBean = new WanAndroidResponseBean();
                                   try {
                                       String str = new String(responseBody.bytes());
                                       JSONObject jo = new JSONObject(str);
                                       if (jo.has("data")) {
                                           responseBean.setData(jo.getString("data"));
                                       }
                                       if (jo.has("errorCode")) {
                                           responseBean.setErrorCode(jo.getString("errorCode"));
                                       }
                                       if (jo.has("errorMsg")) {
                                           responseBean.setErrorMsg(jo.getString("errorMsg"));
                                       }
                                       LogUtils.d("SignupPresenter", str);
                                       LogUtils.d("SignupPresenter", responseBean.toString());


                                   } catch (Exception e) {
                                       e.printStackTrace();
                                   }
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   throwable.printStackTrace();
                               }
                           }
                ));
    }
}
