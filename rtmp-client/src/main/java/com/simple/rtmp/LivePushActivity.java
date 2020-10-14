package com.simple.rtmp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.simple.rtmp.OnConntionListener;
import com.simple.rtmp.RtmpHelper;
import com.simple.rtmp.camera.CameraEglSurfaceView;
import com.simple.rtmp.encoder.BasePushEncoder;
import com.simple.rtmp.encoder.PushEncode;


public class LivePushActivity extends AppCompatActivity implements View.OnClickListener, OnConntionListener, BasePushEncoder.OnMediaInfoListener {

    private CameraEglSurfaceView cameraEglSurfaceView;

    private RtmpHelper rtmpHelper;
    private PushEncode pushEncode;
    private boolean isStart;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_push);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO}, 10010);
        } else {

        }
        cameraEglSurfaceView = findViewById(R.id.camera);
        button = findViewById(R.id.push);
        button.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraEglSurfaceView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cameraEglSurfaceView.previewAngle(this);
    }

    @Override
    public void onClick(View v) {
        if (isStart) {
            button.setText("开始");
            if (pushEncode != null) {
                pushEncode.stop();
                pushEncode = null;
            }

            if(rtmpHelper!=null){
                rtmpHelper.stop();
                rtmpHelper =null;
            }
            isStart = false;

        } else {
            button.setText("停止");
            isStart = true;
            rtmpHelper = new RtmpHelper();
            rtmpHelper.setOnConntionListener(this);
            rtmpHelper.initLivePush("rtmp://live-origin.test.mtyuncdn.com/livetest/live");
//            rtmpHelper.initLivePush("rtmp://127.0.0.1/live/mystream");
        }
    }

    @Override
    public void onConntecting() {
        Log.e("zzz", "连接中...");
    }

    @Override
    public void onConntectSuccess() {
        Log.e("zzz", "onConntectSuccess...");
        startPush();
    }

    private void startPush() {
        pushEncode = new PushEncode(this, cameraEglSurfaceView.getTextureId());
        pushEncode.initEncoder(cameraEglSurfaceView.getEglContext(), 720,
                1280,44100,2,16);
        pushEncode.setOnMediaInfoListener(this);
        pushEncode.start();
    }

    @Override
    public void onConntectFail(String msg) {
        Log.e("zzz", "onConntectFail  " + msg);
    }

    @Override
    public void onMediaTime(int times) {

    }

    @Override
    public void onSPSPPSInfo(byte[] sps, byte[] pps) {
        if (rtmpHelper == null) return;
        rtmpHelper.pushSPSPPS(sps, pps);
    }

    @Override
    public void onVideoDataInfo(byte[] data, boolean keyFrame) {
        if (rtmpHelper == null) return;
        rtmpHelper.pushVideoData(data,keyFrame);
    }

    @Override
    public void onAudioInfo(byte[] data) {
        if (rtmpHelper == null) return;
        rtmpHelper.pushAudioData(data);
    }


}
