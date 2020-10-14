package com.tencent.rtmp.downloader;

import com.tencent.rtmp.*;

public class TXVodDownloadDataSource
{
    public static final int QUALITY_OD = 0;
    public static final int QUALITY_FLU = 1;
    public static final int QUALITY_SD = 2;
    public static final int QUALITY_HD = 3;
    public static final int QUALITY_FHD = 4;
    public static final int QUALITY_2K = 5;
    public static final int QUALITY_4K = 6;
    public static final int QUALITY_UNK = 1000;
    protected int quality;
    protected TXPlayerAuthBuilder authBuilder;
    protected String token;
    protected String templateName;
    
    public TXVodDownloadDataSource(final TXPlayerAuthBuilder authBuilder, final int quality) {
        this.quality = 1000;
        this.authBuilder = authBuilder;
        this.quality = quality;
    }
    
    public TXVodDownloadDataSource(final TXPlayerAuthBuilder authBuilder, final String templateName) {
        this.quality = 1000;
        this.authBuilder = authBuilder;
        this.templateName = templateName;
    }
    
    public void setToken(final String token) {
        this.token = token;
    }
    
    public TXPlayerAuthBuilder getAuthBuilder() {
        return this.authBuilder;
    }
    
    public int getQuality() {
        return this.quality;
    }
    
    public String getTemplateName() {
        return this.templateName;
    }
    
    public String getToken() {
        return this.token;
    }
    
    protected static String qualityToId(final int n) {
        if (n == 1) {
            return "FLU";
        }
        if (n == 2) {
            return "SD";
        }
        if (n == 3) {
            return "HD";
        }
        if (n == 4) {
            return "FHD";
        }
        if (n == 5) {
            return "2K";
        }
        if (n == 6) {
            return "4K";
        }
        return "";
    }
}
