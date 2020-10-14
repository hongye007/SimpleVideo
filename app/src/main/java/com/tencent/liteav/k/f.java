package com.tencent.liteav.k;

import com.tencent.liteav.basic.c.*;
import android.util.*;
import android.opengl.*;

public class f extends h
{
    private static String r;
    private static String s;
    private static String t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private final float[] G;
    private float[] H;
    
    public f() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", f.s);
        this.u = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.y = -1;
        this.z = -1;
        this.A = -1;
        this.B = -1;
        this.C = -1;
        this.D = -1;
        this.E = -1;
        this.F = -1;
        this.G = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };
        this.H = this.G.clone();
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            Log.e(f.t, "super.onInit failed");
            return false;
        }
        this.u = GLES20.glGetUniformLocation(this.q(), "xOffset");
        this.v = GLES20.glGetUniformLocation(this.q(), "xWidth");
        this.w = GLES20.glGetUniformLocation(this.q(), "xStride");
        this.x = GLES20.glGetUniformLocation(this.q(), "yOffset");
        this.y = GLES20.glGetUniformLocation(this.q(), "yWidth");
        this.z = GLES20.glGetUniformLocation(this.q(), "yStride");
        this.A = GLES20.glGetUniformLocation(this.q(), "textureTransform");
        this.B = GLES20.glGetUniformLocation(this.q(), "radius");
        this.C = GLES20.glGetUniformLocation(this.q(), "center");
        this.D = GLES20.glGetUniformLocation(this.q(), "aspectRatio");
        this.E = GLES20.glGetUniformLocation(this.q(), "zoomModel");
        this.F = GLES20.glGetUniformLocation(this.q(), "maxRadius");
        this.a(this.C, new float[] { 0.5f, 0.5f });
        return true;
    }
    
    public void a(final n.c c) {
        this.a(this.u, c.a);
        this.a(this.v, c.b);
        this.a(this.w, c.c);
        this.a(this.x, c.a);
        this.a(this.y, c.b);
        this.a(this.z, c.c);
        this.a(this.B, c.f);
        this.b(this.E, c.g.value);
        this.a(this.F, c.e);
    }
    
    @Override
    public void a(final int n, final int n2) {
        if (this.f == n2 && this.e == n) {
            return;
        }
        super.a(n, n2);
        this.a(this.D, n2 * 1.0f / n);
    }
    
    static {
        f.r = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nvarying vec2 textureCoordinate;\nvarying vec2 textureNoRotate; // \u4fdd\u8bc1\u4ee5\u4e2d\u5fc3\u70b9\u65cb\u8f6c\n\n// \u65cb\u8f6c\u903b\u8f91\nuniform mat4 textureTransform;\n\nvoid main() \n{ \n  gl_Position = position;\n  textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n  textureNoRotate = inputTextureCoordinate.xy;\n}\n";
        f.s = "precision mediump float; \nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture; \n \n// x \u8f74\u7ad6\u6761\nuniform float xOffset;\nuniform float xWidth;\nuniform float xStride;\n\n// y \u8f74\u7ad6\u6761\nuniform float yOffset;\nuniform float yWidth;\nuniform float yStride;\n\n// \u4e2d\u5fc3\u70b9\nuniform vec2 center;\n// \u7f51\u683c\u534a\u5f84\nuniform float radius;\n// \u5bbd\u9ad8\u6bd4\nuniform float aspectRatio;\n// \u653e\u5927 \u6216 \u7f29\u5c0f\nuniform int zoomModel;  // 0 \u653e\u5927\uff0c1\u7f29\u5c0f\n\nuniform float maxRadius;\n\nvoid drawGrid(){\n    // \u7b2c\u4e00\u6b65\uff1a\u753b\u9ed1\u8272\u77e9\u5f62\u6846\n    // \u9ed1\u8272\u7ad6\u6761\n    float mx = mod(textureCoordinate.x + xOffset, xStride); \n    float my = mod(textureCoordinate.y + yOffset, yStride);\n\n    if(mx <= xWidth || my <= yWidth){ \n        gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0); \n    }else{\n        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n    }\n\n}\n\nvoid main()\n{ \n    highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\n    highp float cRadius = distance(center, textureCoordinateToUse);\n\n    // \u5982\u679c\u662f\u7f29\u5c0f\u6a21\u5f0f\n    if (1 == zoomModel){\n        if (cRadius < maxRadius && cRadius > radius){\n            drawGrid();\n        }else{\n            gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n        }\n    }else{\n        if (cRadius < radius){\n            drawGrid();\n        }else{\n            gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n        }\n    }\n}\n";
        f.t = "Diffuse";
    }
}
