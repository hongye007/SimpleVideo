package com.tencent.liteav.network;

import java.lang.ref.*;
import android.content.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import com.tencent.liteav.basic.util.*;
import android.os.*;
import java.net.*;
import org.json.*;
import com.tencent.liteav.basic.module.*;

public class TXCStreamUploader extends BaseObj implements b
{
    static final String TAG = "TXCStreamUploader";
    public static final int TXE_UPLOAD_MODE_REAL_TIME = 0;
    public static final int TXE_UPLOAD_MODE_AUDIO_ONLY = 1;
    public static final int TXE_UPLOAD_MODE_LINK_MIC = 2;
    public static final int TXE_UPLOAD_PROTOCOL_RTMP = 0;
    public static final int TXE_UPLOAD_PROTOCOL_AV = 1;
    public static final int RTMPSENDSTRATEGY_LIVE = 1;
    public static final int RTMPSENDSTRATEGY_REALTIME_TCP = 2;
    public static final int RTMPSENDSTRATEGY_REALTIME_QUIC = 3;
    private long mUploaderInstance;
    private Thread mThread;
    private Object mThreadLock;
    private boolean mIsPushing;
    private String mRtmpUrl;
    private boolean mQuicChannel;
    private int mChannelType;
    private boolean mEnableNearestIP;
    private c mIntelligentRoute;
    private int mLastNetworkType;
    private WeakReference<com.tencent.liteav.basic.b.b> mNotifyListener;
    private Context mContext;
    private ArrayList<com.tencent.liteav.network.a> mIpList;
    private int mCurrentRecordIdx;
    private int mRetryCount;
    private long mConnectSuccessTimeStamps;
    private long mGoodPushTime;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private final int MSG_RECONNECT = 101;
    private final int MSG_EVENT = 102;
    private final int MSG_REPORT_STATUS = 103;
    private final int MSG_RTMPPROXY_HEARTBEAT = 104;
    private long mLastTimeStamp;
    private UploadStats mLastUploadStats;
    private Vector<TXSNALPacket> mVecPendingNAL;
    private l mParam;
    private int mConnectCountQuic;
    private int mConnectCountTcp;
    private n mUploadQualityReport;
    private boolean mRtmpProxyEnable;
    private boolean mAudioMuted;
    private a mRtmpProxyParam;
    private Vector<String> mRtmpProxyIPList;
    private int mRtmpProxyIPIndex;
    private long mRtmpProxyInstance;
    private long mRtmpMsgRecvThreadInstance;
    private Object mRtmpProxyLock;
    private Object mRtmpMsgRecvThreadLock;
    HashMap<String, String> mMetaData;
    
    public void setNotifyListener(final com.tencent.liteav.basic.b.b b) {
        this.mNotifyListener = new WeakReference<com.tencent.liteav.basic.b.b>(b);
    }
    
    public TXCStreamUploader(final Context mContext, l mParam) {
        this.mUploaderInstance = 0L;
        this.mThread = null;
        this.mThreadLock = null;
        this.mIsPushing = false;
        this.mRtmpUrl = "";
        this.mQuicChannel = false;
        this.mChannelType = 0;
        this.mEnableNearestIP = true;
        this.mIntelligentRoute = null;
        this.mLastNetworkType = 0;
        this.mNotifyListener = null;
        this.mContext = null;
        this.mIpList = null;
        this.mCurrentRecordIdx = 0;
        this.mRetryCount = 0;
        this.mConnectSuccessTimeStamps = 0L;
        this.mGoodPushTime = 30000L;
        this.mHandlerThread = null;
        this.mHandler = null;
        this.mLastTimeStamp = 0L;
        this.mLastUploadStats = null;
        this.mVecPendingNAL = new Vector<TXSNALPacket>();
        this.mParam = null;
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.mUploadQualityReport = null;
        this.mRtmpProxyEnable = false;
        this.mAudioMuted = false;
        this.mRtmpProxyParam = new a();
        this.mRtmpProxyIPList = new Vector<String>();
        this.mRtmpProxyIPIndex = 0;
        this.mRtmpProxyInstance = 0L;
        this.mRtmpMsgRecvThreadInstance = 0L;
        this.mRtmpProxyLock = new Object();
        this.mRtmpMsgRecvThreadLock = new Object();
        this.mContext = mContext;
        if (mParam == null) {
            mParam = new l();
            mParam.a = 0;
            mParam.g = 3;
            mParam.f = 3;
            mParam.h = 40;
            mParam.i = 1000;
            mParam.j = true;
        }
        this.mParam = mParam;
        this.mThreadLock = new Object();
        this.mIntelligentRoute = new c();
        this.mIntelligentRoute.a = this;
        this.mUploaderInstance = 0L;
        this.mRetryCount = 0;
        this.mCurrentRecordIdx = 0;
        this.mIpList = null;
        this.mIsPushing = false;
        this.mThread = null;
        this.mRtmpUrl = null;
        this.mLastNetworkType = 0;
        this.mHandlerThread = null;
        this.mUploadQualityReport = new n(mContext);
        m.a().a(mContext);
    }
    
    public void setRetryInterval(final int g) {
        if (this.mParam != null) {
            this.mParam.g = g;
        }
    }
    
    public void setAudioInfo(final int e, final int d) {
        if (this.mParam != null) {
            this.mParam.d = d;
            this.mParam.e = e;
        }
    }
    
    public void setRetryTimes(final int f) {
        if (this.mParam != null) {
            this.mParam.f = f;
        }
    }
    
    public void setMode(final int a) {
        if (this.mParam != null) {
            this.mParam.a = a;
        }
    }
    
    private void postReconnectMsg(final String obj, final boolean b, final int n) {
        final Message message = new Message();
        message.what = 101;
        message.obj = obj;
        message.arg1 = (b ? 2 : 1);
        if (this.mHandler != null) {
            this.mHandler.sendMessageDelayed(message, (long)n);
        }
    }
    
    @Override
    public void onFetchDone(final int n, final ArrayList<com.tencent.liteav.network.a> mIpList) {
        if (!this.mIsPushing) {
            return;
        }
        int size = 0;
        if (mIpList != null) {
            TXCLog.e("TXCStreamUploader", "onFetchDone: code = " + n + " ip count = " + mIpList.size());
            if (n == 0) {
                this.mIpList = mIpList;
                size = mIpList.size();
                this.mCurrentRecordIdx = 0;
            }
        }
        if (size > 0) {
            int n2 = 0;
            String string = "";
            for (final com.tencent.liteav.network.a a : this.mIpList) {
                if (a != null && a.c && a.a != null && a.a.length() > 0) {
                    ++n2;
                }
                if (a != null) {
                    string = string + " " + this.getConfusionIP(a.a) + ":" + a.b;
                }
            }
            this.setStatusValue(7016, (long)n2);
            this.setStatusValue(7019, "{" + string + " }");
        }
        final b rtmpRealConnectInfo = this.getRtmpRealConnectInfo();
        this.postReconnectMsg(rtmpRealConnectInfo.a, rtmpRealConnectInfo.b, 0);
    }
    
    public String getConfusionIP(final String s) {
        if (s != null) {
            final int index = s.indexOf(".");
            if (index != -1) {
                final String substring = s.substring(index + 1);
                final int index2 = substring.indexOf(".");
                if (index2 != -1) {
                    return "A.B." + substring.substring(index2 + 1);
                }
            }
        }
        return s;
    }
    
    public int init() {
        return 0;
    }
    
    public String start(final String mRtmpUrl, final boolean mEnableNearestIP, final int mChannelType) {
        if (this.mIsPushing) {
            return this.mRtmpUrl;
        }
        this.mIsPushing = true;
        this.mConnectSuccessTimeStamps = 0L;
        this.mRetryCount = 0;
        this.mRtmpUrl = mRtmpUrl;
        this.mChannelType = mChannelType;
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.mRtmpProxyEnable = false;
        this.mRtmpProxyParam.a();
        this.mRtmpProxyIPList.clear();
        this.mRtmpProxyIPIndex = 0;
        this.mRtmpProxyInstance = 0L;
        this.mRtmpMsgRecvThreadInstance = 0L;
        this.setStatusValue(7016, 0L);
        this.setStatusValue(7017, 0L);
        this.setStatusValue(7018, 0L);
        this.mUploadQualityReport.a();
        TXCLog.i("TXCStreamUploader", "start push with url:" + this.mRtmpUrl + " enable nearest ip:" + (mEnableNearestIP ? "yes" : "no") + "channel type:" + mChannelType);
        if (f.e(this.mContext) == 0) {
            this.sendNotifyEvent(-1325);
            return this.mRtmpUrl;
        }
        this.mEnableNearestIP = mEnableNearestIP;
        if (this.mHandlerThread == null) {
            (this.mHandlerThread = new HandlerThread("RTMP_PUSH")).start();
        }
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) {
            public void handleMessage(final Message message) {
                switch (message.what) {
                    case 101: {
                        TXCStreamUploader.this.startPushTask((String)message.obj, message.arg1 == 2, 0);
                        break;
                    }
                    case 103: {
                        TXCStreamUploader.this.reportNetStatus();
                        break;
                    }
                    case 104: {
                        TXCStreamUploader.this.rtmpProxySendHeartBeat();
                        if (TXCStreamUploader.this.mHandler != null) {
                            TXCStreamUploader.this.mHandler.sendEmptyMessageDelayed(104, 2000L);
                            break;
                        }
                        break;
                    }
                }
            }
        };
        this.parseProxyInfo(mRtmpUrl);
        if (this.mRtmpProxyEnable) {
            this.mLastNetworkType = f.e(this.mContext);
            this.nativeCacheJNIParams();
            this.startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
        }
        else if (this.mEnableNearestIP && this.mLastNetworkType != f.e(this.mContext)) {
            TXCLog.i("TXCStreamUploader", "fetching nearest ip list");
            this.mLastNetworkType = f.e(this.mContext);
            this.mIntelligentRoute.a(mRtmpUrl, mChannelType);
        }
        else {
            this.startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
        }
        this.mHandler.sendEmptyMessageDelayed(103, 2000L);
        return this.mRtmpUrl;
    }
    
    public void stop() {
        if (!this.mIsPushing) {
            return;
        }
        this.mIsPushing = false;
        TXCLog.i("TXCStreamUploader", "stop push");
        if (this.mRtmpProxyEnable) {
            synchronized (this.mRtmpProxyLock) {
                this.nativeRtmpProxyLeaveRoom(this.mRtmpProxyInstance);
            }
        }
        synchronized (this.mThreadLock) {
            this.nativeStopPush(this.mUploaderInstance);
        }
        if (this.mHandlerThread != null) {
            this.mHandlerThread.getLooper().quit();
            this.mHandlerThread = null;
        }
        if (this.mHandler != null) {
            this.mHandler = null;
        }
        if (this.mRtmpProxyEnable) {
            this.nativeReleaseJNIParams();
        }
        this.mUploadQualityReport.c();
        this.mUploadQualityReport.a();
    }
    
    private void tryResetRetryCount() {
        if (this.mConnectSuccessTimeStamps != 0L && TXCTimeUtil.getTimeTick() - this.mConnectSuccessTimeStamps > this.mParam.f * (this.mParam.g + 13) * 1000) {
            this.mRetryCount = 0;
            this.mConnectSuccessTimeStamps = 0L;
            TXCLog.i("TXCStreamUploader", "reset retry count");
        }
    }
    
    public void setMetaData(final HashMap<String, String> mMetaData) {
        this.mMetaData = mMetaData;
    }
    
    public void pushAAC(final byte[] array, final long n) {
        this.tryResetRetryCount();
        synchronized (this.mThreadLock) {
            if (!this.mAudioMuted || !this.mRtmpProxyEnable) {
                this.nativePushAAC(this.mUploaderInstance, array, n);
            }
        }
    }
    
    public void pushNAL(final TXSNALPacket txsnalPacket) {
        this.tryResetRetryCount();
        synchronized (this.mThreadLock) {
            if (this.mUploaderInstance != 0L) {
                if (txsnalPacket != null && txsnalPacket.nalData != null && txsnalPacket.nalData.length > 0) {
                    this.nativePushNAL(this.mUploaderInstance, txsnalPacket.nalData, txsnalPacket.nalType, txsnalPacket.frameIndex, txsnalPacket.pts, txsnalPacket.dts);
                }
            }
            else {
                if (txsnalPacket.nalType == 0) {
                    this.mVecPendingNAL.removeAllElements();
                }
                this.mVecPendingNAL.add(txsnalPacket);
            }
        }
    }
    
    public void setAudioMute(final boolean mAudioMuted) {
        synchronized (this.mThreadLock) {
            this.mAudioMuted = mAudioMuted;
            if (this.mRtmpProxyEnable && this.mUploaderInstance != 0L) {
                this.nativeSetSendStrategy(this.mUploaderInstance, this.mParam.m ? (this.mQuicChannel ? 3 : 2) : 1, false);
            }
        }
    }
    
    public void setDropEanble(final boolean b) {
        TXCLog.i("TXCStreamUploader", "drop enable " + (b ? "yes" : "no"));
        synchronized (this.mThreadLock) {
            this.nativeEnableDrop(this.mUploaderInstance, b);
        }
    }
    
    public void setVideoDropParams(final boolean j, final int h, final int i) {
        TXCLog.i("TXCStreamUploader", "drop params wait i frame:" + (j ? "yes" : "no") + " max video count:" + h + " max video cache time: " + i + " ms");
        synchronized (this.mThreadLock) {
            this.mParam.j = j;
            this.mParam.h = h;
            this.mParam.i = i;
            if (this.mUploaderInstance != 0L) {
                this.nativeSetVideoDropParams(this.mUploaderInstance, this.mParam.j, this.mParam.h, this.mParam.i);
            }
        }
    }
    
    public void setSendStrategy(final boolean m, final boolean n) {
        this.mParam.m = m;
        this.mParam.n = n;
        this.mUploadQualityReport.a(m);
        int n2 = m ? (this.mQuicChannel ? 3 : 2) : 1;
        if (!this.mRtmpProxyEnable && (this.mIpList == null || this.mIpList.size() == 0)) {
            n2 = 1;
        }
        synchronized (this.mThreadLock) {
            if (this.mUploaderInstance != 0L) {
                this.nativeSetSendStrategy(this.mUploaderInstance, n2, n);
            }
        }
        this.setStatusValue(7020, (long)n2);
    }
    
    public UploadStats getUploadStats() {
        final UploadStats nativeGetStats;
        synchronized (this.mThreadLock) {
            nativeGetStats = this.nativeGetStats(this.mUploaderInstance);
            if (nativeGetStats != null) {
                nativeGetStats.channelType = (this.mQuicChannel ? 2L : 1L);
            }
        }
        return nativeGetStats;
    }
    
    private void startPushTask(final String s, final boolean b, final int n) {
        TXCLog.i("TXCStreamUploader", "start push task");
        if (this.mQuicChannel != b && this.mQuicChannel) {
            Monitor.a(2, String.format("Network: switch push channel from quic to tcp.[retryCount:%d][retryLimit:%d]", this.mRetryCount, this.mParam.f), "", 0);
        }
        if (b) {
            this.setStatusValue(7017, (long)(++this.mConnectCountQuic));
        }
        else {
            this.setStatusValue(7018, (long)(++this.mConnectCountTcp));
        }
        (this.mThread = new Thread("RTMPUpload") {
            @Override
            public void run() {
                while (TXCStreamUploader.this.mUploaderInstance != 0L) {
                    try {
                        Thread.sleep(100L, 0);
                    }
                    catch (InterruptedException ex) {}
                }
                TXCStreamUploader.this.mUploadQualityReport.b();
                TXCStreamUploader.this.mUploadQualityReport.a(TXCStreamUploader.this.mParam.m);
                TXCStreamUploader.this.mUploadQualityReport.a(TXCStreamUploader.this.mRtmpUrl);
                TXCStreamUploader.this.mUploadQualityReport.a(b, TXCStreamUploader.this.getAddressFromUrl(s));
                synchronized (TXCStreamUploader.this.mThreadLock) {
                    TXCStreamUploader.this.mQuicChannel = b;
                    int n = TXCStreamUploader.this.mParam.m ? (TXCStreamUploader.this.mQuicChannel ? 3 : 2) : 1;
                    if (!TXCStreamUploader.this.mRtmpProxyEnable) {
                        if (TXCStreamUploader.this.mIpList == null || TXCStreamUploader.this.mIpList.size() == 0) {
                            n = 1;
                        }
                    }
                    else if (TXCStreamUploader.this.mAudioMuted) {
                        TXCStreamUploader.this.mParam.n = false;
                    }
                    TXCStreamUploader.this.setStatusValue(7020, (long)n);
                    TXCStreamUploader.this.mUploaderInstance = TXCStreamUploader.this.nativeInitUploader(TXCStreamUploader.this.mRtmpUrl, s, b, TXCStreamUploader.this.mParam.e, TXCStreamUploader.this.mParam.d, TXCStreamUploader.this.mParam.a, TXCStreamUploader.this.mParam.c, TXCStreamUploader.this.mParam.h, 16, n, TXCStreamUploader.this.mParam.n, TXCStreamUploader.this.mParam.o, TXCStreamUploader.this.mMetaData);
                    if (TXCStreamUploader.this.mUploaderInstance != 0L) {
                        TXCStreamUploader.this.nativeSetVideoDropParams(TXCStreamUploader.this.mUploaderInstance, TXCStreamUploader.this.mParam.j, TXCStreamUploader.this.mParam.h, TXCStreamUploader.this.mParam.i);
                        int n2 = 0;
                        for (final TXSNALPacket txsnalPacket : TXCStreamUploader.this.mVecPendingNAL) {
                            if (n2 == 0 && txsnalPacket.nalType == 0) {
                                n2 = 1;
                            }
                            if (n2 != 0) {
                                TXCStreamUploader.this.nativePushNAL(TXCStreamUploader.this.mUploaderInstance, txsnalPacket.nalData, txsnalPacket.nalType, txsnalPacket.frameIndex, txsnalPacket.pts, txsnalPacket.dts);
                            }
                        }
                        TXCStreamUploader.this.mVecPendingNAL.removeAllElements();
                    }
                }
                if (TXCStreamUploader.this.mRtmpProxyEnable) {
                    synchronized (TXCStreamUploader.this.mRtmpProxyLock) {
                        TXCStreamUploader.this.mRtmpProxyInstance = TXCStreamUploader.this.nativeInitRtmpProxyInstance(TXCStreamUploader.this.mRtmpProxyParam.a, TXCStreamUploader.this.mRtmpProxyParam.b, TXCStreamUploader.this.mRtmpProxyParam.c, TXCStreamUploader.this.mRtmpProxyParam.d, TXCStreamUploader.this.mRtmpProxyParam.e, TXCStreamUploader.this.mRtmpProxyParam.f, TXCStreamUploader.this.mRtmpProxyParam.g, TXCStreamUploader.this.mRtmpProxyParam.h, TXCStreamUploader.this.mRtmpProxyParam.i, TXCStreamUploader.this.mRtmpProxyParam.j);
                    }
                    synchronized (TXCStreamUploader.this.mRtmpMsgRecvThreadLock) {
                        TXCStreamUploader.this.mRtmpMsgRecvThreadInstance = TXCStreamUploader.this.nativeInitRtmpMsgRecvThreadInstance(TXCStreamUploader.this.mRtmpProxyInstance, TXCStreamUploader.this.mUploaderInstance);
                    }
                }
                TXCStreamUploader.this.nativeOnThreadRun(TXCStreamUploader.this.mUploaderInstance);
                if (TXCStreamUploader.this.mRtmpProxyEnable) {
                    synchronized (TXCStreamUploader.this.mRtmpMsgRecvThreadLock) {
                        TXCStreamUploader.this.nativeRtmpMsgRecvThreadStop(TXCStreamUploader.this.mRtmpMsgRecvThreadInstance);
                        TXCStreamUploader.this.nativeUninitRtmpMsgRecvThreadInstance(TXCStreamUploader.this.mRtmpMsgRecvThreadInstance);
                        TXCStreamUploader.this.mRtmpMsgRecvThreadInstance = 0L;
                    }
                    synchronized (TXCStreamUploader.this.mRtmpProxyLock) {
                        TXCStreamUploader.this.nativeUninitRtmpProxyInstance(TXCStreamUploader.this.mRtmpProxyInstance);
                        TXCStreamUploader.this.mRtmpProxyInstance = 0L;
                    }
                }
                synchronized (TXCStreamUploader.this.mThreadLock) {
                    TXCStreamUploader.this.nativeUninitUploader(TXCStreamUploader.this.mUploaderInstance);
                    TXCStreamUploader.this.mUploaderInstance = 0L;
                }
            }
        }).start();
    }
    
    private void stopPushTask() {
        TXCLog.i("TXCStreamUploader", "stop push task");
        synchronized (this.mThreadLock) {
            this.mVecPendingNAL.removeAllElements();
            this.nativeStopPush(this.mUploaderInstance);
        }
    }
    
    private b getRtmpRealConnectInfo() {
        if (!this.mEnableNearestIP) {
            return new b(this.mRtmpUrl, false);
        }
        if (this.mIpList == null) {
            return new b(this.mRtmpUrl, false);
        }
        if (this.mCurrentRecordIdx >= this.mIpList.size() || this.mCurrentRecordIdx < 0) {
            return new b(this.mRtmpUrl, false);
        }
        final com.tencent.liteav.network.a a = this.mIpList.get(this.mCurrentRecordIdx);
        final String[] split = this.mRtmpUrl.split("://");
        if (split.length < 2) {
            return new b(this.mRtmpUrl, false);
        }
        final String[] split2 = split[1].split("/");
        if (a.a.split(":").length > 1 && !a.a.startsWith("[")) {
            split2[0] = "[" + a.a + "]:" + a.b;
        }
        else {
            split2[0] = a.a + ":" + a.b;
        }
        final StringBuilder sb = new StringBuilder(split2[0]);
        for (int i = 1; i < split2.length; ++i) {
            sb.append("/");
            sb.append(split2[i]);
        }
        return new b(split[0] + "://" + sb.toString(), a.c);
    }
    
    private boolean nextRecordIdx(final boolean b) {
        if (this.mIpList == null || this.mIpList.size() == 0) {
            return false;
        }
        if (b) {
            final com.tencent.liteav.network.a a = this.mIpList.get(this.mCurrentRecordIdx);
            ++a.e;
        }
        if (this.mCurrentRecordIdx + 1 < this.mIpList.size()) {
            ++this.mCurrentRecordIdx;
            return true;
        }
        return false;
    }
    
    private String getAddressFromUrl(final String s) {
        if (s != null) {
            final int index = s.indexOf("://");
            if (index != -1) {
                final String substring = s.substring(index + 3);
                final int index2 = substring.indexOf("/");
                if (index2 != -1) {
                    return substring.substring(0, index2);
                }
            }
        }
        return "";
    }
    
    private void reconnect(final boolean b) {
        this.stopPushTask();
        if (this.mHandler != null) {
            this.mHandler.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    TXCStreamUploader.this.internalReconnect(b);
                }
            }, (long)(this.mParam.g * 1000));
        }
    }
    
    private void internalReconnect(boolean b) {
        if (!this.mIsPushing) {
            return;
        }
        if (this.mRtmpProxyEnable) {
            if (this.mLastNetworkType != f.e(this.mContext)) {
                TXCLog.e("TXCStreamUploader", "reconnect network switch from " + this.mLastNetworkType + " to " + f.e(this.mContext));
                this.mLastNetworkType = f.e(this.mContext);
                Monitor.a(2, "WebrtcRoom: need enter again by user", "", this.mRetryCount = 0);
                this.sendNotifyEvent(1021, String.format("Network type has changed. Need to re-enter the room", new Object[0]));
                return;
            }
            if (this.mRetryCount < this.mParam.f) {
                ++this.mRetryCount;
                Monitor.a(2, String.format("Network: reconnecting to upload server with quic.[addr:%s][retryCount:%d][retryLimit:%d]", this.mRtmpProxyParam.h, this.mRetryCount, this.mParam.f), "", 0);
                TXCEventRecorderProxy.a(this.mRtmpUrl, 91003, -1L, -1L, "reconnect rtmp-proxy server(econnect retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f + ")", 0);
                this.sendNotifyEvent(1102);
                this.startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
            }
            else if (this.getNextRtmpProxyIP()) {
                this.mRetryCount = 0;
                Monitor.a(2, String.format("Network: reconnecting to upload server with quic.[addr:%s][retryCount:%d][retryLimit:%d]", this.mRtmpProxyParam.h, this.mRetryCount, this.mParam.f), "", 0);
                this.sendNotifyEvent(1102);
                this.startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
            }
            else {
                TXCEventRecorderProxy.a(this.mRtmpUrl, 91002, -1L, -1L, "connect rtmp-proxy server failed(try all addresses)", 0);
                this.sendNotifyEvent(-1324);
            }
        }
        else {
            this.mUploadQualityReport.c();
            if (this.mEnableNearestIP && this.mLastNetworkType != f.e(this.mContext)) {
                TXCLog.e("TXCStreamUploader", "reconnect network switch from " + this.mLastNetworkType + " to " + f.e(this.mContext));
                this.mLastNetworkType = f.e(this.mContext);
                this.mIntelligentRoute.a(this.mRtmpUrl, this.mChannelType);
                this.mRetryCount = 0;
                return;
            }
            if (!this.mEnableNearestIP) {
                b = false;
            }
            if (this.mQuicChannel) {
                b = true;
            }
            if (b && !this.nextRecordIdx(true)) {
                TXCLog.e("TXCStreamUploader", "reconnect: try all addresses failed");
                TXCEventRecorderProxy.a(this.mRtmpUrl, 91002, -1L, -1L, "connect upload server failed(try all addresses failed)", 0);
            }
            final b rtmpRealConnectInfo = this.getRtmpRealConnectInfo();
            final String addressFromUrl = this.getAddressFromUrl(rtmpRealConnectInfo.a);
            TXCLog.e("TXCStreamUploader", "reconnect change ip: " + addressFromUrl + " enableNearestIP: " + this.mEnableNearestIP + " last channel type: " + (this.mQuicChannel ? "Q Channel" : "TCP"));
            if (this.mQuicChannel) {
                TXCLog.e("TXCStreamUploader", "reconnect last channel type is Q Channel\uff0cignore retry limit");
                Monitor.a(2, String.format("Network: reconnecting to upload server with quic.[addr:%s]", addressFromUrl), "", 0);
                this.startPushTask(rtmpRealConnectInfo.a, rtmpRealConnectInfo.b, 0);
                this.sendNotifyEvent(1102);
            }
            else {
                TXCLog.e("TXCStreamUploader", "reconnect retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f);
                if (this.mRetryCount < this.mParam.f) {
                    ++this.mRetryCount;
                    Monitor.a(2, String.format("Network: reconnecting to upload server with tcp.[addr:%s][retryCount:%d][retryLimit:%d]", addressFromUrl, this.mRetryCount, this.mParam.f), "", 0);
                    TXCEventRecorderProxy.a(this.mRtmpUrl, 91003, -1L, -1L, "reconnect upload server:(retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f + ")", 0);
                    this.startPushTask(rtmpRealConnectInfo.a, rtmpRealConnectInfo.b, 0);
                    this.sendNotifyEvent(1102);
                }
                else {
                    TXCLog.e("TXCStreamUploader", "reconnect: try all times failed");
                    TXCEventRecorderProxy.a(this.mRtmpUrl, 91002, -1L, -1L, "connect upload server failed(try all times failed)", 0);
                    this.sendNotifyEvent(-1324);
                }
            }
        }
    }
    
    private void sendNotifyEvent(final int n, final String s) {
        if (s == null || s.isEmpty()) {
            this.sendNotifyEvent(n);
        }
        else {
            final Bundle bundle = new Bundle();
            bundle.putString("EVT_MSG", s);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            f.a(this.mNotifyListener, n, bundle);
        }
        if (n == 1002) {
            final UploadStats uploadStats = this.getUploadStats();
            if (uploadStats != null) {
                this.mUploadQualityReport.a(uploadStats.dnsparseTimeCost, uploadStats.connectTimeCost, uploadStats.handshakeTimeCost);
            }
        }
        else if (n == 1101) {
            this.mUploadQualityReport.d();
        }
    }
    
    private void sendNotifyEvent(final int n) {
        if (n == 0) {
            this.reconnect(false);
        }
        else if (n == 1) {
            this.reconnect(true);
        }
        else {
            if (n == 1001) {
                this.mConnectSuccessTimeStamps = TXCTimeUtil.getTimeTick();
            }
            if (n == 1026) {
                if (this.mRtmpProxyEnable) {
                    synchronized (this.mRtmpMsgRecvThreadLock) {
                        this.nativeRtmpMsgRecvThreadStart(this.mRtmpMsgRecvThreadInstance);
                    }
                    synchronized (this.mRtmpProxyLock) {
                        this.nativeRtmpProxyEnterRoom(this.mRtmpProxyInstance);
                    }
                    if (this.mHandler != null) {
                        this.mHandler.sendEmptyMessageDelayed(104, 2000L);
                    }
                }
                return;
            }
            if (this.mNotifyListener != null) {
                final Bundle bundle = new Bundle();
                switch (n) {
                    case -1324: {
                        bundle.putString("EVT_MSG", "Failed to connect all IPs, abort connection.");
                        break;
                    }
                    case -1307: {
                        bundle.putString("EVT_MSG", "failed to connect server for several times, abort connection");
                        break;
                    }
                    case 3008: {
                        bundle.putString("EVT_MSG", "No data is sent for more than 30s. Actively disconnect");
                        break;
                    }
                    case -1325: {
                        bundle.putString("EVT_MSG", "No internet. Please check if WiFi or mobile data is turned on");
                        break;
                    }
                    case 3009: {
                        bundle.putString("EVT_MSG", "Failed to connect server");
                        break;
                    }
                    case 1001: {
                        bundle.putString("EVT_MSG", "Already connected to rtmp server");
                        break;
                    }
                    case 3003: {
                        bundle.putString("EVT_MSG", "RTMP servers handshake failed");
                        break;
                    }
                    case 1101: {
                        bundle.putString("EVT_MSG", "Insufficient upstream bandwidth. Data transmission is not timely");
                        break;
                    }
                    case 1002: {
                        bundle.putString("EVT_MSG", "rtmp start push stream");
                        break;
                    }
                    case -2308: {
                        bundle.putString("EVT_MSG", "The server rejects the connection request. It may be that the push URL has been occupied or expired, or the anti-leech link is wrong.");
                        break;
                    }
                    case 1026: {
                        if (this.mRtmpProxyEnable) {
                            synchronized (this.mRtmpMsgRecvThreadLock) {
                                this.nativeRtmpMsgRecvThreadStart(this.mRtmpMsgRecvThreadInstance);
                            }
                            synchronized (this.mRtmpProxyLock) {
                                this.nativeRtmpProxyEnterRoom(this.mRtmpProxyInstance);
                            }
                            if (this.mHandler != null) {
                                this.mHandler.sendEmptyMessageDelayed(104, 2000L);
                            }
                        }
                        return;
                    }
                    case 1102: {
                        bundle.putString("EVT_MSG", "Enables network reconnection");
                        break;
                    }
                    default: {
                        bundle.putString("EVT_MSG", "UNKNOWN");
                        break;
                    }
                }
                bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                f.a(this.mNotifyListener, n, bundle);
            }
        }
    }
    
    private void reportNetStatus() {
        final long timeTick = TXCTimeUtil.getTimeTick();
        final long n = timeTick - this.mLastTimeStamp;
        final UploadStats uploadStats = this.getUploadStats();
        long longValue = 0L;
        long longValue2 = 0L;
        long longValue3 = 0L;
        long longValue4 = 0L;
        if (uploadStats != null) {
            if (this.mLastUploadStats != null) {
                longValue = this.getSpeed(this.mLastUploadStats.inVideoBytes, uploadStats.inVideoBytes, n);
                longValue2 = this.getSpeed(this.mLastUploadStats.inAudioBytes, uploadStats.inAudioBytes, n);
                longValue3 = this.getSpeed(this.mLastUploadStats.outVideoBytes, uploadStats.outVideoBytes, n);
                longValue4 = this.getSpeed(this.mLastUploadStats.outAudioBytes, uploadStats.outAudioBytes, n);
            }
            this.setStatusValue(7005, uploadStats.videoCacheLen);
            this.setStatusValue(7006, uploadStats.audioCacheLen);
            this.setStatusValue(7007, uploadStats.videoDropCount);
            this.setStatusValue(7008, uploadStats.audioDropCount);
            this.setStatusValue(7021, uploadStats.bandwidthEst);
            this.setStatusValue(7009, uploadStats.startTS);
            this.setStatusValue(7010, uploadStats.dnsTS);
            this.setStatusValue(7011, uploadStats.connTS);
            this.setStatusValue(7012, String.valueOf(uploadStats.serverIP));
            this.setStatusValue(7013, this.mQuicChannel ? 2L : 1L);
            this.setStatusValue(7014, uploadStats.connectionID);
            this.setStatusValue(7015, uploadStats.connectionStats);
            this.mUploadQualityReport.a(uploadStats.videoDropCount, uploadStats.audioDropCount);
            this.mUploadQualityReport.b(uploadStats.videoCacheLen, uploadStats.audioCacheLen);
        }
        else {
            this.setStatusValue(7005, 0L);
            this.setStatusValue(7006, 0L);
            this.setStatusValue(7007, 0L);
            this.setStatusValue(7008, 0L);
        }
        this.setStatusValue(7001, longValue);
        this.setStatusValue(7002, longValue2);
        this.setStatusValue(7003, longValue3);
        this.setStatusValue(7004, longValue4);
        this.mLastTimeStamp = timeTick;
        this.mLastUploadStats = uploadStats;
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessageDelayed(103, 2000L);
        }
    }
    
    private Long getSpeed(final long n, final long n2, final long n3) {
        long n4;
        if (n > n2) {
            n4 = n2;
        }
        else {
            n4 = n2 - n;
        }
        long n5 = 0L;
        if (n3 > 0L) {
            n5 = n4 * 8L * 1000L / (n3 * 1024L);
        }
        return n5;
    }
    
    private boolean isQCloudStreamUrl(final String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        final String s2 = "://";
        final int index = s.indexOf(s2);
        if (index != -1) {
            final String substring = s.substring(index + s2.length());
            if (substring != null && substring.startsWith("cloud.tencent.com")) {
                return true;
            }
        }
        return false;
    }
    
    private void parseProxyInfo(final String mRtmpUrl) {
        if (mRtmpUrl == null || mRtmpUrl.length() == 0 || !mRtmpUrl.startsWith("room")) {
            return;
        }
        this.mRtmpProxyParam.i = this.isQCloudStreamUrl(mRtmpUrl);
        final HashMap paramsFromUrl = this.getParamsFromUrl(mRtmpUrl);
        if (paramsFromUrl == null) {
            return;
        }
        if (paramsFromUrl.containsKey("sdkappid")) {
            this.mRtmpProxyParam.a = Long.valueOf(paramsFromUrl.get("sdkappid"));
        }
        if (!paramsFromUrl.containsKey("roomid") || !paramsFromUrl.containsKey("userid") || !paramsFromUrl.containsKey("roomsig")) {
            return;
        }
        this.mRtmpProxyParam.d = Long.valueOf(paramsFromUrl.get("roomid"));
        this.mRtmpProxyParam.c = paramsFromUrl.get("userid");
        if (paramsFromUrl.containsKey("bizbuf")) {
            try {
                this.mRtmpProxyParam.j = URLDecoder.decode(paramsFromUrl.get("bizbuf"), "UTF-8");
            }
            catch (Exception ex) {
                TXCLog.e("TXCStreamUploader", "decode bizbuf failed.", ex);
            }
        }
        try {
            final JSONObject jsonObject = new JSONObject(URLDecoder.decode(paramsFromUrl.get("roomsig"), "UTF-8"));
            if (jsonObject == null) {
                return;
            }
            this.mRtmpProxyParam.b = 0L;
            if (!jsonObject.has("Key")) {
                return;
            }
            this.mRtmpProxyParam.e = jsonObject.optString("Key");
            final JSONObject optJSONObject = jsonObject.optJSONObject("RtmpProxy");
            if (optJSONObject != null && (!optJSONObject.has("Ip") || !optJSONObject.has("Port") || !optJSONObject.has("Type"))) {
                return;
            }
            final JSONArray optJSONArray = jsonObject.optJSONArray("AccessList");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); ++i) {
                    final JSONObject jsonObject2 = optJSONArray.getJSONObject(i);
                    if (jsonObject2 != null && jsonObject2.has("Ip") && jsonObject2.has("Port")) {
                        if (jsonObject2.has("Type")) {
                            final String optString = jsonObject2.optString("Ip");
                            final long optLong = jsonObject2.optLong("Port");
                            if (jsonObject2.optLong("Type") == 2L) {
                                this.mRtmpProxyIPList.add(optString + ":" + optLong);
                            }
                        }
                    }
                }
            }
            if (this.mRtmpProxyParam.i) {
                if (optJSONObject == null) {
                    return;
                }
                this.mRtmpUrl = mRtmpUrl.substring(0, mRtmpUrl.indexOf("?")) + "/webrtc/" + (this.mRtmpProxyParam.a + "_" + this.mRtmpProxyParam.d + "_" + this.mRtmpProxyParam.c) + "?real_rtmp_ip=" + optJSONObject.optString("Ip") + "&real_rtmp_port=" + optJSONObject.optLong("Port") + "&tinyid=" + this.mRtmpProxyParam.b + "&srctinyid=0";
                this.getNextRtmpProxyIP();
            }
            else {
                this.mRtmpUrl = mRtmpUrl;
                this.mQuicChannel = false;
            }
            this.mRtmpProxyEnable = true;
        }
        catch (Exception ex2) {
            TXCLog.e("TXCStreamUploader", "parse proxy info failed.", ex2);
        }
    }
    
    private HashMap getParamsFromUrl(final String s) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final String[] split = s.split("[?]");
        if (split == null || split.length < 2 || split[1] == null || split[1].length() == 0) {
            return hashMap;
        }
        for (final String s2 : split[1].split("[&]")) {
            if (s2.indexOf("=") != -1) {
                final String[] split3 = s2.split("[=]");
                if (split3.length == 2) {
                    hashMap.put(split3[0], split3[1]);
                }
            }
        }
        return hashMap;
    }
    
    private boolean getNextRtmpProxyIP() {
        this.mRtmpProxyParam.f = 234L;
        this.mRtmpProxyParam.g = 80L;
        if (this.mRtmpProxyIPList == null || this.mRtmpProxyIPList.size() <= 0) {
            return false;
        }
        if (this.mRtmpProxyIPIndex >= this.mRtmpProxyIPList.size()) {
            this.mRtmpProxyIPIndex = 0;
            return false;
        }
        final String[] split = this.mRtmpUrl.split("://");
        if (split.length < 2) {
            return false;
        }
        final String substring = split[1].substring(split[1].indexOf("/"));
        final String h = this.mRtmpProxyIPList.get(this.mRtmpProxyIPIndex);
        this.mRtmpProxyParam.h = h;
        this.mRtmpUrl = "room://" + h + substring;
        this.mQuicChannel = true;
        ++this.mRtmpProxyIPIndex;
        return true;
    }
    
    private void rtmpProxySendHeartBeat() {
        final int[] a = f.a();
        final long n = a[0] / 10;
        final long n2 = a[1] / 10;
        final long n3 = TXCStatus.c(this.getID(), 7004);
        final long n4 = TXCStatus.c(this.getID(), 7003);
        final long n5 = TXCStatus.c(this.getID(), 1001);
        final long n6 = TXCStatus.c(this.getID(), 4001);
        final long n7 = TXCStatus.c(this.getID(), 7006);
        final long n8 = TXCStatus.c(this.getID(), 7005);
        final long n9 = TXCStatus.c(this.getID(), 7008);
        final long n10 = TXCStatus.c(this.getID(), 7007);
        synchronized (this.mRtmpProxyLock) {
            this.nativeRtmpProxySendHeartBeat(this.mRtmpProxyInstance, n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        }
    }
    
    private void onSendRtmpProxyMsg(final byte[] array) {
        synchronized (this.mThreadLock) {
            if (this.mUploaderInstance != 0L) {
                this.nativeSendRtmpProxyMsg(this.mUploaderInstance, array);
            }
        }
    }
    
    private void onRtmpProxyUserListPushed(final RtmpProxyUserInfo[] array) {
        if (array == null) {
            return;
        }
        if (!this.mIsPushing) {
            return;
        }
        if (!this.mRtmpProxyEnable || this.mRtmpProxyParam == null) {
            return;
        }
        try {
            final JSONArray jsonArray = new JSONArray();
            final JSONArray jsonArray2 = new JSONArray();
            for (int i = 0; i < array.length; ++i) {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("userid", (Object)array[i].account);
                jsonObject.put("playurl", (Object)array[i].playUrl);
                if (array[i].stmType == 0) {
                    jsonArray.put((Object)jsonObject);
                }
                else {
                    jsonArray2.put((Object)jsonObject);
                }
            }
            final JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("userlist", (Object)jsonArray);
            jsonObject2.put("userlist_aux", (Object)jsonArray2);
            this.sendNotifyEvent(1020, jsonObject2.toString());
        }
        catch (Exception ex) {
            TXCLog.e("TXCStreamUploader", "build json object failed.", ex);
        }
    }
    
    private void onRtmpProxyRoomEvent(final int n, final int n2) {
        if (n == 1) {
            this.sendNotifyEvent(1018, String.format("Already in room\uff0c[%d]", n2));
        }
        else if (n == 2) {
            this.sendNotifyEvent(1019, String.format("Not in the room\uff0c[%d]", n2));
        }
    }
    
    private native long nativeInitUploader(final String p0, final String p1, final boolean p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final boolean p10, final int p11, final HashMap<String, String> p12);
    
    private native void nativeOnThreadRun(final long p0);
    
    private native void nativeUninitUploader(final long p0);
    
    private native void nativeEnableDrop(final long p0, final boolean p1);
    
    private native void nativeSetSendStrategy(final long p0, final int p1, final boolean p2);
    
    private native void nativeSetVideoDropParams(final long p0, final boolean p1, final int p2, final int p3);
    
    private native void nativeStopPush(final long p0);
    
    private native void nativePushAAC(final long p0, final byte[] p1, final long p2);
    
    private native void nativePushNAL(final long p0, final byte[] p1, final int p2, final long p3, final long p4, final long p5);
    
    private native UploadStats nativeGetStats(final long p0);
    
    private native void nativeSendRtmpProxyMsg(final long p0, final byte[] p1);
    
    private native void nativeCacheJNIParams();
    
    private native void nativeReleaseJNIParams();
    
    private native long nativeInitRtmpProxyInstance(final long p0, final long p1, final String p2, final long p3, final String p4, final long p5, final long p6, final String p7, final boolean p8, final String p9);
    
    private native void nativeUninitRtmpProxyInstance(final long p0);
    
    private native void nativeRtmpProxyEnterRoom(final long p0);
    
    private native void nativeRtmpProxyLeaveRoom(final long p0);
    
    private native void nativeRtmpProxySendHeartBeat(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8, final long p9, final long p10);
    
    private native long nativeInitRtmpMsgRecvThreadInstance(final long p0, final long p1);
    
    private native void nativeUninitRtmpMsgRecvThreadInstance(final long p0);
    
    private native void nativeRtmpMsgRecvThreadStart(final long p0);
    
    private native void nativeRtmpMsgRecvThreadStop(final long p0);
    
    static {
        f.f();
    }
    
    private class b
    {
        public String a;
        public boolean b;
        
        public b(final String a, final boolean b) {
            this.a = "";
            this.b = false;
            this.a = a;
            this.b = b;
        }
    }
    
    public class UploadStats
    {
        public long inVideoBytes;
        public long inAudioBytes;
        public long outVideoBytes;
        public long outAudioBytes;
        public long videoCacheLen;
        public long audioCacheLen;
        public long videoDropCount;
        public long audioDropCount;
        public long bandwidthEst;
        public long startTS;
        public long dnsTS;
        public long connTS;
        public String serverIP;
        public long channelType;
        public long dnsparseTimeCost;
        public long connectTimeCost;
        public long handshakeTimeCost;
        public String connectionID;
        public String connectionStats;
    }
    
    public class a
    {
        public long a;
        public long b;
        public String c;
        public long d;
        public String e;
        public long f;
        public long g;
        public String h;
        public boolean i;
        public String j;
        
        public void a() {
            this.a = 0L;
            this.b = 0L;
            this.c = "";
            this.d = 0L;
            this.e = "";
            this.f = 0L;
            this.g = 0L;
            this.i = false;
            this.j = "";
        }
    }
    
    public class RtmpProxyUserInfo
    {
        public String account;
        public String playUrl;
        public int stmType;
        
        public RtmpProxyUserInfo() {
            this.account = "";
            this.playUrl = "";
            this.stmType = 0;
        }
    }
}
