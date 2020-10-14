package com.tencent.liteav.beauty.b.c;

import com.tencent.liteav.beauty.b.*;
import android.opengl.*;

public class b extends ae
{
    private int r;
    private int s;
    
    public b() {
        super(" attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n varying vec2 textureCoordinate;\n \n void main(void)\n {\n     gl_Position = position;\n     textureCoordinate = inputTextureCoordinate.xy;\n }\n", " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n void main()\n {\n     lowp vec3 iColor = texture2D(inputImageTexture, textureCoordinate).rgb;\n     lowp vec3 meanColor = texture2D(inputImageTexture2, textureCoordinate).rgb;\n     highp vec3 diffColor = (iColor - meanColor) * 7.07;\n     diffColor = min(diffColor * diffColor, 1.0);\n     gl_FragColor = vec4(diffColor, 1.0);\n }\n");
    }
    
    @Override
    public void a(final int n, final int n2) {
        final float min = Math.min(1.0f, 360.0f / Math.min(n, n2));
        this.r = Math.round(n * min);
        this.s = Math.round(n2 * min);
        super.a(this.r, this.s);
    }
    
    @Override
    public int a(final int n) {
        GLES20.glViewport(0, 0, this.r, this.s);
        return super.a(n, this.m, this.n);
    }
}
