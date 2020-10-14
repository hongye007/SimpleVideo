package com.tencent.liteav.avprotocol;

import com.tencent.liteav.basic.d.*;
import java.util.*;
import android.util.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.structs.*;

public class TXCAVProtocol
{
    public static byte AV_STATE_NONE;
    public static byte AV_STATE_ENTER_AUDIO;
    public static byte AV_STATE_EXIT_AUDIO;
    public static byte AV_STATE_ENTER_VIDEO;
    public static byte AV_STATE_EXIT_VIDEO;
    private static final String TAG;
    private long mInstance;
    private TXIAVListener mListener;
    
    public TXCAVProtocol() {
        this.mInstance = 0L;
        this.mListener = null;
        this.mInstance = this.nativeInitAVProtocol();
    }
    
    public void destory() {
        if (this.mInstance == 0L) {
            return;
        }
        this.nativeUninitAVProtocol(this.mInstance);
        this.mInstance = 0L;
    }
    
    public void setListener(final TXIAVListener mListener) {
        this.mListener = mListener;
    }
    
    public void enterRoom(final TXCAVProtoParam txcavProtoParam, final TXIAVCompletionCallback txiavCompletionCallback) {
        if (this.mInstance == 0L) {
            return;
        }
        this.nativeEnterRoom(this.mInstance, txiavCompletionCallback, txcavProtoParam.sdkAppid, txcavProtoParam.sdkVersion, txcavProtoParam.roomID, txcavProtoParam.authBits, txcavProtoParam.authBuffer, txcavProtoParam.userID, (int)b.a().a("QUICMode", "AVRoom"));
    }
    
    public void exitRoom(final TXIAVCompletionCallback txiavCompletionCallback) {
        if (this.mInstance == 0L) {
            return;
        }
        this.nativeExitRoom(this.mInstance, new TXIAVCompletionCallback() {
            @Override
            public void onComplete(final int n) {
                txiavCompletionCallback.onComplete(n);
            }
        });
    }
    
    public void changeAVState(final byte b, final TXIAVCompletionCallback txiavCompletionCallback) {
        if (this.mInstance == 0L) {
            return;
        }
        this.nativeChangeAVState(this.mInstance, txiavCompletionCallback, b);
    }
    
    public void requestViews(final ArrayList<TXSAVRoomView> list, final TXIAVCompletionCallback txiavCompletionCallback) {
        if (this.mInstance == 0L) {
            return;
        }
        final long[] array = new long[list.size()];
        final int[] array2 = new int[list.size()];
        final int[] array3 = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            array[i] = list.get(i).tinyID;
            array2[i] = list.get(i).width;
            array3[i] = list.get(i).height;
        }
        this.nativeRequestViews(this.mInstance, txiavCompletionCallback, array, array2, array3);
    }
    
    public void pushAAC(final byte[] array, final long n, final int n2, final int n3) {
        if (this.mInstance == 0L) {
            return;
        }
        this.nativePushAAC(this.mInstance, array, n2, n3, 16, 3, n);
    }
    
    public void pushNAL(final TXSNALPacket txsnalPacket) {
        if (this.mInstance == 0L) {
            return;
        }
        this.nativePushNAL(this.mInstance, txsnalPacket.nalData, txsnalPacket.nalType, txsnalPacket.gopIndex, txsnalPacket.gopFrameIndex, txsnalPacket.frameIndex, txsnalPacket.refFremeIndex, txsnalPacket.pts, txsnalPacket.dts);
    }
    
    private void onPullAudio(final int roomID, final long tinyID, final byte[] audioData, final long timestamp, final int sampleRate, final int channelsPerSample, final int bitsPerChannel, final int packetType) {
        if (this.mListener != null) {
            final TXSAVProtoAudioPacket txsavProtoAudioPacket = new TXSAVProtoAudioPacket();
            txsavProtoAudioPacket.roomID = roomID;
            txsavProtoAudioPacket.tinyID = tinyID;
            txsavProtoAudioPacket.audioData = audioData;
            txsavProtoAudioPacket.timestamp = timestamp;
            txsavProtoAudioPacket.sampleRate = sampleRate;
            txsavProtoAudioPacket.channelsPerSample = channelsPerSample;
            txsavProtoAudioPacket.bitsPerChannel = bitsPerChannel;
            txsavProtoAudioPacket.packetType = packetType;
            this.mListener.onPullAudio(txsavProtoAudioPacket);
        }
    }
    
    private void onPullVideo(final int roomID, final long tinyID, final byte[] nalData, final int nalType, final long pts, final long dts, final int n, final int n2, final int n3, final int n4) {
        if (this.mListener != null) {
            final TXSAVProtoNALPacket txsavProtoNALPacket = new TXSAVProtoNALPacket();
            txsavProtoNALPacket.roomID = roomID;
            txsavProtoNALPacket.tinyID = tinyID;
            txsavProtoNALPacket.nalType = nalType;
            txsavProtoNALPacket.nalData = nalData;
            txsavProtoNALPacket.pts = pts;
            txsavProtoNALPacket.dts = dts;
            txsavProtoNALPacket.gopIndex = n;
            txsavProtoNALPacket.gopFrameIndex = n2;
            txsavProtoNALPacket.frameIndex = n3;
            txsavProtoNALPacket.refFremeIndex = n4;
            this.mListener.onPullNAL(txsavProtoNALPacket);
        }
    }
    
    private void onMemberChange(final long n, final boolean b) {
        this.mListener.onMemberChange(n, b);
    }
    
    private void onVideoStateChange(final long n, final boolean b) {
        this.mListener.onVideoStateChange(n, b);
    }
    
    private void sendNotifyEvent(final int n, final String s) {
        Log.i(TXCAVProtocol.TAG, "event" + n);
        this.mListener.sendNotifyEvent(n, s);
    }
    
    public long[] getRoomMemberList() {
        if (this.mInstance == 0L) {
            return null;
        }
        return this.nativeGetRoomMemberList(this.mInstance);
    }
    
    public long[] getRoomVideoList() {
        if (this.mInstance == 0L) {
            return null;
        }
        return this.nativeGetRoomVideoList(this.mInstance);
    }
    
    public UploadStats getUploadStats() {
        UploadStats nativeGetUploadStats = null;
        if (this.mInstance != 0L) {
            nativeGetUploadStats = this.nativeGetUploadStats(this.mInstance);
        }
        return nativeGetUploadStats;
    }
    
    public DownloadStats getDownloadStats() {
        DownloadStats nativeGetDownloadStats = null;
        if (this.mInstance != 0L) {
            nativeGetDownloadStats = this.nativeGetDownloadStats(this.mInstance);
        }
        return nativeGetDownloadStats;
    }
    
    public native String nativeNAT64Compatable(final String p0, final short p1);
    
    private native long nativeInitAVProtocol();
    
    private native void nativeUninitAVProtocol(final long p0);
    
    private native void nativeEnterRoom(final long p0, final TXIAVCompletionCallback p1, final long p2, final long p3, final long p4, final long p5, final byte[] p6, final long p7, final int p8);
    
    private native void nativeExitRoom(final long p0, final TXIAVCompletionCallback p1);
    
    private native void nativeChangeAVState(final long p0, final TXIAVCompletionCallback p1, final byte p2);
    
    private native void nativeRequestViews(final long p0, final TXIAVCompletionCallback p1, final long[] p2, final int[] p3, final int[] p4);
    
    private native void nativePushAAC(final long p0, final byte[] p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    private native void nativePushNAL(final long p0, final byte[] p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8);
    
    private native UploadStats nativeGetUploadStats(final long p0);
    
    private native DownloadStats nativeGetDownloadStats(final long p0);
    
    private native long[] nativeGetRoomMemberList(final long p0);
    
    private native long[] nativeGetRoomVideoList(final long p0);
    
    static {
        TXCAVProtocol.AV_STATE_NONE = 0;
        TXCAVProtocol.AV_STATE_ENTER_AUDIO = 1;
        TXCAVProtocol.AV_STATE_EXIT_AUDIO = 2;
        TXCAVProtocol.AV_STATE_ENTER_VIDEO = 3;
        TXCAVProtocol.AV_STATE_EXIT_VIDEO = 4;
        TAG = TXCAVProtocol.class.getSimpleName();
        f.f();
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
        public long startTS;
        public long dnsTS;
        public long connTS;
        public String serverIP;
        public long channelType;
    }
    
    public class DownloadStats
    {
        public long beforeParseVideoBytes;
        public long beforeParseAudioBytes;
        public long afterParseVideoBytes;
        public long afterParseAudioBytes;
        public long startTS;
        public long dnsTS;
        public long connTS;
        public long firstVideoTS;
        public long firstAudioTS;
        public String serverIP;
    }
    
    public class TXSAVProtoAudioPacket extends a
    {
        public int roomID;
        public long tinyID;
    }
    
    public class TXSAVProtoNALPacket extends TXSNALPacket
    {
        public int roomID;
        public long tinyID;
    }
    
    public class TXCAVProtoParam
    {
        public int sdkAppid;
        public int sdkVersion;
        public long userID;
        public long roomID;
        public int authBits;
        public byte[] authBuffer;
    }
    
    public class TXSAVRoomView
    {
        public long tinyID;
        public int height;
        public int width;
    }
    
    public interface TXIAVListener
    {
        void onPullAudio(final TXSAVProtoAudioPacket p0);
        
        void onPullNAL(final TXSAVProtoNALPacket p0);
        
        void sendNotifyEvent(final int p0, final String p1);
        
        void onMemberChange(final long p0, final boolean p1);
        
        void onVideoStateChange(final long p0, final boolean p1);
    }
    
    public interface TXIAVCompletionCallback
    {
        void onComplete(final int p0);
    }
}
