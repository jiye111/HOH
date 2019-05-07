package com.example.zhang.hoh.ui.fragment.lineup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.fragment.BaseMVPCompatFragment;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.adapter.list.lineupListViewAdapter;
import com.example.zhang.hoh.bean.list.lineupMainListViewBean;
import com.example.zhang.hoh.contract.lineup.LineupContract;
import com.example.zhang.hoh.presenter.lineup.LineupPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LineupFragment extends BaseMVPCompatFragment<LineupContract.LineupPresenter> implements  LineupContract.LineupView {

    @BindView(R.id.lineup_main_lv)
    ListView listView;

    private lineupListViewAdapter lvAdapter;
    private List<lineupMainListViewBean> lvData;

    public static LineupFragment newInstance() {
        Bundle args = new Bundle();
        LineupFragment fragment = new LineupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return LineupPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lineup;
    }

    @Override
    public void initData() {
        super.initData();
        lvData= new ArrayList<lineupMainListViewBean>();
        lineupMainListViewBean bean1=new lineupMainListViewBean();
        bean1.setImg(R.drawable.lineup_img_one);
        bean1.setLocal("淮海南路22号");
        bean1.setMealNumber("2");
        bean1.setMealTime("2019.3.3 19:22");
        bean1.setName("老张烧烤");
        bean1.setOrderTime("2019.2.2 12:22");
        lvData.add(bean1);

        lineupMainListViewBean bean2=new lineupMainListViewBean();
        bean2.setImg(R.drawable.lineup_img_one);
        bean2.setLocal("淮海南路22号");
        bean2.setMealNumber("2");
        bean2.setMealTime("2019.3.3 19:22");
        bean2.setName("老张烧烤");
        bean2.setOrderTime("2019.2.2 12:22");
        lvData.add(bean2);

        lineupMainListViewBean bean3=new lineupMainListViewBean();
        bean3.setImg(R.drawable.lineup_img_one);
        bean3.setLocal("淮海南路22号");
        bean3.setMealNumber("2");
        bean3.setMealTime("2019.3.3 19:22");
        bean3.setName("老张烧烤");
        bean3.setOrderTime("2019.2.2 12:22");
        lvData.add(bean3);

    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        lvAdapter=new lineupListViewAdapter(getActivity(),lvData,listView);
        listView.setAdapter(lvAdapter);
    }
}
