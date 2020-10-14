package com.tencent.liteav.network;

import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.b.*;
import java.lang.ref.*;
import java.util.*;
import android.os.*;

public class d implements h
{
    private h a;
    private b b;
    private long c;
    private long d;
    private b e;
    private a f;
    private long g;
    private long h;
    
    public void a(final h a) {
        this.a = a;
    }
    
    public d(final a f) {
        this.a = null;
        this.b = null;
        this.c = 0L;
        this.d = 0L;
        this.e = null;
        this.g = 0L;
        this.h = 0L;
        this.f = f;
    }
    
    public void a() {
        if (this.b != null) {
            this.b.b(0L);
        }
        if (this.e != null) {
            this.e.b(0L);
        }
    }
    
    public void a(final TXIStreamDownloader txiStreamDownloader, final TXIStreamDownloader txiStreamDownloader2, final long n, final long n2, final String originUrl) {
        this.c = txiStreamDownloader.getCurrentTS();
        this.d = txiStreamDownloader.getLastIFrameTS();
        (this.b = new b(txiStreamDownloader, this)).a(this);
        ((TXCFLVDownloader)txiStreamDownloader).recvData(true);
        final Vector<e> vector = new Vector<e>();
        vector.add(new e(originUrl, false));
        txiStreamDownloader2.setOriginUrl(originUrl);
        ((TXCFLVDownloader)txiStreamDownloader2).recvData(true);
        txiStreamDownloader2.startDownload(vector, false, false, txiStreamDownloader.mEnableMessage, txiStreamDownloader.mEnableMetaData);
        (this.e = new b(txiStreamDownloader2, this)).a(this.c);
    }
    
    public void b() {
        this.b.a((h)null);
        this.e.a(this);
        this.b = this.e;
        this.e = null;
        TXCLog.w("TXCMultiStreamDownloader", " stream_switch end at " + this.c + " stop ts " + this.h + " start ts " + this.g + " diff ts " + ((this.h > this.g) ? (this.h - this.g) : (this.g - this.h)));
    }
    
    void a(final TXIStreamDownloader txiStreamDownloader, final boolean b) {
        if (this.f != null) {
            this.f.onSwitchFinish(txiStreamDownloader, b);
        }
    }
    
    long a(final long n) {
        if (this.b != null) {
            this.b.b(this.c);
        }
        TXCLog.w("TXCMultiStreamDownloader", " stream_switch delay stop begin from " + this.c);
        return this.c;
    }
    
    void b(final long g) {
        this.g = g;
    }
    
    void c(final long h) {
        this.h = h;
    }
    
    @Override
    public void onPullAudio(final com.tencent.liteav.basic.structs.a a) {
        if (this.a != null) {
            this.a.onPullAudio(a);
        }
    }
    
    @Override
    public void onPullNAL(final TXSNALPacket txsnalPacket) {
        this.c = txsnalPacket.pts;
        if (txsnalPacket.nalType == 0) {
            this.d = txsnalPacket.pts;
        }
        if (this.a != null) {
            this.a.onPullNAL(txsnalPacket);
        }
    }
    
    private static class b implements com.tencent.liteav.basic.b.b, h
    {
        private final int a = 2;
        private long b;
        private long c;
        private int d;
        private boolean e;
        private long f;
        private long g;
        private long h;
        private ArrayList<TXSNALPacket> i;
        private ArrayList<com.tencent.liteav.basic.structs.a> j;
        private TXIStreamDownloader k;
        private WeakReference<d> l;
        private h m;
        
        public b(final TXIStreamDownloader k, final d d) {
            this.b = 0L;
            this.c = 0L;
            this.d = 0;
            this.e = false;
            this.f = 0L;
            this.g = 0L;
            this.h = 0L;
            this.i = new ArrayList<TXSNALPacket>();
            this.j = new ArrayList<com.tencent.liteav.basic.structs.a>();
            this.k = null;
            this.l = new WeakReference<d>(d);
            (this.k = k).setListener(this);
        }
        
        public void a(final long b) {
            this.d = 0;
            this.b = b;
            this.k.setListener(this);
            this.k.setNotifyListener(this);
        }
        
        public void b(final long f) {
            this.b = 0L;
            this.f = f;
            this.h = 0L;
            this.g = 0L;
            if (this.k != null && this.f == 0L) {
                this.k.stopDownload();
                this.k = null;
            }
        }
        
        public void a(final h m) {
            this.m = m;
        }
        
        @Override
        public void onPullAudio(final com.tencent.liteav.basic.structs.a a) {
            if (this.b > 0L) {
                this.a(a);
            }
            else if (this.f > 0L) {
                this.b(a);
            }
            else if (this.m != null) {
                this.m.onPullAudio(a);
            }
        }
        
        @Override
        public void onPullNAL(final TXSNALPacket txsnalPacket) {
            if (txsnalPacket == null) {
                return;
            }
            if (this.b > 0L) {
                this.a(txsnalPacket);
            }
            else if (this.f > 0L) {
                this.b(txsnalPacket);
            }
            else if (this.m != null) {
                this.m.onPullNAL(txsnalPacket);
            }
        }
        
        private void a(final com.tencent.liteav.basic.structs.a a) {
            if (a == null) {
                return;
            }
            if (a.timestamp < this.c || a.timestamp < this.b) {
                return;
            }
            if (this.m != null && this.c > 0L && a.timestamp >= this.c) {
                this.m.onPullAudio(a);
            }
            else {
                this.j.add(a);
            }
        }
        
        private void b(final com.tencent.liteav.basic.structs.a a) {
            if (this.h > 0L) {
                return;
            }
            if (this.g > 0L && a != null && a.timestamp >= this.g) {
                this.h = a.timestamp;
                return;
            }
            if (this.m != null) {
                this.m.onPullAudio(a);
            }
        }
        
        private void a(final TXSNALPacket txsnalPacket) {
            final d d = this.l.get();
            if (txsnalPacket.nalType == 0 && !this.e) {
                ++this.d;
                if (d != null && (d.d <= txsnalPacket.pts || this.d == 2)) {
                    this.b = d.a(txsnalPacket.pts);
                    this.e = true;
                }
                if (d != null) {
                    TXCLog.w("TXCMultiStreamDownloader", " stream_switch pre start begin gop " + this.d + " last iframe ts " + d.d + " pts " + txsnalPacket.pts + " from " + this.b + " type " + txsnalPacket.nalType);
                }
            }
            if (!this.e) {
                return;
            }
            if (d != null) {
                d.b(txsnalPacket.pts);
            }
            if (txsnalPacket.pts >= this.b) {
                if (txsnalPacket.nalType == 0 && this.c == 0L) {
                    this.c = txsnalPacket.pts;
                    TXCLog.w("TXCMultiStreamDownloader", " stream_switch pre start end " + txsnalPacket.pts + " from " + this.b + " type " + txsnalPacket.nalType);
                }
                if (this.c > 0L) {
                    if (this.m != null) {
                        if (d != null) {
                            d.a(this.k, true);
                        }
                        if (!this.j.isEmpty()) {
                            for (final com.tencent.liteav.basic.structs.a a : this.j) {
                                if (a.timestamp >= this.c) {
                                    TXCLog.i("TXCMultiStreamDownloader", " stream_switch pre start cache audio pts " + a.timestamp + " from " + this.c);
                                    this.m.onPullAudio(a);
                                }
                            }
                            TXCLog.w("TXCMultiStreamDownloader", " stream_switch pre start end audio cache  " + this.j.size());
                            this.j.clear();
                        }
                        if (!this.i.isEmpty()) {
                            TXCLog.w("TXCMultiStreamDownloader", " stream_switch pre start end video cache  " + this.i.size());
                            final Iterator<TXSNALPacket> iterator2 = this.i.iterator();
                            while (iterator2.hasNext()) {
                                this.m.onPullNAL(iterator2.next());
                            }
                            this.i.clear();
                        }
                        TXCLog.w("TXCMultiStreamDownloader", " stream_switch pre start first pull nal " + txsnalPacket.pts + " from " + this.c + " type " + txsnalPacket.nalType);
                        this.m.onPullNAL(txsnalPacket);
                        this.m = null;
                        this.k.setNotifyListener(null);
                    }
                    else {
                        TXCLog.i("TXCMultiStreamDownloader", " stream_switch pre start cache video pts " + txsnalPacket.pts + " from " + this.c + " type " + txsnalPacket.nalType);
                        this.i.add(txsnalPacket);
                    }
                }
            }
        }
        
        private void b(final TXSNALPacket txsnalPacket) {
            final d d = this.l.get();
            if (d != null) {
                d.c(txsnalPacket.pts);
            }
            if (txsnalPacket.pts >= this.f) {
                if (txsnalPacket.nalType == 0) {
                    this.g = txsnalPacket.pts;
                }
                if (this.g > 0L) {
                    if (this.h > 0L) {
                        TXCLog.w("TXCMultiStreamDownloader", " stream_switch delay stop end video pts " + this.g + " audio ts " + this.h + " from " + this.f);
                        if (d != null) {
                            d.b();
                        }
                        this.m = null;
                        this.k.setListener(null);
                        this.k.stopDownload();
                    }
                    else {
                        TXCLog.w("TXCMultiStreamDownloader", " stream_switch delay stop video end wait audio end video pts " + txsnalPacket.pts + " from " + this.f + " type " + txsnalPacket.nalType);
                    }
                }
                else if (this.m != null) {
                    this.m.onPullNAL(txsnalPacket);
                }
            }
            else if (this.m != null) {
                this.m.onPullNAL(txsnalPacket);
            }
        }
        
        @Override
        public void onNotifyEvent(final int n, final Bundle bundle) {
            if (n == -2301 || n == 3010) {
                final d d = this.l.get();
                if (d != null) {
                    d.a(this.k, false);
                }
                this.k.setNotifyListener(null);
            }
        }
    }
    
    public interface a
    {
        void onSwitchFinish(final TXIStreamDownloader p0, final boolean p1);
    }
}
