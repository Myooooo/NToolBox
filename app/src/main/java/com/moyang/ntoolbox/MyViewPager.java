package com.moyang.ntoolbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    //是否可以左右滑动
    // true 可以像Android原生ViewPager一样
    // false 禁止ViewPager左右滑动
    private boolean scrollable = false;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    // 左右滑动
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollable;
    }

    // 页面切换动画
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, scrollable);

    }

}