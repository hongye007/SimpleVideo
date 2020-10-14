package com.tencent.liteav.basic.license;

import android.content.*;

public class i implements e
{
    private Context a;
    
    public i(final Context a) {
        this.a = a;
    }
    
    @Override
    public boolean a() {
        if (this.a != null) {
            LicenceCheck.a().a((f)null, this.a);
        }
        return LicenceCheck.a().b() >= 5;
    }
}
