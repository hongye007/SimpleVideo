package com.tencent.trtc;

import android.os.*;
import java.util.*;
import android.graphics.*;

public abstract class TRTCCloudListener
{
    public void onError(final int n, final String s, final Bundle bundle) {
    }
    
    public void onWarning(final int n, final String s, final Bundle bundle) {
    }
    
    public void onEnterRoom(final long n) {
    }
    
    public void onExitRoom(final int n) {
    }
    
    public void onSwitchRole(final int n, final String s) {
    }
    
    public void onConnectOtherRoom(final String s, final int n, final String s2) {
    }
    
    public void onDisConnectOtherRoom(final int n, final String s) {
    }
    
    public void onRemoteUserEnterRoom(final String s) {
    }
    
    public void onRemoteUserLeaveRoom(final String s, final int n) {
    }
    
    public void onUserVideoAvailable(final String s, final boolean b) {
    }
    
    public void onUserSubStreamAvailable(final String s, final boolean b) {
    }
    
    public void onUserAudioAvailable(final String s, final boolean b) {
    }
    
    public void onFirstVideoFrame(final String s, final int n, final int n2, final int n3) {
    }
    
    public void onFirstAudioFrame(final String s) {
    }
    
    public void onSendFirstLocalVideoFrame(final int n) {
    }
    
    public void onSendFirstLocalAudioFrame() {
    }
    
    @Deprecated
    public void onUserEnter(final String s) {
    }
    
    @Deprecated
    public void onUserExit(final String s, final int n) {
    }
    
    public void onNetworkQuality(final TRTCCloudDef.TRTCQuality trtcQuality, final ArrayList<TRTCCloudDef.TRTCQuality> list) {
    }
    
    public void onStatistics(final TRTCStatistics trtcStatistics) {
    }
    
    public void onConnectionLost() {
    }
    
    public void onTryToReconnect() {
    }
    
    public void onConnectionRecovery() {
    }
    
    public void onSpeedTest(final TRTCCloudDef.TRTCSpeedTestResult trtcSpeedTestResult, final int n, final int n2) {
    }
    
    public void onCameraDidReady() {
    }
    
    public void onMicDidReady() {
    }
    
    public void onAudioRouteChanged(final int n, final int n2) {
    }
    
    public void onUserVoiceVolume(final ArrayList<TRTCCloudDef.TRTCVolumeInfo> list, final int n) {
    }
    
    public void onRecvCustomCmdMsg(final String s, final int n, final int n2, final byte[] array) {
    }
    
    public void onMissCustomCmdMsg(final String s, final int n, final int n2, final int n3) {
    }
    
    public void onRecvSEIMsg(final String s, final byte[] array) {
    }
    
    public void onStartPublishing(final int n, final String s) {
    }
    
    public void onStopPublishing(final int n, final String s) {
    }
    
    public void onStartPublishCDNStream(final int n, final String s) {
    }
    
    public void onStopPublishCDNStream(final int n, final String s) {
    }
    
    public void onSetMixTranscodingConfig(final int n, final String s) {
    }
    
    public void onAudioEffectFinished(final int n, final int n2) {
    }
    
    public void onScreenCaptureStarted() {
    }
    
    public void onScreenCapturePaused() {
    }
    
    public void onScreenCaptureResumed() {
    }
    
    public void onScreenCaptureStopped(final int n) {
    }
    
    public abstract static class TRTCLogListener
    {
        public abstract void onLog(final String p0, final int p1, final String p2);
    }
    
    public interface TRTCSnapshotListener
    {
        void onSnapshotComplete(final Bitmap p0);
    }
    
    public interface TRTCAudioFrameListener
    {
        void onCapturedRawAudioFrame(final TRTCCloudDef.TRTCAudioFrame p0);
        
        void onLocalProcessedAudioFrame(final TRTCCloudDef.TRTCAudioFrame p0);
        
        void onRemoteUserAudioFrame(final TRTCCloudDef.TRTCAudioFrame p0, final String p1);
        
        void onMixedPlayAudioFrame(final TRTCCloudDef.TRTCAudioFrame p0);
    }
    
    public interface TRTCVideoRenderListener
    {
        void onRenderVideoFrame(final String p0, final int p1, final TRTCCloudDef.TRTCVideoFrame p2);
    }
}
