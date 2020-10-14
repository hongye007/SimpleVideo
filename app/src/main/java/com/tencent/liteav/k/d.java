package com.tencent.liteav.k;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import java.nio.*;

public class d extends h
{
    private h r;
    private int s;
    private int t;
    private int u;
    private float v;
    
    public d() {
        super("attribute vec4 position;  \nattribute vec4 inputTextureCoordinate;\nuniform vec2 step;  \nvarying vec2 textureCoordinate;  \nvarying vec2 oneBackCoord;  \nvarying vec2 twoBackCoord;  \nvarying vec2 threeBackCoord;  \nvarying vec2 fourBackCoord;  \nvarying vec2 oneForwardCoord;  \nvarying vec2 twoForwardCoord;  \nvarying vec2 threeForwardCoord;  \nvarying vec2 fourForwardCoord;  \nvoid main() {  \n\tgl_Position = position;  \n\tvec2 coord = inputTextureCoordinate.xy;  \n\ttextureCoordinate = coord;  \n\toneBackCoord = coord.xy - step;  \n\ttwoBackCoord = coord.xy - 2.0 * step;  \n\tthreeBackCoord = coord.xy - 3.0 * step;  \n\tfourBackCoord = coord.xy - 4.0 * step;  \n\toneForwardCoord = coord.xy + step;  \n\ttwoForwardCoord = coord.xy + 2.0 * step;  \n\tthreeForwardCoord = coord.xy + 3.0 * step;  \n\tfourForwardCoord = coord.xy + 4.0 * step;  \n}  \n", "precision mediump float;  \nuniform sampler2D inputImageTexture;  \nvarying vec2 textureCoordinate;  \nvarying vec2 oneBackCoord;  \nvarying vec2 twoBackCoord;  \nvarying vec2 threeBackCoord;  \nvarying vec2 fourBackCoord;  \nvarying vec2 oneForwardCoord;  \nvarying vec2 twoForwardCoord;  \nvarying vec2 threeForwardCoord;  \nvarying vec2 fourForwardCoord;  \nvoid main() {   \n\tlowp vec4 fragmentColor = texture2D(inputImageTexture, textureCoordinate) * 0.18;  \n\tfragmentColor += texture2D(inputImageTexture, oneBackCoord) * 0.15;  \n\tfragmentColor += texture2D(inputImageTexture, twoBackCoord) * 0.12;  \n\tfragmentColor += texture2D(inputImageTexture, threeBackCoord) * 0.09;  \n\tfragmentColor += texture2D(inputImageTexture, fourBackCoord) * 0.05;  \n\tfragmentColor += texture2D(inputImageTexture, oneForwardCoord) * 0.15;  \n\tfragmentColor += texture2D(inputImageTexture, twoForwardCoord) * 0.12;  \n\tfragmentColor += texture2D(inputImageTexture, threeForwardCoord) * 0.09;  \n\tfragmentColor += texture2D(inputImageTexture, fourForwardCoord) * 0.05;  \n\tgl_FragColor = fragmentColor;  \n}  \n");
        this.s = -1;
        this.t = -1;
        this.u = -1;
        this.v = 0.0f;
        this.o = true;
        this.r = new h("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision lowp float;  \nvarying vec2 textureCoordinate;  \n\tuniform sampler2D inputImageTexture;  \n\tuniform float shift;  \n\tuniform float alpha;  \n\tvoid main() { vec4 colorShift = texture2D(inputImageTexture, textureCoordinate + vec2(shift, 0.0));  \n\tvec4 color = texture2D(inputImageTexture, textureCoordinate + vec2(shift * 0.1, 0.0));  \n\tgl_FragColor = vec4(mix(colorShift.rgb, color.rgb, alpha), color.a);  \n}  \n");
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            return false;
        }
        this.u = GLES20.glGetUniformLocation(this.a, "step");
        if (!this.r.c()) {
            this.e();
            return false;
        }
        this.s = GLES20.glGetUniformLocation(this.r.q(), "shift");
        this.t = GLES20.glGetUniformLocation(this.r.q(), "alpha");
        return true;
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
        if (this.r != null) {
            this.r.a(n, n2);
        }
        if (n != 0 && this.v != 0.0) {
            this.a(this.u, new float[] { this.v / this.e, 0.0f });
        }
    }
    
    @Override
    public void e() {
        super.e();
        this.r.e();
    }
    
    @Override
    public void a(final boolean b) {
        this.r.a(b);
    }
    
    @Override
    public int a(final int n, final int n2, final int n3) {
        if (!this.g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, this.m);
        this.a(n, this.h, this.i);
        if (this.l instanceof a) {
            this.l.a(n3);
        }
        if (n2 != this.m) {
            return this.r.a(this.n, n2, n3);
        }
        return this.r.a(this.n);
    }
    
    @Override
    public int b(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        if (!this.g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, this.m);
        this.a(n, floatBuffer, floatBuffer2);
        if (this.l instanceof a) {
            this.l.a(n);
        }
        this.r.b(this.n);
        return 1;
    }
    
    public void a(final n.e e) {
        this.a(e.b, e.a, e.c);
    }
    
    private void a(final float v, final float n, final float n2) {
        this.v = v;
        if (this.e != 0) {
            this.a(this.u, new float[] { v / this.e, 0.0f });
        }
        this.r.a(this.t, n2);
        this.r.a(this.s, n);
    }
}
