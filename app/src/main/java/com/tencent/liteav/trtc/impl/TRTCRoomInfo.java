package com.tencent.liteav.trtc.impl;

import com.tencent.trtc.*;
import com.tencent.rtmp.ui.*;
import org.json.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import android.content.*;
import java.util.*;
import com.tencent.liteav.*;
import com.tencent.liteav.renderer.*;
import android.view.*;

public class TRTCRoomInfo
{
    private static final String TAG = "TRTCRoomInfo";
    private static final String TRTC_INFO = "TRTC.Info";
    private static final String TOKEN = "TRTC.0x0.Token";
    public static final int STATE_BIG_VIDEO = 1;
    public static final int STATE_SMALL_VIDEO = 2;
    public static final int STATE_SUB_VIDEO = 4;
    public static final int STATE_AUDIO = 8;
    public static final int STATE_MUTE_MAIN_VIDEO = 16;
    public static final int STATE_MUTE_SUB_VIDEO = 32;
    public static final int STATE_MUTE_AUDIO = 64;
    public static final int NETWORK_STATUS_OFFLINE = 1;
    public static final int NETWORK_STATUS_RECONNECTING = 2;
    public static final int NETWORK_STATUS_ONLINE = 3;
    public int sdkAppId;
    public long enterTime;
    public long roomId;
    public String strRoomId;
    public String userId;
    public String tinyId;
    public byte[] token;
    public String userSig;
    public String privateMapKey;
    public int networkStatus;
    public TRTCCloud.TRTCViewMargin debugMargin;
    public int localPixelFormat;
    public int localBufferType;
    public TRTCCloudListener.TRTCVideoRenderListener localListener;
    public boolean enableCustomPreprocessor;
    public TXCloudVideoView localView;
    private HashMap<String, UserInfo> userList;
    private HashMap<Long, Integer> recvFirstIFrameCntList;
    public boolean muteLocalVideo;
    public boolean muteRemoteVideo;
    public boolean muteLocalAudio;
    public boolean muteRemoteAudio;
    public int localRenderRotation;
    private boolean micHasStartd;
    private boolean hasExitedRoom;
    private int exitRoomCode;
    public JSONArray decProperties;
    public boolean enableRestartDecoder;
    public i.a bigEncSize;
    public i.a smallEncSize;
    
    public TRTCRoomInfo() {
        this.userId = "";
        this.token = null;
        this.networkStatus = 1;
        this.debugMargin = new TRTCCloud.TRTCViewMargin(0.0f, 0.0f, 0.1f, 0.0f);
        this.enableCustomPreprocessor = false;
        this.localView = null;
        this.userList = new HashMap<String, UserInfo>();
        this.recvFirstIFrameCntList = new HashMap<Long, Integer>();
        this.muteLocalVideo = false;
        this.muteRemoteVideo = false;
        this.muteLocalAudio = false;
        this.muteRemoteAudio = false;
        this.localRenderRotation = 0;
        this.micHasStartd = false;
        this.hasExitedRoom = false;
        this.exitRoomCode = 0;
        this.decProperties = null;
        this.enableRestartDecoder = false;
        this.bigEncSize = new i.a();
        this.smallEncSize = new i.a();
    }
    
    public void init(final long roomId, final String userId) {
        this.roomId = roomId;
        this.userId = userId;
    }
    
    public synchronized void clear() {
        this.roomId = 0L;
        this.userId = "";
        this.enterTime = 0L;
        this.tinyId = "";
        this.muteLocalVideo = false;
        this.muteLocalAudio = false;
        this.muteRemoteVideo = false;
        this.muteRemoteAudio = false;
        this.userList.clear();
        this.recvFirstIFrameCntList.clear();
        this.networkStatus = 1;
        this.decProperties = null;
        this.enableRestartDecoder = false;
        this.enableCustomPreprocessor = false;
        this.localListener = null;
    }
    
    public String getStrRoomId() {
        return TextUtils.isEmpty((CharSequence)this.strRoomId) ? String.valueOf(this.roomId) : this.strRoomId;
    }
    
    public long getRoomId() {
        return this.roomId;
    }
    
    public void setRoomId(final int n) {
        this.roomId = n;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    
    public String getTinyId() {
        return this.tinyId;
    }
    
    public void setTinyId(final String tinyId) {
        this.tinyId = tinyId;
    }
    
    private String byteArrayToHexStr(final byte[] array) {
        if (array == null) {
            return null;
        }
        final char[] charArray = "0123456789ABCDEF".toCharArray();
        final char[] array2 = new char[array.length * 2];
        for (int i = 0; i < array.length; ++i) {
            final int n = array[i] & 0xFF;
            array2[i * 2] = charArray[n >>> 4];
            array2[i * 2 + 1] = charArray[n & 0xF];
        }
        return new String(array2);
    }
    
    private byte[] hexStrToByteArray(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return new byte[0];
        }
        final byte[] array = new byte[s.length() / 2];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return array;
    }
    
    public void setToken(final Context context, final byte[] token) {
        this.token = token;
        try {
            final SharedPreferences.Editor edit = context.getSharedPreferences("TRTC.Info", 0).edit();
            if (this.token != null) {
                edit.putString("TRTC.0x0.Token", this.byteArrayToHexStr(this.token));
            }
            else {
                edit.clear();
            }
            edit.commit();
        }
        catch (Exception ex) {
            TXCLog.e("TRTCRoomInfo", "set token failed.", ex);
        }
    }
    
    public byte[] getToken(final Context context) {
        try {
            if (this.token == null) {
                this.token = this.hexStrToByteArray(context.getSharedPreferences("TRTC.Info", 0).getString("TRTC.0x0.Token", ""));
            }
        }
        catch (Exception ex) {
            TXCLog.e("TRTCRoomInfo", "get token failed.", ex);
        }
        return this.token;
    }
    
    public long getRoomElapsed() {
        return System.currentTimeMillis() - this.enterTime;
    }
    
    public synchronized void addUserInfo(final String s, final UserInfo userInfo) {
        this.userList.put(s, userInfo);
    }
    
    public synchronized void removeRenderInfo(final String s) {
        this.recvFirstIFrameCntList.remove(this.userList.get(s).tinyID);
        this.userList.remove(s);
    }
    
    public synchronized UserInfo getUser(final String s) {
        return this.userList.get(s);
    }
    
    public synchronized String getUserIdByTinyId(final long n) {
        final Iterator<Map.Entry<String, UserInfo>> iterator = this.userList.entrySet().iterator();
        while (iterator.hasNext()) {
            final UserInfo userInfo = iterator.next().getValue();
            if (userInfo != null && userInfo.tinyID == n) {
                return userInfo.userID;
            }
        }
        return null;
    }
    
    public void forEachUser(final UserAction userAction) {
        final HashMap<String, Object> hashMap = new HashMap<String, Object>(this.userList.size());
        synchronized (this) {
            hashMap.putAll(this.userList);
        }
        for (final Map.Entry<String, V> entry : hashMap.entrySet()) {
            if (userAction != null && entry.getValue() != null) {
                userAction.accept(entry.getKey(), (UserInfo)entry.getValue());
            }
        }
    }
    
    public synchronized void clearUserList() {
        this.userList.clear();
        this.recvFirstIFrameCntList.clear();
    }
    
    public synchronized int recvFirstIFrame(final long n) {
        final Integer n2 = this.recvFirstIFrameCntList.get(n);
        this.recvFirstIFrameCntList.put(n, (n2 == null) ? 1 : (n2 + 1));
        return (n2 == null) ? 1 : (n2 + 1);
    }
    
    public synchronized boolean hasRecvFirstIFrame(final long n) {
        final Integer n2 = this.recvFirstIFrameCntList.get(n);
        return n2 != null && n2 > 0;
    }
    
    public void micStart(final boolean micHasStartd) {
        this.micHasStartd = micHasStartd;
    }
    
    public synchronized boolean isMicStard() {
        return this.micHasStartd;
    }
    
    public synchronized void setRoomExit(final boolean hasExitedRoom, final int exitRoomCode) {
        this.hasExitedRoom = hasExitedRoom;
        this.exitRoomCode = exitRoomCode;
    }
    
    public synchronized boolean isRoomExit() {
        return this.hasExitedRoom;
    }
    
    public synchronized int getRoomExitCode() {
        return this.exitRoomCode;
    }
    
    public static boolean isMuteMainVideo(final int n) {
        return (n & 0x10) != 0x0;
    }
    
    public static boolean isMuteSubVideo(final int n) {
        return (n & 0x20) != 0x0;
    }
    
    public static boolean isMuteAudio(final int n) {
        return (n & 0x40) != 0x0;
    }
    
    public static boolean hasMainVideo(final int n) {
        return (n & 0x1) != 0x0;
    }
    
    public static boolean hasSmallVideo(final int n) {
        return (n & 0x2) != 0x0;
    }
    
    public static boolean hasSubVideo(final int n) {
        return (n & 0x4) != 0x0;
    }
    
    public static boolean hasAudio(final int n) {
        return (n & 0x8) != 0x0;
    }
    
    public static class RenderInfo implements SurfaceHolder.Callback
    {
        public long tinyID;
        public TXCRenderAndDec render;
        public TXCloudVideoView view;
        public boolean muteVideo;
        public boolean muteAudio;
        
        public RenderInfo() {
            this.render = null;
            this.view = null;
            this.muteVideo = false;
            this.muteAudio = false;
        }
        
        public void surfaceCreated(final SurfaceHolder surfaceHolder) {
            TXCLog.i("RenderInfo", "trtc_api startRemoteView surfaceCreated " + surfaceHolder.getSurface() + ", " + this.tinyID + ", " + this.render);
            if (surfaceHolder.getSurface().isValid()) {
                final f f = (this.render != null) ? this.render.getVideoRender() : null;
                if (f != null) {
                    f.a(surfaceHolder.getSurface());
                }
            }
        }
        
        public void surfaceChanged(final SurfaceHolder surfaceHolder, final int n, final int n2, final int n3) {
            TXCLog.i("RenderInfo", "trtc_api startRemoteView surfaceChanged " + surfaceHolder.getSurface() + " width " + n2 + ", height " + n3 + ", " + this.tinyID + ", " + this.render);
            final f f = (this.render != null) ? this.render.getVideoRender() : null;
            if (f != null) {
                f.c(n2, n3);
            }
        }
        
        public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
            TXCLog.i("RenderInfo", "trtc_api startRemoteView surfaceDestroyed " + surfaceHolder.getSurface() + ", " + this.tinyID + ", " + this.render);
            final f f = (this.render != null) ? this.render.getVideoRender() : null;
            if (f != null) {
                f.a((Surface)null);
            }
        }
        
        public void stop() {
            try {
                if (this.view != null && this.view.getSurfaceView() != null && this.view.getSurfaceView().getHolder() != null) {
                    this.view.getSurfaceView().getHolder().removeCallback((SurfaceHolder.Callback)this);
                }
            }
            catch (Exception ex) {
                TXCLog.e("TRTCRoomInfo", "remove callback failed.", ex);
            }
        }
    }
    
    public static class UserInfo
    {
        public long tinyID;
        public String userID;
        public int terminalType;
        public RenderInfo mainRender;
        public RenderInfo subRender;
        public int streamType;
        public TRTCCloud.TRTCViewMargin debugMargin;
        public int streamState;
        public boolean muteAudioInSpeaker;
        
        public UserInfo(final long tinyID, final String userID, final int terminalType, final int streamState) {
            this.mainRender = new RenderInfo();
            this.subRender = new RenderInfo();
            this.streamType = 2;
            this.debugMargin = new TRTCCloud.TRTCViewMargin(0.0f, 0.0f, 0.1f, 0.0f);
            this.muteAudioInSpeaker = false;
            this.tinyID = tinyID;
            this.userID = userID;
            this.terminalType = terminalType;
            this.streamState = streamState;
            this.mainRender.tinyID = tinyID;
            this.subRender.tinyID = tinyID;
        }
    }
    
    public interface UserAction
    {
        void accept(final String p0, final UserInfo p1);
    }
}
