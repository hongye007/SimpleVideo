package com.tencent.liteav.beauty.b;

import android.opengl.*;

public class g extends ad
{
    private int x;
    private int y;
    private int z;
    private int A;
    private float[] B;
    
    @Override
    public void d() {
        super.d();
        this.x = GLES20.glGetUniformLocation(this.q(), "screenMode");
        this.y = GLES20.glGetUniformLocation(this.q(), "screenReplaceColor");
        this.z = GLES20.glGetUniformLocation(this.q(), "screenMirrorX");
        this.A = GLES20.glGetUniformLocation(this.q(), "screenMirrorY");
        this.b(this.B);
    }
    
    public void b(final float[] array) {
        final float[] array2 = { (float)(0.2989 * array[0] + 0.5866 * array[1] + 0.1145 * array[2]), 0.0f, 0.0f };
        array2[1] = (float)(0.7132 * (array[0] - array2[0]));
        array2[2] = (float)(0.5647 * (array[2] - array2[0]));
        this.b(this.y, array2);
    }
}
