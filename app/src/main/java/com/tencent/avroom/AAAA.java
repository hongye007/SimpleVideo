package com.tencent.avroom;

import java.lang.ref.*;
import com.tencent.liteav.renderer.f;
import com.tencent.liteav.avprotocol.*;
import com.tencent.liteav.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.audio.*;
import com.tencent.liteav.basic.module.*;
import android.os.*;
import android.util.*;

public class AAAA
{
    private long a;
    private Handler b;
    private static String c;
    private HashMap d;
    private HashMap e;
    private HashMap f;
    private boolean g;
    private long h;
    private long i;
    private long j;
    private long k;
    private int l;
    private int m;
    private TXCAVRoomLisenter n;
    private WeakReference<f> o;
    private WeakReference<TXCAVProtocol> p;
    private WeakReference<TXCRenderAndDec> q;
    private TimerTask r;
    private Timer s;
    
    public AAAA(final long a) {
        this.b = new Handler(Looper.getMainLooper());
        this.d = new HashMap(100);
        this.e = new HashMap(100);
        this.f = new HashMap(100);
        this.g = false;
        this.h = 0L;
        this.i = 0L;
        this.j = 0L;
        this.k = 0L;
        this.l = 0;
        this.m = 0;
        this.r = null;
        this.a = a;
    }
    
    public void a(final int n) {
        this.m += n;
    }
    
    public void b(final int n) {
        this.l += n;
    }
    
    public void a(final f f) {
        if (f != null) {
            f.setID(String.format("%d", this.a));
        }
        this.o = new WeakReference<f>(f);
    }
    
    public void a(final TXCAVProtocol txcavProtocol) {
        this.p = new WeakReference<TXCAVProtocol>(txcavProtocol);
    }
    
    public void a(final TXCAVRoomLisenter n) {
        this.n = n;
    }
    
    public void a(final TXCRenderAndDec txcRenderAndDec) {
        this.q = new WeakReference<TXCRenderAndDec>(txcRenderAndDec);
    }
    
    public void a() {
        if (null != this.r) {
            this.r.cancel();
        }
        this.s = new Timer(true);
        this.r = new TimerTask() {
            @Override
            public void run() {
                TXCLog.i(AAAA.c, "dataCollectingStart run: ");
                if (AAAA.this.p != null) {
                    final TXCAVProtocol txcavProtocol = (TXCAVProtocol) AAAA.this.p.get();
                    if (txcavProtocol != null) {
                        AAAA.this.a(txcavProtocol.getDownloadStats(), 2000L);
                    }
                }
                if (AAAA.this.o != null) {
                    final f f = (f) AAAA.this.o.get();
                    if (f != null) {
                        AAAA.this.a(f.m(), 2000L);
                    }
                }
                final long videoCacheFrameCount = ((TXCRenderAndDec) AAAA.this.q.get()).getVideoCacheFrameCount();
                if (AAAA.this.g) {
                    AAAA.this.k++;
                    AAAA.this.j += videoCacheFrameCount;
                    if (videoCacheFrameCount > AAAA.this.i) {
                        AAAA.this.i = videoCacheFrameCount;
                    }
                }
                if (AAAA.this.q != null && AAAA.this.q.get() != null) {
                    AAAA.this.d.put("VIDEO_CACHE", (int)((TXCRenderAndDec) AAAA.this.q.get()).getVideoCacheDuration());
                    AAAA.this.d.put("VIDEO_CACHE_CNT", (int)((TXCRenderAndDec) AAAA.this.q.get()).getVideoCacheFrameCount());
                    AAAA.this.d.put("V_DEC_CACHE_CNT", ((TXCRenderAndDec) AAAA.this.q.get()).getVideoDecCacheFrameCount());
                    AAAA.this.d.put("AV_INTERVAL", (int)((TXCRenderAndDec) AAAA.this.q.get()).getAVPlayInterval());
                    AAAA.this.d.put("AV_NET_RECV_INTERVAL", ((TXCRenderAndDec) AAAA.this.q.get()).getAVNetRecvInterval());
                    AAAA.this.d.put("VIDEO_GOP", ((TXCRenderAndDec) AAAA.this.q.get()).getVideoGop());
                }
                AAAA.this.d.put("AUDIO_INFO", TXCAudioEngine.getInstance().getPlayAECType() + " | " + TXCStatus.c(String.valueOf(AAAA.this.a), 2019) + "," + TXCStatus.c(String.valueOf(AAAA.this.a), 2020) + " | " + TXCAudioEngine.getInstance().getPlaySampleRate() + "," + TXCAudioEngine.getInstance().getPlayChannels());
                AAAA.this.d.put("AUDIO_CACHE", TXCStatus.c(String.valueOf(AAAA.this.a), 2007));
                AAAA.this.d.put("AUDIO_JITTER", TXCStatus.c(String.valueOf(AAAA.this.a), 2018));
                AAAA.this.d.put("AUDIO_SPEED", TXCStatus.c(String.valueOf(AAAA.this.a), 2021));
                final int[] a = com.tencent.liteav.basic.util.f.a();
                AAAA.this.d.put("u32_app_cpu_usage", (long)a[0]);
                AAAA.this.d.put("u32_cpu_usage", (long)a[1]);
                AAAA.this.b.post((Runnable)new Runnable() {
                    final /* synthetic */ Bundle a = AAAA.this.d();
                    
                    @Override
                    public void run() {
                        if (null != AAAA.this.n) {
                            AAAA.this.n.onAVRoomStatus(AAAA.this.a, this.a);
                        }
                    }
                });
            }
        };
        this.s.schedule(this.r, 0L, 2000L);
    }
    
    public void b() {
        if (null != this.s) {
            this.s.cancel();
            this.s = null;
        }
        if (this.d != null) {
            this.d.clear();
        }
    }
    
    private void a(final f.a a, final long n) {
        if (a == null || n == 0L) {
            return;
        }
        this.d.put("u32_fps", this.a("u32_fps", a.c) * 10000L / n);
        this.d.put("u32_first_i_frame", a.j);
        this.d.put("u32_avg_block_count", a.f);
        this.d.put("u32_avg_block_time", a.e);
        this.d.put("VIDEO_WIDTH", (long)a.k);
        this.d.put("VIDEO_HEIGHT", (long)a.l);
    }
    
    private Bundle d() {
        final Bundle bundle = new Bundle();
        final String string = (int)this.d.get("u32_app_cpu_usage") / 10 + "/" + (int)this.d.get("u32_cpu_usage") / 10 + "%";
        bundle.putLong("myid", 16842960L);
        bundle.putCharSequence("CPU_USAGE", (CharSequence)string);
        bundle.putInt("VIDEO_WIDTH", this.a("VIDEO_WIDTH"));
        bundle.putInt("VIDEO_HEIGHT", this.a("VIDEO_HEIGHT"));
        bundle.putInt("NET_SPEED", this.a("u32_avg_net_speed"));
        bundle.putInt("VIDEO_FPS", this.a("u32_fps") / 10);
        bundle.putInt("VIDEO_DROP", this.a("video_drop"));
        bundle.putInt("VIDEO_BITRATE", this.a("u32_avg_video_bitrate"));
        bundle.putInt("AUDIO_BITRATE", this.a("u32_avg_audio_bitrate"));
        bundle.putInt("AUDIO_CACHE", this.a("AUDIO_CACHE"));
        bundle.putInt("VIDEO_CACHE", this.a("VIDEO_CACHE"));
        bundle.putInt("V_SUM_CACHE_SIZE", this.a("VIDEO_CACHE_CNT"));
        bundle.putInt("V_DEC_CACHE_SIZE", this.a("V_DEC_CACHE_CNT"));
        bundle.putInt("AV_PLAY_INTERVAL", this.a("AV_INTERVAL"));
        bundle.putString("AUDIO_PLAY_INFO", (String)this.d.get("AUDIO_INFO"));
        bundle.putCharSequence("SERVER_IP", (CharSequence)this.d.get("u32_server_ip"));
        bundle.putInt("NET_JITTER", this.a("AUDIO_JITTER"));
        bundle.putInt("AV_RECV_INTERVAL", this.a("AV_NET_RECV_INTERVAL"));
        bundle.putFloat("AUDIO_CACHE_THRESHOLD", (float)this.a("AUDIO_SPEED"));
        int n = this.a("u32_fps") / 10;
        if (n == 0) {
            n = 15;
        }
        bundle.putInt("VIDEO_GOP", (int)(this.a("VIDEO_GOP") * 10 / n / 10.0f + 0.5));
        return bundle;
    }
    
    private void a(final TXCAVProtocol.DownloadStats downloadStats, final long n) {
        if (downloadStats == null || n == 0L) {
            return;
        }
        TXCLog.i(AAAA.c, "updateNetStats: diff_a raw " + downloadStats.afterParseAudioBytes);
        TXCLog.i(AAAA.c, "updateNetStats: diff_v raw " + downloadStats.afterParseVideoBytes);
        downloadStats.afterParseAudioBytes = this.l;
        downloadStats.afterParseVideoBytes = this.m;
        final long a = this.a("u32_avg_audio_bitrate", downloadStats.afterParseAudioBytes);
        TXCLog.i(AAAA.c, "updateNetStats: kAvgAudioBitrate " + (Object)(a * 8L * 1000L / (n * 1024L)));
        this.d.put("u32_avg_audio_bitrate", a * 8L * 1000L / (n * 1024L));
        final long a2 = this.a("u32_avg_video_bitrate", downloadStats.afterParseVideoBytes);
        TXCLog.i(AAAA.c, "updateNetStats: diff_v " + a2);
        TXCLog.i(AAAA.c, "updateNetStats: kAvgVideoBitrate " + (Object)(a2 * 8L * 1000L / (n * 1024L)));
        this.d.put("u32_avg_video_bitrate", a2 * 8L * 1000L / (n * 1024L));
        Log.i(AAAA.c, "updateNetStats: kAvgNetSpeed " + (Object)((a + a2) * 8L * 1000L / (n * 1024L)));
        this.d.put("u32_avg_net_speed", (a + a2) * 8L * 1000L / (n * 1024L));
        this.d.put("u32_server_ip", downloadStats.serverIP);
        this.d.put("u32_dns_time", downloadStats.dnsTS);
        this.d.put("u32_connect_server_time", downloadStats.connTS);
        this.d.put("u64_timestamp", downloadStats.startTS);
        this.d.put("u32_first_frame_down", downloadStats.firstVideoTS);
    }
    
    private long a(final String s, final long n) {
        if (!this.f.containsKey(s)) {
            this.f.put(s, 0L);
        }
        if (!this.e.containsKey(s)) {
            this.e.put(s, 0L);
        }
        long n2;
        if ((long)this.e.get(s) > n) {
            this.f.put(s, (long)this.e.get(s) + n);
            this.e.put(s, n);
            n2 = n;
        }
        else {
            n2 = n - (long)this.e.get(s);
            this.e.put(s, n);
        }
        return n2;
    }
    
    private int a(final String s) {
        final Number n = (long)this.e.get(s);
        if (n != null) {
            return n.intValue();
        }
        return 0;
    }
    
    static {
        AAAA.c = "DataCollectionPlayer";
    }
}
