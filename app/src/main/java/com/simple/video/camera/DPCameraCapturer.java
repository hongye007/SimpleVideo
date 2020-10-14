package com.simple.video.camera;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;

import com.simple.video.CodeLogProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DPCameraCapturer implements AutoFocusCallback, ErrorCallback, PreviewCallback {
    private static final String SU_TAG = "DPCameraCapturer";
    private Camera mCamera;
    private boolean mIsFrontCamera = true;
    private DPCameraFrameListener mFrameListener;
    private int mFrameRate = 15;
    private int mHomeOrientation = 1;
    private int mPreviewWidth;
    private int mPreviewHeight;
    private int mDisplayOrientation;
    private int mRotation;
    private SurfaceTexture mSurfaceTexture;
    private boolean mIsCameraFocusPositionInPreviewSupported;
    private boolean mSupportMetering;
    private boolean mEnableTorch;
    private boolean mEnableCallbackBuffer = true;
    private int mBufferWidth;
    private int mBufferHeight;
    private boolean mEnableAutoFocus = false;
    private boolean mEnablePerformanceMode = false;
    // 相机缓存
    private byte[] mCallbackBuffer;
    private int mZoomLevel = 0;

    public void setFrameListener(DPCameraFrameListener listener) {
        mFrameListener = listener;
    }

    /**
     * 设置相机输出的纹理，必须在{@link #startPreview(boolean)}之前调用
     *
     * @param surfaceTexture 输出纹理
     */
    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        mSurfaceTexture = surfaceTexture;
    }

    /**
     * 获取相机参数
     *
     * @return 相机参数
     */
    public Parameters getCameraParameters() {
        if (mCamera == null) {
            return null;
        } else {
            try {
                return mCamera.getParameters();
            } catch (Exception e) {
                e.printStackTrace();
                CodeLogProxy.getInstance().e(DPCameraCapturer.class, SU_TAG, "getCameraParameters error: " + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 闪光灯开关, 前置不支持
     *
     * @param enable true:开, false:关
     * @return true：操作成功，false：操作失败
     */
    public boolean enableTorch(boolean enable) {
        mEnableTorch = enable;
        if (mCamera != null) {
            boolean result = true;
            Parameters param = getCameraParameters();
            if (param == null) {
                return false;
            } else {
                List<String> supportedFlashModes = param.getSupportedFlashModes();
                if (enable) {
                    if (supportedFlashModes != null && supportedFlashModes.contains("torch")) {
                        CodeLogProxy.getInstance().i(DPCameraCapturer.class, SU_TAG, "set FLASH_MODE_TORCH");
                        param.setFlashMode("torch");
                    } else {
                        CodeLogProxy.getInstance().i(DPCameraCapturer.class, SU_TAG, "set FLASH_MODE_TORCH torch failure , not supported");
                        result = false;
                    }
                } else if (supportedFlashModes != null && supportedFlashModes.contains("off")) {
                    CodeLogProxy.getInstance().i(DPCameraCapturer.class, SU_TAG, "set FLASH_MODE_OFF");
                    param.setFlashMode("off");
                } else {
                    CodeLogProxy.getInstance().i(DPCameraCapturer.class, SU_TAG, "set FLASH_MODE_TORCH off failure , not supported");
                    result = false;
                }

                try {
                    mCamera.setParameters(param);
                } catch (Exception e) {
                    result = false;
                    e.printStackTrace();
                    CodeLogProxy.getInstance().e(DPCameraCapturer.class, SU_TAG, "enableTorch setParameters exception");
                }
                return result;
            }
        } else {
            return false;
        }
    }

    /**
     * 设置分辨率
     *
     * @param resolution
     */
    public void setResolution(Resolution resolution) {
        CodeLogProxy.getInstance().i(DPCameraCapturer.class, SU_TAG, "set resolution " + resolution);
        if (resolution != Resolution.RES_180) {
            mBufferWidth = resolution.getWidth();
            mBufferHeight = resolution.getHeight();
        }
    }

    /**
     * 设置帧率
     *
     * @param frameRate
     */
    public void setFrameRate(int frameRate) {
        mFrameRate = frameRate;
    }


    /**
     * 设置对焦框位置
     *
     * @param x x坐标
     * @param y y坐标
     */
    public void setFocusPosition(float x, float y) {
        if (mEnableAutoFocus) {
            Parameters parameters = null;
            try {
                mCamera.cancelAutoFocus();
                parameters = mCamera.getParameters();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            ArrayList<Area> areas;
            if (mIsCameraFocusPositionInPreviewSupported) {
                areas = new ArrayList();
                areas.add(new Area(calculateAreaRect(x, y, 2.0F), 1000));
                parameters.setFocusAreas(areas);
            }

            if (mSupportMetering) {
                areas = new ArrayList();
                areas.add(new Area(calculateAreaRect(x, y, 3.0F), 1000));
                parameters.setMeteringAreas(areas);
            }

            try {
                mCamera.setParameters(parameters);
                mCamera.autoFocus(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void enablePerformanceMode(boolean enable) {
        mEnablePerformanceMode = enable;
        Log.i("DPCameraCapturer", "set performance mode to " + enable);
    }

    /**
     * 开启自动对焦
     *
     * @param enable
     */
    public void enableAutoFocus(boolean enable) {
        mEnableAutoFocus = enable;
    }

    /**
     * 计算对焦&测量区域
     *
     * @param x           x = x/width
     * @param y           y = y/height
     * @param coefficient 系数
     * @return
     */
    private Rect calculateAreaRect(float x, float y, float coefficient) {
        int areaSize = (int) (200.0F * coefficient);
        // 测量区域是传感器相关的，画面和传感器可能是不匹配的
        // 后置摄像头90，前置270度，表现在在手机水平方向旋转了180，因此对于前置摄像头翻转180
        // 水平方向为view的width，而x = x/width
        if (mIsFrontCamera) {
            x = 1.0F - x;
        }
        // 同样需要处理 homeOrientation
        int rotateCount = mDisplayOrientation / 90;

        for (int i = 0; i < rotateCount; ++i) {
            x -= 0.5F;
            y -= 0.5F;
            y = -y;
            float temp = x;
            x = -y;
            y = -temp;
            x += 0.5F;
            y += 0.5F;
        }

        // 计算left和top坐标，转化为【-1000, 1000】范围内
        int left = (int) (x * 2000.0F - 1000.0F);
        int top = (int) (y * 2000.0F - 1000.0F);
        // 边界处理
        if (left < -1000) {
            left = -1000;
        }
        if (top < -1000) {
            top = -1000;
        }
        // 计算right和bottom坐标，转化为【-1000, 1000】范围内
        int right = left + areaSize;
        int bottom = top + areaSize;
        // 边界处理
        if (right > 1000) {
            right = 1000;
        }
        if (bottom > 1000) {
            bottom = 1000;
        }
        return new Rect(left, top, right, bottom);
    }

    public int getMaxZoom() {
        int result = 0;
        Parameters parameters = getCameraParameters();
        if (parameters != null && parameters.getMaxZoom() > 0 && parameters.isZoomSupported()) {
            result = parameters.getMaxZoom();
        }
        return result;
    }

    public boolean setZoom(int zoom) {
        boolean result = false;
        if (mCamera != null) {
            Parameters cameraParam = getCameraParameters();
            if (cameraParam != null && cameraParam.getMaxZoom() > 0 && cameraParam.isZoomSupported()) {
                if (zoom >= 0 && zoom <= cameraParam.getMaxZoom()) {
                    try {
                        cameraParam.setZoom(zoom);
                        mCamera.setParameters(cameraParam);
                        mZoomLevel = zoom;
                        result = true;
                    } catch (Exception e) {
                        result = false;
                        e.printStackTrace();
                    }
                } else {
                    Log.e("DPCameraCapturer", "invalid zoom value : " + zoom + ", while max zoom is " + cameraParam.getMaxZoom());
                }
            } else {
                Log.e("DPCameraCapturer", "camera not support zoom!");
            }
        }
        return result;
    }

    public int getZoom() {
        return mZoomLevel;
    }

    public void setCaptureBufferSize(boolean enableCapture, int width, int height) {
        this.mEnableCallbackBuffer = enableCapture;
        mBufferWidth = width;
        mBufferHeight = height;
        Log.i("DPCameraCapturer", "setCaptureBuffer %b, width: %d, height: %d");
    }

    public void setExposureCompensation(float rate) {
        if (mCamera != null) {
            if (rate > 1.0F) {
                rate = 1.0F;
            }

            if (rate < -1.0F) {
                rate = -1.0F;
            }

            Parameters cameraParam = getCameraParameters();
            if (cameraParam == null) {
                Log.e("DPCameraCapturer", "camera setExposureCompensation camera parameter is null");
                return;
            }

            int minExposureCompensation = cameraParam.getMinExposureCompensation();
            int maxExposureCompensation = cameraParam.getMaxExposureCompensation();
            if (minExposureCompensation != 0 && maxExposureCompensation != 0) {
                cameraParam.setExposureCompensation(minExposureCompensation
                        + (int) ((maxExposureCompensation - minExposureCompensation) * rate));
            } else {
                Log.e("DPCameraCapturer", "camera not support setExposureCompensation!");
            }

            try {
                mCamera.setParameters(cameraParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public float getExposureCompensation() {
        Parameters parameters = getCameraParameters();
        if (parameters != null) {
            int mMinExposureCompensation = parameters.getMinExposureCompensation();
            int mMaxExposureCompensation = parameters.getMaxExposureCompensation();
            int currentExposureCompensation = parameters.getExposureCompensation();
            return (float) (currentExposureCompensation - mMinExposureCompensation) / (mMaxExposureCompensation - mMinExposureCompensation);
        } else {
            return 0.5f;
        }
    }

    /**
     * 设置采集的视频的旋转角度
     *
     * @param homeOrientation 采集的视频的旋转角度；取值请参见 DPLiveConstants VIDEO_ANGLE_HOME_XXX
     *                        默认值：VIDEO_ANGLE_HOME_DOWN（竖屏推流）
     *                        常用的还有 VIDEO_ANGLE_HOME_RIGHT 和 VIDEO_ANGLE_HOME_LEFT，也就是横屏推流。
     */
    public void setHomeOrientation(int homeOrientation) {
        Log.w("DPCameraCapturer", "vsize setHomeOrientation " + homeOrientation);
        mHomeOrientation = homeOrientation;
        mDisplayOrientation = (mRotation - 90 + mHomeOrientation * 90 + 360) % 360;
    }

    public int startPreview(boolean frontCamera, SurfaceHolder holder) {
        try {
            if (mCamera != null) {
                releaseCamera();
            }

            int frontCameraIndex = -1;
            int backCameraIndex = -1;
            CameraInfo cameraInfo = new CameraInfo();

            for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
                Camera.getCameraInfo(i, cameraInfo);
                Log.i("DPCameraCapturer", "camera index " + i + ", facing = " + cameraInfo.facing);
                if (cameraInfo.facing == 1 && frontCameraIndex == -1) {
                    frontCameraIndex = i;
                }

                if (cameraInfo.facing == 0 && backCameraIndex == -1) {
                    backCameraIndex = i;
                }
            }

            Log.i("DPCameraCapturer", "camera front, id = " + frontCameraIndex);
            Log.i("DPCameraCapturer", "camera back , id = " + backCameraIndex);
            if (frontCameraIndex == -1 && backCameraIndex != -1) {
                frontCameraIndex = backCameraIndex;
            }

            if (backCameraIndex == -1 && frontCameraIndex != -1) {
                backCameraIndex = frontCameraIndex;
            }

            mIsFrontCamera = frontCamera;
            if (mIsFrontCamera) {
                mCamera = Camera.open(frontCameraIndex);
            } else {
                mCamera = Camera.open(backCameraIndex);
            }

            Parameters parameters = mCamera.getParameters();
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (mEnableAutoFocus && supportedFocusModes != null && supportedFocusModes.contains("auto")) {
                Log.i("DPCameraCapturer", "support FOCUS_MODE_AUTO");
                parameters.setFocusMode("auto");
            } else if (supportedFocusModes != null && supportedFocusModes.contains("continuous-video")) {
                Log.i("DPCameraCapturer", "support FOCUS_MODE_CONTINUOUS_VIDEO");
                parameters.setFocusMode("continuous-video");
            }

            if (parameters.getMaxNumFocusAreas() > 0) {
                mIsCameraFocusPositionInPreviewSupported = true;
            }
            if (parameters.getMaxNumMeteringAreas() > 0) {
                mSupportMetering = true;
            }

            Size bufferSize = findSuitableBufferSize(mEnablePerformanceMode, mBufferWidth, mBufferHeight);
            Size previewSize = findSuitablePreviewSize(parameters, Math.max(bufferSize.width, bufferSize.height), Math.min(bufferSize.width, bufferSize.height));
            mPreviewWidth = 1280;
            mPreviewHeight = 720;
            parameters.setPreviewSize(mPreviewWidth, mPreviewHeight);
            int[] fpsRange = getPreviewFPSRange(mFrameRate);
            if (fpsRange != null) {
                parameters.setPreviewFpsRange(fpsRange[0], fpsRange[1]);
            } else {
                parameters.setPreviewFrameRate(calculateFinalFPS(mFrameRate));
            }

            mRotation = getCameraRotation(mIsFrontCamera ? frontCameraIndex : backCameraIndex);
            mDisplayOrientation = (mRotation - 90 + mHomeOrientation * 90 + 360) % 360;
            mCamera.setDisplayOrientation(mDisplayOrientation);
            Log.i("DPCameraCapturer", String.format("WantedSize:W*H = %d*%d, BufferSize: " + bufferSize.toString() + ", PreviewSize: " + previewSize.toString() + ", rotation:%d, displayRotation:%d, homeOrientation:%d ", mBufferWidth, mBufferHeight, mRotation, mDisplayOrientation, mHomeOrientation));
            mCamera.setPreviewDisplay(holder);
            if (mEnableCallbackBuffer) {
                if (mCallbackBuffer == null) {
                    parameters.setPreviewFormat(ImageFormat.NV21);
                    int width = parameters.getPreviewSize().width;
                    int height = parameters.getPreviewSize().height;
                    mCallbackBuffer = new byte[width * height * 3 / 2];
                }
                mCamera.addCallbackBuffer(mCallbackBuffer);
                mCamera.setPreviewCallbackWithBuffer(this);
            }
            mCamera.setParameters(parameters);
            mCamera.setErrorCallback(this);
            mCamera.startPreview();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int startPreview(boolean frontCamera) {
        try {
            Log.i("DPCameraCapturer", "trtc_capture: start capture");
            if (mSurfaceTexture == null) {
                return -2;
            } else {
                if (mCamera != null) {
                    releaseCamera();
                }

                int frontCameraIndex = -1;
                int backCameraIndex = -1;
                CameraInfo cameraInfo = new CameraInfo();

                for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
                    Camera.getCameraInfo(i, cameraInfo);
                    Log.i("DPCameraCapturer", "camera index " + i + ", facing = " + cameraInfo.facing);
                    if (cameraInfo.facing == 1 && frontCameraIndex == -1) {
                        frontCameraIndex = i;
                    }

                    if (cameraInfo.facing == 0 && backCameraIndex == -1) {
                        backCameraIndex = i;
                    }
                }

                Log.i("DPCameraCapturer", "camera front, id = " + frontCameraIndex);
                Log.i("DPCameraCapturer", "camera back , id = " + backCameraIndex);
                if (frontCameraIndex == -1 && backCameraIndex != -1) {
                    frontCameraIndex = backCameraIndex;
                }

                if (backCameraIndex == -1 && frontCameraIndex != -1) {
                    backCameraIndex = frontCameraIndex;
                }

                mIsFrontCamera = frontCamera;
                if (mIsFrontCamera) {
                    mCamera = Camera.open(frontCameraIndex);
                } else {
                    mCamera = Camera.open(backCameraIndex);
                }

                Parameters parameters = mCamera.getParameters();
                List<String> supportedFocusModes = parameters.getSupportedFocusModes();
                if (mEnableAutoFocus && supportedFocusModes != null && supportedFocusModes.contains("auto")) {
                    Log.i("DPCameraCapturer", "support FOCUS_MODE_AUTO");
                    parameters.setFocusMode("auto");
                } else if (supportedFocusModes != null && supportedFocusModes.contains("continuous-video")) {
                    Log.i("DPCameraCapturer", "support FOCUS_MODE_CONTINUOUS_VIDEO");
                    parameters.setFocusMode("continuous-video");
                }

                if (parameters.getMaxNumFocusAreas() > 0) {
                    mIsCameraFocusPositionInPreviewSupported = true;
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    mSupportMetering = true;
                }

                Size bufferSize = findSuitableBufferSize(mEnablePerformanceMode, mBufferWidth, mBufferHeight);
                Size previewSize = findSuitablePreviewSize(parameters, Math.max(bufferSize.width, bufferSize.height), Math.min(bufferSize.width, bufferSize.height));
                mPreviewWidth = previewSize.width;
                mPreviewHeight = previewSize.height;
                parameters.setPreviewSize(mPreviewWidth, mPreviewHeight);
                int[] fpsRange = getPreviewFPSRange(mFrameRate);
                if (fpsRange != null) {
                    parameters.setPreviewFpsRange(fpsRange[0], fpsRange[1]);
                } else {
                    parameters.setPreviewFrameRate(calculateFinalFPS(mFrameRate));
                }

                mRotation = getCameraRotation(mIsFrontCamera ? frontCameraIndex : backCameraIndex);
                mDisplayOrientation = (mRotation - 90 + mHomeOrientation * 90 + 360) % 360;
                mCamera.setDisplayOrientation(mDisplayOrientation);
                Log.i("DPCameraCapturer", String.format("WantedSize:W*H = %d*%d, BufferSize: " + bufferSize.toString() + ", PreviewSize: " + previewSize.toString() + ", rotation:%d, displayRotation:%d, homeOrientation:%d ", mBufferWidth, mBufferHeight, mRotation, mDisplayOrientation, mHomeOrientation));
                mCamera.setPreviewTexture(mSurfaceTexture);
                if (mEnableCallbackBuffer) {
                    if (mCallbackBuffer == null) {
                        parameters.setPreviewFormat(ImageFormat.NV21);
                        int width = parameters.getPreviewSize().width;
                        int height = parameters.getPreviewSize().height;
                        mCallbackBuffer = new byte[width * height * 3 / 2];
                    }
                    mCamera.addCallbackBuffer(mCallbackBuffer);
                    mCamera.setPreviewCallbackWithBuffer(this);
                }
                mCamera.setParameters(parameters);
                mCamera.setErrorCallback(this);
                mCamera.startPreview();
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 关闭摄像头，释放资源
     */
    public void releaseCamera() {
        if (mCamera != null) {
            try {
                mCamera.setErrorCallback(null);
                mCamera.setPreviewCallback(null);
                mCamera.setPreviewCallbackWithBuffer(null);
                mCamera.stopPreview();
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mCamera = null;
                mSurfaceTexture = null;
            }
        }
    }

    private static Size findSuitableBufferSize(boolean enableFix, int width, int height) {
        if (enableFix) {
            return new Size(width, height);
        } else {
            Size[] sizes = new Size[]{new Size(1080, 1920)};
            float min = (float) Math.min(width, height);
            float max = (float) Math.max(width, height);

            for (int i = 0; i < sizes.length; ++i) {
                Size temp = sizes[i];
                if (min <= (float) temp.width && max <= (float) temp.height) {
                    float minRadio = Math.min((float) temp.width / min, (float) temp.height / max);
                    width = (int) ((float) width * minRadio);
                    height = (int) ((float) height * minRadio);
                    break;
                }
            }
            return new Size(width, height);
        }
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        if (mCamera != null && mEnableCallbackBuffer) {
            mCamera.addCallbackBuffer(data);
        }
        if (mFrameListener != null) {
            mFrameListener.onFrame(data);
        }
    }

    public int getDisplayOrientation() {
        return mDisplayOrientation;
    }

    public boolean isFrontCamera() {
        return mIsFrontCamera;
    }

    public int getPreviewWidth() {
        return mPreviewWidth;
    }

    public int getPreviewHeight() {
        return mPreviewHeight;
    }

    public Camera getCamera() {
        return mCamera;
    }

    /**
     * 查询相机是否支持缩放
     *
     * @return
     */
    public boolean isZoomSupported() {
        if (mCamera != null) {
            Parameters param = getCameraParameters();
            return param != null && param.getMaxZoom() > 0 && param.isZoomSupported();
        }
        return false;
    }

    /**
     * 查询是否支持闪光灯
     *
     * @return
     */
    public boolean isCameraTorchSupported() {
        if (mCamera != null) {
            Parameters param = getCameraParameters();
            if (param == null) {
                return false;
            } else {
                List flashModes = param.getSupportedFlashModes();
                return flashModes != null && flashModes.contains("torch");
            }
        } else {
            return false;
        }
    }

    /**
     * 查询是否支持触摸对焦
     * TODO 需要判断是否在open之前能够获取该值
     *
     * @return
     */
    public boolean isCameraFocusPositionInPreviewSupported() {
        return mIsCameraFocusPositionInPreviewSupported;
    }

    /**
     * 查询是否支持人脸识别
     *
     * @return
     */
    public boolean isCameraAutoFocusFaceModeSupported() {
        if (mCamera == null) {
            return false;
        } else {
            Parameters param = getCameraParameters();
            return param != null && param.getMaxNumDetectedFaces() > 0;
        }
    }

    private static Size findSuitablePreviewSize(Parameters parameters, int width, int height) {
        Log.d("DPCameraCapturer", String.format("camera preview wanted: %dx%d", width, height));
        List previewSizes = parameters.getSupportedPreviewSizes();
        float aspectRatio = (float) width / (float) height;
        int minRatio = Integer.MAX_VALUE;
        ArrayList<Camera.Size> sizeList = new ArrayList();

        Iterator iterator = previewSizes.iterator();

        while (iterator.hasNext()) {
            Camera.Size item = (Camera.Size) iterator.next();
            Log.d("DPCameraCapturer", String.format("camera support preview size: %dx%d", item.width, item.height));
            int currentRatio;
            if (item.width >= 640 && item.height >= 480) {
                currentRatio = Math.round(10.0F * Math.abs((float) item.width / (float) item.height - aspectRatio));
            } else {
                currentRatio = Integer.MAX_VALUE;
            }

            if (currentRatio < minRatio) {
                minRatio = currentRatio;
                sizeList.clear();
                sizeList.add(item);
            } else if (currentRatio == minRatio) {
                sizeList.add(item);
            }
        }

        Collections.sort(sizeList, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size size1, Camera.Size size2) {
                return size2.width * size2.height - size1.width * size1.height;
            }
        });
        Camera.Size targetSize = (Camera.Size) sizeList.get(0);
        float requirePixelCount = (float) (width * height);
        float minSize = Integer.MAX_VALUE;
        Iterator sizeIterator = sizeList.iterator();
        while (sizeIterator.hasNext()) {
            Camera.Size currentSize = (Camera.Size) sizeIterator.next();
            int pixelCount = currentSize.width * currentSize.height;
            if ((double) ((float) pixelCount / requirePixelCount) >= 0.9D && Math.abs((float) pixelCount - requirePixelCount) < minSize) {
                targetSize = currentSize;
                minSize = Math.abs((float) pixelCount - requirePixelCount);
            }
        }

        return new Size(targetSize.width, targetSize.height);
    }

    public void onAutoFocus(boolean result, Camera camera) {
        if (result) {
            Log.i("DPCameraCapturer", "AUTO focus success");
        } else {
            Log.i("DPCameraCapturer", "AUTO focus failed");
        }

    }

    public void onError(int error, Camera camera) {
        Log.w("DPCameraCapturer", "camera catch error " + error);
        if ((error == 1 || error == 2 || error == 100) && mFrameListener != null) {
            mFrameListener.onError();
        }

    }

    private int calculateFinalFPS(int requireFPS) {
        byte defaultFPS = 1;
        Parameters cameraParam = getCameraParameters();
        if (cameraParam == null) {
            return defaultFPS;
        } else {
            List<Integer> frameRates = cameraParam.getSupportedPreviewFrameRates();
            if (frameRates == null) {
                Log.e("DPCameraCapturer", "getSupportedFPS error");
                return defaultFPS;
            } else {
                int fps = frameRates.get(0);
                for (int i = 0; i < frameRates.size(); ++i) {
                    int current = frameRates.get(i);
                    if (Math.abs(current - requireFPS) - Math.abs(fps - requireFPS) < 0) {
                        fps = current;
                    }
                }
                Log.i("DPCameraCapturer", "choose fps=" + fps);
                return fps;
            }
        }
    }

    private int[] getPreviewFPSRange(int requireFPS) {
        requireFPS *= 1000;
        String logMsg = "camera supported preview fps range: wantFPS = " + requireFPS + "\n";
        Parameters parameters = getCameraParameters();
        if (parameters == null) {
            return null;
        } else {
            List supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
            if (supportedPreviewFpsRange != null && supportedPreviewFpsRange.size() > 0) {
                int[] result = (int[]) supportedPreviewFpsRange.get(0);
                Collections.sort(supportedPreviewFpsRange, new Comparator<int[]>() {
                    @Override
                    public int compare(int[] o1, int[] o2) {
                        return o1[1] - o2[1];
                    }
                });

                Iterator iterator;
                int[] tempSize;
                for (iterator = supportedPreviewFpsRange.iterator(); iterator.hasNext(); logMsg = logMsg + "camera supported preview fps range: " + tempSize[0] + " - " + tempSize[1] + "\n") {
                    tempSize = (int[]) iterator.next();
                }

                iterator = supportedPreviewFpsRange.iterator();

                while (iterator.hasNext()) {
                    tempSize = (int[]) iterator.next();
                    if (tempSize[0] <= requireFPS && requireFPS <= tempSize[1]) {
                        result = tempSize;
                        break;
                    }
                }

                logMsg = logMsg + "choose preview fps range: " + result[0] + " - " + result[1];
                Log.i("DPCameraCapturer", logMsg);
                return result;
            } else {
                return null;
            }
        }
    }

    private int getCameraRotation(int cameraId) {
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        Log.i("DPCameraCapturer", "vsize camera orientation " + cameraInfo.orientation + ", front " + (cameraInfo.facing == 1));
        int cameraOrientation = cameraInfo.orientation;
        if (cameraOrientation == 0 || cameraOrientation == 180) {
            cameraOrientation += 90;
        }
        int result;
        if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
            result = (360 - cameraOrientation) % 360;
        } else {
            result = (cameraOrientation + 360) % 360;
        }
        return result;
    }

    /**
     * 相机支持设置的分辨率
     */
    public static enum Resolution {

        RES_NONE(-1, -1),
        RES_180(180, 320),
        RES_270(270, 480),
        RES_320(320, 480),
        RES_360(360, 640),
        RES_540(540, 960),
        RES_720(720, 1280),
        RES_1080(1080, 1920);

        private final int mWidth;
        private final int mHeight;

        private Resolution(int width, int height) {
            mWidth = width;
            mHeight = height;
        }

        private int getWidth() {
            return mWidth;
        }

        private int getHeight() {
            return mHeight;
        }
    }

    /**
     * 预览帧回调
     */
    public interface DPCameraFrameListener {
        void onFrame(byte[] data);

        void onError();
    }

    public static class Size {
        /**
         * width of the picture
         */
        public int width;
        /**
         * height of the picture
         */
        public int height;

        /**
         * Sets the dimensions for pictures.
         *
         * @param w the photo width (pixels)
         * @param h the photo height (pixels)
         */
        public Size(int w, int h) {
            width = w;
            height = h;
        }

        /**
         * Compares {@code obj} to this size.
         *
         * @param obj the object to compare this size with.
         * @return {@code true} if the width and height of {@code obj} is the
         * same as those of this size. {@code false} otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Camera.Size)) {
                return false;
            }
            Camera.Size s = (Camera.Size) obj;
            return width == s.width && height == s.height;
        }

        @Override
        public int hashCode() {
            return width * 32713 + height;
        }

        public void swap() {
            int temp = width;
            width = height;
            height = temp;
        }

        @Override
        public String toString() {
            return "Size{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }
}

