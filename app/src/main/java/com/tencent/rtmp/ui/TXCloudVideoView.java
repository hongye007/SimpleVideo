package com.tencent.rtmp.ui;

import android.widget.*;
import com.tencent.liteav.renderer.*;
import com.tencent.liteav.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class TXCloudVideoView extends FrameLayout implements View.OnTouchListener
{
    private static final String TAG = "TXCloudVideoView";
    protected TXDashBoard mDashBoard;
    private int mCaptureWidth;
    private int mCaptureHeight;
    private float mLeft;
    private float mRight;
    private float mTop;
    private float mBottom;
    protected TextureView mVideoView;
    protected TXCGLSurfaceView mGLSurfaceView;
    protected TXCFocusIndicatorView mFocusIndicatorView;
    protected SurfaceView mSurfaceView;
    private int mFocusAreaSize;
    private static final int FOCUS_AREA_SIZE_DP = 70;
    private String mUserId;
    private boolean mFocus;
    private boolean mZoom;
    private p mCapture;
    private int mCurrentScale;
    private ScaleGestureDetector mScaleGestureDetector;
    private ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener;
    private a mTouchFocusRunnable;
    
    public TXCloudVideoView(final Context context) {
        this(context, null);
    }
    
    public TXCloudVideoView(final Context context, final AttributeSet set) {
        super(context, set);
        this.mCaptureWidth = 0;
        this.mCaptureHeight = 0;
        this.mLeft = 0.0f;
        this.mRight = 0.0f;
        this.mTop = 0.0f;
        this.mBottom = 0.0f;
        this.mFocusAreaSize = 0;
        this.mUserId = "";
        this.mFocus = false;
        this.mZoom = false;
        this.mCurrentScale = 1;
        this.mScaleGestureDetector = null;
        this.mScaleGestureListener = (ScaleGestureDetector.OnScaleGestureListener)new ScaleGestureDetector.OnScaleGestureListener() {
            public boolean onScale(final ScaleGestureDetector scaleGestureDetector) {
                final int n = (TXCloudVideoView.this.mCapture != null) ? TXCloudVideoView.this.mCapture.e() : 0;
                if (n > 0) {
                    float scaleFactor = scaleGestureDetector.getScaleFactor();
                    if (scaleFactor > 1.0f) {
                        scaleFactor = 1.0f + 0.2f / n * (n - TXCloudVideoView.this.mCurrentScale);
                        if (scaleFactor <= 1.1f) {
                            scaleFactor = 1.1f;
                        }
                    }
                    else if (scaleFactor < 1.0f) {
                        scaleFactor = 1.0f - 0.2f / n * TXCloudVideoView.this.mCurrentScale;
                        if (scaleFactor >= 0.9f) {
                            scaleFactor = 0.9f;
                        }
                    }
                    int n2 = Math.round(TXCloudVideoView.this.mCurrentScale * scaleFactor);
                    if (n2 == TXCloudVideoView.this.mCurrentScale) {
                        if (scaleFactor > 1.0f) {
                            ++n2;
                        }
                        else if (scaleFactor < 1.0f) {
                            --n2;
                        }
                    }
                    if (n2 >= n) {
                        n2 = n;
                    }
                    if (n2 <= 1) {
                        n2 = 1;
                    }
                    if (scaleFactor > 1.0f) {
                        if (n2 < TXCloudVideoView.this.mCurrentScale) {
                            n2 = TXCloudVideoView.this.mCurrentScale;
                        }
                    }
                    else if (scaleFactor < 1.0f && n2 > TXCloudVideoView.this.mCurrentScale) {
                        n2 = TXCloudVideoView.this.mCurrentScale;
                    }
                    TXCloudVideoView.this.mCurrentScale = n2;
                    if (TXCloudVideoView.this.mCapture != null) {
                        TXCloudVideoView.this.mCapture.a(TXCloudVideoView.this.mCurrentScale);
                    }
                }
                return false;
            }
            
            public boolean onScaleBegin(final ScaleGestureDetector scaleGestureDetector) {
                return true;
            }
            
            public void onScaleEnd(final ScaleGestureDetector scaleGestureDetector) {
            }
        };
        this.mTouchFocusRunnable = new a();
        this.mDashBoard = new TXDashBoard(context);
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this.mScaleGestureListener);
    }
    
    public TXCloudVideoView(final SurfaceView mSurfaceView) {
        this(mSurfaceView.getContext(), null);
        this.mSurfaceView = mSurfaceView;
    }
    
    public void onResume() {
    }
    
    public void onPause() {
    }
    
    public void addVideoView(final TXCGLSurfaceView mglSurfaceView) {
        if (this.mGLSurfaceView != null) {
            this.removeView((View)this.mGLSurfaceView);
        }
        this.addView((View)(this.mGLSurfaceView = mglSurfaceView));
        this.resetLogView();
    }
    
    public void addVideoView(final TextureView mVideoView) {
        if (this.mVideoView != null) {
            this.removeView((View)this.mVideoView);
        }
        this.addView((View)(this.mVideoView = mVideoView));
        this.resetLogView();
    }
    
    public void removeVideoView() {
        if (this.mVideoView != null) {
            this.removeView((View)this.mVideoView);
            this.mVideoView = null;
        }
        if (this.mGLSurfaceView != null) {
            this.removeView((View)this.mGLSurfaceView);
            this.mGLSurfaceView = null;
        }
        this.mSurfaceView = null;
    }
    
    public void removeFocusIndicatorView() {
        if (this.mFocusIndicatorView != null) {
            this.removeView((View)this.mFocusIndicatorView);
            this.mFocusIndicatorView = null;
        }
    }
    
    public void onDestroy() {
    }
    
    public TextureView getVideoView() {
        return this.mVideoView;
    }
    
    public TXCGLSurfaceView getGLSurfaceView() {
        return this.mGLSurfaceView;
    }
    
    public SurfaceView getSurfaceView() {
        return this.mSurfaceView;
    }
    
    public void setRenderMode(final int n) {
    }
    
    public void setRenderRotation(final int n) {
    }
    
    public TextureView getHWVideoView() {
        return this.mVideoView;
    }
    
    public void clearLastFrame(final boolean b) {
        if (b) {
            this.setVisibility(8);
        }
    }
    
    public void onTouchFocus(final int n, final int n2) {
        if (this.mGLSurfaceView == null) {
            return;
        }
        if (n < 0 || n2 < 0) {
            if (this.mFocusIndicatorView != null) {
                this.mFocusIndicatorView.setVisibility(8);
            }
            return;
        }
        if (this.mFocusIndicatorView == null) {
            (this.mFocusIndicatorView = new TXCFocusIndicatorView(this.getContext())).setVisibility(0);
            this.addView((View)this.mFocusIndicatorView);
        }
        else if (this.indexOfChild((View)this.mFocusIndicatorView) != this.getChildCount() - 1) {
            this.removeView((View)this.mFocusIndicatorView);
            this.addView((View)this.mFocusIndicatorView);
        }
        final Rect touchRect = this.getTouchRect(n, n2, this.mGLSurfaceView.getWidth(), this.mGLSurfaceView.getHeight(), 1.0f);
        this.mFocusIndicatorView.show(touchRect.left, touchRect.top, touchRect.right - touchRect.left);
    }
    
    private Rect getTouchRect(final int n, final int n2, final int n3, final int n4, final float n5) {
        if (this.mFocusAreaSize == 0 && this.mGLSurfaceView != null) {
            this.mFocusAreaSize = (int)(70.0f * this.mGLSurfaceView.getResources().getDisplayMetrics().density + 0.5f);
        }
        final int intValue = (this.mFocusAreaSize * n5).intValue();
        final int clamp = this.clamp(n - intValue / 2, 0, n3 - intValue);
        final int clamp2 = this.clamp(n2 - intValue / 2, 0, n4 - intValue);
        return new Rect(clamp, clamp2, clamp + intValue, clamp2 + intValue);
    }
    
    private int clamp(final int n, final int n2, final int n3) {
        if (n > n3) {
            return n3;
        }
        if (n < n2) {
            return n2;
        }
        return n;
    }
    
    public void setMirror(final boolean b) {
    }
    
    public void disableLog(final boolean b) {
        if (this.mDashBoard != null) {
            this.mDashBoard.a(b);
        }
    }
    
    public void showLog(final boolean b) {
        if (this.mDashBoard != null) {
            this.mDashBoard.setShowLevel(b ? 2 : 0);
        }
    }
    
    public void clearLog() {
        if (this.mDashBoard != null) {
            this.mDashBoard.a();
        }
    }
    
    public void setLogText(final Bundle bundle, final Bundle bundle2, final int n) {
        if (this.mDashBoard != null) {
            this.mDashBoard.a(bundle, bundle2, n);
        }
    }
    
    protected void resetLogView() {
        if (this.mDashBoard != null) {
            this.removeView((View)this.mDashBoard);
            this.addView((View)this.mDashBoard);
        }
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        super.onLayout(b, n, n2, n3, n4);
        this.updateDbMargin();
        if (this.mDashBoard != null) {
            this.mDashBoard.setStatusTextSize((float)(px2dip(this.getContext(), (float)this.getWidth()) / 30.0));
            this.mDashBoard.setEventTextSize((float)(px2dip(this.getContext(), (float)this.getWidth()) / 25.0));
        }
        if (0 != this.mCaptureWidth && 0 != this.mCaptureHeight) {
            this.updateVideoViewSize(this.mCaptureWidth, this.mCaptureHeight);
        }
    }
    
    public void updateVideoViewSize(final int mCaptureWidth, final int mCaptureHeight) {
        Object o;
        if (null != this.mGLSurfaceView) {
            o = this.mGLSurfaceView;
        }
        else {
            if (null == this.mVideoView) {
                return;
            }
            o = this.mVideoView;
        }
        final int width = this.getWidth();
        final int height = this.getHeight();
        if (0 == mCaptureHeight || height == 0) {
            return;
        }
        this.mCaptureWidth = mCaptureWidth;
        this.mCaptureHeight = mCaptureHeight;
        final float n = mCaptureWidth * 1.0f / mCaptureHeight;
        final float n2 = width * 1.0f / height;
        int leftMargin = 0;
        int topMargin = 0;
        int width2 = width;
        int height2 = height;
        if (n > n2) {
            height2 = (int)(width2 / n);
            topMargin = (this.getHeight() - height2) / 2;
        }
        else {
            width2 = (int)(height2 * n);
            leftMargin = (this.getWidth() - width2) / 2;
        }
        final ViewGroup.LayoutParams layoutParams = ((View)o).getLayoutParams();
        LayoutParams layoutParams2;
        if (layoutParams != null) {
            layoutParams2 = (LayoutParams)layoutParams;
            if (layoutParams2.width == width2 && layoutParams2.height == height2) {
                return;
            }
            layoutParams2.width = width2;
            layoutParams2.height = height2;
        }
        else {
            layoutParams2 = new LayoutParams(width2, height2);
        }
        layoutParams2.leftMargin = leftMargin;
        layoutParams2.topMargin = topMargin;
        ((View)o).setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
    }
    
    public void setDashBoardStatusInfo(final CharSequence charSequence) {
        if (this.mDashBoard != null) {
            this.mDashBoard.a(charSequence);
        }
    }
    
    public void appendEventInfo(final String s) {
        if (this.mDashBoard != null) {
            this.mDashBoard.a(s);
        }
    }
    
    public void showVideoDebugLog(final int showLevel) {
        if (this.mDashBoard != null) {
            this.mDashBoard.setShowLevel(showLevel);
        }
    }
    
    public void setLogMargin(final float mLeft, final float mRight, final float mTop, final float mBottom) {
        this.mLeft = mLeft;
        this.mRight = mRight;
        this.mTop = mTop;
        this.mBottom = mBottom;
        if (this.mDashBoard != null) {
            this.mDashBoard.a((int)this.mLeft, (int)this.mTop, (int)this.mRight, (int)this.mBottom);
        }
    }
    
    public void setLogMarginRatio(final float n, final float n2, final float n3, final float n4) {
        this.getWidth();
        this.getHeight();
        this.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                TXCloudVideoView.this.mLeft = TXCloudVideoView.this.getWidth() * n;
                TXCloudVideoView.this.mRight = TXCloudVideoView.this.getWidth() * n2;
                TXCloudVideoView.this.mTop = TXCloudVideoView.this.getHeight() * n3;
                TXCloudVideoView.this.mBottom = TXCloudVideoView.this.getHeight() * n4;
                if (TXCloudVideoView.this.mDashBoard != null) {
                    TXCloudVideoView.this.mDashBoard.a((int)TXCloudVideoView.this.mLeft, (int)TXCloudVideoView.this.mTop, (int)TXCloudVideoView.this.mRight, (int)TXCloudVideoView.this.mBottom);
                }
            }
        }, 100L);
    }
    
    public String getUserId() {
        return this.mUserId;
    }
    
    public void setUserId(final String mUserId) {
        this.mUserId = mUserId;
    }
    
    public static int px2dip(final Context context, final float n) {
        return (int)(n / context.getResources().getDisplayMetrics().density + 0.5f);
    }
    
    private void updateDbMargin() {
        if (this.mDashBoard != null) {
            this.mDashBoard.a((int)this.mLeft, (int)this.mTop, (int)this.mRight, (int)this.mBottom);
        }
    }
    
    public void start(final boolean mFocus, final boolean mZoom, final p mCapture) {
        this.mFocus = mFocus;
        this.mZoom = mZoom;
        if (this.mFocus || this.mZoom) {
            this.setOnTouchListener((OnTouchListener)this);
            this.mCapture = mCapture;
        }
        if (this.mGLSurfaceView != null) {
            this.mGLSurfaceView.setVisibility(0);
        }
    }
    
    public void stop(final boolean b) {
        if (this.mFocus || this.mZoom) {
            this.setOnTouchListener((OnTouchListener)null);
        }
        this.mCapture = null;
        if (b && this.mGLSurfaceView != null) {
            this.mGLSurfaceView.setVisibility(8);
        }
    }
    
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() == 1 && motionEvent.getAction() == 0) {
            this.mTouchFocusRunnable.a(view);
            this.mTouchFocusRunnable.a(motionEvent);
            this.postDelayed((Runnable)this.mTouchFocusRunnable, 100L);
        }
        else if (motionEvent.getPointerCount() > 1 && motionEvent.getAction() == 2) {
            this.removeCallbacks((Runnable)this.mTouchFocusRunnable);
            this.onTouchFocus(-1, -1);
            if (this.mScaleGestureDetector != null && this.mZoom) {
                this.mScaleGestureDetector.onTouchEvent(motionEvent);
            }
        }
        if (this.mZoom && motionEvent.getAction() == 0) {
            this.performClick();
        }
        return this.mZoom;
    }
    
    private class a implements Runnable
    {
        private View b;
        private MotionEvent c;
        
        public void a(final View b) {
            this.b = b;
        }
        
        public void a(final MotionEvent c) {
            this.c = c;
        }
        
        @Override
        public void run() {
            if (TXCloudVideoView.this.mCapture != null && TXCloudVideoView.this.mFocus) {
                TXCloudVideoView.this.mCapture.a(this.c.getX() / this.b.getWidth(), this.c.getY() / this.b.getHeight());
            }
            if (TXCloudVideoView.this.mFocus) {
                TXCloudVideoView.this.onTouchFocus((int)this.c.getX(), (int)this.c.getY());
            }
        }
    }
}
