package com.simple.video.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.simple.video.opengl.GPUImageExtTexFilter;
import com.simple.video.opengl.OpenGlUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 描述：
 * <p>
 * Created BY zhouwenye  2020/5/6-10:59
 */
public class CameraPreivew1 extends SurfaceView implements DPCameraCapturer.DPCameraFrameListener, SurfaceHolder.Callback {

    private Context mContext;
    // OpenGL 坐标变换矩阵
    private DPCameraCapturer mCameraManager;
    private SurfaceHolder mHolder;

    public CameraPreivew1(Context context) {
        this(context, null);
    }

    public CameraPreivew1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        commonInit();
    }

    private void commonInit() {
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    private void initRender() {
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    private void releaseCamera() {
        if (mCameraManager != null) {
            mCameraManager.releaseCamera();
            mCameraManager = null;
            mHolder = null;
        }
    }

    private void startCamera() {
        if (mHolder == null) {
            return;
        }
        if (mCameraManager == null) {
            mCameraManager = new DPCameraCapturer();
            mCameraManager.setResolution(DPCameraCapturer.Resolution.RES_720);
            mCameraManager.setFrameListener(this);
        } else {
            mCameraManager.releaseCamera();
        }
        mCameraManager.startPreview(false, mHolder);
    }

    @Override
    public void onFrame(byte[] data) {
         saveBitmap(data);
    }

    @Override
    public void onError() {

    }

    public AtomicBoolean canSave = new AtomicBoolean(true);

    public void saveBitmap(final byte[] data) {
        if (canSave.getAndSet(false)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    YuvImage im = new YuvImage(data, ImageFormat.NV21, 1280,
                            720, null);
                    Rect r = new Rect(0, 0, 1280, 720);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    im.compressToJpeg(r, 70, baos);

                    try {
                        File outputFile = new File(Environment.getExternalStorageDirectory() + "/aa/kk/" + System.currentTimeMillis() + ".jpg");
                        if (outputFile.exists()) {
                            outputFile.delete();
                        }
                        outputFile.createNewFile();

                        FileOutputStream output = new FileOutputStream(outputFile);
                        output.write(baos.toByteArray());
                        output.flush();
                        output.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    canSave.set(true);
                }
            }).start();
        }
    }

}
