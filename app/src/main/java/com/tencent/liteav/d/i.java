package com.tencent.liteav.d;

import android.graphics.*;
import com.tencent.liteav.i.*;

public class i extends j
{
    private final int a = 3000;
    private int b;
    
    public i(final Bitmap bitmap, final a.h h, final int b) {
        super(bitmap, h);
        this.b = b;
    }
    
    public int a() {
        return this.b;
    }
    
    @Override
    public void b() {
        super.b();
        this.b = 0;
    }
}
