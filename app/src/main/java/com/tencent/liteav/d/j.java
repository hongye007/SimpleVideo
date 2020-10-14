package com.tencent.liteav.d;

import android.graphics.*;
import com.tencent.liteav.i.*;

public class j
{
    private Bitmap a;
    private a.h b;
    
    public j(final Bitmap a, final a.h b) {
        this.a = a;
        this.b = b;
    }
    
    public Bitmap c() {
        return this.a;
    }
    
    public a.h d() {
        return this.b;
    }
    
    public void b() {
        if (this.a != null && !this.a.isRecycled()) {
            this.a.recycle();
            this.a = null;
        }
        this.b = null;
    }
}
