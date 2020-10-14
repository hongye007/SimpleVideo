package com.tencent.liteav.basic.license;

import android.content.*;

public class g implements e
{
    private Context a;
    
    public g(final Context a) {
        this.a = a;
    }
    
    @Override
    public boolean a() {
        if (this.a != null) {
            LicenceCheck.a().b(null, this.a);
        }
        return LicenceCheck.a().c() >= 2;
    }
}
