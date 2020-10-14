package com.tencent.rtmp.a;

import android.text.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import android.graphics.*;
import android.os.*;
import java.net.*;
import java.lang.ref.*;
import java.io.*;

public class b implements com.tencent.rtmp.a.a
{
    private final BitmapFactory.Options a;
    private HandlerThread b;
    private Handler c;
    private List<c> d;
    private Map<String, BitmapRegionDecoder> e;
    
    public b() {
        this.a = new BitmapFactory.Options();
        this.d = new ArrayList<c>();
        this.d = Collections.synchronizedList(this.d);
        this.e = new HashMap<String, BitmapRegionDecoder>();
        this.e = Collections.synchronizedMap(this.e);
    }
    
    @Override
    public void setVTTUrlAndImageUrls(final String s, final List<String> list) {
        if (TextUtils.isEmpty((CharSequence)s)) {
            TXCLog.e("TXImageSprite", "setVTTUrlAndImageUrls: vttUrl can't be null!");
            return;
        }
        this.b();
        this.a();
        this.c.post((Runnable)new a(this, s));
        if (list != null && list.size() != 0) {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                this.c.post((Runnable)new b(this, s, iterator.next()));
            }
        }
    }
    
    @Override
    public Bitmap getThumbnail(final float n) {
        if (this.d.size() == 0) {
            return null;
        }
        final c a = this.a(0, this.d.size() - 1, n);
        if (a == null) {
            return null;
        }
        final BitmapRegionDecoder bitmapRegionDecoder = this.e.get(a.d);
        if (bitmapRegionDecoder == null) {
            return null;
        }
        final Rect rect = new Rect();
        rect.left = a.e;
        rect.top = a.f;
        rect.right = a.e + a.g;
        rect.bottom = a.f + a.h;
        return bitmapRegionDecoder.decodeRegion(rect, this.a);
    }
    
    @Override
    public void release() {
        this.b();
        if (this.b != null && this.c != null) {
            if (Build.VERSION.SDK_INT >= 18) {
                this.b.quitSafely();
            }
            else {
                this.b.quit();
            }
            this.c = null;
            this.b = null;
        }
    }
    
    private c a(final int n, final int n2, final float n3) {
        final int n4 = (n2 - n) / 2 + n;
        if (this.d.get(n4).a <= n3 && this.d.get(n4).b > n3) {
            return this.d.get(n4);
        }
        if (n >= n2) {
            return this.d.get(n);
        }
        if (n3 >= this.d.get(n4).b) {
            return this.a(n4 + 1, n2, n3);
        }
        if (n3 < this.d.get(n4).a) {
            return this.a(n, n4 - 1, n3);
        }
        return null;
    }
    
    private void a() {
        if (this.b == null) {
            (this.b = new HandlerThread("SuperVodThumbnailsWorkThread")).start();
            this.c = new Handler(this.b.getLooper());
        }
    }
    
    private void b() {
        if (this.c != null) {
            TXCLog.i("TXImageSprite", " remove all tasks!");
            this.c.removeCallbacksAndMessages((Object)null);
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.rtmp.a.b.this.d != null) {
                        com.tencent.rtmp.a.b.this.d.clear();
                    }
                    if (com.tencent.rtmp.a.b.this.e != null) {
                        for (final BitmapRegionDecoder bitmapRegionDecoder : com.tencent.rtmp.a.b.this.e.values()) {
                            if (bitmapRegionDecoder != null) {
                                bitmapRegionDecoder.recycle();
                            }
                        }
                        com.tencent.rtmp.a.b.this.e.clear();
                    }
                }
            });
        }
    }
    
    private InputStream a(final String s) throws IOException {
        final URLConnection openConnection = new URL(s).openConnection();
        openConnection.connect();
        openConnection.getInputStream();
        openConnection.setConnectTimeout(15000);
        openConnection.setReadTimeout(15000);
        return openConnection.getInputStream();
    }
    
    private static class a implements Runnable
    {
        private WeakReference<b> a;
        private String b;
        
        public a(final b b, final String b2) {
            this.a = new WeakReference<b>(b);
            this.b = b2;
        }
        
        private float a(final String s) {
            float n = 0.0f;
            final String[] split = s.split(":");
            String s2 = null;
            String s3 = null;
            String s4 = null;
            if (split.length == 3) {
                s2 = split[0];
                s3 = split[1];
                s4 = split[2];
            }
            else if (split.length == 2) {
                s3 = split[0];
                s4 = split[1];
            }
            else if (split.length == 1) {
                s4 = split[0];
            }
            if (s2 != null) {
                n += n * 3600.0f;
            }
            if (s3 != null) {
                n += Float.valueOf(s3) * 60.0f;
            }
            if (s4 != null) {
                n += Float.valueOf(s4);
            }
            return n;
        }
        
        @Override
        public void run() {
            final b b = this.a.get();
            BufferedReader bufferedReader = null;
            try {
                final InputStream a = b.a(this.b);
                if (a == null) {
                    return;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(a));
                final String line = bufferedReader.readLine();
                if (line == null || line.length() == 0 || !line.contains("WEBVTT")) {
                    TXCLog.e("TXImageSprite", "DownloadAndParseVTTFileTask : getVTT File Error!");
                    if (b != null) {
                        b.b();
                    }
                    return;
                }
                String line2;
                do {
                    line2 = bufferedReader.readLine();
                    if (line2 != null && line2.contains("-->")) {
                        final String[] split = line2.split(" --> ");
                        if (split.length != 2) {
                            continue;
                        }
                        final String line3 = bufferedReader.readLine();
                        final c c = new c();
                        c.a = this.a(split[0]);
                        c.b = this.a(split[1]);
                        final String c2 = line3;
                        c.c = c2;
                        final int index = c2.indexOf("#");
                        if (index != -1) {
                            c.d = c2.substring(0, index);
                        }
                        final int index2 = c2.indexOf("=");
                        if (index2 != -1 && index2 + 1 < c2.length()) {
                            final String[] split2 = c2.substring(index2 + 1, c2.length()).split(",");
                            if (split2.length == 4) {
                                c.e = Integer.valueOf(split2[0]);
                                c.f = Integer.valueOf(split2[1]);
                                c.g = Integer.valueOf(split2[2]);
                                c.h = Integer.valueOf(split2[3]);
                            }
                        }
                        if (b == null || b.d == null) {
                            continue;
                        }
                        b.d.add(c);
                    }
                } while (line2 != null);
            }
            catch (IOException ex) {
                TXCLog.e("TXImageSprite", "load image sprite failed.", ex);
            }
            finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    }
                    catch (IOException ex2) {}
                }
            }
        }
    }
    
    private static class b implements Runnable
    {
        private WeakReference<com.tencent.rtmp.a.b> a;
        private String b;
        private String c;
        
        public b(final com.tencent.rtmp.a.b b, final String b2, final String c) {
            this.a = new WeakReference<com.tencent.rtmp.a.b>(b);
            this.b = b2;
            this.c = c;
        }
        
        @Override
        public void run() {
            final com.tencent.rtmp.a.b b = this.a.get();
            if (this.a != null && b != null) {
                InputStream a = null;
                try {
                    a = b.a(this.c);
                    final int lastIndex = this.c.lastIndexOf("/");
                    if (lastIndex != -1 && lastIndex + 1 < this.c.length()) {
                        final String substring = this.c.substring(lastIndex + 1, this.c.length());
                        if (b.e != null) {
                            b.e.put(substring, BitmapRegionDecoder.newInstance(a, true));
                        }
                    }
                }
                catch (IOException ex) {
                    TXCLog.e("TXImageSprite", "load bitmap from network failed.", ex);
                }
                finally {
                    if (a != null) {
                        try {
                            a.close();
                        }
                        catch (IOException ex2) {}
                    }
                }
            }
        }
    }
}
