package com.tencent.liteav.muxer;

import java.nio.*;
import android.media.*;

public interface a
{
    void a(final MediaFormat p0);
    
    void b(final MediaFormat p0);
    
    void a(final String p0);
    
    void a(final ByteBuffer p0, final MediaCodec.BufferInfo p1);
    
    void a(final byte[] p0, final int p1, final int p2, final long p3, final int p4);
    
    void b(final byte[] p0, final int p1, final int p2, final long p3, final int p4);
    
    int a();
    
    int b();
    
    void a(final int p0);
    
    boolean c();
    
    boolean d();
}
