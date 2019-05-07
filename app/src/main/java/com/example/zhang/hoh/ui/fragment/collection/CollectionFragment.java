package com.example.zhang.hoh.ui.fragment.collection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

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

public class CollectionFragment extends BaseMVPCompatFragment<CollectionContract.CollectionPresenter> implements CollectionContract.CollectionView {

    @BindView(R.id.collection_main_food_btn)
    ImageButton foodBtn;
    @BindView(R.id.collection_main_museum_btn)
    ImageButton museumBtn;
    @BindView(R.id.collection_main_scenery_btn)
    ImageButton sceneryBtn;
    @BindView(R.id.collection_main_lv)
    ListView listView;
    @BindView(R.id.collection_main_userImg_iv)
    ImageView header;

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
        bean1.setBg(R.drawable.fruit);
        bean1.setLocal("缙云县 五云街道");
        bean1.setName("小王烤饼店");
        lvData.add(bean1);
        collectionMainListViewBean bean2=new collectionMainListViewBean();
        bean2.setBg(R.drawable.fruit);
        bean2.setLocal("留和路 318号");
        bean2.setName("小和山公园");
        lvData.add(bean2);
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //头像
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this).load(R.drawable.personal_head).apply(requestOptions).into(header);


        lvAdapter=new collectionListViewAdapter(getActivity(),lvData,listView);
        listView.setAdapter(lvAdapter);



    }


}
