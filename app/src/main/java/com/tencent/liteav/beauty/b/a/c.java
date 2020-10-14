package com.tencent.liteav.beauty.b.a;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.beauty.*;

public class c extends h
{
    @Override
    public boolean c() {
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(6);
        if (this.a != 0 && this.a()) {
            this.g = true;
        }
        else {
            this.g = false;
        }
        this.d();
        return this.g;
    }
}
