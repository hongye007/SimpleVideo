package com.tencent.rtmp.downloader;

import com.tencent.liteav.network.*;

public class TXVodDownloadMediaInfo
{
    protected TXVodDownloadDataSource dataSource;
    protected int duration;
    protected int size;
    protected int downloadSize;
    protected float progress;
    protected String playPath;
    protected int tid;
    protected f netApi;
    protected boolean isStop;
    protected String url;
    
    public TXVodDownloadMediaInfo() {
        this.tid = -1;
    }
    
    public TXVodDownloadDataSource getDataSource() {
        return this.dataSource;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getDownloadSize() {
        return this.downloadSize;
    }
    
    public float getProgress() {
        if (this.size > 0) {
            return this.downloadSize / (float)this.size;
        }
        return 0.0f;
    }
    
    public String getPlayPath() {
        return this.playPath;
    }
    
    public int getTaskId() {
        return this.tid;
    }
    
    public String getUrl() {
        return this.url;
    }
}
