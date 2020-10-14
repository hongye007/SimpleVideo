package com.tencent.rtmp.sharp.jni;

import java.util.*;
import java.util.concurrent.locks.*;
import android.content.*;

public class TraeAudioSessionHost
{
    private ArrayList<SessionInfo> _sessionInfoList;
    private ReentrantLock mLock;
    
    public TraeAudioSessionHost() {
        this._sessionInfoList = new ArrayList<SessionInfo>();
        this.mLock = new ReentrantLock();
    }
    
    public SessionInfo find(final long n) {
        SessionInfo sessionInfo = null;
        this.mLock.lock();
        for (int i = 0; i < this._sessionInfoList.size(); ++i) {
            final SessionInfo sessionInfo2 = this._sessionInfoList.get(i);
            if (sessionInfo2.sessionId == n) {
                sessionInfo = sessionInfo2;
                break;
            }
        }
        this.mLock.unlock();
        return sessionInfo;
    }
    
    public void add(final TraeAudioSession traeAs, final long sessionId, final Context context) {
        if (null != this.find(sessionId)) {
            return;
        }
        final SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.sessionId = sessionId;
        sessionInfo._traeAs = traeAs;
        this.mLock.lock();
        this._sessionInfoList.add(sessionInfo);
        this.mLock.unlock();
    }
    
    public void remove(final long n) {
        this.mLock.lock();
        for (int i = 0; i < this._sessionInfoList.size(); ++i) {
            if (this._sessionInfoList.get(i).sessionId == n) {
                this._sessionInfoList.remove(i);
                break;
            }
        }
        this.mLock.unlock();
    }
    
    public void sendToAudioSessionMessage(final Intent intent) {
        this.mLock.lock();
        for (int i = 0; i < this._sessionInfoList.size(); ++i) {
            this._sessionInfoList.get(i)._traeAs.onReceiveCallback(intent);
        }
        this.mLock.unlock();
    }
    
    public class SessionInfo
    {
        public long sessionId;
        public TraeAudioSession _traeAs;
    }
}
