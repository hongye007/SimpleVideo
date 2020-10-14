package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;

public class aa extends h
{
    private int r;
    private float s;
    private int t;
    private int u;
    private static String v;
    
    public aa() {
        this(0.0f);
    }
    
    public aa(final float s) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform float imageWidthFactor; \nuniform float imageHeightFactor; \n\nvarying vec2 textureCoordinate;\nvarying vec2 leftTextureCoordinate;\nvarying vec2 rightTextureCoordinate; \nvarying vec2 topTextureCoordinate;\nvarying vec2 bottomTextureCoordinate;\n\n\nvoid main()\n{\n    gl_Position = position;\n    \n    mediump vec2 widthStep = vec2(imageWidthFactor, 0.0);\n    mediump vec2 heightStep = vec2(0.0, imageHeightFactor);\n    \n    textureCoordinate = inputTextureCoordinate.xy;\n    leftTextureCoordinate = inputTextureCoordinate.xy - widthStep;\n    rightTextureCoordinate = inputTextureCoordinate.xy + widthStep;\n    topTextureCoordinate = inputTextureCoordinate.xy + heightStep;     \n    bottomTextureCoordinate = inputTextureCoordinate.xy - heightStep;\n}\n", "precision mediump float;\n\nuniform float sharpness;\nvarying mediump vec2 textureCoordinate;\nvarying mediump vec2 leftTextureCoordinate;\nvarying mediump vec2 rightTextureCoordinate; \nvarying mediump vec2 topTextureCoordinate;\nvarying mediump vec2 bottomTextureCoordinate;\n\nuniform sampler2D inputImageTexture;\nfloat centerMultiplier;\nfloat edgeMultiplier;\n\nvoid main()\n{\n    mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    mediump vec3 leftTextureColor = texture2D(inputImageTexture, leftTextureCoordinate).rgb;\n    mediump vec3 rightTextureColor = texture2D(inputImageTexture, rightTextureCoordinate).rgb;\n    mediump vec3 topTextureColor = texture2D(inputImageTexture, topTextureCoordinate).rgb;\n    mediump vec3 bottomTextureColor = texture2D(inputImageTexture, bottomTextureCoordinate).rgb;\n\n    centerMultiplier = 1.0 + 4.0 * sharpness * (1.0 - textureColor.a);\n    edgeMultiplier = sharpness * (1.0 - textureColor.a);\n    gl_FragColor = vec4((textureColor.rgb * centerMultiplier - (leftTextureColor * edgeMultiplier + rightTextureColor * edgeMultiplier + topTextureColor * edgeMultiplier + bottomTextureColor * edgeMultiplier)), textureColor.a);    \n}\n");
        this.s = s;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.r = GLES20.glGetUniformLocation(this.q(), "sharpness");
        this.t = GLES20.glGetUniformLocation(this.q(), "imageWidthFactor");
        this.u = GLES20.glGetUniformLocation(this.q(), "imageHeightFactor");
        this.a(this.s);
        return a;
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
        this.a(this.t, 1.0f / n);
        this.a(this.u, 1.0f / n2);
    }
    
    public void a(final float s) {
        this.s = s;
        TXCLog.i(aa.v, "set Sharpness " + s);
        this.a(this.r, this.s);
    }
    
    static {
        aa.v = "GPUSharpen";
    }
}
