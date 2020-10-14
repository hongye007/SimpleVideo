package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.log.*;
import android.opengl.*;

public class h extends ae
{
    private static String r;
    private static String s;
    private int t;
    
    public h() {
        super(h.r);
        this.t = -1;
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            TXCLog.e(h.s, "onInit failed");
            return false;
        }
        this.t = GLES20.glGetUniformLocation(this.a, "mixturePercent");
        this.a(0.5f);
        return true;
    }
    
    public void a(final float n) {
        this.a(this.t, n);
    }
    
    static {
        h.r = "precision mediump float; \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float mixturePercent;\n\nvoid main()\n{\n   vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n   vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n   \n   gl_FragColor = mix(textureColor, textureColor2, mixturePercent);\n}\n";
        h.s = "DissolveBlend";
    }
}
