package com.tencent.liteav.txcvodplayer;

import android.content.*;
import android.util.*;
import android.view.*;
import com.tencent.liteav.basic.log.*;
import android.view.accessibility.*;
import android.os.*;
import android.annotation.*;
import android.support.annotation.*;
import com.tencent.ijk.media.player.*;
import android.graphics.*;
import java.lang.ref.*;
import java.util.concurrent.*;
import java.util.*;

public class SurfaceRenderView extends SurfaceView implements com.tencent.liteav.txcvodplayer.a
{
    private c a;
    private b b;
    
    public SurfaceRenderView(final Context context) {
        super(context);
        this.a(context);
    }
    
    public SurfaceRenderView(final Context context, final AttributeSet set) {
        super(context, set);
        this.a(context);
    }
    
    public SurfaceRenderView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a(context);
    }
    
    private void a(final Context context) {
        this.a = new c((View)this);
        this.b = new b(this);
        this.getHolder().addCallback((SurfaceHolder.Callback)this.b);
        this.getHolder().setType(0);
    }
    
    public View getView() {
        return (View)this;
    }
    
    public boolean shouldWaitForResize() {
        return true;
    }
    
    public void setVideoSize(final int n, final int n2) {
        if (n > 0 && n2 > 0) {
            this.a.a(n, n2);
            this.getHolder().setFixedSize(n, n2);
            this.requestLayout();
        }
    }
    
    public void setVideoSampleAspectRatio(final int n, final int n2) {
        if (n > 0 && n2 > 0) {
            this.a.b(n, n2);
            this.requestLayout();
        }
    }
    
    public void setVideoRotation(final int n) {
        TXCLog.e("", "SurfaceView doesn't support rotation (" + n + ")!\n");
    }
    
    public void setAspectRatio(final int n) {
        this.a.b(n);
        this.requestLayout();
    }
    
    protected void onMeasure(final int n, final int n2) {
        this.a.c(n, n2);
        this.setMeasuredDimension(this.a.a(), this.a.b());
    }
    
    public void addRenderCallback(final com.tencent.liteav.txcvodplayer.a.a a) {
        this.b.a(a);
    }
    
    public void removeRenderCallback(final com.tencent.liteav.txcvodplayer.a.a a) {
        this.b.b(a);
    }
    
    public void onInitializeAccessibilityEvent(final AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)SurfaceRenderView.class.getName());
    }
    
    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(final AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (Build.VERSION.SDK_INT >= 14) {
            accessibilityNodeInfo.setClassName((CharSequence)SurfaceRenderView.class.getName());
        }
    }
    
    private static final class a implements com.tencent.liteav.txcvodplayer.a.b
    {
        private SurfaceRenderView a;
        private SurfaceHolder b;
        
        public a(@NonNull final SurfaceRenderView a, @Nullable final SurfaceHolder b) {
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void a(final IMediaPlayer mediaPlayer) {
            if (mediaPlayer != null) {
                if (Build.VERSION.SDK_INT >= 16 && mediaPlayer instanceof ISurfaceTextureHolder) {
                    ((ISurfaceTextureHolder)mediaPlayer).setSurfaceTexture(null);
                }
                mediaPlayer.setDisplay(this.b);
            }
        }
        
        @NonNull
        @Override
        public com.tencent.liteav.txcvodplayer.a a() {
            return this.a;
        }
    }
    
    private static final class b implements SurfaceHolder.Callback
    {
        private SurfaceHolder a;
        private boolean b;
        private int c;
        private int d;
        private int e;
        private WeakReference<SurfaceRenderView> f;
        private Map<com.tencent.liteav.txcvodplayer.a.a, Object> g;
        
        public b(@NonNull final SurfaceRenderView surfaceRenderView) {
            this.g = new ConcurrentHashMap<com.tencent.liteav.txcvodplayer.a.a, Object>();
            this.f = new WeakReference<SurfaceRenderView>(surfaceRenderView);
        }
        
        public void a(@NonNull final com.tencent.liteav.txcvodplayer.a.a a) {
            this.g.put(a, a);
            com.tencent.liteav.txcvodplayer.a.b b = null;
            if (this.a != null) {
                if (b == null) {
                    b = new a(this.f.get(), this.a);
                }
                a.a(b, this.d, this.e);
            }
            if (this.b) {
                if (b == null) {
                    b = new a(this.f.get(), this.a);
                }
                a.a(b, this.c, this.d, this.e);
            }
        }
        
        public void b(@NonNull final com.tencent.liteav.txcvodplayer.a.a a) {
            this.g.remove(a);
        }
        
        public void surfaceCreated(final SurfaceHolder a) {
            this.a = a;
            this.b = false;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            final a a2 = new a(this.f.get(), this.a);
            final Iterator<com.tencent.liteav.txcvodplayer.a.a> iterator = this.g.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().a(a2, 0, 0);
            }
        }
        
        public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
            this.a = null;
            this.b = false;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            final a a = new a(this.f.get(), this.a);
            final Iterator<com.tencent.liteav.txcvodplayer.a.a> iterator = this.g.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().a(a);
            }
        }
        
        public void surfaceChanged(final SurfaceHolder a, final int c, final int d, final int e) {
            this.a = a;
            this.b = true;
            this.c = c;
            this.d = d;
            this.e = e;
            final a a2 = new a(this.f.get(), this.a);
            final Iterator<com.tencent.liteav.txcvodplayer.a.a> iterator = this.g.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().a(a2, c, d, e);
            }
        }
    }
}
