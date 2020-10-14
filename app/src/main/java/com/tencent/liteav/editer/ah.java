package com.tencent.liteav.editer;

import android.content.*;

import com.tencent.liteav.CameraProxy;

public class ah extends ag
{
    public ah(final Context context) {
        super(context, "record");
        if (this.c != null) {
            this.c.k();
        }
        this.c = new z();
    }
    
    @Override
    public void a() {
        super.a();
        if (CameraProxy.h.a().e()) {
            return;
        }
        this.c.a(CameraProxy.h.a().b());
    }
}
