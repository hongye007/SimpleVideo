package com.tencent.liteav.beauty.b.c;

import com.tencent.liteav.beauty.b.*;
import android.opengl.*;

public class c extends ad
{
    private final String x = "TXCYTBeautySmoothFilter";
    private int y;
    private int z;
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    
    public c() {
        super("attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n attribute vec4 inputTextureCoordinate2;\n attribute vec4 inputTextureCoordinate3;\n uniform float texelWidthOffset;\n uniform float texelHeightOffset;\n \n varying vec2 textureCoordinate;\n varying vec2 textureCoordinate2;\n varying vec2 textureCoordinate3;\n varying vec4 textureShift_1;\n varying vec4 textureShift_2;\n varying vec4 textureShift_3;\n varying vec4 textureShift_4;\n \n void main(void)\n {\n     gl_Position = position;\n     textureCoordinate = inputTextureCoordinate.xy;\n     textureCoordinate2 = inputTextureCoordinate2.xy;\n     textureCoordinate3 = inputTextureCoordinate3.xy;\n     textureShift_1 = vec4(textureCoordinate + vec2(-texelWidthOffset,0.0),textureCoordinate + vec2(texelWidthOffset,0.0));\n     textureShift_2 = vec4(textureCoordinate + vec2(0.0,-texelHeightOffset),textureCoordinate + vec2(0.0,texelHeightOffset));\n     textureShift_3 = vec4(textureCoordinate + vec2(texelWidthOffset,texelHeightOffset),textureCoordinate + vec2(-texelWidthOffset,-texelHeightOffset));\n     textureShift_4 = vec4(textureCoordinate + vec2(-texelWidthOffset,texelHeightOffset),textureCoordinate + vec2(texelWidthOffset,-texelHeightOffset));\n }\n", "precision mediump float;\n varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n varying highp vec2 textureCoordinate3;\n varying highp vec4 textureShift_1;\n varying highp vec4 textureShift_2;\n varying highp vec4 textureShift_3;\n varying highp vec4 textureShift_4;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform sampler2D inputImageTexture3;\n uniform highp float blurStrength;\n uniform highp float sharpenStrength;\n uniform highp float whitenStrength;\n uniform highp float ruddyStrength;\n\n \n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n  const highp mat3 saturateMatrix = mat3(\n                                        1.1102, -0.0598, -0.061,\n                                        -0.0774, 1.0826, -0.1186,\n                                        -0.0228, -0.0228, 1.1772);\n highp vec3 rgb2hsv(highp vec3 c)\n {\n     highp vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n     highp vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n     highp vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n     \n     highp float d = q.x - min(q.w, q.y);\n     highp float e = 1.0e-10;\n     return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n }\n void main()\n {\n     lowp vec4 iColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp vec4 meanColor = texture2D(inputImageTexture2, textureCoordinate2);\n     lowp vec4 varColor = texture2D(inputImageTexture3, textureCoordinate3);\n     \n     lowp float iSkinR = iColor.r;\n     lowp float mSkinR = meanColor.r;\n     \n     // smooth\n     mediump float p = clamp((min(iSkinR, mSkinR - 0.1) - 0.2) * 4.0, 0.0, 1.0);\n     mediump float meanVar = (varColor.r + varColor.g + varColor.b) / 3.0;\n     mediump float diffFactor = (1.0 - meanVar / (meanVar + 0.1));\n     mediump float kMin = diffFactor * p;\n     lowp vec3 smoothColor = mix(iColor.rgb, meanColor.rgb, kMin * blurStrength);\n     \n     // sharpen\n     mediump vec3 sum = 0.25* iColor.rgb;// 0.25*iColor.rgb;\n     sum += 0.125 *texture2D(inputImageTexture,textureShift_1.xy).rgb;\n     sum += 0.125 *texture2D(inputImageTexture,textureShift_1.zw).rgb;\n     sum += 0.125 *texture2D(inputImageTexture,textureShift_2.xy).rgb;\n     sum += 0.125 *texture2D(inputImageTexture,textureShift_2.zw).rgb;\n     sum += 0.0625*texture2D(inputImageTexture,textureShift_3.xy).rgb;\n     sum += 0.0625*texture2D(inputImageTexture,textureShift_3.zw).rgb;\n     sum += 0.0625*texture2D(inputImageTexture,textureShift_4.xy).rgb;\n     sum += 0.0625*texture2D(inputImageTexture,textureShift_4.zw).rgb;\n     vec3 hPass = iColor.rgb - sum;\n     lowp vec3 sharpenResult = clamp(smoothColor + hPass.g + hPass.rgb * max(0.0, meanVar - 0.05) / (meanVar + 0.1), vec3(0.0), vec3(1.0));\n     lowp vec3 sharpenColor = clamp(mix(smoothColor.rgb, sharpenResult.rgb, sharpenStrength), vec3(0.0), vec3(1.0));\n     \n     //\u7f8e\u767d\n     //\u63d0\u5347\u5bf9\u6bd4\u5ea6\uff0c\u9ed1\u767d\u5206\u660e\n     lowp vec4 white = vec4(((sharpenColor.rgb - vec3(0.5)) * (1.0 + whitenStrength * 0.125) + vec3(0.5)), iColor.a);\n     //\u8d8a\u4eae\u8d8a\u767d\uff0c\u8d8a\u6697\u8d8a\u4e0d\u767d\n     lowp vec3 hsv = rgb2hsv(white.rgb);\n     highp float wDegree = 4.0 * hsv.z * whitenStrength + 0.00001;\n     //log\u66f2\u7ebf\u7f8e\u767d\n     white.r = log(1.0 + wDegree * white.r)/log(wDegree + 1.0);\n     white.gb = log(1.0 + wDegree * white.gb)/log(wDegree + 1.0);\n     white = mix(vec4(sharpenColor, iColor.a), white, min(wDegree, 0.8));\n     //\u9971\u548c\u5ea6\u7ea2\u6da6\n     lowp vec3 ruddy = white.rgb * saturateMatrix;\n     ruddy = mix(white.rgb, ruddy, ruddyStrength * 0.7);\n     gl_FragColor = vec4(ruddy, iColor.a);     // whiten red\n\n }");
    }
    
    @Override
    public boolean a() {
        super.a();
        this.y = GLES20.glGetUniformLocation(this.q(), "texelWidthOffset");
        this.z = GLES20.glGetUniformLocation(this.q(), "texelHeightOffset");
        this.A = GLES20.glGetUniformLocation(this.q(), "blurStrength");
        this.B = GLES20.glGetUniformLocation(this.q(), "sharpenStrength");
        this.C = GLES20.glGetUniformLocation(this.q(), "ruddyStrength");
        this.D = GLES20.glGetUniformLocation(this.q(), "whitenStrength");
        return this.a != 0;
    }
    
    @Override
    public void a(final int e, final int f) {
        super.a(e, f);
        this.E = e;
        this.F = f;
        this.a((float)e, (float)f);
    }
    
    public void a(final float n, final float n2) {
        this.a(this.y, 1.0f / n);
        this.a(this.z, 1.0f / n2);
    }
    
    public void a(final float n) {
        this.a(this.A, n);
        this.a(this.B, 0.3f * n * n);
    }
    
    public void b(final float n) {
        this.a(this.D, n);
    }
    
    public void c(final float n) {
        this.a(this.C, n);
    }
    
    public void d(final float n) {
        this.a(this.B, n);
    }
    
    @Override
    public int a(final int n, final int n2, final int n3) {
        GLES20.glViewport(0, 0, this.E, this.F);
        return super.a(n, n2, n3);
    }
}
