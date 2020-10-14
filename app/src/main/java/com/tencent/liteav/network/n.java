package com.tencent.liteav.network;

import android.content.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.datareport.*;
import java.util.regex.*;

public class n
{
    private Context a;
    private String b;
    private String c;
    private String d;
    private String e;
    private long f;
    private long g;
    private String h;
    private long i;
    private long j;
    private long k;
    private long l;
    private long m;
    private long n;
    private long o;
    private long p;
    private long q;
    private long r;
    private long s;
    private long t;
    private long u;
    private long v;
    private boolean w;
    
    public n(final Context context) {
        this.w = true;
        this.a = context.getApplicationContext();
        this.b = com.tencent.liteav.basic.util.f.b(this.a);
        this.d = "Android";
        com.tencent.liteav.network.m.a().a(this.a);
        this.a();
    }
    
    public void a() {
        this.c = "";
        this.f = 0L;
        this.g = -1L;
        this.h = "";
        this.i = -1L;
        this.j = -1L;
        this.k = -1L;
        this.l = -1L;
        this.e = "";
        this.m = 0L;
        this.n = 0L;
        this.o = 0L;
        this.p = 0L;
        this.q = 0L;
        this.r = 0L;
        this.s = 0L;
        this.t = 0L;
        this.u = 0L;
        this.v = 0L;
        this.w = true;
    }
    
    private void e() {
        final long p = this.p;
        final long q = this.q;
        this.a();
        this.n = p;
        this.o = q;
    }
    
    public void b() {
        this.f = System.currentTimeMillis();
        this.c = com.tencent.liteav.network.m.a().b();
    }
    
    public void c() {
        this.f();
        this.e();
    }
    
    public void a(final boolean b) {
        this.l = (b ? 2L : 1L);
        if (b) {
            this.w = false;
        }
    }
    
    public void a(final String e) {
        this.e = e;
    }
    
    public void a(final boolean b, final String h) {
        this.h = h;
        if (b) {
            this.g = 1L;
        }
        else if (h != null) {
            String substring = h;
            final int index = h.indexOf(":");
            if (index != -1) {
                substring = h.substring(0, index);
            }
            if (substring != null) {
                final String[] split = substring.split("[.]");
                for (int length = split.length, i = 0; i < length; ++i) {
                    if (!this.c(split[i])) {
                        this.g = 3L;
                        return;
                    }
                }
                this.g = 2L;
            }
        }
    }
    
    public void a(final long i, final long j, final long k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }
    
    public void d() {
        ++this.m;
    }
    
    public void a(final long p2, final long q) {
        this.p = p2;
        this.q = q;
    }
    
    public void b(final long t, final long u) {
        ++this.v;
        this.r += t;
        this.s += u;
        if (t > this.t) {
            this.t = t;
        }
        if (u > this.u) {
            this.u = u;
        }
    }
    
    private void f() {
        if (this.f == 0L || this.b(this.b) || this.b(this.e)) {
            return;
        }
        final String streamIDByStreamUrl = TXCCommonUtil.getStreamIDByStreamUrl(this.e);
        final long n = System.currentTimeMillis() - this.f;
        long n2 = 0L;
        long n3 = 0L;
        if (this.p > this.n) {
            n2 = this.p - this.n;
        }
        if (this.q > this.o) {
            n3 = this.q - this.o;
        }
        long n4 = 0L;
        long n5 = 0L;
        if (this.v > 0L) {
            n4 = this.r / this.v;
            n5 = this.s / this.v;
        }
        final String txCreateToken = TXCDRApi.txCreateToken();
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = false;
        txcdrExtInfo.url = this.e;
        TXCDRApi.InitEvent(this.a, txCreateToken, com.tencent.liteav.basic.datareport.a.T, com.tencent.liteav.basic.datareport.a.al, txcdrExtInfo);
        TXCDRApi.txSetEventValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "str_user_id", this.b);
        TXCDRApi.txSetEventValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "str_stream_id", streamIDByStreamUrl);
        TXCDRApi.txSetEventValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "str_access_id", this.c);
        TXCDRApi.txSetEventValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "str_platform", this.d);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_server_type", this.g);
        TXCDRApi.txSetEventValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "str_server_addr", this.h);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_dns_timecost", this.i);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_connect_timecost", this.j);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_handshake_timecost", this.k);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_push_type", this.l);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_totaltime", n);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_block_count", this.m);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_video_drop", n2);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_audio_drop", n3);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_video_que_avg", n4);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_audio_que_avg", n5);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_video_que_max", this.t);
        TXCDRApi.txSetEventIntValue(txCreateToken, com.tencent.liteav.basic.datareport.a.T, "u32_audio_que_max", this.u);
        TXCDRApi.nativeReportEvent(txCreateToken, com.tencent.liteav.basic.datareport.a.T);
        float n6 = 0.0f;
        float n7 = 0.0f;
        float n8 = 0.0f;
        if (n > 0L) {
            if (this.m == 0L) {
                n6 = 0.0f;
            }
            else {
                n6 = this.m * 60000.0f / n;
            }
        }
        if (this.v > 0L) {
            if (this.r == 0L) {
                n7 = 0.0f;
            }
            else {
                n7 = this.r / (float)this.v;
            }
            if (this.s == 0L) {
                n8 = 0.0f;
            }
            else {
                n8 = this.s / (float)this.v;
            }
        }
        if (this.w && !this.b(this.c) && this.k != -1L) {
            com.tencent.liteav.network.m.a().a(this.c, this.g, n, this.k, n6, n7, n8);
        }
    }
    
    private boolean b(final String s) {
        return s == null || s.length() == 0;
    }
    
    private boolean c(final String s) {
        return Pattern.compile("[0-9]*").matcher(s).matches();
    }
}
