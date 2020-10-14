package com.tencent.liteav.d;

import android.graphics.*;

public class h
{
    private Bitmap a;
    
    public void a() {
        if (this.a != null && !this.a.isRecycled()) {
            this.a.recycle();
            this.a = null;
        }
    }
}
