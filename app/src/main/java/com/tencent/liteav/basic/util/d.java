package com.tencent.liteav.basic.util;

public class d
{
    public int a;
    public int b;
    
    public d() {
    }
    
    public d(final int a, final int b) {
        this.a = a;
        this.b = b;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof d)) {
            return false;
        }
        final d d = (d)o;
        return d.a == this.a && d.b == this.b;
    }
    
    @Override
    public int hashCode() {
        return this.a * 32713 + this.b;
    }
    
    @Override
    public String toString() {
        return "Size(" + this.a + ", " + this.b + ")";
    }
}
