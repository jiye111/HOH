package com.example.zhang.hoh.model.personal;

import android.support.annotation.NonNull;

import com.example.sdk.base.IBaseModel;
import com.example.zhang.hoh.contract.personal.PersonalContract;

public class PersonalModel  implements PersonalContract.PersonalModel {
    @NonNull
    public static PersonalModel newInstance() {
        return new PersonalModel();
    }


}
