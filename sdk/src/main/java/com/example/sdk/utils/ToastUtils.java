package com.example.sdk.utils;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * <p>
 * toast工具类封装
 */
public class ToastUtils {
    private static Toast mToast = null;
    public static int mPosition = 0;

    /**
     * 显示一个toast提示
     *
     * @param resouceId toast字符串资源id
     */
    public static void showToast(int resouceId) {

        showToast(ResourcesUtils.getString(resouceId));

    }

    /**
     * 显示一个toast提示
     *
     * @param text toast字符串
     */
    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个toast提示
     *
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    public static void showToast(String text, int duration) {
        showToast(AppUtils.getContext(), text, duration);
    }

    /**
     * 显示一个toast提示
     *
     * @param context  context 上下文对象
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    public static void showToast(final Context context, final String text, final int duration) {
        /**
         * 保证运行在主线程
         */
        AppUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, text, duration);
                } else {
                    mToast.setText(text);
                    mToast.setDuration(duration);
                }
                mToast.show();
            }
        });
    }

    private void changePostionShow() {
        if (mPosition != 0) {
            switch (mPosition) {

                case Gravity.CENTER:
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                    break;
                case Gravity.TOP:
                    WindowManager wm = (WindowManager) AppUtils.getContext().getSystemService(AppUtils.getContext().WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    // 获取屏幕高度
                    int height = display.getHeight();
                    mToast.setGravity(Gravity.CENTER, 0, height/4);
                    break;
            }
        }
    }
}
