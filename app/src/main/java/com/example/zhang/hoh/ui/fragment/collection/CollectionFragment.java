package com.example.zhang.hoh.ui.fragment.collection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.adapter.collection.collectionListViewAdapter;
import com.example.zhang.hoh.bean.collection.collectionMainListViewBean;
import com.example.zhang.hoh.contract.collection.CollectionContract;
import com.example.zhang.hoh.presenter.collection.CollectionPresenter;
import com.example.zhang.hoh.ui.fragment.map.MapFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CollectionFragment extends BaseMVPCompatFragment<CollectionContract.CollectionPresenter> implements CollectionContract.CollectionView {

    @BindView(R.id.collection_main_food_btn)
    ImageView foodBtn;
    @BindView(R.id.collection_main_museum_btn)
    ImageView museumBtn;
    @BindView(R.id.collection_main_scenery_btn)
    ImageView sceneryBtn;
    @BindView(R.id.collection_main_lv)
    ListView listView;
    @BindView(R.id.collection_main_userImg_iv)
    ImageView header;
    @BindView(R.id.collection_main_scenery_text)
    TextView tvScenery;
    @BindView(R.id.collection_main_museum_text)
    TextView tvMuseum;
    @BindView(R.id.collection_main_food_text)
    TextView tvFood;

    @OnClick({R.id.collection_main_scenery_btn,R.id.collection_main_museum_btn,R.id.collection_main_food_btn})
    void changeTextColor(ImageView imageButton){
        switch (imageButton.getId()){
            case R.id.collection_main_scenery_btn:
                resetTextColor();
                tvScenery.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                break;
            case R.id.collection_main_museum_btn:
                resetTextColor();
                tvMuseum.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                break;
            case R.id.collection_main_food_btn:
                resetTextColor();
                tvFood.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                break;
        }
    }

    private void resetTextColor() {
        tvScenery.setTextColor(ContextCompat.getColor(mContext,R.color.toolbar_bg));
        tvMuseum.setTextColor(ContextCompat.getColor(mContext,R.color.toolbar_bg));
        tvFood.setTextColor(ContextCompat.getColor(mContext,R.color.toolbar_bg));
    }


    private collectionListViewAdapter lvAdapter;
    private List<collectionMainListViewBean>  lvData;


    public static CollectionFragment newInstance() {
        Bundle args = new Bundle();
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return CollectionPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void initData() {
        super.initData();
        lvData=new ArrayList<collectionMainListViewBean>();
        //假数据
        collectionMainListViewBean bean1=new collectionMainListViewBean();
        bean1.setBg(R.drawable.list_museum_gushan);
        bean1.setLocal("杭州市西湖区孤山路25号");
        bean1.setName("浙江省博物馆");
        lvData.add(bean1);
        collectionMainListViewBean bean2=new collectionMainListViewBean();
        bean2.setBg(R.drawable.list_museum_xihu);
        bean2.setLocal("杭州市南山路89号");
        bean2.setName("杭州西湖博物馆");
        lvData.add(bean2);
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //头像
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this).load(R.drawable.personal_head).apply(requestOptions).into(header);

        lvAdapter=new collectionListViewAdapter(getActivity(),lvData,listView);
        listView.setAdapter(lvAdapter);

        museumBtn.performClick();


    }


}
