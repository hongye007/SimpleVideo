package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;

public class r extends h
{
    private static String r;
    private int s;
    private boolean t;
    
    public r() {
        this(com.tencent.liteav.beauty.b.r.r, "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", false);
    }
    
    public r(final String s, final String s2, final boolean b) {
        super(s, s2, b);
        this.s = -1;
        this.t = false;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.b(this.s = GLES20.glGetUniformLocation(this.a, "bTransform"), 0);
        return a;
    }
    
    @Override
    public void a(final float[] array) {
        if (null != array) {
            this.b(this.s, 1);
        }
        else {
            this.b(this.s, 0);
        }
        super.a(array);
    }
    
    public void c(final boolean t) {
        if (t != this.t) {
            this.t = t;
            this.h();
        }
    }
    
    static {
        com.tencent.liteav.beauty.b.r.r = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform int  bTransform;\nuniform mat4 textureTransform;\n\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n     gl_Position = position;\n    if (0 != bTransform){\n        textureCoordinate =  (textureTransform * inputTextureCoordinate).xy;\n    }else{\n        textureCoordinate = inputTextureCoordinate.xy;\n    }\n}\n";
    }
}
