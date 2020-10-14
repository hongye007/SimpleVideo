package com.tencent.liteav.j;

import android.util.*;

public class b
{
    private static boolean a;
    private static int b;
    private static int c;
    private static int d;
    private static int e;
    private static int f;
    private static int g;
    private static int h;
    private static boolean i;
    
    public static void a() {
        ++com.tencent.liteav.j.b.b;
        if (com.tencent.liteav.j.b.a) {
            Log.d("FrameCounter", "decodeVideoCount:" + com.tencent.liteav.j.b.b);
        }
    }
    
    public static void b() {
        ++com.tencent.liteav.j.b.c;
        if (com.tencent.liteav.j.b.a) {
            Log.d("FrameCounter", "decodeAudioCount:" + com.tencent.liteav.j.b.c);
        }
    }
    
    public static void c() {
        ++com.tencent.liteav.j.b.d;
        if (com.tencent.liteav.j.b.a) {
            Log.d("FrameCounter", "processVideoCount:" + com.tencent.liteav.j.b.d);
        }
    }
    
    public static void d() {
        ++com.tencent.liteav.j.b.e;
        if (com.tencent.liteav.j.b.a) {
            Log.d("FrameCounter", "processAudioCount:" + com.tencent.liteav.j.b.e);
        }
    }
    
    public static void e() {
        ++com.tencent.liteav.j.b.f;
        if (com.tencent.liteav.j.b.a) {
            Log.d("FrameCounter", "renderVideoCount:" + com.tencent.liteav.j.b.f);
        }
    }
    
    public static void f() {
        ++com.tencent.liteav.j.b.g;
        if (com.tencent.liteav.j.b.a) {
            Log.d("FrameCounter", "encodeVideoCount:" + com.tencent.liteav.j.b.g);
        }
    }
    
    public static void g() {
        ++com.tencent.liteav.j.b.h;
        if (com.tencent.liteav.j.b.a) {
            Log.d("FrameCounter", "encodeAudioCount:" + com.tencent.liteav.j.b.h);
        }
    }
    
    public static void h() {
        com.tencent.liteav.j.b.i = true;
        com.tencent.liteav.j.b.b = 0;
        com.tencent.liteav.j.b.c = 0;
        com.tencent.liteav.j.b.d = 0;
        com.tencent.liteav.j.b.e = 0;
        com.tencent.liteav.j.b.f = 0;
        com.tencent.liteav.j.b.g = 0;
        com.tencent.liteav.j.b.h = 0;
    }
    
    static {
        com.tencent.liteav.j.b.a = false;
        com.tencent.liteav.j.b.i = false;
    }
}
