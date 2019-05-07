package com.example.zhang.hoh.ui.fragment.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.contract.map.MapContract;
import com.example.zhang.hoh.presenter.map.MapPresenter;
import com.example.zhang.hoh.ui.fragment.list.ListFragment;

public class MapFragment extends BaseMVPCompatFragment<MapContract.MapPresenter> implements  MapContract.MapView {

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MapPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
