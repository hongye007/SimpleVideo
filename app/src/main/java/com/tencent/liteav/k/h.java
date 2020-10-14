package com.tencent.liteav.k;

import android.opengl.*;

public class h extends com.tencent.liteav.basic.c.h
{
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    
    public h() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision mediump float; \nvarying highp vec2 textureCoordinate; \nuniform sampler2D inputImageTexture; \n \nuniform float a; \nuniform float b; \nuniform float c; \nuniform float d; \nuniform float mode; \nuniform float width; \nuniform float stride; \nuniform float alpha; \n \nvoid main() \n{ \n\tgl_FragColor = texture2D(inputImageTexture, textureCoordinate); \n    if(b == 0.0){ \n\t\t//float mx = textureCoordinate.x > (stride - c) ? mod(textureCoordinate.x + c, stride) - c : textureCoordinate.x; \n\t\t//if((mode < 0.5 && mx > -1.0*c && mx <= width - c) || (mode > 0.5 && (mx > width - c || mx < -1.0 *c))){ \n\t\tfloat mx = mod(textureCoordinate.x + c, stride); \n\t\tif((mode < 0.5 && mx <= width) || (mode > 0.5 && (mx > width))){ \n\t\t\tgl_FragColor.rgb = gl_FragColor.rgb*alpha; \n\t\t} \n\t} \n} \n");
        this.r = -1;
        this.s = -1;
        this.t = -1;
        this.u = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.y = -1;
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            return false;
        }
        this.s = GLES20.glGetUniformLocation(this.a, "b");
        this.t = GLES20.glGetUniformLocation(this.a, "c");
        this.v = GLES20.glGetUniformLocation(this.a, "mode");
        this.w = GLES20.glGetUniformLocation(this.a, "width");
        this.x = GLES20.glGetUniformLocation(this.a, "stride");
        this.y = GLES20.glGetUniformLocation(this.a, "alpha");
        return true;
    }
    
    public void a(final n.i i) {
        this.a(i.a, i.b, i.c, i.d, i.e);
    }
    
    private void a(final float n, final float n2, final float n3, final float n4, final float n5) {
        this.a(this.v, n);
        this.a(this.y, n2);
        this.a(this.t, -1.0f * n3);
        this.a(this.w, n4);
        this.a(this.x, n5);
    }
}
