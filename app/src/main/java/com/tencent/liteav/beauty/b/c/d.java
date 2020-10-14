package com.tencent.liteav.beauty.b.c;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;

class d extends h
{
    private final boolean r;
    private int s;
    private int t;
    private int u;
    private int v;
    
    public d(final boolean r) {
        super(" attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n uniform float texelWidthOffset;\n uniform float texelHeightOffset;\n \n varying vec2 textureCoordinate;\n varying vec4 textureShift_1;\n varying vec4 textureShift_2;\n varying vec4 textureShift_3;\n varying vec4 textureShift_4;\n \n void main(void)\n {\n     gl_Position = position;\n     textureCoordinate = inputTextureCoordinate.xy;\n     \n     vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n     textureShift_1 = vec4(textureCoordinate - singleStepOffset, textureCoordinate + singleStepOffset);\n     textureShift_2 = vec4(textureCoordinate - 2.0 * singleStepOffset, textureCoordinate + 2.0 * singleStepOffset);\n     textureShift_3 = vec4(textureCoordinate - 3.0 * singleStepOffset, textureCoordinate + 3.0 * singleStepOffset);\n     textureShift_4 = vec4(textureCoordinate - 4.0 * singleStepOffset, textureCoordinate + 4.0 * singleStepOffset);\n }\n", "uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n varying highp vec4 textureShift_1;\n varying highp vec4 textureShift_2;\n varying highp vec4 textureShift_3;\n varying highp vec4 textureShift_4;\n \n void main()\n {\n     mediump vec3 sum = texture2D(inputImageTexture, textureCoordinate).rgb;\n     sum += texture2D(inputImageTexture, textureShift_1.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_1.zw).rgb;\n     sum += texture2D(inputImageTexture, textureShift_2.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_2.zw).rgb;\n     sum += texture2D(inputImageTexture, textureShift_3.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_3.zw).rgb;\n     sum += texture2D(inputImageTexture, textureShift_4.xy).rgb;\n     sum += texture2D(inputImageTexture, textureShift_4.zw).rgb;\n     \n     gl_FragColor = vec4(sum * 0.1111, 1.0);\n }\n");
        this.s = -1;
        this.t = -1;
        this.r = r;
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            return false;
        }
        this.s = GLES20.glGetUniformLocation(this.q(), "texelWidthOffset");
        this.t = GLES20.glGetUniformLocation(this.q(), "texelHeightOffset");
        return true;
    }
    
    @Override
    public void a(final int n, final int n2) {
        final float min = Math.min(1.0f, 360.0f / Math.min(n, n2));
        this.u = Math.round(n * min);
        this.v = Math.round(n2 * min);
        super.a(this.u, this.v);
        if (this.r) {
            this.a(this.s, 0.0f);
            this.a(this.t, 1.5f / this.v);
        }
        else {
            this.a(this.s, 1.5f / this.u);
            this.a(this.t, 0.0f);
        }
    }
    
    @Override
    public int a(final int n) {
        GLES20.glViewport(0, 0, this.u, this.v);
        return super.a(n, this.m, this.n);
    }
}
