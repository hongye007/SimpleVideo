package com.tencent.liteav.c;

import java.util.*;
import com.tencent.liteav.i.*;

public class f
{
    private static f a;
    private List<a.i> b;
    
    public static f a() {
        if (f.a == null) {
            synchronized (g.class) {
                if (f.a == null) {
                    f.a = new f();
                }
            }
        }
        return f.a;
    }
    
    public void a(final List<a.i> b) {
        this.b = b;
    }
    
    public a.i b() {
        if (this.b == null || this.b.size() == 0) {
            return null;
        }
        return this.b.get(0);
    }
    
    public void c() {
        if (this.b != null) {
            this.b.clear();
        }
        this.b = null;
    }
}
