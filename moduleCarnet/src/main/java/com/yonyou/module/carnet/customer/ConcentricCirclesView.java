package com.yonyou.module.carnet.customer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yonyou.module.carnet.R;

public class ConcentricCirclesView extends View {
    private Paint mPaint;

    private Point mCenter;
    private float mRadius;
    private int pattern;

    private float ringSize;
    private float border_width;
    //<!--边界颜色-->
    private int border_color;
    private int circle_color;
    private boolean checked;

    public ConcentricCirclesView(Context context) {
        this(context, null);
    }

    public ConcentricCirclesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConcentricCirclesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CirclesView);
        ringSize = array.getDimension(R.styleable.CirclesView_circles_size, dp2px(12));
        border_color = array.getColor(R.styleable.CirclesView_circles_borderColor, getResources().getColor(R.color.change_pwd_text_white2));
        circle_color = array.getColor(R.styleable.CirclesView_circles_borderColor, getResources().getColor(R.color.text_red));
        border_width = array.getDimension(R.styleable.CirclesView_circles_borderWidth, dp2px(1));
        pattern = array.getInt(R.styleable.CirclesView_circles_pattern, 1);
        checked = array.getBoolean(R.styleable.CirclesView_circles_checked, false);

        // 创建圆心
        mCenter = new Point();
    }

    public void setBorderColor(int color) {
        border_color = getResources().getColor(color);
        invalidate();
    }

    public void setChecked(boolean checke) {
        checked = checke;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mCenter.x = w / 2;
            mCenter.y = h / 2;

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width, height;

        int cWidth = (int) ringSize;
        int mHeight = (int) ringSize;

        width = getHowToGetWH(widthMeasureSpec, cWidth);
        height = getHowToGetWH(heightMeasureSpec, mHeight);

        // 使用测量必须调用该方法
        setMeasuredDimension(width, height);
    }

    /**
     * 测量宽度和高度的方法
     */
    private int getHowToGetWH(int measureSpec, int mSize) {

        int specSize = MeasureSpec.getSize(measureSpec);

        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.AT_MOST:
                return Math.min(specSize, mSize);
            case MeasureSpec.UNSPECIFIED:
                return mSize;
            case MeasureSpec.EXACTLY:
                return specSize;
            default:
                return 0;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        mPaint.setColor(border_color);
        mPaint.setStrokeWidth(border_width);
        mPaint.setAntiAlias(true);

        switch (pattern) {
            case 1:
                mPaint.setStyle(Paint.Style.STROKE);
                break;
            case 2:
                mPaint.setStyle(Paint.Style.FILL);
                break;
        }


        canvas.drawCircle(mCenter.x, mCenter.y, ringSize / 2, mPaint);
        if (checked) {
            mPaint.setColor(circle_color);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mCenter.x, mCenter.y, ringSize / 2 * 8 / 10, mPaint);
        }

    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }

}
