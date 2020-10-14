package com.tencent.liteav.trtc.impl;

import com.tencent.trtc.*;
import java.util.*;

public class TRTCTranscodingConfigInner extends TRTCCloudDef.TRTCTranscodingConfig
{
    public String backgroundURL;
    public String mixExtraInfo;
    
    public TRTCTranscodingConfigInner() {
        this.backgroundURL = "";
        this.mixExtraInfo = "";
    }
    
    public TRTCTranscodingConfigInner(final TRTCCloudDef.TRTCTranscodingConfig trtcTranscodingConfig) {
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
        this.backgroundURL = "";
        this.mixExtraInfo = "";
        this.mixUsers = new ArrayList<TRTCCloudDef.TRTCMixUser>();
        if (trtcTranscodingConfig.mixUsers != null) {
            for (final TRTCCloudDef.TRTCMixUser trtcMixUser : trtcTranscodingConfig.mixUsers) {
                if (trtcMixUser != null) {
                    this.mixUsers.add(new TRTCMixUserInner(trtcMixUser));
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "backgroundURL=" + this.backgroundURL + ", mixExtraInfo=" + this.mixExtraInfo + ", " + super.toString();
    }
    
    public static class TRTCMixUserInner extends TRTCCloudDef.TRTCMixUser
    {
        public String streamId;
        
        public TRTCMixUserInner() {
            this.streamId = "";
        }
        
        public TRTCMixUserInner(final String s, final int n, final int n2, final int n3, final int n4, final int n5) {
            super(s, n, n2, n3, n4, n5);
            this.streamId = "";
        }
        
        public TRTCMixUserInner(final TRTCCloudDef.TRTCMixUser trtcMixUser) {
            super(trtcMixUser);
            this.streamId = "";
        }
        
        @Override
        public String toString() {
            return super.toString() + ", streamId=" + this.streamId;
        }
    }
}
