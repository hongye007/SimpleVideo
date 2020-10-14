package com.tencent.liteav.c;

public class e
{
    private final String a = "PicConfig";
    private static e b;
    private int c;
    
    public static e a() {
        if (e.b == null) {
            synchronized (e.class) {
                if (e.b == null) {
                    e.b = new e();
                }
            }
        }
        return e.b;
    }
    
    public void a(final int c) {
        this.c = c;
    }
    
    public int b() {
        return this.c;
    }
}
