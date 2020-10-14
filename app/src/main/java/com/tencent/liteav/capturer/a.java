package com.tencent.liteav.capturer;

import android.hardware.*;
import com.tencent.liteav.basic.log.*;
import android.graphics.*;
import android.os.*;
import java.io.*;
import com.tencent.liteav.basic.util.*;
import java.util.*;

public class a implements Camera.AutoFocusCallback, Camera.ErrorCallback, Camera.PreviewCallback
{
    private Matrix a;
    private int b;
    private Camera c;
    private boolean d;
    private b e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private SurfaceTexture l;
    private boolean m;
    private boolean n;
    private boolean o;
    private boolean p;
    private int q;
    private int r;
    private boolean s;
    private boolean t;
    
    public a() {
        this.a = new Matrix();
        this.b = 0;
        this.d = true;
        this.f = 15;
        this.g = 1;
        this.p = false;
        this.s = false;
        this.t = false;
    }
    
    public void a(final b e) {
        this.e = e;
    }
    
    public void a(final SurfaceTexture l) {
        this.l = l;
    }
    
    public Camera.Parameters a() {
        if (this.c == null) {
            return null;
        }
        try {
            return this.c.getParameters();
        }
        catch (Exception ex) {
            TXCLog.e("TXCCameraCapturer", "getCameraParameters error ", ex);
            return null;
        }
    }
    
    public boolean b() {
        if (this.c != null) {
            final Camera.Parameters a = this.a();
            if (a != null && a.getMaxZoom() > 0 && a.isZoomSupported()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean c() {
        if (this.c == null) {
            return false;
        }
        final Camera.Parameters a = this.a();
        if (a != null) {
            final List supportedFlashModes = a.getSupportedFlashModes();
            return supportedFlashModes != null && supportedFlashModes.contains("torch");
        }
        return false;
    }
    
    public boolean d() {
        return this.m;
    }
    
    public boolean e() {
        if (this.c != null) {
            final Camera.Parameters a = this.a();
            return a != null && a.getMaxNumDetectedFaces() > 0;
        }
        return false;
    }
    
    public boolean a(final boolean o) {
        this.o = o;
        if (this.c == null) {
            return false;
        }
        boolean b = true;
        final Camera.Parameters a = this.a();
        if (a == null) {
            return false;
        }
        final List supportedFlashModes = a.getSupportedFlashModes();
        if (o) {
            if (supportedFlashModes != null && supportedFlashModes.contains("torch")) {
                TXCLog.i("TXCCameraCapturer", "set FLASH_MODE_TORCH");
                a.setFlashMode("torch");
            }
            else {
                b = false;
            }
        }
        else if (supportedFlashModes != null && supportedFlashModes.contains("off")) {
            TXCLog.i("TXCCameraCapturer", "set FLASH_MODE_OFF");
            a.setFlashMode("off");
        }
        else {
            b = false;
        }
        try {
            this.c.setParameters(a);
        }
        catch (Exception ex) {
            b = false;
            TXCLog.e("TXCCameraCapturer", "setParameters failed.", ex);
        }
        return b;
    }
    
    public void a(final a a) {
        if (a != a.a) {
            this.q = a.a();
            this.r = a.b();
        }
        TXCLog.i("TXCCameraCapturer", "set resolution " + a);
    }
    
    public void a(final int f) {
        this.f = f;
    }
    
    public void b(final boolean t) {
        this.t = t;
        TXCLog.i("TXCCameraCapturer", "set performance mode to " + t);
    }
    
    public void a(final float n, final float n2) {
        if (!this.s) {
            return;
        }
        Camera.Parameters parameters;
        try {
            this.c.cancelAutoFocus();
            parameters = this.c.getParameters();
        }
        catch (Exception ex) {
            return;
        }
        if (this.m) {
            final ArrayList<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
            focusAreas.add(new Camera.Area(this.a(n, n2, 2.0f), 1000));
            parameters.setFocusAreas((List)focusAreas);
        }
        if (this.n) {
            final ArrayList<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
            meteringAreas.add(new Camera.Area(this.a(n, n2, 3.0f), 1000));
            parameters.setMeteringAreas((List)meteringAreas);
        }
        try {
            this.c.setParameters(parameters);
            this.c.autoFocus((Camera.AutoFocusCallback)this);
        }
        catch (Exception ex2) {}
    }
    
    public void c(final boolean s) {
        this.s = s;
    }
    
    private Rect a(float n, float n2, final float n3) {
        final float n4 = 200.0f * n3;
        float n5 = n;
        float n6 = n2;
        if (this.d) {
            n5 = 1.0f - n5;
        }
        for (int n7 = this.j / 90, i = 0; i < n7; ++i) {
            final float n8 = n5 - 0.5f;
            final float n9 = -(n6 - 0.5f);
            final float n10 = n8;
            final float n11 = -n9;
            final float n12 = -n10;
            n5 = n11 + 0.5f;
            n6 = n12 + 0.5f;
        }
        n = n5;
        n2 = n6;
        int n13 = (int)(n * 2000.0f - 1000.0f);
        int n14 = (int)(n2 * 2000.0f - 1000.0f);
        if (n13 < -1000) {
            n13 = -1000;
        }
        if (n14 < -1000) {
            n14 = -1000;
        }
        int n15 = n13 + (int)n4;
        int n16 = n14 + (int)n4;
        if (n15 > 1000) {
            n15 = 1000;
        }
        if (n16 > 1000) {
            n16 = 1000;
        }
        return new Rect(n13, n14, n15, n16);
    }
    
    public int f() {
        int maxZoom = 0;
        final Camera.Parameters a = this.a();
        if (a != null && a.getMaxZoom() > 0 && a.isZoomSupported()) {
            maxZoom = a.getMaxZoom();
        }
        return maxZoom;
    }
    
    public boolean b(final int zoom) {
        boolean b = false;
        if (this.c != null) {
            final Camera.Parameters a = this.a();
            if (a != null && a.getMaxZoom() > 0 && a.isZoomSupported()) {
                if (zoom >= 0 && zoom <= a.getMaxZoom()) {
                    try {
                        a.setZoom(zoom);
                        this.c.setParameters(a);
                        b = true;
                    }
                    catch (Exception ex) {
                        b = false;
                        TXCLog.e("TXCCameraCapturer", "set zoom failed.", ex);
                    }
                }
                else {
                    TXCLog.e("TXCCameraCapturer", "invalid zoom value : " + zoom + ", while max zoom is " + a.getMaxZoom());
                }
            }
            else {
                TXCLog.e("TXCCameraCapturer", "camera not support zoom!");
            }
        }
        return b;
    }
    
    public void a(final boolean p3, final int q, final int r) {
        this.p = p3;
        this.q = q;
        this.r = r;
        TXCLog.i("TXCCameraCapturer", "setCaptureBuffer %b, width: %d, height: %d", p3, q, r);
    }
    
    public void a(float n) {
        if (this.c != null) {
            if (n > 1.0f) {
                n = 1.0f;
            }
            if (n < -1.0f) {
                n = -1.0f;
            }
            final Camera.Parameters a = this.a();
            if (a == null) {
                TXCLog.e("TXCCameraCapturer", "camera setExposureCompensation camera parameter is null");
                return;
            }
            final int minExposureCompensation = a.getMinExposureCompensation();
            final int maxExposureCompensation = a.getMaxExposureCompensation();
            if (minExposureCompensation != 0 && maxExposureCompensation != 0) {
                final int d = com.tencent.liteav.basic.d.b.a().d();
                final float n2 = n * maxExposureCompensation;
                if (d != 0 && d <= maxExposureCompensation && d >= minExposureCompensation) {
                    TXCLog.i("TXCCameraCapturer", "camera setExposureCompensation: " + d);
                    a.setExposureCompensation(d);
                }
                else if (n2 <= maxExposureCompensation && n2 >= minExposureCompensation) {
                    TXCLog.i("TXCCameraCapturer", "camera setExposureCompensation: " + n2);
                    a.setExposureCompensation((int)n2);
                }
            }
            else {
                TXCLog.e("TXCCameraCapturer", "camera not support setExposureCompensation!");
            }
            try {
                this.c.setParameters(a);
            }
            catch (Exception ex) {
                TXCLog.e("TXCCameraCapturer", "setExposureCompensation failed.", ex);
            }
        }
    }
    
    public void c(final int g) {
        TXCLog.w("TXCCameraCapturer", "vsize setHomeOrientation " + g);
        this.g = g;
        this.j = (this.k - 90 + this.g * 90 + 360) % 360;
    }
    
    public int d(final boolean d) {
        try {
            TXCLog.i("TXCCameraCapturer", "trtc_capture: start capture");
            if (this.l == null) {
                return -2;
            }
            if (this.c != null) {
                this.g();
            }
            int n = -1;
            int n2 = -1;
            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
                Camera.getCameraInfo(i, cameraInfo);
                TXCLog.i("TXCCameraCapturer", "camera index " + i + ", facing = " + cameraInfo.facing);
                if (cameraInfo.facing == 1 && n == -1) {
                    n = i;
                }
                if (cameraInfo.facing == 0 && n2 == -1) {
                    n2 = i;
                }
            }
            TXCLog.i("TXCCameraCapturer", "camera front, id = " + n);
            TXCLog.i("TXCCameraCapturer", "camera back , id = " + n2);
            if (n == -1 && n2 != -1) {
                n = n2;
            }
            if (n2 == -1 && n != -1) {
                n2 = n;
            }
            this.d = d;
            if (this.d) {
                this.c = Camera.open(n);
            }
            else {
                this.c = Camera.open(n2);
            }
            final Camera.Parameters parameters = this.c.getParameters();
            final List supportedFocusModes = parameters.getSupportedFocusModes();
            if (this.s && supportedFocusModes != null && supportedFocusModes.contains("auto")) {
                TXCLog.i("TXCCameraCapturer", "support FOCUS_MODE_AUTO");
                parameters.setFocusMode("auto");
            }
            else if (supportedFocusModes != null && supportedFocusModes.contains("continuous-video")) {
                TXCLog.i("TXCCameraCapturer", "support FOCUS_MODE_CONTINUOUS_VIDEO");
                parameters.setFocusMode("continuous-video");
            }
            if (Build.VERSION.SDK_INT >= 14) {
                if (parameters.getMaxNumFocusAreas() > 0) {
                    this.m = true;
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    this.n = true;
                }
            }
            if (this.p) {
                parameters.setPreviewFormat(17);
                this.c.setPreviewCallback((Camera.PreviewCallback)this);
            }
            final d b = b(this.t, this.q, this.r);
            final d a = a(parameters, Math.max(b.a, b.b), Math.min(b.a, b.b));
            this.h = a.a;
            this.i = a.b;
            parameters.setPreviewSize(this.h, this.i);
            final int[] e = this.e(this.f);
            if (e != null) {
                parameters.setPreviewFpsRange(e[0], e[1]);
            }
            else {
                parameters.setPreviewFrameRate(this.d(this.f));
            }
            this.k = this.f(this.d ? n : n2);
            this.j = (this.k - 90 + this.g * 90 + 360) % 360;
            this.c.setDisplayOrientation(0);
            TXCLog.i("TXCCameraCapturer", "vsize camera orientation " + this.k + ", preview " + this.j + ", home orientation " + this.g);
            this.c.setPreviewTexture(this.l);
            this.c.setParameters(parameters);
            this.c.setErrorCallback((Camera.ErrorCallback)this);
            this.c.startPreview();
        }
        catch (IOException ex) {
            TXCLog.e("TXCCameraCapturer", "open camera failed." + ex.getMessage());
            return -1;
        }
        catch (Exception ex2) {
            TXCLog.e("TXCCameraCapturer", "open camera failed." + ex2.getMessage());
            return -1;
        }
        return 0;
    }
    
    private static d b(final boolean b, int n, int n2) {
        if (b) {
            return new d(n, n2);
        }
        final d[] array = { new d(1080, 1920) };
        final float n3 = (float)Math.min(n, n2);
        final float n4 = (float)Math.max(n, n2);
        for (final d d : array) {
            if (n3 <= d.a && n4 <= d.b) {
                final float min = Math.min(d.a / n3, d.b / n4);
                n *= (int)min;
                n2 *= (int)min;
                break;
            }
        }
        return new d(n, n2);
    }
    
    public void onPreviewFrame(final byte[] array, final Camera camera) {
        final b e = this.e;
        if (e != null) {
            e.a(array);
        }
    }
    
    public void g() {
        if (this.c != null) {
            try {
                this.c.setErrorCallback((Camera.ErrorCallback)null);
                this.c.setPreviewCallback((Camera.PreviewCallback)null);
                this.c.stopPreview();
                this.c.release();
            }
            catch (Exception ex) {
                TXCLog.e("TXCCameraCapturer", "stop capture failed.", ex);
            }
            finally {
                this.c = null;
                this.l = null;
            }
        }
    }
    
    public int h() {
        return this.j;
    }
    
    public boolean i() {
        return this.d;
    }
    
    public int j() {
        return this.h;
    }
    
    public int k() {
        return this.i;
    }
    
    public Camera l() {
        return this.c;
    }
    
    private static d a(final Camera.Parameters parameters, final int n, final int n2) {
        TXCLog.d("TXCCameraCapturer", "camera preview wanted: %d x %d", n, n2);
        final List supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        final float n3 = 1.0f * n / n2;
        int n4 = Integer.MAX_VALUE;
        final ArrayList<Camera.Size> list = new ArrayList<Camera.Size>();
        for (final Camera.Size size : supportedPreviewSizes) {
            TXCLog.d("TXCCameraCapturer", "camera support preview size: %dx%d", size.width, size.height);
            int round;
            if (size.width < 640 || size.height < 480) {
                round = Integer.MAX_VALUE;
            }
            else {
                round = Math.round(10.0f * Math.abs(1.0f * size.width / size.height - n3));
            }
            if (round < n4) {
                n4 = round;
                list.clear();
                list.add(size);
            }
            else {
                if (round != n4) {
                    continue;
                }
                list.add(size);
            }
        }
        Collections.sort((List<Object>)list, (Comparator<? super Object>)new Comparator<Camera.Size>() {
            public int a(final Camera.Size size, final Camera.Size size2) {
                return size2.width * size2.height - size.width * size.height;
            }
        });
        Camera.Size size2 = list.get(0);
        final float n5 = (float)(n * n2);
        float abs = 2.14748365E9f;
        for (final Camera.Size size3 : list) {
            TXCLog.i("TXCCameraCapturer", "size in same buck: %dx%d", size3.width, size3.height);
            final int n6 = size3.width * size3.height;
            if (n6 / n5 >= 0.9 && Math.abs(n6 - n5) < abs) {
                size2 = size3;
                abs = Math.abs(n6 - n5);
            }
        }
        TXCLog.i("TXCCameraCapturer", "best match preview size: %d x %d", size2.width, size2.height);
        return new d(size2.width, size2.height);
    }
    
    public void onAutoFocus(final boolean b, final Camera camera) {
        if (b) {
            TXCLog.i("TXCCameraCapturer", "AUTO focus success");
        }
        else {
            TXCLog.i("TXCCameraCapturer", "AUTO focus failed");
        }
    }
    
    public void onError(final int n, final Camera camera) {
        TXCLog.w("TXCCameraCapturer", "camera catch error " + n);
        if ((n == 1 || n == 2 || n == 100) && this.e != null) {
            this.e.m();
        }
    }
    
    private int d(final int n) {
        final int n2 = 1;
        final Camera.Parameters a = this.a();
        if (a == null) {
            return n2;
        }
        final List supportedPreviewFrameRates = a.getSupportedPreviewFrameRates();
        if (supportedPreviewFrameRates == null) {
            TXCLog.e("TXCCameraCapturer", "getSupportedFPS error");
            return n2;
        }
        int intValue = supportedPreviewFrameRates.get(0);
        for (int i = 0; i < supportedPreviewFrameRates.size(); ++i) {
            final int intValue2 = supportedPreviewFrameRates.get(i);
            if (Math.abs(intValue2 - n) - Math.abs(intValue - n) < 0) {
                intValue = intValue2;
            }
        }
        TXCLog.i("TXCCameraCapturer", "choose fps=" + intValue);
        return intValue;
    }
    
    private int[] e(int n) {
        n *= 1000;
        String s = "camera supported preview fps range: wantFPS = " + n + "\n";
        final Camera.Parameters a = this.a();
        if (a == null) {
            return null;
        }
        final List supportedPreviewFpsRange = a.getSupportedPreviewFpsRange();
        if (supportedPreviewFpsRange != null && supportedPreviewFpsRange.size() > 0) {
            int[] array = supportedPreviewFpsRange.get(0);
            Collections.sort((List<Object>)supportedPreviewFpsRange, (Comparator<? super Object>)new Comparator<int[]>() {
                public int a(final int[] array, final int[] array2) {
                    return array[1] - array2[1];
                }
            });
            for (final int[] array2 : supportedPreviewFpsRange) {
                s = s + "camera supported preview fps range: " + array2[0] + " - " + array2[1] + "\n";
            }
            for (final int[] array3 : supportedPreviewFpsRange) {
                if (array3[0] <= n && n <= array3[1]) {
                    array = array3;
                    break;
                }
            }
            TXCLog.i("TXCCameraCapturer", s + "choose preview fps range: " + array[0] + " - " + array[1]);
            return array;
        }
        return null;
    }
    
    private int f(final int n) {
        final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(n, cameraInfo);
        TXCLog.i("TXCCameraCapturer", "vsize camera orientation " + cameraInfo.orientation + ", front " + (cameraInfo.facing == 1));
        int orientation = cameraInfo.orientation;
        if (orientation == 0 || orientation == 180) {
            orientation += 90;
        }
        int n2;
        if (cameraInfo.facing == 1) {
            n2 = (360 - orientation) % 360;
        }
        else {
            n2 = (orientation + 360) % 360;
        }
        return n2;
    }
    
    public enum a
    {
        a(-1, -1), 
        b(180, 320), 
        c(270, 480), 
        d(320, 480), 
        e(360, 640), 
        f(540, 960), 
        g(720, 1280), 
        h(1080, 1920), 
        i(1080, 1920);
        
        private final int mWidth;
        private final int mHeight;
        
        private a(final int mWidth, final int mHeight) {
            this.mWidth = mWidth;
            this.mHeight = mHeight;
        }
        
        private int a() {
            return this.mWidth;
        }
        
        private int b() {
            return this.mHeight;
        }
    }
}
