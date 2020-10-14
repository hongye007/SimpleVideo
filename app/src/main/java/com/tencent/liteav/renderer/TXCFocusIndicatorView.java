package com.tencent.liteav.renderer;

import android.view.*;
import android.content.*;
import android.util.*;
import android.widget.*;
import android.view.animation.*;
import android.graphics.*;

public class TXCFocusIndicatorView extends View
{
    private Paint mPaint;
    private int mSize;
    private int mFocusAreaStroke;
    private ScaleAnimation mScaleAnimation;
    private static final int FOCUS_AREA_STROKE = 1;
    private Runnable mHideRunnable;
    
    public TXCFocusIndicatorView(final Context context) {
        super(context);
        this.mSize = 0;
        this.mFocusAreaStroke = 2;
        this.mHideRunnable = new Runnable() {
            @Override
            public void run() {
                TXCFocusIndicatorView.this.setVisibility(8);
            }
        };
        this.init(null, 0);
    }
    
    public TXCFocusIndicatorView(final Context context, final AttributeSet set) {
        super(context, set);
        this.mSize = 0;
        this.mFocusAreaStroke = 2;
        this.mHideRunnable = new Runnable() {
            @Override
            public void run() {
                TXCFocusIndicatorView.this.setVisibility(8);
            }
        };
        this.init(set, 0);
    }
    
    public TXCFocusIndicatorView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.mSize = 0;
        this.mFocusAreaStroke = 2;
        this.mHideRunnable = new Runnable() {
            @Override
            public void run() {
                TXCFocusIndicatorView.this.setVisibility(8);
            }
        };
        this.init(set, n);
    }
    
    private void init(final AttributeSet set, final int n) {
        this.mPaint = new Paint();
        this.mFocusAreaStroke = (int)(1.0f * this.getContext().getResources().getDisplayMetrics().density + 0.5f);
        (this.mScaleAnimation = new ScaleAnimation(1.3f, 1.0f, 1.3f, 1.0f, 1, 0.5f, 1, 0.5f)).setDuration(200L);
    }
    
    public void show(final int n, final int n2, final int mSize) {
        this.removeCallbacks(this.mHideRunnable);
        this.mScaleAnimation.cancel();
        this.mSize = mSize;
        final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.getLayoutParams();
        layoutParams.setMargins(n, n2, 0, 0);
        layoutParams.width = this.mSize;
        layoutParams.height = this.mSize;
        this.setVisibility(0);
        this.requestLayout();
        this.mScaleAnimation.reset();
        this.startAnimation((Animation)this.mScaleAnimation);
        this.postDelayed(this.mHideRunnable, 1000L);
    }
    
    protected void onDraw(final Canvas canvas) {
        canvas.save();
        final int n = this.mFocusAreaStroke / 2;
        final Rect rect = new Rect(n, n, this.mSize - n, this.mSize - n);
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float)(n * 2));
        canvas.drawRect(rect, this.mPaint);
        canvas.restore();
        super.onDraw(canvas);
    }
}
