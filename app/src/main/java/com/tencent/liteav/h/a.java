package com.tencent.liteav.h;

import com.tencent.liteav.audio.*;
import com.tencent.liteav.audio.impl.Record.*;
import com.tencent.liteav.muxer.*;
import android.content.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import java.io.*;
import java.lang.ref.*;
import com.tencent.liteav.videoencoder.*;
import java.text.*;
import java.util.*;
import android.text.*;
import android.os.*;
import com.tencent.liteav.basic.structs.*;
import android.media.*;

public class a implements d, com.tencent.liteav.videoencoder.d
{
    private com.tencent.liteav.audio.impl.Record.b a;
    private a b;
    private c c;
    private a d;
    private b e;
    private long f;
    private long g;
    private boolean h;
    private Handler i;
    
    public a(final Context context) {
        this.f = 0L;
        this.g = -1L;
        this.h = false;
        this.i = new Handler(Looper.getMainLooper()) {
            public void handleMessage(final Message message) {
                if (com.tencent.liteav.h.a.this.e != null) {
                    switch (message.what) {
                        case 1: {
                            com.tencent.liteav.h.a.this.e.a((long)message.obj);
                            break;
                        }
                        case 2: {
                            TXCLog.d("TXCStreamRecord", "record complete. errcode = " + message.arg1 + ", errmsg = " + (String)message.obj + ", outputPath = " + com.tencent.liteav.h.a.this.d.f + ", coverImage = " + com.tencent.liteav.h.a.this.d.g);
                            if (message.arg1 == 0 && com.tencent.liteav.h.a.this.d.g != null && !com.tencent.liteav.h.a.this.d.g.isEmpty() && !com.tencent.liteav.basic.util.f.a(com.tencent.liteav.h.a.this.d.f, com.tencent.liteav.h.a.this.d.g)) {
                                TXCLog.e("TXCStreamRecord", "saveVideoThumb error. sourcePath = " + com.tencent.liteav.h.a.this.d.f + ", coverImagePath = " + com.tencent.liteav.h.a.this.d.g);
                            }
                            if (message.arg1 != 0) {
                                try {
                                    final File file = new File(com.tencent.liteav.h.a.this.d.f);
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                }
                                catch (Exception ex) {
                                    TXCLog.e("TXCStreamRecord", "delete file failed.", ex);
                                }
                            }
                            com.tencent.liteav.h.a.this.e.a(message.arg1, (String)message.obj, com.tencent.liteav.h.a.this.d.f, com.tencent.liteav.h.a.this.d.g);
                            break;
                        }
                    }
                }
            }
        };
        this.a = new com.tencent.liteav.audio.impl.Record.b();
        this.b = new a();
        this.c = new c(context, 2);
    }
    
    public void a(final b e) {
        this.e = e;
    }
    
    public void a(final a d) {
        this.d = d;
        this.f = 0L;
        this.g = -1L;
        this.c.a(this.d.f);
        if (d.h > 0 && d.i > 0 && d.j > 0) {
            this.a.a(10, d.i, d.h, d.j, new WeakReference<d>(this));
            this.c.b(com.tencent.liteav.basic.util.f.a(this.d.i, this.d.h, 2));
            this.h = true;
        }
        this.b.setListener(this);
        final TXSVideoEncoderParam txsVideoEncoderParam = new TXSVideoEncoderParam();
        txsVideoEncoderParam.width = this.d.a;
        txsVideoEncoderParam.height = this.d.b;
        txsVideoEncoderParam.fps = this.d.c;
        txsVideoEncoderParam.glContext = this.d.e;
        txsVideoEncoderParam.annexb = true;
        txsVideoEncoderParam.appendSpsPps = false;
        this.b.setBitrate(this.d.d);
        this.b.start(txsVideoEncoderParam);
    }
    
    public void a() {
        this.h = false;
        this.a.a();
        this.b.stop();
        if (this.c.b() < 0) {
            this.i.sendMessage(Message.obtain(this.i, 2, 1, 0, (Object)"mp4\u5408\u6210\u5931\u8d25"));
        }
        else {
            this.i.sendMessage(Message.obtain(this.i, 2, 0, 0, (Object)""));
        }
    }
    
    public void a(final int n, final long n2) {
        this.b.pushVideoFrame(n, this.d.a, this.d.b, n2);
    }
    
    public void a(final byte[] array, final long n) {
        if (this.h) {
            this.a.a(array, n);
        }
        else {
            TXCLog.e("TXCStreamRecord", "drainAudio fail because of not init yet!");
        }
    }
    
    public static String a(final Context context, final String s) {
        if (context == null) {
            return null;
        }
        try {
            final String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Long.valueOf(String.valueOf(System.currentTimeMillis() / 1000L) + "000")));
            final String a = a(context);
            if (TextUtils.isEmpty((CharSequence)a)) {
                return null;
            }
            return new File(a, String.format("TXUGC_%s" + s, format)).getAbsolutePath();
        }
        catch (Exception ex) {
            TXCLog.e("TXCStreamRecord", "create file path failed.", ex);
            return null;
        }
    }
    
    private static String a(final Context context) {
        if (context == null) {
            return null;
        }
        String s = null;
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            final File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            if (externalFilesDir != null) {
                s = externalFilesDir.getPath();
            }
        }
        else {
            s = context.getFilesDir().getPath();
        }
        return s;
    }
    
    private String a(final int n) {
        String s = null;
        switch (n) {
            case 10000005: {
                s = "Video encoding failed";
                break;
            }
            case 10000004: {
                s = "Video encoding failed to initialize";
                break;
            }
            case 10000003: {
                s = "Illegal video input parameters";
                break;
            }
            case 10000002: {
                s = "Video encoder is not activated";
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        this.i.sendMessage(Message.obtain(this.i, 2, 1, 0, (Object)s));
        return s;
    }
    
    @Override
    public void onRecordRawPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4, final boolean b) {
    }
    
    @Override
    public void onRecordPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
    }
    
    @Override
    public void onRecordEncData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
        this.c.a(array, 0, array.length, n * 1000L, 1);
    }
    
    @Override
    public void onRecordError(final int n, final String s) {
    }
    
    @Override
    public void onEncodeNAL(final TXSNALPacket txsnalPacket, final int n) {
        if (n == 0) {
            this.c.b(txsnalPacket.nalData, 0, txsnalPacket.nalData.length, txsnalPacket.pts * 1000L, txsnalPacket.info.flags);
            if (this.g < 0L) {
                this.g = txsnalPacket.pts;
            }
            if (txsnalPacket.pts > this.f + 500L) {
                this.i.sendMessage(Message.obtain(this.i, 1, (Object)new Long(txsnalPacket.pts - this.g)));
                this.f = txsnalPacket.pts;
            }
        }
        else {
            TXCLog.e("TXCStreamRecord", "video encode error! errmsg: " + this.a(n));
        }
    }
    
    @Override
    public void onEncodeFormat(final MediaFormat mediaFormat) {
        this.c.a(mediaFormat);
        if (this.c.c() && this.c.a() < 0) {
            this.i.sendMessage(Message.obtain(this.i, 2, 1, 0, (Object)"mp4 wrapper failed to start"));
        }
    }
    
    @Override
    public void onEncodeFinished(final int n, final long n2, final long n3) {
    }
    
    @Override
    public void onRestartEncoder(final int n) {
    }
    
    @Override
    public void onEncodeDataIn(final int n) {
    }
    
    public static class a
    {
        public int a;
        public int b;
        public int c;
        public int d;
        public Object e;
        public String f;
        public String g;
        public int h;
        public int i;
        public int j;
        
        public a() {
            this.a = 544;
            this.b = 960;
            this.c = 20;
            this.d = 1000;
            this.h = 0;
            this.i = 0;
            this.j = 16;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("TXCStreamRecordParams: [width=" + this.a);
            sb.append("; height=" + this.b);
            sb.append("; fps=" + this.c);
            sb.append("; bitrate=" + this.d);
            sb.append("; channels=" + this.h);
            sb.append("; samplerate=" + this.i);
            sb.append("; bits=" + this.j);
            sb.append("; EGLContext=" + this.e);
            sb.append("; coveriamge=" + this.g);
            sb.append("; outputpath=" + this.f + "]");
            return sb.toString();
        }
    }
    
    public interface b
    {
        void a(final int p0, final String p1, final String p2, final String p3);
        
        void a(final long p0);
    }
}
