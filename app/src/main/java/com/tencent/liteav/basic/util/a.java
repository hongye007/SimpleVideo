package com.tencent.liteav.basic.util;

import java.lang.ref.*;

public class a<T>
{
    private final ThreadLocal<T> a;
    private final a<T> b;
    private WeakReference<T> c;
    
    public a(final a<T> b) {
        this.a = new ThreadLocal<T>();
        this.c = new WeakReference<T>(null);
        this.b = b;
    }
    
    public T a() {
        T t = this.a.get();
        if (t == null) {
            t = this.b();
            this.a.set(t);
        }
        return t;
    }
    
    private T b() {
        T t;
        if ((t = this.c.get()) == null) {
            synchronized (this) {
                if ((t = this.c.get()) == null) {
                    t = this.b.a();
                    this.c = new WeakReference<T>(t);
                }
            }
        }
        return t;
    }
    
    public interface a<T>
    {
        T a();
    }
}
