package com.tencent.rtmp;

public class TXBitrateItem implements Comparable<TXBitrateItem>
{
    public int index;
    public int width;
    public int height;
    public int bitrate;
    
    @Override
    public int compareTo(final TXBitrateItem txBitrateItem) {
        return this.bitrate - txBitrateItem.bitrate;
    }
}
