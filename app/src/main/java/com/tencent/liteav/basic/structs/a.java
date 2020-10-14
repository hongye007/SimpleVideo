package com.tencent.liteav.basic.structs;

public class a implements Cloneable
{
    public int sampleRate;
    public int channelsPerSample;
    public int bitsPerChannel;
    public int packetType;
    public long timestamp;
    public long timestampInSample;
    public long sequenceNum;
    public byte[] audioData;
    public int codecFormat;
    public int audioType;
    
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return o;
    }
}
