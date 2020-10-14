package com.tencent.trtc;

import java.lang.ref.*;
import android.content.*;
import android.os.*;
import android.text.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.module.*;
import com.tencent.liteav.trtc.impl.*;
import com.tencent.liteav.audio.*;
import com.tencent.liteav.basic.a.*;
import com.tencent.liteav.*;
import com.tencent.rtmp.ui.*;
import com.tencent.liteav.basic.c.*;
import android.graphics.*;
import android.view.*;
import com.tencent.liteav.beauty.*;
import java.util.*;
import org.json.*;

public class TRTCSubCloud extends TRTCCloudImpl
{
    private static final String TAG;
    protected WeakReference<TRTCCloudImpl> mMainCloud;
    private a mVolumeLevelCalTask;
    
    public TRTCSubCloud(final Context context, final WeakReference<TRTCCloudImpl> mMainCloud, final Handler handler) {
        super(context, handler);
        this.mMainCloud = null;
        this.mVolumeLevelCalTask = null;
        this.mRoomInfo.muteLocalAudio = true;
        this.mRoomInfo.muteLocalVideo = true;
        this.mMainCloud = mMainCloud;
    }
    
    @Override
    public void destroy() {
        this.runOnSDKThread(new Runnable() {
            @Override
            public void run() {
                synchronized (TRTCSubCloud.this.mNativeLock) {
                    if (TRTCSubCloud.this.mNativeRtcContext != 0L) {
                        TRTCCloudImpl.this.apiLog("destroy context");
                        TRTCCloudImpl.this.nativeDestroyContext(TRTCSubCloud.this.mNativeRtcContext);
                    }
                    TRTCSubCloud.this.mNativeRtcContext = 0L;
                }
                TRTCSubCloud.this.mTRTCListener = null;
                TRTCSubCloud.this.mAudioFrameListener = null;
                TRTCSubCloud.this.mCurrentPublishClouds.clear();
                TRTCSubCloud.this.mSubClouds.clear();
            }
        });
    }
    
    @Override
    public void finalize() throws Throwable {
        this.mSDKHandler = null;
        super.finalize();
    }
    
    @Override
    public void setListener(final TRTCCloudListener listener) {
        super.setListener(listener);
    }
    
    @Override
    public void setListenerHandler(final Handler listenerHandler) {
        super.setListenerHandler(listenerHandler);
    }
    
    @Override
    public void enterRoom(final TRTCCloudDef.TRTCParams trtcParams, final int n) {
        if (trtcParams == null) {
            this.apiLog("enter room, param nil!");
            this.onEnterRoom(-3316, "enter room param null");
            return;
        }
        final TRTCCloudDef.TRTCParams trtcParams2 = new TRTCCloudDef.TRTCParams(trtcParams);
        if (trtcParams2.sdkAppId == 0 || TextUtils.isEmpty((CharSequence)trtcParams2.userId) || TextUtils.isEmpty((CharSequence)trtcParams2.userSig)) {
            this.apiLog("enterRoom param invalid:" + trtcParams2);
            if (trtcParams2.sdkAppId == 0) {
                this.onEnterRoom(-3317, "enter room sdkAppId invalid.");
            }
            if (TextUtils.isEmpty((CharSequence)trtcParams2.userSig)) {
                this.onEnterRoom(-3320, "enter room userSig invalid.");
            }
            if (TextUtils.isEmpty((CharSequence)trtcParams2.userId)) {
                this.onEnterRoom(-3319, "enter room userId invalid.");
            }
            return;
        }
        final long n2 = (long)trtcParams2.roomId & 0xFFFFFFFFL;
        if (n2 == 0L) {
            this.apiLog("enter room, room id " + n2 + " error");
            this.onEnterRoom(-3318, "room id invalid.");
            return;
        }
        String optString = "";
        String s = trtcParams2.businessInfo;
        if (trtcParams2.roomId == -1 && !TextUtils.isEmpty((CharSequence)trtcParams2.businessInfo)) {
            try {
                final JSONObject jsonObject = new JSONObject(trtcParams2.businessInfo);
                optString = jsonObject.optString("strGroupId");
                jsonObject.remove("strGroupId");
                jsonObject.remove("Role");
                s = "";
                if (jsonObject.length() != 0) {
                    s = jsonObject.toString();
                }
            }
            catch (Exception ex) {
                this.apiLog("enter room, room id error, busInfo " + trtcParams2.businessInfo);
                optString = "";
                s = "";
            }
            if (TextUtils.isEmpty((CharSequence)optString)) {
                this.onEnterRoom(-3318, "room id invalid.");
                return;
            }
        }
        this.runOnSDKThread(new Runnable() {
            final /* synthetic */ int f = trtcParams2.role;
            final /* synthetic */ long g = System.currentTimeMillis();
            
            @Override
            public void run() {
                if (TRTCSubCloud.this.mRoomState != 0) {
                    if ((!TextUtils.isEmpty((CharSequence)optString) && optString.equalsIgnoreCase(TRTCSubCloud.this.mRoomInfo.strRoomId)) || TRTCSubCloud.this.mRoomInfo.roomId == n2) {
                        TRTCCloudImpl.this.apiLog(String.format("enter the same room[%d] again, ignore!!!", n2));
                        return;
                    }
                    TRTCCloudImpl.this.apiLog(String.format("enter another room[%d] when in room[%d], exit the old room!!!", n2, TRTCSubCloud.this.mRoomInfo.roomId));
                    TRTCSubCloud.this.mIsExitOldRoom = true;
                    TRTCSubCloud.this.exitRoom();
                }
                TRTCCloudImpl.this.apiLog("========================================================================================================");
                TRTCCloudImpl.this.apiLog("========================================================================================================");
                TRTCCloudImpl.this.apiLog(String.format("============= SDK Version:%s Device Name:%s System Version:%s =============", TXCCommonUtil.getSDKVersionStr(), com.tencent.liteav.basic.util.f.c(), com.tencent.liteav.basic.util.f.d()));
                TRTCCloudImpl.this.apiLog("========================================================================================================");
                TRTCCloudImpl.this.apiLog("========================================================================================================");
                TRTCCloudImpl.this.apiLog(String.format("enterRoom roomId:%d(%s)  userId:%s sdkAppId:%d scene:%d", n2, optString, trtcParams2.userId, trtcParams2.sdkAppId, n));
                final String string = "enterRoom self:" + TRTCSubCloud.this.hashCode() + ", roomId:" + ((trtcParams2.roomId == -1) ? optString : Integer.valueOf(trtcParams2.roomId));
                int d = n;
                String s = "VideoCall";
                switch (n) {
                    case 0: {
                        s = "VideoCall";
                        break;
                    }
                    case 1: {
                        s = "Live";
                        break;
                    }
                    case 2: {
                        s = "AudioCall";
                        d = 0;
                        break;
                    }
                    case 3: {
                        s = "VoiceChatRoom";
                        d = 1;
                        break;
                    }
                    default: {
                        TXCLog.w(TRTCSubCloud.TAG, "enter room scene:%u error! default to VideoCall! " + n + " self:" + TRTCSubCloud.this.hashCode());
                        d = 0;
                        break;
                    }
                }
                Monitor.a(1, string, String.format("bussInfo:%s, appScene:%s, role:%s, streamid:%s", s, s, (this.f == 20) ? "Anchor" : "Audience", trtcParams2.streamId), 0);
                TXCEventRecorderProxy.a("18446744073709551615", 5001, n2, -1L, "", 0);
                TRTCSubCloud.this.mRoomState = 1;
                if (TRTCSubCloud.this.mNativeRtcContext == 0L) {
                    final int[] sdkVersion = TXCCommonUtil.getSDKVersion();
                    TRTCSubCloud.this.mNativeRtcContext = TRTCCloudImpl.this.nativeCreateContext((sdkVersion.length >= 1) ? sdkVersion[0] : 0, (sdkVersion.length >= 2) ? sdkVersion[1] : 0, (sdkVersion.length >= 3) ? sdkVersion[2] : 0);
                }
                TRTCCloudImpl.this.updateAppScene(d);
                TRTCCloudImpl.this.nativeSetPriorRemoteVideoStreamType(TRTCSubCloud.this.mNativeRtcContext, TRTCSubCloud.this.mPriorStreamType);
                TRTCCloudImpl.this.nativeInit(TRTCSubCloud.this.mNativeRtcContext, trtcParams2.sdkAppId, trtcParams2.userId, trtcParams2.userSig, TRTCSubCloud.this.mRoomInfo.getToken(TRTCSubCloud.this.mContext));
                final String privateMapKey = (trtcParams2.privateMapKey != null) ? trtcParams2.privateMapKey : "";
                final String strRoomId = (optString != null) ? optString : "";
                TRTCCloudImpl.this.nativeEnterRoom(TRTCSubCloud.this.mNativeRtcContext, n2, (s != null) ? s : "", privateMapKey, strRoomId, this.f, 255, 0, n, TRTCSubCloud.this.mPerformanceMode, com.tencent.liteav.basic.util.f.c(), com.tencent.liteav.basic.util.f.d(), TRTCSubCloud.this.mRecvMode, (trtcParams2.userDefineRecordId != null) ? trtcParams2.userDefineRecordId : "", (trtcParams2.streamId != null) ? trtcParams2.streamId : "");
                TRTCSubCloud.this.mCurrentRole = this.f;
                TRTCSubCloud.this.mTargetRole = this.f;
                TRTCCloudImpl.this.startCollectStatus();
                TRTCSubCloud.this.mLastStateTimeMs = 0L;
                TRTCSubCloud.this.mRoomInfo.init(n2, trtcParams2.userId);
                TRTCSubCloud.this.mRoomInfo.strRoomId = strRoomId;
                TRTCSubCloud.this.mRoomInfo.sdkAppId = trtcParams2.sdkAppId;
                TRTCSubCloud.this.mRoomInfo.userSig = trtcParams2.userSig;
                TRTCSubCloud.this.mRoomInfo.privateMapKey = privateMapKey;
                TRTCSubCloud.this.mRoomInfo.enterTime = this.g;
            }
        });
    }
    
    @Override
    public void exitRoom() {
        this.runOnSDKThread(new Runnable() {
            @Override
            public void run() {
                final String string = "exitRoom " + TRTCSubCloud.this.mRoomInfo.getRoomId() + ", " + TRTCSubCloud.this.hashCode();
                TRTCCloudImpl.this.apiLog(string);
                Monitor.a(1, string, "", 0);
                TRTCSubCloud.this.exitRoomInternal(true, "call from api");
            }
        });
    }
    
    @Override
    protected void exitRoomInternal(final boolean b, final String s) {
        this.apiLog("exitRoomInternal reqExit: " + b + ", reason: " + s + ", mRoomState: " + this.mRoomState);
        if (this.mRoomState == 0) {
            this.apiLog("exitRoom ignore when no in room");
            return;
        }
        this.mRoomState = 0;
        this.stopCollectStatus();
        this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() {
            @Override
            public void accept(final String s, final TRTCRoomInfo.UserInfo userInfo) {
                TRTCCloudImpl.this.stopRemoteRender(userInfo);
                TXCAudioEngine.getInstance().stopRemoteAudio(String.valueOf(userInfo.tinyID));
                if (userInfo.mainRender.render != null) {
                    userInfo.mainRender.render.setVideoFrameListener(null, b.a);
                }
                if (userInfo.subRender.render != null) {
                    userInfo.subRender.render.setVideoFrameListener(null, b.a);
                }
            }
        });
        if (b) {
            this.nativeExitRoom(this.mNativeRtcContext);
        }
        this.mRoomInfo.clear();
        this.mRenderListenerMap.clear();
    }
    
    @Override
    public void switchRole(final int n) {
        super.switchRole(n);
    }
    
    @Override
    public void ConnectOtherRoom(final String s) {
        super.ConnectOtherRoom(s);
    }
    
    @Override
    public void DisconnectOtherRoom() {
        super.DisconnectOtherRoom();
    }
    
    @Override
    public void setDefaultStreamRecvMode(final boolean b, final boolean b2) {
        super.setDefaultStreamRecvMode(b, b2);
    }
    
    @Override
    public void startLocalPreview(final boolean b, final TXCloudVideoView txCloudVideoView) {
    }
    
    @Override
    public void stopLocalPreview() {
    }
    
    @Override
    public void startRemoteView(final String s, final TXCloudVideoView txCloudVideoView) {
        super.startRemoteView(s, txCloudVideoView);
    }
    
    @Override
    public void stopRemoteView(final String s) {
        super.stopRemoteView(s);
    }
    
    @Override
    public void startRemoteSubStreamView(final String s, final TXCloudVideoView txCloudVideoView) {
        super.startRemoteSubStreamView(s, txCloudVideoView);
    }
    
    @Override
    public void stopRemoteSubStreamView(final String s) {
        super.stopRemoteSubStreamView(s);
    }
    
    @Override
    public void setRemoteSubStreamViewFillMode(final String s, final int n) {
        super.setRemoteSubStreamViewFillMode(s, n);
    }
    
    @Override
    public void setRemoteSubStreamViewRotation(final String s, final int n) {
        super.setRemoteSubStreamViewRotation(s, n);
    }
    
    @Override
    public void stopAllRemoteView() {
        super.stopAllRemoteView();
    }
    
    @Override
    public void snapshotVideo(final String s, final int n, final TRTCCloudListener.TRTCSnapshotListener trtcSnapshotListener) {
        this.apiLog(String.format("snapshotVideo user:%s streamType:%d", s, n));
        this.runOnSDKThread(new Runnable() {
            @Override
            public void run() {
                if (s != null) {
                    final TRTCRoomInfo.UserInfo user = TRTCSubCloud.this.mRoomInfo.getUser(s);
                    com.tencent.liteav.renderer.f f = null;
                    if (n == 2) {
                        if (user != null && user.mainRender != null && user.mainRender.render != null) {
                            TRTCCloudImpl.this.apiLog("snapshotRemoteSubStreamView->userId: " + s);
                            f = user.subRender.render.getVideoRender();
                        }
                    }
                    else if (user != null && user.mainRender != null && user.mainRender.render != null) {
                        TRTCCloudImpl.this.apiLog("snapshotRemoteView->userId: " + s);
                        f = user.mainRender.render.getVideoRender();
                    }
                    if (f != null) {
                        f.a(new o() {
                            @Override
                            public void onTakePhotoComplete(final Bitmap bitmap) {
                                TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (trtcSnapshotListener != null) {
                                            trtcSnapshotListener.onSnapshotComplete(bitmap);
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else {
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() {
                            @Override
                            public void run() {
                                if (trtcSnapshotListener != null) {
                                    trtcSnapshotListener.onSnapshotComplete(null);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    
    @Override
    public void muteLocalVideo(final boolean b) {
        this.runOnSDKThread(new Runnable() {
            @Override
            public void run() {
                TRTCCloudImpl.this.apiLog("muteLocalVideo " + b + ", roomId=" + TRTCSubCloud.this.mRoomInfo.getRoomId());
                Monitor.a(1, String.format("muteLocalVideo mute:%b", b) + " self:" + TRTCSubCloud.this.hashCode(), "", 0);
                final TRTCCloudImpl trtcCloudImpl = TRTCSubCloud.this.mMainCloud.get();
                if (trtcCloudImpl != null) {
                    trtcCloudImpl.muteLocalVideo(b, TRTCSubCloud.this);
                }
            }
        });
    }
    
    @Override
    public void muteRemoteVideoStream(final String s, final boolean b) {
        super.muteRemoteVideoStream(s, b);
    }
    
    @Override
    public void muteAllRemoteVideoStreams(final boolean b) {
        super.muteAllRemoteVideoStreams(b);
    }
    
    @Override
    public void setVideoEncoderParam(final TRTCCloudDef.TRTCVideoEncParam trtcVideoEncParam) {
    }
    
    @Override
    public void setNetworkQosParam(final TRTCCloudDef.TRTCNetworkQosParam trtcNetworkQosParam) {
    }
    
    @Override
    public void setLocalViewFillMode(final int n) {
    }
    
    @Override
    public void setRemoteViewFillMode(final String s, final int n) {
        super.setRemoteViewFillMode(s, n);
    }
    
    @Override
    public void setLocalViewRotation(final int n) {
    }
    
    @Override
    public void setRemoteViewRotation(final String s, final int n) {
        super.setRemoteViewRotation(s, n);
    }
    
    @Override
    public void setVideoEncoderRotation(final int n) {
    }
    
    @Override
    public void setGSensorMode(final int n) {
    }
    
    @Override
    public int enableEncSmallVideoStream(final boolean b, final TRTCCloudDef.TRTCVideoEncParam trtcVideoEncParam) {
        return -1;
    }
    
    @Override
    public int setPriorRemoteVideoStreamType(final int priorRemoteVideoStreamType) {
        return super.setPriorRemoteVideoStreamType(priorRemoteVideoStreamType);
    }
    
    @Override
    public void setLocalViewMirror(final int n) {
    }
    
    @Override
    public void setVideoEncoderMirror(final boolean b) {
    }
    
    @Override
    public void startLocalAudio() {
    }
    
    @Override
    public void stopLocalAudio() {
    }
    
    @Override
    public int setRemoteVideoStreamType(final String s, final int n) {
        return super.setRemoteVideoStreamType(s, n);
    }
    
    @Override
    public void setAudioRoute(final int n) {
    }
    
    @Override
    public void muteLocalAudio(final boolean b) {
        this.runOnSDKThread(new Runnable() {
            @Override
            public void run() {
                TRTCCloudImpl.this.apiLog("muteLocalAudio " + b + ", roomId=" + TRTCSubCloud.this.mRoomInfo.getRoomId());
                Monitor.a(1, String.format("muteLocalAudio mute:%b", b) + " self:" + TRTCSubCloud.this.hashCode(), "", 0);
                final TRTCCloudImpl trtcCloudImpl = TRTCSubCloud.this.mMainCloud.get();
                if (trtcCloudImpl != null) {
                    trtcCloudImpl.muteLocalAudio(b, TRTCSubCloud.this);
                }
            }
        });
    }
    
    @Override
    public void muteRemoteAudio(final String s, final boolean b) {
        super.muteRemoteAudio(s, b);
    }
    
    @Override
    public void muteAllRemoteAudio(final boolean b) {
        super.muteAllRemoteAudio(b);
    }
    
    @Override
    public void setRemoteAudioVolume(final String s, final int n) {
        super.setRemoteAudioVolume(s, n);
    }
    
    @Override
    public void setAudioCaptureVolume(final int n) {
    }
    
    @Override
    public int getAudioCaptureVolume() {
        return 0;
    }
    
    @Override
    public void setAudioPlayoutVolume(final int n) {
    }
    
    @Override
    public int getAudioPlayoutVolume() {
        return 0;
    }
    
    @Override
    public void setSystemVolumeType(final int n) {
    }
    
    @Override
    public void enableAudioEarMonitoring(final boolean b) {
    }
    
    @Override
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
    }
    
    @Override
    public void surfaceChanged(final SurfaceHolder surfaceHolder, final int n, final int n2, final int n3) {
    }
    
    @Override
    public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
    }
    
    @Override
    protected void startVolumeLevelCal(final boolean b) {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(b, this.mAudioVolumeEvalInterval);
        if (b) {
            if (this.mVolumeLevelCalTask == null) {
                this.mVolumeLevelCalTask = new a(this);
                this.mSDKHandler.postDelayed((Runnable)this.mVolumeLevelCalTask, (long)this.mAudioVolumeEvalInterval);
            }
        }
        else {
            this.mVolumeLevelCalTask = null;
            this.mAudioVolumeEvalInterval = 0;
        }
    }
    
    @Override
    public void enableAudioVolumeEvaluation(final int n) {
        super.enableAudioVolumeEvaluation(n);
    }
    
    @Override
    public int startAudioRecording(final TRTCCloudDef.TRTCAudioRecordingParams trtcAudioRecordingParams) {
        return -1;
    }
    
    @Override
    public void stopAudioRecording() {
    }
    
    @Override
    public void switchCamera() {
    }
    
    @Override
    public boolean isCameraZoomSupported() {
        return false;
    }
    
    @Override
    public void setZoom(final int n) {
    }
    
    @Override
    public boolean isCameraTorchSupported() {
        return false;
    }
    
    @Override
    public boolean enableTorch(final boolean b) {
        return false;
    }
    
    @Override
    public boolean isCameraFocusPositionInPreviewSupported() {
        return false;
    }
    
    @Override
    public void setFocusPosition(final int n, final int n2) {
    }
    
    @Override
    public boolean isCameraAutoFocusFaceModeSupported() {
        return false;
    }
    
    @Override
    public TXBeautyManager getBeautyManager() {
        return null;
    }
    
    @Override
    public void setBeautyStyle(final int n, final int n2, final int n3, final int n4) {
    }
    
    @Override
    public void setFilter(final Bitmap bitmap) {
    }
    
    @Override
    public void setFilterConcentration(final float n) {
    }
    
    @Override
    public void selectMotionTmpl(final String s) {
    }
    
    @Override
    public void setMotionMute(final boolean b) {
    }
    
    @Override
    public boolean setGreenScreenFile(final String s) {
        return false;
    }
    
    @Override
    public void setEyeScaleLevel(final int n) {
    }
    
    @Override
    public void setFaceSlimLevel(final int n) {
    }
    
    @Override
    public void setFaceVLevel(final int n) {
    }
    
    @Override
    public void setFaceShortLevel(final int n) {
    }
    
    @Override
    public void setChinLevel(final int n) {
    }
    
    @Override
    public void setNoseSlimLevel(final int n) {
    }
    
    @Override
    public void setWatermark(final Bitmap bitmap, final int n, final float n2, final float n3, final float n4) {
    }
    
    @Override
    public void enableCustomVideoCapture(final boolean b) {
    }
    
    @Override
    public void sendCustomVideoData(final TRTCCloudDef.TRTCVideoFrame trtcVideoFrame) {
    }
    
    @Override
    public void callExperimentalAPI(final String s) {
        if (s != null) {
            this.apiLog("callExperimentalAPI  " + s + ", roomid = " + ((this.mRoomInfo.roomId != -1L) ? Long.valueOf(this.mRoomInfo.roomId) : this.mRoomInfo.strRoomId));
            Monitor.a(1, String.format("callExperimentalAPI:%s", s) + " self:" + this.hashCode(), "", 0);
        }
        this.runOnSDKThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.has("api")) {
                        TRTCCloudImpl.this.apiLog("callExperimentalAPI[lack api or illegal type]: " + s);
                        return;
                    }
                    final String string = jsonObject.getString("api");
                    JSONObject jsonObject2 = null;
                    if (jsonObject.has("params")) {
                        jsonObject2 = jsonObject.getJSONObject("params");
                    }
                    if (string.equals("setSEIPayloadType")) {
                        TRTCCloudImpl.this.setSEIPayloadType(jsonObject2);
                    }
                    else if (!string.equals("setLocalAudioMuteMode")) {
                        if (!string.equals("setVideoEncodeParamEx")) {
                            if (!string.equals("setAudioSampleRate")) {
                                if (string.equals("muteRemoteAudioInSpeaker")) {
                                    TRTCCloudImpl.this.muteRemoteAudioInSpeaker(jsonObject2);
                                }
                                else if (!string.equals("enableAudioAGC")) {
                                    if (!string.equals("enableAudioAEC")) {
                                        if (!string.equals("enableAudioANS")) {
                                            if (string.equals("setPerformanceMode")) {
                                                TRTCCloudImpl.this.setPerformanceMode(jsonObject2);
                                            }
                                            else if (!string.equals("setCustomRenderMode")) {
                                                if (!string.equals("setMediaCodecConfig")) {
                                                    if (string.equals("sendJsonCMD")) {
                                                        TRTCCloudImpl.this.sendJsonCmd(jsonObject2, s);
                                                    }
                                                    else if (string.equals("updatePrivateMapKey")) {
                                                        TRTCCloudImpl.this.updatePrivateMapKey(jsonObject2);
                                                    }
                                                    else {
                                                        TRTCCloudImpl.this.apiLog("callExperimentalAPI[illegal api]: " + string);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                catch (Exception ex) {
                    TRTCCloudImpl.this.apiLog("callExperimentalAPI[failed]: " + s);
                }
            }
        });
    }
    
    @Override
    public int setLocalVideoRenderListener(final int n, final int n2, final TRTCCloudListener.TRTCVideoRenderListener trtcVideoRenderListener) {
        return -1;
    }
    
    @Override
    public int setRemoteVideoRenderListener(final String s, final int n, final int n2, final TRTCCloudListener.TRTCVideoRenderListener trtcVideoRenderListener) {
        return super.setRemoteVideoRenderListener(s, n, n2, trtcVideoRenderListener);
    }
    
    @Override
    public void enableCustomAudioCapture(final boolean b) {
    }
    
    @Override
    public void sendCustomAudioData(final TRTCCloudDef.TRTCAudioFrame trtcAudioFrame) {
    }
    
    @Override
    public void playBGM(final String s, final BGMNotify bgmNotify) {
    }
    
    @Override
    public void stopBGM() {
    }
    
    @Override
    public void pauseBGM() {
    }
    
    @Override
    public void resumeBGM() {
    }
    
    @Override
    public int getBGMDuration(final String s) {
        return 0;
    }
    
    @Override
    public int setBGMPosition(final int n) {
        return 0;
    }
    
    @Override
    public void setMicVolumeOnMixing(final int n) {
    }
    
    @Override
    public void setBGMVolume(final int n) {
    }
    
    @Override
    public void setBGMPlayoutVolume(final int n) {
    }
    
    @Override
    public void setBGMPublishVolume(final int n) {
    }
    
    @Override
    public void setReverbType(final int n) {
    }
    
    @Override
    public boolean setVoiceChangerType(final int n) {
        return false;
    }
    
    @Override
    public void playAudioEffect(final TRTCCloudDef.TRTCAudioEffectParam trtcAudioEffectParam) {
    }
    
    @Override
    public void setAudioEffectVolume(final int n, final int n2) {
    }
    
    @Override
    public void stopAudioEffect(final int n) {
    }
    
    @Override
    public void stopAllAudioEffects() {
    }
    
    @Override
    public void setAllAudioEffectsVolume(final int n) {
    }
    
    @Override
    public void pauseAudioEffect(final int n) {
    }
    
    @Override
    public void resumeAudioEffect(final int n) {
    }
    
    @Override
    public void showDebugView(final int n) {
        super.showDebugView(n);
    }
    
    @Override
    public void setDebugViewMargin(final String s, final TRTCViewMargin trtcViewMargin) {
        super.setDebugViewMargin(s, trtcViewMargin);
    }
    
    @Override
    public void startSpeedTest(final int n, final String s, final String s2) {
    }
    
    @Override
    public void stopSpeedTest() {
    }
    
    @Override
    public void startPublishCDNStream(final TRTCCloudDef.TRTCPublishCDNParam trtcPublishCDNParam) {
        super.startPublishCDNStream(trtcPublishCDNParam);
    }
    
    @Override
    public void stopPublishing() {
        super.stopPublishing();
    }
    
    @Override
    public void stopPublishCDNStream() {
        super.stopPublishCDNStream();
    }
    
    @Override
    public void startPublishing(final String s, final int n) {
        super.startPublishing(s, n);
    }
    
    @Override
    public void setMixTranscodingConfig(final TRTCCloudDef.TRTCTranscodingConfig mixTranscodingConfig) {
        super.setMixTranscodingConfig(mixTranscodingConfig);
    }
    
    @Override
    public boolean sendCustomCmdMsg(final int n, final byte[] array, final boolean b, final boolean b2) {
        return super.sendCustomCmdMsg(n, array, b, b2);
    }
    
    @Override
    public boolean sendSEIMsg(final byte[] array, final int n) {
        return super.sendSEIMsg(array, n);
    }
    
    @Override
    public void setAudioFrameListener(final TRTCCloudListener.TRTCAudioFrameListener trtcAudioFrameListener) {
    }
    
    @Override
    public void onAudioQosChanged(final int n, final int n2, final int n3) {
        final TRTCCloudImpl trtcCloudImpl = this.mMainCloud.get();
        if (trtcCloudImpl != null) {
            trtcCloudImpl.onAudioQosChanged(this, n, n2, n3);
        }
    }
    
    @Override
    public void onVideoQosChanged(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final TRTCCloudImpl trtcCloudImpl = this.mMainCloud.get();
        if (trtcCloudImpl != null) {
            trtcCloudImpl.onVideoQosChanged(this, n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void onIdrFpsChanged(final int n) {
        final TRTCCloudImpl trtcCloudImpl = this.mMainCloud.get();
        if (trtcCloudImpl != null) {
            trtcCloudImpl.onIdrFpsChanged(this, n);
        }
    }
    
    @Override
    public void onVideoConfigChanged(final int n, final boolean b) {
        final TRTCCloudImpl trtcCloudImpl = this.mMainCloud.get();
        if (trtcCloudImpl != null) {
            trtcCloudImpl.onVideoConfigChanged(this, n, b);
        }
    }
    
    protected void collectCustomCaptureFps() {
    }
    
    @Override
    public TRTCCloud createSubCloud() {
        return null;
    }
    
    static {
        TAG = TRTCSubCloud.class.getName();
    }
    
    private static class a implements Runnable
    {
        private WeakReference<TRTCSubCloud> a;
        
        a(final TRTCSubCloud trtcSubCloud) {
            this.a = new WeakReference<TRTCSubCloud>(trtcSubCloud);
        }
        
        @Override
        public void run() {
            TRTCSubCloud trtcSubCloud = null;
            if (this.a != null) {
                trtcSubCloud = this.a.get();
            }
            if (trtcSubCloud != null) {
                final ArrayList list = new ArrayList();
                trtcSubCloud.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() {
                    @Override
                    public void accept(final String s, final TRTCRoomInfo.UserInfo userInfo) {
                        final int remotePlayoutVolumeLevel = TXCAudioEngine.getInstance().getRemotePlayoutVolumeLevel(String.valueOf(userInfo.tinyID));
                        if (remotePlayoutVolumeLevel > 0) {
                            final TRTCCloudDef.TRTCVolumeInfo trtcVolumeInfo = new TRTCCloudDef.TRTCVolumeInfo();
                            trtcVolumeInfo.userId = userInfo.userID;
                            trtcVolumeInfo.volume = remotePlayoutVolumeLevel;
                            list.add(trtcVolumeInfo);
                        }
                    }
                });
                trtcSubCloud.runOnListenerThread(new Runnable() {
                    final /* synthetic */ TRTCCloudListener a = trtcSubCloud.mTRTCListener;
                    
                    @Override
                    public void run() {
                        if (this.a != null) {
                            this.a.onUserVoiceVolume(list, 0);
                        }
                    }
                });
                if (trtcSubCloud.mAudioVolumeEvalInterval > 0) {
                    trtcSubCloud.mSDKHandler.postDelayed((Runnable)trtcSubCloud.mVolumeLevelCalTask, (long)trtcSubCloud.mAudioVolumeEvalInterval);
                }
            }
        }
    }
}
