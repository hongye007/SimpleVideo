package com.tencent.liteav.network;

import android.content.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.log.*;
import javax.net.ssl.*;
import java.io.*;
import android.os.*;
import java.net.*;
import android.text.*;
import java.util.*;

public class TXCFLVDownloader extends TXIStreamDownloader
{
    public final String TAG = "network.TXCFLVDownloader";
    private final int FLV_HEAD_SIZE = 9;
    private final int MAX_FRAME_SIZE = 1048576;
    private final int MSG_CONNECT = 100;
    private final int MSG_RECV_DATA = 101;
    private final int MSG_DISCONNECT = 102;
    private final int MSG_RECONNECT = 103;
    private final int MSG_SEEK = 104;
    private final int MSG_RESUME = 105;
    private final int MSG_QUIT = 106;
    private final int CONNECT_TIMEOUT = 8000;
    private final int READ_STREAM_SIZE = 1388;
    private HandlerThread mFlvThread;
    private Handler mFlvHandler;
    private InputStream mInputStream;
    private HttpURLConnection mConnection;
    private byte[] mPacketBytes;
    private boolean mRecvData;
    private long mContentLength;
    private long mDownloadedSize;
    private boolean mHandleDataInJava;
    private long mFLVParser;
    private long mRefFLVParser;
    private long mCurrentNalTs;
    private long mLastIFramelTs;
    private boolean mStopJitter;
    private String mPlayUrl;
    private boolean mHasReceivedFirstVideo;
    private boolean mHasReceivedFirstAudio;
    private TXCStreamDownloader.DownloadStats mStats;
    
    public TXCFLVDownloader(final Context context) {
        super(context);
        this.mFlvThread = null;
        this.mFlvHandler = null;
        this.mInputStream = null;
        this.mConnection = null;
        this.mPacketBytes = null;
        this.mRecvData = false;
        this.mContentLength = 0L;
        this.mDownloadedSize = 0L;
        this.mHandleDataInJava = false;
        this.mFLVParser = 0L;
        this.mRefFLVParser = 0L;
        this.mCurrentNalTs = 0L;
        this.mLastIFramelTs = 0L;
        this.mStopJitter = true;
        this.mPlayUrl = "";
        this.mHasReceivedFirstVideo = false;
        this.mHasReceivedFirstAudio = false;
        this.mStats = null;
        this.mStats = new TXCStreamDownloader.DownloadStats();
        this.mStats.afterParseAudioBytes = 0L;
        this.mStats.dnsTS = 0L;
        this.mStats.startTS = TXCTimeUtil.getTimeTick();
        TXCLog.i("network.TXCFLVDownloader", "new flv download " + this);
    }
    
    public TXCFLVDownloader(final Context context, final TXCFLVDownloader txcflvDownloader) {
        super(context);
        this.mFlvThread = null;
        this.mFlvHandler = null;
        this.mInputStream = null;
        this.mConnection = null;
        this.mPacketBytes = null;
        this.mRecvData = false;
        this.mContentLength = 0L;
        this.mDownloadedSize = 0L;
        this.mHandleDataInJava = false;
        this.mFLVParser = 0L;
        this.mRefFLVParser = 0L;
        this.mCurrentNalTs = 0L;
        this.mLastIFramelTs = 0L;
        this.mStopJitter = true;
        this.mPlayUrl = "";
        this.mHasReceivedFirstVideo = false;
        this.mHasReceivedFirstAudio = false;
        this.mStats = null;
        this.mStats = new TXCStreamDownloader.DownloadStats();
        this.mStats.afterParseAudioBytes = 0L;
        this.mStats.dnsTS = 0L;
        this.mStats.startTS = TXCTimeUtil.getTimeTick();
        if (txcflvDownloader != null) {
            this.mRefFLVParser = txcflvDownloader.mFLVParser;
            txcflvDownloader.mStopJitter = false;
        }
        TXCLog.i("network.TXCFLVDownloader", "new multi flv download " + this);
    }
    
    public void recvData(final boolean mHandleDataInJava) {
        this.mHandleDataInJava = mHandleDataInJava;
    }
    
    @Override
    public void PushVideoFrame(final byte[] array, final int n, final long n2, final long n3, final int n4) {
        this.nativePushVideoFrame(this.mFLVParser, array, n, n2, n3, n4);
    }
    
    @Override
    public void PushAudioFrame(final byte[] array, final int n, final long n2, final int n3) {
        this.nativePushAudioFrame(this.mFLVParser, array, n, n2, n3);
    }
    
    @Override
    public long getCurrentTS() {
        return this.mCurrentNalTs;
    }
    
    @Override
    public long getLastIFrameTS() {
        return this.mLastIFramelTs;
    }
    
    private void processMsgConnect() {
        try {
            this.connect();
        }
        catch (SocketTimeoutException ex3) {
            TXCLog.e("network.TXCFLVDownloader", "socket timeout, reconnect");
            this.postReconnectMsg();
            return;
        }
        catch (FileNotFoundException ex) {
            TXCLog.e("network.TXCFLVDownloader", "file not found, reconnect");
            ex.printStackTrace();
            this.postReconnectMsg();
            return;
        }
        catch (Exception ex2) {
            TXCLog.e("network.TXCFLVDownloader", "exception, reconnect");
            ex2.printStackTrace();
            this.postReconnectMsg();
            return;
        }
        catch (Error error) {
            TXCLog.e("network.TXCFLVDownloader", "error, reconnect");
            error.printStackTrace();
            this.postReconnectMsg();
            return;
        }
        if (this.mFLVParser == 0L) {
            if (this.mRefFLVParser != 0L) {
                this.mFLVParser = this.nativeInitFlvHanderByRef(this.mRefFLVParser);
                this.mRefFLVParser = 0L;
            }
            else {
                this.mFLVParser = this.nativeInitFlvHander(this.mUserID, 0, this.mEnableMessage, this.mEnableMetaData);
            }
        }
        if (this.mFlvHandler != null) {
            this.mFlvHandler.sendEmptyMessage(101);
        }
    }
    
    private void processMsgRecvData() {
        if (this.mInputStream != null) {
            try {
                final int read = this.mInputStream.read(this.mPacketBytes, 0, 1388);
                if (read > 0) {
                    this.mDownloadedSize += read;
                    if (!this.mRecvData) {
                        TXCLog.w("network.TXCFLVDownloader", "flv play receive first data " + this);
                        this.mRecvData = true;
                    }
                    int nativeParseData = 0;
                    if (this.mFLVParser != 0L) {
                        final TXCStreamDownloader.DownloadStats mStats = this.mStats;
                        mStats.beforeParseVideoBytes += read;
                        nativeParseData = this.nativeParseData(this.mFLVParser, this.mPacketBytes, read);
                        this.mStats.afterParseVideoBytes = this.nativeGetVideoBytes(this.mFLVParser);
                        this.mStats.afterParseAudioBytes = this.nativeGetAudioBytes(this.mFLVParser);
                        this.mStats.videoGop = this.nativeGetVideoGop(this.mFLVParser);
                    }
                    if (nativeParseData > 1048576) {
                        TXCLog.e("network.TXCFLVDownloader", "flv play parse frame: " + nativeParseData + " > " + 1048576 + ",sart reconnect");
                        this.postReconnectMsg();
                        return;
                    }
                }
                else if (read < 0) {
                    TXCLog.w("network.TXCFLVDownloader", "http read: " + read + " < 0, start reconnect");
                    this.postReconnectMsg();
                    return;
                }
                if (this.mFlvHandler != null) {
                    this.mFlvHandler.sendEmptyMessage(101);
                }
            }
            catch (SocketTimeoutException ex2) {
                TXCLog.w("network.TXCFLVDownloader", "socket timeout start reconnect");
                this.postReconnectMsg();
            }
            catch (SocketException ex3) {
                TXCLog.w("network.TXCFLVDownloader", "socket exception start reconnect");
                this.postReconnectMsg();
            }
            catch (SSLException ex4) {
                TXCLog.w("network.TXCFLVDownloader", "ssl exception start reconnect");
                this.postReconnectMsg();
            }
            catch (EOFException ex5) {
                TXCLog.w("network.TXCFLVDownloader", "eof exception start reconnect");
                this.postReconnectMsg();
            }
            catch (Exception ex) {
                TXCLog.e("network.TXCFLVDownloader", "exception");
                ex.printStackTrace();
                this.mInputStream = null;
                this.mConnection = null;
            }
            catch (Error error) {
                TXCLog.e("network.TXCFLVDownloader", "error");
                error.printStackTrace();
                this.mInputStream = null;
                this.mConnection = null;
            }
        }
    }
    
    private void processMsgDisConnect() {
        try {
            this.disconnect();
        }
        catch (Exception ex) {
            TXCLog.e("network.TXCFLVDownloader", "disconnect failed.", ex);
        }
        if (this.mFLVParser != 0L) {
            this.nativeUninitFlvhander(this.mFLVParser, this.mStopJitter);
            this.mFLVParser = 0L;
        }
    }
    
    private void processMsgReconnect() {
        if (this.mStopJitter) {
            this.reconnect();
        }
        else {
            TXCLog.i("network.TXCFLVDownloader", "ignore processMsgReconnect when start multi stream switch" + this);
            if (this.mRestartListener != null) {
                this.mRestartListener.onOldStreamStop();
            }
        }
    }
    
    private void startInternal() {
        if (this.mFlvThread == null) {
            (this.mFlvThread = new HandlerThread("FlvThread")).start();
        }
        if (this.mFlvHandler == null) {
            this.mFlvHandler = new Handler(this.mFlvThread.getLooper()) {
                public void handleMessage(final Message message) {
                    switch (message.what) {
                        case 100: {
                            TXCFLVDownloader.this.processMsgConnect();
                            break;
                        }
                        case 101: {
                            TXCFLVDownloader.this.processMsgRecvData();
                            break;
                        }
                        case 102: {
                            TXCFLVDownloader.this.processMsgDisConnect();
                            break;
                        }
                        case 103: {
                            TXCFLVDownloader.this.processMsgReconnect();
                            break;
                        }
                        case 106: {
                            try {
                                Looper.myLooper().quit();
                            }
                            catch (Exception ex) {}
                            break;
                        }
                    }
                }
            };
        }
        this.postConnectMsg();
    }
    
    private void reconnect() {
        this.processMsgDisConnect();
        if (this.connectRetryTimes < this.connectRetryLimit) {
            ++this.connectRetryTimes;
            TXCLog.i("network.TXCFLVDownloader", "reconnect retry time:" + this.connectRetryTimes + ", limit:" + this.connectRetryLimit);
            this.processMsgConnect();
            this.sendNotifyEvent(2103);
        }
        else {
            TXCLog.e("network.TXCFLVDownloader", "reconnect all times retried, send failed event ");
            this.sendNotifyEvent(-2301);
        }
    }
    
    private void postReconnectMsg() {
        if (this.mFlvHandler != null) {
            this.mFlvHandler.sendEmptyMessageDelayed(103, (long)(this.connectRetryInterval * 1000));
        }
    }
    
    private void postDisconnectMsg() {
        if (this.mFlvHandler != null) {
            this.mFlvHandler.sendEmptyMessage(102);
        }
    }
    
    private void postConnectMsg() {
        this.mInputStream = null;
        if (this.mConnection != null) {
            this.mConnection.disconnect();
            this.mConnection = null;
        }
        final Message message = new Message();
        message.what = 100;
        message.arg1 = 0;
        if (this.mFlvHandler != null) {
            this.mFlvHandler.sendMessage(message);
        }
    }
    
    private void connect() throws Exception {
        if (this.mConnection != null) {
            this.mConnection.disconnect();
            this.mConnection = null;
        }
        this.mConnection = (HttpURLConnection)new URL(this.mPlayUrl).openConnection();
        this.mStats.dnsTS = TXCTimeUtil.getTimeTick();
        this.mConnection.setConnectTimeout(8000);
        this.mConnection.setReadTimeout(8000);
        this.mConnection.setRequestProperty("Accept-Encoding", "identity");
        this.mConnection.setInstanceFollowRedirects(true);
        if (this.mHeaders != null) {
            for (final Map.Entry<String, String> entry : this.mHeaders.entrySet()) {
                this.mConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        this.mConnection.connect();
        if (200 == this.mConnection.getResponseCode()) {
            this.mStats.connTS = TXCTimeUtil.getTimeTick();
        }
        else {
            this.mStats.errorCode = this.mConnection.getResponseCode();
        }
        this.mInputStream = this.mConnection.getInputStream();
        this.mPacketBytes = new byte[1388];
        this.mRecvData = false;
        this.mContentLength = this.mConnection.getContentLength();
        this.mDownloadedSize = 0L;
        this.mStats.serverIP = InetAddress.getByName(this.mConnection.getURL().getHost()).getHostAddress();
        this.sendNotifyEvent(2001);
        this.mStats.flvSessionKey = this.mConnection.getHeaderField("X-Tlive-SpanId");
        if (!TextUtils.isEmpty((CharSequence)this.mFlvSessionKey)) {
            final String headerField = this.mConnection.getHeaderField(this.mFlvSessionKey);
            if (headerField != null) {
                TXCLog.i("network.TXCFLVDownloader", "receive flvSessionKey " + headerField);
                this.sendNotifyEvent(2031, headerField);
            }
        }
    }
    
    @Override
    public String getRealStreamUrl() {
        if (this.mConnection != null) {
            return this.mConnection.getURL().toString();
        }
        return null;
    }
    
    private void disconnect() throws Exception {
        if (this.mConnection != null) {
            this.mConnection.disconnect();
            this.mConnection = null;
        }
        if (this.mInputStream != null) {
            this.mInputStream.close();
            this.mInputStream = null;
        }
    }
    
    private native long nativeInitFlvHander(final String p0, final int p1, final boolean p2, final boolean p3);
    
    private native long nativeInitFlvHanderByRef(final long p0);
    
    private native void nativeUninitFlvhander(final long p0, final boolean p1);
    
    private native int nativeParseData(final long p0, final byte[] p1, final int p2);
    
    private native int nativeGetVideoBytes(final long p0);
    
    private native int nativeGetAudioBytes(final long p0);
    
    private native int nativeGetVideoGop(final long p0);
    
    private native void nativeCleanData(final long p0);
    
    public native void nativePushVideoFrame(final long p0, final byte[] p1, final int p2, final long p3, final long p4, final int p5);
    
    public native void nativePushAudioFrame(final long p0, final byte[] p1, final int p2, final long p3, final int p4);
    
    @Override
    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        final TXCStreamDownloader.DownloadStats downloadStats = new TXCStreamDownloader.DownloadStats();
        downloadStats.afterParseAudioBytes = this.mStats.afterParseAudioBytes;
        downloadStats.afterParseVideoBytes = this.mStats.afterParseVideoBytes;
        downloadStats.beforeParseVideoBytes = this.mStats.beforeParseVideoBytes;
        downloadStats.beforeParseAudioBytes = this.mStats.beforeParseAudioBytes;
        downloadStats.videoGop = this.mStats.videoGop;
        downloadStats.startTS = this.mStats.startTS;
        downloadStats.dnsTS = this.mStats.dnsTS;
        downloadStats.connTS = this.mStats.connTS;
        downloadStats.firstAudioTS = this.mStats.firstAudioTS;
        downloadStats.firstVideoTS = this.mStats.firstVideoTS;
        downloadStats.serverIP = this.mStats.serverIP;
        downloadStats.flvSessionKey = this.mStats.flvSessionKey;
        downloadStats.errorCode = this.mStats.errorCode;
        downloadStats.errorInfo = this.mStats.errorInfo;
        return downloadStats;
    }
    
    @Override
    public void startDownload(final Vector<e> vector, final boolean b, final boolean b2, final boolean mEnableMessage, final boolean mEnableMetaData) {
        if (this.mIsRunning) {
            return;
        }
        if (vector == null || vector.isEmpty()) {
            return;
        }
        this.mEnableMessage = mEnableMessage;
        this.mEnableMetaData = mEnableMetaData;
        this.mIsRunning = true;
        this.mPlayUrl = vector.get(0).a;
        TXCLog.i("network.TXCFLVDownloader", "start pull with url " + this.mPlayUrl);
        this.startInternal();
    }
    
    @Override
    public void stopDownload() {
        if (!this.mIsRunning) {
            return;
        }
        this.mIsRunning = false;
        TXCLog.i("network.TXCFLVDownloader", "stop pull");
        try {
            if (this.mFlvHandler != null) {
                this.mFlvHandler.removeCallbacksAndMessages((Object)null);
                this.mFlvHandler.sendEmptyMessage(102);
                this.mFlvHandler.sendEmptyMessage(106);
                this.mFlvHandler = null;
            }
        }
        catch (Exception ex) {
            TXCLog.e("network.TXCFLVDownloader", "stop download failed.", ex);
        }
    }
    
    @Override
    public void onRecvVideoData(final byte[] array, final int n, final long n2, final long n3, final int n4) {
        if (!this.mHasReceivedFirstVideo) {
            this.mHasReceivedFirstVideo = true;
            this.mStats.firstVideoTS = TXCTimeUtil.getTimeTick();
            TXCLog.i("network.TXCFLVDownloader", "receive first video with ts " + this.mStats.firstVideoTS);
        }
        final TXCStreamDownloader.DownloadStats mStats = this.mStats;
        mStats.afterParseVideoBytes += array.length;
        super.onRecvVideoData(array, n, n2, n3, n4);
    }
    
    @Override
    public void onRecvAudioData(final byte[] array, final int n, final int n2, final int n3) {
        if (!this.mHasReceivedFirstAudio) {
            this.mHasReceivedFirstAudio = true;
            this.mStats.firstAudioTS = TXCTimeUtil.getTimeTick();
            TXCLog.i("network.TXCFLVDownloader", "receive first audio with ts " + this.mStats.firstAudioTS);
        }
        final TXCStreamDownloader.DownloadStats mStats = this.mStats;
        mStats.afterParseAudioBytes += array.length;
        super.onRecvAudioData(array, n, n2, n3);
    }
    
    private void onRecvFirstVideoData() {
        if (!this.mHasReceivedFirstVideo) {
            this.mHasReceivedFirstVideo = true;
            this.mStats.firstVideoTS = TXCTimeUtil.getTimeTick();
            TXCLog.i("network.TXCFLVDownloader", "onRecvData: receive first video with ts " + this.mStats.firstVideoTS);
        }
    }
    
    private void onRecvFirstAudioData() {
        if (!this.mHasReceivedFirstAudio) {
            this.mHasReceivedFirstAudio = true;
            this.mStats.firstAudioTS = TXCTimeUtil.getTimeTick();
            TXCLog.i("network.TXCFLVDownloader", "onRecvData: receive first audio with ts " + this.mStats.firstAudioTS);
        }
    }
}
