package com.tencent.trtc;

import javax.microedition.khronos.egl.*;
import java.nio.*;
import java.util.*;
import android.view.*;

public class TRTCCloudDef
{
    public static final String TRTC_SDK_VERSION = "0.0.0.0";
    public static final int TRTC_VIDEO_RESOLUTION_120_120 = 1;
    public static final int TRTC_VIDEO_RESOLUTION_160_160 = 3;
    public static final int TRTC_VIDEO_RESOLUTION_270_270 = 5;
    public static final int TRTC_VIDEO_RESOLUTION_480_480 = 7;
    public static final int TRTC_VIDEO_RESOLUTION_160_120 = 50;
    public static final int TRTC_VIDEO_RESOLUTION_240_180 = 52;
    public static final int TRTC_VIDEO_RESOLUTION_280_210 = 54;
    public static final int TRTC_VIDEO_RESOLUTION_320_240 = 56;
    public static final int TRTC_VIDEO_RESOLUTION_400_300 = 58;
    public static final int TRTC_VIDEO_RESOLUTION_480_360 = 60;
    public static final int TRTC_VIDEO_RESOLUTION_640_480 = 62;
    public static final int TRTC_VIDEO_RESOLUTION_960_720 = 64;
    public static final int TRTC_VIDEO_RESOLUTION_160_90 = 100;
    public static final int TRTC_VIDEO_RESOLUTION_256_144 = 102;
    public static final int TRTC_VIDEO_RESOLUTION_320_180 = 104;
    public static final int TRTC_VIDEO_RESOLUTION_480_270 = 106;
    public static final int TRTC_VIDEO_RESOLUTION_640_360 = 108;
    public static final int TRTC_VIDEO_RESOLUTION_960_540 = 110;
    public static final int TRTC_VIDEO_RESOLUTION_1280_720 = 112;
    public static final int TRTC_VIDEO_RESOLUTION_1920_1080 = 114;
    public static final int TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE = 0;
    public static final int TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT = 1;
    public static final int TRTC_VIDEO_STREAM_TYPE_BIG = 0;
    public static final int TRTC_VIDEO_STREAM_TYPE_SMALL = 1;
    public static final int TRTC_VIDEO_STREAM_TYPE_SUB = 2;
    public static final int TRTC_QUALITY_UNKNOWN = 0;
    public static final int TRTC_QUALITY_Excellent = 1;
    public static final int TRTC_QUALITY_Good = 2;
    public static final int TRTC_QUALITY_Poor = 3;
    public static final int TRTC_QUALITY_Bad = 4;
    public static final int TRTC_QUALITY_Vbad = 5;
    public static final int TRTC_QUALITY_Down = 6;
    public static final int TRTC_VIDEO_RENDER_MODE_FILL = 0;
    public static final int TRTC_VIDEO_RENDER_MODE_FIT = 1;
    public static final int TRTC_VIDEO_ROTATION_0 = 0;
    public static final int TRTC_VIDEO_ROTATION_90 = 1;
    public static final int TRTC_VIDEO_ROTATION_180 = 2;
    public static final int TRTC_VIDEO_ROTATION_270 = 3;
    public static final int TRTC_BEAUTY_STYLE_SMOOTH = 0;
    public static final int TRTC_BEAUTY_STYLE_NATURE = 1;
    public static final int TRTC_BEAUTY_STYLE_PITU = 2;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_UNKNOWN = 0;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_I420 = 1;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_Texture_2D = 2;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_TEXTURE_EXTERNAL_OES = 3;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_NV21 = 4;
    public static final int TRTC_VIDEO_MIRROR_TYPE_AUTO = 0;
    public static final int TRTC_VIDEO_MIRROR_TYPE_ENABLE = 1;
    public static final int TRTC_VIDEO_MIRROR_TYPE_DISABLE = 2;
    public static final int TRTC_VIDEO_BUFFER_TYPE_UNKNOWN = 0;
    public static final int TRTC_VIDEO_BUFFER_TYPE_BYTE_BUFFER = 1;
    public static final int TRTC_VIDEO_BUFFER_TYPE_BYTE_ARRAY = 2;
    public static final int TRTC_VIDEO_BUFFER_TYPE_TEXTURE = 3;
    public static final int TRTC_APP_SCENE_VIDEOCALL = 0;
    public static final int TRTC_APP_SCENE_LIVE = 1;
    public static final int TRTC_APP_SCENE_AUDIOCALL = 2;
    public static final int TRTC_APP_SCENE_VOICE_CHATROOM = 3;
    public static final int TRTCRoleAnchor = 20;
    public static final int TRTCRoleAudience = 21;
    public static final int VIDEO_QOS_CONTROL_CLIENT = 0;
    public static final int VIDEO_QOS_CONTROL_SERVER = 1;
    public static final int TRTC_VIDEO_QOS_PREFERENCE_SMOOTH = 1;
    public static final int TRTC_VIDEO_QOS_PREFERENCE_CLEAR = 2;
    public static final int TRTCAudioSampleRate16000 = 16000;
    public static final int TRTCAudioSampleRate32000 = 32000;
    public static final int TRTCAudioSampleRate44100 = 44100;
    public static final int TRTCAudioSampleRate48000 = 48000;
    public static final int TRTC_AUDIO_QUALITY_SPEECH = 1;
    public static final int TRTC_AUDIO_QUALITY_DEFAULT = 2;
    public static final int TRTC_AUDIO_QUALITY_MUSIC = 3;
    public static final int TRTC_AUDIO_ROUTE_SPEAKER = 0;
    public static final int TRTC_AUDIO_ROUTE_EARPIECE = 1;
    public static final int TRTC_REVERB_TYPE_0 = 0;
    public static final int TRTC_REVERB_TYPE_1 = 1;
    public static final int TRTC_REVERB_TYPE_2 = 2;
    public static final int TRTC_REVERB_TYPE_3 = 3;
    public static final int TRTC_REVERB_TYPE_4 = 4;
    public static final int TRTC_REVERB_TYPE_5 = 5;
    public static final int TRTC_REVERB_TYPE_6 = 6;
    public static final int TRTC_REVERB_TYPE_7 = 7;
    public static final int TRTC_VOICE_CHANGER_TYPE_0 = 0;
    public static final int TRTC_VOICE_CHANGER_TYPE_1 = 1;
    public static final int TRTC_VOICE_CHANGER_TYPE_2 = 2;
    public static final int TRTC_VOICE_CHANGER_TYPE_3 = 3;
    public static final int TRTC_VOICE_CHANGER_TYPE_4 = 4;
    public static final int TRTC_VOICE_CHANGER_TYPE_5 = 5;
    public static final int TRTC_VOICE_CHANGER_TYPE_6 = 6;
    public static final int TRTC_VOICE_CHANGER_TYPE_7 = 7;
    public static final int TRTC_VOICE_CHANGER_TYPE_8 = 8;
    public static final int TRTC_VOICE_CHANGER_TYPE_9 = 9;
    public static final int TRTC_VOICE_CHANGER_TYPE_10 = 10;
    public static final int TRTC_VOICE_CHANGER_TYPE_11 = 11;
    public static final int TRTC_AUDIO_FRAME_FORMAT_PCM = 1;
    public static final int TRTCSystemVolumeTypeAuto = 0;
    public static final int TRTCSystemVolumeTypeMedia = 1;
    public static final int TRTCSystemVolumeTypeVOIP = 2;
    public static final int TRTC_DEBUG_VIEW_LEVEL_GONE = 0;
    public static final int TRTC_DEBUG_VIEW_LEVEL_STATUS = 1;
    public static final int TRTC_DEBUG_VIEW_LEVEL_ALL = 2;
    public static final int TRTC_LOG_LEVEL_VERBOSE = 0;
    public static final int TRTC_LOG_LEVEL_DEBUG = 1;
    public static final int TRTC_LOG_LEVEL_INFO = 2;
    public static final int TRTC_LOG_LEVEL_WARN = 3;
    public static final int TRTC_LOG_LEVEL_ERROR = 4;
    public static final int TRTC_LOG_LEVEL_FATAL = 5;
    public static final int TRTC_LOG_LEVEL_NULL = 6;
    public static final int TRTC_GSENSOR_MODE_DISABLE = 0;
    public static final int TRTC_GSENSOR_MODE_UIAUTOLAYOUT = 1;
    public static final int TRTC_GSENSOR_MODE_UIFIXLAYOUT = 2;
    public static final int TRTC_TranscodingConfigMode_Unknown = 0;
    public static final int TRTC_TranscodingConfigMode_Manual = 1;
    public static final int TRTC_TranscodingConfigMode_Template_PureAudio = 2;
    public static final int TRTC_TranscodingConfigMode_Template_PresetLayout = 3;
    public static final int TRTC_TranscodingConfigMode_Template_ScreenSharing = 4;
    
    public static class TRTCParams
    {
        public int sdkAppId;
        public String userId;
        public String userSig;
        public int roomId;
        public int role;
        public String streamId;
        public String userDefineRecordId;
        public String privateMapKey;
        public String businessInfo;
        
        public TRTCParams() {
            this.sdkAppId = 0;
            this.userId = "";
            this.userSig = "";
            this.roomId = 0;
            this.role = 20;
            this.privateMapKey = "";
            this.businessInfo = "";
        }
        
        public TRTCParams(final int sdkAppId, final String userId, final String userSig, final int roomId, final String privateMapKey, final String businessInfo) {
            this.sdkAppId = 0;
            this.userId = "";
            this.userSig = "";
            this.roomId = 0;
            this.role = 20;
            this.privateMapKey = "";
            this.businessInfo = "";
            this.sdkAppId = sdkAppId;
            this.userId = userId;
            this.userSig = userSig;
            this.roomId = roomId;
            this.privateMapKey = privateMapKey;
            this.businessInfo = businessInfo;
        }
        
        public TRTCParams(final TRTCParams trtcParams) {
            this.sdkAppId = 0;
            this.userId = "";
            this.userSig = "";
            this.roomId = 0;
            this.role = 20;
            this.privateMapKey = "";
            this.businessInfo = "";
            this.sdkAppId = trtcParams.sdkAppId;
            this.userId = trtcParams.userId;
            this.userSig = trtcParams.userSig;
            this.roomId = trtcParams.roomId;
            this.role = trtcParams.role;
            this.streamId = trtcParams.streamId;
            this.userDefineRecordId = trtcParams.userDefineRecordId;
            this.privateMapKey = trtcParams.privateMapKey;
            this.businessInfo = trtcParams.businessInfo;
        }
    }
    
    public static class TRTCVideoEncParam
    {
        public int videoResolution;
        public int videoResolutionMode;
        public int videoFps;
        public int videoBitrate;
        public int minVideoBitrate;
        public boolean enableAdjustRes;
    }
    
    public static class TRTCNetworkQosParam
    {
        public int preference;
        public int controlMode;
    }
    
    public static class TRTCQuality
    {
        public String userId;
        public int quality;
    }
    
    public static class TRTCTexture
    {
        public int textureId;
        public EGLContext eglContext10;
        public android.opengl.EGLContext eglContext14;
    }
    
    public static class TRTCVideoFrame
    {
        public int pixelFormat;
        public int bufferType;
        public TRTCTexture texture;
        public byte[] data;
        public ByteBuffer buffer;
        public int width;
        public int height;
        public long timestamp;
        public int rotation;
    }
    
    public static class TRTCAudioFrame
    {
        public byte[] data;
        public int sampleRate;
        public int channel;
        public long timestamp;
    }
    
    public static class TRTCVolumeInfo
    {
        public String userId;
        public int volume;
    }
    
    public static class TRTCSpeedTestResult
    {
        public String ip;
        public int quality;
        public float upLostRate;
        public float downLostRate;
        public int rtt;
        
        @Override
        public String toString() {
            return String.format("ip: %s, quality: %d, upLostRate: %.2f%%, downLostRate: %.2f%%, rtt: %d", this.ip, this.quality, this.upLostRate * 100.0f, this.downLostRate * 100.0f, this.rtt);
        }
    }
    
    public static class TRTCMixUser
    {
        public String userId;
        public String roomId;
        public int x;
        public int y;
        public int width;
        public int height;
        public int zOrder;
        public int streamType;
        public boolean pureAudio;
        
        public TRTCMixUser() {
            this.userId = "";
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
            this.zOrder = 0;
            this.streamType = 0;
        }
        
        public TRTCMixUser(final String userId, final int x, final int y, final int width, final int height, final int zOrder) {
            this.userId = userId;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.zOrder = zOrder;
            this.streamType = 0;
        }
        
        public TRTCMixUser(final TRTCMixUser trtcMixUser) {
            this.userId = trtcMixUser.userId;
            this.roomId = trtcMixUser.roomId;
            this.x = trtcMixUser.x;
            this.y = trtcMixUser.y;
            this.width = trtcMixUser.width;
            this.height = trtcMixUser.height;
            this.zOrder = trtcMixUser.zOrder;
            this.streamType = trtcMixUser.streamType;
            this.pureAudio = trtcMixUser.pureAudio;
        }
        
        @Override
        public String toString() {
            return "userId=" + this.userId + ", roomId=" + this.roomId + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", zOrder=" + this.zOrder + ", streamType=" + this.streamType + ", pureAudio=" + this.pureAudio;
        }
    }
    
    public static class TRTCTranscodingConfig
    {
        public int appId;
        public int bizId;
        public int mode;
        public int videoWidth;
        public int videoHeight;
        public int videoBitrate;
        public int videoFramerate;
        public int videoGOP;
        public int backgroundColor;
        public String backgroundImage;
        public int audioSampleRate;
        public int audioBitrate;
        public int audioChannels;
        public ArrayList<TRTCMixUser> mixUsers;
        public String streamId;
        
        public TRTCTranscodingConfig() {
            this.mode = 0;
            this.videoWidth = 0;
            this.videoHeight = 0;
            this.videoBitrate = 0;
            this.videoFramerate = 15;
            this.videoGOP = 2;
            this.backgroundColor = 0;
            this.audioSampleRate = 48000;
            this.audioBitrate = 64;
            this.audioChannels = 1;
            this.streamId = null;
        }
        
        public TRTCTranscodingConfig(final TRTCTranscodingConfig trtcTranscodingConfig) {
            this.appId = trtcTranscodingConfig.appId;
            this.bizId = trtcTranscodingConfig.bizId;
            this.mode = trtcTranscodingConfig.mode;
            this.videoWidth = trtcTranscodingConfig.videoWidth;
            this.videoHeight = trtcTranscodingConfig.videoHeight;
            this.videoBitrate = trtcTranscodingConfig.videoBitrate;
            this.videoFramerate = trtcTranscodingConfig.videoFramerate;
            this.videoGOP = trtcTranscodingConfig.videoGOP;
            this.backgroundColor = trtcTranscodingConfig.backgroundColor;
            this.backgroundImage = trtcTranscodingConfig.backgroundImage;
            this.audioSampleRate = trtcTranscodingConfig.audioSampleRate;
            this.audioBitrate = trtcTranscodingConfig.audioBitrate;
            this.audioChannels = trtcTranscodingConfig.audioChannels;
            this.streamId = trtcTranscodingConfig.streamId;
            this.mixUsers = new ArrayList<TRTCMixUser>(trtcTranscodingConfig.mixUsers);
        }
        
        @Override
        public String toString() {
            return "appId=" + this.appId + ", bizId=" + this.bizId + ", mode=" + this.mode + ", videoWidth=" + this.videoWidth + ", videoHeight=" + this.videoHeight + ", videoBitrate=" + this.videoBitrate + ", videoFramerate=" + this.videoFramerate + ", videoGOP=" + this.videoGOP + ", backgroundColor=" + this.backgroundColor + ", backgroundImage=" + this.backgroundImage + ", audioSampleRate=" + this.audioSampleRate + ", audioBitrate=" + this.audioBitrate + ", audioChannels=" + this.audioChannels + ", streamId=" + this.streamId + ", mixUsers=" + this.mixUsers;
        }
    }
    
    public static class TRTCPublishCDNParam
    {
        public int appId;
        public int bizId;
        public String url;
    }
    
    public static class TRTCAudioRecordingParams
    {
        public String filePath;
        
        public TRTCAudioRecordingParams() {
            this.filePath = "";
        }
    }
    
    public static class TRTCAudioEffectParam
    {
        public int effectId;
        public String path;
        public int loopCount;
        public boolean publish;
        public int volume;
        
        public TRTCAudioEffectParam(final int effectId, final String path) {
            this.path = path;
            this.effectId = effectId;
            this.loopCount = 0;
            this.publish = false;
            this.volume = 100;
        }
    }
    
    public static class TRTCScreenShareParams
    {
        public View floatingView;
    }
}
