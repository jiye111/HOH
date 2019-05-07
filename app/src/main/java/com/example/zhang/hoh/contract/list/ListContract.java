package com.example.zhang.hoh.contract.list;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseFragment;
import com.example.sdk.base.IBaseModel;

public interface ListContract {

    abstract class ListPresenter extends BasePresenter<ListModel,ListView> {

    }
    interface ListModel extends IBaseModel {


    }
    interface ListView extends IBaseFragment {

    }

}
