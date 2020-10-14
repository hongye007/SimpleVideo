package com.tencent.rtmp.downloader;

public interface ITXVodDownloadListener
{
    void onDownloadStart(final TXVodDownloadMediaInfo p0);
    
    void onDownloadProgress(final TXVodDownloadMediaInfo p0);
    
    void onDownloadStop(final TXVodDownloadMediaInfo p0);
    
    void onDownloadFinish(final TXVodDownloadMediaInfo p0);
    
    void onDownloadError(final TXVodDownloadMediaInfo p0, final int p1, final String p2);
    
    int hlsKeyVerify(final TXVodDownloadMediaInfo p0, final String p1, final byte[] p2);
}
