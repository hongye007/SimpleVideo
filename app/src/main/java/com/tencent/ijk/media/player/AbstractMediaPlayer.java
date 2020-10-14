package com.tencent.ijk.media.player;

import com.tencent.ijk.media.player.misc.*;

public abstract class AbstractMediaPlayer implements IMediaPlayer
{
    private OnPreparedListener mOnPreparedListener;
    private OnCompletionListener mOnCompletionListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnTimedTextListener mOnTimedTextListener;
    private OnHLSKeyErrorListener mOnHLSKeyErrorListener;
    private OnHevcVideoDecoderErrorListener mOnHevcVideoDecoderErrorListener;
    private OnVideoDecoderErrorListener mOnVideoDecoderErrorListener;
    
    @Override
    public final void setOnPreparedListener(final OnPreparedListener mOnPreparedListener) {
        this.mOnPreparedListener = mOnPreparedListener;
    }
    
    @Override
    public final void setOnCompletionListener(final OnCompletionListener mOnCompletionListener) {
        this.mOnCompletionListener = mOnCompletionListener;
    }
    
    @Override
    public final void setOnBufferingUpdateListener(final OnBufferingUpdateListener mOnBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = mOnBufferingUpdateListener;
    }
    
    @Override
    public final void setOnSeekCompleteListener(final OnSeekCompleteListener mOnSeekCompleteListener) {
        this.mOnSeekCompleteListener = mOnSeekCompleteListener;
    }
    
    @Override
    public final void setOnVideoSizeChangedListener(final OnVideoSizeChangedListener mOnVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = mOnVideoSizeChangedListener;
    }
    
    @Override
    public final void setOnErrorListener(final OnErrorListener mOnErrorListener) {
        this.mOnErrorListener = mOnErrorListener;
    }
    
    @Override
    public final void setOnInfoListener(final OnInfoListener mOnInfoListener) {
        this.mOnInfoListener = mOnInfoListener;
    }
    
    @Override
    public final void setOnTimedTextListener(final OnTimedTextListener mOnTimedTextListener) {
        this.mOnTimedTextListener = mOnTimedTextListener;
    }
    
    @Override
    public final void setOnHLSKeyErrorListener(final OnHLSKeyErrorListener mOnHLSKeyErrorListener) {
        this.mOnHLSKeyErrorListener = mOnHLSKeyErrorListener;
    }
    
    @Override
    public final void setOnHevcVideoDecoderErrorListener(final OnHevcVideoDecoderErrorListener mOnHevcVideoDecoderErrorListener) {
        this.mOnHevcVideoDecoderErrorListener = mOnHevcVideoDecoderErrorListener;
    }
    
    @Override
    public final void setOnVideoDecoderErrorListener(final OnVideoDecoderErrorListener mOnVideoDecoderErrorListener) {
        this.mOnVideoDecoderErrorListener = mOnVideoDecoderErrorListener;
    }
    
    public void resetListeners() {
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnTimedTextListener = null;
        this.mOnHLSKeyErrorListener = null;
        this.mOnHevcVideoDecoderErrorListener = null;
        this.mOnVideoDecoderErrorListener = null;
    }
    
    protected final void notifyOnPrepared() {
        if (this.mOnPreparedListener != null) {
            this.mOnPreparedListener.onPrepared(this);
        }
    }
    
    protected final void notifyOnCompletion() {
        if (this.mOnCompletionListener != null) {
            this.mOnCompletionListener.onCompletion(this);
        }
    }
    
    protected final void notifyOnBufferingUpdate(final int n) {
        if (this.mOnBufferingUpdateListener != null) {
            this.mOnBufferingUpdateListener.onBufferingUpdate(this, n);
        }
    }
    
    protected final void notifyOnSeekComplete() {
        if (this.mOnSeekCompleteListener != null) {
            this.mOnSeekCompleteListener.onSeekComplete(this);
        }
    }
    
    protected final void notifyOnVideoSizeChanged(final int n, final int n2, final int n3, final int n4) {
        if (this.mOnVideoSizeChangedListener != null) {
            this.mOnVideoSizeChangedListener.onVideoSizeChanged(this, n, n2, n3, n4);
        }
    }
    
    protected final boolean notifyOnError(final int n, final int n2) {
        return this.mOnErrorListener != null && this.mOnErrorListener.onError(this, n, n2);
    }
    
    protected final boolean notifyOnInfo(final int n, final int n2) {
        return this.mOnInfoListener != null && this.mOnInfoListener.onInfo(this, n, n2);
    }
    
    protected final void notifyOnTimedText(final IjkTimedText ijkTimedText) {
        if (this.mOnTimedTextListener != null) {
            this.mOnTimedTextListener.onTimedText(this, ijkTimedText);
        }
    }
    
    @Override
    public void setDataSource(final IMediaDataSource mediaDataSource) {
        throw new UnsupportedOperationException();
    }
    
    protected final void notifyHLSKeyError() {
        if (this.mOnHLSKeyErrorListener != null) {
            this.mOnHLSKeyErrorListener.onHLSKeyError(this);
        }
    }
    
    protected final void notifyHevcVideoDecoderError() {
        if (this.mOnHevcVideoDecoderErrorListener != null) {
            this.mOnHevcVideoDecoderErrorListener.onHevcVideoDecoderError(this);
        }
    }
    
    protected final void notifyVideoDecoderError() {
        if (this.mOnVideoDecoderErrorListener != null) {
            this.mOnVideoDecoderErrorListener.onVideoDecoderError(this);
        }
    }
}
