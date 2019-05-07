package com.example.zhang.hoh.presenter.lineup;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.lineup.LineupContract;
import com.example.zhang.hoh.presenter.personal.PersonalPresenter;

public class LineupPresenter extends LineupContract.LineupPresenter{

    @NonNull
    public static LineupPresenter newInstance() {
        return new LineupPresenter();
    }


    @Override
    public LineupContract.LineupModel getModel() {
        return null;
    }

    @Override
    public void onStart() {

    }

}
