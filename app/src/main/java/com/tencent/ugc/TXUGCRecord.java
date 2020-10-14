package com.tencent.ugc;

import com.tencent.liteav.basic.b.*;
import android.content.*;
import com.tencent.liteav.capturer.*;
import java.util.concurrent.atomic.*;
import com.tencent.liteav.*;
import com.tencent.liteav.renderer.*;
import com.tencent.liteav.muxer.*;
import java.util.concurrent.*;
import com.tencent.rtmp.ui.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.license.*;
import com.tencent.liteav.audio.impl.*;
import com.tencent.liteav.basic.datareport.*;
import android.text.*;
import java.io.*;
import com.tencent.rtmp.*;
import android.os.*;
import android.util.*;
import java.text.*;
import java.util.*;
import com.tencent.liteav.audio.*;
import com.tencent.liteav.basic.c.*;
import android.annotation.*;
import android.graphics.*;
import javax.microedition.khronos.egl.*;
import com.tencent.liteav.videoencoder.*;
import com.tencent.liteav.basic.util.*;
import android.media.*;
import com.tencent.liteav.basic.structs.*;

public class TXUGCRecord implements d, b, n, f, com.tencent.liteav.videoencoder.d, TXUGCPartsManager.IPartsManagerListener
{
    private static final String TAG = "TXUGCRecord";
    private static TXUGCRecord instance;
    private static final String OUTPUT_DIR_NAME = "TXUGC";
    private static final String OUTPUT_TEMP_DIR_NAME = "TXUGCParts";
    private static final String OUTPUT_VIDEO_NAME = "TXUGCRecord.mp4";
    private static final String OUTPUT_VIDEO_COVER_NAME = "TXUGCCover.jpg";
    private static final int STATE_RECORD_INIT = 1;
    private static final int STATE_RECORD_RECORDING = 2;
    private static final int STATE_RECORD_PAUSE = 3;
    private static final int DEFAULT_CHANNEL = 1;
    private volatile int mRecordState;
    private boolean mUseSWEncoder;
    private int mFps;
    private int mGop;
    private final int AAC_FRAME_SIZE = 4096;
    private Context mContext;
    private Handler mMainHandler;
    private TXRecordCommon.ITXVideoRecordListener mVideoRecordListener;
    private long mRecordStartTime;
    private long mEncodePcmDataSize;
    private boolean mStartMuxer;
    private boolean mRecording;
    private boolean needCompose;
    private String mSaveDir;
    private String mVideoFileCurTempPath;
    private String mVideoFilePath;
    private String mVideoFileTempDir;
    private String mCoverCurTempPath;
    private String mCoverPath;
    private int mVideoWidth;
    private int mVideoHeight;
    private com.tencent.liteav.capturer.a.a mCameraResolution;
    private AtomicBoolean mStartPreview;
    private boolean mCapturing;
    private k mConfig;
    private TXCGLSurfaceView mVideoView;
    private com.tencent.liteav.capturer.a mCameraCapture;
    private e mVideoPreprocessor;
    private com.tencent.liteav.videoencoder.b mVideoEncoder;
    private c mMP4Muxer;
    private com.tencent.liteav.videoediter.a.c mTXMultiMediaComposer;
    private VideoCustomProcessListener mCustomProcessListener;
    private int mRenderRotationReadyChange;
    private int mCameraOrientationReadyChange;
    private int mRenderMode;
    private int mMinDuration;
    private int mMaxDuration;
    private boolean isReachedMaxDuration;
    private com.tencent.liteav.audio.e mBGMNotifyProxy;
    private TXRecordCommon.ITXBGMNotify mBGMNotify;
    private CopyOnWriteArrayList<Long> mBgmPartBytesList;
    private boolean mBGMDeletePart;
    private String mBGMPath;
    private long mBGMStartTime;
    private long mBGMEndTime;
    private boolean mBGMPlayStop;
    private Bitmap mWatermarkBitmap;
    private TXVideoEditConstants.TXRect mTxWaterMarkRect;
    private int mCropWidth;
    private int mCropHeight;
    private int mDisplayType;
    private TXUGCPartsManager mTXUGCPartsManager;
    private long mCurrentRecordDuration;
    private int mRecordRetCode;
    private int mRecordSpeed;
    private com.tencent.liteav.videoediter.ffmpeg.b mTXFFQuickJoiner;
    private boolean mInitCompelete;
    private TXCloudVideoView mTXCloudVideoView;
    private boolean mSnapshotRunning;
    private int mVoiceChangerType;
    private float mSpecialRadio;
    private boolean mSmartLicenseSupport;
    private TXBeautyManager mBeautyManager;
    private i mUGCLicenceControl;
    private a mTouchFocusRunnable;
    public static float PLAY_SPEED_FASTEST;
    public static float PLAY_SPEED_FAST;
    public static float PLAY_SPEED_SLOW;
    public static float PLAY_SPEED_SLOWEST;
    public static float ENCODE_SPEED_FASTEST;
    public static float ENCODE_SPEED_FAST;
    public static float ENCODE_SPEED_SLOW;
    public static float ENCODE_SPEED_SLOWEST;
    private static final int STATE_SUCCESS = 0;
    private static final int STATE_RECORDING = 1;
    private static final int STATE_NO_PERMISSION = -1;
    
    public static synchronized TXUGCRecord getInstance(final Context context) {
        if (TXUGCRecord.instance == null) {
            TXUGCRecord.instance = new TXUGCRecord(context);
        }
        return TXUGCRecord.instance;
    }
    
    protected TXUGCRecord(final Context context) {
        this.mRecordState = 1;
        this.mUseSWEncoder = false;
        this.mFps = 20;
        this.mGop = 3;
        this.mRecordStartTime = 0L;
        this.mEncodePcmDataSize = 0L;
        this.mStartMuxer = false;
        this.mRecording = false;
        this.needCompose = false;
        this.mSaveDir = null;
        this.mVideoFileCurTempPath = null;
        this.mVideoFilePath = null;
        this.mVideoFileTempDir = null;
        this.mCoverCurTempPath = null;
        this.mCoverPath = null;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mCameraResolution = com.tencent.liteav.capturer.a.a.f;
        this.mStartPreview = new AtomicBoolean(false);
        this.mCapturing = false;
        this.mConfig = new k();
        this.mCameraCapture = null;
        this.mVideoPreprocessor = null;
        this.mVideoEncoder = null;
        this.mMP4Muxer = null;
        this.mTXMultiMediaComposer = null;
        this.mRenderRotationReadyChange = -1;
        this.mCameraOrientationReadyChange = -1;
        this.mRenderMode = 0;
        this.isReachedMaxDuration = false;
        this.mBGMNotifyProxy = null;
        this.mBGMNotify = null;
        this.mBGMDeletePart = false;
        this.mRecordSpeed = 2;
        this.mInitCompelete = false;
        this.mSnapshotRunning = false;
        this.mVoiceChangerType = 0;
        this.mSpecialRadio = 0.5f;
        this.mSmartLicenseSupport = true;
        this.mTouchFocusRunnable = new a();
        if (context != null) {
            this.mContext = context.getApplicationContext();
            this.mMainHandler = new Handler(this.mContext.getMainLooper());
            TXCCommonUtil.setAppContext(this.mContext);
            TXCLog.init();
            this.mTXUGCPartsManager = new TXUGCPartsManager(this.mContext);
            this.mBgmPartBytesList = new CopyOnWriteArrayList<Long>();
            LicenceCheck.a().a((com.tencent.liteav.basic.license.f)null, this.mContext);
        }
        this.mUseSWEncoder = com.tencent.liteav.basic.util.f.h();
        this.initFileAndFolder();
        this.mUGCLicenceControl = new i(this.mContext);
        this.mBeautyManager = new com.tencent.liteav.beauty.b(this.mUGCLicenceControl);
        TXCAudioEngineJNI.nativeUseSysAudioDevice(true);
    }
    
    private void initFileAndFolder() {
        this.mSaveDir = this.getDefaultDir();
        this.mVideoFilePath = this.mSaveDir + File.separator + "TXUGCRecord.mp4";
        this.mCoverPath = this.mSaveDir + File.separator + "TXUGCCover.jpg";
        this.mVideoFileTempDir = this.mSaveDir + File.separator + "TXUGCParts";
        final File file = new File(this.mVideoFileTempDir);
        if (!file.exists()) {
            file.mkdir();
        }
        this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", this.getTimeString());
        final File file2 = new File(this.mVideoFilePath);
        if (file2.exists()) {
            file2.delete();
        }
    }
    
    public void setVideoRecordListener(final TXRecordCommon.ITXVideoRecordListener mVideoRecordListener) {
        this.mVideoRecordListener = mVideoRecordListener;
    }
    
    @Override
    public void onDeleteLastPart() {
        if (this.mBgmPartBytesList.size() != 0) {
            this.mBgmPartBytesList.remove(this.mBgmPartBytesList.size() - 1);
            this.mBGMDeletePart = true;
            if (this.mBgmPartBytesList.size() > 0) {
                this.mEncodePcmDataSize = this.mBgmPartBytesList.get(this.mBgmPartBytesList.size() - 1);
            }
            else {
                this.mEncodePcmDataSize = 0L;
            }
        }
    }
    
    @Override
    public void onDeleteAllParts() {
        TXCLog.i("TXUGCRecord", "onDeleteAllParts");
        this.mBgmPartBytesList.clear();
        this.mBGMDeletePart = false;
        this.mEncodePcmDataSize = 0L;
    }
    
    public int startCameraSimplePreview(final TXRecordCommon.TXUGCSimpleConfig txugcSimpleConfig, final TXCloudVideoView txCloudVideoView) {
        if (txCloudVideoView == null || txugcSimpleConfig == null) {
            TXCLog.e("TXUGCRecord", "startCameraPreview: invalid param");
            return -1;
        }
        this.mConfig.u = txugcSimpleConfig.needEdit;
        this.mConfig.a = txugcSimpleConfig.videoQuality;
        this.mConfig.o = txugcSimpleConfig.isFront;
        this.mConfig.f = txugcSimpleConfig.touchFocus;
        this.mMinDuration = txugcSimpleConfig.minDuration;
        this.mMaxDuration = txugcSimpleConfig.maxDuration;
        this.startCameraPreviewInternal(txCloudVideoView, this.mConfig);
        return 0;
    }
    
    public int startCameraCustomPreview(final TXRecordCommon.TXUGCCustomConfig txugcCustomConfig, final TXCloudVideoView txCloudVideoView) {
        this.mConfig.u = txugcCustomConfig.needEdit;
        if (txCloudVideoView == null || txugcCustomConfig == null) {
            TXCLog.e("TXUGCRecord", "startCameraPreview: invalid param");
            return -1;
        }
        this.mConfig.a = -1;
        if (txugcCustomConfig.videoBitrate < 600) {
            txugcCustomConfig.videoBitrate = 600;
        }
        if (txugcCustomConfig.needEdit) {
            this.mConfig.d = 10000;
        }
        else {
            this.mConfig.d = txugcCustomConfig.videoBitrate;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bi, this.mConfig.d, "");
        if (txugcCustomConfig.videoFps < 15) {
            txugcCustomConfig.videoFps = 15;
        }
        else if (txugcCustomConfig.videoFps > 30) {
            txugcCustomConfig.videoFps = 30;
        }
        this.mConfig.c = txugcCustomConfig.videoFps;
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bj, this.mConfig.c, "");
        if (txugcCustomConfig.videoGop < 1) {
            txugcCustomConfig.videoGop = 1;
        }
        else if (txugcCustomConfig.videoGop > 10) {
            txugcCustomConfig.videoGop = 10;
        }
        if (txugcCustomConfig.needEdit) {
            this.mConfig.e = 0;
        }
        else {
            this.mConfig.e = txugcCustomConfig.videoGop;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bk, this.mConfig.e, "");
        if (txugcCustomConfig.audioSampleRate != 8000 && txugcCustomConfig.audioSampleRate != 16000 && txugcCustomConfig.audioSampleRate != 32000 && txugcCustomConfig.audioSampleRate != 44100 && txugcCustomConfig.audioSampleRate != 48000) {
            this.mConfig.t = 48000;
        }
        else {
            this.mConfig.t = txugcCustomConfig.audioSampleRate;
        }
        this.mConfig.b = txugcCustomConfig.videoResolution;
        this.mConfig.o = txugcCustomConfig.isFront;
        this.mConfig.f = txugcCustomConfig.touchFocus;
        this.mConfig.q = txugcCustomConfig.enableHighResolutionCapture;
        this.mMinDuration = txugcCustomConfig.minDuration;
        this.mMaxDuration = txugcCustomConfig.maxDuration;
        this.mConfig.u = txugcCustomConfig.needEdit;
        this.startCameraPreviewInternal(txCloudVideoView, this.mConfig);
        return 0;
    }
    
    public void setVideoResolution(final int b) {
        if (this.mRecordState != 1) {
            TXCLog.e("TXUGCRecord", "setVideoResolution err, state not init");
            return;
        }
        if (this.mTXCloudVideoView == null) {
            TXCLog.e("TXUGCRecord", "setVideoResolution, mTXCloudVideoView is null");
            return;
        }
        if (this.mConfig.b == b) {
            TXCLog.i("TXUGCRecord", "setVideoResolution, resolution not change");
            return;
        }
        this.mConfig.a = -1;
        this.mConfig.b = b;
        this.stopCameraPreview();
        this.startCameraPreviewInternal(this.mTXCloudVideoView, this.mConfig);
    }
    
    public void setVideoBitrate(final int d) {
        if (this.mRecordState != 1) {
            TXCLog.e("TXUGCRecord", "setVideoBitrate err, state not init");
            return;
        }
        this.mConfig.a = -1;
        this.mConfig.d = d;
    }
    
    public void stopCameraPreview() {
        this.mStartPreview.set(false);
        try {
            TXCLog.i("TXUGCRecord", "ugcRecord, stopCameraPreview");
            synchronized (this) {
                this.mCapturing = false;
                if (this.mCameraCapture != null) {
                    this.mCameraCapture.g();
                }
            }
            if (this.mVideoView != null) {
                this.mVideoView.b(new Runnable() {
                    @Override
                    public void run() {
                        if (TXUGCRecord.this.mVideoPreprocessor != null) {
                            TXUGCRecord.this.mVideoPreprocessor.b();
                        }
                    }
                });
                this.mVideoView.b(false);
                this.mVideoView = null;
            }
            if (this.mCustomProcessListener != null) {
                this.mCustomProcessListener.onTextureDestroyed();
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXUGCRecord", "stop camera preview failed.", ex);
        }
    }
    
    public TXUGCPartsManager getPartsManager() {
        return this.mTXUGCPartsManager;
    }
    
    public void setMute(final boolean mute) {
        TXCAudioUGCRecorder.getInstance().setMute(mute);
    }
    
    public int startRecord() {
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.e("TXUGCRecord", "API level is too low (record need 18, current is " + Build.VERSION.SDK_INT + ")");
            return -3;
        }
        if (this.mRecording) {
            TXCLog.e("TXUGCRecord", "startRecord: there is existing uncompleted record task");
            return -1;
        }
        try {
            TXCDRApi.initCrashReport(this.mContext);
            this.mCoverCurTempPath = this.mCoverPath;
            final File file = new File(this.mCoverPath);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXUGCRecord", "delete file failed.", ex);
        }
        return this.startRecordInternal(true);
    }
    
    public int startRecord(final String mVideoFilePath, final String s) {
        TXCAudioEngineJNI.nativeUseSysAudioDevice(true);
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.e("TXUGCRecord", "API level is too low (record need 18, current is " + Build.VERSION.SDK_INT + ")");
            return -3;
        }
        if (this.mRecording) {
            TXCLog.e("TXUGCRecord", "startRecord: there is existing uncompleted record task");
            return -1;
        }
        if (TextUtils.isEmpty((CharSequence)mVideoFilePath)) {
            TXCLog.e("TXUGCRecord", "startRecord: init videoRecord failed, videoFilePath is empty");
            return -2;
        }
        this.mVideoFilePath = mVideoFilePath;
        final File file = new File(mVideoFilePath);
        if (file.exists()) {
            file.delete();
        }
        this.mVideoFileTempDir = this.getDefaultDir() + File.separator + "TXUGCParts";
        final File file2 = new File(this.mVideoFileTempDir);
        if (!file2.exists()) {
            file2.mkdir();
        }
        this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", this.getTimeString());
        this.mCoverPath = s;
        this.mCoverCurTempPath = s;
        return this.startRecordInternal(true);
    }
    
    public int startRecord(final String mVideoFilePath, final String mVideoFileTempDir, final String s) {
        if (Build.VERSION.SDK_INT < 18) {
            TXCLog.e("TXUGCRecord", "API level is too low (record need 18, current is " + Build.VERSION.SDK_INT + ")");
            return -3;
        }
        if (this.mRecording) {
            TXCLog.e("TXUGCRecord", "startRecord: there is existing uncompleted record task");
            return -1;
        }
        if (TextUtils.isEmpty((CharSequence)mVideoFilePath)) {
            TXCLog.e("TXUGCRecord", "startRecord: init videoRecord failed, videoFilePath is empty");
            return -2;
        }
        this.mVideoFilePath = mVideoFilePath;
        final File file = new File(mVideoFilePath);
        if (file.exists()) {
            file.delete();
        }
        if (!TextUtils.isEmpty((CharSequence)mVideoFileTempDir)) {
            this.mVideoFileTempDir = mVideoFileTempDir;
        }
        else {
            this.mVideoFileTempDir = this.getDefaultDir() + File.separator + "TXUGCParts";
        }
        final File file2 = new File(this.mVideoFileTempDir);
        if (!file2.exists()) {
            file2.mkdir();
        }
        this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", this.getTimeString());
        this.mCoverPath = s;
        this.mCoverCurTempPath = s;
        return this.startRecordInternal(true);
    }
    
    private int startRecordInternal(final boolean b) {
        if (!this.mInitCompelete) {
            TXCLog.i("TXUGCRecord", "startRecordInternal, mInitCompelete = " + this.mInitCompelete);
            return -4;
        }
        if (!this.checkLicenseMatch()) {
            return -5;
        }
        TXCAudioUGCRecorder.getInstance().setAECType(0, this.mContext);
        TXCAudioUGCRecorder.getInstance().setListener(this);
        TXCAudioUGCRecorder.getInstance().setChannels(1);
        TXCAudioUGCRecorder.getInstance().setSampleRate(this.mConfig.t);
        if (b) {
            TXCAudioUGCRecorder.getInstance().startRecord(this.mContext);
        }
        switch (this.mRecordSpeed) {
            case 4: {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_FASTEST);
                break;
            }
            case 3: {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_FAST);
                break;
            }
            case 2: {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(1.0f);
                break;
            }
            case 1: {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_SLOW);
                break;
            }
            case 0: {
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_SLOWEST);
                break;
            }
        }
        if (this.mVideoEncoder != null) {
            this.mVideoWidth = 0;
            this.mVideoHeight = 0;
        }
        this.mUseSWEncoder = (this.mConfig.g < 1280 && this.mConfig.h < 1280);
        if (this.mMP4Muxer == null) {
            this.mMP4Muxer = new c(this.mContext, this.mUseSWEncoder ? 0 : 2);
        }
        final File file = new File(this.mVideoFileCurTempPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                TXCLog.e("TXUGCRecord", "create new file failed.", ex);
            }
        }
        TXCLog.i("TXUGCRecord", "startRecord");
        this.mMP4Muxer.a(this.mRecordSpeed);
        this.mMP4Muxer.a(this.mVideoFileCurTempPath);
        this.addAudioTrack();
        TXCDRApi.txReportDAU(this.mContext.getApplicationContext(), com.tencent.liteav.basic.datareport.a.au);
        this.mRecordState = 2;
        this.mRecording = true;
        this.mRecordStartTime = 0L;
        TXCAudioUGCRecorder.getInstance().resume();
        return 0;
    }
    
    public int stopRecord() {
        TXCLog.i("TXUGCRecord", "stopRecord called, mRecording = " + this.mRecording + ", needCompose = " + this.needCompose);
        if (this.mRecording) {
            this.needCompose = true;
            return this.stopRecordForClip();
        }
        final int quickJoiner = this.quickJoiner();
        if (quickJoiner != 0) {
            this.callbackRecordFail(quickJoiner);
        }
        this.mBgmPartBytesList.clear();
        this.mEncodePcmDataSize = 0L;
        return 0;
    }
    
    private int quickJoiner() {
        if (this.mTXFFQuickJoiner == null) {
            this.mTXFFQuickJoiner = new com.tencent.liteav.videoediter.ffmpeg.b("compose");
        }
        this.mTXFFQuickJoiner.a(new com.tencent.liteav.videoediter.ffmpeg.b.a() {
            @Override
            public void a(final com.tencent.liteav.videoediter.ffmpeg.b b, final int n, final String s) {
                if (n == 0) {
                    TXUGCRecord.this.callbackRecordSuccess();
                }
                else if (n == 1) {
                    TXUGCRecord.this.callbackRecordFail(-7);
                    TXLog.e("TXUGCRecord", "quickJoiner, quick joiner result cancel");
                }
                else if (n == -1) {
                    TXUGCRecord.this.callbackRecordFail(-8);
                    TXLog.e("TXUGCRecord", "quickJoiner, quick joiner result verify fail");
                }
                else if (n == -2) {
                    TXUGCRecord.this.callbackRecordFail(-9);
                    TXLog.e("TXUGCRecord", "quickJoiner, quick joiner result err");
                }
                b.a((b.a)null);
                b.c();
                b.d();
                TXUGCRecord.this.mTXFFQuickJoiner = null;
                TXUGCRecord.this.mRecordState = 1;
            }
            
            @Override
            public void a(final com.tencent.liteav.videoediter.ffmpeg.b b, final float n) {
                TXCLog.i("TXUGCRecord", "joiner progress " + n);
            }
        });
        if (this.mTXFFQuickJoiner.a(this.mTXUGCPartsManager.getPartsPathList()) != 0) {
            TXLog.e("TXUGCRecord", "quickJoiner, quick joiner set src path err");
            this.destroyQuickJoiner();
            return -4;
        }
        if (this.mTXFFQuickJoiner.a(this.mVideoFilePath) != 0) {
            TXLog.e("TXUGCRecord", "quickJoiner, quick joiner set dst path err, mVideoFilePath = " + this.mVideoFilePath);
            this.destroyQuickJoiner();
            return -5;
        }
        final int b = this.mTXFFQuickJoiner.b();
        TXCLog.i("TXUGCRecord", "quickJoiner start");
        if (b != 0) {
            TXLog.e("TXUGCRecord", "quickJoiner, quick joiner start fail");
            this.destroyQuickJoiner();
            return -6;
        }
        return 0;
    }
    
    private void destroyQuickJoiner() {
        if (this.mTXFFQuickJoiner != null) {
            this.mTXFFQuickJoiner.a((com.tencent.liteav.videoediter.ffmpeg.b.a)null);
            this.mTXFFQuickJoiner.c();
            this.mTXFFQuickJoiner.d();
        }
        this.mTXFFQuickJoiner = null;
        this.mRecordState = 1;
    }
    
    public void release() {
        TXCLog.i("TXUGCRecord", "release");
        TXCAudioUGCRecorder.getInstance().stopRecord();
        TXCAudioUGCRecorder.getInstance().setChangerType(0);
        TXCAudioUGCRecorder.getInstance().setReverbType(0);
        TXCAudioUGCRecorder.getInstance().setSpeedRate(1.0f);
        this.mTXCloudVideoView = null;
        this.mRecordState = 1;
        this.mRenderMode = 0;
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.a((f)null);
        }
        TXCAudioEngineJNI.nativeUseSysAudioDevice(false);
        this.mRecordStartTime = 0L;
        this.mEncodePcmDataSize = 0L;
        this.mStartMuxer = false;
        this.mRecording = false;
        this.needCompose = false;
        this.isReachedMaxDuration = false;
        this.mSnapshotRunning = false;
        this.mCapturing = false;
        this.mCurrentRecordDuration = 0L;
        this.mBGMDeletePart = false;
    }
    
    private int stopRecordForClip() {
        TXCLog.i("TXUGCRecord", "stopRecordForClip");
        if (!this.mRecording) {
            TXCLog.e("TXUGCRecord", "stopRecordForClip: there is no existing uncompleted record task");
            return -2;
        }
        int b = 0;
        TXCAudioUGCRecorder.getInstance().pause();
        if (this.mVideoEncoder != null) {
            this.mVideoEncoder.c();
        }
        this.mStartMuxer = false;
        this.stopEncoder(this.mVideoEncoder);
        this.mVideoEncoder = null;
        synchronized (this) {
            this.mRecording = false;
            if (this.mMP4Muxer != null) {
                b = this.mMP4Muxer.b();
                this.mMP4Muxer = null;
            }
        }
        final File file = new File(this.mVideoFileCurTempPath);
        if (b != 0) {
            if (file.exists()) {
                this.asyncDeleteFile(this.mVideoFileCurTempPath);
                this.mVideoFileCurTempPath = null;
            }
            TXCLog.e("TXUGCRecord", "stopRecordForClip, maybe mMP4Muxer not write data");
            if (!TextUtils.isEmpty((CharSequence)this.mBGMPath)) {
                this.mBGMDeletePart = true;
            }
            if (!this.needCompose && !this.isReachedMaxDuration) {
                return -3;
            }
        }
        if (TextUtils.isEmpty((CharSequence)this.mVideoFileCurTempPath) || !file.exists() || file.length() == 0L || this.mCurrentRecordDuration < 150L) {
            TXCLog.e("TXUGCRecord", "stopRecordForClip, file err ---> path = " + this.mVideoFileCurTempPath + ", mCurrentRecordDuration(ms) too short = " + this.mCurrentRecordDuration);
            if (!TextUtils.isEmpty((CharSequence)this.mBGMPath)) {
                this.mBGMDeletePart = true;
            }
            if (file.exists()) {
                this.asyncDeleteFile(this.mVideoFileCurTempPath);
            }
            if (!this.needCompose && !this.isReachedMaxDuration) {
                return -3;
            }
        }
        else {
            TXCLog.i("TXUGCRecord", "stopRecordForClip, tempVideoFile exist, path = " + this.mVideoFileCurTempPath + ", length = " + file.length());
            final PartInfo partInfo = new PartInfo();
            partInfo.setPath(this.mVideoFileCurTempPath);
            partInfo.setDuration(this.mCurrentRecordDuration);
            this.mTXUGCPartsManager.addClipInfo(partInfo);
            if (!TextUtils.isEmpty((CharSequence)this.mBGMPath)) {
                this.mBgmPartBytesList.add(this.mEncodePcmDataSize);
                this.mBGMDeletePart = false;
            }
            this.callbackEvent(1, null);
        }
        final String mCoverCurTempPath = this.mCoverCurTempPath;
        if (!TextUtils.isEmpty((CharSequence)this.mCoverCurTempPath)) {
            this.mCoverCurTempPath = null;
        }
        this.asyncGenCoverAndDetermineCompose(mCoverCurTempPath);
        return 0;
    }
    
    private void asyncGenCoverAndDetermineCompose(final String s) {
        AsyncTask.execute((Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    if (!TextUtils.isEmpty((CharSequence)TXUGCRecord.this.mVideoFileCurTempPath) && !TextUtils.isEmpty((CharSequence)s)) {
                        com.tencent.liteav.basic.util.f.a(TXUGCRecord.this.mVideoFileCurTempPath, s);
                    }
                }
                catch (Exception ex) {
                    TXCLog.e("TXUGCRecord", "gen video thumb failed.", ex);
                }
                TXUGCRecord.this.mMainHandler.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TXCLog.i("TXUGCRecord", "stopRecordForClip, isReachedMaxDuration = " + TXUGCRecord.this.isReachedMaxDuration + ", needCompose = " + TXUGCRecord.this.needCompose);
                        if (TXUGCRecord.this.isReachedMaxDuration) {
                            TXUGCRecord.this.mRecordRetCode = 2;
                            final int access$1300 = TXUGCRecord.this.quickJoiner();
                            if (access$1300 != 0) {
                                TXUGCRecord.this.callbackRecordFail(access$1300);
                            }
                            return;
                        }
                        if (TXUGCRecord.this.needCompose) {
                            TXUGCRecord.this.mRecordRetCode = 0;
                            TXUGCRecord.this.needCompose = false;
                            final int access$1301 = TXUGCRecord.this.quickJoiner();
                            if (access$1301 != 0) {
                                TXUGCRecord.this.callbackRecordFail(access$1301);
                            }
                        }
                    }
                });
            }
        });
    }
    
    private void callbackRecordFail(final int retCode) {
        final TXRecordCommon.TXRecordResult txRecordResult = new TXRecordCommon.TXRecordResult();
        txRecordResult.retCode = retCode;
        txRecordResult.descMsg = "record video failed";
        if (this.mVideoRecordListener != null) {
            this.mVideoRecordListener.onRecordComplete(txRecordResult);
        }
    }
    
    private void callbackRecordSuccess() {
        final TXRecordCommon.TXRecordResult txRecordResult = new TXRecordCommon.TXRecordResult();
        if (this.mTXUGCPartsManager.getDuration() < (long)this.mMinDuration) {
            this.mRecordRetCode = 1;
        }
        txRecordResult.retCode = this.mRecordRetCode;
        txRecordResult.descMsg = "record success";
        txRecordResult.videoPath = this.mVideoFilePath;
        txRecordResult.coverPath = this.mCoverPath;
        if (this.mVideoRecordListener != null) {
            this.mVideoRecordListener.onRecordComplete(txRecordResult);
        }
    }
    
    private String getDefaultDir() {
        String path = null;
        if (!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
            final File filesDir = this.mContext.getFilesDir();
            if (filesDir != null) {
                path = filesDir.getPath();
            }
            return path;
        }
        final File externalFilesDir = this.mContext.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            Log.e("TXUGCRecord", "getDefaultDir sdcardDir is null");
            return null;
        }
        final String string = externalFilesDir + File.separator + "TXUGC";
        final File file = new File(string);
        if (!file.exists()) {
            file.mkdir();
        }
        return string;
    }
    
    private String getTimeString() {
        return new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date(System.currentTimeMillis()));
    }
    
    private void asyncDeleteFile(final String s) {
        if (s == null || s.isEmpty()) {
            return;
        }
        try {
            new AsyncTask() {
                protected Object doInBackground(final Object[] array) {
                    final File file = new File(s);
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                    return null;
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Object[0]);
        }
        catch (Exception ex) {
            TXCLog.e("TXUGCRecord", "asyncDeleteFile, exception = " + ex);
        }
    }
    
    public int pauseRecord() {
        if (!this.mRecording) {
            TXCLog.e("TXUGCRecord", "pauseRecord: there is no existing uncompleted record task");
            return -2;
        }
        TXCLog.i("TXUGCRecord", "pauseRecord called");
        this.mRecordState = 3;
        return this.stopRecordForClip();
    }
    
    public int resumeRecord() {
        if (this.mRecording) {
            TXCLog.e("TXUGCRecord", "resumeRecord: there is existing uncompleted record task");
            return -1;
        }
        TXCLog.i("TXUGCRecord", "resumeRecord called");
        this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", this.getTimeString());
        final int startRecordInternal = this.startRecordInternal(false);
        this.callbackEvent(2, null);
        return startRecordInternal;
    }
    
    private void changeRecordSpeed() {
        switch (this.mRecordSpeed) {
            case 3: {
                TXCUGCBGMPlayer.getInstance().setSpeedRate(TXUGCRecord.PLAY_SPEED_FAST);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_FAST);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bl, this.mRecordSpeed, "FAST");
                break;
            }
            case 4: {
                TXCUGCBGMPlayer.getInstance().setSpeedRate(TXUGCRecord.PLAY_SPEED_FASTEST);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_FASTEST);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bb);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bl, this.mRecordSpeed, "FASTEST");
                break;
            }
            case 2: {
                TXCUGCBGMPlayer.getInstance().setSpeedRate(1.0f);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(1.0f);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bl, this.mRecordSpeed, "NORMAL");
                break;
            }
            case 1: {
                TXCUGCBGMPlayer.getInstance().setSpeedRate(TXUGCRecord.PLAY_SPEED_SLOW);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_SLOW);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bl, this.mRecordSpeed, "SLOW");
                break;
            }
            case 0: {
                TXCUGCBGMPlayer.getInstance().setSpeedRate(TXUGCRecord.PLAY_SPEED_SLOWEST);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(TXUGCRecord.ENCODE_SPEED_SLOWEST);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bl, this.mRecordSpeed, "SLOWEST");
                break;
            }
        }
    }
    
    public boolean setMicVolume(final float volume) {
        TXCAudioUGCRecorder.getInstance().setVolume(volume);
        return true;
    }
    
    public boolean switchCamera(final boolean o) {
        this.mConfig.o = o;
        if (this.mVideoView != null) {
            this.mVideoView.b(new Runnable() {
                final /* synthetic */ TXCGLSurfaceView a = TXUGCRecord.this.mVideoView;
                
                @Override
                public void run() {
                    if (TXUGCRecord.this.mCameraCapture != null && this.a != null) {
                        TXUGCRecord.this.mCameraCapture.g();
                        this.a.a(false);
                        TXUGCRecord.this.mCameraCapture.a(this.a.getSurfaceTexture());
                        if (TXUGCRecord.this.mCameraCapture.d(o) == 0) {
                            TXUGCRecord.this.mCapturing = true;
                        }
                        else {
                            TXUGCRecord.this.mCapturing = false;
                            TXUGCRecord.this.callbackEvent(3, null);
                        }
                        this.a.a(TXUGCRecord.this.mConfig.c, true);
                    }
                    TXUGCRecord.this.setWatermark(TXUGCRecord.this.mWatermarkBitmap, TXUGCRecord.this.mTxWaterMarkRect);
                }
            });
        }
        return true;
    }
    
    public void setAspectRatio(final int mDisplayType) {
        this.mDisplayType = mDisplayType;
        if (mDisplayType == 0) {
            this.mCropWidth = this.mConfig.g;
            this.mCropHeight = this.mConfig.h;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bd);
        }
        else if (mDisplayType == 1) {
            this.mCropHeight = (int)(this.mConfig.g * 4.0f / 3.0f) / 16 * 16;
            this.mCropWidth = this.mConfig.g;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bc);
        }
        else if (mDisplayType == 2) {
            this.mCropHeight = this.mConfig.g;
            this.mCropWidth = this.mConfig.g;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bb);
        }
        else if (mDisplayType == 3) {
            this.mCropHeight = this.mConfig.g * 9 / 16;
            this.mCropHeight = (this.mCropHeight + 15) / 16 * 16;
            this.mCropWidth = this.mConfig.g;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bp);
        }
        else if (mDisplayType == 4) {
            this.mCropHeight = (int)(this.mConfig.g * 3.0f / 4.0f) / 16 * 16;
            this.mCropWidth = this.mConfig.g;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bq);
        }
    }
    
    private boolean checkLicenseMatch() {
        final int a = LicenceCheck.a().a((com.tencent.liteav.basic.license.f)null, this.mContext);
        if (a != 0) {
            TXCLog.e("TXUGCRecord", "checkLicenseMatch, checkErrCode = " + a);
            return false;
        }
        if (LicenceCheck.a().b() == 2 && !this.mSmartLicenseSupport) {
            TXCLog.e("TXUGCRecord", "checkLicenseMatch, called UnSupported method!");
        }
        return true;
    }
    
    private boolean isSmartLicense() {
        LicenceCheck.a().a((com.tencent.liteav.basic.license.f)null, this.mContext);
        if (LicenceCheck.a().b() == -1) {
            TXCLog.i("TXUGCRecord", "isSmartLicense, license type is = " + LicenceCheck.a().b());
            this.mSmartLicenseSupport = false;
        }
        else if (LicenceCheck.a().b() == 2) {
            return true;
        }
        return false;
    }
    
    public void snapshot(final TXRecordCommon.ITXSnapshotListener itxSnapshotListener) {
        if (!this.checkLicenseMatch()) {
            return;
        }
        if (this.mSnapshotRunning || itxSnapshotListener == null) {
            return;
        }
        this.mSnapshotRunning = true;
        if (this.mVideoView != null) {
            this.mVideoView.a(new o() {
                @Override
                public void onTakePhotoComplete(final Bitmap bitmap) {
                    itxSnapshotListener.onSnapshot(bitmap);
                }
            });
        }
        this.mSnapshotRunning = false;
    }
    
    public void setRecordSpeed(final int mRecordSpeed) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setRecordSpeed is not supported in UGC_Smart license");
            return;
        }
        this.mRecordSpeed = mRecordSpeed;
        if (!TextUtils.isEmpty((CharSequence)this.mBGMPath)) {
            this.changeRecordSpeed();
        }
    }
    
    public void setVideoProcessListener(final VideoCustomProcessListener mCustomProcessListener) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setVideoProcessListener is not supported in UGC_Smart license");
            return;
        }
        this.mCustomProcessListener = mCustomProcessListener;
    }
    
    public void setReverb(final int reverbType) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setReverb is not supported in UGC_Smart license");
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bo, reverbType, "");
        TXCAudioUGCRecorder.getInstance().setReverbType(reverbType);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.az);
    }
    
    public void setVoiceChangerType(final int n) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setVoiceChangerType is not supported in UGC_Smart license");
            return;
        }
        this.mVoiceChangerType = n;
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bn, n, "");
        TXCAudioUGCRecorder.getInstance().setChangerType(n);
    }
    
    public int setBGM(final String mbgmPath) {
        TXCAudioEngineJNI.nativeUseSysAudioDevice(true);
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setBGM is not supported in UGC_Smart license");
            return -1;
        }
        if (TextUtils.isEmpty((CharSequence)mbgmPath)) {
            TXCLog.e("TXUGCRecord", "setBGM, path is empty");
            this.stopBGM();
            TXCUGCBGMPlayer.getInstance().setOnPlayListener(null);
            return 0;
        }
        this.mBGMPath = mbgmPath;
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bm);
        if (this.mBGMNotifyProxy == null) {
            this.mBGMNotifyProxy = new com.tencent.liteav.audio.e() {
                @Override
                public void onPlayStart() {
                    TXUGCRecord.this.mBGMPlayStop = false;
                    if (TXUGCRecord.this.mBGMNotify != null) {
                        TXUGCRecord.this.mBGMNotify.onBGMStart();
                    }
                }
                
                @Override
                public void onPlayEnd(final int n) {
                    if (TXUGCRecord.this.mBGMNotify != null) {
                        TXUGCRecord.this.mBGMNotify.onBGMComplete(0);
                    }
                    TXUGCRecord.this.mMainHandler.post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (TXUGCRecord.this.mRecording) {
                                TXCUGCBGMPlayer.getInstance().stopPlay();
                                TXCUGCBGMPlayer.getInstance().playFromTime(TXUGCRecord.this.mBGMStartTime, TXUGCRecord.this.mBGMEndTime);
                                TXCUGCBGMPlayer.getInstance().startPlay(TXUGCRecord.this.mBGMPath, true);
                            }
                        }
                    });
                }
                
                @Override
                public void onPlayProgress(final long n, final long n2) {
                    if (TXUGCRecord.this.mBGMNotify != null) {
                        TXUGCRecord.this.mBGMNotify.onBGMProgress(n, n2);
                    }
                }
            };
        }
        TXCUGCBGMPlayer.getInstance().setOnPlayListener(this.mBGMNotifyProxy);
        return (int)TXCUGCBGMPlayer.getInstance().getDurationMS(mbgmPath);
    }
    
    public void setBGMNofify(final TXRecordCommon.ITXBGMNotify mbgmNotify) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setBGMNofify is not supported in UGC_Smart license");
            return;
        }
        if (mbgmNotify == null) {
            this.mBGMNotify = null;
        }
        else {
            this.mBGMNotify = mbgmNotify;
        }
    }
    
    public boolean playBGMFromTime(final int n, final int n2) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "playBGMFromTime is not supported in UGC_Smart license");
            return false;
        }
        if (TextUtils.isEmpty((CharSequence)this.mBGMPath)) {
            TXCLog.e("TXUGCRecord", "playBGMFromTime, path is empty");
            return false;
        }
        if (n < 0 || n2 < 0) {
            TXCLog.e("TXUGCRecord", "playBGMFromTime, time is negative number");
            return false;
        }
        if (n >= n2) {
            TXCLog.e("TXUGCRecord", "playBGMFromTime, start time is bigger than end time");
            return false;
        }
        this.mBGMStartTime = n;
        this.mBGMEndTime = n2;
        this.mBGMDeletePart = false;
        this.mTXUGCPartsManager.setPartsManagerObserver(this);
        this.changeRecordSpeed();
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aA);
        if (TXCAudioUGCRecorder.getInstance().isRecording()) {
            TXCAudioUGCRecorder.getInstance().enableBGMRecord(true);
            TXCAudioUGCRecorder.getInstance().setChannels(1);
            TXCAudioUGCRecorder.getInstance().startRecord(this.mContext);
        }
        TXCUGCBGMPlayer.getInstance().playFromTime(n, n2);
        TXCUGCBGMPlayer.getInstance().startPlay(this.mBGMPath, TXCAudioUGCRecorder.getInstance().isRecording() && !TXCAudioUGCRecorder.getInstance().isPaused());
        return true;
    }
    
    public boolean stopBGM() {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "stopBGM is not supported in UGC_Smart license");
            return false;
        }
        this.mBGMPath = null;
        this.mTXUGCPartsManager.removePartsManagerObserver(this);
        TXCUGCBGMPlayer.getInstance().stopPlay();
        TXCAudioUGCRecorder.getInstance().enableBGMRecord(false);
        TXCUGCBGMPlayer.getInstance().setOnPlayListener(null);
        return true;
    }
    
    public boolean pauseBGM() {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "pauseBGM is not supported in UGC_Smart license");
            return false;
        }
        TXCUGCBGMPlayer.getInstance().pause();
        return true;
    }
    
    public boolean resumeBGM() {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "resumeBGM is not supported in UGC_Smart license");
            return false;
        }
        if (TextUtils.isEmpty((CharSequence)this.mBGMPath)) {
            TXCLog.e("TXUGCRecord", "resumeBGM, mBGMPath is empty");
            return false;
        }
        this.changeRecordSpeed();
        if (this.mBGMDeletePart) {
            long longValue = 0L;
            if (this.mBgmPartBytesList.size() > 0) {
                longValue = this.mBgmPartBytesList.get(this.mBgmPartBytesList.size() - 1);
            }
            TXCLog.i("TXUGCRecord", "resumeBGM, curBGMBytesProgress = " + longValue);
            if (this.mBGMPlayStop) {
                TXCUGCBGMPlayer.getInstance().startPlay(this.mBGMPath, TXCAudioUGCRecorder.getInstance().isRecording());
            }
            TXCAudioUGCRecorder.getInstance().clearCache();
            TXCUGCBGMPlayer.getInstance().seekBytes(longValue);
        }
        TXCUGCBGMPlayer.getInstance().resume();
        return true;
    }
    
    public boolean seekBGM(final int n, final int n2) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "seekBGM is not supported in UGC_Smart license");
            return false;
        }
        TXCUGCBGMPlayer.getInstance().playFromTime(n, n2);
        return true;
    }
    
    public boolean setBGMVolume(final float volume) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setBGMVolume is not supported in UGC_Smart license");
            return false;
        }
        TXCUGCBGMPlayer.getInstance().setVolume(volume);
        return true;
    }
    
    public int getMusicDuration(final String s) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "getMusicDuration is not supported in UGC_Smart license");
            return 0;
        }
        return (int)TXCUGCBGMPlayer.getInstance().getDurationMS(s);
    }
    
    public void setWatermark(final Bitmap mWatermarkBitmap, final TXVideoEditConstants.TXRect mTxWaterMarkRect) {
        if (this.isSmartLicense()) {
            TXCLog.e("TXUGCRecord", "setWatermark is not supported in UGC_Smart license");
            return;
        }
        if (this.mVideoPreprocessor != null && mWatermarkBitmap != null && mTxWaterMarkRect != null) {
            this.mVideoPreprocessor.a(mWatermarkBitmap, mTxWaterMarkRect.x, mTxWaterMarkRect.y, mTxWaterMarkRect.width);
        }
        this.mWatermarkBitmap = mWatermarkBitmap;
        this.mTxWaterMarkRect = mTxWaterMarkRect;
    }
    
    @Deprecated
    public void setMotionTmpl(final String motionTmpl) {
        this.mBeautyManager.setMotionTmpl(motionTmpl);
    }
    
    @Deprecated
    public void setMotionMute(final boolean motionMute) {
        this.mBeautyManager.setMotionMute(motionMute);
    }
    
    @Deprecated
    @TargetApi(18)
    public void setGreenScreenFile(final String greenScreenFile, final boolean b) {
        if (Build.VERSION.SDK_INT < 18) {
            return;
        }
        this.getBeautyManager().setGreenScreenFile(greenScreenFile);
    }
    
    @Deprecated
    public void setFaceVLevel(final int faceVLevel) {
        this.mBeautyManager.setFaceVLevel(faceVLevel);
    }
    
    @Deprecated
    public void setFaceShortLevel(final int faceShortLevel) {
        this.mBeautyManager.setFaceShortLevel(faceShortLevel);
    }
    
    @Deprecated
    public void setChinLevel(final int chinLevel) {
        this.mBeautyManager.setChinLevel(chinLevel);
    }
    
    @Deprecated
    public void setNoseSlimLevel(final int noseSlimLevel) {
        this.mBeautyManager.setNoseSlimLevel(noseSlimLevel);
    }
    
    @Deprecated
    public void setEyeScaleLevel(final float n) {
        this.mBeautyManager.setEyeScaleLevel((int)n);
    }
    
    @Deprecated
    public void setFaceScaleLevel(final float n) {
        this.mBeautyManager.setFaceSlimLevel((int)n);
    }
    
    @Deprecated
    public void setBeautyStyle(final int beautyStyle) {
        this.mBeautyManager.setBeautyStyle(beautyStyle);
    }
    
    @Deprecated
    public void setBeautyDepth(final int beautyStyle, final int beautyLevel, final int whitenessLevel, final int ruddyLevel) {
        this.mBeautyManager.setBeautyStyle(beautyStyle);
        this.mBeautyManager.setBeautyLevel(beautyLevel);
        this.mBeautyManager.setWhitenessLevel(whitenessLevel);
        this.mBeautyManager.setRuddyLevel(ruddyLevel);
    }
    
    @Deprecated
    public void setFilter(final Bitmap filter) {
        this.getBeautyManager().setFilter(filter);
    }
    
    public void setFilter(final Bitmap bitmap, final float n, final Bitmap bitmap2, final float n2, final float n3) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.a(n3, bitmap, n, bitmap2, n2);
        }
    }
    
    @Deprecated
    public void setSpecialRatio(final float n) {
        this.mSpecialRadio = n;
        this.getBeautyManager().setFilterStrength(n);
    }
    
    private void setSharpenLevel(final int n) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.f(n);
        }
    }
    
    public boolean toggleTorch(final boolean b) {
        if (this.mCameraCapture != null) {
            this.mCameraCapture.a(b);
        }
        return true;
    }
    
    public int getMaxZoom() {
        if (this.mCameraCapture == null) {
            return 0;
        }
        return this.mCameraCapture.f();
    }
    
    public boolean setZoom(final int n) {
        return this.mCameraCapture != null && this.mCameraCapture.b(n);
    }
    
    public void setFocusPosition(final float n, final float n2) {
        this.mTouchFocusRunnable.a(n, n2);
        this.mMainHandler.postDelayed((Runnable)this.mTouchFocusRunnable, 100L);
    }
    
    public void setVideoRenderMode(final int n) {
        if (n == 1) {
            this.mRenderMode = 1;
        }
        else {
            this.mRenderMode = 0;
        }
    }
    
    private int startCameraPreviewInternal(final TXCloudVideoView mtxCloudVideoView, final k k) {
        TXCLog.i("TXUGCRecord", "ugcRecord, startCameraPreviewInternal");
        this.mStartPreview.set(true);
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.removeVideoView();
            this.mTXCloudVideoView.removeFocusIndicatorView();
        }
        this.mTXCloudVideoView = mtxCloudVideoView;
        this.initConfig();
        this.calcVideoEncInfo();
        this.initModules();
        this.mInitCompelete = false;
        this.mVideoView.setRendMode(this.mRenderMode);
        this.mVideoView.setSurfaceTextureListener(this);
        return 0;
    }
    
    public void setHomeOrientation(final int mCameraOrientationReadyChange) {
        this.mCameraOrientationReadyChange = mCameraOrientationReadyChange;
        this.resetRotation();
    }
    
    public void setRenderRotation(final int mRenderRotationReadyChange) {
        this.mRenderRotationReadyChange = mRenderRotationReadyChange;
        this.resetRotation();
    }
    
    private void resetRotation() {
        if (this.mVideoView != null) {
            this.mVideoView.b(new Runnable() {
                @Override
                public void run() {
                    if (TXUGCRecord.this.mRenderRotationReadyChange != -1) {
                        TXUGCRecord.this.mConfig.s = TXUGCRecord.this.mRenderRotationReadyChange;
                        TXUGCRecord.this.mRenderRotationReadyChange = -1;
                    }
                    if (TXUGCRecord.this.mCameraOrientationReadyChange != -1) {
                        TXUGCRecord.this.mConfig.r = TXUGCRecord.this.mCameraOrientationReadyChange;
                        TXUGCRecord.this.mCameraCapture.c(TXUGCRecord.this.mConfig.r);
                        TXUGCRecord.this.mCameraOrientationReadyChange = -1;
                    }
                }
            });
        }
        else {
            this.mConfig.s = this.mRenderRotationReadyChange;
            this.mConfig.r = this.mCameraOrientationReadyChange;
        }
    }
    
    @TargetApi(16)
    private void addAudioTrack() {
        final MediaFormat a = com.tencent.liteav.basic.util.f.a(TXCAudioUGCRecorder.getInstance().getSampleRate(), TXCAudioUGCRecorder.getInstance().getChannels(), 2);
        if (this.mMP4Muxer != null) {
            this.mMP4Muxer.b(a);
        }
    }
    
    private void initModules() {
        this.mVideoView = this.mTXCloudVideoView.getGLSurfaceView();
        if (this.mVideoView == null) {
            this.mVideoView = new TXCGLSurfaceView(this.mTXCloudVideoView.getContext());
            this.mTXCloudVideoView.addVideoView(this.mVideoView);
        }
        if (this.mCameraCapture == null) {
            this.mCameraCapture = new com.tencent.liteav.capturer.a();
        }
        this.mCameraCapture.a(this.mConfig.q ? com.tencent.liteav.capturer.a.a.i : this.mCameraResolution);
        this.mCameraCapture.a(this.mConfig.c);
        if (this.mVideoPreprocessor == null) {
            this.mVideoPreprocessor = new e(this.mContext, true);
        }
        this.mVideoPreprocessor.a((f)this);
        this.mVideoEncoder = null;
        this.mBeautyManager.setPreprocessor(this.mVideoPreprocessor);
    }
    
    private boolean startCapture(final SurfaceTexture surfaceTexture) {
        synchronized (this) {
            TXCLog.i("TXUGCRecord", "startCapture, mCapturing = " + this.mCapturing + ", mCameraCapture = " + this.mCameraCapture);
            if (surfaceTexture != null && !this.mCapturing) {
                this.mCameraCapture.a(surfaceTexture);
                this.mCameraCapture.a(this.mConfig.c);
                this.mCameraCapture.c(this.mConfig.f);
                TXCLog.i("TXUGCRecord", "startCapture, setHomeOriention = " + this.mConfig.r);
                this.mCameraCapture.c(this.mConfig.r);
                if (this.mCameraCapture.d(this.mConfig.o) == 0) {
                    this.mCapturing = true;
                    if (this.mVideoView != null) {
                        this.mVideoView.setFPS(this.mConfig.c);
                        this.mVideoView.setSurfaceTextureListener(this);
                        this.mVideoView.setNotifyListener(this);
                        this.mVideoView.a(this.mConfig.c, true);
                    }
                    return true;
                }
                this.mCapturing = false;
                TXLog.e("TXUGCRecord", "startCapture fail!");
                this.onRecordError();
                return false;
            }
        }
        return false;
    }
    
    private void stopEncoder(final com.tencent.liteav.videoencoder.b b) {
        if (this.mVideoView != null) {
            this.mVideoView.b(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (b != null) {
                            b.a();
                            b.a((com.tencent.liteav.videoencoder.d)null);
                        }
                    }
                    catch (Exception ex) {
                        TXCLog.e("TXUGCRecord", "stop encoder failed.", ex);
                    }
                }
            });
        }
    }
    
    private void startEncoder(final int n, final int n2) {
        TXCLog.i("TXUGCRecord", "New encode size width = " + n + " height = " + n2 + ", mVideoView = " + this.mVideoView);
        this.stopEncoder(this.mVideoEncoder);
        this.mVideoEncoder = null;
        final EGLContext eglGetCurrentContext = ((EGL10)EGLContext.getEGL()).eglGetCurrentContext();
        this.mVideoWidth = n;
        this.mVideoHeight = n2;
        final TXSVideoEncoderParam txsVideoEncoderParam = new TXSVideoEncoderParam();
        txsVideoEncoderParam.width = n;
        txsVideoEncoderParam.height = n2;
        txsVideoEncoderParam.fps = this.mConfig.c;
        txsVideoEncoderParam.fullIFrame = this.mConfig.u;
        txsVideoEncoderParam.glContext = eglGetCurrentContext;
        txsVideoEncoderParam.annexb = true;
        txsVideoEncoderParam.appendSpsPps = false;
        if (this.mUseSWEncoder) {
            this.mVideoEncoder = new com.tencent.liteav.videoencoder.b(2);
            txsVideoEncoderParam.encoderMode = 1;
            txsVideoEncoderParam.encoderProfile = 3;
        }
        else {
            this.mVideoEncoder = new com.tencent.liteav.videoencoder.b(1);
            txsVideoEncoderParam.encoderMode = 3;
            txsVideoEncoderParam.encoderProfile = 1;
        }
        txsVideoEncoderParam.record = true;
        if (this.mConfig.u) {
            if (this.mUseSWEncoder) {
                this.mVideoEncoder.c(24000);
            }
            else {
                this.mVideoEncoder.c(15000);
            }
        }
        else {
            this.mVideoEncoder.c(this.mConfig.d);
        }
        txsVideoEncoderParam.realTime = true;
        txsVideoEncoderParam.enableBlackList = false;
        this.mVideoEncoder.a((com.tencent.liteav.videoencoder.d)this);
        this.mVideoEncoder.a(txsVideoEncoderParam);
    }
    
    private void encodeFrame(final int n, final int n2, final int n3) {
        if (this.mVideoEncoder == null || this.mVideoWidth != n2 || this.mVideoHeight != n3) {
            this.startEncoder(n2, n3);
        }
        this.mVideoEncoder.a(n, n2, n3, TXCTimeUtil.getTimeTick());
    }
    
    private void onRecordError() {
        if (this.mVideoRecordListener != null && this.mRecording) {
            this.mMainHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    TXUGCRecord.this.stopRecordForClip();
                }
            });
            TXCUGCBGMPlayer.getInstance().pause();
            this.mRecording = false;
            this.mMainHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    final TXRecordCommon.TXRecordResult txRecordResult = new TXRecordCommon.TXRecordResult();
                    txRecordResult.descMsg = "record video failed";
                    txRecordResult.retCode = -1;
                    if (TXUGCRecord.this.mVideoRecordListener != null) {
                        TXUGCRecord.this.mVideoRecordListener.onRecordComplete(txRecordResult);
                    }
                }
            });
        }
    }
    
    private void initConfig() {
        if (this.mConfig.a >= 0) {
            switch (this.mConfig.a) {
                case 0: {
                    this.mConfig.b = 0;
                    this.mConfig.g = 360;
                    this.mConfig.h = 640;
                    this.mConfig.d = 2400;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.e;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.be);
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bi, 2400, "");
                    break;
                }
                case 1: {
                    this.mConfig.b = 1;
                    this.mConfig.g = 540;
                    this.mConfig.h = 960;
                    this.mConfig.d = 6500;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.f;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf);
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bi, 6500, "");
                    break;
                }
                case 2: {
                    this.mConfig.b = 2;
                    this.mConfig.g = 720;
                    this.mConfig.h = 1280;
                    this.mConfig.d = 9600;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.g;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bg);
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bi, 9600, "");
                    break;
                }
                default: {
                    this.mConfig.b = 1;
                    this.mConfig.g = 540;
                    this.mConfig.h = 960;
                    this.mConfig.d = 6500;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.f;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf);
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bi, 6500, "");
                    break;
                }
            }
            this.mConfig.c = this.mFps;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bj, this.mFps, "");
        }
        else {
            switch (this.mConfig.b) {
                case 0: {
                    this.mConfig.g = 360;
                    this.mConfig.h = 640;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.e;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.be, 360, "360x640");
                    break;
                }
                case 1: {
                    this.mConfig.g = 540;
                    this.mConfig.h = 960;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.f;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf, 540, "540x960");
                    break;
                }
                case 2: {
                    this.mConfig.g = 720;
                    this.mConfig.h = 1280;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.g;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bg, 720, "720x1280");
                    break;
                }
                case 3: {
                    this.mConfig.g = 1080;
                    this.mConfig.h = 1920;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.i;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bh, 1080, "1080x1920");
                    break;
                }
                default: {
                    this.mConfig.g = 540;
                    this.mConfig.h = 960;
                    this.mCameraResolution = com.tencent.liteav.capturer.a.a.f;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf, 720, "720x1280");
                    break;
                }
            }
        }
        TXCLog.w("TXUGCRecord", "record:camera init record param, width:" + this.mConfig.g + ",height:" + this.mConfig.h + ",bitrate:" + this.mConfig.d + ",fps:" + this.mConfig.c);
    }
    
    private void calcVideoEncInfo() {
        if (this.mConfig.h == 0) {
            return;
        }
        final double n = this.mConfig.g / (double)this.mConfig.h;
        this.mConfig.g = (this.mConfig.g + 15) / 16 * 16;
        this.mConfig.h = (this.mConfig.h + 15) / 16 * 16;
        final double n2 = this.mConfig.g / (double)this.mConfig.h;
        final double n3 = (this.mConfig.g + 16) / (double)this.mConfig.h;
        final double n4 = (this.mConfig.g - 16) / (double)this.mConfig.h;
        this.mConfig.g = ((Math.abs(n2 - n) < Math.abs(n3 - n)) ? ((Math.abs(n2 - n) < Math.abs(n4 - n)) ? this.mConfig.g : (this.mConfig.g - 16)) : ((Math.abs(n3 - n) < Math.abs(n4 - n)) ? (this.mConfig.g + 16) : (this.mConfig.g - 16)));
    }
    
    private boolean onRecordProgress(long mCurrentRecordDuration) {
        if (this.mRecordSpeed != 2) {
            if (this.mRecordSpeed == 3) {
                mCurrentRecordDuration /= (long)TXUGCRecord.ENCODE_SPEED_FAST;
            }
            else if (this.mRecordSpeed == 4) {
                mCurrentRecordDuration /= (long)TXUGCRecord.ENCODE_SPEED_FASTEST;
            }
            else if (this.mRecordSpeed == 1) {
                mCurrentRecordDuration /= (long)TXUGCRecord.ENCODE_SPEED_SLOW;
            }
            else if (this.mRecordSpeed == 0) {
                mCurrentRecordDuration /= (long)TXUGCRecord.ENCODE_SPEED_SLOWEST;
            }
        }
        this.mCurrentRecordDuration = mCurrentRecordDuration;
        final long n = this.mTXUGCPartsManager.getDuration() + this.mCurrentRecordDuration;
        this.mMainHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordProgress(n);
                }
            }
        });
        if (n >= this.mMaxDuration) {
            this.isReachedMaxDuration = true;
            TXCLog.i("TXUGCRecord", "onRecordProgress ReachMacDuration mMaxDuration= " + this.mMaxDuration);
            this.mMainHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    TXUGCRecord.this.stopRecordForClip();
                }
            });
            return false;
        }
        this.isReachedMaxDuration = false;
        return true;
    }
    
    private int getSreenRotation() {
        if (this.mContext != null && this.mContext.getResources().getConfiguration().orientation == 2) {
            return 90;
        }
        return 0;
    }
    
    private int getRecordState() {
        final int minBufferSize = AudioRecord.getMinBufferSize(44100, 16, 2);
        final AudioRecord audioRecord = new AudioRecord(0, 44100, 16, 2, minBufferSize * 100);
        final short[] array = new short[minBufferSize];
        try {
            audioRecord.startRecording();
        }
        catch (Exception ex) {
            if (audioRecord != null) {
                audioRecord.release();
                TXCLog.e("CheckAudioPermission", "Unable to enter the initial state of audio recording");
            }
            return -1;
        }
        if (audioRecord.getRecordingState() != 3) {
            if (audioRecord != null) {
                audioRecord.stop();
                audioRecord.release();
                TXCLog.e("CheckAudioPermission", "The audio recorder is in use");
            }
            return 1;
        }
        if (audioRecord.read(array, 0, array.length) <= 0) {
            if (audioRecord != null) {
                audioRecord.stop();
                audioRecord.release();
            }
            TXCLog.e("CheckAudioPermission", "The audio recording result is empty");
            return -1;
        }
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
        }
        return 0;
    }
    
    private void callbackEvent(final int n, final Bundle bundle) {
        this.mMainHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordEvent(n, bundle);
                }
            }
        });
    }
    
    @Override
    public void onRecordRawPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4, final boolean b) {
    }
    
    @Override
    public void onRecordPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
    }
    
    @Override
    public void onRecordEncData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
        synchronized (this) {
            if (this.mMP4Muxer != null && this.mRecording) {
                this.mEncodePcmDataSize += 4096L;
                this.mMP4Muxer.a(array, 0, array.length, n * 1000L, 0);
            }
            else {
                TXCLog.e("TXUGCRecord", "onRecordEncData err!");
            }
        }
    }
    
    @Override
    public void onRecordError(final int n, final String s) {
        if (n == -1) {
            TXLog.e("TXUGCRecord", "onRecordError, audio no mic permit");
            this.onRecordError();
        }
    }
    
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture) {
        TXCLog.i("TXUGCRecord", "ugcRecord, onSurfaceTextureAvailable, surfaceTexture = " + surfaceTexture + ", mCapturing = " + this.mCapturing + ", mStartPreview = " + this.mStartPreview.get());
        if (!this.mStartPreview.get()) {
            return;
        }
        if (surfaceTexture == null) {
            return;
        }
        if (this.startCapture(surfaceTexture)) {
            if (TXCAudioUGCRecorder.getInstance().isRecording()) {
                this.mInitCompelete = true;
                TXCLog.i("TXUGCRecord", "onSurfaceTextureAvailable mInitCompelete = true");
                return;
            }
            if (this.getRecordState() == -1) {
                this.callbackEvent(4, null);
            }
        }
        else {
            this.callbackEvent(3, null);
        }
        this.mInitCompelete = true;
    }
    
    @Override
    public void onSurfaceTextureDestroy(final SurfaceTexture surfaceTexture) {
        TXCLog.i("TXUGCRecord", "ugcRecord, onSurfaceTextureDestroy, surfaceTexture = " + surfaceTexture + ", mCapturing = " + this.mCapturing);
        if (this.mCustomProcessListener != null) {
            this.mCustomProcessListener.onTextureDestroyed();
        }
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.b();
        }
        if (this.mVideoEncoder != null) {
            this.mVideoEncoder.a();
            this.mVideoEncoder.a((com.tencent.liteav.videoencoder.d)null);
            this.mVideoEncoder = null;
        }
    }
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
    }
    
    @Override
    public int willAddWatermark(int onTextureCustomProcess, final int n, final int n2) {
        if (this.mCustomProcessListener != null) {
            onTextureCustomProcess = this.mCustomProcessListener.onTextureCustomProcess(onTextureCustomProcess, n, n2);
        }
        if (this.mVideoView != null) {
            this.mVideoView.a(onTextureCustomProcess, false, this.mConfig.s, n, n2, this.mCameraCapture.i());
        }
        return onTextureCustomProcess;
    }
    
    @Override
    public void didProcessFrame(final int n, final int n2, final int n3, final long n4) {
        if (this.mRecording) {
            this.encodeFrame(n, n2, n3);
        }
    }
    
    @Override
    public void didProcessFrame(final byte[] array, final int n, final int n2, final int n3, final long n4) {
    }
    
    public void didDetectFacePoints(final float[] array) {
        if (this.mCustomProcessListener != null) {
            this.mCustomProcessListener.onDetectFacePoints(array);
        }
    }
    
    @Override
    public void onEncodeNAL(final TXSNALPacket txsnalPacket, final int n) {
        if (n == 0) {
            synchronized (this) {
                if (this.mMP4Muxer == null) {
                    return;
                }
                if (txsnalPacket != null && txsnalPacket.nalData != null) {
                    if (this.mStartMuxer) {
                        this.recordVideoData(txsnalPacket, txsnalPacket.nalData);
                    }
                    else if (txsnalPacket.nalType == 0) {
                        final MediaFormat a = com.tencent.liteav.basic.util.f.a(txsnalPacket.nalData, this.mVideoWidth, this.mVideoHeight);
                        if (a != null) {
                            this.mMP4Muxer.a(a);
                            this.mMP4Muxer.a();
                            this.mStartMuxer = true;
                            this.mRecordStartTime = 0L;
                            TXLog.i("TXUGCRecord", "onEncodeNAL, mMP4Muxer.start(), mStartMuxer = true");
                        }
                        this.recordVideoData(txsnalPacket, txsnalPacket.nalData);
                    }
                }
            }
        }
        else {
            TXCLog.e("TXUGCRecord", "onEncodeNAL error: " + n);
        }
    }
    
    @Override
    public void onEncodeFormat(final MediaFormat mediaFormat) {
        synchronized (this) {
            TXCLog.i("TXUGCRecord", "onEncodeFormat: " + mediaFormat.toString());
            if (this.mMP4Muxer != null) {
                this.mMP4Muxer.a(mediaFormat);
                if (!this.mStartMuxer) {
                    this.mMP4Muxer.a();
                    this.mStartMuxer = true;
                    TXCLog.i("TXUGCRecord", "onEncodeFormat, mMP4Muxer.start(), mStartMuxer = true");
                }
            }
        }
    }
    
    @Override
    public void onEncodeFinished(final int n, final long n2, final long n3) {
    }
    
    @Override
    public void onRestartEncoder(final int n) {
    }
    
    @Override
    public void onEncodeDataIn(final int n) {
    }
    
    private void recordVideoData(final TXSNALPacket txsnalPacket, final byte[] array) {
        if (this.mRecordStartTime == 0L) {
            this.mRecordStartTime = txsnalPacket.pts;
        }
        int flags = 0;
        if (txsnalPacket.info == null) {
            if (txsnalPacket.nalType == 0) {
                flags = 1;
            }
        }
        else {
            flags = txsnalPacket.info.flags;
        }
        if (this.onRecordProgress(txsnalPacket.pts - this.mRecordStartTime)) {
            this.mMP4Muxer.b(array, 0, array.length, txsnalPacket.pts * 1000L, flags);
        }
    }
    
    @Override
    public int onTextureProcess(final int n, final float[] array) {
        if (this.mVideoPreprocessor != null) {
            int n2 = this.mConfig.g;
            int n3 = this.mConfig.h;
            int n4 = this.mCropWidth;
            int n5 = this.mCropHeight;
            if (this.mConfig.r == 2 || this.mConfig.r == 0) {
                n2 = this.mConfig.h;
                n3 = this.mConfig.g;
                n4 = this.mCropHeight;
                n5 = this.mCropWidth;
            }
            if (this.mDisplayType != 0) {
                this.mVideoPreprocessor.a(com.tencent.liteav.basic.util.f.a(this.mCameraCapture.j(), this.mCameraCapture.k(), this.mCropHeight, this.mCropWidth));
                this.mVideoPreprocessor.a(n4, n5);
                this.mVideoView.setRendMode(1);
            }
            else {
                this.mVideoPreprocessor.a(com.tencent.liteav.basic.util.f.a(this.mCameraCapture.j(), this.mCameraCapture.k(), this.mConfig.h, this.mConfig.g));
                this.mVideoPreprocessor.a(n2, n3);
                this.mVideoView.setRendMode(this.mRenderMode);
            }
            this.mVideoPreprocessor.b(false);
            this.mVideoPreprocessor.a(array);
            this.mVideoPreprocessor.a(this.mConfig.r);
            this.mVideoPreprocessor.a(n, this.mCameraCapture.j(), this.mCameraCapture.k(), this.mCameraCapture.h(), 4, 0);
        }
        return 0;
    }
    
    @Override
    public void onBufferProcess(final byte[] array, final float[] array2) {
    }
    
    public TXBeautyManager getBeautyManager() {
        return this.mBeautyManager;
    }
    
    static {
        TXUGCRecord.PLAY_SPEED_FASTEST = 0.5f;
        TXUGCRecord.PLAY_SPEED_FAST = 0.8f;
        TXUGCRecord.PLAY_SPEED_SLOW = 1.25f;
        TXUGCRecord.PLAY_SPEED_SLOWEST = 2.0f;
        TXUGCRecord.ENCODE_SPEED_FASTEST = 2.0f;
        TXUGCRecord.ENCODE_SPEED_FAST = 1.25f;
        TXUGCRecord.ENCODE_SPEED_SLOW = 0.8f;
        TXUGCRecord.ENCODE_SPEED_SLOWEST = 0.5f;
    }
    
    private class a implements Runnable
    {
        private float b;
        private float c;
        
        public void a(final float b, final float c) {
            this.b = b;
            this.c = c;
        }
        
        @Override
        public void run() {
            if (TXUGCRecord.this.mTXCloudVideoView == null) {
                return;
            }
            if (TXUGCRecord.this.mCameraCapture != null && TXUGCRecord.this.mConfig.f) {
                TXUGCRecord.this.mCameraCapture.a(this.b / TXUGCRecord.this.mTXCloudVideoView.getWidth(), this.c / TXUGCRecord.this.mTXCloudVideoView.getHeight());
            }
            if (TXUGCRecord.this.mConfig.f) {
                TXUGCRecord.this.mTXCloudVideoView.onTouchFocus((int)this.b, (int)this.c);
            }
        }
    }
    
    public interface VideoCustomProcessListener
    {
        int onTextureCustomProcess(final int p0, final int p1, final int p2);
        
        void onDetectFacePoints(final float[] p0);
        
        void onTextureDestroyed();
    }
}
