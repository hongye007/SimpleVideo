package com.tencent.liteav.c;

import android.media.*;
import java.util.concurrent.atomic.*;
import android.text.*;
import java.io.*;
import android.os.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.basic.log.*;

public class i
{
    private static i u;
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public g h;
    public String i;
    public int j;
    public long k;
    public long l;
    public boolean m;
    public boolean n;
    public String o;
    public int p;
    public int q;
    public boolean r;
    public int s;
    private MediaFormat v;
    private MediaFormat w;
    private int x;
    private MediaFormat y;
    public AtomicInteger t;
    
    public static i a() {
        if (i.u == null) {
            i.u = new i();
        }
        return i.u;
    }
    
    protected i() {
        this.x = 0;
        this.f = 5000;
        this.g = 20;
        this.e = 3;
        this.a = 48000;
        this.b = 1;
        this.d = 98304;
        this.n = true;
        this.t = new AtomicInteger(1);
    }
    
    public boolean b() {
        return TextUtils.isEmpty((CharSequence)this.i);
    }
    
    public boolean c() {
        return new File(this.i).exists();
    }
    
    public boolean d() {
        return TextUtils.isEmpty((CharSequence)this.o);
    }
    
    public boolean e() {
        return this.r && this.m;
    }
    
    public void f() {
        if (this.d()) {
            return;
        }
        final File file = new File(this.o);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void g() {
        if (this.b()) {
            return;
        }
        final File file = new File(this.i);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public int h() {
        if (this.q == 0) {
            return this.d;
        }
        return this.q;
    }
    
    public int i() {
        if (this.m) {
            if (this.h.a < 1280 && this.h.b < 1280) {
                this.f = 24000;
            }
            else {
                this.f = 15000;
            }
        }
        else if (this.p != 0) {
            this.f = this.p;
        }
        else {
            switch (this.j) {
                case 0:
                case 1: {
                    this.f = 2400;
                    break;
                }
                case 2: {
                    this.f = 6500;
                    break;
                }
                case 3: {
                    this.f = 9600;
                    break;
                }
            }
        }
        return this.f;
    }
    
    public int j() {
        try {
            if (this.y != null && Build.VERSION.SDK_INT >= 16) {
                this.g = this.y.getInteger("frame-rate");
            }
        }
        catch (NullPointerException ex) {
            this.g = 20;
        }
        return this.g;
    }
    
    public int k() {
        try {
            if (this.y != null && Build.VERSION.SDK_INT >= 16) {
                this.e = this.y.getInteger("i-frame-interval");
            }
        }
        catch (NullPointerException ex) {
            this.e = 3;
        }
        return this.e;
    }
    
    public g a(final g g) {
        g g2 = g;
        if (g.a == 0 || g.b == 0) {
            return g;
        }
        if (CameraProxy.k.a().d() == 2) {
            switch (this.j) {
                case 0: {
                    g2 = this.i(g);
                    break;
                }
                case 1: {
                    g2 = this.h(g);
                    break;
                }
                case 2: {
                    g2 = this.g(g);
                    break;
                }
                case 3: {
                    g2 = this.f(g);
                    break;
                }
            }
        }
        else {
            switch (this.j) {
                case 0: {
                    g2 = this.b(g);
                    break;
                }
                case 1: {
                    g2 = this.c(g);
                    break;
                }
                case 2: {
                    g2 = this.d(g);
                    break;
                }
                case 3: {
                    g2 = this.e(g);
                    break;
                }
            }
        }
        final g g3 = new g();
        g3.c = g2.c;
        final int e = CameraProxy.j.a().e();
        if (e == 90 || e == 270) {
            g3.a = (g2.b + 15) / 16 * 16;
            g3.b = (g2.a + 15) / 16 * 16;
        }
        else {
            g3.a = (g2.a + 15) / 16 * 16;
            g3.b = (g2.b + 15) / 16 * 16;
        }
        return g3;
    }
    
    private g f(final g g) {
        final g g2 = new g();
        int n;
        int n2;
        if (g.a / (float)g.b >= 0.5625f) {
            n = 720;
            n2 = (int)(720.0f * g.b / g.a);
        }
        else {
            n = (int)(1280.0f * g.a / g.b);
            n2 = 1280;
        }
        g2.a = (n + 15) / 16 * 16;
        g2.b = (n2 + 15) / 16 * 16;
        return g2;
    }
    
    private g g(final g g) {
        final g g2 = new g();
        int n;
        int n2;
        if (g.a / (float)g.b >= 0.5625f) {
            n = 540;
            n2 = (int)(540.0f * g.b / g.a);
        }
        else {
            n = (int)(960.0f * g.a / g.b);
            n2 = 960;
        }
        g2.a = (n + 15) / 16 * 16;
        g2.b = (n2 + 15) / 16 * 16;
        return g2;
    }
    
    private g h(final g g) {
        final g g2 = new g();
        int n;
        int n2;
        if (g.a / (float)g.b >= 0.5625f) {
            n = 360;
            n2 = (int)(360.0f * g.b / g.a);
        }
        else {
            n = (int)(640.0f * g.a / g.b);
            n2 = 640;
        }
        g2.a = (n + 15) / 16 * 16;
        g2.b = (n2 + 15) / 16 * 16;
        return g2;
    }
    
    private g i(final g g) {
        final g g2 = new g();
        int n;
        int n2;
        if (g.a / (float)g.b >= 0.5625f) {
            n = 720;
            n2 = (int)(720.0f * g.b / g.a);
        }
        else {
            n = (int)(1280.0f * g.a / g.b);
            n2 = 1280;
        }
        g2.a = (n + 15) / 16 * 16;
        g2.b = (n2 + 15) / 16 * 16;
        return g2;
    }
    
    protected g b(final g g) {
        final g g2 = new g();
        if ((g.a <= 640 && g.b <= 360) || (g.a <= 360 && g.b <= 640)) {
            return this.a(g.c, g);
        }
        final float n = g.a * 1.0f / g.b;
        int n3;
        int n4;
        if (g.a >= g.b) {
            final int n2 = (int)(360.0f * n);
            n3 = ((n2 >= 640) ? 640 : n2);
            n4 = (int)(n3 / n);
        }
        else {
            final int n5 = (int)(640.0f * n);
            n3 = ((n5 >= 360) ? 360 : n5);
            n4 = (int)(n3 / n);
        }
        final int a = n3 + 1 >> 1 << 1;
        final int b = n4 + 1 >> 1 << 1;
        g2.a = a;
        g2.b = b;
        return this.a(g.c, g2);
    }
    
    protected g c(final g g) {
        final g g2 = new g();
        if ((g.a <= 640 && g.b <= 480) || (g.a <= 480 && g.b <= 640)) {
            return this.a(g.c, g);
        }
        final float n = g.a * 1.0f / g.b;
        int n3;
        int n4;
        if (g.a >= g.b) {
            final int n2 = (int)(480.0f * n);
            n3 = ((n2 >= 640) ? 640 : n2);
            n4 = (int)(n3 / n);
        }
        else {
            final int n5 = (int)(640.0f * n);
            n3 = ((n5 >= 480) ? 480 : n5);
            n4 = (int)(n3 / n);
        }
        final int a = n3 + 1 >> 1 << 1;
        final int b = n4 + 1 >> 1 << 1;
        g2.a = a;
        g2.b = b;
        return this.a(g.c, g2);
    }
    
    protected g d(final g g) {
        final g g2 = new g();
        if ((g.a <= 960 && g.b <= 544) || (g.a <= 544 && g.b <= 960)) {
            return this.a(g.c, g);
        }
        final float n = g.a * 1.0f / g.b;
        int n3;
        int n4;
        if (g.a >= g.b) {
            final int n2 = (int)(544.0f * n);
            n3 = ((n2 >= 960) ? 960 : n2);
            n4 = (int)(n3 / n);
        }
        else {
            final int n5 = (int)(960.0f * n);
            n3 = ((n5 >= 544) ? 544 : n5);
            n4 = (int)(n3 / n);
        }
        final int a = n3 + 1 >> 1 << 1;
        final int b = n4 + 1 >> 1 << 1;
        g2.a = a;
        g2.b = b;
        return this.a(g.c, g2);
    }
    
    protected g e(final g g) {
        final g g2 = new g();
        if ((g.a <= 1280 && g.b <= 720) || (g.a <= 720 && g.b <= 1280)) {
            return this.a(g.c, g);
        }
        final float n = g.a * 1.0f / g.b;
        int n3;
        int n4;
        if (g.a >= g.b) {
            final int n2 = (int)(720.0f * n);
            n3 = ((n2 >= 1280) ? 1280 : n2);
            n4 = (int)(n3 / n);
        }
        else {
            final int n5 = (int)(1280.0f * n);
            n3 = ((n5 >= 720) ? 720 : n5);
            n4 = (int)(n3 / n);
        }
        final int a = n3 + 1 >> 1 << 1;
        final int b = n4 + 1 >> 1 << 1;
        g2.a = a;
        g2.b = b;
        return this.a(g.c, g2);
    }
    
    private g a(final int n, final g g) {
        if (n == 90 || n == 270) {
            final int a = g.a;
            g.a = g.b;
            g.b = a;
        }
        return g;
    }
    
    public void a(final MediaFormat v) {
        this.v = v;
    }
    
    public void b(final MediaFormat y) {
        this.y = y;
    }
    
    public void c(final MediaFormat w) {
        this.w = w;
    }
    
    public boolean l() {
        return this.v != null || this.w != null;
    }
    
    public boolean m() {
        return this.v == null && this.w != null;
    }
    
    public MediaFormat n() {
        final b b = new b();
        if (this.w == null) {
            if (this.v == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                b.b = this.v.getInteger("sample-rate");
                b.a = this.v.getInteger("channel-count");
                if (this.v.containsKey("bitrate")) {
                    b.c = this.v.getInteger("bitrate");
                }
            }
        }
        else if (Build.VERSION.SDK_INT >= 16) {
            if (this.v == null) {
                final int integer = this.w.getInteger("sample-rate");
                final int integer2 = this.w.getInteger("channel-count");
                b.b = integer;
                b.a = integer2;
                if (this.w.containsKey("bitrate")) {
                    b.c = this.w.getInteger("bitrate");
                }
            }
            else {
                this.v.getInteger("sample-rate");
                b.b = this.w.getInteger("sample-rate");
                final int integer3 = this.v.getInteger("channel-count");
                final int integer4 = this.w.getInteger("channel-count");
                b.a = ((integer3 >= integer4) ? integer3 : integer4);
                if (this.v.containsKey("bitrate")) {
                    b.c = this.v.getInteger("bitrate");
                }
            }
        }
        MediaFormat audioFormat = null;
        if (Build.VERSION.SDK_INT >= 16) {
            audioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", b.b, b.a);
            if (b.c != 0) {
                audioFormat.setInteger("bitrate", b.c);
            }
        }
        if (this.v != null && Build.VERSION.SDK_INT >= 16 && this.v.containsKey("max-input-size")) {
            this.c = this.v.getInteger("max-input-size");
        }
        this.a = b.b;
        this.b = b.a;
        if (b.c != 0) {
            this.d = b.c;
        }
        return audioFormat;
    }
    
    public void o() {
        if (!TextUtils.isEmpty((CharSequence)this.o)) {
            final File file = new File(this.o);
            if (file.exists()) {
                TXCLog.i("VideoOutputConfig", "clear delete process path:" + file.delete());
            }
        }
        this.k = 0L;
        this.o = null;
        this.i = null;
        this.w = null;
        this.v = null;
        this.p = 0;
        this.q = 0;
        this.n = true;
    }
    
    public int p() {
        if (TextUtils.isEmpty((CharSequence)this.i)) {
            return 0;
        }
        return (int)(new File(this.i).length() / 1024L);
    }
    
    public int q() {
        return Math.round((float)(this.k / 1000L / 1000L));
    }
}
