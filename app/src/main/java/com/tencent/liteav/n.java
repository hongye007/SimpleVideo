package com.tencent.liteav;

import android.content.*;
import android.content.pm.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.datareport.*;
import com.tencent.liteav.basic.log.*;

public class n
{
    private final String a = "TXCVodPlayCollection";
    private Context b;
    private String c;
    private long d;
    private long e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private String n;
    private boolean o;
    private boolean p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private String y;
    
    public n(final Context b) {
        this.c = null;
        this.d = 0L;
        this.e = 0L;
        this.f = false;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.o = false;
        this.p = false;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.b = b;
        this.n = TXCCommonUtil.getAppVersion();
    }
    
    public void a(final String c) {
        this.c = c;
    }
    
    public String a() {
        final Context b = this.b;
        final ApplicationInfo applicationInfo = this.b.getApplicationInfo();
        final int labelRes = applicationInfo.labelRes;
        return (labelRes == 0) ? applicationInfo.nonLocalizedLabel.toString() : b.getString(labelRes);
    }
    
    private void m() {
        final String e = com.tencent.liteav.basic.util.f.e();
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = false;
        txcdrExtInfo.url = this.c;
        TXCDRApi.InitEvent(this.b, e, com.tencent.liteav.basic.datareport.a.ad, com.tencent.liteav.basic.datareport.a.as, txcdrExtInfo);
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_timeuse", this.h);
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "str_stream_url", this.c);
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_videotime", this.g);
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "str_device_type", com.tencent.liteav.basic.util.f.c());
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_network_type", com.tencent.liteav.basic.util.f.e(this.b));
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "str_user_id", com.tencent.liteav.basic.util.f.b(this.b));
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "str_package_name", com.tencent.liteav.basic.util.f.c(this.b));
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "str_app_version", this.n);
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "dev_uuid", com.tencent.liteav.basic.util.f.f(this.b));
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_first_i_frame", this.i);
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_isp2p", this.j);
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_avg_load", (this.k == 0) ? 0L : ((long)(this.l / this.k)));
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_load_cnt", this.k);
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_max_load", this.m);
        TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_player_type", this.r);
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "str_app_name", this.a());
        if (this.t > 0) {
            TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_dns_time", this.t);
        }
        else {
            TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_dns_time", -1L);
        }
        if (this.s > 0) {
            TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_tcp_did_connect", this.s);
        }
        else {
            TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_tcp_did_connect", -1L);
        }
        if (this.u > 0) {
            TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_first_video_packet", this.u);
        }
        else {
            TXCDRApi.txSetEventIntValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_first_video_packet", -1L);
        }
        TXCDRApi.txSetEventValue(e, com.tencent.liteav.basic.datareport.a.ad, "u32_server_ip", this.y);
        TXCDRApi.nativeReportEvent(e, com.tencent.liteav.basic.datareport.a.ad);
        TXCLog.w("TXCVodPlayCollection", "report evt 40301: token=" + e + " " + "u32_timeuse" + "=" + this.h + " " + "str_stream_url" + "=" + this.c + " " + "u32_videotime" + "=" + this.g + " " + "str_device_type" + "=" + com.tencent.liteav.basic.util.f.c() + " " + "u32_network_type" + "=" + com.tencent.liteav.basic.util.f.e(this.b) + " " + "str_user_id" + "=" + com.tencent.liteav.basic.util.f.b(this.b) + " " + "str_package_name" + "=" + com.tencent.liteav.basic.util.f.c(this.b) + " " + "str_app_version" + "=" + this.n + " " + "dev_uuid" + "=" + com.tencent.liteav.basic.util.f.f(this.b) + " " + "u32_first_i_frame" + "=" + this.i + " " + "u32_isp2p" + "=" + this.j + " " + "u32_avg_load" + "=" + ((this.k == 0) ? 0 : (this.l / this.k)) + " " + "u32_load_cnt" + "=" + this.k + " " + "u32_max_load" + "=" + this.m + " " + "u32_player_type" + "=" + this.r + " " + "u32_dns_time" + "=" + this.t + " " + "u32_tcp_did_connect" + "=" + this.s + " " + "u32_first_video_packet" + "=" + this.u + " " + "u32_server_ip" + "=" + this.y);
    }
    
    public void a(final int g) {
        this.g = g;
    }
    
    public void b() {
        this.f = true;
        this.d = System.currentTimeMillis();
    }
    
    public void c() {
        if (this.f) {
            this.h = (int)((System.currentTimeMillis() - this.d) / 1000L);
            this.m();
            this.f = false;
        }
        this.o = false;
        this.p = false;
    }
    
    public void d() {
        if (this.i != 0) {
            if (this.p) {
                final int m = (int)(System.currentTimeMillis() - this.e);
                this.l += m;
                ++this.k;
                if (this.m < m) {
                    this.m = m;
                }
                this.p = false;
            }
        }
        if (this.o) {
            this.o = false;
        }
    }
    
    public void e() {
        if (this.i == 0) {
            this.i = (int)(System.currentTimeMillis() - this.d);
        }
    }
    
    public void f() {
        if (this.s == 0) {
            this.s = (int)(System.currentTimeMillis() - this.d);
        }
    }
    
    public void g() {
        if (this.t == 0) {
            this.t = (int)(System.currentTimeMillis() - this.d);
        }
    }
    
    public void h() {
        if (this.u == 0) {
            this.u = (int)(System.currentTimeMillis() - this.d);
        }
    }
    
    public void i() {
        this.e = System.currentTimeMillis();
        this.p = true;
    }
    
    public void j() {
        this.o = true;
        ++this.q;
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.by);
    }
    
    public void b(final int r) {
        this.r = r;
    }
    
    public void a(final boolean b) {
        if (b) {
            this.v = 1;
            TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.bA);
        }
        else {
            this.v = 0;
        }
    }
    
    public void k() {
        ++this.x;
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.bB);
    }
    
    public void l() {
        ++this.w;
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.bz);
    }
    
    public void b(final String y) {
        this.y = y;
    }
}
