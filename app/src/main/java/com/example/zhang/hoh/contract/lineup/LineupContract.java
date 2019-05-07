package com.example.zhang.hoh.contract.lineup;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseFragment;
import com.example.sdk.base.IBaseModel;

public interface LineupContract {

    abstract class LineupPresenter extends BasePresenter<LineupModel,LineupView> {

    }
    interface LineupModel extends IBaseModel {


    }
    interface LineupView extends IBaseFragment {

    }

}
