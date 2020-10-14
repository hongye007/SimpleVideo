package com.tencent.liteav.beauty;

import com.tencent.liteav.basic.license.*;
import com.tencent.liteav.basic.log.*;
import android.graphics.*;
import android.os.*;

public class b implements TXBeautyManager
{
    private e a;
    private com.tencent.liteav.beauty.e b;
    private int c;
    private a d;
    private boolean e;
    
    public b(final e a) {
        this.e = true;
        this.a = a;
        this.d = new a();
        this.enableSharpnessEnhancement(true);
        this.setFilterStrength(0.5f);
    }
    
    @Override
    public void setPreprocessor(final com.tencent.liteav.beauty.e b) {
        this.b = b;
        if (this.b != null) {
            this.a();
        }
    }
    
    private void a() {
        TXCLog.d("TXBeautyManager", "applyBeautyParams");
        this.b.b(this.c);
        this.b(this.e);
        if (this.a.a()) {
            this.b.g(this.d.e);
            this.b.h(this.d.f);
            this.b.i(this.d.g);
            this.b.k(this.d.h);
            this.b.j(this.d.i);
            this.b.l(this.d.j);
            this.b.m(this.d.k);
            this.b.n(this.d.l);
            this.b.o(this.d.m);
            this.b.p(this.d.n);
            this.b.q(this.d.o);
            this.b.r(this.d.p);
            this.b.s(this.d.q);
            this.b.t(this.d.r);
            this.b.u(this.d.s);
            this.b.v(this.d.t);
            this.b.w(this.d.u);
            this.b.x(this.d.v);
            this.b.y(this.d.w);
            this.b.a(this.d.B, true);
        }
        this.b.a(this.d.z);
        this.b.a(this.d.A);
        this.b.a(this.d.x);
        this.b.c(this.d.y);
    }
    
    public void a(final boolean e) {
        this.b(this.e = e);
    }
    
    private void b(final boolean b) {
        if (b) {
            this.b.c(this.d.a);
            this.b.d(this.d.b);
            this.b.e(this.d.c);
            this.b.f(this.d.d);
        }
        else {
            this.b.c(0);
            this.b.d(0);
            this.b.e(0);
            this.b.f(0);
        }
    }
    
    @Override
    public void setBeautyStyle(final int c) {
        TXCLog.d("TXBeautyManager", "setBeautyStyle beautyStyle:" + c);
        this.c = c;
        if (this.b != null) {
            this.b.b(c);
        }
    }
    
    @Override
    public void setFilter(final Bitmap z) {
        TXCLog.d("TXBeautyManager", "setFilter image:" + z);
        this.d.z = z;
        if (this.b != null) {
            this.b.a(z);
        }
    }
    
    @Override
    public void setFilterStrength(final float a) {
        TXCLog.d("TXBeautyManager", "setFilterStrength strength:" + a);
        this.d.A = a;
        if (this.b != null) {
            this.b.a(a);
        }
    }
    
    @Override
    public void setGreenScreenFile(final String b) {
        TXCLog.d("TXBeautyManager", "setGreenScreenFile path:" + b);
        this.d.B = b;
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.e("TXBeautyManager", "setGreenScreenFile system version below 18");
            return;
        }
        if (!this.a.a()) {
            TXCLog.e("TXBeautyManager", "setGreenScreenFile is only supported in EnterprisePro license");
            return;
        }
        if (this.b != null) {
            this.b.a(b, true);
        }
    }
    
    @Override
    public void setBeautyLevel(final int a) {
        TXCLog.d("TXBeautyManager", "setBeautyLevel beautyLevel:" + a);
        this.d.a = a;
        if (this.b != null && this.e) {
            this.b.c(a);
        }
    }
    
    @Override
    public void setWhitenessLevel(final int b) {
        TXCLog.d("TXBeautyManager", "setWhitenessLevel whitenessLevel:" + b);
        this.d.b = b;
        if (this.b != null && this.e) {
            this.b.d(b);
        }
    }
    
    @Override
    public void enableSharpnessEnhancement(final boolean b) {
        TXCLog.d("TXBeautyManager", "enableSharpnessEnhancement enable: %b", b);
        this.d.d = (b ? 4 : 0);
        if (this.b != null && this.e) {
            this.b.f(this.d.d);
        }
    }
    
    @Override
    public void setRuddyLevel(final int c) {
        TXCLog.d("TXBeautyManager", "setRuddyLevel ruddyLevel:" + c);
        this.d.c = c;
        if (this.b != null && this.e) {
            this.b.e(c);
        }
    }
    
    @Override
    public void setEyeScaleLevel(final int e) {
        TXCLog.d("TXBeautyManager", "setEyeScaleLevel eyeScaleLevel:" + e);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.e = e;
        if (this.b != null) {
            this.b.g(e);
        }
    }
    
    @Override
    public void setFaceSlimLevel(final int f) {
        TXCLog.d("TXBeautyManager", "setFaceSlimLevel faceSlimLevel:" + f);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.f = f;
        if (this.b != null) {
            this.b.h(f);
        }
    }
    
    @Override
    public void setFaceVLevel(final int g) {
        TXCLog.d("TXBeautyManager", "setFaceVLevel faceVLevel:" + g);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.g = g;
        if (this.b != null) {
            this.b.i(g);
        }
    }
    
    @Override
    public void setChinLevel(final int h) {
        TXCLog.d("TXBeautyManager", "setChinLevel chinLevel:" + h);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.h = h;
        if (this.b != null) {
            this.b.k(h);
        }
    }
    
    @Override
    public void setFaceShortLevel(final int i) {
        TXCLog.d("TXBeautyManager", "setFaceShortLevel faceShortLevel:" + i);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.i = i;
        if (this.b != null) {
            this.b.j(i);
        }
    }
    
    @Override
    public void setNoseSlimLevel(final int j) {
        TXCLog.d("TXBeautyManager", "setNoseSlimLevel noseSlimLevel:" + j);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.j = j;
        if (this.b != null) {
            this.b.l(j);
        }
    }
    
    @Override
    public void setEyeLightenLevel(final int k) {
        TXCLog.d("TXBeautyManager", "setEyeLightenLevel eyeLightenLevel:" + k);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.k = k;
        if (this.b != null) {
            this.b.m(k);
        }
    }
    
    @Override
    public void setToothWhitenLevel(final int l) {
        TXCLog.d("TXBeautyManager", "setToothWhitenLevel toothWhitenLevel:" + l);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.l = l;
        if (this.b != null) {
            this.b.n(l);
        }
    }
    
    @Override
    public void setWrinkleRemoveLevel(final int m) {
        TXCLog.d("TXBeautyManager", "setWrinkleRemoveLevel wrinkleRemoveLevel:" + m);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.m = m;
        if (this.b != null) {
            this.b.o(m);
        }
    }
    
    @Override
    public void setPounchRemoveLevel(final int n) {
        TXCLog.d("TXBeautyManager", "setPounchRemoveLevel pounchRemoveLevel:" + n);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.n = n;
        if (this.b != null) {
            this.b.p(n);
        }
    }
    
    @Override
    public void setSmileLinesRemoveLevel(final int o) {
        TXCLog.d("TXBeautyManager", "setSmileLinesRemoveLevel smileLinesRemoveLevel:" + o);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.o = o;
        if (this.b != null) {
            this.b.q(o);
        }
    }
    
    @Override
    public void setForeheadLevel(final int p) {
        TXCLog.d("TXBeautyManager", "setForeheadLevel foreheadLevel:" + p);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.p = p;
        if (this.b != null) {
            this.b.r(p);
        }
    }
    
    @Override
    public void setEyeDistanceLevel(final int q) {
        TXCLog.d("TXBeautyManager", "setEyeDistanceLevel eyeDistanceLevel:" + q);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.q = q;
        if (this.b != null) {
            this.b.s(q);
        }
    }
    
    @Override
    public void setEyeAngleLevel(final int r) {
        TXCLog.d("TXBeautyManager", "setEyeAngleLevel eyeAngleLevel:" + r);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.r = r;
        if (this.b != null) {
            this.b.t(r);
        }
    }
    
    @Override
    public void setMouthShapeLevel(final int s) {
        TXCLog.d("TXBeautyManager", "setMouthShapeLevel mouthShapeLevel:" + s);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.s = s;
        if (this.b != null) {
            this.b.u(s);
        }
    }
    
    @Override
    public void setNoseWingLevel(final int t) {
        TXCLog.d("TXBeautyManager", "setNoseWingLevel noseWingLevel:" + t);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.t = t;
        if (this.b != null) {
            this.b.v(t);
        }
    }
    
    @Override
    public void setNosePositionLevel(final int u) {
        TXCLog.d("TXBeautyManager", "setNosePositionLevel nosePositionLevel:" + u);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.u = u;
        if (this.b != null) {
            this.b.w(u);
        }
    }
    
    @Override
    public void setLipsThicknessLevel(final int v) {
        TXCLog.d("TXBeautyManager", "setLipsThicknessLevel lipsThicknessLevel:" + v);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.v = v;
        if (this.b != null) {
            this.b.x(v);
        }
    }
    
    @Override
    public void setFaceBeautyLevel(final int w) {
        TXCLog.d("TXBeautyManager", "setFaceBeautyLevel faceBeautyLevel:" + w);
        if (!this.a.a()) {
            TXCLog.i("TXBeautyManager", "support EnterPrise above!!!");
            return;
        }
        this.d.w = w;
        if (this.b != null) {
            this.b.y(w);
        }
    }
    
    @Override
    public void setMotionTmpl(final String x) {
        TXCLog.d("TXBeautyManager", "setMotionTmpl tmplPath:" + x);
        this.d.x = x;
        if (this.b != null) {
            this.b.a(x);
        }
    }
    
    @Override
    public void setMotionMute(final boolean y) {
        TXCLog.d("TXBeautyManager", "setMotionMute motionMute:" + y);
        this.d.y = y;
        if (this.b != null) {
            this.b.c(y);
        }
    }
    
    class a
    {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public int r;
        public int s;
        public int t;
        public int u;
        public int v;
        public int w;
        public String x;
        public boolean y;
        public Bitmap z;
        public float A;
        public String B;
    }
}
