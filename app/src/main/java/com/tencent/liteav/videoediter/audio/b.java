package com.tencent.liteav.videoediter.audio;

import java.nio.*;

public class b
{
    public static short[] a(final ByteBuffer byteBuffer, final int n) {
        if (byteBuffer == null) {
            return null;
        }
        final short[] array = new short[n / 2];
        byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer().get(array);
        return array;
    }
    
    public static ByteBuffer a(final short[] array) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(array.length * 2);
        allocateDirect.order(ByteOrder.nativeOrder()).asShortBuffer().put(array);
        allocateDirect.position(0);
        return allocateDirect;
    }
    
    public static ByteBuffer a(final ByteBuffer byteBuffer, final short[] array) {
        final int n = array.length * 2;
        if (byteBuffer != null && byteBuffer.capacity() >= n) {
            byteBuffer.position(0);
            byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer().put(array);
            byteBuffer.limit(n);
            return byteBuffer;
        }
        return a(array);
    }
}
