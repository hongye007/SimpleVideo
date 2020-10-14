package com.tencent.liteav.basic.util;

import java.util.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import android.text.*;
import java.io.*;
import java.util.concurrent.*;

public class b
{
    private static final long a;
    private final long b;
    private final int c;
    private RandomAccessFile d;
    private RandomAccessFile e;
    private long f;
    private float g;
    private float h;
    private long i;
    private long j;
    private float k;
    
    public b() {
        this.f = 0L;
        this.g = 0.0f;
        this.h = 0.0f;
        this.i = 0L;
        this.j = 0L;
        this.k = 0.0f;
        this.b = TXCTimeUtil.getClockTickInHz();
        this.c = Runtime.getRuntime().availableProcessors();
        final String format = String.format(Locale.ENGLISH, "/proc/%d/stat", Process.myPid());
        try {
            this.d = new RandomAccessFile(format, "r");
        }
        catch (IOException ex) {
            TXCLog.e("CpuUsageMeasurer", "open /proc/[PID]/stat failed. " + ex.getMessage());
        }
        try {
            this.e = new RandomAccessFile("/proc/stat", "r");
        }
        catch (IOException ex2) {}
    }
    
    public int[] a() {
        synchronized (this) {
            if (TXCTimeUtil.getTimeTick() - this.f >= com.tencent.liteav.basic.util.b.a) {
                this.b();
            }
            return new int[] { (int)(this.h * 10.0f), (int)(this.k * 10.0f) };
        }
    }
    
    private void b() {
        final String[] a = a(this.d);
        if (a == null || a.length < 52) {
            return;
        }
        final long n = (long)(1000.0f * (Long.parseLong(a[13]) + Long.parseLong(a[14]) + Long.parseLong(a[15]) + Long.parseLong(a[16])) / this.b);
        final String[] a2 = a(this.e);
        long i;
        long j;
        if (a2 == null || a2.length < 8) {
            j = (i = TXCTimeUtil.getTimeTick() * this.c);
        }
        else {
            final long n2 = Long.parseLong(a2[1]) + Long.parseLong(a2[2]) + Long.parseLong(a2[3]) + Long.parseLong(a2[4]) + Long.parseLong(a2[5]) + Long.parseLong(a2[6]) + Long.parseLong(a2[7]);
            final long n3 = Long.parseLong(a2[4]) + Long.parseLong(a2[5]);
            i = (long)(1000.0f * n2 / this.b);
            j = (long)(1000.0f * n3 / this.b);
        }
        final long n4 = i - this.i;
        this.h = 100.0f * (n - this.g) / n4;
        this.k = 100.0f * (n4 - (j - this.j)) / n4;
        this.g = (float)n;
        this.j = j;
        this.i = i;
        this.f = TXCTimeUtil.getTimeTick();
    }
    
    private static String[] a(final RandomAccessFile randomAccessFile) {
        if (randomAccessFile == null) {
            return null;
        }
        String line = null;
        try {
            randomAccessFile.seek(0L);
            line = randomAccessFile.readLine();
        }
        catch (IOException ex) {
            TXCLog.e("CpuUsageMeasurer", "read line failed. " + ex.getMessage());
        }
        if (TextUtils.isEmpty((CharSequence)line)) {
            return null;
        }
        return line.split("\\s+");
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        com.tencent.liteav.basic.util.c.a(this.d);
        com.tencent.liteav.basic.util.c.a(this.e);
        TXCLog.i("CpuUsageMeasurer", "measurer is released");
    }
    
    static {
        a = TimeUnit.SECONDS.toMillis(2L);
    }
}
