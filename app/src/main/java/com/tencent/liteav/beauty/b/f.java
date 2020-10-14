package com.tencent.liteav.beauty.b;

import android.opengl.*;

public class f extends ae
{
    private static String r;
    private int s;
    private int t;
    
    public f() {
        super(f.r);
        this.s = -1;
        this.t = -1;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.s = GLES20.glGetUniformLocation(this.q(), "brushColor");
        this.t = GLES20.glGetUniformLocation(this.q(), "fillColor");
        this.b(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
        this.c(new float[] { 0.0f, 0.0f, 0.0f, 0.0f });
        return a;
    }
    
    public void b(final float[] array) {
        this.c(this.s, array);
    }
    
    public void c(final float[] array) {
        this.c(this.t, array);
    }
    
    static {
        f.r = "precision highp float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\n\nvarying mediump vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture2;\n\nuniform mediump vec4 brushColor;\nuniform mediump vec4 fillColor;\n\nvoid main()\n{\n    // First texture network\n    vec4 texture1Color = texture2D(inputImageTexture, textureCoordinate);\n    // Second texture, last texture\n    vec4 texture2Color = texture2D(inputImageTexture2, textureCoordinate2);\n\n    if (brushColor.a == texture1Color.a || brushColor.a == texture2Color.a){\n        gl_FragColor = brushColor;\n    }else{\n        gl_FragColor = fillColor;\n    }\n}\n";
    }
}
