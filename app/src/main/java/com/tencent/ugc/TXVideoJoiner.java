package com.tencent.ugc;

import android.content.*;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.basic.datareport.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.license.*;
import android.os.*;

import java.util.*;

public class TXVideoJoiner
{
    private static final String TAG = "TXVideoJoiner";
    private Context mContext;
    private k mTXCombineVideo;
    private r mVideoJoinPreview;
    private p mVideoJoinGenerate;
    private t mVideoSourceListConfig;
    private s mVideoOutputListConfig;
    private TXVideoPreviewListener mTXVideoPreviewListener;
    private TXVideoJoinerListener mTXVideoJoinerListener;
    private b mQuickJointer;
    private List<String> mVideoPathList;
    private c.b mTXCVideoPreviewListener;
    private c.a mTXCVideoJoinerListener;
    
    public TXVideoJoiner(final Context context) {
        this.mTXCVideoPreviewListener = new c.b() {
            @Override
            public void a(final int n) {
                if (TXVideoJoiner.this.mTXVideoPreviewListener != null) {
                    TXVideoJoiner.this.mTXVideoPreviewListener.onPreviewProgress(n);
                }
            }
            
            @Override
            public void a() {
                if (TXVideoJoiner.this.mTXVideoPreviewListener != null) {
                    TXVideoJoiner.this.mTXVideoPreviewListener.onPreviewFinished();
                }
            }
        };
        this.mTXCVideoJoinerListener = new c.a() {
            @Override
            public void a(final float n) {
                if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                    TXVideoJoiner.this.mTXVideoJoinerListener.onJoinProgress(n);
                }
            }
            
            @Override
            public void a(final com.tencent.liteav.i.a.d d) {
                final TXVideoEditConstants.TXJoinerResult txJoinerResult = new TXVideoEditConstants.TXJoinerResult();
                txJoinerResult.retCode = d.a;
                txJoinerResult.descMsg = d.b;
                if (txJoinerResult.retCode == 0) {
                    final int p = i.a().p();
                    final int q = i.a().q();
                    TXCDRApi.txReportDAU(TXVideoJoiner.this.mContext, com.tencent.liteav.basic.datareport.a.aY, p, "");
                    TXCDRApi.txReportDAU(TXVideoJoiner.this.mContext, com.tencent.liteav.basic.datareport.a.aZ, q, "");
                }
                if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                    TXVideoJoiner.this.mTXVideoJoinerListener.onJoinComplete(txJoinerResult);
                }
            }
        };
        TXCCommonUtil.setAppContext(this.mContext = context.getApplicationContext());
        TXCLog.init();
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.ax);
        TXCDRApi.initCrashReport(this.mContext);
        LicenceCheck.a().a((f)null, this.mContext);
        this.mVideoJoinPreview = new r(this.mContext);
        this.mVideoJoinGenerate = new p(this.mContext);
        this.mTXCombineVideo = new k(context);
        this.mVideoSourceListConfig = t.a();
        this.mVideoOutputListConfig = s.r();
    }
    
    public int setVideoPathList(final List<String> mVideoPathList) {
        if (mVideoPathList == null || mVideoPathList.size() == 0) {
            TXCLog.e("TXVideoJoiner", "==== setVideoSourceList ==== is empty");
            return 0;
        }
        TXCLog.i("TXVideoJoiner", "==== setVideoSourceList videoSourceList:" + mVideoPathList);
        this.mVideoPathList = mVideoPathList;
        this.mVideoSourceListConfig.a(mVideoPathList);
        this.mTXCombineVideo.a(mVideoPathList);
        return this.mVideoSourceListConfig.c();
    }
    
    public void initWithPreview(final TXVideoEditConstants.TXPreviewParam txPreviewParam) {
        if (txPreviewParam == null) {
            TXCLog.e("TXVideoJoiner", "=== initWithPreview === please set param not null");
            return;
        }
        TXCLog.i("TXVideoJoiner", "=== initWithPreview === rendeMode: " + txPreviewParam.renderMode);
        final a.g g = new a.g();
        g.a = txPreviewParam.videoView;
        g.b = txPreviewParam.renderMode;
        this.mVideoOutputListConfig.u = g.b;
        if (this.mVideoJoinPreview != null) {
            this.mVideoJoinPreview.a(g);
        }
    }
    
    public void setTXVideoPreviewListener(final TXVideoPreviewListener mtxVideoPreviewListener) {
        TXCLog.i("TXVideoJoiner", "==== setTXVideoPreviewListener ====listener:" + mtxVideoPreviewListener);
        this.mTXVideoPreviewListener = mtxVideoPreviewListener;
        if (this.mVideoJoinPreview != null) {
            if (mtxVideoPreviewListener == null) {
                this.mVideoJoinPreview.a((c.b)null);
            }
            else {
                this.mVideoJoinPreview.a(this.mTXCVideoPreviewListener);
            }
        }
    }
    
    public void startPlay() {
        TXCLog.i("TXVideoJoiner", "==== startPlay ====");
        if (this.mVideoJoinPreview != null) {
            this.mVideoJoinPreview.a();
        }
    }
    
    public void pausePlay() {
        TXCLog.i("TXVideoJoiner", "==== pausePlay ====");
        if (this.mVideoJoinPreview != null) {
            this.mVideoJoinPreview.c();
        }
    }
    
    public void resumePlay() {
        TXCLog.i("TXVideoJoiner", "==== resumePlay ====");
        if (this.mVideoJoinPreview != null) {
            this.mVideoJoinPreview.d();
        }
    }
    
    public void stopPlay() {
        TXCLog.i("TXVideoJoiner", "==== stopPlay ====");
        if (this.mVideoJoinPreview != null) {
            this.mVideoJoinPreview.b();
        }
    }
    
    public void setVideoJoinerListener(final TXVideoJoinerListener mtxVideoJoinerListener) {
        TXCLog.i("TXVideoJoiner", "=== setVideoJoinerListener === listener:" + mtxVideoJoinerListener);
        this.mTXVideoJoinerListener = mtxVideoJoinerListener;
        if (this.mVideoJoinGenerate != null) {
            if (mtxVideoJoinerListener == null) {
                this.mVideoJoinGenerate.a((c.a)null);
            }
            else {
                this.mVideoJoinGenerate.a(this.mTXCVideoJoinerListener);
            }
        }
    }
    
    public void joinVideo(final int v, final String i) {
        TXCLog.i("TXVideoJoiner", "==== joinVideo ====");
        final int a = LicenceCheck.a().a((f)null, this.mContext);
        if (a != 0 || LicenceCheck.a().b() == 2) {
            TXCLog.e("TXVideoJoiner", "joinVideo, checkErrCode = " + a + ", licenseVersionType = " + LicenceCheck.a().b());
            final TXVideoEditConstants.TXJoinerResult txJoinerResult = new TXVideoEditConstants.TXJoinerResult();
            txJoinerResult.retCode = -5;
            txJoinerResult.descMsg = "licence verify failed";
            if (this.mTXVideoJoinerListener != null) {
                this.mTXVideoJoinerListener.onJoinComplete(txJoinerResult);
            }
            return;
        }
        this.mVideoOutputListConfig.i = i;
        this.mVideoOutputListConfig.v = v;
        if (!this.quickJoin() && this.mVideoJoinGenerate != null) {
            this.mVideoJoinGenerate.a();
        }
    }
    
    public void cancel() {
        TXCLog.i("TXVideoJoiner", "==== cancel ====");
        if (this.mVideoJoinGenerate != null) {
            this.mVideoJoinGenerate.b();
        }
        if (this.mQuickJointer != null) {
            this.mQuickJointer.c();
            this.mQuickJointer = null;
        }
        if (this.mTXCombineVideo != null) {
            this.mTXCombineVideo.a((c.a)null);
            this.mTXCombineVideo.b();
        }
    }
    
    private boolean quickJoin() {
        (this.mQuickJointer = new b("joiner")).a(this.mVideoPathList);
        this.mQuickJointer.a(this.mVideoOutputListConfig.i);
        final boolean matchQuickJoin = this.isMatchQuickJoin();
        if (matchQuickJoin) {
            this.mQuickJointer.a(new b.a() {
                @Override
                public void a(final b b, final int n, final String descMsg) {
                    b.c();
                    b.d();
                    TXVideoJoiner.this.mQuickJointer = null;
                    final TXVideoEditConstants.TXJoinerResult txJoinerResult = new TXVideoEditConstants.TXJoinerResult();
                    txJoinerResult.retCode = ((n == 0) ? 0 : -1);
                    txJoinerResult.descMsg = descMsg;
                    if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                        TXVideoJoiner.this.mTXVideoJoinerListener.onJoinComplete(txJoinerResult);
                    }
                }
                
                @Override
                public void a(final b b, final float n) {
                    if (TXVideoJoiner.this.mTXVideoJoinerListener != null) {
                        TXVideoJoiner.this.mTXVideoJoinerListener.onJoinProgress(n);
                    }
                }
            });
            TXCLog.i("TXVideoJoiner", "==== quickJoin ====");
            this.mQuickJointer.b();
        }
        return matchQuickJoin;
    }
    
    private boolean isMatchQuickJoin() {
        boolean a = this.mQuickJointer.a();
        if (a) {
            final int e = this.mQuickJointer.e();
            final int f = this.mQuickJointer.f();
            this.mVideoOutputListConfig.a(this.mVideoSourceListConfig.j());
            final int[] a2 = com.tencent.liteav.j.c.a(this.mVideoOutputListConfig.v, e, f);
            a = (e == a2[0] && f == a2[1]);
        }
        if (!a) {
            return a;
        }
        if (this.isVideoDurationBiggerTooMuchThanAudio()) {
            a = false;
        }
        return !this.hasBFrame() && !this.isContainsContentUri() && a;
    }
    
    private boolean isContainsContentUri() {
        boolean b = false;
        final List<String> b2 = t.a().b();
        for (int i = 0; i < b2.size(); ++i) {
            if (com.tencent.liteav.editer.p.c(b2.get(i))) {
                b = true;
            }
        }
        return b;
    }
    
    private boolean isVideoDurationBiggerTooMuchThanAudio() {
        if (Build.VERSION.SDK_INT >= 16) {
            final e e = new e();
            for (int i = 0; i < this.mVideoPathList.size(); ++i) {
                e.a(this.mVideoPathList.get(i));
                final long j = e.j();
                final long k = e.k();
                if (j <= 0L || k <= 0L) {
                    return true;
                }
                if (j - k > 400000L) {
                    TXCLog.i("TXVideoJoiner", "isVideoDurationBiggerTooMuchThanAudio, videoDuration = " + j + ", audioDuration = " + k);
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hasBFrame() {
        if (Build.VERSION.SDK_INT >= 16) {
            final e e = new e();
            for (int i = 0; i < this.mVideoPathList.size(); ++i) {
                long n = -1L;
                e.a(this.mVideoPathList.get(i));
                int d = e.d();
                if (d <= 0) {
                    d = 1;
                }
                e.a(0L);
                do {
                    final long r = e.r();
                    TXCLog.i("TXVideoJoiner", "isMatchQuickJoin, video index = " + i + ", pts = " + r + ", lastVideoPts = " + n);
                    if (r >= d * 1000000L) {
                        break;
                    }
                    if (r < n) {
                        e.o();
                        return true;
                    }
                    n = r;
                } while (!e.c(new CaptureAndEnc.e()));
            }
            e.o();
        }
        return false;
    }
    
    public void setSplitScreenList(final List<TXVideoEditConstants.TXAbsoluteRect> list, final int n, final int n2) {
        TXCLog.i("TXVideoJoiner", "==== setSplitScreenList ====canvasWidth:" + n + ",canvasHeight:" + n2);
        if (list != null && list.size() > 0) {
            final ArrayList<a.a> list2 = new ArrayList<a.a>();
            for (int i = 0; i < list.size(); ++i) {
                final TXVideoEditConstants.TXAbsoluteRect txAbsoluteRect = list.get(i);
                final a.a a = new a.a();
                a.c = txAbsoluteRect.width;
                a.d = txAbsoluteRect.height;
                a.a = txAbsoluteRect.x;
                a.b = txAbsoluteRect.y;
                list2.add(a);
            }
            this.mTXCombineVideo.a(list2, n, n2);
        }
    }
    
    public void setVideoVolumes(final List<Float> list) {
        if (this.mTXCombineVideo != null) {
            this.mTXCombineVideo.b(list);
        }
    }
    
    public void splitJoinVideo(final int n, final String s) {
        TXCLog.i("TXVideoJoiner", "==== splitJoinVideo ====");
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aX);
        final int a = LicenceCheck.a().a((f)null, this.mContext);
        if (a != 0 || LicenceCheck.a().b() == 2) {
            TXCLog.e("TXVideoJoiner", "splitJoinVideo, checkErrCode = " + a + ", licenseVersionType = " + LicenceCheck.a().b());
            final TXVideoEditConstants.TXJoinerResult txJoinerResult = new TXVideoEditConstants.TXJoinerResult();
            txJoinerResult.retCode = -5;
            txJoinerResult.descMsg = "licence verify failed";
            if (this.mTXVideoJoinerListener != null) {
                this.mTXVideoJoinerListener.onJoinComplete(txJoinerResult);
            }
            return;
        }
        if (!t.a().h()) {
            final TXVideoEditConstants.TXJoinerResult txJoinerResult2 = new TXVideoEditConstants.TXJoinerResult();
            txJoinerResult2.retCode = -5;
            txJoinerResult2.descMsg = "Chorus video does not support videos with no audio tracks";
            if (this.mTXVideoJoinerListener != null) {
                this.mTXVideoJoinerListener.onJoinComplete(txJoinerResult2);
            }
            return;
        }
        if (this.mTXCombineVideo != null) {
            this.mTXCombineVideo.a(s);
            this.mTXCombineVideo.a(this.mTXCVideoJoinerListener);
            this.mTXCombineVideo.a();
        }
    }
    
    public interface TXVideoPreviewListener
    {
        void onPreviewProgress(final int p0);
        
        void onPreviewFinished();
    }
    
    public interface TXVideoJoinerListener
    {
        void onJoinProgress(final float p0);
        
        void onJoinComplete(final TXVideoEditConstants.TXJoinerResult p0);
    }
}
