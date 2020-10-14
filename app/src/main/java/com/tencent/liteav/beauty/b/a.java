package com.tencent.liteav.beauty.b;

public class a
{
    private boolean a;
    
    public a() {
        this.a = false;
    }
    
    public synchronized void a() {
        this.a = true;
        this.notify();
    }
    
    public synchronized void b() throws InterruptedException {
        while (!this.a) {
            this.wait();
        }
        this.a = false;
    }
}
