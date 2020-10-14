package com.tencent.liteav.txcvodplayer;

import android.annotation.*;
import android.content.*;
import android.util.*;
import android.view.accessibility.*;
import android.graphics.*;
import android.view.*;
import android.support.annotation.*;
import android.os.*;
import com.tencent.ijk.media.player.*;
import java.lang.ref.*;
import java.util.concurrent.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;

@TargetApi(14)
public class TextureRenderView extends TextureView implements a
{
    private static final String TAG = "TextureRenderView";
    private c mMeasureHelper;
    private b mSurfaceCallback;
    
    public TextureRenderView(final Context context) {
        super(context);
        this.initView(context);
    }
    
    public TextureRenderView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView(context);
    }
    
    public TextureRenderView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.initView(context);
    }
    
    @TargetApi(21)
    public TextureRenderView(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set, n, n2);
        this.initView(context);
    }
    
    private void initView(final Context context) {
        this.mMeasureHelper = new c((View)this);
        this.setSurfaceTextureListener((SurfaceTextureListener)(this.mSurfaceCallback = new b(this)));
    }
    
    public View getView() {
        return (View)this;
    }
    
    public boolean shouldWaitForResize() {
        return false;
    }
    
    protected void onDetachedFromWindow() {
        try {
            this.mSurfaceCallback.a();
            super.onDetachedFromWindow();
            this.mSurfaceCallback.b();
        }
        catch (Exception ex) {}
    }
    
    public void setVideoSize(final int n, final int n2) {
        if (n > 0 && n2 > 0) {
            this.mMeasureHelper.a(n, n2);
            this.requestLayout();
        }
    }
    
    public void setVideoSampleAspectRatio(final int n, final int n2) {
        if (n > 0 && n2 > 0) {
            this.mMeasureHelper.b(n, n2);
            this.requestLayout();
        }
    }
    
    public void setVideoRotation(final int n) {
        this.mMeasureHelper.a(n);
        this.setRotation((float)n);
    }
    
    public void setAspectRatio(final int n) {
        this.mMeasureHelper.b(n);
        this.requestLayout();
    }
    
    protected void onMeasure(final int n, final int n2) {
        this.mMeasureHelper.c(n, n2);
        this.setMeasuredDimension(this.mMeasureHelper.a(), this.mMeasureHelper.b());
    }
    
    public com.tencent.liteav.txcvodplayer.a.b getSurfaceHolder() {
        return new a(this, this.mSurfaceCallback.a, this.mSurfaceCallback);
    }
    
    public void addRenderCallback(final com.tencent.liteav.txcvodplayer.a.a a) {
        this.mSurfaceCallback.a(a);
    }
    
    public void removeRenderCallback(final com.tencent.liteav.txcvodplayer.a.a a) {
        this.mSurfaceCallback.b(a);
    }
    
    public void onInitializeAccessibilityEvent(final AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)TextureRenderView.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(final AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)TextureRenderView.class.getName());
    }
    
    private static final class a implements com.tencent.liteav.txcvodplayer.a.b
    {
        private TextureRenderView a;
        private SurfaceTexture b;
        private ISurfaceTextureHost c;
        private Surface d;
        
        public a(@NonNull final TextureRenderView a, @Nullable final SurfaceTexture b, @NonNull final ISurfaceTextureHost c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        @TargetApi(16)
        @Override
        public void a(final IMediaPlayer mediaPlayer) {
            if (mediaPlayer == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 16 && mediaPlayer instanceof ISurfaceTextureHolder) {
                final ISurfaceTextureHolder surfaceTextureHolder = (ISurfaceTextureHolder)mediaPlayer;
                this.a.mSurfaceCallback.a(false);
                if (this.a.getSurfaceTexture() != null) {
                    this.b = this.a.getSurfaceTexture();
                }
                try {
                    final SurfaceTexture surfaceTexture = surfaceTextureHolder.getSurfaceTexture();
                    if (surfaceTexture != null) {
                        surfaceTextureHolder.setSurfaceTextureHost(this.a.mSurfaceCallback);
                        this.a.setSurfaceTexture(surfaceTexture);
                        this.a.mSurfaceCallback.a(surfaceTexture);
                    }
                    else {
                        if (this.d != null) {
                            mediaPlayer.setSurface(this.d);
                        }
                        surfaceTextureHolder.setSurfaceTexture(this.b);
                        surfaceTextureHolder.setSurfaceTextureHost(this.a.mSurfaceCallback);
                    }
                    this.d = mediaPlayer.getSurface();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                mediaPlayer.setSurface(this.d = this.b());
            }
        }
        
        @NonNull
        @Override
        public com.tencent.liteav.txcvodplayer.a a() {
            return this.a;
        }
        
        @Nullable
        public Surface b() {
            if (this.b == null) {
                return null;
            }
            if (this.d == null) {
                this.d = new Surface(this.b);
            }
            return this.d;
        }
    }
    
    private static final class b implements SurfaceTextureListener, ISurfaceTextureHost
    {
        private SurfaceTexture a;
        private boolean b;
        private int c;
        private int d;
        private boolean e;
        private boolean f;
        private boolean g;
        private WeakReference<TextureRenderView> h;
        private Map<com.tencent.liteav.txcvodplayer.a.a, Object> i;
        
        public b(@NonNull final TextureRenderView textureRenderView) {
            this.e = true;
            this.f = false;
            this.g = false;
            this.i = new ConcurrentHashMap<com.tencent.liteav.txcvodplayer.a.a, Object>();
            this.h = new WeakReference<TextureRenderView>(textureRenderView);
        }
        
        public void a(final boolean e) {
            this.e = e;
        }
        
        public void a(final SurfaceTexture a) {
            this.a = a;
        }
        
        public void a(@NonNull final com.tencent.liteav.txcvodplayer.a.a a) {
            this.i.put(a, a);
            com.tencent.liteav.txcvodplayer.a.b b = null;
            if (this.a != null) {
                if (b == null) {
                    b = new a(this.h.get(), this.a, this);
                }
                a.a(b, this.c, this.d);
            }
            if (this.b) {
                if (b == null) {
                    b = new a(this.h.get(), this.a, this);
                }
                a.a(b, 0, this.c, this.d);
            }
        }
        
        public void b(@NonNull final com.tencent.liteav.txcvodplayer.a.a a) {
            this.i.remove(a);
        }
        
        public void onSurfaceTextureAvailable(final SurfaceTexture a, final int n, final int n2) {
            this.a = a;
            this.b = false;
            this.c = 0;
            this.d = 0;
            final a a2 = new a(this.h.get(), a, this);
            final Iterator<com.tencent.liteav.txcvodplayer.a.a> iterator = this.i.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().a(a2, 0, 0);
            }
        }
        
        public void onSurfaceTextureSizeChanged(final SurfaceTexture a, final int c, final int d) {
            this.a = a;
            this.b = true;
            this.c = c;
            this.d = d;
            final a a2 = new a(this.h.get(), a, this);
            final Iterator<com.tencent.liteav.txcvodplayer.a.a> iterator = this.i.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().a(a2, 0, c, d);
            }
        }
        
        public boolean onSurfaceTextureDestroyed(final SurfaceTexture a) {
            this.a = a;
            this.b = false;
            this.c = 0;
            this.d = 0;
            final a a2 = new a(this.h.get(), a, this);
            final Iterator<com.tencent.liteav.txcvodplayer.a.a> iterator = this.i.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().a(a2);
            }
            TXCLog.i("TextureRenderView", "onSurfaceTextureDestroyed: destroy: " + this.e);
            return this.e;
        }
        
        public void onSurfaceTextureUpdated(final SurfaceTexture surfaceTexture) {
        }
        
        public void releaseSurfaceTexture(final SurfaceTexture surfaceTexture) {
            if (surfaceTexture == null) {
                TXCLog.i("TextureRenderView", "releaseSurfaceTexture: null");
            }
            else if (this.g) {
                if (surfaceTexture != this.a) {
                    TXCLog.i("TextureRenderView", "releaseSurfaceTexture: didDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                }
                else if (!this.e) {
                    TXCLog.i("TextureRenderView", "releaseSurfaceTexture: didDetachFromWindow(): release detached SurfaceTexture");
                    surfaceTexture.release();
                }
                else {
                    TXCLog.i("TextureRenderView", "releaseSurfaceTexture: didDetachFromWindow(): already released by TextureView");
                }
            }
            else if (this.f) {
                if (surfaceTexture != this.a) {
                    TXCLog.i("TextureRenderView", "releaseSurfaceTexture: willDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                }
                else if (!this.e) {
                    TXCLog.i("TextureRenderView", "releaseSurfaceTexture: willDetachFromWindow(): re-attach SurfaceTexture to TextureView");
                    this.a(true);
                }
                else {
                    TXCLog.i("TextureRenderView", "releaseSurfaceTexture: willDetachFromWindow(): will released by TextureView");
                }
            }
            else if (surfaceTexture != this.a) {
                TXCLog.i("TextureRenderView", "releaseSurfaceTexture: alive: release different SurfaceTexture");
                surfaceTexture.release();
            }
            else if (!this.e) {
                TXCLog.i("TextureRenderView", "releaseSurfaceTexture: alive: re-attach SurfaceTexture to TextureView");
                this.a(true);
            }
            else {
                TXCLog.i("TextureRenderView", "releaseSurfaceTexture: alive: will released by TextureView");
            }
        }
        
        public void a() {
            TXCLog.i("TextureRenderView", "willDetachFromWindow()");
            this.f = true;
        }
        
        public void b() {
            TXCLog.i("TextureRenderView", "didDetachFromWindow()");
            this.g = true;
        }
    }
}
