package com.tencent.avroom;

import android.graphics.*;

public class TXCAVRoomConfig
{
    private static int LOCAL_RENDER_MODE;
    private static int REMOTE_RENDER_MODE;
    private boolean enableVideoHWAcceleration;
    private int homeOrientation;
    private int captureFPS;
    private int videoAspect;
    private int videoBitrate;
    private boolean frontCamera;
    private int pauseFps;
    private Bitmap pauseImg;
    private int pauseFlag;
    private boolean enablePureAudioPush;
    public static final int AVROOM_VIDEO_ASPECT_9_16 = 1;
    public static final int AVROOM_VIDEO_ASPECT_3_4 = 2;
    public static final int AVROOM_VIDEO_ASPECT_1_1 = 3;
    private boolean isHasVideo;
    
    public boolean isHasVideo() {
        return this.isHasVideo;
    }
    
    public void setHasVideo(final boolean isHasVideo) {
        this.isHasVideo = isHasVideo;
    }
    
    public TXCAVRoomConfig() {
        this.enableVideoHWAcceleration = true;
        this.homeOrientation = TXCAVRoomConstants.AVROOM_HOME_ORIENTATION_DOWN;
        this.captureFPS = 15;
        this.videoAspect = 1;
        this.videoBitrate = 600;
        this.frontCamera = true;
        this.pauseFps = 5;
        this.pauseImg = null;
        this.pauseFlag = 3;
        this.enablePureAudioPush = false;
        this.isHasVideo = false;
    }
    
    public boolean isEnableVideoHWAcceleration() {
        return this.enableVideoHWAcceleration;
    }
    
    public TXCAVRoomConfig enableVideoHWAcceleration(final boolean enableVideoHWAcceleration) {
        this.enableVideoHWAcceleration = enableVideoHWAcceleration;
        return this;
    }
    
    public int getHomeOrientation() {
        return this.homeOrientation;
    }
    
    public TXCAVRoomConfig homeOrientation(final int homeOrientation) {
        this.homeOrientation = homeOrientation;
        return this;
    }
    
    public int getCaptureVideoFPS() {
        return this.captureFPS;
    }
    
    public TXCAVRoomConfig setCaptureVideoFPS(final int captureFPS) {
        this.captureFPS = captureFPS;
        return this;
    }
    
    public int getVideoAspect() {
        return this.videoAspect;
    }
    
    public TXCAVRoomConfig VideoAspect(final int videoAspect) {
        this.videoAspect = videoAspect;
        return this;
    }
    
    public int getVideoBitrate() {
        return this.videoBitrate;
    }
    
    public TXCAVRoomConfig videoBitrate(final int videoBitrate) {
        this.videoBitrate = videoBitrate;
        return this;
    }
    
    public boolean isFrontCamera() {
        return this.frontCamera;
    }
    
    public TXCAVRoomConfig frontCamera(final boolean frontCamera) {
        this.frontCamera = frontCamera;
        return this;
    }
    
    public int getPauseFps() {
        return this.pauseFps;
    }
    
    public TXCAVRoomConfig pauseFps(final int pauseFps) {
        this.pauseFps = pauseFps;
        return this;
    }
    
    public boolean isEnablePureAudioPush() {
        return this.enablePureAudioPush;
    }
    
    public TXCAVRoomConfig enablePureAudioPush(final boolean enablePureAudioPush) {
        this.enablePureAudioPush = enablePureAudioPush;
        return this;
    }
    
    public Bitmap getPauseImg() {
        return this.pauseImg;
    }
    
    public TXCAVRoomConfig pauseImg(final Bitmap pauseImg) {
        this.pauseImg = pauseImg;
        return this;
    }
    
    public int getLocalRenderMode() {
        return TXCAVRoomConfig.LOCAL_RENDER_MODE;
    }
    
    public TXCAVRoomConfig setLocalRenderMode(final int local_RENDER_MODE) {
        TXCAVRoomConfig.LOCAL_RENDER_MODE = local_RENDER_MODE;
        return this;
    }
    
    public int getRemoteRenderMode() {
        return TXCAVRoomConfig.REMOTE_RENDER_MODE;
    }
    
    public TXCAVRoomConfig setRemoteRenderMode(final int remote_RENDER_MODE) {
        TXCAVRoomConfig.REMOTE_RENDER_MODE = remote_RENDER_MODE;
        return this;
    }
    
    public void setPauseFlag(final int pauseFlag) {
        this.pauseFlag = pauseFlag;
    }
    
    public int getPauseFlag() {
        return this.pauseFlag;
    }
    
    static {
        TXCAVRoomConfig.LOCAL_RENDER_MODE = 0;
        TXCAVRoomConfig.REMOTE_RENDER_MODE = 0;
    }
}
