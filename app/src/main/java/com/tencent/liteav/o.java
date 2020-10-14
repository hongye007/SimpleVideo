package com.tencent.liteav;

import android.content.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.txcvodplayer.*;
import android.view.*;
import com.tencent.liteav.basic.datareport.*;
import com.tencent.rtmp.ui.*;
import com.tencent.rtmp.*;
import com.tencent.ijk.media.player.*;
import java.util.*;

public class o extends s
{
    private TXCVodVideoView f;
    private CaptureAndEnc g;
    private n h;
    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private float m;
    private Surface n;
    private e o;
    protected boolean a;
    
    public o(final Context context) {
        super(context);
        this.h = null;
        this.j = true;
        this.k = true;
        this.l = true;
        this.m = 1.0f;
        this.o = new e() {
            @Override
            public void a(final int n, final Bundle bundle) {
                final Bundle bundle2 = new Bundle(bundle);
                int n2 = 0;
                switch (n) {
                    case 2013: {
                        n2 = 2013;
                        com.tencent.liteav.o.this.h.d();
                        break;
                    }
                    case 2004: {
                        n2 = 2004;
                        com.tencent.liteav.o.this.h.d();
                        break;
                    }
                    case 2003: {
                        if (com.tencent.liteav.o.this.i) {
                            return;
                        }
                        com.tencent.liteav.o.this.i = true;
                        n2 = 2003;
                        com.tencent.liteav.o.this.h.e();
                        final Bundle bundle3 = new Bundle();
                        bundle3.putInt("EVT_ID", 2008);
                        bundle3.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                        int n3 = 0;
                        final MediaInfo mediaInfo = com.tencent.liteav.o.this.f.getMediaInfo();
                        if (mediaInfo != null && mediaInfo.mVideoDecoderImpl != null && mediaInfo.mVideoDecoderImpl.contains("hevc")) {
                            n3 = 1;
                        }
                        if (com.tencent.liteav.o.this.f.getPlayerType() == 0) {
                            if (n3 == 0) {
                                bundle3.putCharSequence("description", (CharSequence)(com.tencent.liteav.o.this.g.a() ? "Enables hardware decoding" : "Enables software decoding"));
                            }
                            else {
                                bundle3.putCharSequence("description", (CharSequence)(com.tencent.liteav.o.this.g.a() ? "Enables hardware decoding H265" : "Enables software decoding h265"));
                            }
                            bundle3.putInt("EVT_PARAM1", com.tencent.liteav.o.this.g.a() ? 1 : 2);
                            bundle3.putInt("hevc", n3);
                        }
                        else {
                            bundle3.putCharSequence("description", (CharSequence)"Enables hardware decoding");
                            bundle3.putInt("EVT_PARAM1", 2);
                        }
                        this.a(2008, bundle3);
                        break;
                    }
                    case 2006: {
                        com.tencent.liteav.o.this.h.c();
                        n2 = 2006;
                        if (com.tencent.liteav.o.this.a) {
                            com.tencent.liteav.o.this.f.b();
                            TXCLog.d("TXVodPlayer", "loop play");
                            return;
                        }
                        break;
                    }
                    case 2007: {
                        n2 = 2007;
                        com.tencent.liteav.o.this.h.i();
                        break;
                    }
                    case 2009: {
                        n2 = 2009;
                        break;
                    }
                    case 2011: {
                        n2 = 2011;
                        bundle2.putInt("EVT_PARAM1", com.tencent.liteav.o.this.f.getMetaRotationDegree());
                        break;
                    }
                    case -2301: {
                        n2 = -2301;
                        break;
                    }
                    case 2005: {
                        n2 = 2005;
                        com.tencent.liteav.o.this.h.a(bundle.getInt("EVT_PLAY_DURATION", 0));
                        break;
                    }
                    case 2103: {
                        n2 = 2103;
                        break;
                    }
                    case 2008: {
                        n2 = 2008;
                        break;
                    }
                    case -2303: {
                        n2 = -2303;
                        break;
                    }
                    case -2305: {
                        n2 = -2305;
                        break;
                    }
                    case -2304: {
                        n2 = -2304;
                        if (!com.tencent.liteav.o.this.i) {
                            com.tencent.liteav.o.this.g.a(false);
                            break;
                        }
                        break;
                    }
                    case 2106: {
                        n2 = 2106;
                        if (!com.tencent.liteav.o.this.i) {
                            com.tencent.liteav.o.this.g.a(false);
                            break;
                        }
                        break;
                    }
                    case 2016: {
                        com.tencent.liteav.o.this.h.f();
                        return;
                    }
                    case 2018: {
                        com.tencent.liteav.o.this.h.g();
                        return;
                    }
                    case 2017: {
                        com.tencent.liteav.o.this.h.h();
                        return;
                    }
                    case 2019: {
                        return;
                    }
                    case 2014: {
                        n2 = 2014;
                        break;
                    }
                    default: {
                        TXCLog.d("TXVodPlayer", "miss match event " + n);
                        return;
                    }
                }
                bundle2.putString("EVT_MSG", bundle.getString("description", ""));
                if (com.tencent.liteav.o.this.e != null) {
                    final b b = com.tencent.liteav.o.this.e.get();
                    if (b != null) {
                        b.onNotifyEvent(n2, bundle2);
                    }
                }
            }
            
            @Override
            public void a(final Bundle bundle) {
                final Bundle bundle2 = new Bundle();
                final int[] a = com.tencent.liteav.basic.util.f.a();
                bundle2.putCharSequence("CPU_USAGE", (CharSequence)(a[0] / 10 + "/" + a[1] / 10 + "%"));
                bundle2.putInt("VIDEO_FPS", (int)bundle.getFloat("fps"));
                bundle2.putInt("VIDEO_DPS", (int)bundle.getFloat("dps"));
                bundle2.putInt("NET_SPEED", (int)bundle.getLong("tcpSpeed") / 1000);
                bundle2.putInt("AUDIO_CACHE", (int)bundle.getLong("cachedBytes") / 1000);
                bundle2.putInt("VIDEO_WIDTH", com.tencent.liteav.o.this.f.getVideoWidth());
                bundle2.putInt("VIDEO_HEIGHT", com.tencent.liteav.o.this.f.getVideoHeight());
                bundle2.putString("SERVER_IP", com.tencent.liteav.o.this.f.getServerIp());
                com.tencent.liteav.o.this.h.b(com.tencent.liteav.o.this.f.getServerIp());
                if (com.tencent.liteav.o.this.e != null) {
                    final b b = com.tencent.liteav.o.this.e.get();
                    if (b != null) {
                        b.onNotifyEvent(15001, bundle2);
                    }
                }
            }
        };
        (this.f = new TXCVodVideoView(context)).setListener(this.o);
    }
    
    @Override
    public void a(final j j) {
        super.a(j);
        if (this.g == null) {
            this.g = new CaptureAndEnc();
        }
        this.g.a((float)this.b.e);
        this.g.b((float)this.b.f);
        this.g.c((float)this.b.r);
        this.g.a(this.b.h);
        this.g.a(this.b.n);
        this.g.setVideoEncRotation(this.b.o);
        this.g.setLocalViewMirror(this.b.p);
        this.g.a(this.b.q);
        this.g.enableBlackStream(this.b.s);
        this.g.c(this.b.u);
        this.g.b(this.b.v);
        this.g.c(this.b.w);
        this.g.d(this.b.x);
        this.f.setConfig(this.g);
        this.l = j.t;
    }
    
    @Override
    public int a(final String videoPath, final int n) {
        if (this.d != null) {
            this.d.setVisibility(0);
            if (this.d.getVideoView() == null) {
                final TextureRenderView textureRenderView = new TextureRenderView(this.d.getContext());
                this.d.addVideoView(textureRenderView);
                this.f.setTextureRenderView(textureRenderView);
            }
            this.d.getVideoView().setVisibility(0);
        }
        else if (this.n != null) {
            this.f.setRenderSurface(this.n);
        }
        (this.h = new n(this.c)).a(videoPath);
        this.h.b();
        this.i = false;
        this.f.setPlayerType(this.g.getBeautyManager());
        this.f.b(this.j);
        this.f.setVideoPath(videoPath);
        this.f.setAutoPlay(this.k);
        this.f.setRate(this.m);
        this.f.setAutoRotate(this.l);
        if (this.g != null) {
            this.f.b();
            if (this.g.getBeautyManager() == 1) {
                this.h.b(3);
            }
            else {
                this.h.b(1);
            }
        }
        else {
            this.f.b();
            this.h.b(1);
        }
        TXCLog.d("TXVodPlayer", "startPlay " + videoPath);
        TXCDRApi.txReportDAU(this.c, com.tencent.liteav.basic.datareport.a.bs);
        try {
            if (Class.forName("com.tencent.liteav.demo.play.SuperPlayerView") != null) {
                TXCDRApi.txReportDAU(this.c, com.tencent.liteav.basic.datareport.a.bD);
            }
        }
        catch (Exception ex) {}
        return 0;
    }
    
    @Override
    public int a(final boolean b) {
        this.f.c();
        if (this.d != null && this.d.getVideoView() != null && b) {
            this.d.getVideoView().setVisibility(8);
        }
        if (this.h != null) {
            this.h.c();
        }
        return 0;
    }
    
    @Override
    public void a(final Surface n) {
        this.n = n;
        if (this.f != null) {
            this.f.setRenderSurface(this.n);
        }
    }
    
    @Override
    public void a() {
        this.f.d();
    }
    
    @Override
    public void b() {
        this.f.b();
    }
    
    @Override
    public void e(final int n) {
        this.f.a(n * 1000);
        if (this.i && this.h != null) {
            this.h.j();
        }
    }
    
    public void a(final float n) {
        this.f.a((int)(n * 1000.0f));
        if (this.i && this.h != null) {
            this.h.j();
        }
    }
    
    public float h() {
        if (this.f != null) {
            return this.f.getCurrentPosition() / 1000.0f;
        }
        return 0.0f;
    }
    
    public float j() {
        if (this.f != null) {
            return this.f.getBufferDuration() / 1000.0f;
        }
        return 0.0f;
    }
    
    public float k() {
        if (this.f != null) {
            return this.f.getDuration() / 1000.0f;
        }
        return 0.0f;
    }
    
    public float l() {
        if (this.f != null) {
            return this.f.getBufferDuration() / 1000.0f;
        }
        return 0.0f;
    }
    
    public int m() {
        if (this.f != null) {
            return this.f.getVideoWidth();
        }
        return 0;
    }
    
    public int n() {
        if (this.f != null) {
            return this.f.getVideoHeight();
        }
        return 0;
    }
    
    @Override
    public void b(final boolean mute) {
        this.f.setMute(mute);
    }
    
    @Override
    public void c(final boolean b) {
    }
    
    @Override
    public void c(final int volume) {
        this.f.setVolume(volume);
    }
    
    @Override
    public void a(final int n) {
        if (n == 1) {
            this.f.setRenderMode(0);
        }
        else {
            this.f.setRenderMode(1);
        }
    }
    
    @Override
    public void b(final int n) {
        this.f.setVideoRotationDegree(360 - n);
    }
    
    @Override
    public void a(final TXCloudVideoView txCloudVideoView) {
        if (txCloudVideoView != this.d) {
            if (this.d != null) {
                this.d.removeVideoView();
            }
            if (txCloudVideoView != null) {
                txCloudVideoView.removeVideoView();
            }
        }
        if (txCloudVideoView != null) {
            txCloudVideoView.setVisibility(0);
            if (txCloudVideoView.getVideoView() == null) {
                final TextureRenderView textureRenderView = new TextureRenderView(txCloudVideoView.getContext());
                txCloudVideoView.addVideoView(textureRenderView);
                this.f.setTextureRenderView(textureRenderView);
            }
            txCloudVideoView.getVideoView().setVisibility(0);
        }
        super.a(txCloudVideoView);
    }
    
    public void a(final TextureRenderView renderView) {
        if (this.f != null) {
            this.f.setRenderView(renderView);
        }
    }
    
    @Override
    public int d(final int n) {
        return 0;
    }
    
    @Override
    public int e() {
        return 0;
    }
    
    @Override
    public TextureView d() {
        if (this.d != null) {
            return this.d.getVideoView();
        }
        return null;
    }
    
    @Override
    public boolean c() {
        return this.f.e();
    }
    
    @Override
    public void a(final com.tencent.liteav.basic.c.o o) {
    }
    
    @Override
    public boolean d(final boolean j) {
        this.j = j;
        return this.f == null || this.f.b(j);
    }
    
    @Override
    public void e(final boolean b) {
        this.k = b;
        if (this.f != null) {
            this.f.setAutoPlay(b);
        }
    }
    
    @Override
    public void b(final float n) {
        this.m = n;
        if (this.f != null) {
            this.f.setRate(n);
        }
        if (this.i && this.h != null) {
            this.h.l();
        }
    }
    
    public void c(final float startTime) {
        if (this.f != null) {
            this.f.setStartTime(startTime);
        }
    }
    
    public int o() {
        if (this.f != null) {
            return this.f.getBitrateIndex();
        }
        return 0;
    }
    
    public void f(final int bitrateIndex) {
        if (this.f != null) {
            this.f.setBitrateIndex(bitrateIndex);
        }
        if (this.i && this.h != null) {
            this.h.k();
        }
    }
    
    public ArrayList<TXBitrateItem> p() {
        final ArrayList<TXBitrateItem> list = new ArrayList<TXBitrateItem>();
        if (this.f != null) {
            final ArrayList<IjkBitrateItem> supportedBitrates = this.f.getSupportedBitrates();
            if (supportedBitrates != null) {
                for (final IjkBitrateItem ijkBitrateItem : supportedBitrates) {
                    final TXBitrateItem txBitrateItem = new TXBitrateItem();
                    txBitrateItem.index = ijkBitrateItem.index;
                    txBitrateItem.width = ijkBitrateItem.width;
                    txBitrateItem.height = ijkBitrateItem.height;
                    txBitrateItem.bitrate = ijkBitrateItem.bitrate;
                    list.add(txBitrateItem);
                }
            }
        }
        return list;
    }
    
    public void f(final boolean a) {
        this.a = a;
    }
    
    public void g(final boolean b) {
        final TextureView d = this.d();
        if (d != null) {
            if (this.b.t && (this.f.getMetaRotationDegree() == 90 || this.f.getMetaRotationDegree() == 270)) {
                d.setScaleY(b ? -1.0f : 1.0f);
            }
            else {
                d.setScaleX(b ? -1.0f : 1.0f);
            }
        }
        if (this.h != null) {
            this.h.a(b);
        }
    }
    
    @Override
    public void a(final boolean b, final int n) {
    }
    
    @Override
    public int i() {
        return 0;
    }
}
