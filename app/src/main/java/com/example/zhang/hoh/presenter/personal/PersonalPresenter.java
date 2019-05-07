package com.example.zhang.hoh.presenter.personal;

import android.support.annotation.NonNull;

import com.example.zhang.hoh.contract.personal.PersonalContract;

public class PersonalPresenter extends PersonalContract.PersonalPresenter{

    @NonNull
    public static PersonalPresenter newInstance() {
        return new PersonalPresenter();
    }


    @Override
    public PersonalContract.PersonalModel getModel() {
        return null;
    }

    @Override
    public void onStart() {

    }

}
