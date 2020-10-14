package com.tencent.liteav;

import com.tencent.liteav.network.*;
import com.tencent.ugc.*;
import com.tencent.rtmp.*;
import android.content.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import java.lang.ref.*;
import com.tencent.liteav.audio.*;
import android.os.*;
import com.tencent.rtmp.ui.*;
import android.view.*;
import com.tencent.liteav.basic.datareport.*;
import com.tencent.liteav.basic.module.*;

import android.text.*;
import java.util.*;
import com.tencent.liteav.basic.structs.*;
import android.graphics.*;

public class h extends s implements TXCRenderAndDec.a, TXCRenderAndDec.b, b, CameraProxy, com.tencent.liteav.basic.b.b, com.tencent.liteav.network.h, com.tencent.liteav.renderer.a.a, com.tencent.liteav.renderer.h
{
    private TXCRenderAndDec a;
    private com.tencent.liteav.renderer.a f;
    private TXCStreamDownloader g;
    private Handler h;
    private TextureView i;
    private boolean j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private boolean o;
    private Surface p;
    private int q;
    private int r;
    private int s;
    private boolean t;
    private boolean u;
    private com.tencent.liteav.h.a v;
    private TXRecordCommon.ITXVideoRecordListener w;
    private e x;
    private int y;
    private int z;
    private i A;
    private i B;
    private float[] C;
    private float[] D;
    private String E;
    private int F;
    private boolean G;
    private com.tencent.liteav.basic.a.b H;
    private Object I;
    private com.tencent.liteav.basic.b.a J;
    private TXLivePlayer.ITXAudioRawDataListener K;
    private String L;
    private boolean M;
    private long N;
    private long O;
    private a P;
    
    public h(final Context context) {
        super(context);
        this.a = null;
        this.f = null;
        this.g = null;
        this.j = false;
        this.k = false;
        this.l = 100;
        this.m = 0;
        this.n = 0;
        this.o = false;
        this.q = 2;
        this.r = 48000;
        this.s = 16;
        this.t = false;
        this.u = false;
        this.y = 0;
        this.z = 0;
        this.A = null;
        this.B = null;
        this.C = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f };
        this.D = new float[16];
        this.E = "";
        this.G = false;
        this.H = com.tencent.liteav.basic.a.b.a;
        this.I = null;
        this.J = new com.tencent.liteav.basic.b.a() {
            @Override
            public void onEvent(final String s, final int n, final String s2, final String s3) {
                TXCLog.i("TXCLivePlayer", "onEvent => id:" + s + " code:" + n + " msg:" + s2 + " params:" + s3);
                final WeakReference<com.tencent.liteav.basic.b.b> e = com.tencent.liteav.h.this.e;
                if (((e == null) ? null : ((com.tencent.liteav.basic.b.b)e.get())) != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putInt("EVT_ID", n);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (s2 != null) {
                        bundle.putCharSequence("EVT_MSG", (CharSequence)(s2 + ((s3 != null) ? s3 : "")));
                    }
                    com.tencent.liteav.h.this.onNotifyEvent(n, bundle);
                }
            }
            
            @Override
            public void onError(final String s, final int n, final String s2, final String s3) {
                TXCLog.e("TXCLivePlayer", "onError => id:" + s + " code:" + n + " msg:" + s2 + " params:" + s3);
                final WeakReference<com.tencent.liteav.basic.b.b> e = com.tencent.liteav.h.this.e;
                if (((e == null) ? null : ((com.tencent.liteav.basic.b.b)e.get())) != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putInt("EVT_ID", n);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (s2 != null) {
                        bundle.putCharSequence("EVT_MSG", (CharSequence)(s2 + ((s3 != null) ? s3 : "")));
                    }
                    com.tencent.liteav.h.this.onNotifyEvent(n, bundle);
                }
            }
        };
        this.L = "";
        this.M = false;
        this.N = 0L;
        this.O = 0L;
        this.P = null;
        com.tencent.liteav.basic.d.b.a().a(context);
        TXCAudioEngine.CreateInstance(context, com.tencent.liteav.basic.d.b.a().b());
        TXCAudioEngine.getInstance().addEventCallback(new WeakReference<com.tencent.liteav.basic.b.a>(this.J));
        final long a = com.tencent.liteav.basic.d.b.a().a("Audio", "EnableAutoRestartDevice");
        TXCAudioEngine.getInstance().enableAutoRestartDevice(a == 1L || a == -1L);
        this.h = new Handler(Looper.getMainLooper());
        (this.f = new com.tencent.liteav.renderer.a()).a(this);
        this.P = new a(this);
    }
    
    @Override
    public void a(final TXCloudVideoView txCloudVideoView) {
        if (this.d != null && this.d != txCloudVideoView) {
            final TextureView videoView = this.d.getVideoView();
            if (videoView != null) {
                this.d.removeView((View)videoView);
            }
        }
        super.a(txCloudVideoView);
        if (this.d != null) {
            this.i = this.d.getVideoView();
            if (this.i == null) {
                this.i = new TextureView(this.d.getContext());
            }
            this.d.addVideoView(this.i);
        }
        if (this.f != null) {
            this.f.a(this.i);
        }
    }
    
    @Override
    public void a(final Surface p) {
        this.p = p;
        if (this.f != null) {
            this.f.a(p);
        }
    }
    
    @Override
    public void a(final int n, final int n2) {
        if (this.f != null) {
            this.f.c(n, n2);
        }
    }
    
    @Override
    public void a(final j config) {
        super.a(config);
        if (this.a != null) {
            this.a.setConfig(config);
        }
    }
    
    @Override
    public int a(final String e, final int f) {
        if (this.c()) {
            TXCLog.w("TXCLivePlayer", "play: ignore start play when is playing");
            return -2;
        }
        if (this.b != null && b.c > b.b) {
            TXCLog.e("TXCLivePlayer", "play: can not start play while invalid cache config [minAutoAdjustCacheTime(" + b.c + ") > maxAutoAdjustCacheTime(" + b.b + ")]!!!!!!");
            return -1;
        }
        if (b.a > b.b || b.a < b.c) {
            TXCLog.w("TXCLivePlayer", "play: invalid cacheTime " + b.a + ", need between minAutoAdjustCacheTime " + b.c + " and maxAutoAdjustCacheTime " + b.b + " , fix to maxAutoAdjustCacheTime");
            b.a = b.b;
        }
        this.E = e;
        this.F = f;
        this.b(e);
        this.o = true;
        this.u = true;
        this.f(f);
        this.n();
        final int b = this.b(e, f);
        if (b != 0) {
            this.o = false;
            this.p();
            this.m();
            this.o();
            if (this.i != null) {
                this.i.setVisibility(8);
            }
        }
        else {
            this.a(this.p);
            this.r();
            this.v();
            if (this.H == com.tencent.liteav.basic.a.b.c && this.i == null && this.f != null) {
                this.f.c(this.I);
            }
            TXCDRApi.txReportDAU(this.c, com.tencent.liteav.basic.datareport.a.bt);
            try {
                if (Class.forName("com.tencent.liteav.demo.play.SuperPlayerView") != null) {
                    TXCDRApi.txReportDAU(this.c, com.tencent.liteav.basic.datareport.a.bE);
                }
            }
            catch (Exception ex) {}
        }
        return b;
    }
    
    @Override
    public int a(final boolean b) {
        if (!this.c()) {
            TXCLog.w("TXCLivePlayer", "play: ignore stop play when not started");
            return -2;
        }
        TXCLog.v("TXCLivePlayer", "play: stop");
        this.o = false;
        this.p();
        this.m();
        if (this.i != null && b) {
            this.i.setVisibility(8);
        }
        if (this.f != null) {
            this.f.a((Surface)null);
        }
        if (this.i == null && this.f != null) {
            this.f.e();
        }
        this.o();
        this.s();
        this.w();
        this.t();
        return 0;
    }
    
    @Override
    public int a(final String e) {
        if (!this.c() || this.g == null) {
            return -1;
        }
        final boolean switchStream = this.g.switchStream(e);
        if (this.a != null) {
            TXCLog.w("TXCLivePlayer", " stream_switch video cache " + this.a.getVideoCacheDuration() + " audio cache " + TXCStatus.c(this.L, 2007));
        }
        if (switchStream) {
            this.E = e;
            return 0;
        }
        return -2;
    }
    
    @Override
    public void a() {
        this.a(false);
    }
    
    @Override
    public void b() {
        this.a(this.E, this.F);
    }
    
    @Override
    public boolean c() {
        return this.o;
    }
    
    @Override
    public void a(final int n) {
        this.n = n;
        if (this.a != null) {
            this.a.setRenderMode(n);
        }
    }
    
    @Override
    public void b(final int n) {
        this.m = n;
        if (this.a != null) {
            this.a.setRenderRotation(n);
        }
    }
    
    @Override
    public void b(final boolean j) {
        this.j = j;
        TXCAudioEngine.getInstance().muteRemoteAudio(this.L, this.j);
    }
    
    @Override
    public void c(final boolean k) {
        this.k = k;
        TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(this.L, k);
    }
    
    @Override
    public void c(final int l) {
        this.l = l;
        TXCAudioEngine.getInstance().setRemotePlayoutVolume(this.L, this.l);
    }
    
    @Override
    public void a(final Context context, final int audioRoute) {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.setAudioRoute(audioRoute);
    }
    
    @Override
    public void a(final TXRecordCommon.ITXVideoRecordListener w) {
        this.w = w;
    }
    
    @Override
    public int d(final int n) {
        if (this.t) {
            TXCLog.e("TXCLivePlayer", "startRecord: there is existing uncompleted record task");
            return -1;
        }
        this.t = true;
        this.f.a((com.tencent.liteav.renderer.h)this);
        this.f.a((com.tencent.liteav.renderer.a.a)this);
        this.y();
        TXCDRApi.txReportDAU(this.c, com.tencent.liteav.basic.datareport.a.av);
        return 0;
    }
    
    @Override
    public TextureView d() {
        return this.i;
    }
    
    @Override
    public void a(final TXLivePlayer.ITXAudioRawDataListener k) {
        this.K = k;
        this.y();
    }
    
    @Override
    public int e() {
        if (!this.t) {
            TXCLog.w("TXCLivePlayer", "stopRecord: no recording task exist");
            return -1;
        }
        this.t = false;
        this.y();
        if (this.v != null) {
            this.v.a();
            this.v = null;
        }
        return 0;
    }
    
    @Override
    public void a(final t t, final com.tencent.liteav.basic.a.b h, final Object i) {
        this.H = h;
        this.I = i;
        if (this.c() && this.H == com.tencent.liteav.basic.a.b.c && this.i == null && t != null && this.f != null) {
            this.f.c(this.I);
        }
        if (this.a != null) {
            this.a.setVideoFrameListener(t, h);
        }
        else {
            TXCLog.w("TXCLivePlayer", "setVideoFrameListener->enter with renderAndDec is empty");
        }
    }
    
    @Override
    public boolean getEGLContext() {
        return true;
    }
    
    @Override
    public void a(final o o) {
        if (this.f != null) {
            this.f.a(o);
        }
    }
    
    private void j() {
        if (this.v == null) {
            this.y = this.f.i();
            this.z = this.f.j();
            (this.v = new a(this.c)).a(this.l());
            this.v.a(new com.tencent.liteav.h.a.b() {
                @Override
                public void a(final int n, final String descMsg, final String videoPath, final String coverPath) {
                    if (h.this.w != null) {
                        final TXRecordCommon.TXRecordResult txRecordResult = new TXRecordCommon.TXRecordResult();
                        if (n == 0) {
                            txRecordResult.retCode = 0;
                        }
                        else {
                            txRecordResult.retCode = -1;
                        }
                        txRecordResult.descMsg = descMsg;
                        txRecordResult.videoPath = videoPath;
                        txRecordResult.coverPath = coverPath;
                        h.this.w.onRecordComplete(txRecordResult);
                    }
                    h.this.f.a((com.tencent.liteav.renderer.h)null);
                    h.this.f.a((a.a)null);
                }
                
                @Override
                public void a(final long n) {
                    if (h.this.w != null) {
                        h.this.w.onRecordProgress(n);
                    }
                }
            });
        }
        if (this.A == null) {
            (this.A = new i(true)).b();
            this.A.b(this.y, this.z);
            this.A.a(this.y, this.z);
        }
        if (this.B == null) {
            (this.B = new i(false)).b();
            this.B.b(this.f.g(), this.f.h());
            this.B.a(this.f.g(), this.f.h());
            Matrix.setIdentityM(this.D, 0);
        }
    }
    
    private void k() {
        if (this.A != null) {
            this.A.c();
            this.A = null;
        }
        if (this.B != null) {
            this.B.c();
            this.B = null;
        }
    }
    
    private com.tencent.liteav.h.a.a l() {
        int y = 480;
        int z = 640;
        if (this.y > 0 && this.z > 0) {
            y = this.y;
            z = this.z;
        }
        final com.tencent.liteav.h.a.a a = new a.a();
        a.a = y;
        a.b = z;
        a.c = 20;
        a.d = (int)(Math.sqrt(y * y * 1.0 + z * z) * 1.2);
        a.h = this.q;
        a.i = this.r;
        a.j = this.s;
        a.f = com.tencent.liteav.h.a.a(this.c, ".mp4");
        a.g = com.tencent.liteav.h.a.a(this.c, ".jpg");
        a.e = this.f.b();
        TXCLog.d("TXCLivePlayer", "record config: " + a);
        return a;
    }
    
    private void f(final int n) {
        if (this.i != null) {
            this.i.setVisibility(0);
        }
        (this.a = new TXCRenderAndDec(this.c)).setNotifyListener(this);
        this.a.setVideoRender(this.f);
        this.a.setDecListener(this);
        this.a.setRenderAndDecDelegate(this);
        this.a.setConfig(this.b);
        this.a.setID(this.L);
        this.a.start(n == 5);
        this.a.setRenderMode(this.n);
        this.a.setRenderRotation(this.m);
    }
    
    private void m() {
        if (this.a != null) {
            this.a.stop();
            this.a.setVideoRender(null);
            this.a.setDecListener(null);
            this.a.setNotifyListener(null);
            this.a = null;
        }
    }
    
    private void n() {
        final boolean b = this.F == 5;
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(this.G, 300);
        this.a(this.K);
        if (this.F == 5) {
            TXCAudioEngine.getInstance().setRemoteAudioCacheParams(this.L, !this.b.g, (int)(com.tencent.liteav.basic.a.a.b * 1000.0f), (int)(com.tencent.liteav.basic.a.a.b * 1000.0f), (int)(com.tencent.liteav.basic.a.a.c * 1000.0f));
        }
        else {
            TXCAudioEngine.getInstance().setRemoteAudioCacheParams(this.L, !this.b.g, (int)(this.b.a * 1000.0f), (int)(this.b.c * 1000.0f), (int)(this.b.b * 1000.0f));
        }
        TXCAudioEngine.getInstance().muteRemoteAudio(this.L, this.j);
        TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(this.L, this.k);
        TXCAudioEngine.getInstance().setRemotePlayoutVolume(this.L, this.l);
        TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(this.L, this);
        this.y();
        TXCAudioEngine.getInstance().startRemoteAudio(this.L, b);
    }
    
    private void o() {
        TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(this.L, null);
        TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(this.L, null);
        TXCAudioEngine.getInstance().stopRemoteAudio(this.L);
    }
    
    private int b(final String s, final int n) {
        if (n == 0) {
            this.g = new TXCStreamDownloader(this.c, 1);
        }
        else if (n == 5) {
            this.g = new TXCStreamDownloader(this.c, 4);
        }
        else {
            this.g = new TXCStreamDownloader(this.c, 0);
            if (!TextUtils.isEmpty((CharSequence) b.l)) {
                this.g.setFlvSessionKey(b.l);
            }
        }
        this.g.setID(this.L);
        this.g.setListener(this);
        this.g.setNotifyListener(this);
        this.g.setHeaders(b.q);
        if (n == 5) {
            this.g.setRetryTimes(5);
            this.g.setRetryInterval(1);
        }
        else {
            this.g.setRetryTimes(b.e);
            this.g.setRetryInterval(b.f);
        }
        return this.g.start(s, b.i, b.m, b.j, b.k);
    }
    
    private void p() {
        if (this.g != null) {
            this.g.setListener(null);
            this.g.setNotifyListener(null);
            this.g.stop();
            this.g = null;
        }
    }
    
    private void r() {
        (this.x = new e(this.c)).a(this.E);
        this.x.a(this.F == 5);
        this.x.d(this.L);
        this.x.e(this.g.getRTMPProxyUserId());
        this.x.a();
    }
    
    private void s() {
        if (this.x != null) {
            this.x.c();
            this.x = null;
        }
    }
    
    private void b(final String s) {
        this.L = String.format("%s-%d", s, TXCTimeUtil.getTimeTick() % 10000L);
        if (this.a != null) {
            this.a.setID(this.L);
        }
        if (this.f != null) {
            this.f.setID(this.L);
        }
        if (this.g != null) {
            this.g.setID(this.L);
        }
        if (this.x != null) {
            this.x.d(this.L);
        }
    }
    
    @Override
    public void g() {
        this.O = 0L;
        if (this.M) {
            return;
        }
        this.M = true;
        this.y();
        if (this.h != null) {
            this.h.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.h.this.M) {
                        com.tencent.liteav.h.this.u();
                    }
                }
            }, 1000L);
        }
    }
    
    private void t() {
        this.M = false;
        this.y();
    }
    
    private void u() {
        if (this.N > 0L) {
            final Bundle bundle = new Bundle();
            bundle.putInt("EVT_PLAY_PROGRESS", (int)(this.N / 1000L));
            bundle.putInt("EVT_PLAY_PROGRESS_MS", (int)this.N);
            this.onNotifyEvent(2005, bundle);
        }
        if (this.h != null && this.M) {
            this.h.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.h.this.M) {
                        com.tencent.liteav.h.this.u();
                    }
                }
            }, 1000L);
        }
    }
    
    private void v() {
        if (this.h != null) {
            this.h.postDelayed((Runnable)this.P, 2000L);
        }
    }
    
    private void w() {
        if (this.h != null) {
            this.h.removeCallbacks((Runnable)this.P);
        }
    }
    
    public void h() {
        this.x();
        final int[] a = com.tencent.liteav.basic.util.f.a();
        final String string = a[0] / 10 + "/" + a[1] / 10 + "%";
        final int c = TXCStatus.c(this.L, 7102);
        final int c2 = TXCStatus.c(this.L, 7101);
        final String b = TXCStatus.b(this.L, 7110);
        final int n = (int)TXCStatus.d(this.L, 6002);
        final Bundle bundle = new Bundle();
        if (this.f != null) {
            bundle.putInt("VIDEO_WIDTH", this.f.i());
            bundle.putInt("VIDEO_HEIGHT", this.f.j());
        }
        if (this.a != null) {
            bundle.putInt("VIDEO_CACHE", (int)this.a.getVideoCacheDuration());
            bundle.putInt("V_SUM_CACHE_SIZE", (int)this.a.getVideoCacheFrameCount());
            bundle.putInt("V_DEC_CACHE_SIZE", this.a.getVideoDecCacheFrameCount());
            bundle.putInt("AV_PLAY_INTERVAL", (int)this.a.getAVPlayInterval());
            bundle.putInt("AV_RECV_INTERVAL", (int)this.a.getAVNetRecvInterval());
            int n2;
            if ((n2 = n) == 0) {
                n2 = 15;
            }
            bundle.putInt("VIDEO_GOP", (int)(TXCStatus.c(this.L, 7120) * 10 / n2 / 10.0f + 0.5));
        }
        bundle.putString("AUDIO_PLAY_INFO", TXCAudioEngine.getInstance().getPlayAECType() + " | " + TXCStatus.c(this.L, 2019) + "," + TXCStatus.c(this.L, 2020) + " | " + TXCAudioEngine.getInstance().getPlaySampleRate() + "," + TXCAudioEngine.getInstance().getPlayChannels());
        bundle.putInt("AUDIO_CACHE", TXCStatus.c(this.L, 2007));
        bundle.putInt("NET_JITTER", TXCStatus.c(this.L, 2018));
        bundle.putFloat("AUDIO_CACHE_THRESHOLD", TXCStatus.c(this.L, 2021) / 1000.0f);
        bundle.putInt("NET_SPEED", c2 + c);
        bundle.putInt("VIDEO_FPS", n);
        bundle.putInt("VIDEO_BITRATE", c2);
        bundle.putInt("AUDIO_BITRATE", c);
        bundle.putCharSequence("SERVER_IP", (CharSequence)b);
        bundle.putCharSequence("CPU_USAGE", (CharSequence)string);
        com.tencent.liteav.basic.util.f.a(this.e, 15001, bundle);
        if (this.a != null) {
            this.a.updateLoadInfo();
        }
        if (this.x != null) {
            this.x.f();
        }
    }
    
    private void x() {
        final ArrayList<String> list = new ArrayList<String>();
        if (this.L != null) {
            list.add(this.L);
        }
        com.tencent.liteav.a.a("18446744073709551615", list);
    }
    
    private void a(final int n, final String s) {
        if (this.e != null) {
            final Bundle bundle = new Bundle();
            bundle.putInt("EVT_ID", n);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            if (s != null) {
                bundle.putCharSequence("EVT_MSG", (CharSequence)s);
            }
            if (this.h != null) {
                this.h.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        com.tencent.liteav.basic.util.f.a(com.tencent.liteav.h.this.e, n, bundle);
                    }
                });
            }
        }
    }
    
    private void y() {
        if (this.t || null != this.K || this.M) {
            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(this.L, this);
        }
        if (!this.t && null == this.K && !this.M) {
            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(this.L, null);
        }
    }
    
    @Override
    public void onPullAudio(final com.tencent.liteav.basic.structs.a a) {
    }
    
    @Override
    public void onPullNAL(final TXSNALPacket txsnalPacket) {
        if (!this.o) {
            return;
        }
        try {
            if (this.a != null) {
                this.a.decVideo(txsnalPacket);
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCLivePlayer", "decode video failed." + ex.getMessage());
        }
    }
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
        if (2003 == n || 2026 == n) {
            if (this.u) {
                this.a(2004, "Video play started");
                this.u = false;
            }
            if (2026 == n) {
                TXCStatus.a(this.L, 2033, TXCTimeUtil.getTimeTick());
                return;
            }
        }
        if (2025 == n) {
            this.a(2004, "Video play started");
            return;
        }
        if (2023 == n || 2024 == n) {
            this.a(2007, "Video play loading");
            return;
        }
        if (this.h != null) {
            this.h.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.basic.util.f.a(com.tencent.liteav.h.this.e, n, bundle);
                    if (n == 2103 && com.tencent.liteav.h.this.a != null) {
                        com.tencent.liteav.h.this.a.restartDecoder();
                    }
                }
            });
        }
    }
    
    @Override
    public int a(final int n, final float[] array) {
        final com.tencent.liteav.h.a v = this.v;
        if (this.t && v != null && this.A != null) {
            final int d = this.A.d(n);
            v.a(d, TXCTimeUtil.getTimeTick());
            this.f.a(d, this.y, this.z, false, 0);
        }
        if (this.t) {
            this.j();
        }
        else {
            this.k();
        }
        return n;
    }
    
    @Override
    public void onTextureProcess(final int n, final int n2, final int n3, final int n4) {
        final com.tencent.liteav.h.a v = this.v;
        if (this.t && v != null && this.B != null) {
            this.B.a(this.C);
            v.a(this.B.d(n), TXCTimeUtil.getTimeTick());
            this.B.a(this.D);
            this.B.c(n);
        }
        if (this.t) {
            this.j();
        }
        else {
            this.k();
        }
    }
    
    @Override
    public void a(final SurfaceTexture surfaceTexture) {
        this.k();
        this.startWithoutAudio();
    }
    
    @Override
    public void onAudioPlayPcmData(final String s, final byte[] array, long timeTick, final int r, final int q) {
        this.r = r;
        this.q = q;
        if (this.v != null) {
            if (timeTick <= 0L) {
                timeTick = TXCTimeUtil.getTimeTick();
            }
            this.v.a(array, timeTick);
        }
        if (this.O <= 0L && this.K != null) {
            this.K.onAudioInfoChanged(r, q, 16);
        }
        if (this.K != null) {
            this.K.onPcmDataAvailable(array, timeTick);
        }
        if (this.O <= 0L) {
            this.O = timeTick;
            return;
        }
        this.N = timeTick - this.O;
    }
    
    @Override
    public void onAudioJitterBufferNotify(final String s, final int n, final String s2) {
        this.onNotifyEvent(n, null);
    }
    
    @Override
    public void a(final boolean g, final int n) {
        this.G = g;
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(g, n);
    }
    
    @Override
    public int i() {
        return TXCAudioEngine.getInstance().getRemotePlayoutVolumeLevel(this.L);
    }
    
    @Override
    public void onRequestKeyFrame(final String s, final int n) {
        if (this.o && this.g != null) {
            this.g.requestKeyFrame(this.E);
        }
    }
    
    private static class a implements Runnable
    {
        private WeakReference<h> a;
        
        a(final h h) {
            this.a = new WeakReference<h>(h);
        }
        
        @Override
        public void run() {
            final h h = this.a.get();
            if (h == null) {
                return;
            }
            h.resumePusher();
            h.v();
        }
    }
}
