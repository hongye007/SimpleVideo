package com.tencent.ijk.media.player;

import android.media.*;
import android.os.*;
import android.text.*;
import java.util.*;
import android.annotation.*;
import android.util.*;

public class IjkMediaCodecInfo
{
    private static final String TAG = "IjkMediaCodecInfo";
    public static final int RANK_MAX = 1000;
    public static final int RANK_TESTED = 800;
    public static final int RANK_ACCEPTABLE = 700;
    public static final int RANK_LAST_CHANCE = 600;
    public static final int RANK_SECURE = 300;
    public static final int RANK_SOFTWARE = 200;
    public static final int RANK_NON_STANDARD = 100;
    public static final int RANK_NO_SENSE = 0;
    public MediaCodecInfo mCodecInfo;
    public int mRank;
    public String mMimeType;
    private static Map<String, Integer> sKnownCodecList;
    
    public IjkMediaCodecInfo() {
        this.mRank = 0;
    }
    
    private static synchronized Map<String, Integer> getKnownCodecList() {
        if (IjkMediaCodecInfo.sKnownCodecList != null) {
            return IjkMediaCodecInfo.sKnownCodecList;
        }
        (IjkMediaCodecInfo.sKnownCodecList = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER)).put("OMX.Nvidia.h264.decode", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.Nvidia.h264.decode.secure", 300);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.Intel.hw_vd.h264", 801);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.Intel.VideoDecoder.AVC", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.qcom.video.decoder.avc", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.ittiam.video.decoder.avc", 0);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.SEC.avc.dec", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.SEC.AVC.Decoder", 799);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.SEC.avcdec", 798);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.SEC.avc.sw.dec", 200);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.Exynos.avc.dec", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.Exynos.AVC.Decoder", 799);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.k3.video.decoder.avc", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.IMG.MSVDX.Decoder.AVC", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.TI.DUCATI1.VIDEO.DECODER", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.rk.video_decoder.avc", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.amlogic.avc.decoder.awesome", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.MARVELL.VIDEO.HW.CODA7542DECODER", 800);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.MARVELL.VIDEO.H264DECODER", 200);
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.Action.Video.Decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.allwinner.video.decoder.avc");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.BRCM.vc4.decoder.avc");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.brcm.video.h264.hw.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.brcm.video.h264.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.cosmo.video.decoder.avc");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.duos.h264.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.hantro.81x0.video.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.hantro.G1.video.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.hisi.video.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.LG.decoder.video.avc");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.MS.AVC.Decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.RENESAS.VIDEO.DECODER.H264");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.RTK.video.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.sprd.h264.decoder");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.ST.VFM.H264Dec");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.vpu.video_decoder.avc");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.WMT.decoder.avc");
        IjkMediaCodecInfo.sKnownCodecList.remove("OMX.bluestacks.hw.decoder");
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.google.h264.decoder", 200);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.google.h264.lc.decoder", 200);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.k3.ffmpeg.decoder", 200);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.ffmpeg.video.decoder", 200);
        IjkMediaCodecInfo.sKnownCodecList.put("OMX.sprd.soft.h264.decoder", 200);
        return IjkMediaCodecInfo.sKnownCodecList;
    }
    
    @TargetApi(16)
    public static IjkMediaCodecInfo setupCandidate(final MediaCodecInfo mCodecInfo, final String mMimeType) {
        if (mCodecInfo == null || Build.VERSION.SDK_INT < 16) {
            return null;
        }
        final String name = mCodecInfo.getName();
        if (TextUtils.isEmpty((CharSequence)name)) {
            return null;
        }
        final String lowerCase = name.toLowerCase(Locale.US);
        int intValue;
        if (!lowerCase.startsWith("omx.")) {
            intValue = 100;
        }
        else if (lowerCase.startsWith("omx.pv")) {
            intValue = 200;
        }
        else if (lowerCase.startsWith("omx.google.")) {
            intValue = 200;
        }
        else if (lowerCase.startsWith("omx.ffmpeg.")) {
            intValue = 200;
        }
        else if (lowerCase.startsWith("omx.k3.ffmpeg.")) {
            intValue = 200;
        }
        else if (lowerCase.startsWith("omx.avcodec.")) {
            intValue = 200;
        }
        else if (lowerCase.startsWith("omx.ittiam.")) {
            intValue = 0;
        }
        else if (lowerCase.startsWith("omx.mtk.")) {
            if (Build.VERSION.SDK_INT < 18) {
                intValue = 0;
            }
            else {
                intValue = 800;
            }
        }
        else {
            final Integer n = getKnownCodecList().get(lowerCase);
            if (n != null) {
                intValue = n;
            }
            else {
                try {
                    if (mCodecInfo.getCapabilitiesForType(mMimeType) != null) {
                        intValue = 700;
                    }
                    else {
                        intValue = 600;
                    }
                }
                catch (Throwable t) {
                    intValue = 600;
                }
            }
        }
        final IjkMediaCodecInfo ijkMediaCodecInfo = new IjkMediaCodecInfo();
        ijkMediaCodecInfo.mCodecInfo = mCodecInfo;
        ijkMediaCodecInfo.mRank = intValue;
        ijkMediaCodecInfo.mMimeType = mMimeType;
        return ijkMediaCodecInfo;
    }
    
    @TargetApi(16)
    public void dumpProfileLevels(final String s) {
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        try {
            final MediaCodecInfo.CodecCapabilities capabilitiesForType = this.mCodecInfo.getCapabilitiesForType(s);
            int max = 0;
            int max2 = 0;
            if (capabilitiesForType != null && capabilitiesForType.profileLevels != null) {
                for (final MediaCodecInfo.CodecProfileLevel codecProfileLevel : capabilitiesForType.profileLevels) {
                    if (codecProfileLevel != null) {
                        max = Math.max(max, codecProfileLevel.profile);
                        max2 = Math.max(max2, codecProfileLevel.level);
                    }
                }
            }
            Log.i("IjkMediaCodecInfo", String.format(Locale.US, "%s", getProfileLevelName(max, max2)));
        }
        catch (Throwable t) {
            Log.i("IjkMediaCodecInfo", "profile-level: exception");
        }
    }
    
    public static String getProfileLevelName(final int n, final int n2) {
        return String.format(Locale.US, " %s Profile Level %s (%d,%d)", getProfileName(n), getLevelName(n2), n, n2);
    }
    
    public static String getProfileName(final int n) {
        switch (n) {
            case 1: {
                return "Baseline";
            }
            case 2: {
                return "Main";
            }
            case 4: {
                return "Extends";
            }
            case 8: {
                return "High";
            }
            case 16: {
                return "High10";
            }
            case 32: {
                return "High422";
            }
            case 64: {
                return "High444";
            }
            default: {
                return "Unknown";
            }
        }
    }
    
    public static String getLevelName(final int n) {
        switch (n) {
            case 1: {
                return "1";
            }
            case 2: {
                return "1b";
            }
            case 4: {
                return "11";
            }
            case 8: {
                return "12";
            }
            case 16: {
                return "13";
            }
            case 32: {
                return "2";
            }
            case 64: {
                return "21";
            }
            case 128: {
                return "22";
            }
            case 256: {
                return "3";
            }
            case 512: {
                return "31";
            }
            case 1024: {
                return "32";
            }
            case 2048: {
                return "4";
            }
            case 4096: {
                return "41";
            }
            case 8192: {
                return "42";
            }
            case 16384: {
                return "5";
            }
            case 32768: {
                return "51";
            }
            case 65536: {
                return "52";
            }
            default: {
                return "0";
            }
        }
    }
}
