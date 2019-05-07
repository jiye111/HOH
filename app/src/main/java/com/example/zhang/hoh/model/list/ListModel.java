package com.example.zhang.hoh.model.list;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.list.ListContract;
import com.example.zhang.hoh.contract.map.MapContract;

public class ListModel implements ListContract.ListModel {
    @NonNull
    public static ListModel newInstance() {
        return new ListModel();
    }


}
