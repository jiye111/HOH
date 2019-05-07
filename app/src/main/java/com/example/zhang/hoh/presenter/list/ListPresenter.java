package com.example.zhang.hoh.presenter.list;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.list.ListContract;
import com.example.zhang.hoh.contract.map.MapContract;
import com.example.zhang.hoh.presenter.personal.PersonalPresenter;

public class ListPresenter extends ListContract.ListPresenter{

    @NonNull
    public static ListPresenter newInstance() {
        return new ListPresenter();
    }


    @Override
    public ListContract.ListModel getModel() {
        return null;
    }

    @Override
    public void onStart() {

    }

}
