package com.example.zhang.hoh.ui.fragment.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.contract.map.MapContract;
import com.example.zhang.hoh.presenter.map.MapPresenter;
import com.example.zhang.hoh.ui.fragment.list.ListFragment;

import butterknife.BindView;

public class MapFragment extends BaseMVPCompatFragment<MapContract.MapPresenter> implements  MapContract.MapView {

    //@BindView(R.id.map_mainmap)
    private  MapView mapView;
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
        //得到地图对象
        AMap aMap=null;
        if (aMap==null){
            aMap=mapView.getMap();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView=getActivity().findViewById(R.id.map_mainmap);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
