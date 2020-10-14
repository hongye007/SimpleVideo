package com.tencent.liteav.beauty.b.a;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.beauty.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;

public class d extends h
{
    private int r;
    private int s;
    private float t;
    private String u;
    
    d() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.r = -1;
        this.s = -1;
        this.t = 4.0f;
        this.u = "SmoothHorizontal";
    }
    
    @Override
    public boolean c() {
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(13);
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
    
    public void r() {
        this.r = GLES20.glGetUniformLocation(this.q(), "texelWidthOffset");
        this.s = GLES20.glGetUniformLocation(this.q(), "texelHeightOffset");
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
        if (n > n2) {
            if (n2 < 540) {
                this.t = 2.0f;
            }
            else {
                this.t = 4.0f;
            }
        }
        else if (n < 540) {
            this.t = 2.0f;
        }
        else {
            this.t = 4.0f;
        }
        TXCLog.i(this.u, "m_textureRation " + this.t);
        this.a(this.r, this.t / n);
        this.a(this.s, this.t / n2);
    }
}
