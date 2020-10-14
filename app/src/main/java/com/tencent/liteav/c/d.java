package com.tencent.liteav.c;

import java.util.*;

public class d
{
    private static d a;
    private final LinkedList<f> b;
    private f c;
    
    public static d a() {
        if (d.a == null) {
            d.a = new d();
        }
        return d.a;
    }
    
    private d() {
        this.b = new LinkedList<f>();
    }
    
    public void a(final f c) {
        this.c = c;
        this.b.add(c);
    }
    
    public f b() {
        return this.c;
    }
    
    public void c() {
        if (this.b.size() == 0) {
            return;
        }
        this.b.removeLast();
    }
    
    public List<f> d() {
        return this.b;
    }
    
    public void e() {
        this.b.clear();
    }
}
