package com.tencent.liteav.videodecoder;

import java.io.*;

class c
{
    protected a a;
    int b;
    private InputStream c;
    private int d;
    private int e;
    private final OutputStream f;
    private int[] g;
    private int h;
    
    public c(final InputStream c, final OutputStream f) throws IOException {
        this.a = new a(50);
        this.g = new int[8];
        this.c = c;
        this.f = f;
        this.d = c.read();
        this.e = c.read();
    }
    
    public boolean a(final boolean b) throws IOException {
        return this.b(b) == 1;
    }
    
    public int b(final boolean b) throws IOException {
        if (this.b == 8) {
            this.d();
            if (this.d == -1) {
                return -1;
            }
        }
        final int n = this.d >> 7 - this.b & 0x1;
        ++this.b;
        if (b && this.f != null) {
            this.d(n);
        }
        return n;
    }
    
    public long a(final int n) throws IOException {
        if (n > 64) {
            throw new IllegalArgumentException("Can not readByte more then 64 bit");
        }
        long n2 = 0L;
        for (int i = 0; i < n; ++i) {
            n2 = (n2 << 1 | (long)this.b(true));
        }
        return n2;
    }
    
    public void b(final int n) throws IOException {
        if (n > 64) {
            throw new IllegalArgumentException("Can not skip more then 64 bit");
        }
        for (int i = 0; i < n; ++i) {
            this.b(true);
        }
    }
    
    private void d() throws IOException {
        this.d = this.e;
        this.e = this.c.read();
        this.b = 0;
    }
    
    public long a(final int n, final String s) throws IOException {
        final long a = this.a(n);
        this.a(s, String.valueOf(a));
        return a;
    }
    
    public void b(final int n, final String s) throws IOException {
        this.b(n);
        this.a(s, "skip NBits");
    }
    
    private int e() throws IOException {
        int n = 0;
        while (this.b(true) == 0) {
            ++n;
        }
        int n2 = 0;
        if (n > 0) {
            n2 = (int)((1 << n) - 1 + this.a(n));
        }
        return n2;
    }
    
    private void f() throws IOException {
        int n = 0;
        while (this.b(true) == 0) {
            ++n;
        }
        if (n > 0) {
            this.b(n);
        }
    }
    
    public int a(final String s) throws IOException {
        final int e = this.e();
        this.a(s, String.valueOf(e));
        return e;
    }
    
    public void b(final String s) throws IOException {
        this.f();
        this.a(s, "skip UE");
    }
    
    public int c(final String s) throws IOException {
        final int e = this.e();
        final int n = ((e >> 1) + (e & 0x1)) * (((e & 0x1) << 1) - 1);
        this.a(s, String.valueOf(n));
        return n;
    }
    
    public boolean d(final String s) throws IOException {
        final boolean a = this.a(true);
        this.a(s, a ? "1" : "0");
        return a;
    }
    
    public boolean e(final String s) throws IOException {
        final boolean a = this.a(false);
        this.a(s, a ? "1" : "0");
        return a;
    }
    
    public void c(final int n) throws IOException {
        final int[] array = new int[n];
        int n2 = 8;
        int n3 = 8;
        for (int i = 0; i < n; ++i) {
            if (n3 != 0) {
                n3 = (n2 + this.c("deltaScale") + 256) % 256;
                final boolean b = i == 0 && n3 == 0;
            }
            array[i] = ((n3 == 0) ? n2 : n3);
            n2 = array[i];
        }
    }
    
    public void a() throws IOException {
        for (int i = this.h; i < 8; ++i) {
            this.g[i] = 0;
        }
        this.h = 0;
        this.g();
    }
    
    private void g() throws IOException {
        this.f.write(this.g[0] << 7 | this.g[1] << 6 | this.g[2] << 5 | this.g[3] << 4 | this.g[4] << 3 | this.g[5] << 2 | this.g[6] << 1 | this.g[7]);
    }
    
    public void d(final int n) throws IOException {
        if (this.h == 8) {
            this.h = 0;
            this.g();
        }
        this.g[this.h++] = n;
    }
    
    public void a(final long n, final int n2) throws IOException {
        for (int i = 0; i < n2; ++i) {
            this.d((int)(n >> n2 - i - 1) & 0x1);
        }
    }
    
    public void b() throws IOException {
        this.a(0L, 8 - this.h);
    }
    
    public void e(final int n) throws IOException {
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < 15; ++i) {
            if (n < n3 + (1 << i)) {
                n2 = i;
                break;
            }
            n3 += 1 << i;
        }
        this.a(0L, n2);
        this.d(1);
        this.a(n - n3, n2);
    }
    
    public void c(final int n, final String s) throws IOException {
        this.e(n);
    }
    
    public void a(final boolean b, final String s) throws IOException {
        this.d(b ? 1 : 0);
    }
    
    public void c() throws IOException {
        this.d(1);
        this.b();
        this.a();
    }
    
    private void a(final String s, final String s2) {
    }
}
