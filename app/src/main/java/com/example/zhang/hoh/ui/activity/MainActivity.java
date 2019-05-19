package com.example.zhang.hoh.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.sdk.AppManager;
import com.example.sdk.base.activity.BaseCompatActivity;
import com.example.sdk.utils.LogUtils;
import com.example.sdk.utils.StatusBarUtils;
import com.example.sdk.utils.ToastUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.ui.fragment.collection.CollectionFragment;
import com.example.zhang.hoh.ui.fragment.lineup.LineupFragment;
import com.example.zhang.hoh.ui.fragment.list.ListFragment;
import com.example.zhang.hoh.ui.fragment.map.MapFragment;
import com.example.zhang.hoh.ui.fragment.personal.PersonalFragment;
import com.example.zhang.hoh.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;


public class MainActivity  extends BaseCompatActivity {
    @BindView(R.id.fl_container_main)
    FrameLayout flContainer;
    @BindView(R.id.bnv_bar_main)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.main_personal_tab)
    LinearLayout tabPersonal;
    @BindView(R.id.main_list_tab)
    LinearLayout tabList;
    @BindView(R.id.main_map_tab)
    LinearLayout tabMap;
    @BindView(R.id.main_collection_tab)
    LinearLayout tabCollection;
    @BindView(R.id.main_lineup_tab)
    LinearLayout tabLineup;

    @OnClick({R.id.main_collection_tab,R.id.main_lineup_tab,R.id.main_list_tab,R.id.main_map_tab,
              R.id.main_personal_tab})
    void toFragment(LinearLayout tab){
        switch (tab.getId()){
            case R.id.main_personal_tab:
                setAllTabColor();
                tabPersonal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                showHideFragment(mFragments[FIRST]);
                break;
            case R.id.main_list_tab:
                setAllTabColor();
                tabList.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                showHideFragment(mFragments[SECOND]);
                break;
            case R.id.main_map_tab:
                setAllTabColor();
                tabMap.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                showHideFragment(mFragments[THIRD]);
                break;
            case R.id.main_collection_tab:
                setAllTabColor();
                tabCollection.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                showHideFragment(mFragments[FOURTH]);
                break;
            case R.id.main_lineup_tab:
                setAllTabColor();
                tabLineup.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                showHideFragment(mFragments[FIFTH]);
                break;
            default:
                break;
        }
    }

    private void setAllTabColor() {
        tabPersonal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.toolbar_bg));
        tabLineup.setBackgroundColor(ContextCompat.getColor(mContext, R.color.toolbar_bg));
        tabList.setBackgroundColor(ContextCompat.getColor(mContext, R.color.toolbar_bg));
        tabMap.setBackgroundColor(ContextCompat.getColor(mContext, R.color.toolbar_bg));
        tabCollection.setBackgroundColor(ContextCompat.getColor(mContext, R.color.toolbar_bg));
    }

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIFTH = 4;
    public SupportFragment[] mFragments = new SupportFragment[5];
    private static int RESTORE_ITEM_ID=0;
    //双击返回退出
    private long firstTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //设置状态栏颜色，并添加占位view
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.lightGray));
        RESTORE_ITEM_ID=0;
        if (savedInstanceState==null){
            mFragments[FIRST] = PersonalFragment.newInstance();
            mFragments[SECOND] = ListFragment.newInstance();
            mFragments[THIRD] = MapFragment.newInstance();
            mFragments[FOURTH] = CollectionFragment.newInstance();
            mFragments[FIFTH] = LineupFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_main, THIRD,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIFTH]);
        }else {
            mFragments[FIRST] = findFragment(PersonalFragment.class);
            mFragments[SECOND] = findFragment(ListFragment.class);
            mFragments[THIRD] = findFragment(MapFragment.class);
            mFragments[FOURTH] = findFragment(CollectionFragment.class);
            mFragments[FIFTH] = findFragment(LineupFragment.class);
        }
        tabMap.performClick();

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
//                .OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_item_personal:
//                        showHideFragment(mFragments[FIRST]);
//                        break;
//                    case R.id.menu_item_list:
//                        showHideFragment(mFragments[SECOND]);
//                        break;
//                    case R.id.menu_item_map:
//                        showHideFragment(mFragments[THIRD]);
//                        break;
//                    case R.id.menu_item_collection:
//                        showHideFragment(mFragments[FOURTH]);
//                        break;
//                    case R.id.menu_item_lineup:
//                        showHideFragment(mFragments[FIFTH]);
//                        break;
//                }
//                return true;
//            }
//        });
        //设置地图为默认选取
        //bottomNavigationView.setSelectedItemId(R.id.menu_item_map);

//        if (RESTORE_ITEM_ID != 0)
//            bottomNavigationView.setSelectedItemId(RESTORE_ITEM_ID);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // RESTORE_ITEM_ID = bottomNavigationView.getSelectedItemId();
    }

    @Override
    protected void ActiveFromBackstage() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtils.showToast("再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                //先结束所有的activity，再退出
                AppManager.getAppManager().finishAllActivity();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
