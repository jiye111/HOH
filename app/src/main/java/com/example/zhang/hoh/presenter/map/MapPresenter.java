package com.example.zhang.hoh.presenter.map;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.map.MapContract;
import com.example.zhang.hoh.contract.personal.PersonalContract;
import com.example.zhang.hoh.presenter.personal.PersonalPresenter;

public class MapPresenter extends MapContract.MapPresenter{

    @NonNull
    public static MapPresenter newInstance() {
        return new MapPresenter();
    }


    @Override
    public MapContract.MapModel getModel() {
        return null;
    }

    @Override
    public void onStart() {

    }

}
