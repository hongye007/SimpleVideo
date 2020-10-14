package com.tencent.rtmp.sharp.jni;

import android.media.*;
import java.util.concurrent.locks.*;
import android.util.*;
import android.net.*;
import java.io.*;
import android.os.*;
import android.content.*;
import android.annotation.*;
import java.util.*;
import android.bluetooth.*;
import android.text.*;
import java.lang.reflect.*;

@SuppressLint({ "NewApi" })
public class TraeAudioManager extends BroadcastReceiver
{
    public static final String ACTION_TRAEAUDIOMANAGER_REQUEST = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST";
    public static final String ACTION_TRAEAUDIOMANAGER_RES = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES";
    public static final String ACTION_TRAEAUDIOMANAGER_NOTIFY = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY";
    public static final String PARAM_OPERATION = "PARAM_OPERATION";
    public static final String PARAM_SESSIONID = "PARAM_SESSIONID";
    public static final String PARAM_ISHOSTSIDE = "PARAM_ISHOSTSIDE";
    public static final String PARAM_RES_ERRCODE = "PARAM_RES_ERRCODE";
    public static final int RES_ERRCODE_NONE = 0;
    public static final int RES_ERRCODE_SERVICE_OFF = 1;
    public static final int RES_ERRCODE_VOICECALL_EXIST = 2;
    public static final int RES_ERRCODE_VOICECALL_NOT_EXIST = 3;
    public static final int RES_ERRCODE_STOPRING_INTERRUPT = 4;
    public static final int RES_ERRCODE_RING_NOT_EXIST = 5;
    public static final int RES_ERRCODE_VOICECALLPOST_INTERRUPT = 6;
    public static final int RES_ERRCODE_DEVICE_UNKOWN = 7;
    public static final int RES_ERRCODE_DEVICE_NOT_VISIABLE = 8;
    public static final int RES_ERRCODE_DEVICE_UNCHANGEABLE = 9;
    public static final int RES_ERRCODE_DEVICE_BTCONNCECTED_TIMEOUT = 10;
    public static final String PARAM_STATUS = "PARAM_STATUS";
    public static final String PARAM_DEVICE = "PARAM_DEVICE";
    public static final String PARAM_ERROR = "PARAM_ERROR";
    public static final String PARAM_MODEPOLICY = "PARAM_MODEPOLICY";
    public static final String PARAM_STREAMTYPE = "PARAM_STREAMTYPE";
    public static final String PARAM_RING_DATASOURCE = "PARAM_RING_DATASOURCE";
    public static final String PARAM_RING_RSID = "PARAM_RING_RSID";
    public static final String PARAM_RING_URI = "PARAM_RING_URI";
    public static final String PARAM_RING_FILEPATH = "PARAM_RING_FILEPATH";
    public static final String PARAM_RING_LOOP = "PARAM_RING_LOOP";
    public static final String PARAM_RING_LOOPCOUNT = "PARAM_RING_LOOPCOUNT";
    public static final String PARAM_RING_MODE = "PARAM_RING_MODE";
    public static final String PARAM_RING_USERDATA_STRING = "PARAM_RING_USERDATA_STRING";
    public static final String OPERATION_STARTSERVICE = "OPERATION_STARTSERVICE";
    public static final String EXTRA_DATA_DEVICECONFIG = "EXTRA_DATA_DEVICECONFIG";
    public static final String OPERATION_STOPSERVICE = "OPERATION_STOPSERVICE";
    public static final String OPERATION_REGISTERAUDIOSESSION = "OPERATION_REGISTERAUDIOSESSION";
    public static final String REGISTERAUDIOSESSION_ISREGISTER = "REGISTERAUDIOSESSION_ISREGISTER";
    public static final String OPERATION_GETDEVICELIST = "OPERATION_GETDEVICELIST";
    public static final String OPERATION_GETSTREAMTYPE = "OPERATION_GETSTREAMTYPE";
    public static final String OPERATION_CONNECTDEVICE = "OPERATION_CONNECTDEVICE";
    public static final String CONNECTDEVICE_DEVICENAME = "CONNECTDEVICE_DEVICENAME";
    public static final String CONNECTDEVICE_RESULT_DEVICENAME = "CONNECTDEVICE_RESULT_DEVICENAME";
    public static final String OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE = "OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE";
    public static final String OPERATION_ISDEVICECHANGABLED = "OPERATION_ISDEVICECHANGABLED";
    public static final String ISDEVICECHANGABLED_RESULT_ISCHANGABLED = "ISDEVICECHANGABLED_REULT_ISCHANGABLED";
    public static final String OPERATION_GETCONNECTEDDEVICE = "OPERATION_GETCONNECTEDDEVICE";
    public static final String GETCONNECTEDDEVICE_RESULT_LIST = "GETCONNECTEDDEVICE_REULT_LIST";
    public static final String OPERATION_GETCONNECTINGDEVICE = "OPERATION_GETCONNECTINGDEVICE";
    public static final String GETCONNECTINGDEVICE_RESULT_LIST = "GETCONNECTINGDEVICE_REULT_LIST";
    public static final String EXTRA_DATA_STREAMTYPE = "EXTRA_DATA_STREAMTYPE";
    public static final String OPERATION_VOICECALL_PREPROCESS = "OPERATION_VOICECALL_PREPROCESS";
    public static final String OPERATION_VOICECALL_POSTPROCESS = "OPERATION_VOICECALL_POSTROCESS";
    public static final String OPERATION_STARTRING = "OPERATION_STARTRING";
    public static final String OPERATION_STOPRING = "OPERATION_STOPRING";
    public static final String OPERATION_REQUEST_RELEASE_AUDIO_FOCUS = "OPERATION_REQUEST_RELEASE_AUDIO_FOCUS";
    public static final String OPERATION_RECOVER_AUDIO_FOCUS = "OPERATION_RECOVER_AUDIO_FOCUS";
    public static final String OPERATION_VOICECALL_AUDIOPARAM_CHANGED = "OPERATION_VOICECALL_AUDIOPARAM_CHANGED";
    public static final String NOTIFY_SERVICE_STATE = "NOTIFY_SERVICE_STATE";
    public static final String NOTIFY_SERVICE_STATE_DATE = "NOTIFY_SERVICE_STATE_DATE";
    public static final String NOTIFY_DEVICELIST_UPDATE = "NOTIFY_DEVICELISTUPDATE";
    public static final String EXTRA_DATA_AVAILABLEDEVICE_LIST = "EXTRA_DATA_AVAILABLEDEVICE_LIST";
    public static final String EXTRA_DATA_PREV_CONNECTEDDEVICE = "EXTRA_DATA_PREV_CONNECTEDDEVICE";
    public static final String EXTRA_DATA_CONNECTEDDEVICE = "EXTRA_DATA_CONNECTEDDEVICE";
    public static final String EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME = "EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME";
    public static final String NOTIFY_DEVICECHANGABLE_UPDATE = "NOTIFY_DEVICECHANGABLE_UPDATE";
    public static final String NOTIFY_DEVICECHANGABLE_UPDATE_DATE = "NOTIFY_DEVICECHANGABLE_UPDATE_DATE";
    public static final String NOTIFY_RING_COMPLETION = "NOTIFY_RING_COMPLETION";
    public static final String NOTIFY_STREAMTYPE_UPDATE = "NOTIFY_STREAMTYPE_UPDATE";
    public static final String NOTIFY_ROUTESWITCHSTART = "NOTIFY_ROUTESWITCHSTART";
    public static final String EXTRA_DATA_ROUTESWITCHSTART_FROM = "EXTRA_DATA_ROUTESWITCHSTART_FROM";
    public static final String EXTRA_DATA_ROUTESWITCHSTART_TO = "EXTRA_DATA_ROUTESWITCHSTART_TO";
    public static final String NOTIFY_ROUTESWITCHEND = "NOTIFY_ROUTESWITCHEND";
    public static final String EXTRA_DATA_ROUTESWITCHEND_DEV = "EXTRA_DATA_ROUTESWITCHEND_DEV";
    public static final String EXTRA_DATA_ROUTESWITCHEND_TIME = "EXTRA_DATA_ROUTESWITCHEND_TIME";
    public static final int EARACTION_AWAY = 0;
    public static final int EARACTION_CLOSE = 1;
    public static final String OPERATION_EARACTION = "OPERATION_EARACTION";
    public static final String EXTRA_EARACTION = "EXTRA_EARACTION";
    public static final String DEVICE_NONE = "DEVICE_NONE";
    public static final String DEVICE_EARPHONE = "DEVICE_EARPHONE";
    public static final String DEVICE_SPEAKERPHONE = "DEVICE_SPEAKERPHONE";
    public static final String DEVICE_WIREDHEADSET = "DEVICE_WIREDHEADSET";
    public static final String DEVICE_BLUETOOTHHEADSET = "DEVICE_BLUETOOTHHEADSET";
    public static final int DEVICE_STATUS_ERROR = -1;
    public static final int DEVICE_STATUS_DISCONNECTED = 0;
    public static final int DEVICE_STATUS_CONNECTING = 1;
    public static final int DEVICE_STATUS_CONNECTED = 2;
    public static final int DEVICE_STATUS_DISCONNECTING = 3;
    public static final int DEVICE_STATUS_UNCHANGEABLE = 4;
    public static final int AUDIO_MANAGER_ACTIVE_NONE = 0;
    public static final int AUDIO_MANAGER_ACTIVE_VOICECALL = 1;
    public static final int AUDIO_MANAGER_ACTIVE_RING = 2;
    public static final int MODE_VOICE_CHAT = 0;
    public static final int MODE_MUSIC_PLAY_RECORD = 1;
    public static final int MODE_MUSIC_PLAYBACK = 2;
    public static final int MODE_MUSIC_PLAY_RECORD_HIGH_QUALITY = 3;
    public static final int MODE_VOICE_PLAYBACK = 4;
    public static final int MODE_MUSIC_PLAY_RECORD_LOW_QUALITY = 5;
    AudioManager _am;
    Context _context;
    int _activeMode;
    int _prevMode;
    int _streamType;
    int _modePolicy;
    public static boolean IsMusicScene;
    public static boolean IsEarPhoneSupported;
    public static boolean IsUpdateSceneFlag;
    public static boolean enableDeviceSwitchFlag;
    boolean IsBluetoothA2dpExisted;
    public static final String VOICECALL_CONFIG = "DEVICE_SPEAKERPHONE;DEVICE_EARPHONE;DEVICE_BLUETOOTHHEADSET;DEVICE_WIREDHEADSET;";
    public static final String VIDEO_CONFIG = "DEVICE_EARPHONE;DEVICE_SPEAKERPHONE;DEVICE_BLUETOOTHHEADSET;DEVICE_WIREDHEADSET;";
    public static final String MUSIC_CONFIG = "DEVICE_SPEAKERPHONE;DEVICE_WIREDHEADSET;DEVICE_BLUETOOTHHEADSET;";
    TraeAudioSessionHost _audioSessionHost;
    DeviceConfigManager _deviceConfigManager;
    BluetoohHeadsetCheckInterface _bluetoothCheck;
    String sessionConnectedDev;
    static ReentrantLock _glock;
    static TraeAudioManager _ginstance;
    static int _gHostProcessId;
    TraeAudioManagerLooper mTraeAudioManagerLooper;
    ReentrantLock _lock;
    static final String AUDIO_PARAMETER_STREAM_ROUTING = "routing";
    static final int AUDIO_DEVICE_OUT_EARPIECE = 1;
    static final int AUDIO_DEVICE_OUT_SPEAKER = 2;
    static final int AUDIO_DEVICE_OUT_WIRED_HEADSET = 4;
    static final int AUDIO_DEVICE_OUT_WIRED_HEADPHONE = 8;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO = 16;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP = 128;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    switchThread _switchThread;
    public static final int FORCE_NONE = 0;
    public static final int FORCE_SPEAKER = 1;
    public static final int FORCE_HEADPHONES = 2;
    public static final int FORCE_BT_SCO = 3;
    public static final int FORCE_BT_A2DP = 4;
    public static final int FORCE_WIRED_ACCESSORY = 5;
    public static final int FORCE_BT_CAR_DOCK = 6;
    public static final int FORCE_BT_DESK_DOCK = 7;
    public static final int FORCE_ANALOG_DOCK = 8;
    public static final int FORCE_DIGITAL_DOCK = 9;
    public static final int FORCE_NO_BT_A2DP = 10;
    private static final int NUM_FORCE_CONFIG = 11;
    public static final int FORCE_DEFAULT = 0;
    public static final int FOR_COMMUNICATION = 0;
    public static final int FOR_MEDIA = 1;
    public static final int FOR_RECORD = 2;
    public static final int FOR_DOCK = 3;
    private static final int NUM_FORCE_USE = 4;
    static final String[] forceName;
    
    public static boolean checkDevName(final String s) {
        return s != null && ("DEVICE_SPEAKERPHONE".equals(s) || "DEVICE_EARPHONE".equals(s) || "DEVICE_WIREDHEADSET".equals(s) || "DEVICE_BLUETOOTHHEADSET".equals(s));
    }
    
    public static boolean isHandfree(final String s) {
        return checkDevName(s) && "DEVICE_SPEAKERPHONE".equals(s);
    }
    
    void printDevices() {
        AudioDeviceInterface.LogTraceEntry("");
        final int deviceNumber = this._deviceConfigManager.getDeviceNumber();
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "   ConnectedDevice:" + this._deviceConfigManager.getConnectedDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "   ConnectingDevice:" + this._deviceConfigManager.getConnectingDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "   prevConnectedDevice:" + this._deviceConfigManager.getPrevConnectedDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "   AHPDevice:" + this._deviceConfigManager.getAvailabledHighestPriorityDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "   deviceNamber:" + deviceNumber);
        }
        for (int i = 0; i < deviceNumber; ++i) {
            final String deviceName = this._deviceConfigManager.getDeviceName(i);
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "      " + i + " devName:" + deviceName + " Visible:" + this._deviceConfigManager.getVisible(deviceName) + " Priority:" + this._deviceConfigManager.getPriority(deviceName));
            }
        }
        final String[] array = this._deviceConfigManager.getAvailableDeviceList().toArray(new String[0]);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "   AvailableNamber:" + array.length);
        }
        for (int j = 0; j < array.length; ++j) {
            final String s = array[j];
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "      " + j + " devName:" + s + " Visible:" + this._deviceConfigManager.getVisible(s) + " Priority:" + this._deviceConfigManager.getPriority(s));
            }
        }
        AudioDeviceInterface.LogTraceExit();
    }
    
    static boolean isCloseSystemAPM(final int n) {
        if (n != -1) {
            return false;
        }
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            if (Build.MODEL.equals("MI 2")) {
                return true;
            }
            if (Build.MODEL.equals("MI 2A")) {
                return true;
            }
            if (Build.MODEL.equals("MI 2S")) {
                return true;
            }
            if (Build.MODEL.equals("MI 2SC")) {
                return true;
            }
        }
        else if (Build.MANUFACTURER.equals("samsung") && Build.MODEL.equals("SCH-I959")) {
            return true;
        }
        return false;
    }
    
    public static boolean IsEabiLowVersionByAbi(final String s) {
        return s == null || (!s.contains("x86") && !s.contains("mips") && (s.equalsIgnoreCase("armeabi") || (!s.equalsIgnoreCase("armeabi-v7a") && !s.equalsIgnoreCase("arm64-v8a"))));
    }
    
    static boolean IsEabiLowVersion() {
        final String cpu_ABI = Build.CPU_ABI;
        String s = "unknown";
        if (Build.VERSION.SDK_INT >= 8) {
            try {
                s = (String)Build.class.getDeclaredField("CPU_ABI2").get(null);
            }
            catch (Exception ex) {
                return IsEabiLowVersionByAbi(cpu_ABI);
            }
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "IsEabiVersion CPU_ABI:" + cpu_ABI + " CPU_ABI2:" + s);
        }
        return IsEabiLowVersionByAbi(cpu_ABI) && IsEabiLowVersionByAbi(s);
    }
    
    static int getAudioSource(final int n) {
        int n2 = 0;
        if (TraeAudioManager.IsMusicScene) {
            return n2;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "[Config] armeabi low Version, getAudioSource _audioSourcePolicy:" + n + " source:" + n2);
            }
            return n2;
        }
        final int sdk_INT = Build.VERSION.SDK_INT;
        if (n >= 0) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "[Config] getAudioSource _audioSourcePolicy:" + n + " source:" + n);
            }
            return n;
        }
        if (sdk_INT >= 11) {
            n2 = 7;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "[Config] getAudioSource _audioSourcePolicy:" + n + " source:" + n2);
        }
        return n2;
    }
    
    static int getAudioStreamType(final int n) {
        int n2 = 3;
        if (TraeAudioManager.IsMusicScene) {
            return n2;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "[Config] armeabi low Version, getAudioStreamType audioStreamTypePolicy:" + n + " streamType:" + n2);
            }
            return n2;
        }
        final int sdk_INT = Build.VERSION.SDK_INT;
        if (n >= 0) {
            n2 = n;
        }
        else if (sdk_INT >= 9) {
            n2 = 0;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "[Config] getAudioStreamType audioStreamTypePolicy:" + n + " streamType:" + n2);
        }
        return n2;
    }
    
    static int getCallAudioMode(final int n) {
        int n2 = 0;
        if (TraeAudioManager.IsMusicScene) {
            return n2;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "[Config] armeabi low Version, getCallAudioMode modePolicy:" + n + " mode:" + n2);
            }
            return n2;
        }
        final int sdk_INT = Build.VERSION.SDK_INT;
        if (n >= 0) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "[Config] getCallAudioMode modePolicy:" + n + " mode:" + n);
            }
            return n;
        }
        if (sdk_INT >= 11) {
            n2 = 3;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "[Config] getCallAudioMode _modePolicy:" + n + " mode:" + n2 + "facturer:" + Build.MANUFACTURER + " model:" + Build.MODEL);
        }
        return n2;
    }
    
    void updateDeviceStatus() {
        for (int deviceNumber = this._deviceConfigManager.getDeviceNumber(), i = 0; i < deviceNumber; ++i) {
            boolean b = false;
            final String deviceName = this._deviceConfigManager.getDeviceName(i);
            if (deviceName != null) {
                if (deviceName.equals("DEVICE_BLUETOOTHHEADSET")) {
                    if (this._bluetoothCheck == null) {
                        b = this._deviceConfigManager.setVisible(deviceName, false);
                    }
                    else {
                        b = this._deviceConfigManager.setVisible(deviceName, this._bluetoothCheck.isConnected());
                    }
                }
                else if (deviceName.equals("DEVICE_WIREDHEADSET")) {
                    b = this._deviceConfigManager.setVisible(deviceName, this._am.isWiredHeadsetOn());
                }
                else if (deviceName.equals("DEVICE_SPEAKERPHONE")) {
                    this._deviceConfigManager.setVisible(deviceName, true);
                }
            }
            if (b && QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "pollUpdateDevice dev:" + deviceName + " Visible:" + this._deviceConfigManager.getVisible(deviceName));
            }
        }
        this.checkAutoDeviceListUpdate();
    }
    
    void _updateEarphoneVisable() {
        if (this._deviceConfigManager.getVisible("DEVICE_WIREDHEADSET")) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " detected headset plugin,so disable earphone");
            }
            this._deviceConfigManager.setVisible("DEVICE_EARPHONE", false);
        }
        else {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " detected headset plugout,so enable earphone");
            }
            this._deviceConfigManager.setVisible("DEVICE_EARPHONE", true);
        }
    }
    
    void checkAutoDeviceListUpdate() {
        if (this._deviceConfigManager.getVisiableUpdateFlag()) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "checkAutoDeviceListUpdate got update!");
            }
            this._updateEarphoneVisable();
            this._deviceConfigManager.resetVisiableUpdateFlag();
            this.internalSendMessage(32785, new HashMap<String, Object>());
        }
    }
    
    void checkDevicePlug(final String s, final boolean b) {
        if (this._deviceConfigManager.getVisiableUpdateFlag()) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "checkDevicePlug got update dev:" + s + (b ? " piugin" : " plugout") + " connectedDev:" + this._deviceConfigManager.getConnectedDevice());
            }
            this._updateEarphoneVisable();
            this._deviceConfigManager.resetVisiableUpdateFlag();
            if (b) {
                final HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("PARAM_DEVICE", s);
                this.internalSendMessage(32786, hashMap);
            }
            else {
                final String connectedDevice = this._deviceConfigManager.getConnectedDevice();
                if (connectedDevice.equals(s) || connectedDevice.equals("DEVICE_NONE")) {
                    final HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
                    hashMap2.put("PARAM_DEVICE", s);
                    this.internalSendMessage(32787, hashMap2);
                }
                else {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, " ---No switch,plugout:" + s + " connectedDev:" + connectedDevice);
                    }
                    this.internalSendMessage(32785, new HashMap<String, Object>());
                }
            }
        }
    }
    
    public static int SetSpeakerForTest(final Context context, final boolean b) {
        int internalSetSpeaker = -1;
        TraeAudioManager._glock.lock();
        if (null != TraeAudioManager._ginstance) {
            internalSetSpeaker = TraeAudioManager._ginstance.InternalSetSpeaker(context, b);
        }
        else if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "TraeAudioManager|static SetSpeakerForTest|null == _ginstance");
        }
        TraeAudioManager._glock.unlock();
        return internalSetSpeaker;
    }
    
    int InternalSetSpeaker(final Context context, final boolean speakerphoneOn) {
        if (context == null) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "Could not InternalSetSpeaker - no context");
            }
            return -1;
        }
        final AudioManager audioManager = (AudioManager)context.getSystemService("audio");
        if (audioManager == null) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "Could not InternalSetSpeaker - no audio manager");
            }
            return -1;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InternalSetSpeaker entry:speaker:" + (audioManager.isSpeakerphoneOn() ? "Y" : "N") + "-->:" + (speakerphoneOn ? "Y" : "N"));
        }
        if (isCloseSystemAPM(this._modePolicy) && this._activeMode != 2) {
            return this.InternalSetSpeakerSpe(audioManager, speakerphoneOn);
        }
        if (audioManager.isSpeakerphoneOn() != speakerphoneOn) {
            audioManager.setSpeakerphoneOn(speakerphoneOn);
        }
        final int n = (audioManager.isSpeakerphoneOn() == speakerphoneOn) ? 0 : -1;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InternalSetSpeaker exit:" + speakerphoneOn + " res:" + n + " mode:" + audioManager.getMode());
        }
        return n;
    }
    
    int InternalSetSpeakerSpe(final AudioManager audioManager, final boolean b) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InternalSetSpeakerSpe fac:" + Build.MANUFACTURER + " model:" + Build.MODEL + " st:" + this._streamType + " media_force_use:" + getForceUse(1));
        }
        if (b) {
            this.InternalSetMode(0);
            audioManager.setSpeakerphoneOn(true);
            setForceUse(1, 1);
        }
        else {
            this.InternalSetMode(3);
            audioManager.setSpeakerphoneOn(false);
            setForceUse(1, 0);
        }
        final int n = (audioManager.isSpeakerphoneOn() == b) ? 0 : -1;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "InternalSetSpeakerSpe exit:" + b + " res:" + n + " mode:" + audioManager.getMode());
        }
        return n;
    }
    
    void InternalSetMode(final int mode) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "SetMode entry:" + mode);
        }
        if (this._am == null) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "setMode:" + mode + " fail am=null");
            }
            return;
        }
        this._am.setMode(mode);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "setMode:" + mode + ((this._am.getMode() != mode) ? "fail" : "success"));
        }
    }
    
    public static int registerAudioSession(final TraeAudioSession traeAudioSession, final boolean b, final long n, final Context context) {
        int n2 = -1;
        TraeAudioManager._glock.lock();
        if (null != TraeAudioManager._ginstance) {
            if (b) {
                TraeAudioManager._ginstance._audioSessionHost.add(traeAudioSession, n, context);
            }
            else {
                TraeAudioManager._ginstance._audioSessionHost.remove(n);
            }
            n2 = 0;
        }
        TraeAudioManager._glock.unlock();
        return n2;
    }
    
    public static int sendMessage(final int n, final HashMap<String, Object> hashMap) {
        int internalSendMessage = -1;
        TraeAudioManager._glock.lock();
        if (null != TraeAudioManager._ginstance) {
            internalSendMessage = TraeAudioManager._ginstance.internalSendMessage(n, hashMap);
        }
        TraeAudioManager._glock.unlock();
        return internalSendMessage;
    }
    
    public static int init(final Context context) {
        Log.w("TRAE", "TraeAudioManager init _ginstance:" + TraeAudioManager._ginstance);
        AudioDeviceInterface.LogTraceEntry(" _ginstance:" + TraeAudioManager._ginstance);
        TraeAudioManager._glock.lock();
        if (null == TraeAudioManager._ginstance) {
            TraeAudioManager._ginstance = new TraeAudioManager(context);
        }
        TraeAudioManager._glock.unlock();
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }
    
    public static void uninit() {
        Log.w("TRAE", "TraeAudioManager uninit _ginstance:" + TraeAudioManager._ginstance);
        AudioDeviceInterface.LogTraceEntry(" _ginstance:" + TraeAudioManager._ginstance);
        TraeAudioManager._glock.lock();
        if (null != TraeAudioManager._ginstance) {
            TraeAudioManager._ginstance.release();
            TraeAudioManager._ginstance = null;
        }
        TraeAudioManager._glock.unlock();
        AudioDeviceInterface.LogTraceExit();
    }
    
    TraeAudioManager(final Context context) {
        this._am = null;
        this._context = null;
        this._activeMode = 0;
        this._prevMode = 0;
        this._streamType = 0;
        this._modePolicy = -1;
        this.IsBluetoothA2dpExisted = true;
        this._audioSessionHost = null;
        this._deviceConfigManager = null;
        this._bluetoothCheck = null;
        this.sessionConnectedDev = "DEVICE_NONE";
        this.mTraeAudioManagerLooper = null;
        this._lock = new ReentrantLock();
        this._switchThread = null;
        AudioDeviceInterface.LogTraceEntry(" context:" + context);
        if (context == null) {
            return;
        }
        this._context = context;
        this.mTraeAudioManagerLooper = new TraeAudioManagerLooper(this);
        if (this.mTraeAudioManagerLooper != null) {}
        AudioDeviceInterface.LogTraceExit();
    }
    
    public void release() {
        AudioDeviceInterface.LogTraceEntry("");
        if (null != this.mTraeAudioManagerLooper) {
            this.mTraeAudioManagerLooper.quit();
            this.mTraeAudioManagerLooper = null;
        }
        AudioDeviceInterface.LogTraceExit();
    }
    
    public void onReceive(final Context context, final Intent intent) {
        if (intent == null || context == null) {
            if (QLog.isColorLevel()) {
                QLog.d("TRAE", 2, "onReceive intent or context is null!");
            }
            return;
        }
        try {
            final String action = intent.getAction();
            final String stringExtra = intent.getStringExtra("PARAM_OPERATION");
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "TraeAudioManager|onReceive::Action:" + intent.getAction());
            }
            if (this._deviceConfigManager == null) {
                if (QLog.isColorLevel()) {
                    QLog.d("TRAE", 2, "_deviceConfigManager null!");
                }
                return;
            }
            final boolean visible = this._deviceConfigManager.getVisible("DEVICE_WIREDHEADSET");
            final boolean visible2 = this._deviceConfigManager.getVisible("DEVICE_BLUETOOTHHEADSET");
            if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                this.onHeadsetPlug(context, intent);
                if (!visible && this._deviceConfigManager.getVisible("DEVICE_WIREDHEADSET")) {
                    this.checkDevicePlug("DEVICE_WIREDHEADSET", true);
                }
                if (visible && !this._deviceConfigManager.getVisible("DEVICE_WIREDHEADSET")) {
                    this.checkDevicePlug("DEVICE_WIREDHEADSET", false);
                }
            }
            else if (!"android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
                if ("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST".equals(action)) {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "   OPERATION:" + stringExtra);
                    }
                    if ("OPERATION_STARTSERVICE".equals(stringExtra)) {
                        startService(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false, intent.getStringExtra("EXTRA_DATA_DEVICECONFIG"));
                    }
                    else if ("OPERATION_STOPSERVICE".equals(stringExtra)) {
                        stopService(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_GETDEVICELIST".equals(stringExtra)) {
                        getDeviceList(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_GETSTREAMTYPE".equals(stringExtra)) {
                        getStreamType(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_CONNECTDEVICE".equals(stringExtra)) {
                        connectDevice(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false, intent.getStringExtra("CONNECTDEVICE_DEVICENAME"));
                    }
                    else if ("OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE".equals(stringExtra)) {
                        connectHighestPriorityDevice(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_EARACTION".equals(stringExtra)) {
                        earAction(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false, intent.getIntExtra("EXTRA_EARACTION", -1));
                    }
                    else if ("OPERATION_ISDEVICECHANGABLED".equals(stringExtra)) {
                        isDeviceChangabled(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_GETCONNECTEDDEVICE".equals(stringExtra)) {
                        getConnectedDevice(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_GETCONNECTINGDEVICE".equals(stringExtra)) {
                        getConnectingDevice(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_VOICECALL_PREPROCESS".equals(stringExtra)) {
                        voicecallPreprocess(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false, intent.getIntExtra("PARAM_MODEPOLICY", -1), intent.getIntExtra("PARAM_STREAMTYPE", -1));
                    }
                    else if ("OPERATION_VOICECALL_POSTROCESS".equals(stringExtra)) {
                        voicecallPostprocess(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                    else if ("OPERATION_VOICECALL_AUDIOPARAM_CHANGED".equals(stringExtra)) {
                        voiceCallAudioParamChanged(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false, intent.getIntExtra("PARAM_MODEPOLICY", -1), intent.getIntExtra("PARAM_STREAMTYPE", -1));
                    }
                    else if ("OPERATION_STARTRING".equals(stringExtra)) {
                        startRing(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false, intent.getIntExtra("PARAM_RING_DATASOURCE", -1), intent.getIntExtra("PARAM_RING_RSID", -1), (Uri)intent.getParcelableExtra("PARAM_RING_URI"), intent.getStringExtra("PARAM_RING_FILEPATH"), intent.getBooleanExtra("PARAM_RING_LOOP", false), intent.getIntExtra("PARAM_RING_LOOPCOUNT", 1), intent.getStringExtra("PARAM_RING_USERDATA_STRING"), intent.getBooleanExtra("PARAM_RING_MODE", false));
                    }
                    else if ("OPERATION_STOPRING".equals(stringExtra)) {
                        stopRing(stringExtra, intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE), false);
                    }
                }
                else if (this._deviceConfigManager != null) {
                    if (this._bluetoothCheck != null) {
                        this._bluetoothCheck.onReceive(context, intent, this._deviceConfigManager);
                    }
                    if (!visible2 && this._deviceConfigManager.getVisible("DEVICE_BLUETOOTHHEADSET")) {
                        this.checkDevicePlug("DEVICE_BLUETOOTHHEADSET", true);
                    }
                    if (visible2 && !this._deviceConfigManager.getVisible("DEVICE_BLUETOOTHHEADSET")) {
                        this.checkDevicePlug("DEVICE_BLUETOOTHHEADSET", false);
                    }
                }
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "deal with receiver failed." + ex.getMessage());
            }
        }
    }
    
    void onHeadsetPlug(final Context context, final Intent intent) {
        final String s = "";
        String stringExtra = intent.getStringExtra("name");
        if (stringExtra == null) {
            stringExtra = "unkonw";
        }
        String s2 = s + " [" + stringExtra + "] ";
        final int intExtra = intent.getIntExtra("state", -1);
        if (intExtra != -1) {
            s2 += ((intExtra == 0) ? "unplugged" : "plugged");
        }
        String s3 = s2 + " mic:";
        final int intExtra2 = intent.getIntExtra("microphone", -1);
        if (intExtra2 != -1) {
            s3 += ((intExtra2 == 1) ? "Y" : "unkown");
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "onHeadsetPlug:: " + s3);
        }
        this._deviceConfigManager.setVisible("DEVICE_WIREDHEADSET", intExtra);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "onHeadsetPlug exit");
        }
    }
    
    int internalSendMessage(final int n, final HashMap<String, Object> hashMap) {
        int sendMessage = -1;
        if (null != this.mTraeAudioManagerLooper) {
            sendMessage = this.mTraeAudioManagerLooper.sendMessage(n, hashMap);
        }
        return sendMessage;
    }
    
    static int getDeviceList(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32774, (HashMap<String, Object>)hashMap);
    }
    
    static int getStreamType(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32784, (HashMap<String, Object>)hashMap);
    }
    
    static int startService(final String s, final long n, final boolean b, final String s2) {
        if (s2.length() <= 0) {
            return -1;
        }
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        hashMap.put("EXTRA_DATA_DEVICECONFIG", (Boolean)s2);
        return sendMessage(32772, (HashMap<String, Object>)hashMap);
    }
    
    static int stopService(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32773, (HashMap<String, Object>)hashMap);
    }
    
    static int disableDeviceSwitch() {
        TraeAudioManager.enableDeviceSwitchFlag = false;
        return 0;
    }
    
    static int connectDevice(final String s, final long n, final boolean b, final String s2) {
        if (s2 == null) {
            return -1;
        }
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        hashMap.put("CONNECTDEVICE_DEVICENAME", (Boolean)s2);
        hashMap.put("PARAM_DEVICE", (Boolean)s2);
        return sendMessage(32775, (HashMap<String, Object>)hashMap);
    }
    
    static int connectHighestPriorityDevice(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32789, (HashMap<String, Object>)hashMap);
    }
    
    static int earAction(final String s, final long n, final boolean b, final int n2) {
        if (n2 != 0 && n2 != 1) {
            return -1;
        }
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("PARAM_SESSIONID", (Integer)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Integer)s);
        hashMap.put("PARAM_ISHOSTSIDE", (Integer)(Object)Boolean.valueOf(b));
        hashMap.put("EXTRA_EARACTION", n2);
        return sendMessage(32776, (HashMap<String, Object>)hashMap);
    }
    
    static int isDeviceChangabled(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32777, (HashMap<String, Object>)hashMap);
    }
    
    static int getConnectedDevice(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32778, (HashMap<String, Object>)hashMap);
    }
    
    static int getConnectingDevice(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32779, (HashMap<String, Object>)hashMap);
    }
    
    static int voicecallPreprocess(final String s, final long n, final boolean b, final int n2, final int n3) {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("PARAM_SESSIONID", (Integer)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Integer)s);
        hashMap.put("PARAM_ISHOSTSIDE", (Integer)(Object)Boolean.valueOf(b));
        hashMap.put("PARAM_MODEPOLICY", n2);
        hashMap.put("PARAM_STREAMTYPE", n3);
        return sendMessage(32780, (HashMap<String, Object>)hashMap);
    }
    
    static int voicecallPostprocess(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32781, (HashMap<String, Object>)hashMap);
    }
    
    static int voiceCallAudioParamChanged(final String s, final long n, final boolean b, final int n2, final int n3) {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("PARAM_SESSIONID", (Integer)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Integer)s);
        hashMap.put("PARAM_ISHOSTSIDE", (Integer)(Object)Boolean.valueOf(b));
        hashMap.put("PARAM_MODEPOLICY", n2);
        hashMap.put("PARAM_STREAMTYPE", n3);
        return sendMessage(32788, (HashMap<String, Object>)hashMap);
    }
    
    static int startRing(final String s, final long n, final boolean b, final int n2, final int n3, final Uri uri, final String s2, final boolean b2, final int n4, final String s3, final boolean b3) {
        final HashMap<String, Uri> hashMap = new HashMap<String, Uri>();
        hashMap.put("PARAM_SESSIONID", (Uri)n);
        hashMap.put("PARAM_OPERATION", (Uri)s);
        hashMap.put("PARAM_ISHOSTSIDE", (Uri)b);
        hashMap.put("PARAM_RING_DATASOURCE", (Uri)n2);
        hashMap.put("PARAM_RING_RSID", (Uri)n3);
        hashMap.put("PARAM_RING_URI", uri);
        hashMap.put("PARAM_RING_FILEPATH", (Uri)s2);
        hashMap.put("PARAM_RING_LOOP", (Uri)b2);
        hashMap.put("PARAM_RING_LOOPCOUNT", (Uri)n4);
        hashMap.put("PARAM_RING_MODE", (Uri)b3);
        hashMap.put("PARAM_RING_USERDATA_STRING", (Uri)s3);
        return sendMessage(32782, (HashMap<String, Object>)hashMap);
    }
    
    static int stopRing(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32783, (HashMap<String, Object>)hashMap);
    }
    
    static int requestReleaseAudioFocus(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32790, (HashMap<String, Object>)hashMap);
    }
    
    static int recoverAudioFocus(final String s, final long n, final boolean b) {
        final HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        hashMap.put("PARAM_SESSIONID", (Boolean)(Object)Long.valueOf(n));
        hashMap.put("PARAM_OPERATION", (Boolean)s);
        hashMap.put("PARAM_ISHOSTSIDE", b);
        return sendMessage(32791, (HashMap<String, Object>)hashMap);
    }
    
    int InternalSessionConnectDevice(final HashMap<String, Object> hashMap) {
        AudioDeviceInterface.LogTraceEntry("");
        if (null == hashMap || this._context == null) {
            return -1;
        }
        if (TraeAudioManager.IsMusicScene) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "MusicScene: InternalSessionConnectDevice failed");
            }
            return -1;
        }
        final String s = hashMap.get("PARAM_DEVICE");
        Log.w("TRAE", "ConnectDevice: " + s);
        if (!TraeAudioManager.IsEarPhoneSupported && s.equals("DEVICE_EARPHONE")) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "InternalSessionConnectDevice IsEarPhoneSupported = false, Connect device:" + s + " failed");
            }
            return -1;
        }
        int n = 0;
        final boolean internalIsDeviceChangeable = this.InternalIsDeviceChangeable();
        if (!checkDevName(s)) {
            n = 7;
        }
        else if (!this._deviceConfigManager.getVisible(s)) {
            n = 8;
        }
        else if (!internalIsDeviceChangeable) {
            n = 9;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "sessonID:" + hashMap.get("PARAM_SESSIONID") + " devName:" + s + " bChangabled:" + (internalIsDeviceChangeable ? "Y" : "N") + " err:" + n);
        }
        if (n != 0) {
            final Intent intent = new Intent();
            intent.putExtra("CONNECTDEVICE_RESULT_DEVICENAME", (String)hashMap.get("PARAM_DEVICE"));
            this.sendResBroadcast(intent, hashMap, n);
            return -1;
        }
        if (s.equals(this._deviceConfigManager.getConnectedDevice())) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, " --has connected!");
            }
            final Intent intent2 = new Intent();
            intent2.putExtra("CONNECTDEVICE_RESULT_DEVICENAME", (String)hashMap.get("PARAM_DEVICE"));
            this.sendResBroadcast(intent2, hashMap, n);
            return 0;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, " --connecting...");
        }
        this.InternalConnectDevice(s, hashMap, false);
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }
    
    int InternalSessionEarAction(final HashMap<String, Object> hashMap) {
        return 0;
    }
    
    int InternalConnectDevice(final String s, final HashMap<String, Object> deviceConnectParam, final boolean b) {
        AudioDeviceInterface.LogTraceEntry(" devName:" + s);
        if (s == null) {
            return -1;
        }
        if (TraeAudioManager.IsMusicScene && s.equals("DEVICE_EARPHONE")) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "MusicScene, Connect device:" + s + " failed");
            }
            return -1;
        }
        if (!TraeAudioManager.IsEarPhoneSupported && s.equals("DEVICE_EARPHONE")) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "IsEarPhoneSupported = false, Connect device:" + s + " failed");
            }
            return -1;
        }
        if (!b && !this._deviceConfigManager.getConnectedDevice().equals("DEVICE_NONE") && s.equals(this._deviceConfigManager.getConnectedDevice())) {
            return 0;
        }
        if (!checkDevName(s) || !this._deviceConfigManager.getVisible(s)) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, " checkDevName fail");
            }
            return -1;
        }
        if (!this.InternalIsDeviceChangeable()) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, " InternalIsDeviceChangeable fail");
            }
            return -1;
        }
        if (this._switchThread != null) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "_switchThread:" + this._switchThread.getDeviceName());
            }
            this._switchThread.quit();
            this._switchThread = null;
        }
        if (s.equals("DEVICE_EARPHONE")) {
            this._switchThread = new earphoneSwitchThread();
        }
        else if (s.equals("DEVICE_SPEAKERPHONE")) {
            this._switchThread = new speakerSwitchThread();
        }
        else if (s.equals("DEVICE_WIREDHEADSET")) {
            this._switchThread = new headsetSwitchThread();
        }
        else if (s.equals("DEVICE_BLUETOOTHHEADSET")) {
            this._switchThread = new bluetoothHeadsetSwitchThread();
        }
        if (this._switchThread != null) {
            this._switchThread.setDeviceConnectParam(deviceConnectParam);
            this._switchThread.start();
        }
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }
    
    int InternalSessionIsDeviceChangabled(final HashMap<String, Object> hashMap) {
        final Intent intent = new Intent();
        intent.putExtra("ISDEVICECHANGABLED_REULT_ISCHANGABLED", this.InternalIsDeviceChangeable());
        this.sendResBroadcast(intent, hashMap, 0);
        return 0;
    }
    
    boolean InternalIsDeviceChangeable() {
        final String connectingDevice = this._deviceConfigManager.getConnectingDevice();
        return connectingDevice == null || connectingDevice.equals("DEVICE_NONE") || connectingDevice.equals("");
    }
    
    int InternalSessionGetConnectedDevice(final HashMap<String, Object> hashMap) {
        final Intent intent = new Intent();
        intent.putExtra("GETCONNECTEDDEVICE_REULT_LIST", this._deviceConfigManager.getConnectedDevice());
        this.sendResBroadcast(intent, hashMap, 0);
        return 0;
    }
    
    int InternalSessionGetConnectingDevice(final HashMap<String, Object> hashMap) {
        final Intent intent = new Intent();
        intent.putExtra("GETCONNECTINGDEVICE_REULT_LIST", this._deviceConfigManager.getConnectingDevice());
        this.sendResBroadcast(intent, hashMap, 0);
        return 0;
    }
    
    int sendResBroadcast(final Intent intent, final HashMap<String, Object> hashMap, final int n) {
        if (this._context == null) {
            return -1;
        }
        final Long n2 = hashMap.get("PARAM_SESSIONID");
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, " sessonID:" + n2 + " " + (String)hashMap.get("PARAM_OPERATION"));
        }
        if (n2 == null || n2 == Long.MIN_VALUE) {
            this.InternalNotifyDeviceListUpdate();
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "sendResBroadcast sid null,don't send res");
            }
            return -1;
        }
        final Long n3 = hashMap.get("PARAM_SESSIONID");
        final String s = (String)hashMap.get("PARAM_OPERATION");
        if ("OPERATION_VOICECALL_PREPROCESS".equals(s)) {
            intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES");
            intent.putExtra("PARAM_SESSIONID", (Serializable)n3);
            intent.putExtra("PARAM_OPERATION", s);
            intent.putExtra("PARAM_RES_ERRCODE", n);
            if (this._audioSessionHost != null) {
                this._audioSessionHost.sendToAudioSessionMessage(intent);
            }
        }
        else {
            new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                @Override
                public void run() {
                    intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES");
                    intent.putExtra("PARAM_SESSIONID", (Serializable)n3);
                    intent.putExtra("PARAM_OPERATION", s);
                    intent.putExtra("PARAM_RES_ERRCODE", n);
                    if (TraeAudioManager.this._context != null) {
                        TraeAudioManager.this._context.sendBroadcast(intent);
                    }
                }
            });
        }
        return 0;
    }
    
    int InternalNotifyDeviceListUpdate() {
        AudioDeviceInterface.LogTraceEntry("");
        if (null == this._context) {
            return -1;
        }
        final HashMap<String, Object> snapParams = this._deviceConfigManager.getSnapParams();
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            final /* synthetic */ ArrayList val$list = (ArrayList)snapParams.get("EXTRA_DATA_AVAILABLEDEVICE_LIST");
            final /* synthetic */ String val$con = (String)snapParams.get("EXTRA_DATA_CONNECTEDDEVICE");
            final /* synthetic */ String val$prevCon = (String)snapParams.get("EXTRA_DATA_PREV_CONNECTEDDEVICE");
            final /* synthetic */ String val$_bluetoothName = TraeAudioManager.this._deviceConfigManager.getBluetoothName();
            
            @Override
            public void run() {
                final Intent intent = new Intent();
                intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY");
                intent.putExtra("PARAM_OPERATION", "NOTIFY_DEVICELISTUPDATE");
                intent.putExtra("EXTRA_DATA_AVAILABLEDEVICE_LIST", (String[])this.val$list.toArray(new String[0]));
                intent.putExtra("EXTRA_DATA_CONNECTEDDEVICE", this.val$con);
                intent.putExtra("EXTRA_DATA_PREV_CONNECTEDDEVICE", this.val$prevCon);
                intent.putExtra("EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME", this.val$_bluetoothName);
                if (TraeAudioManager.this._context != null) {
                    TraeAudioManager.this._context.sendBroadcast(intent);
                }
            }
        });
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }
    
    int InternalNotifyDeviceChangableUpdate() {
        if (null == this._context) {
            return -1;
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            final /* synthetic */ boolean val$TAMisDeviceChangeable = TraeAudioManager.this.InternalIsDeviceChangeable();
            
            @Override
            public void run() {
                final Intent intent = new Intent();
                intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY");
                intent.putExtra("PARAM_OPERATION", "NOTIFY_DEVICECHANGABLE_UPDATE");
                intent.putExtra("NOTIFY_DEVICECHANGABLE_UPDATE_DATE", this.val$TAMisDeviceChangeable);
                if (TraeAudioManager.this._context != null) {
                    TraeAudioManager.this._context.sendBroadcast(intent);
                }
            }
        });
        return 0;
    }
    
    public BluetoohHeadsetCheckInterface CreateBluetoothCheck(final Context context, final DeviceConfigManager deviceConfigManager) {
        BluetoohHeadsetCheckInterface bluetoohHeadsetCheckInterface;
        if (Build.VERSION.SDK_INT >= 11) {
            bluetoohHeadsetCheckInterface = new BluetoohHeadsetCheck();
        }
        else if (Build.VERSION.SDK_INT != 18) {
            bluetoohHeadsetCheckInterface = new BluetoohHeadsetCheckFor2x();
        }
        else {
            bluetoohHeadsetCheckInterface = new BluetoohHeadsetCheckFake();
        }
        if (!bluetoohHeadsetCheckInterface.init(context, deviceConfigManager)) {
            bluetoohHeadsetCheckInterface = new BluetoohHeadsetCheckFake();
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "CreateBluetoothCheck:" + bluetoohHeadsetCheckInterface.interfaceDesc() + " skip android4.3:" + ((Build.VERSION.SDK_INT == 18) ? "Y" : "N"));
        }
        return bluetoohHeadsetCheckInterface;
    }
    
    static String getForceConfigName(final int n) {
        if (n >= 0 && n < TraeAudioManager.forceName.length) {
            return TraeAudioManager.forceName[n];
        }
        return "unknow";
    }
    
    public static Object invokeMethod(final Object o, final String s, final Object[] array, final Class[] array2) {
        Object invoke = null;
        try {
            invoke = o.getClass().getMethod(s, (Class<?>[])array2).invoke(o, array);
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "invokeMethod Exception:" + ex.getMessage());
            }
        }
        return invoke;
    }
    
    public static Object invokeStaticMethod(final String s, final String s2, final Object[] array, final Class[] array2) {
        Object invoke = null;
        try {
            invoke = Class.forName(s).getMethod(s2, (Class<?>[])array2).invoke(null, array);
        }
        catch (ClassNotFoundException ex2) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "ClassNotFound:" + s);
            }
        }
        catch (NoSuchMethodException ex3) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "NoSuchMethod:" + s2);
            }
        }
        catch (IllegalArgumentException ex4) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "IllegalArgument:" + s2);
            }
        }
        catch (IllegalAccessException ex5) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "IllegalAccess:" + s2);
            }
        }
        catch (InvocationTargetException ex6) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "InvocationTarget:" + s2);
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "invokeStaticMethod Exception:" + ex.getMessage());
            }
        }
        return invoke;
    }
    
    static void setParameters(final String s) {
        final Object[] array = { s };
        final Class[] array2 = new Class[array.length];
        array2[0] = String.class;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "setParameters  :" + s);
        }
        invokeStaticMethod("android.media.AudioSystem", "setParameters", array, array2);
    }
    
    static void setPhoneState(final int n) {
        final Object[] array = { n };
        final Class[] array2 = new Class[array.length];
        array2[0] = Integer.TYPE;
        invokeStaticMethod("android.media.AudioSystem", "setPhoneState", array, array2);
    }
    
    static void setForceUse(final int n, final int n2) {
        final Object[] array = { n, n2 };
        final Class[] array2 = new Class[array.length];
        array2[0] = Integer.TYPE;
        array2[1] = Integer.TYPE;
        final Object invokeStaticMethod = invokeStaticMethod("android.media.AudioSystem", "setForceUse", array, array2);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "setForceUse  usage:" + n + " config:" + n2 + " ->" + getForceConfigName(n2) + " res:" + invokeStaticMethod);
        }
    }
    
    static int getForceUse(final int n) {
        Integer value = 0;
        final Object[] array = { n };
        final Class[] array2 = new Class[array.length];
        array2[0] = Integer.TYPE;
        final Object invokeStaticMethod = invokeStaticMethod("android.media.AudioSystem", "getForceUse", array, array2);
        if (invokeStaticMethod != null) {
            value = (Integer)invokeStaticMethod;
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "getForceUse  usage:" + n + " config:" + value + " ->" + getForceConfigName(value));
        }
        return value;
    }
    
    static void forceVolumeControlStream(final AudioManager audioManager, final int n) {
        if (Build.MANUFACTURER.equals("Google")) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "forceVolumeControlStream, Google phone nothing to do");
            }
            return;
        }
        final Object[] array = { n };
        final Class[] array2 = new Class[array.length];
        array2[0] = Integer.TYPE;
        final Object invokeMethod = invokeMethod(audioManager, "forceVolumeControlStream", array, array2);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "forceVolumeControlStream  streamType:" + n + " res:" + invokeMethod);
        }
    }
    
    static {
        TraeAudioManager.IsMusicScene = false;
        TraeAudioManager.IsEarPhoneSupported = false;
        TraeAudioManager.IsUpdateSceneFlag = false;
        TraeAudioManager.enableDeviceSwitchFlag = true;
        TraeAudioManager._glock = new ReentrantLock();
        TraeAudioManager._ginstance = null;
        TraeAudioManager._gHostProcessId = -1;
        forceName = new String[] { "FORCE_NONE", "FORCE_SPEAKER", "FORCE_HEADPHONES", "FORCE_BT_SCO", "FORCE_BT_A2DP", "FORCE_WIRED_ACCESSORY", "FORCE_BT_CAR_DOCK", "FORCE_BT_DESK_DOCK", "FORCE_ANALOG_DOCK", "FORCE_NO_BT_A2DP", "FORCE_DIGITAL_DOCK" };
    }
    
    public class Parameters
    {
        public static final String CONTEXT = "com.tencent.sharp.TraeAudioManager.Parameters.CONTEXT";
        public static final String MODEPOLICY = "com.tencent.sharp.TraeAudioManager.Parameters.MODEPOLICY";
        public static final String BLUETOOTHPOLICY = "com.tencent.sharp.TraeAudioManager.Parameters.BLUETOOTHPOLICY";
        public static final String DEVICECONFIG = "com.tencent.sharp.TraeAudioManager.Parameters.DEVICECONFIG";
    }
    
    class DeviceConfigManager
    {
        HashMap<String, DeviceConfig> deviceConfigs;
        String prevConnectedDevice;
        String connectedDevice;
        String connectingDevice;
        ReentrantLock mLock;
        boolean visiableUpdate;
        String _bluetoothDevName;
        
        public DeviceConfigManager() {
            this.deviceConfigs = new HashMap<String, DeviceConfig>();
            this.prevConnectedDevice = "DEVICE_NONE";
            this.connectedDevice = "DEVICE_NONE";
            this.connectingDevice = "DEVICE_NONE";
            this.mLock = new ReentrantLock();
            this.visiableUpdate = false;
            this._bluetoothDevName = "unknow";
        }
        
        public boolean init(String s) {
            AudioDeviceInterface.LogTraceEntry(" strConfigs:" + s);
            if (null == s || s.length() <= 0) {
                return false;
            }
            s = s.replace("\n", "");
            s = s.replace("\r", "");
            if (null == s || s.length() <= 0) {
                return false;
            }
            if (0 > s.indexOf(";")) {
                s += ";";
            }
            final String[] split = s.split(";");
            if (null == split || 1 > split.length) {
                return false;
            }
            this.mLock.lock();
            for (int i = 0; i < split.length; ++i) {
                this._addConfig(split[i], i);
            }
            this.mLock.unlock();
            TraeAudioManager.this.printDevices();
            return true;
        }
        
        boolean _addConfig(final String s, final int n) {
            AudioDeviceInterface.LogTraceEntry(" devName:" + s + " priority:" + n);
            final DeviceConfig deviceConfig = new DeviceConfig();
            if (!deviceConfig.init(s, n)) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " err dev init!");
                }
                return false;
            }
            if (this.deviceConfigs.containsKey(s)) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "err dev exist!");
                }
                return false;
            }
            this.deviceConfigs.put(s, deviceConfig);
            this.visiableUpdate = true;
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " n" + this.getDeviceNumber() + " 0:" + this.getDeviceName(0));
            }
            AudioDeviceInterface.LogTraceExit();
            return true;
        }
        
        public void clearConfig() {
            this.mLock.lock();
            this.deviceConfigs.clear();
            this.prevConnectedDevice = "DEVICE_NONE";
            this.connectedDevice = "DEVICE_NONE";
            this.connectingDevice = "DEVICE_NONE";
            this.mLock.unlock();
        }
        
        public boolean getVisiableUpdateFlag() {
            this.mLock.lock();
            final boolean visiableUpdate = this.visiableUpdate;
            this.mLock.unlock();
            return visiableUpdate;
        }
        
        public void resetVisiableUpdateFlag() {
            this.mLock.lock();
            this.visiableUpdate = false;
            this.mLock.unlock();
        }
        
        public boolean setVisible(final String s, final boolean visible) {
            boolean b = false;
            this.mLock.lock();
            final DeviceConfig deviceConfig = this.deviceConfigs.get(s);
            if (deviceConfig != null && deviceConfig.getVisible() != visible) {
                deviceConfig.setVisible(visible);
                this.visiableUpdate = true;
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, " ++setVisible:" + s + (visible ? " Y" : " N"));
                }
                b = true;
            }
            this.mLock.unlock();
            return b;
        }
        
        public void setBluetoothName(final String bluetoothDevName) {
            if (bluetoothDevName == null) {
                this._bluetoothDevName = "unknow";
                return;
            }
            if (bluetoothDevName.isEmpty()) {
                this._bluetoothDevName = "unknow";
            }
            else {
                this._bluetoothDevName = bluetoothDevName;
            }
        }
        
        public String getBluetoothName() {
            return this._bluetoothDevName;
        }
        
        public boolean getVisible(final String s) {
            boolean visible = false;
            this.mLock.lock();
            final DeviceConfig deviceConfig = this.deviceConfigs.get(s);
            if (null != deviceConfig) {
                visible = deviceConfig.getVisible();
            }
            this.mLock.unlock();
            return visible;
        }
        
        public int getPriority(final String s) {
            int priority = -1;
            this.mLock.lock();
            final DeviceConfig deviceConfig = this.deviceConfigs.get(s);
            if (null != deviceConfig) {
                priority = deviceConfig.getPriority();
            }
            this.mLock.unlock();
            return priority;
        }
        
        public int getDeviceNumber() {
            this.mLock.lock();
            final int size = this.deviceConfigs.size();
            this.mLock.unlock();
            return size;
        }
        
        public String getDeviceName(final int n) {
            String deviceName = "DEVICE_NONE";
            int n2 = 0;
            this.mLock.lock();
            DeviceConfig deviceConfig = null;
            for (final Map.Entry<String, DeviceConfig> entry : this.deviceConfigs.entrySet()) {
                if (n2 == n) {
                    deviceConfig = entry.getValue();
                    break;
                }
                ++n2;
            }
            if (deviceConfig != null) {
                deviceName = deviceConfig.getDeviceName();
            }
            this.mLock.unlock();
            return deviceName;
        }
        
        public String getAvailabledHighestPriorityDevice(final String s) {
            DeviceConfig deviceConfig = null;
            this.mLock.lock();
            for (final Map.Entry<String, DeviceConfig> entry : this.deviceConfigs.entrySet()) {
                entry.getKey();
                entry.getValue();
                final DeviceConfig deviceConfig2 = entry.getValue();
                if (deviceConfig2 == null) {
                    continue;
                }
                if (!deviceConfig2.getVisible()) {
                    continue;
                }
                if (deviceConfig2.getDeviceName().equals(s)) {
                    continue;
                }
                if (deviceConfig == null) {
                    deviceConfig = deviceConfig2;
                }
                else {
                    if (deviceConfig2.getPriority() < deviceConfig.getPriority()) {
                        continue;
                    }
                    deviceConfig = deviceConfig2;
                }
            }
            this.mLock.unlock();
            return (deviceConfig != null) ? deviceConfig.getDeviceName() : "DEVICE_SPEAKERPHONE";
        }
        
        public String getAvailabledHighestPriorityDevice() {
            DeviceConfig deviceConfig = null;
            this.mLock.lock();
            for (final Map.Entry<String, DeviceConfig> entry : this.deviceConfigs.entrySet()) {
                entry.getKey();
                entry.getValue();
                final DeviceConfig deviceConfig2 = entry.getValue();
                if (deviceConfig2 == null) {
                    continue;
                }
                if (!deviceConfig2.getVisible()) {
                    continue;
                }
                if (deviceConfig == null) {
                    deviceConfig = deviceConfig2;
                }
                else {
                    if (deviceConfig2.getPriority() < deviceConfig.getPriority()) {
                        continue;
                    }
                    deviceConfig = deviceConfig2;
                }
            }
            this.mLock.unlock();
            return (deviceConfig != null) ? deviceConfig.getDeviceName() : "DEVICE_SPEAKERPHONE";
        }
        
        public String getConnectingDevice() {
            this.mLock.lock();
            String connectingDevice = null;
            final DeviceConfig deviceConfig = this.deviceConfigs.get(this.connectingDevice);
            if (null != deviceConfig && deviceConfig.getVisible()) {
                connectingDevice = this.connectingDevice;
            }
            this.mLock.unlock();
            return connectingDevice;
        }
        
        public String getConnectedDevice() {
            this.mLock.lock();
            final String getConnectedDevice = this._getConnectedDevice();
            this.mLock.unlock();
            return getConnectedDevice;
        }
        
        public String getPrevConnectedDevice() {
            this.mLock.lock();
            final String getPrevConnectedDevice = this._getPrevConnectedDevice();
            this.mLock.unlock();
            return getPrevConnectedDevice;
        }
        
        public boolean setConnecting(final String connectingDevice) {
            boolean b = false;
            this.mLock.lock();
            final DeviceConfig deviceConfig = this.deviceConfigs.get(connectingDevice);
            if (deviceConfig != null && deviceConfig.getVisible()) {
                this.connectingDevice = connectingDevice;
                b = true;
            }
            this.mLock.unlock();
            return b;
        }
        
        public boolean setConnected(final String connectedDevice) {
            boolean b = false;
            this.mLock.lock();
            final DeviceConfig deviceConfig = this.deviceConfigs.get(connectedDevice);
            if (deviceConfig != null && deviceConfig.getVisible()) {
                if (this.connectedDevice != null && !this.connectedDevice.equals(connectedDevice)) {
                    this.prevConnectedDevice = this.connectedDevice;
                }
                this.connectedDevice = connectedDevice;
                this.connectingDevice = "";
                b = true;
            }
            this.mLock.unlock();
            return b;
        }
        
        public boolean isConnected(final String s) {
            boolean equals = false;
            this.mLock.lock();
            final DeviceConfig deviceConfig = this.deviceConfigs.get(s);
            if (deviceConfig != null && deviceConfig.getVisible()) {
                equals = this.connectedDevice.equals(s);
            }
            this.mLock.unlock();
            return equals;
        }
        
        public HashMap<String, Object> getSnapParams() {
            final HashMap<String, ArrayList<String>> hashMap = (HashMap<String, ArrayList<String>>)new HashMap<String, String>();
            this.mLock.lock();
            hashMap.put("EXTRA_DATA_AVAILABLEDEVICE_LIST", (String)this._getAvailableDeviceList());
            hashMap.put("EXTRA_DATA_CONNECTEDDEVICE", this._getConnectedDevice());
            hashMap.put("EXTRA_DATA_PREV_CONNECTEDDEVICE", this._getPrevConnectedDevice());
            this.mLock.unlock();
            return (HashMap<String, Object>)hashMap;
        }
        
        public ArrayList<String> getAvailableDeviceList() {
            final ArrayList list = new ArrayList();
            this.mLock.lock();
            final ArrayList<String> getAvailableDeviceList = this._getAvailableDeviceList();
            this.mLock.unlock();
            return getAvailableDeviceList;
        }
        
        ArrayList<String> _getAvailableDeviceList() {
            final ArrayList<String> list = new ArrayList<String>();
            final Iterator<Map.Entry<String, DeviceConfig>> iterator = this.deviceConfigs.entrySet().iterator();
            while (iterator.hasNext()) {
                final DeviceConfig deviceConfig = iterator.next().getValue();
                if (deviceConfig == null) {
                    continue;
                }
                if (!deviceConfig.getVisible()) {
                    continue;
                }
                list.add(deviceConfig.getDeviceName());
            }
            return list;
        }
        
        String _getConnectedDevice() {
            String connectedDevice = "DEVICE_NONE";
            final DeviceConfig deviceConfig = this.deviceConfigs.get(this.connectedDevice);
            if (null != deviceConfig && deviceConfig.getVisible()) {
                connectedDevice = this.connectedDevice;
            }
            return connectedDevice;
        }
        
        String _getPrevConnectedDevice() {
            String prevConnectedDevice = "DEVICE_NONE";
            final DeviceConfig deviceConfig = this.deviceConfigs.get(this.prevConnectedDevice);
            if (null != deviceConfig && deviceConfig.getVisible()) {
                prevConnectedDevice = this.prevConnectedDevice;
            }
            return prevConnectedDevice;
        }
        
        public class DeviceConfig
        {
            String deviceName;
            boolean visible;
            int priority;
            
            public DeviceConfig() {
                this.deviceName = "DEVICE_NONE";
                this.visible = false;
                this.priority = 0;
            }
            
            public boolean init(final String deviceName, final int priority) {
                if (null == deviceName || deviceName.length() <= 0) {
                    return false;
                }
                if (!TraeAudioManager.checkDevName(deviceName)) {
                    return false;
                }
                this.deviceName = deviceName;
                this.priority = priority;
                return true;
            }
            
            public String getDeviceName() {
                return this.deviceName;
            }
            
            public boolean getVisible() {
                return this.visible;
            }
            
            public int getPriority() {
                return this.priority;
            }
            
            public void setVisible(final boolean visible) {
                this.visible = visible;
            }
        }
    }
    
    class TraeAudioManagerLooper extends Thread
    {
        public static final int MESSAGE_BEGIN = 32768;
        public static final int MESSAGE_ENABLE = 32772;
        public static final int MESSAGE_DISABLE = 32773;
        public static final int MESSAGE_GETDEVICELIST = 32774;
        public static final int MESSAGE_CONNECTDEVICE = 32775;
        public static final int MESSAGE_EARACTION = 32776;
        public static final int MESSAGE_ISDEVICECHANGABLED = 32777;
        public static final int MESSAGE_GETCONNECTEDDEVICE = 32778;
        public static final int MESSAGE_GETCONNECTINGDEVICE = 32779;
        public static final int MESSAGE_VOICECALLPREPROCESS = 32780;
        public static final int MESSAGE_VOICECALLPOSTPROCESS = 32781;
        public static final int MESSAGE_STARTRING = 32782;
        public static final int MESSAGE_STOPRING = 32783;
        public static final int MESSAGE_GETSTREAMTYPE = 32784;
        public static final int MESSAGE_AUTO_DEVICELIST_UPDATE = 32785;
        public static final int MESSAGE_AUTO_DEVICELIST_PLUGIN_UPDATE = 32786;
        public static final int MESSAGE_AUTO_DEVICELIST_PLUGOUT_UPDATE = 32787;
        public static final int MESSAGE_VOICECALL_AUIDOPARAM_CHANGED = 32788;
        public static final int MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE = 32789;
        public static final int MESSAGE_REQUEST_RELEASE_AUDIO_FOCUS = 32790;
        public static final int MESSAGE_RECOVER_AUDIO_FOCUS = 32791;
        Handler mMsgHandler;
        TraeMediaPlayer _ringPlayer;
        long _ringSessionID;
        String _ringOperation;
        String _ringUserdata;
        final boolean[] _started;
        boolean _enabled;
        TraeAudioManager _parent;
        String _lastCfg;
        int _preServiceMode;
        int _preRingMode;
        long _voiceCallSessionID;
        String _voiceCallOperation;
        AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;
        int _focusSteamType;
        
        public TraeAudioManagerLooper(final TraeAudioManager parent) {
            this.mMsgHandler = null;
            this._ringPlayer = null;
            this._ringSessionID = -1L;
            this._ringOperation = "";
            this._ringUserdata = "";
            this._started = new boolean[] { false };
            this._enabled = false;
            this._parent = null;
            this._lastCfg = "";
            this._preServiceMode = 0;
            this._preRingMode = 0;
            this._voiceCallSessionID = -1L;
            this._voiceCallOperation = "";
            this.mAudioFocusChangeListener = null;
            this._focusSteamType = 0;
            this._parent = parent;
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "TraeAudioManagerLooper start...");
            }
            this.start();
            synchronized (this._started) {
                if (!this._started[0]) {
                    try {
                        this._started.wait(3000L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "  start used:" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms");
            }
        }
        
        public void quit() {
            AudioDeviceInterface.LogTraceEntry("");
            if (null == this.mMsgHandler) {
                return;
            }
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mMsgHandler.getLooper().quit();
            synchronized (this._started) {
                if (this._started[0]) {
                    try {
                        this._started.wait(10000L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "  quit used:" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms");
            }
            this.mMsgHandler = null;
            AudioDeviceInterface.LogTraceExit();
        }
        
        public int sendMessage(final int n, final HashMap<String, Object> hashMap) {
            if (null == this.mMsgHandler) {
                AudioDeviceInterface.LogTraceEntry(" fail mMsgHandler==null _enabled:" + (this._enabled ? "Y" : "N") + " activeMode:" + TraeAudioManager.this._activeMode + " msg:" + n);
                return -1;
            }
            return this.mMsgHandler.sendMessage(Message.obtain(this.mMsgHandler, n, (Object)hashMap)) ? 0 : -1;
        }
        
        void startService(final HashMap<String, Object> hashMap) {
            final String lastCfg = hashMap.get("EXTRA_DATA_DEVICECONFIG");
            Log.w("TRAE", "startService cfg:" + lastCfg);
            AudioDeviceInterface.LogTraceEntry(" _enabled:" + (this._enabled ? "Y" : "N") + " activeMode:" + TraeAudioManager.this._activeMode + " cfg:" + lastCfg);
            if (TraeAudioManager.this._context == null) {
                return;
            }
            QLog.w("TRAE", 2, "   startService:" + lastCfg);
            if ((this._enabled && this._lastCfg.equals(lastCfg)) || TraeAudioManager.this._activeMode != 0) {
                return;
            }
            if (this._enabled) {
                this.stopService();
            }
            this._prev_startService();
            final AudioManager audioManager = (AudioManager)TraeAudioManager.this._context.getSystemService("audio");
            TraeAudioManager.this._deviceConfigManager.clearConfig();
            TraeAudioManager.this._deviceConfigManager.init(lastCfg);
            this._lastCfg = lastCfg;
            if (TraeAudioManager.this._am != null) {
                this._preServiceMode = TraeAudioManager.this._am.getMode();
            }
            this._enabled = true;
            if (this._ringPlayer == null) {
                this._ringPlayer = new TraeMediaPlayer(TraeAudioManager.this._context, new TraeMediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion() {
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, "_ringPlayer onCompletion _activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + TraeAudioManagerLooper.this._preRingMode);
                        }
                        final HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("PARAM_ISHOSTSIDE", true);
                        TraeAudioManagerLooper.this.sendMessage(32783, hashMap);
                        TraeAudioManagerLooper.this.notifyRingCompletion();
                    }
                });
            }
            this.notifyServiceState(this._enabled);
            TraeAudioManager.this.updateDeviceStatus();
            AudioDeviceInterface.LogTraceExit();
        }
        
        boolean isNeedForceVolumeType() {
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
            return false;
        }
        
        void stopService() {
            AudioDeviceInterface.LogTraceEntry(" _enabled:" + (this._enabled ? "Y" : "N") + " activeMode:" + TraeAudioManager.this._activeMode);
            if (!this._enabled) {
                return;
            }
            if (TraeAudioManager.this._activeMode == 1) {
                this.interruptVoicecallPostprocess();
            }
            else if (TraeAudioManager.this._activeMode == 2) {
                this.interruptRing();
            }
            if (TraeAudioManager.this._switchThread != null) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "_switchThread:" + TraeAudioManager.this._switchThread.getDeviceName());
                }
                TraeAudioManager.this._switchThread.quit();
                TraeAudioManager.this._switchThread = null;
            }
            if (this._ringPlayer != null) {
                this._ringPlayer.stopRing();
            }
            this._ringPlayer = null;
            this.notifyServiceState(this._enabled = false);
            if (TraeAudioManager.this._am != null && TraeAudioManager.this._context != null) {
                try {
                    TraeAudioManager.this.InternalSetMode(0);
                    if (this.isNeedForceVolumeType()) {
                        QLog.w("TRAE", 2, "NeedForceVolumeType: AudioManager.STREAM_MUSIC");
                        TraeAudioManager.forceVolumeControlStream(TraeAudioManager.this._am, 3);
                    }
                }
                catch (Exception ex) {
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "set mode failed." + ex.getMessage());
                    }
                }
            }
            this._post_stopService();
            AudioDeviceInterface.LogTraceExit();
        }
        
        int notifyServiceState(final boolean b) {
            if (null == TraeAudioManager.this._context) {
                return -1;
            }
            final Intent intent = new Intent();
            intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY");
            intent.putExtra("PARAM_OPERATION", "NOTIFY_SERVICE_STATE");
            intent.putExtra("NOTIFY_SERVICE_STATE_DATE", b);
            if (TraeAudioManager.this._context != null) {
                TraeAudioManager.this._context.sendBroadcast(intent);
            }
            return 0;
        }
        
        @Override
        public void run() {
            AudioDeviceInterface.LogTraceEntry("");
            Looper.prepare();
            this.mMsgHandler = new Handler() {
                public void handleMessage(final Message message) {
                    final boolean b = true;
                    HashMap<String, String> hashMap = null;
                    try {
                        hashMap = (HashMap<String, String>)message.obj;
                    }
                    catch (Exception ex) {}
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "TraeAudioManagerLooper msg:" + message.what + " _enabled:" + (TraeAudioManagerLooper.this._enabled ? "Y" : "N"));
                    }
                    if (message.what == 32772) {
                        TraeAudioManagerLooper.this.startService((HashMap<String, Object>)hashMap);
                    }
                    else if (!TraeAudioManagerLooper.this._enabled) {
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, "******* disabled ,skip msg******");
                        }
                        TraeAudioManager.this.sendResBroadcast(new Intent(), (HashMap<String, Object>)hashMap, 1);
                    }
                    else {
                        switch (message.what) {
                            case 32773: {
                                TraeAudioManagerLooper.this.stopService();
                                break;
                            }
                            case 32774: {
                                TraeAudioManagerLooper.this.InternalSessionGetDeviceList((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32777: {
                                TraeAudioManager.this.InternalSessionIsDeviceChangabled((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32778: {
                                TraeAudioManager.this.InternalSessionGetConnectedDevice((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32779: {
                                TraeAudioManager.this.InternalSessionGetConnectingDevice((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32780: {
                                TraeAudioManagerLooper.this.InternalVoicecallPreprocess((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32781: {
                                TraeAudioManagerLooper.this.InternalVoicecallPostprocess((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32788: {
                                final Integer n = (Integer)hashMap.get("PARAM_STREAMTYPE");
                                if (n != null) {
                                    TraeAudioManager.this._streamType = n;
                                    TraeAudioManagerLooper.this.InternalNotifyStreamTypeUpdate(n);
                                    break;
                                }
                                if (QLog.isColorLevel()) {
                                    QLog.e("TRAE", 2, " MESSAGE_VOICECALL_AUIDOPARAM_CHANGED params.get(PARAM_STREAMTYPE)==null!!");
                                    break;
                                }
                                break;
                            }
                            case 32782: {
                                TraeAudioManagerLooper.this.InternalStartRing((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32783: {
                                TraeAudioManagerLooper.this.InternalStopRing((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32790: {
                                TraeAudioManagerLooper.this.abandonAudioFocus();
                            }
                            case 32784: {
                                TraeAudioManagerLooper.this.InternalGetStreamType((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32775: {
                                TraeAudioManager.this.InternalSessionConnectDevice((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32776: {
                                TraeAudioManager.this.InternalSessionEarAction((HashMap<String, Object>)hashMap);
                                break;
                            }
                            case 32785:
                            case 32789: {
                                final String availabledHighestPriorityDevice = TraeAudioManager.this._deviceConfigManager.getAvailabledHighestPriorityDevice();
                                final String connectedDevice = TraeAudioManager.this._deviceConfigManager.getConnectedDevice();
                                if (QLog.isColorLevel()) {
                                    QLog.w("TRAE", 2, "MESSAGE_AUTO_DEVICELIST_UPDATE  connectedDev:" + connectedDevice + " highestDev" + availabledHighestPriorityDevice);
                                }
                                if (TraeAudioManager.IsUpdateSceneFlag && b) {
                                    if (TraeAudioManager.IsMusicScene && !TraeAudioManager.this.IsBluetoothA2dpExisted) {
                                        TraeAudioManager.this.InternalConnectDevice(TraeAudioManager.this._deviceConfigManager.getAvailabledHighestPriorityDevice("DEVICE_BLUETOOTHHEADSET"), null, true);
                                        break;
                                    }
                                    TraeAudioManager.this.InternalConnectDevice(availabledHighestPriorityDevice, null, true);
                                    break;
                                }
                                else {
                                    if (!availabledHighestPriorityDevice.equals(connectedDevice)) {
                                        TraeAudioManager.this.InternalConnectDevice(availabledHighestPriorityDevice, null, false);
                                        break;
                                    }
                                    TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                                    break;
                                }
                                break;
                            }
                            case 32786: {
                                final String s = hashMap.get("PARAM_DEVICE");
                                if (TraeAudioManager.this.InternalConnectDevice(s, null, false) != 0) {
                                    if (QLog.isColorLevel()) {
                                        QLog.w("TRAE", 2, " plugin dev:" + s + " sessionConnectedDev:" + TraeAudioManager.this.sessionConnectedDev + " connected fail,auto switch!");
                                    }
                                    TraeAudioManager.this.InternalConnectDevice(TraeAudioManager.this._deviceConfigManager.getAvailabledHighestPriorityDevice(), null, false);
                                    break;
                                }
                                break;
                            }
                            case 32787: {
                                if (TraeAudioManager.this.InternalConnectDevice(TraeAudioManager.this.sessionConnectedDev, null, false) != 0) {
                                    final String s2 = hashMap.get("PARAM_DEVICE");
                                    if (QLog.isColorLevel()) {
                                        QLog.w("TRAE", 2, " plugout dev:" + s2 + " sessionConnectedDev:" + TraeAudioManager.this.sessionConnectedDev + " connected fail,auto switch!");
                                    }
                                    TraeAudioManager.this.InternalConnectDevice(TraeAudioManager.this._deviceConfigManager.getAvailabledHighestPriorityDevice(), null, false);
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            };
            this._init();
            synchronized (this._started) {
                this._started[0] = true;
                this._started.notifyAll();
            }
            Looper.loop();
            this._uninit();
            synchronized (this._started) {
                this._started[0] = false;
                this._started.notifyAll();
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        void _init() {
            AudioDeviceInterface.LogTraceEntry("");
            try {
                TraeAudioManager.this._audioSessionHost = new TraeAudioSessionHost();
                TraeAudioManager.this._deviceConfigManager = new DeviceConfigManager();
                TraeAudioManager._gHostProcessId = Process.myPid();
                TraeAudioManager.this._am = (AudioManager)TraeAudioManager.this._context.getSystemService("audio");
                TraeAudioManager.this._bluetoothCheck = TraeAudioManager.this.CreateBluetoothCheck(TraeAudioManager.this._context, TraeAudioManager.this._deviceConfigManager);
                final IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                TraeAudioManager.this._bluetoothCheck.addAction(intentFilter);
                intentFilter.addAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
                TraeAudioManager.this._context.registerReceiver((BroadcastReceiver)this._parent, intentFilter);
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "======7");
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        void _prev_startService() {
            try {
                TraeAudioManager.this._am = (AudioManager)TraeAudioManager.this._context.getSystemService("audio");
                if (TraeAudioManager.this._bluetoothCheck == null) {
                    TraeAudioManager.this._bluetoothCheck = TraeAudioManager.this.CreateBluetoothCheck(TraeAudioManager.this._context, TraeAudioManager.this._deviceConfigManager);
                }
                TraeAudioManager.this._context.unregisterReceiver((BroadcastReceiver)this._parent);
                final IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                TraeAudioManager.this._bluetoothCheck.addAction(intentFilter);
                intentFilter.addAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
                TraeAudioManager.this._context.registerReceiver((BroadcastReceiver)this._parent, intentFilter);
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "======7");
                }
            }
        }
        
        void _post_stopService() {
            try {
                if (TraeAudioManager.this._bluetoothCheck != null) {
                    TraeAudioManager.this._bluetoothCheck.release();
                }
                TraeAudioManager.this._bluetoothCheck = null;
                if (TraeAudioManager.this._context != null) {
                    TraeAudioManager.this._context.unregisterReceiver((BroadcastReceiver)this._parent);
                    final IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
                    TraeAudioManager.this._context.registerReceiver((BroadcastReceiver)this._parent, intentFilter);
                }
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "stop service failed." + ex.getMessage());
                }
            }
        }
        
        void _uninit() {
            AudioDeviceInterface.LogTraceEntry("");
            try {
                this.stopService();
                if (TraeAudioManager.this._bluetoothCheck != null) {
                    TraeAudioManager.this._bluetoothCheck.release();
                }
                TraeAudioManager.this._bluetoothCheck = null;
                if (null != TraeAudioManager.this._context) {
                    TraeAudioManager.this._context.unregisterReceiver((BroadcastReceiver)this._parent);
                    TraeAudioManager.this._context = null;
                }
                if (TraeAudioManager.this._deviceConfigManager != null) {
                    TraeAudioManager.this._deviceConfigManager.clearConfig();
                }
                TraeAudioManager.this._deviceConfigManager = null;
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "uninit failed." + ex.getMessage());
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        int InternalSessionGetDeviceList(final HashMap<String, Object> hashMap) {
            final Intent intent = new Intent();
            final HashMap<String, Object> snapParams = TraeAudioManager.this._deviceConfigManager.getSnapParams();
            final ArrayList list = snapParams.get("EXTRA_DATA_AVAILABLEDEVICE_LIST");
            final String s = snapParams.get("EXTRA_DATA_CONNECTEDDEVICE");
            final String s2 = snapParams.get("EXTRA_DATA_PREV_CONNECTEDDEVICE");
            intent.putExtra("EXTRA_DATA_AVAILABLEDEVICE_LIST", (String[])list.toArray(new String[0]));
            intent.putExtra("EXTRA_DATA_CONNECTEDDEVICE", s);
            intent.putExtra("EXTRA_DATA_PREV_CONNECTEDDEVICE", s2);
            intent.putExtra("EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME", TraeAudioManager.this._deviceConfigManager.getBluetoothName());
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
            return 0;
        }
        
        @TargetApi(8)
        void abandonAudioFocus() {
        }
        
        int InternalVoicecallPreprocess(final HashMap<String, Object> hashMap) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            if (null == hashMap) {
                return -1;
            }
            if (TraeAudioManager.this._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " InternalVoicecallPreprocess am==null!!");
                }
                return -1;
            }
            if (TraeAudioManager.this._activeMode == 1) {
                TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 2);
                return -1;
            }
            this._voiceCallSessionID = hashMap.get("PARAM_SESSIONID");
            this._voiceCallOperation = (String)hashMap.get("PARAM_OPERATION");
            TraeAudioManager.this._activeMode = 1;
            TraeAudioManager.this._prevMode = TraeAudioManager.this._am.getMode();
            -1;
            0;
            final Integer n = (Object)hashMap.get("PARAM_MODEPOLICY");
            if (n == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " params.get(PARAM_MODEPOLICY)==null!!");
                }
                TraeAudioManager.this._modePolicy = -1;
            }
            else {
                TraeAudioManager.this._modePolicy = n;
            }
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "  _modePolicy:" + TraeAudioManager.this._modePolicy);
            }
            final Integer n2 = (Object)hashMap.get("PARAM_STREAMTYPE");
            if (n2 == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " params.get(PARAM_STREAMTYPE)==null!!");
                }
                TraeAudioManager.this._streamType = 0;
            }
            else {
                TraeAudioManager.this._streamType = n2;
            }
            if (TraeAudioManager.isCloseSystemAPM(TraeAudioManager.this._modePolicy) && TraeAudioManager.this._activeMode != 2 && TraeAudioManager.this._deviceConfigManager != null) {
                if (TraeAudioManager.this._deviceConfigManager.getConnectedDevice().equals("DEVICE_SPEAKERPHONE")) {
                    TraeAudioManager.this.InternalSetMode(0);
                }
                else {
                    TraeAudioManager.this.InternalSetMode(3);
                }
            }
            else {
                TraeAudioManager.this.InternalSetMode(TraeAudioManager.getCallAudioMode(TraeAudioManager.this._modePolicy));
            }
            TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 0);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        
        int InternalVoicecallPostprocess(final HashMap<String, Object> hashMap) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            if (TraeAudioManager.this._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " InternalVoicecallPostprocess am==null!!");
                }
                return -1;
            }
            if (TraeAudioManager.this._activeMode != 1) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " not ACTIVE_VOICECALL!!");
                }
                TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 3);
                return -1;
            }
            TraeAudioManager.this._activeMode = 0;
            this.abandonAudioFocus();
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        
        int interruptVoicecallPostprocess() {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            if (TraeAudioManager.this._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " am==null!!");
                }
                return -1;
            }
            if (TraeAudioManager.this._activeMode != 1) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " not ACTIVE_RING!!");
                }
                return -1;
            }
            TraeAudioManager.this._activeMode = 0;
            if (TraeAudioManager.this._prevMode != -1) {
                TraeAudioManager.this.InternalSetMode(TraeAudioManager.this._prevMode);
            }
            final HashMap<String, Object> hashMap = (HashMap<String, Object>)new HashMap<String, Long>();
            hashMap.put("PARAM_SESSIONID", this._voiceCallSessionID);
            hashMap.put("PARAM_OPERATION", this._voiceCallOperation);
            TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 6);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        
        int InternalStartRing(final HashMap<String, Object> hashMap) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            if (TraeAudioManager.this._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " InternalStartRing am==null!!");
                }
                return -1;
            }
            if (TraeAudioManager.this._activeMode == 2) {
                this.interruptRing();
            }
            int intValue;
            int intValue2;
            Uri uri;
            String s;
            boolean booleanValue;
            int intValue3;
            boolean booleanValue2;
            try {
                this._ringSessionID = (Long)(Object)hashMap.get("PARAM_SESSIONID");
                this._ringOperation = (String)hashMap.get("PARAM_OPERATION");
                this._ringUserdata = (String)hashMap.get("PARAM_RING_USERDATA_STRING");
                intValue = (int)hashMap.get("PARAM_RING_DATASOURCE");
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "  dataSource:" + intValue);
                }
                intValue2 = (int)hashMap.get("PARAM_RING_RSID");
                uri = hashMap.get("PARAM_RING_URI");
                s = hashMap.get("PARAM_RING_FILEPATH");
                booleanValue = hashMap.get("PARAM_RING_LOOP");
                intValue3 = hashMap.get("PARAM_RING_LOOPCOUNT");
                booleanValue2 = hashMap.get("PARAM_RING_MODE");
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " startRing err params");
                }
                return -1;
            }
            if (TraeAudioManager.this._activeMode != 1) {
                TraeAudioManager.this._activeMode = 2;
            }
            final Intent intent = new Intent();
            intent.putExtra("PARAM_RING_USERDATA_STRING", this._ringUserdata);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
            this._preRingMode = TraeAudioManager.this._am.getMode();
            this._ringPlayer.playRing(intValue, intValue2, uri, s, booleanValue, intValue3, booleanValue2, TraeAudioManager.this._activeMode == 1, TraeAudioManager.this._streamType);
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " _ringUserdata:" + this._ringUserdata + " DurationMS:" + this._ringPlayer.getDuration());
            }
            this.InternalNotifyStreamTypeUpdate(this._ringPlayer.getStreamType());
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        
        int InternalStopRing(final HashMap<String, Object> hashMap) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this._preRingMode);
            if (TraeAudioManager.this._am == null || this._ringPlayer == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " InternalStopRing am==null!!");
                }
                return -1;
            }
            this._ringPlayer.stopRing();
            if (!this._ringPlayer.hasCall() && TraeAudioManager.this._activeMode == 2) {
                this.abandonAudioFocus();
                TraeAudioManager.this._activeMode = 0;
            }
            final Intent intent = new Intent();
            intent.putExtra("PARAM_RING_USERDATA_STRING", this._ringUserdata);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        
        int InternalGetStreamType(final HashMap<String, Object> hashMap) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this._preRingMode);
            if (TraeAudioManager.this._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " InternalStopRing am==null!!");
                }
                return -1;
            }
            int n;
            if (TraeAudioManager.this._activeMode == 2) {
                n = this._ringPlayer.getStreamType();
            }
            else {
                n = TraeAudioManager.this._streamType;
            }
            final Intent intent = new Intent();
            intent.putExtra("EXTRA_DATA_STREAMTYPE", n);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        
        int InternalNotifyStreamTypeUpdate(final int n) {
            if (null == TraeAudioManager.this._context) {
                return -1;
            }
            new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                @Override
                public void run() {
                    final Intent intent = new Intent();
                    intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY");
                    intent.putExtra("PARAM_OPERATION", "NOTIFY_STREAMTYPE_UPDATE");
                    intent.putExtra("EXTRA_DATA_STREAMTYPE", n);
                    if (TraeAudioManager.this._context != null) {
                        TraeAudioManager.this._context.sendBroadcast(intent);
                    }
                }
            });
            return 0;
        }
        
        int interruptRing() {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this._preRingMode);
            if (TraeAudioManager.this._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " interruptRing am==null!!");
                }
                return -1;
            }
            if (TraeAudioManager.this._activeMode != 2) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " not ACTIVE_RING!!");
                }
                return -1;
            }
            this._ringPlayer.stopRing();
            this.abandonAudioFocus();
            TraeAudioManager.this._activeMode = 0;
            final HashMap<String, Object> hashMap = (HashMap<String, Object>)new HashMap<String, Long>();
            hashMap.put("PARAM_SESSIONID", this._ringSessionID);
            hashMap.put("PARAM_OPERATION", this._ringOperation);
            final Intent intent = new Intent();
            intent.putExtra("PARAM_RING_USERDATA_STRING", this._ringUserdata);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 4);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        
        void notifyRingCompletion() {
            final HashMap<String, Object> hashMap = (HashMap<String, Object>)new HashMap<String, Long>();
            hashMap.put("PARAM_SESSIONID", this._ringSessionID);
            hashMap.put("PARAM_OPERATION", "NOTIFY_RING_COMPLETION");
            final Intent intent = new Intent();
            intent.putExtra("PARAM_RING_USERDATA_STRING", this._ringUserdata);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
        }
    }
    
    abstract class switchThread extends Thread
    {
        boolean _running;
        boolean[] _exited;
        HashMap<String, Object> _params;
        long _usingtime;
        
        switchThread() {
            this._running = true;
            this._exited = new boolean[] { false };
            this._params = null;
            this._usingtime = 0L;
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " ++switchThread:" + this.getDeviceName());
            }
        }
        
        public void setDeviceConnectParam(final HashMap<String, Object> params) {
            this._params = params;
        }
        
        void updateStatus() {
            TraeAudioManager.this._deviceConfigManager.setConnected(this.getDeviceName());
            this.processDeviceConnectRes(0);
        }
        
        void processDeviceConnectRes(final int n) {
            TraeAudioManager.this.InternalNotifyDeviceChangableUpdate();
            AudioDeviceInterface.LogTraceEntry(this.getDeviceName() + " err:" + n);
            if (this._params == null) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                return;
            }
            TraeAudioManager.this.sessionConnectedDev = TraeAudioManager.this._deviceConfigManager.getConnectedDevice();
            final Long n2 = this._params.get("PARAM_SESSIONID");
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " sessonID:" + n2);
            }
            if (n2 == null || n2 == Long.MIN_VALUE) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "processDeviceConnectRes sid null,don't send res");
                }
                return;
            }
            final Intent intent = new Intent();
            intent.putExtra("CONNECTDEVICE_RESULT_DEVICENAME", (String)this._params.get("PARAM_DEVICE"));
            if (TraeAudioManager.this.sendResBroadcast(intent, this._params, n) == 0) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        @Override
        public void run() {
            AudioDeviceInterface.LogTraceEntry(this.getDeviceName());
            TraeAudioManager.this._deviceConfigManager.setConnecting(this.getDeviceName());
            TraeAudioManager.this.InternalNotifyDeviceChangableUpdate();
            this._run();
            synchronized (this._exited) {
                this._exited[0] = true;
                this._exited.notifyAll();
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        public void quit() {
            AudioDeviceInterface.LogTraceEntry(this.getDeviceName());
            this._running = false;
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " quit:" + this.getDeviceName() + " _running:" + this._running);
            }
            this.interrupt();
            this._quit();
            synchronized (this._exited) {
                if (!this._exited[0]) {
                    try {
                        this._exited.wait(10000L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        public abstract String getDeviceName();
        
        public abstract void _run();
        
        public abstract void _quit();
    }
    
    class earphoneSwitchThread extends switchThread
    {
        @Override
        public void _run() {
            int n = 0;
            if (TraeAudioManager.IsUpdateSceneFlag && TraeAudioManager.enableDeviceSwitchFlag) {
                TraeAudioManager.this.InternalSetSpeaker(TraeAudioManager.this._context, false);
            }
            this.updateStatus();
            if (!TraeAudioManager.IsUpdateSceneFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect earphone: do nothing");
                }
                return;
            }
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect earphone: disableDeviceSwitchFlag");
                }
                return;
            }
            while (this._running) {
                if (TraeAudioManager.this._am.isSpeakerphoneOn()) {
                    TraeAudioManager.this.InternalSetSpeaker(TraeAudioManager.this._context, false);
                }
                try {
                    Thread.sleep((n < 5) ? 1000L : 4000L);
                }
                catch (InterruptedException ex) {}
                ++n;
            }
        }
        
        @Override
        public String getDeviceName() {
            return "DEVICE_EARPHONE";
        }
        
        @Override
        public void _quit() {
        }
    }
    
    class speakerSwitchThread extends switchThread
    {
        @Override
        public void _run() {
            int n = 0;
            if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag && TraeAudioManager.enableDeviceSwitchFlag) {
                TraeAudioManager.this.InternalSetSpeaker(TraeAudioManager.this._context, true);
            }
            this.updateStatus();
            if (TraeAudioManager.IsMusicScene || !TraeAudioManager.IsUpdateSceneFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect speakerPhone: do nothing");
                }
                return;
            }
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect speakerPhone: disableDeviceSwitchFlag");
                }
                return;
            }
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " _run:" + this.getDeviceName() + " _running:" + this._running);
            }
            while (this._running) {
                if (!TraeAudioManager.this._am.isSpeakerphoneOn()) {
                    TraeAudioManager.this.InternalSetSpeaker(TraeAudioManager.this._context, true);
                }
                try {
                    Thread.sleep((n < 5) ? 1000L : 4000L);
                }
                catch (InterruptedException ex) {}
                ++n;
            }
        }
        
        @Override
        public String getDeviceName() {
            return "DEVICE_SPEAKERPHONE";
        }
        
        @Override
        public void _quit() {
        }
    }
    
    class headsetSwitchThread extends switchThread
    {
        @Override
        public void _run() {
            int n = 0;
            if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag && TraeAudioManager.enableDeviceSwitchFlag) {
                TraeAudioManager.this.InternalSetSpeaker(TraeAudioManager.this._context, false);
                TraeAudioManager.this._am.setWiredHeadsetOn(true);
            }
            this.updateStatus();
            if (TraeAudioManager.IsMusicScene || !TraeAudioManager.IsUpdateSceneFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect headset: do nothing");
                }
                return;
            }
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect headset: disableDeviceSwitchFlag");
                }
                return;
            }
            while (this._running) {
                if (TraeAudioManager.this._am.isSpeakerphoneOn()) {
                    TraeAudioManager.this.InternalSetSpeaker(TraeAudioManager.this._context, false);
                }
                try {
                    Thread.sleep((n < 5) ? 1000L : 4000L);
                }
                catch (InterruptedException ex) {}
                ++n;
            }
        }
        
        @Override
        public String getDeviceName() {
            return "DEVICE_WIREDHEADSET";
        }
        
        @Override
        public void _quit() {
        }
    }
    
    class bluetoothHeadsetSwitchThread extends switchThread
    {
        @Override
        public void _run() {
            if (TraeAudioManager.IsMusicScene || !TraeAudioManager.IsUpdateSceneFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect bluetoothHeadset: do nothing, IsMusicScene:" + TraeAudioManager.IsMusicScene + " ,IsUpdateSceneFlag:" + TraeAudioManager.IsUpdateSceneFlag);
                }
                this.updateStatus();
                return;
            }
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "connect bluetoothHeadset: disableDeviceSwitchFlag");
                }
                return;
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            final boolean b = false;
            if (!b) {
                this._startBluetoothSco();
            }
            int n = 0;
            while (this._running && n++ < 10 && !b) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "bluetoothHeadsetSwitchThread i:" + n + " sco:" + (TraeAudioManager.this._am.isBluetoothScoOn() ? "Y" : "N") + " :" + TraeAudioManager.this._deviceConfigManager.getBluetoothName());
                }
                if (TraeAudioManager.this._am.isBluetoothScoOn()) {
                    this.updateStatus();
                    break;
                }
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex2) {}
            }
            if (!TraeAudioManager.this._am.isBluetoothScoOn()) {
                if (QLog.isColorLevel() && !b) {
                    QLog.e("TRAE", 2, "bluetoothHeadsetSwitchThread sco fail,remove btheadset");
                }
                TraeAudioManager.this._deviceConfigManager.setVisible(this.getDeviceName(), false);
                this.processDeviceConnectRes(10);
                TraeAudioManager.this.checkAutoDeviceListUpdate();
            }
        }
        
        @Override
        public String getDeviceName() {
            return "DEVICE_BLUETOOTHHEADSET";
        }
        
        @TargetApi(8)
        @Override
        public void _quit() {
            if (TraeAudioManager.this._am == null) {
                return;
            }
            this._stopBluetoothSco();
        }
        
        @TargetApi(8)
        void _startBluetoothSco() {
            TraeAudioManager.this._am.setBluetoothScoOn(true);
            if (Build.VERSION.SDK_INT > 8) {
                TraeAudioManager.this._am.startBluetoothSco();
            }
        }
        
        @TargetApi(8)
        void _stopBluetoothSco() {
            if (Build.VERSION.SDK_INT > 8) {
                TraeAudioManager.this._am.stopBluetoothSco();
            }
            TraeAudioManager.this._am.setBluetoothScoOn(false);
        }
    }
    
    abstract class BluetoohHeadsetCheckInterface
    {
        public abstract String interfaceDesc();
        
        public abstract boolean init(final Context p0, final DeviceConfigManager p1);
        
        public abstract void release();
        
        public abstract boolean isConnected();
        
        public void addAction(final IntentFilter intentFilter) {
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
            this._addAction(intentFilter);
        }
        
        abstract void _addAction(final IntentFilter p0);
        
        abstract void _onReceive(final Context p0, final Intent p1);
        
        public void onReceive(final Context context, final Intent intent, final DeviceConfigManager deviceConfigManager) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                final int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                final int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -1);
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BT ACTION_STATE_CHANGED|   EXTRA_STATE " + this.getBTActionStateChangedExtraString(intExtra));
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BT ACTION_STATE_CHANGED|   EXTRA_PREVIOUS_STATE " + this.getBTActionStateChangedExtraString(intExtra2));
                }
                if (intExtra == 10) {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "    BT off");
                    }
                    deviceConfigManager.setVisible("DEVICE_BLUETOOTHHEADSET", false);
                }
                else if (intExtra == 12 && QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BT OFF-->ON,Visiable it...");
                }
            }
            else if (!"android.bluetooth.device.action.ACL_CONNECTED".equals(intent.getAction()) || Build.VERSION.SDK_INT >= 11) {
                if (!"android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction()) || Build.VERSION.SDK_INT >= 11) {
                    this._onReceive(context, intent);
                }
            }
        }
        
        String getBTActionStateChangedExtraString(final int n) {
            String s = null;
            switch (n) {
                case 10: {
                    s = "STATE_OFF";
                    break;
                }
                case 11: {
                    s = "STATE_TURNING_ON";
                    break;
                }
                case 12: {
                    s = "STATE_ON";
                    break;
                }
                case 13: {
                    s = "STATE_TURNING_OFF";
                    break;
                }
                default: {
                    s = "unknow";
                    break;
                }
            }
            return s + ":" + n;
        }
        
        String getSCOAudioStateExtraString(final int n) {
            String s = null;
            switch (n) {
                case 0: {
                    s = "SCO_AUDIO_STATE_DISCONNECTED";
                    break;
                }
                case 1: {
                    s = "SCO_AUDIO_STATE_CONNECTED";
                    break;
                }
                case 2: {
                    s = "SCO_AUDIO_STATE_CONNECTING";
                    break;
                }
                case -1: {
                    s = "SCO_AUDIO_STATE_ERROR";
                    break;
                }
                default: {
                    s = "unknow";
                    break;
                }
            }
            return s + ":" + n;
        }
        
        String getBTAdapterConnectionState(final int n) {
            String s = null;
            switch (n) {
                case 0: {
                    s = "STATE_DISCONNECTED";
                    break;
                }
                case 1: {
                    s = "STATE_CONNECTING";
                    break;
                }
                case 2: {
                    s = "STATE_CONNECTED";
                    break;
                }
                case 3: {
                    s = "STATE_DISCONNECTING";
                    break;
                }
                default: {
                    s = "unknow";
                    break;
                }
            }
            return s + ":" + n;
        }
        
        String getBTHeadsetConnectionState(final int n) {
            String s = null;
            switch (n) {
                case 0: {
                    s = "STATE_DISCONNECTED";
                    break;
                }
                case 1: {
                    s = "STATE_CONNECTING";
                    break;
                }
                case 2: {
                    s = "STATE_CONNECTED";
                    break;
                }
                case 3: {
                    s = "STATE_DISCONNECTING";
                    break;
                }
                default: {
                    s = "unknow";
                    break;
                }
            }
            return s + ":" + n;
        }
        
        String getBTHeadsetAudioState(final int n) {
            String string = null;
            switch (n) {
                case 12: {
                    string = "STATE_AUDIO_CONNECTED";
                    break;
                }
                case 10: {
                    string = "STATE_AUDIO_DISCONNECTED";
                    break;
                }
                default: {
                    string = "unknow:" + n;
                    break;
                }
            }
            return string + ":" + n;
        }
    }
    
    class BluetoohHeadsetCheckFake extends BluetoohHeadsetCheckInterface
    {
        @Override
        public boolean init(final Context context, final DeviceConfigManager deviceConfigManager) {
            return true;
        }
        
        @Override
        public void release() {
        }
        
        @Override
        public boolean isConnected() {
            return false;
        }
        
        @Override
        void _addAction(final IntentFilter intentFilter) {
        }
        
        @Override
        void _onReceive(final Context context, final Intent intent) {
        }
        
        @Override
        public String interfaceDesc() {
            return "BluetoohHeadsetCheckFake";
        }
    }
    
    @TargetApi(11)
    class BluetoohHeadsetCheck extends BluetoohHeadsetCheckInterface implements BluetoothProfile.ServiceListener
    {
        Context _ctx;
        DeviceConfigManager _devCfg;
        BluetoothAdapter _adapter;
        BluetoothProfile _profile;
        private final ReentrantLock _profileLock;
        
        BluetoohHeadsetCheck() {
            this._ctx = null;
            this._devCfg = null;
            this._adapter = null;
            this._profile = null;
            this._profileLock = new ReentrantLock();
        }
        
        @TargetApi(11)
        @Override
        public boolean init(final Context ctx, final DeviceConfigManager devCfg) {
            AudioDeviceInterface.LogTraceEntry("");
            if (ctx == null || devCfg == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " err ctx==null||_devCfg==null");
                }
                return false;
            }
            this._ctx = ctx;
            this._devCfg = devCfg;
            this._adapter = BluetoothAdapter.getDefaultAdapter();
            if (this._adapter == null) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, " err getDefaultAdapter fail!");
                }
                return false;
            }
            this._profileLock.lock();
            try {
                if (this._adapter.isEnabled() && this._profile == null && !this._adapter.getProfileProxy(this._ctx, (BluetoothProfile.ServiceListener)this, 1)) {
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "BluetoohHeadsetCheck: getProfileProxy HEADSET fail!");
                    }
                    return false;
                }
            }
            finally {
                this._profileLock.unlock();
            }
            AudioDeviceInterface.LogTraceExit();
            return true;
        }
        
        @Override
        public void release() {
            AudioDeviceInterface.LogTraceEntry("_profile:" + this._profile);
            this._profileLock.lock();
            try {
                if (this._adapter != null) {
                    if (this._profile != null) {
                        this._adapter.closeProfileProxy(1, this._profile);
                    }
                    this._profile = null;
                }
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, " closeProfileProxy:e:" + ex.getMessage());
                }
            }
            finally {
                this._profileLock.unlock();
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        @Override
        public boolean isConnected() {
            boolean b = false;
            this._profileLock.lock();
            try {
                if (this._profile != null) {
                    final List connectedDevices = this._profile.getConnectedDevices();
                    if (connectedDevices == null) {
                        return false;
                    }
                    b = (connectedDevices.size() > 0);
                }
            }
            finally {
                this._profileLock.unlock();
            }
            return b;
        }
        
        @TargetApi(11)
        public void onServiceConnected(final int n, final BluetoothProfile profile) {
            AudioDeviceInterface.LogTraceEntry("_profile:" + this._profile + " profile:" + n + " proxy:" + profile);
            if (n == 1) {
                this._profileLock.lock();
                try {
                    if (this._profile != null && this._profile != profile) {
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, "BluetoohHeadsetCheck: HEADSET Connected proxy:" + profile + " _profile:" + this._profile);
                        }
                        this._adapter.closeProfileProxy(1, this._profile);
                        this._profile = null;
                    }
                    this._profile = profile;
                    List<BluetoothDevice> connectedDevices = null;
                    if (this._profile != null) {
                        connectedDevices = (List<BluetoothDevice>)this._profile.getConnectedDevices();
                    }
                    if (connectedDevices != null && this._profile != null) {
                        if (QLog.isColorLevel()) {
                            QLog.w("TRAE", 2, "TRAEBluetoohProxy: HEADSET Connected devs:" + connectedDevices.size() + " _profile:" + this._profile);
                        }
                        for (int i = 0; i < connectedDevices.size(); ++i) {
                            final BluetoothDevice bluetoothDevice = connectedDevices.get(i);
                            int connectionState = 0;
                            try {
                                if (this._profile != null) {
                                    connectionState = this._profile.getConnectionState(bluetoothDevice);
                                }
                            }
                            catch (Exception ex) {
                                if (QLog.isColorLevel()) {
                                    QLog.e("TRAE", 2, "get bluetooth connection state failed." + ex.getMessage());
                                }
                            }
                            if (connectionState == 2) {
                                this._devCfg.setBluetoothName(bluetoothDevice.getName());
                            }
                            if (QLog.isColorLevel()) {
                                QLog.w("TRAE", 2, "   " + i + " " + bluetoothDevice.getName() + " ConnectionState:" + connectionState);
                            }
                        }
                    }
                }
                finally {
                    this._profileLock.unlock();
                }
                if (this._devCfg != null) {
                    CharSequence bluetoothName = null;
                    if (TraeAudioManager.this._deviceConfigManager != null) {
                        bluetoothName = TraeAudioManager.this._deviceConfigManager.getBluetoothName();
                    }
                    if (TextUtils.isEmpty(bluetoothName)) {
                        this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", false);
                    }
                    else if (this.isConnected()) {
                        this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", true);
                        TraeAudioManager.this.checkDevicePlug("DEVICE_BLUETOOTHHEADSET", true);
                    }
                    else {
                        this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", false);
                    }
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        @TargetApi(11)
        public void onServiceDisconnected(final int n) {
            AudioDeviceInterface.LogTraceEntry("_profile:" + this._profile + " profile:" + n);
            if (n == 1) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "TRAEBluetoohProxy: HEADSET Disconnected");
                }
                if (this.isConnected()) {
                    TraeAudioManager.this.checkDevicePlug("DEVICE_BLUETOOTHHEADSET", false);
                }
                this._profileLock.lock();
                try {
                    if (this._profile != null) {
                        this._adapter.closeProfileProxy(1, this._profile);
                        this._profile = null;
                    }
                }
                finally {
                    this._profileLock.unlock();
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }
        
        @Override
        void _addAction(final IntentFilter intentFilter) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " " + this.interfaceDesc() + " _addAction");
            }
            intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        }
        
        @Override
        void _onReceive(final Context context, final Intent intent) {
            if ("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                final int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", -1);
                final int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_CONNECTION_STATE", -1);
                final BluetoothDevice bluetoothDevice = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BT ACTION_CONNECTION_STATE_CHANGED|   EXTRA_CONNECTION_STATE " + this.getBTAdapterConnectionState(intExtra));
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "    EXTRA_PREVIOUS_CONNECTION_STATE " + this.getBTAdapterConnectionState(intExtra2));
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "    EXTRA_DEVICE " + bluetoothDevice + " " + ((bluetoothDevice != null) ? bluetoothDevice.getName() : " "));
                }
                if (intExtra == 2) {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "   dev:" + bluetoothDevice.getName() + " connected,start sco...");
                    }
                    this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", true);
                    this._devCfg.setBluetoothName((bluetoothDevice != null) ? bluetoothDevice.getName() : "unkown");
                }
                else if (intExtra == 0) {
                    this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", false);
                }
            }
            else if ("android.media.ACTION_SCO_AUDIO_STATE_UPDATED".equals(intent.getAction())) {
                final int intExtra3 = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1);
                final int intExtra4 = intent.getIntExtra("android.media.extra.SCO_AUDIO_PREVIOUS_STATE", -1);
                final BluetoothDevice bluetoothDevice2 = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BT ACTION_SCO_AUDIO_STATE_UPDATED|   EXTRA_CONNECTION_STATE  dev:" + bluetoothDevice2);
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "   EXTRA_SCO_AUDIO_STATE " + this.getSCOAudioStateExtraString(intExtra3));
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "   EXTRA_SCO_AUDIO_PREVIOUS_STATE " + this.getSCOAudioStateExtraString(intExtra4));
                }
            }
            else if ("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                final BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                switch (defaultAdapter.getProfileConnectionState(2)) {
                    case 2: {
                        QLog.w("TRAE", 2, "BluetoothA2dp STATE_CONNECTED");
                        TraeAudioManager.this.IsBluetoothA2dpExisted = true;
                        break;
                    }
                    case 0: {
                        QLog.w("TRAE", 2, "BluetoothA2dp STATE_DISCONNECTED");
                        TraeAudioManager.this.IsBluetoothA2dpExisted = false;
                        break;
                    }
                    default: {
                        QLog.w("TRAE", 2, "BluetoothA2dp" + defaultAdapter.getProfileConnectionState(2));
                        break;
                    }
                }
            }
        }
        
        @Override
        public String interfaceDesc() {
            return "BluetoohHeadsetCheck";
        }
    }
    
    class BluetoohHeadsetCheckFor2x extends BluetoohHeadsetCheckInterface
    {
        public static final String ACTION_BLUETOOTHHEADSET_AUDIO_STATE_CHANGED = "android.bluetooth.headset.action.AUDIO_STATE_CHANGED";
        public static final String ACTION_BLUETOOTHHEADSET_STATE_CHANGED = "android.bluetooth.headset.action.STATE_CHANGED";
        static final int STATE_CONNECTED = 2;
        static final int STATE_DISCONNECTED = 0;
        public static final int AUDIO_STATE_DISCONNECTED = 0;
        public static final int AUDIO_STATE_CONNECTED = 1;
        Class<?> BluetoothHeadsetClass;
        Class<?> ListenerClass;
        Object BluetoothHeadsetObj;
        Method getCurrentHeadsetMethod;
        Context _ctx;
        DeviceConfigManager _devCfg;
        
        BluetoohHeadsetCheckFor2x() {
            this.BluetoothHeadsetClass = null;
            this.ListenerClass = null;
            this.BluetoothHeadsetObj = null;
            this.getCurrentHeadsetMethod = null;
            this._ctx = null;
            this._devCfg = null;
        }
        
        @Override
        public boolean init(final Context ctx, final DeviceConfigManager devCfg) {
            AudioDeviceInterface.LogTraceEntry("");
            this._ctx = ctx;
            this._devCfg = devCfg;
            if (this._ctx == null || this._devCfg == null) {
                return false;
            }
            try {
                this.BluetoothHeadsetClass = Class.forName("android.bluetooth.BluetoothHeadset");
            }
            catch (Exception ex2) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset class not found");
                }
            }
            if (this.BluetoothHeadsetClass == null) {
                return false;
            }
            try {
                this.ListenerClass = Class.forName("android.bluetooth.BluetoothHeadset$ServiceListener");
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset.ServiceListener class not found:" + ex);
                }
            }
            if (this.ListenerClass != null) {}
            try {
                this.getCurrentHeadsetMethod = this.BluetoothHeadsetClass.getDeclaredMethod("getCurrentHeadset", (Class<?>[])new Class[0]);
            }
            catch (NoSuchMethodException ex3) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset NoSuchMethodException");
                }
            }
            if (this.getCurrentHeadsetMethod == null) {
                return false;
            }
            try {
                this.BluetoothHeadsetObj = this.BluetoothHeadsetClass.getConstructor(Context.class, this.ListenerClass).newInstance(ctx, null);
            }
            catch (IllegalArgumentException ex4) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor IllegalArgumentException");
                }
            }
            catch (InstantiationException ex5) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor InstantiationException");
                }
            }
            catch (IllegalAccessException ex6) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor IllegalAccessException");
                }
            }
            catch (InvocationTargetException ex7) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor InvocationTargetException");
                }
            }
            catch (NoSuchMethodException ex8) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor NoSuchMethodException");
                }
            }
            if (this.BluetoothHeadsetObj == null) {
                return false;
            }
            this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", this.isConnected());
            if (this.isConnected()) {
                this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", true);
                TraeAudioManager.this.checkDevicePlug("DEVICE_BLUETOOTHHEADSET", true);
            }
            else {
                this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", false);
            }
            AudioDeviceInterface.LogTraceExit();
            return true;
        }
        
        @Override
        public void release() {
            AudioDeviceInterface.LogTraceEntry("");
            Method declaredMethod = null;
            if (this.BluetoothHeadsetObj == null) {
                return;
            }
            try {
                declaredMethod = this.BluetoothHeadsetClass.getDeclaredMethod("close", (Class<?>[])new Class[0]);
            }
            catch (NoSuchMethodException ex2) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "BTLooperThread _uninitHeadsetfor2x method close NoSuchMethodException");
                }
            }
            if (declaredMethod == null) {
                return;
            }
            try {
                declaredMethod.invoke(this.BluetoothHeadsetObj, new Object[0]);
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "close bluetooth headset failed." + ex.getMessage());
                }
            }
            this.BluetoothHeadsetClass = null;
            this.ListenerClass = null;
            this.BluetoothHeadsetObj = null;
            this.getCurrentHeadsetMethod = null;
            AudioDeviceInterface.LogTraceExit();
        }
        
        @Override
        public boolean isConnected() {
            Object invoke = null;
            if (this.getCurrentHeadsetMethod == null || this.getCurrentHeadsetMethod == null) {
                return false;
            }
            try {
                invoke = this.getCurrentHeadsetMethod.invoke(this.BluetoothHeadsetObj, new Object[0]);
            }
            catch (IllegalArgumentException ex) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset IllegalArgumentException");
                }
            }
            catch (IllegalAccessException ex2) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset IllegalAccessException");
                }
            }
            catch (InvocationTargetException ex3) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset InvocationTargetException");
                }
            }
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset res:" + ((invoke != null) ? " Y" : "N"));
            }
            return invoke != null;
        }
        
        @Override
        void _addAction(final IntentFilter intentFilter) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, " " + this.interfaceDesc() + " _addAction");
            }
            intentFilter.addAction("android.bluetooth.headset.action.AUDIO_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.action.STATE_CHANGED");
        }
        
        @Override
        void _onReceive(final Context context, final Intent intent) {
            if ("android.bluetooth.headset.action.AUDIO_STATE_CHANGED".equals(intent.getAction())) {
                final int intExtra = intent.getIntExtra("android.bluetooth.headset.extra.STATE", -2);
                final int intExtra2 = intent.getIntExtra("android.bluetooth.headset.extra.PREVIOUS_STATE", -2);
                final int intExtra3 = intent.getIntExtra("android.bluetooth.headset.extra.AUDIO_STATE", -2);
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "++ AUDIO_STATE_CHANGED|  STATE " + intExtra);
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "       PREVIOUS_STATE " + intExtra2);
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "       AUDIO_STATE " + intExtra3);
                }
                if (intExtra3 == 2) {
                    this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", true);
                }
                else if (intExtra3 == 0) {
                    this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", false);
                }
            }
            else if ("android.bluetooth.headset.action.STATE_CHANGED".equals(intent.getAction())) {
                final int intExtra4 = intent.getIntExtra("android.bluetooth.headset.extra.STATE", -2);
                final int intExtra5 = intent.getIntExtra("android.bluetooth.headset.extra.PREVIOUS_STATE", -2);
                final int intExtra6 = intent.getIntExtra("android.bluetooth.headset.extra.AUDIO_STATE", -2);
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "++ STATE_CHANGED|  STATE " + intExtra4);
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "       PREVIOUS_STATE " + intExtra5);
                }
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "       AUDIO_STATE " + intExtra6);
                }
                if (intExtra6 == 2) {
                    this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", true);
                }
                else if (intExtra6 == 0) {
                    this._devCfg.setVisible("DEVICE_BLUETOOTHHEADSET", false);
                }
            }
        }
        
        @Override
        public String interfaceDesc() {
            return "BluetoohHeadsetCheckFor2x";
        }
    }
}
