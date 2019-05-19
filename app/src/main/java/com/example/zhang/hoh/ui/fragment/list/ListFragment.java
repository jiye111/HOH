package com.example.zhang.hoh.ui.fragment.list;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.sdk.utils.AppUtils;
import com.example.sdk.utils.BitmapUtils;
import com.example.sdk.utils.DisplayUtil;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.adapter.list.listAdapter;
import com.example.zhang.hoh.adapter.list.listFoodLocalAdapter;
import com.example.zhang.hoh.adapter.list.listFoodRecommendAdapter;
import com.example.zhang.hoh.bean.list.listBottomInRVBean;
import com.example.zhang.hoh.contract.list.ListContract;
import com.example.zhang.hoh.presenter.list.ListPresenter;
import com.example.zhang.hoh.ui.activity.list.ListFoodActivity;
import com.example.zhang.hoh.ui.activity.list.MuseumDetailActivity;
import com.example.zhang.hoh.ui.activity.list.SceneryDetailActivity;
import com.example.zhang.hoh.utils.GlideImageLoader;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListFragment extends BaseMVPCompatFragment<ListContract.ListPresenter> implements ListContract.ListView {
    @BindView(R.id.list_main_userImg_iv)
    ImageView header;
    @BindView(R.id.list_scenery_bg)
    ImageView sceneryIv;
    @BindView(R.id.list_museum_bg)
    ImageView museumIv;
    @BindView(R.id.list_food_bg)
    ImageView foodIv;
    @BindView(R.id.list_main_bottom_in_rv)
    DiscreteScrollView detailRv;
    @BindView(R.id.list_main_bottom_normal)
    LinearLayout bottomNormalLl;
    @BindView(R.id.list_main_bottom_in)
    LinearLayout bottomInLl;
    @BindView(R.id.list_main_topll_normal)
    LinearLayout topNormalLl;
    @BindView(R.id.list_main_topll_in)
    LinearLayout topInLl;
    @BindView(R.id.list_main_bottom_in_food)
    LinearLayout bottomInFoodLl;
    @BindView(R.id.list_main_bottom_in_food_awesome_more)
    ImageView bottomInFoodAwesomeMoreIv;
    @BindView(R.id.list_main_bottom_in_food_local_more)
    ImageView bottomInFoodLocalMoreIv;
    @BindView(R.id.list_main_bottom_in_food_recommend_more)
    ImageView bottomInFoodrecommendMoreIv;
    @BindView(R.id.list_main_bottom_in_food_awesome_banner)
    Banner bottomInFoodBanner;
    @BindView(R.id.list_main_bottom_in_food_local_lv)
    RecyclerView bottomInFoodLocalRl;
    @BindView(R.id.list_main_bottom_in_food_recommend_lv)
    RecyclerView bottomInFoodRecommendRl;


    @OnClick(R.id.list_scenery_bg)
    void toScenery() {
        bottomNormalLl.setVisibility(View.GONE);
        topNormalLl.setVisibility(View.GONE);
        bottomInLl.setVisibility(View.VISIBLE);
        topInLl.setVisibility(View.VISIBLE);

        showRecyclerView(1);
    }

    @OnClick(R.id.list_museum_bg)
    void toMuseum() {
        bottomNormalLl.setVisibility(View.GONE);
        topNormalLl.setVisibility(View.GONE);
        bottomInLl.setVisibility(View.VISIBLE);
        topInLl.setVisibility(View.VISIBLE);

        showRecyclerView(2);
    }

    @OnClick(R.id.list_food_bg)
    void toFood() {
        bottomNormalLl.setVisibility(View.GONE);
        topNormalLl.setVisibility(View.GONE);
        bottomInFoodLl.setVisibility(View.VISIBLE);
        topInLl.setVisibility(View.VISIBLE);

        showFoodView();
    }


    @OnClick(R.id.list_main_topll_in)
    void backToMain() {
        //food做特殊处理
        if (bottomInFoodLl.getVisibility() == View.VISIBLE) {
            bottomInFoodLl.setVisibility(View.GONE);
            bottomInFoodBanner.stopAutoPlay();
        } else {
            bottomInLl.setVisibility(View.GONE);
        }
        bottomNormalLl.setVisibility(View.VISIBLE);
        topNormalLl.setVisibility(View.VISIBLE);
        topInLl.setVisibility(View.GONE);
        //释放adapter
        //detailRv.setAdapter(null);
    }

    private List<listBottomInRVBean> sceneryDataList;
    private List<listBottomInRVBean> museumDataList;
    private List<listBottomInRVBean> foodLocalDataList;
    private List<listBottomInRVBean> foodRecommendDataList;
    private View mView;


    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ListPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mView = view;
        //头像
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this).load(R.drawable.personal_head).apply(requestOptions).into(header);
        initRvData();
        initBg();
    }

    private void initRvData() {
        museumDataList = new ArrayList<>();
        listBottomInRVBean bean1 = new listBottomInRVBean("浙江省博物馆", "杭州市西湖区孤山路25号"
                , R.drawable.list_museum_gushan, true);
        listBottomInRVBean bean2 = new listBottomInRVBean("杭州西湖博物馆", "杭州市南山路89号"
                , R.drawable.list_museum_xihu, false);
        listBottomInRVBean bean3 = new listBottomInRVBean("浙江省博物馆", "杭州市西湖区孤山路25号"
                , R.drawable.list_museum_gushan, false);
        museumDataList.add(bean1);
        museumDataList.add(bean2);
        museumDataList.add(bean3);

        sceneryDataList = new ArrayList<>();
        bean1 = new listBottomInRVBean("西湖", "杭州市西湖区龙井路1号"
                , R.drawable.list_scenery_xihu, true);
        bean2 = new listBottomInRVBean("千岛湖", "杭州市淳安县境内"
                , R.drawable.list_scenery_two, false);
        bean3 = new listBottomInRVBean("西湖", "杭州市西湖区龙井路1号"
                , R.drawable.list_scenery_xihu, false);
        sceneryDataList.add(bean1);
        sceneryDataList.add(bean2);
        sceneryDataList.add(bean3);

        foodLocalDataList = new ArrayList<>();
        bean1 = new listBottomInRVBean("meishi 1", "WestLake 43"
                , R.drawable.list_food_one, true);
        bean2 = new listBottomInRVBean("meishi 2", "WestLake 43"
                , R.drawable.list_food_two, false);
        bean3 = new listBottomInRVBean("meishi 3", "WestLake 43"
                , R.drawable.list_food_three, false);
        foodLocalDataList.add(bean1);
        foodLocalDataList.add(bean2);
        foodLocalDataList.add(bean3);

        foodRecommendDataList = new ArrayList<>();
        bean1 = new listBottomInRVBean("meishi 1", null
                , R.drawable.list_food_three, false);
        bean2 = new listBottomInRVBean("meishi 2", null
                , R.drawable.list_food_two, false);
        bean3 = new listBottomInRVBean("meishi 3", null
                , R.drawable.list_food_three, false);
        foodRecommendDataList.add(bean1);
        foodRecommendDataList.add(bean2);
        foodRecommendDataList.add(bean3);

    }


    private void initBg() {
        RoundedCorners roundedCorners = new RoundedCorners(DisplayUtil.dip2px(mContext,10));
        Bitmap sceneryBitmap = BitmapUtils.decodeSampledBitmapFromResoure(getResources(), R.drawable.list_scenery_one
                , 400, 300);
        Glide.with(this)
                .load(sceneryBitmap)
                .apply(RequestOptions.bitmapTransform(roundedCorners))
                .into(sceneryIv);


        Bitmap museumBitmap = BitmapUtils.decodeSampledBitmapFromResoure(getResources(), R.drawable.list_museum_one
                , 400, 300);
        Glide.with(this)
                .load(museumBitmap)
                .apply(RequestOptions.bitmapTransform(roundedCorners))
                .into(museumIv);

        Bitmap foodBitmap = BitmapUtils.decodeSampledBitmapFromResoure(getResources(), R.drawable.list_food_one
                , 400, 300);
        Glide.with(this)
                .load(foodBitmap)
                .apply(RequestOptions.bitmapTransform(roundedCorners))
                .into(foodIv);
    }

    private void showFoodView() {
        //展示banner
        bottomInFoodBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.list_food_banner_one);
        imageList.add(R.drawable.list_food_banner_two);
        imageList.add(R.drawable.list_food_banner_three);
        bottomInFoodBanner.setImages(imageList);
        //设置轮播时间
        bottomInFoodBanner.setDelayTime(3000);
        //指示器
        bottomInFoodBanner.setIndicatorGravity(BannerConfig.CENTER);
        bottomInFoodBanner.start();
        //调转到网红餐厅
        bottomInFoodBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AppUtils.getContext(), ListFoodActivity.class);
                startActivity(intent);
            }
        });

        //加载下方两个view
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(AppUtils.getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        listFoodLocalAdapter adapter=new listFoodLocalAdapter(AppUtils.getContext(),foodLocalDataList);
        bottomInFoodLocalRl.setLayoutManager(layoutManager);
        bottomInFoodLocalRl.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(AppUtils.getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        listFoodRecommendAdapter adapter2=new listFoodRecommendAdapter(AppUtils.getContext(),foodLocalDataList);
        bottomInFoodRecommendRl.setLayoutManager(layoutManager2);
        bottomInFoodRecommendRl.setAdapter(adapter2);

//        adapter.setClickListener(new listFoodLocalAdapter.ItemClickListener() {
//            @Override
//            public void onItemClickListentr(View view, int pos) {
//                Intent intent=new Intent(AppUtils.getContext(), ListFoodActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void showRecyclerView(int pos) {
        listAdapter adapter = null;
        switch (pos) {
            case 1://风景
                adapter = new listAdapter(AppUtils.getContext(), sceneryDataList);
                adapter.setClickListener(new listAdapter.ItemClickListener() {
                    @Override
                    public void onItemClickListentr(View view, int pos) {
                        Intent intent=new Intent(AppUtils.getContext(), SceneryDetailActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            case 2://博物馆
                adapter = new listAdapter(AppUtils.getContext(), museumDataList);
                adapter.setClickListener(new listAdapter.ItemClickListener() {
                    @Override
                    public void onItemClickListentr(View view, int pos) {
                        Intent intent=new Intent(AppUtils.getContext(), MuseumDetailActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            case 3://
              //  adapter = new listAdapter(AppUtils.getContext(), foodDataList);
                break;
            default:
                break;
        }
        //detailRv.setLayoutManager(new LinearLayoutManager(AppUtils.getContext(), LinearLayoutManager.HORIZONTAL, false));
        detailRv.setAdapter(adapter);
        detailRv.setOffscreenItems(10);
        detailRv.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.95f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.BOTTOM)
                .build());



    }

}
