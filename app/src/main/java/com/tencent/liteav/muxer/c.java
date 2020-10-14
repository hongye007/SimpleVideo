package com.tencent.liteav.muxer;

import android.content.*;
import com.tencent.liteav.basic.log.*;
import java.nio.*;
import android.media.*;

public class c implements a
{
    private int a;
    private a b;
    
    public c(final Context context, final int n) {
        this.a = 0;
        switch (n) {
            case 0: {
                this.a = 0;
                this.b = new d();
                TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use sw model ");
                break;
            }
            case 1: {
                this.a = 1;
                this.b = new b();
                TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use hw model ");
                break;
            }
            default: {
                if (a(context)) {
                    this.a = 0;
                    this.b = new d();
                    TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use sw model ");
                    break;
                }
                this.a = 1;
                this.b = new b();
                TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use hw model ");
                break;
            }
        }
    }
    
    public static boolean a(final Context context) {
        com.tencent.liteav.basic.d.b.a().a(context);
        return com.tencent.liteav.basic.d.b.a().e() == 1;
    }
    
    @Override
    public void a(final MediaFormat mediaFormat) {
        this.b.a(mediaFormat);
    }
    
    @Override
    public void b(final MediaFormat mediaFormat) {
        this.b.b(mediaFormat);
    }
    
    @Override
    public void a(final String s) {
        this.b.a(s);
    }
    
    @Override
    public void a(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        this.b.a(byteBuffer, bufferInfo);
    }
    
    @Override
    public void a(final byte[] array, final int n, final int n2, final long n3, final int n4) {
        this.b.a(array, n, n2, n3, n4);
    }
    
    @Override
    public void b(final byte[] array, final int n, final int n2, final long n3, final int n4) {
        this.b.b(array, n, n2, n3, n4);
    }
    
    @Override
    public int a() {
        return this.b.a();
    }
    
    @Override
    public int b() {
        return this.b.b();
    }
    
    @Override
    public void a(final int n) {
        this.b.a(n);
    }
    
    @Override
    public boolean c() {
        return this.b.c();
    }
    
    @Override
    public boolean d() {
        return this.b.d();
    }
}
