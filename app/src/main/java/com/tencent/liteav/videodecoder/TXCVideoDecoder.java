package com.tencent.liteav.videodecoder;

import com.tencent.liteav.basic.b.*;
import android.view.*;
import java.nio.*;
import org.json.*;
import java.lang.ref.*;
import android.graphics.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.util.*;
import android.util.*;
import com.tencent.liteav.basic.module.*;
import android.os.*;

public class TXCVideoDecoder implements b, f
{
    private static final String TAG = "TXCVideoDecoder";
    private static final boolean NEW_DECODER = true;
    boolean mNeedSortFrame;
    boolean mHWDec;
    boolean mHevc;
    boolean mRecvFirstFrame;
    Surface mSurface;
    f mDecoderListener;
    private int mDecoderCacheNum;
    private ByteBuffer mSps;
    private ByteBuffer mPps;
    private String mUserId;
    private long mNativeContext;
    private boolean mRestarting;
    private int mStreamType;
    private int mVideoWidth;
    private int mVideoHeight;
    private boolean mEnableDecoderChange;
    private boolean mEnableRestartDecoder;
    private boolean mEnableLimitDecCache;
    private static long mDecodeFirstFrameTS;
    b mVideoDecoder;
    private JSONArray mDecFormat;
    private ArrayList<TXSNALPacket> mNALList;
    private a mDecoderHandler;
    private WeakReference<b> mNotifyListener;
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
        com.tencent.liteav.basic.util.f.a(this.mNotifyListener, this.mUserId, n, bundle);
    }
    
    public void setUserId(final String mUserId) {
        this.mUserId = mUserId;
        synchronized (this) {
            this.nativeSetID(this.mNativeContext, this.mUserId);
        }
    }
    
    public void setStreamType(final int mStreamType) {
        this.mStreamType = mStreamType;
        synchronized (this) {
            this.nativeSetStreamType(this.mNativeContext, this.mStreamType);
        }
    }
    
    public void enableChange(final boolean mEnableDecoderChange) {
        this.mEnableDecoderChange = mEnableDecoderChange;
        synchronized (this) {
            this.nativeEnableDecodeChange(this.mNativeContext, this.mEnableDecoderChange);
        }
    }
    
    public void enableLimitDecCache(final boolean mEnableLimitDecCache) {
        this.mEnableLimitDecCache = mEnableLimitDecCache;
        final b mVideoDecoder = this.mVideoDecoder;
        if (mVideoDecoder != null) {
            mVideoDecoder.enableLimitDecCache(this.mEnableLimitDecCache);
        }
    }
    
    public void config(final JSONArray mDecFormat) {
        this.mDecFormat = mDecFormat;
    }
    
    public void enableRestart(final boolean mEnableRestartDecoder) {
        this.mEnableRestartDecoder = mEnableRestartDecoder;
    }
    
    public TXCVideoDecoder() {
        this.mRestarting = false;
        this.mStreamType = 0;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mEnableDecoderChange = false;
        this.mEnableRestartDecoder = false;
        this.mEnableLimitDecCache = false;
        this.mDecFormat = null;
        this.mNALList = new ArrayList<TXSNALPacket>();
        this.mHWDec = true;
        this.mHevc = false;
        this.mNeedSortFrame = true;
        this.mRecvFirstFrame = false;
        TXCVideoDecoder.mDecodeFirstFrameTS = 0L;
    }
    
    public void setListener(final f mDecoderListener) {
        this.mDecoderListener = mDecoderListener;
    }
    
    public boolean isHardwareDecode() {
        return this.mVideoDecoder != null;
    }
    
    public void setNotifyListener(final b b) {
        this.mNotifyListener = new WeakReference<b>(b);
    }
    
    public int setup(final SurfaceTexture surfaceTexture, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final boolean b) {
        if (this.mSurface != null) {
            this.mSurface.release();
            this.mSurface = null;
        }
        return this.setup(new Surface(surfaceTexture), byteBuffer, byteBuffer2, b);
    }
    
    public int setup(final Surface mSurface, final ByteBuffer mSps, final ByteBuffer mPps, final boolean mNeedSortFrame) {
        this.mSurface = mSurface;
        this.mSps = mSps;
        this.mPps = mPps;
        this.mNeedSortFrame = mNeedSortFrame;
        return 0;
    }
    
    public void enableHWDec(final boolean mhwDec) {
        this.mHWDec = mhwDec;
    }
    
    private void addOneNalToDecoder(final TXSNALPacket txsnalPacket) {
        final boolean b = txsnalPacket.nalType == 0;
        final Bundle data = new Bundle();
        data.putBoolean("iframe", b);
        data.putByteArray("nal", txsnalPacket.nalData);
        data.putLong("pts", txsnalPacket.pts);
        data.putLong("dts", txsnalPacket.dts);
        data.putInt("codecId", txsnalPacket.codecId);
        final Message message = new Message();
        message.what = 101;
        message.setData(data);
        final a mDecoderHandler = this.mDecoderHandler;
        if (mDecoderHandler != null) {
            mDecoderHandler.sendMessage(message);
        }
        ++this.mDecoderCacheNum;
    }
    
    private void decNALByNewWay(final TXSNALPacket txsnalPacket) {
        if (this.mHWDec) {
            this.decodeFrame(txsnalPacket.nalData, txsnalPacket.pts, txsnalPacket.dts, txsnalPacket.rotation, txsnalPacket.codecId, 0, 0, txsnalPacket.nalType);
        }
        else {
            synchronized (this) {
                this.nativeDecodeFrame(this.mNativeContext, txsnalPacket.nalData, txsnalPacket.nalType, txsnalPacket.pts, txsnalPacket.dts, txsnalPacket.rotation, txsnalPacket.codecId);
            }
        }
    }
    
    private void decNALByOldWay(final TXSNALPacket txsnalPacket) {
        try {
            final boolean b = txsnalPacket.nalType == 0;
            if (!this.mRecvFirstFrame && !b) {
                TXCLog.i("TXCVideoDecoder", "play:decode: push nal ignore p frame when not got i frame");
                return;
            }
            if (!this.mRecvFirstFrame && b) {
                TXCLog.w("TXCVideoDecoder", "play:decode: push first i frame");
                this.mRecvFirstFrame = true;
            }
            if (!this.mRestarting && txsnalPacket.codecId == 1 && !this.mHWDec) {
                TXCLog.w("TXCVideoDecoder", "play:decode: hevc decode error  ");
                com.tencent.liteav.basic.util.f.a(this.mNotifyListener, -2304, "h265 Decoding failed");
                this.mRestarting = true;
            }
            if (this.mDecoderHandler != null) {
                if (!this.mNALList.isEmpty()) {
                    final Iterator<TXSNALPacket> iterator = this.mNALList.iterator();
                    while (iterator.hasNext()) {
                        this.addOneNalToDecoder(iterator.next());
                    }
                }
                this.mNALList.clear();
                this.addOneNalToDecoder(txsnalPacket);
            }
            else {
                if (b && !this.mNALList.isEmpty()) {
                    this.mNALList.clear();
                }
                this.mNALList.add(txsnalPacket);
                if (!this.mRestarting) {
                    this.start();
                }
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCVideoDecoder", "decode NAL By Old way failed.", ex);
        }
    }
    
    public void pushNAL(final TXSNALPacket txsnalPacket) {
        this.decNALByNewWay(txsnalPacket);
    }
    
    public synchronized int start() {
        if (this.mHWDec && this.mSurface == null) {
            TXCLog.i("TXCVideoDecoder", "play:decode: start decoder error when not setup surface, id " + this.mUserId + "_" + this.mStreamType);
            return -1;
        }
        if (this.mNativeContext != 0L) {
            TXCLog.w("TXCVideoDecoder", "play:decode: start decoder error when decoder is started, id " + this.mUserId + "_" + this.mStreamType);
            return -1;
        }
        TXCLog.w("TXCVideoDecoder", "play:decode: start decoder java id " + this.mUserId + "_" + this.mStreamType);
        this.nativeSetID(this.mNativeContext = this.nativeCreateContext(this.mHWDec), this.mUserId);
        this.nativeSetStreamType(this.mNativeContext, this.mStreamType);
        this.nativeEnableDecodeChange(this.mNativeContext, this.mEnableDecoderChange);
        this.nativeEnableRestartDecoder(this.mNativeContext, this.mEnableRestartDecoder);
        return 0;
    }
    
    public synchronized void stop() {
        if (this.mNativeContext == 0L) {
            TXCLog.w("TXCVideoDecoder", "play:decode: stop decoder ignore when decoder is stopped, id " + this.mUserId + "_" + this.mStreamType);
            return;
        }
        TXCLog.w("TXCVideoDecoder", "play:decode: stop decoder java id " + this.mUserId + "_" + this.mStreamType);
        this.nativeDestroyContext(this.mNativeContext);
        this.mNativeContext = 0L;
        this.mNALList.clear();
        this.mRecvFirstFrame = false;
        this.mDecoderCacheNum = 0;
        TXCVideoDecoder.mDecodeFirstFrameTS = 0L;
        synchronized (this) {
            if (this.mVideoDecoder != null) {
                this.mVideoDecoder.stop();
                this.mVideoDecoder.setListener(null);
                this.mVideoDecoder.setNotifyListener(null);
                this.mVideoDecoder = null;
            }
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
        }
    }
    
    public void restart(final boolean mhwDec) {
        synchronized (this) {
            this.mHWDec = mhwDec;
            this.mNALList.clear();
            this.mRecvFirstFrame = false;
            this.mDecoderCacheNum = 0;
            TXCVideoDecoder.mDecodeFirstFrameTS = 0L;
            final Message obtain = Message.obtain();
            obtain.what = 103;
            obtain.arg1 = (this.mHWDec ? 1 : 0);
            obtain.arg2 = (this.mNeedSortFrame ? 1 : 0);
            if (this.mDecoderHandler != null) {
                this.mDecoderHandler.sendMessage(obtain);
            }
        }
    }
    
    public int getDecoderCacheNum() {
        return this.mDecoderCacheNum + this.mNALList.size();
    }
    
    @Override
    public void onDecodeFrame(final TXSVideoFrame txsVideoFrame, final int n, final int n2, final long n3, final long n4, final int n5) {
        if (TXCVideoDecoder.mDecodeFirstFrameTS == 0L) {
            TXCVideoDecoder.mDecodeFirstFrameTS = TXCTimeUtil.getTimeTick();
            Log.i("TXCVideoDecoder", "MediaCodec onDecodeFrame: decode first frame success:" + this.mUserId + "_" + this.mStreamType);
            TXCStatus.a(this.mUserId, 5005, this.mStreamType, TXCVideoDecoder.mDecodeFirstFrameTS);
            TXCStatus.a(this.mUserId, 5004, this.mStreamType, this.mHevc ? 3 : 1);
        }
        if (this.mDecoderListener != null) {
            this.mDecoderListener.onDecodeFrame(txsVideoFrame, n, n2, n3, n4, n5);
        }
        if (this.mDecoderCacheNum > 0) {
            --this.mDecoderCacheNum;
        }
        if (txsVideoFrame == null) {
            synchronized (this) {
                this.nativeNotifyPts(this.mNativeContext, n3, n, n2);
            }
        }
        final int getDecodeCost = this.mVideoDecoder.GetDecodeCost();
        if (this.mHWDec) {
            TXCStatus.a(this.mUserId, 8004, this.mStreamType, getDecodeCost);
        }
        else {
            TXCStatus.a(this.mUserId, 8003, this.mStreamType, getDecodeCost);
        }
    }
    
    @Override
    public void onVideoSizeChange(final int mVideoWidth, final int mVideoHeight) {
        final f mDecoderListener = this.mDecoderListener;
        if (mDecoderListener != null && (this.mVideoWidth != mVideoWidth || this.mVideoHeight != mVideoHeight)) {
            this.mVideoWidth = mVideoWidth;
            this.mVideoHeight = mVideoHeight;
            mDecoderListener.onVideoSizeChange(this.mVideoWidth, this.mVideoHeight);
        }
    }
    
    @Override
    public void onDecodeFailed(final int n) {
        if (this.mDecoderListener != null) {
            this.mDecoderListener.onDecodeFailed(n);
        }
        synchronized (this) {
            this.nativeDecCache(this.mNativeContext);
        }
    }
    
    public boolean isHevc() {
        final a mDecoderHandler = this.mDecoderHandler;
        return mDecoderHandler != null && mDecoderHandler.a();
    }
    
    public long GetDecodeFirstFrameTS() {
        return TXCVideoDecoder.mDecodeFirstFrameTS;
    }
    
    private int startDecodeThread() {
        synchronized (this) {
            if (this.mDecoderHandler != null) {
                TXCLog.e("TXCVideoDecoder", "play:decode: start decoder error when decoder is started");
                return -1;
            }
            this.mDecoderCacheNum = 0;
            this.mRestarting = false;
            final HandlerThread handlerThread = new HandlerThread("VDecoder");
            handlerThread.start();
            if (this.mHWDec) {
                handlerThread.setName("VideoWDec" + handlerThread.getId());
            }
            else {
                handlerThread.setName("VideoSWDec" + handlerThread.getId());
            }
            final a mDecoderHandler = new a(handlerThread.getLooper());
            mDecoderHandler.a(this.mHevc, this.mHWDec, this.mSurface, this.mSps, this.mPps, this, this);
            TXCLog.w("TXCVideoDecoder", "play:decode: start decode thread");
            final Message obtain = Message.obtain();
            obtain.what = 100;
            obtain.obj = this.mNeedSortFrame;
            mDecoderHandler.sendMessage(obtain);
            this.mDecoderHandler = mDecoderHandler;
        }
        return 0;
    }
    
    private void stopDecodeThread() {
        synchronized (this) {
            if (this.mDecoderHandler != null) {
                this.mDecoderHandler.sendEmptyMessage(102);
            }
            this.mDecoderHandler = null;
        }
    }
    
    private void notifyDecoderStartEvent(final boolean b) {
        TXCEventRecorderProxy.a(this.mUserId, 4005, b ? 1 : 0, -1L, "", this.mStreamType);
        final Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", 2008);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putCharSequence("EVT_MSG", (CharSequence)(b ? "Enables hardware decoding" : "Enables software decoding"));
        bundle.putInt("EVT_PARAM1", b ? 1 : 2);
        com.tencent.liteav.basic.util.f.a(this.mNotifyListener, this.mUserId, 2008, bundle);
        TXCKeyPointReportProxy.a(this.mUserId, 40026, b ? 1L : 2L, this.mStreamType);
    }
    
    private boolean hasSurface() {
        return this.mSurface != null;
    }
    
    private native long nativeCreateContext(final boolean p0);
    
    private native void nativeDestroyContext(final long p0);
    
    private native void nativeSetID(final long p0, final String p1);
    
    private native void nativeSetStreamType(final long p0, final int p1);
    
    private native void nativeDecCache(final long p0);
    
    private native void nativeNotifyPts(final long p0, final long p1, final int p2, final int p3);
    
    private native void nativeDecodeFrame(final long p0, final byte[] p1, final int p2, final long p3, final long p4, final int p5, final int p6);
    
    private native void nativeEnableDecodeChange(final long p0, final boolean p1);
    
    private native void nativeEnableRestartDecoder(final long p0, final boolean p1);
    
    private void onDecodeDone(final TXSVideoFrame txsVideoFrame, final int n, final int n2, final long pts, final long n3, final int rotation, final int frameType) {
        if (TXCVideoDecoder.mDecodeFirstFrameTS == 0L) {
            TXCVideoDecoder.mDecodeFirstFrameTS = TXCTimeUtil.getTimeTick();
            Log.i("TXCVideoDecoder", "SoftDecode onDecodeFrame: decode first frame success:" + this.mUserId + "_" + this.mStreamType);
            TXCStatus.a(this.mUserId, 5005, this.mStreamType, TXCVideoDecoder.mDecodeFirstFrameTS);
            TXCStatus.a(this.mUserId, 5004, this.mStreamType, this.mHevc ? 2 : 0);
        }
        final f mDecoderListener = this.mDecoderListener;
        if (mDecoderListener != null) {
            txsVideoFrame.width = n;
            txsVideoFrame.height = n2;
            txsVideoFrame.rotation = rotation;
            txsVideoFrame.pts = pts;
            txsVideoFrame.frameType = frameType;
            mDecoderListener.onDecodeFrame(txsVideoFrame, n, n2, pts, n3, rotation);
            if (this.mVideoWidth != n || this.mVideoHeight != n2) {
                this.mVideoWidth = n;
                this.mVideoHeight = n2;
                mDecoderListener.onVideoSizeChange(this.mVideoWidth, this.mVideoHeight);
            }
        }
    }
    
    private void decodeFrame(final byte[] nalData, final long pts, final long dts, final int rotation, final int codecId, final int n, final int n2, final int nalType) {
        final TXSNALPacket txsnalPacket = new TXSNALPacket();
        txsnalPacket.nalData = nalData;
        txsnalPacket.pts = pts;
        txsnalPacket.dts = dts;
        txsnalPacket.rotation = rotation;
        txsnalPacket.codecId = codecId;
        txsnalPacket.nalType = nalType;
        synchronized (this) {
            if (this.mNativeContext != 0L && this.mVideoDecoder == null) {
                final e mVideoDecoder = new e();
                mVideoDecoder.a(n, n2);
                mVideoDecoder.setListener(this);
                mVideoDecoder.setNotifyListener(this.mNotifyListener);
                mVideoDecoder.a(this.mDecFormat);
                mVideoDecoder.config(this.mSurface);
                mVideoDecoder.enableLimitDecCache(this.mEnableLimitDecCache);
                mVideoDecoder.start(this.mSps, this.mPps, this.mNeedSortFrame, this.mHevc);
                this.notifyDecoderStartEvent(true);
                this.mVideoDecoder = mVideoDecoder;
            }
            if (this.mVideoDecoder != null) {
                this.mVideoDecoder.decode(txsnalPacket);
            }
        }
    }
    
    private synchronized void stopHWDecoder() {
        if (this.mVideoDecoder != null) {
            this.mVideoDecoder.stop();
            this.mVideoDecoder.setListener(null);
            this.mVideoDecoder.setNotifyListener(null);
            this.mVideoDecoder = null;
        }
    }
    
    private void onStartDecoder(final boolean b) {
        this.notifyDecoderStartEvent(b);
    }
    
    static {
        TXCVideoDecoder.mDecodeFirstFrameTS = 0L;
        com.tencent.liteav.basic.util.f.f();
    }
    
    private static class a extends Handler
    {
        com.tencent.liteav.videodecoder.b a;
        f b;
        WeakReference<b> c;
        boolean d;
        boolean e;
        Surface f;
        private ByteBuffer g;
        private ByteBuffer h;
        
        public a(final Looper looper) {
            super(looper);
        }
        
        public void a(final boolean e, final boolean d, final Surface f, final ByteBuffer g, final ByteBuffer h, final f b, final b b2) {
            this.e = e;
            this.d = d;
            this.f = f;
            this.g = g;
            this.h = h;
            this.b = b;
            this.c = new WeakReference<b>(b2);
        }
        
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 100: {
                    this.a((boolean)message.obj);
                    break;
                }
                case 101: {
                    try {
                        final Bundle data = message.getData();
                        this.a(data.getByteArray("nal"), data.getLong("pts"), data.getLong("dts"), data.getInt("codecId"));
                    }
                    catch (Exception ex) {
                        TXCLog.e("TXCVideoDecoder", "decode frame failed." + ex.getMessage());
                    }
                    break;
                }
                case 102: {
                    this.b();
                    break;
                }
                case 103: {
                    this.a(message.arg1 == 1, message.arg2 == 1);
                    break;
                }
            }
        }
        
        public boolean a() {
            return this.a != null && this.a.isHevc();
        }
        
        private void a(final byte[] nalData, final long pts, final long dts, final int codecId) {
            final TXSNALPacket txsnalPacket = new TXSNALPacket();
            txsnalPacket.nalData = nalData;
            txsnalPacket.pts = pts;
            txsnalPacket.dts = dts;
            txsnalPacket.codecId = codecId;
            if (this.a != null) {
                this.a.decode(txsnalPacket);
            }
        }
        
        private void b() {
            if (this.a != null) {
                this.a.stop();
                this.a.setListener(null);
                this.a.setNotifyListener(null);
                this.a = null;
            }
            Looper.myLooper().quit();
            TXCLog.w("TXCVideoDecoder", "play:decode: stop decode hwdec: " + this.d);
        }
        
        private void a(final boolean d, final boolean b) {
            this.d = d;
            TXCLog.w("TXCVideoDecoder", "play:decode: restart decode hwdec: " + this.d);
            if (this.a != null) {
                this.a.stop();
                this.a.setListener(null);
                this.a.setNotifyListener(null);
                this.a = null;
            }
            this.a(b);
        }
        
        private void a(final boolean b) {
            if (this.a != null) {
                TXCLog.i("TXCVideoDecoder", "play:decode: start decode ignore hwdec: " + this.d);
                return;
            }
            if (this.d) {
                this.a = new e();
            }
            else {
                this.a = new TXCVideoFfmpegDecoder();
            }
            this.a.setListener(this.b);
            this.a.setNotifyListener(this.c);
            this.a.config(this.f);
            this.a.start(this.g, this.h, b, this.e);
            TXCLog.w("TXCVideoDecoder", "play:decode: start decode hwdec: " + this.d + ", hevc: " + this.e);
        }
    }
}
