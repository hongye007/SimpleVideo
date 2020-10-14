package com.tencent.liteav.k;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;

public class m extends h
{
    private static String r;
    private static String s;
    private int t;
    
    public m() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", m.r);
        this.t = -1;
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            TXCLog.e(m.s, "onInit failed");
            return false;
        }
        this.t = GLES20.glGetUniformLocation(this.a, "scale");
        this.a(1.0f);
        return true;
    }
    
    public void a(final float n) {
        this.a(this.t, n);
    }
    
    static {
        m.r = " precision mediump float;\n varying mediump vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform float scale;\n \n void main(void) {\n       float x = 0.5 + (textureCoordinate.x - 0.5) / scale; \n       float y = 0.5 + (textureCoordinate.y - 0.5) / scale; \n       if (x < 0.0 || x > 1.0 || y < 0.0 || y > 1.0) { \n           gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0); \n       } else { \n           gl_FragColor = texture2D(inputImageTexture, vec2(x, y)); \n       } \n }\n";
        m.s = "GuidRefilne";
    }
}
