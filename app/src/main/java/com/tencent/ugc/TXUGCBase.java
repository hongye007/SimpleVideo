package com.tencent.ugc;

import android.content.*;
import com.tencent.liteav.basic.license.*;

public class TXUGCBase
{
    private static TXUGCBase sInstance;
    private LicenceCheck mUGCLicenseNewCheck;
    
    public static TXUGCBase getInstance() {
        if (TXUGCBase.sInstance == null) {
            synchronized (TXUGCBase.class) {
                if (TXUGCBase.sInstance == null) {
                    TXUGCBase.sInstance = new TXUGCBase();
                }
            }
        }
        return TXUGCBase.sInstance;
    }
    
    private TXUGCBase() {
    }
    
    public void setLicence(final Context context, final String s, final String s2) {
        (this.mUGCLicenseNewCheck = LicenceCheck.a()).a(context, s, s2);
    }
    
    public String getLicenceInfo(final Context context) {
        final f f = new f();
        LicenceCheck.a().a(f, context);
        return f.a;
    }
}
