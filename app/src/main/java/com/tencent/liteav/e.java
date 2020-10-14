package com.tencent.liteav;

import android.content.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.module.*;
import android.net.*;
import android.text.*;
import com.tencent.liteav.network.a.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.datareport.*;
import java.text.*;
import java.util.*;

public class e
{
    private static String a;
    private HashMap b;
    private String c;
    private int d;
    private int e;
    private int f;
    private int g;
    private Context h;
    private String i;
    private long j;
    private long k;
    private long l;
    private long m;
    private boolean n;
    private long o;
    private int p;
    private boolean q;
    private long r;
    private long s;
    private long t;
    private long u;
    private long v;
    private int w;
    private String x;
    private static HashMap<String, a> y;
    private String z;
    private String A;
    
    public e(final Context context) {
        this.l = -1L;
        this.q = false;
        this.r = 0L;
        this.s = 0L;
        this.t = 0L;
        this.u = 0L;
        this.v = 0L;
        this.w = 0;
        this.z = "";
        this.A = "";
        this.b = new HashMap(100);
        this.h = context.getApplicationContext();
        this.i = TXCCommonUtil.getAppVersion();
        this.p = 5000;
        this.v = 0L;
    }
    
    public void a() {
        this.o();
        this.k = -1L;
        this.m = -1L;
        this.r = System.currentTimeMillis();
    }
    
    public void b() {
        if (this.n) {
            this.m();
        }
        else {
            TXCLog.e(com.tencent.liteav.e.a, "push " + this.c + " failed!");
            this.k();
        }
    }
    
    public void c() {
        if (this.n) {
            if (this.q) {
                this.d(com.tencent.liteav.basic.datareport.a.Z);
            }
            else {
                this.d(com.tencent.liteav.basic.datareport.a.W);
            }
        }
        else {
            TXCLog.e(com.tencent.liteav.e.a, "play " + this.c + " failed");
            if (this.q) {
                this.c(com.tencent.liteav.basic.datareport.a.X);
            }
            else {
                this.c(com.tencent.liteav.basic.datareport.a.U);
            }
        }
        if (this.q) {
            this.j();
        }
        TXCStatus.a(this.z, 7107, 0L);
        TXCStatus.a(this.z, 2033, 0L);
        TXCStatus.a(this.z, 6001, 0L);
        TXCStatus.a(this.z, 7104, 0L);
        TXCStatus.a(this.z, 7108, 0L);
    }
    
    public void a(final boolean q) {
        this.q = q;
    }
    
    public void a(final String c) {
        this.b(this.c = c);
    }
    
    public void b(final String x) {
        if (x == null) {
            return;
        }
        this.x = x;
    }
    
    protected a d() {
        try {
            final Uri parse = Uri.parse(this.x);
            if (parse == null) {
                return com.tencent.liteav.e.a.a;
            }
            final String host = parse.getHost();
            if (TextUtils.isEmpty((CharSequence)host)) {
                return com.tencent.liteav.e.a.a;
            }
            final String scheme = parse.getScheme();
            if (scheme == null) {
                return com.tencent.liteav.e.a.a;
            }
            if (!scheme.equals("rtmp") && !scheme.equals("http") && !scheme.equals("https")) {
                return com.tencent.liteav.e.a.a;
            }
            if (c(host)) {
                return com.tencent.liteav.e.a.b;
            }
            final Set queryParameterNames = parse.getQueryParameterNames();
            if (queryParameterNames != null && (queryParameterNames.contains("bizid") || queryParameterNames.contains("txTime") || queryParameterNames.contains("txSecret"))) {
                return com.tencent.liteav.e.a.b;
            }
            if (com.tencent.liteav.e.y.containsKey(host)) {
                return com.tencent.liteav.e.y.get(host);
            }
            com.tencent.liteav.e.y.put(host, com.tencent.liteav.e.a.a);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final com.tencent.liteav.network.a.e[] a = com.tencent.liteav.network.a.a.a.c().a(new b(host, true), null);
                        boolean b = false;
                        for (final com.tencent.liteav.network.a.e e : a) {
                            if (e.a() && com.tencent.liteav.e.c(e.a)) {
                                b = true;
                                break;
                            }
                        }
                        com.tencent.liteav.e.y.put(host, b ? com.tencent.liteav.e.a.b : com.tencent.liteav.e.a.c);
                        TXCLog.d(com.tencent.liteav.e.a, host + " isTencent " + b);
                    }
                    catch (Exception ex) {
                        TXCLog.e(com.tencent.liteav.e.a, "check dns failed.", ex);
                    }
                }
            }).start();
        }
        catch (Exception ex) {
            TXCLog.e(com.tencent.liteav.e.a, "check stream failed.", ex);
        }
        return com.tencent.liteav.e.a.a;
    }
    
    protected static boolean c(final String s) {
        return (s != null && s.contains("myqcloud")) || com.tencent.liteav.basic.d.b.a().a(s);
    }
    
    public void d(final String z) {
        this.z = z;
    }
    
    public void e(final String a) {
        this.A = a;
    }
    
    public void a(final int d, final int e) {
        this.d = d;
        this.e = e;
    }
    
    public void a(final int f) {
        this.f = f;
    }
    
    public void b(final int g) {
        this.g = g;
    }
    
    public void e() {
        if (!this.n && !TextUtils.isEmpty((CharSequence)TXCStatus.b(this.z, 7012))) {
            this.l();
            this.n = true;
        }
        if (this.o <= 0L) {
            this.o = TXCTimeUtil.getTimeTick();
        }
        if (this.n && TXCTimeUtil.getTimeTick() - this.o > 5000L) {
            this.n();
            this.o = TXCTimeUtil.getTimeTick();
        }
    }
    
    private void i() {
        this.a(6002, 6017, 6018);
        TXCStatus.a(this.z, 9001, (Object)com.tencent.liteav.basic.util.f.a()[0]);
        this.a(9001, 9002, 9003);
        TXCStatus.a(this.z, 9004, (Object)com.tencent.liteav.basic.util.f.b());
        this.a(9004, 9005, 9006);
    }
    
    public void a(final int n, final int n2, final int n3) {
        if (n == 6002) {
            final double d = TXCStatus.d(this.z, n);
            if (d < 0.001) {
                return;
            }
            final double d2 = TXCStatus.d(this.z, n2);
            final int n4 = TXCStatus.c(this.z, n3) + 1;
            TXCStatus.a(this.z, n2, d2 + (d - d2) / n4);
            TXCStatus.a(this.z, n3, (Object)n4);
        }
        else {
            final int c = TXCStatus.c(this.z, n);
            if (c < 0.001) {
                return;
            }
            final double d3 = TXCStatus.d(this.z, n2);
            final int n5 = TXCStatus.c(this.z, n3) + 1;
            TXCStatus.a(this.z, n2, d3 + (c - d3) / n5);
            TXCStatus.a(this.z, n3, (Object)n5);
        }
    }
    
    public void f() {
        this.i();
        if (!this.n) {
            final long a = TXCStatus.a(this.z, 6001);
            final long a2 = TXCStatus.a(this.z, 7104);
            final long a3 = TXCStatus.a(this.z, 2033);
            final long a4 = TXCStatus.a(this.z, 7108);
            if (a > 0L && a2 > 0L && a4 > 0L && a3 > 0L) {
                this.c(this.q ? com.tencent.liteav.basic.datareport.a.X : com.tencent.liteav.basic.datareport.a.U);
                this.p = 5000;
                this.n = true;
            }
            final String b = TXCStatus.b(this.z, 7119);
            if (b != null) {
                this.b(b);
            }
        }
        if (this.w >= 3 && !this.n) {
            this.c(this.q ? com.tencent.liteav.basic.datareport.a.X : com.tencent.liteav.basic.datareport.a.U);
            this.p = 5000;
            this.n = true;
        }
        ++this.w;
        if (this.o <= 0L) {
            this.o = TXCTimeUtil.getTimeTick();
        }
        if (TXCTimeUtil.getTimeTick() > this.o + this.p) {
            if (this.q) {
                this.e(com.tencent.liteav.basic.datareport.a.Y);
                this.p = 5000;
            }
            else {
                if (this.d() == com.tencent.liteav.e.a.c) {
                    return;
                }
                this.e(com.tencent.liteav.basic.datareport.a.V);
                this.p = TXCDRApi.getStatusReportInterval();
                if (this.p < 5000) {
                    this.p = 5000;
                }
                if (this.p > 300000) {
                    this.p = 300000;
                }
            }
            this.k = TXCStatus.a(this.z, 6004);
            this.m = TXCStatus.c(this.z, 2002);
            this.o = TXCTimeUtil.getTimeTick();
        }
    }
    
    private void c(final int n) {
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.url = this.c;
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = false;
        final String s = this.b.get("token");
        TXCDRApi.InitEvent(this.h, s, n, com.tencent.liteav.basic.datareport.a.am, txcdrExtInfo);
        this.a(n, s);
        TXCDRApi.txSetEventIntValue(s, n, "u64_timestamp", this.b.get("u64_timestamp"));
        final long a = TXCStatus.a(this.z, 7107);
        long a2 = TXCStatus.a(this.z, 7108);
        if (a2 != -1L) {
            a2 -= a;
        }
        TXCDRApi.txSetEventIntValue(s, n, "u32_dns_time", (a2 < 0L) ? -1L : a2);
        long a3 = TXCStatus.a(this.z, 7109);
        if (a3 != -1L) {
            a3 -= a;
        }
        TXCDRApi.txSetEventIntValue(s, n, "u32_connect_server_time", (a3 < 0L) ? -1L : a3);
        final int c = TXCStatus.c(this.z, 5004);
        TXCDRApi.txSetEventIntValue(s, n, "u32_video_decode_type", c);
        this.j = TXCStatus.a(this.z, 6001) - a;
        TXCDRApi.txSetEventIntValue(s, n, "u32_first_i_frame", (this.j < 0L) ? -1L : this.j);
        final long n2 = TXCStatus.a(this.z, 7103) - a;
        TXCDRApi.txSetEventIntValue(s, n, "u32_first_frame_down", (n2 < 0L) ? -1L : n2);
        final long n3 = TXCStatus.a(this.z, 5005) - a;
        TXCDRApi.txSetEventIntValue(s, n, "u32_first_video_decode_time", (n3 < 0L) ? -1L : n3);
        final long n4 = TXCStatus.a(this.z, 7104) - a;
        TXCDRApi.txSetEventIntValue(s, n, "u32_first_audio_frame_down", (n4 < 0L) ? -1L : n4);
        final long n5 = TXCStatus.a(this.z, 2033) - a;
        TXCDRApi.txSetEventIntValue(s, n, "u32_first_audio_render_time", (n5 < 0L) ? -1L : n5);
        final int c2 = TXCStatus.c(this.z, 7105);
        TXCDRApi.txSetEventIntValue(s, n, "u64_err_code", c2);
        final int c3 = TXCStatus.c(this.z, 7106);
        TXCDRApi.txSetEventIntValue(s, n, "str_err_info", c3);
        final int c4 = TXCStatus.c(this.z, 7112);
        TXCDRApi.txSetEventValue(s, n, "u32_link_type", String.valueOf(c4));
        final int c5 = TXCStatus.c(this.z, 7111);
        TXCDRApi.txSetEventValue(s, n, "u32_channel_type", String.valueOf(c5));
        TXCDRApi.txSetEventValue(s, n, "str_app_version", this.i);
        TXCDRApi.nativeReportEvent(s, n);
        TXCLog.d(com.tencent.liteav.e.a, "report evt " + n + ": token=" + s + "\n" + "str_user_id" + "=" + this.b.get("str_user_id") + "\n" + "dev_uuid" + "=" + this.b.get("dev_uuid") + "\n" + "str_session_id" + "=" + this.b.get("str_session_id") + "\n" + "str_device_type" + "=" + this.b.get("str_device_type") + "\n" + "str_os_info" + "=" + this.b.get("str_os_info") + "\n" + "str_package_name" + "=" + this.b.get("str_package_name") + "\n" + "u32_network_type" + "=" + this.b.get("u32_network_type") + "\n" + "u32_server_ip" + "=" + this.b.get("u32_server_ip") + "\n" + "str_stream_url" + "=" + this.b.get("str_stream_url") + "\n" + "u64_timestamp" + "=" + this.b.get("u64_timestamp") + "\n" + "u32_dns_time" + "=" + a2 + "\n" + "u32_connect_server_time" + "=" + a3 + "\n" + "u32_video_decode_type" + "=" + c + "\n" + "u32_first_frame_down" + "=" + n2 + "\n" + "u32_first_video_decode_time" + "=" + n3 + "\n" + "u32_first_i_frame" + "=" + this.j + "\n" + "u32_first_audio_frame_down" + "=" + n4 + "\n" + "u32_first_audio_render_time" + "=" + n5 + "\n" + "u64_err_code" + "=" + c2 + "\n" + "str_err_info" + "=" + c3 + "\n" + "u32_link_type" + "=" + c4 + "\n" + "u32_channel_type" + "=" + c5 + "\n" + "str_app_version" + "=" + this.i);
    }
    
    private void f(final String s) {
        if (!TextUtils.isEmpty((CharSequence)s) && s.startsWith("room://")) {
            final String[] split = s.split("/");
            split[split.length - 1].split("_");
            TXCStatus.a(this.z, 7112, 3L);
        }
    }
    
    private void d(final int n) {
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.url = this.c;
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = false;
        final String s = this.b.get("token");
        TXCDRApi.InitEvent(this.h, s, n, com.tencent.liteav.basic.datareport.a.am, txcdrExtInfo);
        this.a(n, s);
        final long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(s, n, "u64_end_timestamp", utcTimeTick);
        TXCDRApi.txSetEventIntValue(s, n, "u64_timestamp", utcTimeTick);
        final double d = TXCStatus.d(this.z, 9002);
        TXCDRApi.txSetEventValue(s, n, "u32_avg_cpu_usage", String.valueOf(d));
        final double d2 = TXCStatus.d(this.z, 9005);
        TXCDRApi.txSetEventValue(s, n, "u32_avg_memory", String.valueOf(d2));
        final String value = String.valueOf(this.v);
        TXCDRApi.txSetEventValue(s, n, "u64_begin_timestamp", value);
        final long n2 = (TXCTimeUtil.getTimeTick() - TXCStatus.a(this.z, 7107)) / 1000L;
        TXCDRApi.txSetEventIntValue(s, n, "u64_playtime", (n2 < 0L) ? -1L : n2);
        TXCDRApi.txSetEventIntValue(s, n, "u32_result", (n2 < 0L) ? -1L : n2);
        final int c = TXCStatus.c(this.z, 7105);
        TXCDRApi.txSetEventIntValue(s, n, "u64_err_code", c);
        final int c2 = TXCStatus.c(this.z, 2004);
        TXCDRApi.txSetEventIntValue(s, n, "u32_speed_cnt", c2);
        final int c3 = TXCStatus.c(this.z, 2008);
        TXCDRApi.txSetEventIntValue(s, n, "u64_audio_cache_avg", c3);
        TXCDRApi.txSetEventIntValue(s, n, "u32_avg_cache_time", c3);
        final long n3 = TXCStatus.c(this.z, 2003);
        TXCDRApi.txSetEventValue(s, n, "u32_max_load", String.valueOf(n3));
        final long n4 = TXCStatus.c(this.z, 2001);
        TXCDRApi.txSetEventValue(s, n, "u32_avg_load", String.valueOf(n4));
        final long n5 = TXCStatus.c(this.z, 2002);
        TXCDRApi.txSetEventValue(s, n, "u32_load_cnt", String.valueOf(n5));
        final int c4 = TXCStatus.c(this.z, 2005);
        TXCDRApi.txSetEventIntValue(s, n, "u32_nodata_cnt", c4);
        final long n6 = n4 * n5;
        TXCDRApi.txSetEventIntValue(s, n, "u32_audio_block_time", n6);
        TXCDRApi.txSetEventIntValue(s, n, "u32_first_i_frame", this.j);
        final int c5 = TXCStatus.c(this.z, 6015);
        TXCDRApi.txSetEventIntValue(s, n, "u32_video_width", c5);
        final int c6 = TXCStatus.c(this.z, 6016);
        TXCDRApi.txSetEventIntValue(s, n, "u32_video_height", c6);
        final double d3 = TXCStatus.d(this.z, 6017);
        TXCDRApi.txSetEventValue(s, n, "u32_video_avg_fps", String.valueOf(d3));
        final long a = TXCStatus.a(this.z, 6003);
        final long a2 = TXCStatus.a(this.z, 6005);
        final long a3 = TXCStatus.a(this.z, 6006);
        long n7 = 0L;
        if (a > 0L) {
            n7 = a3 / a;
        }
        TXCDRApi.txSetEventIntValue(s, n, "u64_block_duration_avg", n7);
        TXCDRApi.txSetEventIntValue(s, n, "u32_avg_block_time", n7);
        TXCDRApi.txSetEventIntValue(s, n, "u64_block_count", a);
        TXCDRApi.txSetEventIntValue(s, n, "u32_video_block_time", a3);
        TXCDRApi.txSetEventIntValue(s, n, "u64_block_duration_max", a2);
        final long a4 = TXCStatus.a(this.z, 6009);
        TXCDRApi.txSetEventIntValue(s, n, "u64_jitter_cache_max", a4);
        final long a5 = TXCStatus.a(this.z, 6008);
        TXCDRApi.txSetEventIntValue(s, n, "u64_jitter_cache_avg", a5);
        TXCDRApi.txSetEventValue(s, n, "u32_link_type", String.valueOf(TXCStatus.c(this.z, 7112)));
        final int c7 = TXCStatus.c(this.z, 7111);
        TXCDRApi.txSetEventValue(s, n, "u32_channel_type", String.valueOf(c7));
        final int c8 = TXCStatus.c(this.z, 7113);
        TXCDRApi.txSetEventValue(s, n, "u32_ip_count_quic", String.valueOf(c8));
        final int c9 = TXCStatus.c(this.z, 7114);
        TXCDRApi.txSetEventValue(s, n, "u32_connect_count_quic", String.valueOf(c9));
        final int c10 = TXCStatus.c(this.z, 7115);
        TXCDRApi.txSetEventValue(s, n, "u32_connect_count_tcp", String.valueOf(c10));
        TXCDRApi.txSetEventValue(s, n, "str_app_version", this.i);
        TXCDRApi.txSetEventIntValue(s, n, "u32_is_real_time", TXCStatus.a(this.z, 2009));
        TXCDRApi.nativeReportEvent(s, n);
        TXCLog.d(com.tencent.liteav.e.a, "report evt " + n + ": token=" + s + "\n" + "str_user_id" + "=" + this.b.get("str_user_id") + "\n" + "dev_uuid" + "=" + this.b.get("dev_uuid") + "\n" + "str_session_id" + "=" + this.b.get("str_session_id") + "\n" + "str_device_type" + "=" + this.b.get("str_device_type") + "\n" + "str_os_info" + "=" + this.b.get("str_os_info") + "\n" + "str_package_name" + "=" + this.b.get("str_package_name") + "\n" + "u32_network_type" + "=" + this.b.get("u32_network_type") + "\n" + "u32_server_ip" + "=" + this.b.get("u32_server_ip") + "\n" + "str_stream_url" + "=" + this.b.get("str_stream_url") + "\n" + "u64_timestamp" + "=" + this.b.get("u64_timestamp") + "\n" + "u32_avg_cpu_usage" + "=" + d + "\n" + "u32_avg_memory" + "=" + d2 + "\n" + "u32_first_i_frame" + "=" + this.j + "\n" + "u32_video_width" + "=" + c5 + "\n" + "u32_video_height" + "=" + c6 + "\n" + "u32_video_avg_fps" + "=" + d3 + "\n" + "u32_speed_cnt" + "=" + c2 + "\n" + "u32_nodata_cnt" + "=" + c4 + "\n" + "u32_avg_cache_time" + "=" + c3 + "\n" + "u32_avg_block_time" + "=" + n7 + "\n" + "u32_avg_load" + "=" + n4 + "\n" + "u32_max_load" + "=" + n3 + "\n" + "u32_video_block_time" + "=" + a3 + "\n" + "u32_audio_block_time" + "=" + n6 + "\n" + "u32_load_cnt" + "=" + n5 + "\n" + "u32_result" + "=" + n2 + "\n" + "u64_err_code" + "=" + c + "\n" + "u32_channel_type" + "=" + c7 + "\n" + "u32_ip_count_quic" + "=" + c8 + "\n" + "u32_connect_count_quic" + "=" + c9 + "\n" + "u32_connect_count_tcp" + "=" + c10 + "\n" + "u64_block_count" + "=" + a + "\n" + "u64_jitter_cache_max" + "=" + a4 + "\n" + "u64_jitter_cache_avg" + "=" + a5 + "\n" + "u64_begin_timestamp" + "=" + value + "\n" + "u32_is_real_time" + "=" + TXCStatus.a(this.z, 2009) + "\n" + "str_app_version" + "=" + this.i);
    }
    
    private void a(final int n, final String s) {
        String b = TXCStatus.b(this.z, 7121);
        if (TextUtils.isEmpty((CharSequence)b)) {
            b = s;
        }
        this.b.put("str_session_id", b);
        this.b.put("u32_server_ip", TXCStatus.b(this.z, 7110));
        if (this.q) {
            this.b.put("str_stream_url", TXCStatus.b(this.z, 7116));
            this.f(this.b.get("str_stream_url"));
        }
        else {
            this.b.put("str_stream_url", this.c);
        }
        TXCDRApi.txSetEventValue(s, n, "str_user_id", this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(s, n, "dev_uuid", this.b.get("dev_uuid"));
        TXCDRApi.txSetEventValue(s, n, "str_session_id", this.b.get("str_session_id"));
        TXCDRApi.txSetEventValue(s, n, "str_device_type", this.b.get("str_device_type"));
        TXCDRApi.txSetEventValue(s, n, "str_os_info", this.b.get("str_os_info"));
        TXCDRApi.txSetEventValue(s, n, "str_package_name", this.b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(s, n, "u32_network_type", this.g("u32_network_type"));
        TXCDRApi.txSetEventValue(s, n, "u32_server_ip", this.b.get("u32_server_ip"));
        TXCDRApi.txSetEventValue(s, n, "str_stream_url", this.b.get("str_stream_url"));
    }
    
    private void e(final int n) {
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.url = this.c;
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = true;
        final String s = this.b.get("token");
        TXCDRApi.InitEvent(this.h, s, n, com.tencent.liteav.basic.datareport.a.am, txcdrExtInfo);
        this.a(n, s);
        TXCDRApi.txSetEventIntValue(s, n, "u64_timestamp", TXCTimeUtil.getUtcTimeTick());
        final int[] a = com.tencent.liteav.basic.util.f.a();
        TXCDRApi.txSetEventIntValue(s, n, "u32_cpu_usage", a[1]);
        TXCDRApi.txSetEventIntValue(s, n, "u32_app_cpu_usage", a[0]);
        TXCDRApi.txSetEventValue(s, n, "u32_avg_cpu_usage", String.valueOf(TXCStatus.d(this.z, 9002)));
        TXCDRApi.txSetEventValue(s, n, "u32_avg_memory", String.valueOf(TXCStatus.d(this.z, 9005)));
        TXCDRApi.txSetEventIntValue(s, n, "u32_recv_av_diff_time", TXCStatus.a(this.z, 6014, 2));
        TXCDRApi.txSetEventIntValue(s, n, "u32_play_av_diff_time", TXCStatus.a(this.z, 6013, 2));
        TXCDRApi.txSetEventValue(s, n, "u64_playtime", String.valueOf((TXCTimeUtil.getUtcTimeTick() - this.v) / 1000L));
        int n2 = 1;
        if (TXCStatus.c(this.z, 2015) == 0) {
            n2 = 2;
        }
        TXCDRApi.txSetEventIntValue(s, n, "u32_audio_decode_type", n2);
        final long a2 = TXCStatus.a(this.z, 2002);
        if (this.m == -1L) {
            TXCDRApi.txSetEventIntValue(s, n, "u32_audio_block_count", 0L);
        }
        else if (a2 >= this.m) {
            TXCDRApi.txSetEventIntValue(s, n, "u32_audio_block_count", a2 - this.m);
        }
        else {
            TXCDRApi.txSetEventIntValue(s, n, "u32_audio_block_count", -1L);
        }
        this.m = a2;
        TXCDRApi.txSetEventIntValue(s, n, "u32_audio_cache_time", TXCStatus.c(this.z, 2010));
        TXCDRApi.txSetEventIntValue(s, n, "u32_audio_drop", TXCStatus.c(this.z, 2014));
        TXCDRApi.txSetEventIntValue(s, n, "u32_video_decode_type", TXCStatus.c(this.z, 5004));
        final int c = TXCStatus.c(this.z, 6019);
        if (c >= this.l) {
            TXCDRApi.txSetEventIntValue(s, n, "u32_video_recv_fps", (c - this.l) / 2L);
        }
        this.l = c;
        TXCDRApi.txSetEventIntValue(s, n, "u32_fps", (int)TXCStatus.d(this.z, 6002));
        TXCDRApi.txSetEventIntValue(s, n, "u32_video_cache_time", TXCStatus.c(this.z, 6007));
        final long a3 = TXCStatus.a(this.z, 6008);
        TXCDRApi.txSetEventIntValue(s, n, "u32_video_cache_count", a3);
        TXCDRApi.txSetEventIntValue(s, n, "u32_avg_cache_count", a3);
        final long a4 = TXCStatus.a(this.z, 6004);
        if (this.k == -1L) {
            TXCDRApi.txSetEventIntValue(s, n, "u32_video_block_count", 0L);
        }
        else if (a4 >= this.k) {
            TXCDRApi.txSetEventIntValue(s, n, "u32_video_block_count", a4 - this.k);
        }
        else {
            TXCDRApi.txSetEventIntValue(s, n, "u32_video_block_count", 0L);
        }
        this.k = a4;
        final int n3 = TXCStatus.c(this.z, 7102) + TXCStatus.c(this.z, 7101);
        TXCDRApi.txSetEventIntValue(s, n, "u32_net_speed", n3);
        TXCDRApi.txSetEventIntValue(s, n, "u32_avg_net_speed", n3);
        TXCDRApi.txSetEventValue(s, n, "u32_link_type", String.valueOf(TXCStatus.c(this.z, 7112)));
        TXCDRApi.txSetEventValue(s, n, "u32_channel_type", String.valueOf(TXCStatus.c(this.z, 7111)));
        TXCDRApi.txSetEventValue(s, n, "str_app_version", this.i);
        TXCDRApi.nativeReportEvent(s, n);
        if (this.q) {
            ++this.u;
            this.t += a3;
            if (a3 > this.s) {
                this.s = a3;
            }
        }
    }
    
    private void j() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final String b = TXCStatus.b(this.z, 7116);
        final String b2 = TXCStatus.b(this.z, 7117);
        final String b3 = TXCStatus.b(this.z, 7118);
        final int c = TXCStatus.c(this.z, 7105);
        final String b4 = TXCStatus.b(this.z, 7106);
        final int c2 = TXCStatus.c(this.z, 7111);
        hashMap.put("stream_url", b);
        hashMap.put("stream_id", b2);
        hashMap.put("bizid", b3);
        hashMap.put("err_code", String.valueOf(c));
        hashMap.put("err_info", b4);
        hashMap.put("channel_type", String.valueOf(c2));
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - this.r;
        hashMap.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(this.r)));
        hashMap.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(currentTimeMillis)));
        hashMap.put("total_time", String.valueOf(n));
        final long a = TXCStatus.a(this.z, 6003);
        final long a2 = TXCStatus.a(this.z, 6006);
        final long a3 = TXCStatus.a(this.z, 6005);
        long n2 = 0L;
        if (a != 0L) {
            n2 = a2 / a;
        }
        hashMap.put("block_count", String.valueOf(a));
        hashMap.put("block_duration_max", String.valueOf(a3));
        hashMap.put("block_duration_avg", String.valueOf(n2));
        long n3 = 0L;
        if (this.u != 0L) {
            n3 = this.t / this.u;
        }
        hashMap.put("jitter_cache_max", String.valueOf(this.s));
        hashMap.put("jitter_cache_avg", String.valueOf(n3));
        final String txCreateToken = TXCDRApi.txCreateToken();
        final int af = com.tencent.liteav.basic.datareport.a.af;
        final int al = com.tencent.liteav.basic.datareport.a.al;
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.command_id_comment = "LINKMIC";
        TXCDRApi.InitEvent(this.h, txCreateToken, af, al, txcdrExtInfo);
        TXCLog.d(com.tencent.liteav.e.a, "report evt 40402: token=" + txCreateToken);
        for (final Map.Entry<Object, Object> entry : hashMap.entrySet()) {
            final String s = entry.getKey();
            final String s2 = entry.getValue();
            TXCLog.e(com.tencent.liteav.e.a, "RealTimePlayStatisticInfo: " + s + " = " + s2);
            if (s != null && s.length() > 0 && s2 != null) {
                TXCDRApi.txSetEventValue(txCreateToken, af, s, s2);
            }
        }
        TXCDRApi.nativeReportEvent(txCreateToken, af);
        this.q = false;
        this.r = 0L;
        this.u = 0L;
        this.t = 0L;
        this.s = 0L;
    }
    
    private void k() {
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = false;
        txcdrExtInfo.url = this.c;
        final long a = TXCStatus.a(this.z, 7013);
        final String s = this.b.get("token");
        TXCDRApi.InitEvent(this.h, s, com.tencent.liteav.basic.datareport.a.P, com.tencent.liteav.basic.datareport.a.al, txcdrExtInfo);
        final long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u64_timestamp", TXCTimeUtil.getUtcTimeTick());
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_device_type", this.b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_network_type", this.g("u32_network_type"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_dns_time", -1L);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_connect_server_time", -1L);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_server_ip", "");
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_video_resolution", this.d << 16 | this.e);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_audio_samplerate", this.g);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_video_bitrate", this.f);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_user_id", this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_package_name", this.b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_channel_type", a);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_app_version", this.i);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "dev_uuid", this.b.get("dev_uuid"));
        TXCDRApi.nativeReportEvent(s, com.tencent.liteav.basic.datareport.a.P);
        TXCLog.d(com.tencent.liteav.e.a, "report evt 40001: token=" + s + " " + "str_stream_url" + "=" + this.c + " " + "u64_timestamp" + "=" + utcTimeTick + " " + "str_device_type" + "=" + this.b.get("str_device_type") + " " + "u32_network_type" + "=" + this.g("u32_network_type") + " " + "u32_dns_time" + "=-1 " + "u32_connect_server_time" + "=-1 " + "u32_server_ip" + "= " + "u32_video_resolution" + "=" + (this.d << 16 | this.e) + " " + "u32_audio_samplerate" + "=" + this.g + " " + "u32_video_bitrate" + "=" + this.f + " " + "str_user_id" + "=" + this.b.get("str_user_id") + " " + "str_package_name" + "=" + this.b.get("str_package_name") + " " + "u32_channel_type" + "=" + a + " " + "str_app_version" + "=" + this.b.get("dev_uuid") + " " + "dev_uuid" + "=" + this.g("u32_max_load"));
    }
    
    private void l() {
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = false;
        txcdrExtInfo.url = this.c;
        final String b = TXCStatus.b(this.z, 7012);
        final long a = TXCStatus.a(this.z, 7009);
        long a2 = TXCStatus.a(this.z, 7010);
        if (a2 != -1L) {
            a2 -= a;
        }
        long a3 = TXCStatus.a(this.z, 7011);
        if (a3 != -1L) {
            a3 -= a;
        }
        final long a4 = TXCStatus.a(this.z, 7013);
        final String s = this.b.get("token");
        TXCDRApi.InitEvent(this.h, s, com.tencent.liteav.basic.datareport.a.P, com.tencent.liteav.basic.datareport.a.al, txcdrExtInfo);
        final long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u64_timestamp", utcTimeTick);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_device_type", this.b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_network_type", this.g("u32_network_type"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_dns_time", a2);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_connect_server_time", a3);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_server_ip", b);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_video_resolution", this.d << 16 | this.e);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_audio_samplerate", this.g);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_video_bitrate", this.f);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_user_id", this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_package_name", this.b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.P, "u32_channel_type", a4);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_app_version", this.i);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "dev_uuid", this.b.get("dev_uuid"));
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.P, "str_nearest_ip_list", TXCStatus.b(this.z, 7019));
        TXCDRApi.nativeReportEvent(s, com.tencent.liteav.basic.datareport.a.P);
        TXCLog.d(com.tencent.liteav.e.a, "report evt 40001: token=" + s + " " + "str_stream_url" + "=" + this.c + " " + "u64_timestamp" + "=" + utcTimeTick + " " + "str_device_type" + "=" + this.b.get("str_device_type") + " " + "u32_network_type" + "=" + this.g("u32_network_type") + " " + "u32_dns_time" + "=" + a2 + " " + "u32_connect_server_time" + "=" + a3 + " " + "u32_server_ip" + "=" + b + " " + "u32_video_resolution" + "=" + (this.d << 16 | this.e) + " " + "u32_audio_samplerate" + "=" + this.g + " " + "u32_video_bitrate" + "=" + this.f + " " + "str_user_id" + "=" + this.b.get("str_user_id") + " " + "str_package_name" + "=" + this.b.get("str_package_name") + " " + "u32_channel_type" + "=" + a4 + " " + "str_app_version" + "=" + this.b.get("dev_uuid") + " " + "dev_uuid" + "=" + this.g("u32_max_load"));
    }
    
    private void m() {
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = false;
        txcdrExtInfo.url = this.c;
        final long a = TXCStatus.a(this.z, 7009);
        final long a2 = TXCStatus.a(this.z, 7013);
        final String s = this.b.get("token");
        TXCDRApi.InitEvent(this.h, s, com.tencent.liteav.basic.datareport.a.R, com.tencent.liteav.basic.datareport.a.al, txcdrExtInfo);
        final long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.R, "u64_timestamp", utcTimeTick);
        final long n = (TXCTimeUtil.getTimeTick() - a) / 1000L;
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.R, "u32_result", n);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.R, "str_user_id", this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.R, "str_package_name", this.b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.R, "u32_channel_type", a2);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.R, "str_app_version", this.i);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.R, "dev_uuid", this.b.get("dev_uuid"));
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.R, "u32_ip_count_quic", String.valueOf(TXCStatus.c(this.z, 7016)));
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.R, "u32_connect_count_quic", String.valueOf(TXCStatus.c(this.z, 7017)));
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.R, "u32_connect_count_tcp", String.valueOf(TXCStatus.c(this.z, 7018)));
        TXCDRApi.nativeReportEvent(s, com.tencent.liteav.basic.datareport.a.R);
        TXCLog.d(com.tencent.liteav.e.a, "report evt 40002: token=" + s + " " + "str_stream_url" + "=" + this.c + " " + "u64_timestamp" + "=" + utcTimeTick + " " + "u32_result" + "=" + n + " " + "str_user_id" + "=" + this.b.get("str_user_id") + " " + "str_package_name" + "=" + this.b.get("str_package_name") + " " + "u32_channel_type" + "=" + a2 + " " + "str_app_version" + "=" + this.i + " " + "dev_uuid" + "=" + this.b.get("dev_uuid"));
    }
    
    private void n() {
        final TXCDRExtInfo txcdrExtInfo = new TXCDRExtInfo();
        txcdrExtInfo.report_common = false;
        txcdrExtInfo.report_status = true;
        txcdrExtInfo.url = this.c;
        final int[] a = com.tencent.liteav.basic.util.f.a();
        final int n = a[0];
        final int n2 = a[1];
        final int b = com.tencent.liteav.basic.util.f.b();
        final long a2 = TXCStatus.a(this.z, 7013);
        final int c = TXCStatus.c(this.z, 7004);
        final int c2 = TXCStatus.c(this.z, 7003);
        final double d = TXCStatus.d(this.z, 4001);
        final int c3 = TXCStatus.c(this.z, 7005);
        final int c4 = TXCStatus.c(this.z, 7002);
        final int c5 = TXCStatus.c(this.z, 7001);
        final int c6 = TXCStatus.c(this.z, 4007);
        final String b2 = TXCStatus.b(this.z, 7012);
        final String b3 = TXCStatus.b(this.z, 7014);
        final String b4 = TXCStatus.b(this.z, 7015);
        final String b5 = TXCStatus.b(this.z, 3001);
        final long a3 = TXCStatus.a(this.z, 3002);
        final double d2 = TXCStatus.d(this.z, 3003);
        final int c7 = TXCStatus.c(this.z, 7020);
        final String s = this.b.get("token");
        TXCDRApi.InitEvent(this.h, s, com.tencent.liteav.basic.datareport.a.Q, com.tencent.liteav.basic.datareport.a.al, txcdrExtInfo);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_avg_audio_bitrate", c4);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_avg_video_bitrate", c5);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_avg_net_speed", c2 + c);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_fps", (int)d);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_avg_cache_size", c3);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_cpu_usage", n2);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_app_cpu_usage", n);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_avg_memory", b);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_channel_type", a2);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.Q, "str_app_version", this.i);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.Q, "str_device_type", this.b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_hw_enc", c6);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.Q, "str_server_ip", b2);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.Q, "str_quic_connection_id", b3);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.Q, "str_quic_connection_stats", b4);
        TXCDRApi.txSetEventValue(s, com.tencent.liteav.basic.datareport.a.Q, "str_beauty_stats", b5);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_send_strategy", c7);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_preprocess_timecost", a3);
        TXCDRApi.txSetEventIntValue(s, com.tencent.liteav.basic.datareport.a.Q, "u32_preprocess_fps_out", (int)d2);
        TXCDRApi.nativeReportEvent(s, com.tencent.liteav.basic.datareport.a.Q);
    }
    
    private int g(final String s) {
        final Number n = this.b.get(s);
        if (n != null) {
            return n.intValue();
        }
        return 0;
    }
    
    private void o() {
        this.n = false;
        this.o = 0L;
        this.w = 0;
        String s = this.A;
        if (TextUtils.isEmpty((CharSequence)s)) {
            s = TXCCommonUtil.getUserId();
        }
        if (TextUtils.isEmpty((CharSequence)s)) {
            s = com.tencent.liteav.basic.util.f.b(this.h);
        }
        this.b.put("str_user_id", s);
        this.b.put("str_device_type", com.tencent.liteav.basic.util.f.c());
        this.b.put("str_device_type", com.tencent.liteav.basic.util.f.c());
        this.b.put("u32_network_type", com.tencent.liteav.basic.util.f.e(this.h));
        this.b.put("token", com.tencent.liteav.basic.util.f.e());
        this.b.put("str_package_name", com.tencent.liteav.basic.util.f.c(this.h));
        this.b.put("dev_uuid", com.tencent.liteav.basic.util.f.f(this.h));
        this.b.put("str_os_info", com.tencent.liteav.basic.util.f.d());
        this.v = TXCTimeUtil.getUtcTimeTick();
        this.b.put("u64_timestamp", this.v);
    }
    
    static {
        e.a = "TXCDataReport";
        e.y = new HashMap<String, a>();
    }
    
    enum a
    {
        a, 
        b, 
        c;
    }
}
