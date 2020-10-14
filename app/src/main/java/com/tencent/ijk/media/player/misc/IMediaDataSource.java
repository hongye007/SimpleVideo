package com.tencent.ijk.media.player.misc;

import java.io.*;

public interface IMediaDataSource
{
    int readAt(final long p0, final byte[] p1, final int p2, final int p3) throws IOException;
    
    long getSize() throws IOException;
    
    void close() throws IOException;
}
