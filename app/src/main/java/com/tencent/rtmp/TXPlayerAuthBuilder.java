package com.tencent.rtmp;

public class TXPlayerAuthBuilder
{
    int appId;
    String fileId;
    String timeout;
    int exper;
    String sign;
    String us;
    protected boolean isHttps;
    
    public TXPlayerAuthBuilder() {
        this.exper = -1;
    }
    
    public int getAppId() {
        return this.appId;
    }
    
    public String getFileId() {
        return this.fileId;
    }
    
    public String getTimeout() {
        return this.timeout;
    }
    
    public int getExper() {
        return this.exper;
    }
    
    public String getSign() {
        return this.sign;
    }
    
    public String getUs() {
        return this.us;
    }
    
    public boolean isHttps() {
        return this.isHttps;
    }
    
    public void setAppId(final int appId) {
        this.appId = appId;
    }
    
    public void setFileId(final String fileId) {
        this.fileId = fileId;
    }
    
    public void setTimeout(final String timeout) {
        this.timeout = timeout;
    }
    
    public void setUs(final String us) {
        this.us = us;
    }
    
    public void setExper(final int exper) {
        this.exper = exper;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
    
    public void setHttps(final boolean isHttps) {
        this.isHttps = isHttps;
    }
}
