package com.example.zhang.hoh.model.map;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.map.MapContract;
import com.example.zhang.hoh.contract.personal.PersonalContract;

public class MapModel implements MapContract.MapModel {
    @NonNull
    public static MapModel newInstance() {
        return new MapModel();
    }


}
