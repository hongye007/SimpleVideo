package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;

public class w extends h
{
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;
    private String A;
    private int B;
    private static float[] C;
    private static float[] D;
    private static float[] E;
    
    public w(final int b) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.r = -1;
        this.s = -1;
        this.t = -1;
        this.u = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.y = -1;
        this.z = -1;
        this.A = "RGBA2I420Filter";
        this.B = 1;
        this.B = b;
    }
    
    @Override
    public boolean c() {
        if (1 == this.B) {
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(8);
            TXCLog.i(this.A, "RGB-->I420 init!");
        }
        else if (3 == this.B) {
            TXCLog.i(this.A, "RGB-->NV21 init!");
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(11);
        }
        else {
            if (2 == this.B) {
                TXCLog.i(this.A, "RGBA Format init!");
                return super.c();
            }
            TXCLog.i(this.A, "don't support format " + this.B + " use default I420");
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(8);
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
        this.r = GLES20.glGetUniformLocation(this.a, "width");
        this.s = GLES20.glGetUniformLocation(this.a, "height");
        return true;
    }
    
    @Override
    public void a(final int n, final int n2) {
        if (n <= 0 || n2 <= 0) {
            TXCLog.e(this.A, "width or height is error!");
            return;
        }
        if (this.f == n2 && this.e == n) {
            return;
        }
        super.a(n, n2);
        TXCLog.i(this.A, "RGBA2I420Filter width " + n + " height " + n2);
        this.a(this.r, (float)n);
        this.a(this.s, (float)n2);
    }
    
    @Override
    public void d() {
        super.d();
    }
    
    static {
        w.C = new float[] { 0.1826f, 0.6142f, 0.062f, -0.1006f, -0.3386f, 0.4392f, 0.4392f, -0.3989f, -0.0403f };
        w.D = new float[] { 0.256816f, 0.504154f, 0.0979137f, -0.148246f, -0.29102f, 0.439266f, 0.439271f, -0.367833f, -0.071438f };
        w.E = new float[] { 0.0625f, 0.5f, 0.5f };
    }
}
