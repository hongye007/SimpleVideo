package com.tencent.liteav.beauty.b.a;

import com.tencent.liteav.beauty.b.*;
import android.os.*;
import android.util.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;

public class e extends ae
{
    private int r;
    private int s;
    private int t;
    private int x;
    private float y;
    private float z;
    private String A;
    
    e() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.r = -1;
        this.s = -1;
        this.t = -1;
        this.x = -1;
        this.y = 2.0f;
        this.z = 0.5f;
        this.A = "SmoothVertical";
    }
    
    @Override
    public boolean c() {
        if (Build.BRAND.equals("samsung") && Build.MODEL.equals("GT-I9500") && Build.VERSION.RELEASE.equals("4.3")) {
            Log.d(this.A, "SAMSUNG_S4 GT-I9500 + Android 4.3; use diffrent shader!");
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(15);
        }
        else {
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(5);
        }
        if (this.a != 0 && this.a()) {
            this.g = true;
        }
        else {
            this.g = false;
        }
        this.d();
        return this.g;
    }
    
    @Override
    public boolean a() {
        super.a();
        this.r();
        return true;
    }
    
    public void a(final float z) {
        this.z = z;
        TXCLog.i(this.A, "setBeautyLevel " + z);
        this.a(this.t, z);
    }
    
    public void r() {
        this.r = GLES20.glGetUniformLocation(this.q(), "texelWidthOffset");
        this.s = GLES20.glGetUniformLocation(this.q(), "texelHeightOffset");
        this.t = GLES20.glGetUniformLocation(this.q(), "smoothDegree");
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
        if (n > n2) {
            if (n2 < 540) {
                this.y = 2.0f;
            }
            else {
                this.y = 4.0f;
            }
        }
        else if (n < 540) {
            this.y = 2.0f;
        }
        else {
            this.y = 4.0f;
        }
        TXCLog.i(this.A, "m_textureRation " + this.y);
        this.a(this.r, this.y / n);
        this.a(this.s, this.y / n2);
    }
}
