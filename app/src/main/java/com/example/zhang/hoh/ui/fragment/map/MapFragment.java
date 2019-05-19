package com.example.zhang.hoh.ui.fragment.map;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.sdk.utils.LogUtils;
import com.example.sdk.utils.ToastUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.contract.map.MapContract;
import com.example.zhang.hoh.presenter.map.MapPresenter;
import com.example.zhang.hoh.ui.fragment.list.ListFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MapFragment extends BaseMVPCompatFragment<MapContract.MapPresenter> implements MapContract.MapView {
    @BindView(R.id.map_main_selected_tab_iv)
    ImageView ivTab;

    @OnClick({R.id.map_main_museum, R.id.map_main_scenery, R.id.map_main_food})
    void changeTab(TextView tv) {
        switch (tv.getId()) {
            case R.id.map_main_museum:
                ivTab.setBackground(ContextCompat.getDrawable(mContext, R.drawable.map_tab_museum));
                hideShowLocalIcon(0);
                break;
            case R.id.map_main_food:
                ivTab.setBackground(ContextCompat.getDrawable(mContext, R.drawable.map_tab_food));
                hideShowLocalIcon(2);
                break;
            case R.id.map_main_scenery:
                ivTab.setBackground(ContextCompat.getDrawable(mContext, R.drawable.map_tab_scenery));
                hideShowLocalIcon(1);
                break;
            default:
                break;
        }
    }


    private MapView mapView;
    private AMap aMap;
    private Marker museumMarker1;
    private Marker museumMarker2;
    private Marker museumMarker3;
    private Marker foodMarker1;
    private Marker foodMarker2;
    private Marker foodMarker3;
    private Marker sceneryMarker1;
    private Marker sceneryMarker2;
    private Marker sceneryMarker3;
    private Marker sceneryMarker4;
    private List<Marker> markerList;

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
    public void initData() {
        super.initData();

    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //得到地图对象
        mapView = getActivity().findViewById(R.id.map_mainmap);
        mapView.onCreate(savedInstanceState);
        aMap = null;
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //读写自定义样式
        initStyleMap();
        //设置中心点并缩放
        //默认中国美院象山学院
        LatLng marker1 = new LatLng(30.242329, 120.158748);
        aMap.moveCamera(CameraUpdateFactory.newLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        //设置地图样式
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.strokeWidth(1.0f);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        //是否开启定位
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //设置英文地图
        //aMap.setMapLanguage("en");

        //开启自定义样式
        String path = Environment.getExternalStoragePublicDirectory("data").getPath() + "/style.data";
        String pathExtra = Environment.getExternalStoragePublicDirectory("data").getPath() + "/style_extra.data";
        CustomMapStyleOptions customMapStyleOptions = new CustomMapStyleOptions();
        customMapStyleOptions.setStyleDataPath(path);
        customMapStyleOptions.setStyleExtraPath(pathExtra);
        aMap.setCustomMapStyle(customMapStyleOptions);
        //设置标记点
        initMuseumMaker();
        initFoodMaker();
        initSceneryMaker();

        markerList = new ArrayList<>();

        addMarkerToList();
    }

    private void addMarkerToList() {
        markerList.add(museumMarker1);
        markerList.add(museumMarker2);
        markerList.add(museumMarker3);
        markerList.add(foodMarker1);
        markerList.add(foodMarker2);
        markerList.add(foodMarker3);
        markerList.add(sceneryMarker1);
        markerList.add(sceneryMarker2);
        markerList.add(sceneryMarker3);
        markerList.add(sceneryMarker4);
    }

    private void initMuseumMaker() {
        BitmapDescriptor iconMuseum = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.map_local_museum_small));
        MarkerOptions museumMarkerOption1 = new MarkerOptions();
        LatLng museum1 = new LatLng(30.241556, 120.158671);
        museumMarkerOption1.position(museum1);
        museumMarkerOption1.title("恒庐美术馆").snippet("建于明国");
        museumMarkerOption1.alpha(0.7f);
        museumMarkerOption1.draggable(true);//设置Marker可拖动
        museumMarkerOption1.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        museumMarkerOption1.setFlat(true);//设置marker平贴地图效果
        museumMarker1 = aMap.addMarker(museumMarkerOption1);

        MarkerOptions museumMarkerOption2 = new MarkerOptions();
        LatLng museum2 = new LatLng(30.24321, 120.160403);
        museumMarkerOption2.position(museum2);
        museumMarkerOption2.title("中国美术学院").snippet("杭州最美校区");
        museumMarkerOption2.alpha(0.7f);
        museumMarkerOption2.anchor(0.2f, 0.2f);
        museumMarkerOption2.draggable(true);//设置Marker可拖动
        museumMarkerOption2.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        museumMarkerOption2.setFlat(true);//设置marker平贴地图效果
        museumMarker2 = aMap.addMarker(museumMarkerOption2);

        MarkerOptions museumMarkerOption3 = new MarkerOptions();
        LatLng museum3 = new LatLng(30.24176, 120.158005);
        museumMarkerOption3.position(museum3);
        museumMarkerOption3.title("西湖博物馆").snippet("馆藏众多");
        museumMarkerOption3.alpha(0.7f);
        museumMarkerOption3.anchor(0.2f, 0.2f);
        museumMarkerOption3.draggable(true);//设置Marker可拖动
        museumMarkerOption3.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        museumMarkerOption3.setFlat(true);//设置marker平贴地图效果
        museumMarker3 = aMap.addMarker(museumMarkerOption3);

    }

    private void initFoodMaker() {
        BitmapDescriptor iconMuseum = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.map_local_food_small));
        MarkerOptions foodMarkerOption1 = new MarkerOptions();
        LatLng food1 = new LatLng(30.243604, 120.159405);
        foodMarkerOption1.position(food1);
        foodMarkerOption1.title("西湖春天").snippet(" ");
        foodMarkerOption1.alpha(0.7f);
        foodMarkerOption1.draggable(true);//设置Marker可拖动
        foodMarkerOption1.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        foodMarkerOption1.setFlat(true);//设置marker平贴地图效果
        foodMarker1 = aMap.addMarker(foodMarkerOption1);

        MarkerOptions foodMarkerOption2 = new MarkerOptions();
        LatLng food2 = new LatLng(30.241621, 120.159008);
        foodMarkerOption2.position(food2);
        foodMarkerOption2.title("恒庐清茶馆").snippet("闲谈去除");
        foodMarkerOption2.alpha(0.7f);
        foodMarkerOption2.anchor(0.2f, 0.2f);
        foodMarkerOption2.draggable(true);//设置Marker可拖动
        foodMarkerOption2.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        foodMarkerOption2.setFlat(true);//设置marker平贴地图效果
        foodMarker2 = aMap.addMarker(foodMarkerOption2);

        MarkerOptions foodMarkerOption3 = new MarkerOptions();
        LatLng food3 = new LatLng(30.240782, 120.157796);
        foodMarkerOption3.position(food3);
        foodMarkerOption3.title("Joy酒隐").snippet(" ");
        foodMarkerOption3.alpha(0.7f);
        foodMarkerOption3.anchor(0.2f, 0.2f);
        foodMarkerOption3.draggable(true);//设置Marker可拖动
        foodMarkerOption3.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        foodMarkerOption3.setFlat(true);//设置marker平贴地图效果
        foodMarker3 = aMap.addMarker(foodMarkerOption3);

    }

    private void initSceneryMaker() {
        BitmapDescriptor iconMuseum = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.map_local_scenery_small));
        MarkerOptions foodMarkerOption1 = new MarkerOptions();
        LatLng food1 = new LatLng(30.242659, 120.158038);
        foodMarkerOption1.position(food1);
        foodMarkerOption1.title("钱王祠").snippet(" ");
        foodMarkerOption1.alpha(0.7f);
        foodMarkerOption1.draggable(true);//设置Marker可拖动
        foodMarkerOption1.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        foodMarkerOption1.setFlat(true);//设置marker平贴地图效果
        sceneryMarker1 = aMap.addMarker(foodMarkerOption1);

        MarkerOptions foodMarkerOption2 = new MarkerOptions();
        LatLng food2 = new LatLng(30.24106, 120.156798);
        foodMarkerOption2.position(food2);
        foodMarkerOption2.title("会芳亭").snippet(" ");
        foodMarkerOption2.alpha(0.7f);
        foodMarkerOption2.anchor(0.2f, 0.2f);
        foodMarkerOption2.draggable(true);//设置Marker可拖动
        foodMarkerOption2.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        foodMarkerOption2.setFlat(true);//设置marker平贴地图效果
        sceneryMarker2 = aMap.addMarker(foodMarkerOption2);

        MarkerOptions foodMarkerOption3 = new MarkerOptions();
        LatLng food3 = new LatLng(30.241041, 120.155168);
        foodMarkerOption3.position(food3);
        foodMarkerOption3.title("周家老宅").snippet("百年老宅");
        foodMarkerOption3.alpha(0.7f);
        foodMarkerOption3.anchor(0.2f, 0.2f);
        foodMarkerOption3.draggable(true);//设置Marker可拖动
        foodMarkerOption3.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        foodMarkerOption3.setFlat(true);//设置marker平贴地图效果
        sceneryMarker3 = aMap.addMarker(foodMarkerOption3);

        MarkerOptions foodMarkerOption4 = new MarkerOptions();
        LatLng food4 = new LatLng(30.241041, 120.155168);
        foodMarkerOption4.position(food4);
        foodMarkerOption4.title("周家老宅").snippet("百年老宅");
        foodMarkerOption4.alpha(0.7f);
        foodMarkerOption4.anchor(0.2f, 0.2f);
        foodMarkerOption4.draggable(true);//设置Marker可拖动
        foodMarkerOption4.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        foodMarkerOption4.setFlat(true);//设置marker平贴地图效果
        sceneryMarker4 = aMap.addMarker(foodMarkerOption4);

    }

    private void initStyleMap() {
        try {
            // 先获取系统默认的文档存放根目录
            File parent_path = Environment.getExternalStorageDirectory();
            File dir = new File(parent_path.getAbsoluteFile(), "data");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dir.getAbsoluteFile(), "style.data");
            File fileExtra = new File(dir.getAbsoluteFile(), "style_extra.data");
            if (file.exists() && fileExtra.exists()) {
                return;
            }
            //读取数据文件
            InputStream open = this.getResources().getAssets().open("styleMap/style.data");
            InputStream openExtra = this.getResources().getAssets().open("styleMap/style_extra.data");

            file.createNewFile();
            fileExtra.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            FileOutputStream fosExtra = new FileOutputStream(fileExtra);
            int len;
            int lenExtra;
            byte[] buf = new byte[1024];
            byte[] bufExtra = new byte[1024];
            while ((len = open.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            while ((lenExtra = openExtra.read(bufExtra)) != -1) {
                fosExtra.write(bufExtra, 0, lenExtra);
            }

            fosExtra.flush();
            fosExtra.close();
            fos.flush();
            fos.close();

            LogUtils.d("TextDiyMap", "自定义地图成功");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d("TextDiyMap", "自定义地图失败：" + e.toString());

        }

    }

    private void hideShowLocalIcon(int count) {
        for (int i = 0; i < markerList.size(); i++) {
            if (markerList.get(i).isVisible() == true) {
                markerList.get(i).setVisible(false);
            }
        }
        switch (count) {
            case 0://博物馆
                museumMarker1.setVisible(true);
                museumMarker2.setVisible(true);
                museumMarker3.setVisible(true);
                break;
            case 1://风景
                sceneryMarker1.setVisible(true);
                sceneryMarker2.setVisible(true);
                sceneryMarker3.setVisible(true);
                sceneryMarker4.setVisible(true);
                break;
            case 2://食物
                foodMarker1.setVisible(true);
                foodMarker2.setVisible(true);
                foodMarker3.setVisible(true);
                //foodMarker4.setVisible(true);
                break;
            default:
                break;

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
