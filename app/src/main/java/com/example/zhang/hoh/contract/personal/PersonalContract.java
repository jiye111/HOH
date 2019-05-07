package com.example.zhang.hoh.contract.personal;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseFragment;
import com.example.sdk.base.IBaseModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public interface PersonalContract {

    abstract class PersonalPresenter extends BasePresenter<PersonalModel,PersonalView> {

    }
    interface PersonalModel extends IBaseModel {


    }
    interface PersonalView extends IBaseFragment {

    }

}
