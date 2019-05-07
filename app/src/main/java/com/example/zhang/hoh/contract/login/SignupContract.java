package com.example.zhang.hoh.contract.login;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseModel;
import com.example.sdk.base.IBaseView;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public interface SignupContract {

    abstract class  SignupPresenter extends BasePresenter<SignupModel,SignupView>{
        public abstract void pSignup();
    }

    interface SignupModel extends IBaseModel{
        Flowable<ResponseBody> mlogin(Map<String, String> maps);
    }

    interface  SignupView extends IBaseView{
        Map<String,String> getParamters();
    }

}
