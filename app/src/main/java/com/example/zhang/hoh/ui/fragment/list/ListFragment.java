package com.example.zhang.hoh.ui.fragment.list;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.sdk.utils.AppUtils;
import com.example.sdk.utils.BitmapUtils;
import com.example.sdk.utils.GlideUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.adapter.list.listAdapter;
import com.example.zhang.hoh.bean.list.listBottomInRVBean;
import com.example.zhang.hoh.contract.list.ListContract;
import com.example.zhang.hoh.presenter.list.ListPresenter;
import com.example.zhang.hoh.ui.fragment.personal.PersonalFragment;
import com.ryan.rv_gallery.GalleryRecyclerView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

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
        bottomInLl.setVisibility(View.VISIBLE);
        topInLl.setVisibility(View.VISIBLE);

        showRecyclerView(3);
    }

    @OnClick(R.id.list_main_topll_in)
    void backToMain() {
        bottomNormalLl.setVisibility(View.VISIBLE);
        topNormalLl.setVisibility(View.VISIBLE);
        bottomInLl.setVisibility(View.GONE);
        topInLl.setVisibility(View.GONE);
        //释放adapter
        //detailRv.setAdapter(null);
    }

    private List<listBottomInRVBean> sceneryDataList;
    private List<listBottomInRVBean> museumDataList;
    private List<listBottomInRVBean> foodDataList;
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
        listBottomInRVBean bean1 = new listBottomInRVBean("Zhejiang Museum", "WestLake 43"
                , R.drawable.list_museum_two, true);
        listBottomInRVBean bean2 = new listBottomInRVBean("Zhejiang Museum", "WestLake 43"
                , R.drawable.list_museum_two, false);
        listBottomInRVBean bean3 = new listBottomInRVBean("Zhejiang Museum", "WestLake 43"
                , R.drawable.list_museum_two, false);
        museumDataList.add(bean1);
        museumDataList.add(bean2);
        museumDataList.add(bean3);

        sceneryDataList = new ArrayList<>();
         bean1 = new listBottomInRVBean("Qiandao Pool", "WestLake 43"
                , R.drawable.list_scenery_two, true);
         bean2 = new listBottomInRVBean("Qiandao Pool", "WestLake 43"
                , R.drawable.list_scenery_two, false);
         bean3 = new listBottomInRVBean("Qiandao Pool", "WestLake 43"
                , R.drawable.list_scenery_two, false);
        sceneryDataList.add(bean1);
        sceneryDataList.add(bean2);
        sceneryDataList.add(bean3);

        foodDataList = new ArrayList<>();
        bean1 = new listBottomInRVBean("Zhejiang Museum", "WestLake 43"
                , R.drawable.list_food_two, true);
        bean2 = new listBottomInRVBean("Zhejiang Museum", "WestLake 43"
                , R.drawable.list_food_two, false);
        bean3 = new listBottomInRVBean("Zhejiang Museum", "WestLake 43"
                , R.drawable.list_food_two, false);
        foodDataList.add(bean1);
        foodDataList.add(bean2);
        foodDataList.add(bean3);

    }

    private void showRecyclerView(int pos) {
        listAdapter adapter=null;
        switch (pos) {
            case 1://风景
                adapter = new listAdapter(AppUtils.getContext(), museumDataList);
                break;
            case 2://博物馆
                adapter = new listAdapter(AppUtils.getContext(), sceneryDataList);
                break;
            case 3://食物
                adapter = new listAdapter(AppUtils.getContext(), foodDataList);
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

    private void initBg() {
        RoundedCorners roundedCorners = new RoundedCorners(50);
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
}
