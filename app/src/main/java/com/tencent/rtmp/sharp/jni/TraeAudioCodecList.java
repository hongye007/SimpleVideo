package com.tencent.rtmp.sharp.jni;

import java.util.*;
import java.util.concurrent.locks.*;

public class TraeAudioCodecList
{
    private ArrayList<CodecInfo> _sessionInfoList;
    private ReentrantLock mLock;
    
    public TraeAudioCodecList() {
        this._sessionInfoList = new ArrayList<CodecInfo>();
        this.mLock = new ReentrantLock();
    }
    
    public CodecInfo find(final long n) {
        CodecInfo codecInfo = null;
        this.mLock.lock();
        for (int i = 0; i < this._sessionInfoList.size(); ++i) {
            final CodecInfo codecInfo2 = this._sessionInfoList.get(i);
            if (codecInfo2.sessionId == n) {
                codecInfo = codecInfo2;
                break;
            }
        }
        this.mLock.unlock();
        return codecInfo;
    }
    
    public CodecInfo add(final long sessionId) {
        final CodecInfo find = this.find(sessionId);
        if (null != find) {
            return find;
        }
        final CodecInfo codecInfo = new CodecInfo();
        codecInfo.sessionId = sessionId;
        codecInfo.audioDecoder = new AudioDecoder();
        codecInfo._tempBufdec = new byte[3840];
        this.mLock.lock();
        this._sessionInfoList.add(codecInfo);
        this.mLock.unlock();
        return this.find(sessionId);
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
    
    public class CodecInfo
    {
        public long sessionId;
        public AudioDecoder audioDecoder;
        public byte[] _tempBufdec;
    }
}
