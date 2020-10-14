package com.tencent.liteav.beauty.b;

import android.opengl.*;

public class n extends ad
{
    private static String x;
    private int y;
    
    public n() {
        super(n.x);
        this.y = -1;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.y = GLES20.glGetUniformLocation(this.q(), "scale1");
        this.a(1.5f);
        return a;
    }
    
    public void a(final float n) {
        this.a(this.y, n);
    }
    
    static {
        n.x = "precision highp float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\n\nvarying mediump vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture2;\n\nvarying mediump vec2 textureCoordinate3;\nuniform sampler2D inputImageTexture3;\n\nuniform mediump float scale1;\n\nmediump vec4 textureScale(sampler2D texture, mediump vec2 coor, mediump float scale){\n\t vec2 rCoor = textureCoordinate - (1.0 - scale) * (vec2(0.5, 0.5) - textureCoordinate);\n     float x = rCoor.x;\n     float y = rCoor.y;\n\n    vec4 scaleColor = texture2D(texture, coor);\n     if (x < 0.0 || x > 1.0 || y < 0.0 || y > 1.0) { \n         scaleColor = vec4(1.0, 1.0, 1.0, 1.0); \n     } else { \n         scaleColor = texture2D(texture, vec2(x, y)); \n     } \n\n     return scaleColor;\n}\n\nvoid main()\n{\n    // First texture network(needs zoom in)\n    vec4 gridColor = texture2D(inputImageTexture, textureCoordinate);\n    if (1.0 != scale1){\n    \tgridColor = textureScale(inputImageTexture, textureCoordinate, scale1);\n    }\n\n    // Second texture, last texture\n    vec4 prevColor = texture2D(inputImageTexture2, textureCoordinate2);\n    // Third texture, current texture\n    vec4 currentColor = texture2D(inputImageTexture3, textureCoordinate3);\n\n    // if alpha equal 1.0\uff0cshow current zoomed in or zoomed out picture\n    if (0.0 == gridColor.a){\n        gl_FragColor = prevColor;\n    }else{\n        gl_FragColor = currentColor;\n    }\n}\n";
    }
}
