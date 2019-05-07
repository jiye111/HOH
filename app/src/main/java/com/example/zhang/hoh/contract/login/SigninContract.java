package com.example.zhang.hoh.contract.login;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseModel;
import com.example.sdk.base.IBaseView;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public interface SigninContract {

    abstract class  SigninPresenter extends BasePresenter<SigninModel,SigninView>{
        public abstract void pSignin();
    }

    interface SigninModel extends IBaseModel{
        Flowable<ResponseBody> mlogin(Map<String, String> maps);
    }

    interface  SigninView extends IBaseView{
        Map<String,String> getParamters();
        void toMainActivity();
    }

}
