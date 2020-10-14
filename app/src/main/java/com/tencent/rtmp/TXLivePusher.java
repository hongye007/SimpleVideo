package com.tencent.rtmp;

import android.content.*;
import com.tencent.rtmp.ui.*;
import com.tencent.liteav.beauty.*;
import android.graphics.*;
import android.annotation.*;
import com.tencent.liteav.audio.*;
import com.tencent.ugc.*;
import android.view.*;

public class TXLivePusher
{
    private b mTXTxLivePusherImpl;
    public static final int YUV_420SP = 1;
    public static final int YUV_420YpCbCr = 2;
    public static final int YUV_420P = 3;
    public static final int RGB_BGRA = 4;
    public static final int RGB_RGBA = 5;
    
    public TXLivePusher(final Context context) {
        this.mTXTxLivePusherImpl = new b(context);
    }
    
    public void setConfig(final TXLivePushConfig txLivePushConfig) {
        this.mTXTxLivePusherImpl.a(txLivePushConfig);
    }
    
    public TXLivePushConfig getConfig() {
        return this.mTXTxLivePusherImpl.b();
    }
    
    public void setPushListener(final ITXLivePushListener itxLivePushListener) {
        this.mTXTxLivePusherImpl.a(itxLivePushListener);
    }
    
    public void startCameraPreview(final TXCloudVideoView txCloudVideoView) {
        this.mTXTxLivePusherImpl.a(txCloudVideoView);
    }
    
    public void stopCameraPreview(final boolean b) {
        this.mTXTxLivePusherImpl.a(b);
    }
    
    public int startPusher(final String s) {
        return this.mTXTxLivePusherImpl.a(s);
    }
    
    public void stopPusher() {
        this.mTXTxLivePusherImpl.c();
    }
    
    public void startScreenCapture() {
        this.mTXTxLivePusherImpl.g();
    }
    
    public void stopScreenCapture() {
        this.mTXTxLivePusherImpl.h();
    }
    
    public void pausePusher() {
        this.mTXTxLivePusherImpl.d();
    }
    
    public void resumePusher() {
        this.mTXTxLivePusherImpl.e();
    }
    
    public boolean isPushing() {
        return this.mTXTxLivePusherImpl.f();
    }
    
    public void setVideoQuality(final int n, final boolean b, final boolean b2) {
        this.mTXTxLivePusherImpl.a(n, b, b2);
    }
    
    public void switchCamera() {
        this.mTXTxLivePusherImpl.i();
    }
    
    public boolean setMirror(final boolean b) {
        return this.mTXTxLivePusherImpl.b(b);
    }
    
    public void setRenderRotation(final int n) {
        this.mTXTxLivePusherImpl.a(n);
    }
    
    public boolean turnOnFlashLight(final boolean b) {
        return this.mTXTxLivePusherImpl.c(b);
    }
    
    public int getMaxZoom() {
        return this.mTXTxLivePusherImpl.j();
    }
    
    public boolean setZoom(final int n) {
        return this.mTXTxLivePusherImpl.b(n);
    }
    
    public void setExposureCompensation(final float n) {
        this.mTXTxLivePusherImpl.a(n);
    }
    
    public TXBeautyManager getBeautyManager() {
        return this.mTXTxLivePusherImpl.k();
    }
    
    public boolean setBeautyFilter(final int n, final int n2, final int n3, final int n4) {
        return this.mTXTxLivePusherImpl.a(n, n2, n3, n4);
    }
    
    @Deprecated
    public void setFilter(final Bitmap filter) {
        this.getBeautyManager().setFilter(filter);
    }
    
    @Deprecated
    public void setSpecialRatio(final float filterStrength) {
        this.getBeautyManager().setFilterStrength(filterStrength);
    }
    
    @Deprecated
    public void setEyeScaleLevel(final int eyeScaleLevel) {
        this.getBeautyManager().setEyeScaleLevel(eyeScaleLevel);
    }
    
    @Deprecated
    public void setFaceSlimLevel(final int faceSlimLevel) {
        this.getBeautyManager().setFaceSlimLevel(faceSlimLevel);
    }
    
    @Deprecated
    public void setFaceVLevel(final int faceVLevel) {
        this.getBeautyManager().setFaceVLevel(faceVLevel);
    }
    
    @Deprecated
    public void setChinLevel(final int chinLevel) {
        this.getBeautyManager().setChinLevel(chinLevel);
    }
    
    @Deprecated
    public void setFaceShortLevel(final int faceShortLevel) {
        this.getBeautyManager().setFaceShortLevel(faceShortLevel);
    }
    
    @Deprecated
    public void setNoseSlimLevel(final int noseSlimLevel) {
        this.getBeautyManager().setNoseSlimLevel(noseSlimLevel);
    }
    
    @Deprecated
    @TargetApi(18)
    public boolean setGreenScreenFile(final String greenScreenFile) {
        this.getBeautyManager().setGreenScreenFile(greenScreenFile);
        return true;
    }
    
    @Deprecated
    public void setMotionTmpl(final String motionTmpl) {
        this.getBeautyManager().setMotionTmpl(motionTmpl);
    }
    
    @Deprecated
    public void setMotionMute(final boolean motionMute) {
        this.getBeautyManager().setMotionMute(motionMute);
    }
    
    public void setMute(final boolean b) {
        this.mTXTxLivePusherImpl.d(b);
    }
    
    public TXAudioEffectManager getAudioEffectManager() {
        return TXAudioEffectManagerImpl.getAutoCacheHolder();
    }
    
    public void setAudioVolumeEvaluationListener(final ITXAudioVolumeEvaluationListener itxAudioVolumeEvaluationListener) {
        this.mTXTxLivePusherImpl.a(itxAudioVolumeEvaluationListener);
    }
    
    public void enableAudioVolumeEvaluation(final int n) {
        this.mTXTxLivePusherImpl.f(n);
    }
    
    public void setVideoRecordListener(final TXRecordCommon.ITXVideoRecordListener itxVideoRecordListener) {
        this.mTXTxLivePusherImpl.a(itxVideoRecordListener);
    }
    
    public int startRecord(final String s) {
        return this.mTXTxLivePusherImpl.d(s);
    }
    
    public void stopRecord() {
        this.mTXTxLivePusherImpl.p();
    }
    
    public void snapshot(final ITXSnapshotListener itxSnapshotListener) {
        this.mTXTxLivePusherImpl.a(itxSnapshotListener);
    }
    
    public int sendCustomVideoTexture(final int n, final int n2, final int n3) {
        return this.mTXTxLivePusherImpl.b(n, n2, n3);
    }
    
    public int sendCustomVideoData(final byte[] array, final int n, final int n2, final int n3) {
        return this.mTXTxLivePusherImpl.a(array, n, n2, n3);
    }
    
    public void sendCustomPCMData(final byte[] array) {
        this.mTXTxLivePusherImpl.a(array);
    }
    
    public void setVideoProcessListener(final VideoCustomProcessListener videoCustomProcessListener) {
        this.mTXTxLivePusherImpl.a(videoCustomProcessListener);
    }
    
    public void setAudioProcessListener(final AudioCustomProcessListener audioCustomProcessListener) {
        this.mTXTxLivePusherImpl.a(audioCustomProcessListener);
    }
    
    public void setSurface(final Surface surface) {
        this.mTXTxLivePusherImpl.a(surface);
    }
    
    public void setSurfaceSize(final int n, final int n2) {
        this.mTXTxLivePusherImpl.a(n, n2);
    }
    
    public void setFocusPosition(final float n, final float n2) {
        this.mTXTxLivePusherImpl.a(n, n2);
    }
    
    public boolean sendMessageEx(final byte[] array) {
        return this.mTXTxLivePusherImpl.b(array);
    }
    
    @Deprecated
    public void sendMessage(final byte[] array) {
        this.mTXTxLivePusherImpl.c(array);
    }
    
    public void onLogRecord(final String s) {
        this.mTXTxLivePusherImpl.e(s);
    }
    
    @Deprecated
    public void setBGMNofify(final OnBGMNotify onBGMNotify) {
        this.mTXTxLivePusherImpl.a(onBGMNotify);
    }
    
    @Deprecated
    public boolean playBGM(final String s) {
        return this.mTXTxLivePusherImpl.b(s);
    }
    
    @Deprecated
    public boolean stopBGM() {
        return this.mTXTxLivePusherImpl.l();
    }
    
    @Deprecated
    public boolean pauseBGM() {
        return this.mTXTxLivePusherImpl.n();
    }
    
    @Deprecated
    public boolean resumeBGM() {
        return this.mTXTxLivePusherImpl.o();
    }
    
    @Deprecated
    public int getMusicDuration(final String s) {
        return this.mTXTxLivePusherImpl.c(s);
    }
    
    @Deprecated
    public boolean setBGMVolume(final float n) {
        return this.mTXTxLivePusherImpl.b(n);
    }
    
    @Deprecated
    public boolean setMicVolume(final float n) {
        return this.mTXTxLivePusherImpl.c(n);
    }
    
    @Deprecated
    public void setBGMPitch(final float n) {
        this.mTXTxLivePusherImpl.d(n);
    }
    
    @Deprecated
    public void setReverb(final int n) {
        this.mTXTxLivePusherImpl.d(n);
    }
    
    @Deprecated
    public void setVoiceChangerType(final int n) {
        this.mTXTxLivePusherImpl.e(n);
    }
    
    @Deprecated
    public boolean setBGMPosition(final int n) {
        return this.mTXTxLivePusherImpl.c(n);
    }
    
    @Deprecated
    public interface OnBGMNotify
    {
        void onBGMStart();
        
        void onBGMProgress(final long p0, final long p1);
        
        void onBGMComplete(final int p0);
    }
    
    public interface ITXAudioVolumeEvaluationListener
    {
        void onAudioVolumeEvaluationNotify(final int p0);
    }
    
    public interface ITXSnapshotListener
    {
        void onSnapshot(final Bitmap p0);
    }
    
    public interface AudioCustomProcessListener
    {
        void onRecordRawPcmData(final byte[] p0, final long p1, final int p2, final int p3, final int p4, final boolean p5);
        
        void onRecordPcmData(final byte[] p0, final long p1, final int p2, final int p3, final int p4);
    }
    
    public interface VideoCustomProcessListener
    {
        int onTextureCustomProcess(final int p0, final int p1, final int p2);
        
        void onDetectFacePoints(final float[] p0);
        
        void onTextureDestoryed();
    }
}
