package com.tencent.ijk.media.player;

import java.lang.ref.*;
import java.util.*;
import com.tencent.ijk.media.player.annotations.*;
import com.tencent.liteav.basic.util.*;
import android.os.*;

public final class IjkDownloadCenter
{
    private static final String TAG = "IjkDownloadCenter";
    private static final int MSG_RESUME = 100;
    private static final int MSG_STOP = 300;
    private static final int MSG_ERROR = 500;
    private static final int MSG_FINISH = 600;
    private static final int MSG_PROGRESS = 900;
    private static final int DC_PROP_STRING_URL = 1001;
    private static final int DC_PROP_LONG_SIZE = 1002;
    private static final int DC_PROP_LONG_DOWNLOAD_SIZE = 1003;
    private static final int DC_PROP_LONG_SPEED = 1004;
    private OnDownloadListener mListener;
    private EventHandler mEventHandler;
    protected Map<String, String> mHeaders;
    private static IjkDownloadCenter instance;
    private static volatile boolean mIsLibLoaded;
    private static final IjkLibLoader sLocalLibLoader;
    
    @Override
    protected void finalize() throws Throwable {
        try {
            this.native_download_free();
        }
        finally {
            super.finalize();
        }
    }
    
    public IjkDownloadCenter setListener(final OnDownloadListener mListener) {
        this.mListener = mListener;
        return this;
    }
    
    public IjkDownloadCenter(final IjkLibLoader ijkLibLoader) {
        loadLibrariesOnce(ijkLibLoader);
        final Looper mainLooper;
        if ((mainLooper = Looper.getMainLooper()) != null) {
            this.mEventHandler = new EventHandler(this, mainLooper);
        }
        else {
            this.mEventHandler = null;
        }
        this.native_download_setup(new WeakReference(this));
    }
    
    public IjkDownloadCenter() {
        this(IjkDownloadCenter.sLocalLibLoader);
    }
    
    public static IjkDownloadCenter getInstance() {
        if (IjkDownloadCenter.instance == null) {
            IjkDownloadCenter.instance = new IjkDownloadCenter();
        }
        return IjkDownloadCenter.instance;
    }
    
    public void setHeaders(final Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }
    
    public static void loadLibrariesOnce(IjkLibLoader sLocalLibLoader) {
        synchronized (IjkMediaPlayer.class) {
            if (!IjkDownloadCenter.mIsLibLoaded) {
                if (sLocalLibLoader == null) {
                    sLocalLibLoader = IjkDownloadCenter.sLocalLibLoader;
                }
                sLocalLibLoader.loadLibrary("txffmpeg");
                sLocalLibLoader.loadLibrary("txsdl");
                sLocalLibLoader.loadLibrary("txplayer");
                IjkDownloadCenter.mIsLibLoaded = true;
            }
        }
    }
    
    public void stop(final int n) {
        this.native_download_stop(n);
    }
    
    public int downloadHls(final String s, final String s2) {
        String s3 = "";
        if (this.mHeaders != null) {
            for (final String s4 : this.mHeaders.keySet()) {
                if (s3 == null) {
                    s3 = String.format("%s: %s", s4, this.mHeaders.get(s4));
                }
                else {
                    s3 = s3 + "\r\n" + String.format("%s: %s", s4, this.mHeaders.get(s4));
                }
            }
        }
        return this.native_download_hls_start(s, s2, s3);
    }
    
    @CalledByNative
    private static int hlsVerifyForNative(final Object o, final int n, final String s, final byte[] array) {
        if (o == null) {
            return 0;
        }
        final IjkDownloadCenter ijkDownloadCenter = (IjkDownloadCenter)((WeakReference)o).get();
        if (ijkDownloadCenter == null) {
            return 0;
        }
        return ijkDownloadCenter.mListener.hlsKeyVerify(ijkDownloadCenter, ijkDownloadCenter.convertMedia(n), s, array);
    }
    
    @CalledByNative
    private static void postEventFromNative(final Object o, final int n, final int n2, final int error, final Object o2) {
        if (o == null) {
            return;
        }
        final IjkDownloadCenter ijkDownloadCenter = (IjkDownloadCenter)((WeakReference)o).get();
        if (ijkDownloadCenter == null) {
            return;
        }
        if (ijkDownloadCenter.mEventHandler != null) {
            final NativeEvent nativeEvent = new NativeEvent();
            nativeEvent.media = ijkDownloadCenter.convertMedia(n2);
            nativeEvent.error = error;
            if (o2 != null) {
                nativeEvent.reason = (String)o2;
            }
            ijkDownloadCenter.mEventHandler.sendMessage(ijkDownloadCenter.mEventHandler.obtainMessage(n, n2, error, (Object)nativeEvent));
        }
    }
    
    private native void native_download_setup(final Object p0);
    
    private native void native_download_free();
    
    private native void native_download_stop(final int p0);
    
    private native int native_download_hls_start(final String p0, final String p1, final String p2);
    
    private native int download_get_task_prop_long(final int p0, final int p1);
    
    private native String download_get_task_prop_string(final int p0, final int p1);
    
    IjkDownloadMedia convertMedia(final int tid) {
        final String download_get_task_prop_string = this.download_get_task_prop_string(1001, tid);
        if (download_get_task_prop_string == null) {
            return null;
        }
        final IjkDownloadMedia ijkDownloadMedia = new IjkDownloadMedia();
        ijkDownloadMedia.size = this.download_get_task_prop_long(1002, tid);
        ijkDownloadMedia.downloadSize = this.download_get_task_prop_long(1003, tid);
        ijkDownloadMedia.speed = this.download_get_task_prop_long(1004, tid);
        ijkDownloadMedia.url = download_get_task_prop_string;
        ijkDownloadMedia.tid = tid;
        return ijkDownloadMedia;
    }
    
    static {
        IjkDownloadCenter.instance = null;
        IjkDownloadCenter.mIsLibLoaded = false;
        sLocalLibLoader = new IjkLibLoader() {
            @Override
            public void loadLibrary(final String s) throws UnsatisfiedLinkError, SecurityException {
                f.a(s);
            }
        };
    }
    
    private static class NativeEvent
    {
        IjkDownloadMedia media;
        int error;
        String reason;
    }
    
    private static class EventHandler extends Handler
    {
        private final WeakReference<IjkDownloadCenter> mWeakCenter;
        
        public EventHandler(final IjkDownloadCenter ijkDownloadCenter, final Looper looper) {
            super(looper);
            this.mWeakCenter = new WeakReference<IjkDownloadCenter>(ijkDownloadCenter);
        }
        
        public void handleMessage(final Message message) {
            final IjkDownloadCenter ijkDownloadCenter = this.mWeakCenter.get();
            if (ijkDownloadCenter == null || ijkDownloadCenter.mListener == null) {
                return;
            }
            final NativeEvent nativeEvent = (NativeEvent)message.obj;
            final IjkDownloadMedia media = nativeEvent.media;
            if (media == null) {
                return;
            }
            switch (message.what) {
                case 100: {
                    ijkDownloadCenter.mListener.downloadBegin(ijkDownloadCenter, media);
                    break;
                }
                case 600: {
                    ijkDownloadCenter.mListener.downloadFinish(ijkDownloadCenter, media);
                    break;
                }
                case 300: {
                    ijkDownloadCenter.mListener.downloadEnd(ijkDownloadCenter, media);
                    break;
                }
                case 900: {
                    ijkDownloadCenter.mListener.downloadProgress(ijkDownloadCenter, media);
                    break;
                }
                case 500: {
                    ijkDownloadCenter.mListener.downloadError(ijkDownloadCenter, media, nativeEvent.error, nativeEvent.reason);
                    break;
                }
            }
        }
    }
    
    public interface OnDownloadListener
    {
        void downloadBegin(final IjkDownloadCenter p0, final IjkDownloadMedia p1);
        
        void downloadEnd(final IjkDownloadCenter p0, final IjkDownloadMedia p1);
        
        void downloadFinish(final IjkDownloadCenter p0, final IjkDownloadMedia p1);
        
        void downloadError(final IjkDownloadCenter p0, final IjkDownloadMedia p1, final int p2, final String p3);
        
        void downloadProgress(final IjkDownloadCenter p0, final IjkDownloadMedia p1);
        
        int hlsKeyVerify(final IjkDownloadCenter p0, final IjkDownloadMedia p1, final String p2, final byte[] p3);
    }
}
