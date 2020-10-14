package com.tencent.liteav.network.a;

public final class e
{
    public final String a;
    public final int b;
    public final int c;
    public final long d;
    
    public e(final String a, final int b, final int n, final long d) {
        this.a = a;
        this.b = b;
        this.c = ((n < 600) ? 600 : n);
        this.d = d;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof e)) {
            return false;
        }
        final e e = (e)o;
        return this.a.equals(e.a) && this.b == e.b && this.c == e.c && this.d == e.d;
    }
    
    public boolean a() {
        return this.b == 5;
    }
}
