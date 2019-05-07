package com.example.sdk.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideUtils {

    /**
     * Created by wjs on 2017/11/20.
     */
    public static class BlurTransformation extends BitmapTransformation {

        private RenderScript rs;
        private int radius;

        public BlurTransformation(Context context, int radius ) {
//            super(context);
            rs = RenderScript.create(context);
            this.radius = radius;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

            // Allocate memory for Renderscript to work with
            //分配用于渲染脚本的内存
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(rs, input.getType());

            // Load up an instance of the specific script that we want to use.
            //加载我们想要使用的特定脚本的实例。
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // Set the blur radius
            //设置模糊半径
            script.setRadius(radius);

            // Start the ScriptIntrinisicBlur
            //启动ScriptIntrinisicBlur,
            script.forEach(output);

            // Copy the output to the blurred bitmap
            //将输出复制到模糊的位图
            output.copyTo(blurredBitmap);

            return blurredBitmap;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.update("blur transformation".getBytes());
        }
    }



    public static class GlideRoundTransform extends BitmapTransformation {

    private float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {

        this.radius = android.content.res.Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        android.graphics.Canvas canvas = new android.graphics.Canvas(result);
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setShader(new android.graphics.BitmapShader(source, android.graphics.BitmapShader.TileMode.CLAMP, android.graphics.BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        android.graphics.RectF rectF = new android.graphics.RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    public String getId() {
        return getClass().getName() + Math.round(radius);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

}



}
