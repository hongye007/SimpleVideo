package com.tencent.avroom;

import com.tencent.liteav.*;
import com.tencent.rtmp.ui.*;
import android.content.*;
import android.view.*;

public class c extends TXCRenderAndDec
{
    TXCloudVideoView a;
    private AAAA b;
    
    public c(final Context context) {
        super(context);
        this.b = null;
    }
    
    public void a(final TXCloudVideoView a) {
        if (a != null) {
            this.a = a;
        }
        if (this.a == null) {
            return;
        }
        this.a.setVisibility(0);
        final TextureView textureView = new TextureView(this.a.getContext());
        this.a.addVideoView(textureView);
        this.getVideoRender().a(textureView);
    }
    
    public AAAA a() {
        return this.b;
    }
    
    public void a(final AAAA b) {
        this.b = b;
    }
}
