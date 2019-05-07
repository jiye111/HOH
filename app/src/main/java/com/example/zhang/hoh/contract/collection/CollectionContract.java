package com.example.zhang.hoh.contract.collection;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseFragment;
import com.example.sdk.base.IBaseModel;

public interface CollectionContract {

    abstract class CollectionPresenter extends BasePresenter<CollectionModel,CollectionView> {

    }
    interface CollectionModel extends IBaseModel {


    }
    interface CollectionView extends IBaseFragment {

    }

}
