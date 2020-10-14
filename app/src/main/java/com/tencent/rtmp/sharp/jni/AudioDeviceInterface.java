package com.tencent.rtmp.sharp.jni;

import android.content.*;
import java.nio.*;
import java.util.concurrent.locks.*;
import java.io.*;
import android.os.*;
import android.app.*;
import java.util.concurrent.*;
import android.hardware.*;
import android.media.*;
import android.annotation.*;

@TargetApi(16)
public class AudioDeviceInterface
{
    private AudioTrack _audioTrack;
    private AudioRecord _audioRecord;
    private int _streamType;
    private int _playSamplerate;
    private int _channelOutType;
    private int _audioSource;
    private int _deviceStat;
    private int _sceneMode;
    private int _sessionId;
    private Context _context;
    private int _modePolicy;
    private int _audioSourcePolicy;
    private int _audioStreamTypePolicy;
    private AudioManager _audioManager;
    private ByteBuffer _playBuffer;
    private ByteBuffer _recBuffer;
    private ByteBuffer _decBuffer0;
    private ByteBuffer _decBuffer1;
    private ByteBuffer _decBuffer2;
    private ByteBuffer _decBuffer3;
    private ByteBuffer _decBuffer4;
    private ByteBuffer _decBuffer5;
    private ByteBuffer _decBuffer6;
    private ByteBuffer _decBuffer7;
    private ByteBuffer _decBuffer8;
    private ByteBuffer _decBuffer9;
    private ByteBuffer _decBuffer10;
    private byte[] _tempBufPlay;
    private byte[] _tempBufRec;
    private boolean _doPlayInit;
    private boolean _doRecInit;
    private boolean _isRecording;
    private boolean _isPlaying;
    private int _bufferedRecSamples;
    private int _bufferedPlaySamples;
    private int _playPosition;
    private File _rec_dump;
    private File _play_dump;
    private FileOutputStream _rec_out;
    private FileOutputStream _play_out;
    private static boolean _dumpEnable;
    private static boolean _logEnable;
    private int nRecordLengthMs;
    private int nPlayLengthMs;
    private TraeAudioCodecList _traeAudioCodecList;
    private static String[] mDeviceList;
    private static VivoKTVHelper mVivoKTVHelper;
    private static boolean isSupportVivoKTVHelper;
    private TraeAudioSession _as;
    private String _connectedDev;
    private boolean _audioRouteChanged;
    private ReentrantLock _prelock;
    private Condition _precon;
    private boolean _preDone;
    private boolean usingJava;
    private int switchState;
    private TraeAudioSession _asAudioManager;
    public static final int OUTPUT_MODE_HEADSET = 0;
    public static final int OUTPUT_MODE_SPEAKER = 1;
    private static final FileFilter CPU_FILTER;
    
    public AudioDeviceInterface() {
        this._audioTrack = null;
        this._audioRecord = null;
        this._streamType = 0;
        this._playSamplerate = 8000;
        this._channelOutType = 4;
        this._audioSource = 0;
        this._deviceStat = 0;
        this._sceneMode = 0;
        this._sessionId = 0;
        this._context = null;
        this._modePolicy = -1;
        this._audioSourcePolicy = -1;
        this._audioStreamTypePolicy = -1;
        this._audioManager = null;
        this._doPlayInit = true;
        this._doRecInit = true;
        this._isRecording = false;
        this._isPlaying = false;
        this._bufferedRecSamples = 0;
        this._bufferedPlaySamples = 0;
        this._playPosition = 0;
        this._rec_dump = null;
        this._play_dump = null;
        this._rec_out = null;
        this._play_out = null;
        this.nRecordLengthMs = 0;
        this.nPlayLengthMs = 0;
        this._traeAudioCodecList = null;
        this._as = null;
        this._connectedDev = "DEVICE_NONE";
        this._audioRouteChanged = false;
        this._prelock = new ReentrantLock();
        this._precon = this._prelock.newCondition();
        this._preDone = false;
        this.usingJava = true;
        this.switchState = 0;
        this._asAudioManager = null;
        try {
            this._playBuffer = ByteBuffer.allocateDirect(1920);
            this._recBuffer = ByteBuffer.allocateDirect(1920);
            this._decBuffer0 = ByteBuffer.allocateDirect(3840);
            this._decBuffer1 = ByteBuffer.allocateDirect(3840);
            this._decBuffer2 = ByteBuffer.allocateDirect(3840);
            this._decBuffer3 = ByteBuffer.allocateDirect(3840);
            this._decBuffer4 = ByteBuffer.allocateDirect(3840);
            this._decBuffer5 = ByteBuffer.allocateDirect(3840);
            this._decBuffer6 = ByteBuffer.allocateDirect(3840);
            this._decBuffer7 = ByteBuffer.allocateDirect(3840);
            this._decBuffer8 = ByteBuffer.allocateDirect(3840);
            this._decBuffer9 = ByteBuffer.allocateDirect(3840);
            this._decBuffer10 = ByteBuffer.allocateDirect(3840);
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, ex.getMessage());
            }
        }
        this._tempBufPlay = new byte[1920];
        this._tempBufRec = new byte[1920];
        this._traeAudioCodecList = new TraeAudioCodecList();
        final int sdk_INT = Build.VERSION.SDK_INT;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "AudioDeviceInterface apiLevel:" + sdk_INT);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, " SDK_INT:" + Build.VERSION.SDK_INT);
        }
        if (sdk_INT <= 0) {}
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "manufacture:" + Build.MANUFACTURER);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "MODEL:" + Build.MODEL);
        }
    }
    
    public void setContext(final Context context) {
        this._context = context;
    }
    
    private int getLowlatencySamplerate() {
        if (this._context == null || Build.VERSION.SDK_INT < 9) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "getLowlatencySamplerate err, _context:" + this._context + " api:" + Build.VERSION.SDK_INT);
            }
            return 0;
        }
        final boolean hasSystemFeature = this._context.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "LOW_LATENCY:" + (hasSystemFeature ? "Y" : "N"));
        }
        if (Build.VERSION.SDK_INT < 17) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "API Level too low not support PROPERTY_OUTPUT_SAMPLE_RATE");
            }
            return 0;
        }
        if (QLog.isColorLevel()) {
            QLog.e("TRAE", 2, "getLowlatencySamplerate not support right now!");
        }
        return 0;
    }
    
    private int getLowlatencyFramesPerBuffer() {
        if (this._context == null || Build.VERSION.SDK_INT < 9) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "getLowlatencySamplerate err, _context:" + this._context + " api:" + Build.VERSION.SDK_INT);
            }
            return 0;
        }
        final boolean hasSystemFeature = this._context.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "LOW_LATENCY:" + (hasSystemFeature ? "Y" : "N"));
        }
        if (Build.VERSION.SDK_INT < 17) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "API Level too low not support PROPERTY_OUTPUT_SAMPLE_RATE");
            }
            return 0;
        }
        return 0;
    }
    
    @TargetApi(16)
    private int getAudioSessionId(final AudioRecord audioRecord) {
        return 0;
    }
    
    private int InitSetting(final int audioSourcePolicy, final int audioStreamTypePolicy, final int modePolicy, final int deviceStat, final int sceneMode) {
        this._audioSourcePolicy = audioSourcePolicy;
        this._audioStreamTypePolicy = audioStreamTypePolicy;
        this._modePolicy = modePolicy;
        this._deviceStat = deviceStat;
        this._sceneMode = sceneMode;
        if (this._deviceStat == 1 || this._deviceStat == 5 || this._deviceStat == 2 || this._deviceStat == 3) {
            TraeAudioManager.IsMusicScene = true;
        }
        else {
            TraeAudioManager.IsMusicScene = false;
        }
        if (this._sceneMode == 0 || this._sceneMode == 4) {
            TraeAudioManager.IsEarPhoneSupported = true;
        }
        else {
            TraeAudioManager.IsEarPhoneSupported = false;
        }
        TraeAudioManager.IsUpdateSceneFlag = true;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InitSetting: _audioSourcePolicy:" + this._audioSourcePolicy + " _audioStreamTypePolicy:" + this._audioStreamTypePolicy + " _modePolicy:" + this._modePolicy + " DeviceStat:" + deviceStat + " isSupportVivoKTVHelper:" + AudioDeviceInterface.isSupportVivoKTVHelper);
        }
        return 0;
    }
    
    private int InitRecording(final int n, final int n2) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InitRecording entry:" + n);
        }
        if (this._isRecording || this._audioRecord != null || n2 > 2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "InitRecording _isRecording:" + this._isRecording);
            }
            return -1;
        }
        int n3 = 16;
        if (n2 == 2) {
            n3 = 12;
        }
        final int minBufferSize = AudioRecord.getMinBufferSize(n, n3, 2);
        final int n4 = 20 * n * n2 * 2 / 1000;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InitRecording: min rec buf size is " + minBufferSize + " sr:" + this.getLowlatencySamplerate() + " fp" + this.getLowlatencyFramesPerBuffer() + " 20msFZ:" + n4);
        }
        this._bufferedRecSamples = 5 * n / 200;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "  rough rec delay set to " + this._bufferedRecSamples);
        }
        if (this._audioRecord != null) {
            this._audioRecord.release();
            this._audioRecord = null;
        }
        final int[] array = { 0, 1, 5, 0 };
        array[0] = TraeAudioManager.getAudioSource(this._audioSourcePolicy);
        int n5 = minBufferSize;
        for (int n6 = 0; n6 < array.length && this._audioRecord == null; ++n6) {
            this._audioSource = array[n6];
            for (int i = 1; i <= 2; ++i) {
                n5 = minBufferSize * i;
                if (n5 >= n4 * 4 || i >= 2) {
                    try {
                        this.nRecordLengthMs = n5 * 500 / (n * n2);
                        this._audioRecord = new AudioRecord(this._audioSource, n, n3, 2, n5);
                    }
                    catch (Exception ex) {
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, ex.getMessage() + " _audioRecord:" + this._audioRecord);
                        }
                        if (this._audioRecord != null) {
                            this._audioRecord.release();
                        }
                        this._audioRecord = null;
                        continue;
                    }
                    if (this._audioRecord.getState() == 1) {
                        break;
                    }
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "InitRecording:  rec not initialized,try agine,  minbufsize:" + n5 + " sr:" + n + " as:" + this._audioSource);
                    }
                    this._audioRecord.release();
                    this._audioRecord = null;
                }
            }
        }
        if (this._audioRecord == null) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "InitRecording fail!!!");
            }
            return -1;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, " [Config] InitRecording: audioSession:" + this._sessionId + " audioSource:" + this._audioSource + " rec sample rate set to " + n + " recBufSize:" + n5 + " nRecordLengthMs:" + this.nRecordLengthMs);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InitRecording exit");
        }
        return this._bufferedRecSamples;
    }
    
    private int InitPlayback(final int playSamplerate, final int n) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InitPlayback entry: sampleRate " + playSamplerate);
        }
        if (this._isPlaying || this._audioTrack != null || n > 2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "InitPlayback _isPlaying:" + this._isPlaying);
            }
            return -1;
        }
        if (this._audioManager == null) {
            try {
                this._audioManager = (AudioManager)this._context.getSystemService("audio");
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, ex.getMessage());
                }
                return -1;
            }
        }
        if (n == 2) {
            this._channelOutType = 12;
        }
        else {
            this._channelOutType = 4;
        }
        this._playSamplerate = playSamplerate;
        final int minBufferSize = AudioTrack.getMinBufferSize(playSamplerate, this._channelOutType, 2);
        if (this._channelOutType == 12) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "InitPlayback, _channelOutType stero");
            }
            else if (this._channelOutType == 4 && QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "InitPlayback, _channelOutType Mono");
            }
        }
        int n2 = 20 * playSamplerate * 1 * 2 / 1000;
        if (this._channelOutType == 12) {
            n2 *= 2;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InitPlayback: minPlayBufSize:" + minBufferSize + " 20msFz:" + n2);
        }
        this._bufferedPlaySamples = 0;
        if (this._audioTrack != null) {
            this._audioTrack.release();
            this._audioTrack = null;
        }
        final int[] array = { 0, 0, 3, 1 };
        this._streamType = TraeAudioManager.getAudioStreamType(this._audioStreamTypePolicy);
        if (this._audioRouteChanged) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "_audioRouteChanged:" + this._audioRouteChanged + " _streamType:" + this._streamType);
            }
            if (this._audioManager.getMode() == 0 && this._connectedDev.equals("DEVICE_SPEAKERPHONE")) {
                this._streamType = 3;
            }
            else {
                this._streamType = 0;
            }
            this._audioRouteChanged = false;
        }
        array[0] = this._streamType;
        int n3 = minBufferSize;
        for (int n4 = 0; n4 < array.length && this._audioTrack == null; ++n4) {
            this._streamType = array[n4];
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "InitPlayback: min play buf size is " + minBufferSize + " hw_sr:" + AudioTrack.getNativeOutputSampleRate(this._streamType));
            }
            for (int i = 1; i <= 2; ++i) {
                n3 = minBufferSize * i;
                if (n3 >= n2 * 4 || i >= 2) {
                    try {
                        this.nPlayLengthMs = n3 * 500 / (playSamplerate * n);
                        this._audioTrack = new AudioTrack(this._streamType, this._playSamplerate, this._channelOutType, 2, n3, 1);
                    }
                    catch (Exception ex2) {
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, ex2.getMessage() + " _audioTrack:" + this._audioTrack);
                        }
                        if (this._audioTrack != null) {
                            this._audioTrack.release();
                        }
                        this._audioTrack = null;
                        continue;
                    }
                    if (this._audioTrack.getState() == 1) {
                        break;
                    }
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "InitPlayback: play not initialized playBufSize:" + n3 + " sr:" + this._playSamplerate);
                    }
                    this._audioTrack.release();
                    this._audioTrack = null;
                }
            }
        }
        if (this._audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "InitPlayback fail!!!");
            }
            return -1;
        }
        if (this._as != null && this._audioManager != null) {
            this._as.voiceCallAudioParamChanged(this._audioManager.getMode(), this._streamType);
        }
        this._playPosition = this._audioTrack.getPlaybackHeadPosition();
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InitPlayback exit: streamType:" + this._streamType + " samplerate:" + this._playSamplerate + " _playPosition:" + this._playPosition + " playBufSize:" + n3 + " nPlayLengthMs:" + this.nPlayLengthMs);
        }
        TraeAudioManager.forceVolumeControlStream(this._audioManager, this._connectedDev.equals("DEVICE_BLUETOOTHHEADSET") ? 6 : this._audioTrack.getStreamType());
        return 0;
    }
    
    private int getPlayRecordSysBufferMs() {
        return (this.nRecordLengthMs + this.nPlayLengthMs) * 2;
    }
    
    private String getDumpFilePath(final String s, final int n) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "manufacture:" + Build.MANUFACTURER);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "MODEL:" + Build.MODEL);
        }
        if (this._context == null) {
            return null;
        }
        final File externalFilesDir = this._context.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            return null;
        }
        final String string = externalFilesDir.getPath() + "/MF-" + Build.MANUFACTURER + "-M-" + Build.MODEL + "-as-" + TraeAudioManager.getAudioSource(this._audioSourcePolicy) + "-st-" + this._streamType + "-m-" + n + "-" + s;
        final File file = new File(string);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "dump:" + string);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "dump replace:" + string.replace(" ", "_"));
        }
        return string.replace(" ", "_");
    }
    
    private int StartRecording() {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StartRecording entry");
        }
        if (this._isRecording) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "StartRecording _isRecording:" + this._isRecording);
            }
            return -1;
        }
        if (this._audioRecord == null) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "StartRecording _audioRecord:" + this._audioRecord);
            }
            return -1;
        }
        try {
            this._audioRecord.startRecording();
        }
        catch (IllegalStateException ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "StartRecording fail");
            }
            ex.printStackTrace();
            return -1;
        }
        if (AudioDeviceInterface._dumpEnable) {
            this._rec_dump = new File(this.getDumpFilePath("jnirecord.pcm", (this._audioManager != null) ? this._audioManager.getMode() : -1));
            try {
                this._rec_out = new FileOutputStream(this._rec_dump);
            }
            catch (FileNotFoundException ex2) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "open rec dump file failed.");
                }
            }
        }
        this._isRecording = true;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StartRecording ok");
        }
        return 0;
    }
    
    private int StartPlayback() {
        if (this._isPlaying) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "StartPlayback _isPlaying");
            }
            return -1;
        }
        if (this._audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "StartPlayback _audioTrack:" + this._audioTrack);
            }
            return -1;
        }
        try {
            this._audioTrack.play();
        }
        catch (IllegalStateException ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "StartPlayback fail");
            }
            return -1;
        }
        if (AudioDeviceInterface._dumpEnable) {
            this._play_dump = new File(this.getDumpFilePath("jniplay.pcm", (this._audioManager != null) ? this._audioManager.getMode() : -1));
            try {
                this._play_out = new FileOutputStream(this._play_dump);
            }
            catch (FileNotFoundException ex2) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "open play dump file failed.");
                }
            }
        }
        this._isPlaying = true;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StartPlayback ok");
        }
        return 0;
    }
    
    private int StopRecording() {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StopRecording entry");
        }
        if (this._audioRecord == null) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "UnintRecord:" + this._audioRecord);
            }
            return -1;
        }
        if (this._audioRecord.getRecordingState() == 3) {
            try {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "StopRecording stop... state:" + this._audioRecord.getRecordingState());
                }
                this._audioRecord.stop();
            }
            catch (IllegalStateException ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "StopRecording  err");
                }
                return -1;
            }
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StopRecording releaseing... state:" + this._audioRecord.getRecordingState());
        }
        this._audioRecord.release();
        this._audioRecord = null;
        this._isRecording = false;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StopRecording exit ok");
        }
        return 0;
    }
    
    private int StopPlayback() {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StopPlayback entry _isPlaying:" + this._isPlaying);
        }
        if (this._audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "StopPlayback _isPlaying:" + this._isPlaying + " " + this._audioTrack);
            }
            return -1;
        }
        if (this._audioTrack.getPlayState() == 3) {
            try {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "StopPlayback stoping...");
                }
                this._audioTrack.stop();
            }
            catch (IllegalStateException ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "StopPlayback err");
                }
                return -1;
            }
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "StopPlayback flushing... state:" + this._audioTrack.getPlayState());
            }
            this._audioTrack.flush();
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StopPlayback releaseing... state:" + this._audioTrack.getPlayState());
        }
        this._audioTrack.release();
        this._audioTrack = null;
        this._isPlaying = false;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "StopPlayback exit ok");
        }
        return 0;
    }
    
    private int PlayAudio(final int n) {
        int write = 0;
        if (!this._isPlaying | this._audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "PlayAudio: _isPlaying " + this._isPlaying + " " + this._audioTrack);
            }
            return -1;
        }
        try {
            if (this._audioTrack == null) {
                return -2;
            }
            if (this._doPlayInit) {
                try {
                    Process.setThreadPriority(-19);
                    Thread.currentThread().setName("TRAEAudioPlay");
                }
                catch (Exception ex) {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "Set play thread priority failed: " + ex.getMessage());
                    }
                }
                this._doPlayInit = false;
            }
            if (AudioDeviceInterface._dumpEnable && this._play_out != null) {
                try {
                    this._play_out.write(this._tempBufPlay, 0, write);
                }
                catch (IOException ex4) {
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "write data failed.");
                    }
                }
            }
            int n2;
            if (!this._audioRouteChanged) {
                n2 = 0;
            }
            else {
                if (this._audioManager == null && this._context != null) {
                    this._audioManager = (AudioManager)this._context.getSystemService("audio");
                }
                if (this._audioManager != null && this._audioManager.getMode() == 0 && this._connectedDev.equals("DEVICE_SPEAKERPHONE")) {
                    this._streamType = 3;
                }
                else {
                    this._streamType = 0;
                }
                n2 = ((this._streamType != this._audioTrack.getStreamType()) ? 1 : 0);
                this._audioRouteChanged = false;
            }
            this._playBuffer.get(this._tempBufPlay);
            if (n2 != 0) {
                write = n;
                this._playBuffer.rewind();
                final long elapsedRealtime = SystemClock.elapsedRealtime();
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, " track resting: _streamType:" + this._streamType + " at.st:" + this._audioTrack.getStreamType());
                }
                if (this._audioTrack.getPlayState() == 3) {
                    try {
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, "StopPlayback stoping...");
                        }
                        this._audioTrack.stop();
                        this._audioTrack.flush();
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, "StopPlayback flushing... state:" + this._audioTrack.getPlayState());
                        }
                        this._audioTrack.release();
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, "StopPlayback releaseing... state:" + this._audioTrack.getPlayState());
                        }
                        this._audioTrack = null;
                    }
                    catch (IllegalStateException ex5) {
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "StopPlayback err");
                        }
                    }
                }
                final int minBufferSize = AudioTrack.getMinBufferSize(this._playSamplerate, this._channelOutType, 2);
                final int[] array = { 0, 0, 3, 1 };
                array[0] = this._streamType;
                int n3 = 20 * this._playSamplerate * 1 * 2 / 1000;
                if (this._channelOutType == 12) {
                    n3 *= 2;
                }
                for (int n4 = 0; n4 < array.length && this._audioTrack == null; ++n4) {
                    this._streamType = array[n4];
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "InitPlayback: min play buf size is " + minBufferSize + " hw_sr:" + AudioTrack.getNativeOutputSampleRate(this._streamType));
                    }
                    for (int i = 1; i <= 2; ++i) {
                        final int n5 = minBufferSize * i;
                        if (n5 >= n3 * 4 || i >= 2) {
                            try {
                                this._audioTrack = new AudioTrack(this._streamType, this._playSamplerate, this._channelOutType, 2, n5, 1);
                            }
                            catch (Exception ex2) {
                                if (QLog.isColorLevel()) {
                                    QLog.w("TRAE", 2, ex2.getMessage() + " _audioTrack:" + this._audioTrack);
                                }
                                if (this._audioTrack != null) {
                                    this._audioTrack.release();
                                }
                                this._audioTrack = null;
                                continue;
                            }
                            if (QLog.isColorLevel()) {
                                QLog.w("TRAE", 2, " _audioTrack:" + this._audioTrack);
                            }
                            if (this._audioTrack.getState() == 1) {
                                break;
                            }
                            if (QLog.isColorLevel()) {
                                QLog.w("TRAE", 2, "InitPlayback: play not initialized playBufSize:" + n5 + " sr:" + this._playSamplerate);
                            }
                            this._audioTrack.release();
                            this._audioTrack = null;
                        }
                    }
                }
                if (this._audioTrack != null) {
                    try {
                        this._audioTrack.play();
                        this._as.voiceCallAudioParamChanged(this._audioManager.getMode(), this._streamType);
                        TraeAudioManager.forceVolumeControlStream(this._audioManager, this._connectedDev.equals("DEVICE_BLUETOOTHHEADSET") ? 6 : this._audioTrack.getStreamType());
                    }
                    catch (Exception ex6) {
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "start play failed.");
                        }
                    }
                }
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "  track reset used:" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms");
                }
            }
            else {
                write = this._audioTrack.write(this._tempBufPlay, 0, n);
                this._playBuffer.rewind();
                if (write < 0) {
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "Could not write data from sc (write = " + write + ", length = " + n + ")");
                    }
                    return -1;
                }
                if (write != n && QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "Could not write all data from sc (write = " + write + ", length = " + n + ")");
                }
                this._bufferedPlaySamples += write >> 1;
                final int playbackHeadPosition = this._audioTrack.getPlaybackHeadPosition();
                if (playbackHeadPosition < this._playPosition) {
                    this._playPosition = 0;
                }
                this._bufferedPlaySamples -= playbackHeadPosition - this._playPosition;
                this._playPosition = playbackHeadPosition;
                if (!this._isRecording) {
                    final int bufferedPlaySamples = this._bufferedPlaySamples;
                }
            }
        }
        catch (Exception ex3) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "PlayAudio Exception: " + ex3.getMessage());
            }
        }
        return write;
    }
    
    private int OpenslesNeedResetAudioTrack(final boolean b) {
        try {
            if (!TraeAudioManager.isCloseSystemAPM(this._modePolicy)) {
                return -1;
            }
            if (this._audioRouteChanged || b) {
                if (this._audioManager == null && this._context != null) {
                    this._audioManager = (AudioManager)this._context.getSystemService("audio");
                }
                if (this._audioManager == null) {
                    return 0;
                }
                if (this._audioManager.getMode() == 0 && this._connectedDev.equals("DEVICE_SPEAKERPHONE")) {
                    this._audioStreamTypePolicy = 3;
                }
                else {
                    this._audioStreamTypePolicy = 0;
                }
                this._audioRouteChanged = false;
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "PlayAudio Exception: " + ex.getMessage());
            }
        }
        return this._audioStreamTypePolicy;
    }
    
    private int RecordAudio(final int n) {
        int read = 0;
        if (!this._isRecording) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "RecordAudio: _isRecording " + this._isRecording);
            }
            return -1;
        }
        try {
            if (this._audioRecord == null) {
                return -2;
            }
            if (this._doRecInit) {
                try {
                    Process.setThreadPriority(-19);
                    Thread.currentThread().setName("TRAEAudioRecord");
                }
                catch (Exception ex) {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "Set rec thread priority failed: " + ex.getMessage());
                    }
                }
                this._doRecInit = false;
            }
            this._recBuffer.rewind();
            read = this._audioRecord.read(this._tempBufRec, 0, n);
            if (read < 0) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "Could not read data from sc (read = " + read + ", length = " + n + ")");
                }
                return -1;
            }
            this._recBuffer.put(this._tempBufRec, 0, read);
            if (AudioDeviceInterface._dumpEnable && this._rec_out != null) {
                try {
                    this._rec_out.write(this._tempBufRec, 0, read);
                }
                catch (IOException ex3) {
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "write rec buffer failed.");
                    }
                }
            }
            if (read != n) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "Could not read all data from sc (read = " + read + ", length = " + n + ")");
                }
                return -1;
            }
        }
        catch (Exception ex2) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "RecordAudio Exception: " + ex2.getMessage());
            }
        }
        return read;
    }
    
    private int SetPlayoutVolume(final int n) {
        if (this._audioManager == null && this._context != null) {
            this._audioManager = (AudioManager)this._context.getSystemService("audio");
        }
        int n2 = -1;
        if (this._audioManager != null) {
            this._audioManager.setStreamVolume(0, n, 0);
            n2 = 0;
        }
        return n2;
    }
    
    public int GetPlayoutVolume() {
        int streamVolume = -1;
        try {
            if (this._audioManager == null && this._context != null) {
                this._audioManager = (AudioManager)this._context.getSystemService("audio");
            }
            if (this._audioManager != null) {
                streamVolume = this._audioManager.getStreamVolume(this._streamType);
            }
            else {
                streamVolume = -1;
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE GetPlayoutVolume", 2, ex.getMessage());
            }
        }
        return streamVolume;
    }
    
    public static String getTraceInfo() {
        final StringBuffer sb = new StringBuffer();
        final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        final int length = stackTrace.length;
        sb.append("").append(stackTrace[2].getClassName()).append(".").append(stackTrace[2].getMethodName()).append(": ").append(stackTrace[2].getLineNumber());
        return sb.toString();
    }
    
    public static final void LogTraceEntry(final String s) {
        if (!AudioDeviceInterface._logEnable) {
            return;
        }
        final String string = getTraceInfo() + " entry:" + s;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, string);
        }
    }
    
    public static final void LogTraceExit() {
        if (!AudioDeviceInterface._logEnable) {
            return;
        }
        final String string = getTraceInfo() + " exit";
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, string);
        }
    }
    
    private void onOutputChanage(final String s) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, " onOutputChanage:" + s);
        }
        this.setAudioRouteSwitchState(s);
        if (!TraeAudioManager.isCloseSystemAPM(this._modePolicy)) {
            return;
        }
        if (this._deviceStat == 1 || this._deviceStat == 5 || this._deviceStat == 2 || this._deviceStat == 3) {
            return;
        }
        this._connectedDev = s;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, " onOutputChanage:" + s + ((this._audioManager == null) ? " am==null" : (" mode:" + this._audioManager.getMode())) + " st:" + this._streamType + ((this._audioTrack == null) ? "_audioTrack==null" : (" at.st:" + this._audioTrack.getStreamType())));
        }
        try {
            if (this._audioManager == null) {
                this._audioManager = (AudioManager)this._context.getSystemService("audio");
            }
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " curr mode:" + s + ((this._audioManager == null) ? "am==null" : (" mode:" + this._audioManager.getMode())));
            }
            if (this._audioManager != null && this._connectedDev.equals("DEVICE_SPEAKERPHONE")) {
                this._audioManager.setMode(0);
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, ex.getMessage());
            }
        }
        this._audioRouteChanged = true;
    }
    
    public int getMode() {
        int mode = -1;
        try {
            if (this._audioManager == null && this._context != null) {
                this._audioManager = (AudioManager)this._context.getSystemService("audio");
            }
            if (this._audioManager != null) {
                mode = this._audioManager.getMode();
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE getMode", 2, ex.getMessage());
            }
        }
        return mode;
    }
    
    public int isBackground() {
        if (this._context == null) {
            return 0;
        }
        try {
            final ActivityManager activityManager = (ActivityManager)this._context.getSystemService("activity");
            if (activityManager.getRunningTasks(1) == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "running task is null, ams is abnormal!!!");
                }
                return 0;
            }
            final ActivityManager.RunningTaskInfo runningTaskInfo = activityManager.getRunningTasks(1).get(0);
            if (runningTaskInfo == null || runningTaskInfo.topActivity == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "failed to get RunningTaskInfo");
                }
                return 0;
            }
            if (!runningTaskInfo.topActivity.getPackageName().equals(this._context.getPackageName())) {
                return 1;
            }
            return 0;
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE isBackground", 2, ex.getMessage());
            }
            return 0;
        }
    }
    
    public int call_preprocess() {
        LogTraceEntry("");
        this.switchState = 0;
        this._streamType = TraeAudioManager.getAudioStreamType(this._audioStreamTypePolicy);
        if (this._as == null) {
            this._as = new TraeAudioSession(this._context, new TraeAudioSession.ITraeAudioCallback() {
                @Override
                public void onServiceStateUpdate(final boolean b) {
                    if (!b) {
                        try {
                            AudioDeviceInterface.this._prelock.lock();
                            AudioDeviceInterface.this._preDone = true;
                            if (QLog.isColorLevel()) {
                                QLog.e("TRAE", 2, "onServiceStateUpdate signalAll");
                            }
                            AudioDeviceInterface.this._precon.signalAll();
                            AudioDeviceInterface.this._prelock.unlock();
                        }
                        catch (Exception ex) {}
                    }
                }
                
                @Override
                public void onDeviceListUpdate(final String[] array, final String s, final String s2, final String s3) {
                    AudioDeviceInterface.mDeviceList = array;
                    if (AudioDeviceInterface.this.usingJava) {
                        AudioDeviceInterface.this.onOutputChanage(s);
                    }
                }
                
                @Override
                public void onDeviceChangabledUpdate(final boolean b) {
                }
                
                @Override
                public void onGetDeviceListRes(final int n, final String[] array, final String s, final String s2, final String s3) {
                    AudioDeviceInterface.mDeviceList = array;
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
                        AudioDeviceInterface.this.onOutputChanage(s);
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
                        AudioDeviceInterface.this._prelock.lock();
                        AudioDeviceInterface.this._preDone = true;
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "onVoicecallPreprocessRes signalAll");
                        }
                        AudioDeviceInterface.this._precon.signalAll();
                        AudioDeviceInterface.this._prelock.unlock();
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
        this._preDone = false;
        if (this._as != null) {
            this._prelock.lock();
            try {
                if (this._audioManager == null) {
                    this._audioManager = (AudioManager)this._context.getSystemService("audio");
                }
                if (this._audioManager != null) {
                    if (this._audioManager.getMode() == 2) {
                        int n = 5;
                        while (n-- > 0 && this._audioManager.getMode() == 2) {
                            if (QLog.isColorLevel()) {
                                QLog.e("TRAE", 2, "call_preprocess waiting...  mode:" + this._audioManager.getMode());
                            }
                            Thread.sleep(500L);
                        }
                    }
                    if (this._audioManager.isMicrophoneMute()) {
                        this._audioManager.setMicrophoneMute(false);
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "media call_preprocess setMicrophoneMute false");
                        }
                    }
                }
                this._as.voiceCallPreprocess(this._modePolicy, this._streamType);
                this._as.connectHighestPriorityDevice();
                try {
                    int n2 = 7;
                    while (n2-- > 0 && !this._preDone) {
                        this._precon.await(1L, TimeUnit.SECONDS);
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "call_preprocess waiting...  as:" + this._as);
                        }
                    }
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "call_preprocess done!");
                    }
                }
                catch (InterruptedException ex) {}
                this._as.getConnectedDevice();
            }
            catch (InterruptedException ex2) {}
            finally {
                this._prelock.unlock();
            }
        }
        LogTraceExit();
        return 0;
    }
    
    public int call_postprocess() {
        LogTraceEntry("");
        this.switchState = 0;
        if (this._as != null) {
            this._as.voiceCallPostprocess();
            this._as.release();
            this._as = null;
        }
        TraeAudioManager.IsUpdateSceneFlag = false;
        LogTraceExit();
        return 0;
    }
    
    public int call_preprocess_media() {
        LogTraceEntry("");
        this.switchState = 0;
        if (AudioDeviceInterface.mVivoKTVHelper != null && AudioDeviceInterface.isSupportVivoKTVHelper) {
            AudioDeviceInterface.mVivoKTVHelper.openKTVDevice();
            AudioDeviceInterface.mVivoKTVHelper.setPreModeParam(1);
            AudioDeviceInterface.mVivoKTVHelper.setPlayFeedbackParam(1);
            AudioDeviceInterface.mVivoKTVHelper.setPlayFeedbackParam(0);
        }
        if (this._as == null) {
            this._as = new TraeAudioSession(this._context, new TraeAudioSession.ITraeAudioCallback() {
                @Override
                public void onServiceStateUpdate(final boolean b) {
                }
                
                @Override
                public void onDeviceListUpdate(final String[] array, final String s, final String s2, final String s3) {
                    AudioDeviceInterface.mDeviceList = array;
                    if (AudioDeviceInterface.this.usingJava) {
                        AudioDeviceInterface.this.onOutputChanage(s);
                    }
                }
                
                @Override
                public void onDeviceChangabledUpdate(final boolean b) {
                }
                
                @Override
                public void onGetDeviceListRes(final int n, final String[] array, final String s, final String s2, final String s3) {
                    AudioDeviceInterface.mDeviceList = array;
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
                        AudioDeviceInterface.this.onOutputChanage(s);
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
                }
                
                @Override
                public void onAudioRouteSwitchStart(final String s, final String s2) {
                }
                
                @Override
                public void onAudioRouteSwitchEnd(final String s, final long n) {
                }
            });
        }
        if (this._as != null) {
            try {
                if (this._audioManager == null) {
                    this._audioManager = (AudioManager)this._context.getSystemService("audio");
                }
                if (this._audioManager != null) {
                    if (this._audioManager.getMode() == 2) {
                        int n = 5;
                        while (n-- > 0 && this._audioManager.getMode() == 2) {
                            if (QLog.isColorLevel()) {
                                QLog.e("TRAE", 2, "media call_preprocess_media waiting...  mode:" + this._audioManager.getMode());
                            }
                            Thread.sleep(500L);
                        }
                    }
                    if (this._audioManager.getMode() != 0) {
                        this._audioManager.setMode(0);
                    }
                    if (this._audioManager.isMicrophoneMute()) {
                        this._audioManager.setMicrophoneMute(false);
                        if (QLog.isColorLevel()) {
                            QLog.e("TRAE", 2, "media call_preprocess_media setMicrophoneMute false");
                        }
                    }
                }
                this._as.connectHighestPriorityDevice();
                this._as.getConnectedDevice();
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "call_preprocess_media done!");
                }
            }
            catch (InterruptedException ex) {}
        }
        LogTraceExit();
        return 0;
    }
    
    public int call_postprocess_media() {
        LogTraceEntry("");
        this.switchState = 0;
        if (this._as != null) {
            this._as.release();
            this._as = null;
        }
        TraeAudioManager.IsUpdateSceneFlag = false;
        if (AudioDeviceInterface.mVivoKTVHelper != null && AudioDeviceInterface.isSupportVivoKTVHelper) {
            AudioDeviceInterface.mVivoKTVHelper.closeKTVDevice();
        }
        LogTraceExit();
        return 0;
    }
    
    public void setJavaInterface(final int n) {
        if (n == 0) {
            this.usingJava = false;
        }
        else {
            this.usingJava = true;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "setJavaInterface flg:" + n);
        }
    }
    
    private void setAudioRouteSwitchState(final String s) {
        if (s.equals("DEVICE_EARPHONE")) {
            this.switchState = 1;
        }
        else if (s.equals("DEVICE_SPEAKERPHONE")) {
            this.switchState = 2;
        }
        else if (s.equals("DEVICE_WIREDHEADSET")) {
            this.switchState = 3;
        }
        else if (s.equals("DEVICE_BLUETOOTHHEADSET")) {
            this.switchState = 4;
        }
        else {
            this.switchState = 0;
        }
    }
    
    public int getAudioRouteSwitchState() {
        return this.switchState;
    }
    
    private void initTRAEAudioManager() {
        if (this._context != null) {
            TraeAudioManager.init(this._context);
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "initTRAEAudioManager , TraeAudioSession create");
            }
            if (this._asAudioManager == null) {
                this._asAudioManager = new TraeAudioSession(this._context, new TraeAudioSession.ITraeAudioCallback() {
                    @Override
                    public void onServiceStateUpdate(final boolean b) {
                    }
                    
                    @Override
                    public void onDeviceListUpdate(final String[] array, final String s, final String s2, final String s3) {
                        AudioDeviceInterface.mDeviceList = array;
                        if (AudioDeviceInterface.this.usingJava) {
                            AudioDeviceInterface.this.onOutputChanage(s);
                        }
                    }
                    
                    @Override
                    public void onDeviceChangabledUpdate(final boolean b) {
                    }
                    
                    @Override
                    public void onGetDeviceListRes(final int n, final String[] array, final String s, final String s2, final String s3) {
                        AudioDeviceInterface.mDeviceList = array;
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
                            AudioDeviceInterface.this.onOutputChanage(s);
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
                    }
                    
                    @Override
                    public void onAudioRouteSwitchStart(final String s, final String s2) {
                    }
                    
                    @Override
                    public void onAudioRouteSwitchEnd(final String s, final long n) {
                    }
                });
            }
            this._asAudioManager.startService("DEVICE_EARPHONE;DEVICE_SPEAKERPHONE;DEVICE_BLUETOOTHHEADSET;DEVICE_WIREDHEADSET;");
        }
    }
    
    private int startService(final String s) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "initTRAEAudioManager , TraeAudioSession startService: " + this._asAudioManager + " deviceConfig:" + s);
        }
        if (this._asAudioManager != null) {
            return this._asAudioManager.startService(s);
        }
        return -1;
    }
    
    private int stopService() {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "initTRAEAudioManager , TraeAudioSession stopService: " + this._asAudioManager);
        }
        if (this._asAudioManager != null) {
            return this._asAudioManager.stopService();
        }
        return -1;
    }
    
    private int SetAudioOutputMode(final int n) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "TraeAudioSession SetAudioOutputMode: " + n);
        }
        if (0 == n) {
            if (null == AudioDeviceInterface.mDeviceList || null == this._asAudioManager) {
                return -1;
            }
            int i = 0;
            do {
                for (int n2 = 0; n2 < AudioDeviceInterface.mDeviceList.length && i == 0; ++n2) {
                    if ("DEVICE_WIREDHEADSET".equals(AudioDeviceInterface.mDeviceList[n2])) {
                        this._asAudioManager.connectDevice("DEVICE_WIREDHEADSET");
                        i = 1;
                        break;
                    }
                }
                for (int n3 = 0; n3 < AudioDeviceInterface.mDeviceList.length && i == 0; ++n3) {
                    if ("DEVICE_BLUETOOTHHEADSET".equals(AudioDeviceInterface.mDeviceList[n3])) {
                        this._asAudioManager.connectDevice("DEVICE_BLUETOOTHHEADSET");
                        i = 1;
                        break;
                    }
                }
                for (int n4 = 0; n4 < AudioDeviceInterface.mDeviceList.length && i == 0; ++n4) {
                    if ("DEVICE_EARPHONE".equals(AudioDeviceInterface.mDeviceList[n4])) {
                        this._asAudioManager.connectDevice("DEVICE_EARPHONE");
                        i = 1;
                        break;
                    }
                }
            } while (i == 0);
            return 0;
        }
        else {
            if (1 != n) {
                return -1;
            }
            if (this._asAudioManager == null) {
                return -1;
            }
            this._asAudioManager.connectDevice("DEVICE_SPEAKERPHONE");
            return 0;
        }
    }
    
    private int getAndroidSdkVersion() {
        return Build.VERSION.SDK_INT;
    }
    
    public int hasLightSensorManager() {
        if (this._context == null) {
            return 1;
        }
        Sensor defaultSensor;
        try {
            defaultSensor = ((SensorManager)this._context.getSystemService("sensor")).getDefaultSensor(5);
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, ex.getMessage());
            }
            return 1;
        }
        if (null == defaultSensor) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "not hasLightSensorManager null == sensor8");
            }
            return 0;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "hasLightSensorManager");
        }
        return 1;
    }
    
    static boolean isHardcodeOpenSles() {
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            if (Build.MODEL.equals("MI 5")) {
                return true;
            }
            if (Build.MODEL.equals("MI 5s")) {
                return true;
            }
            if (Build.MODEL.equals("MI 5s Plus")) {
                return true;
            }
        }
        else if (Build.MANUFACTURER.equals("samsung") && Build.MODEL.equals("SM-G9350")) {
            return true;
        }
        return false;
    }
    
    public int getNumberOfCPUCores() {
        if (Build.VERSION.SDK_INT <= 10) {
            return 1;
        }
        int length;
        try {
            length = new File("/sys/devices/system/cpu/").listFiles(AudioDeviceInterface.CPU_FILTER).length;
        }
        catch (SecurityException ex) {
            length = -1;
        }
        catch (NullPointerException ex2) {
            length = -1;
        }
        return length;
    }
    
    private int isSupportLowLatency() {
        if (isHardcodeOpenSles()) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "hardcode FEATURE_AUDIO_LOW_LATENCY: " + Build.MANUFACTURER + "_" + Build.MODEL);
            }
            return 1;
        }
        return 0;
    }
    
    private int isSupportVivoKTVHelper() {
        if (this._context != null) {
            AudioDeviceInterface.mVivoKTVHelper = VivoKTVHelper.getInstance(this._context);
            if (AudioDeviceInterface.mVivoKTVHelper != null) {
                AudioDeviceInterface.isSupportVivoKTVHelper = AudioDeviceInterface.mVivoKTVHelper.isDeviceSupportKaraoke();
            }
        }
        return AudioDeviceInterface.isSupportVivoKTVHelper ? 1 : 0;
    }
    
    private int EnableVivoKTVLoopback(final int playFeedbackParam) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "EnableVivoKTVLoopback: " + playFeedbackParam + " isSupportVivoKTVHelper:" + AudioDeviceInterface.isSupportVivoKTVHelper + " mVivoKTVHelper:" + AudioDeviceInterface.mVivoKTVHelper);
        }
        if (AudioDeviceInterface.mVivoKTVHelper != null && AudioDeviceInterface.isSupportVivoKTVHelper) {
            AudioDeviceInterface.mVivoKTVHelper.setPlayFeedbackParam(playFeedbackParam);
            return 0;
        }
        return -1;
    }
    
    private int isVivoKTVLoopback() {
        if (AudioDeviceInterface.mVivoKTVHelper != null && AudioDeviceInterface.isSupportVivoKTVHelper) {
            return AudioDeviceInterface.mVivoKTVHelper.getPlayFeedbackParam();
        }
        return 0;
    }
    
    private void uninitTRAEAudioManager() {
        if (this._context != null) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "uninitTRAEAudioManager , stopService");
            }
            if (this._asAudioManager != null) {
                this._asAudioManager.stopService();
                this._asAudioManager.release();
                this._asAudioManager = null;
            }
        }
        else if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "uninitTRAEAudioManager , context null");
        }
    }
    
    private int OpenMp3File(final String ioPath, final int index, final int n) {
        if (this._traeAudioCodecList.find(index) != null) {
            return -1;
        }
        final TraeAudioCodecList.CodecInfo add = this._traeAudioCodecList.add(index);
        add.audioDecoder.setIOPath(ioPath);
        add.audioDecoder.setIndex(index);
        final int prepare = add.audioDecoder.prepare(n);
        if (prepare != 0) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "openFile mp3 Failed!!!");
            }
            add.audioDecoder.release();
            add.audioDecoder = null;
            this._traeAudioCodecList.remove(index);
            return prepare;
        }
        return 0;
    }
    
    private ByteBuffer getDecBuffer(final int n) {
        switch (n) {
            case 0: {
                return this._decBuffer0;
            }
            case 1: {
                return this._decBuffer1;
            }
            case 2: {
                return this._decBuffer2;
            }
            case 3: {
                return this._decBuffer3;
            }
            case 4: {
                return this._decBuffer4;
            }
            case 5: {
                return this._decBuffer5;
            }
            case 6: {
                return this._decBuffer6;
            }
            case 7: {
                return this._decBuffer7;
            }
            case 8: {
                return this._decBuffer8;
            }
            case 9: {
                return this._decBuffer9;
            }
            case 10: {
                return this._decBuffer10;
            }
            default: {
                QLog.w("TRAE", 2, "getDecBuffer failed!! index:" + n);
                return null;
            }
        }
    }
    
    private int ReadMp3File(final int n) {
        final TraeAudioCodecList.CodecInfo find = this._traeAudioCodecList.find(n);
        if (find == null) {
            return -1;
        }
        final ByteBuffer decBuffer = this.getDecBuffer(n);
        if (decBuffer == null) {
            return -1;
        }
        decBuffer.rewind();
        final int frameSize = find.audioDecoder.getFrameSize();
        final int readOneFrame = find.audioDecoder.ReadOneFrame(find._tempBufdec, frameSize);
        decBuffer.put(find._tempBufdec, 0, frameSize);
        return readOneFrame;
    }
    
    private int CloseMp3File(final int n) {
        final TraeAudioCodecList.CodecInfo find = this._traeAudioCodecList.find(n);
        if (find != null) {
            find.audioDecoder.release();
            find.audioDecoder = null;
            this._traeAudioCodecList.remove(n);
            return 0;
        }
        return -1;
    }
    
    private int SeekMp3To(final int n, final int n2) {
        final TraeAudioCodecList.CodecInfo find = this._traeAudioCodecList.find(n);
        if (find != null) {
            return find.audioDecoder.SeekTo(n2);
        }
        return 0;
    }
    
    private int getMp3SampleRate(final int n) {
        final TraeAudioCodecList.CodecInfo find = this._traeAudioCodecList.find(n);
        if (find != null) {
            return find.audioDecoder.getSampleRate();
        }
        return -1;
    }
    
    private int getMp3Channels(final int n) {
        final TraeAudioCodecList.CodecInfo find = this._traeAudioCodecList.find(n);
        if (find != null) {
            return find.audioDecoder.getChannels();
        }
        return -1;
    }
    
    private long getMp3FileTotalMs(final int n) {
        final TraeAudioCodecList.CodecInfo find = this._traeAudioCodecList.find(n);
        if (find != null) {
            return find.audioDecoder.getFileTotalMs();
        }
        return -1L;
    }
    
    public int checkAACSupported() {
        final int checkAACMediaCodecSupported = this.checkAACMediaCodecSupported(false);
        final int checkAACMediaCodecSupported2 = this.checkAACMediaCodecSupported(true);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "checkAACSupported isSupportEncoder: " + checkAACMediaCodecSupported + ", isSupportDecoder:" + checkAACMediaCodecSupported2);
        }
        if (checkAACMediaCodecSupported == 1 && checkAACMediaCodecSupported2 == 1) {
            return 1;
        }
        return 0;
    }
    
    @SuppressLint({ "NewApi" })
    public int checkAACMediaCodecSupported(final boolean b) {
        try {
            final String s = "audio/mp4a-latm";
            if (Build.VERSION.SDK_INT >= 21) {
                for (final MediaCodecInfo mediaCodecInfo : new MediaCodecList(1).getCodecInfos()) {
                    if (mediaCodecInfo.isEncoder() != b) {
                        if (mediaCodecInfo.getName().toLowerCase().contains("nvidia")) {
                            return 0;
                        }
                        final String[] supportedTypes = mediaCodecInfo.getSupportedTypes();
                        for (int j = 0; j < supportedTypes.length; ++j) {
                            if (supportedTypes[j].equalsIgnoreCase(s)) {
                                if (QLog.isColorLevel()) {
                                    QLog.w("TRAE", 2, "checkAACSupported support!, " + mediaCodecInfo.getName());
                                }
                                return 1;
                            }
                        }
                    }
                }
            }
            else {
                for (int codecCount = MediaCodecList.getCodecCount(), k = 0; k < codecCount; ++k) {
                    final MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(k);
                    if (codecInfo.isEncoder() != b) {
                        if (codecInfo.getName().toLowerCase().contains("nvidia")) {
                            return 0;
                        }
                        final String[] supportedTypes2 = codecInfo.getSupportedTypes();
                        for (int l = 0; l < supportedTypes2.length; ++l) {
                            if (supportedTypes2[l].equalsIgnoreCase(s)) {
                                if (QLog.isColorLevel()) {
                                    QLog.w("TRAE", 2, "checkAACSupported support!, " + codecInfo.getName());
                                }
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "check if support aac codec failed.");
            }
        }
        if (QLog.isColorLevel()) {
            QLog.e("TRAE", 2, "Error when checking aac codec availability");
        }
        return 0;
    }
    
    static {
        AudioDeviceInterface._dumpEnable = false;
        AudioDeviceInterface._logEnable = true;
        AudioDeviceInterface.mDeviceList = null;
        AudioDeviceInterface.mVivoKTVHelper = null;
        AudioDeviceInterface.isSupportVivoKTVHelper = false;
        CPU_FILTER = new FileFilter() {
            @Override
            public boolean accept(final File file) {
                final String name = file.getName();
                if (name.startsWith("cpu")) {
                    for (int i = 3; i < name.length(); ++i) {
                        if (name.charAt(i) < '0' || name.charAt(i) > '9') {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        };
    }
}
