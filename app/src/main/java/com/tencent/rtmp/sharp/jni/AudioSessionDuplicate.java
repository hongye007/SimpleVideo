package com.tencent.rtmp.sharp.jni;

import java.util.concurrent.locks.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.audio.impl.*;
import android.content.*;

public class AudioSessionDuplicate
{
    private static final String TAG = "AudioSessionDuplicate";
    private static TraeAudioSession _as;
    private static ReentrantLock _prelock;
    private static Condition _precon;
    private static boolean _preDone;
    private static boolean usingJava;
    private static String[] mDeviceList;
    private static int playoutDeviceType;
    
    public static void DeleteAudioSessionDuplicate() {
        TXCLog.i("AudioSessionDuplicate", " DeleteAudioSessionDuplicate...");
        if (AudioSessionDuplicate._as != null) {
            AudioSessionDuplicate._as.voiceCallPostprocess();
            AudioSessionDuplicate._as.release();
            AudioSessionDuplicate._as = null;
        }
    }
    
    private static void onOutputChanage(final String s) {
        TXCLog.i("AudioSessionDuplicate", "device: " + s);
        if (s.equals("DEVICE_EARPHONE")) {
            AudioSessionDuplicate.playoutDeviceType = 1;
        }
        else if (s.equals("DEVICE_SPEAKERPHONE")) {
            AudioSessionDuplicate.playoutDeviceType = 2;
        }
        else if (s.equals("DEVICE_WIREDHEADSET")) {
            AudioSessionDuplicate.playoutDeviceType = 3;
        }
        else if (s.equals("DEVICE_BLUETOOTHHEADSET")) {
            AudioSessionDuplicate.playoutDeviceType = 4;
        }
        else {
            AudioSessionDuplicate.playoutDeviceType = 0;
        }
        TXCAudioEngineJNI.nativeSetPlayoutDevice(AudioSessionDuplicate.playoutDeviceType);
    }
    
    public static void NewAudioSessionDuplicate(final Context context) {
        TXCLog.i("AudioSessionDuplicate", " NewAudioSessionDuplicate...");
        if (AudioSessionDuplicate._as == null) {
            AudioSessionDuplicate._as = new TraeAudioSession(context, new TraeAudioSession.ITraeAudioCallback() {
                @Override
                public void onServiceStateUpdate(final boolean b) {
                    if (!b) {
                        try {
                            AudioSessionDuplicate._prelock.lock();
                            AudioSessionDuplicate._preDone = true;
                            if (QLog.isColorLevel()) {
                                QLog.e("TRAE", 2, "onServiceStateUpdate signalAll");
                            }
                            AudioSessionDuplicate._precon.signalAll();
                            AudioSessionDuplicate._prelock.unlock();
                        }
                        catch (Exception ex) {}
                    }
                }
                
                @Override
                public void onDeviceListUpdate(final String[] array, final String s, final String s2, final String s3) {
                    AudioSessionDuplicate.mDeviceList = array;
                    if (AudioSessionDuplicate.usingJava) {
                        onOutputChanage(s);
                    }
                }
                
                @Override
                public void onDeviceChangabledUpdate(final boolean b) {
                }
                
                @Override
                public void onGetDeviceListRes(final int n, final String[] array, final String s, final String s2, final String s3) {
                    AudioSessionDuplicate.mDeviceList = array;
                }
                
                @Override
                public void onConnectDeviceRes(final int n, final String s, final boolean b) {
                }
                
                @Override
                public void onIsDeviceChangabledRes(final int n, final boolean b) {
                }
                
                @Override
                public void onGetConnectedDeviceRes(final int n, final String s) {
                    if (n == 0) {
                        onOutputChanage(s);
                    }
                }
                
                @Override
                public void onGetConnectingDeviceRes(final int n, final String s) {
                }
                
                @Override
                public void onRingCompletion(final int n, final String s) {
                }
                
                @Override
                public void onStreamTypeUpdate(final int n) {
                }
                
                @Override
                public void onGetStreamTypeRes(final int n, final int n2) {
                }
                
                @Override
                public void onVoicecallPreprocessRes(final int n) {
                    try {
                        AudioSessionDuplicate._prelock.lock();
                        AudioSessionDuplicate._preDone = true;
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "onVoicecallPreprocessRes signalAll");
                        }
                        AudioSessionDuplicate._precon.signalAll();
                        AudioSessionDuplicate._prelock.unlock();
                    }
                    catch (Exception ex) {}
                }
                
                @Override
                public void onAudioRouteSwitchStart(final String s, final String s2) {
                }
                
                @Override
                public void onAudioRouteSwitchEnd(final String s, final long n) {
                }
            });
        }
    }
    
    static {
        AudioSessionDuplicate._as = null;
        AudioSessionDuplicate._prelock = new ReentrantLock();
        AudioSessionDuplicate._precon = AudioSessionDuplicate._prelock.newCondition();
        AudioSessionDuplicate._preDone = false;
        AudioSessionDuplicate.usingJava = true;
        AudioSessionDuplicate.mDeviceList = null;
        AudioSessionDuplicate.playoutDeviceType = 0;
    }
}
