package com.example.zhang.hoh.model.collection;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.collection.CollectionContract;

public class CollectionModel implements CollectionContract.CollectionModel {
    @NonNull
    public static CollectionModel newInstance() {
        return new CollectionModel();
    }


}
