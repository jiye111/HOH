package com.example.sdk.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.sdk.base.BasePresenter;
import com.example.sdk.base.IBaseActivity;
import com.example.sdk.base.IBaseView;
import com.example.sdk.utils.LogUtils;
import com.example.sdk.utils.ToastUtils;

/**
 *
 * <p>
 * Mvp Activity基类
 */

public abstract class BaseMVPCompatActivity<P extends BasePresenter> extends BaseCompatActivity
        implements IBaseActivity {
    /**
     * presenter 具体的presenter由子类确定
     */
    protected P mPresenter;

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
        super.initData();
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mPresenter.attachMV(this);
            LogUtils.d("attach M V success.");
        }
    }

    /**
     *
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMV();
            LogUtils.d("detach M V success.");
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void back() {
        super.onBackPressedSupport();
    }


}
