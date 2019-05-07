package com.example.sdk.base.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.example.sdk.AppManager;
import com.example.sdk.R;
import com.example.sdk.global.GlobalApplication;
import com.example.sdk.utils.AppUtils;
import com.example.sdk.utils.StatusBarUtils;

import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class BaseCompatActivity extends SupportActivity{
    protected GlobalApplication mApplication;
    static boolean isActive = true;
    protected boolean isTransAnim;
    protected Context mContext;//全局上下文对象

    static {
        //5.0以下兼容vector
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            //从后台唤醒
            isActive = true;
            ActiveFromBackstage();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnFreground()) {
            isActive = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        //fragment切换使用默认Vertical动画
        return new DefaultVerticalAnimator();
    }


    private void init(Bundle savedInstanceState) {
        setContentView(getLayoutId());//设置XML
        ButterKnife.bind(this);
//        StatusBarUtils.setTransparent(this);
//        StatusBarUtils.setColor(this, getResources().getColor(R.color.lightGray));

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();//初始化数据
        initView(savedInstanceState);//初始化界面
        AppManager.getAppManager().addActivity(this);
    }

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
        mContext = AppUtils.getContext();
        mApplication = (GlobalApplication) getApplication();
    }

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * <p>
     * 交由子类实现
     *
     * @return layout Id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param
     */
    protected abstract void  ActiveFromBackstage();

    /**
     * 是否在前台
     *
     * @return
     */
    public boolean isAppOnFreground() {
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if (app == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo a : app) {
            if (a.processName.equals(curPackageName) &&
                    a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
//        if (isTransAnim)
//            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
//                    .activity_start_zoom_out);
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
//        if (isTransAnim)
//            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
//                    .activity_start_zoom_out);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//        if (isTransAnim)
//            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
//                    .activity_start_zoom_out);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
//        if (isTransAnim)
//            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
//                    .activity_start_zoom_out);
    }

    @Override
    public void finish() {
        super.finish();
//        if (isTransAnim)
//            overridePendingTransition(R.anim.activity_finish_trans_in, R.anim
//                    .activity_finish_trans_out);
    }

}
