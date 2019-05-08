package com.example.zhang.hoh.ui.activity.list;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.base.activity.BaseCompatActivity;
import com.example.sdk.utils.BitmapUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SceneryDetailActivity extends BaseCompatActivity {
    @BindView(R.id.list_scenery_detail_banner)
    Banner banner;
    @BindView(R.id.list_scenery_detail_bg)
    ImageView ivBg;

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

    }

    @Override
    protected void ActiveFromBackstage() {

    }
}
