package com.tencent.liteav.renderer;

import com.tencent.liteav.basic.log.*;
import java.nio.*;
import com.tencent.liteav.basic.structs.*;
import android.opengl.*;
import com.tencent.liteav.basic.util.*;

public class TXCYuvTextureRender
{
    private static final String TAG;
    private static final String mVertexShaderCode = "uniform mat4 uMatrix;uniform mat4 uTextureMatrix;attribute vec2 position;attribute vec2 inputTextureCoordinate;varying vec2 textureCoordinate;void main() {vec4 pos  = vec4(position, 0.0, 1.0);gl_Position = uMatrix * pos;textureCoordinate = (uTextureMatrix*vec4(inputTextureCoordinate, 0.0, 0.0)).xy;}";
    private static final String mFragmentShaderCode = "precision highp float;\nvarying vec2 textureCoordinate;\nuniform sampler2D yTexture;\nuniform sampler2D uTexture;\nuniform mat3 convertMatrix;\nuniform vec3 offset;\n\nvoid main()\n{\n    highp vec3 yuvColor;\n    highp vec3 rgbColor;\n\n    // Get the YUV values\n    yuvColor.x = texture2D(yTexture, textureCoordinate).r;\n    yuvColor.y = texture2D(uTexture, vec2(textureCoordinate.x * 0.5, textureCoordinate.y * 0.5)).r;\n    yuvColor.z = texture2D(uTexture, vec2(textureCoordinate.x * 0.5, textureCoordinate.y * 0.5 + 0.5)).r;\n\n    // Do the color transform   \n    yuvColor += offset;\n    rgbColor = convertMatrix * yuvColor; \n\n    gl_FragColor = vec4(rgbColor, 1.0);\n}\n";
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private float[] mVerticesCoordinates;
    private short[] mIndices;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureBuffer;
    private ShortBuffer mIndicesBuffer;
    private float[] mMVPMatrix;
    private float[] mTextureMatrix;
    private int[] mTextureIds;
    private int mProgram;
    private int mVertexMatrixHandle;
    private int mTextureMatrixHandle;
    private int mPositionHandle;
    private int mTextureCoordinatesHandle;
    private int mTextureUnitHandle0;
    private int mTextureUnitHandle1;
    private int mConvertMatrixUniform;
    private int mConvertOffsetUniform;
    private boolean mNeedReLoadFrameBuffer;
    private int mHeight;
    private int mWidth;
    private static final int INVALID_TEXTURE_ID = -12345;
    private int mFrameBufferTextureID;
    private int mFrameBufferID;
    private int mVideoWidth;
    private int mVideoHeight;
    private final int def_InputType_YUVJ420 = 4;
    private int mRawDataFrameType;
    private float[] mbt601_fullRange_matrix3;
    private float[] mbt601_videoRange_matrix3;
    private float[] mbt601_offset_matrix;
    private float[] mbt709_videoRange_matrix3;
    float[] bt601_videorange_ffmpeg_offset;
    float[] bt601_videorage_ffmpeg_matrix;
    float[] bt601_fullrange_ffmpeg_offset;
    float[] bt601_fullrage_ffmpeg_matrix;
    
    public TXCYuvTextureRender() {
        this.mMVPMatrix = new float[16];
        this.mTextureMatrix = new float[16];
        this.mConvertMatrixUniform = -1;
        this.mConvertOffsetUniform = -1;
        this.mNeedReLoadFrameBuffer = false;
        this.mFrameBufferTextureID = -12345;
        this.mFrameBufferID = -12345;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mRawDataFrameType = -1;
        this.mbt601_fullRange_matrix3 = new float[] { 1.0f, 1.0f, 1.0f, 0.0f, -0.343f, 1.765f, 1.4f, -0.711f, 0.0f };
        this.mbt601_videoRange_matrix3 = new float[] { 1.164f, 1.164f, 1.164f, 0.0f, -0.392f, 2.017f, 1.596f, -0.813f, 0.0f };
        this.mbt601_offset_matrix = new float[] { 0.0f, -0.5f, -0.5f };
        this.mbt709_videoRange_matrix3 = new float[] { 1.164f, 1.164f, 1.164f, 0.0f, -0.213f, 2.112f, 1.793f, -0.533f, 0.0f };
        this.bt601_videorange_ffmpeg_offset = new float[] { -0.0627451f, -0.5019608f, -0.5019608f };
        this.bt601_videorage_ffmpeg_matrix = new float[] { 1.1644f, 1.1644f, 1.1644f, 0.0f, -0.3918f, 2.0172f, 1.596f, -0.813f, 0.0f };
        this.bt601_fullrange_ffmpeg_offset = new float[] { 0.0f, -0.5019608f, -0.5019608f };
        this.bt601_fullrage_ffmpeg_matrix = new float[] { 1.0f, 1.0f, 1.0f, 0.0f, -0.3441f, 1.772f, 1.402f, -0.7141f, 0.0f };
        final float[] array = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
        this.mIndices = new short[] { 0, 1, 2, 1, 3, 2 };
        this.mVerticesCoordinates = new float[] { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f };
        (this.mTextureBuffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()).put(array);
        this.mTextureBuffer.position(0);
        (this.mVertexBuffer = ByteBuffer.allocateDirect(this.mVerticesCoordinates.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()).put(this.mVerticesCoordinates);
        this.mVertexBuffer.position(0);
        (this.mIndicesBuffer = ByteBuffer.allocateDirect(this.mIndices.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer()).put(this.mIndices);
        this.mIndicesBuffer.position(0);
    }
    
    public void setVideoSize(final int mVideoWidth, final int mVideoHeight) {
        this.mVideoWidth = mVideoWidth;
        this.mVideoHeight = mVideoHeight;
    }
    
    public void createTexture() {
        final int glCreateShader = GLES20.glCreateShader(35633);
        this.checkError();
        GLES20.glShaderSource(glCreateShader, "uniform mat4 uMatrix;uniform mat4 uTextureMatrix;attribute vec2 position;attribute vec2 inputTextureCoordinate;varying vec2 textureCoordinate;void main() {vec4 pos  = vec4(position, 0.0, 1.0);gl_Position = uMatrix * pos;textureCoordinate = (uTextureMatrix*vec4(inputTextureCoordinate, 0.0, 0.0)).xy;}");
        this.checkError();
        GLES20.glCompileShader(glCreateShader);
        this.checkError();
        final int glCreateShader2 = GLES20.glCreateShader(35632);
        this.checkError();
        GLES20.glShaderSource(glCreateShader2, "precision highp float;\nvarying vec2 textureCoordinate;\nuniform sampler2D yTexture;\nuniform sampler2D uTexture;\nuniform mat3 convertMatrix;\nuniform vec3 offset;\n\nvoid main()\n{\n    highp vec3 yuvColor;\n    highp vec3 rgbColor;\n\n    // Get the YUV values\n    yuvColor.x = texture2D(yTexture, textureCoordinate).r;\n    yuvColor.y = texture2D(uTexture, vec2(textureCoordinate.x * 0.5, textureCoordinate.y * 0.5)).r;\n    yuvColor.z = texture2D(uTexture, vec2(textureCoordinate.x * 0.5, textureCoordinate.y * 0.5 + 0.5)).r;\n\n    // Do the color transform   \n    yuvColor += offset;\n    rgbColor = convertMatrix * yuvColor; \n\n    gl_FragColor = vec4(rgbColor, 1.0);\n}\n");
        this.checkError();
        GLES20.glCompileShader(glCreateShader2);
        this.mProgram = GLES20.glCreateProgram();
        this.checkError();
        GLES20.glAttachShader(this.mProgram, glCreateShader);
        this.checkError();
        GLES20.glAttachShader(this.mProgram, glCreateShader2);
        this.checkError();
        GLES20.glLinkProgram(this.mProgram);
        this.checkError();
        GLES20.glDeleteShader(glCreateShader);
        GLES20.glDeleteShader(glCreateShader2);
        this.mVertexMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uMatrix");
        this.checkError();
        this.mTextureMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uTextureMatrix");
        this.checkError();
        this.mPositionHandle = GLES20.glGetAttribLocation(this.mProgram, "position");
        this.checkError();
        this.mTextureCoordinatesHandle = GLES20.glGetAttribLocation(this.mProgram, "inputTextureCoordinate");
        this.checkError();
        this.mTextureUnitHandle0 = GLES20.glGetUniformLocation(this.mProgram, "yTexture");
        this.checkError();
        this.mTextureUnitHandle1 = GLES20.glGetUniformLocation(this.mProgram, "uTexture");
        this.checkError();
        GLES20.glUniform3fv(this.mConvertOffsetUniform = GLES20.glGetUniformLocation(this.mProgram, "offset"), 1, FloatBuffer.wrap(this.bt601_fullrange_ffmpeg_offset));
        GLES20.glUniformMatrix3fv(this.mConvertMatrixUniform = GLES20.glGetUniformLocation(this.mProgram, "convertMatrix"), 1, false, this.bt601_fullrage_ffmpeg_matrix, 0);
        GLES20.glGenTextures(2, this.mTextureIds = new int[2], 0);
    }
    
    public void onSurfaceDestroy() {
        if (this.mTextureIds != null) {
            GLES20.glDeleteTextures(2, this.mTextureIds, 0);
            this.mTextureIds = null;
        }
        this.destroyFrameBuffer();
        GLES20.glDeleteProgram(this.mProgram);
    }
    
    public void setHasFrameBuffer(final int mWidth, final int mHeight) {
        if (this.mWidth == mWidth && this.mHeight == mHeight) {
            return;
        }
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mNeedReLoadFrameBuffer = true;
    }
    
    public void flipVertical(final boolean b) {
        float[] array;
        if (b) {
            array = new float[] { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
        }
        else {
            array = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
        }
        (this.mTextureBuffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()).put(array);
        this.mTextureBuffer.position(0);
    }
    
    private void reloadFrameBuffer() {
        if (!this.mNeedReLoadFrameBuffer) {
            return;
        }
        TXCLog.i(TXCYuvTextureRender.TAG, "reloadFrameBuffer. size = " + this.mWidth + "*" + this.mHeight);
        this.destroyFrameBuffer();
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glGenFramebuffers(1, array2, 0);
        this.mFrameBufferTextureID = array[0];
        this.mFrameBufferID = array2[0];
        TXCLog.d(TXCYuvTextureRender.TAG, "frameBuffer id = " + this.mFrameBufferID + ", texture id = " + this.mFrameBufferTextureID);
        GLES20.glBindTexture(3553, this.mFrameBufferTextureID);
        GLES20.glTexImage2D(3553, 0, 6408, this.mWidth, this.mHeight, 0, 6408, 5121, (Buffer)null);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindFramebuffer(36160, this.mFrameBufferID);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.mFrameBufferTextureID, 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.mNeedReLoadFrameBuffer = false;
    }
    
    private void destroyFrameBuffer() {
        if (this.mFrameBufferID != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[] { this.mFrameBufferID }, 0);
            this.mFrameBufferID = -12345;
        }
        if (this.mFrameBufferTextureID != -12345) {
            GLES20.glDeleteTextures(1, new int[] { this.mFrameBufferTextureID }, 0);
            this.mFrameBufferTextureID = -12345;
        }
    }
    
    public int drawToTexture(final TXSVideoFrame txsVideoFrame) {
        this.reloadFrameBuffer();
        if (this.mFrameBufferID == -12345) {
            TXCLog.w(TXCYuvTextureRender.TAG, "invalid frame buffer id");
            return -12345;
        }
        GLES20.glBindFramebuffer(36160, this.mFrameBufferID);
        GLES20.glViewport(0, 0, this.mWidth, this.mHeight);
        this.drawFrame(txsVideoFrame);
        GLES20.glBindFramebuffer(36160, 0);
        return this.mFrameBufferTextureID;
    }
    
    public void drawFrame(final TXSVideoFrame txsVideoFrame) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        Matrix.setIdentityM(this.mMVPMatrix, 0);
        Matrix.setIdentityM(this.mTextureMatrix, 0);
        GLES20.glUseProgram(this.mProgram);
        this.checkError();
        GLES20.glEnableVertexAttribArray(this.mPositionHandle);
        this.checkError();
        this.mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(this.mPositionHandle, 2, 5126, false, 8, (Buffer)this.mVertexBuffer);
        this.checkError();
        GLES20.glEnableVertexAttribArray(this.mTextureCoordinatesHandle);
        this.checkError();
        this.mTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(this.mTextureCoordinatesHandle, 2, 5126, false, 8, (Buffer)this.mTextureBuffer);
        this.checkError();
        GLES20.glUniformMatrix4fv(this.mVertexMatrixHandle, 1, false, this.mMVPMatrix, 0);
        this.checkError();
        GLES20.glUniformMatrix4fv(this.mTextureMatrixHandle, 1, false, this.mTextureMatrix, 0);
        this.checkError();
        final int frameType = txsVideoFrame.frameType;
        if (4 == frameType) {
            GLES20.glUniform3fv(this.mConvertOffsetUniform, 1, FloatBuffer.wrap(this.bt601_fullrange_ffmpeg_offset));
            GLES20.glUniformMatrix3fv(this.mConvertMatrixUniform, 1, false, this.bt601_fullrage_ffmpeg_matrix, 0);
            if (frameType != this.mRawDataFrameType) {
                this.mRawDataFrameType = frameType;
                TXCLog.i(TXCYuvTextureRender.TAG, " frame type " + frameType + " matrix_test fullRange");
            }
        }
        else {
            GLES20.glUniform3fv(this.mConvertOffsetUniform, 1, FloatBuffer.wrap(this.bt601_videorange_ffmpeg_offset));
            GLES20.glUniformMatrix3fv(this.mConvertMatrixUniform, 1, false, this.bt601_videorage_ffmpeg_matrix, 0);
            if (frameType != this.mRawDataFrameType) {
                this.mRawDataFrameType = frameType;
                TXCLog.i(TXCYuvTextureRender.TAG, " frame type " + frameType + " matrix_test videoRange");
            }
        }
        GLES20.glUniform1i(this.mTextureUnitHandle0, 0);
        this.checkError();
        GLES20.glUniform1i(this.mTextureUnitHandle1, 1);
        this.checkError();
        if (txsVideoFrame.buffer != null && this.mTextureIds != null) {
            nativeLoadTexture(txsVideoFrame.buffer, txsVideoFrame.width, txsVideoFrame.height, this.mTextureIds);
        }
        txsVideoFrame.release();
        GLES20.glDrawElements(4, this.mIndices.length, 5123, (Buffer)this.mIndicesBuffer);
        GLES20.glDisableVertexAttribArray(this.mPositionHandle);
        GLES20.glDisableVertexAttribArray(this.mTextureCoordinatesHandle);
    }
    
    public int checkError() {
        final int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            throw new IllegalStateException("gl error=" + glGetError);
        }
        return glGetError;
    }
    
    public static native void nativeLoadTexture(final ByteBuffer p0, final int p1, final int p2, final int[] p3);
    
    static {
        TAG = TXCYuvTextureRender.class.getSimpleName();
        f.f();
    }
}
