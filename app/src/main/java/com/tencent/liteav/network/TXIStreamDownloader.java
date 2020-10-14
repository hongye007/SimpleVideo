package com.tencent.liteav.network;

import com.tencent.liteav.basic.b.*;
import android.content.*;
import android.os.*;
import com.tencent.liteav.basic.structs.*;
import java.util.*;
import java.io.*;

public abstract class TXIStreamDownloader
{
    protected h mListener;
    protected b mNotifyListener;
    protected a mRestartListener;
    protected Context mApplicationContext;
    protected boolean mIsRunning;
    protected String mOriginUrl;
    public int connectRetryTimes;
    public int connectRetryLimit;
    public int connectRetryInterval;
    protected boolean mEnableMessage;
    protected boolean mEnableMetaData;
    protected String mFlvSessionKey;
    protected String mUserID;
    protected Map<String, String> mHeaders;
    
    public TXIStreamDownloader(final Context mApplicationContext) {
        this.mListener = null;
        this.mNotifyListener = null;
        this.mRestartListener = null;
        this.mApplicationContext = null;
        this.mIsRunning = false;
        this.mOriginUrl = "";
        this.connectRetryTimes = 0;
        this.connectRetryLimit = 3;
        this.connectRetryInterval = 3;
        this.mEnableMessage = false;
        this.mEnableMetaData = false;
        this.mFlvSessionKey = "";
        this.mUserID = "";
        this.mApplicationContext = mApplicationContext;
    }
    
    public void setListener(final h mListener) {
        this.mListener = mListener;
    }
    
    public void setHeaders(final Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }
    
    public void setNotifyListener(final b mNotifyListener) {
        this.mNotifyListener = mNotifyListener;
    }
    
    public void setRestartListener(final a mRestartListener) {
        this.mRestartListener = mRestartListener;
    }
    
    public abstract void startDownload(final Vector<e> p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4);
    
    public abstract void stopDownload();
    
    public void sendNotifyEvent(final int n) {
        if (this.mNotifyListener != null) {
            this.mNotifyListener.onNotifyEvent(n, null);
        }
    }
    
    public void sendNotifyEvent(final int n, final String s) {
        if (this.mNotifyListener != null) {
            final Bundle bundle = new Bundle();
            bundle.putString("EVT_MSG", s);
            this.mNotifyListener.onNotifyEvent(n, bundle);
        }
    }
    
    public void onRecvVideoData(final byte[] nalData, final int nalType, final long dts, final long pts, final int codecId) {
        if (this.mListener != null) {
            final TXSNALPacket txsnalPacket = new TXSNALPacket();
            txsnalPacket.nalData = nalData;
            txsnalPacket.nalType = nalType;
            txsnalPacket.dts = dts;
            txsnalPacket.pts = pts;
            txsnalPacket.codecId = codecId;
            this.mListener.onPullNAL(txsnalPacket);
        }
    }
    
    public void onRecvAudioData(final byte[] audioData, final int n, final int audioType, final int codecFormat) {
        if (this.mListener != null) {
            final com.tencent.liteav.basic.structs.a a = new com.tencent.liteav.basic.structs.a();
            a.audioData = audioData;
            a.timestamp = n;
            if (audioType == 10) {
                if (codecFormat == 1) {
                    a.packetType = 2;
                }
                else {
                    a.packetType = 3;
                }
                if (a.packetType == 2) {
                    a.bitsPerChannel = 16;
                }
            }
            if (audioType == 2) {
                a.packetType = 5;
            }
            a.audioType = audioType;
            a.codecFormat = codecFormat;
            this.mListener.onPullAudio(a);
        }
    }
    
    public void onRecvSEIData(final byte[] array) {
        if (array != null && array.length > 0 && this.mNotifyListener != null) {
            final Bundle bundle = new Bundle();
            bundle.putByteArray("EVT_GET_MSG", array);
            this.mNotifyListener.onNotifyEvent(2012, bundle);
        }
    }
    
    public void onRecvMetaData(final HashMap<String, String> hashMap) {
        if (hashMap != null && hashMap.size() > 0 && this.mNotifyListener != null) {
            final Bundle bundle = new Bundle();
            bundle.putSerializable("EVT_GET_METADATA", (Serializable)hashMap);
            this.mNotifyListener.onNotifyEvent(2028, bundle);
        }
    }
    
    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        return null;
    }
    
    public void setOriginUrl(final String mOriginUrl) {
        this.mOriginUrl = mOriginUrl;
    }
    
    public String getCurrentStreamUrl() {
        return null;
    }
    
    public String getRealStreamUrl() {
        return null;
    }
    
    public boolean isQuicChannel() {
        return false;
    }
    
    public int getConnectCountQuic() {
        return 0;
    }
    
    public int getConnectCountTcp() {
        return 0;
    }
    
    public void setUserID(final String mUserID) {
        this.mUserID = mUserID;
    }
    
    public void PushVideoFrame(final byte[] array, final int n, final long n2, final long n3, final int n4) {
    }
    
    public void PushAudioFrame(final byte[] array, final int n, final long n2, final int n3) {
    }
    
    public long getCurrentTS() {
        return 0L;
    }
    
    public long getLastIFrameTS() {
        return 0L;
    }
    
    public void requestKeyFrame(final String s) {
    }
    
    public void setFlvSessionKey(final String mFlvSessionKey) {
        this.mFlvSessionKey = mFlvSessionKey;
    }
    
    public String getFlvSessionKey() {
        return this.mFlvSessionKey;
    }
    
    public interface a
    {
        void onRestartDownloader();
        
        void onOldStreamStop();
    }
}
