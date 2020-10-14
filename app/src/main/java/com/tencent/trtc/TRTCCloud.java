package com.tencent.trtc;

import java.lang.ref.*;
import android.content.*;
import com.tencent.liteav.trtc.impl.*;
import android.os.*;
import com.tencent.rtmp.ui.*;
import com.tencent.liteav.beauty.*;
import android.graphics.*;
import com.tencent.liteav.audio.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.log.*;
import android.annotation.*;

public abstract class TRTCCloud
{
    static WeakReference<TRTCCloud> sInstance;
    private static a mTXLogListener;
    
    public static TRTCCloud sharedInstance(final Context context) {
        return TRTCCloudImpl.sharedInstance(context);
    }
    
    public static void destroySharedInstance() {
        TRTCCloudImpl.destroySharedInstance();
    }
    
    public abstract void setListener(final TRTCCloudListener p0);
    
    public abstract void setListenerHandler(final Handler p0);
    
    public abstract void enterRoom(final TRTCCloudDef.TRTCParams p0, final int p1);
    
    public abstract void exitRoom();
    
    public abstract void switchRole(final int p0);
    
    public abstract void ConnectOtherRoom(final String p0);
    
    public abstract void DisconnectOtherRoom();
    
    public abstract void setDefaultStreamRecvMode(final boolean p0, final boolean p1);
    
    public abstract TRTCCloud createSubCloud();
    
    public abstract void destroySubCloud(final TRTCCloud p0);
    
    public abstract void startPublishing(final String p0, final int p1);
    
    public abstract void stopPublishing();
    
    public abstract void startPublishCDNStream(final TRTCCloudDef.TRTCPublishCDNParam p0);
    
    public abstract void stopPublishCDNStream();
    
    public abstract void setMixTranscodingConfig(final TRTCCloudDef.TRTCTranscodingConfig p0);
    
    public abstract void startLocalPreview(final boolean p0, final TXCloudVideoView p1);
    
    public abstract void stopLocalPreview();
    
    public abstract void muteLocalVideo(final boolean p0);
    
    public abstract void startRemoteView(final String p0, final TXCloudVideoView p1);
    
    public abstract void stopRemoteView(final String p0);
    
    public abstract void stopAllRemoteView();
    
    public abstract void muteRemoteVideoStream(final String p0, final boolean p1);
    
    public abstract void muteAllRemoteVideoStreams(final boolean p0);
    
    public abstract void setVideoEncoderParam(final TRTCCloudDef.TRTCVideoEncParam p0);
    
    public abstract void setNetworkQosParam(final TRTCCloudDef.TRTCNetworkQosParam p0);
    
    public abstract void setLocalViewFillMode(final int p0);
    
    public abstract void setRemoteViewFillMode(final String p0, final int p1);
    
    public abstract void setLocalViewRotation(final int p0);
    
    public abstract void setRemoteViewRotation(final String p0, final int p1);
    
    public abstract void setVideoEncoderRotation(final int p0);
    
    public abstract void setLocalViewMirror(final int p0);
    
    public abstract void setVideoEncoderMirror(final boolean p0);
    
    public abstract void setGSensorMode(final int p0);
    
    public abstract int enableEncSmallVideoStream(final boolean p0, final TRTCCloudDef.TRTCVideoEncParam p1);
    
    public abstract int setRemoteVideoStreamType(final String p0, final int p1);
    
    public abstract int setPriorRemoteVideoStreamType(final int p0);
    
    public abstract void snapshotVideo(final String p0, final int p1, final TRTCCloudListener.TRTCSnapshotListener p2);
    
    public abstract void setAudioQuality(final int p0);
    
    public abstract void startLocalAudio();
    
    public abstract void stopLocalAudio();
    
    public abstract void muteLocalAudio(final boolean p0);
    
    public abstract void setAudioRoute(final int p0);
    
    public abstract void muteRemoteAudio(final String p0, final boolean p1);
    
    public abstract void muteAllRemoteAudio(final boolean p0);
    
    public abstract void setRemoteAudioVolume(final String p0, final int p1);
    
    public abstract void setAudioCaptureVolume(final int p0);
    
    public abstract int getAudioCaptureVolume();
    
    public abstract void setAudioPlayoutVolume(final int p0);
    
    public abstract int getAudioPlayoutVolume();
    
    public abstract void enableAudioVolumeEvaluation(final int p0);
    
    public abstract int startAudioRecording(final TRTCCloudDef.TRTCAudioRecordingParams p0);
    
    public abstract void stopAudioRecording();
    
    public abstract void setSystemVolumeType(final int p0);
    
    public abstract void enableAudioEarMonitoring(final boolean p0);
    
    public abstract void switchCamera();
    
    public abstract boolean isCameraZoomSupported();
    
    public abstract void setZoom(final int p0);
    
    public abstract boolean isCameraTorchSupported();
    
    public abstract boolean enableTorch(final boolean p0);
    
    public abstract boolean isCameraFocusPositionInPreviewSupported();
    
    public abstract void setFocusPosition(final int p0, final int p1);
    
    public abstract boolean isCameraAutoFocusFaceModeSupported();
    
    public abstract TXBeautyManager getBeautyManager();
    
    public abstract void setWatermark(final Bitmap p0, final int p1, final float p2, final float p3, final float p4);
    
    public abstract TXAudioEffectManager getAudioEffectManager();
    
    public abstract void startScreenCapture(final TRTCCloudDef.TRTCVideoEncParam p0, final TRTCCloudDef.TRTCScreenShareParams p1);
    
    public abstract void stopScreenCapture();
    
    public abstract void pauseScreenCapture();
    
    public abstract void resumeScreenCapture();
    
    public abstract void startRemoteSubStreamView(final String p0, final TXCloudVideoView p1);
    
    public abstract void stopRemoteSubStreamView(final String p0);
    
    public abstract void setRemoteSubStreamViewFillMode(final String p0, final int p1);
    
    public abstract void setRemoteSubStreamViewRotation(final String p0, final int p1);
    
    public abstract void enableCustomVideoCapture(final boolean p0);
    
    public abstract void sendCustomVideoData(final TRTCCloudDef.TRTCVideoFrame p0);
    
    public abstract int setLocalVideoRenderListener(final int p0, final int p1, final TRTCCloudListener.TRTCVideoRenderListener p2);
    
    public abstract int setRemoteVideoRenderListener(final String p0, final int p1, final int p2, final TRTCCloudListener.TRTCVideoRenderListener p3);
    
    public abstract void enableCustomAudioCapture(final boolean p0);
    
    public abstract void sendCustomAudioData(final TRTCCloudDef.TRTCAudioFrame p0);
    
    public abstract void setAudioFrameListener(final TRTCCloudListener.TRTCAudioFrameListener p0);
    
    public abstract boolean sendCustomCmdMsg(final int p0, final byte[] p1, final boolean p2, final boolean p3);
    
    public abstract boolean sendSEIMsg(final byte[] p0, final int p1);
    
    public abstract void startSpeedTest(final int p0, final String p1, final String p2);
    
    public abstract void stopSpeedTest();
    
    public static String getSDKVersion() {
        return TXCCommonUtil.getSDKVersionStr();
    }
    
    public static void setLogLevel(final int level) {
        TXCLog.setLevel(level);
    }
    
    public static void setConsoleEnabled(final boolean consoleEnabled) {
        TXCLog.setConsoleEnabled(consoleEnabled);
    }
    
    public static void setLogCompressEnabled(final boolean logCompressEnabled) {
        TXCLog.setLogCompressEnabled(logCompressEnabled);
    }
    
    public static void setLogDirPath(final String logDirPath) {
        TXCLog.setLogDirPath(logDirPath);
    }
    
    public static void setLogListener(final TRTCCloudListener.TRTCLogListener trtcLogListener) {
        if (TRTCCloud.mTXLogListener != null) {
            TRTCCloud.mTXLogListener.a(null);
        }
        if (trtcLogListener != null) {
            (TRTCCloud.mTXLogListener = new a()).a(trtcLogListener);
        }
        else {
            TRTCCloud.mTXLogListener = null;
        }
        TXCLog.setListener(TRTCCloud.mTXLogListener);
    }
    
    public abstract void showDebugView(final int p0);
    
    public abstract void setDebugViewMargin(final String p0, final TRTCViewMargin p1);
    
    public abstract void callExperimentalAPI(final String p0);
    
    @Deprecated
    public abstract void setMicVolumeOnMixing(final int p0);
    
    @Deprecated
    public abstract void setBeautyStyle(final int p0, final int p1, final int p2, final int p3);
    
    @Deprecated
    public abstract void setEyeScaleLevel(final int p0);
    
    @Deprecated
    public abstract void setFaceSlimLevel(final int p0);
    
    @Deprecated
    public abstract void setFaceVLevel(final int p0);
    
    @Deprecated
    public abstract void setChinLevel(final int p0);
    
    @Deprecated
    public abstract void setFaceShortLevel(final int p0);
    
    @Deprecated
    public abstract void setNoseSlimLevel(final int p0);
    
    @Deprecated
    public abstract void selectMotionTmpl(final String p0);
    
    @Deprecated
    public abstract void setMotionMute(final boolean p0);
    
    @Deprecated
    public abstract void setFilter(final Bitmap p0);
    
    @Deprecated
    public abstract void setFilterConcentration(final float p0);
    
    @Deprecated
    @TargetApi(18)
    public abstract boolean setGreenScreenFile(final String p0);
    
    @Deprecated
    public abstract void playBGM(final String p0, final BGMNotify p1);
    
    @Deprecated
    public abstract void stopBGM();
    
    @Deprecated
    public abstract void pauseBGM();
    
    @Deprecated
    public abstract void resumeBGM();
    
    @Deprecated
    public abstract int getBGMDuration(final String p0);
    
    @Deprecated
    public abstract int setBGMPosition(final int p0);
    
    @Deprecated
    public abstract void setBGMVolume(final int p0);
    
    @Deprecated
    public abstract void setBGMPlayoutVolume(final int p0);
    
    @Deprecated
    public abstract void setBGMPublishVolume(final int p0);
    
    @Deprecated
    public abstract void setReverbType(final int p0);
    
    @Deprecated
    public abstract boolean setVoiceChangerType(final int p0);
    
    @Deprecated
    public abstract void playAudioEffect(final TRTCCloudDef.TRTCAudioEffectParam p0);
    
    @Deprecated
    public abstract void setAudioEffectVolume(final int p0, final int p1);
    
    @Deprecated
    public abstract void stopAudioEffect(final int p0);
    
    @Deprecated
    public abstract void stopAllAudioEffects();
    
    @Deprecated
    public abstract void setAllAudioEffectsVolume(final int p0);
    
    @Deprecated
    public abstract void pauseAudioEffect(final int p0);
    
    @Deprecated
    public abstract void resumeAudioEffect(final int p0);
    
    static {
        TRTCCloud.sInstance = null;
        TRTCCloud.mTXLogListener = null;
    }
    
    private static class a implements TXCLog.a
    {
        private TRTCCloudListener.TRTCLogListener a;
        
        public a() {
            this.a = null;
            this.a = null;
        }
        
        public void a(final TRTCCloudListener.TRTCLogListener a) {
            this.a = a;
        }
        
        @Override
        public void a(final int n, final String s, final String s2) {
            final TRTCCloudListener.TRTCLogListener a = this.a;
            if (a != null) {
                a.onLog(s2, n, s);
            }
        }
    }
    
    public static class TRTCViewMargin
    {
        public float leftMargin;
        public float topMargin;
        public float rightMargin;
        public float bottomMargin;
        
        public TRTCViewMargin(final float leftMargin, final float rightMargin, final float topMargin, final float bottomMargin) {
            this.leftMargin = 0.0f;
            this.topMargin = 0.0f;
            this.rightMargin = 0.0f;
            this.bottomMargin = 0.0f;
            this.leftMargin = leftMargin;
            this.topMargin = topMargin;
            this.rightMargin = rightMargin;
            this.bottomMargin = bottomMargin;
        }
    }
    
    @Deprecated
    public interface BGMNotify
    {
        void onBGMStart(final int p0);
        
        void onBGMProgress(final long p0, final long p1);
        
        void onBGMComplete(final int p0);
    }
}
