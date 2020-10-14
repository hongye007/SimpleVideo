package com.tencent.liteav;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import com.tencent.liteav.k.*;

public class g extends h
{
    private int r;
    
    public g() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate; \nuniform sampler2D inputImageTexture; \nuniform lowp float mode; \n \nvoid main() \n{ \n    highp vec2 position = textureCoordinate; \n     \n    if (mode <= 0.5) \n    { \n        if (position.x > 0.5) \n        { \n            position.x = 1.0 - position.x; \n        } \n    } \n    else \n    { \n        if (position.x > 0.5) \n        { \n            position.x = position.x - 0.5; \n        } \n        else \n        { \n            position.x = 0.5 - position.x; \n        } \n    } \n     \n    gl_FragColor = texture2D(inputImageTexture, position); \n} \n");
        this.r = -1;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.r = GLES20.glGetUniformLocation(this.a, "mode");
        return a;
    }
    
    public void a(final n.j j) {
        this.a(this.r, j.a);
    }
}
