package com.tencent.liteav.beauty;

import android.content.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.datareport.*;

public class a
{
    private static a a;
    private static Context b;
    private static boolean c;
    private static boolean d;
    private static boolean e;
    private static boolean f;
    private static boolean g;
    private static boolean h;
    private static boolean i;
    private static boolean j;
    private static boolean k;
    private static boolean l;
    private static boolean m;
    private static boolean n;
    private static boolean o;
    private static boolean p;
    private static boolean q;
    private static boolean r;
    private static boolean s;
    private static boolean t;
    private static boolean u;
    private static boolean v;
    private static boolean w;
    private static boolean x;
    private static boolean y;
    private static boolean z;
    private static boolean A;
    private static boolean B;
    private static boolean C;
    private static boolean D;
    private static boolean E;
    private String F;
    
    public a() {
        this.F = "ReportDuaManage";
    }
    
    public static a a() {
        if (com.tencent.liteav.beauty.a.a == null) {
            com.tencent.liteav.beauty.a.a = new a();
        }
        return com.tencent.liteav.beauty.a.a;
    }
    
    public void a(final Context context) {
        this.h();
        com.tencent.liteav.beauty.a.b = context.getApplicationContext();
        if (!com.tencent.liteav.beauty.a.c) {
            TXCLog.i(this.F, "reportSDKInit");
            TXCDRApi.txReportDAU(com.tencent.liteav.beauty.a.b, 1201, 0, "reportSDKInit!");
        }
        com.tencent.liteav.beauty.a.c = true;
    }
    
    public void b() {
        if (!com.tencent.liteav.beauty.a.d) {
            TXCLog.i(this.F, "reportBeautyDua");
            TXCDRApi.txReportDAU(com.tencent.liteav.beauty.a.b, 1202, 0, "reportBeautyDua");
        }
        com.tencent.liteav.beauty.a.d = true;
    }
    
    public void c() {
        if (!com.tencent.liteav.beauty.a.e) {
            TXCLog.i(this.F, "reportWhiteDua");
            TXCDRApi.txReportDAU(com.tencent.liteav.beauty.a.b, 1203, 0, "reportWhiteDua");
        }
        com.tencent.liteav.beauty.a.e = true;
    }
    
    public void d() {
        if (!com.tencent.liteav.beauty.a.f) {
            TXCLog.i(this.F, "reportRuddyDua");
            TXCDRApi.txReportDAU(com.tencent.liteav.beauty.a.b, 1204, 0, "reportRuddyDua");
        }
        com.tencent.liteav.beauty.a.f = true;
    }
    
    public void e() {
        if (!com.tencent.liteav.beauty.a.j) {
            TXCLog.i(this.F, "reportFilterImageDua");
            TXCDRApi.txReportDAU(com.tencent.liteav.beauty.a.b, 1208, 0, "reportFilterImageDua");
        }
        com.tencent.liteav.beauty.a.j = true;
    }
    
    public void f() {
        if (!com.tencent.liteav.beauty.a.l) {
            TXCLog.i(this.F, "reportSharpDua");
            TXCDRApi.txReportDAU(com.tencent.liteav.beauty.a.b, 1210, 0, "reportSharpDua");
        }
        com.tencent.liteav.beauty.a.l = true;
    }
    
    public void g() {
        if (!com.tencent.liteav.beauty.a.n) {
            TXCLog.i(this.F, "reportWarterMarkDua");
            TXCDRApi.txReportDAU(com.tencent.liteav.beauty.a.b, 1212, 0, "reportWarterMarkDua");
        }
        com.tencent.liteav.beauty.a.n = true;
    }
    
    private void h() {
        TXCLog.i(this.F, "resetReportState");
        com.tencent.liteav.beauty.a.c = false;
        com.tencent.liteav.beauty.a.d = false;
        com.tencent.liteav.beauty.a.e = false;
        com.tencent.liteav.beauty.a.f = false;
        com.tencent.liteav.beauty.a.g = false;
        com.tencent.liteav.beauty.a.h = false;
        com.tencent.liteav.beauty.a.i = false;
        com.tencent.liteav.beauty.a.j = false;
        com.tencent.liteav.beauty.a.k = false;
        com.tencent.liteav.beauty.a.l = false;
        com.tencent.liteav.beauty.a.m = false;
        com.tencent.liteav.beauty.a.n = false;
        com.tencent.liteav.beauty.a.C = false;
        com.tencent.liteav.beauty.a.o = false;
        com.tencent.liteav.beauty.a.p = false;
        com.tencent.liteav.beauty.a.q = false;
        com.tencent.liteav.beauty.a.r = false;
        com.tencent.liteav.beauty.a.s = false;
        com.tencent.liteav.beauty.a.t = false;
        com.tencent.liteav.beauty.a.u = false;
        com.tencent.liteav.beauty.a.v = false;
        com.tencent.liteav.beauty.a.w = false;
        com.tencent.liteav.beauty.a.x = false;
        com.tencent.liteav.beauty.a.y = false;
        com.tencent.liteav.beauty.a.z = false;
        com.tencent.liteav.beauty.a.A = false;
        com.tencent.liteav.beauty.a.B = false;
        com.tencent.liteav.beauty.a.D = false;
        com.tencent.liteav.beauty.a.E = false;
    }
    
    static {
        com.tencent.liteav.beauty.a.a = null;
        com.tencent.liteav.beauty.a.b = null;
        com.tencent.liteav.beauty.a.c = false;
        com.tencent.liteav.beauty.a.d = false;
        com.tencent.liteav.beauty.a.e = false;
        com.tencent.liteav.beauty.a.f = false;
        com.tencent.liteav.beauty.a.g = false;
        com.tencent.liteav.beauty.a.h = false;
        com.tencent.liteav.beauty.a.i = false;
        com.tencent.liteav.beauty.a.j = false;
        com.tencent.liteav.beauty.a.k = false;
        com.tencent.liteav.beauty.a.l = false;
        com.tencent.liteav.beauty.a.m = false;
        com.tencent.liteav.beauty.a.n = false;
        com.tencent.liteav.beauty.a.o = false;
        com.tencent.liteav.beauty.a.p = false;
        com.tencent.liteav.beauty.a.q = false;
        com.tencent.liteav.beauty.a.r = false;
        com.tencent.liteav.beauty.a.s = false;
        com.tencent.liteav.beauty.a.t = false;
        com.tencent.liteav.beauty.a.u = false;
        com.tencent.liteav.beauty.a.v = false;
        com.tencent.liteav.beauty.a.w = false;
        com.tencent.liteav.beauty.a.x = false;
        com.tencent.liteav.beauty.a.y = false;
        com.tencent.liteav.beauty.a.z = false;
        com.tencent.liteav.beauty.a.A = false;
        com.tencent.liteav.beauty.a.B = false;
        com.tencent.liteav.beauty.a.C = false;
        com.tencent.liteav.beauty.a.D = false;
        com.tencent.liteav.beauty.a.E = false;
    }
}
