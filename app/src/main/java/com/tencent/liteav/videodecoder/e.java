package com.tencent.liteav.videodecoder;

import android.view.*;
import java.lang.ref.*;
import com.tencent.liteav.basic.log.*;
import java.nio.*;
import org.json.*;
import com.tencent.liteav.basic.util.*;
import java.util.*;
import android.annotation.*;
import com.tencent.liteav.basic.structs.*;
import android.os.*;
import android.media.*;

public class e implements b
{
    private MediaCodec.BufferInfo a;
    private MediaCodec b;
    private String c;
    private int d;
    private int e;
    private long f;
    private long g;
    private boolean h;
    private boolean i;
    private boolean j;
    private Surface k;
    private int l;
    private ArrayList<TXSNALPacket> m;
    private ArrayList<Long> n;
    private long o;
    private int p;
    private JSONArray q;
    private f r;
    private d s;
    private boolean t;
    private WeakReference<com.tencent.liteav.basic.b.b> u;
    
    public e() {
        this.a = new MediaCodec.BufferInfo();
        this.b = null;
        this.c = "video/avc";
        this.d = 540;
        this.e = 960;
        this.f = 0L;
        this.g = 0L;
        this.h = true;
        this.i = false;
        this.j = false;
        this.k = null;
        this.l = 0;
        this.m = new ArrayList<TXSNALPacket>();
        this.n = new ArrayList<Long>();
        this.o = 0L;
        this.p = 0;
        this.q = null;
        this.s = new d();
        this.t = false;
    }
    
    @Override
    public void setListener(final f r) {
        this.r = r;
    }
    
    @Override
    public void setNotifyListener(final WeakReference<com.tencent.liteav.basic.b.b> u) {
        this.u = u;
    }
    
    @Override
    public int config(final Surface k) {
        if (k == null) {
            return -1;
        }
        this.k = k;
        return 0;
    }
    
    @Override
    public void decode(final TXSNALPacket txsnalPacket) {
        this.a(txsnalPacket.codecId == 1);
        this.a(txsnalPacket);
        this.m.add(txsnalPacket);
        while (!this.m.isEmpty()) {
            final int size = this.m.size();
            try {
                this.b();
            }
            catch (Exception ex) {
                TXCLog.e("MediaCodecDecoder", "decode: doDecode Exception!! " + ex.toString());
            }
            if (size == this.m.size()) {
                break;
            }
        }
    }
    
    @Override
    public int start(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final boolean b, final boolean b2) {
        return this.a(byteBuffer, byteBuffer2, b2);
    }
    
    @Override
    public void stop() {
        this.a();
    }
    
    @Override
    public boolean isHevc() {
        return this.j;
    }
    
    @Override
    public int GetDecodeCost() {
        return this.p;
    }
    
    public void a(final JSONArray q) {
        this.q = q;
    }
    
    @Override
    public void enableLimitDecCache(final boolean t) {
        this.t = t;
        TXCLog.i("MediaCodecDecoder", "decode: enable limit dec cache: " + t);
    }
    
    public void a(final int d, final int e) {
        if (d > 0 && e > 0) {
            this.d = d;
            this.e = e;
            TXCLog.w("MediaCodecDecoder", "decode: init with video size: " + this.d + ", " + this.e);
        }
    }
    
    private int a(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final boolean j) {
        int n = -1;
        int n2 = 0;
        try {
            if (this.b != null || this.k == null) {
                TXCLog.e("MediaCodecDecoder", "decode: init decoder error, can not init for decoder=" + this.b + ",surface=" + this.k);
                return n;
            }
            this.j = j;
            if (this.j) {
                this.c = "video/hevc";
            }
            else {
                this.c = "video/avc";
            }
            final MediaFormat videoFormat = MediaFormat.createVideoFormat(this.c, this.d, this.e);
            if (byteBuffer != null) {
                videoFormat.setByteBuffer("csd-0", byteBuffer);
            }
            if (byteBuffer2 != null) {
                videoFormat.setByteBuffer("csd-1", byteBuffer2);
            }
            final JSONArray q = this.q;
            if (q != null) {
                try {
                    for (int i = 0; i < q.length(); ++i) {
                        final JSONObject jsonObject = q.getJSONObject(i);
                        videoFormat.setInteger(jsonObject.optString("key"), jsonObject.optInt("value"));
                    }
                }
                catch (Exception ex) {
                    TXCLog.w("MediaCodecDecoder", "config custom format error " + ex.toString());
                }
            }
            this.b = MediaCodec.createDecoderByType(this.c);
            n2 = 1;
            this.b.configure(videoFormat, this.k, (MediaCrypto)null, 0);
            n2 = 2;
            this.b.setVideoScalingMode(1);
            n2 = 3;
            this.b.start();
            n2 = 4;
            TXCLog.w("MediaCodecDecoder", "decode: start decoder success, is hevc: " + this.j + " w = " + this.d + " h = " + this.e + ", format = " + videoFormat.toString());
            n = 0;
            this.l = 0;
        }
        catch (Exception ex2) {
            if (this.b != null) {
                try {
                    this.b.release();
                    TXCLog.w("MediaCodecDecoder", "decode: , decoder release success");
                }
                catch (Exception ex3) {
                    TXCLog.e("MediaCodecDecoder", "decode: , decoder release exception: " + ex2.toString());
                }
                finally {
                    this.b = null;
                }
            }
            TXCLog.e("MediaCodecDecoder", "decode: init decoder " + n2 + " step exception: " + ex2.toString());
            ex2.printStackTrace();
            this.f();
        }
        return n;
    }
    
    private void a() {
        if (this.b != null) {
            try {
                this.b.stop();
                TXCLog.w("MediaCodecDecoder", "decode: stop decoder sucess");
            }
            catch (Exception ex) {
                TXCLog.e("MediaCodecDecoder", "decode: stop decoder Exception: " + ex.toString());
                try {
                    this.b.release();
                    TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                }
                catch (Exception ex2) {
                    TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + ex2.toString());
                }
                finally {
                    this.b = null;
                }
            }
            finally {
                try {
                    this.b.release();
                    TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                }
                catch (Exception ex3) {
                    TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + ex3.toString());
                    this.b = null;
                }
                finally {
                    this.b = null;
                }
            }
            this.m.clear();
            this.f = 0L;
            this.h = true;
        }
    }
    
    @TargetApi(16)
    private void b() {
        if (this.b == null) {
            TXCLog.e("MediaCodecDecoder", "null decoder");
            return;
        }
        final TXSNALPacket txsnalPacket = this.m.get(0);
        if (txsnalPacket == null || txsnalPacket.nalData.length == 0) {
            TXCLog.e("MediaCodecDecoder", "decode: empty buffer");
            this.m.remove(0);
            return;
        }
        final long timeTick = TXCTimeUtil.getTimeTick();
        if (this.o == 0L) {
            this.o = timeTick;
        }
        ByteBuffer[] inputBuffers = null;
        try {
            inputBuffers = this.b.getInputBuffers();
        }
        catch (Exception ex) {
            TXCLog.e("MediaCodecDecoder", "decode: getInputBuffers Exception!! " + ex.toString());
        }
        if (inputBuffers == null || inputBuffers.length == 0) {
            TXCLog.e("MediaCodecDecoder", "decode: getInputBuffers failed");
            return;
        }
        int dequeueInputBuffer = -10000;
        try {
            dequeueInputBuffer = this.b.dequeueInputBuffer(10000L);
        }
        catch (Exception ex2) {
            TXCLog.e("MediaCodecDecoder", "decode: dequeueInputBuffer Exception!! " + ex2.toString());
        }
        if (dequeueInputBuffer >= 0) {
            inputBuffers[dequeueInputBuffer].put(txsnalPacket.nalData);
            try {
                this.b.queueInputBuffer(dequeueInputBuffer, 0, txsnalPacket.nalData.length, txsnalPacket.pts, 0);
                this.m.remove(0);
            }
            catch (Exception ex5) {
                this.g();
            }
            if (this.f == 0L) {
                TXCLog.w("MediaCodecDecoder", "decode: input buffer available, dequeueInputBuffer index: " + dequeueInputBuffer);
            }
        }
        else {
            TXCLog.w("MediaCodecDecoder", "decode: input buffer not available, dequeueInputBuffer failed");
        }
        final boolean b = false;
        int dequeueOutputBuffer = -10000;
        try {
            dequeueOutputBuffer = this.b.dequeueOutputBuffer(this.a, b ? 0L : 10000L);
        }
        catch (Exception ex3) {
            this.g();
            TXCLog.e("MediaCodecDecoder", "decode: dequeueOutputBuffer exception!!" + ex3);
        }
        if (dequeueOutputBuffer >= 0) {
            this.a(dequeueOutputBuffer, this.a.presentationTimeUs, this.a.presentationTimeUs, txsnalPacket.rotation);
            this.l = 0;
        }
        else if (dequeueOutputBuffer == -1) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException ex4) {
                ex4.printStackTrace();
            }
            TXCLog.i("MediaCodecDecoder", "decode: no output from decoder available when timeout fail count " + this.l);
            this.g();
        }
        else if (dequeueOutputBuffer == -3) {
            TXCLog.i("MediaCodecDecoder", "decode: output buffers changed");
        }
        else if (dequeueOutputBuffer == -2) {
            this.c();
        }
        else {
            TXCLog.e("MediaCodecDecoder", "decode: unexpected result from decoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
        }
        final long timeTick2 = TXCTimeUtil.getTimeTick();
        this.n.add(timeTick2 - timeTick);
        if (timeTick2 > this.o + 1000L) {
            long longValue = 0L;
            for (final Long n : this.n) {
                if (n > longValue) {
                    longValue = n;
                }
            }
            this.n.clear();
            this.o = timeTick2;
            this.p = (int)(longValue * 3L);
        }
    }
    
    private void a(final int n, final long n2, final long n3, final int n4) {
        this.b.releaseOutputBuffer(n, true);
        if ((this.a.flags & 0x4) != 0x0) {
            TXCLog.i("MediaCodecDecoder", "output EOS");
        }
        try {
            if (this.r != null) {
                this.r.onDecodeFrame(null, this.d, this.e, n2, n3, n4);
            }
        }
        catch (Exception ex) {
            TXCLog.e("MediaCodecDecoder", "onDecodeFrame failed.", ex);
        }
        this.d();
    }
    
    private void c() {
        final MediaFormat outputFormat = this.b.getOutputFormat();
        TXCLog.i("MediaCodecDecoder", "decode output format changed: " + outputFormat);
        final int n = Math.abs(outputFormat.getInteger("crop-right") - outputFormat.getInteger("crop-left")) + 1;
        final int n2 = Math.abs(outputFormat.getInteger("crop-bottom") - outputFormat.getInteger("crop-top")) + 1;
        final int integer = outputFormat.getInteger("width");
        final int integer2 = outputFormat.getInteger("height");
        final int min = Math.min(n, integer);
        final int min2 = Math.min(n2, integer2);
        if (min != this.d || min2 != this.e) {
            this.d = min;
            this.e = min2;
            try {
                if (this.r != null) {
                    this.r.onVideoSizeChange(this.d, this.e);
                }
            }
            catch (Exception ex) {
                TXCLog.e("MediaCodecDecoder", "onVideoSizeChange failed.", ex);
            }
            TXCLog.i("MediaCodecDecoder", "decode: video size change to w:" + min + ",h:" + min2);
        }
        else if (this.h) {
            this.h = false;
            if (this.r != null) {
                this.r.onVideoSizeChange(this.d, this.e);
            }
        }
    }
    
    private void d() {
        if (this.f == 0L) {
            TXCLog.w("MediaCodecDecoder", "decode first frame sucess");
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.f > 0L && currentTimeMillis > this.f + 1000L && currentTimeMillis > this.g + 2000L && this.g != 0L) {
            TXCLog.e("MediaCodecDecoder", "frame interval[" + (currentTimeMillis - this.f) + "] > " + 1000L);
            this.g = currentTimeMillis;
        }
        if (this.g == 0L) {
            this.g = currentTimeMillis;
        }
        this.f = currentTimeMillis;
    }
    
    private boolean e() {
        if (Build.VERSION.SDK_INT >= 21) {
            for (final MediaCodecInfo mediaCodecInfo : new MediaCodecList(1).getCodecInfos()) {
                final String[] supportedTypes = mediaCodecInfo.getSupportedTypes();
                for (int length2 = supportedTypes.length, j = 0; j < length2; ++j) {
                    if (supportedTypes[j].contains("video/hevc")) {
                        TXCLog.e("MediaCodecDecoder", "decode: video/hevc MediaCodecInfo: " + mediaCodecInfo.getName() + ",encoder:" + mediaCodecInfo.isEncoder());
                        return true;
                    }
                }
            }
            return false;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            for (int codecCount = MediaCodecList.getCodecCount(), k = 0; k < codecCount; ++k) {
                final MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(k);
                final String[] supportedTypes2 = codecInfo.getSupportedTypes();
                for (int length3 = supportedTypes2.length, l = 0; l < length3; ++l) {
                    if (supportedTypes2[l].contains("video/hevc")) {
                        TXCLog.e("MediaCodecDecoder", "video/hevc MediaCodecInfo: " + codecInfo.getName() + ",encoder:" + codecInfo.isEncoder());
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void f() {
        if (!this.i) {
            TXCLog.w("MediaCodecDecoder", "decode: hw decode error, hevc: " + this.j);
            if (this.j) {
                com.tencent.liteav.basic.util.f.a(this.u, -2304, "h265 Decoding failed");
            }
            else {
                com.tencent.liteav.basic.util.f.a(this.u, 2106, "Failed to enable hardware decoding\uff0cuse software decoding.");
            }
            this.i = true;
            if (this.r != null) {
                this.r.onDecodeFailed(-1);
            }
        }
    }
    
    private void g() {
        if (this.l >= 40) {
            this.f();
            this.l = 0;
        }
        else {
            ++this.l;
        }
    }
    
    private void a(final boolean j) {
        if (this.j != j) {
            this.j = j;
            if (this.i) {
                return;
            }
            if (this.j && !this.e()) {
                this.a();
                this.f();
            }
            else {
                this.a();
                this.a(null, null, this.j);
            }
        }
    }
    
    private void a(final TXSNALPacket txsnalPacket) {
        if (!this.t) {
            return;
        }
        if (txsnalPacket.nalType == 0) {
            try {
                int n = -1;
                for (int i = 0; i < txsnalPacket.nalData.length; ++i, ++i) {
                    if (txsnalPacket.nalData[i] == 0 && txsnalPacket.nalData[i + 1] == 0 && txsnalPacket.nalData[i + 2] == 0 && txsnalPacket.nalData[i + 3] == 1 && (txsnalPacket.nalData[i + 4] & 0x1F) == 0x7) {
                        n = i + 4;
                        break;
                    }
                    if (txsnalPacket.nalData[i] == 0 && txsnalPacket.nalData[i + 1] == 0 && txsnalPacket.nalData[i + 2] == 0 && (txsnalPacket.nalData[i + 3] & 0x1F) == 0x7) {
                        n = i + 3;
                        break;
                    }
                }
                if (n >= 0) {
                    int n2 = txsnalPacket.nalData.length - n;
                    for (int j = n; j < txsnalPacket.nalData.length; ++j) {
                        if ((txsnalPacket.nalData[j] == 0 && txsnalPacket.nalData[j + 1] == 0 && txsnalPacket.nalData[j + 2] == 1) || (txsnalPacket.nalData[j] == 0 && txsnalPacket.nalData[j + 1] == 0 && txsnalPacket.nalData[j + 2] == 0 && txsnalPacket.nalData[j + 3] == 1)) {
                            n2 = j - n;
                            break;
                        }
                    }
                    final byte[] array = new byte[n2];
                    System.arraycopy(txsnalPacket.nalData, n, array, 0, n2);
                    final byte[] a = this.s.a(array);
                    if (a != null) {
                        final byte[] nalData = new byte[txsnalPacket.nalData.length + a.length - n2];
                        if (n > 0) {
                            System.arraycopy(txsnalPacket.nalData, 0, nalData, 0, n);
                        }
                        System.arraycopy(a, 0, nalData, n, a.length);
                        System.arraycopy(txsnalPacket.nalData, n + n2, nalData, n + a.length, txsnalPacket.nalData.length - n2 - n);
                        txsnalPacket.nalData = nalData;
                    }
                }
            }
            catch (Exception ex) {
                TXCLog.e("MediaCodecDecoder", "modify dec buffer error ", ex);
            }
        }
    }
}
