package com.tencent.liteav.beauty;

import android.util.*;
import java.nio.*;
import com.tencent.liteav.basic.util.*;

public class NativeLoad
{
    private static final String TAG = "NativeLoad";
    
    private NativeLoad() {
        OnLoadBeauty();
        Log.e("NativeLoad", "NativeLoad: load jni");
    }
    
    public static NativeLoad getInstance() {
        return a.a;
    }
    
    public static native void OnLoadBeauty();
    
    public static native void nativeGlReadPixs(final int p0, final int p1, final byte[] p2);
    
    public static native void nativeGlMapBufferToQueue(final int p0, final int p1, final ByteBuffer p2);
    
    public static native void nativeGlReadPixsToQueue(final int p0, final int p1);
    
    public static native boolean nativeGlReadPixsFromQueue(final int p0, final int p1, final byte[] p2);
    
    public static native void nativeClearQueue();
    
    public static native void nativeglTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final byte[] p8, final int p9);
    
    public static native void nativeDeleteYuv2Yuv();
    
    public static native int nativeLoadGLProgram(final int p0);
    
    private static class a
    {
        public static final NativeLoad a;
        
        static {
            f.f();
            a = new NativeLoad(null);
        }
    }
}
