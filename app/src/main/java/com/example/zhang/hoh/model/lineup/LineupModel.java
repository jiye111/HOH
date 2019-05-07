package com.example.zhang.hoh.model.lineup;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.lineup.LineupContract;

public class LineupModel implements LineupContract.LineupModel {
    @NonNull
    public static LineupModel newInstance() {
        return new LineupModel();
    }


}
