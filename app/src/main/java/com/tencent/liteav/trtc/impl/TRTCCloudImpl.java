//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tencent.liteav.trtc.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.WindowManager.LayoutParams;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.basic.util.MainWaitHandler;
import com.tencent.liteav.i;
import com.tencent.liteav.j;
import com.tencent.liteav.t;
import com.tencent.liteav.TXCRenderAndDec.b;
import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.audio.TXAudioEffectManagerImpl;
import com.tencent.liteav.audio.TXCAudioEncoderConfig;
import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.audio.TXCLiveBGMPlayer;
import com.tencent.liteav.audio.TXCSoundEffectPlayer;
import com.tencent.liteav.audio.a;
import com.tencent.liteav.audio.c;
import com.tencent.liteav.audio.d;
import com.tencent.liteav.audio.e;
import com.tencent.liteav.audio.TXAudioEffectManager.TXVoiceChangerType;
import com.tencent.liteav.audio.TXAudioEffectManager.TXVoiceReverbType;
import com.tencent.liteav.audio.impl.TXCAudioEngineJNI;
import com.tencent.liteav.basic.c.o;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCEventRecorderProxy;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.f;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.liteav.trtc.impl.TRTCRoomInfo.RenderInfo;
import com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction;
import com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserInfo;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudListener;
import com.tencent.trtc.TRTCStatistics;
import com.tencent.trtc.TRTCSubCloud;
import com.tencent.trtc.TRTCCloudDef.TRTCAudioEffectParam;
import com.tencent.trtc.TRTCCloudDef.TRTCAudioFrame;
import com.tencent.trtc.TRTCCloudDef.TRTCAudioRecordingParams;
import com.tencent.trtc.TRTCCloudDef.TRTCNetworkQosParam;
import com.tencent.trtc.TRTCCloudDef.TRTCParams;
import com.tencent.trtc.TRTCCloudDef.TRTCPublishCDNParam;
import com.tencent.trtc.TRTCCloudDef.TRTCQuality;
import com.tencent.trtc.TRTCCloudDef.TRTCScreenShareParams;
import com.tencent.trtc.TRTCCloudDef.TRTCSpeedTestResult;
import com.tencent.trtc.TRTCCloudDef.TRTCTexture;
import com.tencent.trtc.TRTCCloudDef.TRTCTranscodingConfig;
import com.tencent.trtc.TRTCCloudDef.TRTCVideoEncParam;
import com.tencent.trtc.TRTCCloudDef.TRTCVideoFrame;
import com.tencent.trtc.TRTCCloudDef.TRTCVolumeInfo;
import com.tencent.trtc.TRTCCloudListener.TRTCAudioFrameListener;
import com.tencent.trtc.TRTCCloudListener.TRTCSnapshotListener;
import com.tencent.trtc.TRTCCloudListener.TRTCVideoRenderListener;
import com.tencent.trtc.TRTCStatistics.TRTCLocalStatistics;
import com.tencent.trtc.TRTCStatistics.TRTCRemoteStatistics;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.Map.Entry;
import javax.microedition.khronos.egl.EGLContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TRTCCloudImpl extends TRTCCloud implements Callback, b, a, com.tencent.liteav.audio.b, c, d, e, com.tencent.liteav.basic.b.b, CaptureAndEnc.a, com.tencent.liteav.screencapture.a.a, t {
    private static final String TAG = "TRTCCloudImpl";
    private static final int STATE_INTERVAL = 2000;
    protected static final int ROOM_STATE_OUT = 0;
    protected static final int ROOM_STATE_ENTRING = 1;
    private static final int ROOM_STATE_IN = 2;
    private static final int MIN_VOLUME_EVALUATION_INTERVAL_MS = 100;
    private static final String KEY_CONFIG_FPS = "config_fps";
    private static final String KEY_CONFIG_GOP = "config_gop";
    private static final String KEY_CONFIG_ADJUST_RESOLUTION = "config_adjust_resolution";
    private static final int DEFAULT_GOP_FOR_SCREEN_CAPTURE = 3;
    private static final int DEFAULT_FPS_FOR_SCREEN_CAPTURE = 10;
    private static final int RECV_MODE_UNKNOWN = 0;
    private static final int RECV_MODE_AUTO_AUDIO_VIDEO = 1;
    private static final int RECV_MODE_AUTO_AUDIO_ONLY = 2;
    private static final int RECV_MODE_AUTO_VIDEO_ONLY = 3;
    private static final int RECV_MODE_MANUAL = 4;
    final TXVoiceReverbType[] reverbTypes;
    final TXVoiceChangerType[] voiceChangerTypes;
    protected long mNativeRtcContext;
    protected Object mNativeLock;
    private TRTCCloudImpl.VideoSourceType mVideoSourceType;
    protected int mRoomState;
    protected boolean mIsExitOldRoom;
    private boolean mIsAudioCapturing;
    protected TRTCRoomInfo mRoomInfo;
    protected TRTCCloudListener mTRTCListener;
    protected TRTCAudioFrameListener mAudioFrameListener;
    protected i mConfig;
    protected CaptureAndEnc mCaptureAndEnc;
    protected Context mContext;
    private MainWaitHandler mMainHandler;
    private Handler mListenerHandler;
    protected Handler mSDKHandler;
    private long mLastSendMsgTimeMs;
    private int mSendMsgCount;
    private int mSendMsgSize;
    protected long mLastStateTimeMs;
    protected int mPriorStreamType;
    private boolean mEnableSmallStream;
    private int mVideoRenderMirror;
    private boolean mCustomRemoteRender;
    protected HashMap<String, TRTCCloudImpl.RenderListenerAdapter> mRenderListenerMap;
    protected int mAudioVolumeEvalInterval;
    private final TRTCVideoEncParam mSmallEncParam;
    private TRTCCloudImpl.DisplayOrientationDetector mOrientationEventListener;
    private Display mDisplay;
    private int mSensorMode;
    protected int mAppScene;
    private int mQosPreference;
    private int mQosMode;
    protected boolean mEnableEosMode;
    private int mCodecType;
    private boolean mEnableSoftAEC;
    private boolean mEnableSoftANS;
    private boolean mEnableSoftAGC;
    private int mSoftAECLevel;
    private int mSoftANSLevel;
    private int mSoftAGCLevel;
    private int mAudioCaptureVolume;
    private int mAudioPlayoutVolume;
    private TRTCCustomTextureUtil mCustomVideoUtil;
    private boolean mEnableCustomAudioCapture;
    protected int mCurrentRole;
    protected int mTargetRole;
    private long mLastCaptureCalculateTS;
    private long mCaptureFrameCount;
    private long mLastCaptureFrameCount;
    protected int mPerformanceMode;
    private int mCurrentOrientation;
    protected int mRecvMode;
    private static TRTCCloudImpl sInstance = null;
    private View mFloatingWindow;
    private boolean mOverrideFPSFromUser;
    private final Bundle mLatestParamsOfBigEncoder;
    private final Bundle mLatestParamsOfSmallEncoder;
    private int mFramework;
    private Set<Integer> mStreamTypes;
    private TRTCVideoServerConfig mVideoServerConfig;
    private BGMNotify mBGMNotify;
    private long mLastLogSEIMsgTs;
    private long mRecvSEIMsgCountInPeriod;
    private long mLastLogCustomCmdMsgTs;
    private long mRecvCustomCmdMsgCountInPeriod;
    private com.tencent.liteav.basic.b.a mCallback;
    protected ArrayList<WeakReference<TRTCCloudImpl>> mSubClouds;
    protected HashMap<Integer, TRTCCloudImpl> mCurrentPublishClouds;
    private TRTCCloudImpl.VolumeLevelNotifyTask mVolumeLevelNotifyTask;
    protected int mDebugType;
    private TRTCCloudImpl.StatusTask mStatusNotifyTask;
    private int mNetType;
    private int mBackground;

    public static TRTCCloud sharedInstance(Context var0) {
        Class var1 = TRTCCloudImpl.class;
        synchronized(TRTCCloudImpl.class) {
            if (sInstance == null) {
                sInstance = new TRTCCloudImpl(var0);
            }

            return sInstance;
        }
    }

    public static void destroySharedInstance() {
        Class var0 = TRTCCloudImpl.class;
        synchronized(TRTCCloudImpl.class) {
            if (sInstance != null) {
                TXCLog.i("TRTCCloudImpl", "trtc_api destroy instance self:" + sInstance.hashCode());
                sInstance.destroy();
                sInstance = null;
            }

        }
    }

    protected TRTCCloudImpl(Context var1) {
        this.reverbTypes = new TXVoiceReverbType[]{TXVoiceReverbType.TXLiveVoiceReverbType_0, TXVoiceReverbType.TXLiveVoiceReverbType_1, TXVoiceReverbType.TXLiveVoiceReverbType_2, TXVoiceReverbType.TXLiveVoiceReverbType_3, TXVoiceReverbType.TXLiveVoiceReverbType_4, TXVoiceReverbType.TXLiveVoiceReverbType_5, TXVoiceReverbType.TXLiveVoiceReverbType_6, TXVoiceReverbType.TXLiveVoiceReverbType_7};
        this.voiceChangerTypes = new TXVoiceChangerType[]{TXVoiceChangerType.TXLiveVoiceChangerType_0, TXVoiceChangerType.TXLiveVoiceChangerType_1, TXVoiceChangerType.TXLiveVoiceChangerType_2, TXVoiceChangerType.TXLiveVoiceChangerType_3, TXVoiceChangerType.TXLiveVoiceChangerType_4, TXVoiceChangerType.TXLiveVoiceChangerType_5, TXVoiceChangerType.TXLiveVoiceChangerType_6, TXVoiceChangerType.TXLiveVoiceChangerType_7, TXVoiceChangerType.TXLiveVoiceChangerType_8, TXVoiceChangerType.TXLiveVoiceChangerType_9, TXVoiceChangerType.TXLiveVoiceChangerType_10, TXVoiceChangerType.TXLiveVoiceChangerType_11};
        this.mNativeLock = new Object();
        this.mAudioFrameListener = null;
        this.mPriorStreamType = 2;
        this.mEnableSmallStream = false;
        this.mVideoRenderMirror = 0;
        this.mCustomRemoteRender = false;
        this.mAudioVolumeEvalInterval = 0;
        this.mSmallEncParam = new TRTCVideoEncParam();
        this.mQosMode = 1;
        this.mEnableEosMode = false;
        this.mCodecType = 2;
        this.mEnableSoftAEC = true;
        this.mEnableSoftANS = false;
        this.mEnableSoftAGC = false;
        this.mSoftAECLevel = 100;
        this.mSoftANSLevel = 100;
        this.mSoftAGCLevel = 100;
        this.mAudioCaptureVolume = 100;
        this.mAudioPlayoutVolume = 100;
        this.mCustomVideoUtil = null;
        this.mEnableCustomAudioCapture = false;
        this.mCurrentRole = 20;
        this.mTargetRole = 20;
        this.mLastCaptureCalculateTS = 0L;
        this.mCaptureFrameCount = 0L;
        this.mLastCaptureFrameCount = 0L;
        this.mPerformanceMode = 0;
        this.mCurrentOrientation = -1;
        this.mFloatingWindow = null;
        this.mOverrideFPSFromUser = false;
        this.mLatestParamsOfBigEncoder = new Bundle();
        this.mLatestParamsOfSmallEncoder = new Bundle();
        this.mFramework = 1;
        this.mCallback = new NamelessClass_1();
        this.mSubClouds = new ArrayList();
        this.mCurrentPublishClouds = new HashMap();
        this.mVolumeLevelNotifyTask = null;
        this.mDebugType = 0;
        this.mStatusNotifyTask = null;
        this.mNetType = -1;
        this.mBackground = -1;
        this.init(var1, (Handler)null);
        TXCCommonUtil.setAppContext(this.mContext);
        TXCLog.init();
        TRTCAudioServerConfig var2 = TRTCAudioServerConfig.loadFromSharedPreferences(var1);
        TXCLog.i("TRTCCloudImpl", "audio config from shared preference: %s", new Object[]{var2});
        TXCAudioEngine.CreateInstanceWithoutInitDevice(this.mContext, buildTRAEConfig(var2.enableOpenSL));
        TXCAudioEngine.getInstance().clean();
        TXCAudioEngine.getInstance().setAudioCaptureDataListener(this);
        TXCAudioEngine.getInstance().addEventCallback(new WeakReference(this.mCallback));
        TXCAudioEngine.getInstance().enableAutoRestartDevice(var2.enableAutoRestartDevice);
        TXCAudioEngine.getInstance().setMaxSelectedPlayStreams(var2.maxSelectedPlayStreams);
        TXCAudioEngineJNI.nativeSetAudioPlayoutTunnelEnabled(true);
        this.mCaptureAndEnc = new CaptureAndEnc(var1);
        this.mCaptureAndEnc.j(2);
        this.mCaptureAndEnc.a(this.mConfig);
        this.mCaptureAndEnc.i(true);
        this.mCaptureAndEnc.g(true);
        this.mCaptureAndEnc.a(this);
        this.mCaptureAndEnc.a(this);
        this.mCaptureAndEnc.setID("18446744073709551615");
        this.mCaptureAndEnc.h(true);
        TXCKeyPointReportProxy.a(this.mContext);
        this.apiLog("reset audio volume");
        this.setAudioCaptureVolume(100);
        this.setAudioPlayoutVolume(100);
        TXCSoundEffectPlayer.getInstance().setSoundEffectListener(this);
    }

    protected TRTCCloudImpl(Context var1, Handler var2) {
        this.reverbTypes = new TXVoiceReverbType[]{TXVoiceReverbType.TXLiveVoiceReverbType_0, TXVoiceReverbType.TXLiveVoiceReverbType_1, TXVoiceReverbType.TXLiveVoiceReverbType_2, TXVoiceReverbType.TXLiveVoiceReverbType_3, TXVoiceReverbType.TXLiveVoiceReverbType_4, TXVoiceReverbType.TXLiveVoiceReverbType_5, TXVoiceReverbType.TXLiveVoiceReverbType_6, TXVoiceReverbType.TXLiveVoiceReverbType_7};
        this.voiceChangerTypes = new TXVoiceChangerType[]{TXVoiceChangerType.TXLiveVoiceChangerType_0, TXVoiceChangerType.TXLiveVoiceChangerType_1, TXVoiceChangerType.TXLiveVoiceChangerType_2, TXVoiceChangerType.TXLiveVoiceChangerType_3, TXVoiceChangerType.TXLiveVoiceChangerType_4, TXVoiceChangerType.TXLiveVoiceChangerType_5, TXVoiceChangerType.TXLiveVoiceChangerType_6, TXVoiceChangerType.TXLiveVoiceChangerType_7, TXVoiceChangerType.TXLiveVoiceChangerType_8, TXVoiceChangerType.TXLiveVoiceChangerType_9, TXVoiceChangerType.TXLiveVoiceChangerType_10, TXVoiceChangerType.TXLiveVoiceChangerType_11};
        this.mNativeLock = new Object();
        this.mAudioFrameListener = null;
        this.mPriorStreamType = 2;
        this.mEnableSmallStream = false;
        this.mVideoRenderMirror = 0;
        this.mCustomRemoteRender = false;
        this.mAudioVolumeEvalInterval = 0;
        this.mSmallEncParam = new TRTCVideoEncParam();
        this.mQosMode = 1;
        this.mEnableEosMode = false;
        this.mCodecType = 2;
        this.mEnableSoftAEC = true;
        this.mEnableSoftANS = false;
        this.mEnableSoftAGC = false;
        this.mSoftAECLevel = 100;
        this.mSoftANSLevel = 100;
        this.mSoftAGCLevel = 100;
        this.mAudioCaptureVolume = 100;
        this.mAudioPlayoutVolume = 100;
        this.mCustomVideoUtil = null;
        this.mEnableCustomAudioCapture = false;
        this.mCurrentRole = 20;
        this.mTargetRole = 20;
        this.mLastCaptureCalculateTS = 0L;
        this.mCaptureFrameCount = 0L;
        this.mLastCaptureFrameCount = 0L;
        this.mPerformanceMode = 0;
        this.mCurrentOrientation = -1;
        this.mFloatingWindow = null;
        this.mOverrideFPSFromUser = false;
        this.mLatestParamsOfBigEncoder = new Bundle();
        this.mLatestParamsOfSmallEncoder = new Bundle();
        this.mFramework = 1;

        class NamelessClass_1 implements com.tencent.liteav.basic.b.a {
            NamelessClass_1() {
            }

            public void onEvent(String var1, int var2, String var3, String var4) {
                TXCLog.i("TRTCCloudImpl", "onEvent => id:" + var1 + " code:" + var2 + " msg:" + var3 + " params:" + var4);
                TRTCCloudListener var5 = TRTCCloudImpl.this.mTRTCListener;
                if (var5 != null) {
                    Bundle var6 = new Bundle();
                    var6.putString("EVT_USERID", var1);
                    var6.putInt("EVT_ID", var2);
                    var6.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (var3 != null) {
                        var6.putCharSequence("EVT_MSG", var3 + (var4 != null ? var4 : ""));
                    }

                    TRTCCloudImpl.this.onNotifyEvent(var2, var6);
                }

                Monitor.a(2, var2, var3, var4, 0, 0);
            }

            public void onError(String var1, int var2, String var3, String var4) {
                TXCLog.e("TRTCCloudImpl", "onError => id:" + var1 + " code:" + var2 + " msg:" + var3 + " params:" + var4);
                TRTCCloudListener var5 = TRTCCloudImpl.this.mTRTCListener;
                if (var5 != null) {
                    Bundle var6 = new Bundle();
                    var6.putString("EVT_USERID", var1);
                    var6.putInt("EVT_ID", var2);
                    var6.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (var3 != null) {
                        var6.putCharSequence("EVT_MSG", var3 + (var4 != null ? var4 : ""));
                    }

                    TRTCCloudImpl.this.onNotifyEvent(var2, var6);
                }

                Monitor.a(3, var2, var3, var4, 0, 0);
            }
        }

        this.mCallback = new NamelessClass_1();
        this.mSubClouds = new ArrayList();
        this.mCurrentPublishClouds = new HashMap();
        this.mVolumeLevelNotifyTask = null;
        this.mDebugType = 0;
        this.mStatusNotifyTask = null;
        this.mNetType = -1;
        this.mBackground = -1;
        this.init(var1, var2);
        this.mCurrentRole = 21;
        this.mTargetRole = 21;
    }

    private void init(Context var1, Handler var2) {
        this.mCurrentPublishClouds.put(2, this);
        this.mCurrentPublishClouds.put(3, this);
        this.mCurrentPublishClouds.put(7, this);
        this.mCurrentPublishClouds.put(1, this);
        this.mContext = var1.getApplicationContext();
        this.mConfig = new i();
        this.mConfig.k = com.tencent.liteav.basic.a.c.e;
        this.mConfig.X = 90;
        this.mConfig.j = 0;
        this.mConfig.P = true;
        this.mConfig.h = 15;
        this.mConfig.K = false;
        this.mConfig.T = false;
        this.mConfig.U = false;
        this.mConfig.a = 368;
        this.mConfig.b = 640;
        this.mConfig.c = 750;
        this.mConfig.e = 0;
        this.mConfig.W = false;
        this.mRoomInfo = new TRTCRoomInfo();
        this.mRoomInfo.bigEncSize.a = 368;
        this.mRoomInfo.bigEncSize.b = 640;
        this.mMainHandler = new MainWaitHandler(var1.getMainLooper());
        this.mListenerHandler = new Handler(var1.getMainLooper());
        if (var2 != null) {
            this.mSDKHandler = var2;
        } else {
            HandlerThread var3 = new HandlerThread("TRTCCloudApi");
            var3.start();
            this.mSDKHandler = new Handler(var3.getLooper());
        }

        this.mStatusNotifyTask = new TRTCCloudImpl.StatusTask(this);
        this.mLastSendMsgTimeMs = 0L;
        this.mSendMsgCount = 0;
        this.mSendMsgSize = 0;
        this.mSensorMode = 2;
        this.mAppScene = 0;
        this.mQosPreference = 2;
        this.mQosMode = 1;
        this.mOrientationEventListener = new TRTCCloudImpl.DisplayOrientationDetector(this.mContext, this);
        this.mDisplay = ((WindowManager)var1.getSystemService("window")).getDefaultDisplay();
        this.mRenderListenerMap = new HashMap();
        this.mStreamTypes = new HashSet();
        synchronized(this.mNativeLock) {
            int[] var4 = TXCCommonUtil.getSDKVersion();
            int var5 = var4.length >= 1 ? var4[0] : 0;
            int var6 = var4.length >= 2 ? var4[1] : 0;
            int var7 = var4.length >= 3 ? var4[2] : 0;
            this.mNativeRtcContext = this.nativeCreateContext(var5, var6, var7);
        }

        this.apiLog("trtc cloud create");
        this.mRoomState = 0;
        this.mVideoSourceType = TRTCCloudImpl.VideoSourceType.NONE;
        this.mIsAudioCapturing = false;
        this.mCurrentRole = 20;
        this.mTargetRole = 20;
        this.mRecvMode = 1;
        this.mLatestParamsOfBigEncoder.putInt("config_gop", this.mConfig.i);
        this.mLatestParamsOfSmallEncoder.putInt("config_gop", this.mConfig.i);
        this.identifyTRTCFrameworkType();
        this.mVideoServerConfig = TRTCVideoServerConfig.loadFromSharedPreferences(var1);
    }

    public void destroy() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TXCAudioEngineJNI.nativeSetAudioPlayoutTunnelEnabled(false);
                synchronized(TRTCCloudImpl.this.mNativeLock) {
                    if (TRTCCloudImpl.this.mNativeRtcContext != 0L) {
                        TRTCCloudImpl.this.apiLog("destroy context");
                        TRTCCloudImpl.this.nativeDestroyContext(TRTCCloudImpl.this.mNativeRtcContext);
                    }

                    TRTCCloudImpl.this.mNativeRtcContext = 0L;
                }

                TRTCCloudImpl.this.mTRTCListener = null;
                TRTCCloudImpl.this.mAudioFrameListener = null;
                TRTCCloudImpl.this.setAudioCaptureVolume(100);
                TRTCCloudImpl.this.setAudioPlayoutVolume(100);
                TXCSoundEffectPlayer.getInstance().setSoundEffectListener((a)null);
                TXCAudioEngine.getInstance().clean();
                synchronized(TRTCCloudImpl.this.mCurrentPublishClouds) {
                    TRTCCloudImpl.this.mCurrentPublishClouds.clear();
                }

                Iterator var1 = TRTCCloudImpl.this.mSubClouds.iterator();

                while(var1.hasNext()) {
                    TRTCCloudImpl var2 = (TRTCCloudImpl)((WeakReference)var1.next()).get();
                    if (var2 != null) {
                        var2.destroy();
                    }
                }

                TRTCCloudImpl.this.mSubClouds.clear();
            }
        });
    }

    public void setListener(final TRTCCloudListener var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setListener " + var1);
                TRTCCloudImpl.this.mTRTCListener = var1;
            }
        });
    }

    public void setListenerHandler(Handler var1) {
        this.apiLog("setListenerHandler " + var1);
        if (var1 == null) {
            this.mListenerHandler = new Handler(Looper.getMainLooper());
        } else {
            this.mListenerHandler = var1;
        }

        this.runOnSDKThread(new Runnable() {
            public void run() {
                Iterator var1 = TRTCCloudImpl.this.mSubClouds.iterator();

                while(var1.hasNext()) {
                    TRTCCloudImpl var2 = (TRTCCloudImpl)((WeakReference)var1.next()).get();
                    if (var2 != null) {
                        var2.setListenerHandler(TRTCCloudImpl.this.mListenerHandler);
                    } else {
                        var1.remove();
                    }
                }

            }
        });
    }

    void extractBizInfo(JSONObject var1, String var2, StringBuilder var3) {
        if (var2.equals("strGroupId")) {
            var3.append(var1.optString("strGroupId").toString());
            var1.remove("strGroupId");
            var1.remove("Role");
        }

        this.apiLog("extractBizInfo: key" + var2 + " value:" + var3.toString());
    }

    private void identifyTRTCFrameworkType() {
        try {
            StackTraceElement[] var1 = Thread.currentThread().getStackTrace();
            String var2 = "";

            for(int var3 = 0; var3 < var1.length; ++var3) {
                var2 = var1[var3].getClassName();
                if (var2.contains("TRTCMeetingImpl") || var2.contains("TRTCLiveRoomImpl") || var2.contains("TRTCAudioCallImpl") || var2.contains("TRTCVideoCallImpl") || var2.contains("TRTCVoiceRoomImpl") || var2.contains("TRTCAVCallImpl")) {
                    TXCLog.i("TRTCCloudImpl", "identifyTRTCFrameworkType callName:" + var2);
                    this.mFramework = 5;
                    break;
                }

                if (var2.contains("WXTRTCCloud")) {
                    TXCLog.i("TRTCCloudImpl", "identifyTRTCFrameworkType callName:" + var2);
                    this.mFramework = 3;
                    break;
                }
            }
        } catch (Exception var4) {
            TXCLog.e("TRTCCloudImpl", "identifyTRTCFrameworkType catch exception:" + var4.getCause());
        }

    }

    public void enterRoom(TRTCParams var1, final int var2) {
        if (var1 == null) {
            this.apiLog("enter room, param nil!");
            this.onEnterRoom(-3316, "enter room param null");
        } else {
            final TRTCParams var3 = new TRTCParams(var1);
            if (var3.sdkAppId != 0 && !TextUtils.isEmpty(var3.userId) && !TextUtils.isEmpty(var3.userSig)) {
                final long var4 = (long)var3.roomId & 4294967295L;
                if (var4 == 0L) {
                    this.apiLog("enter room, room id " + var4 + " error");
                    this.onEnterRoom(-3318, "room id invalid.");
                } else {
                    final String var6 = "";
                    final String var7 = var3.businessInfo;
                    if (var3.roomId == -1 && !TextUtils.isEmpty(var7)) {
                        try {
                            JSONObject var8 = new JSONObject(var7);
                            StringBuilder var9 = new StringBuilder("");
                            this.extractBizInfo(var8, "strGroupId", var9);
                            var6 = var9.toString();
                            var7 = "";
                            if (var8.length() != 0) {
                                var7 = var8.toString();
                            }
                        } catch (Exception var14) {
                            this.apiLog("enter room, room id error, busInfo " + var3.businessInfo);
                            var6 = "";
                            var7 = "";
                        }

                        if (TextUtils.isEmpty(var6)) {
                            this.onEnterRoom(-3318, "room id invalid.");
                            return;
                        }
                    }

                    TXCKeyPointReportProxy.a(30001);
                    final int var15 = var3.role;
                    final long var11 = System.currentTimeMillis();
                    this.runOnSDKThread(new Runnable() {
                        public void run() {
                            Monitor.a(var3.userId, var3.sdkAppId, TextUtils.isEmpty(var6) ? var4 + "" : var6);
                            if (TRTCCloudImpl.this.mRoomState != 0) {
                                if (!TextUtils.isEmpty(var6) && var6.equalsIgnoreCase(TRTCCloudImpl.this.mRoomInfo.strRoomId) || TRTCCloudImpl.this.mRoomInfo.roomId == var4) {
                                    TRTCCloudImpl.this.apiLog(String.format("enter the same room[%d] again!!!", var4));
                                    TRTCCloudImpl.this.mRoomInfo.enterTime = var11;
                                    TRTCCloudImpl.this.onEnterRoom(0, "enter the same room.");
                                    return;
                                }

                                TRTCCloudImpl.this.apiLog(String.format("enter another room[%d] when in room[%d], exit the old room!!!", var4, TRTCCloudImpl.this.mRoomInfo.roomId));
                                TRTCCloudImpl.this.mIsExitOldRoom = true;
                                TRTCCloudImpl.this.exitRoom();
                            }

                            TRTCCloudImpl.this.apiLog("========================================================================================================");
                            TRTCCloudImpl.this.apiLog("========================================================================================================");
                            TRTCCloudImpl.this.apiLog(String.format("============= SDK Version:%s Device Name:%s System Version:%s =============", TXCCommonUtil.getSDKVersionStr(), f.c(), f.d()));
                            TRTCCloudImpl.this.apiLog("========================================================================================================");
                            TRTCCloudImpl.this.apiLog("========================================================================================================");
                            TRTCCloudImpl.this.apiLog(String.format("enterRoom roomId:%d(%s)  userId:%s sdkAppId:%d scene:%d, bizinfo:%s", var4, var6, var3.userId, var3.sdkAppId, var2, var7));
                            String var1 = "enterRoom self:" + TRTCCloudImpl.this.hashCode();
                            int var2x = var2;
                            String var3x = "VideoCall";
                            byte var4x = 2;
                            switch(var2) {
                                case 0:
                                    var3x = "VideoCall";
                                    var4x = 1;
                                    break;
                                case 1:
                                    var3x = "Live";
                                    var4x = 2;
                                    break;
                                case 2:
                                    var3x = "AudioCall";
                                    var2x = 0;
                                    var4x = 1;
                                    break;
                                case 3:
                                    var3x = "VoiceChatRoom";
                                    var2x = 1;
                                    var4x = 2;
                                    break;
                                default:
                                    TXCLog.w("TRTCCloudImpl", "enter room scene:%u error! default to VideoCall! " + var2 + " self:" + TRTCCloudImpl.this.hashCode());
                                    var2x = 0;
                            }

                            TXCAudioEngine.getInstance().setAudioQuality(var4x, 1);
                            Monitor.a(1, var1, String.format("bussInfo:%s, appScene:%s, role:%s, streamid:%s", var7, var3x, var15 == 20 ? "Anchor" : "Audience", var3.streamId), 0);
                            if (TRTCCloudImpl.this.mAudioFrameListener != null) {
                                TXCAudioEngine.setPlayoutDataListener(TRTCCloudImpl.this);
                            }

                            TXCEventRecorderProxy.a("18446744073709551615", 5001, var4, -1L, "", 0);
                            TXCStatus.a("18446744073709551615", 10003, f.c());
                            TRTCCloudImpl.this.mRoomState = 1;
                            if (TRTCCloudImpl.this.mNativeRtcContext == 0L) {
                                int[] var5 = TXCCommonUtil.getSDKVersion();
                                int var6x = var5.length >= 1 ? var5[0] : 0;
                                int var7x = var5.length >= 2 ? var5[1] : 0;
                                int var8 = var5.length >= 3 ? var5[2] : 0;
                                TRTCCloudImpl.this.mNativeRtcContext = TRTCCloudImpl.this.nativeCreateContext(var6x, var7x, var8);
                            }

                            TRTCCloudImpl.this.updateAppScene(var2x);
                            TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this.mConfig);
                            boolean var16 = true;
                            byte var17 = 1;
                            if (var2 != 0 || TRTCCloudImpl.this.mCodecType != 2) {
                                var16 = false;
                                var17 = 0;
                            }

                            TRTCCloudImpl.this.mCaptureAndEnc.g(var16);
                            TRTCCloudImpl.this.setVideoQuality(TRTCCloudImpl.this.mQosMode, TRTCCloudImpl.this.mQosPreference);
                            TRTCCloudImpl.this.setVideoEncConfig(2, TRTCCloudImpl.this.mRoomInfo.bigEncSize.a, TRTCCloudImpl.this.mRoomInfo.bigEncSize.b, TRTCCloudImpl.this.mConfig.h, TRTCCloudImpl.this.mConfig.c, TRTCCloudImpl.this.mConfig.p, TRTCCloudImpl.this.mConfig.e);
                            if (TRTCCloudImpl.this.mEnableSmallStream) {
                                TRTCCloudImpl.this.setVideoEncConfig(3, TRTCCloudImpl.this.mRoomInfo.smallEncSize.a, TRTCCloudImpl.this.mRoomInfo.smallEncSize.b, TRTCCloudImpl.this.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, TRTCCloudImpl.this.mConfig.p, TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate);
                            } else {
                                TRTCCloudImpl.this.setVideoEncoderConfiguration(3, 0, 0, 0, 0, 0, TRTCCloudImpl.this.mConfig.p, 0);
                            }

                            TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this.mEnableSmallStream, TRTCCloudImpl.this.mRoomInfo.smallEncSize.a, TRTCCloudImpl.this.mRoomInfo.smallEncSize.b, TRTCCloudImpl.this.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, TRTCCloudImpl.this.mConfig.i);
                            String var18 = f.d();
                            String var19 = f.c();
                            TRTCCloudImpl.this.nativeSetDataReportDeviceInfo(var19, var18, var3.sdkAppId);
                            com.tencent.liteav.basic.module.TXCKeyPointReportProxy.a var9 = new com.tencent.liteav.basic.module.TXCKeyPointReportProxy.a();
                            var9.d = var2;
                            var9.e = var19;
                            var9.f = var18;
                            var9.h = TRTCCloudImpl.this.mContext != null ? TRTCCloudImpl.this.mContext.getPackageName() : "";
                            var9.b = var3.sdkAppId;
                            var9.g = TXCCommonUtil.getSDKVersionStr();
                            var9.c = TRTCCloudImpl.this.mFramework;
                            TXCKeyPointReportProxy.a(var9);
                            TRTCCloudImpl.this.nativeSetPriorRemoteVideoStreamType(TRTCCloudImpl.this.mNativeRtcContext, TRTCCloudImpl.this.mPriorStreamType);
                            byte[] var10 = TRTCCloudImpl.this.mRoomInfo.getToken(TRTCCloudImpl.this.mContext);
                            TRTCCloudImpl.this.nativeInit(TRTCCloudImpl.this.mNativeRtcContext, var3.sdkAppId, var3.userId, var3.userSig, var10);
                            Iterator var11x = TRTCCloudImpl.this.mStreamTypes.iterator();

                            while(var11x.hasNext()) {
                                Integer var12 = (Integer)var11x.next();
                                TRTCCloudImpl.this.addUpStreamType(var12);
                            }

                            TRTCCloudImpl.this.enableNetworkSmallStream(TRTCCloudImpl.this.mEnableSmallStream);
                            TRTCCloudImpl.this.enableNetworkBlackStream(TRTCCloudImpl.this.mCaptureAndEnc.i());
                            String var20 = var3.privateMapKey != null ? var3.privateMapKey : "";
                            String var21 = var6 != null ? var6 : "";
                            String var13 = var7 != null ? var7 : "";
                            String var14 = var3.userDefineRecordId != null ? var3.userDefineRecordId : "";
                            String var15x = var3.streamId != null ? var3.streamId : "";
                            TRTCCloudImpl.this.nativeEnterRoom(TRTCCloudImpl.this.mNativeRtcContext, var4, var13, var20, var21, var15, 255, var17, var2, TRTCCloudImpl.this.mPerformanceMode, f.c(), f.d(), TRTCCloudImpl.this.mRecvMode, var14, var15x);
                            TRTCCloudImpl.this.mCurrentRole = var15;
                            TRTCCloudImpl.this.mTargetRole = var15;
                            if (TRTCCloudImpl.this.mCurrentRole == 21 && (TRTCCloudImpl.this.mEnableCustomAudioCapture || TRTCCloudImpl.this.mIsAudioCapturing || TRTCCloudImpl.this.mVideoSourceType != TRTCCloudImpl.VideoSourceType.NONE)) {
                                TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                    public void run() {
                                        TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                                        if (var1 != null) {
                                            var1.onWarning(6001, "ignore upstream for audience", (Bundle)null);
                                        }
                                    }
                                });
                                TRTCCloudImpl.this.apiLog("ignore upstream for audience, when enter room!!");
                            }

                            TRTCCloudImpl.this.mCaptureAndEnc.startWithoutAudio();
                            TRTCCloudImpl.this.startCollectStatus();
                            TRTCCloudImpl.this.mLastStateTimeMs = 0L;
                            TRTCCloudImpl.this.mRoomInfo.init(var4, var3.userId);
                            TRTCCloudImpl.this.mRoomInfo.strRoomId = var21;
                            TRTCCloudImpl.this.mRoomInfo.sdkAppId = var3.sdkAppId;
                            TRTCCloudImpl.this.mRoomInfo.userSig = var3.userSig;
                            TRTCCloudImpl.this.mRoomInfo.privateMapKey = var20;
                            TRTCCloudImpl.this.mRoomInfo.enterTime = var11;
                            TXCEventRecorderProxy.a("18446744073709551615", 4007, (long)TRTCCloudImpl.this.mConfig.a, (long)TRTCCloudImpl.this.mConfig.b, "", 2);
                            TXCEventRecorderProxy.a("18446744073709551615", 4008, (long)TRTCCloudImpl.this.mConfig.h, -1L, "", 2);
                            TXCEventRecorderProxy.a("18446744073709551615", 4009, (long)TRTCCloudImpl.this.mConfig.c, -1L, "", 2);
                        }
                    });
                }
            } else {
                this.apiLog("enterRoom param invalid:" + var3);
                if (var3.sdkAppId == 0) {
                    this.onEnterRoom(-3317, "enter room sdkAppId invalid.");
                }

                if (TextUtils.isEmpty(var3.userSig)) {
                    this.onEnterRoom(-3320, "enter room userSig invalid.");
                }

                if (TextUtils.isEmpty(var3.userId)) {
                    this.onEnterRoom(-3319, "enter room userId invalid.");
                }

            }
        }
    }

    public void exitRoom() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.exitRoomInternal(true, "call from api");
            }
        });
    }

    protected void exitRoomInternal(boolean var1, String var2) {
        String var3 = String.format(Locale.ENGLISH, "exitRoom %s, self: %d, reason: %s", this.mRoomInfo.getRoomId(), this.hashCode(), var2);
        this.apiLog(var3);
        Monitor.a(1, var3, "", 0);
        if (this.mRoomState == 0) {
            Monitor.a();
            this.apiLog("exitRoom ignore when no in room.");
        } else {
            this.mRoomState = 0;
            this.mCaptureAndEnc.stopPush();
            TXCSoundEffectPlayer.getInstance().stopAllEffect();
            this.stopCollectStatus();
            this.startVolumeLevelCal(false);
            this.mRoomInfo.forEachUser(new UserAction() {
                public void accept(String var1, UserInfo var2) {
                    TRTCCloudImpl.this.stopRemoteRender(var2);
                    TXCAudioEngine.getInstance().stopRemoteAudio(String.valueOf(var2.tinyID));
                    if (var2.mainRender.render != null) {
                        var2.mainRender.render.setVideoFrameListener((t)null, com.tencent.liteav.basic.a.b.a);
                    }

                    if (var2.subRender.render != null) {
                        var2.subRender.render.setVideoFrameListener((t)null, com.tencent.liteav.basic.a.b.a);
                    }

                }
            });
            TXCAudioEngine.getInstance();
            TXCAudioEngine.setPlayoutDataListener((c)null);
            this.enableVideoStream(false);
            this.enableAudioStream(false);
            if (var1) {
                this.nativeExitRoom(this.mNativeRtcContext);
            }

            this.enableAudioEarMonitoring(false);
            this.stopLocalAudio();
            this.stopBGM();
            TXCKeyPointReportProxy.a(31004);
            this.stopLocalPreview();
            this.stopScreenCapture();
            TXCKeyPointReportProxy.b(31004, 0);
            this.mRoomInfo.clear();
            this.mRenderListenerMap.clear();
            this.mVideoSourceType = TRTCCloudImpl.VideoSourceType.NONE;
            this.mEnableSmallStream = false;
            this.mEnableEosMode = false;
            this.mCodecType = 2;
            this.mEnableSoftAEC = true;
            this.mEnableSoftANS = false;
            this.mEnableSoftAGC = false;
            this.mCaptureAndEnc.a(false);
            TXCAudioEngine.getInstance().muteLocalAudio(false);
            TXCAudioEngine.getInstance().clean();
            this.enableCustomAudioCapture(false);
            this.mEnableCustomAudioCapture = false;
            synchronized(this) {
                if (this.mCustomVideoUtil != null) {
                    this.mCustomVideoUtil.release();
                    this.mCustomVideoUtil = null;
                }
            }

            this.mCaptureAndEnc.a((t)null, 0);
            this.stopAudioRecording();
            TXCSoundEffectPlayer.getInstance().clearCache();
            Monitor.a();
        }
    }

    public void ConnectOtherRoom(final String var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("ConnectOtherRoom " + var1);
                Monitor.a(1, String.format("ConnectOtherRoom param:%s", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudImpl.this.nativeConnectOtherRoom(TRTCCloudImpl.this.mNativeRtcContext, var1);
            }
        });
    }

    public void DisconnectOtherRoom() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("DisconnectOtherRoom");
                Monitor.a(1, "DisconnectOtherRoom self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudImpl.this.nativeDisconnectOtherRoom(TRTCCloudImpl.this.mNativeRtcContext);
            }
        });
    }

    public void setDefaultStreamRecvMode(final boolean var1, final boolean var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.mRecvMode = 0;
                if (var1 && var2) {
                    TRTCCloudImpl.this.mRecvMode = 1;
                } else if (var1) {
                    TRTCCloudImpl.this.mRecvMode = 2;
                } else if (var2) {
                    TRTCCloudImpl.this.mRecvMode = 3;
                } else {
                    TRTCCloudImpl.this.mRecvMode = 4;
                }

                String var1x = String.format("setDefaultStreamRecvMode audio:%b, video:%b", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode();
                TRTCCloudImpl.this.apiLog(var1x);
                Monitor.a(1, var1x, "", 0);
            }
        });
    }

    public void switchRole(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("switchRole:" + var1);
                Monitor.a(1, String.format("switchRole:%s", var1 == 20 ? "Anchor" : "Audience") + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudImpl.this.mTargetRole = var1;
                TRTCCloudImpl.this.nativeChangeRole(TRTCCloudImpl.this.mNativeRtcContext, var1);
            }
        });
    }

    public TRTCCloud createSubCloud() {
        final TRTCSubCloud var1 = new TRTCSubCloud(this.mContext, new WeakReference(this), this.mSDKHandler);
        var1.setListenerHandler(this.mListenerHandler);
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.mSubClouds.add(new WeakReference(var1));
            }
        });
        return var1;
    }

    public void destroySubCloud(final TRTCCloud var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                Iterator var1x = TRTCCloudImpl.this.mSubClouds.iterator();

                while(var1x.hasNext()) {
                    TRTCCloudImpl var2 = (TRTCCloudImpl)((WeakReference)var1x.next()).get();
                    if (var2 != null && var2 == var1) {
                        var2.destroy();
                        var1x.remove();
                        break;
                    }
                }

            }
        });
    }

    public void startLocalPreview(final boolean var1, final TXCloudVideoView var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                boolean var1x = TRTCCloudImpl.this.mVideoSourceType != TRTCCloudImpl.VideoSourceType.NONE;
                if (var1x) {
                    TRTCCloudImpl.this.apiLog("startLocalPreview just reset view when is started");
                }

                if (TRTCCloudImpl.this.mCurrentRole == 21) {
                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                        public void run() {
                            TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                            if (var1x != null) {
                                var1x.onWarning(6001, "ignore start local preview,for role audience", (Bundle)null);
                            }
                        }
                    });
                    TRTCCloudImpl.this.apiLog("ignore startLocalPreview for audience");
                }

                String var2x = "startLocalPreview front:" + var1 + ", view:" + (var2 != null ? var2.hashCode() : "") + " " + TRTCCloudImpl.this.hashCode();
                TRTCCloudImpl.this.apiLog(var2x);
                Monitor.a(1, var2x, "", 0);
                TRTCCloudImpl.this.mRoomInfo.localView = var2;
                TRTCCloudImpl.this.mConfig.m = var1;
                TRTCCloudImpl.this.mConfig.W = TRTCCloudImpl.this.mPerformanceMode == 0;
                TRTCCloudImpl.this.mConfig.U = TRTCCloudImpl.this.mPerformanceMode == 1;
                TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this.mConfig);
                TXCKeyPointReportProxy.a(40046, 1, 2);
                TRTCCloudImpl.this.mOrientationEventListener.enable();
                TRTCCloudImpl.this.updateOrientation();
                TRTCCloudImpl.this.enableVideoStream(true);
                final SurfaceView var3 = var2 != null ? var2.getSurfaceView() : null;
                if (var3 != null) {
                    if (!var1x && TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.NONE) {
                        TRTCCloudImpl.this.mVideoSourceType = TRTCCloudImpl.VideoSourceType.CAMERA;
                        TRTCCloudImpl.this.mCaptureAndEnc.startPreview((TXCloudVideoView)null);
                    } else {
                        TRTCCloudImpl.this.apiLog("startLocalPreview with surface view when is started");
                    }
                } else if (!var1x && TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.NONE) {
                    TRTCCloudImpl.this.mVideoSourceType = TRTCCloudImpl.VideoSourceType.CAMERA;
                    TRTCCloudImpl.this.mCaptureAndEnc.startPreview(var2);
                } else {
                    TRTCCloudImpl.this.apiLog("startLocalPreview with view view when is started");
                }

                final Surface[] var4 = new Surface[1];
                final com.tencent.liteav.basic.util.d var5 = new com.tencent.liteav.basic.util.d();
                TRTCCloudImpl.this.runOnMainThreadAndWaitDone(new Runnable() {
                    public void run() {
                        if (var3 != null) {
                            SurfaceHolder var1x = var3.getHolder();
                            var1x.removeCallback(TRTCCloudImpl.this);
                            var1x.addCallback(TRTCCloudImpl.this);
                            if (var1x.getSurface().isValid()) {
                                TRTCCloudImpl.this.apiLog("startLocalPreview with valid surface " + var1x.getSurface() + " width " + var3.getWidth() + ", height " + var3.getHeight());
                                var4[0] = var1x.getSurface();
                                var5.a = var3.getWidth();
                                var5.b = var3.getHeight();
                            } else {
                                TRTCCloudImpl.this.apiLog("startLocalPreview with surfaceView add callback");
                            }
                        }

                        if (var2 != null) {
                            var2.showVideoDebugLog(TRTCCloudImpl.this.mDebugType);
                            if (TRTCCloudImpl.this.mRoomInfo.debugMargin != null) {
                                var2.setLogMarginRatio(TRTCCloudImpl.this.mRoomInfo.debugMargin.leftMargin, TRTCCloudImpl.this.mRoomInfo.debugMargin.rightMargin, TRTCCloudImpl.this.mRoomInfo.debugMargin.topMargin, TRTCCloudImpl.this.mRoomInfo.debugMargin.bottomMargin);
                            }
                        }

                    }
                });
                if (var4[0] != null) {
                    TRTCCloudImpl.this.mCaptureAndEnc.a(var4[0]);
                    TRTCCloudImpl.this.mCaptureAndEnc.a(var5.a, var5.b);
                }

            }
        });
    }

    public void stopLocalPreview() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                String var1 = "stopLocalPreview self:" + TRTCCloudImpl.this.hashCode();
                TRTCCloudImpl.this.apiLog(var1);
                Monitor.a(1, var1, "", 0);
                if (TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.CAMERA) {
                    TRTCCloudImpl.this.mVideoSourceType = TRTCCloudImpl.VideoSourceType.NONE;
                    TRTCCloudImpl.this.mCaptureAndEnc.c(true);
                }

                if (TRTCCloudImpl.this.mRoomInfo.localView != null) {
                    final SurfaceView var2 = TRTCCloudImpl.this.mRoomInfo.localView.getSurfaceView();
                    if (var2 != null) {
                        TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                            public void run() {
                                var2.getHolder().removeCallback(TRTCCloudImpl.this);
                            }
                        });
                    }
                }

                TRTCCloudImpl.this.mRoomInfo.localView = null;
                TRTCCloudImpl.this.mOrientationEventListener.disable();
                TRTCCloudImpl.this.enableVideoStream(false);
                TXCKeyPointReportProxy.a(40046, 0, 2);
            }
        });
    }

    public void startRemoteView(final String var1, final TXCloudVideoView var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                String var3;
                if (var1x == null) {
                    TRTCCloudImpl.this.apiLog("startRemoteView user is not exist save view" + var1);
                    UserInfo var4 = TRTCCloudImpl.this.createUserInfo(var1);
                    var4.mainRender.view = var2;
                    TRTCCloudImpl.this.mRoomInfo.addUserInfo(var1, var4);
                    var3 = String.format("Remote-startRemoteView userID:%s (save view before user enter)", var1) + " self:" + TRTCCloudImpl.this.hashCode();
                    Monitor.a(1, var3, "", 0);
                } else if (var2 != null && var2.equals(var1x.mainRender.view)) {
                    TRTCCloudImpl.this.apiLog("startRemoteView user view is the same, ignore " + var1);
                } else {
                    boolean var2x = var1x.mainRender.view != null;
                    var1x.mainRender.view = var2;
                    if (var1x.mainRender.tinyID == 0L) {
                        TRTCCloudImpl.this.apiLog("startRemoteView user tinyID is 0, ignore " + var1);
                    } else {
                        TRTCCloudImpl.this.setRenderView(var1, var1x.mainRender, var2, var1x.debugMargin);
                        var3 = String.format("Remote-startRemoteView userID:%s tinyID:%d streamType:%d view:%d", var1, var1x.tinyID, var1x.streamType, var2 != null ? var2.hashCode() : 0) + " self:" + TRTCCloudImpl.this.hashCode();
                        TRTCCloudImpl.this.apiLog(var3);
                        Monitor.a(1, var3, "", 0);
                        TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(var1x.tinyID), var1x.streamType, 0, "Start watching " + var1);
                        if (!var2x || !var1x.mainRender.render.isRendering()) {
                            TRTCCloudImpl.this.startRemoteRender(var1x.mainRender.render, var1x.streamType);
                        }

                        TXCKeyPointReportProxy.a(String.valueOf(var1x.tinyID), 40021, 0L, var1x.streamType);
                        if (!var1x.mainRender.muteVideo) {
                            TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, var1x.streamType, true);
                        } else {
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, var1x.streamType, true);
                        }

                        TXCEventRecorderProxy.a(String.valueOf(var1x.tinyID), 4015, 1L, -1L, "", 0);
                    }
                }
            }
        });
    }

    public void stopRemoteView(final String var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x == null) {
                    TRTCCloudImpl.this.apiLog("stopRemoteRender user is not exist " + var1);
                } else {
                    TRTCCloudImpl.this.apiLog(String.format("stopRemoteView userID:%s tinyID:%d streamType:%d", var1, var1x.tinyID, var1x.streamType));
                    Monitor.a(1, String.format("stopRemoteView userID:%s", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    TXCEventRecorderProxy.a(String.valueOf(var1x.tinyID), 4015, 0L, -1L, "", 0);
                    TRTCCloudImpl.this.stopRemoteMainRender(var1x, false);
                    final TXCloudVideoView var2 = var1x.mainRender.view;
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                        public void run() {
                            if (var2 != null) {
                                var2.removeVideoView();
                            }

                        }
                    });
                    var1x.mainRender.view = null;
                }
            }
        });
    }

    public void startRemoteSubStreamView(final String var1, final TXCloudVideoView var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x == null) {
                    TRTCCloudImpl.this.apiLog("startRemoteSubStreamView user is not exist save view" + var1);
                    UserInfo var3 = TRTCCloudImpl.this.createUserInfo(var1);
                    var3.subRender.view = var2;
                    TRTCCloudImpl.this.mRoomInfo.addUserInfo(var1, var3);
                } else if (var2 != null && var2.equals(var1x.subRender.view)) {
                    TRTCCloudImpl.this.apiLog("startRemoteSubStreamView user view is the same, ignore " + var1);
                } else {
                    boolean var2x = var1x.subRender.view != null;
                    var1x.subRender.view = var2;
                    if (var1x.subRender.tinyID == 0L) {
                        TRTCCloudImpl.this.apiLog("startRemoteSubStreamView user tinyID is 0, ignore " + var1);
                    } else {
                        TRTCCloudImpl.this.setRenderView(var1, var1x.subRender, var2, var1x.debugMargin);
                        TRTCCloudImpl.this.apiLog(String.format("startRemoteSubStreamView userID:%s tinyID:%d streamType:%d view:%d", var1, var1x.tinyID, 7, var2 != null ? var2.hashCode() : 0));
                        Monitor.a(1, String.format("startRemoteSubStreamView userID:%s", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                        TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(var1x.tinyID), 7, 0, "Start watching " + var1);
                        TXCKeyPointReportProxy.a(String.valueOf(var1x.tinyID), 40021, 0L, 7);
                        if (!var2x || !var1x.subRender.render.isRendering()) {
                            TRTCCloudImpl.this.startRemoteRender(var1x.subRender.render, 7);
                        }

                        if (!var1x.subRender.muteVideo) {
                            TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, 7, true);
                        }

                    }
                }
            }
        });
    }

    public void stopRemoteSubStreamView(final String var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x == null) {
                    TRTCCloudImpl.this.apiLog("stopRemoteSubStreamView user is not exist " + var1);
                } else {
                    TRTCCloudImpl.this.apiLog(String.format("stopRemoteSubStreamView userID:%s tinyID:%d streamType:%d", var1, var1x.tinyID, var1x.streamType));
                    Monitor.a(1, String.format("stopRemoteSubStreamView userID:%s", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    TRTCCloudImpl.this.stopRemoteSubRender(var1x);
                    final TXCloudVideoView var2 = var1x.subRender.view;
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                        public void run() {
                            if (var2 != null) {
                                var2.removeVideoView();
                            }

                        }
                    });
                    var1x.subRender.view = null;
                }
            }
        });
    }

    public void setRemoteSubStreamViewFillMode(final String var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                TRTCCloudImpl.this.apiLog("setSubStreamRemoteViewFillMode->userId: " + var1 + ", fillMode: " + var2);
                if (var1x != null && var1x.subRender.render != null) {
                    var1x.subRender.render.setRenderMode(var2);
                }

            }
        });
    }

    public void setRemoteSubStreamViewRotation(final String var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setRemoteSubStreamViewRotation->userId: " + var1 + ", rotation: " + var2);
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x != null && var1x.subRender.render != null) {
                    var1x.subRender.render.setRenderRotation(var2 * 90);
                }

            }
        });
    }

    public void stopAllRemoteView() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("stopAllRemoteView");
                Monitor.a(1, "stopAllRemoteView self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1, UserInfo var2) {
                        TRTCCloudImpl.this.stopRemoteMainRender(var2, true);
                        TRTCCloudImpl.this.stopRemoteSubRender(var2);
                        var2.mainRender.view = null;
                        var2.subRender.view = null;
                    }
                });
            }
        });
    }

    public void snapshotVideo(final String var1, final int var2, final TRTCSnapshotListener var3) {
        this.apiLog(String.format("snapshotVideo user:%s streamType:%d", var1, var2));
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (var1 == null) {
                    TRTCCloudImpl.this.apiLog("snapshotLocalView");
                    TRTCCloudImpl.this.mCaptureAndEnc.a(new o() {
                        public void onTakePhotoComplete(final Bitmap var1x) {
                            TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                public void run() {
                                    if (var3 != null) {
                                        var3.onSnapshotComplete(var1x);
                                    }

                                }
                            });
                        }
                    });
                } else {
                    UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                    com.tencent.liteav.renderer.f var2x = null;
                    if (var2 == 2) {
                        if (var1x != null && var1x.mainRender != null && var1x.mainRender.render != null) {
                            TRTCCloudImpl.this.apiLog("snapshotRemoteSubStreamView->userId: " + var1);
                            var2x = var1x.subRender.render.getVideoRender();
                        }
                    } else if (var1x != null && var1x.mainRender != null && var1x.mainRender.render != null) {
                        TRTCCloudImpl.this.apiLog("snapshotRemoteView->userId: " + var1);
                        var2x = var1x.mainRender.render.getVideoRender();
                    }

                    if (var2x != null) {
                        var2x.a(new o() {
                            public void onTakePhotoComplete(final Bitmap var1x) {
                                TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                    public void run() {
                                        if (var3 != null) {
                                            var3.onSnapshotComplete(var1x);
                                        }

                                    }
                                });
                            }
                        });
                    } else {
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                if (var3 != null) {
                                    var3.onSnapshotComplete((Bitmap)null);
                                }

                            }
                        });
                    }
                }

            }
        });
    }

    public void startScreenCapture(final TRTCVideoEncParam var1, final TRTCScreenShareParams var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mVideoSourceType != TRTCCloudImpl.VideoSourceType.NONE) {
                    TRTCCloudImpl.this.notifyCaptureStarted("Has started capturing, ignore startScreenCapture");
                } else {
                    TRTCCloudImpl.this.mVideoSourceType = TRTCCloudImpl.VideoSourceType.SCREEN;
                    TRTCCloudImpl.this.mSensorMode = 0;
                    TRTCCloudImpl.this.mOrientationEventListener.disable();
                    if (var1 != null) {
                        TRTCCloudImpl.this.mOverrideFPSFromUser = false;
                        TRTCCloudImpl.this.setVideoEncoderParamInternal(var1);
                    } else {
                        TRTCCloudImpl.this.mOverrideFPSFromUser = true;
                    }

                    if (TRTCCloudImpl.this.mCurrentRole == 21) {
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                                if (var1x != null) {
                                    var1x.onWarning(6001, "ignore start local preview,for role audience", (Bundle)null);
                                }
                            }
                        });
                        TRTCCloudImpl.this.apiLog("ignore startLocalPreview for audience");
                    }

                    String var1x = "start screen capture self:" + TRTCCloudImpl.this.hashCode();
                    TRTCCloudImpl.this.apiLog(var1x);
                    Monitor.a(1, var1x, "", 0);
                    TRTCCloudImpl.this.mCaptureAndEnc.setVideoEncRotation(0);
                    if (TRTCCloudImpl.this.mConfig.l != 1 && TRTCCloudImpl.this.mConfig.l != 3) {
                        TRTCCloudImpl.this.updateBigStreamEncoder(false, TRTCCloudImpl.this.mConfig.b, TRTCCloudImpl.this.mConfig.a, TRTCCloudImpl.this.mConfig.h, TRTCCloudImpl.this.mConfig.c, TRTCCloudImpl.this.mConfig.p, TRTCCloudImpl.this.mConfig.e);
                    } else {
                        TRTCCloudImpl.this.updateBigStreamEncoder(true, TRTCCloudImpl.this.mConfig.a, TRTCCloudImpl.this.mConfig.b, TRTCCloudImpl.this.mConfig.h, TRTCCloudImpl.this.mConfig.c, TRTCCloudImpl.this.mConfig.p, TRTCCloudImpl.this.mConfig.e);
                    }

                    com.tencent.liteav.i.a var2x = TRTCCloudImpl.this.getSizeByResolution(TRTCCloudImpl.this.mSmallEncParam.videoResolution, TRTCCloudImpl.this.mSmallEncParam.videoResolutionMode);
                    TRTCCloudImpl.this.updateSmallStreamEncoder(var2x.a, var2x.b, TRTCCloudImpl.this.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate);
                    TRTCCloudImpl.this.mRoomInfo.localView = null;
                    TRTCCloudImpl.this.enableVideoStream(true);
                    TXCKeyPointReportProxy.a(40046, 1, 7);
                    TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this);
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                        public void run() {
                            if (var2 != null) {
                                TRTCCloudImpl.this.showFloatingWindow(var2.floatingView);
                            }

                        }
                    });
                }
            }
        });
    }

    private void showFloatingWindow(View var1) {
        if (var1 != null) {
            if (VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(var1.getContext())) {
                TXCLog.e("TRTCCloudImpl", "can't show floating window for no drawing overlay permission");
            } else {
                this.mFloatingWindow = var1;
                WindowManager var2 = (WindowManager)var1.getContext().getSystemService("window");
                short var3 = 2005;
                if (VERSION.SDK_INT >= 26) {
                    var3 = 2038;
                } else if (VERSION.SDK_INT > 24) {
                    var3 = 2002;
                }

                LayoutParams var4 = new LayoutParams(var3);
                var4.flags = 8;
                var4.flags |= 262144;
                var4.width = -2;
                var4.height = -2;
                var4.format = -3;
                var2.addView(var1, var4);
            }
        }
    }

    public void stopScreenCapture() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mVideoSourceType != TRTCCloudImpl.VideoSourceType.SCREEN) {
                    TRTCCloudImpl.this.apiLog("stopScreenCapture been ignored for Screen capture is not started");
                } else {
                    TRTCCloudImpl.this.mVideoSourceType = TRTCCloudImpl.VideoSourceType.NONE;
                    String var1 = "stopScreenCapture self:" + TRTCCloudImpl.this.hashCode();
                    TRTCCloudImpl.this.apiLog(var1);
                    Monitor.a(1, var1, "", 0);
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                        public void run() {
                            TRTCCloudImpl.this.hideFloatingWindow();
                        }
                    });
                    TRTCCloudImpl.this.mCaptureAndEnc.l();
                    TRTCCloudImpl.this.mRoomInfo.localView = null;
                    TRTCCloudImpl.this.enableVideoStream(false);
                    TXCKeyPointReportProxy.a(40046, 0, 7);
                    TRTCCloudImpl.this.mConfig.h = TRTCCloudImpl.this.mLatestParamsOfBigEncoder.getInt("config_fps", TRTCCloudImpl.this.mConfig.h);
                    TRTCCloudImpl.this.mConfig.i = TRTCCloudImpl.this.mLatestParamsOfBigEncoder.getInt("config_gop", TRTCCloudImpl.this.mConfig.i);
                    TRTCCloudImpl.this.mConfig.p = TRTCCloudImpl.this.mLatestParamsOfBigEncoder.getBoolean("config_adjust_resolution", TRTCCloudImpl.this.mConfig.p);
                    TRTCCloudImpl.this.mSmallEncParam.videoFps = TRTCCloudImpl.this.mLatestParamsOfSmallEncoder.getInt("config_fps", TRTCCloudImpl.this.mSmallEncParam.videoFps);
                    TRTCCloudImpl.this.mSmallEncParam.enableAdjustRes = TRTCCloudImpl.this.mLatestParamsOfSmallEncoder.getBoolean("config_adjust_resolution", TRTCCloudImpl.this.mSmallEncParam.enableAdjustRes);
                    TXCLog.i("TRTCCloudImpl", String.format(Locale.ENGLISH, "restore big encoder's fps: %d, gop: %d, small encoder's fps: %d", TRTCCloudImpl.this.mConfig.h, TRTCCloudImpl.this.mConfig.i, TRTCCloudImpl.this.mSmallEncParam.videoFps));
                }
            }
        });
    }

    private void hideFloatingWindow() {
        if (this.mFloatingWindow != null) {
            WindowManager var1 = (WindowManager)this.mFloatingWindow.getContext().getSystemService("window");
            var1.removeViewImmediate(this.mFloatingWindow);
            this.mFloatingWindow = null;
        }
    }

    public void pauseScreenCapture() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.SCREEN) {
                    TRTCCloudImpl.this.apiLog("pause screen capture");
                    Monitor.a(1, "pause screen capture self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    TRTCCloudImpl.this.mCaptureAndEnc.pausePusher();
                }

            }
        });
    }

    public void resumeScreenCapture() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.SCREEN) {
                    TRTCCloudImpl.this.apiLog("resume screen capture");
                    Monitor.a(1, "resume screen capture self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    TRTCCloudImpl.this.mCaptureAndEnc.resumePusher();
                }

            }
        });
    }

    public void muteLocalVideo(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("muteLocalVideo " + var1);
                Monitor.a(1, String.format("muteLocalVideo mute:%b", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TXCEventRecorderProxy.a("18446744073709551615", 4006, var1 ? 1L : 0L, -1L, "", 2);
                TRTCCloudImpl.this.muteLocalVideo(var1, TRTCCloudImpl.this);
            }
        });
    }

    public void muteLocalVideo(final boolean var1, final TRTCCloudImpl var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl var1x = (TRTCCloudImpl)TRTCCloudImpl.this.mCurrentPublishClouds.get(2);
                if (!var1) {
                    boolean var2x = false;
                    if (var1x != var2) {
                        var2x = true;
                        TRTCCloudImpl.this.enableVideoStream(false);
                        synchronized(TRTCCloudImpl.this.mCurrentPublishClouds) {
                            TRTCCloudImpl.this.mCurrentPublishClouds.put(2, var2);
                            TRTCCloudImpl.this.mCurrentPublishClouds.put(3, var2);
                            TRTCCloudImpl.this.mCurrentPublishClouds.put(7, var2);
                        }

                        TRTCCloudImpl.this.enableNetworkBlackStream(TRTCCloudImpl.this.mCaptureAndEnc.i());
                        TRTCCloudImpl.this.enableNetworkSmallStream(TRTCCloudImpl.this.mEnableSmallStream);
                        TRTCCloudImpl.this.setVideoQuality(TRTCCloudImpl.this.mQosMode, TRTCCloudImpl.this.mQosPreference);
                        TRTCCloudImpl.this.setVideoEncConfig(2, TRTCCloudImpl.this.mRoomInfo.bigEncSize.a, TRTCCloudImpl.this.mRoomInfo.bigEncSize.b, TRTCCloudImpl.this.mConfig.h, TRTCCloudImpl.this.mConfig.c, TRTCCloudImpl.this.mConfig.p, TRTCCloudImpl.this.mConfig.e);
                        if (TRTCCloudImpl.this.mEnableSmallStream) {
                            TRTCCloudImpl.this.setVideoEncConfig(3, TRTCCloudImpl.this.mRoomInfo.smallEncSize.a, TRTCCloudImpl.this.mRoomInfo.smallEncSize.b, TRTCCloudImpl.this.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, TRTCCloudImpl.this.mConfig.p, TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate);
                        } else {
                            TRTCCloudImpl.this.setVideoEncoderConfiguration(3, 0, 0, 0, 0, 0, TRTCCloudImpl.this.mConfig.p, 0);
                        }
                    }

                    TRTCCloudImpl.this.mRoomInfo.muteLocalVideo = var1;
                    TRTCCloudImpl.this.enableVideoStream(!var1);
                    TRTCCloudImpl.this.enableNetworkBlackStream(TRTCCloudImpl.this.mCaptureAndEnc.i());
                    TRTCCloudImpl.this.muteUpstream(2, var1);
                    if (var2x) {
                        TRTCCloudImpl.this.mCaptureAndEnc.k(2);
                        TRTCCloudImpl.this.mCaptureAndEnc.k(3);
                    }
                } else if (var1x == var2) {
                    TRTCCloudImpl.this.mRoomInfo.muteLocalVideo = var1;
                    TRTCCloudImpl.this.enableVideoStream(!var1);
                    TRTCCloudImpl.this.enableNetworkBlackStream(TRTCCloudImpl.this.mCaptureAndEnc.i());
                    TRTCCloudImpl.this.muteUpstream(2, var1);
                }

            }
        });
    }

    public void muteRemoteVideoStream(final String var1, final boolean var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x == null) {
                    TRTCCloudImpl.this.apiLog("muteRemoteVideoStream " + var1 + " no exist.");
                    UserInfo var2x = TRTCCloudImpl.this.createUserInfo(var1);
                    var2x.mainRender.muteVideo = var2;
                    TRTCCloudImpl.this.mRoomInfo.addUserInfo(var1, var2x);
                } else {
                    var1x.mainRender.muteVideo = var2;
                    TRTCCloudImpl.this.apiLog("muteRemoteVideoStream " + var1 + ", mute:" + var2);
                    Monitor.a(1, String.format("muteRemoteVideoStream userId:%s mute:%b self:" + TRTCCloudImpl.this.hashCode(), var1, var2), "", 0);
                    if (var1x.tinyID != 0L) {
                        if (var1x.mainRender.render != null) {
                            var1x.mainRender.render.muteVideo(var2);
                        }

                        if (var2) {
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, 2, true);
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, 3, true);
                            TXCEventRecorderProxy.a(String.valueOf(var1x.tinyID), 4014, 1L, -1L, "", 0);
                        } else {
                            TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, var1x.streamType, true);
                            TXCEventRecorderProxy.a(String.valueOf(var1x.tinyID), 4014, 0L, -1L, "", 0);
                        }

                    }
                }
            }
        });
    }

    public void muteAllRemoteVideoStreams(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("muteAllRemoteVideoStreams mute " + var1);
                Monitor.a(1, String.format("muteAllRemoteVideoStreams mute:%b", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudImpl.this.mRoomInfo.muteRemoteVideo = var1;
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1x, UserInfo var2) {
                        var2.mainRender.muteVideo = var1;
                        TRTCCloudImpl.this.apiLog("muteRemoteVideoStream " + var2.userID + ", mute " + var1);
                        if (var2.mainRender.render != null) {
                            var2.mainRender.render.muteVideo(var1);
                        }

                        if (var1) {
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, 2, true);
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, 3, true);
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, 7, true);
                        } else {
                            if (var2.mainRender.render != null && var2.mainRender.render.isRendering()) {
                                TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, var2.streamType, true);
                            }

                            if (var2.subRender.render != null && var2.subRender.render.isRendering()) {
                                TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, 7, true);
                            }
                        }

                    }
                });
            }
        });
    }

    public void setVideoEncoderParam(final TRTCVideoEncParam var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.setVideoEncoderParamInternal(var1);
            }
        });
    }

    private void setVideoEncoderParamInternal(TRTCVideoEncParam var1) {
        if (var1 != null) {
            this.mLatestParamsOfBigEncoder.putInt("config_fps", var1.videoFps);
            this.mLatestParamsOfBigEncoder.putBoolean("config_adjust_resolution", var1.enableAdjustRes);
            com.tencent.liteav.i.a var2 = this.getSizeByResolution(var1.videoResolution, var1.videoResolutionMode);
            this.updateBigStreamEncoder(var1.videoResolutionMode == 1, var2.a, var2.b, var1.videoFps, var1.videoBitrate, var1.enableAdjustRes, var1.minVideoBitrate);
            this.apiLog("vsize setVideoEncoderParam->width:" + this.mRoomInfo.bigEncSize.a + ", height:" + this.mRoomInfo.bigEncSize.b + ", fps:" + var1.videoFps + ", bitrate:" + var1.videoBitrate + ", mode:" + var1.videoResolutionMode + " minVideoBitrate:" + var1.minVideoBitrate);
            Monitor.a(1, String.format("setVideoEncoderParam width:%d, height:%d, fps:%d, bitrate:%d, mode:%d, minBitrate:%d", this.mRoomInfo.bigEncSize.a, this.mRoomInfo.bigEncSize.b, var1.videoFps, var1.videoBitrate, var1.videoResolutionMode, var1.minVideoBitrate) + " self:" + this.hashCode(), "", 0);
            this.updateOrientation();
            TXCEventRecorderProxy.a("18446744073709551615", 4007, (long)this.mRoomInfo.bigEncSize.a, (long)this.mRoomInfo.bigEncSize.b, "", 2);
            TXCEventRecorderProxy.a("18446744073709551615", 4008, (long)var1.videoFps, -1L, "", 2);
            TXCEventRecorderProxy.a("18446744073709551615", 4009, (long)var1.videoBitrate, -1L, "", 2);
        } else {
            this.apiLog("setVideoEncoderParam param is null");
        }

    }

    public void setNetworkQosParam(final TRTCNetworkQosParam var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (var1 != null) {
                    TRTCCloudImpl.this.apiLog("setNetworkQosParam");
                    TRTCCloudImpl.this.mQosPreference = var1.preference;
                    TRTCCloudImpl.this.mQosMode = var1.controlMode;
                    TRTCCloudImpl.this.setVideoQuality(TRTCCloudImpl.this.mQosMode, TRTCCloudImpl.this.mQosPreference);
                } else {
                    TRTCCloudImpl.this.apiLog("setNetworkQosParam param is null");
                }

            }
        });
    }

    public void setLocalViewFillMode(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setLocalViewFillMode " + var1);
                TRTCCloudImpl.this.mCaptureAndEnc.f(var1);
            }
        });
    }

    public void setRemoteViewFillMode(final String var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setRemoteViewFillMode " + var1 + ", " + var2);
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x != null && var1x.mainRender.render != null) {
                    var1x.mainRender.render.setRenderMode(var2);
                }

            }
        });
    }

    public void setLocalViewRotation(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("vrotation setLocalViewRotation " + var1);
                TRTCCloudImpl.this.mRoomInfo.localRenderRotation = var1 * 90;
                TRTCCloudImpl.this.mCaptureAndEnc.g(var1 * 90);
                TRTCCloudImpl.this.updateOrientation();
            }
        });
    }

    public void setRemoteViewRotation(final String var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("vrotation setRemoteViewRotation " + var1 + ", " + var2);
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x != null && var1x.mainRender.render != null) {
                    var1x.mainRender.render.setRenderRotation(var2 * 90);
                }

            }
        });
    }

    public void setVideoEncoderRotation(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("vrotation setVideoEncoderRotation " + var1 + ", g sensor mode " + TRTCCloudImpl.this.mSensorMode);
                if (TRTCCloudImpl.this.mSensorMode == 0) {
                    TRTCCloudImpl.this.mCaptureAndEnc.setVideoEncRotation(var1 * 90);
                }

            }
        });
    }

    public void setGSensorMode(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.SCREEN) {
                    TRTCCloudImpl.this.apiLog("setGSensorMode has been ignored for screen capturing");
                } else {
                    TRTCCloudImpl.this.mSensorMode = var1;
                    TRTCCloudImpl.this.apiLog("vrotation setGSensorMode " + var1);
                }
            }
        });
    }

    public int enableEncSmallVideoStream(final boolean var1, final TRTCVideoEncParam var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("enableEncSmallVideoStream " + var1);
                TRTCCloudImpl.this.mEnableSmallStream = var1;
                TRTCCloudImpl.this.enableNetworkSmallStream(TRTCCloudImpl.this.mEnableSmallStream);
                if (var2 != null) {
                    TRTCCloudImpl.this.mSmallEncParam.videoBitrate = var2.videoBitrate;
                    TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate = var2.minVideoBitrate;
                    TRTCCloudImpl.this.mSmallEncParam.videoFps = var2.videoFps;
                    TRTCCloudImpl.this.mSmallEncParam.videoResolution = var2.videoResolution;
                    TRTCCloudImpl.this.mSmallEncParam.videoResolutionMode = var2.videoResolutionMode;
                    TRTCCloudImpl.this.mLatestParamsOfSmallEncoder.putInt("config_fps", var2.videoFps);
                    TRTCCloudImpl.this.mLatestParamsOfSmallEncoder.putBoolean("config_adjust_resolution", var2.enableAdjustRes);
                }

                boolean var1x = TRTCCloudImpl.this.mConfig.p;
                int var2x = TRTCCloudImpl.this.mConfig.i;
                if (TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.SCREEN) {
                    var2x = 3;
                    var1x = false;
                    if (TRTCCloudImpl.this.mOverrideFPSFromUser) {
                        TRTCCloudImpl.this.mSmallEncParam.videoFps = 10;
                    }
                }

                TRTCCloudImpl.this.mRoomInfo.smallEncSize = TRTCCloudImpl.this.getSizeByResolution(TRTCCloudImpl.this.mSmallEncParam.videoResolution, TRTCCloudImpl.this.mSmallEncParam.videoResolutionMode);
                TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this.mEnableSmallStream, TRTCCloudImpl.this.mRoomInfo.smallEncSize.a, TRTCCloudImpl.this.mRoomInfo.smallEncSize.b, TRTCCloudImpl.this.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, var2x);
                if (TRTCCloudImpl.this.mEnableSmallStream) {
                    TRTCCloudImpl.this.setVideoEncConfig(3, TRTCCloudImpl.this.mRoomInfo.smallEncSize.a, TRTCCloudImpl.this.mRoomInfo.smallEncSize.b, TRTCCloudImpl.this.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, var1x, TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate);
                    TRTCCloudImpl.this.addUpStreamType(3);
                } else {
                    TRTCCloudImpl.this.setVideoEncoderConfiguration(3, 0, 0, 0, 0, 0, TRTCCloudImpl.this.mConfig.p, 0);
                    TRTCCloudImpl.this.removeUpStreamType(3);
                }

            }
        });
        return 0;
    }

    public int setPriorRemoteVideoStreamType(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (var1 == 0) {
                    TRTCCloudImpl.this.mPriorStreamType = 2;
                } else if (var1 == 1) {
                    TRTCCloudImpl.this.mPriorStreamType = 3;
                } else {
                    TRTCCloudImpl.this.mPriorStreamType = 2;
                }

                TRTCCloudImpl.this.apiLog("setPriorRemoteVideoStreamType " + TRTCCloudImpl.this.mPriorStreamType);
            }
        });
        return 0;
    }

    public void setLocalViewMirror(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.mVideoRenderMirror = var1;
                TRTCCloudImpl.this.apiLog("setLocalViewMirror " + var1);
                TRTCCloudImpl.this.mCaptureAndEnc.setLocalViewMirror(var1);
                TRTCCloudImpl.this.updateOrientation();
            }
        });
    }

    public void setVideoEncoderMirror(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setVideoEncoderMirror " + var1);
                TRTCCloudImpl.this.mConfig.S = var1;
                TRTCCloudImpl.this.mCaptureAndEnc.f(var1);
                TRTCCloudImpl.this.updateOrientation();
            }
        });
    }

    public void setAudioQuality(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setAudioQuality " + var1);
                TXCAudioEngine.getInstance().setAudioQuality(var1, 2);
            }
        });
    }

    public void startLocalAudio() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mEnableCustomAudioCapture) {
                    TRTCCloudImpl.this.apiLog("startLocalAudio when enable custom audio capturing, ignore!!!");
                } else if (TRTCCloudImpl.this.mIsAudioCapturing) {
                    TRTCCloudImpl.this.apiLog("startLocalAudio when capturing audio, ignore!!!");
                } else {
                    if (TRTCCloudImpl.this.mCurrentRole == 21) {
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                                if (var1 != null) {
                                    var1.onWarning(6001, "ignore start local audio,for role audience", (Bundle)null);
                                }
                            }
                        });
                        TRTCCloudImpl.this.apiLog("ignore startLocalAudio,for role audience");
                    }

                    TRTCCloudImpl.this.apiLog("startLocalAudio");
                    Monitor.a(1, "startLocalAudio self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    TXCEventRecorderProxy.a("18446744073709551615", 3001, 0L, -1L, "", 0);
                    TRTCCloudImpl.this.mIsAudioCapturing = true;
                    TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this.mConfig);
                    TRTCCloudImpl.this.setQoSParams();
                    TXCAudioEngine.getInstance().enableCaptureEOSMode(TRTCCloudImpl.this.mEnableEosMode);
                    TXCAudioEngine.getInstance().enableSoftAEC(TRTCCloudImpl.this.mEnableSoftAEC, TRTCCloudImpl.this.mSoftAECLevel);
                    TXCAudioEngineJNI.nativeUseSysAudioDevice(false);
                    TXCAudioEngine.getInstance().startLocalAudio(11, false);
                    TXCAudioEngine.getInstance().enableEncodedDataPackWithTRAEHeaderCallback(true);
                    TXCAudioEngine.getInstance().muteLocalAudio(TRTCCloudImpl.this.mRoomInfo.muteLocalAudio);
                    TXCEventRecorderProxy.a("18446744073709551615", 3003, 11L, -1L, "", 0);
                    TRTCCloudImpl.this.enableAudioStream(true);
                    TXCKeyPointReportProxy.a(40050, 1, 1);
                }
            }
        });
    }

    public void stopLocalAudio() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (!TRTCCloudImpl.this.mIsAudioCapturing) {
                    TRTCCloudImpl.this.apiLog("stopLocalAudio when no capturing audio, ignore!!!");
                } else {
                    TRTCCloudImpl.this.apiLog("stopLocalAudio");
                    Monitor.a(1, "stopLocalAudio self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    TXCEventRecorderProxy.a("18446744073709551615", 3001, 2L, -1L, "", 0);
                    TRTCCloudImpl.this.mIsAudioCapturing = false;
                    TXCAudioEngine.getInstance().stopLocalAudio();
                    TRTCCloudImpl.this.enableAudioStream(false);
                    TXCKeyPointReportProxy.a(40050, 0, 1);
                }
            }
        });
    }

    public int setRemoteVideoStreamType(final String var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x != null) {
                    byte var2x = 2;
                    if (var2 == 1) {
                        var2x = 3;
                    }

                    if (var1x.streamType != var2x) {
                        var1x.streamType = var2x;
                        TRTCCloudImpl.this.apiLog("setRemoteVideoStreamType " + var1 + ", " + var2x + ", " + var1x.tinyID);
                        TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, var2x, false);
                    }
                }
            }
        });
        return 0;
    }

    public void setAudioRoute(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setAudioRoute " + var1);
                Monitor.a(1, String.format("setAudioRoute route:%s", var1 == 0 ? "Speaker" : "Earpiece") + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TXCAudioEngine.setAudioRoute(var1);
            }
        });
    }

    public void muteLocalAudio(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("muteLocalAudio " + var1);
                Monitor.a(1, String.format("muteLocalAudio mute:%b", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudImpl.this.muteLocalAudio(var1, TRTCCloudImpl.this);
                if (var1) {
                    TXCEventRecorderProxy.a("18446744073709551615", 3001, 1L, -1L, "", 0);
                } else {
                    TXCEventRecorderProxy.a("18446744073709551615", 3001, 3L, -1L, "", 0);
                }

            }
        });
    }

    public void muteLocalAudio(final boolean var1, final TRTCCloudImpl var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl var1x = (TRTCCloudImpl)TRTCCloudImpl.this.mCurrentPublishClouds.get(1);
                if (!var1) {
                    if (var1x != var2) {
                        TRTCCloudImpl.this.enableAudioStream(false);
                        synchronized(TRTCCloudImpl.this.mCurrentPublishClouds) {
                            TRTCCloudImpl.this.mCurrentPublishClouds.put(1, var2);
                        }

                        TRTCCloudImpl.this.setAudioEncodeConfiguration();
                    }

                    TRTCCloudImpl.this.mRoomInfo.muteLocalAudio = var1;
                    TXCAudioEngine.getInstance().muteLocalAudio(var1);
                    TRTCCloudImpl.this.muteUpstream(1, var1);
                    TRTCCloudImpl.this.enableAudioStream(true);
                } else if (var1x == var2) {
                    TRTCCloudImpl.this.mRoomInfo.muteLocalAudio = var1;
                    TXCAudioEngine.getInstance().muteLocalAudio(var1);
                    TRTCCloudImpl.this.muteUpstream(1, var1);
                }

            }
        });
    }

    public void muteRemoteAudio(final String var1, final boolean var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                UserInfo var1x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var1x == null) {
                    TRTCCloudImpl.this.apiLog("muteRemoteAudio " + var1 + " no exist.");
                    UserInfo var2x = TRTCCloudImpl.this.createUserInfo(var1);
                    var2x.mainRender.muteAudio = var2;
                    TRTCCloudImpl.this.mRoomInfo.addUserInfo(var1, var2x);
                } else {
                    var1x.mainRender.muteAudio = var2;
                    TRTCCloudImpl.this.apiLog("muteRemoteAudio " + var1 + ", " + var2);
                    Monitor.a(1, String.format("muteRemoteAudio userId:%s mute:%b", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    if (var1x.tinyID != 0L) {
                        TXCAudioEngine.getInstance().muteRemoteAudio(String.valueOf(var1x.tinyID), var2);
                        if (var2) {
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, 1, true);
                        } else {
                            TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1x.tinyID, 1, true);
                        }

                    }
                }
            }
        });
    }

    public void muteAllRemoteAudio(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("muteAllRemoteAudio " + var1);
                Monitor.a(1, String.format("muteAllRemoteAudio mute:%b", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudImpl.this.mRoomInfo.muteRemoteAudio = var1;
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1x, UserInfo var2) {
                        var2.mainRender.muteAudio = var1;
                        TXCAudioEngine.getInstance().muteRemoteAudio(String.valueOf(var2.tinyID), var1);
                        if (var1) {
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, 1, true);
                        } else {
                            TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, 1, true);
                        }

                    }
                });
            }
        });
    }

    public void setRemoteAudioVolume(final String var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                int var1x = var2;
                if (var1x < 0) {
                    var1x = 0;
                }

                if (var1x > 100) {
                    var1x = 100;
                }

                TRTCCloudImpl.this.apiLog("setRemoteAudioVolume: userId = " + var1 + " volume = " + var1x);
                UserInfo var2x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                if (var2x != null) {
                    TXCAudioEngine.getInstance().setRemotePlayoutVolume(String.valueOf(var2x.tinyID), var1x);
                }

            }
        });
    }

    public void setAudioCaptureVolume(int var1) {
        int var2 = var1;
        if (var1 < 0) {
            var2 = 0;
        }

        if (var2 > 100) {
            var2 = 100;
        }

        this.mAudioCaptureVolume = var2;
        this.apiLog("setAudioCaptureVolume:  volume=" + this.mAudioCaptureVolume);
        TXAudioEffectManagerImpl.getInstance().setVoiceCaptureVolume(var2);
    }

    public int getAudioCaptureVolume() {
        return this.mAudioCaptureVolume;
    }

    public void setAudioPlayoutVolume(int var1) {
        int var2 = var1;
        if (var1 < 0) {
            var2 = 0;
        }

        if (var2 > 100) {
            var2 = 100;
        }

        this.mAudioPlayoutVolume = var2;
        this.apiLog("setAudioPlayoutVolume:  volume=" + this.mAudioPlayoutVolume);
        TXAudioEffectManagerImpl.getInstance().setAudioPlayoutVolume(var2);
    }

    public int getAudioPlayoutVolume() {
        return this.mAudioPlayoutVolume;
    }

    public void setSystemVolumeType(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                Monitor.a(1, String.format("setSystemVolumeType type:%d,  auto(0),media(1),VOIP(2)", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                if (0 == var1 || 1 == var1 || 2 == var1) {
                    TXCAudioEngine.getInstance();
                    TXCAudioEngine.setSystemVolumeType(var1);
                }

            }
        });
    }

    public void enableAudioEarMonitoring(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                Monitor.a(1, String.format("enableAudioEarMonitoring enable:%b", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TXAudioEffectManagerImpl.getInstance().enableVoiceEarMonitor(var1);
            }
        });
    }

    public void surfaceCreated(SurfaceHolder var1) {
        if (var1.getSurface().isValid()) {
            this.apiLog("startLocalPreview surfaceCreated " + var1.getSurface());
            this.mCaptureAndEnc.a(var1.getSurface());
        }

    }

    public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4) {
        this.apiLog("startLocalPreview surfaceChanged " + var1.getSurface() + " width " + var3 + ", height " + var4);
        this.mCaptureAndEnc.a(var3, var4);
    }

    public void surfaceDestroyed(SurfaceHolder var1) {
        this.apiLog("startLocalPreview surfaceDestroyed " + var1.getSurface());
        this.mCaptureAndEnc.a((Surface)null);
    }

    public void onScreenCaptureStarted() {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                if (var1 != null) {
                    var1.onScreenCaptureStarted();
                }

            }
        });
    }

    public void onScreenCaptureResumed() {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                if (var1 != null) {
                    var1.onScreenCaptureResumed();
                }

            }
        });
    }

    public void onScreenCapturePaused() {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                if (var1 != null) {
                    var1.onScreenCapturePaused();
                }

            }
        });
    }

    public void onScreenCaptureStopped(final int var1) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onScreenCaptureStopped(var1);
                }

            }
        });
    }

    protected void startVolumeLevelCal(boolean var1) {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(var1, this.mAudioVolumeEvalInterval);
        if (var1) {
            if (this.mVolumeLevelNotifyTask == null) {
                this.mVolumeLevelNotifyTask = new TRTCCloudImpl.VolumeLevelNotifyTask(this);
                this.mSDKHandler.postDelayed(this.mVolumeLevelNotifyTask, (long)this.mAudioVolumeEvalInterval);
            }
        } else {
            this.mVolumeLevelNotifyTask = null;
            this.mAudioVolumeEvalInterval = 0;
        }

    }

    public void enableAudioVolumeEvaluation(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                boolean var1x = false;
                int var2;
                if (var1 > 0) {
                    var2 = var1 < 100 ? 100 : var1;
                } else {
                    var2 = 0;
                }

                if (var2 != TRTCCloudImpl.this.mAudioVolumeEvalInterval) {
                    TRTCCloudImpl.this.apiLog("enableAudioVolumeEvaluation " + var2);
                    TRTCCloudImpl.this.mAudioVolumeEvalInterval = var2;
                    if (TRTCCloudImpl.this.mAudioVolumeEvalInterval > 0) {
                        TRTCCloudImpl.this.startVolumeLevelCal(true);
                    } else {
                        TRTCCloudImpl.this.startVolumeLevelCal(false);
                    }

                }
            }
        });
    }

    public int startAudioRecording(TRTCAudioRecordingParams var1) {
        if (TextUtils.isEmpty(var1.filePath)) {
            this.apiLog("startLocalAudioRecord error:" + var1.filePath);
            return -1;
        } else {
            this.apiLog("startLocalAudioRecord:" + var1.filePath);
            TXCAudioEngine.getInstance().setAudioDumpingListener(new com.tencent.liteav.audio.impl.TXCAudioEngineJNI.a() {
                public void onLocalAudioWriteFailed() {
                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                        public void run() {
                            TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                            if (var1 != null) {
                                TRTCCloudImpl.this.apiLog("startLocalAudioRecord onWarning:7001");
                                var1.onWarning(7001, "write file failed when recording audio.", (Bundle)null);
                            }
                        }
                    });
                }
            });
            return TXCAudioEngine.getInstance().startLocalAudioDumping(48000, 16, var1.filePath);
        }
    }

    public void stopAudioRecording() {
        TXCAudioEngine.getInstance().stopLocalAudioDumping();
    }

    public void switchCamera() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.mConfig.m = !TRTCCloudImpl.this.mConfig.m;
                TRTCCloudImpl.this.mCaptureAndEnc.k();
                TRTCCloudImpl.this.apiLog("switchCamera " + TRTCCloudImpl.this.mConfig.m);
                TRTCCloudImpl.this.updateOrientation();
            }
        });
    }

    public boolean isCameraZoomSupported() {
        return this.mCaptureAndEnc.m();
    }

    public void setZoom(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setZoom " + var1);
                TRTCCloudImpl.this.mCaptureAndEnc.i(var1);
            }
        });
    }

    public boolean isCameraTorchSupported() {
        return this.mCaptureAndEnc.n();
    }

    public boolean enableTorch(boolean var1) {
        this.apiLog("enableTorch " + var1);
        return this.mCaptureAndEnc.e(var1);
    }

    public boolean isCameraFocusPositionInPreviewSupported() {
        return this.mCaptureAndEnc.o();
    }

    public void setFocusPosition(final int var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.mCaptureAndEnc.b(var1, var2);
            }
        });
    }

    public boolean isCameraAutoFocusFaceModeSupported() {
        return this.mCaptureAndEnc.p();
    }

    public TXBeautyManager getBeautyManager() {
        if (this.mCaptureAndEnc == null) {
            this.mCaptureAndEnc = new CaptureAndEnc(this.mContext);
        }

        return this.mCaptureAndEnc.getBeautyManager();
    }

    public void setBeautyStyle(final int var1, final int var2, final int var3, final int var4) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.getBeautyManager().setBeautyStyle(var1);
                TRTCCloudImpl.this.getBeautyManager().setBeautyLevel(var2);
                TRTCCloudImpl.this.getBeautyManager().setWhitenessLevel(var3);
                TRTCCloudImpl.this.getBeautyManager().setRuddyLevel(var4);
            }
        });
    }

    public void setFilter(final Bitmap var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setFilter");
                TRTCCloudImpl.this.getBeautyManager().setFilter(var1);
            }
        });
    }

    public void setFilterConcentration(final float var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setFilterStrength: " + var1);
                TRTCCloudImpl.this.getBeautyManager().setFilterStrength(var1);
            }
        });
    }

    public void selectMotionTmpl(final String var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("selectMotionTmpl " + var1);
                TRTCCloudImpl.this.getBeautyManager().setMotionTmpl(var1);
            }
        });
    }

    public void setMotionMute(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setMotionMute " + var1);
                TRTCCloudImpl.this.getBeautyManager().setMotionMute(var1);
            }
        });
    }

    @TargetApi(18)
    public boolean setGreenScreenFile(final String var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setGreenScreenFile " + var1);
                TRTCCloudImpl.this.getBeautyManager().setGreenScreenFile(var1);
            }
        });
        return true;
    }

    public void setEyeScaleLevel(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setEyeScaleLevel " + var1);
                TRTCCloudImpl.this.getBeautyManager().setEyeScaleLevel(var1);
            }
        });
    }

    public void setFaceSlimLevel(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setFaceSlimLevel " + var1);
                TRTCCloudImpl.this.getBeautyManager().setFaceSlimLevel(var1);
            }
        });
    }

    public void setFaceVLevel(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setFaceVLevel " + var1);
                TRTCCloudImpl.this.getBeautyManager().setFaceVLevel(var1);
            }
        });
    }

    public void setFaceShortLevel(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setFaceShortLevel " + var1);
                TRTCCloudImpl.this.getBeautyManager().setFaceShortLevel(var1);
            }
        });
    }

    public void setChinLevel(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setChinLevel " + var1);
                TRTCCloudImpl.this.getBeautyManager().setChinLevel(var1);
            }
        });
    }

    public void setNoseSlimLevel(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setNoseSlimLevel " + var1);
                TRTCCloudImpl.this.getBeautyManager().setNoseSlimLevel(var1);
            }
        });
    }

    public void setWatermark(final Bitmap var1, final int var2, final float var3, final float var4, final float var5) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("addWatermark stream:" + var2);
                if (var2 != 2) {
                    TRTCCloudImpl.this.mConfig.E = var1;
                    TRTCCloudImpl.this.mConfig.H = var3;
                    TRTCCloudImpl.this.mConfig.I = var4;
                    TRTCCloudImpl.this.mConfig.J = var5;
                    TRTCCloudImpl.this.mCaptureAndEnc.a(var1, var3, var4, var5);
                }

            }
        });
    }

    public void enableCustomVideoCapture(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (var1 && TRTCCloudImpl.this.mVideoSourceType != TRTCCloudImpl.VideoSourceType.NONE) {
                    TRTCCloudImpl.this.notifyCaptureStarted("Has started capturing, ignore enableCustomVideoCapture");
                } else if (var1 || TRTCCloudImpl.this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.CUSTOM) {
                    TRTCCloudImpl.this.mVideoSourceType = var1 ? TRTCCloudImpl.VideoSourceType.CUSTOM : TRTCCloudImpl.VideoSourceType.NONE;
                    i var10000;
                    if (var1) {
                        var10000 = TRTCCloudImpl.this.mConfig;
                        var10000.R |= 2;
                        TRTCCloudImpl.this.mLastCaptureCalculateTS = 0L;
                        if (TRTCCloudImpl.this.mCurrentRole == 21) {
                            TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                public void run() {
                                    TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                                    if (var1x != null) {
                                        var1x.onWarning(6001, "ignore send custom video,for role audience", (Bundle)null);
                                    }
                                }
                            });
                            TRTCCloudImpl.this.apiLog("ignore enableCustomVideoCapture,for role audience");
                        }
                    } else {
                        var10000 = TRTCCloudImpl.this.mConfig;
                        var10000.R &= -3;
                        synchronized(this) {
                            if (TRTCCloudImpl.this.mCustomVideoUtil != null) {
                                TRTCCloudImpl.this.mCustomVideoUtil.release();
                                TRTCCloudImpl.this.mCustomVideoUtil = null;
                            }
                        }
                    }

                    TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this.mConfig);
                    TRTCCloudImpl.this.apiLog("enableCustomVideoCapture " + var1);
                    Monitor.a(1, String.format("enableCustomVideoCapture:%b", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    TRTCCloudImpl.this.enableVideoStream(var1);
                    TXCKeyPointReportProxy.a(40046, var1 ? 1 : 0, 2);
                }
            }
        });
    }

    public void sendCustomVideoData(TRTCVideoFrame var1) {
        if (var1 == null) {
            this.apiLog("sendCustomVideoData parameter is null");
        } else if (var1.pixelFormat != 1 && var1.pixelFormat != 4 && var1.pixelFormat != 2) {
            this.apiLog("sendCustomVideoData parameter error unsupported pixel format " + var1.pixelFormat);
        } else if (var1.bufferType != 2 && var1.texture == null) {
            this.apiLog("sendCustomVideoData parameter error unsupported buffer type " + var1.bufferType);
        } else if (this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.CUSTOM && this.mRoomState == 2) {
            synchronized(this) {
                if (this.mCustomVideoUtil == null) {
                    this.mCustomVideoUtil = new TRTCCustomTextureUtil(this.mCaptureAndEnc);
                }

                if (this.mCustomVideoUtil != null) {
                    this.mCustomVideoUtil.sendCustomTexture(var1);
                }
            }

            if (this.mLastCaptureCalculateTS == 0L) {
                this.mLastCaptureCalculateTS = System.currentTimeMillis();
                this.mLastCaptureFrameCount = 0L;
                this.mCaptureFrameCount = 0L;
            } else {
                ++this.mCaptureFrameCount;
            }

        }
    }

    protected void setSEIPayloadType(JSONObject var1) throws JSONException {
        if (var1 != null && var1.has("payloadType")) {
            int var2 = var1.getInt("payloadType");
            if (var2 != 5 && var2 != 243) {
                this.apiLog("callExperimentalAPI[invalid param]: payloadType[" + var2 + "]");
            } else {
                if (this.nativeSetSEIPayloadType(this.mNativeRtcContext, var2)) {
                    this.apiLog("callExperimentalAPI[succeeded]: setSEIPayloadType (" + var2 + ")");
                } else {
                    this.apiLog("callExperimentalAPI[failed]: setSEIPayloadType (" + var2 + ")");
                }

            }
        } else {
            this.apiLog("callExperimentalAPI[lack parameter or illegal type]: payloadType");
        }
    }

    private void updateBigStreamEncoder(boolean var1, int var2, int var3, int var4, int var5, boolean var6, int var7) {
        if (var2 > 0 && var3 > 0) {
            this.mRoomInfo.bigEncSize.a = var2;
            this.mRoomInfo.bigEncSize.b = var3;
            if (this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.SCREEN) {
                this.mConfig.l = 1;
                this.mConfig.a = this.mRoomInfo.bigEncSize.a;
                this.mConfig.b = this.mRoomInfo.bigEncSize.b;
            } else if (var1) {
                this.mConfig.l = 1;
                this.mConfig.a = this.mRoomInfo.bigEncSize.a;
                this.mConfig.b = this.mRoomInfo.bigEncSize.b;
            } else {
                this.mConfig.l = 0;
                this.mConfig.a = this.mRoomInfo.bigEncSize.b;
                this.mConfig.b = this.mRoomInfo.bigEncSize.a;
            }

            this.mConfig.k = com.tencent.liteav.basic.a.c.a;
        }

        if (var4 > 0) {
            if (var4 > 30) {
                this.apiLog("setVideoEncoderParam fps > 30, limit fps to 30");
                this.mConfig.h = 30;
            } else {
                this.mConfig.h = var4;
            }
        }

        if (var5 > 0) {
            this.mConfig.c = var5;
        }

        if (var7 >= 0) {
            this.mConfig.e = var7;
        }

        if (this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.SCREEN) {
            this.mConfig.i = 3;
            this.mConfig.p = false;
            if (this.mOverrideFPSFromUser) {
                this.mConfig.h = 10;
            }
        } else {
            this.mConfig.p = var6;
        }

        this.setVideoEncConfig(2, this.mRoomInfo.bigEncSize.a, this.mRoomInfo.bigEncSize.b, this.mConfig.h, this.mConfig.c, this.mConfig.p, this.mConfig.e);
        this.updateEncType();
        this.mCaptureAndEnc.e(this.mConfig.h);
        this.mCaptureAndEnc.a(this.mConfig);
    }

    private void updateSmallStreamEncoder(int var1, int var2, int var3, int var4, int var5) {
        if (var1 > 0 && var2 > 0) {
            this.mRoomInfo.smallEncSize.a = var1;
            this.mRoomInfo.smallEncSize.b = var2;
        }

        if (var3 > 0) {
            if (var3 > 20) {
                this.apiLog("setVideoSmallEncoderParam fps > 20, limit fps to 20");
                this.mSmallEncParam.videoFps = 20;
            } else {
                this.mSmallEncParam.videoFps = var3;
            }
        }

        if (var4 > 0) {
            this.mSmallEncParam.videoBitrate = var4;
        }

        if (var5 >= 0) {
            this.mSmallEncParam.minVideoBitrate = var5;
        }

        int var6 = this.mConfig.i;
        if (this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.SCREEN) {
            this.mSmallEncParam.enableAdjustRes = false;
            var6 = 3;
            if (this.mOverrideFPSFromUser) {
                this.mSmallEncParam.videoFps = 10;
            }
        }

        this.mCaptureAndEnc.a(this.mEnableSmallStream, this.mRoomInfo.smallEncSize.a, this.mRoomInfo.smallEncSize.b, this.mSmallEncParam.videoFps, this.mSmallEncParam.videoBitrate, var6);
        this.setVideoEncConfig(3, this.mRoomInfo.smallEncSize.a, this.mRoomInfo.smallEncSize.b, this.mSmallEncParam.videoFps, this.mSmallEncParam.videoBitrate, this.mConfig.p, this.mSmallEncParam.minVideoBitrate);
    }

    private void setVideoEncoderParamEx(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("callExperimentalAPI[lack parameter or illegal type]: codecType");
        } else {
            int var2 = var1.optInt("codecType", -1);
            if (var2 != -1) {
                this.mCodecType = var2;
                if (this.mCodecType == 0) {
                    JSONObject var3 = var1.optJSONObject("softwareCodecParams");
                    if (var3 != null) {
                        this.mConfig.P = var3.optInt("enableRealTime") != 0;
                        this.mConfig.n = var3.optInt("profile");
                    }
                }
            }

            int var10 = var1.optInt("videoWidth", 0);
            int var4 = var1.optInt("videoHeight", 0);
            int var5 = var1.optInt("videoFps", 0);
            int var6 = var1.optInt("videoBitrate", 0);
            int var7 = var1.optInt("minVideoBitrate", 0);
            int var8 = var1.optInt("rcMethod", 0);
            if (var10 > 0 && var4 > 0) {
                if (var10 > 1920) {
                    var10 = 1920;
                    var4 = 1920 * var4 / var10;
                }

                if (var4 > 1920) {
                    var4 = 1920;
                    var10 = 1920 * var10 / var4;
                }

                if (var10 < 90) {
                    var10 = 90;
                    var4 = 90 * var4 / var10;
                }

                if (var4 < 90) {
                    var4 = 90;
                    var10 = 90 * var10 / var4;
                }

                var10 = (var10 + 15) / 16 * 16;
                var4 = (var4 + 15) / 16 * 16;
                int var9 = var1.optInt("streamType", 0);
                if (var9 == 0) {
                    this.mLatestParamsOfBigEncoder.putInt("config_fps", var5);
                    this.updateBigStreamEncoder(var10 <= var4, var10, var4, var5, var6, this.mConfig.p, var7);
                    this.mCaptureAndEnc.l(var8);
                } else if (var9 == 1) {
                    this.mLatestParamsOfSmallEncoder.putInt("config_fps", var5);
                    this.updateSmallStreamEncoder(var10, var4, var5, var6, var7);
                }

                this.apiLog("vsize setVideoEncoderParamEx->width:" + this.mRoomInfo.bigEncSize.a + ", height:" + this.mRoomInfo.bigEncSize.b + ", fps:" + var5 + ", bitrate:" + var6 + ", stream:" + var9);
                this.updateOrientation();
            }

        }
    }

    private void setLocalAudioMuteMode(JSONObject var1) throws JSONException {
        if (var1 == null || !var1.has("mode")) {
            this.apiLog("setLocalAudioMuteMode[lack parameter or illegal type]: mode");
        }

        int var2 = var1.getInt("mode");
        if (0 == var2) {
            this.mEnableEosMode = false;
        } else {
            this.mEnableEosMode = true;
        }

        TXCAudioEngine.getInstance().enableCaptureEOSMode(this.mEnableEosMode);
    }

    private void setAudioSampleRate(JSONObject var1) throws JSONException {
        if (var1 != null && var1.has("sampleRate")) {
            int var2 = var1.getInt("sampleRate");
            if (!this.mEnableCustomAudioCapture && !this.mIsAudioCapturing) {
                if (16000 != var2 && 48000 != var2) {
                    this.apiLog("muteRemoteAudioInSpeaker[illegal sampleRate]: " + var2);
                } else {
                    TXCAudioEngine.getInstance().setEncoderSampleRate(var2);
                }
            } else {
                this.apiLog("setAudioSampleRate[illegal state]");
            }
        } else {
            this.apiLog("setAudioSampleRate[lack parameter or illegal type]: sampleRate");
        }
    }

    private void enableAudioAGC(JSONObject var1) throws JSONException {
        if (var1 == null || !var1.has("enable")) {
            this.apiLog("enableAudioAGC[lack parameter or illegal type]: enable");
        }

        int var2 = var1.getInt("enable");
        if (0 == var2) {
            this.mEnableSoftAGC = false;
        } else {
            this.mEnableSoftAGC = true;
        }

        if (var1.has("level")) {
            this.mSoftAGCLevel = var1.getInt("level");
        } else {
            this.mSoftAGCLevel = 100;
        }

        TXCAudioEngine.getInstance().enableSoftAGC(this.mEnableSoftAGC, this.mSoftAGCLevel);
    }

    private void enableAudioAEC(JSONObject var1) throws JSONException {
        if (var1 == null || !var1.has("enable")) {
            this.apiLog("enableAudioAEC[lack parameter or illegal type]: enable");
        }

        int var2 = var1.getInt("enable");
        if (0 == var2) {
            this.mEnableSoftAEC = false;
        } else {
            this.mEnableSoftAEC = true;
        }

        if (var1.has("level")) {
            this.mSoftAECLevel = var1.getInt("level");
        } else {
            this.mSoftAECLevel = 100;
        }

        TXCAudioEngine.getInstance().enableSoftAEC(this.mEnableSoftAEC, this.mSoftAECLevel);
    }

    private void enableAudioANS(JSONObject var1) throws JSONException {
        if (var1 == null || !var1.has("enable")) {
            this.apiLog("enableAudioANS[lack parameter or illegal type]: enable");
        }

        int var2 = var1.getInt("enable");
        if (0 == var2) {
            this.mEnableSoftANS = false;
        } else {
            this.mEnableSoftANS = true;
        }

        if (var1.has("level")) {
            this.mSoftANSLevel = var1.getInt("level");
        } else {
            this.mSoftANSLevel = 100;
        }

        TXCAudioEngine.getInstance().enableSoftANS(this.mEnableSoftANS, this.mSoftANSLevel);
    }

    protected void setPerformanceMode(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("setPerformanceMode[lack parameter]");
        } else if (!var1.has("mode")) {
            this.apiLog("setPerformanceMode[lack parameter]: mode");
        } else {
            int var2 = var1.getInt("mode");
            if (var2 == 1) {
                this.mPerformanceMode = 1;
                this.mCaptureAndEnc.getBeautyManager().enableSharpnessEnhancement(false);
            } else {
                this.mPerformanceMode = 0;
            }

        }
    }

    protected void muteRemoteAudioInSpeaker(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("muteRemoteAudioInSpeaker[lack parameter]");
        } else if (!var1.has("userID")) {
            this.apiLog("muteRemoteAudioInSpeaker[lack parameter]: userID");
        } else {
            String var2 = var1.getString("userID");
            if (var2 == null) {
                this.apiLog("muteRemoteAudioInSpeaker[illegal type]: userID");
            } else if (!var1.has("mute")) {
                this.apiLog("muteRemoteAudioInSpeaker[lack parameter]: mute");
            } else {
                int var3 = var1.getInt("mute");
                UserInfo var4 = this.mRoomInfo.getUser(var2);
                if (var4 == null) {
                    this.apiLog("muteRemoteAudioInSpeaker " + var2 + " no exist, create one.");
                    UserInfo var5 = this.createUserInfo(var2);
                    var5.muteAudioInSpeaker = var3 == 1;
                    this.mRoomInfo.addUserInfo(var2, var5);
                } else {
                    if (var4 != null) {
                        TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(String.valueOf(var4.tinyID), var3 == 1);
                    } else {
                        this.apiLog("muteRemoteAudioInSpeaker[illegal type]: userID");
                    }

                }
            }
        }
    }

    private void setCustomRenderMode(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("setCustomRenderMode param is null");
        } else if (!var1.has("mode")) {
            this.apiLog("setCustomRenderMode[lack parameter]: mode");
        } else {
            int var2 = var1.optInt("mode", 0);
            this.mRoomInfo.enableCustomPreprocessor = var2 == 1;
            this.mCaptureAndEnc.a(this.mRoomInfo.enableCustomPreprocessor);
        }
    }

    private void setMediaCodecConfig(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("setMediaCodecConfig param is null");
        } else {
            JSONArray var2 = null;
            if (var1.has("encProperties")) {
                var2 = var1.getJSONArray("encProperties");
            }

            this.mConfig.Y = var2;
            this.mCaptureAndEnc.a(this.mConfig);
            JSONArray var3 = null;
            if (var1.has("decProperties")) {
                var3 = var1.getJSONArray("decProperties");
            }

            this.mRoomInfo.decProperties = var3;
            int var4 = 0;
            if (var1.has("restartDecoder")) {
                var4 = var1.getInt("restartDecoder");
            }

            this.mRoomInfo.enableRestartDecoder = var4 != 0;
        }
    }

    private void setFramework(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("setFramework[lack parameter]");
        } else if (!var1.has("framework")) {
            this.apiLog("setFramework[lack parameter]: framework");
        } else {
            int var2 = var1.getInt("framework");
            this.mFramework = var2;
        }
    }

    private void forceCallbackMixedPlayAudioFrame(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("forceCallbackMixedPlayAudioFrame param is null");
        } else if (!var1.has("enable")) {
            this.apiLog("forceCallbackMixedPlayAudioFrame[lack parameter]: enable");
        } else {
            int var2 = var1.optInt("enable", 0);
            TXCAudioEngine.getInstance().forceCallbackMixedPlayAudioFrame(var2 != 0);
        }
    }

    public void callExperimentalAPI(final String var1) {
        if (var1 != null) {
            this.apiLog("callExperimentalAPI  " + var1 + ", roomid = " + (this.mRoomInfo.roomId != -1L ? this.mRoomInfo.roomId : this.mRoomInfo.strRoomId));
            Monitor.a(1, String.format("callExperimentalAPI:%s", var1) + " self:" + this.hashCode(), "", 0);
        }

        this.runOnSDKThread(new Runnable() {
            public void run() {
                try {
                    JSONObject var1x = new JSONObject(var1);
                    if (!var1x.has("api")) {
                        TRTCCloudImpl.this.apiLog("callExperimentalAPI[lack api or illegal type]: " + var1);
                        return;
                    }

                    String var2 = var1x.getString("api");
                    JSONObject var3 = null;
                    if (var1x.has("params")) {
                        var3 = var1x.getJSONObject("params");
                    }

                    if (var2.equals("setSEIPayloadType")) {
                        TRTCCloudImpl.this.setSEIPayloadType(var3);
                    } else if (var2.equals("setLocalAudioMuteMode")) {
                        TRTCCloudImpl.this.setLocalAudioMuteMode(var3);
                    } else if (var2.equals("setVideoEncodeParamEx")) {
                        TRTCCloudImpl.this.setVideoEncoderParamEx(var3);
                    } else if (var2.equals("setAudioSampleRate")) {
                        TRTCCloudImpl.this.setAudioSampleRate(var3);
                    } else if (var2.equals("muteRemoteAudioInSpeaker")) {
                        TRTCCloudImpl.this.muteRemoteAudioInSpeaker(var3);
                    } else if (var2.equals("enableAudioAGC")) {
                        TRTCCloudImpl.this.enableAudioAGC(var3);
                    } else if (var2.equals("enableAudioAEC")) {
                        TRTCCloudImpl.this.enableAudioAEC(var3);
                    } else if (var2.equals("enableAudioANS")) {
                        TRTCCloudImpl.this.enableAudioANS(var3);
                    } else if (var2.equals("setPerformanceMode")) {
                        TRTCCloudImpl.this.setPerformanceMode(var3);
                    } else if (var2.equals("setCustomRenderMode")) {
                        TRTCCloudImpl.this.setCustomRenderMode(var3);
                    } else if (var2.equals("setMediaCodecConfig")) {
                        TRTCCloudImpl.this.setMediaCodecConfig(var3);
                    } else if (var2.equals("sendJsonCMD")) {
                        TRTCCloudImpl.this.sendJsonCmd(var3, var1);
                    } else if (var2.equals("updatePrivateMapKey")) {
                        TRTCCloudImpl.this.updatePrivateMapKey(var3);
                    } else if (var2.equals("setFramework")) {
                        TRTCCloudImpl.this.setFramework(var3);
                    } else if (var2.equals("forceCallbackMixedPlayAudioFrame")) {
                        TRTCCloudImpl.this.forceCallbackMixedPlayAudioFrame(var3);
                    } else {
                        TRTCCloudImpl.this.apiLog("callExperimentalAPI[illegal api]: " + var2);
                    }
                } catch (Exception var4) {
                    TRTCCloudImpl.this.apiLog("callExperimentalAPI[failed]: " + var1 + " " + var4.getMessage());
                }

            }
        });
    }

    protected void updatePrivateMapKey(JSONObject var1) throws JSONException {
        if (var1 == null) {
            this.apiLog("callExperimentalAPI[update private map key fail, params is null");
        } else {
            String var2 = var1.getString("privateMapKey");
            if (!TextUtils.isEmpty(var2)) {
                this.nativeUpdatePrivateMapKey(this.mNativeRtcContext, var2);
            } else {
                this.apiLog("callExperimentalAPI[update private map key fail, key is empty");
            }

        }
    }

    protected void sendJsonCmd(JSONObject var1, String var2) throws JSONException {
        if (var1 != null && var1.has("jsonParam") && var1.get("jsonParam") instanceof JSONObject) {
            JSONObject var3 = var1.getJSONObject("jsonParam");
            String var4 = var3.toString();
            this.nativeSendJsonCmd(this.mNativeRtcContext, var4, var2);
        } else {
            this.apiLog("callExperimentalAPI[lack parameter or illegal type]: sendJsonCMD");
        }
    }

    public int setLocalVideoRenderListener(final int var1, final int var2, final TRTCVideoRenderListener var3) {
        if (var1 != 1 && var1 != 4 && var1 != 2) {
            this.apiLog("setLocalVideoRenderListener unsupported pixelFormat : " + var1);
            return -1327;
        } else if (var2 != 1 && var2 != 2 && var2 != 3) {
            this.apiLog("setLocalVideoRenderListener unsupported bufferType : " + var2);
            return -1328;
        } else {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.apiLog(String.format("setLocalVideoRenderListener pixelFormat:%d bufferType:%d", var1, var2));
                    TRTCCloudImpl.this.mRoomInfo.localPixelFormat = var1;
                    TRTCCloudImpl.this.mRoomInfo.localBufferType = var2;
                    TRTCCloudImpl.this.mRoomInfo.localListener = var3;
                    if (var3 == null) {
                        TRTCCloudImpl.this.mCaptureAndEnc.a((t)null, var1);
                    } else {
                        TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this, var1);
                    }

                }
            });
            return 0;
        }
    }

    public int setRemoteVideoRenderListener(final String var1, final int var2, final int var3, final TRTCVideoRenderListener var4) {
        if (var2 != 1 && var2 != 4 && var2 != 2) {
            this.apiLog("setLocalVideoRenderListener unsupported pixelFormat : " + var2);
            return -1327;
        } else if (var3 != 1 && var3 != 2 && var3 != 3) {
            this.apiLog("setLocalVideoRenderListener unsupported bufferType : " + var3);
            return -1328;
        } else {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.apiLog(String.format("setRemoteVideoRenderListener userid:%s pixelFormat:%d bufferType:%d", var1, var2, var3));
                    if (var4 == null) {
                        TRTCCloudImpl.this.mRenderListenerMap.remove(var1);
                    } else {
                        TRTCCloudImpl.RenderListenerAdapter var1x = new TRTCCloudImpl.RenderListenerAdapter();
                        var1x.bufferType = var3;
                        var1x.pixelFormat = var2;
                        var1x.listener = var4;
                        TRTCCloudImpl.this.mRenderListenerMap.put(var1, var1x);
                        TRTCCloudImpl.this.mCustomRemoteRender = true;
                    }

                    TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                        public void accept(String var1x, UserInfo var2x) {
                            if (var1x.equalsIgnoreCase(var1)) {
                                TRTCCloudImpl.RenderListenerAdapter var3x = (TRTCCloudImpl.RenderListenerAdapter)TRTCCloudImpl.this.mRenderListenerMap.get(var1);
                                if (var3x != null) {
                                    var3x.strTinyID = String.valueOf(var2x.tinyID);
                                }

                                TRTCCloudImpl var4x = var4 != null ? TRTCCloudImpl.this : null;
                                if (var2x.mainRender.render != null) {
                                    var2x.mainRender.render.setVideoFrameListener(var4x, TRTCCloudImpl.this.getPixelFormat(var3x.pixelFormat));
                                }

                                if (var2x.subRender.render != null) {
                                    var2x.subRender.render.setVideoFrameListener(var4x, TRTCCloudImpl.this.getPixelFormat(var3x.pixelFormat));
                                }
                            }

                        }
                    });
                }
            });
            return 0;
        }
    }

    public void enableCustomAudioCapture(final boolean var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mEnableCustomAudioCapture != var1) {
                    TRTCCloudImpl.this.mEnableCustomAudioCapture = var1;
                    i var10000;
                    if (var1) {
                        var10000 = TRTCCloudImpl.this.mConfig;
                        var10000.R |= 1;
                        if (TRTCCloudImpl.this.mCurrentRole == 21) {
                            TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                public void run() {
                                    TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                                    if (var1x != null) {
                                        var1x.onWarning(6001, "ignore send custom audio,for role audience", (Bundle)null);
                                    }
                                }
                            });
                            TRTCCloudImpl.this.apiLog("ignore enableCustomAudioCapture,for role audience");
                        }
                    } else {
                        var10000 = TRTCCloudImpl.this.mConfig;
                        var10000.R &= -2;
                    }

                    TRTCCloudImpl.this.mCaptureAndEnc.a(TRTCCloudImpl.this.mConfig);
                    TRTCCloudImpl.this.apiLog("enableCustomAudioCapture " + var1);
                    Monitor.a(1, String.format("enableCustomAudioCapture:%b", var1) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                    if (!TRTCCloudImpl.this.mIsAudioCapturing) {
                        TRTCCloudImpl.this.enableAudioStream(var1);
                    }

                    if (var1) {
                        TRTCCloudImpl.this.setQoSParams();
                        TXCAudioEngineJNI.nativeUseSysAudioDevice(false);
                        TXCAudioEngine.getInstance().startLocalAudio(11, true);
                        TXCAudioEngine.getInstance().enableEncodedDataPackWithTRAEHeaderCallback(true);
                        TXCEventRecorderProxy.a("18446744073709551615", 3003, 11L, -1L, "", 0);
                    } else {
                        TXCAudioEngine.getInstance().stopLocalAudio();
                    }

                    TXCKeyPointReportProxy.a(40050, var1 ? 1 : 0, 1);
                }
            }
        });
    }

    public void sendCustomAudioData(TRTCAudioFrame var1) {
        if (var1 == null) {
            this.apiLog("sendCustomAudioData parameter is null");
        } else {
            final com.tencent.liteav.basic.structs.a var2 = new com.tencent.liteav.basic.structs.a();
            var2.audioData = new byte[var1.data.length];
            System.arraycopy(var1.data, 0, var2.audioData, 0, var1.data.length);
            var2.sampleRate = var1.sampleRate;
            var2.channelsPerSample = var1.channel;
            var2.bitsPerChannel = 16;
            if (0L == var1.timestamp) {
                var2.timestamp = TXCTimeUtil.generatePtsMS();
            } else {
                var2.timestamp = var1.timestamp;
            }

            this.runOnSDKThread(new Runnable() {
                public void run() {
                    if (!TRTCCloudImpl.this.mEnableCustomAudioCapture) {
                        TRTCCloudImpl.this.apiLog("sendCustomAudioData when mEnableCustomAudioCapture is false");
                    } else {
                        TXCAudioEngine.getInstance().sendCustomPCMData(var2);
                    }
                }
            });
        }
    }

    public void playBGM(final String var1, final BGMNotify var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("playBGM");
                TRTCCloudImpl.this.mBGMNotify = var2;
                if (TRTCCloudImpl.this.mBGMNotify != null) {
                    TXCLiveBGMPlayer.getInstance().setOnPlayListener(TRTCCloudImpl.this);
                } else {
                    TXCLiveBGMPlayer.getInstance().setOnPlayListener((e)null);
                }

                TXCLiveBGMPlayer.getInstance().startPlay(var1);
            }
        });
    }

    public void stopBGM() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("stopBGM");
                TXCLiveBGMPlayer.getInstance().stopPlay();
                TRTCCloudImpl.this.mBGMNotify = null;
            }
        });
    }

    public void pauseBGM() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("pauseBGM");
                TXCLiveBGMPlayer.getInstance().pause();
            }
        });
    }

    public void resumeBGM() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("resumeBGM");
                TXCLiveBGMPlayer.getInstance().resume();
            }
        });
    }

    public int getBGMDuration(String var1) {
        return TXCLiveBGMPlayer.getInstance().getBGMDuration(var1);
    }

    public int setBGMPosition(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setBGMPosition " + var1);
                TXCLiveBGMPlayer.getInstance().setBGMPosition(var1);
            }
        });
        return 0;
    }

    public void setMicVolumeOnMixing(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setMicVolume " + var1);
                float var1x = (float)var1 / 100.0F;
                TXCAudioEngine.getInstance().setSoftwareCaptureVolume(var1x);
            }
        });
    }

    public void setBGMVolume(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setBGMVolume " + var1);
                float var1x = (float)var1 / 100.0F;
                TXCLiveBGMPlayer.getInstance().setVolume(var1x);
            }
        });
    }

    public void setBGMPlayoutVolume(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                float var1x = (float)var1 / 100.0F;
                TRTCCloudImpl.this.apiLog("setBGMPlayoutVolume:" + var1 + " fVolume:" + var1x);
                TXCLiveBGMPlayer.getInstance().setPlayoutVolume(var1x);
            }
        });
    }

    public void setBGMPublishVolume(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                float var1x = (float)var1 / 100.0F;
                TRTCCloudImpl.this.apiLog("setBGMPublishVolume " + var1);
                TXCLiveBGMPlayer.getInstance().setPublishVolume(var1x);
            }
        });
    }

    public void setReverbType(final int var1) {
        if (var1 >= 0 && var1 <= 7) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.apiLog("setLocalViewFillMode");
                    TXAudioEffectManagerImpl.getInstance().setVoiceReverbType(TRTCCloudImpl.this.reverbTypes[var1]);
                }
            });
        } else {
            TXCLog.e("TRTCCloudImpl", "reverbType not support :" + var1);
        }
    }

    public boolean setVoiceChangerType(final int var1) {
        if (var1 >= 0 && var1 <= 11) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TXAudioEffectManagerImpl.getInstance().setVoiceChangerType(TRTCCloudImpl.this.voiceChangerTypes[var1]);
                }
            });
            return true;
        } else {
            TXCLog.e("TRTCCloudImpl", "voiceChangerType not support :" + var1);
            return false;
        }
    }

    public TXAudioEffectManager getAudioEffectManager() {
        return TXAudioEffectManagerImpl.getAutoCacheHolder();
    }

    public void onEffectPlayFinish(final int var1) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onEffectPlayFinish -> effectId = " + var1);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onAudioEffectFinished(var1, 0);
                }

            }
        });
    }

    public void onEffectPlayStart(final int var1, final int var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onEffectPlayStart -> effectId = " + var1 + " code = " + var2);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null && var2 < 0) {
                    var1x.onAudioEffectFinished(var1, var2);
                }

            }
        });
    }

    public void playAudioEffect(final TRTCAudioEffectParam var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("playAudioEffect -> effectId = " + var1.effectId + " path = " + var1.path + " publish = " + var1.publish + " loopCount = " + var1.loopCount);
                TXCSoundEffectPlayer.getInstance().playEffectWithId(var1.effectId, var1.path, var1.publish, var1.loopCount);
            }
        });
    }

    public void setAudioEffectVolume(final int var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setAudioEffectVolume -> effectId = " + var1 + " volume = " + var2);
                float var1x = (float)var2 / 100.0F;
                TXCSoundEffectPlayer.getInstance().setVolumeOfEffect(var1, var1x);
            }
        });
    }

    public void stopAudioEffect(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("stopAudioEffect -> effectId = " + var1);
                TXCSoundEffectPlayer.getInstance().stopEffectWithId(var1);
            }
        });
    }

    public void stopAllAudioEffects() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("stopAllAudioEffects");
                TXCSoundEffectPlayer.getInstance().stopAllEffect();
            }
        });
    }

    public void setAllAudioEffectsVolume(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setAllAudioEffectsVolume volume = " + var1);
                float var1x = (float)var1 / 100.0F;
                TXCSoundEffectPlayer.getInstance().setEffectsVolume(var1x);
            }
        });
    }

    public void pauseAudioEffect(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("pauseAudioEffect -> effectId = " + var1);
                TXCSoundEffectPlayer.getInstance().pauseEffectWithId(var1);
            }
        });
    }

    public void resumeAudioEffect(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("resumeAudioEffect -> effectId = " + var1);
                TXCSoundEffectPlayer.getInstance().resumeEffectWithId(var1);
            }
        });
    }

    public void showDebugView(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("showDebugView " + var1);
                TRTCCloudImpl.this.mDebugType = var1;
                final TXCloudVideoView var1x = TRTCCloudImpl.this.mRoomInfo.localView;
                if (var1x != null) {
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                        public void run() {
                            var1x.showVideoDebugLog(var1);
                        }
                    });
                }

                TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1x, UserInfo var2) {
                        final TXCloudVideoView var3 = var2.mainRender.view;
                        final TXCloudVideoView var4 = var2.subRender.view;
                        if (var3 != null || var4 != null) {
                            TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                                public void run() {
                                    if (var3 != null) {
                                        var3.showVideoDebugLog(var1);
                                    }

                                    if (var4 != null) {
                                        var4.showVideoDebugLog(var1);
                                    }

                                }
                            });
                        }

                    }
                });
            }
        });
    }

    public void setDebugViewMargin(final String var1, final TRTCViewMargin var2) {
        if (!TextUtils.isEmpty(var1)) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.apiLog("setDebugViewMargin");
                    final TXCloudVideoView var1x = TRTCCloudImpl.this.mRoomInfo.localView;
                    if (var1x != null && var1.equalsIgnoreCase(var1x.getUserId())) {
                        TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                            public void run() {
                                var1x.setLogMarginRatio(var2.leftMargin, var2.rightMargin, var2.topMargin, var2.bottomMargin);
                            }
                        });
                    }

                    UserInfo var2x = TRTCCloudImpl.this.mRoomInfo.getUser(var1);
                    if (var2x != null) {
                        var2x.debugMargin = var2;
                        final TXCloudVideoView var3 = var2x.mainRender.view;
                        final TXCloudVideoView var4 = var2x.subRender.view;
                        if (var3 != null || var4 != null) {
                            TRTCCloudImpl.this.runOnMainThread(new Runnable() {
                                public void run() {
                                    if (var3 != null) {
                                        var3.setLogMarginRatio(var2.leftMargin, var2.rightMargin, var2.topMargin, var2.bottomMargin);
                                    }

                                    if (var4 != null) {
                                        var4.setLogMarginRatio(var2.leftMargin, var2.rightMargin, var2.topMargin, var2.bottomMargin);
                                    }

                                }
                            });
                        }
                    }

                }
            });
        }
    }

    public void startSpeedTest(final int var1, final String var2, final String var3) {
        if (!TextUtils.isEmpty(var2) && !TextUtils.isEmpty(var3)) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.apiLog("startSpeedTest");
                    TRTCCloudImpl.this.nativeStartSpeedTest(TRTCCloudImpl.this.mNativeRtcContext, var1, var2, var3);
                }
            });
        } else {
            TXCLog.e("TRTCCloudImpl", "startSpeedTest failed with invalid params. userId = " + var2 + ", userSig = " + var3 + " self:" + this.hashCode());
        }
    }

    public void stopSpeedTest() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("stopSpeedTest");
                TRTCCloudImpl.this.nativeStopSpeedTest(TRTCCloudImpl.this.mNativeRtcContext);
            }
        });
    }

    public void startPublishCDNStream(final TRTCPublishCDNParam var1) {
        if (var1 == null) {
            this.apiLog("startPublishCDNStream param is null");
        } else {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.apiLog("startPublishCDNStream");
                    TRTCCloudImpl.this.nativeStartPublishCDNStream(TRTCCloudImpl.this.mNativeRtcContext, var1);
                }
            });
        }
    }

    public void stopPublishing() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("stopPublishing");
                TRTCCloudImpl.this.nativeStopPublishing(TRTCCloudImpl.this.mNativeRtcContext);
            }
        });
    }

    public void stopPublishCDNStream() {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("stopPublishCDNStream");
                TRTCCloudImpl.this.nativeStopPublishCDNStream(TRTCCloudImpl.this.mNativeRtcContext);
            }
        });
    }

    public void startPublishing(final String var1, final int var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("startPublishing streamId:" + var1 + ", streamType:" + var2);
                byte var1x = 2;
                if (var2 == 2) {
                    var1x = 7;
                }

                TRTCCloudImpl.this.nativeStartPublishing(TRTCCloudImpl.this.mNativeRtcContext, var1, var1x);
            }
        });
    }

    public void setMixTranscodingConfig(final TRTCTranscodingConfig var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setMixTranscodingConfig " + var1);
                if (var1 == null) {
                    Monitor.a(1, "cancelLiveMixTranscoding self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                }

                if (var1 != null) {
                    TRTCTranscodingConfigInner var1x = new TRTCTranscodingConfigInner(var1);
                    TRTCCloudImpl.this.nativeSetMixTranscodingConfig(TRTCCloudImpl.this.mNativeRtcContext, var1x);
                } else {
                    TRTCCloudImpl.this.nativeSetMixTranscodingConfig(TRTCCloudImpl.this.mNativeRtcContext, (TRTCTranscodingConfigInner)null);
                }

            }
        });
    }

    public boolean sendCustomCmdMsg(final int var1, byte[] var2, final boolean var3, final boolean var4) {
        if (var2 == null) {
            return false;
        } else {
            final String var5 = null;

            try {
                var5 = new String(var2, "UTF-8");
            } catch (UnsupportedEncodingException var10) {
                TXCLog.e("TRTCCloudImpl", "invalid message data", var10);
            }

            if (this.mCurrentRole == 21) {
                this.apiLog("ignore send custom cmd msg for audience");
                return false;
            } else {
                long var6 = System.currentTimeMillis();
                if (this.mLastSendMsgTimeMs == 0L) {
                    this.mLastSendMsgTimeMs = var6;
                }

                boolean var8 = false;
                if (var6 - this.mLastSendMsgTimeMs < 1000L) {
                    if (this.mSendMsgCount < 30 && this.mSendMsgSize < 8192) {
                        var8 = true;
                        ++this.mSendMsgCount;
                        this.mSendMsgSize += var5.length();
                    } else {
                        TXCLog.e("TRTCCloudImpl", "send msg too more self:" + this.hashCode());
                    }
                } else {
                    var8 = true;
                    this.mLastSendMsgTimeMs = var6;
                    this.mSendMsgCount = 1;
                    this.mSendMsgSize = var5.length();
                }

                if (var8) {
                    this.runOnSDKThread(new Runnable() {
                        public void run() {
                            TRTCCloudImpl.this.nativeSendCustomCmdMsg(TRTCCloudImpl.this.mNativeRtcContext, var1, var5, var3, var4);
                        }
                    });
                }

                return var8;
            }
        }
    }

    public boolean sendSEIMsg(final byte[] var1, final int var2) {
        if (var1 == null) {
            return false;
        } else if (this.mCurrentRole == 21) {
            this.apiLog("ignore send sei msg for audience");
            return false;
        } else {
            long var3 = System.currentTimeMillis();
            if (this.mLastSendMsgTimeMs == 0L) {
                this.mLastSendMsgTimeMs = var3;
            }

            boolean var5 = false;
            if (var3 - this.mLastSendMsgTimeMs < 1000L) {
                if (this.mSendMsgCount < 30 && this.mSendMsgSize < 8192) {
                    var5 = true;
                    ++this.mSendMsgCount;
                    this.mSendMsgSize += var1.length;
                } else {
                    TXCLog.e("TRTCCloudImpl", "send msg too more self:" + this.hashCode());
                }
            } else {
                var5 = true;
                this.mLastSendMsgTimeMs = var3;
                this.mSendMsgCount = 1;
                this.mSendMsgSize = var1.length;
            }

            if (var5) {
                this.runOnSDKThread(new Runnable() {
                    public void run() {
                        TRTCCloudImpl.this.nativeSendSEIMsg(TRTCCloudImpl.this.mNativeRtcContext, var1, var2);
                    }
                });
            }

            return var5;
        }
    }

    public void setAudioFrameListener(final TRTCAudioFrameListener var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("setAudioFrameListener " + var1);
                TRTCCloudImpl.this.mAudioFrameListener = var1;
                if (TRTCCloudImpl.this.mAudioFrameListener == null) {
                    TXCAudioEngine.setPlayoutDataListener((c)null);
                    TXCAudioEngine.getInstance().setAudioCaptureDataListener((d)null);
                    TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                        public void accept(String var1x, UserInfo var2) {
                            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(var2.tinyID), (c)null);
                        }
                    });
                } else {
                    TXCAudioEngine.setPlayoutDataListener(TRTCCloudImpl.this);
                    TXCAudioEngine.getInstance().setAudioCaptureDataListener(TRTCCloudImpl.this);
                    TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                        public void accept(String var1x, UserInfo var2) {
                            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(var2.tinyID), TRTCCloudImpl.this);
                        }
                    });
                }

            }
        });
    }

    public void finalize() throws Throwable {
        super.finalize();

        try {
            this.destroy();
            if (this.mSDKHandler != null) {
                this.mSDKHandler.getLooper().quit();
            }
        } catch (Exception var2) {
        } catch (Error var3) {
        }

    }

    public void onNotifyEvent(final int var1, final Bundle var2) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (var2 != null) {
                    String var1x = var2.getString("EVT_USERID", "");
                    if (!TextUtils.isEmpty(var1x) && !var1x.equalsIgnoreCase("18446744073709551615") && !var1x.equalsIgnoreCase(TRTCCloudImpl.this.mRoomInfo.getTinyId())) {
                        TRTCCloudImpl.this.notifyEventByUserId(var1x, var1, var2);
                    } else {
                        TRTCCloudImpl.this.notifyEvent(TRTCCloudImpl.this.mRoomInfo.getUserId(), var1, var2);
                    }

                }
            }
        });
    }

    public void onEncVideo(TXSNALPacket var1) {
        if (var1 != null) {
            synchronized(this.mNativeLock) {
                this.pushVideoFrame(var1);
            }
        }
    }

    public void onEncVideoFormat(MediaFormat var1) {
    }

    public void onBackgroudPushStop() {
    }

    public void onRequestKeyFrame(final String var1, final int var2) {
        if (!TextUtils.isEmpty(var1)) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.nativeRequestKeyFrame(TRTCCloudImpl.this.mNativeRtcContext, Long.valueOf(var1), var2);
                }
            });
        }
    }

    public void onAudioPlayPcmData(final String var1, final byte[] var2, final long var3, final int var5, final int var6) {
        if (var1 == null) {
            TRTCAudioFrameListener var7 = this.mAudioFrameListener;
            if (var7 != null) {
                TRTCAudioFrame var8 = new TRTCAudioFrame();
                var8.data = var2;
                var8.timestamp = var3;
                var8.sampleRate = var5;
                var8.channel = var6;
                var7.onMixedPlayAudioFrame(var8);
            }
        } else {
            this.runOnListenerThread(new Runnable() {
                public void run() {
                    TRTCAudioFrameListener var1x = TRTCCloudImpl.this.mAudioFrameListener;
                    if (var1x != null) {
                        TRTCAudioFrame var2x = new TRTCAudioFrame();
                        var2x.data = var2;
                        var2x.timestamp = var3;
                        var2x.sampleRate = var5;
                        var2x.channel = var6;

                        try {
                            long var3x = Long.valueOf(var1);
                            var1x.onRemoteUserAudioFrame(var2x, TRTCCloudImpl.this.mRoomInfo.getUserIdByTinyId(var3x));
                        } catch (Exception var5x) {
                            TXCLog.e("TRTCCloudImpl", "onPlayAudioFrame failed." + var5x.getMessage());
                        }
                    }

                }
            });
        }

    }

    public void onAudioJitterBufferError(String var1, int var2, String var3) {
    }

    public void onAudioJitterBufferNotify(final String var1, final int var2, final String var3) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                Bundle var1x = new Bundle();
                var1x.putLong("EVT_ID", (long)var2);
                var1x.putLong("EVT_TIME", System.currentTimeMillis());
                var1x.putString("EVT_MSG", var3);
                TRTCCloudImpl.this.notifyEventByUserId(var1, var2, var1x);
            }
        });
    }

    public void onRecordRawPcmData(byte[] var1, long var2, int var4, int var5, int var6, boolean var7) {
        TRTCAudioFrameListener var8 = this.mAudioFrameListener;
        if (var8 != null) {
            TRTCAudioFrame var9 = new TRTCAudioFrame();
            var9.data = var1;
            var9.timestamp = var2;
            var9.sampleRate = var4;
            var9.channel = var5;
            var8.onCapturedRawAudioFrame(var9);
        }

    }

    public void onRecordPcmData(byte[] var1, long var2, int var4, int var5, int var6) {
        TRTCAudioFrameListener var7 = this.mAudioFrameListener;
        if (var7 != null) {
            TRTCAudioFrame var8 = new TRTCAudioFrame();
            var8.data = var1;
            var8.timestamp = var2;
            var8.sampleRate = var4;
            var8.channel = var5;
            var7.onLocalProcessedAudioFrame(var8);
        }

    }

    public void onRecordEncData(byte[] var1, long var2, int var4, int var5, int var6) {
    }

    public void onRecordError(int var1, String var2) {
        Bundle var4 = new Bundle();
        var4.putString("EVT_USERID", "18446744073709551615");
        var4.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        TXCLog.e("TRTCCloudImpl", "onRecordError code = " + var1 + ":" + var2 + " self:" + this.hashCode());
        if (var1 == -1) {
            short var3 = -1302;
            var4.putInt("EVT_ID", var3);
            this.onNotifyEvent(var3, var4);
        }

        short var5;
        if (var1 == -6) {
            var5 = 2027;
            var4.putInt("EVT_ID", var5);
            this.onNotifyEvent(var5, var4);
        }

        if (var1 == -7) {
            var5 = 2029;
            var4.putInt("EVT_ID", var5);
            this.onNotifyEvent(var5, var4);
        }

    }

    public void onRenderVideoFrame(String var1, int var2, TXSVideoFrame var3) {
        if (var3 != null) {
            TRTCVideoFrame var4 = new TRTCVideoFrame();
            var4.width = var3.width;
            var4.height = var3.height;
            var4.rotation = var3.rotation;
            var4.timestamp = var3.pts;
            int var5 = this.translateStreamType(var2);
            TRTCVideoRenderListener var6 = null;
            String var7 = "";
            boolean var8 = TextUtils.isEmpty(var1) || var1.equalsIgnoreCase("18446744073709551615") || var1.equalsIgnoreCase(this.mRoomInfo.getTinyId());
            if (var8) {
                var7 = this.mRoomInfo.getUserId();
                var4.pixelFormat = this.mRoomInfo.localPixelFormat;
                var4.bufferType = this.mRoomInfo.localBufferType;
                var6 = this.mRoomInfo.localListener;
            } else {
                Iterator var9 = this.mRenderListenerMap.entrySet().iterator();

                while(var9.hasNext()) {
                    Entry var10 = (Entry)var9.next();
                    TRTCCloudImpl.RenderListenerAdapter var11 = (TRTCCloudImpl.RenderListenerAdapter)var10.getValue();
                    if (var11 != null && var1.equalsIgnoreCase(((TRTCCloudImpl.RenderListenerAdapter)var10.getValue()).strTinyID)) {
                        var4.pixelFormat = var11.pixelFormat;
                        var4.bufferType = var11.bufferType;
                        var6 = var11.listener;
                        var7 = (String)var10.getKey();
                        break;
                    }
                }
            }

            if (var6 != null) {
                if (var4.bufferType == 1) {
                    if (var3.buffer == null) {
                        var3.loadYUVBufferFromGL();
                    }

                    var4.buffer = var3.buffer;
                } else if (var4.bufferType == 2) {
                    var4.data = var3.data;
                    if (var4.data == null) {
                        var4.data = new byte[var3.width * var3.height * 3 / 2];
                        var3.loadYUVArray(var4.data);
                    }
                } else if (var4.bufferType == 3) {
                    if (var3.eglContext == null) {
                        return;
                    }

                    var4.texture = new TRTCTexture();
                    var4.texture.textureId = var3.textureId;
                    if (var3.eglContext instanceof EGLContext) {
                        var4.texture.eglContext10 = (EGLContext)var3.eglContext;
                    } else if (var3.eglContext instanceof android.opengl.EGLContext) {
                        var4.texture.eglContext14 = (android.opengl.EGLContext)var3.eglContext;
                    }
                }

                var6.onRenderVideoFrame(var7, var5, var4);
                if (this.mRoomInfo.enableCustomPreprocessor && var8) {
                    if (var4.bufferType == 2) {
                        var3.data = var4.data;
                    } else if (var4.bufferType == 3) {
                        var3.textureId = var4.texture.textureId;
                    }
                }
            }

        }
    }

    public void onPlayStart() {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                BGMNotify var1 = TRTCCloudImpl.this.mBGMNotify;
                if (var1 != null) {
                    var1.onBGMStart(0);
                }

            }
        });
    }

    public void onPlayEnd(final int var1) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                BGMNotify var1x = TRTCCloudImpl.this.mBGMNotify;
                if (var1x != null) {
                    var1x.onBGMComplete(var1);
                }

            }
        });
    }

    public void onPlayProgress(final long var1, final long var3) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                BGMNotify var1x = TRTCCloudImpl.this.mBGMNotify;
                if (var1x != null) {
                    var1x.onBGMProgress(var1, var3);
                }

            }
        });
    }

    protected native long nativeCreateContext(int var1, int var2, int var3);

    protected native void nativeDestroyContext(long var1);

    protected native void nativeInit(long var1, int var3, String var4, String var5, byte[] var6);

    protected native int nativeEnterRoom(long var1, long var3, String var5, String var6, String var7, int var8, int var9, int var10, int var11, int var12, String var13, String var14, int var15, String var16, String var17);

    protected native int nativeExitRoom(long var1);

    private native void nativeUpdatePrivateMapKey(long var1, String var3);

    protected native int nativeSetPriorRemoteVideoStreamType(long var1, int var3);

    protected native int nativeAddUpstream(long var1, int var3);

    private native int nativeRemoveUpstream(long var1, int var3);

    private native void nativeMuteUpstream(long var1, int var3, boolean var4);

    private native void nativeEnableBlackStream(long var1, boolean var3);

    private native void nativeEnableSmallStream(long var1, boolean var3);

    private native int nativeRequestDownStream(long var1, long var3, int var5, boolean var6);

    private native int nativeCancelDownStream(long var1, long var3, int var5, boolean var6);

    private native void nativePushVideo(long var1, int var3, int var4, int var5, byte[] var6, long var7, long var9, long var11, long var13, long var15);

    private native void nativeSetVideoQuality(long var1, int var3, int var4);

    private native void nativeRequestKeyFrame(long var1, long var3, int var5);

    private native void nativeSetVideoEncoderConfiguration(long var1, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, int var10);

    private native void nativeSetAudioEncodeConfiguration(long var1, int var3, int var4, int var5, int var6);

    private native void nativeSetDataReportDeviceInfo(String var1, String var2, int var3);

    private native void nativeSendCustomCmdMsg(long var1, int var3, String var4, boolean var5, boolean var6);

    private native void nativeSendSEIMsg(long var1, byte[] var3, int var4);

    private native boolean nativeSetSEIPayloadType(long var1, int var3);

    private native void nativeSendJsonCmd(long var1, String var3, String var4);

    private native void nativeStartSpeedTest(long var1, int var3, String var4, String var5);

    private native void nativeStopSpeedTest(long var1);

    private native int nativeConnectOtherRoom(long var1, String var3);

    private native int nativeDisconnectOtherRoom(long var1);

    protected native void nativeSetMixTranscodingConfig(long var1, TRTCTranscodingConfigInner var3);

    private native void nativeStartPublishCDNStream(long var1, TRTCPublishCDNParam var3);

    private native void nativeStopPublishCDNStream(long var1);

    private native void nativeStartPublishing(long var1, String var3, int var4);

    private native void nativeStopPublishing(long var1);

    private native void nativeChangeRole(long var1, int var3);

    private native void nativeReenterRoom(long var1, int var3);

    private void onRequestToken(int var1, String var2, final long var3, final byte[] var5) {
        this.apiLog("onRequestToken " + var3 + "," + var1 + ", " + var2);
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.mRoomInfo.setTinyId(String.valueOf(var3));
                TRTCCloudImpl.this.mRoomInfo.setToken(TRTCCloudImpl.this.mContext, var5);
            }
        });
    }

    private void onRequestAccIP(int var1, String var2, boolean var3) {
        this.apiLog("onRequestAccIP err:" + var1 + " " + var2 + " isAcc:" + var3);
        if (var1 == 0) {
            String var4 = var3 ? "connect ACC" : "connect PROXY";
            Bundle var5 = new Bundle();
            var5.putLong("EVT_ID", (long)var1);
            var5.putLong("EVT_TIME", System.currentTimeMillis());
            var5.putString("EVT_MSG", var4);
            var5.putInt("EVT_STREAM_TYPE", 2);
            this.notifyEvent(this.mRoomInfo.getUserId(), var1, var5);
        }

    }

    protected void onEnterRoom(final int var1, final String var2) {
        this.apiLog("onEnterRoom " + var1 + ", " + var2);
        Monitor.a(1, String.format("onEnterRoom err:%d msg:%s", var1, var2) + " self:" + this.hashCode(), "", 0);
        if (var1 == 0) {
            TXCEventRecorderProxy.a("18446744073709551615", 5003, 1L, -1L, "", 0);
        } else {
            TXCEventRecorderProxy.a("18446744073709551615", 5003, 0L, -1L, "", 0);
        }

        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (var1 == 0) {
                    TRTCCloudImpl.this.mRoomState = 2;
                    TRTCCloudImpl.this.mRoomInfo.networkStatus = 3;
                    if (TRTCCloudImpl.this.mRoomInfo.muteLocalVideo) {
                        TRTCCloudImpl.this.muteUpstream(2, TRTCCloudImpl.this.mRoomInfo.muteLocalVideo);
                    }

                    if (TRTCCloudImpl.this.mRoomInfo.muteLocalAudio) {
                        TRTCCloudImpl.this.muteUpstream(1, TRTCCloudImpl.this.mRoomInfo.muteLocalAudio);
                    }

                    TRTCCloudImpl.this.notifyEvent(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, (String)"Enter room success");
                } else {
                    TRTCCloudImpl.this.exitRoomInternal(false, "enter room failed");
                    TRTCCloudImpl.this.notifyEvent(TRTCCloudImpl.this.mRoomInfo.getUserId(), var1, "Enter room fail " + var2);
                    switch(var1) {
                        case -3320:
                        case -3319:
                        case -3318:
                        case -3317:
                        case -3316:
                            TXCKeyPointReportProxy.b(var1);
                    }
                }

            }
        });
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TXCKeyPointReportProxy.b(30001, var1);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                long var2 = TRTCCloudImpl.this.mRoomInfo.getRoomElapsed();
                if (var1x != null) {
                    if (var1 == 0) {
                        var1x.onEnterRoom(var2);
                    } else {
                        var1x.onEnterRoom((long)var1);
                    }
                }

            }
        });
    }

    private void onExitRoom(final int var1, String var2) {
        this.apiLog("onExitRoom " + var1 + ", " + var2);
        Monitor.a(1, String.format("onExitRoom err:%d msg:%s", var1, var2) + " self:" + this.hashCode(), "", 0);
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mIsExitOldRoom) {
                    TRTCCloudImpl.this.mIsExitOldRoom = false;
                    TRTCCloudImpl.this.apiLog("exit no current room, ignore onExitRoom.");
                } else {
                    if (TRTCCloudImpl.this.mRoomInfo.isMicStard()) {
                        TRTCCloudImpl.this.mRoomInfo.setRoomExit(true, var1);
                        TRTCCloudImpl.this.apiLog("onExitRoom delay 2s when mic is not release.");
                        TRTCCloudImpl.this.runOnSDKThread(new Runnable() {
                            public void run() {
                                if (TRTCCloudImpl.this.mRoomInfo.isRoomExit()) {
                                    TRTCCloudImpl.this.apiLog("force onExitRoom after 2s");
                                    final int var1x = TRTCCloudImpl.this.mRoomInfo.getRoomExitCode();
                                    TRTCCloudImpl.this.mRoomInfo.setRoomExit(false, 0);
                                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                        public void run() {
                                            TRTCCloudListener var1xx = TRTCCloudImpl.this.mTRTCListener;
                                            if (var1xx != null) {
                                                var1xx.onExitRoom(var1x);
                                            }

                                        }
                                    });
                                }

                            }
                        }, 2000);
                    } else {
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                                if (var1x != null) {
                                    var1x.onExitRoom(var1);
                                }

                            }
                        });
                    }

                }
            }
        });
    }

    private void onKickOut(final int var1, final String var2) {
        this.apiLog("onKickOut " + var1 + ", " + var2);
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.exitRoomInternal(false, "onKickOut " + var2);
                TRTCCloudImpl.this.onExitRoom(var1, var2);
            }
        });
    }

    private void onConnectOtherRoom(final String var1, final int var2, final String var3) {
        this.apiLog("onConnectOtherRoom " + var1 + ", " + var2 + ", " + var3);
        Monitor.a(1, String.format("onConnectOtherRoom userId:%s err:%d, msg:%s", var1, var2, var3) + " self:" + this.hashCode(), "", 0);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mTRTCListener != null) {
                    TRTCCloudImpl.this.mTRTCListener.onConnectOtherRoom(var1, var2, var3);
                }

            }
        });
    }

    private void onDisConnectOtherRoom(final int var1, final String var2) {
        this.apiLog("onDisConnectOtherRoom " + var1 + ", " + var2);
        Monitor.a(1, String.format("onDisConnectOtherRoom err:%d, msg:%s", var1, var2) + " self:" + this.hashCode(), "", 0);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mTRTCListener != null) {
                    TRTCCloudImpl.this.mTRTCListener.onDisConnectOtherRoom(var1, var2);
                }

            }
        });
    }

    private void onCallExperimentalAPI(int var1, String var2) {
        this.apiLog("onCallExperimentalAPI " + var1 + ", " + var2);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mTRTCListener != null) {
                }

            }
        });
    }

    private void onRequestDownStream(final int var1, final String var2, final long var3, final int var5) {
        if (var1 != 0) {
            this.runOnListenerThread(new Runnable() {
                public void run() {
                    TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                    if (var1x != null) {
                        var1x.onError(var1, var2, (Bundle)null);
                    }

                }
            });
        } else {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                        public void accept(String var1, UserInfo var2) {
                            if (var5 != 1 && var3 == var2.tinyID) {
                                TRTCCloudImpl.this.apiLog("onRequestDownStream " + var2.tinyID + ", " + var2.userID + ", " + var5);
                                if (var5 == 7) {
                                    if (var2.subRender.render != null && var2.subRender.render.getStreamType() != var5) {
                                        var2.subRender.render.stopVideo();
                                        var2.subRender.render.setStreamType(var5);
                                        var2.subRender.render.startVideo();
                                    }
                                } else if (var2.mainRender.render != null && var2.mainRender.render.getStreamType() != var5) {
                                    var2.mainRender.render.stopVideo();
                                    var2.mainRender.render.setStreamType(var5);
                                    var2.mainRender.render.startVideo();
                                    TXCKeyPointReportProxy.a(String.valueOf(var2.tinyID), 40038, 0L, var5);
                                }
                            }

                        }
                    });
                }
            });
        }
    }

    private TXCRenderAndDec createRender(long var1, int var3) {
        TXCRenderAndDec var4 = new TXCRenderAndDec(this.mContext);
        var4.setID(String.valueOf(var1));
        var4.setVideoRender(new com.tencent.liteav.renderer.a());
        var4.setStreamType(var3);
        var4.setNotifyListener(this);
        var4.setRenderAndDecDelegate(this);
        var4.setRenderMode(0);
        var4.enableDecoderChange(this.mPerformanceMode != 1);
        var4.enableRestartDecoder(this.mRoomInfo.enableRestartDecoder);
        var4.enableLimitDecCache(this.mVideoServerConfig.enableHWVUI);
        this.applyRenderConfig(var4);
        return var4;
    }

    protected void onAVMemberEnter(final long var1, final String var3, final int var4, final int var5) {
        final WeakReference var6 = new WeakReference(this);
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mRoomState == 0) {
                    TRTCCloudImpl.this.apiLog("ignore onAVMemberEnter when out room.");
                } else {
                    TRTCCloudImpl var1x = (TRTCCloudImpl)var6.get();
                    if (var1x != null) {
                        UserInfo var2 = TRTCCloudImpl.this.mRoomInfo.getUser(var3);
                        if (var2 != null) {
                            TRTCCloudImpl.this.apiLog(" user " + var3 + "enter room when user is in room " + var1);
                        }

                        String var3x = String.valueOf(var1);
                        if (var2 == null) {
                            var2 = TRTCCloudImpl.this.createUserInfo(var3);
                        }

                        TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(var3x, TRTCCloudImpl.this);
                        if (TRTCCloudImpl.this.mAudioFrameListener != null) {
                            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(var3x, TRTCCloudImpl.this);
                        }

                        TXCAudioEngine.getInstance().startRemoteAudio(var3x, true);
                        TXCAudioEngine.getInstance().muteRemoteAudio(var3x, var2.mainRender.muteAudio);
                        TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(var3x, var2.muteAudioInSpeaker);
                        if (var2.mainRender.muteAudio) {
                            TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var1, 1, true);
                        }

                        TXCRenderAndDec var4x = TRTCCloudImpl.this.createRender(var1, TRTCCloudImpl.this.mPriorStreamType);
                        TRTCCloudImpl.RenderListenerAdapter var5x = (TRTCCloudImpl.RenderListenerAdapter)TRTCCloudImpl.this.mRenderListenerMap.get(var3);
                        if (var5x != null) {
                            var5x.strTinyID = var3x;
                            if (var5x.listener != null) {
                                var4x.setVideoFrameListener(TRTCCloudImpl.this, TRTCCloudImpl.this.getPixelFormat(var5x.pixelFormat));
                            }
                        }

                        var2.tinyID = var1;
                        var2.userID = var3;
                        var2.terminalType = var4;
                        var2.streamState = var5;
                        var2.mainRender.render = var4x;
                        var2.mainRender.tinyID = var1;
                        var2.streamType = TRTCCloudImpl.this.mPriorStreamType;
                        if (var2.mainRender.view != null) {
                            TRTCCloudImpl.this.setRenderView(var3, var2.mainRender, var2.mainRender.view, var2.debugMargin);
                            TRTCCloudImpl.this.apiLog(String.format("startRemoteView when user enter userID:%s tinyID:%d streamType:%d", var3, var2.tinyID, var2.streamType));
                            TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(var2.tinyID), var2.streamType, 0, "Start watching " + var3);
                            TRTCCloudImpl.this.startRemoteRender(var2.mainRender.render, var2.streamType);
                            TXCKeyPointReportProxy.a(String.valueOf(var2.tinyID), 40021, 0L, var2.streamType);
                            if (!var2.mainRender.muteVideo) {
                                TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, var2.streamType, true);
                            } else {
                                TRTCCloudImpl.this.nativeCancelDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, var2.streamType, true);
                            }
                        }

                        TXCRenderAndDec var6x = TRTCCloudImpl.this.createRender(var1, 7);
                        var2.subRender.render = var6x;
                        var2.subRender.tinyID = var1;
                        var2.subRender.muteVideo = TRTCCloudImpl.this.mRoomInfo.muteRemoteVideo;
                        if (var2.subRender.view != null) {
                            TRTCCloudImpl.this.setRenderView(var3, var2.subRender, var2.subRender.view, var2.debugMargin);
                            TRTCCloudImpl.this.apiLog(String.format("onUserScreenAvailable when user enter userID:%s tinyID:%d streamType:%d", var3, var2.tinyID, 7));
                            Monitor.a(1, String.format("startRemoteSubStreamView userID:%s", var3) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(var2.tinyID), 7, 0, "Start watching " + var3);
                            TRTCCloudImpl.this.startRemoteRender(var2.subRender.render, 7);
                            TXCKeyPointReportProxy.a(String.valueOf(var2.tinyID), 40021, 0L, 7);
                            if (!var2.subRender.muteVideo) {
                                TRTCCloudImpl.this.nativeRequestDownStream(TRTCCloudImpl.this.mNativeRtcContext, var2.tinyID, 7, true);
                            }
                        }

                        TRTCCloudImpl.this.mRoomInfo.addUserInfo(var3, var2);
                        TRTCCloudImpl.this.apiLog("onAVMemberEnter " + var1 + ", " + var3 + ", " + var5);
                        final TRTCCloudListener var7 = TRTCCloudImpl.this.mTRTCListener;
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                if (var7 != null) {
                                    var7.onUserEnter(var3);
                                }

                            }
                        });
                        final boolean var8 = TRTCRoomInfo.hasAudio(var5) && !TRTCRoomInfo.isMuteAudio(var5);
                        if (var8) {
                            TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                public void run() {
                                    if (var7 != null) {
                                        var7.onUserAudioAvailable(var3, var8);
                                    }

                                    Monitor.a(2, String.format("onUserAudioAvailable userID:%s, bAvailable:%b", var3, var8) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                }
                            });
                            TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, String.format("[%s]audio Available[true]", var3));
                        }

                        final boolean var9 = (TRTCRoomInfo.hasMainVideo(var5) || TRTCRoomInfo.hasSmallVideo(var5)) && !TRTCRoomInfo.isMuteMainVideo(var5);
                        if (var9 && TRTCCloudImpl.this.mRoomInfo.hasRecvFirstIFrame(var1)) {
                            TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                public void run() {
                                    TXCLog.i("TRTCCloudImpl", "notify onUserVideoAvailable:" + var1 + " [" + var9 + "] by bit state. self:" + TRTCCloudImpl.this.hashCode());
                                    if (var7 != null) {
                                        var7.onUserVideoAvailable(var3, var9);
                                    }

                                    Monitor.a(2, String.format("onUserVideoAvailable userID:%s, bAvailable:%b", var3, var9) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                }
                            });
                            TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, String.format("[%s]video Available[true]", var3));
                        }

                        final boolean var10 = TRTCRoomInfo.hasSubVideo(var5) && !TRTCRoomInfo.isMuteSubVideo(var5);
                        if (var10) {
                            TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                public void run() {
                                    if (var7 != null) {
                                        var7.onUserSubStreamAvailable(var3, var10);
                                    }

                                    Monitor.a(2, String.format("onUserSubStreamAvailable userID:%s, bAvailable:%b", var3, var10) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                }
                            });
                            TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, String.format("[%s]subvideo Available[true]", var3));
                        }

                        TRTCCloudImpl.this.notifyEvent(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, (String)String.format("[%s]enter room", var3));
                    }
                }
            }
        });
    }

    protected void onAVMemberExit(final long var1, final String var3, int var4, final int var5) {
        final WeakReference var6 = new WeakReference(this);
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mRoomState == 0) {
                    TRTCCloudImpl.this.apiLog("ignore onAVMemberExit when out room.");
                } else {
                    TRTCCloudImpl var1x = (TRTCCloudImpl)var6.get();
                    if (var1x != null) {
                        UserInfo var2 = TRTCCloudImpl.this.mRoomInfo.getUser(var3);
                        if (null != var2) {
                            TRTCCloudImpl.this.stopRemoteRender(var2);
                            TRTCCloudImpl.this.mRoomInfo.removeRenderInfo(var2.userID);
                        } else {
                            TRTCCloudImpl.this.apiLog("user " + var3 + " exit room when user is not in room " + var1);
                        }

                        TXCAudioEngine.getInstance().stopRemoteAudio(String.valueOf(var1));
                        TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(var1), (c)null);
                        TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(String.valueOf(var1), (com.tencent.liteav.audio.b)null);
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                TRTCCloudImpl.this.apiLog("onAVMemberExit " + var1 + ", " + var3 + ", " + var5);
                                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                                if (var1x != null) {
                                    if (TRTCRoomInfo.hasAudio(var5) && !TRTCRoomInfo.isMuteAudio(var5)) {
                                        var1x.onUserAudioAvailable(var3, false);
                                        TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, String.format("[%s]audio Available[%b]", var3, false));
                                        Monitor.a(2, String.format("onUserAudioAvailable userID:%s, bAvailable:%b", var3, false) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                    }

                                    if ((TRTCRoomInfo.hasMainVideo(var5) || TRTCRoomInfo.hasSmallVideo(var5)) && !TRTCRoomInfo.isMuteMainVideo(var5)) {
                                        var1x.onUserVideoAvailable(var3, false);
                                        TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, String.format("[%s]video Available[%b]", var3, false));
                                        Monitor.a(2, String.format("onUserVideoAvailable userID:%s, bAvailable:%b", var3, false) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                    }

                                    if (TRTCRoomInfo.hasSubVideo(var5) && !TRTCRoomInfo.isMuteSubVideo(var5)) {
                                        var1x.onUserSubStreamAvailable(var3, false);
                                        TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, String.format("[%s]subVideo Available[%b]", var3, false));
                                        Monitor.a(2, String.format("onUserSubStreamAvailable userID:%s, bAvailable:%b", var3, false) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                    }

                                    var1x.onUserExit(var3, 0);
                                }

                            }
                        });
                    }
                }
            }
        });
        this.notifyEvent(this.mRoomInfo.getUserId(), 0, (String)String.format("[%s]leave room", var3));
    }

    private void onAVMemberChange(final long var1, final String var3, int var4, final int var5, final int var6) {
        final WeakReference var7 = new WeakReference(this);
        this.runOnSDKThread(new Runnable() {
            public void run() {
                if (TRTCCloudImpl.this.mRoomState == 0) {
                    TRTCCloudImpl.this.apiLog("ignore onAVMemberChange when out room");
                } else {
                    TRTCCloudImpl var1x = (TRTCCloudImpl)var7.get();
                    if (var1x != null) {
                        TRTCCloudImpl.this.apiLog("onAVMemberChange " + var1 + ", " + var3 + ", old state:" + var6 + ", new state:" + var5);
                        UserInfo var2 = TRTCCloudImpl.this.mRoomInfo.getUser(var3);
                        if (var2 != null) {
                            var2.streamState = var5;
                            TRTCCloudImpl.this.checkUserState(var3, var1, var5, var6);
                        }

                    }
                }
            }
        });
    }

    private void onWholeMemberEnter(long var1, final String var3) {
        final WeakReference var4 = new WeakReference(this);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl var1 = (TRTCCloudImpl)var4.get();
                if (var1 != null) {
                    TRTCCloudListener var2 = TRTCCloudImpl.this.mTRTCListener;
                    if (var2 != null) {
                        var2.onRemoteUserEnterRoom(var3);
                    }

                }
            }
        });
    }

    private void onWholeMemberExit(long var1, final String var3, final int var4) {
        final WeakReference var5 = new WeakReference(this);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl var1 = (TRTCCloudImpl)var5.get();
                if (var1 != null) {
                    TRTCCloudListener var2 = TRTCCloudImpl.this.mTRTCListener;
                    if (var2 != null) {
                        var2.onRemoteUserLeaveRoom(var3, var4);
                    }

                }
            }
        });
    }

    private void onNotify(long var1, int var3, int var4, String var5) {
        this.apiLog(var1 + " event " + var4 + ", " + var5);
        String var6 = String.valueOf(var1);
        Bundle var7 = new Bundle();
        var7.putLong("EVT_ID", (long)var4);
        var7.putLong("EVT_TIME", System.currentTimeMillis());
        var7.putString("EVT_MSG", var5);
        var7.putInt("EVT_STREAM_TYPE", var3);
        if (!TextUtils.isEmpty(var6) && var1 != 0L && !var6.equalsIgnoreCase("18446744073709551615") && !var6.equalsIgnoreCase(this.mRoomInfo.getTinyId())) {
            this.notifyLogByUserId(String.valueOf(var1), var3, var4, var5);
        } else {
            this.notifyEvent(this.mRoomInfo.getUserId(), var4, var7);
        }

    }

    public void onAudioQosChanged(int var1, int var2, int var3) {
        this.onAudioQosChanged(this, var1, var2, var3);
    }

    public void onAudioQosChanged(TRTCCloudImpl var1, final int var2, final int var3, final int var4) {
        if (this.isPublishingInCloud(var1, 1)) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TXCAudioEngine.getInstance().setAudioEncoderParam(var2, var3);
                    TXCAudioEngine.getInstance().setEncoderFECPercent((float)var4);
                }
            });
        }
    }

    public void onVideoQosChanged(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        this.onVideoQosChanged(this, var1, var2, var3, var4, var5, var6, var7);
    }

    public void onVideoQosChanged(TRTCCloudImpl var1, final int var2, final int var3, final int var4, final int var5, final int var6, final int var7, final int var8) {
        if (this.isPublishingInCloud(var1, var2)) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.mCaptureAndEnc.a(var2, var3, var4, var5, var6, var7, var8);
                    if (var2 == 2) {
                        int var1 = var3 > var4 ? 0 : 1;
                        if (TRTCCloudImpl.this.mConfig.l != var1 && var3 != var4) {
                            TRTCCloudImpl.this.mConfig.l = var1;
                            TRTCCloudImpl.this.updateOrientation();
                        }
                    }

                }
            });
        }
    }

    public void onIdrFpsChanged(int var1) {
        this.onIdrFpsChanged(this, var1);
    }

    public void onIdrFpsChanged(TRTCCloudImpl var1, final int var2) {
        if (this.isPublishingInCloud(var1, 2)) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.mCaptureAndEnc.c(var2);
                }
            });
        }
    }

    private void onVideoBlockThresholdChanged(final int var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1x, UserInfo var2) {
                        if (var2.mainRender.render != null) {
                            var2.mainRender.render.setBlockInterval(var1);
                        }

                        if (var2.subRender.render != null) {
                            var2.subRender.render.setBlockInterval(var1);
                        }

                    }
                });
            }
        });
    }

    private void onConnectionLost() {
        this.mRoomInfo.networkStatus = 1;
        this.notifyEvent(this.mRoomInfo.getUserId(), 0, (String)"Network anomaly.");
        Monitor.a(1, "onConnectionLost self:" + this.hashCode(), "", 0);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                if (var1 != null) {
                    var1.onConnectionLost();
                }

            }
        });
    }

    private void onTryToReconnect() {
        this.mRoomInfo.networkStatus = 2;
        this.notifyEvent(this.mRoomInfo.getUserId(), 0, (String)"Retry enter room.");
        Monitor.a(1, "onTryToReconnect self:" + this.hashCode(), "", 0);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                if (var1 != null) {
                    var1.onTryToReconnect();
                }

            }
        });
    }

    private void onConnectionRecovery() {
        this.mRoomInfo.networkStatus = 3;
        this.notifyEvent(this.mRoomInfo.getUserId(), 0, (String)"Network recovered. Successfully re-enter room");
        Monitor.a(1, "onConnectionRecovery self:" + this.hashCode(), "", 0);
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                if (var1 != null) {
                    var1.onConnectionRecovery();
                }

            }
        });
    }

    private void onSendCustomCmdMsgResult(int var1, int var2, int var3, String var4) {
    }

    private void onRecvCustomCmdMsg(final String var1, long var2, final int var4, final int var5, final String var6, final boolean var7, final int var8, long var9) {
        long var11 = System.currentTimeMillis();
        ++this.mRecvCustomCmdMsgCountInPeriod;
        if (var11 - this.mLastLogCustomCmdMsgTs > 10000L) {
            TXCLog.i("TRTCCloudImpl", "onRecvMsg. tinyId=" + var2 + ", streamId = " + var4 + ", msg = " + var6 + ", recvTime = " + var9 + ", recvCustomMsgCountInPeriod = " + this.mRecvCustomCmdMsgCountInPeriod + " self:" + this.hashCode());
            this.mLastLogCustomCmdMsgTs = var11;
            this.mRecvCustomCmdMsgCountInPeriod = 0L;
        }

        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    try {
                        var1x.onRecvCustomCmdMsg(var1, var4, var5, var6.getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException var3) {
                        TXCLog.e("TRTCCloudImpl", "onRecvCustomCmdMsg failed.", var3);
                    }

                    if (var7 && var8 > 0) {
                        var1x.onMissCustomCmdMsg(var1, var4, -1, var8);
                    }
                }

            }
        });
    }

    private void onRecvSEIMsg(final long var1, final byte[] var3) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    try {
                        String var2 = TRTCCloudImpl.this.mRoomInfo.getUserIdByTinyId(var1);
                        if (var2 != null) {
                            long var3x = System.currentTimeMillis();
                            TRTCCloudImpl.this.mRecvSEIMsgCountInPeriod++;
                            if (var3x - TRTCCloudImpl.this.mLastLogSEIMsgTs > 10000L) {
                                TXCLog.i("TRTCCloudImpl", "onRecvSEIMsg. userId=" + var2 + ", message = " + new String(var3) + ", recvSEIMsgCountInPeriod = " + TRTCCloudImpl.this.mRecvSEIMsgCountInPeriod + " self:" + TRTCCloudImpl.this.hashCode());
                                TRTCCloudImpl.this.mLastLogSEIMsgTs = var3x;
                                TRTCCloudImpl.this.mRecvSEIMsgCountInPeriod = 0L;
                            }

                            var1x.onRecvSEIMsg(var2, var3);
                        } else {
                            TXCLog.i("TRTCCloudImpl", "onRecvSEIMsg Error, user id is null for tinyId=" + var1 + " self:" + TRTCCloudImpl.this.hashCode());
                        }
                    } catch (Exception var5) {
                        TXCLog.e("TRTCCloudImpl", "onRecvSEIMsg failed.", var5);
                    }
                }

            }
        });
    }

    private void onSpeedTest(final String var1, final int var2, final float var3, final float var4, final int var5, final int var6) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    TRTCSpeedTestResult var2x = new TRTCSpeedTestResult();
                    var2x.ip = var1;
                    var2x.rtt = var2;
                    var2x.upLostRate = var3;
                    var2x.downLostRate = var4;
                    if (var3 >= var4) {
                        var2x.quality = TRTCCloudImpl.this.getNetworkQuality(var2, (int)(var3 * 100.0F));
                    } else {
                        var2x.quality = TRTCCloudImpl.this.getNetworkQuality(var2, (int)(var4 * 100.0F));
                    }

                    var1x.onSpeedTest(var2x, var5, var6);
                    TRTCCloudImpl.this.apiLog(String.format("SpeedTest progress %d/%d, result: %s", var5, var6, var2x.toString()));
                }

            }
        });
    }

    public void onVideoConfigChanged(int var1, boolean var2) {
        this.onVideoConfigChanged(this, var1, var2);
    }

    public void onVideoConfigChanged(TRTCCloudImpl var1, final int var2, final boolean var3) {
        if (this.isPublishingInCloud(var1, var2)) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    if (var2 == 2) {
                        TRTCCloudImpl.this.mCaptureAndEnc.g(var3);
                    }

                }
            });
        }
    }

    private void onRecvFirstAudio(long var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
            }
        });
    }

    private void onRecvFirstVideo(final long var1, int var3) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                int var1x = TRTCCloudImpl.this.mRoomInfo.recvFirstIFrame(var1);
                UserInfo var2 = null;

                final String var3;
                try {
                    var3 = TRTCCloudImpl.this.mRoomInfo.getUserIdByTinyId(var1);
                    if (var3 != null) {
                        var2 = TRTCCloudImpl.this.mRoomInfo.getUser(var3);
                    }
                } catch (Exception var4) {
                    TXCLog.e("TRTCCloudImpl", "get user info failed.", var4);
                }

                TRTCCloudImpl.this.apiLog("onRecvFirstVideo " + var1 + ", " + var1x);
                if (var2 != null && var1x <= 1) {
                    var3 = var2.userID;
                    if ((TRTCRoomInfo.hasMainVideo(var2.streamState) || TRTCRoomInfo.hasSmallVideo(var2.streamState)) && !TRTCRoomInfo.isMuteMainVideo(var2.streamState)) {
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                                TXCLog.i("TRTCCloudImpl", "notify onUserVideoAvailable:" + var1 + " [true] by IDR. self:" + TRTCCloudImpl.this.hashCode());
                                if (var1x != null) {
                                    var1x.onUserVideoAvailable(var3, true);
                                    TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, String.format("[%s]video Available[%b]", var3, true));
                                }

                            }
                        });
                    }

                }
            }
        });
    }

    private void onStartPublishing(final int var1, final String var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onStartPublishing " + var1 + ", " + var2);
                Monitor.a(1, String.format("onStartPublishing err:%d, msg:%s", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onStartPublishing(var1, var2);
                }

            }
        });
    }

    private void onStopPublishing(final int var1, final String var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onStopPublishing " + var1 + ", " + var2);
                Monitor.a(1, String.format("onStopPublishing err:%d, msg:%s", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onStopPublishing(var1, var2);
                }

            }
        });
    }

    private void onStreamPublished(final int var1, final String var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onStreamPublished " + var1 + ", " + var2);
                Monitor.a(1, String.format("onStreamPublished err:%d, msg:%s", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onStartPublishCDNStream(var1, var2);
                }

            }
        });
    }

    private void onStreamUnpublished(final int var1, final String var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onStreamUnpublished " + var1 + ", " + var2);
                Monitor.a(1, String.format("onStreamUnpublished err:%d, msg:%s", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onStopPublishCDNStream(var1, var2);
                }

            }
        });
    }

    private void onTranscodingUpdated(final int var1, final String var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onTranscodingUpdated " + var1 + ", " + var2);
                Monitor.a(1, String.format("onTranscodingUpdated err:%d, msg:%s", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onSetMixTranscodingConfig(var1, var2);
                }

            }
        });
    }

    protected void onCancelTranscoding(final int var1, final String var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                Monitor.a(1, String.format("onCancelTranscoding err:%d, msg:%s", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onSetMixTranscodingConfig(var1, var2);
                }

            }
        });
    }

    private void onChangeRole(final int var1, final String var2) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                if (var1 == 0) {
                    TRTCCloudImpl.this.mCurrentRole = TRTCCloudImpl.this.mTargetRole;
                } else {
                    TRTCCloudImpl.this.mCurrentRole = 21;
                    TRTCCloudImpl.this.mTargetRole = 21;
                }

                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onSwitchRole(var1, var2);
                }

                TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1x, UserInfo var2x) {
                        if (var2x.mainRender.render != null) {
                            TRTCCloudImpl.this.applyRenderPlayStrategy(var2x.mainRender.render, var2x.mainRender.render.getConfig());
                        }

                    }
                });
                TRTCCloudImpl.this.notifyEvent(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, (String)("onChangeRole:" + var1));
                Monitor.a(1, String.format("onChangeRole err:%d, msg:%s", var1, var2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
            }
        });
    }

    protected void onSendFirstLocalVideoFrame(final int var1) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onSendFirstLocalVideoFrame " + var1);
                int var1x = TRTCCloudImpl.this.translateStreamType(var1);
                TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, "onSendFirstLocalVideoFrame:" + var1x);
                TRTCCloudListener var2 = TRTCCloudImpl.this.mTRTCListener;
                if (var2 != null) {
                    var2.onSendFirstLocalVideoFrame(var1x);
                }

            }
        });
    }

    protected void onSendFirstLocalAudioFrame() {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onSendFirstLocalAudioFrame");
                TRTCCloudImpl.this.appendDashboardLog(TRTCCloudImpl.this.mRoomInfo.getUserId(), 0, "onSendFirstLocalAudioFrame");
                TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                if (var1 != null) {
                    var1.onSendFirstLocalAudioFrame();
                }

            }
        });
    }

    private static TRTCVideoServerConfig createVideoServerConfigFromNative() {
        return new TRTCVideoServerConfig();
    }

    private void onRecvVideoServerConfig(final TRTCVideoServerConfig var1) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.apiLog("onRecvVideoServerConfig " + var1);
                TRTCCloudImpl.this.mVideoServerConfig = var1;
                TRTCVideoServerConfig.saveToSharedPreferences(TRTCCloudImpl.this.mContext, var1);
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1x, UserInfo var2) {
                        TXCRenderAndDec var3 = var2.mainRender.render;
                        if (var3 != null) {
                            var3.enableLimitDecCache(TRTCCloudImpl.this.mVideoServerConfig.enableHWVUI);
                        }

                        var3 = var2.subRender.render;
                        if (var3 != null) {
                            var3.enableLimitDecCache(TRTCCloudImpl.this.mVideoServerConfig.enableHWVUI);
                        }

                    }
                });
            }
        });
    }

    private static TRTCAudioServerConfig createAudioServerConfigFromNative() {
        return new TRTCAudioServerConfig();
    }

    private void onRecvAudioServerConfig(TRTCAudioServerConfig var1) {
        TXCLog.i("TRTCCloudImpl", "on receive audio config: [%s]", new Object[]{var1});
        TRTCAudioServerConfig.saveToSharedPreferences(this.mContext, var1);
        TXCAudioEngine.getInstance().enableAutoRestartDevice(var1.enableAutoRestartDevice);
        TXCAudioEngine.getInstance().setMaxSelectedPlayStreams(var1.maxSelectedPlayStreams);
    }

    private void setQoSParams() {
        TXCAudioEncoderConfig var1 = TXCAudioEngine.getInstance().getAudioEncoderConfig();
        TXCLog.i("", "setQoSParams:" + var1.sampleRate + " " + var1.channels + " " + var1.minBitrate + " " + var1.maxBitrate);
        TRTCCloudImpl var2 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(1);
        if (var2 != null) {
            long var3 = var2.getNetworkContext();
            this.nativeSetAudioEncodeConfiguration(var3, var1.minBitrate, var1.maxBitrate, var1.sampleRate, var1.channels);
        }

    }

    protected void runOnMainThread(Runnable var1) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainHandler.post(var1);
        } else {
            var1.run();
        }

    }

    private void runOnMainThreadAndWaitDone(Runnable var1) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainHandler.a(var1);
        } else {
            var1.run();
        }

    }

    protected void runOnListenerThread(Runnable var1) {
        Handler var2 = this.mListenerHandler;
        if (var2 == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mMainHandler.post(var1);
            } else {
                var1.run();
            }
        } else if (Looper.myLooper() != var2.getLooper()) {
            var2.post(var1);
        } else {
            var1.run();
        }

    }

    private void runOnListenerThread(Runnable var1, int var2) {
        Handler var3 = this.mListenerHandler;
        if (var3 == null) {
            this.mMainHandler.postDelayed(var1, (long)var2);
        } else {
            var3.postDelayed(var1, (long)var2);
        }

    }

    private void runOnSDKThread(Runnable var1, int var2) {
        if (this.mSDKHandler != null) {
            this.mSDKHandler.postDelayed(var1, (long)var2);
        }

    }

    protected void runOnSDKThread(Runnable var1) {
        if (this.mSDKHandler != null) {
            if (Looper.myLooper() != this.mSDKHandler.getLooper()) {
                this.mSDKHandler.post(var1);
            } else {
                var1.run();
            }
        }

    }

    protected void updateAppScene(int var1) {
        this.mAppScene = var1;
        if (this.mAppScene != 0 && this.mAppScene != 1) {
            this.mAppScene = 0;
        }

        if (this.mConfig.a * this.mConfig.b >= 518400) {
            this.mAppScene = 1;
        }

        this.updateEncType();
        this.apiLog(String.format("update appScene[%d] for video enc[%d] source scene[%d]", this.mAppScene, this.mConfig.j, var1));
    }

    private void updateEncType() {
        if (this.mCodecType != 0 && this.mCodecType != 1) {
            if (this.mAppScene == 1) {
                this.mConfig.j = 1;
            }
        } else {
            this.mConfig.j = this.mCodecType;
        }

    }

    private void setVideoEncConfig(int var1, int var2, int var3, int var4, int var5, boolean var6, int var7) {
        if (this.mRoomState == 0) {
            this.apiLog("setVideoEncConfig ignore when no in room");
        } else {
            if (this.mCodecType != 2) {
                this.setVideoEncoderConfiguration(var1, var2, var3, var4, var5, 1, var6, var7);
            } else {
                this.setVideoEncoderConfiguration(var1, var2, var3, var4, var5, this.mAppScene, var6, var7);
            }

        }
    }

    protected void setRenderView(final String var1, final RenderInfo var2, final TXCloudVideoView var3, final TRTCViewMargin var4) {
        if (var2 != null && var2.render != null && var2.render.getVideoRender() != null) {
            final com.tencent.liteav.renderer.f var5 = var2.render.getVideoRender();
            if (var3 == null) {
                var5.c((Object)null);
            } else {
                this.runOnMainThread(new Runnable() {
                    public void run() {
                        SurfaceView var1x = var3.getSurfaceView();
                        if (var1x != null) {
                            var1x.setVisibility(0);
                            SurfaceHolder var2x = var1x.getHolder();
                            var2x.removeCallback(var2);
                            var2x.addCallback(var2);
                            if (var2x.getSurface().isValid()) {
                                TRTCCloudImpl.this.apiLog(String.format(Locale.ENGLISH, "startRemoteView with valid surface %s, width: %d, height: %d", var2x.getSurface(), var1x.getWidth(), var1x.getHeight()));
                                var5.a(var2x.getSurface());
                                var5.c(var1x.getWidth(), var1x.getHeight());
                            } else {
                                TRTCCloudImpl.this.apiLog("startRemoteView with surfaceView add callback " + var2);
                            }
                        } else {
                            TextureView var3x = new TextureView(var3.getContext());
                            var3.addVideoView(var3x);
                            var3.setVisibility(0);
                            var3.setUserId(var1);
                            var3.showVideoDebugLog(TRTCCloudImpl.this.mDebugType);
                            if (var4 != null) {
                                var3.setLogMarginRatio(var4.leftMargin, var4.rightMargin, var4.topMargin, var4.bottomMargin);
                            }

                            var5.a(var3x);
                        }

                    }
                });
            }
        }
    }

    private void startRemoteRender(TXCRenderAndDec var1, int var2) {
        var1.stopVideo();
        var1.setStreamType(var2);
        var1.startVideo();
    }

    private void stopRemoteSubRender(UserInfo var1) {
        if (var1 != null) {
            this.apiLog(String.format("stopRemoteRender userID:%s tinyID:%d streamType:%d", var1.userID, var1.tinyID, 7));
            this.nativeCancelDownStream(this.mNativeRtcContext, var1.tinyID, 7, false);
            if (var1.subRender.render != null) {
                var1.subRender.render.stopVideo();
            }

        }
    }

    private void stopRemoteMainRender(UserInfo var1, Boolean var2) {
        if (var1 != null) {
            this.apiLog(String.format("stopRemoteRender userID:%s tinyID:%d streamType:%d", var1.userID, var1.tinyID, var1.streamType));
            this.nativeCancelDownStream(this.mNativeRtcContext, var1.tinyID, 2, var2);
            this.nativeCancelDownStream(this.mNativeRtcContext, var1.tinyID, 3, var2);
            if (var1.mainRender.render != null) {
                var1.mainRender.render.stopVideo();
            }

        }
    }

    protected void stopRemoteRender(UserInfo var1) {
        if (var1 != null) {
            this.apiLog(String.format("stopRemoteRender userID:%s tinyID:%d streamType:%d", var1.userID, var1.tinyID, var1.streamType));
            TXCAudioEngine.getInstance().stopRemoteAudio(String.valueOf(var1.tinyID));
            final TXCloudVideoView var2 = var1.mainRender.view;
            final TXCloudVideoView var3 = var1.subRender.view;
            if (var1.mainRender.render != null) {
                var1.mainRender.render.setVideoFrameListener((t)null, com.tencent.liteav.basic.a.b.a);
                var1.mainRender.render.stop();
                if (var2 == null && var1.mainRender.render.getVideoRender() != null) {
                    var1.mainRender.render.getVideoRender().e();
                }
            }

            if (var1.subRender.render != null) {
                var1.subRender.render.setVideoFrameListener((t)null, com.tencent.liteav.basic.a.b.a);
                var1.subRender.render.stop();
                if (var3 == null && var1.subRender.render.getVideoRender() != null) {
                    var1.subRender.render.getVideoRender().e();
                }
            }

            var1.mainRender.stop();
            var1.subRender.stop();
            this.runOnMainThread(new Runnable() {
                public void run() {
                    if (var2 != null) {
                        var2.removeVideoView();
                    }

                    if (var3 != null) {
                        var3.removeVideoView();
                    }

                }
            });
        }
    }

    protected void enableVideoStream(boolean var1) {
        if (var1) {
            if (!this.mRoomInfo.muteLocalVideo) {
                this.addUpStreamType(2);
                if (this.mEnableSmallStream) {
                    this.addUpStreamType(3);
                }
            }
        } else {
            if (!this.mCaptureAndEnc.i()) {
                this.removeUpStreamType(2);
            }

            this.removeUpStreamType(3);
        }

    }

    protected void enableAudioStream(boolean var1) {
        if (var1) {
            this.addUpStreamType(1);
        } else {
            this.removeUpStreamType(1);
        }

    }

    private void applyRenderConfig(TXCRenderAndDec var1) {
        j var2 = new j();
        var2.h = false;
        if (this.mAppScene == 1) {
            var2.h = true;
        }

        int var3 = TXCStatus.c("18446744073709551615", 17020);
        if (var3 == 0) {
            var3 = 600;
        }

        var2.d = var3;
        var2.y = this.mRoomInfo.decProperties;
        this.applyRenderPlayStrategy(var1, var2);
    }

    private void applyRenderPlayStrategy(TXCRenderAndDec var1, j var2) {
        var2.g = true;
        if (this.mCurrentRole == 20) {
            var2.a = com.tencent.liteav.basic.a.a.a;
            var2.c = com.tencent.liteav.basic.a.a.b;
            var2.b = com.tencent.liteav.basic.a.a.c;
        } else if (this.mCurrentRole == 21) {
            var2.a = com.tencent.liteav.basic.a.a.d;
            var2.c = com.tencent.liteav.basic.a.a.e;
            var2.b = com.tencent.liteav.basic.a.a.f;
        }

        var1.setConfig(var2);
    }

    private void notifyLogByUserId(String var1, int var2, int var3, String var4) {
        if (var1 != null && var4 != null) {
            Bundle var5 = new Bundle();
            var5.putLong("EVT_ID", (long)var3);
            var5.putLong("EVT_TIME", System.currentTimeMillis());
            var5.putString("EVT_MSG", var4);
            var5.putInt("EVT_STREAM_TYPE", var2);
            this.notifyEventByUserId(var1, var3, var5);
        }
    }

    private void notifyEventByUserId(final String var1, final int var2, final Bundle var3) {
        if (var1 != null && var3 != null) {
            this.mRoomInfo.forEachUser(new UserAction() {
                public void accept(String var1x, UserInfo var2x) {
                    if (var1.equalsIgnoreCase(String.valueOf(var2x.tinyID))) {
                        TRTCCloudImpl.this.notifyEvent(var2x.userID, var2, var3);
                    }

                }
            });
        }
    }

    private void appendDashboardLog(String var1, int var2, String var3) {
        this.appendDashboardLog(var1, var2, var3, "");
    }

    private void appendDashboardLog(String var1, int var2, final String var3, String var4) {
        this.apiLog(var4 + var3);
        final TXCloudVideoView var5 = null;
        if (!TextUtils.isEmpty(var1) && (this.mRoomInfo.userId == null || !var1.equalsIgnoreCase(this.mRoomInfo.userId))) {
            UserInfo var6 = this.mRoomInfo.getUser(var1);
            if (var6 != null) {
                if (var2 == 7) {
                    var5 = var6.subRender.view;
                } else {
                    var5 = var6.mainRender.view;
                }
            }
        } else {
            var5 = this.mRoomInfo.localView;
        }

        this.runOnMainThread(new Runnable() {
            public void run() {
                if (var5 != null) {
                    var5.appendEventInfo(var3);
                }

            }
        });
    }

    protected void notifyEvent(final String var1, final int var2, final Bundle var3) {
        this.runOnSDKThread(new Runnable() {
            public void run() {
                TRTCCloudImpl.this.appendDashboardLog(var1, var3.getInt("EVT_STREAM_TYPE", 2), var3.getString("EVT_MSG", ""), String.format("event %d, ", var2));
                final int var1x;
                if (var2 == 2029) {
                    TRTCCloudImpl.this.apiLog("release mic~");
                    if (TRTCCloudImpl.this.mRoomInfo.isRoomExit()) {
                        TRTCCloudImpl.this.apiLog("onExitRoom when mic release.");
                        var1x = TRTCCloudImpl.this.mRoomInfo.getRoomExitCode();
                        TRTCCloudImpl.this.mRoomInfo.setRoomExit(false, 0);
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            public void run() {
                                TRTCCloudListener var1xx = TRTCCloudImpl.this.mTRTCListener;
                                if (var1xx != null) {
                                    var1xx.onExitRoom(var1x);
                                }

                            }
                        });
                    } else {
                        TRTCCloudImpl.this.mRoomInfo.micStart(false);
                    }
                } else if (var2 == 2027) {
                    TRTCCloudImpl.this.apiLog(String.format("onMicDidReady~"));
                    TRTCCloudImpl.this.mRoomInfo.micStart(true);
                }

                var1x = TRTCCloudImpl.this.translateStreamType(var3.getInt("EVT_STREAM_TYPE", 2));
                TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                    public void run() {
                        TRTCCloudListener var1xx = TRTCCloudImpl.this.mTRTCListener;
                        if (var1xx != null) {
                            if (var2 == 2003) {
                                if (var1 != null && var1.equals(TRTCCloudImpl.this.mRoomInfo.getUserId())) {
                                    TRTCCloudImpl.this.apiLog("onFirstVideoFrame local.");
                                    var1xx.onFirstVideoFrame((String)null, var1x, var3.getInt("EVT_PARAM1"), var3.getInt("EVT_PARAM2"));
                                } else {
                                    TRTCCloudImpl.this.apiLog("onFirstVideoFrame " + var1);
                                    var1xx.onFirstVideoFrame(var1, var1x, var3.getInt("EVT_PARAM1"), var3.getInt("EVT_PARAM2"));
                                }
                            } else if (var2 == 2026) {
                                TRTCCloudImpl.this.apiLog("onFirstAudioFrame " + var1);
                                var1xx.onFirstAudioFrame(var1);
                            } else if (var2 == 1003) {
                                var1xx.onCameraDidReady();
                                Monitor.a(1, "onCameraDidReady self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            } else if (var2 == 2027) {
                                var1xx.onMicDidReady();
                                Monitor.a(1, "onMicDidReady self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            } else if (var2 < 0) {
                                var1xx.onError(var2, var3.getString("EVT_MSG", ""), var3);
                                Monitor.a(3, String.format("onError event:%d, msg:%s", var2, var3) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                TXCKeyPointReportProxy.b(var2);
                            } else if (var2 > 1100 && var2 < 1110 || var2 > 1200 && var2 < 1206 || var2 > 2100 && var2 < 2110 || var2 > 3001 && var2 < 3011 || var2 > 5100 && var2 < 5104) {
                                var1xx.onWarning(var2, var3.getString("EVT_MSG", ""), var3);
                                if (var2 != 2105) {
                                    Monitor.a(1, String.format("onWarning event:%d, msg:%s", var2, var3) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                                }

                                switch(var2) {
                                    case 1103:
                                    case 1109:
                                    case 2101:
                                    case 2102:
                                    case 2106:
                                    case 2109:
                                        TXCKeyPointReportProxy.b(var2);
                                }
                            }

                        }
                    }
                });
            }
        });
    }

    private void notifyEvent(String var1, int var2, String var3) {
        Bundle var4 = new Bundle();
        var4.putLong("EVT_ID", (long)var2);
        var4.putLong("EVT_TIME", System.currentTimeMillis());
        var4.putString("EVT_MSG", var3);
        this.notifyEvent(var1, var2, var4);
    }

    protected void apiLog(String var1) {
        TXCLog.i("TRTCCloudImpl", "(" + this.hashCode() + ")trtc_api " + var1);
    }

    private UserInfo createUserInfo(String var1) {
        UserInfo var2 = new UserInfo(0L, var1, 0, 0);
        var2.mainRender.muteVideo = this.mRoomInfo.muteRemoteVideo;
        var2.mainRender.muteAudio = this.mRoomInfo.muteRemoteAudio;
        return var2;
    }

    private com.tencent.liteav.i.a getSizeByResolution(int var1, int var2) {
        boolean var3 = false;
        boolean var4 = false;
        short var6;
        short var7;
        switch(var1) {
            case 1:
                var6 = 128;
                var7 = 128;
                break;
            case 3:
                var6 = 160;
                var7 = 160;
                break;
            case 5:
                var6 = 272;
                var7 = 272;
                break;
            case 7:
                var6 = 480;
                var7 = 480;
                break;
            case 50:
                var6 = 176;
                var7 = 128;
                break;
            case 52:
                var6 = 256;
                var7 = 192;
                break;
            case 54:
                var6 = 288;
                var7 = 224;
                break;
            case 56:
                var6 = 320;
                var7 = 240;
                break;
            case 58:
                var6 = 400;
                var7 = 304;
                break;
            case 60:
                var6 = 480;
                var7 = 368;
                break;
            case 62:
                var6 = 640;
                var7 = 480;
                break;
            case 64:
                var6 = 960;
                var7 = 720;
                break;
            case 100:
                var6 = 176;
                var7 = 96;
                break;
            case 102:
                var6 = 256;
                var7 = 144;
                break;
            case 104:
                var6 = 336;
                var7 = 192;
                break;
            case 106:
                var6 = 480;
                var7 = 272;
                break;
            case 108:
                var6 = 640;
                var7 = 368;
                break;
            case 110:
                var6 = 960;
                var7 = 544;
                break;
            case 112:
                var6 = 1280;
                var7 = 720;
                break;
            case 114:
                var6 = 1920;
                var7 = 1088;
                break;
            default:
                var6 = 640;
                var7 = 368;
        }

        com.tencent.liteav.i.a var5 = new com.tencent.liteav.i.a();
        if (var2 == 1) {
            var5.a = var7;
            var5.b = var6;
        } else {
            var5.a = var6;
            var5.b = var7;
        }

        return var5;
    }

    protected void checkUserState(final String var1, long var2, int var4, int var5) {
        final TRTCCloudListener var6 = this.mTRTCListener;
        if (var6 != null && !TextUtils.isEmpty(var1)) {
            final boolean var7 = TRTCRoomInfo.hasAudio(var4) && !TRTCRoomInfo.isMuteAudio(var4);
            boolean var8 = (TRTCRoomInfo.hasAudio(var5) && !TRTCRoomInfo.isMuteAudio(var5)) != var7;
            if (var8) {
                this.runOnListenerThread(new Runnable() {
                    public void run() {
                        var6.onUserAudioAvailable(var1, var7);
                    }
                });
                this.appendDashboardLog(this.mRoomInfo.getUserId(), 0, String.format("[%s]audio Available[%b]", var1, var7));
                Monitor.a(2, String.format("onUserAudioAvailable userID:%s, bAvailable:%b", var1, var7) + " self:" + this.hashCode(), "", 0);
            }

            final boolean var9 = (TRTCRoomInfo.hasMainVideo(var4) || TRTCRoomInfo.hasSmallVideo(var4)) && !TRTCRoomInfo.isMuteMainVideo(var4);
            var8 = ((TRTCRoomInfo.hasMainVideo(var5) || TRTCRoomInfo.hasSmallVideo(var5)) && !TRTCRoomInfo.isMuteMainVideo(var5)) != var9;
            boolean var10 = this.mRecvMode != 3 && this.mRecvMode != 1;
            if (var8 && (this.mRoomInfo.hasRecvFirstIFrame(var2) || var10)) {
                this.runOnListenerThread(new Runnable() {
                    public void run() {
                        var6.onUserVideoAvailable(var1, var9);
                    }
                });
                this.appendDashboardLog(this.mRoomInfo.getUserId(), 0, String.format("[%s]video Available[%b]", var1, var9));
                Monitor.a(2, String.format("onUserVideoAvailable userID:%s, bAvailable:%b", var1, var9) + " self:" + this.hashCode(), "", 0);
            }

            final boolean var11 = TRTCRoomInfo.hasSubVideo(var4) && !TRTCRoomInfo.isMuteSubVideo(var4);
            var8 = (TRTCRoomInfo.hasSubVideo(var5) && !TRTCRoomInfo.isMuteSubVideo(var5)) != var11;
            if (var8) {
                this.runOnListenerThread(new Runnable() {
                    public void run() {
                        var6.onUserSubStreamAvailable(var1, var11);
                    }
                });
                this.appendDashboardLog(this.mRoomInfo.getUserId(), 0, String.format("[%s]subVideo Available[%b]", var1, var11));
                Monitor.a(2, String.format("onUserSubStreamAvailable userID:%s, bAvailable:%b", var1, var11) + " self:" + this.hashCode(), "", 0);
            }
        }

    }

    private void collectCustomCaptureFps() {
        if (this.mVideoSourceType == TRTCCloudImpl.VideoSourceType.CUSTOM) {
            long var1 = System.currentTimeMillis();
            long var3 = var1 - this.mLastCaptureCalculateTS;
            if (var3 >= 1000L) {
                double var5 = (double)(this.mCaptureFrameCount - this.mLastCaptureFrameCount) * 1000.0D / (double)var3;
                this.mLastCaptureFrameCount = this.mCaptureFrameCount;
                this.mLastCaptureCalculateTS = var1;
                TXCStatus.a("18446744073709551615", 1001, 2, var5);
            }
        }

    }

    protected void checkRemoteDashBoard(final TXCloudVideoView var1, TXCRenderAndDec var2, UserInfo var3) {
        if (var1 != null && var2 != null && var2.isRendering()) {
            final CharSequence var4 = this.getDownloadStreamInfo(var2, var3);
            TXCLog.i("TRTCCloudImpl", "[STATUS]" + var4.toString().replace("\n", "") + " self:" + this.hashCode());
            this.runOnMainThread(new Runnable() {
                public void run() {
                    var1.setDashBoardStatusInfo(var4);
                }
            });
        }

    }

    protected void checkDashBoard() {
        if (this.mDebugType != 0) {
            final TXCloudVideoView var1 = this.mRoomInfo.localView;
            if (var1 != null) {
                final CharSequence var2 = this.getUploadStreamInfo();
                TXCLog.i("TRTCCloudImpl", "[STATUS]" + var2.toString().replace("\n", "") + " self:" + this.hashCode());
                this.runOnMainThread(new Runnable() {
                    public void run() {
                        var1.setDashBoardStatusInfo(var2);
                    }
                });
            }
        }

        this.mRoomInfo.forEachUser(new UserAction() {
            public void accept(String var1, UserInfo var2) {
                if (var2.mainRender.render != null && var2.mainRender.render.isRendering()) {
                    var2.mainRender.render.updateLoadInfo();
                }

                if (var2.subRender.render != null && var2.subRender.render.isRendering()) {
                    var2.subRender.render.updateLoadInfo();
                }

                if (TRTCCloudImpl.this.mDebugType != 0) {
                    TRTCCloudImpl.this.checkRemoteDashBoard(var2.mainRender.view, var2.mainRender.render, var2);
                    TRTCCloudImpl.this.checkRemoteDashBoard(var2.subRender.view, var2.subRender.render, var2);
                }

            }
        });
    }

    protected int getNetworkQuality(int var1, int var2) {
        if (!f.d(this.mContext)) {
            return 6;
        } else if (var2 <= 50 && var1 <= 500) {
            if (var2 <= 30 && var1 <= 350) {
                if (var2 <= 20 && var1 <= 200) {
                    if (var2 <= 10 && var1 <= 100) {
                        return var2 < 0 && var1 < 0 ? 0 : 1;
                    } else {
                        return 2;
                    }
                } else {
                    return 3;
                }
            } else {
                return 4;
            }
        } else {
            return 5;
        }
    }

    private int translateStreamType(int var1) {
        switch(var1) {
            case 2:
                return 0;
            case 3:
                return 1;
            case 7:
                return 2;
            default:
                return 0;
        }
    }

    private com.tencent.liteav.basic.a.b getPixelFormat(int var1) {
        switch(var1) {
            case 1:
                return com.tencent.liteav.basic.a.b.b;
            case 2:
                return com.tencent.liteav.basic.a.b.c;
            case 3:
                return com.tencent.liteav.basic.a.b.d;
            case 4:
                return com.tencent.liteav.basic.a.b.e;
            default:
                return com.tencent.liteav.basic.a.b.a;
        }
    }

    private TRTCRemoteStatistics getRemoteStatistics(TXCRenderAndDec var1, UserInfo var2) {
        String var3 = String.valueOf(var2.tinyID);
        int var4 = var1.getStreamType();
        int var5 = TXCStatus.c(var3, 5003, var4);
        int var6 = TXCStatus.c(var3, 17011, var4);
        int var7 = TXCStatus.c(var3, 18014);
        TRTCRemoteStatistics var8 = new TRTCRemoteStatistics();
        var8.userId = var2.userID;
        var8.finalLoss = var7 > var6 ? var7 : var6;
        var8.width = var5 >> 16;
        var8.height = var5 & '\uffff';
        var8.frameRate = (int)(TXCStatus.d(var3, 6002, var4) + 0.5D);
        var8.videoBitrate = TXCStatus.c(var3, 17002, var4);
        var8.audioSampleRate = TXCStatus.c(var3, 18003);
        var8.audioBitrate = TXCStatus.c(var3, 18002);
        var8.jitterBufferDelay = TXCStatus.c(var3, 2007);
        var8.streamType = this.translateStreamType(var4);
        return var8;
    }

    private TRTCLocalStatistics getLocalStatistics(int var1) {
        String var2 = "18446744073709551615";
        int var3 = TXCStatus.c(var2, 4003, var1);
        TRTCLocalStatistics var4 = new TRTCLocalStatistics();
        var4.width = var3 >> 16;
        var4.height = var3 & '\uffff';
        var4.frameRate = (int)(TXCStatus.d(var2, 4001, var1) + 0.5D);
        var4.videoBitrate = TXCStatus.c(var2, 13002, var1);
        var4.audioSampleRate = TXCStatus.c(var2, 14003);
        var4.audioBitrate = TXCStatus.c(var2, 14002);
        var4.streamType = this.translateStreamType(var1);
        return var4;
    }

    private void addRemoteStatistics(TXCRenderAndDec var1, UserInfo var2, TRTCStatistics var3, ArrayList<TRTCQuality> var4) {
        TRTCRemoteStatistics var5 = this.getRemoteStatistics(var1, var2);
        var3.remoteArray.add(var5);
        var3.downLoss = TXCStatus.c(String.valueOf(var2.tinyID), 16002);
        TRTCQuality var6 = new TRTCQuality();
        var6.userId = var2.userID;
        var6.quality = this.getNetworkQuality(var3.rtt, var5.finalLoss);
        var4.add(var6);
    }

    private void checkRTCState() {
        long var1 = System.currentTimeMillis();
        if (var1 >= this.mLastStateTimeMs + 2000L) {
            this.mLastStateTimeMs = var1;
            int[] var3 = f.a();
            final ArrayList var4 = new ArrayList();
            final TRTCStatistics var5 = new TRTCStatistics();
            var5.appCpu = var3[0] / 10;
            var5.systemCpu = var3[1] / 10;
            var5.rtt = TXCStatus.c("18446744073709551615", 12002);
            var5.sendBytes = TXCStatus.a("18446744073709551615", 12004);
            var5.receiveBytes = TXCStatus.a("18446744073709551615", 16004);
            var5.upLoss = TXCStatus.c("18446744073709551615", 12003);
            var5.localArray = new ArrayList();
            var5.remoteArray = new ArrayList();
            TRTCLocalStatistics var6 = this.getLocalStatistics(2);
            var5.localArray.add(var6);
            if (this.mEnableSmallStream) {
                TRTCLocalStatistics var7 = this.getLocalStatistics(3);
                var5.localArray.add(var7);
            }

            this.mRoomInfo.forEachUser(new UserAction() {
                public void accept(String var1, UserInfo var2) {
                    if (var2.mainRender.render != null) {
                        TRTCCloudImpl.this.addRemoteStatistics(var2.mainRender.render, var2, var5, var4);
                    }

                    if (var2.subRender.render != null && var2.subRender.render.isRendering()) {
                        TRTCCloudImpl.this.addRemoteStatistics(var2.subRender.render, var2, var5, var4);
                    }

                }
            });
            final TRTCQuality var8 = new TRTCQuality();
            var8.userId = this.mRoomInfo.getUserId();
            var8.quality = TXCStatus.c("18446744073709551615", 12005);
            this.runOnListenerThread(new Runnable() {
                public void run() {
                    TRTCCloudListener var1 = TRTCCloudImpl.this.mTRTCListener;
                    if (var1 != null) {
                        var1.onStatistics(var5);
                        var1.onNetworkQuality(var8, var4);
                    }

                }
            });
        }
    }

    protected void startCollectStatus() {
        if (this.mSDKHandler != null) {
            this.mSDKHandler.postDelayed(this.mStatusNotifyTask, 1000L);
        }

    }

    protected void stopCollectStatus() {
        if (this.mSDKHandler != null) {
            this.mSDKHandler.removeCallbacks(this.mStatusNotifyTask);
        }

    }

    protected CharSequence getUploadStreamInfo() {
        int[] var1 = f.a();
        String var2 = "18446744073709551615";
        byte var3 = 2;
        int var4 = TXCStatus.c(var2, 4003, var3);
        String var5 = TXCStatus.b(var2, 10001);
        return String.format("LOCAL: [%s] RTT:%dms\n", this.mRoomInfo.getUserId(), TXCStatus.c(var2, 12002)) + String.format(Locale.CHINA, "SEND:% 5dkbps LOSS:%d-%d-%d-%d|%d-%d-%d-%d|%d%%\n", TXCStatus.c(var2, 12001), TXCStatus.c(var2, 13011, var3), TXCStatus.c(var2, 13012, var3), TXCStatus.c(var2, 13013, var3), TXCStatus.c(var2, 13010, var3), TXCStatus.c(var2, 14011), TXCStatus.c(var2, 14012), TXCStatus.c(var2, 14013), TXCStatus.c(var2, 14010), TXCStatus.c(var2, 12003)) + String.format(Locale.CHINA, "BIT:%d|%d|%dkbps RES:%dx%d FPS:%d-%d\n", TXCStatus.c(var2, 13002, var3), TXCStatus.c(var2, 13002, 3), TXCStatus.c(var2, 14002), var4 >> 16, var4 & '\uffff', (int)TXCStatus.d(var2, 4001, var3), (int)TXCStatus.d(var2, 13014, var3)) + String.format(Locale.CHINA, "FEC:%d%%|%d%%  ARQ:%d|%dkbps  RPS:%d\n", TXCStatus.c(var2, 13004, var3), TXCStatus.c(var2, 14006), TXCStatus.c(var2, 13008, var3), TXCStatus.c(var2, 14008), TXCStatus.c(var2, 13007, var3)) + String.format(Locale.CHINA, "CPU:%d%%|%d%%    QOS:%s|%dkbps|%d-%d\n", var1[0] / 10, var1[1] / 10, this.getQosValue(TXCStatus.c(var2, 15009, var3)), TXCStatus.c(var2, 15002, var3), TXCStatus.c(var2, 15010, var3), TXCStatus.c(var2, 15005, var3)) + String.format(Locale.CHINA, "SVR:%s", var5);
    }

    private CharSequence getDownloadStreamInfo(TXCRenderAndDec var1, UserInfo var2) {
        String var3 = String.valueOf(var2.tinyID);
        int[] var4 = f.a();
        int var5 = var1.getStreamType();
        long var6 = TXCStatus.a(var3, 17014, var5);
        int var8 = TXCStatus.c(var3, 5003, var5);
        String var9 = var5 == 3 ? "S" : (var5 == 7 ? "Sub" : (var5 == 1 ? "A" : "B"));
        String var10 = String.format("REMOTE: [%s]%s RTT:%dms\n", var2.userID, var9, TXCStatus.c("18446744073709551615", 12002)) + String.format(Locale.CHINA, "RECV:%dkbps LOSS:%d-%d-%d%%|%d-%d-%d%%|%d%%\n", TXCStatus.c(var3, 17001, var5) + TXCStatus.c(var3, 18001), TXCStatus.c(var3, 17010, var5), TXCStatus.c(var3, 17005, var5), TXCStatus.c(var3, 17011, var5), TXCStatus.c(var3, 18013), TXCStatus.c(var3, 18007), TXCStatus.c(var3, 18014), TXCStatus.c(var3, 16002)) + String.format(Locale.CHINA, "BIT:%d|%dkbps RES:%dx%d FPS:%d-%d\n", TXCStatus.c(var3, 17002, var5), TXCStatus.c(var3, 18002), var8 >> 16, var8 & '\uffff', (int)TXCStatus.d(var3, 6002, var5), (int)TXCStatus.d(var3, 17003, var5)) + String.format(Locale.CHINA, "FEC:%d-%d-%d%%|%d-%d-%d%%    ARQ:%d-%d|%d-%d\n", TXCStatus.c(var3, 17007, var5), TXCStatus.c(var3, 17005, var5), TXCStatus.c(var3, 17006, var5), TXCStatus.c(var3, 18009), TXCStatus.c(var3, 18007), TXCStatus.c(var3, 18008), TXCStatus.c(var3, 17009, var5), TXCStatus.c(var3, 17008, var5), TXCStatus.c(var3, 18012), TXCStatus.c(var3, 18010)) + String.format(Locale.CHINA, "CPU:%d%%|%d%%  RPS:%d  LFR:%d  DERR:%d\n", var4[0] / 10, var4[1] / 10, TXCStatus.c(var3, 17012, var5), TXCStatus.c(var3, 17013, var5), var6) + String.format(Locale.CHINA, "Jitter: %d,%d|%d,%d|%d   ADROP: %d\n", TXCStatus.c(var3, 2007), TXCStatus.c(var3, 6010, var5), TXCStatus.c(var3, 6011, var5), TXCStatus.c(var3, 6012, var5), TXCStatus.c(var3, 2021), TXCStatus.c(var3, 18015)) + String.format(Locale.CHINA, "QUALITY: %d   LEN: %d\n", TXCStatus.c(var3, 18023), TXCStatus.c(var3, 18016));
        SpannableString var11 = new SpannableString(var10);
        int var12 = var10.lastIndexOf("DECERR:");
        if (-1 != var12 && var6 > 0L) {
            var11.setSpan(new ForegroundColorSpan(-65536), var12 + 7, var10.length(), 33);
        }

        return var11;
    }

    private String getQosValue(int var1) {
        switch(var1) {
            case 0:
                return "HOLD";
            case 1:
                return "UP";
            case 2:
                return "DOWN";
            default:
                return "ERR";
        }
    }

    private int getDisplayRotation() {
        int var1 = this.mDisplay.getRotation();
        short var2 = 0;
        switch(var1) {
            case 0:
                var2 = 0;
                break;
            case 1:
                var2 = 90;
                break;
            case 2:
                var2 = 180;
                break;
            case 3:
                var2 = 270;
        }

        return var2;
    }

    private void updateOrientation() {
        if (this.mVideoSourceType != TRTCCloudImpl.VideoSourceType.CUSTOM && this.mVideoSourceType != TRTCCloudImpl.VideoSourceType.SCREEN) {
            if (this.mCurrentOrientation == -1) {
                int var1 = this.mDisplay.getRotation();
                if (var1 == 1) {
                    this.mCurrentOrientation = 0;
                } else {
                    this.mCurrentOrientation = 1;
                }
            }

            this.setOrientation(this.mCurrentOrientation);
        }
    }

    private void checkRenderRotation(int var1) {
        int var2 = this.getDisplayRotation();
        int var3 = (360 - var2 - (this.mConfig.l - 1) * 90) % 360;
        boolean var4 = var1 % 2 == var2 % 2 && this.mConfig.l == 1 || var1 % 2 != var2 % 2 && this.mConfig.l == 0;
        if (this.mVideoRenderMirror == 1) {
            if (!this.mConfig.m && var4) {
                var3 += 180;
            }
        } else if (this.mVideoRenderMirror == 2 && this.mConfig.m && var4) {
            var3 += 180;
        }

        TXCLog.d("TRTCCloudImpl", String.format("vrotation rotation-change %d-%d-%d ======= renderRotation %d-%d", var1, this.mConfig.l, var2, var3, this.mRoomInfo.localRenderRotation) + " self:" + this.hashCode());
        var3 = (this.mRoomInfo.localRenderRotation + var3) % 360;
        this.mCaptureAndEnc.g(var3);
    }

    private void checkVideoEncRotation(int var1) {
        int var2 = 0;
        short var3 = 0;
        if (this.mConfig.l != 1) {
            if ((!this.mConfig.S || !this.mConfig.m) && (this.mConfig.S || this.mConfig.m)) {
                var3 = 270;
            } else {
                var3 = 90;
            }
        }

        switch(var1) {
            case 0:
                var2 = (90 + var3) % 360;
                if (!this.mConfig.m) {
                    var2 = (180 + var2) % 360;
                }

                if (this.mConfig.S) {
                    var2 = (180 + var2) % 360;
                }
                break;
            case 1:
                var2 = (0 + var3) % 360;
                break;
            case 2:
                var2 = (270 + var3) % 360;
                if (!this.mConfig.m) {
                    var2 = (180 + var2) % 360;
                }

                if (this.mConfig.S) {
                    var2 = (180 + var2) % 360;
                }
                break;
            case 3:
                var2 = (180 + var3) % 360;
        }

        TXCLog.d("TRTCCloudImpl", String.format("vrotation rotation-change %d-%d ======= encRotation %d", var1, this.mConfig.l, var2) + " self:" + this.hashCode());
        this.mCurrentOrientation = var1;
        this.mCaptureAndEnc.setVideoEncRotation(var2);
    }

    private void setOrientation(final int var1) {
        if (var1 != -1) {
            this.runOnSDKThread(new Runnable() {
                public void run() {
                    TRTCCloudImpl.this.checkRenderRotation(var1);
                    if (TRTCCloudImpl.this.mSensorMode != 0) {
                        TRTCCloudImpl.this.checkVideoEncRotation(var1);
                    }

                }
            });
        }
    }

    private void addUpStreamType(int var1) {
        if (!this.mStreamTypes.contains(var1)) {
            this.mStreamTypes.add(var1);
        }

        this.addUpstream(var1);
    }

    private void removeUpStreamType(int var1) {
        if (this.mStreamTypes.contains(var1)) {
            this.mStreamTypes.remove(var1);
        }

        this.removeUpstream(var1);
    }

    private void setVideoEncoderConfiguration(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, int var8) {
        TRTCCloudImpl var9 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(var1);
        if (var9 != null) {
            long var10 = var9.getNetworkContext();
            this.nativeSetVideoEncoderConfiguration(var10, var1, var2, var3, var4, var5, var6, var7, var8);
        }

    }

    private void setVideoQuality(int var1, int var2) {
        TRTCCloudImpl var3 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(2);
        if (var3 != null) {
            long var4 = var3.getNetworkContext();
            this.nativeSetVideoQuality(var4, var1, var2);
        }

    }

    private void muteUpstream(int var1, boolean var2) {
        TRTCCloudImpl var3 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(var1);
        if (var3 != null) {
            long var4 = var3.getNetworkContext();
            this.nativeMuteUpstream(var4, var1, var2);
        }

    }

    private void addUpstream(int var1) {
        TRTCCloudImpl var2 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(var1);
        if (var2 != null) {
            long var3 = var2.getNetworkContext();
            this.nativeAddUpstream(var3, var1);
        }

    }

    private void removeUpstream(int var1) {
        TRTCCloudImpl var2 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(var1);
        if (var2 != null) {
            long var3 = var2.getNetworkContext();
            this.nativeRemoveUpstream(var3, var1);
        }

    }

    private void setAudioEncodeConfiguration() {
        this.setQoSParams();
    }

    public void enableNetworkBlackStream(boolean var1) {
        TRTCCloudImpl var2 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(2);
        if (var2 != null) {
            long var3 = var2.getNetworkContext();
            this.nativeEnableBlackStream(var3, var1);
        }

    }

    public void enableNetworkSmallStream(boolean var1) {
        TRTCCloudImpl var2 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(2);
        if (var2 != null) {
            long var3 = var2.getNetworkContext();
            this.nativeEnableSmallStream(var3, var1);
        }

    }

    private void pushVideoFrame(TXSNALPacket var1) {
        TRTCCloudImpl var2 = null;
        synchronized(this.mCurrentPublishClouds) {
            var2 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(var1.streamType);
        }

        if (var2 != null) {
            long var3 = var2.getNetworkContext();
            this.nativePushVideo(var3, var1.streamType, 1, var1.nalType, var1.nalData, var1.gopIndex, var1.gopFrameIndex, var1.refFremeIndex, var1.pts, var1.dts);
        }

    }

    public long getNetworkContext() {
        return this.mNativeRtcContext;
    }

    public boolean isPublishingInCloud(TRTCCloudImpl var1, int var2) {
        synchronized(this.mCurrentPublishClouds) {
            TRTCCloudImpl var4 = (TRTCCloudImpl)this.mCurrentPublishClouds.get(var2);
            return var4 == var1;
        }
    }

    private void notifyCaptureStarted(final String var1) {
        this.runOnListenerThread(new Runnable() {
            public void run() {
                TRTCCloudListener var1x = TRTCCloudImpl.this.mTRTCListener;
                if (var1x != null) {
                    var1x.onWarning(4000, var1, (Bundle)null);
                }
            }
        });
        this.apiLog(var1);
    }

    private static String buildTRAEConfig(boolean var0) {
        String var1 = "";
        var1 = var1 + "sharp {\r\n";
        var1 = var1 + "  os android\r\n";
        var1 = var1 + "  trae {\r\n";
        var1 = var1 + "    dev {\r\n";
        var1 = var1 + "      closeOpensl " + (var0 ? "n" : "y") + "\r\n";
        var1 = var1 + "    }\r\n";
        var1 = var1 + "  }\r\n";
        var1 = var1 + "}";
        return var1;
    }

    static {
        f.f();
    }

    static enum VideoSourceType {
        NONE,
        CAMERA,
        SCREEN,
        CUSTOM;

        private VideoSourceType() {
        }
    }

    static class RenderListenerAdapter {
        public String strTinyID;
        public int pixelFormat;
        public int bufferType;
        public TRTCVideoRenderListener listener;

        RenderListenerAdapter() {
        }
    }

    private static class DisplayOrientationDetector extends OrientationEventListener {
        private WeakReference<TRTCCloudImpl> mTRTCEngine;
        public int mCurOrientation = -1;
        private int mCurrentDisplayRotation = 0;

        DisplayOrientationDetector(Context var1, TRTCCloudImpl var2) {
            super(var1);
            this.mTRTCEngine = new WeakReference(var2);
        }

        public void onOrientationChanged(int var1) {
            if (var1 == -1) {
                TXCLog.i("DisplayOrientationDetector", "rotation-change invalid " + var1);
            } else {
                boolean var2 = false;
                byte var4;
                if (var1 <= 45) {
                    var4 = 1;
                } else if (var1 <= 135) {
                    var4 = 2;
                } else if (var1 <= 225) {
                    var4 = 3;
                } else if (var1 <= 315) {
                    var4 = 0;
                } else {
                    var4 = 1;
                }

                if (this.mCurOrientation != var4) {
                    this.mCurOrientation = var4;
                    TRTCCloudImpl var3 = (TRTCCloudImpl)this.mTRTCEngine.get();
                    if (var3 != null) {
                        this.mCurrentDisplayRotation = var3.getDisplayRotation();
                        var3.setOrientation(this.mCurOrientation);
                    }

                    TXCLog.d("DisplayOrientationDetector", "rotation-change onOrientationChanged " + var1 + ", orientation " + this.mCurOrientation + " self:" + (var3 != null ? var3.hashCode() : ""));
                }

            }
        }

        public void checkOrientation() {
            TRTCCloudImpl var1 = (TRTCCloudImpl)this.mTRTCEngine.get();
            if (var1 != null) {
                int var2 = var1.getDisplayRotation();
                if (this.mCurrentDisplayRotation != var2) {
                    this.mCurrentDisplayRotation = var2;
                    var1.setOrientation(this.mCurOrientation);
                }
            }

        }
    }

    private static class StatusTask implements Runnable {
        private WeakReference<TRTCCloudImpl> mTRTCEngine;

        StatusTask(TRTCCloudImpl var1) {
            this.mTRTCEngine = new WeakReference(var1);
        }

        public void run() {
            TRTCCloudImpl var1 = (TRTCCloudImpl)this.mTRTCEngine.get();
            if (var1 != null) {
                int var2 = f.e(var1.mContext);
                int[] var3 = f.a();
                int var4 = f.b() * 1024;
                TXCStatus.a("18446744073709551615", 11006, var2);
                TXCStatus.a("18446744073709551615", 11001, var3[0] / 10);
                TXCStatus.a("18446744073709551615", 11002, var3[1] / 10);
                TXCStatus.a("18446744073709551615", 11003, var4);
                boolean var5 = true;
                byte var6;
                if (f.a(var1.mContext)) {
                    TXCStatus.a("18446744073709551615", 11004, 1);
                    var6 = 1;
                } else {
                    TXCStatus.a("18446744073709551615", 11004, 0);
                    var6 = 0;
                }

                if (var1.mNetType != var2) {
                    if (var1.mNetType >= 0 && var2 > 0) {
                        var1.nativeReenterRoom(var1.mNativeRtcContext, 100);
                    }

                    TXCEventRecorderProxy.a("18446744073709551615", 1003, var2 == 0 ? 0L : (long)var2, -1L, "", 0);
                    Monitor.a(2, String.format("network switch from:%d to %d", var1.mNetType, var2) + " self:" + var1.hashCode(), "1:wifi/2:4G/3:3G/4:2G/5:Cable", 0);
                    var1.mNetType = var2;
                    TXCKeyPointReportProxy.a(40039, var2, 0);
                }

                if (var1.mBackground != var6) {
                    TXCEventRecorderProxy.a("18446744073709551615", 2001, (long)var6, -1L, "", 0);
                    var1.mBackground = var6;
                    if (var6 == 0) {
                        Monitor.a(1, "onAppDidBecomeActive self:" + var1.hashCode(), "", 0);
                    } else {
                        Monitor.a(1, "onAppEnterBackground self:" + var1.hashCode(), "", 0);
                    }

                    TXCKeyPointReportProxy.c(50001, var6);
                }

                TXCKeyPointReportProxy.a(var3[0] / 10, var3[1] / 10);
                TXCKeyPointReportProxy.a();
                var1.checkRTCState();
                var1.checkDashBoard();
                var1.collectCustomCaptureFps();
                var1.startCollectStatus();
                if (var1.mSensorMode != 0) {
                    var1.mOrientationEventListener.checkOrientation();
                }

            }
        }
    }

    private static class VolumeLevelNotifyTask implements Runnable {
        private WeakReference<TRTCCloudImpl> mWeakTRTCEngine;

        VolumeLevelNotifyTask(TRTCCloudImpl var1) {
            this.mWeakTRTCEngine = new WeakReference(var1);
        }

        public void run() {
            TRTCCloudImpl var1 = null;
            if (this.mWeakTRTCEngine != null) {
                var1 = (TRTCCloudImpl)this.mWeakTRTCEngine.get();
            }

            if (var1 != null) {
                final ArrayList var2 = new ArrayList();
                int var3 = 0;
                if (var1.mCaptureAndEnc != null) {
                    var3 = TXCAudioEngine.getInstance().getSoftwareCaptureVolumeLevel();
                }

                if (var3 > 0) {
                    TRTCVolumeInfo var4 = new TRTCVolumeInfo();
                    var4.userId = var1.mRoomInfo.userId;
                    var4.volume = var3;
                    var2.add(var4);
                }

                var1.mRoomInfo.forEachUser(new UserAction() {
                    public void accept(String var1, UserInfo var2x) {
                        boolean var3 = false;
                        int var5 = TXCAudioEngine.getInstance().getRemotePlayoutVolumeLevel(String.valueOf(var2x.tinyID));
                        if (var5 > 0) {
                            TRTCVolumeInfo var4 = new TRTCVolumeInfo();
                            var4.userId = var2x.userID;
                            var4.volume = var5;
                            var2.add(var4);
                        }

                    }
                });
                final int var6 = TXCAudioEngine.getMixingPlayoutVolumeLevel();
                final TRTCCloudListener var5 = var1.mTRTCListener;
                var1.runOnListenerThread(new Runnable() {
                    public void run() {
                        if (var5 != null) {
                            var5.onUserVoiceVolume(var2, var6);
                        }

                    }
                });
                if (var1.mAudioVolumeEvalInterval > 0) {
                    var1.mSDKHandler.postDelayed(var1.mVolumeLevelNotifyTask, (long)var1.mAudioVolumeEvalInterval);
                }
            }

        }
    }
}
