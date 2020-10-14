package com.tencent.liteav.basic.module;

public class BaseObj
{
    private String mID;
    
    public BaseObj() {
        this.mID = "";
    }
    
    public void finalize() throws Throwable {
        this.clearID();
        super.finalize();
    }
    
    public void setID(final String mid) {
        this.clearID();
        synchronized (this) {
            if (mid.length() != 0) {
                TXCStatus.a(this.mID = mid);
            }
        }
    }
    
    public void clearID() {
        synchronized (this) {
            if (this.mID.length() != 0) {
                TXCStatus.b(this.mID);
                this.mID = "";
            }
        }
    }
    
    public String getID() {
        return this.mID;
    }
    
    public boolean setStatusValue(final int n, final Object o) {
        return TXCStatus.a(this.mID, n, o);
    }
    
    public boolean setStatusValue(final int n, final int n2, final Object o) {
        return TXCStatus.a(this.mID, n, n2, o);
    }
    
    public long getLongValue(final int n) {
        return TXCStatus.a(this.mID, n);
    }
    
    public String getStringValue(final int n) {
        return TXCStatus.b(this.mID, n);
    }
    
    public int getIntValue(final int n) {
        return TXCStatus.c(this.mID, n);
    }
    
    public double getDoubleValue(final int n) {
        return TXCStatus.d(this.mID, n);
    }
    
    public String getStringValue(final int n, final int n2) {
        return TXCStatus.b(this.mID, n, n2);
    }
    
    public int getIntValue(final int n, final int n2) {
        return TXCStatus.c(this.mID, n, n2);
    }
    
    public long getLongValue(final int n, final int n2) {
        return TXCStatus.a(this.mID, n, n2);
    }
    
    public double getDoubleValue(final int n, final int n2) {
        return TXCStatus.d(this.mID, n, n2);
    }
}
