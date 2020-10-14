package com.tencent.rtmp;

import android.content.*;
import com.tencent.liteav.basic.datareport.*;
import java.util.*;
import com.tencent.rtmp.a.*;
import android.graphics.*;

public class TXImageSprite implements a
{
    private a mImageSprite;
    private Context mContext;
    
    public TXImageSprite(final Context context) {
        this.mContext = context.getApplicationContext();
        TXCDRApi.initCrashReport(context);
    }
    
    @Override
    public void setVTTUrlAndImageUrls(final String s, final List<String> list) {
        if (this.mImageSprite != null) {
            this.release();
        }
        if (s != null && list != null && list.size() != 0) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bC);
            (this.mImageSprite = new b()).setVTTUrlAndImageUrls(s, list);
        }
    }
    
    @Override
    public Bitmap getThumbnail(final float n) {
        return (this.mImageSprite != null) ? this.mImageSprite.getThumbnail(n) : null;
    }
    
    @Override
    public void release() {
        if (this.mImageSprite != null) {
            this.mImageSprite.release();
            this.mImageSprite = null;
        }
    }
}
