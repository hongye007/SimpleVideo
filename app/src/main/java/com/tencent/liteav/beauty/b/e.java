package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.graphics.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;

public class e extends h
{
    private float r;
    private int s;
    private float t;
    private int u;
    private PointF v;
    private int w;
    private float x;
    private int y;
    private static String z;
    
    public e() {
        this(0.5f, 0.3f, new PointF(0.5f, 0.5f));
    }
    
    public e(final float t, final float r, final PointF v) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\n\nuniform highp float aspectRatio;\nuniform highp vec2 center;\nuniform highp float radius;\nuniform highp float scale;\n\nvoid main()\n{\nhighp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\nhighp float dist = distance(center, textureCoordinateToUse);\ntextureCoordinateToUse = textureCoordinate;\n\nif (dist < radius)\n{\ntextureCoordinateToUse -= center;\nhighp float percent = 1.0 - ((radius - dist) / radius) * scale;\npercent = percent * percent;\n\ntextureCoordinateToUse = textureCoordinateToUse * percent;\ntextureCoordinateToUse += center;\n}\n\ngl_FragColor = texture2D(inputImageTexture, textureCoordinateToUse );    \n}\n");
        this.t = t;
        this.r = r;
        this.v = v;
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            TXCLog.e(e.z, "TXCGPUBulgeDistortionFilter init Failed!");
            return false;
        }
        this.s = GLES20.glGetUniformLocation(this.q(), "scale");
        this.u = GLES20.glGetUniformLocation(this.q(), "radius");
        this.w = GLES20.glGetUniformLocation(this.q(), "center");
        this.y = GLES20.glGetUniformLocation(this.q(), "aspectRatio");
        return true;
    }
    
    @Override
    public void d() {
        super.d();
        this.a(this.t);
        this.b(this.r);
        this.a(this.v);
    }
    
    @Override
    public void a(final int n, final int n2) {
        this.c(this.x = n2 / (float)n);
        super.a(n, n2);
    }
    
    private void c(final float x) {
        this.x = x;
        this.a(this.y, x);
    }
    
    public void a(final float t) {
        this.t = t;
        this.a(this.u, t);
    }
    
    public void b(final float r) {
        this.r = r;
        this.a(this.s, r);
    }
    
    public void a(final PointF v) {
        this.v = v;
        this.a(this.w, v);
    }
    
    static {
        e.z = "BulgeDistortion";
    }
}
