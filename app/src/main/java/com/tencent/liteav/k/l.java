package com.tencent.liteav.k;

import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.beauty.*;

public class l extends aj
{
    private String x;
    
    public l(final String s, final String s2) {
        super(s, s2);
        this.x = "WatermarkTexture";
        this.t = true;
        this.u = 770;
    }
    
    public l() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }
    
    public void a(final e.f[] array) {
        if (null == this.r) {
            this.r = new a[array.length];
        }
        for (int i = 0; i < array.length; ++i) {
            if (null == this.r[i]) {
                this.r[i] = new a();
            }
            if (null == this.r[i].d) {
                this.r[i].d = new int[1];
            }
            this.a(array[i].f, array[i].g, array[i].b, array[i].c, array[i].d, i);
            this.r[i].d[0] = array[i].e;
        }
    }
}
