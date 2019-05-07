package com.example.zhang.hoh.presenter.collection;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.collection.CollectionContract;
import com.example.zhang.hoh.contract.map.MapContract;
import com.example.zhang.hoh.presenter.lineup.LineupPresenter;

public class CollectionPresenter extends CollectionContract.CollectionPresenter{

    @NonNull
    public static CollectionPresenter newInstance() {
        return new CollectionPresenter();
    }


    @Override
    public CollectionContract.CollectionModel getModel() {
        return null;
    }

    @Override
    public void onStart() {

    }

}
