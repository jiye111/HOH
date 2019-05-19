package com.example.zhang.hoh.ui.activity.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.base.activity.BaseCompatActivity;
import com.example.sdk.utils.BitmapUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.utils.GlideImageLoader;
import com.example.zhang.hoh.widget.ConflictAMap;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SceneryDetailActivity extends BaseCompatActivity {
    @BindView(R.id.list_scenery_detail_banner)
    Banner banner;
    @BindView(R.id.list_scenery_detail_bg)
    ImageView ivBg;

    @OnClick(R.id.list_scenery_detail_back_btn)
    void toBack(){
        finish();
    }

    private ConflictAMap mapView;
    private Marker sceneryMarker;
    AMap aMap;

    @Override
    protected int getLayoutId() {
        return R.layout.list_scenery_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bitmap sceneryBitmap = BitmapUtils.decodeSampledBitmapFromResoure(getResources(), R.drawable.list_scenery_three
                , 400, 250);
        Glide.with(this)
                .load(sceneryBitmap)
                .into(ivBg);
        //展示banner
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.list_food_one);
        imageList.add(R.drawable.list_food_three);
        imageList.add(R.drawable.list_food_one);
        banner.setImages(imageList);
        //设置轮播时间
        banner.setDelayTime(3000);
        //指示器
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
        initMap(savedInstanceState);
    }

    private void initMap(Bundle savedInstanceState) {
        //得到地图对象
        mapView=findViewById(R.id.list_scenery_map);
        mapView.onCreate(savedInstanceState);
        AMap aMap=null;
        if (aMap==null){
            aMap=mapView.getMap();
        }
        //设置中心点并缩放
        //设置为西湖
        LatLng marker1 = new LatLng(30.222574,120.121498);
        aMap.moveCamera(CameraUpdateFactory.newLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        //开启自定义样式
        String path= Environment.getExternalStoragePublicDirectory("data").getPath()+"/style.data";
        String pathExtra=Environment.getExternalStoragePublicDirectory("data").getPath()+"/style_extra.data";
        CustomMapStyleOptions customMapStyleOptions=new CustomMapStyleOptions();
        customMapStyleOptions.setStyleDataPath(path);
        customMapStyleOptions.setStyleExtraPath(pathExtra);
        aMap.setCustomMapStyle(customMapStyleOptions);

        initMapLocalIcon();
    }
    private void initMapLocalIcon() {
        BitmapDescriptor iconMuseum = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.map_local_museum_small));
        MarkerOptions museumMarkerOption1 = new MarkerOptions();
        LatLng museum1 = new LatLng(30.222574, 120.121498);
        museumMarkerOption1.position(museum1);
        museumMarkerOption1.title("西湖");
        museumMarkerOption1.alpha(0.7f);
        museumMarkerOption1.draggable(true);//设置Marker可拖动
        museumMarkerOption1.icon(iconMuseum);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        museumMarkerOption1.setFlat(true);//设置marker平贴地图效果
        sceneryMarker = aMap.addMarker(museumMarkerOption1);
    }

    @Override
    protected void ActiveFromBackstage() {

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
