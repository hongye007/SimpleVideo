package com.tencent.liteav.k;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;

public class a extends h
{
    private int r;
    
    public a() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision mediump float;  \nvarying vec2 textureCoordinate;  \nuniform sampler2D inputImageTexture;  \nuniform vec2 textureSize;  \nconst mat3 xKernal = mat3(-1.0, 0.0, 1.0,  \n-2.0, 0.0, 2.0,  \n-1.0, 0.0, 1.0);  \nconst mat3 yKernal = mat3(1.0, 2.0, 1.0,  \n0.0, 0.0, 0.0,  \n-1.0, -2.0, -1.0);  \nconst vec3 LW = vec3(0.2125, 0.7154, 0.0721);  \nfloat convolution3x3(float invalue[9], mat3 kernal)  \n{  \nfloat v = 0.0;  \nv += invalue[0] * kernal[0][0];  \nv += invalue[1] * kernal[0][1];  \nv += invalue[2] * kernal[0][2];  \nv += invalue[3] * kernal[1][0];  \nv += invalue[4] * kernal[1][1];  \nv += invalue[5] * kernal[1][2];  \nv += invalue[6] * kernal[2][0];  \nv += invalue[7] * kernal[2][1];  \nv += invalue[8] * kernal[2][2];  \nreturn v;  \n}  \nvoid main()  \n{  \nfloat gray[9];  \nvec2 offsets[9];  \noffsets[0] = vec2(-1.0, 1.0);  \noffsets[1] = vec2(0.0, 1.0);  \noffsets[2] = vec2(1.0, 1.0);  \noffsets[3] = vec2(-1.0, 0.0);  \noffsets[4] = vec2(0.0, 0.0);  \noffsets[5] = vec2(0.0, 1.0);  \noffsets[6] = vec2(-1.0, -1.0);  \noffsets[7] = vec2(0.0, -1.0);  \noffsets[8] = vec2(1.0, -1.0);  \nvec2 _step = vec2(1.0 / textureSize.x, 1.0 / textureSize.y);  \nfor (int i = 0; i < 9; ++i)  \n{  \ngray[i] = dot(texture2D(inputImageTexture, textureCoordinate + _step * offsets[i]).xyz, LW);  \n}  \nvec2 G = vec2(convolution3x3(gray, xKernal), convolution3x3(gray, yKernal));  \ngl_FragColor = vec4(vec3(length(G)), 1.0);  \n}  \n");
        this.r = -1;
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
        this.a(this.r, new float[] { (float)this.e, (float)this.f });
    }
    
    public void a(final n.a a) {
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            return false;
        }
        this.r = GLES20.glGetUniformLocation(this.a, "textureSize");
        return true;
    }
}
