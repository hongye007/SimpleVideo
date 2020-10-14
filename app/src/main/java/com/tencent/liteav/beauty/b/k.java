package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;

public class k extends h
{
    private int r;
    private float s;
    
    public k() {
        this(1.2f);
    }
    
    public k(final float s) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float gamma;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.w);\n }");
        this.s = s;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.r = GLES20.glGetUniformLocation(this.q(), "gamma");
        return a;
    }
    
    @Override
    public void d() {
        super.d();
        this.a(this.s);
    }
    
    public void a(final float s) {
        this.s = s;
        this.a(this.r, this.s);
    }
}
