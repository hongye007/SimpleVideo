package com.tencent.rtmp;

import com.tencent.rtmp.ui.*;
import android.content.*;
import com.tencent.liteav.qos.*;
import com.tencent.liteav.*;
import com.tencent.ugc.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import java.lang.ref.*;
import com.tencent.liteav.basic.license.*;
import android.text.*;
import android.view.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.audio.*;
import java.io.*;
import com.tencent.liteav.basic.datareport.*;
import java.text.*;
import android.os.*;
import android.graphics.*;
import javax.microedition.khronos.egl.*;
import com.tencent.liteav.basic.module.*;
import com.tencent.liteav.basic.structs.*;
import java.util.*;
import android.media.*;
import android.annotation.*;
import com.tencent.liteav.network.*;
import com.tencent.liteav.audio.impl.*;
import java.nio.*;

public class b implements d, b, CaptureAndEnc.a, com.tencent.liteav.qos.a, r
{
    private static final String d;
    private TXCloudVideoView e;
    private TXLivePushConfig f;
    private ITXLivePushListener g;
    private int h;
    private TXLivePusher.VideoCustomProcessListener i;
    private TXLivePusher.AudioCustomProcessListener j;
    private i k;
    private CaptureAndEnc l;
    private TXCStreamUploader m;
    private Context n;
    private Handler o;
    private TXCQoS p;
    private e q;
    private String r;
    private String s;
    private boolean t;
    private int u;
    private int v;
    private boolean w;
    private boolean x;
    TXLivePusher.OnBGMNotify a;
    private HashSet<String> y;
    private HashMap<Integer, Long> z;
    final TXAudioEffectManager.TXVoiceReverbType[] b;
    final TXAudioEffectManager.TXVoiceChangerType[] c;
    private ArrayList<b> A;
    private TXLivePusher.ITXAudioVolumeEvaluationListener B;
    private int C;
    private com.tencent.liteav.basic.b.a D;
    private com.tencent.liteav.audio.e E;
    private a F;
    private Runnable G;
    private c H;
    private boolean I;
    private String J;
    private long K;
    private boolean L;
    private TXRecordCommon.ITXVideoRecordListener M;
    private boolean N;
    
    public b(final Context context) {
        this.f = null;
        this.g = null;
        this.h = -1;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = "";
        this.s = "";
        this.t = false;
        this.u = -1;
        this.v = -1;
        this.w = false;
        this.x = false;
        this.y = new HashSet<String>();
        this.z = new HashMap<Integer, Long>();
        this.b = new TXAudioEffectManager.TXVoiceReverbType[] { TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_0, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_1, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_2, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_3, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_4, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_5, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_6, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_7 };
        this.c = new TXAudioEffectManager.TXVoiceChangerType[] { TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_0, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_1, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_2, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_3, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_4, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_5, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_6, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_7, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_8, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_9, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_10, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_11 };
        this.A = new ArrayList<b>();
        this.B = null;
        this.C = 0;
        this.D = new com.tencent.liteav.basic.b.a() {
            @Override
            public void onEvent(final String s, final int n, final String s2, final String s3) {
                TXCLog.i(com.tencent.rtmp.b.d, "onEvent => id:" + s + " code:" + n + " msg:" + s2 + " params:" + s3);
                if (com.tencent.rtmp.b.this.g != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putInt("EVT_ID", n);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (s2 != null) {
                        bundle.putCharSequence("EVT_MSG", (CharSequence)(s2 + ((s3 != null) ? s3 : "")));
                    }
                    com.tencent.rtmp.b.this.onNotifyEvent(n, bundle);
                }
            }
            
            @Override
            public void onError(final String s, final int n, final String s2, final String s3) {
                TXCLog.e(com.tencent.rtmp.b.d, "onError => id:" + s + " code:" + n + " msg:" + s2 + " params:" + s3);
                if (com.tencent.rtmp.b.this.g != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putInt("EVT_ID", n);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (s2 != null) {
                        bundle.putCharSequence("EVT_MSG", (CharSequence)(s2 + ((s3 != null) ? s3 : "")));
                    }
                    com.tencent.rtmp.b.this.onNotifyEvent(n, bundle);
                }
            }
        };
        this.E = new com.tencent.liteav.audio.e() {
            @Override
            public void onPlayStart() {
                if (com.tencent.rtmp.b.this.a != null) {
                    com.tencent.rtmp.b.this.a.onBGMStart();
                }
            }
            
            @Override
            public void onPlayEnd(final int n) {
                if (com.tencent.rtmp.b.this.a != null) {
                    com.tencent.rtmp.b.this.a.onBGMComplete(n);
                }
            }
            
            @Override
            public void onPlayProgress(final long n, final long n2) {
                if (com.tencent.rtmp.b.this.a != null) {
                    com.tencent.rtmp.b.this.a.onBGMProgress(n, n2);
                }
            }
        };
        this.F = null;
        this.G = new Runnable() {
            @Override
            public void run() {
                com.tencent.rtmp.b.this.t = false;
            }
        };
        this.H = null;
        this.I = false;
        this.J = "";
        this.K = 0L;
        this.L = false;
        this.N = false;
        this.f = new TXLivePushConfig();
        this.k = new i();
        TXCCommonUtil.setAppContext(this.n = context.getApplicationContext());
        TXCLog.init();
        this.o = new Handler(Looper.getMainLooper());
        com.tencent.liteav.basic.d.b.a().a(this.n);
        TXCAudioEngine.CreateInstance(this.n, com.tencent.liteav.basic.d.b.a().b());
        TXCAudioEngine.getInstance().clean();
        TXCAudioEngine.getInstance().addEventCallback(new WeakReference<com.tencent.liteav.basic.b.a>(this.D));
        final long a = com.tencent.liteav.basic.d.b.a().a("Audio", "EnableAutoRestartDevice");
        TXCAudioEngine.getInstance().enableAutoRestartDevice(a == 1L || a == -1L);
        (this.l = new CaptureAndEnc(this.n)).d(true);
        this.l.a((b)this);
        LicenceCheck.a().b(null, this.n);
        TXCTimeUtil.initAppStartTime();
        this.z.put(-1303, 0L);
        this.z.put(1101, 0L);
        this.z.put(1006, 0L);
    }
    
    public void a(TXLivePushConfig f) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setConfig " + f + ", " + this);
        if (f == null) {
            f = new TXLivePushConfig();
        }
        this.b(this.f = f);
        this.J();
        Monitor.a(1, String.format("setConfig:[fps:%d][resolution:%d*%d][bitrate:%dkbps][minBitrate:%dkbps][maxBitrate:%dkbps][gop:%d][audioSampleRate:%d][customMode:%d]", this.k.h, this.k.a, this.k.b, this.k.c, this.k.e, this.k.d, this.k.i, this.k.s, this.k.R), "", 0);
    }
    
    public TXLivePushConfig b() {
        return this.f;
    }
    
    public void a(final ITXLivePushListener g) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setPushListener " + g);
        this.g = g;
    }
    
    public void a(final TXCloudVideoView e) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api startCameraPreview " + e + ", " + this);
        Monitor.a(1, "startCameraPreview [view:" + ((e != null) ? e.hashCode() : 0) + "]", "", 0);
        this.a(this.f);
        if (this.k.M) {
            TXCLog.e(com.tencent.rtmp.b.d, "enable pure audio push , so can not start preview!");
            return;
        }
        if (this.e != e && this.e != null) {
            this.e.removeVideoView();
        }
        this.e = e;
        if (this.l == null) {
            this.l = new CaptureAndEnc(this.n);
        }
        this.l.a((b)this);
        this.l.a((CaptureAndEnc.a)this);
        this.l.startPreview(e);
        this.l.b(this.f.mBeautyLevel, this.f.mWhiteningLevel, this.f.mRuddyLevel);
        LicenceCheck.a().b(null, this.n);
    }
    
    public void a(final boolean b) {
        TXCLog.i(b.d, "liteav_api stopCameraPreview " + b + ", " + this);
        Monitor.a(1, "stopCameraPreview", "", 0);
        if (this.l == null) {
            return;
        }
        this.l.c(b);
    }
    
    public int a(final String r) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api startPusher " + this);
        if (!this.w) {
            this.w = (LicenceCheck.a().b(null, this.n) == 0);
        }
        if (!this.w) {
            return -5;
        }
        if (TextUtils.isEmpty((CharSequence)r)) {
            TXCLog.e(com.tencent.rtmp.b.d, "start push error when url is empty " + this);
            return -1;
        }
        if (!TextUtils.isEmpty((CharSequence)this.r) && this.f()) {
            if (this.r.equalsIgnoreCase(r)) {
                TXCLog.w(com.tencent.rtmp.b.d, "ignore start push when new url is the same with old url  " + this);
                return -1;
            }
            TXCLog.w(com.tencent.rtmp.b.d, " stop old push when new url is not the same with old url  " + this);
            this.c();
        }
        TXCLog.i(com.tencent.rtmp.b.d, "================================================================================================================================================");
        TXCLog.i(com.tencent.rtmp.b.d, "================================================================================================================================================");
        TXCLog.i(com.tencent.rtmp.b.d, "============= startPush pushUrl = " + r + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "=============");
        TXCLog.i(com.tencent.rtmp.b.d, "================================================================================================================================================");
        TXCLog.i(com.tencent.rtmp.b.d, "================================================================================================================================================");
        this.g(this.r = r);
        this.z();
        this.F();
        this.H();
        this.B();
        this.D();
        Monitor.a(this.r, 0, "");
        Monitor.a(1, "startPush", "", 0);
        this.v();
        if (this.e != null) {
            this.e.clearLog();
        }
        if (this.f(this.r)) {
            this.l.enableBlackStream(true);
        }
        this.r();
        return 0;
    }
    
    public void c() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api stopPusher " + this);
        Monitor.a(1, "stopPush", "", 0);
        this.p();
        this.w();
        this.E();
        this.C();
        this.G();
        this.m();
        this.I();
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioEarMonitoring(false);
        this.x = false;
        this.k.P = false;
        this.A();
        this.r = "";
        this.y.clear();
        Monitor.a();
        this.l.enableBlackStream(false);
        this.s();
        TXCAudioEngine.getInstance().clean();
    }
    
    private boolean f(final String s) {
        try {
            for (final String s2 : s.split("[?&]")) {
                if (s2.indexOf("=") != -1) {
                    final String[] split2 = s2.split("[=]");
                    if (split2.length == 2) {
                        final String s3 = split2[0];
                        final String s4 = split2[1];
                        if (!TextUtils.isEmpty((CharSequence)s3) && !TextUtils.isEmpty((CharSequence)s4) && s3.equalsIgnoreCase("enableblackstream")) {
                            return Integer.parseInt(s4) == 1;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            TXCLog.w(com.tencent.rtmp.b.d, "parse black stream flag error " + ex.toString());
        }
        return false;
    }
    
    public void d() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api pausePusher " + this);
        Monitor.a(1, "pausePush", "", 0);
        if (this.l != null) {
            this.l.pausePusher();
        }
        TXCLog.i(com.tencent.rtmp.b.d, "mPauseFlag:" + this.f.mPauseFlag);
        if ((this.f.mPauseFlag & 0x2) == 0x2) {
            TXCAudioEngine.getInstance().pauseAudioCapture(true);
        }
    }
    
    public void e() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api resumePusher " + this);
        Monitor.a(1, "resumePush", "", 0);
        if (this.l != null) {
            this.l.resumePusher();
        }
        TXCAudioEngine.getInstance().resumeAudioCapture();
    }
    
    public boolean f() {
        return this.l != null && this.l.j();
    }
    
    public void a(final Surface surface) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setSurface " + surface);
        if (this.l == null) {
            return;
        }
        this.l.a(surface);
    }
    
    public void a(final int n, final int n2) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setSurfaceSize " + n + "," + n2);
        if (this.l == null) {
            return;
        }
        this.l.a(n, n2);
    }
    
    public void a(final float n, final float n2) {
        if (this.l != null) {
            this.l.a(n, n2);
        }
    }
    
    public void g() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api startScreenCapture ");
        Monitor.a(1, "startScreenCapture", "", 0);
        if (this.l == null) {
            return;
        }
        this.l.a((com.tencent.liteav.screencapture.a.a)null);
    }
    
    public void h() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api stopScreenCapture ");
        Monitor.a(1, "stopScreenCapture", "", 0);
        if (this.l == null) {
            return;
        }
        this.l.l();
    }
    
    public void a(int h, final boolean b, final boolean b2) {
        TXCLog.i(b.d, "liteav_api setVideoQuality " + h + ", " + b + ", " + b2);
        Monitor.a(1, String.format("setVideoQuality:[quality:%d][adjustBitrate:%s][adjustResolution:%s]", h, b ? "true" : "false", b2 ? "true" : "false"), "", 0);
        if (Build.VERSION.SDK_INT < 18 && (h == 2 || h == 3)) {
            h = 1;
        }
        if (this.f == null) {
            this.f = new TXLivePushConfig();
        }
        this.f.setVideoFPS(18);
        final boolean b3 = b2 && b;
        boolean p3 = false;
        boolean q = false;
        switch (h) {
            case 1: {
                this.f.enableAEC(false);
                this.f.setHardwareAcceleration(2);
                this.f.setVideoResolution(0);
                this.f.setAudioSampleRate(48000);
                this.a(b, b2);
                if (b3) {
                    this.f.setMinVideoBitrate(301);
                    this.f.setVideoBitrate(800);
                    this.f.setMaxVideoBitrate(900);
                }
                else {
                    this.f.setMinVideoBitrate(500);
                    this.f.setVideoBitrate(800);
                    this.f.setMaxVideoBitrate(900);
                }
                p3 = false;
                q = false;
                break;
            }
            case 2: {
                this.f.enableAEC(false);
                this.f.setHardwareAcceleration(2);
                this.f.setVideoResolution(1);
                this.f.setAudioSampleRate(48000);
                this.a(b, b2);
                if (b3) {
                    this.f.setMinVideoBitrate(600);
                    this.f.setVideoBitrate(1200);
                    this.f.setMaxVideoBitrate(1500);
                }
                else {
                    this.f.setMinVideoBitrate(800);
                    this.f.setVideoBitrate(1200);
                    this.f.setMaxVideoBitrate(1500);
                }
                p3 = false;
                q = false;
                break;
            }
            case 3: {
                this.f.enableAEC(false);
                this.f.setHardwareAcceleration(1);
                this.f.setVideoResolution(2);
                this.f.setAudioSampleRate(48000);
                this.a(b, b2);
                if (b3) {
                    this.f.setMinVideoBitrate(600);
                    this.f.setVideoBitrate(1800);
                    this.f.setMaxVideoBitrate(1800);
                }
                else {
                    this.f.setMinVideoBitrate(1000);
                    this.f.setVideoBitrate(1800);
                    this.f.setMaxVideoBitrate(1800);
                }
                p3 = false;
                q = false;
                break;
            }
            case 7: {
                this.f.enableAEC(false);
                this.f.setHardwareAcceleration(1);
                this.f.setVideoResolution(30);
                this.f.setAudioSampleRate(48000);
                this.a(b, false);
                this.f.setMinVideoBitrate(2500);
                this.f.setVideoBitrate(3000);
                this.f.setMaxVideoBitrate(3000);
                p3 = false;
                q = false;
                break;
            }
            case 6: {
                this.f.enableAEC(true);
                this.f.setHardwareAcceleration(1);
                this.f.setVideoResolution(0);
                this.f.setAudioSampleRate(48000);
                this.f.setAutoAdjustBitrate(true);
                this.f.setAutoAdjustStrategy(5);
                this.f.setMinVideoBitrate(190);
                this.f.setVideoBitrate(400);
                this.f.setMaxVideoBitrate(810);
                p3 = true;
                q = true;
                break;
            }
            case 4: {
                if (Build.VERSION.SDK_INT < 18) {
                    this.f.enableAEC(true);
                    this.f.setHardwareAcceleration(0);
                    this.f.setVideoResolution(0);
                    this.f.setAutoAdjustBitrate(true);
                    this.f.setAutoAdjustStrategy(4);
                    this.f.setMinVideoBitrate(500);
                    this.f.setVideoBitrate(800);
                    this.f.setMaxVideoBitrate(900);
                }
                else if (this.h == 1) {
                    this.f.enableAEC(true);
                    this.f.setHardwareAcceleration(1);
                    this.f.setVideoResolution(0);
                    this.f.setAutoAdjustBitrate(true);
                    this.f.setAutoAdjustStrategy(4);
                    this.f.setMinVideoBitrate(500);
                    this.f.setVideoBitrate(800);
                    this.f.setMaxVideoBitrate(900);
                }
                else if (this.h == 3) {
                    this.f.enableAEC(true);
                    this.f.setHardwareAcceleration(1);
                    this.f.setVideoResolution(2);
                    this.f.setAutoAdjustBitrate(true);
                    this.f.setAutoAdjustStrategy(4);
                    this.f.setMinVideoBitrate(1000);
                    this.f.setVideoBitrate(1800);
                    this.f.setMaxVideoBitrate(1800);
                }
                else if (this.h == 7) {
                    this.f.enableAEC(true);
                    this.f.setHardwareAcceleration(1);
                    this.f.setVideoResolution(30);
                    this.f.setAutoAdjustBitrate(true);
                    this.f.setAutoAdjustStrategy(4);
                    this.f.setMinVideoBitrate(2000);
                    this.f.setVideoBitrate(3000);
                    this.f.setMaxVideoBitrate(3000);
                }
                else {
                    this.f.enableAEC(true);
                    this.f.setHardwareAcceleration(1);
                    this.f.setVideoResolution(1);
                    this.f.setAutoAdjustBitrate(true);
                    this.f.setAutoAdjustStrategy(4);
                    this.f.setMinVideoBitrate(800);
                    this.f.setVideoBitrate(1200);
                    this.f.setMaxVideoBitrate(1500);
                }
                this.f.setAudioSampleRate(48000);
                p3 = true;
                q = false;
                break;
            }
            case 5: {
                this.f.enableAEC(true);
                this.f.setHardwareAcceleration(1);
                this.f.setVideoResolution(6);
                this.f.setAutoAdjustBitrate(false);
                this.f.setVideoBitrate(350);
                this.f.setAudioSampleRate(48000);
                p3 = true;
                q = false;
                break;
            }
            default: {
                this.f.setHardwareAcceleration(2);
                TXCLog.e(b.d, "setVideoPushQuality: invalid quality " + h);
                return;
            }
        }
        this.h = h;
        this.f.enableVideoHardEncoderMainProfile(!p3);
        this.f.setVideoEncodeGop(p3 ? 1 : 3);
        if (this.k != null) {
            this.k.P = p3;
            this.k.Q = q;
        }
        this.a(this.f);
    }
    
    public void i() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api switchCamera ");
        Monitor.a(1, "switchCamera", "", 0);
        if (this.l == null) {
            return;
        }
        this.l.k();
    }
    
    public boolean b(final boolean videoEncoderXMirror) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setMirror " + videoEncoderXMirror);
        if (this.f != null) {
            this.f.setVideoEncoderXMirror(videoEncoderXMirror);
        }
        if (this.l == null) {
            return false;
        }
        this.l.f(videoEncoderXMirror);
        return true;
    }
    
    public void a(final int n) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setRenderRotation ");
        if (this.l == null) {
            return;
        }
        this.l.g(n);
    }
    
    public boolean c(final boolean b) {
        TXCLog.i(b.d, "liteav_api turnOnFlashLight " + b);
        return this.l != null && this.l.e(b);
    }
    
    public int j() {
        if (this.l == null) {
            return 0;
        }
        return this.l.q();
    }
    
    public boolean b(final int n) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setZoom " + n);
        return this.l != null && this.l.i(n);
    }
    
    public void a(final float n) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setExposureCompensation " + n);
        if (this.l == null) {
            return;
        }
        this.l.a(n);
    }
    
    public boolean a(final int n, final int mBeautyLevel, final int mWhiteningLevel, final int mRuddyLevel) {
        if (this.l != null) {
            this.l.h(n);
            this.l.b(mBeautyLevel, mWhiteningLevel, mRuddyLevel);
        }
        if (this.f != null) {
            this.f.mBeautyLevel = mBeautyLevel;
            this.f.mWhiteningLevel = mWhiteningLevel;
            this.f.mRuddyLevel = mRuddyLevel;
        }
        return true;
    }
    
    public TXBeautyManager k() {
        if (this.l == null) {
            this.l = new CaptureAndEnc(this.n);
        }
        return this.l.getBeautyManager();
    }
    
    public void d(final boolean b) {
        TXCLog.i(b.d, "liteav_api setMute " + b);
        Monitor.a(1, "setMute:" + (b ? "true" : "false"), "", 0);
        this.x = b;
        TXCAudioEngine.getInstance().muteLocalAudio(b);
        if (this.f.mEnablePureAudioPush && this.m != null) {
            this.m.setAudioMute(b);
        }
    }
    
    public void a(final TXLivePusher.OnBGMNotify a) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setBGMNofify " + a);
        this.a = a;
        if (this.a != null) {
            TXCLiveBGMPlayer.getInstance().setOnPlayListener(this.E);
        }
        else {
            TXCLiveBGMPlayer.getInstance().setOnPlayListener(null);
        }
    }
    
    public boolean b(final String s) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api playBGM " + s);
        return TXCLiveBGMPlayer.getInstance().startPlay(s);
    }
    
    public boolean l() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api stopBGM ");
        return TXCLiveBGMPlayer.getInstance().stopPlay();
    }
    
    public void m() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api stopAllBGM ");
        TXCLiveBGMPlayer.getInstance().stopAll();
    }
    
    public boolean n() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api pauseBGM ");
        return TXCLiveBGMPlayer.getInstance().pause();
    }
    
    public boolean o() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api resumeBGM ");
        return TXCLiveBGMPlayer.getInstance().resume();
    }
    
    public int c(final String s) {
        return TXCLiveBGMPlayer.getInstance().getBGMDuration(s);
    }
    
    public boolean b(final float volume) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setBGMVolume " + volume);
        return TXCLiveBGMPlayer.getInstance().setVolume(volume);
    }
    
    public boolean c(final float softwareCaptureVolume) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setMicVolume " + softwareCaptureVolume);
        return TXCAudioEngine.getInstance().setSoftwareCaptureVolume(softwareCaptureVolume);
    }
    
    public void d(final float pitch) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setBGMPitch " + pitch);
        TXCLiveBGMPlayer.getInstance().setPitch(pitch);
    }
    
    public boolean c(final int bgmPosition) {
        TXCLiveBGMPlayer.getInstance().setBGMPosition(bgmPosition);
        return true;
    }
    
    public void d(final int n) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setReverb " + n);
        if (n < 0 || n > 7) {
            TXCLog.e(com.tencent.rtmp.b.d, "reverbType not support :" + n);
            return;
        }
        TXCAudioEngine.getInstance().setReverbType(this.b[n]);
    }
    
    public void e(final int n) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setVoiceChangerType " + n);
        if (n < 0 || n > 11) {
            TXCLog.e(com.tencent.rtmp.b.d, "voiceChangerType not support :" + n);
            return;
        }
        TXCAudioEngine.getInstance().setVoiceChangerType(this.c[n]);
    }
    
    public void a(final TXLivePusher.ITXAudioVolumeEvaluationListener b) {
        this.B = b;
    }
    
    public void f(final int n) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api enableAudioVolumeEvaluation intervalMs = " + n);
        Monitor.a(1, "liteav_api enableAudioVolumeEvaluation intervalMs = " + n, "", 0);
        if (n > 0) {
            this.C = ((n < 100) ? 100 : n);
            this.r();
        }
        else {
            this.C = 0;
            this.s();
        }
    }
    
    private void r() {
        if (this.C > 0 && this.f()) {
            TXCAudioEngine.getInstance();
            TXCAudioEngine.enableAudioVolumeEvaluation(true, 300);
            if (this.F == null) {
                this.F = new a();
            }
            this.F.a(this.C);
            if (this.o != null) {
                this.o.removeCallbacks((Runnable)this.F);
                this.o.postDelayed((Runnable)this.F, (long)this.C);
            }
        }
    }
    
    private void s() {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(false, 300);
        if (this.F != null) {
            this.F.a(0);
        }
        if (this.o != null) {
            this.o.removeCallbacks((Runnable)this.F);
        }
        this.F = null;
        this.C = 0;
    }
    
    public void a(final TXRecordCommon.ITXVideoRecordListener m) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setVideoRecordListener " + m);
        this.M = m;
    }
    
    public int d(final String j) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api startRecord " + j);
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.e(com.tencent.rtmp.b.d, "API levl is too low (record need 18, current is" + Build.VERSION.SDK_INT + ")");
            return -3;
        }
        if (this.L) {
            TXCLog.w(com.tencent.rtmp.b.d, "ignore start record when recording");
            return -1;
        }
        if (this.l == null || !this.l.j()) {
            TXCLog.w(com.tencent.rtmp.b.d, "ignore start record when not pushing");
            return -2;
        }
        TXCLog.w(com.tencent.rtmp.b.d, "start record ");
        this.L = true;
        this.J = j;
        final File file = new File(j);
        if (file.exists()) {
            file.delete();
        }
        this.H = new c(this.n, 0);
        this.I = false;
        this.H.a(this.J);
        this.u();
        TXCDRApi.txReportDAU(this.n.getApplicationContext(), com.tencent.liteav.basic.datareport.a.aH);
        if (this.l != null) {
            this.l.t();
        }
        return 0;
    }
    
    public void p() {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api stopRecord ");
        if (this.L && this.H != null) {
            final int b = this.H.b();
            TXCLog.w(com.tencent.rtmp.b.d, "start record ");
            this.L = false;
            if (b == 0) {
                AsyncTask.execute((Runnable)new Runnable() {
                    final /* synthetic */ String a = com.tencent.rtmp.b.this.J;
                    
                    @Override
                    public void run() {
                        final String string = new File(this.a).getParentFile() + File.separator + String.format("TXUGCCover_%s.jpg", new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date(System.currentTimeMillis())));
                        com.tencent.liteav.basic.util.f.a(this.a, string);
                        com.tencent.rtmp.b.this.a(this.a, string);
                    }
                });
            }
            else {
                this.t();
            }
        }
    }
    
    public void a(final TXLivePusher.ITXSnapshotListener itxSnapshotListener) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api snapshot " + itxSnapshotListener);
        if (this.t || itxSnapshotListener == null || this.l == null) {
            return;
        }
        if (this.l != null) {
            this.t = true;
            this.l.a(new o() {
                @Override
                public void onTakePhotoComplete(final Bitmap bitmap) {
                    com.tencent.rtmp.b.this.a(itxSnapshotListener, bitmap);
                    com.tencent.rtmp.b.this.t = false;
                    com.tencent.rtmp.b.this.o.removeCallbacks(com.tencent.rtmp.b.this.G);
                }
            });
            this.o.postDelayed(this.G, 2000L);
        }
        else {
            this.t = false;
        }
    }
    
    public int b(final int n, final int n2, final int n3) {
        if (this.l != null) {
            return this.l.a(n, n2, n3, ((EGL10)EGLContext.getEGL()).eglGetCurrentContext(), 0L);
        }
        return -1000;
    }
    
    public int a(final byte[] array, int n, final int n2, final int n3) {
        if (this.l != null) {
            switch (n) {
                case 3: {
                    n = 1;
                    break;
                }
                case 5: {
                    n = 2;
                    break;
                }
                default: {
                    return -1000;
                }
            }
            return this.l.a(array, n, n2, n3, 0L);
        }
        return -1000;
    }
    
    public void a(final byte[] array) {
        TXCAudioEngine.getInstance().sendCustomPCMData(array, this.f.mAudioSample, this.f.mAudioChannels);
    }
    
    public void a(final TXLivePusher.VideoCustomProcessListener i) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setVideoProcessListener " + i);
        this.i = i;
        if (this.i == null) {
            if (this.l != null) {
                this.l.a((r)null);
            }
        }
        else if (this.l != null) {
            this.l.a((r)this);
        }
    }
    
    public void a(final TXLivePusher.AudioCustomProcessListener j) {
        TXCLog.i(com.tencent.rtmp.b.d, "liteav_api setAudioProcessListener " + j);
        this.j = j;
    }
    
    public boolean b(final byte[] array) {
        if (array.length <= 0 || array.length > 2048) {
            return false;
        }
        synchronized (this) {
            if (this.A != null) {
                final b b = new b();
                b.a = TXCTimeUtil.generatePtsMS();
                b.b = this.a(array.length, this.e(array));
                this.A.add(b);
            }
        }
        return true;
    }
    
    @Deprecated
    public void c(final byte[] array) {
        synchronized (this) {
            if (this.A != null) {
                final b b = new b();
                b.a = TXCTimeUtil.generatePtsMS();
                b.b = this.e(array);
                this.A.add(b);
            }
        }
    }
    
    public void e(final String s) {
        TXCLog.i("User", s);
    }
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
        if (this.o != null) {
            this.o.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.rtmp.b.this.e != null) {
                        com.tencent.rtmp.b.this.e.setLogText(null, bundle, n);
                    }
                }
            });
        }
        if (n < 0) {
            Monitor.a(3, String.format("%s [errcode:%d]", (bundle != null) ? bundle.getString("EVT_MSG") : "", n), "", 0);
        }
        this.a(n, bundle);
    }
    
    @Override
    public int a(final int n, final int n2, final int n3) {
        if (this.i != null) {
            return this.i.onTextureCustomProcess(n, n2, n3);
        }
        return 0;
    }
    
    @Override
    public void a() {
        if (this.i != null) {
            this.i.onTextureDestoryed();
        }
    }
    
    @Override
    public int onGetEncoderRealBitrate() {
        return TXCStatus.c(this.s, 4002);
    }
    
    @Override
    public int onGetQueueInputSize() {
        final int c = TXCStatus.c(this.s, 7002);
        int n;
        if (this.k.P) {
            n = c + TXCStatus.c(this.s, 7001);
        }
        else {
            n = c + TXCStatus.c(this.s, 4002);
        }
        return n;
    }
    
    @Override
    public int onGetQueueOutputSize() {
        if (this.m == null) {
            return 0;
        }
        return TXCStatus.c(this.s, 7004) + TXCStatus.c(this.s, 7003);
    }
    
    @Override
    public int onGetVideoQueueMaxCount() {
        return 5;
    }
    
    @Override
    public int onGetVideoQueueCurrentCount() {
        return TXCStatus.c(this.s, 7005);
    }
    
    @Override
    public int onGetVideoDropCount() {
        return TXCStatus.c(this.s, 7007);
    }
    
    @Override
    public int onGetBandwidthEst() {
        return TXCStatus.c(this.s, 7021);
    }
    
    @Override
    public void onEncoderParamsChanged(final int c, final int a, final int b) {
        if (this.l != null) {
            this.l.a(c, a, b);
        }
        if (a != 0 && b != 0) {
            this.k.a = a;
            this.k.b = b;
        }
        if (c != 0) {
            this.k.c = c;
            Monitor.a(2, String.format("Qos: Change [mode:%d][bitrate:%d][videosize:%d*%d]", this.k.f, c, a, b), "", 0);
        }
    }
    
    @Override
    public void onEnableDropStatusChanged(final boolean dropEanble) {
        if (this.m != null) {
            this.m.setDropEanble(dropEanble);
        }
    }
    
    @Override
    public void onEncVideo(final TXSNALPacket txsnalPacket) {
        if (this.p != null) {
            this.p.setHasVideo(true);
        }
        if (this.m != null && txsnalPacket != null && txsnalPacket.nalData != null) {
            synchronized (this) {
                if (this.A != null && !this.A.isEmpty()) {
                    int n = 0;
                    for (final b b : this.A) {
                        if (b.a > txsnalPacket.pts) {
                            break;
                        }
                        n += ((b.b.length <= 10240) ? b.b.length : 10240) + 5;
                    }
                    if (n != 0) {
                        final byte[] nalData = new byte[n + txsnalPacket.nalData.length];
                        int n2 = 0;
                        final byte[] array = new byte[5];
                        int n3 = 0;
                        for (final b b2 : this.A) {
                            if (b2.a > txsnalPacket.pts) {
                                break;
                            }
                            ++n3;
                            final int n4 = (b2.b.length <= 10240) ? b2.b.length : 10240;
                            final int n5 = n4 + 1;
                            array[0] = (byte)(n5 >> 24 & 0xFF);
                            array[1] = (byte)(n5 >> 16 & 0xFF);
                            array[2] = (byte)(n5 >> 8 & 0xFF);
                            array[3] = (byte)(n5 & 0xFF);
                            array[4] = 6;
                            System.arraycopy(array, 0, nalData, n2, array.length);
                            final int n6 = n2 + array.length;
                            System.arraycopy(b2.b, 0, nalData, n6, n4);
                            n2 = n6 + n4;
                        }
                        final long pts = txsnalPacket.pts;
                        for (int i = 0; i < n3; ++i) {
                            this.A.remove(0);
                        }
                        System.arraycopy(txsnalPacket.nalData, 0, nalData, n2, txsnalPacket.nalData.length);
                        txsnalPacket.nalData = nalData;
                    }
                }
            }
            this.m.pushNAL(txsnalPacket);
        }
        if (this.L && this.H != null && txsnalPacket != null && txsnalPacket.nalData != null) {
            final byte[] d = this.d(txsnalPacket.nalData);
            if (this.I) {
                this.a(txsnalPacket, d);
            }
            else if (txsnalPacket.nalType == 0) {
                final MediaFormat a = com.tencent.liteav.basic.util.f.a(d, this.l.c(), this.l.d());
                if (a != null) {
                    this.H.a(a);
                    this.H.a();
                    this.I = true;
                    this.K = 0L;
                }
                this.a(txsnalPacket, d);
            }
        }
    }
    
    @Override
    public void onRecordRawPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4, final boolean b) {
        final TXLivePusher.AudioCustomProcessListener j = this.j;
        if (j != null) {
            j.onRecordRawPcmData(array, n, n2, n3, n4, b);
        }
    }
    
    @Override
    public void onRecordPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
        final TXLivePusher.AudioCustomProcessListener j = this.j;
        if (j != null) {
            j.onRecordPcmData(array, n, n2, n3, n4);
        }
    }
    
    @Override
    public void onRecordEncData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
        if (this.L && this.H != null && this.I && array != null) {
            this.H.a(array, 0, array.length, n * 1000L, 0);
        }
    }
    
    @Override
    public void onRecordError(final int n, final String s) {
    }
    
    @Override
    public void onEncVideoFormat(final MediaFormat mediaFormat) {
        if (this.L && this.H != null) {
            this.H.a(mediaFormat);
            if (!this.I) {
                this.H.a();
                this.I = true;
                this.K = 0L;
            }
        }
    }
    
    @Override
    public void onBackgroudPushStop() {
        TXCAudioEngine.getInstance().resumeAudioCapture();
        TXCAudioEngine.getInstance().muteLocalAudio(false);
    }
    
    private void t() {
        this.o.post((Runnable)new Runnable() {
            @Override
            public void run() {
                final TXRecordCommon.TXRecordResult txRecordResult = new TXRecordCommon.TXRecordResult();
                txRecordResult.retCode = -1;
                txRecordResult.descMsg = "record video failed";
                if (com.tencent.rtmp.b.this.M != null) {
                    com.tencent.rtmp.b.this.M.onRecordComplete(txRecordResult);
                }
                TXCLog.w(com.tencent.rtmp.b.d, "record complete fail");
            }
        });
    }
    
    private void a(final String s, final String s2) {
        this.o.post((Runnable)new Runnable() {
            @Override
            public void run() {
                final TXRecordCommon.TXRecordResult txRecordResult = new TXRecordCommon.TXRecordResult();
                txRecordResult.retCode = 0;
                txRecordResult.descMsg = "record success";
                txRecordResult.videoPath = s;
                txRecordResult.coverPath = s2;
                if (com.tencent.rtmp.b.this.M != null) {
                    com.tencent.rtmp.b.this.M.onRecordComplete(txRecordResult);
                }
                TXCLog.w(com.tencent.rtmp.b.d, "record complete success");
            }
        });
    }
    
    @TargetApi(16)
    private void u() {
        final MediaFormat a = com.tencent.liteav.basic.util.f.a(this.f.mAudioSample, this.f.mAudioChannels, 2);
        if (this.H != null) {
            this.H.b(a);
        }
    }
    
    private void a(final TXSNALPacket txsnalPacket, final byte[] array) {
        if (this.K == 0L) {
            this.K = txsnalPacket.pts;
        }
        final long n = txsnalPacket.pts - this.K;
        int flags = 0;
        if (txsnalPacket.info == null) {
            if (txsnalPacket.nalType == 0) {
                flags = 1;
            }
        }
        else {
            flags = txsnalPacket.info.flags;
        }
        this.H.b(array, 0, array.length, txsnalPacket.pts * 1000L, flags);
        this.o.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (com.tencent.rtmp.b.this.M != null) {
                    com.tencent.rtmp.b.this.M.onRecordProgress(n);
                }
            }
        });
    }
    
    private void a(final TXLivePusher.ITXSnapshotListener itxSnapshotListener, final Bitmap bitmap) {
        if (null == itxSnapshotListener) {
            return;
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (null != itxSnapshotListener) {
                    itxSnapshotListener.onSnapshot(bitmap);
                }
            }
        });
    }
    
    private void a(final boolean b, final boolean b2) {
        final int b3 = this.b(b, b2);
        if (b3 == -1) {
            this.f.setAutoAdjustBitrate(false);
            this.f.setAutoAdjustStrategy(-1);
        }
        else {
            this.f.setAutoAdjustBitrate(true);
            this.f.setAutoAdjustStrategy(b3);
        }
    }
    
    private int b(final boolean b, final boolean b2) {
        if (!b) {
            return -1;
        }
        if (b2) {
            return 1;
        }
        return 0;
    }
    
    private void g(final String s) {
        if (this.m != null) {
            this.m.setID(s);
        }
        if (this.l != null) {
            this.l.setID(s);
        }
        if (this.q != null) {
            this.q.d(s);
        }
        this.s = s;
    }
    
    private void v() {
        this.N = true;
        if (this.o != null) {
            this.o.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.rtmp.b.this.N) {
                        com.tencent.rtmp.b.this.y();
                    }
                }
            }, 2000L);
        }
    }
    
    private void w() {
        this.N = false;
    }
    
    private String g(final int n) {
        switch (n) {
            case 0: {
                return "UNKNOWN";
            }
            case 1: {
                return "WIFI";
            }
            case 2: {
                return "4G";
            }
            case 3: {
                return "3G";
            }
            case 4: {
                return "2G";
            }
            case 5: {
                return "WIRED";
            }
            default: {
                return "UNKNOWN";
            }
        }
    }
    
    private void x() {
        com.tencent.liteav.a.a(this.s, null);
    }
    
    private void y() {
        this.x();
        final int[] a = com.tencent.liteav.basic.util.f.a();
        final String string = a[0] / 10 + "/" + a[1] / 10 + "%";
        final int c = TXCStatus.c(this.s, 7004);
        final int c2 = TXCStatus.c(this.s, 7003);
        final int c3 = TXCStatus.c(this.s, 7002);
        final int c4 = TXCStatus.c(this.s, 7001);
        final int c5 = TXCStatus.c(this.s, 7007);
        final int c6 = TXCStatus.c(this.s, 7005);
        final int c7 = TXCStatus.c(this.s, 7006);
        final String b = TXCStatus.b(this.s, 7012);
        double d = TXCStatus.d(this.s, 4001);
        final int c8 = TXCStatus.c(this.s, 4006);
        final Bundle bundle = new Bundle();
        bundle.putInt("NET_SPEED", c2 + c);
        bundle.putInt("VIDEO_FPS", (int)d);
        if (d < 1.0) {
            d = 15.0;
        }
        bundle.putInt("VIDEO_GOP", (int)(10 * c8 / (int)d / 10.0f + 0.5));
        bundle.putInt("VIDEO_DROP", c5);
        bundle.putInt("VIDEO_BITRATE", c4);
        bundle.putInt("AUDIO_BITRATE", c3);
        bundle.putInt("AUDIO_CACHE", c7);
        bundle.putInt("VIDEO_CACHE", c6);
        bundle.putCharSequence("SERVER_IP", (CharSequence)b);
        bundle.putCharSequence("CPU_USAGE", (CharSequence)string);
        if (this.l != null) {
            bundle.putString("AUDIO_PLAY_INFO", TXCAudioEngine.getInstance().getAECType() + " | " + this.f.mAudioSample + " , " + this.f.mAudioChannels);
            bundle.putInt("VIDEO_WIDTH", this.l.c());
            bundle.putInt("VIDEO_HEIGHT", this.l.d());
        }
        if (this.e != null) {
            this.e.setLogText(bundle, null, 0);
        }
        if (this.g != null) {
            this.g.onNetStatus(bundle);
        }
        if (this.q != null) {
            this.q.e();
        }
        final int e = com.tencent.liteav.basic.util.f.e(this.n);
        final boolean a2 = com.tencent.liteav.basic.util.f.a(this.n);
        if (this.u != e) {
            Monitor.a(2, String.format("Network: net type change from %s to %s", this.g(this.u), this.g(e)), "", 0);
            this.u = e;
        }
        if (this.v != (a2 ? 1 : 0)) {
            Monitor.a(2, String.format("app: switch to %s", a2 ? "background" : "foreground"), "", 0);
            this.v = (a2 ? 1 : 0);
        }
        if (this.o != null && this.N) {
            this.o.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.rtmp.b.this.N) {
                        com.tencent.rtmp.b.this.y();
                    }
                }
            }, 2000L);
        }
    }
    
    private void z() {
        final l l = new l();
        l.d = this.f.mAudioChannels;
        l.e = this.f.mAudioSample;
        l.a = 0;
        l.c = 20;
        l.b = 0;
        l.f = 3;
        l.j = true;
        l.l = true;
        l.k = false;
        l.h = 40;
        l.i = 5000;
        l.m = this.k.P;
        l.n = this.k.Q;
        l.o = this.i(this.h);
        (this.m = new TXCStreamUploader(this.n, l)).setID(this.s);
        this.m.setMetaData(this.f.mMetaData);
        if (this.m != null) {
            this.m.setAudioInfo(this.k.s, this.k.t);
        }
        this.m.setNotifyListener(this);
        if (this.k.M) {
            this.m.setAudioMute(this.x);
        }
        this.r = this.m.start(this.r, this.k.N, this.k.O);
        if (this.k.M) {
            this.m.setMode(1);
        }
        if (this.k.P) {
            final int q = this.k.q;
            final int r = this.k.r;
            final int retryTimes = (q < 5) ? 5 : q;
            this.m.setRetryInterval((r > 1) ? 1 : r);
            this.m.setRetryTimes(retryTimes);
            this.m.setVideoDropParams(false, this.k.h, 1000);
        }
        else {
            this.m.setRetryInterval(this.k.r);
            this.m.setRetryTimes(this.k.q);
            this.m.setVideoDropParams(true, 40, 3000);
        }
        this.m.setSendStrategy(this.k.P, this.k.Q);
    }
    
    private void A() {
        if (this.m != null) {
            this.m.stop();
            this.m.setNotifyListener(null);
            this.m = null;
        }
    }
    
    private void B() {
        (this.p = new TXCQoS(true)).setListener(this);
        this.p.setNotifyListener(this);
        this.p.setAutoAdjustBitrate(this.k.g);
        this.p.setAutoAdjustStrategy(this.k.f);
        this.p.setDefaultVideoResolution(this.k.k);
        this.p.setVideoEncBitrate(this.k.e, this.k.d, this.k.c);
        if (this.k.g) {
            this.p.start(2000L);
        }
    }
    
    private void C() {
        if (this.p != null) {
            this.p.stop();
            this.p.setListener(null);
            this.p.setNotifyListener(null);
            this.p = null;
        }
    }
    
    private void D() {
        (this.q = new e(this.n)).d(this.s);
        this.q.a(this.k.c);
        this.q.b(this.k.s);
        this.q.a(this.k.a, this.k.b);
        this.q.a(this.r);
        this.q.a();
    }
    
    private void E() {
        if (this.q != null) {
            this.q.b();
            this.q = null;
        }
    }
    
    private void F() {
        if (this.l != null) {
            this.l.setID(this.s);
            this.l.a((CaptureAndEnc.a)this);
            this.l.startWithoutAudio();
        }
    }
    
    private void G() {
        if (this.l != null) {
            this.l.a((CaptureAndEnc.a)null);
            this.l.stopPush();
            this.l.a((CaptureAndEnc.a)null);
        }
    }
    
    private void H() {
        TXCAudioEngine.getInstance().setEncoderSampleRate(this.f.mAudioSample);
        TXCAudioEngine.getInstance().setEncoderChannels(this.f.mAudioChannels);
        TXCAudioEngine.getInstance().muteLocalAudio(this.x);
        final boolean b = 0x0 != (this.f.mCustomModeType & 0x1);
        TXCAudioEngine.getInstance().setAudioCaptureDataListener(this);
        TXCAudioEngineJNI.nativeUseSysAudioDevice(!TXCAudioEngine.hasTrae());
        TXCAudioEngine.getInstance().startLocalAudio(10, b);
        TXCAudioEngine.getInstance().enableEncodedDataCallback(true);
    }
    
    private void I() {
        TXCAudioEngine.getInstance().stopLocalAudio();
    }
    
    private void b(final TXLivePushConfig txLivePushConfig) {
        final i k = this.k;
        k.c = txLivePushConfig.mVideoBitrate;
        k.e = txLivePushConfig.mMinVideoBitrate;
        k.d = txLivePushConfig.mMaxVideoBitrate;
        k.f = txLivePushConfig.mAutoAdjustStrategy;
        k.g = txLivePushConfig.mAutoAdjustBitrate;
        k.h = txLivePushConfig.mVideoFPS;
        k.i = txLivePushConfig.mVideoEncodeGop;
        k.j = txLivePushConfig.mHardwareAccel;
        k.k = txLivePushConfig.mVideoResolution;
        k.n = (txLivePushConfig.mEnableVideoHardEncoderMainProfile ? 3 : 1);
        k.o = txLivePushConfig.mLocalVideoMirrorType;
        k.s = txLivePushConfig.mAudioSample;
        k.t = txLivePushConfig.mAudioChannels;
        k.u = txLivePushConfig.mEnableAec;
        k.v = txLivePushConfig.mEnableAgc;
        k.w = txLivePushConfig.mEnableAns;
        k.x = txLivePushConfig.mVolumeType;
        k.D = txLivePushConfig.mPauseFlag;
        k.C = txLivePushConfig.mPauseFps;
        k.A = txLivePushConfig.mPauseImg;
        k.B = txLivePushConfig.mPauseTime;
        k.M = txLivePushConfig.mEnablePureAudioPush;
        k.K = txLivePushConfig.mTouchFocus;
        k.L = txLivePushConfig.mEnableZoom;
        k.E = txLivePushConfig.mWatermark;
        k.F = txLivePushConfig.mWatermarkX;
        k.G = txLivePushConfig.mWatermarkY;
        k.H = txLivePushConfig.mWatermarkXF;
        k.I = txLivePushConfig.mWatermarkYF;
        k.J = txLivePushConfig.mWatermarkWidth;
        k.l = txLivePushConfig.mHomeOrientation;
        k.N = txLivePushConfig.mEnableNearestIP;
        k.O = txLivePushConfig.mRtmpChannelType;
        k.q = txLivePushConfig.mConnectRetryCount;
        k.r = txLivePushConfig.mConnectRetryInterval;
        k.m = txLivePushConfig.mFrontCamera;
        k.R = txLivePushConfig.mCustomModeType;
        k.S = txLivePushConfig.mVideoEncoderXMirror;
        k.T = txLivePushConfig.mEnableHighResolutionCapture;
        k.V = txLivePushConfig.mEnableScreenCaptureAutoRotate;
        k.z = txLivePushConfig.mEnableAudioPreview;
        k.a();
    }
    
    private void J() {
        if (this.l == null) {
            return;
        }
        TXCAudioEngine.getInstance().enableSoftAGC(this.k.v, 100);
        TXCAudioEngine.getInstance().enableSoftANS(this.k.w, 100);
        TXCAudioEngine.getInstance();
        TXCAudioEngine.setSystemVolumeType(this.k.x);
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioEarMonitoring(this.k.z);
        this.l.a(this.k);
        this.l.b(this.k.o);
        if (this.l.j()) {
            if (this.m != null) {
                if (this.k.P) {
                    final int q = this.k.q;
                    final int r = this.k.r;
                    final int retryTimes = (q < 5) ? 5 : q;
                    this.m.setRetryInterval((r > 1) ? 1 : r);
                    this.m.setRetryTimes(retryTimes);
                    this.m.setVideoDropParams(false, this.k.h, 1000);
                }
                else {
                    this.m.setRetryInterval(this.k.r);
                    this.m.setRetryTimes(this.k.q);
                    this.m.setVideoDropParams(true, 40, 3000);
                }
                this.m.setSendStrategy(this.k.P, this.k.Q);
            }
            if (this.p != null) {
                this.p.stop();
                this.p.setAutoAdjustBitrate(this.k.g);
                this.p.setAutoAdjustStrategy(this.k.f);
                this.p.setDefaultVideoResolution(this.k.k);
                this.p.setVideoEncBitrate(this.k.e, this.k.d, this.k.c);
                if (this.k.g) {
                    this.p.start(2000L);
                }
            }
        }
        else if (this.m != null) {
            this.m.setAudioInfo(this.k.s, this.k.t);
        }
    }
    
    private void a(int n, final Bundle bundle) {
        switch (n) {
            case 1003: {
                break;
            }
            case -1315:
            case -1314:
            case -1301: {
                n = -1301;
                break;
            }
            case 1007: {
                break;
            }
            case 0: {
                return;
            }
            case -1317:
            case -1302: {
                n = -1302;
                break;
            }
            case 2003: {
                return;
            }
            case 2009: {
                return;
            }
            case 1008:
            case 1027:
            case 1028: {
                n = 1008;
                break;
            }
            case 1103: {
                n = 1103;
                break;
            }
            case 1109: {
                return;
            }
            case -1303: {
                n = -1303;
                break;
            }
            case 1001: {
                n = 1001;
                break;
            }
            case 1002: {
                n = 1002;
                break;
            }
            case 3001: {
                break;
            }
            case 1101: {
                n = 1101;
                break;
            }
            case 3003: {
                n = 3003;
                break;
            }
            case 3002: {
                n = 3002;
                break;
            }
            case -1326: {
                n = 3004;
                break;
            }
            case -1325: {
                n = -1307;
                break;
            }
            case -1324: {
                n = -1307;
                break;
            }
            case -1307: {
                n = -1307;
                break;
            }
            case 3008: {
                n = -1307;
                break;
            }
            case 1102: {
                n = 1102;
                break;
            }
            case 3006:
            case 3007: {
                n = 3005;
                break;
            }
            case -1313: {
                n = -1313;
                break;
            }
            case 1005:
            case 1006: {
                break;
            }
            case 1020: {
                n = 1020;
                break;
            }
            case 1018: {
                n = 1018;
                break;
            }
            case 1019: {
                n = 1019;
                break;
            }
            case 1021: {
                n = 1021;
                break;
            }
            case 2110: {
                n = 2110;
                break;
            }
            case -1309:
            case -1308:
            case 1004: {
                break;
            }
            default: {
                return;
            }
        }
        if (this.o != null) {
            this.o.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.rtmp.b.this.g != null) {
                        com.tencent.rtmp.b.this.g.onPushEvent(n, bundle);
                    }
                }
            });
        }
    }
    
    private byte[] d(final byte[] array) {
        final int length = array.length;
        final byte[] array2 = new byte[length];
        System.arraycopy(array, 0, array2, 0, length);
        int int1;
        for (int n = 0; n + 4 < length; n = n + int1 + 4) {
            int1 = ByteBuffer.wrap(array, n, 4).getInt();
            if (n + 4 + int1 <= length) {
                array2[n] = 0;
                array2[n + 2] = (array2[n + 1] = 0);
                array2[n + 3] = 1;
            }
        }
        return array2;
    }
    
    private byte[] e(final byte[] array) {
        final int n = array.length * 4 / 3 + 2;
        final byte[] array2 = new byte[n];
        int n2 = 0;
        for (int n3 = 0; n3 < array.length && n2 < n; ++n3, ++n2) {
            if (n3 + 3 < array.length && array[n3] == 0 && array[n3 + 1] == 0 && array[n3 + 2] >= 0 && array[n3 + 2] <= 3) {
                array2[n2++] = array[n3++];
                array2[n2++] = array[n3++];
                array2[n2++] = 3;
            }
            array2[n2] = array[n3];
        }
        final byte[] array3 = new byte[n2];
        System.arraycopy(array2, 0, array3, 0, n2);
        return array3;
    }
    
    private byte[] a(final int n, final byte[] array) {
        final byte[] h = this.h(n);
        int n2 = 0;
        final byte[] array2 = new byte[1 + h.length + array.length + 1];
        array2[0] = -14;
        ++n2;
        System.arraycopy(h, 0, array2, n2, h.length);
        final int n3 = n2 + h.length;
        System.arraycopy(array, 0, array2, n3, array.length);
        array2[n3 + array.length] = -128;
        return array2;
    }
    
    private byte[] h(final int n) {
        final int n2 = n / 255 + 1;
        final byte[] array = new byte[n2];
        for (int i = 0; i < n2 - 1; ++i) {
            array[i] = -1;
        }
        array[n2 - 1] = (byte)(n % 255 & 0xFF);
        return array;
    }
    
    private int i(final int n) {
        int n2 = 0;
        switch (n) {
            case 1: {
                n2 = (int)com.tencent.liteav.basic.d.b.a().a("QUICMode", "Live");
                break;
            }
            case 2: {
                n2 = (int)com.tencent.liteav.basic.d.b.a().a("QUICMode", "Live");
                break;
            }
            case 3: {
                n2 = (int)com.tencent.liteav.basic.d.b.a().a("QUICMode", "Live");
                break;
            }
            case 7: {
                n2 = (int)com.tencent.liteav.basic.d.b.a().a("QUICMode", "Live");
                break;
            }
            case 6: {
                n2 = (int)com.tencent.liteav.basic.d.b.a().a("QUICMode", "RTC");
                break;
            }
            case 4: {
                n2 = (int)com.tencent.liteav.basic.d.b.a().a("QUICMode", "LinkMain");
                break;
            }
            case 5: {
                n2 = (int)com.tencent.liteav.basic.d.b.a().a("QUICMode", "LinkSub");
                break;
            }
            default: {
                n2 = 0;
                break;
            }
        }
        return n2;
    }
    
    static {
        d = b.class.getSimpleName();
    }
    
    private class b
    {
        long a;
        byte[] b;
    }
    
    private class a implements Runnable
    {
        private int b;
        
        private a() {
            this.b = 300;
        }
        
        public void a(final int b) {
            this.b = b;
        }
        
        @Override
        public void run() {
            if (com.tencent.rtmp.b.this.f()) {
                final int softwareCaptureVolumeLevel = TXCAudioEngine.getInstance().getSoftwareCaptureVolumeLevel();
                if (com.tencent.rtmp.b.this.B != null) {
                    com.tencent.rtmp.b.this.B.onAudioVolumeEvaluationNotify(softwareCaptureVolumeLevel);
                }
            }
            if (com.tencent.rtmp.b.this.o != null && this.b > 0) {
                com.tencent.rtmp.b.this.o.postDelayed((Runnable)com.tencent.rtmp.b.this.F, (long)this.b);
            }
        }
    }
}
