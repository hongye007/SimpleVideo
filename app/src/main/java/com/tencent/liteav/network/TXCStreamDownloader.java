package com.tencent.liteav.network;

import com.tencent.liteav.basic.module.*;

import android.content.*;
import android.os.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.datareport.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import android.text.*;
import com.tencent.liteav.basic.util.*;

public class TXCStreamDownloader extends BaseObj implements b, TXIStreamDownloader.a, d.a, h
{
    public static final String TAG = "TXCStreamDownloader";
    private TXIStreamDownloader mDownloader;
    private h mListener;
    private byte[] mListenerLock;
    private b mNotifyListener;
    private boolean mDownloaderRunning;
    private String mOriginPlayUrl;
    private boolean mEnableNearestIP;
    private int mChannelType;
    private boolean mEnableMessage;
    private boolean mEnableMetaData;
    private String mFlvSessionKey;
    private int mDownloadFormat;
    private k mAccUrlFetcher;
    private Context mApplicationContext;
    private Handler mHandler;
    private long mLastTimeStamp;
    private DownloadStats mLastDownloadStats;
    private boolean mRecvFirstNal;
    private long mSwitchStartTime;
    private long mCurrentNalTs;
    private long mLastIFramelTs;
    private d mStreamSwitcher;
    private Runnable mReportNetStatusRunnalbe;
    protected Map<String, String> mHeaders;
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
        synchronized (this.mListenerLock) {
            if (this.mNotifyListener != null) {
                Bundle bundle2 = new Bundle();
                switch (n) {
                    case 3003: {
                        bundle2.putString("EVT_MSG", "RTMP handshake failed");
                        break;
                    }
                    case -2301: {
                        bundle2.putString("EVT_MSG", "failed to connect server for several times, abort connection");
                        break;
                    }
                    case 3010: {
                        bundle2.putString("EVT_MSG", "No video at this stream address");
                        break;
                    }
                    case 3007: {
                        bundle2.putString("EVT_MSG", "Read data error");
                        break;
                    }
                    case 3006: {
                        bundle2.putString("EVT_MSG", "Write data error");
                        break;
                    }
                    case -2302: {
                        bundle2.putString("EVT_MSG", "Failed to get accelerated pull address");
                        break;
                    }
                    case 2001: {
                        bundle2.putString("EVT_MSG", "connection SUCCESS");
                        break;
                    }
                    case 3002: {
                        bundle2.putString("EVT_MSG", "Failed to connect server");
                        break;
                    }
                    case 2103: {
                        bundle2.putString("EVT_MSG", "retry connecting stream server");
                        break;
                    }
                    case 2002: {
                        bundle2.putString("EVT_MSG", "begine receiving stream");
                        break;
                    }
                    case -2308: {
                        bundle2.putString("EVT_MSG", "The server rejected the connection request");
                        break;
                    }
                    case 2012: {
                        final byte[] byteArray = bundle.getByteArray("EVT_GET_MSG");
                        if (byteArray != null && byteArray.length > 0) {
                            bundle2.putByteArray("EVT_GET_MSG", byteArray);
                            break;
                        }
                        break;
                    }
                    case 2028:
                    case 2031: {
                        bundle2 = bundle;
                        break;
                    }
                    default: {
                        bundle2.putString("EVT_MSG", "UNKNOWN event = " + n);
                        break;
                    }
                }
                String string = "";
                if (bundle != null) {
                    string = bundle.getString("EVT_MSG", "");
                }
                if (string != null && !string.isEmpty()) {
                    bundle2.putString("EVT_MSG", string);
                }
                bundle2.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                this.mNotifyListener.onNotifyEvent(n, bundle2);
                if (n == 3001 || n == 3002 || n == 3003 || n == 3004 || n == 3005 || n == 3006 || n == 3007 || n == 3008 || n == 3009 || n == 3010 || n == 2101 || n == 2102 || n == 2109 || n == 2110 || n == -2301 || n == -2304 || n == -2308 || n == -2309) {
                    this.setStatusValue(7105, n);
                    if (bundle2 != null) {
                        this.setStatusValue(7106, bundle2.getString("EVT_MSG"));
                    }
                }
            }
        }
        if (n == 2001) {
            this.reportNetStatusInternal();
        }
    }
    
    private void tryResetRetryCount() {
        if (this.mDownloader != null) {
            this.mDownloader.connectRetryTimes = 0;
        }
    }
    
    @Override
    public void onPullAudio(final com.tencent.liteav.basic.structs.a a) {
        this.tryResetRetryCount();
        synchronized (this.mListenerLock) {
            if (this.mListener != null) {
                this.mListener.onPullAudio(a);
            }
            if (this.mDownloader != null) {
                this.mDownloader.PushAudioFrame(a.audioData, a.audioType, a.timestamp, a.codecFormat);
            }
        }
    }
    
    @Override
    public void onPullNAL(final TXSNALPacket txsnalPacket) {
        this.tryResetRetryCount();
        if (!this.mRecvFirstNal) {
            this.reportNetStatusInternal();
            this.mRecvFirstNal = true;
        }
        synchronized (this.mListenerLock) {
            this.mCurrentNalTs = txsnalPacket.pts;
            if (txsnalPacket.nalType == 0) {
                this.mLastIFramelTs = txsnalPacket.pts;
            }
            if (this.mListener != null) {
                this.mListener.onPullNAL(txsnalPacket);
            }
            if (this.mDownloader != null) {
                this.mDownloader.PushVideoFrame(txsnalPacket.nalData, txsnalPacket.nalType, txsnalPacket.dts, txsnalPacket.pts, txsnalPacket.codecId);
            }
        }
    }
    
    @Override
    public void onRestartDownloader() {
        if (this.mHandler != null) {
            this.mHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    TXCStreamDownloader.this.stop();
                    TXCStreamDownloader.this.start(TXCStreamDownloader.this.mOriginPlayUrl, TXCStreamDownloader.this.mEnableNearestIP, TXCStreamDownloader.this.mChannelType, TXCStreamDownloader.this.mEnableMessage, TXCStreamDownloader.this.mEnableMetaData);
                }
            });
        }
    }
    
    @Override
    public void onOldStreamStop() {
        synchronized (this.mListenerLock) {
            if (this.mStreamSwitcher != null) {
                this.mStreamSwitcher.b();
            }
        }
    }
    
    public void setListener(final h mListener) {
        synchronized (this.mListenerLock) {
            this.mListener = mListener;
        }
    }
    
    public void setNotifyListener(final b mNotifyListener) {
        synchronized (this.mListenerLock) {
            this.mNotifyListener = mNotifyListener;
        }
    }
    
    @Override
    public void setID(final String s) {
        super.setID(s);
        if (this.mDownloader != null) {
            this.mDownloader.setUserID(s);
        }
    }
    
    @Override
    public void onSwitchFinish(final TXIStreamDownloader mDownloader, final boolean b) {
        synchronized (this.mListenerLock) {
            final int n = (int)(System.currentTimeMillis() - this.mSwitchStartTime);
            this.mSwitchStartTime = 0L;
            final Bundle bundle = new Bundle();
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            if (b) {
                (this.mDownloader = mDownloader).setListener(this);
                this.mDownloader.setNotifyListener(this);
                this.mDownloader.setRestartListener(this);
                bundle.putInt("EVT_ID", 2015);
                bundle.putCharSequence("EVT_MSG", (CharSequence)"Switched resolution successfully");
                if (this.mNotifyListener != null) {
                    this.mNotifyListener.onNotifyEvent(2015, bundle);
                }
                TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.bw, n, "");
            }
            else {
                bundle.putInt("EVT_ID", 2015);
                bundle.putCharSequence("EVT_MSG", (CharSequence)"Failed to switch resolution");
                if (this.mNotifyListener != null) {
                    this.mNotifyListener.onNotifyEvent(2015, bundle);
                }
                TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.bx);
            }
            this.mStreamSwitcher = null;
        }
    }
    
    public TXCStreamDownloader(final Context mApplicationContext, final int mDownloadFormat) {
        this.mDownloader = null;
        this.mListener = null;
        this.mListenerLock = new byte[0];
        this.mNotifyListener = null;
        this.mDownloaderRunning = false;
        this.mOriginPlayUrl = "";
        this.mEnableNearestIP = false;
        this.mChannelType = 0;
        this.mEnableMessage = false;
        this.mEnableMetaData = false;
        this.mFlvSessionKey = "";
        this.mDownloadFormat = 1;
        this.mHandler = null;
        this.mLastTimeStamp = 0L;
        this.mLastDownloadStats = null;
        this.mRecvFirstNal = false;
        this.mSwitchStartTime = 0L;
        this.mCurrentNalTs = 0L;
        this.mLastIFramelTs = 0L;
        this.mStreamSwitcher = null;
        this.mReportNetStatusRunnalbe = new Runnable() {
            @Override
            public void run() {
                TXCStreamDownloader.this.reportNetStatus();
            }
        };
        if (mDownloadFormat == 0) {
            (this.mDownloader = new TXCFLVDownloader(mApplicationContext)).setFlvSessionKey(this.mFlvSessionKey);
        }
        else if (mDownloadFormat == 1 || mDownloadFormat == 4) {
            this.mDownloader = new TXCRTMPDownloader(mApplicationContext);
        }
        if (this.mDownloader != null) {
            this.mDownloader.setListener(this);
            this.mDownloader.setNotifyListener(this);
            this.mDownloader.setRestartListener(this);
        }
        this.mDownloadFormat = mDownloadFormat;
        this.mAccUrlFetcher = new k(mApplicationContext);
        this.mApplicationContext = mApplicationContext;
        if (this.mApplicationContext != null) {
            this.mHandler = new Handler(this.mApplicationContext.getMainLooper());
        }
    }
    
    public void setRetryTimes(final int connectRetryLimit) {
        if (this.mDownloader != null) {
            this.mDownloader.connectRetryLimit = connectRetryLimit;
        }
    }
    
    public void setRetryInterval(final int connectRetryInterval) {
        if (this.mDownloader != null) {
            this.mDownloader.connectRetryInterval = connectRetryInterval;
        }
    }
    
    public int start(final String originUrl, final boolean mEnableNearestIP, final int mChannelType, final boolean mEnableMessage, final boolean mEnableMetaData) {
        this.mDownloaderRunning = true;
        this.mRecvFirstNal = false;
        this.mOriginPlayUrl = originUrl;
        this.mEnableNearestIP = mEnableNearestIP;
        this.mChannelType = mChannelType;
        this.mEnableMessage = mEnableMessage;
        this.mEnableMetaData = mEnableMetaData;
        this.setStatusValue(7113, 0L);
        this.setStatusValue(7114, 0L);
        this.setStatusValue(7115, 0L);
        if (originUrl.startsWith("room")) {
            this.setStatusValue(7113, 1L);
            this.setStatusValue(7112, 2L);
            if (this.mDownloader != null) {
                final Vector<e> vector = new Vector<e>();
                vector.add(new e(originUrl, true));
                this.mDownloader.setOriginUrl(originUrl);
                this.mDownloader.setUserID(this.getID());
                this.mDownloader.startDownload(vector, false, false, mEnableMessage, mEnableMetaData);
            }
            if (this.mHandler != null) {
                this.mHandler.postDelayed(this.mReportNetStatusRunnalbe, 2000L);
            }
            return 0;
        }
        if (mEnableNearestIP && this.mDownloadFormat == 4) {
            final int a = this.mAccUrlFetcher.a(originUrl, mChannelType, new k.a() {
                @Override
                public void a(final int n, final String s, final Vector<e> vector) {
                    if (n == 0 && vector != null && !vector.isEmpty()) {
                        if (TXCStreamDownloader.this.mDownloaderRunning) {
                            if (TXCStreamDownloader.this.mDownloader != null) {
                                int n2 = 0;
                                for (final e e : vector) {
                                    if (e != null && e.b && e.a != null && e.a.length() > 0) {
                                        ++n2;
                                    }
                                }
                                TXCStreamDownloader.this.setStatusValue(7113, (long)n2);
                                TXCStreamDownloader.this.setStatusValue(7112, 2L);
                                TXCStreamDownloader.this.mDownloader.setOriginUrl(originUrl);
                                TXCStreamDownloader.this.mDownloader.startDownload(vector, true, true, mEnableMessage, mEnableMetaData);
                            }
                            if (TXCStreamDownloader.this.mHandler != null) {
                                TXCStreamDownloader.this.mHandler.postDelayed(TXCStreamDownloader.this.mReportNetStatusRunnalbe, 2000L);
                            }
                            TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, com.tencent.liteav.basic.datareport.a.at, n, TXCStreamDownloader.this.mAccUrlFetcher.b());
                        }
                        else {
                            TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, com.tencent.liteav.basic.datareport.a.at, -4, "livePlayer have been stopped");
                        }
                    }
                    else {
                        TXCStreamDownloader.this.onNotifyEvent(-2302, null);
                        TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, com.tencent.liteav.basic.datareport.a.at, n, s);
                        TXCLog.e("TXCStreamDownloader", "getAccelerateStreamPlayUrl failed, play stream with raw url");
                        if (TXCStreamDownloader.this.mDownloaderRunning) {
                            TXCStreamDownloader.this.onNotifyEvent(-2301, null);
                        }
                    }
                }
            });
            if (a != 0) {
                if (a == -1) {
                    TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.at, a, "invalid playUrl");
                }
                else if (a == -2) {
                    TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.at, a, "invalid streamID");
                }
                else if (a == -3) {
                    TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.at, a, "invalid signature");
                }
                TXCLog.e("TXCStreamDownloader", "getAccelerateStreamPlayUrl failed, result = " + a + ", play stream with raw url");
                this.onNotifyEvent(-2302, null);
                this.onNotifyEvent(-2301, null);
            }
            return 0;
        }
        if (this.mDownloader != null) {
            this.setStatusValue(7112, 1L);
            final Vector<e> vector2 = new Vector<e>();
            vector2.add(new e(originUrl, false));
            this.mDownloader.setOriginUrl(originUrl);
            this.mDownloader.startDownload(vector2, this.mDownloadFormat == 4, mEnableNearestIP, mEnableMessage, mEnableMetaData);
            if (this.mHandler != null) {
                this.mHandler.postDelayed(this.mReportNetStatusRunnalbe, 2000L);
            }
        }
        return 0;
    }
    
    private void playStreamWithRawUrl(final String originUrl, final boolean b) {
        if (this.mDownloader != null) {
            if (originUrl != null && (originUrl.startsWith("http://") || originUrl.startsWith("https://")) && originUrl.contains(".flv")) {
                final int connectRetryLimit = this.mDownloader.connectRetryLimit;
                final int connectRetryInterval = this.mDownloader.connectRetryInterval;
                this.mDownloader = null;
                (this.mDownloader = new TXCFLVDownloader(this.mApplicationContext)).setFlvSessionKey(this.mFlvSessionKey);
                this.mDownloader.setListener(this);
                this.mDownloader.setNotifyListener(this);
                this.mDownloader.setRestartListener(this);
                this.mDownloader.connectRetryLimit = connectRetryLimit;
                this.mDownloader.connectRetryInterval = connectRetryInterval;
                this.mDownloader.setHeaders(this.mHeaders);
                this.mDownloader.setUserID(this.getID());
            }
            this.setStatusValue(7112, 1L);
            final Vector<e> vector = new Vector<e>();
            vector.add(new e(originUrl, false));
            this.mDownloader.setOriginUrl(originUrl);
            this.mDownloader.startDownload(vector, false, false, b, b);
        }
    }
    
    public void stop() {
        this.mDownloaderRunning = false;
        this.mRecvFirstNal = false;
        if (this.mDownloader != null) {
            this.mDownloader.stopDownload();
        }
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mReportNetStatusRunnalbe);
        }
        synchronized (this.mListenerLock) {
            if (this.mStreamSwitcher != null) {
                this.mStreamSwitcher.a((h)null);
                this.mStreamSwitcher.a();
                this.mStreamSwitcher = null;
            }
        }
    }
    
    public boolean switchStream(final String s) {
        synchronized (this.mListenerLock) {
            if (this.mStreamSwitcher != null || this.mDownloader == null || !(this.mDownloader instanceof TXCFLVDownloader)) {
                TXCLog.w("TXCStreamDownloader", "stream_switch stream is changing ignore this change");
                return false;
            }
            final TXCFLVDownloader txcflvDownloader = new TXCFLVDownloader(this.mApplicationContext, (TXCFLVDownloader)this.mDownloader);
            txcflvDownloader.connectRetryLimit = this.mDownloader.connectRetryLimit;
            txcflvDownloader.connectRetryInterval = this.mDownloader.connectRetryInterval;
            txcflvDownloader.setHeaders(this.mHeaders);
            txcflvDownloader.setUserID(this.getID());
            txcflvDownloader.setFlvSessionKey(this.mFlvSessionKey);
            (this.mStreamSwitcher = new d(this)).a(this);
            this.mStreamSwitcher.a(this.mDownloader, txcflvDownloader, this.mCurrentNalTs, this.mLastIFramelTs, s);
            this.mSwitchStartTime = System.currentTimeMillis();
        }
        return true;
    }
    
    public void requestKeyFrame(final String s) {
        if (this.mDownloaderRunning && s != null && s.startsWith("room") && this.mDownloader != null) {
            this.mDownloader.requestKeyFrame(s);
        }
    }
    
    private DownloadStats getDownloadStats() {
        if (this.mDownloader != null) {
            return this.mDownloader.getDownloadStats();
        }
        return null;
    }
    
    private a getRealTimeStreamInfo() {
        a a = null;
        if (this.mAccUrlFetcher != null && !TextUtils.isEmpty((CharSequence)this.mAccUrlFetcher.a())) {
            a = new a();
            a.b = this.mAccUrlFetcher.a();
            a.c = this.mAccUrlFetcher.b();
            a.d = this.mAccUrlFetcher.c();
            a.e = this.mAccUrlFetcher.d();
        }
        if (this.mDownloader != null && a != null) {
            a.a = this.mDownloader.getCurrentStreamUrl();
            a.f = this.mDownloader.isQuicChannel();
        }
        return a;
    }
    
    private void reportNetStatus() {
        this.reportNetStatusInternal();
        this.mHandler.postDelayed(this.mReportNetStatusRunnalbe, 2000L);
    }
    
    private void reportNetStatusInternal() {
        final long timeTick = TXCTimeUtil.getTimeTick();
        final long n = timeTick - this.mLastTimeStamp;
        final DownloadStats downloadStats = this.getDownloadStats();
        final a realTimeStreamInfo = this.getRealTimeStreamInfo();
        long longValue = 0L;
        long longValue2 = 0L;
        if (downloadStats != null) {
            if (this.mLastDownloadStats != null) {
                longValue = this.getSpeed(this.mLastDownloadStats.afterParseVideoBytes, downloadStats.afterParseVideoBytes, n);
                longValue2 = this.getSpeed(this.mLastDownloadStats.afterParseAudioBytes, downloadStats.afterParseAudioBytes, n);
            }
            if (longValue > 0L || longValue2 > 0L) {
                this.mDownloader.connectRetryTimes = 0;
            }
            this.setStatusValue(7101, longValue);
            this.setStatusValue(7102, longValue2);
            this.setStatusValue(7103, downloadStats.firstVideoTS);
            this.setStatusValue(7104, downloadStats.firstAudioTS);
            this.setStatusValue(7120, downloadStats.videoGop);
            if (realTimeStreamInfo != null) {
                this.setStatusValue(7105, (long)realTimeStreamInfo.d);
                this.setStatusValue(7106, realTimeStreamInfo.e);
                this.setStatusValue(7111, realTimeStreamInfo.f ? 2L : 1L);
                this.setStatusValue(7116, realTimeStreamInfo.a);
                this.setStatusValue(7117, realTimeStreamInfo.b);
                this.setStatusValue(7118, realTimeStreamInfo.c);
            }
            else {
                this.setStatusValue(7105, (long)downloadStats.errorCode);
                this.setStatusValue(7106, downloadStats.errorInfo);
                this.setStatusValue(7111, 1L);
            }
            this.setStatusValue(7107, downloadStats.startTS);
            this.setStatusValue(7108, downloadStats.dnsTS);
            this.setStatusValue(7109, downloadStats.connTS);
            this.setStatusValue(7110, String.valueOf(downloadStats.serverIP));
        }
        if (this.mDownloader != null) {
            int connectCountQuic = this.mDownloader.getConnectCountQuic();
            int connectCountTcp = this.mDownloader.getConnectCountTcp();
            this.setStatusValue(7114, (long)(++connectCountQuic));
            this.setStatusValue(7115, (long)(++connectCountTcp));
            this.setStatusValue(7119, this.mDownloader.getRealStreamUrl());
            this.setStatusValue(7121, String.valueOf(this.mDownloader.getFlvSessionKey()));
        }
        this.mLastTimeStamp = timeTick;
        this.mLastDownloadStats = downloadStats;
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
    
    public void setHeaders(final Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
        if (this.mDownloader != null) {
            this.mDownloader.setHeaders(this.mHeaders);
        }
    }
    
    public void setFlvSessionKey(final String s) {
        this.mFlvSessionKey = s;
        if (this.mDownloader != null) {
            this.mDownloader.setFlvSessionKey(s);
        }
    }
    
    public String getRTMPProxyUserId() {
        return this.nativeGetRTMPProxyUserId();
    }
    
    private native String nativeGetRTMPProxyUserId();
    
    static {
        f.f();
    }
    
    public static class DownloadStats
    {
        public long beforeParseVideoBytes;
        public long beforeParseAudioBytes;
        public long afterParseVideoBytes;
        public long afterParseAudioBytes;
        public long videoGop;
        public long startTS;
        public long dnsTS;
        public long connTS;
        public long firstVideoTS;
        public long firstAudioTS;
        public String serverIP;
        public String flvSessionKey;
        public int errorCode;
        public String errorInfo;
    }
    
    public static class a
    {
        public String a;
        public String b;
        public String c;
        public int d;
        public String e;
        public boolean f;
    }
}
