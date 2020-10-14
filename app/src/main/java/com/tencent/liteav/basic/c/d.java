package com.tencent.liteav.basic.c;

import java.io.*;

public class d extends IOException
{
    private static final long serialVersionUID = 2723743254380545567L;
    private final int mErrorCode;
    private final String mErrorMessage;
    
    public d(final int n) {
        this(n, null);
    }
    
    public d(final int mErrorCode, final String mErrorMessage) {
        this.mErrorCode = mErrorCode;
        this.mErrorMessage = mErrorMessage;
    }
    
    @Override
    public String getMessage() {
        if (this.mErrorMessage != null) {
            return "EGL error code: " + this.mErrorCode + this.mErrorMessage;
        }
        return "EGL error code: " + this.mErrorCode;
    }
}
