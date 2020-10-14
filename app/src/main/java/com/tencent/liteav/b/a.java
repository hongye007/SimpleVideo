package com.tencent.liteav.b;

import android.media.*;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import com.tencent.liteav.videoediter.audio.*;
import java.nio.*;

public class a
{
    private ArrayList<Float> a;
    private int b;
    private int c;
    private ArrayList<MediaFormat> d;
    private ArrayList<e> e;
    private ArrayList<TXSkpResample> f;
    private ArrayList<short[]> g;
    private volatile boolean h;
    
    public a() {
        this.a = new ArrayList<Float>();
        this.d = new ArrayList<MediaFormat>();
        this.e = new ArrayList<e>();
        this.f = new ArrayList<TXSkpResample>();
        this.g = new ArrayList<short[]>();
        this.h = false;
    }
    
    public void a(final int b, final int c) {
        this.b = b;
        this.c = c;
    }
    
    public void a(final MediaFormat mediaFormat, final int n) {
        this.d.add(n, mediaFormat);
        if (!this.h) {
            this.a.add(n, 1.0f);
        }
        this.g.add(n, null);
    }
    
    public void a(final List<Float> list) {
        if (list == null) {
            TXCLog.i("CombineAudioMixer", "setVideoVolumes volumes is null !");
            return;
        }
        this.h = true;
        if (this.a.size() == 0) {
            for (Float n : list) {
                if (n < 0.0f) {
                    n = 0.0f;
                }
                if (n > 1.0f) {
                    n = 1.0f;
                }
                this.a.add(n);
            }
        }
        else {
            if (this.a.size() != list.size()) {
                TXCLog.i("CombineAudioMixer", "setVideoVolumes size not match!");
                return;
            }
            for (int i = 0; i < list.size(); ++i) {
                Float n2 = list.get(i);
                if (n2 < 0.0f) {
                    n2 = 0.0f;
                }
                if (n2 > 1.0f) {
                    n2 = 1.0f;
                }
                this.a.set(i, n2);
            }
        }
    }
    
    private boolean c() {
        if (this.d == null || this.d.size() == 0) {
            return false;
        }
        for (int i = 0; i < this.d.size(); ++i) {
            if (this.d.get(i) != null) {
                return true;
            }
        }
        return false;
    }
    
    public void a() {
        if (!this.c()) {
            TXCLog.e("CombineAudioMixer", "not have audio format :");
            return;
        }
        if (this.b == 0) {
            TXCLog.e("CombineAudioMixer", "Target Audio SampleRate is not set!!!");
            return;
        }
        for (int i = 0; i < this.d.size(); ++i) {
            final MediaFormat mediaFormat = this.d.get(i);
            if (mediaFormat != null) {
                if (mediaFormat.containsKey("channel-count")) {
                    final int integer = mediaFormat.getInteger("channel-count");
                    if (integer != 1) {
                        final e e = new e();
                        e.a(integer, 1);
                        this.e.add(i, e);
                    }
                    else {
                        this.e.add(i, null);
                    }
                }
                if (mediaFormat.containsKey("sample-rate")) {
                    final int integer2 = mediaFormat.getInteger("sample-rate");
                    if (integer2 != this.b) {
                        final TXSkpResample txSkpResample = new TXSkpResample();
                        txSkpResample.init(integer2, this.b);
                        this.f.add(i, txSkpResample);
                    }
                    else {
                        this.f.add(i, null);
                    }
                }
            }
            else {
                this.f.add(i, null);
            }
        }
    }
    
    public void b(final int n, final int n2) {
        if (n != this.b) {
            TXSkpResample txSkpResample = this.f.get(n2);
            if (txSkpResample == null) {
                final TXSkpResample txSkpResample2 = new TXSkpResample();
                this.f.set(n2, txSkpResample2);
                txSkpResample = txSkpResample2;
            }
            txSkpResample.init(n, this.b);
        }
    }
    
    public void b() {
        this.g.clear();
    }
    
    public CaptureAndEnc.e a(final ArrayList<CaptureAndEnc.e> list) {
        int n = 1;
        int length = -1;
        final ArrayList<short[]> list2 = new ArrayList<short[]>();
        for (int i = 0; i < list.size(); ++i) {
            final short[] array = this.g.get(i);
            final CaptureAndEnc.e e = list.get(i);
            if (e != null) {
                short[] array2 = com.tencent.liteav.videoediter.audio.b.a(e.b(), e.g());
                final e e2 = this.e.get(i);
                if (e2 != null) {
                    array2 = e2.a(array2);
                }
                final TXSkpResample txSkpResample = this.f.get(i);
                if (txSkpResample != null) {
                    array2 = txSkpResample.doResample(array2);
                }
                if (array2 != null) {
                    list2.add(i, this.a(array, array2));
                }
                else {
                    list2.add(i, array);
                }
            }
            else {
                list2.add(i, array);
            }
            final short[] array3 = list2.get(i);
            if (n != 0) {
                if (length == -1) {
                    if (array3 == null) {
                        length = 0;
                    }
                    else {
                        length = array3.length;
                    }
                }
                else if (((array3 == null) ? 0 : array3.length) != length) {
                    n = 0;
                }
            }
        }
        for (int j = 0; j < list2.size(); ++j) {
            final short[] array4 = list2.get(j);
            if (array4 == null || array4.length == 0) {
                TXCLog.i("CombineAudioMixer", "one row reach end " + j);
                return null;
            }
        }
        if (n != 0) {
            final short[] b = this.b(list2);
            for (int k = 0; k < list2.size(); ++k) {
                this.g.set(k, null);
            }
            final ByteBuffer a = com.tencent.liteav.videoediter.audio.b.a(b);
            CaptureAndEnc.e e3 = list.get(this.c);
            for (int n2 = 0; n2 < list.size() && e3 == null; e3 = list.get(n2), ++n2) {
                TXCLog.d("CombineAudioMixer", "AUDIO PTS " + n2);
            }
            e3.a(a);
            e3.d(b.length * 2);
            return e3;
        }
        final short[] b2 = this.b(list2);
        for (int l = 0; l < list2.size(); ++l) {
            final short[] array5 = list2.get(l);
            if (array5 != null) {
                this.g.set(l, this.a(array5, b2.length, array5.length - b2.length));
            }
            else {
                this.g.set(l, null);
            }
        }
        CaptureAndEnc.e e4 = list.get(this.c);
        for (int n3 = 0; n3 < list.size() && e4 == null; e4 = list.get(n3), ++n3) {
            TXCLog.d("CombineAudioMixer", "AUDIO PTS ss " + n3);
        }
        e4.a(com.tencent.liteav.videoediter.audio.b.a(b2));
        e4.d(b2.length * 2);
        return e4;
    }
    
    private short[] a(final short[] array, final short[] array2) {
        if (array == null || array.length == 0) {
            return array2;
        }
        final short[] array3 = new short[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    private short[] a(final short[] array, final int n, final int n2) {
        final short[] array2 = new short[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    private short[] b(final ArrayList<short[]> list) {
        int length = Integer.MAX_VALUE;
        short[] array = null;
        for (int i = 0; i < list.size(); ++i) {
            final short[] array2 = list.get(i);
            if (array2 != null && array2.length < length) {
                length = array2.length;
                array = array2;
            }
        }
        for (int j = 0; j < length; ++j) {
            float n = 0.0f;
            for (int k = 0; k < list.size(); ++k) {
                final short[] array3 = list.get(k);
                if (array3 != null) {
                    n += array3[j] * this.a.get(k);
                }
            }
            final int n2 = (int)n;
            short n3;
            if (n2 > 32767) {
                n3 = 32767;
            }
            else if (n2 < -32768) {
                n3 = -32768;
            }
            else {
                n3 = (short)n2;
            }
            array[j] = n3;
        }
        return array;
    }
}
