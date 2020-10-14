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
import android.util.Log;

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
public class CameraPreivew extends DPGLSurfaceViewBase implements DPCameraCapturer.DPCameraFrameListener, GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    private static final String TAG = "CameraPreview";
    private Context mContext;
    private int genTextureID;
    private SurfaceTexture mSurfaceTexture;
    // OpenGL 坐标变换矩阵
    private volatile float[] mSTMatrix = new float[16];
    private GPUImageExtTexFilter mRender;
    private DPCameraCapturer mCameraManager;

    public static final float[] CUBE = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f
    };

    public static final float[] TEXTURE_NO_ROTATION = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f
    };

//    public static final float[] TEXTURE_ROTATED_180 = {
//            1.0f, 0.0f,
//            0.0f, 0.0f,
//            1.0f, 1.0f,
//            0.0f, 1.0f
//    };


    FloatBuffer cubeBuffer = ByteBuffer.allocateDirect(CUBE.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    FloatBuffer textureBuffer = ByteBuffer.allocateDirect(TEXTURE_NO_ROTATION.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    public CameraPreivew(Context context) {
        this(context, null);
    }

    public CameraPreivew(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        commonInit();
    }

    private void commonInit() {
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        // 如果缺失，会导致黑屏的
        cubeBuffer.put(CUBE).position(0);
        textureBuffer.put(TEXTURE_NO_ROTATION).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.w(TAG,"onSurfaceCreated");
        gpuInit();
        initRender();
        startCamera();

    }

    private void initRender() {
        mRender = new GPUImageExtTexFilter();
        mRender.init();
        mRender.setTransformMatrix(mSTMatrix);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        try {
            mSurfaceTexture.updateTexImage();
            mSurfaceTexture.getTransformMatrix(mSTMatrix);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        mRender.onDraw(genTextureID, cubeBuffer, textureBuffer);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }

    private void gpuInit() {
        // 初始化承接相机输出的纹理
        genTextureID = OpenGlUtils.generateTextureOES();
        mSurfaceTexture = new SurfaceTexture(genTextureID);
        mSurfaceTexture.setOnFrameAvailableListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w(TAG,"onPasuse");
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCameraManager != null) {
            mCameraManager.releaseCamera();
            mCameraManager = null;
        }
    }

    private void startCamera() {
        Log.w(TAG,"startCamera");
        if (mCameraManager == null) {
            mCameraManager = new DPCameraCapturer();
            mCameraManager.setSurfaceTexture(mSurfaceTexture);
            mCameraManager.setResolution(DPCameraCapturer.Resolution.RES_720);
            mCameraManager.setFrameListener(this);
        } else {
            mCameraManager.releaseCamera();
        }
        mCameraManager.startPreview(false);
    }

    @Override
    public void onFrame(byte[] data) {
        // saveBitmap(data);
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
                    YuvImage im = new YuvImage(data, ImageFormat.NV21, 480,
                            360, null);
                    Rect r = new Rect(0, 0, 480, 360);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    im.compressToJpeg(r, 70, baos);

                    try {
                        File outputFile = new File(Environment.getExternalStorageDirectory() + "/aa/kk/" + System.currentTimeMillis() + ".jpg");
                        if(outputFile.exists()) {
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
