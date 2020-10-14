package com.simple.video;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.simple.video.camera.CameraPreivew;

public class MainActivity extends AppCompatActivity {

    private FrameLayout surfaceViewContainer;
    private boolean hasPermission = false;
    private View play, send;
    private EditText mInput;
    private TextView receive;
    private CameraPreivew cameraPreivew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO}, 10010);
        } else {
            hasPermission = true;
            initWithPermission();
        }
        send = findViewById(R.id.send);
        receive = findViewById(R.id.receiver);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mInput = findViewById(R.id.input);
        play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                surfaceView.requestReleaseEglContextLocked()
            }
        });
        surfaceViewContainer = findViewById(R.id.movie_texture_view);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("surface-life","add click");
                if (cameraPreivew == null) {
                    initCameraView();
                } else {
                    Log.w("surface-life","addView");
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    surfaceViewContainer.addView(cameraPreivew, params);
                }
            }
        });
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraPreivew != null && cameraPreivew.getParent() != null) {
                    Log.w("surface-life","remove");
                    surfaceViewContainer.removeView(cameraPreivew);
                }
            }
        });
        findViewById(R.id.visi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraPreivew != null && cameraPreivew.getParent() != null) {
                    Log.w("surface-life","visiable");
                    type = (1+type) % 3;
                    if (type == 1) {
                        Log.w("surface-life","visiable gone");
                        cameraPreivew.setVisibility(View.GONE);
                    } else if (type == 2) {
                        Log.w("surface-life","visiable visible");
                        cameraPreivew.setVisibility(View.VISIBLE);
                    } else {
                        Log.w("surface-life","visiable invisible");
                        cameraPreivew.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraPreivew != null && cameraPreivew.getParent() != null) {
                    tem = !tem;
                    if (tem) {
                        Log.w("surface-life","click resume");
                        cameraPreivew.onResume();
                    } else {
                        Log.w("surface-life","click pasuse");
                        cameraPreivew.onPause();
                    }
                }
            }
        });
    }

    int type = 0;
    boolean tem = true;

    private void initCameraView() {
        Log.w("surface-life","initCamera");
        cameraPreivew = new CameraPreivew(MainActivity.this);
    }

    boolean isPause = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initWithPermission();
    }

    private void initWithPermission() {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
