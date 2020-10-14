package com.tencent.liteav.network;

import java.util.*;
import android.content.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import android.os.*;

public class TXCRTMPDownloader extends TXIStreamDownloader
{
    private final String TAG = "network.TXCRTMPDownloader";
    private final int MSG_RECONNECT = 101;
    private final int MSG_EVENT = 102;
    private String mPlayUrl;
    private boolean mQuicChannel;
    private String mServerIp;
    private a mCurrentThread;
    private Object mRTMPThreadLock;
    private HandlerThread mThread;
    private Handler mHandler;
    private Vector<e> mVecPlayUrls;
    private boolean mHasTcpPlayUrl;
    private boolean mIsPlayRtmpAccStream;
    private boolean mEnableNearestIP;
    private int mConnectCountQuic;
    private int mConnectCountTcp;
    private int mLastNetworkType;
    
    public TXCRTMPDownloader(final Context context) {
        super(context);
        this.mPlayUrl = "";
        this.mQuicChannel = false;
        this.mServerIp = "";
        this.mCurrentThread = null;
        this.mRTMPThreadLock = null;
        this.mThread = null;
        this.mHandler = null;
        this.mIsPlayRtmpAccStream = false;
        this.mEnableNearestIP = false;
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.mLastNetworkType = 0;
        this.mRTMPThreadLock = new Object();
    }
    
    private void startInternal() {
        if (this.mQuicChannel) {
            ++this.mConnectCountQuic;
        }
        else {
            ++this.mConnectCountTcp;
        }
        synchronized (this.mRTMPThreadLock) {
            (this.mCurrentThread = new a(this.mPlayUrl, this.mQuicChannel)).start();
        }
    }
    
    private void postReconnectMsg() {
        final Message message = new Message();
        message.what = 101;
        if (this.mHandler != null) {
            this.mHandler.sendMessageDelayed(message, (long)(this.connectRetryInterval * 1000));
        }
    }
    
    private void reconnect(final boolean b) {
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                this.mCurrentThread.a();
                this.mCurrentThread = null;
            }
        }
        if (this.mHandler != null) {
            this.mHandler.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    TXCRTMPDownloader.this.internalReconnect(b);
                }
            }, (long)(this.connectRetryInterval * 1000));
        }
    }
    
    private void internalReconnect(boolean b) {
        if (!this.mIsRunning) {
            return;
        }
        if (this.mIsPlayRtmpAccStream && this.mLastNetworkType != f.e(this.mApplicationContext)) {
            this.mLastNetworkType = f.e(this.mApplicationContext);
            if (this.mRestartListener != null) {
                this.mRestartListener.onRestartDownloader();
            }
            return;
        }
        final boolean mQuicChannel = this.mQuicChannel;
        if (this.mIsPlayRtmpAccStream) {
            if (!this.mEnableNearestIP) {
                b = false;
            }
            if (mQuicChannel) {
                b = true;
            }
            if (b) {
                boolean b2 = false;
                if (this.mVecPlayUrls != null && !this.mVecPlayUrls.isEmpty()) {
                    final e e = this.mVecPlayUrls.get(0);
                    this.mVecPlayUrls.remove(0);
                    this.mPlayUrl = e.a;
                    this.mQuicChannel = e.b;
                    b2 = true;
                }
                if (!b2) {}
            }
        }
        if (mQuicChannel && this.mHasTcpPlayUrl) {
            this.sendNotifyEvent(2103);
            this.startInternal();
        }
        else if (this.connectRetryTimes < this.connectRetryLimit) {
            ++this.connectRetryTimes;
            TXCLog.i("network.TXCRTMPDownloader", "reconnect retry count:" + this.connectRetryTimes + " limit:" + this.connectRetryLimit);
            this.sendNotifyEvent(2103);
            this.startInternal();
        }
        else {
            TXCLog.e("network.TXCRTMPDownloader", "reconnect all times retried, send failed event ");
            this.sendNotifyEvent(-2301);
        }
    }
    
    private native long nativeInitRtmpHandler(final String p0, final String p1, final String p2, final boolean p3, final boolean p4, final boolean p5);
    
    private native void nativeUninitRtmpHandler(final long p0);
    
    private native void nativeStart(final long p0);
    
    private native void nativeStop(final long p0);
    
    private native TXCStreamDownloader.DownloadStats nativeGetStats(final long p0);
    
    private native void nativeRequestKeyFrame(final long p0, final String p1);
    
    @Override
    public void sendNotifyEvent(final int n, final String s) {
        if (s.isEmpty()) {
            this.sendNotifyEvent(n);
        }
        else {
            final Bundle bundle = new Bundle();
            bundle.putString("EVT_MSG", s);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            if (this.mNotifyListener != null) {
                this.mNotifyListener.onNotifyEvent(n, bundle);
            }
        }
    }
    
    @Override
    public void sendNotifyEvent(final int n) {
        if (n == 0 || n == 1) {
            this.reconnect(n == 1);
        }
        else {
            super.sendNotifyEvent(n);
        }
    }
    
    @Override
    public void startDownload(final Vector<e> mVecPlayUrls, final boolean mIsPlayRtmpAccStream, final boolean mEnableNearestIP, final boolean mEnableMessage, final boolean mEnableMetaData) {
        if (this.mIsRunning) {
            return;
        }
        if (mVecPlayUrls == null || mVecPlayUrls.isEmpty()) {
            return;
        }
        this.mEnableMessage = mEnableMessage;
        this.mEnableMetaData = mEnableMetaData;
        this.mIsPlayRtmpAccStream = mIsPlayRtmpAccStream;
        this.mEnableNearestIP = mEnableNearestIP;
        this.mVecPlayUrls = mVecPlayUrls;
        this.mHasTcpPlayUrl = false;
        for (int i = 0; i < this.mVecPlayUrls.size(); ++i) {
            if (!this.mVecPlayUrls.elementAt(i).b) {
                this.mHasTcpPlayUrl = true;
                break;
            }
        }
        final e e = this.mVecPlayUrls.get(0);
        this.mVecPlayUrls.remove(0);
        this.mPlayUrl = e.a;
        this.mQuicChannel = e.b;
        this.mIsRunning = true;
        TXCLog.i("network.TXCRTMPDownloader", "start pull with url:" + this.mPlayUrl + " quic:" + (this.mQuicChannel ? "yes" : "no"));
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.connectRetryTimes = 0;
        if (this.mThread == null) {
            (this.mThread = new HandlerThread("RTMP_PULL")).start();
        }
        this.mHandler = new Handler(this.mThread.getLooper()) {
            public void handleMessage(final Message message) {
                if (message.what == 101) {
                    TXCRTMPDownloader.this.startInternal();
                }
            }
        };
        this.startInternal();
    }
    
    @Override
    public void stopDownload() {
        if (!this.mIsRunning) {
            return;
        }
        this.mIsRunning = false;
        this.mVecPlayUrls.removeAllElements();
        this.mVecPlayUrls = null;
        this.mIsPlayRtmpAccStream = false;
        this.mEnableNearestIP = false;
        TXCLog.i("network.TXCRTMPDownloader", "stop pull");
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                this.mCurrentThread.a();
                this.mCurrentThread = null;
            }
        }
        if (this.mThread != null) {
            this.mThread.quit();
            this.mThread = null;
        }
        if (this.mHandler != null) {
            this.mHandler = null;
        }
    }
    
    @Override
    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                return this.mCurrentThread.b();
            }
            return null;
        }
    }
    
    @Override
    public String getCurrentStreamUrl() {
        return this.mPlayUrl;
    }
    
    @Override
    public boolean isQuicChannel() {
        return this.mQuicChannel;
    }
    
    @Override
    public int getConnectCountQuic() {
        return this.mConnectCountQuic;
    }
    
    @Override
    public int getConnectCountTcp() {
        return this.mConnectCountTcp;
    }
    
    @Override
    public void requestKeyFrame(final String s) {
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                this.mCurrentThread.a(s);
            }
        }
    }
    
    class a extends Thread
    {
        private long b;
        private String c;
        private boolean d;
        
        a(final String c, final boolean d) {
            super("RTMPDownLoad");
            this.b = 0L;
            this.c = c;
            this.d = d;
        }
        
        @Override
        public void run() {
            synchronized (this) {
                this.b = TXCRTMPDownloader.this.nativeInitRtmpHandler(TXCRTMPDownloader.this.mUserID, TXCRTMPDownloader.this.mOriginUrl, this.c, this.d, TXCRTMPDownloader.this.mEnableMessage, TXCRTMPDownloader.this.mEnableMetaData);
            }
            TXCRTMPDownloader.this.nativeStart(this.b);
            synchronized (this) {
                TXCRTMPDownloader.this.nativeUninitRtmpHandler(this.b);
                this.b = 0L;
            }
        }
        
        public void a() {
            synchronized (this) {
                if (this.b != 0L) {
                    TXCRTMPDownloader.this.nativeStop(this.b);
                }
            }
        }
        
        public TXCStreamDownloader.DownloadStats b() {
            TXCStreamDownloader.DownloadStats access$400 = null;
            synchronized (this) {
                if (this.b != 0L) {
                    access$400 = TXCRTMPDownloader.this.nativeGetStats(this.b);
                }
            }
            return access$400;
        }
        
        public void a(final String s) {
            synchronized (this) {
                if (this.b != 0L) {
                    TXCRTMPDownloader.this.nativeRequestKeyFrame(this.b, s);
                }
            }
        }
    }
}
