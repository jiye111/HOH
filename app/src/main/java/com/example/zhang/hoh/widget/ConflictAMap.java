package com.example.zhang.hoh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.autonavi.amap.mapcore.interfaces.IAMap;

public class ConflictAMap extends MapView {

    public ConflictAMap(Context context) {
        super(context);
    }

    public ConflictAMap(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ConflictAMap(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ConflictAMap(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(true);

        return super.onInterceptTouchEvent(ev);
    }
}
