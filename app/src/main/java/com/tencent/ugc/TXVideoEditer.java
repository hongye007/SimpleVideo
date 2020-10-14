package com.tencent.ugc;

import android.content.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.editer.*;

import android.graphics.*;

import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.datareport.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.license.*;

import java.util.*;
import android.text.*;

public class TXVideoEditer
{
    private static final String TAG = "TXVideoEditer";
    private Context mContext;
    private ac mVideoEditerPreview;
    private ab mVideoEditGenerate;
    private ag mVideoProcessGenerate;
    private ah mVideoRecordGenerate;
    private x mVideoAverageThumbnailGenerate;
    private aj mVideoTimelistThumbnailGenerate;
    private k mVideoSourceConfig;
    private i mVideoOutputConfig;
    private j mVideoPreProcessConfig;
    private b mBgmConfig;
    private d mMotionFilterConfig;
    private volatile boolean mIsPreviewStart;
    private TXVideoProcessListener mTXVideoProcessListener;
    private TXThumbnailListener mTXThumbnailListener;
    private TXVideoPreviewListener mTXVideoPreviewListener;
    private TXVideoGenerateListener mTXVideoGenerateListener;
    private TXVideoCustomProcessListener mTXVideoCustomProcessListener;
    private boolean mSmartLicenseSupport;
    private List<Bitmap> mBitmapList;
    private com.tencent.liteav.i.b.b mTXCVideoCustomProcessListener;
    private com.tencent.liteav.i.b.e mTXCVideoProcessListener;
    private com.tencent.liteav.i.b.a mTXCThumbnailListener;
    private com.tencent.liteav.i.b.d mTXCVideoPreviewListener;
    private com.tencent.liteav.i.b.c mTXCVideoGenerateListener;
    
    public TXVideoEditer(final Context context) {
        this.mSmartLicenseSupport = true;
        this.mTXCVideoCustomProcessListener = new com.tencent.liteav.i.b.b() {
            @Override
            public int a(final int n, final int n2, final int n3, final long n4) {
                if (TXVideoEditer.this.mTXVideoCustomProcessListener != null) {
                    return TXVideoEditer.this.mTXVideoCustomProcessListener.onTextureCustomProcess(n, n2, n3, n4);
                }
                return 0;
            }
            
            @Override
            public void a() {
                if (TXVideoEditer.this.mTXVideoCustomProcessListener != null) {
                    TXVideoEditer.this.mTXVideoCustomProcessListener.onTextureDestroyed();
                }
            }
        };
        this.mTXCVideoProcessListener = new com.tencent.liteav.i.b.e() {
            @Override
            public void a(final float n) {
                if (TXVideoEditer.this.mTXVideoProcessListener != null) {
                    TXVideoEditer.this.mTXVideoProcessListener.onProcessProgress(n);
                }
            }
            
            @Override
            public void a(final com.tencent.liteav.i.a.c c) {
                if (TXVideoEditer.this.mTXVideoProcessListener != null) {
                    if (c.a != 0) {
                        TXVideoEditer.this.cancel();
                    }
                    final TXVideoEditConstants.TXGenerateResult txGenerateResult = new TXVideoEditConstants.TXGenerateResult();
                    txGenerateResult.retCode = c.a;
                    txGenerateResult.descMsg = c.b;
                    TXVideoEditer.this.mTXVideoProcessListener.onProcessComplete(txGenerateResult);
                }
            }
        };
        this.mTXCThumbnailListener = new com.tencent.liteav.i.b.a() {
            @Override
            public void a(final int n, final long n2, final Bitmap bitmap) {
                if (TXVideoEditer.this.mTXThumbnailListener != null) {
                    TXVideoEditer.this.mTXThumbnailListener.onThumbnail(n, n2, bitmap);
                }
            }
        };
        this.mTXCVideoPreviewListener = new com.tencent.liteav.i.b.d() {
            @Override
            public void a(final int n) {
                if (TXVideoEditer.this.mTXVideoPreviewListener != null) {
                    TXVideoEditer.this.mTXVideoPreviewListener.onPreviewProgress(n);
                }
            }
            
            @Override
            public void a() {
                if (TXVideoEditer.this.mTXVideoPreviewListener != null) {
                    TXVideoEditer.this.mTXVideoPreviewListener.onPreviewFinished();
                }
            }
            
            @Override
            public void a(final com.tencent.liteav.i.a.f f) {
                TXCLog.e("TXVideoEditer", "onPreviewError -> error code = " + f.a + " msg = " + f.b);
                TXVideoEditer.this.stopPlay();
                final TXVideoPreviewListener access$300 = TXVideoEditer.this.mTXVideoPreviewListener;
                if (access$300 != null) {
                    final TXVideoEditConstants.TXPreviewError txPreviewError = new TXVideoEditConstants.TXPreviewError();
                    txPreviewError.errorCode = f.a;
                    txPreviewError.errorMsg = f.b;
                    if (access$300 instanceof TXVideoPreviewListenerEx) {
                        ((TXVideoPreviewListenerEx)access$300).onPreviewError(txPreviewError);
                    }
                    else {
                        access$300.onPreviewFinished();
                    }
                }
            }
        };
        this.mTXCVideoGenerateListener = new com.tencent.liteav.i.b.c() {
            @Override
            public void a(final float n) {
                if (TXVideoEditer.this.mTXVideoGenerateListener != null) {
                    TXVideoEditer.this.mTXVideoGenerateListener.onGenerateProgress(n);
                }
            }
            
            @Override
            public void a(final com.tencent.liteav.i.a.c c) {
                final TXVideoEditConstants.TXGenerateResult txGenerateResult = new TXVideoEditConstants.TXGenerateResult();
                txGenerateResult.retCode = c.a;
                txGenerateResult.descMsg = c.b;
                if (txGenerateResult.retCode == 0) {
                    final int p = i.a().p();
                    final int q = i.a().q();
                    TXCDRApi.txReportDAU(TXVideoEditer.this.mContext, com.tencent.liteav.basic.datareport.a.aY, p, "");
                    TXCDRApi.txReportDAU(TXVideoEditer.this.mContext, com.tencent.liteav.basic.datareport.a.aZ, q, "");
                }
                if (TXVideoEditer.this.mTXVideoGenerateListener != null) {
                    TXVideoEditer.this.mTXVideoGenerateListener.onGenerateComplete(txGenerateResult);
                }
            }
        };
        TXCCommonUtil.setAppContext(this.mContext = context.getApplicationContext());
        TXCLog.init();
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aw);
        TXCDRApi.initCrashReport(this.mContext);
        LicenceCheck.a().a((f)null, this.mContext);
        this.mVideoEditerPreview = new ac(this.mContext);
        this.mVideoEditGenerate = new ab(this.mContext);
        this.mVideoProcessGenerate = new ag(this.mContext, "Pretreatment");
        this.mVideoRecordGenerate = new ah(this.mContext);
        this.mVideoAverageThumbnailGenerate = new x(this.mContext);
        this.mVideoTimelistThumbnailGenerate = new aj(this.mContext);
        this.mVideoOutputConfig = i.a();
        this.mVideoSourceConfig = k.a();
        this.mVideoPreProcessConfig = j.a();
        this.mBgmConfig = b.a();
        this.mMotionFilterConfig = d.a();
        this.mBitmapList = new ArrayList<Bitmap>();
    }
    
    public int setVideoPath(final String a) {
        TXCLog.i("TXVideoEditer", "=== setVideoPath === videoSource: " + a);
        this.mVideoSourceConfig.a = a;
        return this.mVideoSourceConfig.e();
    }
    
    private boolean isSmartLicense() {
        LicenceCheck.a().a((f)null, this.mContext);
        if (LicenceCheck.a().b() == -1) {
            this.mSmartLicenseSupport = false;
        }
        else if (LicenceCheck.a().b() == 2) {
            return true;
        }
        return false;
    }
    
    public void setCustomVideoProcessListener(final TXVideoCustomProcessListener mtxVideoCustomProcessListener) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setCustomVideoProcessListener is not supported in UGC_Smart license");
            return;
        }
        this.mTXVideoCustomProcessListener = mtxVideoCustomProcessListener;
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a(this.mTXCVideoCustomProcessListener);
        }
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.a(this.mTXCVideoCustomProcessListener);
        }
    }
    
    public void setSpecialRatio(final float n) {
        TXCLog.i("TXVideoEditer", " setSpecialRatio -> " + n);
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setSpecialRatio is not supported in UGC_Smart license");
            return;
        }
        CaptureAndEnc.d d = this.mVideoPreProcessConfig.d();
        if (d == null) {
            d = new CaptureAndEnc.d();
        }
        d.a(n);
        d.b(0.0f);
        this.mVideoPreProcessConfig.a(d);
    }
    
    public void setFilter(final Bitmap bitmap) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setFilter is not supported in UGC_Smart license");
            return;
        }
        final CaptureAndEnc.d d = this.mVideoPreProcessConfig.d();
        float b = 0.5f;
        float c = 0.0f;
        if (d != null) {
            b = d.b();
            c = d.c();
        }
        this.setFilter(bitmap, b, null, c, 1.0f);
        this.refreshOneFrame();
    }
    
    public void setFilter(final Bitmap bitmap, final float n, final Bitmap bitmap2, final float n2, final float n3) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setFilter is not supported in UGC_Smart license");
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aP);
        this.mVideoPreProcessConfig.a(new CaptureAndEnc.d(n3, bitmap, n, bitmap2, n2));
        this.refreshOneFrame();
    }
    
    public void setBeautyFilter(final int n, final int n2) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBeautyFilter is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setBeautyFilter ==== beautyLevel: " + n + ",whiteningLevel:" + n2);
        this.mVideoPreProcessConfig.a(new c(n, n2));
    }
    
    public int setPictureList(final List<Bitmap> list, int n) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBeautyFilter is not supported in UGC_Smart license");
            return -1;
        }
        if (list == null || list.size() <= 0) {
            TXCLog.e("TXVideoEditer", "setPictureList, bitmapList is empty!");
            return -1;
        }
        if (n <= 15) {
            TXCLog.i("TXVideoEditer", "setPictureList, fps <= 15, set 15");
            n = 15;
        }
        if (n >= 30) {
            TXCLog.i("TXVideoEditer", "setPictureList, fps >= 30, set 30");
            n = 30;
        }
        this.initConfig();
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aW);
        this.mBitmapList.clear();
        for (final Bitmap bitmap : list) {
            if (bitmap == null) {
                continue;
            }
            this.mBitmapList.add(bitmap.copy(bitmap.getConfig(), false));
        }
        this.mVideoSourceConfig.a(this.mBitmapList, n);
        this.mVideoEditerPreview.a(this.mBitmapList, n);
        return 0;
    }
    
    public long setPictureTransition(final int n) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setPictureTransition is not supported in UGC_Smart license");
            return 0L;
        }
        final long a = this.mVideoEditerPreview.a(n);
        i.a().l = a * 1000L;
        return a;
    }
    
    public int setBGM(final String s) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBGM is not supported in UGC_Smart license");
            return 0;
        }
        TXCLog.i("TXVideoEditer", "==== setBGM ==== path: " + s);
        int a = 0;
        if (!TextUtils.isEmpty((CharSequence)s)) {
            a = k.a().a(s);
            TXCLog.i("TXVideoEditer", " setBGM -> ret = " + a);
        }
        else {
            TXCLog.e("TXVideoEditer", " setBGM -> bgm path is empty.");
        }
        if (a != 0) {
            return a;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aQ);
        this.mBgmConfig.a(s);
        this.mVideoEditerPreview.b(s);
        this.stopPlay();
        return 0;
    }
    
    public void setBGMLoop(final boolean e) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBGMLoop is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setBGMLoop ==== looping: " + e);
        if (this.mBgmConfig.b == this.mBgmConfig.c) {
            TXCLog.w("TXVideoEditer", "setBGMLoop fail: bgmconfig starttime equals endtime!");
            return;
        }
        this.mBgmConfig.e = e;
        this.mVideoEditerPreview.a(e);
    }
    
    public void setBGMAtVideoTime(final long d) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBGMAtVideoTime is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setBGMAtVideoTime ==== videoStartTime: " + d);
        this.mBgmConfig.d = d;
        this.mVideoEditerPreview.a(d);
    }
    
    public void setBGMStartTime(final long b, final long c) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBGMStartTime is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setBGMStartTime ==== startTime: " + b + ", endTime: " + c);
        if (b == c) {
            TXCLog.w("TXVideoEditer", "bgm starttime is equal endtime: fail !");
            return;
        }
        this.mBgmConfig.b = b;
        this.mBgmConfig.c = c;
        this.mVideoEditerPreview.a(b, c);
    }
    
    public void setBGMVolume(final float g) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBGMVolume is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setBGMVolume ==== volume: " + g);
        this.mBgmConfig.g = g;
        this.mVideoEditerPreview.b(g);
    }
    
    public void setBGMFadeInOutDuration(final long j, final long k) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setBGMFadeInOutDuration is not supported in UGC_Smart license");
            return;
        }
        if ((j == 0L && k == 0L) || j < 0L || k < 0L) {
            this.mBgmConfig.i = false;
            return;
        }
        this.mBgmConfig.i = true;
        this.mBgmConfig.j = j;
        this.mBgmConfig.k = k;
    }
    
    public void setWaterMark(final Bitmap bitmap, final TXVideoEditConstants.TXRect txRect) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setWaterMark is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setWaterMark ==== waterMark: " + bitmap + ", rect: " + txRect);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aU);
        final a.h h = new a.h();
        h.c = txRect.width;
        h.a = txRect.x;
        h.b = txRect.y;
        this.mVideoPreProcessConfig.a(new CaptureAndEnc.j(bitmap, h));
    }
    
    public void setTailWaterMark(final Bitmap bitmap, final TXVideoEditConstants.TXRect txRect, final int n) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setTailWaterMark is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setTailWaterMark ==== tailWaterMark: " + bitmap + ", rect: " + txRect + ", duration: " + n);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aV);
        final a.h h = new a.h();
        h.c = txRect.width;
        h.a = txRect.x;
        h.b = txRect.y;
        com.tencent.liteav.f.j.a().a(new CaptureAndEnc.i(bitmap, h, n));
    }
    
    public void setSubtitleList(final List<TXVideoEditConstants.TXSubtitle> list) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setSubtitleList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setSubtitleList ==== subtitleList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aT);
            final ArrayList<a.k> list2 = new ArrayList<a.k>();
            for (int i = 0; i < list.size(); ++i) {
                final TXVideoEditConstants.TXSubtitle txSubtitle = list.get(i);
                final a.k k = new a.k();
                final a.h b = new a.h();
                b.c = txSubtitle.frame.width;
                b.a = txSubtitle.frame.x;
                b.b = txSubtitle.frame.y;
                k.b = b;
                k.a = txSubtitle.titleImage;
                k.c = txSubtitle.startTime;
                k.d = txSubtitle.endTime;
                list2.add(k);
            }
            h.a().a(list2);
        }
        else {
            h.a().a((List<a.k>)null);
        }
    }
    
    public void setAnimatedPasterList(final List<TXVideoEditConstants.TXAnimatedPaster> list) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setAnimatedPasterList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setAnimatedPasterList ==== animatedPasterList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aS);
            final ArrayList<a.b> list2 = new ArrayList<a.b>();
            for (int i = 0; i < list.size(); ++i) {
                final TXVideoEditConstants.TXAnimatedPaster txAnimatedPaster = list.get(i);
                final a.b b = new a.b();
                final a.h b2 = new a.h();
                b2.c = txAnimatedPaster.frame.width;
                b2.a = txAnimatedPaster.frame.x;
                b2.b = txAnimatedPaster.frame.y;
                b.b = b2;
                b.a = txAnimatedPaster.animatedPasterPathFolder;
                b.c = txAnimatedPaster.startTime;
                b.d = txAnimatedPaster.endTime;
                b.e = txAnimatedPaster.rotation;
                list2.add(b);
            }
            com.tencent.liteav.f.a.a().a(list2);
        }
        else {
            com.tencent.liteav.f.a.a().a((List<a.b>)null);
        }
    }
    
    public void setPasterList(final List<TXVideoEditConstants.TXPaster> list) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setPasterList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setPasterList ==== pasterList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aR);
            final ArrayList<a.e> list2 = new ArrayList<a.e>();
            for (int i = 0; i < list.size(); ++i) {
                final TXVideoEditConstants.TXPaster txPaster = list.get(i);
                final a.e e = new a.e();
                final a.h b = new a.h();
                b.c = txPaster.frame.width;
                b.a = txPaster.frame.x;
                b.b = txPaster.frame.y;
                e.b = b;
                e.a = txPaster.pasterImage;
                e.c = txPaster.startTime;
                e.d = txPaster.endTime;
                list2.add(e);
            }
            com.tencent.liteav.f.f.a().a(list2);
        }
        else {
            com.tencent.liteav.f.f.a().a((List<a.e>)null);
        }
    }
    
    public void setRenderRotation(final int n) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setRenderRotation is not supported in UGC_Smart license");
            return;
        }
        j.a().a(n);
        if (i.a().t.get() == 3 && this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.b();
        }
    }
    
    public void setSpeedList(final List<TXVideoEditConstants.TXSpeed> list) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setSpeedList is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setSpeedList ==== ");
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aL);
            final ArrayList<a.j> list2 = new ArrayList<a.j>();
            for (int i = 0; i < list.size(); ++i) {
                final TXVideoEditConstants.TXSpeed txSpeed = list.get(i);
                final a.j j = new a.j();
                j.a = txSpeed.speedLevel;
                j.b = txSpeed.startTime;
                j.c = txSpeed.endTime;
                list2.add(j);
            }
            g.a().a(list2);
        }
        else {
            g.a().a(null);
        }
    }
    
    public void setRepeatPlay(final List<TXVideoEditConstants.TXRepeat> list) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setRepeatPlay is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setRepeatPlay ==== ");
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aM);
            final ArrayList<a.i> list2 = new ArrayList<a.i>();
            for (int i = 0; i < list.size(); ++i) {
                final TXVideoEditConstants.TXRepeat txRepeat = list.get(i);
                final a.i j = new a.i();
                j.c = txRepeat.repeatTimes;
                j.a = txRepeat.startTime;
                j.b = txRepeat.endTime;
                list2.add(j);
            }
            CameraProxy.f.a().a(list2);
            final TXVideoEditConstants.TXRepeat txRepeat2 = list.get(0);
            this.mVideoEditerPreview.c(txRepeat2.startTime * 1000L, txRepeat2.endTime * 1000L);
        }
        else {
            TXCLog.i("TXVideoEditer", "==== cancel setRepeatPlay ==== ");
            CameraProxy.f.a().a(null);
            this.mVideoEditerPreview.c(0L, 0L);
        }
    }
    
    public void setReverse(final boolean b) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "setReverse is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== setReverse ====isReverse:" + b);
        if (b) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aN);
        }
        this.mVideoEditerPreview.d();
        CameraProxy.g.a().a(b);
    }
    
    public void startEffect(final int n, final long n2) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "startEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== startEffect ==== type: " + n + ", startTime: " + n2);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aO, n, "");
        CaptureAndEnc.f f = null;
        switch (n) {
            case 0: {
                f = new CaptureAndEnc.f(2);
                break;
            }
            case 1: {
                f = new CaptureAndEnc.f(3);
                break;
            }
            case 2: {
                f = new CaptureAndEnc.f(0);
                break;
            }
            case 3: {
                f = new CaptureAndEnc.f(1);
                break;
            }
            case 4: {
                f = new CaptureAndEnc.f(4);
                break;
            }
            case 5: {
                f = new CaptureAndEnc.f(5);
                break;
            }
            case 6: {
                f = new CaptureAndEnc.f(6);
                break;
            }
            case 7: {
                f = new CaptureAndEnc.f(7);
                break;
            }
            case 8: {
                f = new CaptureAndEnc.f(8);
                break;
            }
            case 10: {
                f = new CaptureAndEnc.f(10);
                break;
            }
            case 9: {
                f = new CaptureAndEnc.f(11);
                break;
            }
        }
        if (f != null) {
            if (CameraProxy.g.a().b()) {
                f.c = n2 * 1000L;
            }
            else {
                f.b = n2 * 1000L;
            }
            this.mMotionFilterConfig.a(f);
        }
    }
    
    public void stopEffect(final int n, final long n2) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "stopEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== stopEffect ==== type: " + n + ", endTime: " + n2);
        final CaptureAndEnc.f b = this.mMotionFilterConfig.b();
        if (b != null) {
            if (CameraProxy.g.a().b()) {
                b.b = n2 * 1000L;
            }
            else {
                b.c = n2 * 1000L;
            }
        }
    }
    
    public void deleteLastEffect() {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "deleteLastEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== deleteLastEffect ====");
        this.mMotionFilterConfig.c();
    }
    
    public void deleteAllEffect() {
        if (this.isSmartLicense()) {
            TXCLog.e("TXVideoEditer", "deleteAllEffect is not supported in UGC_Smart license");
            return;
        }
        TXCLog.i("TXVideoEditer", "==== deleteAllEffect ====");
        this.mMotionFilterConfig.e();
    }
    
    public void setVideoProcessListener(final TXVideoProcessListener mtxVideoProcessListener) {
        this.mTXVideoProcessListener = mtxVideoProcessListener;
        if (mtxVideoProcessListener == null) {
            if (this.mVideoProcessGenerate != null) {
                this.mVideoProcessGenerate.a((com.tencent.liteav.i.b.e)null);
            }
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a((com.tencent.liteav.i.b.e)null);
            }
            if (this.mVideoAverageThumbnailGenerate != null) {
                this.mVideoAverageThumbnailGenerate.a((com.tencent.liteav.i.b.e)null);
            }
        }
        else {
            if (this.mVideoProcessGenerate != null) {
                this.mVideoProcessGenerate.a(this.mTXCVideoProcessListener);
            }
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a(this.mTXCVideoProcessListener);
            }
            if (this.mVideoAverageThumbnailGenerate != null) {
                this.mVideoAverageThumbnailGenerate.a(this.mTXCVideoProcessListener);
            }
        }
    }
    
    public void getThumbnail(final List<Long> list, final int n, final int n2, final boolean b, final TXThumbnailListener mtxThumbnailListener) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.mTXThumbnailListener = mtxThumbnailListener;
        CameraProxy.h.a().i();
        CameraProxy.h.a().a(b);
        if (this.mVideoTimelistThumbnailGenerate != null) {
            this.mVideoTimelistThumbnailGenerate.a(this.mTXCThumbnailListener);
            this.mVideoTimelistThumbnailGenerate.a(n);
            this.mVideoTimelistThumbnailGenerate.b(n2);
            this.mVideoAverageThumbnailGenerate.b(true);
            this.mVideoTimelistThumbnailGenerate.c(b);
            this.mVideoTimelistThumbnailGenerate.a(list);
            this.mVideoTimelistThumbnailGenerate.a();
        }
    }
    
    public void getThumbnail(final int a, final int b, final int c, final boolean b2, final TXThumbnailListener mtxThumbnailListener) {
        CameraProxy.h.a().i();
        this.mTXThumbnailListener = mtxThumbnailListener;
        final a.l l = new a.l();
        l.a = a;
        l.b = b;
        l.c = c;
        CameraProxy.h.a().a(l);
        CameraProxy.h.a().a(b2);
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.a(this.mTXCThumbnailListener);
            this.mVideoAverageThumbnailGenerate.b(true);
            this.mVideoAverageThumbnailGenerate.a(true);
            this.mVideoAverageThumbnailGenerate.c(b2);
            this.mVideoAverageThumbnailGenerate.a();
        }
    }
    
    public void setThumbnail(final TXVideoEditConstants.TXThumbnail txThumbnail) {
        final a.l l = new a.l();
        l.a = txThumbnail.count;
        l.b = txThumbnail.width;
        l.c = txThumbnail.height;
        CameraProxy.h.a().a(l);
    }
    
    public void setThumbnailListener(final TXThumbnailListener mtxThumbnailListener) {
        this.mTXThumbnailListener = mtxThumbnailListener;
        if (mtxThumbnailListener == null) {
            if (this.mVideoProcessGenerate != null) {
                this.mVideoProcessGenerate.a((com.tencent.liteav.i.b.a)null);
            }
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a((com.tencent.liteav.i.b.a)null);
            }
            if (this.mVideoAverageThumbnailGenerate != null) {
                this.mVideoAverageThumbnailGenerate.a((com.tencent.liteav.i.b.a)null);
            }
            if (this.mVideoTimelistThumbnailGenerate != null) {
                this.mVideoTimelistThumbnailGenerate.a((com.tencent.liteav.i.b.a)null);
            }
        }
        else {
            if (this.mVideoProcessGenerate != null) {
                this.mVideoProcessGenerate.a(this.mTXCThumbnailListener);
            }
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a(this.mTXCThumbnailListener);
            }
            if (this.mVideoAverageThumbnailGenerate != null) {
                this.mVideoAverageThumbnailGenerate.a(this.mTXCThumbnailListener);
            }
            if (this.mVideoTimelistThumbnailGenerate != null) {
                this.mVideoTimelistThumbnailGenerate.a(this.mTXCThumbnailListener);
            }
        }
    }
    
    public void processVideo() {
        TXCLog.i("TXVideoEditer", "=== processVideo ===");
        if (LicenceCheck.a().a((f)null, this.mContext) != 0 || (LicenceCheck.a().b() == 2 && !this.mSmartLicenseSupport)) {
            final TXVideoEditConstants.TXGenerateResult txGenerateResult = new TXVideoEditConstants.TXGenerateResult();
            txGenerateResult.retCode = -5;
            txGenerateResult.descMsg = "licence verify failed";
            if (this.mTXVideoProcessListener != null) {
                this.mTXVideoProcessListener.onProcessComplete(txGenerateResult);
            }
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.ba);
        CameraProxy.h.a().a(false);
        this.mVideoOutputConfig.o = com.tencent.liteav.j.f.a(this.mContext, 1);
        this.mVideoOutputConfig.j = 3;
        this.mVideoOutputConfig.m = true;
        final boolean f = this.mVideoSourceConfig.f();
        TXCLog.i("TXVideoEditer", "allFullFrame:" + f);
        if (this.mVideoOutputConfig.r = f) {
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a();
            }
        }
        else if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.a();
        }
    }
    
    public void release() {
        TXCLog.i("TXVideoEditer", "=== release ===");
        this.initConfig();
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.g();
        }
        if (this.mVideoRecordGenerate != null) {
            this.mVideoRecordGenerate.d();
        }
        if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.d();
        }
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.d();
        }
        if (this.mVideoTimelistThumbnailGenerate != null) {
            this.mVideoTimelistThumbnailGenerate.d();
        }
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.d();
        }
        this.mTXCThumbnailListener = null;
        this.mTXCVideoCustomProcessListener = null;
        this.mTXCVideoGenerateListener = null;
        this.mTXCVideoPreviewListener = null;
        this.mTXCVideoProcessListener = null;
        for (final Bitmap bitmap : this.mBitmapList) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        this.mBitmapList.clear();
    }
    
    private void initConfig() {
        i.a().o();
        k.a().g();
        CameraProxy.c.a().h();
        CameraProxy.g.a().c();
        CameraProxy.f.a().c();
        g.a().d();
        j.a().g();
        h.a().c();
        com.tencent.liteav.f.f.a().c();
        com.tencent.liteav.f.a.a().c();
        d.a().e();
        b.a().b();
        com.tencent.liteav.f.j.a().i();
        CameraProxy.h.a().j();
    }
    
    public void setTXVideoPreviewListener(final TXVideoPreviewListener mtxVideoPreviewListener) {
        this.mTXVideoPreviewListener = mtxVideoPreviewListener;
        if (this.mVideoEditerPreview != null) {
            if (mtxVideoPreviewListener == null) {
                this.mVideoEditerPreview.a((com.tencent.liteav.i.b.d)null);
            }
            else {
                this.mVideoEditerPreview.a(this.mTXCVideoPreviewListener);
            }
        }
    }
    
    public void initWithPreview(final TXVideoEditConstants.TXPreviewParam txPreviewParam) {
        if (txPreviewParam == null) {
            TXCLog.e("TXVideoEditer", "=== initWithPreview === please set param not null");
            return;
        }
        TXCLog.i("TXVideoEditer", "=== initWithPreview === rendeMode: " + txPreviewParam.renderMode);
        final a.g g = new a.g();
        g.b = txPreviewParam.renderMode;
        g.a = txPreviewParam.videoView;
        this.mVideoOutputConfig.s = g.b;
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a(g);
        }
    }
    
    public void startPlayFromTime(final long n, final long n2) {
        TXCLog.i("TXVideoEditer", "==== startPlayFromTime ==== startTime: " + n + ", endTime: " + n2);
        if (this.mIsPreviewStart) {
            this.stopPlay();
        }
        this.mIsPreviewStart = true;
        if (this.mVideoEditerPreview != null) {
            CameraProxy.c.a().b(n * 1000L, n2 * 1000L);
            this.mVideoEditerPreview.b(n, n2);
            this.mVideoEditerPreview.c();
            i.a().t.set(2);
        }
    }
    
    public void pausePlay() {
        TXCLog.i("TXVideoEditer", "==== pausePlay ====");
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.f();
            i.a().t.set(3);
        }
    }
    
    public void resumePlay() {
        TXCLog.i("TXVideoEditer", "==== resumePlay ====");
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.e();
            i.a().t.set(2);
        }
    }
    
    public void stopPlay() {
        TXCLog.i("TXVideoEditer", "==== stopPlay ====");
        if (this.mIsPreviewStart) {
            if (this.mVideoEditerPreview != null) {
                this.mVideoEditerPreview.d();
                i.a().t.set(1);
            }
            this.mIsPreviewStart = false;
        }
    }
    
    public void previewAtTime(final long n) {
        TXCLog.d("TXVideoEditer", "==== previewAtTime ==== timeMs: " + n);
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.b(n);
        }
    }
    
    public void setVideoGenerateListener(final TXVideoGenerateListener mtxVideoGenerateListener) {
        TXCLog.i("TXVideoEditer", "=== setVideoGenerateListener === listener:" + mtxVideoGenerateListener);
        this.mTXVideoGenerateListener = mtxVideoGenerateListener;
        if (this.mVideoEditGenerate != null) {
            if (mtxVideoGenerateListener == null) {
                this.mVideoEditGenerate.a((com.tencent.liteav.i.b.c)null);
            }
            else {
                this.mVideoEditGenerate.a(this.mTXCVideoGenerateListener);
            }
        }
    }
    
    public void setCutFromTime(final long n, final long n2) {
        TXCLog.i("TXVideoEditer", "==== setCutFromTime ==== startTime: " + n + ", endTime: " + n2);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aK);
        CameraProxy.c.a().a(n * 1000L, n2 * 1000L);
    }
    
    public void setVideoBitrate(final int p) {
        TXCLog.i("TXVideoEditer", "==== setVideoBitrate ==== videoBitrate: " + p);
        this.mVideoOutputConfig.p = p;
    }
    
    public void setAudioBitrate(final int n) {
        TXCLog.i("TXVideoEditer", "==== setAudioBitrate ==== audioBitrate: " + n);
        this.mVideoOutputConfig.q = n * 1024;
    }
    
    public void generateVideo(final int j, final String i) {
        TXCLog.i("TXVideoEditer", "==== generateVideo ==== videoCompressed: " + j + ", videoOutputPath: " + i);
        if (LicenceCheck.a().a((f)null, this.mContext) != 0 || (LicenceCheck.a().b() == 2 && !this.mSmartLicenseSupport)) {
            final TXVideoEditConstants.TXGenerateResult txGenerateResult = new TXVideoEditConstants.TXGenerateResult();
            txGenerateResult.retCode = -5;
            txGenerateResult.descMsg = "licence verify failed";
            if (this.mTXVideoGenerateListener != null) {
                this.mTXVideoGenerateListener.onGenerateComplete(txGenerateResult);
            }
            return;
        }
        this.mVideoOutputConfig.s = 2;
        this.mVideoOutputConfig.i = i;
        this.mVideoOutputConfig.j = j;
        this.mVideoOutputConfig.m = false;
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.a();
        }
    }
    
    public void cancel() {
        TXCLog.i("TXVideoEditer", "==== cancel ====");
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.c();
        }
        if (this.mVideoTimelistThumbnailGenerate != null) {
            this.mVideoTimelistThumbnailGenerate.c();
        }
        if (this.mVideoRecordGenerate != null) {
            this.mVideoRecordGenerate.c();
        }
        if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.c();
        }
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.c();
        }
    }
    
    public void refreshOneFrame() {
        TXCLog.d("TXVideoEditer", "==== refreshOneFrame ====");
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a();
        }
    }
    
    public void setVideoVolume(final float f) {
        TXCLog.i("TXVideoEditer", "==== setVideoVolume ==== volume: " + f);
        this.mBgmConfig.f = f;
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a(f);
        }
    }
    
    public interface TXThumbnailListener
    {
        void onThumbnail(final int p0, final long p1, final Bitmap p2);
    }
    
    public interface TXPCMCallbackListener
    {
        TXAudioFrame onPCMCallback(final TXAudioFrame p0);
    }
    
    public interface TXVideoCustomProcessListener
    {
        int onTextureCustomProcess(final int p0, final int p1, final int p2, final long p3);
        
        void onTextureDestroyed();
    }
    
    public interface TXVideoProcessListener
    {
        void onProcessProgress(final float p0);
        
        void onProcessComplete(final TXVideoEditConstants.TXGenerateResult p0);
    }
    
    public interface TXVideoPreviewListenerEx extends TXVideoPreviewListener
    {
        void onPreviewError(final TXVideoEditConstants.TXPreviewError p0);
    }
    
    public interface TXVideoPreviewListener
    {
        void onPreviewProgress(final int p0);
        
        void onPreviewFinished();
    }
    
    public interface TXVideoGenerateListener
    {
        void onGenerateProgress(final float p0);
        
        void onGenerateComplete(final TXVideoEditConstants.TXGenerateResult p0);
    }
}
