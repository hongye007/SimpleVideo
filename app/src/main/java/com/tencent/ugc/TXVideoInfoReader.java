package com.tencent.ugc;

import android.content.*;
import java.lang.ref.*;
import java.util.concurrent.atomic.*;
import com.tencent.liteav.g.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.editer.*;
import android.net.*;
import java.io.*;
import android.os.*;
import android.graphics.*;

public class TXVideoInfoReader
{
    private Context mContext;
    private String TAG;
    private int mCount;
    private volatile WeakReference<OnSampleProgrocess> mListener;
    private a mGenerateImageThread;
    private static TXVideoInfoReader sInstance;
    private Handler mHandler;
    private String mVideoPath;
    private long mImageVideoDuration;
    private static final int RETRY_MAX_COUNT = 3;
    private AtomicInteger mRetryGeneThreadTimes;
    
    long getDuration(final String s) {
        try {
            final h h = new h();
            if (TextUtils.isEmpty((CharSequence)s)) {
                return 0L;
            }
            if (!new File(s).exists()) {
                return 0L;
            }
            h.a(s);
            final long a = h.a();
            h.i();
            return a;
        }
        catch (RuntimeException ex) {
            TXCLog.e(this.TAG, "get duration failed.", ex);
            return 0L;
        }
    }
    
    private TXVideoInfoReader(final Context mContext) {
        this.TAG = "TXVideoInfoReader";
        this.mContext = mContext;
        this.mRetryGeneThreadTimes = new AtomicInteger(0);
        this.mHandler = new Handler(Looper.getMainLooper());
    }
    
    private TXVideoInfoReader() {
        this.TAG = "TXVideoInfoReader";
        this.mRetryGeneThreadTimes = new AtomicInteger(0);
        this.mHandler = new Handler(Looper.getMainLooper());
    }
    
    @Deprecated
    public static TXVideoInfoReader getInstance() {
        if (TXVideoInfoReader.sInstance == null) {
            TXVideoInfoReader.sInstance = new TXVideoInfoReader();
        }
        return TXVideoInfoReader.sInstance;
    }
    
    public static TXVideoInfoReader getInstance(final Context context) {
        if (TXVideoInfoReader.sInstance == null) {
            TXVideoInfoReader.sInstance = new TXVideoInfoReader(context);
        }
        return TXVideoInfoReader.sInstance;
    }
    
    public TXVideoEditConstants.TXVideoInfo getVideoFileInfo(final String s) {
        TXCLog.d(this.TAG, "videoSource:" + s);
        if (Build.VERSION.SDK_INT < 18) {
            return null;
        }
        if (TextUtils.isEmpty((CharSequence)s)) {
            TXCLog.e(this.TAG, "videoSource is empty!!");
            return null;
        }
        if (!p.c(s)) {
            final File file = new File(s);
            if (!file.exists() || !file.canRead()) {
                TXCLog.e(this.TAG, "getVideoFileInfo -> file exist = " + file.exists() + " can read = " + file.canRead());
                return null;
            }
        }
        final TXVideoEditConstants.TXVideoInfo txVideoInfo = new TXVideoEditConstants.TXVideoInfo();
        final h h = new h();
        h.a(s);
        txVideoInfo.duration = h.a();
        TXCLog.i(this.TAG, "getVideoFileInfo: duration = " + txVideoInfo.duration);
        txVideoInfo.coverImage = h.h();
        txVideoInfo.fps = h.e();
        txVideoInfo.bitrate = (int)(h.f() / 1024L);
        txVideoInfo.audioSampleRate = h.g();
        final int b = h.b();
        TXCLog.i(this.TAG, "rotation: " + b);
        if (b == 90 || b == 270) {
            txVideoInfo.width = h.c();
            txVideoInfo.height = h.d();
        }
        else {
            txVideoInfo.width = h.d();
            txVideoInfo.height = h.c();
        }
        h.i();
        if (p.c(s)) {
            final Uri parse = Uri.parse(s);
            ParcelFileDescriptor openFileDescriptor = null;
            FileInputStream fileInputStream = null;
            try {
                if (this.mContext != null) {
                    openFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(parse, "r");
                    fileInputStream = new FileInputStream(openFileDescriptor.getFileDescriptor());
                    txVideoInfo.fileSize = fileInputStream.available();
                }
            }
            catch (Exception ex) {
                TXCLog.e(this.TAG, "getVideoFileInfo -> not found , uri = " + parse);
                txVideoInfo.fileSize = 0L;
            }
            finally {
                if (openFileDescriptor != null) {
                    try {
                        openFileDescriptor.close();
                    }
                    catch (IOException ex2) {}
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    }
                    catch (IOException ex3) {}
                }
            }
        }
        else {
            txVideoInfo.fileSize = new File(s).length();
        }
        return txVideoInfo;
    }
    
    public Bitmap getSampleImage(final long n, final String s) {
        if (TextUtils.isEmpty((CharSequence)s)) {
            TXCLog.w(this.TAG, "videoPath is null");
            return null;
        }
        if (!new File(s).exists()) {
            TXCLog.w(this.TAG, "videoPath is not exist");
            return null;
        }
        final h h = new h();
        h.a(s);
        this.mImageVideoDuration = h.a() * 1000L;
        long mImageVideoDuration = n * 1000L;
        if (mImageVideoDuration > this.mImageVideoDuration) {
            mImageVideoDuration = this.mImageVideoDuration;
        }
        if (this.mImageVideoDuration <= 0L) {
            TXCLog.w(this.TAG, "video duration is 0");
            h.i();
            return null;
        }
        final Bitmap a = h.a(mImageVideoDuration);
        if (a == null) {
            TXCLog.e(this.TAG, "getSampleImages failed!!!");
            h.i();
            return a;
        }
        TXCLog.d(this.TAG, "getSampleImages bmp  = " + a + ",time=" + mImageVideoDuration + ",duration=" + this.mImageVideoDuration);
        h.i();
        return a;
    }
    
    public void getSampleImages(final int mCount, final String s, final OnSampleProgrocess onSampleProgrocess) {
        this.mCount = mCount;
        this.mListener = new WeakReference<OnSampleProgrocess>(onSampleProgrocess);
        if (TextUtils.isEmpty((CharSequence)s)) {
            return;
        }
        if (!new File(s).exists()) {
            return;
        }
        this.cancelThread();
        (this.mGenerateImageThread = new a(s)).start();
        TXCLog.i(this.TAG, "getSampleImages: thread start");
    }
    
    public void cancel() {
        this.cancelThread();
        this.mHandler.removeCallbacksAndMessages((Object)null);
        if (this.mListener != null) {
            this.mListener.clear();
            this.mListener = null;
        }
    }
    
    private void cancelThread() {
        if (this.mGenerateImageThread != null && this.mGenerateImageThread.isAlive() && !this.mGenerateImageThread.isInterrupted()) {
            TXCLog.i(this.TAG, "cancelThread: thread cancel");
            this.mGenerateImageThread.interrupt();
            this.mGenerateImageThread = null;
        }
    }
    
    class a extends Thread
    {
        private h b;
        private String c;
        private long d;
        private volatile Bitmap e;
        private int f;
        
        public a(final String c) {
            this.f = TXVideoInfoReader.this.mListener.hashCode();
            this.c = c;
        }
        
        @Override
        public void run() {
            (this.b = new h()).a(this.c);
            this.d = this.b.a() * 1000L;
            final long n = this.d / TXVideoInfoReader.this.mCount;
            TXCLog.i(TXVideoInfoReader.this.TAG, String.format("run duration = %s ", this.d));
            TXCLog.i(TXVideoInfoReader.this.TAG, String.format("run count = %s ", TXVideoInfoReader.this.mCount));
            for (int n2 = 0; n2 < TXVideoInfoReader.this.mCount && !Thread.currentThread().isInterrupted(); ++n2) {
                long d = n * n2;
                if (d > this.d) {
                    d = this.d;
                }
                TXCLog.i(TXVideoInfoReader.this.TAG, String.format("current frame time = %s", d));
                Bitmap e = this.b.a(d);
                TXCLog.i(TXVideoInfoReader.this.TAG, String.format("the %s of bitmap is null ? %s", n2, e == null));
                if (e == null) {
                    TXCLog.w(TXVideoInfoReader.this.TAG, "getSampleImages failed!!!");
                    if (n2 == 0) {
                        if (TXVideoInfoReader.this.mRetryGeneThreadTimes.get() < 3) {
                            TXCLog.d(TXVideoInfoReader.this.TAG, "retry to get sample images");
                            TXVideoInfoReader.this.mHandler.post((Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    TXVideoInfoReader.this.getSampleImages(TXVideoInfoReader.this.mCount, a.this.c, (OnSampleProgrocess)TXVideoInfoReader.this.mListener.get());
                                    TXVideoInfoReader.this.mRetryGeneThreadTimes.getAndIncrement();
                                }
                            });
                            break;
                        }
                        break;
                    }
                    else if (this.e != null && !this.e.isRecycled()) {
                        TXCLog.i(TXVideoInfoReader.this.TAG, "copy last image");
                        e = this.e.copy(this.e.getConfig(), true);
                    }
                }
                this.e = e;
                if (TXVideoInfoReader.this.mRetryGeneThreadTimes.get() != 0) {
                    TXVideoInfoReader.this.mRetryGeneThreadTimes.getAndSet(0);
                }
                if (TXVideoInfoReader.this.mListener != null && TXVideoInfoReader.this.mListener.get() != null && TXVideoInfoReader.this.mCount > 0 && TXVideoInfoReader.this.mListener.hashCode() == this.f) {
                    TXVideoInfoReader.this.mHandler.post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (TXVideoInfoReader.this.mListener != null && TXVideoInfoReader.this.mListener.get() != null && TXVideoInfoReader.this.mListener.hashCode() == a.this.f) {
                                TXCLog.i(TXVideoInfoReader.this.TAG, "return image success");
                                ((OnSampleProgrocess)TXVideoInfoReader.this.mListener.get()).sampleProcess(n2, e);
                            }
                        }
                    });
                }
            }
            this.e = null;
            this.b.i();
        }
    }
    
    public interface OnSampleProgrocess
    {
        void sampleProcess(final int p0, final Bitmap p1);
    }
}
