package com.tencent.ijk.media.player.misc;

public interface IMediaFormat
{
    public static final String KEY_MIME = "mime";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_HEIGHT = "height";
    
    String getString(final String p0);
    
    int getInteger(final String p0);
}
