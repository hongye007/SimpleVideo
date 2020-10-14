package com.tencent.rtmp;

import android.content.*;
import com.tencent.rtmp.ui.*;
import android.view.*;
import com.tencent.ugc.*;
import android.graphics.*;

public class TXLivePlayer
{
    public static final String TAG = "TXLivePlayer";
    public static final int PLAY_TYPE_LIVE_RTMP = 0;
    public static final int PLAY_TYPE_LIVE_FLV = 1;
    public static final int PLAY_TYPE_VOD_FLV = 2;
    public static final int PLAY_TYPE_VOD_HLS = 3;
    public static final int PLAY_TYPE_VOD_MP4 = 4;
    public static final int PLAY_TYPE_LIVE_RTMP_ACC = 5;
    public static final int PLAY_TYPE_LOCAL_VIDEO = 6;
    private a mTXLivePlayerImpl;
    
    public TXLivePlayer(final Context context) {
        this.mTXLivePlayerImpl = new a(context);
    }
    
    public void setConfig(final TXLivePlayConfig txLivePlayConfig) {
        this.mTXLivePlayerImpl.a(txLivePlayConfig);
    }
    
    public void setPlayListener(final ITXLivePlayListener itxLivePlayListener) {
        this.mTXLivePlayerImpl.a(itxLivePlayListener);
    }
    
    public void setPlayerView(final TXCloudVideoView txCloudVideoView) {
        this.mTXLivePlayerImpl.a(txCloudVideoView);
    }
    
    public int startPlay(final String s, final int n) {
        return this.mTXLivePlayerImpl.a(s, n);
    }
    
    public int stopPlay(final boolean b) {
        return this.mTXLivePlayerImpl.a(b);
    }
    
    public boolean isPlaying() {
        return this.mTXLivePlayerImpl.a();
    }
    
    public void pause() {
        this.mTXLivePlayerImpl.b();
    }
    
    public void resume() {
        this.mTXLivePlayerImpl.c();
    }
    
    public void setSurface(final Surface surface) {
        this.mTXLivePlayerImpl.a(surface);
    }
    
    public void setSurfaceSize(final int n, final int n2) {
        this.mTXLivePlayerImpl.a(n, n2);
    }
    
    public void setRenderMode(final int n) {
        this.mTXLivePlayerImpl.a(n);
    }
    
    public void setRenderRotation(final int n) {
        this.mTXLivePlayerImpl.b(n);
    }
    
    public boolean enableHardwareDecode(final boolean b) {
        return this.mTXLivePlayerImpl.b(b);
    }
    
    public void setMute(final boolean b) {
        this.mTXLivePlayerImpl.c(b);
    }
    
    public void setVolume(final int n) {
        this.mTXLivePlayerImpl.c(n);
    }
    
    public void setAudioRoute(final int n) {
        this.mTXLivePlayerImpl.d(n);
    }
    
    public int switchStream(final String s) {
        return this.mTXLivePlayerImpl.a(s);
    }
    
    public void setAudioVolumeEvaluationListener(final ITXAudioVolumeEvaluationListener itxAudioVolumeEvaluationListener) {
        this.mTXLivePlayerImpl.a(itxAudioVolumeEvaluationListener);
    }
    
    public void enableAudioVolumeEvaluation(final int n) {
        this.mTXLivePlayerImpl.e(n);
    }
    
    public void callExperimentalAPI(final String s) {
        this.mTXLivePlayerImpl.b(s);
    }
    
    public void setVideoRecordListener(final TXRecordCommon.ITXVideoRecordListener itxVideoRecordListener) {
        this.mTXLivePlayerImpl.a(itxVideoRecordListener);
    }
    
    public int startRecord(final int n) {
        return this.mTXLivePlayerImpl.f(n);
    }
    
    public int stopRecord() {
        return this.mTXLivePlayerImpl.d();
    }
    
    public void snapshot(final ITXSnapshotListener itxSnapshotListener) {
        this.mTXLivePlayerImpl.a(itxSnapshotListener);
    }
    
    public boolean addVideoRawData(final byte[] array) {
        return this.mTXLivePlayerImpl.a(array);
    }
    
    public void setVideoRawDataListener(final ITXVideoRawDataListener itxVideoRawDataListener) {
        this.mTXLivePlayerImpl.a(itxVideoRawDataListener);
    }
    
    public int setVideoRenderListener(final ITXLivePlayVideoRenderListener itxLivePlayVideoRenderListener, final Object o) {
        return this.mTXLivePlayerImpl.a(itxLivePlayVideoRenderListener, o);
    }
    
    public void setAudioRawDataListener(final ITXAudioRawDataListener itxAudioRawDataListener) {
        this.mTXLivePlayerImpl.a(itxAudioRawDataListener);
    }
    
    public int prepareLiveSeek(final String s, final int n) {
        return this.mTXLivePlayerImpl.b(s, n);
    }
    
    public void seek(final int n) {
        this.mTXLivePlayerImpl.g(n);
    }
    
    public int resumeLive() {
        return this.mTXLivePlayerImpl.e();
    }
    
    @Deprecated
    public void setAutoPlay(final boolean b) {
        this.mTXLivePlayerImpl.d(b);
    }
    
    @Deprecated
    public void setRate(final float n) {
        this.mTXLivePlayerImpl.a(n);
    }
    
    public static class TXLiteAVTexture
    {
        public int textureId;
        public int width;
        public int height;
        public Object eglContext;
    }
    
    public interface ITXAudioVolumeEvaluationListener
    {
        void onAudioVolumeEvaluationNotify(final int p0);
    }
    
    public interface ITXAudioRawDataListener
    {
        void onPcmDataAvailable(final byte[] p0, final long p1);
        
        void onAudioInfoChanged(final int p0, final int p1, final int p2);
    }
    
    public interface ITXLivePlayVideoRenderListener
    {
        void onRenderVideoFrame(final TXLiteAVTexture p0);
    }
    
    public interface ITXVideoRawDataListener
    {
        void onVideoRawDataAvailable(final byte[] p0, final int p1, final int p2, final int p3);
    }
    
    public interface ITXSnapshotListener
    {
        void onSnapshot(final Bitmap p0);
    }
}
