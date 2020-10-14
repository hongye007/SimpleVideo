package com.tencent.avroom;

import android.content.*;
import java.lang.ref.*;
import com.tencent.liteav.avprotocol.*;
import java.util.*;
import com.tencent.liteav.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;
import com.tencent.liteav.basic.module.*;

import android.util.*;

public class b extends AAAA
{
    private Context a;
    private long b;
    private Handler c;
    private static String d;
    private e e;
    private TXCAVRoomLisenter f;
    private TimerTask g;
    private Timer h;
    private WeakReference<CaptureAndEnc> i;
    private WeakReference<TXCAVProtocol> j;
    private HashMap k;
    private HashMap l;
    private HashMap m;
    
    public b(final int n, final long b, final Context context, final i i) {
        this.c = new Handler(Looper.getMainLooper());
        this.e = null;
        this.g = null;
        this.i = null;
        this.k = new HashMap(100);
        this.l = new HashMap(100);
        this.m = new HashMap(100);
        this.a = context.getApplicationContext();
        this.b = b;
        this.setID("" + b);
        (this.e = new e(this.a)).a(i.c);
        this.e.b(i.s);
        this.e.a(i.a, i.b);
        this.e.d("" + b);
        this.e.a("rtmp://0.livepush.myqcloud.com/live/" + ("" + n + "_" + b));
        TXCLog.w(com.tencent.avroom.b.d, "stream_id = " + b);
    }
    
    public void a(final TXCAVRoomLisenter f) {
        this.f = f;
    }
    
    public void a(final CaptureAndEnc d) {
        this.i = new WeakReference<CaptureAndEnc>(d);
    }
    
    public void a(final TXCAVProtocol txcavProtocol) {
        this.j = new WeakReference<TXCAVProtocol>(txcavProtocol);
    }
    
    private Bundle d() {
        final Bundle bundle = new Bundle();
        final String string = this.k.get("u32_app_cpu_usage").intValue() / 10 + "/" + this.k.get("u32_cpu_usage").intValue() / 10 + "%";
        bundle.putLong("myid", this.b);
        bundle.putCharSequence("CPU_USAGE", (CharSequence)string);
        bundle.putInt("VIDEO_WIDTH", this.a("VIDEO_WIDTH"));
        bundle.putInt("VIDEO_HEIGHT", this.a("VIDEO_HEIGHT"));
        bundle.putInt("NET_SPEED", this.a("u32_avg_net_speed"));
        bundle.putInt("VIDEO_FPS", this.a("u32_fps"));
        bundle.putInt("VIDEO_DROP", this.a("video_drop"));
        bundle.putInt("VIDEO_BITRATE", this.a("u32_avg_video_bitrate"));
        bundle.putInt("AUDIO_BITRATE", this.a("u32_avg_audio_bitrate"));
        bundle.putInt("AUDIO_CACHE", this.a("u32_avg_cache_size"));
        bundle.putString("AUDIO_PLAY_INFO", (String)this.k.get("AUDIO_INFO"));
        bundle.putCharSequence("SERVER_IP", (CharSequence)this.k.get("u32_server_ip"));
        bundle.putInt("qos_video_bitrate", this.a("qos_video_bitrate"));
        int a = this.a("u32_fps");
        if (a == 0) {
            a = 15;
        }
        bundle.putInt("VIDEO_GOP", (int)(this.a("VIDEO_GOP") * 10 / a / 10.0f + 0.5));
        return bundle;
    }
    
    private int a(final String s) {
        final Number n = this.k.get(s);
        if (n != null) {
            return n.intValue();
        }
        return 0;
    }
    
    public void a() {
        if (null != this.g) {
            this.g.cancel();
        }
        this.e.a();
        this.h = new Timer(true);
        this.g = new TimerTask() {
            @Override
            public void run() {
                if (com.tencent.avroom.b.this.j != null) {
                    final TXCAVProtocol txcavProtocol = (TXCAVProtocol)com.tencent.avroom.b.this.j.get();
                    if (txcavProtocol != null) {
                        final TXCAVProtocol.UploadStats uploadStats = txcavProtocol.getUploadStats();
                        com.tencent.avroom.b.this.a(uploadStats, 2000L);
                        com.tencent.avroom.b.this.setStatusValue(7012, String.valueOf(uploadStats.serverIP));
                    }
                }
                if (com.tencent.avroom.b.this.i != null) {
                    final CaptureAndEnc d = (CaptureAndEnc)com.tencent.avroom.b.this.i.get();
                    if (d != null) {
                        com.tencent.avroom.b.this.k.put("VIDEO_WIDTH", (long)d.c());
                        com.tencent.avroom.b.this.k.put("VIDEO_HEIGHT", (long)d.d());
                    }
                }
                if (com.tencent.avroom.b.this.i != null && com.tencent.avroom.b.this.i.get() != null) {
                    com.tencent.avroom.b.this.k.put("VIDEO_GOP", TXCStatus.c(((CaptureAndEnc)com.tencent.avroom.b.this.i.get()).getID(), 4006));
                }
                final int[] a = com.tencent.liteav.basic.util.f.a();
                com.tencent.avroom.b.this.k.put("u32_app_cpu_usage", (long)a[0]);
                com.tencent.avroom.b.this.k.put("u32_cpu_usage", (long)a[1]);
                com.tencent.avroom.b.this.k.put("u32_fps", (long)TXCStatus.d(com.tencent.avroom.b.this.getID(), 4001));
                Log.i(com.tencent.avroom.b.d, "run: kAvgVideoBitrate" + (Object)(long)TXCStatus.c(com.tencent.avroom.b.this.getID(), 4002));
                com.tencent.avroom.b.this.c.post((Runnable)new Runnable() {
                    final /* synthetic */ Bundle a = com.tencent.avroom.b.this.d();
                    
                    @Override
                    public void run() {
                        if (null != com.tencent.avroom.b.this.f) {
                            com.tencent.avroom.b.this.f.onAVRoomStatus(com.tencent.avroom.b.this.b, this.a);
                        }
                    }
                });
                com.tencent.avroom.b.this.setStatusValue(7002, com.tencent.avroom.b.this.k.get("u32_avg_audio_bitrate"));
                com.tencent.avroom.b.this.setStatusValue(7001, com.tencent.avroom.b.this.k.get("u32_avg_video_bitrate"));
                com.tencent.avroom.b.this.setStatusValue(7004, com.tencent.avroom.b.this.k.get("u32_avg_net_speed_audio"));
                com.tencent.avroom.b.this.setStatusValue(7003, com.tencent.avroom.b.this.k.get("u32_avg_net_speed_video"));
                com.tencent.avroom.b.this.setStatusValue(7005, com.tencent.avroom.b.this.k.get("u32_avg_cache_size"));
                com.tencent.avroom.b.this.setStatusValue(7007, com.tencent.avroom.b.this.k.get("video_drop"));
                com.tencent.avroom.b.this.setStatusValue(7007, com.tencent.avroom.b.this.k.get("video_drop"));
                if (com.tencent.avroom.b.this.e != null) {
                    com.tencent.avroom.b.this.e.e();
                }
            }
        };
        this.h.schedule(this.g, 0L, 2000L);
    }
    
    public void b() {
        if (null != this.h) {
            this.h.cancel();
            this.h = null;
        }
        if (this.e != null) {
            this.e.b();
        }
    }
    
    private void a(final TXCAVProtocol.UploadStats uploadStats, final long n) {
        if (uploadStats == null || n == 0L) {
            return;
        }
        this.k.put("u32_avg_video_bitrate", this.a("u32_avg_video_bitrate", uploadStats.inVideoBytes) * 8L * 1000L / (n * 1024L));
        this.k.put("u32_avg_audio_bitrate", this.a("u32_avg_audio_bitrate", uploadStats.inAudioBytes) * 8L * 1000L / (n * 1024L));
        final long a = this.a("VIDEO_BITRATE", uploadStats.outVideoBytes);
        final long a2 = this.a("AUDIO_BITRATE", uploadStats.outAudioBytes);
        this.k.put("u32_avg_net_speed_video", a * 8L * 1000L / (n * 1024L));
        this.k.put("u32_avg_net_speed_audio", a2 * 8L * 1000L / (n * 1024L));
        this.k.put("u32_avg_net_speed", (a2 + a) * 8L * 1000L / (n * 1024L));
        this.k.put("u32_avg_cache_size", uploadStats.videoCacheLen);
        this.k.put("video_drop", uploadStats.videoDropCount);
        this.k.put("u32_server_ip", uploadStats.serverIP);
        if (uploadStats.dnsTS != -1L) {
            this.k.put("u32_dns_time", uploadStats.dnsTS - uploadStats.startTS);
        }
        else {
            this.k.put("u32_dns_time", uploadStats.dnsTS);
        }
        if (uploadStats.connTS != -1L) {
            this.k.put("u32_connect_server_time", uploadStats.connTS - uploadStats.startTS);
        }
        else {
            this.k.put("u32_connect_server_time", uploadStats.connTS);
        }
        this.k.put("u32_channel_type", uploadStats.channelType);
        this.k.put("u64_timestamp", uploadStats.startTS);
    }
    
    private long a(final String s, final long n) {
        if (!this.m.containsKey(s)) {
            this.m.put(s, 0L);
        }
        if (!this.l.containsKey(s)) {
            this.l.put(s, 0L);
        }
        long n2;
        if (this.l.get(s) > n) {
            this.m.put(s, this.m.get(s) + n);
            this.l.put(s, n);
            n2 = n;
        }
        else {
            n2 = n - this.l.get(s);
            this.l.put(s, n);
        }
        return n2;
    }
    
    static {
        b.d = "DataCollectionPusher";
    }
}
