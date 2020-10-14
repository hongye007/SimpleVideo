package com.tencent.avroom;

public class TXCAVRoomParam
{
    public long roomID;
    public int authBits;
    public byte[] authBuffer;
    
    public TXCAVRoomParam(final long roomID) {
        this.authBits = -1;
        this.authBuffer = null;
        this.roomID = roomID;
    }
    
    public TXCAVRoomParam authBits(final int authBits) {
        this.authBits = authBits;
        return this;
    }
    
    public TXCAVRoomParam authBuffer(final byte[] authBuffer) {
        this.authBuffer = authBuffer;
        return this;
    }
    
    public long getRoomID() {
        return this.roomID;
    }
    
    public int getAuthBits() {
        return this.authBits;
    }
    
    public byte[] getAuthBuffer() {
        return this.authBuffer;
    }
}
