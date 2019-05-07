package com.example.zhang.hoh.contract.map;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseFragment;
import com.example.sdk.base.IBaseModel;

public interface MapContract {

    abstract class MapPresenter extends BasePresenter<MapModel,MapView> {

    }
    interface MapModel extends IBaseModel {


    }
    interface MapView extends IBaseFragment {

    }

}
