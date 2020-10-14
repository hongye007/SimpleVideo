package com.tencent.rtmp.sharp.jni;

import android.content.*;
import android.net.*;
import android.os.*;

public class TraeAudioSession extends BroadcastReceiver
{
    private boolean mIsHostside;
    private long mSessionId;
    private ITraeAudioCallback mCallback;
    private Context mContext;
    private String _connectedDev;
    private boolean _canSwtich2Earphone;
    static int s_nSessionIdAllocator;
    final String TRAE_ACTION_PHONE_STATE = "android.intent.action.PHONE_STATE";
    
    public static long requestSessionId() {
        return ((long)Process.myPid() << 32) + ++TraeAudioSession.s_nSessionIdAllocator;
    }
    
    public static void ExConnectDevice(final Context context, final String s) {
        if (null == context || null == s || s.length() <= 0) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", Long.MIN_VALUE);
        intent.putExtra("PARAM_OPERATION", "OPERATION_CONNECTDEVICE");
        intent.putExtra("CONNECTDEVICE_DEVICENAME", s);
        context.sendBroadcast(intent);
    }
    
    public TraeAudioSession(final Context mContext, final ITraeAudioCallback mCallback) {
        this.mIsHostside = false;
        this.mSessionId = Long.MIN_VALUE;
        this._connectedDev = "DEVICE_NONE";
        this._canSwtich2Earphone = true;
        this.mIsHostside = (Process.myPid() == TraeAudioManager._gHostProcessId);
        this.mSessionId = requestSessionId();
        this.mCallback = mCallback;
        this.mContext = mContext;
        if (null == mContext && QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "AudioSession | Invalid parameters: ctx = " + ((null == mContext) ? "null" : "{object}") + "; cb = " + ((null == mCallback) ? "null" : "{object}"));
        }
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES");
        intentFilter.addAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY");
        try {
            if (mContext == null || mContext.registerReceiver((BroadcastReceiver)this, intentFilter) == null) {}
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "registerReceiver Exception: " + ex.getMessage());
            }
        }
        this.registerAudioSession(this, true);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "TraeAudioSession create, mSessionId: " + this.mSessionId);
        }
    }
    
    public void release() {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "TraeAudioSession release, mSessionId: " + this.mSessionId);
        }
        if (null != this.mContext) {
            try {
                this.mContext.unregisterReceiver((BroadcastReceiver)this);
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "unregisterReceiver failed." + ex.getMessage());
                }
            }
        }
        this.registerAudioSession(this, false);
        this.mContext = null;
        this.mCallback = null;
    }
    
    public void setCallback(final ITraeAudioCallback mCallback) {
        this.mCallback = mCallback;
    }
    
    private int registerAudioSession(final TraeAudioSession traeAudioSession, final boolean b) {
        if (null == this.mContext) {
            return -1;
        }
        return TraeAudioManager.registerAudioSession(traeAudioSession, b, this.mSessionId, this.mContext);
    }
    
    public int startService(String s) {
        if (null == s || s.length() <= 0) {
            s = "internal_disable_dev_switch";
        }
        if (this.mIsHostside) {
            return TraeAudioManager.startService("OPERATION_STARTSERVICE", this.mSessionId, this.mIsHostside, s);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_STARTSERVICE");
        intent.putExtra("EXTRA_DATA_DEVICECONFIG", s);
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int disableDeviceSwitch() {
        return TraeAudioManager.disableDeviceSwitch();
    }
    
    public int stopService() {
        if (this.mIsHostside) {
            return TraeAudioManager.stopService("OPERATION_STOPSERVICE", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_STOPSERVICE");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int getDeviceList() {
        if (this.mIsHostside) {
            return TraeAudioManager.getDeviceList("OPERATION_GETDEVICELIST", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_GETDEVICELIST");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int getStreamType() {
        if (this.mIsHostside) {
            return TraeAudioManager.getStreamType("OPERATION_GETSTREAMTYPE", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_GETSTREAMTYPE");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int connectDevice(final String s) {
        if (this.mIsHostside) {
            return TraeAudioManager.connectDevice("OPERATION_CONNECTDEVICE", this.mSessionId, this.mIsHostside, s);
        }
        if (null == this.mContext || null == s || s.length() <= 0) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_CONNECTDEVICE");
        intent.putExtra("CONNECTDEVICE_DEVICENAME", s);
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int connectHighestPriorityDevice() {
        if (this.mIsHostside) {
            return TraeAudioManager.connectHighestPriorityDevice("OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int EarAction(final int n) {
        if (this.mIsHostside) {
            return TraeAudioManager.earAction("OPERATION_EARACTION", this.mSessionId, this.mIsHostside, n);
        }
        if (null == this.mContext || (n != 0 && n != 1)) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_EARACTION");
        intent.putExtra("EXTRA_EARACTION", n);
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int isDeviceChangabled() {
        if (this.mIsHostside) {
            return TraeAudioManager.isDeviceChangabled("OPERATION_ISDEVICECHANGABLED", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_ISDEVICECHANGABLED");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int getConnectedDevice() {
        if (this.mIsHostside) {
            return TraeAudioManager.getConnectedDevice("OPERATION_GETCONNECTEDDEVICE", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_GETCONNECTEDDEVICE");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int getConnectingDevice() {
        if (this.mIsHostside) {
            return TraeAudioManager.getConnectingDevice("OPERATION_GETCONNECTINGDEVICE", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_GETCONNECTINGDEVICE");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int voiceCallPreprocess(final int n, final int n2) {
        if (this.mIsHostside) {
            return TraeAudioManager.voicecallPreprocess("OPERATION_VOICECALL_PREPROCESS", this.mSessionId, this.mIsHostside, n, n2);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_MODEPOLICY", n);
        intent.putExtra("PARAM_STREAMTYPE", n2);
        intent.putExtra("PARAM_OPERATION", "OPERATION_VOICECALL_PREPROCESS");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int voiceCallPostprocess() {
        if (this.mIsHostside) {
            return TraeAudioManager.voicecallPostprocess("OPERATION_VOICECALL_POSTROCESS", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_VOICECALL_POSTROCESS");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int voiceCallAudioParamChanged(final int n, final int n2) {
        if (this.mIsHostside) {
            return TraeAudioManager.voiceCallAudioParamChanged("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST", this.mSessionId, this.mIsHostside, n, n2);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_MODEPOLICY", n);
        intent.putExtra("PARAM_STREAMTYPE", n2);
        intent.putExtra("PARAM_OPERATION", "OPERATION_VOICECALL_AUDIOPARAM_CHANGED");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int startRing(final int n, final int n2, final Uri uri, final String s, final boolean b) {
        if (this.mIsHostside) {
            return TraeAudioManager.startRing("OPERATION_STARTRING", this.mSessionId, this.mIsHostside, n, n2, uri, s, b, 1, "normal-ring", false);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_RING_DATASOURCE", n);
        intent.putExtra("PARAM_RING_RSID", n2);
        intent.putExtra("PARAM_RING_URI", (Parcelable)uri);
        intent.putExtra("PARAM_RING_FILEPATH", s);
        intent.putExtra("PARAM_RING_LOOP", b);
        intent.putExtra("PARAM_RING_MODE", false);
        intent.putExtra("PARAM_RING_USERDATA_STRING", "normal-ring");
        intent.putExtra("PARAM_OPERATION", "OPERATION_STARTRING");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int startRing(final int n, final int n2, final Uri uri, final String s, final boolean b, final int n3, final String s2) {
        if (this.mIsHostside) {
            return TraeAudioManager.startRing("OPERATION_STARTRING", this.mSessionId, this.mIsHostside, n, n2, uri, s, b, n3, s2, false);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_RING_DATASOURCE", n);
        intent.putExtra("PARAM_RING_RSID", n2);
        intent.putExtra("PARAM_RING_URI", (Parcelable)uri);
        intent.putExtra("PARAM_RING_FILEPATH", s);
        intent.putExtra("PARAM_RING_LOOP", b);
        intent.putExtra("PARAM_RING_LOOPCOUNT", n3);
        intent.putExtra("PARAM_RING_MODE", false);
        intent.putExtra("PARAM_RING_USERDATA_STRING", s2);
        intent.putExtra("PARAM_OPERATION", "OPERATION_STARTRING");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int startRing(final int n, final int n2, final Uri uri, final String s, final boolean b, final int n3, final String s2, final boolean b2) {
        if (this.mIsHostside) {
            return TraeAudioManager.startRing("OPERATION_STARTRING", this.mSessionId, this.mIsHostside, n, n2, uri, s, b, n3, s2, b2);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_RING_DATASOURCE", n);
        intent.putExtra("PARAM_RING_RSID", n2);
        intent.putExtra("PARAM_RING_URI", (Parcelable)uri);
        intent.putExtra("PARAM_RING_FILEPATH", s);
        intent.putExtra("PARAM_RING_LOOP", b);
        intent.putExtra("PARAM_RING_LOOPCOUNT", n3);
        intent.putExtra("PARAM_RING_MODE", b2);
        intent.putExtra("PARAM_RING_USERDATA_STRING", s2);
        intent.putExtra("PARAM_OPERATION", "OPERATION_STARTRING");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int stopRing() {
        if (this.mIsHostside) {
            return TraeAudioManager.stopRing("OPERATION_STOPRING", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_STOPRING");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int requestReleaseAudioFocus() {
        if (this.mIsHostside) {
            return TraeAudioManager.requestReleaseAudioFocus("OPERATION_REQUEST_RELEASE_AUDIO_FOCUS", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_REQUEST_RELEASE_AUDIO_FOCUS");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public int recoverAudioFocus() {
        if (this.mIsHostside) {
            return TraeAudioManager.recoverAudioFocus("OPERATION_RECOVER_AUDIO_FOCUS", this.mSessionId, this.mIsHostside);
        }
        if (null == this.mContext) {
            return -1;
        }
        final Intent intent = new Intent();
        intent.setAction("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST");
        intent.putExtra("PARAM_SESSIONID", this.mSessionId);
        intent.putExtra("PARAM_OPERATION", "OPERATION_RECOVER_AUDIO_FOCUS");
        this.mContext.sendBroadcast(intent);
        return 0;
    }
    
    public void onReceiveCallback(final Intent intent) {
        try {
            if (null == intent) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "[ERROR] intent = null!!");
                }
                return;
            }
            final long longExtra = intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE);
            final String stringExtra = intent.getStringExtra("PARAM_OPERATION");
            final int intExtra = intent.getIntExtra("PARAM_RES_ERRCODE", 0);
            if ("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES".equals(intent.getAction())) {
                if (this.mSessionId != longExtra) {
                    return;
                }
                if ("OPERATION_VOICECALL_PREPROCESS".equals(stringExtra)) {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onReceiveCallback onVoicecallPreprocess] err:" + intExtra);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onVoicecallPreprocessRes(intExtra);
                    }
                }
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "AudioSession| nSessinId = " + this.mSessionId + " onReceive::intent:" + intent.toString() + " intent.getAction():" + intent.getAction() + " Exception:" + ex.getMessage());
            }
        }
    }
    
    public void onReceive(final Context context, final Intent intent) {
        try {
            if (null == intent) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "[ERROR] intent = null!!");
                }
                return;
            }
            final long longExtra = intent.getLongExtra("PARAM_SESSIONID", Long.MIN_VALUE);
            final String stringExtra = intent.getStringExtra("PARAM_OPERATION");
            final int intExtra = intent.getIntExtra("PARAM_RES_ERRCODE", 0);
            if ("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY".equals(intent.getAction())) {
                if ("NOTIFY_SERVICE_STATE".equals(stringExtra)) {
                    final boolean booleanExtra = intent.getBooleanExtra("NOTIFY_SERVICE_STATE_DATE", false);
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onServiceStateUpdate]" + (booleanExtra ? "on" : "off"));
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onServiceStateUpdate(booleanExtra);
                    }
                }
                else if ("NOTIFY_DEVICELISTUPDATE".equals(stringExtra)) {
                    final String[] stringArrayExtra = intent.getStringArrayExtra("EXTRA_DATA_AVAILABLEDEVICE_LIST");
                    final String stringExtra2 = intent.getStringExtra("EXTRA_DATA_CONNECTEDDEVICE");
                    final String stringExtra3 = intent.getStringExtra("EXTRA_DATA_PREV_CONNECTEDDEVICE");
                    final String stringExtra4 = intent.getStringExtra("EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME");
                    String string = "\n";
                    boolean canSwtich2Earphone = true;
                    for (int i = 0; i < stringArrayExtra.length; ++i) {
                        string = string + "AudioSession|    " + i + " " + stringArrayExtra[i] + "\n";
                        if (stringArrayExtra[i].equals("DEVICE_WIREDHEADSET") || stringArrayExtra[i].equals("DEVICE_BLUETOOTHHEADSET")) {
                            canSwtich2Earphone = false;
                        }
                    }
                    final String string2 = string + "\n";
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onDeviceListUpdate]  connected:" + stringExtra2 + " prevConnected:" + stringExtra3 + " bt:" + stringExtra4 + " Num:" + stringArrayExtra.length + string2);
                    }
                    this._canSwtich2Earphone = canSwtich2Earphone;
                    this._connectedDev = stringExtra2;
                    if (null != this.mCallback) {
                        this.mCallback.onDeviceListUpdate(stringArrayExtra, stringExtra2, stringExtra3, stringExtra4);
                    }
                }
                else if ("NOTIFY_DEVICECHANGABLE_UPDATE".equals(stringExtra)) {
                    final boolean booleanExtra2 = intent.getBooleanExtra("NOTIFY_DEVICECHANGABLE_UPDATE_DATE", true);
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onDeviceChangabledUpdate]" + booleanExtra2);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onDeviceChangabledUpdate(booleanExtra2);
                    }
                }
                else if ("NOTIFY_STREAMTYPE_UPDATE".equals(stringExtra)) {
                    final int intExtra2 = intent.getIntExtra("EXTRA_DATA_STREAMTYPE", -1);
                    if (intExtra && QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onStreamTypeUpdate] err:" + intExtra + " st:" + intExtra2);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onStreamTypeUpdate(intExtra2);
                    }
                }
                else if ("NOTIFY_ROUTESWITCHSTART".equals(stringExtra)) {
                    final String stringExtra5 = intent.getStringExtra("EXTRA_DATA_ROUTESWITCHSTART_FROM");
                    final String stringExtra6 = intent.getStringExtra("EXTRA_DATA_ROUTESWITCHSTART_TO");
                    if (null != this.mCallback && stringExtra5 != null && stringExtra6 != null) {
                        this.mCallback.onAudioRouteSwitchStart(stringExtra5, stringExtra6);
                    }
                }
                else if ("NOTIFY_ROUTESWITCHEND".equals(stringExtra)) {
                    final String stringExtra7 = intent.getStringExtra("EXTRA_DATA_ROUTESWITCHEND_DEV");
                    final long longExtra2 = intent.getLongExtra("EXTRA_DATA_ROUTESWITCHEND_TIME", -1L);
                    if (null != this.mCallback && stringExtra7 != null && longExtra2 != -1L) {
                        this.mCallback.onAudioRouteSwitchEnd(stringExtra7, longExtra2);
                    }
                }
            }
            else if ("com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES".equals(intent.getAction())) {
                if (this.mSessionId != longExtra) {
                    return;
                }
                if ("OPERATION_GETDEVICELIST".equals(stringExtra)) {
                    final String[] stringArrayExtra2 = intent.getStringArrayExtra("EXTRA_DATA_AVAILABLEDEVICE_LIST");
                    final String stringExtra8 = intent.getStringExtra("EXTRA_DATA_CONNECTEDDEVICE");
                    final String stringExtra9 = intent.getStringExtra("EXTRA_DATA_PREV_CONNECTEDDEVICE");
                    final String stringExtra10 = intent.getStringExtra("EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME");
                    String string3 = "\n";
                    boolean canSwtich2Earphone2 = true;
                    for (int j = 0; j < stringArrayExtra2.length; ++j) {
                        string3 = string3 + "AudioSession|    " + j + " " + stringArrayExtra2[j] + "\n";
                        if (stringArrayExtra2[j].equals("DEVICE_WIREDHEADSET") || stringArrayExtra2[j].equals("DEVICE_BLUETOOTHHEADSET")) {
                            canSwtich2Earphone2 = false;
                        }
                    }
                    final String string4 = string3 + "\n";
                    this._canSwtich2Earphone = canSwtich2Earphone2;
                    this._connectedDev = stringExtra8;
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onGetDeviceListRes] err:" + intExtra + " connected:" + stringExtra8 + " prevConnected:" + stringExtra9 + " bt:" + stringExtra10 + " Num:" + stringArrayExtra2.length + string4);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onGetDeviceListRes(intExtra, stringArrayExtra2, stringExtra8, stringExtra9, stringExtra10);
                    }
                }
                else if ("OPERATION_CONNECTDEVICE".equals(stringExtra)) {
                    final String stringExtra11 = intent.getStringExtra("CONNECTDEVICE_RESULT_DEVICENAME");
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onConnectDeviceRes] err:" + intExtra + " dev:" + stringExtra11);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onConnectDeviceRes(intExtra, stringExtra11, intExtra == 0);
                    }
                }
                else if ("OPERATION_EARACTION".equals(stringExtra)) {
                    final int intExtra3 = intent.getIntExtra("EXTRA_EARACTION", -1);
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onConnectDeviceRes] err:" + intExtra + " earAction:" + intExtra3);
                    }
                    if (null != this.mCallback) {}
                }
                else if ("OPERATION_ISDEVICECHANGABLED".equals(stringExtra)) {
                    final boolean booleanExtra3 = intent.getBooleanExtra("ISDEVICECHANGABLED_REULT_ISCHANGABLED", false);
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onIsDeviceChangabledRes] err:" + intExtra + " Changabled:" + (booleanExtra3 ? "Y" : "N"));
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onIsDeviceChangabledRes(intExtra, booleanExtra3);
                    }
                }
                else if ("OPERATION_GETCONNECTEDDEVICE".equals(stringExtra)) {
                    final String stringExtra12 = intent.getStringExtra("GETCONNECTEDDEVICE_REULT_LIST");
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onGetConnectedDeviceRes] err:" + intExtra + " dev:" + stringExtra12);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onGetConnectedDeviceRes(intExtra, stringExtra12);
                    }
                }
                else if ("OPERATION_GETCONNECTINGDEVICE".equals(stringExtra)) {
                    final String stringExtra13 = intent.getStringExtra("GETCONNECTINGDEVICE_REULT_LIST");
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onGetConnectingDeviceRes] err:" + intExtra + " dev:" + stringExtra13);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onGetConnectingDeviceRes(intExtra, stringExtra13);
                    }
                }
                else if ("OPERATION_GETSTREAMTYPE".equals(stringExtra)) {
                    final int intExtra4 = intent.getIntExtra("EXTRA_DATA_STREAMTYPE", -1);
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onGetStreamTypeRes] err:" + intExtra + " st:" + intExtra4);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onGetStreamTypeRes(intExtra, intExtra4);
                    }
                }
                else if ("NOTIFY_RING_COMPLETION".equals(stringExtra)) {
                    final String stringExtra14 = intent.getStringExtra("PARAM_RING_USERDATA_STRING");
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onRingCompletion] err:" + intExtra + " userData:" + stringExtra14);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onRingCompletion(intExtra, stringExtra14);
                    }
                }
                else if ("OPERATION_VOICECALL_PREPROCESS".equals(stringExtra)) {
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "AudioSession|[onVoicecallPreprocess] err:" + intExtra);
                    }
                    if (null != this.mCallback) {
                        this.mCallback.onVoicecallPreprocessRes(intExtra);
                    }
                }
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "AudioSession| nSessinId = " + this.mSessionId + " onReceive::intent:" + intent.toString() + " intent.getAction():" + intent.getAction() + " Exception:" + ex.getMessage());
            }
        }
    }
    
    static {
        TraeAudioSession.s_nSessionIdAllocator = 0;
    }
    
    public interface ITraeAudioCallback
    {
        void onServiceStateUpdate(final boolean p0);
        
        void onDeviceListUpdate(final String[] p0, final String p1, final String p2, final String p3);
        
        void onDeviceChangabledUpdate(final boolean p0);
        
        void onStreamTypeUpdate(final int p0);
        
        void onGetDeviceListRes(final int p0, final String[] p1, final String p2, final String p3, final String p4);
        
        void onConnectDeviceRes(final int p0, final String p1, final boolean p2);
        
        void onIsDeviceChangabledRes(final int p0, final boolean p1);
        
        void onGetConnectedDeviceRes(final int p0, final String p1);
        
        void onGetConnectingDeviceRes(final int p0, final String p1);
        
        void onGetStreamTypeRes(final int p0, final int p1);
        
        void onRingCompletion(final int p0, final String p1);
        
        void onVoicecallPreprocessRes(final int p0);
        
        void onAudioRouteSwitchStart(final String p0, final String p1);
        
        void onAudioRouteSwitchEnd(final String p0, final long p1);
    }
}
