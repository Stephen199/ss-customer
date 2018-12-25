package com.yonyou.sh.common.widget;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:09 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    private Context mContext;

    public MyGestureListener(Context context) {
        mContext = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (onSingleTapListener != null) {
            onSingleTapListener.onSingleTap();
        }
        return false;
    }

    private OnSingleTapListener onSingleTapListener;

    public void setOnSingleTapListener(OnSingleTapListener onSingleTapListener) {
        this.onSingleTapListener = onSingleTapListener;
    }

    public interface OnSingleTapListener{
        void onSingleTap();
    }
}
