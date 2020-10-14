package com.tencent.liteav.beauty.b.a;

import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.basic.log.*;
import android.util.*;
import android.opengl.*;

public class b extends ae
{
    private int r;
    private int s;
    private int t;
    private final String x = "BeautyBlend";
    
    public b() {
        super("varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.r = -1;
        this.s = -1;
        this.t = -1;
    }
    
    @Override
    public boolean c() {
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(12);
        if (this.a != 0 && this.a()) {
            this.g = true;
        }
        else {
            this.g = false;
        }
        this.d();
        return this.g;
    }
    
    @Override
    public boolean a() {
        super.a();
        this.r();
        return true;
    }
    
    public void a(final float n) {
        TXCLog.i("BeautyBlend", "setBrightLevel " + n);
        this.a(this.s, n);
    }
    
    public void b(final float n) {
        Log.i("BeautyBlend", "setRuddyLevel level " + n);
        this.a(this.t, n / 2.0f);
    }
    
    private void r() {
        this.s = GLES20.glGetUniformLocation(this.q(), "whiteDegree");
        this.r = GLES20.glGetUniformLocation(this.q(), "contrast");
        this.t = GLES20.glGetUniformLocation(this.q(), "ruddyDegree");
    }
}
