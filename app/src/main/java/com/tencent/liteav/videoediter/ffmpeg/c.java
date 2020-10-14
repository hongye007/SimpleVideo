package com.tencent.liteav.videoediter.ffmpeg;

import android.annotation.*;

import java.util.concurrent.atomic.*;
import com.tencent.liteav.videoediter.ffmpeg.jni.*;
import java.util.*;
import android.media.*;
import com.tencent.liteav.basic.log.*;
import android.view.*;
import java.nio.*;

@TargetApi(16)
public class c implements b
{
    private ByteBuffer c;
    private int d;
    private int e;
    private int f;
    private TXFFAudioDecoderJNI g;
    private List<e> h;
    private AtomicBoolean i;
    public static String[] b;
    private FFDecodedFrame j;
    
    public static boolean a(final String s) {
        final String[] b = c.b;
        for (int length = b.length, i = 0; i < length; ++i) {
            if (b[i].equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    private int b(final String s) {
        switch (s) {
            case "audio/mp4a-latm": {
                return 0;
            }
            case "audio/mpeg": {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    public c() {
        this.i = new AtomicBoolean(false);
        this.h = new LinkedList<e>();
        this.h = Collections.synchronizedList(this.h);
    }
    
    @Override
    public void a(final MediaFormat mediaFormat) {
        this.b();
        this.d = mediaFormat.getInteger("channel-count");
        this.e = mediaFormat.getInteger("sample-rate");
        if (mediaFormat.containsKey("max-input-size")) {
            this.f = mediaFormat.getInteger("max-input-size");
        }
        final ByteBuffer byteBuffer = mediaFormat.getByteBuffer("csd-0");
        if (byteBuffer != null) {
            byteBuffer.position(0);
        }
        final String string = mediaFormat.getString("mime");
        (this.g = new TXFFAudioDecoderJNI()).configureInput(this.b(string), byteBuffer, (byteBuffer != null) ? byteBuffer.capacity() : 0, this.e, this.d);
        final int n = 1024 * this.d * 2;
        this.c = ByteBuffer.allocateDirect((n > this.f) ? n : this.f);
        TXCLog.i("TXSWAudioDecoder", "createDecoderByFormat: type = " + string + ", mediaFormat = " + mediaFormat.toString() + ", calculateBufferSize = " + n + ", mMaxInputSize = " + this.f);
    }
    
    @Override
    public void a(final MediaFormat mediaFormat, final Surface surface) {
    }
    
    @Override
    public void a() {
        if (this.i.get()) {
            TXCLog.e("TXSWAudioDecoder", "start error: decoder have been started!");
            return;
        }
        this.h.clear();
        this.i.set(true);
    }
    
    @Override
    public void b() {
        if (!this.i.get()) {
            TXCLog.e("TXSWAudioDecoder", "stop error: decoder isn't starting yet!!");
            return;
        }
        this.h.clear();
        if (this.g != null) {
            this.g.release();
            this.g = null;
        }
        this.i.set(false);
    }
    
    @Override
    public e c() {
        if (!this.i.get()) {
            TXCLog.e("TXSWAudioDecoder", "find frame error: decoder isn't starting yet!!");
            return null;
        }
        this.c.position(0);
        final e e = new e();
        e.a(this.c);
        e.h(this.d);
        e.g(this.e);
        e.d(this.c.capacity());
        return e;
    }
    
    @Override
    public void a(final e e) {
        if (!this.i.get()) {
            TXCLog.e("TXSWAudioDecoder", "decode error: decoder isn't starting yet!!");
            return;
        }
        if (e.f() == 1) {
            final byte[] a = this.a(e.b(), e.g());
            if (a == null) {
                this.j = null;
                return;
            }
            this.j = this.g.decode(a, e.e(), e.f());
        }
        else if (e.f() == 4) {
            this.j = new FFDecodedFrame();
            this.j.data = new byte[0];
            this.j.flags = 4;
            this.j.pts = e.e();
        }
        e.a((ByteBuffer)null);
        e.d(0);
        this.c.position(0);
    }
    
    private byte[] a(final ByteBuffer byteBuffer, final int n) {
        final byte[] array = new byte[n];
        try {
            byteBuffer.get(array);
        }
        catch (BufferUnderflowException ex) {
            ex.printStackTrace();
            return null;
        }
        return array;
    }
    
    @Override
    public e d() {
        if (!this.i.get()) {
            TXCLog.e("TXSWAudioDecoder", "decode error: decoder isn't starting yet!!");
            return null;
        }
        if (this.j != null && this.j.data != null) {
            final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.j.data.length);
            allocateDirect.put(this.j.data);
            allocateDirect.position(0);
            final e e = new e();
            e.a(allocateDirect);
            e.d(this.j.data.length);
            e.a(this.j.pts);
            e.c(this.j.flags);
            e.h(this.d);
            e.g(this.j.sampleRate);
            this.j = null;
            return e;
        }
        return null;
    }
    
    static {
        c.b = new String[] { "audio/mp4a-latm", "audio/mpeg" };
    }
}
