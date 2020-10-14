package com.tencent.liteav.beauty.b;

import com.tencent.liteav.k.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;
import java.nio.*;

public class ai extends l
{
    private static String x;
    private int y;
    private static String z;
    private boolean A;
    
    public ai() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.y = -1;
        this.A = false;
    }
    
    @Override
    public boolean a() {
        if (!super.a()) {
            TXCLog.e(ai.z, "onInit failed!");
            return false;
        }
        this.y = GLES20.glGetUniformLocation(this.a, "alphaBlend");
        this.a(1.0f);
        return true;
    }
    
    public void a(final float n) {
        this.a(this.y, n);
    }
    
    public void c(final boolean a) {
        this.A = a;
    }
    
    @Override
    protected void j() {
        if (this.t && null != this.r) {
            GLES20.glEnable(3042);
            if (this.A) {
                GLES20.glBlendFunc(773, 772);
            }
            else {
                GLES20.glBlendFunc(770, 771);
            }
            GLES20.glActiveTexture(33984);
            for (int i = 0; i < this.r.length; ++i) {
                if (null != this.r[i]) {
                    GLES20.glBindTexture(3553, this.r[i].d[0]);
                    GLES20.glUniform1i(this.c, 0);
                    GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 8, (Buffer)this.r[i].a);
                    GLES20.glEnableVertexAttribArray(this.b);
                    GLES20.glVertexAttribPointer(this.d, 4, 5126, false, 16, (Buffer)this.r[i].b);
                    GLES20.glEnableVertexAttribArray(this.d);
                    GLES20.glDrawElements(4, ai.v.length, 5123, (Buffer)this.w);
                    GLES20.glDisableVertexAttribArray(this.b);
                    GLES20.glDisableVertexAttribArray(this.d);
                }
            }
            GLES20.glDisable(3042);
        }
    }
    
    static {
        ai.x = "varying lowp vec2 textureCoordinate;\n   \n  uniform sampler2D inputImageTexture;\n  uniform mediump float alphaBlend;\n  \n  void main()\n  {\n      mediump vec4 color = texture2D(inputImageTexture, textureCoordinate);\n       if (0.0 == color.a){\n            gl_FragColor = color;\n       }else{\n            gl_FragColor = vec4(color.rgb, alphaBlend);\n       } \n  }\n";
        ai.z = "AlphaTexture";
    }
}
