package com.tencent.rtmp.sharp.jni;

import android.content.*;
import java.util.*;
import android.net.*;
import android.media.*;
import android.os.*;
import java.io.*;
import android.content.res.*;

public class TraeMediaPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener
{
    public static final int TRAE_MEDIAPLAER_DATASOURCE_RSID = 0;
    public static final int TRAE_MEDIAPLAER_DATASOURCE_URI = 1;
    public static final int TRAE_MEDIAPLAER_DATASOURCE_FILEPATH = 2;
    public static final int TRAE_MEDIAPLAER_STOP = 100;
    private MediaPlayer mMediaPlay;
    private OnCompletionListener mCallback;
    private Context _context;
    private int _streamType;
    private boolean _hasCall;
    private boolean _loop;
    private int _durationMS;
    int _loopCount;
    boolean _ringMode;
    private Timer _watchTimer;
    private TimerTask _watchTimertask;
    private int _prevVolume;
    
    public TraeMediaPlayer(final Context context, final OnCompletionListener mCallback) {
        this.mMediaPlay = null;
        this._streamType = 0;
        this._hasCall = false;
        this._loop = false;
        this._durationMS = -1;
        this._loopCount = 0;
        this._ringMode = false;
        this._watchTimer = null;
        this._watchTimertask = null;
        this._prevVolume = -1;
        this._context = context;
        this.mCallback = mCallback;
    }
    
    public boolean playRing(final int n, final int n2, final Uri uri, final String dataSource, final boolean b, final int loopCount, final boolean ringMode, final boolean hasCall, final int streamType) {
        if (QLog.isColorLevel()) {
            QLog.e("TRAE", 2, "TraeMediaPlay | playRing datasource:" + n + " rsid:" + n2 + " uri:" + uri + " filepath:" + dataSource + " loop:" + (b ? "Y" : "N") + " :loopCount" + loopCount + " ringMode:" + (ringMode ? "Y" : "N") + " hasCall:" + hasCall + " cst:" + streamType);
        }
        if (!b && loopCount <= 0) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "TraeMediaPlay | playRing err datasource:" + n + " loop:" + (b ? "Y" : "N") + " :loopCount" + loopCount);
            }
            return false;
        }
        try {
            try {
                if (this.mMediaPlay != null) {
                    if (this.mMediaPlay.isPlaying()) {
                        return false;
                    }
                    try {
                        this.mMediaPlay.release();
                    }
                    catch (Exception ex) {
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "release MediaPlayer failed." + ex.getMessage());
                        }
                    }
                    finally {
                        this.mMediaPlay = null;
                    }
                }
                if (this._watchTimer != null) {
                    this._watchTimer.cancel();
                    this._watchTimer = null;
                    this._watchTimertask = null;
                }
                final AudioManager audioManager = (AudioManager)this._context.getSystemService("audio");
                this.mMediaPlay = new MediaPlayer();
                if (null == this.mMediaPlay) {
                    this.mMediaPlay.release();
                    this.mMediaPlay = null;
                    return false;
                }
                this.mMediaPlay.setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
                this.mMediaPlay.setOnErrorListener((MediaPlayer.OnErrorListener)this);
                switch (n) {
                    case 0: {
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "TraeMediaPlay | rsid:" + n2);
                        }
                        final AssetFileDescriptor openRawResourceFd = this._context.getResources().openRawResourceFd(n2);
                        if (openRawResourceFd == null) {
                            if (QLog.isColorLevel()) {
                                QLog.e("TRAE", 2, "TraeMediaPlay | afd == null rsid:" + n2);
                            }
                            this.mMediaPlay.release();
                            this.mMediaPlay = null;
                            return false;
                        }
                        this.mMediaPlay.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
                        openRawResourceFd.close();
                        break;
                    }
                    case 1: {
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "TraeMediaPlay | uri:" + uri);
                        }
                        this.mMediaPlay.setDataSource(this._context, uri);
                        break;
                    }
                    case 2: {
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "TraeMediaPlay | FilePath:" + dataSource);
                        }
                        this.mMediaPlay.setDataSource(dataSource);
                        break;
                    }
                    default: {
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "TraeMediaPlay | err datasource:" + n);
                        }
                        this.mMediaPlay.release();
                        this.mMediaPlay = null;
                        break;
                    }
                }
                if (this.mMediaPlay == null) {
                    return false;
                }
                this._ringMode = ringMode;
                int mode = 0;
                if (this._ringMode) {
                    this._streamType = 2;
                    mode = 1;
                }
                else {
                    this._streamType = 0;
                    if (Build.VERSION.SDK_INT >= 11) {
                        mode = 3;
                    }
                }
                this._hasCall = hasCall;
                if (this._hasCall) {
                    this._streamType = streamType;
                }
                this.mMediaPlay.setAudioStreamType(this._streamType);
                this.mMediaPlay.prepare();
                this.mMediaPlay.setLooping(b);
                this.mMediaPlay.start();
                this._loop = b;
                if (this._loop) {
                    this._loopCount = 1;
                    this._durationMS = -1;
                }
                else {
                    this._loopCount = loopCount;
                    this._durationMS = this._loopCount * this.mMediaPlay.getDuration();
                }
                --this._loopCount;
                if (!this._hasCall) {
                    audioManager.setMode(mode);
                }
                if (this._durationMS > 0) {
                    this._watchTimer = new Timer();
                    this._watchTimertask = new TimerTask() {
                        @Override
                        public void run() {
                            if (TraeMediaPlayer.this.mMediaPlay != null) {
                                if (QLog.isColorLevel()) {
                                    QLog.e("TRAE", 2, "TraeMediaPlay | play timeout");
                                }
                                if (null != TraeMediaPlayer.this.mCallback) {
                                    TraeMediaPlayer.this.mCallback.onCompletion();
                                }
                            }
                        }
                    };
                    this._watchTimer.schedule(this._watchTimertask, this._durationMS + 1000);
                }
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "TraeMediaPlay | DurationMS:" + this.mMediaPlay.getDuration() + " loop:" + b);
                }
                return true;
            }
            catch (IllegalStateException ex2) {
                if (QLog.isColorLevel()) {
                    QLog.d("TRAE", 2, "TraeMediaPlay | IllegalStateException: " + ex2.getLocalizedMessage() + " " + ex2.getMessage());
                }
            }
            catch (IOException ex3) {
                if (QLog.isColorLevel()) {
                    QLog.d("TRAE", 2, "TraeMediaPlay | IOException: " + ex3.getLocalizedMessage() + " " + ex3.getMessage());
                }
            }
            catch (IllegalArgumentException ex4) {
                if (QLog.isColorLevel()) {
                    QLog.d("TRAE", 2, "TraeMediaPlay | IllegalArgumentException: " + ex4.getLocalizedMessage() + " " + ex4.getMessage());
                }
            }
            catch (SecurityException ex5) {
                if (QLog.isColorLevel()) {
                    QLog.d("TRAE", 2, "TraeMediaPlay | SecurityException: " + ex5.getLocalizedMessage() + " " + ex5.getMessage());
                }
            }
        }
        catch (Exception ex6) {
            if (QLog.isColorLevel()) {
                QLog.d("TRAE", 2, "TraeMediaPlay | Except: " + ex6.getLocalizedMessage() + " " + ex6.getMessage());
            }
        }
        try {
            this.mMediaPlay.release();
        }
        catch (Exception ex7) {}
        this.mMediaPlay = null;
        return false;
    }
    
    public void stopRing() {
        if (QLog.isColorLevel()) {
            QLog.d("TRAE", 2, "TraeMediaPlay stopRing ");
        }
        if (null == this.mMediaPlay) {
            return;
        }
        if (this.mMediaPlay.isPlaying()) {
            this.mMediaPlay.stop();
        }
        this.mMediaPlay.reset();
        try {
            if (this._watchTimer != null) {
                this._watchTimer.cancel();
                this._watchTimer = null;
                this._watchTimertask = null;
            }
            this.mMediaPlay.release();
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "release MediaPlayer failed." + ex.getMessage());
            }
        }
        this.mMediaPlay = null;
        this._durationMS = -1;
    }
    
    public int getStreamType() {
        return this._streamType;
    }
    
    public int getDuration() {
        return this._durationMS;
    }
    
    public boolean hasCall() {
        return this._hasCall;
    }
    
    public void onCompletion(final MediaPlayer mediaPlayer) {
        AudioDeviceInterface.LogTraceEntry(" cb:" + this.mCallback + " loopCount:" + this._loopCount + " _loop:" + this._loop);
        if (this._loop) {
            if (QLog.isColorLevel()) {
                QLog.d("TRAE", 2, "loop play,continue...");
            }
            return;
        }
        try {
            if (this._loopCount <= 0) {
                this.volumeUndo();
                if (this.mMediaPlay.isPlaying()) {
                    this.mMediaPlay.stop();
                }
                this.mMediaPlay.reset();
                this.mMediaPlay.release();
                this.mMediaPlay = null;
                if (null != this.mCallback) {
                    this.mCallback.onCompletion();
                }
            }
            else {
                this.mMediaPlay.start();
                --this._loopCount;
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "stop play failed." + ex.getMessage());
            }
        }
        AudioDeviceInterface.LogTraceExit();
    }
    
    public boolean onError(final MediaPlayer mediaPlayer, final int n, final int n2) {
        AudioDeviceInterface.LogTraceEntry(" cb:" + this.mCallback + " arg1:" + n + " arg2:" + n2);
        try {
            this.mMediaPlay.release();
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "release MediaPlayer failed." + ex.getMessage());
            }
        }
        this.mMediaPlay = null;
        if (null != this.mCallback) {
            this.mCallback.onCompletion();
        }
        AudioDeviceInterface.LogTraceExit();
        return false;
    }
    
    private void volumeDo() {
        if (this.mMediaPlay == null || !this._ringMode || this._streamType == 2) {
            return;
        }
        try {
            final AudioManager audioManager = (AudioManager)this._context.getSystemService("audio");
            final int streamVolume = audioManager.getStreamVolume(this._streamType);
            final int streamMaxVolume = audioManager.getStreamMaxVolume(this._streamType);
            final int streamVolume2 = audioManager.getStreamVolume(2);
            final int streamMaxVolume2 = audioManager.getStreamMaxVolume(2);
            final int n = (int)(streamVolume2 * 1.0 / streamMaxVolume2 * streamMaxVolume);
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "TraeMediaPlay volumeDo currV:" + streamVolume + " maxV:" + streamMaxVolume + " currRV:" + streamVolume2 + " maxRV:" + streamMaxVolume2 + " setV:" + n);
            }
            int n2;
            if (n + 1 >= streamMaxVolume) {
                n2 = streamMaxVolume;
            }
            else {
                n2 = n + 1;
            }
            audioManager.setStreamVolume(this._streamType, n2, 0);
            this._prevVolume = streamVolume;
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "set stream volume failed." + ex.getMessage());
            }
        }
    }
    
    private void volumeUndo() {
        if (this.mMediaPlay == null || !this._ringMode || this._streamType == 2 || this._prevVolume == -1) {
            return;
        }
        try {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "TraeMediaPlay volumeUndo _prevVolume:" + this._prevVolume);
            }
            ((AudioManager)this._context.getSystemService("audio")).setStreamVolume(this._streamType, this._prevVolume, 0);
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "set stream volume failed." + ex.getMessage());
            }
        }
    }
    
    public interface OnCompletionListener
    {
        void onCompletion();
    }
}
