package com.tencent.liteav.trtc.impl;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.*;
import com.tencent.trtc.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;
import java.util.concurrent.*;
import android.os.*;

public class TRTCCustomTextureUtil
{
    private static final String TAG = "TRTCCustomTextureUtil";
    private g mGLThreadHandler;
    private HandlerThread mEGLThread;
    private Object mEGLContext;
    private long mLastGLThreadId;
    private h mRotateFilter;
    private p mI4202RGBAFilter;
    private d mCaptureAndEnc;
    
    public TRTCCustomTextureUtil(final d mCaptureAndEnc) {
        this.mGLThreadHandler = null;
        this.mEGLThread = null;
        this.mEGLContext = null;
        this.mI4202RGBAFilter = null;
        this.mCaptureAndEnc = mCaptureAndEnc;
    }
    
    public void release() {
        this.stopThread();
    }
    
    public void sendCustomTexture(final TRTCCloudDef.TRTCVideoFrame trtcVideoFrame) {
        this.checkEGLContext(trtcVideoFrame);
        this.sendCustomTextureInternal(trtcVideoFrame);
    }
    
    private void sendCustomTextureInternal(final TRTCCloudDef.TRTCVideoFrame trtcVideoFrame) {
        synchronized (this) {
            if (this.mGLThreadHandler != null) {
                GLES20.glFinish();
                this.mGLThreadHandler.post((Runnable)new Runnable() {
                    final /* synthetic */ g val$glThreadHandler = TRTCCustomTextureUtil.this.mGLThreadHandler;
                    
                    @Override
                    public void run() {
                        this.val$glThreadHandler.d();
                        if (this.val$glThreadHandler.d) {
                            if (trtcVideoFrame.texture != null) {
                                trtcVideoFrame.texture.textureId = TRTCCustomTextureUtil.this.checkRotate(trtcVideoFrame.texture.textureId, trtcVideoFrame);
                                TRTCCustomTextureUtil.this.mCaptureAndEnc.a(trtcVideoFrame.texture.textureId, trtcVideoFrame.width, trtcVideoFrame.height, this.val$glThreadHandler.e.e(), trtcVideoFrame.timestamp, trtcVideoFrame.rotation);
                            }
                        }
                        else if (trtcVideoFrame.texture != null) {
                            trtcVideoFrame.texture.textureId = TRTCCustomTextureUtil.this.checkRotate(trtcVideoFrame.texture.textureId, trtcVideoFrame);
                            TRTCCustomTextureUtil.this.mCaptureAndEnc.a(trtcVideoFrame.texture.textureId, trtcVideoFrame.width, trtcVideoFrame.height, this.val$glThreadHandler.g.d(), trtcVideoFrame.timestamp, trtcVideoFrame.rotation);
                        }
                        else {
                            int n = 3;
                            if (trtcVideoFrame.pixelFormat == 1) {
                                n = 1;
                            }
                            else if (trtcVideoFrame.pixelFormat == 4) {
                                n = 3;
                            }
                            if (TRTCCustomTextureUtil.this.mI4202RGBAFilter == null) {
                                final p p = new p(n);
                                p.a(true);
                                if (!p.c()) {
                                    TXCLog.e("TRTCCustomTextureUtil", "mI4202RGBAFilter init failed!!, break init");
                                }
                                p.a(trtcVideoFrame.width, trtcVideoFrame.height);
                                TRTCCustomTextureUtil.this.mI4202RGBAFilter = p;
                            }
                            final p access$200 = TRTCCustomTextureUtil.this.mI4202RGBAFilter;
                            if (access$200 != null) {
                                GLES20.glViewport(0, 0, trtcVideoFrame.width, trtcVideoFrame.height);
                                access$200.a(trtcVideoFrame.data);
                                TRTCCustomTextureUtil.this.mCaptureAndEnc.a(TRTCCustomTextureUtil.this.checkRotate(access$200.r(), trtcVideoFrame), trtcVideoFrame.width, trtcVideoFrame.height, this.val$glThreadHandler.g.d(), trtcVideoFrame.timestamp, trtcVideoFrame.rotation);
                            }
                            else {
                                TRTCCustomTextureUtil.this.mCaptureAndEnc.a(trtcVideoFrame.data, n, trtcVideoFrame.width, trtcVideoFrame.height, this.val$glThreadHandler.g.d(), trtcVideoFrame.timestamp, trtcVideoFrame.rotation);
                            }
                        }
                    }
                });
            }
        }
    }
    
    private void checkEGLContext(final TRTCCloudDef.TRTCVideoFrame trtcVideoFrame) {
        if (trtcVideoFrame == null) {
            return;
        }
        int n = 0;
        if (this.mLastGLThreadId == Thread.currentThread().getId()) {
            if (trtcVideoFrame.texture != null) {
                if (trtcVideoFrame.texture.eglContext10 != null) {
                    n = (trtcVideoFrame.texture.eglContext10.equals(this.mEGLContext) ? 0 : 1);
                    if (n != 0) {
                        this.apiLog("CustomCapture egl10Context change!");
                    }
                }
                if (trtcVideoFrame.texture.eglContext14 != null) {
                    n = (trtcVideoFrame.texture.eglContext14.equals(this.mEGLContext) ? 0 : 1);
                    if (n != 0) {
                        this.apiLog("CustomCapture egl14Context change!");
                    }
                }
            }
        }
        else {
            n = 1;
            this.apiLog("CustomCapture eglContext's thread change!");
        }
        this.mLastGLThreadId = Thread.currentThread().getId();
        if (trtcVideoFrame.texture != null) {
            if (trtcVideoFrame.texture.eglContext10 != null) {
                this.mEGLContext = trtcVideoFrame.texture.eglContext10;
            }
            else {
                this.mEGLContext = trtcVideoFrame.texture.eglContext14;
            }
        }
        if (n != 0) {
            this.stopThread();
            this.startThread(trtcVideoFrame);
        }
    }
    
    private void startThread(final TRTCCloudDef.TRTCVideoFrame trtcVideoFrame) {
        if (trtcVideoFrame == null) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        synchronized (this) {
            if (this.mEGLThread == null) {
                (this.mEGLThread = new HandlerThread("customCaptureGLThread")).start();
                this.mGLThreadHandler = new g(this.mEGLThread.getLooper());
                if (trtcVideoFrame.texture == null) {
                    this.apiLog("CustomCapture buffer start egl10 thread");
                    this.mGLThreadHandler.d = false;
                    this.mGLThreadHandler.h = null;
                    this.mGLThreadHandler.a = 1280;
                    this.mGLThreadHandler.b = 720;
                    this.mGLThreadHandler.sendEmptyMessage(100);
                }
                else if (trtcVideoFrame.texture.eglContext10 != null) {
                    this.apiLog("CustomCapture texture start egl10 thread");
                    this.mGLThreadHandler.d = false;
                    this.mGLThreadHandler.h = trtcVideoFrame.texture.eglContext10;
                    this.mGLThreadHandler.a = 1280;
                    this.mGLThreadHandler.b = 720;
                    this.mGLThreadHandler.sendEmptyMessage(100);
                }
                else if (trtcVideoFrame.texture.eglContext14 != null && Build.VERSION.SDK_INT >= 17) {
                    this.apiLog("CustomCapture texture start egl14 thread");
                    this.mGLThreadHandler.d = true;
                    this.mGLThreadHandler.f = trtcVideoFrame.texture.eglContext14;
                    this.mGLThreadHandler.a = 1280;
                    this.mGLThreadHandler.b = 720;
                    this.mGLThreadHandler.sendEmptyMessage(100);
                }
                this.mGLThreadHandler.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TXCLog.i("TRTCCustomTextureUtil", "GLContext create finished!");
                        countDownLatch.countDown();
                    }
                });
            }
            else {
                countDownLatch.countDown();
            }
        }
        try {
            countDownLatch.await();
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    private synchronized void stopThread() {
        if (this.mGLThreadHandler != null) {
            final h mRotateFilter = this.mRotateFilter;
            this.mRotateFilter = null;
            final p mi4202RGBAFilter = this.mI4202RGBAFilter;
            this.mI4202RGBAFilter = null;
            this.mGLThreadHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (mRotateFilter != null) {
                        mRotateFilter.e();
                    }
                    if (mi4202RGBAFilter != null) {
                        mi4202RGBAFilter.e();
                    }
                    if (TRTCCustomTextureUtil.this.mCaptureAndEnc != null) {
                        TRTCCustomTextureUtil.this.apiLog("CustomCapture release");
                        TRTCCustomTextureUtil.this.mCaptureAndEnc.r();
                    }
                }
            });
            g.a(this.mGLThreadHandler, this.mEGLThread);
            this.apiLog("CustomCapture destroy egl thread");
        }
        this.mGLThreadHandler = null;
        this.mEGLThread = null;
    }
    
    private void apiLog(final String s) {
        TXCLog.i("TRTCCustomTextureUtil", "trtc_api " + s);
    }
    
    private int checkRotate(final int n, final TRTCCloudDef.TRTCVideoFrame trtcVideoFrame) {
        if (trtcVideoFrame == null || trtcVideoFrame.rotation == 0) {
            return n;
        }
        final int n2 = trtcVideoFrame.rotation * 90;
        if (this.mRotateFilter == null) {
            final h mRotateFilter = new h();
            mRotateFilter.c();
            mRotateFilter.a(true);
            mRotateFilter.a(trtcVideoFrame.width, trtcVideoFrame.height);
            this.mRotateFilter = mRotateFilter;
        }
        int l = n;
        final h mRotateFilter2 = this.mRotateFilter;
        if (mRotateFilter2 != null) {
            GLES20.glViewport(0, 0, trtcVideoFrame.width, trtcVideoFrame.height);
            final int n3 = (720 - n2) % 360;
            mRotateFilter2.a(trtcVideoFrame.width, trtcVideoFrame.height);
            mRotateFilter2.a(trtcVideoFrame.width, trtcVideoFrame.height, n3, null, trtcVideoFrame.width / (float)trtcVideoFrame.height, false, false);
            mRotateFilter2.a(l);
            l = mRotateFilter2.l();
            final int width = (n3 == 90 || n3 == 270) ? trtcVideoFrame.height : trtcVideoFrame.width;
            final int height = (n3 == 90 || n3 == 270) ? trtcVideoFrame.width : trtcVideoFrame.height;
            trtcVideoFrame.width = width;
            trtcVideoFrame.height = height;
        }
        return l;
    }
}
