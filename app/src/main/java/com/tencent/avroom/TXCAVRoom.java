package com.tencent.avroom;

import com.tencent.liteav.avprotocol.*;
import android.content.*;
import com.tencent.liteav.qos.*;
import com.tencent.rtmp.ui.*;
import com.tencent.liteav.basic.structs.*;
import android.media.*;
import android.os.*;
import com.tencent.liteav.audio.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import android.app.*;
import com.tencent.liteav.basic.datareport.*;
import android.util.*;

import com.tencent.liteav.*;
import java.util.*;
import android.graphics.*;

public class TXCAVRoom
{
    private static final String TAG;
    private static final int AVROOM_IDLE = 0;
    private static final int AVROOM_ENTERING = 1;
    private static final int AVROOM_ENTERED = 2;
    private static final int AVROOM_EXITING = 3;
    private d mPusher;
    private i mLivePushConfig;
    private TXCAVProtocol mTXCAVProtocol;
    private Context mContext;
    private TXCAVRoomConfig mRoomConfig;
    private boolean allowedPush;
    private HashMap<Long, c> playerList;
    private ArrayList<Long> videoMemList;
    private TXCAVProtocol.TXCAVProtoParam protoparam;
    private TXCAVRoomLisenter mAvRoomLisenter;
    private TXCQoS mQos;
    private int maxReconnectCount;
    private int reconnectCount;
    private long myid;
    private int appid;
    private int sdkVersion;
    private b mCaptureDataCollection;
    private Handler mainHandler;
    private TXCAVRoomCallback enterRoomCB;
    private int roomStatus;
    private TXCloudVideoView localView;
    private static int videoMemNum;
    private com.tencent.liteav.basic.a.c videoResolution;
    
    public boolean isPushing() {
        return this.mPusher.j();
    }
    
    public boolean isInRoom() {
        return this.roomStatus == 2;
    }
    
    public TXCAVRoom(final Context mContext, final TXCAVRoomConfig mRoomConfig, final long myid, final int appid) {
        this.allowedPush = false;
        this.playerList = new HashMap<Long, c>();
        this.videoMemList = new ArrayList<Long>();
        this.protoparam = new TXCAVProtocol().new TXCAVProtoParam();
        this.mQos = null;
        this.maxReconnectCount = 10;
        this.reconnectCount = 0;
        this.myid = -1L;
        this.appid = -1;
        this.sdkVersion = 26215104;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.roomStatus = 0;
        this.videoResolution = com.tencent.liteav.basic.a.c.c;
        this.mContext = mContext;
        this.mRoomConfig = mRoomConfig;
        this.myid = myid;
        this.appid = appid;
        (this.mPusher = new d(mContext)).a(new d.a() {
            @Override
            public void onEncVideo(final TXSNALPacket txsnalPacket) {
                if (TXCAVRoom.this.allowedPush) {
                    TXCAVRoom.this.mTXCAVProtocol.pushNAL(txsnalPacket);
                }
            }
            
            @Override
            public void onEncVideoFormat(final MediaFormat mediaFormat) {
            }
            
            @Override
            public void onBackgroudPushStop() {
            }
        });
        this.mPusher.setID("" + this.myid);
        this.mPusher.a(new com.tencent.liteav.basic.b.b() {
            @Override
            public void onNotifyEvent(final int n, final Bundle bundle) {
                TXCAVRoom.this.onBothNotifyEvent(n, bundle);
            }
        });
        this.applyCaptureConfig();
        (this.mTXCAVProtocol = new TXCAVProtocol()).setListener(new TXCAVProtocol.TXIAVListener() {
            @Override
            public void onPullAudio(final TXCAVProtocol.TXSAVProtoAudioPacket txsavProtoAudioPacket) {
                synchronized (TXCAVRoom.this) {
                    synchronized (TXCAVRoom.this.playerList) {
                        if (TXCAVRoom.this.playerList.get(txsavProtoAudioPacket.tinyID) != null) {
                            if (TXCAudioEngine.getInstance().isRemoteAudioPlaying(String.valueOf(TXCAVRoom.this.myid))) {
                                txsavProtoAudioPacket.audioData = new byte[2];
                                txsavProtoAudioPacket.packetType = 2;
                                TXCAVRoomConstants.makeAACCodecSpecificData(txsavProtoAudioPacket.audioData, 2, txsavProtoAudioPacket.sampleRate, txsavProtoAudioPacket.channelsPerSample);
                                TXCLog.i(TXCAVRoom.TAG, "audioKey: makeAACCodecSpecificData id " + txsavProtoAudioPacket.tinyID);
                            }
                            if (TXCAVRoom.this.playerList.get(txsavProtoAudioPacket.tinyID) != null && TXCAVRoom.this.playerList.get(txsavProtoAudioPacket.tinyID).a() != null && txsavProtoAudioPacket.audioData != null) {
                                TXCAVRoom.this.playerList.get(txsavProtoAudioPacket.tinyID).a().b(txsavProtoAudioPacket.audioData.length);
                            }
                        }
                    }
                }
            }
            
            @Override
            public void onPullNAL(final TXCAVProtocol.TXSAVProtoNALPacket txsavProtoNALPacket) {
                synchronized (TXCAVRoom.this.playerList) {
                    if (TXCAVRoom.this.playerList.get(txsavProtoNALPacket.tinyID) != null) {
                        if (TXCAVRoom.this.playerList.get(txsavProtoNALPacket.tinyID).a() != null) {
                            TXCAVRoom.this.playerList.get(txsavProtoNALPacket.tinyID).a().a(txsavProtoNALPacket.nalData.length);
                        }
                        TXCAVRoom.this.playerList.get(txsavProtoNALPacket.tinyID).decVideo(txsavProtoNALPacket);
                    }
                }
            }
            
            @Override
            public void sendNotifyEvent(int n, final String s) {
                switch (n) {
                    case 8001: {
                        n = TXCAVRoomConstants.AVROOM_EVT_REQUEST_IP_SUCC;
                        break;
                    }
                    case -3303: {
                        n = TXCAVRoomConstants.AVROOM_ERR_REQUEST_IP_FAIL;
                        break;
                    }
                    case 8002: {
                        n = TXCAVRoomConstants.AVROOM_EVT_CONNECT_SUCC;
                        break;
                    }
                    case -3304: {
                        n = TXCAVRoomConstants.AVROOM_ERR_CONNECT_FAILE;
                        break;
                    }
                    case 1018: {
                        n = TXCAVRoomConstants.AVROOM_EVT_ENTER_ROOM_SUCC;
                        break;
                    }
                    case -3301: {
                        n = TXCAVRoomConstants.AVROOM_ERR_ENTER_ROOM_FAIL;
                        break;
                    }
                    case 1019: {
                        n = TXCAVRoomConstants.AVROOM_EVT_EXIT_ROOM_SUCC;
                        break;
                    }
                    case 8003: {
                        n = TXCAVRoomConstants.AVROOM_EVT_REQUEST_AVSEAT_SUCC;
                        break;
                    }
                    case -3305: {
                        n = TXCAVRoomConstants.AVROOM_ERR_REQUEST_AVSEAT_FAIL;
                        break;
                    }
                }
                final Bundle bundle = new Bundle();
                bundle.putInt("EVT_ID", n);
                bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                bundle.putString("EVT_MSG", s);
                ((Activity)TXCAVRoom.this.mContext).runOnUiThread((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TXCAVRoom.this.mAvRoomLisenter.onAVRoomEvent(TXCAVRoom.this.myid, n, bundle);
                    }
                });
                if (n == -3302) {
                    TXCAVRoom.this.reconnectRoom();
                }
            }
            
            @Override
            public void onMemberChange(final long n, final boolean b) {
                TXCLog.i(TXCAVRoom.TAG, "onMemberChange: " + n + " flag:" + b);
                TXCAVRoom.this.mainHandler.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (b) {
                            TXCAVRoom.this.addRender(n);
                        }
                        else {
                            TXCAVRoom.this.removeRender(n);
                        }
                        TXCAVRoom.this.mAvRoomLisenter.onMemberChange(n, b);
                    }
                });
            }
            
            @Override
            public void onVideoStateChange(final long n, final boolean b) {
                TXCLog.i(TXCAVRoom.TAG, "onVideoStateChange: " + n + " flag:" + b);
                TXCAVRoom.this.mainHandler.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TXCAVRoom.this.mAvRoomLisenter.onVideoStateChange(n, b);
                    }
                });
            }
        });
    }
    
    public TXCAVRoomConfig getRoomConfig() {
        return this.mRoomConfig;
    }
    
    public void setRoomConfig(final TXCAVRoomConfig mRoomConfig) {
        this.mRoomConfig = mRoomConfig;
        this.applyCaptureConfig();
    }
    
    public String nat64Compatable(final String s, final short n) {
        return this.mTXCAVProtocol.nativeNAT64Compatable(s, n);
    }
    
    public void enterRoom(final TXCAVRoomParam txcavRoomParam, final TXCAVRoomCallback enterRoomCB) {
        if (this.roomStatus != 0) {
            this.postToUiThread(enterRoomCB, -4);
            return;
        }
        this.roomStatus = 1;
        TXCDRApi.initCrashReport(this.mContext);
        TXCDRApi.txReportDAU(this.mContext, AAAA.bv);
        this.protoparam.roomID = txcavRoomParam.getRoomID();
        this.protoparam.authBits = txcavRoomParam.getAuthBits();
        this.protoparam.authBuffer = txcavRoomParam.getAuthBuffer();
        this.enterRoomCB = enterRoomCB;
        this.protoparam.userID = this.myid;
        this.protoparam.sdkAppid = this.appid;
        this.protoparam.sdkVersion = this.sdkVersion;
        this.applyCaptureConfig();
        this.mTXCAVProtocol.enterRoom(this.protoparam, new TXCAVProtocol.TXIAVCompletionCallback() {
            @Override
            public void onComplete(final int n) {
                if (n == 0) {
                    TXCAVRoom.this.reconnectCount = 0;
                    TXCAVRoom.this.startPush();
                    TXCAVRoom.this.roomStatus = 2;
                    TXCAVRoom.videoMemNum = 1;
                    TXCAVRoom.this.postToUiThread(enterRoomCB, n);
                    if (TXCAVRoom.this.mRoomConfig.isHasVideo()) {
                        TXCAVRoom.this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_VIDEO, new TXCAVProtocol.TXIAVCompletionCallback() {
                            @Override
                            public void onComplete(final int n) {
                                TXCLog.i(TXCAVRoom.TAG, "keyway change to Video  onComplete: " + n);
                            }
                        });
                    }
                }
                else {
                    TXCAVRoom.this.roomStatus = 0;
                    TXCAVRoom.this.postToUiThread(enterRoomCB, n);
                }
            }
        });
        (this.mQos = new TXCQoS(true)).setUserID(String.valueOf(this.myid));
        this.mQos.setListener(new com.tencent.liteav.qos.a() {
            @Override
            public int onGetEncoderRealBitrate() {
                return (int)((TXCAVRoom.this.mCaptureDataCollection != null) ? TXCAVRoom.this.mCaptureDataCollection.getLongValue(4002) : 0L);
            }
            
            @Override
            public int onGetQueueInputSize() {
                return (int)(((TXCAVRoom.this.mCaptureDataCollection != null) ? TXCAVRoom.this.mCaptureDataCollection.getLongValue(7002) : 0L) + ((TXCAVRoom.this.mCaptureDataCollection != null) ? TXCAVRoom.this.mCaptureDataCollection.getLongValue(7001) : 0L));
            }
            
            @Override
            public int onGetQueueOutputSize() {
                if (TXCAVRoom.this.mCaptureDataCollection == null) {
                    return 0;
                }
                return (int)(TXCAVRoom.this.mCaptureDataCollection.getLongValue(7004) + TXCAVRoom.this.mCaptureDataCollection.getLongValue(7003));
            }
            
            @Override
            public int onGetVideoQueueMaxCount() {
                return 5;
            }
            
            @Override
            public int onGetVideoQueueCurrentCount() {
                if (TXCAVRoom.this.mCaptureDataCollection == null) {
                    return 0;
                }
                return (int)TXCAVRoom.this.mCaptureDataCollection.getLongValue(7005);
            }
            
            @Override
            public int onGetVideoDropCount() {
                if (TXCAVRoom.this.mCaptureDataCollection == null) {
                    return 0;
                }
                return (int)TXCAVRoom.this.mCaptureDataCollection.getLongValue(7007);
            }
            
            @Override
            public int onGetBandwidthEst() {
                return 0;
            }
            
            @Override
            public void onEncoderParamsChanged(final int n, final int n2, final int n3) {
                if (n != 0) {
                    if (TXCAVRoom.this.mPusher != null) {
                        TXCAVRoom.this.mPusher.a(n, n2, n3);
                    }
                    TXCLog.i(TXCAVRoom.TAG, "onEncoderParamsChanged:" + n);
                }
            }
            
            @Override
            public void onEnableDropStatusChanged(final boolean b) {
            }
        });
        this.mQos.setNotifyListener(new com.tencent.liteav.basic.b.b() {
            @Override
            public void onNotifyEvent(final int n, final Bundle bundle) {
                TXCAVRoom.this.onBothNotifyEvent(n, bundle);
            }
        });
        this.mQos.setAutoAdjustBitrate(true);
        this.mQos.setAutoAdjustStrategy(5);
        this.mQos.setDefaultVideoResolution(com.tencent.liteav.basic.a.c.b);
        this.mQos.setDefaultVideoResolution(this.videoResolution);
        this.mQos.setVideoEncBitrate(100, this.mRoomConfig.getVideoBitrate(), this.mRoomConfig.getVideoBitrate());
        this.mQos.start(TXCAVRoomConstants.AVROOM_QOS_INTERVAL);
    }
    
    public long[] getRoomMemberList() {
        return this.mTXCAVProtocol.getRoomMemberList();
    }
    
    public long[] getRoomVideoList() {
        return this.mTXCAVProtocol.getRoomVideoList();
    }
    
    private void reconnectRoom() {
        if (this.roomStatus != 2) {
            return;
        }
        TXCLog.i(TXCAVRoom.TAG, "reconnectRoom");
        if (this.reconnectCount > this.maxReconnectCount) {
            if (this.mAvRoomLisenter != null) {
                final Bundle bundle = new Bundle();
                bundle.putLong("EVT_USERID", this.myid);
                bundle.putInt("EVT_ID", TXCAVRoomConstants.AVROOM_WARNING_DISCONNECT);
                bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                bundle.putString("EVT_MSG", "Failed to reconnect many times. Abondon reconnect");
                ((Activity)this.mContext).runOnUiThread((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TXCAVRoom.this.mAvRoomLisenter.onAVRoomEvent(TXCAVRoom.this.myid, TXCAVRoomConstants.AVROOM_WARNING_DISCONNECT, bundle);
                    }
                });
            }
            return;
        }
        this.mainHandler.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                TXCAVRoom.this.mTXCAVProtocol.enterRoom(TXCAVRoom.this.protoparam, new TXCAVProtocol.TXIAVCompletionCallback() {
                    @Override
                    public void onComplete(final int n) {
                        if (n == 0) {
                            TXCAVRoom.this.reconnectCount = 0;
                            if (TXCAVRoom.this.mRoomConfig.isHasVideo()) {
                                TXCAVRoom.this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_VIDEO, new TXCAVProtocol.TXIAVCompletionCallback() {
                                    @Override
                                    public void onComplete(final int n) {
                                        TXCLog.i(TXCAVRoom.TAG, "keyway changeAVState onComplete: " + n);
                                    }
                                });
                            }
                        }
                        else {
                            TXCAVRoom.this.reconnectRoom();
                        }
                    }
                });
                TXCAVRoom.this.reconnectCount++;
            }
        }, (long)(((this.reconnectCount < 3) ? 0 : 2) * 1000));
    }
    
    public void setAvRoomLisenter(final TXCAVRoomLisenter mAvRoomLisenter) {
        this.mAvRoomLisenter = mAvRoomLisenter;
    }
    
    public void exitRoom(final TXCAVRoomCallback txcavRoomCallback) {
        if (this.roomStatus == 3 || this.roomStatus == 1) {
            this.postToUiThread(txcavRoomCallback, -4);
            return;
        }
        this.roomStatus = 3;
        if (this.mQos != null) {
            this.mQos.stop();
            this.mQos.setListener(null);
            this.mQos.setNotifyListener(null);
            this.mQos = null;
        }
        this.removeAllRender();
        this.stopLocalPreview();
        this.stopPush();
        if (this.playerList != null) {
            this.playerList.clear();
        }
        this.mTXCAVProtocol.exitRoom(new TXCAVProtocol.TXIAVCompletionCallback() {
            @Override
            public void onComplete(final int n) {
                TXCAVRoom.this.roomStatus = 0;
                TXCAVRoom.this.postToUiThread(txcavRoomCallback, n);
                if (n == 0) {
                    TXCAVRoom.videoMemNum = 0;
                    if (TXCAVRoom.this.mCaptureDataCollection != null) {
                        TXCAVRoom.this.mCaptureDataCollection.b();
                        TXCAVRoom.this.mCaptureDataCollection = null;
                    }
                }
            }
        });
    }
    
    public void destory() {
        if (this.mQos != null) {
            this.mQos.stop();
        }
        this.stopLocalPreview();
        this.removeAllRender();
        this.mPusher.f();
        this.mPusher = null;
        this.mContext = null;
        this.mTXCAVProtocol.destory();
    }
    
    public void startLocalPreview(final TXCloudVideoView localView) {
        (this.localView = localView).setVisibility(0);
        TXCLog.i(TXCAVRoom.TAG, "startLocalPreview: " + localView.getTag());
        this.applyCaptureConfig();
        this.mPusher.a(localView);
        if (this.mCaptureDataCollection == null) {
            (this.mCaptureDataCollection = new b(this.appid, this.myid, this.mContext, this.mLivePushConfig)).a(this.mTXCAVProtocol);
            this.mCaptureDataCollection.a(this.mAvRoomLisenter);
            this.mCaptureDataCollection.a();
            this.mCaptureDataCollection.a(this.mPusher);
        }
        this.mRoomConfig.setHasVideo(true);
        if (this.roomStatus != 2) {
            return;
        }
        this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_VIDEO, new TXCAVProtocol.TXIAVCompletionCallback() {
            @Override
            public void onComplete(final int n) {
                Log.i(TXCAVRoom.TAG, "keyway change to Video onComplete: " + n);
            }
        });
    }
    
    public void stopLocalPreview() {
        if (this.mCaptureDataCollection != null) {
            this.mCaptureDataCollection.b();
            this.mCaptureDataCollection = null;
        }
        if (this.localView != null) {
            this.localView.setVisibility(4);
            this.localView = null;
        }
        if (this.mPusher != null) {
            this.mPusher.c(true);
        }
        this.mRoomConfig.setHasVideo(false);
        if (this.roomStatus == 2) {
            this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_AUDIO, new TXCAVProtocol.TXIAVCompletionCallback() {
                @Override
                public void onComplete(final int n) {
                    Log.i(TXCAVRoom.TAG, "keyway change to audio onComplete: " + n);
                }
            });
        }
    }
    
    private void addRender(final long n) {
        synchronized (this.playerList) {
            if (this.playerList.get(n) == null) {
                final c c = new c(this.mContext);
                c.setID(String.valueOf(n));
                c.setNotifyListener(new com.tencent.liteav.basic.b.b() {
                    @Override
                    public void onNotifyEvent(final int n, final Bundle bundle) {
                        TXCAVRoom.this.onBothNotifyEvent(n, bundle);
                    }
                });
                final j renderConfig = this.getRenderConfig();
                c.setConfig(renderConfig);
                c.setRenderMode(this.mRoomConfig.getRemoteRenderMode());
                c.setRenderRotation(TXCAVRoomConstants.AVROOM_HOME_ORIENTATION_RIGHT);
                if (renderConfig.h) {
                    c.setVideoRender(new com.tencent.liteav.renderer.d());
                }
                else {
                    c.setVideoRender(new com.tencent.liteav.renderer.a());
                }
                this.playerList.put(n, c);
            }
            this.playerList.get(n).start(true);
        }
    }
    
    private void removeRender(final Long n) {
        synchronized (this.playerList) {
            final c c = this.playerList.get(n);
            if (c == null) {
                return;
            }
            c.stop();
            c.setVideoRender(null);
            c.setDecListener(null);
            if (c.a() != null) {
                c.a().b();
                c.a((AAAA)null);
            }
            this.playerList.remove(n);
        }
    }
    
    public synchronized void startRemoteView(final TXCloudVideoView txCloudVideoView, final long n) {
        synchronized (this.playerList) {
            final c c = this.playerList.get(n);
            if (c == null) {
                return;
            }
            if (txCloudVideoView != null) {
                c.startPreview(txCloudVideoView);
                this.videoMemList.add(n);
                c.setRenderMode(0);
                this.requestView(this.videoMemList);
            }
            final AAAA a = new AAAA(n);
            a.a(this.mAvRoomLisenter);
            a.a(this.mTXCAVProtocol);
            a.a(this.playerList.get(n));
            a.a(this.playerList.get(n).getVideoRender());
            this.playerList.get(n).a(a);
            a.a();
            this.setRenderMode(this.mRoomConfig.getRemoteRenderMode());
        }
    }
    
    public synchronized void stopRemoteView(final long n) {
        this.videoMemList.remove(n);
        this.requestView(this.videoMemList);
    }
    
    private void removeAllRender() {
        if (this.playerList == null || this.playerList.size() == 0) {
            return;
        }
        for (final Long n : this.playerList.keySet()) {
            if (this.playerList.get(n) != null) {
                this.stopRemoteView(n);
                this.playerList.get(n).stop();
                if (this.playerList.get(n) == null || this.playerList.get(n).a() == null) {
                    continue;
                }
                this.playerList.get(n).a().b();
                this.playerList.get(n).a((AAAA)null);
            }
        }
        this.playerList.clear();
    }
    
    private void requestViewList(final ArrayList<TXCAVProtocol.TXSAVRoomView> list, final TXCAVRoomCallback txcavRoomCallback) {
        this.mTXCAVProtocol.requestViews(list, new TXCAVProtocol.TXIAVCompletionCallback() {
            @Override
            public void onComplete(final int n) {
                TXCAVRoom.this.postToUiThread(txcavRoomCallback, n);
            }
        });
    }
    
    private void startPush() {
        TXCLog.i(TXCAVRoom.TAG, "keyway startPush: ");
        this.allowedPush = true;
        this.mPusher.e();
    }
    
    private void stopPush() {
        TXCLog.i(TXCAVRoom.TAG, "keyway stopPush: ");
        this.allowedPush = false;
        this.mPusher.f();
    }
    
    public void onPause() {
        TXCLog.i(TXCAVRoom.TAG, "keyway onPause : ");
        this.mPusher.g();
    }
    
    public void onResume() {
        TXCLog.i(TXCAVRoom.TAG, "keyway onResume: ");
        this.mPusher.h();
    }
    
    public void setVideoBitrateAndvideoAspect(final int n, final int n2) {
        this.videoResolution = TXCQoS.getProperResolutionByVideoBitrate(true, n, n2);
        if (this.videoResolution == null) {
            if (n2 == 1) {
                this.videoResolution = com.tencent.liteav.basic.a.c.b;
            }
            else if (n2 == 2) {
                this.videoResolution = com.tencent.liteav.basic.a.c.n;
            }
            else if (n2 == 3) {
                this.videoResolution = com.tencent.liteav.basic.a.c.s;
            }
            else {
                this.videoResolution = com.tencent.liteav.basic.a.c.b;
            }
        }
        TXCLog.i(TXCAVRoom.TAG, "setVideoBitrateAndvideoAspect videoBitrate: " + n + " videoAspect:" + n2 + " videoResolution:" + this.videoResolution);
        this.mRoomConfig.videoBitrate(n);
        this.applyCaptureConfig();
    }
    
    private void postToUiThread(final TXCAVRoomCallback txcavRoomCallback, final int n) {
        if (this.mContext != null) {
            ((Activity)this.mContext).runOnUiThread((Runnable)new Runnable() {
                @Override
                public void run() {
                    txcavRoomCallback.onComplete(n);
                }
            });
        }
    }
    
    private void requestView(final ArrayList<Long> list) {
        TXCLog.i(TXCAVRoom.TAG, "requestView: " + list.size());
        final ArrayList<TXCAVProtocol.TXSAVRoomView> list2 = new ArrayList<TXCAVProtocol.TXSAVRoomView>();
        for (final long longValue : list) {
            final TXCAVProtocol.TXSAVRoomView txsavRoomView = new TXCAVProtocol().new TXSAVRoomView();
            txsavRoomView.tinyID = longValue;
            txsavRoomView.height = 240;
            txsavRoomView.width = 320;
            list2.add(txsavRoomView);
        }
        this.requestViewList(list2, new TXCAVRoomCallback() {
            @Override
            public void onComplete(final int n) {
                TXCLog.i(TXCAVRoom.TAG, "keyway requestViewList onComplete: " + n);
            }
        });
    }
    
    public void switchCamera() {
        if (this.mPusher != null) {
            this.mPusher.k();
        }
    }
    
    public void setRemoteMute(final boolean b, final long n) {
        if (this.playerList == null) {
            return;
        }
        if (n == 0L) {
            final Iterator<Long> iterator = this.playerList.keySet().iterator();
            while (iterator.hasNext()) {
                final c c = this.playerList.get(iterator.next());
            }
        }
        synchronized (this.playerList) {
            if (this.playerList.get(n) != null) {}
        }
    }
    
    public void setMirror(final boolean b) {
        if (this.mPusher != null) {
            this.mPusher.f(b);
        }
    }
    
    public void setAudioMode(final int n) {
    }
    
    public void setLocalMute(final boolean b) {
        if (this.mPusher != null) {}
    }
    
    private void applyCaptureConfig() {
        if (this.mPusher != null) {
            this.mLivePushConfig = new i();
            this.mLivePushConfig.s = TXCAVRoomConstants.AVROOM_AUDIO_SAMPLE_RATE;
            this.mLivePushConfig.t = 1;
            this.mLivePushConfig.u = true;
            this.mLivePushConfig.c = this.mRoomConfig.getVideoBitrate();
            this.mLivePushConfig.h = this.mRoomConfig.getCaptureVideoFPS();
            this.mLivePushConfig.i = 1;
            this.mLivePushConfig.l = this.mRoomConfig.getHomeOrientation();
            this.mLivePushConfig.m = this.mRoomConfig.isFrontCamera();
            this.mLivePushConfig.C = this.mRoomConfig.getPauseFps();
            this.mLivePushConfig.A = this.mRoomConfig.getPauseImg();
            this.mLivePushConfig.B = TXCAVRoomConstants.AVROOM_PUSH_PAUSETIME;
            this.mLivePushConfig.D = this.mRoomConfig.getPauseFlag();
            this.mLivePushConfig.k = this.videoResolution;
            this.mLivePushConfig.j = (this.mRoomConfig.isEnableVideoHWAcceleration() ? 1 : 0);
            if (this.mQos != null) {
                this.mQos.setDefaultVideoResolution(this.videoResolution);
                this.mQos.setVideoEncBitrate(100, this.mRoomConfig.getVideoBitrate(), this.mRoomConfig.getVideoBitrate());
            }
            this.mPusher.a(this.mLivePushConfig);
        }
    }
    
    private j getRenderConfig() {
        final j j = new j();
        j.a(true);
        j.a(TXCAVRoomConstants.AVROOM_CACHETIME);
        j.b(TXCAVRoomConstants.AVROOM_MAX_ADJUSTCACHETIME);
        j.h = this.mRoomConfig.isEnableVideoHWAcceleration();
        return j;
    }
    
    public void setRenderMode(final int renderMode) {
        for (final Map.Entry<Long, c> entry : this.playerList.entrySet()) {
            TXCLog.i(TXCAVRoom.TAG, "Key = " + entry.getKey() + ", Value = " + entry.getValue());
            entry.getValue().setRenderMode(renderMode);
        }
    }
    
    public void setEyeScaleLevel(final int eyeScaleLevel) {
        this.mPusher.b().setEyeScaleLevel(eyeScaleLevel);
    }
    
    public void setFaceSlimLevel(final int faceSlimLevel) {
        this.mPusher.b().setFaceSlimLevel(faceSlimLevel);
    }
    
    public void setFilter(final Bitmap filter) {
        this.mPusher.b().setFilter(filter);
    }
    
    public void setGreenScreenFile(final String greenScreenFile) {
        this.mPusher.b().setGreenScreenFile(greenScreenFile);
    }
    
    public void setMotionTmpl(final String motionTmpl) {
        this.mPusher.b().setMotionTmpl(motionTmpl);
    }
    
    public void setExposureCompensation(final float n) {
        this.mPusher.a(n);
    }
    
    public void setBeautyFilter(final int beautyStyle, final int ruddyLevel, final int n, final int n2) {
        if (null != this.mPusher) {
            this.mPusher.b().setBeautyStyle(beautyStyle);
            this.mPusher.b().setBeautyLevel(ruddyLevel);
            this.mPusher.b().setWhitenessLevel(ruddyLevel);
            this.mPusher.b().setRuddyLevel(ruddyLevel);
        }
    }
    
    public void setFaceVLevel(final int faceVLevel) {
        if (this.mPusher != null) {
            this.mPusher.b().setFaceVLevel(faceVLevel);
        }
    }
    
    public void setSpecialRatio(final float filterStrength) {
        if (this.mPusher != null) {
            this.mPusher.b().setFilterStrength(filterStrength);
        }
    }
    
    public void setFaceShortLevel(final int faceShortLevel) {
        if (this.mPusher != null) {
            this.mPusher.b().setFaceShortLevel(faceShortLevel);
        }
    }
    
    public void setChinLevel(final int chinLevel) {
        if (this.mPusher != null) {
            this.mPusher.b().setChinLevel(chinLevel);
        }
    }
    
    public void setNoseSlimLevel(final int noseSlimLevel) {
        if (this.mPusher != null) {
            this.mPusher.b().setNoseSlimLevel(noseSlimLevel);
        }
    }
    
    private void onBothNotifyEvent(int n, final Bundle bundle) {
        TXCLog.i(TXCAVRoom.TAG, "AVROOM onNotifyEvent: " + bundle.getString("EVT_MSG"));
        if (this.mAvRoomLisenter != null) {
            final Long value = bundle.getLong("EVT_USERID");
            switch (n) {
                case 1003: {
                    n = TXCAVRoomConstants.AVROOM_EVT_OPEN_CAMERA_SUCC;
                    break;
                }
                case -1301: {
                    n = TXCAVRoomConstants.AVROOM_ERR_OPEN_CAMERA_FAIL;
                    break;
                }
                case 1007: {
                    n = TXCAVRoomConstants.AVROOM_EVT_FIRST_FRAME_AVAILABLE;
                    break;
                }
                case -1302: {
                    n = TXCAVRoomConstants.AVROOM_ERR_OPEN_MIC_FAIL;
                    break;
                }
                case 1008: {
                    n = TXCAVRoomConstants.AVROOM_EVT_START_VIDEO_ENCODER;
                    break;
                }
                case 1103: {
                    n = TXCAVRoomConstants.AVROOM_WARNING_HW_ACCELERATION_ENCODE_FAIL;
                    break;
                }
                case -1303: {
                    n = TXCAVRoomConstants.AVROOM_ERR_VIDEO_ENCODE_FAIL;
                    break;
                }
                case 1005: {
                    n = TXCAVRoomConstants.AVROOM_EVT_UP_CHANGE_RESOLUTION;
                }
                case 1006: {
                    n = TXCAVRoomConstants.AVROOM_EVT_UP_CHANGE_BITRATE;
                    break;
                }
                case 2003: {
                    n = TXCAVRoomConstants.AVROOM_EVT_RCV_FIRST_I_FRAME;
                    break;
                }
                case 2105: {
                    n = TXCAVRoomConstants.AVROOM_WARNING_VIDEO_PLAY_LAG;
                    break;
                }
                case 2007: {
                    n = TXCAVRoomConstants.AVROOM_EVT_PLAY_LOADING;
                    break;
                }
                case 2004: {
                    n = TXCAVRoomConstants.AVROOM_EVT_PLAY_BEGIN;
                    break;
                }
                case 2008: {
                    n = TXCAVRoomConstants.AVROOM_EVT_START_VIDEO_DECODER;
                    break;
                }
                case 2106: {
                    n = TXCAVRoomConstants.AVROOM_WARNING_HW_ACCELERATION_DECODE_FAIL;
                    break;
                }
                case 2101: {
                    n = TXCAVRoomConstants.AVROOM_WARNING_VIDEO_DECODE_FAIL;
                    break;
                }
                default: {
                    return;
                }
            }
            final int n2 = n;
            final Bundle bundle2 = new Bundle();
            bundle2.putLong("EVT_USERID", (long)value);
            bundle2.putInt("EVT_ID", bundle.getInt("EVT_ID", 0));
            bundle2.putLong("EVT_TIME", bundle.getLong("EVT_TIME", 0L));
            bundle2.putString("EVT_MSG", bundle.getString("EVT_MSG", ""));
            if (this.mPusher != null) {
                ((Activity)this.mContext).runOnUiThread((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TXCLog.i(TXCAVRoom.TAG, "NotifyEvent sendNotifyEvent userID: " + value + "  msg " + bundle2.getString("EVT_MSG"));
                        TXCAVRoom.this.mAvRoomLisenter.onAVRoomEvent(value, n2, bundle2);
                    }
                });
            }
        }
    }
    
    static {
        TAG = TXCAVRoom.class.getSimpleName();
        TXCAVRoom.videoMemNum = 0;
    }
}
