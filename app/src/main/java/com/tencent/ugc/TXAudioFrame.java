package com.tencent.ugc;

import java.nio.*;

public class TXAudioFrame
{
    private ByteBuffer mByteBuffer;
    private int mLength;
    
    public ByteBuffer getByteBuffer() {
        return this.mByteBuffer;
    }
    
    public void setByteBuffer(final ByteBuffer mByteBuffer) {
        this.mByteBuffer = mByteBuffer;
    }
    
    public int getLength() {
        return this.mLength;
    }
    
    public void setLength(final int mLength) {
        this.mLength = mLength;
    }
}
