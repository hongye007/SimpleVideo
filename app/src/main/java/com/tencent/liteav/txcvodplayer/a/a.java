package com.tencent.liteav.txcvodplayer.a;

public class a
{
    String a;
    String b;
    String c;
    
    a(final String b, final String a, final String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public String a() {
        if ("mp4".equals(this.c)) {
            return this.c();
        }
        return null;
    }
    
    public String b() {
        if ("m3u8".equals(this.c)) {
            return this.c();
        }
        return null;
    }
    
    public String c() {
        return this.a + "/" + this.b;
    }
    
    public String d() {
        return this.b;
    }
}
