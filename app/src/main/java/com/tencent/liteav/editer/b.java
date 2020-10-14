package com.tencent.liteav.editer;

import java.util.concurrent.*;
import com.tencent.liteav.basic.log.*;
import android.media.*;
import android.os.*;
import java.lang.ref.*;

public class b
{
    private volatile AudioTrack a;
    private volatile e b;
    private LinkedBlockingDeque<e> c;
    private b d;
    private volatile a e;
    private int f;
    private int g;
    
    public b() {
        this.c = new LinkedBlockingDeque<e>();
    }
    
    private void b(final e e) {
        if (this.b == null) {
            this.b = e;
        }
        if (e.f() == 4) {
            this.e();
            return;
        }
        final byte[] array = e.b().array();
        final int remaining = e.b().remaining();
        if (remaining != 0) {
            try {
                if (this.a != null && this.a.getPlayState() == 3) {
                    this.a.write(array, e.b().arrayOffset(), remaining);
                    if (this.c.size() > 0) {
                        this.c.remove();
                    }
                    if (this.e != null) {
                        this.e.a(this.c.size());
                    }
                }
            }
            catch (Exception ex) {
                TXCLog.e("AudioTrackRender", "write data to AudioTrack failed.", ex);
            }
        }
        this.b = e;
    }
    
    public void a(final a e) {
        this.e = e;
    }
    
    public void a() {
        try {
            if (this.a != null) {
                this.a.pause();
            }
        }
        catch (Exception ex) {
            TXCLog.e("AudioTrackRender", "pause audio track failed.", ex);
        }
    }
    
    public void b() {
        try {
            if (this.a != null && this.a.getPlayState() != 3) {
                this.a.play();
            }
        }
        catch (Exception ex) {
            TXCLog.e("AudioTrackRender", "AudioTrack play failed.", ex);
        }
    }
    
    public void c() {
        this.a(this.g, this.f);
    }
    
    public void a(final MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            this.e();
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            final int integer = mediaFormat.getInteger("sample-rate");
            final int integer2 = mediaFormat.getInteger("channel-count");
            if (this.f != integer || this.g != integer2) {
                this.e();
            }
            this.f = integer;
            this.g = integer2;
            TXCLog.i("AudioTrackRender", "setAudioFormat sampleRate=" + integer + ",channelCount=" + integer2);
        }
    }
    
    public void a(final e e) {
        if (this.d == null || !this.d.isAlive() || this.d.isInterrupted()) {
            (this.d = new b(this)).start();
        }
        this.c.add(e);
        if (this.e != null) {
            this.e.a(this.c.size());
        }
    }
    
    private boolean a(final int n, final int n2) {
        int n3 = 0;
        if (n == 1) {
            n3 = 4;
        }
        else if (n == 2 || n == 3) {
            n3 = 12;
        }
        else if (n == 4 || n == 5) {
            n3 = 204;
        }
        else if (n == 6 || n == 7) {
            n3 = 252;
        }
        else if (n == 8) {
            n3 = 6396;
        }
        if (this.a == null) {
            final int minBufferSize = AudioTrack.getMinBufferSize(n2, n3, 2);
            try {
                (this.a = new AudioTrack(3, n2, n3, 2, minBufferSize, 1)).play();
            }
            catch (IllegalArgumentException ex) {
                TXCLog.e("AudioTrackRender", "new AudioTrack IllegalArgumentException: " + ex + ", sampleRate: " + n2 + ", channelType: " + n3 + ", minBufferLen: " + minBufferSize);
                this.a = null;
                return true;
            }
            catch (IllegalStateException ex2) {
                TXCLog.e("AudioTrackRender", "new AudioTrack IllegalArgumentException: " + ex2 + ", sampleRate: " + n2 + ", channelType: " + n3 + ", minBufferLen: " + minBufferSize);
                if (this.a != null) {
                    this.a.release();
                }
                this.a = null;
                return true;
            }
        }
        return false;
    }
    
    private void e() {
        try {
            if (this.a != null) {
                this.a.stop();
                this.a.release();
            }
            this.a = null;
        }
        catch (Exception ex) {
            this.a = null;
            TXCLog.e("AudioTrackRender", "audio track stop exception: " + ex);
        }
    }
    
    public void d() {
        this.c.clear();
        if (this.d != null) {
            this.d.a();
            this.d = null;
        }
        TXCLog.i("AudioTrackRender", "mPlayPCMThread:" + this.d);
        this.b = null;
        this.e();
    }
    
    private static class b extends Thread
    {
        private WeakReference<com.tencent.liteav.editer.b> a;
        
        public b(final com.tencent.liteav.editer.b b) {
            super("PlayPCMThread for Video Editer");
            this.a = new WeakReference<com.tencent.liteav.editer.b>(b);
        }
        
        @Override
        public void run() {
            super.run();
            while (!this.isInterrupted()) {
                try {
                    final e b = this.b();
                    if (b == null) {
                        continue;
                    }
                    this.a(b);
                    continue;
                }
                catch (Exception ex) {
                    TXCLog.e("AudioTrackRender", "play frame failed.", ex);
                }
                break;
            }
        }
        
        private void a(final e e) {
            this.c();
            this.a.get().b(e);
        }
        
        private e b() throws InterruptedException {
            this.c();
            return (e)this.a.get().c.peek();
        }
        
        private void c() {
            if (this.a.get() == null) {
                throw new RuntimeException("can't reach the object: AudioTrackRender");
            }
        }
        
        public void a() {
            this.interrupt();
            this.a.clear();
            this.a = null;
        }
    }
    
    public interface a
    {
        void a(final int p0);
    }
}
