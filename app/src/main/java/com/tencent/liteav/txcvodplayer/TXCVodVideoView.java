package com.tencent.liteav.txcvodplayer;

import android.widget.*;
import android.net.*;
import android.content.*;
import com.tencent.liteav.txcvodplayer.a.*;
import com.tencent.liteav.basic.log.*;
import android.support.annotation.*;
import android.util.*;
import android.os.*;
import android.view.*;
import android.media.*;
import java.io.*;
import com.tencent.ijk.media.exo.*;
import com.tencent.liteav.basic.util.*;
import android.annotation.*;
import java.util.*;
import com.tencent.ijk.media.player.*;
import java.lang.ref.*;
import com.google.android.exoplayer2.decoder.*;

public class TXCVodVideoView extends FrameLayout
{
    private String h;
    private Uri i;
    private int j;
    private int k;
    private com.tencent.liteav.txcvodplayer.a.b l;
    private IMediaPlayer m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    protected boolean a;
    private Context w;
    private d x;
    private com.tencent.liteav.txcvodplayer.a y;
    private int z;
    private int A;
    private String B;
    private float C;
    private com.tencent.liteav.txcvodplayer.a.a D;
    private b E;
    private int F;
    private long G;
    private int H;
    private int I;
    protected boolean b;
    protected boolean c;
    protected final int d = 0;
    private long J;
    private boolean K;
    private int L;
    private float M;
    private float N;
    private boolean O;
    private int P;
    private boolean Q;
    private com.tencent.liteav.txcvodplayer.b R;
    IMediaPlayer.OnVideoSizeChangedListener e;
    IMediaPlayer.OnPreparedListener f;
    private IMediaPlayer.OnCompletionListener S;
    private IMediaPlayer.OnInfoListener T;
    private int U;
    private IMediaPlayer.OnErrorListener V;
    private IMediaPlayer.OnHevcVideoDecoderErrorListener W;
    private IMediaPlayer.OnVideoDecoderErrorListener aa;
    private IMediaPlayer.OnBufferingUpdateListener ab;
    private IMediaPlayer.OnSeekCompleteListener ac;
    private IMediaPlayer.OnTimedTextListener ad;
    private IjkMediaPlayer.OnNativeInvokeListener ae;
    private IMediaPlayer.OnHLSKeyErrorListener af;
    com.tencent.liteav.txcvodplayer.a.a g;
    private int ag;
    private e ah;
    private Handler ai;
    private boolean aj;
    
    public int getMetaRotationDegree() {
        return this.s;
    }
    
    public TXCVodVideoView(final Context context) {
        super(context);
        this.h = "TXCVodVideoView";
        this.j = 0;
        this.k = 0;
        this.l = null;
        this.m = null;
        this.a = true;
        this.C = 1.0f;
        this.E = com.tencent.liteav.txcvodplayer.a.b.a();
        this.b = true;
        this.c = true;
        this.K = false;
        this.L = -1;
        this.M = 1.0f;
        this.N = 1.0f;
        this.O = false;
        this.e = new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(final IMediaPlayer mediaPlayer, final int n, final int n2, final int n3, final int n4) {
                final boolean b = (TXCVodVideoView.this.o != n2 && Math.abs(TXCVodVideoView.this.o - n2) > 16) || (TXCVodVideoView.this.n != n && Math.abs(TXCVodVideoView.this.n - n) > 16);
                TXCVodVideoView.this.n = mediaPlayer.getVideoWidth();
                TXCVodVideoView.this.o = mediaPlayer.getVideoHeight();
                TXCVodVideoView.this.z = mediaPlayer.getVideoSarNum();
                TXCVodVideoView.this.A = mediaPlayer.getVideoSarDen();
                if (TXCVodVideoView.this.n != 0 && TXCVodVideoView.this.o != 0) {
                    if (TXCVodVideoView.this.y != null) {
                        TXCVodVideoView.this.y.setVideoSize(TXCVodVideoView.this.n, TXCVodVideoView.this.o);
                        TXCVodVideoView.this.y.setVideoSampleAspectRatio(TXCVodVideoView.this.z, TXCVodVideoView.this.A);
                    }
                    TXCVodVideoView.this.requestLayout();
                }
                if (b) {
                    final Message message = new Message();
                    message.what = 101;
                    message.arg1 = 2009;
                    final Bundle data = new Bundle();
                    data.putString("description", "Resolution change:" + TXCVodVideoView.this.n + "*" + TXCVodVideoView.this.o);
                    data.putInt("EVT_PARAM1", TXCVodVideoView.this.n);
                    data.putInt("EVT_PARAM2", TXCVodVideoView.this.o);
                    message.setData(data);
                    if (TXCVodVideoView.this.ai != null) {
                        TXCVodVideoView.this.ai.sendMessage(message);
                    }
                }
            }
        };
        this.f = new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final IMediaPlayer mediaPlayer) {
                if (TXCVodVideoView.this.j == 1) {
                    TXCVodVideoView.this.a(2013, "VOD ready", "prepared");
                    if (!TXCVodVideoView.this.c) {
                        TXCVodVideoView.this.k = 4;
                        TXCVodVideoView.this.c = true;
                    }
                    TXCVodVideoView.this.j = 2;
                }
                TXCVodVideoView.this.u = 0;
                if (TXCVodVideoView.this.j == -1) {
                    TXCVodVideoView.this.j = 3;
                    TXCVodVideoView.this.k = 3;
                }
                if (TXCVodVideoView.this.ai != null) {
                    TXCVodVideoView.this.ai.sendEmptyMessage(100);
                    TXCVodVideoView.this.ai.sendEmptyMessage(103);
                }
                TXCVodVideoView.this.n = mediaPlayer.getVideoWidth();
                TXCVodVideoView.this.o = mediaPlayer.getVideoHeight();
                if (TXCVodVideoView.this.n != 0 && TXCVodVideoView.this.o != 0) {
                    if (TXCVodVideoView.this.y != null) {
                        TXCVodVideoView.this.y.setVideoSize(TXCVodVideoView.this.n, TXCVodVideoView.this.o);
                        TXCVodVideoView.this.y.setVideoSampleAspectRatio(TXCVodVideoView.this.z, TXCVodVideoView.this.A);
                        if ((!TXCVodVideoView.this.y.shouldWaitForResize() || (TXCVodVideoView.this.p == TXCVodVideoView.this.n && TXCVodVideoView.this.q == TXCVodVideoView.this.o)) && TXCVodVideoView.this.k == 3) {
                            TXCVodVideoView.this.b();
                        }
                    }
                }
                else if (TXCVodVideoView.this.k == 3) {
                    TXCVodVideoView.this.b();
                }
            }
        };
        this.S = new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final IMediaPlayer mediaPlayer) {
                TXCVodVideoView.this.j = 5;
                TXCVodVideoView.this.k = 5;
                TXCVodVideoView.this.a(2006, "Playback completed", "play end");
            }
        };
        this.T = new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                switch (n) {
                    case 700: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    }
                    case 3: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_RENDERING_START:");
                        if (!TXCVodVideoView.this.Q) {
                            TXCVodVideoView.this.a(2003, "VOD displayed the first frame", "render start");
                        }
                        TXCVodVideoView.this.setRate(TXCVodVideoView.this.C);
                        TXCVodVideoView.this.Q = true;
                        break;
                    }
                    case 701: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BUFFERING_START:");
                        TXCVodVideoView.this.a(2007, "Buffer started", "loading start");
                        break;
                    }
                    case 702: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BUFFERING_END: eof " + n2);
                        TXCVodVideoView.this.a(2014, "Buffer ended", "loading end");
                        if (n2 != 0 && TXCVodVideoView.this.i != null && TXCVodVideoView.this.i.getPath() != null && TXCVodVideoView.this.i.getPath().endsWith("m3u8")) {
                            break;
                        }
                        if (TXCVodVideoView.this.k == 3) {
                            TXCVodVideoView.this.a(2004, "Playback started", "playing");
                            break;
                        }
                        break;
                    }
                    case 703: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_NETWORK_BANDWIDTH: " + n2);
                        break;
                    }
                    case 800: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BAD_INTERLEAVING:");
                        break;
                    }
                    case 801: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_NOT_SEEKABLE:");
                        break;
                    }
                    case 802: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_METADATA_UPDATE:");
                        break;
                    }
                    case 901: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        break;
                    }
                    case 902: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        break;
                    }
                    case 10001: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + n2);
                        TXCVodVideoView.this.s = n2;
                        if (TXCVodVideoView.this.a && TXCVodVideoView.this.s > 0) {
                            TXCVodVideoView.this.r = TXCVodVideoView.this.s;
                            if (TXCVodVideoView.this.y != null) {
                                TXCVodVideoView.this.y.setVideoRotation(TXCVodVideoView.this.r);
                            }
                        }
                        TXCVodVideoView.this.a(2011, "Video angle " + TXCVodVideoView.this.s, "rotation " + TXCVodVideoView.this.s);
                        break;
                    }
                    case 10002: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_AUDIO_RENDERING_START:");
                        break;
                    }
                    case 10011: {
                        TXCVodVideoView.this.a(2017, "Video data received", "first video packet");
                        break;
                    }
                }
                return true;
            }
        };
        this.V = new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                TXCLog.e(TXCVodVideoView.this.h, "onError: " + n + "," + n2);
                TXCVodVideoView.this.j = -1;
                TXCVodVideoView.this.k = -1;
                if (n == -1004 && n2 == -2303) {
                    TXCVodVideoView.this.a(n2, "The file does not exist", "file not exist");
                    TXCVodVideoView.this.c();
                    return true;
                }
                if (TXCVodVideoView.this.J != TXCVodVideoView.this.getCurrentPosition()) {
                    TXCVodVideoView.this.U = 0;
                }
                TXCVodVideoView.this.J = TXCVodVideoView.this.getCurrentPosition();
                if (TXCVodVideoView.this.U++ < TXCVodVideoView.this.x.a) {
                    if (TXCVodVideoView.this.ai != null) {
                        TXCVodVideoView.this.ai.sendEmptyMessageDelayed(102, (long)(TXCVodVideoView.this.x.b * 1000.0f));
                    }
                }
                else {
                    TXCVodVideoView.this.a(-2301, "Disconnected from the network. Playback error", "disconnect");
                    TXCVodVideoView.this.c();
                }
                return true;
            }
        };
        this.W = new IMediaPlayer.OnHevcVideoDecoderErrorListener() {
            @Override
            public void onHevcVideoDecoderError(final IMediaPlayer mediaPlayer) {
                Log.d(TXCVodVideoView.this.h, "onHevcVideoDecoderError");
                TXCVodVideoView.this.a(-2304, "Vod H265 decoding failed", "hevc decode fail");
            }
        };
        this.aa = new IMediaPlayer.OnVideoDecoderErrorListener() {
            @Override
            public void onVideoDecoderError(final IMediaPlayer mediaPlayer) {
                Log.d(TXCVodVideoView.this.h, "onVideoDecoderError");
                if (TXCVodVideoView.this.j != 4) {
                    TXCVodVideoView.this.a(2106, "VOD decoding failed", "decode fail");
                }
                if (!TXCVodVideoView.this.Q && TXCVodVideoView.this.x.d) {
                    if (Math.min(TXCVodVideoView.this.o, TXCVodVideoView.this.n) >= 1080) {
                        return;
                    }
                    TXCVodVideoView.this.x.d = false;
                    TXCVodVideoView.this.g();
                }
            }
        };
        this.ab = new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(final IMediaPlayer mediaPlayer, final int n) {
                TXCVodVideoView.this.t = n;
            }
        };
        this.ac = new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(final IMediaPlayer mediaPlayer) {
                TXCLog.v(TXCVodVideoView.this.h, "seek complete");
                TXCVodVideoView.this.K = false;
                if (TXCVodVideoView.this.L >= 0) {
                    TXCVodVideoView.this.a(TXCVodVideoView.this.L);
                }
            }
        };
        this.ad = new IMediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(final IMediaPlayer mediaPlayer, final IjkTimedText ijkTimedText) {
            }
        };
        this.ae = new IjkMediaPlayer.OnNativeInvokeListener() {
            @Override
            public boolean onNativeInvoke(final int n, final Bundle bundle) {
                switch (n) {
                    case 131074: {
                        TXCVodVideoView.this.B = bundle.getString("ip");
                        TXCVodVideoView.this.a(2016, "CTRL_DID_TCP_OPEN", "tcp open");
                        return true;
                    }
                    case 131106: {
                        TXCVodVideoView.this.a(2018, "PLAYER_EVENT_DNS_RESOLVED", "dns resolved");
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }
        };
        this.af = new IMediaPlayer.OnHLSKeyErrorListener() {
            @Override
            public void onHLSKeyError(final IMediaPlayer mediaPlayer) {
                Log.e(TXCVodVideoView.this.h, "onHLSKeyError");
                TXCVodVideoView.this.a(-2305, "HLS decypt key get failed", "hls key error");
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.m.stop();
                    TXCVodVideoView.this.m.release();
                    TXCVodVideoView.this.m = null;
                }
                TXCVodVideoView.this.j = -1;
                TXCVodVideoView.this.k = -1;
            }
        };
        this.g = new com.tencent.liteav.txcvodplayer.a.a() {
            @Override
            public void a(@NonNull final b b, final int n, final int n2, final int n3) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceChanged: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceChanged");
                TXCVodVideoView.this.p = n2;
                TXCVodVideoView.this.q = n3;
                final boolean b2 = TXCVodVideoView.this.k == 3;
                final boolean b3 = !TXCVodVideoView.this.y.shouldWaitForResize() || (TXCVodVideoView.this.n == n2 && TXCVodVideoView.this.o == n3);
                if (TXCVodVideoView.this.m != null && b2 && b3 && TXCVodVideoView.this.k == 3) {
                    TXCVodVideoView.this.b();
                }
            }
            
            @Override
            public void a(@NonNull final b b, final int n, final int n2) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceCreated: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceCreated");
                TXCVodVideoView.this.l = b;
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.a(TXCVodVideoView.this.m, b);
                }
                else {
                    TXCVodVideoView.this.f();
                }
            }
            
            @Override
            public void a(@NonNull final b b) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceDestroyed: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceDestroyed");
                TXCVodVideoView.this.l = null;
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.m.setSurface(null);
                }
                TXCVodVideoView.this.a();
            }
        };
        this.ag = 0;
        this.aj = false;
        this.a(context);
    }
    
    public TXCVodVideoView(final Context context, final AttributeSet set) {
        super(context, set);
        this.h = "TXCVodVideoView";
        this.j = 0;
        this.k = 0;
        this.l = null;
        this.m = null;
        this.a = true;
        this.C = 1.0f;
        this.E = com.tencent.liteav.txcvodplayer.a.b.a();
        this.b = true;
        this.c = true;
        this.K = false;
        this.L = -1;
        this.M = 1.0f;
        this.N = 1.0f;
        this.O = false;
        this.e = new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(final IMediaPlayer mediaPlayer, final int n, final int n2, final int n3, final int n4) {
                final boolean b = (TXCVodVideoView.this.o != n2 && Math.abs(TXCVodVideoView.this.o - n2) > 16) || (TXCVodVideoView.this.n != n && Math.abs(TXCVodVideoView.this.n - n) > 16);
                TXCVodVideoView.this.n = mediaPlayer.getVideoWidth();
                TXCVodVideoView.this.o = mediaPlayer.getVideoHeight();
                TXCVodVideoView.this.z = mediaPlayer.getVideoSarNum();
                TXCVodVideoView.this.A = mediaPlayer.getVideoSarDen();
                if (TXCVodVideoView.this.n != 0 && TXCVodVideoView.this.o != 0) {
                    if (TXCVodVideoView.this.y != null) {
                        TXCVodVideoView.this.y.setVideoSize(TXCVodVideoView.this.n, TXCVodVideoView.this.o);
                        TXCVodVideoView.this.y.setVideoSampleAspectRatio(TXCVodVideoView.this.z, TXCVodVideoView.this.A);
                    }
                    TXCVodVideoView.this.requestLayout();
                }
                if (b) {
                    final Message message = new Message();
                    message.what = 101;
                    message.arg1 = 2009;
                    final Bundle data = new Bundle();
                    data.putString("description", "Resolution change:" + TXCVodVideoView.this.n + "*" + TXCVodVideoView.this.o);
                    data.putInt("EVT_PARAM1", TXCVodVideoView.this.n);
                    data.putInt("EVT_PARAM2", TXCVodVideoView.this.o);
                    message.setData(data);
                    if (TXCVodVideoView.this.ai != null) {
                        TXCVodVideoView.this.ai.sendMessage(message);
                    }
                }
            }
        };
        this.f = new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final IMediaPlayer mediaPlayer) {
                if (TXCVodVideoView.this.j == 1) {
                    TXCVodVideoView.this.a(2013, "VOD ready", "prepared");
                    if (!TXCVodVideoView.this.c) {
                        TXCVodVideoView.this.k = 4;
                        TXCVodVideoView.this.c = true;
                    }
                    TXCVodVideoView.this.j = 2;
                }
                TXCVodVideoView.this.u = 0;
                if (TXCVodVideoView.this.j == -1) {
                    TXCVodVideoView.this.j = 3;
                    TXCVodVideoView.this.k = 3;
                }
                if (TXCVodVideoView.this.ai != null) {
                    TXCVodVideoView.this.ai.sendEmptyMessage(100);
                    TXCVodVideoView.this.ai.sendEmptyMessage(103);
                }
                TXCVodVideoView.this.n = mediaPlayer.getVideoWidth();
                TXCVodVideoView.this.o = mediaPlayer.getVideoHeight();
                if (TXCVodVideoView.this.n != 0 && TXCVodVideoView.this.o != 0) {
                    if (TXCVodVideoView.this.y != null) {
                        TXCVodVideoView.this.y.setVideoSize(TXCVodVideoView.this.n, TXCVodVideoView.this.o);
                        TXCVodVideoView.this.y.setVideoSampleAspectRatio(TXCVodVideoView.this.z, TXCVodVideoView.this.A);
                        if ((!TXCVodVideoView.this.y.shouldWaitForResize() || (TXCVodVideoView.this.p == TXCVodVideoView.this.n && TXCVodVideoView.this.q == TXCVodVideoView.this.o)) && TXCVodVideoView.this.k == 3) {
                            TXCVodVideoView.this.b();
                        }
                    }
                }
                else if (TXCVodVideoView.this.k == 3) {
                    TXCVodVideoView.this.b();
                }
            }
        };
        this.S = new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final IMediaPlayer mediaPlayer) {
                TXCVodVideoView.this.j = 5;
                TXCVodVideoView.this.k = 5;
                TXCVodVideoView.this.a(2006, "Playback completed", "play end");
            }
        };
        this.T = new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                switch (n) {
                    case 700: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    }
                    case 3: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_RENDERING_START:");
                        if (!TXCVodVideoView.this.Q) {
                            TXCVodVideoView.this.a(2003, "VOD displayed the first frame", "render start");
                        }
                        TXCVodVideoView.this.setRate(TXCVodVideoView.this.C);
                        TXCVodVideoView.this.Q = true;
                        break;
                    }
                    case 701: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BUFFERING_START:");
                        TXCVodVideoView.this.a(2007, "Buffer started", "loading start");
                        break;
                    }
                    case 702: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BUFFERING_END: eof " + n2);
                        TXCVodVideoView.this.a(2014, "Buffer ended", "loading end");
                        if (n2 != 0 && TXCVodVideoView.this.i != null && TXCVodVideoView.this.i.getPath() != null && TXCVodVideoView.this.i.getPath().endsWith("m3u8")) {
                            break;
                        }
                        if (TXCVodVideoView.this.k == 3) {
                            TXCVodVideoView.this.a(2004, "Playback started", "playing");
                            break;
                        }
                        break;
                    }
                    case 703: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_NETWORK_BANDWIDTH: " + n2);
                        break;
                    }
                    case 800: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BAD_INTERLEAVING:");
                        break;
                    }
                    case 801: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_NOT_SEEKABLE:");
                        break;
                    }
                    case 802: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_METADATA_UPDATE:");
                        break;
                    }
                    case 901: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        break;
                    }
                    case 902: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        break;
                    }
                    case 10001: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + n2);
                        TXCVodVideoView.this.s = n2;
                        if (TXCVodVideoView.this.a && TXCVodVideoView.this.s > 0) {
                            TXCVodVideoView.this.r = TXCVodVideoView.this.s;
                            if (TXCVodVideoView.this.y != null) {
                                TXCVodVideoView.this.y.setVideoRotation(TXCVodVideoView.this.r);
                            }
                        }
                        TXCVodVideoView.this.a(2011, "Video angle " + TXCVodVideoView.this.s, "rotation " + TXCVodVideoView.this.s);
                        break;
                    }
                    case 10002: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_AUDIO_RENDERING_START:");
                        break;
                    }
                    case 10011: {
                        TXCVodVideoView.this.a(2017, "Video data received", "first video packet");
                        break;
                    }
                }
                return true;
            }
        };
        this.V = new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                TXCLog.e(TXCVodVideoView.this.h, "onError: " + n + "," + n2);
                TXCVodVideoView.this.j = -1;
                TXCVodVideoView.this.k = -1;
                if (n == -1004 && n2 == -2303) {
                    TXCVodVideoView.this.a(n2, "The file does not exist", "file not exist");
                    TXCVodVideoView.this.c();
                    return true;
                }
                if (TXCVodVideoView.this.J != TXCVodVideoView.this.getCurrentPosition()) {
                    TXCVodVideoView.this.U = 0;
                }
                TXCVodVideoView.this.J = TXCVodVideoView.this.getCurrentPosition();
                if (TXCVodVideoView.this.U++ < TXCVodVideoView.this.x.a) {
                    if (TXCVodVideoView.this.ai != null) {
                        TXCVodVideoView.this.ai.sendEmptyMessageDelayed(102, (long)(TXCVodVideoView.this.x.b * 1000.0f));
                    }
                }
                else {
                    TXCVodVideoView.this.a(-2301, "Disconnected from the network. Playback error", "disconnect");
                    TXCVodVideoView.this.c();
                }
                return true;
            }
        };
        this.W = new IMediaPlayer.OnHevcVideoDecoderErrorListener() {
            @Override
            public void onHevcVideoDecoderError(final IMediaPlayer mediaPlayer) {
                Log.d(TXCVodVideoView.this.h, "onHevcVideoDecoderError");
                TXCVodVideoView.this.a(-2304, "Vod H265 decoding failed", "hevc decode fail");
            }
        };
        this.aa = new IMediaPlayer.OnVideoDecoderErrorListener() {
            @Override
            public void onVideoDecoderError(final IMediaPlayer mediaPlayer) {
                Log.d(TXCVodVideoView.this.h, "onVideoDecoderError");
                if (TXCVodVideoView.this.j != 4) {
                    TXCVodVideoView.this.a(2106, "VOD decoding failed", "decode fail");
                }
                if (!TXCVodVideoView.this.Q && TXCVodVideoView.this.x.d) {
                    if (Math.min(TXCVodVideoView.this.o, TXCVodVideoView.this.n) >= 1080) {
                        return;
                    }
                    TXCVodVideoView.this.x.d = false;
                    TXCVodVideoView.this.g();
                }
            }
        };
        this.ab = new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(final IMediaPlayer mediaPlayer, final int n) {
                TXCVodVideoView.this.t = n;
            }
        };
        this.ac = new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(final IMediaPlayer mediaPlayer) {
                TXCLog.v(TXCVodVideoView.this.h, "seek complete");
                TXCVodVideoView.this.K = false;
                if (TXCVodVideoView.this.L >= 0) {
                    TXCVodVideoView.this.a(TXCVodVideoView.this.L);
                }
            }
        };
        this.ad = new IMediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(final IMediaPlayer mediaPlayer, final IjkTimedText ijkTimedText) {
            }
        };
        this.ae = new IjkMediaPlayer.OnNativeInvokeListener() {
            @Override
            public boolean onNativeInvoke(final int n, final Bundle bundle) {
                switch (n) {
                    case 131074: {
                        TXCVodVideoView.this.B = bundle.getString("ip");
                        TXCVodVideoView.this.a(2016, "CTRL_DID_TCP_OPEN", "tcp open");
                        return true;
                    }
                    case 131106: {
                        TXCVodVideoView.this.a(2018, "PLAYER_EVENT_DNS_RESOLVED", "dns resolved");
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }
        };
        this.af = new IMediaPlayer.OnHLSKeyErrorListener() {
            @Override
            public void onHLSKeyError(final IMediaPlayer mediaPlayer) {
                Log.e(TXCVodVideoView.this.h, "onHLSKeyError");
                TXCVodVideoView.this.a(-2305, "HLS decypt key get failed", "hls key error");
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.m.stop();
                    TXCVodVideoView.this.m.release();
                    TXCVodVideoView.this.m = null;
                }
                TXCVodVideoView.this.j = -1;
                TXCVodVideoView.this.k = -1;
            }
        };
        this.g = new com.tencent.liteav.txcvodplayer.a.a() {
            @Override
            public void a(@NonNull final b b, final int n, final int n2, final int n3) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceChanged: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceChanged");
                TXCVodVideoView.this.p = n2;
                TXCVodVideoView.this.q = n3;
                final boolean b2 = TXCVodVideoView.this.k == 3;
                final boolean b3 = !TXCVodVideoView.this.y.shouldWaitForResize() || (TXCVodVideoView.this.n == n2 && TXCVodVideoView.this.o == n3);
                if (TXCVodVideoView.this.m != null && b2 && b3 && TXCVodVideoView.this.k == 3) {
                    TXCVodVideoView.this.b();
                }
            }
            
            @Override
            public void a(@NonNull final b b, final int n, final int n2) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceCreated: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceCreated");
                TXCVodVideoView.this.l = b;
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.a(TXCVodVideoView.this.m, b);
                }
                else {
                    TXCVodVideoView.this.f();
                }
            }
            
            @Override
            public void a(@NonNull final b b) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceDestroyed: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceDestroyed");
                TXCVodVideoView.this.l = null;
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.m.setSurface(null);
                }
                TXCVodVideoView.this.a();
            }
        };
        this.ag = 0;
        this.aj = false;
        this.a(context);
    }
    
    public TXCVodVideoView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.h = "TXCVodVideoView";
        this.j = 0;
        this.k = 0;
        this.l = null;
        this.m = null;
        this.a = true;
        this.C = 1.0f;
        this.E = com.tencent.liteav.txcvodplayer.a.b.a();
        this.b = true;
        this.c = true;
        this.K = false;
        this.L = -1;
        this.M = 1.0f;
        this.N = 1.0f;
        this.O = false;
        this.e = new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(final IMediaPlayer mediaPlayer, final int n, final int n2, final int n3, final int n4) {
                final boolean b = (TXCVodVideoView.this.o != n2 && Math.abs(TXCVodVideoView.this.o - n2) > 16) || (TXCVodVideoView.this.n != n && Math.abs(TXCVodVideoView.this.n - n) > 16);
                TXCVodVideoView.this.n = mediaPlayer.getVideoWidth();
                TXCVodVideoView.this.o = mediaPlayer.getVideoHeight();
                TXCVodVideoView.this.z = mediaPlayer.getVideoSarNum();
                TXCVodVideoView.this.A = mediaPlayer.getVideoSarDen();
                if (TXCVodVideoView.this.n != 0 && TXCVodVideoView.this.o != 0) {
                    if (TXCVodVideoView.this.y != null) {
                        TXCVodVideoView.this.y.setVideoSize(TXCVodVideoView.this.n, TXCVodVideoView.this.o);
                        TXCVodVideoView.this.y.setVideoSampleAspectRatio(TXCVodVideoView.this.z, TXCVodVideoView.this.A);
                    }
                    TXCVodVideoView.this.requestLayout();
                }
                if (b) {
                    final Message message = new Message();
                    message.what = 101;
                    message.arg1 = 2009;
                    final Bundle data = new Bundle();
                    data.putString("description", "Resolution change:" + TXCVodVideoView.this.n + "*" + TXCVodVideoView.this.o);
                    data.putInt("EVT_PARAM1", TXCVodVideoView.this.n);
                    data.putInt("EVT_PARAM2", TXCVodVideoView.this.o);
                    message.setData(data);
                    if (TXCVodVideoView.this.ai != null) {
                        TXCVodVideoView.this.ai.sendMessage(message);
                    }
                }
            }
        };
        this.f = new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final IMediaPlayer mediaPlayer) {
                if (TXCVodVideoView.this.j == 1) {
                    TXCVodVideoView.this.a(2013, "VOD ready", "prepared");
                    if (!TXCVodVideoView.this.c) {
                        TXCVodVideoView.this.k = 4;
                        TXCVodVideoView.this.c = true;
                    }
                    TXCVodVideoView.this.j = 2;
                }
                TXCVodVideoView.this.u = 0;
                if (TXCVodVideoView.this.j == -1) {
                    TXCVodVideoView.this.j = 3;
                    TXCVodVideoView.this.k = 3;
                }
                if (TXCVodVideoView.this.ai != null) {
                    TXCVodVideoView.this.ai.sendEmptyMessage(100);
                    TXCVodVideoView.this.ai.sendEmptyMessage(103);
                }
                TXCVodVideoView.this.n = mediaPlayer.getVideoWidth();
                TXCVodVideoView.this.o = mediaPlayer.getVideoHeight();
                if (TXCVodVideoView.this.n != 0 && TXCVodVideoView.this.o != 0) {
                    if (TXCVodVideoView.this.y != null) {
                        TXCVodVideoView.this.y.setVideoSize(TXCVodVideoView.this.n, TXCVodVideoView.this.o);
                        TXCVodVideoView.this.y.setVideoSampleAspectRatio(TXCVodVideoView.this.z, TXCVodVideoView.this.A);
                        if ((!TXCVodVideoView.this.y.shouldWaitForResize() || (TXCVodVideoView.this.p == TXCVodVideoView.this.n && TXCVodVideoView.this.q == TXCVodVideoView.this.o)) && TXCVodVideoView.this.k == 3) {
                            TXCVodVideoView.this.b();
                        }
                    }
                }
                else if (TXCVodVideoView.this.k == 3) {
                    TXCVodVideoView.this.b();
                }
            }
        };
        this.S = new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final IMediaPlayer mediaPlayer) {
                TXCVodVideoView.this.j = 5;
                TXCVodVideoView.this.k = 5;
                TXCVodVideoView.this.a(2006, "Playback completed", "play end");
            }
        };
        this.T = new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                switch (n) {
                    case 700: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    }
                    case 3: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_RENDERING_START:");
                        if (!TXCVodVideoView.this.Q) {
                            TXCVodVideoView.this.a(2003, "VOD displayed the first frame", "render start");
                        }
                        TXCVodVideoView.this.setRate(TXCVodVideoView.this.C);
                        TXCVodVideoView.this.Q = true;
                        break;
                    }
                    case 701: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BUFFERING_START:");
                        TXCVodVideoView.this.a(2007, "Buffer started", "loading start");
                        break;
                    }
                    case 702: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BUFFERING_END: eof " + n2);
                        TXCVodVideoView.this.a(2014, "Buffer ended", "loading end");
                        if (n2 != 0 && TXCVodVideoView.this.i != null && TXCVodVideoView.this.i.getPath() != null && TXCVodVideoView.this.i.getPath().endsWith("m3u8")) {
                            break;
                        }
                        if (TXCVodVideoView.this.k == 3) {
                            TXCVodVideoView.this.a(2004, "Playback started", "playing");
                            break;
                        }
                        break;
                    }
                    case 703: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_NETWORK_BANDWIDTH: " + n2);
                        break;
                    }
                    case 800: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_BAD_INTERLEAVING:");
                        break;
                    }
                    case 801: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_NOT_SEEKABLE:");
                        break;
                    }
                    case 802: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_METADATA_UPDATE:");
                        break;
                    }
                    case 901: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        break;
                    }
                    case 902: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        break;
                    }
                    case 10001: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + n2);
                        TXCVodVideoView.this.s = n2;
                        if (TXCVodVideoView.this.a && TXCVodVideoView.this.s > 0) {
                            TXCVodVideoView.this.r = TXCVodVideoView.this.s;
                            if (TXCVodVideoView.this.y != null) {
                                TXCVodVideoView.this.y.setVideoRotation(TXCVodVideoView.this.r);
                            }
                        }
                        TXCVodVideoView.this.a(2011, "Video angle " + TXCVodVideoView.this.s, "rotation " + TXCVodVideoView.this.s);
                        break;
                    }
                    case 10002: {
                        TXCLog.i(TXCVodVideoView.this.h, "MEDIA_INFO_AUDIO_RENDERING_START:");
                        break;
                    }
                    case 10011: {
                        TXCVodVideoView.this.a(2017, "Video data received", "first video packet");
                        break;
                    }
                }
                return true;
            }
        };
        this.V = new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                TXCLog.e(TXCVodVideoView.this.h, "onError: " + n + "," + n2);
                TXCVodVideoView.this.j = -1;
                TXCVodVideoView.this.k = -1;
                if (n == -1004 && n2 == -2303) {
                    TXCVodVideoView.this.a(n2, "The file does not exist", "file not exist");
                    TXCVodVideoView.this.c();
                    return true;
                }
                if (TXCVodVideoView.this.J != TXCVodVideoView.this.getCurrentPosition()) {
                    TXCVodVideoView.this.U = 0;
                }
                TXCVodVideoView.this.J = TXCVodVideoView.this.getCurrentPosition();
                if (TXCVodVideoView.this.U++ < TXCVodVideoView.this.x.a) {
                    if (TXCVodVideoView.this.ai != null) {
                        TXCVodVideoView.this.ai.sendEmptyMessageDelayed(102, (long)(TXCVodVideoView.this.x.b * 1000.0f));
                    }
                }
                else {
                    TXCVodVideoView.this.a(-2301, "Disconnected from the network. Playback error", "disconnect");
                    TXCVodVideoView.this.c();
                }
                return true;
            }
        };
        this.W = new IMediaPlayer.OnHevcVideoDecoderErrorListener() {
            @Override
            public void onHevcVideoDecoderError(final IMediaPlayer mediaPlayer) {
                Log.d(TXCVodVideoView.this.h, "onHevcVideoDecoderError");
                TXCVodVideoView.this.a(-2304, "Vod H265 decoding failed", "hevc decode fail");
            }
        };
        this.aa = new IMediaPlayer.OnVideoDecoderErrorListener() {
            @Override
            public void onVideoDecoderError(final IMediaPlayer mediaPlayer) {
                Log.d(TXCVodVideoView.this.h, "onVideoDecoderError");
                if (TXCVodVideoView.this.j != 4) {
                    TXCVodVideoView.this.a(2106, "VOD decoding failed", "decode fail");
                }
                if (!TXCVodVideoView.this.Q && TXCVodVideoView.this.x.d) {
                    if (Math.min(TXCVodVideoView.this.o, TXCVodVideoView.this.n) >= 1080) {
                        return;
                    }
                    TXCVodVideoView.this.x.d = false;
                    TXCVodVideoView.this.g();
                }
            }
        };
        this.ab = new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(final IMediaPlayer mediaPlayer, final int n) {
                TXCVodVideoView.this.t = n;
            }
        };
        this.ac = new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(final IMediaPlayer mediaPlayer) {
                TXCLog.v(TXCVodVideoView.this.h, "seek complete");
                TXCVodVideoView.this.K = false;
                if (TXCVodVideoView.this.L >= 0) {
                    TXCVodVideoView.this.a(TXCVodVideoView.this.L);
                }
            }
        };
        this.ad = new IMediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(final IMediaPlayer mediaPlayer, final IjkTimedText ijkTimedText) {
            }
        };
        this.ae = new IjkMediaPlayer.OnNativeInvokeListener() {
            @Override
            public boolean onNativeInvoke(final int n, final Bundle bundle) {
                switch (n) {
                    case 131074: {
                        TXCVodVideoView.this.B = bundle.getString("ip");
                        TXCVodVideoView.this.a(2016, "CTRL_DID_TCP_OPEN", "tcp open");
                        return true;
                    }
                    case 131106: {
                        TXCVodVideoView.this.a(2018, "PLAYER_EVENT_DNS_RESOLVED", "dns resolved");
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }
        };
        this.af = new IMediaPlayer.OnHLSKeyErrorListener() {
            @Override
            public void onHLSKeyError(final IMediaPlayer mediaPlayer) {
                Log.e(TXCVodVideoView.this.h, "onHLSKeyError");
                TXCVodVideoView.this.a(-2305, "HLS decypt key get failed", "hls key error");
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.m.stop();
                    TXCVodVideoView.this.m.release();
                    TXCVodVideoView.this.m = null;
                }
                TXCVodVideoView.this.j = -1;
                TXCVodVideoView.this.k = -1;
            }
        };
        this.g = new com.tencent.liteav.txcvodplayer.a.a() {
            @Override
            public void a(@NonNull final b b, final int n, final int n2, final int n3) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceChanged: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceChanged");
                TXCVodVideoView.this.p = n2;
                TXCVodVideoView.this.q = n3;
                final boolean b2 = TXCVodVideoView.this.k == 3;
                final boolean b3 = !TXCVodVideoView.this.y.shouldWaitForResize() || (TXCVodVideoView.this.n == n2 && TXCVodVideoView.this.o == n3);
                if (TXCVodVideoView.this.m != null && b2 && b3 && TXCVodVideoView.this.k == 3) {
                    TXCVodVideoView.this.b();
                }
            }
            
            @Override
            public void a(@NonNull final b b, final int n, final int n2) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceCreated: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceCreated");
                TXCVodVideoView.this.l = b;
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.a(TXCVodVideoView.this.m, b);
                }
                else {
                    TXCVodVideoView.this.f();
                }
            }
            
            @Override
            public void a(@NonNull final b b) {
                if (b.a() != TXCVodVideoView.this.y) {
                    TXCLog.e(TXCVodVideoView.this.h, "onSurfaceDestroyed: unmatched render callback\n");
                    return;
                }
                TXCLog.i(TXCVodVideoView.this.h, "onSurfaceDestroyed");
                TXCVodVideoView.this.l = null;
                if (TXCVodVideoView.this.m != null) {
                    TXCVodVideoView.this.m.setSurface(null);
                }
                TXCVodVideoView.this.a();
            }
        };
        this.ag = 0;
        this.aj = false;
        this.a(context);
    }
    
    private void a(final Context context) {
        this.w = context.getApplicationContext();
        this.x = new d();
        this.i();
        this.n = 0;
        this.o = 0;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.requestFocus();
        this.j = 0;
        this.k = 0;
        final Looper mainLooper;
        if ((mainLooper = Looper.getMainLooper()) != null) {
            this.ai = new a(this, mainLooper);
        }
        else {
            this.ai = null;
        }
        this.R = new b();
    }
    
    public void setRenderView(final com.tencent.liteav.txcvodplayer.a y) {
        TXCLog.i(this.h, "setRenderView " + y);
        if (this.y != null) {
            if (this.m != null) {
                this.m.setDisplay(null);
            }
            final View view = this.y.getView();
            this.y.removeRenderCallback(this.g);
            this.y = null;
            if (view.getParent() == this) {
                this.removeView(view);
            }
        }
        if (y == null) {
            return;
        }
        (this.y = y).setAspectRatio(this.ag);
        if (this.n > 0 && this.o > 0) {
            y.setVideoSize(this.n, this.o);
        }
        if (this.z > 0 && this.A > 0) {
            y.setVideoSampleAspectRatio(this.z, this.A);
        }
        final View view2 = this.y.getView();
        view2.setLayoutParams((ViewGroup.LayoutParams)new LayoutParams(-2, -2, 17));
        if (view2.getParent() == null) {
            this.addView(view2);
        }
        this.y.addRenderCallback(this.g);
        this.y.setVideoRotation(this.r);
    }
    
    public void setRender(final int n) {
        switch (n) {
            case 0: {
                this.setRenderView(null);
                break;
            }
            case 2: {
                final TextureRenderView renderView = new TextureRenderView(this.w);
                if (this.m != null) {
                    renderView.getSurfaceHolder().a(this.m);
                    renderView.setVideoSize(this.m.getVideoWidth(), this.m.getVideoHeight());
                    renderView.setVideoSampleAspectRatio(this.m.getVideoSarNum(), this.m.getVideoSarDen());
                    renderView.setAspectRatio(this.ag);
                }
                this.setRenderView(renderView);
                break;
            }
            case 1: {
                this.setRenderView(new SurfaceRenderView(this.w));
                break;
            }
            default: {
                TXCLog.e(this.h, String.format(Locale.getDefault(), "invalid render %d\n", n));
                break;
            }
        }
    }
    
    public void setTextureRenderView(final TextureRenderView renderView) {
        TXCLog.i(this.h, "setTextureRenderView " + renderView);
        if (this.m != null) {
            renderView.getSurfaceHolder().a(this.m);
            renderView.setVideoSize(this.m.getVideoWidth(), this.m.getVideoHeight());
            renderView.setVideoSampleAspectRatio(this.m.getVideoSarNum(), this.m.getVideoSarDen());
            renderView.setAspectRatio(this.ag);
        }
        this.setRenderView(renderView);
    }
    
    public void setRenderSurface(final Surface surface) {
        this.l = new com.tencent.liteav.txcvodplayer.a.b() {
            @Override
            public void a(final IMediaPlayer mediaPlayer) {
                mediaPlayer.setSurface(surface);
            }
            
            @NonNull
            @Override
            public a a() {
                return TXCVodVideoView.this.y;
            }
        };
        if (this.m != null) {
            this.a(this.m, this.l);
        }
    }
    
    public void setVideoPath(final String s) {
        this.setVideoURI(Uri.parse(s));
    }
    
    public void setVideoURI(final Uri i) {
        this.i = i;
        this.v = 0;
        this.I = 0;
        this.U = 0;
        this.B = null;
        TXCLog.i(this.h, "setVideoURI " + i);
        this.f();
        this.requestLayout();
        this.invalidate();
    }
    
    @TargetApi(23)
    private boolean f() {
        TXCLog.i(this.h, "openVideo");
        if (this.i == null) {
            return false;
        }
        this.a(false);
        if (this.b) {
            ((AudioManager)this.w.getSystemService("audio")).requestAudioFocus((AudioManager.OnAudioFocusChangeListener)null, 3, 1);
        }
        try {
            String dataSource = this.i.toString();
            if (dataSource.startsWith("/") && !new File(dataSource).exists()) {
                throw new FileNotFoundException();
            }
            AbstractMediaPlayer abstractMediaPlayer;
            switch (false) {
                case 1: {
                    abstractMediaPlayer = new IjkExoMediaPlayer(this.w);
                    TXCLog.i(this.h, "exo media player " + abstractMediaPlayer);
                    break;
                }
                case 2: {
                    abstractMediaPlayer = new AndroidMediaPlayer();
                    TXCLog.i(this.h, "android media player " + abstractMediaPlayer);
                    break;
                }
                default: {
                    IjkMediaPlayer ijkMediaPlayer = null;
                    if (this.i != null) {
                        ijkMediaPlayer = new IjkMediaPlayer(new IjkLibLoader() {
                            @Override
                            public void loadLibrary(final String s) throws UnsatisfiedLinkError, SecurityException {
                                com.tencent.liteav.basic.util.f.a(s);
                            }
                        });
                        IjkMediaPlayer.native_setLogLevel(3);
                        ijkMediaPlayer.setOnNativeInvokeListener(this.ae);
                        if (this.x.d) {
                            ijkMediaPlayer.setOption(4, "mediacodec", 1L);
                            ijkMediaPlayer.setOption(4, "mediacodec-hevc", 1L);
                        }
                        else {
                            ijkMediaPlayer.setOption(4, "mediacodec", 0L);
                        }
                        TXCLog.i(this.h, "ijk mediacodec " + this.x.d);
                        ijkMediaPlayer.setOption(4, "mediacodec-auto-rotate", 0L);
                        ijkMediaPlayer.setOption(4, "mediacodec-handle-resolution-change", 0L);
                        ijkMediaPlayer.setOption(4, "opensles", 0L);
                        ijkMediaPlayer.setOption(4, "overlay-format", 842225234L);
                        ijkMediaPlayer.setOption(4, "framedrop", 1L);
                        ijkMediaPlayer.setOption(4, "soundtouch", 1L);
                        ijkMediaPlayer.setOption(4, "max-fps", 30L);
                        if (this.c && this.k != 4) {
                            ijkMediaPlayer.setOption(4, "start-on-prepared", 1L);
                        }
                        else {
                            ijkMediaPlayer.setOption(4, "start-on-prepared", 0L);
                        }
                        ijkMediaPlayer.setOption(4, "load-on-prepared", 1L);
                        ijkMediaPlayer.setOption(1, "http-detect-range-support", 0L);
                        ijkMediaPlayer.setOption(2, "skip_loop_filter", 0L);
                        ijkMediaPlayer.setOption(2, "skip_frame", 0L);
                        ijkMediaPlayer.setOption(1, "timeout", (int)(this.x.c * 1000.0f * 1000.0f));
                        ijkMediaPlayer.setOption(1, "reconnect", 1L);
                        ijkMediaPlayer.setOption(1, "analyzeduration", 90000000L);
                        ijkMediaPlayer.setOption(4, "enable-accurate-seek", this.x.i ? 1 : 0);
                        ijkMediaPlayer.setOption(4, "disable-bitrate-sync", this.x.j ? 0 : 1);
                        ijkMediaPlayer.setOption(1, "dns_cache_timeout", 0L);
                        ijkMediaPlayer.setOption(1, "cache_max_capacity", 2147483647L);
                        if (this.u > 0) {
                            ijkMediaPlayer.setOption(4, "seek-at-start", this.u);
                            TXCLog.i(this.h, "ijk start time " + this.u);
                        }
                        if (this.x.m > 0) {
                            ijkMediaPlayer.setOption(4, "max-buffer-size", this.x.m * 1024 * 1024);
                            TXCLog.i(this.h, "ijk max buffer size " + this.x.m);
                        }
                        if (this.x.h != null) {
                            String s = null;
                            for (final String s2 : this.x.h.keySet()) {
                                if (s == null) {
                                    s = String.format("%s: %s", s2, this.x.h.get(s2));
                                }
                                else {
                                    s = s + "\r\n" + String.format("%s: %s", s2, this.x.h.get(s2));
                                }
                            }
                            ijkMediaPlayer.setOption(1, "headers", s);
                        }
                        ijkMediaPlayer.setBitrateIndex(this.P);
                        IjkMediaPlayer.native_setLogLevel(5);
                        if (this.x.e != null && this.E.e(dataSource)) {
                            this.E.b(this.x.e);
                            this.E.a(this.x.f);
                            this.D = this.E.d(dataSource);
                            if (this.D != null) {
                                if (this.D.a() != null) {
                                    ijkMediaPlayer.setOption(1, "cache_file_path", this.D.a());
                                    dataSource = "ijkio:cache:ffio:" + this.i.toString();
                                }
                                else if (this.D.b() != null) {
                                    ijkMediaPlayer.setOption(1, "cache_db_path", this.D.b());
                                    dataSource = "ijkhlscache:" + this.i.toString();
                                }
                            }
                        }
                    }
                    abstractMediaPlayer = ijkMediaPlayer;
                    TXCLog.i(this.h, "ijk media player " + abstractMediaPlayer);
                    break;
                }
            }
            (this.m = new TextureMediaPlayer(abstractMediaPlayer)).setDataSource(dataSource);
            this.m.setOnPreparedListener(this.f);
            this.m.setOnVideoSizeChangedListener(this.e);
            this.m.setOnCompletionListener(this.S);
            this.m.setOnErrorListener(this.V);
            this.m.setOnInfoListener(this.T);
            this.m.setOnBufferingUpdateListener(this.ab);
            this.m.setOnSeekCompleteListener(this.ac);
            this.m.setOnTimedTextListener(this.ad);
            this.m.setOnHLSKeyErrorListener(this.af);
            this.m.setOnHevcVideoDecoderErrorListener(this.W);
            this.m.setOnVideoDecoderErrorListener(this.aa);
            this.t = 0;
            this.a(this.m, this.l);
            this.m.setAudioStreamType(3);
            this.m.setScreenOnWhilePlaying(true);
            this.m.prepareAsync();
            this.m.setVolume(this.M, this.N);
            this.setMute(this.O);
            if (this.R != null) {
                this.R.a(this.m);
            }
            this.j = 1;
        }
        catch (FileNotFoundException ex2) {
            this.j = -1;
            this.k = -1;
            this.V.onError(this.m, -1004, -2303);
        }
        catch (Exception ex) {
            TXCLog.w(this.h, ex.toString());
            this.j = -1;
            this.k = -1;
            this.V.onError(this.m, 1, 0);
        }
        return true;
    }
    
    private void a(final IMediaPlayer mediaPlayer, final com.tencent.liteav.txcvodplayer.a.b b) {
        if (mediaPlayer == null) {
            return;
        }
        if (b == null) {
            mediaPlayer.setDisplay(null);
            return;
        }
        TXCLog.i(this.h, "bindSurfaceHolder");
        b.a(mediaPlayer);
    }
    
    void a() {
        if (this.m != null) {
            this.m.setDisplay(null);
        }
    }
    
    void a(final boolean b) {
        if (this.m != null) {
            TXCLog.i(this.h, "release player " + this.m);
            this.m.release();
            this.m = null;
            this.j = 0;
            if (b) {
                this.k = 0;
                this.n = 0;
                this.o = 0;
            }
            if (this.b) {
                ((AudioManager)this.w.getSystemService("audio")).abandonAudioFocus((AudioManager.OnAudioFocusChangeListener)null);
            }
        }
    }
    
    public void b() {
        TXCLog.i(this.h, "start");
        if (this.h()) {
            this.m.start();
            if (this.j != 3 && !this.K) {
                this.j = 3;
                this.a(2004, "Playback started", "playing");
            }
        }
        this.k = 3;
    }
    
    private void g() {
        TXCLog.i(this.h, "replay");
        if (this.u == 0 && this.m != null && this.v > 0) {
            this.u = (int)this.m.getCurrentPosition();
        }
        if (!this.f()) {
            this.a(false);
        }
    }
    
    public void c() {
        if (this.m != null) {
            if (this.D != null) {
                if (this.getDuration() <= 0) {
                    this.E.a(this.D.d(), true);
                }
                else {
                    this.E.a(this.D.d(), false);
                }
                this.D = null;
            }
            this.m.stop();
            this.m.release();
            this.m = null;
            this.i = null;
            this.n = 0;
            this.o = 0;
            this.C = 1.0f;
            this.K = false;
            this.L = -1;
            this.j = 0;
            this.k = 0;
            this.Q = false;
            this.P = 0;
            if (this.R != null) {
                this.R.a((IMediaPlayer)null);
            }
            if (this.b) {
                ((AudioManager)this.w.getSystemService("audio")).abandonAudioFocus((AudioManager.OnAudioFocusChangeListener)null);
            }
        }
        if (this.ai != null) {
            this.ai.removeMessages(102);
        }
        TXCLog.i(this.h, "stop");
    }
    
    public void d() {
        this.k = 4;
        TXCLog.i(this.h, "pause");
        if (this.h() && this.m.isPlaying()) {
            this.m.pause();
            this.j = 4;
        }
    }
    
    public int getDuration() {
        if (this.m != null && this.v < 1) {
            this.v = (int)this.m.getDuration();
        }
        return this.v;
    }
    
    public int getCurrentPosition() {
        if (this.K && this.L >= 0) {
            return this.L;
        }
        if (this.u > 0) {
            return this.u;
        }
        if (this.m == null) {
            return 0;
        }
        final boolean b = false;
        final int i = (int)this.m.getCurrentPosition();
        if (i <= 1) {
            return b ? Math.max(i, this.I) : i;
        }
        return this.I = i;
    }
    
    public void a(int l) {
        TXCLog.i(this.h, "seek to " + l);
        if (this.getUrlPathExtention().equals("m3u8")) {
            l = Math.min(l, this.getDuration() - 1000);
        }
        if (l < 0) {
            return;
        }
        if (this.h()) {
            if (l > this.getDuration()) {
                l = this.getDuration();
            }
            if (this.K) {
                this.L = l;
            }
            else {
                this.L = -1;
                this.m.seekTo(l);
            }
            this.K = true;
        }
    }
    
    public void setMute(final boolean o) {
        this.O = o;
        if (this.m == null) {
            return;
        }
        if (o) {
            this.m.setVolume(0.0f, 0.0f);
        }
        else {
            this.m.setVolume(this.M, this.N);
        }
    }
    
    public void setVolume(final int n) {
        this.M = n / 100.0f;
        this.N = n / 100.0f;
        if (this.m != null) {
            this.m.setVolume(this.M, this.N);
        }
    }
    
    public boolean e() {
        return this.h() && this.m.isPlaying() && this.j != 4;
    }
    
    public int getBufferDuration() {
        int n = 0;
        if (this.m != null) {
            this.getUnwrappedMediaPlayer();
            n = this.t * this.getDuration() / 100;
            if (n < this.getCurrentPosition()) {
                n = this.getCurrentPosition();
            }
            if (Math.abs(this.getDuration() - n) < 1000) {
                n = this.getDuration();
            }
        }
        return n;
    }
    
    private boolean h() {
        return this.m != null && this.j != -1 && this.j != 0 && this.j != 1;
    }
    
    public void setConfig(final d x) {
        if (x != null) {
            this.x = x;
            this.E.a(this.x.k);
        }
    }
    
    public void setRenderMode(final int ag) {
        this.ag = ag;
        if (this.y != null) {
            this.y.setAspectRatio(this.ag);
        }
        if (this.y != null) {
            this.y.setVideoRotation(this.r);
        }
    }
    
    public void setVideoRotationDegree(int r) {
        switch (r) {
            case 360: {
                r = 0;
            }
            case 0:
            case 90:
            case 180:
            case 270: {
                this.r = r;
                if (this.y != null) {
                    this.y.setVideoRotation(this.r);
                }
                if (this.y != null) {
                    this.y.setAspectRatio(this.ag);
                }
            }
            default: {
                TXCLog.e(this.h, "not support degree " + r);
            }
        }
    }
    
    public int getVideoRotationDegree() {
        return this.r;
    }
    
    private void i() {
        this.setRender(0);
    }
    
    public boolean b(final boolean b) {
        if (this.j == 0) {
            this.b = b;
            return true;
        }
        return false;
    }
    
    public void setAutoPlay(final boolean c) {
        this.c = c;
    }
    
    public void setRate(final float n) {
        TXCLog.i(this.h, "setRate " + n);
        if (this.m != null) {
            this.m.setRate(n);
        }
        this.C = n;
    }
    
    public void setStartTime(final float n) {
        this.u = (int)(n * 1000.0f);
    }
    
    public void setAutoRotate(final boolean a) {
        this.a = a;
    }
    
    private void a(final int arg1, final String s, final String s2) {
        if ((arg1 == -2304 || arg1 == 2106) && this.aj) {
            return;
        }
        final Message message = new Message();
        message.what = 101;
        final Bundle data = new Bundle();
        message.arg1 = arg1;
        data.putString("description", s);
        message.setData(data);
        if (this.ai != null) {
            this.ai.sendMessage(message);
        }
        if (arg1 != 2018) {
            if (arg1 != 2016) {
                TXCLog.i(this.h, "sendSimpleEvent " + arg1 + " " + s2);
            }
        }
        this.aj = (arg1 == -2304 || arg1 == 2106);
    }
    
    public void setListener(final e ah) {
        this.ah = ah;
    }
    
    public int getVideoWidth() {
        return this.n;
    }
    
    public int getVideoHeight() {
        return this.o;
    }
    
    public String getServerIp() {
        return this.B;
    }
    
    public int getPlayerType() {
        return 0;
    }
    
    public void setPlayerType(final int n) {
    }
    
    @NonNull
    String getUrlPathExtention() {
        if (this.i != null && this.i.getPath() != null) {
            final String path = this.i.getPath();
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
        return "";
    }
    
    public IMediaPlayer getUnwrappedMediaPlayer() {
        if (this.m instanceof TextureMediaPlayer) {
            return ((TextureMediaPlayer)this.m).getBackEndMediaPlayer();
        }
        return this.m;
    }
    
    public int getBitrateIndex() {
        if (this.m != null) {
            return this.m.getBitrateIndex();
        }
        return this.P;
    }
    
    public void setBitrateIndex(final int n) {
        TXCLog.i(this.h, "setBitrateIndex " + n);
        if (this.P == n) {
            return;
        }
        this.P = n;
        if (this.m != null) {
            if (this.x.j) {
                this.m.setBitrateIndex(n);
            }
            else {
                this.g();
            }
        }
    }
    
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        if (this.m != null) {
            return this.m.getSupportedBitrates();
        }
        return new ArrayList<IjkBitrateItem>();
    }
    
    public MediaInfo getMediaInfo() {
        if (this.m == null) {
            return null;
        }
        return this.m.getMediaInfo();
    }
    
    private static class a extends Handler
    {
        private final WeakReference<TXCVodVideoView> a;
        private final int b = 500;
        
        public a(final TXCVodVideoView txcVodVideoView, final Looper looper) {
            super(looper);
            this.a = new WeakReference<TXCVodVideoView>(txcVodVideoView);
        }
        
        public void handleMessage(final Message message) {
            final TXCVodVideoView txcVodVideoView = this.a.get();
            if (txcVodVideoView == null || txcVodVideoView.ah == null) {
                return;
            }
            switch (message.what) {
                case 100: {
                    float videoOutputFramesPerSecond = 0.0f;
                    float videoDecodeFramesPerSecond = 0.0f;
                    long n = 0L;
                    long bitRate = 0L;
                    long tcpSpeed = 0L;
                    final IMediaPlayer unwrappedMediaPlayer = txcVodVideoView.getUnwrappedMediaPlayer();
                    if (unwrappedMediaPlayer == null) {
                        return;
                    }
                    if (unwrappedMediaPlayer instanceof IjkMediaPlayer) {
                        final IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer)unwrappedMediaPlayer;
                        videoOutputFramesPerSecond = ijkMediaPlayer.getVideoOutputFramesPerSecond();
                        videoDecodeFramesPerSecond = ijkMediaPlayer.getVideoDecodeFramesPerSecond();
                        n = ijkMediaPlayer.getVideoCachedBytes() + ijkMediaPlayer.getAudioCachedBytes();
                        bitRate = ijkMediaPlayer.getBitRate();
                        tcpSpeed = ijkMediaPlayer.getTcpSpeed();
                    }
                    else if (unwrappedMediaPlayer instanceof IjkExoMediaPlayer) {
                        final IjkExoMediaPlayer ijkExoMediaPlayer = (IjkExoMediaPlayer)unwrappedMediaPlayer;
                        final DecoderCounters videoDecoderCounters = ijkExoMediaPlayer.getVideoDecoderCounters();
                        if (videoDecoderCounters != null) {
                            final long n2 = System.currentTimeMillis() - txcVodVideoView.G;
                            final int n3 = videoDecoderCounters.renderedOutputBufferCount - txcVodVideoView.F;
                            txcVodVideoView.G = System.currentTimeMillis();
                            txcVodVideoView.F = videoDecoderCounters.renderedOutputBufferCount;
                            if (n2 < 3000L && n2 > 0L && n3 < 120 && n3 > 0) {
                                txcVodVideoView.H = (int)Math.ceil(1000.0 / n2 * n3);
                            }
                        }
                        videoOutputFramesPerSecond = (float)txcVodVideoView.H;
                        bitRate = ijkExoMediaPlayer.getObservedBitrate();
                        tcpSpeed = bitRate / 8L;
                    }
                    final Bundle bundle = new Bundle();
                    bundle.putFloat("fps", videoOutputFramesPerSecond);
                    bundle.putFloat("dps", videoDecodeFramesPerSecond);
                    bundle.putLong("cachedBytes", n);
                    bundle.putLong("bitRate", bitRate);
                    bundle.putLong("tcpSpeed", tcpSpeed);
                    txcVodVideoView.ah.a(bundle);
                    this.removeMessages(100);
                    this.sendEmptyMessageDelayed(100, 500L);
                    break;
                }
                case 101: {
                    final int arg1 = message.arg1;
                    switch (arg1) {
                    }
                    txcVodVideoView.ah.a(arg1, message.getData());
                    break;
                }
                case 102: {
                    txcVodVideoView.g();
                    txcVodVideoView.a(2103, "VOD network reconnected", "reconnect");
                    break;
                }
                case 103: {
                    final long n4 = txcVodVideoView.getCurrentPosition();
                    final Bundle bundle2 = new Bundle();
                    final long n5 = txcVodVideoView.getBufferDuration();
                    final long n6 = txcVodVideoView.getDuration();
                    bundle2.putInt("EVT_PLAY_PROGRESS", (int)(n4 / 1000L));
                    bundle2.putInt("EVT_PLAY_DURATION", (int)(n6 / 1000L));
                    bundle2.putInt("EVT_PLAYABLE_DURATION", (int)(n5 / 1000L));
                    bundle2.putInt("EVT_PLAY_PROGRESS_MS", (int)n4);
                    bundle2.putInt("EVT_PLAY_DURATION_MS", (int)n6);
                    bundle2.putInt("EVT_PLAYABLE_DURATION_MS", (int)n5);
                    txcVodVideoView.ah.a(2005, bundle2);
                    if (txcVodVideoView.m != null) {
                        this.removeMessages(103);
                        if (txcVodVideoView.x.l <= 0) {
                            txcVodVideoView.x.l = 500;
                        }
                        this.sendEmptyMessageDelayed(103, (long)txcVodVideoView.x.l);
                        break;
                    }
                    break;
                }
            }
        }
    }
}
