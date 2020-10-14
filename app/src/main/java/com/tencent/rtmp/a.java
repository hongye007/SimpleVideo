package com.tencent.rtmp;

import com.tencent.liteav.basic.b.*;
import com.tencent.rtmp.ui.*;
import android.view.*;
import android.content.*;
import java.util.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.log.*;
import android.text.*;
import com.tencent.liteav.basic.datareport.*;
import android.os.*;
import org.json.*;
import com.tencent.ugc.*;
import com.tencent.liteav.basic.c.*;
import android.graphics.*;
import javax.microedition.khronos.egl.*;
import com.tencent.liteav.*;
import com.tencent.liteav.basic.structs.*;

public class a implements b
{
    private TXCloudVideoView a;
    private Surface b;
    private int c;
    private int d;
    private ITXLivePlayListener e;
    private TXLivePlayConfig f;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private String k;
    private boolean l;
    private int m;
    private TXLivePlayer.ITXVideoRawDataListener n;
    private byte[] o;
    private Object p;
    private TXLivePlayer.ITXLivePlayVideoRenderListener q;
    private Context r;
    private Handler s;
    private s t;
    private boolean u;
    private float v;
    private boolean w;
    private int x;
    private m y;
    private boolean z;
    private long A;
    private String B;
    private int C;
    private Vector<String> D;
    private long E;
    private TXLivePlayer.ITXAudioVolumeEvaluationListener F;
    private int G;
    private a H;
    private TXLivePlayer.ITXAudioRawDataListener I;
    
    public a(final Context context) {
        this.g = false;
        this.h = true;
        this.k = "";
        this.l = false;
        this.m = 100;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.u = true;
        this.v = 1.0f;
        this.w = false;
        this.x = 0;
        this.C = -1;
        this.D = new Vector<String>();
        this.E = 0L;
        this.F = null;
        this.G = 0;
        this.H = null;
        this.e = null;
        TXCCommonUtil.setAppContext(this.r = context.getApplicationContext());
        TXCLog.init();
        this.s = new Handler(Looper.getMainLooper());
        TXCCommonUtil.setAppContext(this.r);
        TXCLog.init();
    }
    
    public void a(final TXLivePlayConfig f) {
        TXCLog.i("TXLivePlayer", "liteav_api setConfig " + this);
        this.f = f;
        if (this.f == null) {
            this.f = new TXLivePlayConfig();
        }
        if (this.t != null) {
            j q = this.t.q();
            if (q == null) {
                q = new j();
            }
            q.a = this.f.mCacheTime;
            q.g = this.f.mAutoAdjustCacheTime;
            q.c = this.f.mMinAutoAdjustCacheTime;
            q.b = this.f.mMaxAutoAdjustCacheTime;
            q.d = this.f.mVideoBlockThreshold;
            q.e = this.f.mConnectRetryCount;
            q.f = this.f.mConnectRetryInterval;
            q.i = this.f.mEnableNearestIP;
            q.m = this.f.mRtmpChannelType;
            q.h = this.g;
            q.n = this.f.mCacheFolderPath;
            q.o = this.f.mMaxCacheItems;
            q.j = this.f.mEnableMessage;
            q.k = this.f.mEnableMetaData;
            q.l = this.f.mFlvSessionKey;
            q.q = this.f.mHeaders;
            this.t.a(q);
        }
    }
    
    public void a(final ITXLivePlayListener e) {
        TXCLog.i("TXLivePlayer", "liteav_api setPlayListener " + this);
        this.e = e;
    }
    
    public void a(final TXCloudVideoView a) {
        TXCLog.i("TXLivePlayer", "liteav_api setPlayerView old view : " + this.a + ", new view : " + a + ", " + this);
        this.a = a;
        if (this.t != null) {
            this.t.a(a);
        }
    }
    
    public int a(final String s, final int c) {
        TXCLog.i("TXLivePlayer", "liteav_api startPlay " + this);
        if (TextUtils.isEmpty((CharSequence)s)) {
            TXCLog.e("TXLivePlayer", "start play error when url is empty " + this);
            return -1;
        }
        if (!TextUtils.isEmpty((CharSequence)this.k)) {
            if (this.k.equalsIgnoreCase(s) && this.a()) {
                TXCLog.e("TXLivePlayer", "start play error when new url is the same with old url  " + this);
                if (this.z) {
                    this.onNotifyEvent(2004, new Bundle());
                }
                return this.z ? 0 : -1;
            }
            TXCLog.w("TXLivePlayer", " stop old play when new url is not the same with old url  " + this);
            if (this.t != null) {
                this.t.a(false);
            }
            this.k = "";
        }
        TXCDRApi.initCrashReport(this.r);
        TXCLog.i("TXLivePlayer", "===========================================================================================================================================================");
        TXCLog.i("TXLivePlayer", "===========================================================================================================================================================");
        TXCLog.i("TXLivePlayer", "=====  StartPlay url = " + s + " playType = " + c + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.i("TXLivePlayer", "===========================================================================================================================================================");
        TXCLog.i("TXLivePlayer", "===========================================================================================================================================================");
        if (this.C == -1 || this.C != c) {
            this.t = com.tencent.liteav.u.a(this.r, c);
        }
        this.C = c;
        if (this.t == null) {
            return -2;
        }
        this.k = this.c(s, c);
        this.a(this.f);
        if (this.a != null) {
            this.a.clearLog();
            this.a.setVisibility(0);
        }
        this.t.a(this.a);
        this.t.a(this);
        this.t.e(this.u);
        if (this.b != null) {
            this.t.a(this.b);
            this.t.a(this.c, this.d);
        }
        this.t.a(this.k, c);
        this.t.b(this.l);
        this.t.c(this.m);
        this.t.b(this.v);
        this.t.b(this.j);
        this.t.a(this.i);
        this.d(this.x);
        this.t.a(this.I);
        if (this.n != null) {
            this.a(this.n);
        }
        if (this.q != null) {
            this.a(this.q, this.p);
        }
        if (this.t.f()) {
            this.B = this.k;
            this.A = ((this.y != null) ? this.y.a() : 0L);
            if (this.A > 0L) {
                this.t.g();
            }
        }
        this.f();
        return 0;
    }
    
    public int a(final boolean b) {
        TXCLog.i("TXLivePlayer", "liteav_api stopPlay " + b + ", " + this);
        if (b && this.a != null) {
            this.a.setVisibility(8);
        }
        this.g();
        if (this.t != null) {
            this.t.a(b);
        }
        this.k = "";
        this.A = 0L;
        this.y = null;
        this.z = false;
        return 0;
    }
    
    public boolean a() {
        return this.t != null && this.t.c();
    }
    
    public void b() {
        TXCLog.i("TXLivePlayer", "liteav_api pause " + this);
        if (this.t != null) {
            TXCLog.w("TXLivePlayer", "pause play");
            this.t.a();
        }
    }
    
    public void c() {
        TXCLog.i("TXLivePlayer", "liteav_api resume " + this);
        if (this.t != null) {
            TXCLog.w("TXLivePlayer", "resume play");
            this.t.b();
            if (this.t.f()) {
                this.A = ((this.y != null) ? this.y.a() : 0L);
                if (this.A > 0L) {
                    this.t.g();
                }
            }
            this.d(this.x);
        }
    }
    
    public void a(final Surface b) {
        TXCLog.i("TXLivePlayer", "liteav_api setSurface old : " + this.b + ", new : " + b + ", " + this);
        this.b = b;
        if (this.t != null) {
            this.t.a(this.b);
        }
    }
    
    public void a(final int c, final int d) {
        TXCLog.i("TXLivePlayer", "liteav_api setSurfaceSize " + c + "x" + d + ", " + this);
        this.c = c;
        this.d = d;
        if (this.t != null) {
            this.t.a(c, d);
        }
    }
    
    public void a(final int i) {
        TXCLog.i("TXLivePlayer", "liteav_api setRenderMode " + i);
        this.i = i;
        if (this.t != null) {
            this.t.a(i);
        }
    }
    
    public void b(final int j) {
        TXCLog.i("TXLivePlayer", "liteav_api setRenderRotation " + j);
        this.j = j;
        if (this.t != null) {
            this.t.b(j);
        }
    }
    
    public boolean b(final boolean g) {
        TXCLog.i("TXLivePlayer", "liteav_api enableHardwareDecode " + g);
        if (g) {
            if (Build.VERSION.SDK_INT < 18) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, android system build.version = " + Build.VERSION.SDK_INT + ", the minimum build.version should be 18(android 4.3 or later)");
                return false;
            }
            if (this.h()) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, MANUFACTURER = " + Build.MANUFACTURER + ", MODEL" + Build.MODEL);
                return false;
            }
        }
        this.g = g;
        if (this.t != null) {
            j q = this.t.q();
            if (q == null) {
                q = new j();
            }
            q.h = this.g;
            this.t.a(q);
        }
        return true;
    }
    
    public void c(final boolean l) {
        TXCLog.i("TXLivePlayer", "liteav_api setMute " + l);
        this.l = l;
        if (this.t != null) {
            this.t.b(l);
        }
    }
    
    public void c(int m) {
        if (m < 0) {
            m = 0;
        }
        if (m > 100) {
            m = 100;
        }
        TXCLog.i("TXLivePlayer", "liteav_api setVolume volume = " + m);
        this.m = m;
        if (this.t != null) {
            this.t.c(m);
        }
    }
    
    public void d(final int x) {
        TXCLog.i("TXLivePlayer", "liteav_api setAudioRoute " + x);
        this.x = x;
        if (this.t != null) {
            this.t.a(this.r, this.x);
        }
    }
    
    public int a(final String s) {
        if (this.t != null) {
            return this.t.a(s);
        }
        return -1;
    }
    
    public void a(final TXLivePlayer.ITXAudioVolumeEvaluationListener f) {
        this.F = f;
    }
    
    public void e(final int n) {
        TXCLog.i("TXLivePlayer", "liteav_api enableAudioVolumeEvaluation intervalMs = " + n);
        if (n > 0) {
            this.G = ((n < 100) ? 100 : n);
            this.f();
        }
        else {
            this.G = 0;
            this.g();
        }
    }
    
    public void b(final String s) {
        try {
            final JSONObject jsonObject = new JSONObject(s);
            if (!jsonObject.has("api")) {
                TXCLog.e("TXLivePlayer", "callExperimentalAPI[lack api or illegal type]: " + s);
                return;
            }
            final String string = jsonObject.getString("api");
            JSONObject jsonObject2 = null;
            if (jsonObject.has("params")) {
                jsonObject2 = jsonObject.getJSONObject("params");
            }
            if (string.equals("muteRemoteAudioInSpeaker")) {
                if (jsonObject2 == null) {
                    TXCLog.e("TXLivePlayer", "muteRemoteAudioInSpeaker[lack parameter]");
                    return;
                }
                if (!jsonObject2.has("enable")) {
                    TXCLog.e("TXLivePlayer", "muteRemoteAudioInSpeaker[lack parameter]: enable");
                    return;
                }
                final int int1 = jsonObject2.getInt("enable");
                if (this.t != null) {
                    this.t.c(int1 == 1);
                }
            }
            else {
                TXCLog.e("TXLivePlayer", "callExperimentalAPI[illegal api]: " + string);
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXLivePlayer", "callExperimentalAPI[failed]: " + s);
        }
    }
    
    private void f() {
        if (this.t != null) {
            this.t.a(this.G > 0, this.G);
            if (this.G > 0) {
                if (this.H == null) {
                    this.H = new a();
                }
                this.H.a(this.G);
                if (this.s != null) {
                    this.s.removeCallbacks((Runnable)this.H);
                    this.s.postDelayed((Runnable)this.H, (long)this.G);
                }
            }
        }
    }
    
    private void g() {
        if (this.t != null) {
            this.t.a(false, 0);
        }
        if (this.s != null) {
            this.s.removeCallbacks((Runnable)this.H);
        }
        this.H = null;
        this.G = 0;
    }
    
    public void a(final TXRecordCommon.ITXVideoRecordListener itxVideoRecordListener) {
        TXCLog.i("TXLivePlayer", "liteav_api setVideoRecordListener");
        if (this.t != null) {
            this.t.a(itxVideoRecordListener);
        }
    }
    
    public int f(final int n) {
        TXCLog.i("TXLivePlayer", "liteav_api startRecord " + this);
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.e("TXLivePlayer", "API levl is too low (record need 18, current is" + Build.VERSION.SDK_INT + ")");
            return -3;
        }
        if (!this.a()) {
            TXCLog.e("TXLivePlayer", "startRecord: there is no playing stream");
            return -1;
        }
        if (this.t != null) {
            return this.t.d(n);
        }
        return -1;
    }
    
    public int d() {
        TXCLog.i("TXLivePlayer", "liteav_api stopRecord " + this);
        if (this.t != null) {
            return this.t.e();
        }
        return -1;
    }
    
    public void a(final TXLivePlayer.ITXSnapshotListener itxSnapshotListener) {
        TXCLog.i("TXLivePlayer", "liteav_api snapshot " + itxSnapshotListener);
        if (this.w || itxSnapshotListener == null) {
            return;
        }
        this.w = true;
        if (this.t != null) {
            this.t.a(new o() {
                @Override
                public void onTakePhotoComplete(final Bitmap bitmap) {
                    com.tencent.rtmp.a.this.a(itxSnapshotListener, bitmap);
                }
            });
        }
        else {
            this.w = false;
        }
    }
    
    public boolean a(final byte[] o) {
        if (null == this.k || this.k.isEmpty()) {
            return false;
        }
        if (this.g) {
            TXLog.e("TXLivePlayer", "can not addVideoRawData because of hw decode has set!");
            return false;
        }
        if (this.t == null) {
            TXCLog.e("TXLivePlayer", "player hasn't created or not instanceof live player");
            return false;
        }
        this.o = o;
        return true;
    }
    
    public int a(final TXLivePlayer.ITXLivePlayVideoRenderListener q, final Object p2) {
        if (p2 != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                if (!(p2 instanceof EGLContext) && !(p2 instanceof android.opengl.EGLContext)) {
                    TXCLog.w("TXLivePlayer", "liteav_api setVideoRenderListener error when glContext error " + p2);
                    return -1;
                }
            }
            else if (!(p2 instanceof EGLContext)) {
                TXCLog.w("TXLivePlayer", "liteav_api setVideoRenderListener error when glContext error " + p2);
                return -1;
            }
        }
        TXCLog.i("TXLivePlayer", "liteav_api setVideoRenderListener " + q + ", context " + p2);
        this.p = p2;
        this.q = q;
        if (this.t != null) {
            if (q != null) {
                this.t.a(new t() {
                    @Override
                    public void onRenderVideoFrame(final String s, final int n, final TXSVideoFrame txsVideoFrame) {
                        if (txsVideoFrame == null || txsVideoFrame.width <= 0 || txsVideoFrame.height <= 0) {
                            return;
                        }
                        final TXLivePlayer.ITXLivePlayVideoRenderListener e = com.tencent.rtmp.a.this.q;
                        if (e != null) {
                            final TXLivePlayer.TXLiteAVTexture txLiteAVTexture = new TXLivePlayer.TXLiteAVTexture();
                            txLiteAVTexture.textureId = txsVideoFrame.textureId;
                            txLiteAVTexture.width = txsVideoFrame.width;
                            txLiteAVTexture.height = txsVideoFrame.height;
                            txLiteAVTexture.eglContext = txsVideoFrame.eglContext;
                            e.onRenderVideoFrame(txLiteAVTexture);
                        }
                    }
                }, com.tencent.liteav.basic.a.b.c, p2);
            }
            else {
                this.t.a(null, com.tencent.liteav.basic.a.b.a, null);
            }
        }
        return 0;
    }
    
    public void a(final TXLivePlayer.ITXVideoRawDataListener n) {
        TXCLog.i("TXLivePlayer", "liteav_api setVideoRawDataListener " + n);
        this.n = n;
        if (this.t == null) {
            return;
        }
        if (n != null) {
            this.t.a(new t() {
                @Override
                public void onRenderVideoFrame(final String s, final int n, final TXSVideoFrame txsVideoFrame) {
                    if (txsVideoFrame == null || txsVideoFrame.width <= 0 || txsVideoFrame.height <= 0) {
                        return;
                    }
                    final byte[] f = com.tencent.rtmp.a.this.o;
                    com.tencent.rtmp.a.this.o = null;
                    final TXLivePlayer.ITXVideoRawDataListener g = com.tencent.rtmp.a.this.n;
                    if (g != null && f != null) {
                        if (f.length >= txsVideoFrame.width * txsVideoFrame.height * 3 / 2) {
                            txsVideoFrame.loadYUVArray(f);
                            g.onVideoRawDataAvailable(f, txsVideoFrame.width, txsVideoFrame.height, (int)txsVideoFrame.pts);
                            txsVideoFrame.release();
                        }
                        else {
                            TXCLog.e("TXLivePlayer", "raw data buffer length is too large");
                        }
                    }
                }
            }, com.tencent.liteav.basic.a.b.b, null);
        }
        else {
            this.t.a(null, com.tencent.liteav.basic.a.b.a, null);
        }
    }
    
    public void a(final TXLivePlayer.ITXAudioRawDataListener i) {
        TXCLog.i("TXLivePlayer", "liteav_api setAudioRawDataListener " + i);
        this.I = i;
        if (this.t != null) {
            this.t.a(i);
        }
    }
    
    public int b(final String s, final int n) {
        TXCLog.i("TXLivePlayer", "liteav_api prepareLiveSeek " + this);
        if (this.y == null) {
            this.y = new m();
        }
        if (this.y != null) {
            return this.y.a(this.k, s, n, new m.a() {
                @Override
                public void a(final long n) {
                    com.tencent.rtmp.a.this.A = n;
                    if (com.tencent.rtmp.a.this.t != null) {
                        com.tencent.rtmp.a.this.t.g();
                    }
                }
            });
        }
        return -1;
    }
    
    public void g(final int n) {
        TXCLog.d("TXLivePlayer", "liteav_api seek " + n);
        if (this.t != null) {
            if (this.t.f() || this.z) {
                final String s = (this.y != null) ? this.y.a(n) : "";
                if (!TextUtils.isEmpty((CharSequence)s)) {
                    this.z = (this.a(s, 3) == 0);
                    if (this.z) {
                        this.A = n * 1000;
                    }
                }
                else if (this.e != null) {
                    this.e.onPlayEvent(-2301, new Bundle());
                }
            }
            else {
                this.t.e(n);
            }
        }
    }
    
    public int e() {
        TXCLog.i("TXLivePlayer", "liteav_api resumeLive " + this);
        if (this.z) {
            this.z = false;
            return this.a(this.B, 1);
        }
        return -1;
    }
    
    @Deprecated
    public void d(final boolean u) {
        TXCLog.i("TXLivePlayer", "liteav_api setAutoPlay " + u);
        this.u = u;
    }
    
    @Deprecated
    public void a(final float v) {
        TXCLog.i("TXLivePlayer", "liteav_api setRate " + v);
        this.v = v;
        if (this.t != null) {
            this.t.b(v);
        }
    }
    
    @Override
    public void onNotifyEvent(int n, final Bundle bundle) {
        if (n == 15001) {
            if (this.a != null) {
                this.a.setLogText(bundle, null, 0);
            }
            if (this.e != null) {
                this.e.onNetStatus(bundle);
            }
        }
        else if (n == 2005) {
            final long n2 = bundle.getInt("EVT_PLAY_PROGRESS_MS") + this.A;
            if (n2 > 0L) {
                bundle.putInt("EVT_PLAY_PROGRESS", (int)(n2 / 1000L));
                bundle.putInt("EVT_PLAY_PROGRESS_MS", (int)n2);
                if (this.e != null) {
                    this.e.onPlayEvent(n, bundle);
                }
            }
        }
        else if (n != 2026) {
            switch (n) {
                case 2003: {
                    break;
                }
                case 2105: {
                    break;
                }
                case 2007: {
                    n = 2007;
                    break;
                }
                case 2004: {
                    n = 2004;
                    break;
                }
                case 2008:
                case 2021:
                case 2022: {
                    n = 2008;
                    break;
                }
                case 2106: {
                    n = 2106;
                    break;
                }
                case 2109: {
                    return;
                }
                case 2101: {
                    n = 2101;
                    break;
                }
                case -2304: {
                    n = -2304;
                    break;
                }
                case 2001: {
                    n = 2001;
                    break;
                }
                case 3003: {
                    n = 3003;
                    break;
                }
                case 3009: {
                    n = 3002;
                    break;
                }
                case 2103: {
                    n = 2103;
                    break;
                }
                case 2002: {
                    n = 2002;
                    break;
                }
                case 3010: {
                    n = 3002;
                    break;
                }
                case -2301: {
                    n = -2301;
                    break;
                }
                case -2302: {
                    n = -2302;
                    break;
                }
                case -2309: {
                    n = -2301;
                    break;
                }
                case 3006:
                case 3007: {
                    n = 3005;
                    break;
                }
                case 2012: {
                    n = 2012;
                    break;
                }
                case 2015: {
                    n = 2015;
                    break;
                }
                case -2307: {
                    n = -2307;
                    break;
                }
                case 2005: {
                    n = 2005;
                    break;
                }
                case 2009: {
                    n = 2009;
                    break;
                }
                case 2028: {
                    n = 2028;
                    break;
                }
                case 2013: {
                    n = 2013;
                    break;
                }
                case 2031: {
                    n = 2031;
                    break;
                }
                default: {
                    return;
                }
            }
            if (this.a != null) {
                this.a.setLogText(null, bundle, n);
            }
            if (this.e != null) {
                this.e.onPlayEvent(n, bundle);
            }
        }
    }
    
    private boolean h() {
        return Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("Che2-TL00");
    }
    
    private String c(String s, final int n) {
        if (n != 6) {
            try {
                final byte[] bytes = s.getBytes("UTF-8");
                final StringBuilder sb = new StringBuilder(bytes.length);
                for (int i = 0; i < bytes.length; ++i) {
                    final int n2 = (bytes[i] < 0) ? (bytes[i] + 256) : bytes[i];
                    if (n2 <= 32 || n2 >= 127 || n2 == 34 || n2 == 37 || n2 == 60 || n2 == 62 || n2 == 91 || n2 == 125 || n2 == 92 || n2 == 93 || n2 == 94 || n2 == 96 || n2 == 123 || n2 == 124) {
                        sb.append(String.format("%%%02X", n2));
                    }
                    else {
                        sb.append((char)n2);
                    }
                }
                s = sb.toString();
            }
            catch (Exception ex) {
                TXCLog.e("TXLivePlayer", "check play url failed.", ex);
            }
        }
        s = s.trim();
        return s;
    }
    
    private void a(final TXLivePlayer.ITXSnapshotListener itxSnapshotListener, final Bitmap bitmap) {
        if (null == itxSnapshotListener) {
            return;
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (null != itxSnapshotListener) {
                    itxSnapshotListener.onSnapshot(bitmap);
                }
                com.tencent.rtmp.a.this.w = false;
            }
        });
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
            if (com.tencent.rtmp.a.this.t != null && com.tencent.rtmp.a.this.t.c()) {
                final int i = com.tencent.rtmp.a.this.t.i();
                if (com.tencent.rtmp.a.this.F != null) {
                    com.tencent.rtmp.a.this.F.onAudioVolumeEvaluationNotify(i);
                }
            }
            if (com.tencent.rtmp.a.this.s != null && this.b > 0) {
                com.tencent.rtmp.a.this.s.postDelayed((Runnable)com.tencent.rtmp.a.this.H, (long)this.b);
            }
        }
    }
}
