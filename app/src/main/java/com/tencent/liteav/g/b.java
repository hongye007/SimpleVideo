package com.tencent.liteav.g;

import android.media.*;
import android.view.*;

import com.tencent.liteav.basic.log.*;

public interface b
{
    public static final a a = new a() {
        @Override
        public void a(final String s) {
            TXCLog.e("IMediaDecoder", "onDecoderError ->   msg = " + s);
        }
    };
    
    void a(final MediaFormat p0);
    
    void a(final MediaFormat p0, final Surface p1);
    
    void a();
    
    void b();
    
    e c();
    
    void a(final e p0);
    
    e d();
    
    public interface a
    {
        void a(final String p0);
    }
}
