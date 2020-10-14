package com.tencent.ijk.media.player.misc;

import java.io.*;

public interface IAndroidIO
{
    int open(final String p0) throws IOException;
    
    int read(final byte[] p0, final int p1) throws IOException;
    
    long seek(final long p0, final int p1) throws IOException;
    
    int close() throws IOException;
}
