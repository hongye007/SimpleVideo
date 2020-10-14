package com.tencent.liteav;

import android.graphics.*;
import org.json.*;

public class i implements Cloneable
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public boolean g;
    public int h;
    public int i;
    public int j;
    public CameraProxy k;
    public int l;
    public boolean m;
    public int n;
    public int o;
    public boolean p;
    public int q;
    public int r;
    public int s;
    public int t;
    public boolean u;
    public boolean v;
    public boolean w;
    public int x;
    public int y;
    public boolean z;
    public Bitmap A;
    public int B;
    public int C;
    public int D;
    public Bitmap E;
    public int F;
    public int G;
    public float H;
    public float I;
    public float J;
    public boolean K;
    public boolean L;
    public boolean M;
    public boolean N;
    public int O;
    public boolean P;
    public boolean Q;
    public int R;
    public boolean S;
    public boolean T;
    public boolean U;
    public boolean V;
    public boolean W;
    public int X;
    public JSONArray Y;
    
    public i() {
        this.a = 0;
        this.b = 0;
        this.c = 1200;
        this.d = 1500;
        this.e = 800;
        this.f = 5;
        this.g = true;
        this.h = 20;
        this.i = 1;
        this.j = 2;
        this.k = com.tencent.liteav.basic.a.c.c;
        this.l = 1;
        this.m = true;
        this.n = 3;
        this.o = 0;
        this.p = false;
        this.q = 3;
        this.r = 3;
        this.s = 48000;
        this.t = 1;
        this.u = true;
        this.v = false;
        this.w = false;
        this.x = 0;
        this.y = 10;
        this.z = false;
        this.A = null;
        this.B = 300;
        this.C = 10;
        this.D = 1;
        this.E = null;
        this.F = 0;
        this.G = 0;
        this.H = 0.0f;
        this.I = 0.0f;
        this.J = -1.0f;
        this.K = true;
        this.L = false;
        this.M = false;
        this.N = true;
        this.O = 1;
        this.P = false;
        this.Q = false;
        this.R = 0;
        this.S = false;
        this.T = true;
        this.U = false;
        this.V = false;
        this.W = false;
        this.X = 0;
        this.Y = null;
    }
    
    public static a a(final CameraProxy c) {
        final a a = new a();
        switch (i$1.a[c.ordinal()]) {
            case 1: {
                a.a = 368;
                a.b = 640;
                break;
            }
            case 2: {
                a.a = 544;
                a.b = 960;
                break;
            }
            case 3: {
                a.a = 720;
                a.b = 1280;
                break;
            }
            case 4: {
                a.a = 320;
                a.b = 480;
                break;
            }
            case 5: {
                a.a = 192;
                a.b = 320;
                break;
            }
            case 6: {
                a.a = 272;
                a.b = 480;
                break;
            }
            case 7: {
                a.a = 240;
                a.b = 320;
                break;
            }
            case 8: {
                a.a = 368;
                a.b = 480;
                break;
            }
            case 9: {
                a.a = 480;
                a.b = 640;
                break;
            }
            case 10: {
                a.a = 480;
                a.b = 480;
                break;
            }
            case 11: {
                a.a = 272;
                a.b = 272;
                break;
            }
            case 12: {
                a.a = 160;
                a.b = 160;
                break;
            }
            case 13: {
                a.a = 640;
                a.b = 368;
                break;
            }
            case 14: {
                a.a = 960;
                a.b = 544;
                break;
            }
            case 15: {
                a.a = 1280;
                a.b = 720;
                break;
            }
            case 16: {
                a.a = 640;
                a.b = 480;
                break;
            }
            case 17: {
                a.a = 480;
                a.b = 368;
                break;
            }
            case 18: {
                a.a = 320;
                a.b = 240;
                break;
            }
            case 19: {
                a.a = 480;
                a.b = 272;
                break;
            }
            case 20: {
                a.a = 320;
                a.b = 192;
                break;
            }
            case 21: {
                a.a = 128;
                a.b = 128;
                break;
            }
            case 22: {
                a.a = 1088;
                a.b = 1920;
                break;
            }
            case 23: {
                a.a = 1920;
                a.b = 1088;
                break;
            }
            default: {
                a.a = 368;
                a.b = 640;
                break;
            }
        }
        return a;
    }
    
    public boolean a() {
        if (this.k != com.tencent.liteav.basic.a.c.a) {
            final a a = a(this.k);
            this.a = a.a;
            this.b = a.b;
        }
        return this.a > this.b;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public static class a
    {
        public int a;
        public int b;
        
        public a() {
            this.a = 0;
            this.b = 0;
        }
    }
}
