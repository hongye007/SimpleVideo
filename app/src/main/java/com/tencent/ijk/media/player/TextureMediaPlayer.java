package com.tencent.ijk.media.player;

import android.annotation.*;
import android.graphics.*;
import android.view.*;
import java.util.*;

@TargetApi(14)
public class TextureMediaPlayer extends MediaPlayerProxy implements IMediaPlayer, ISurfaceTextureHolder
{
    private SurfaceTexture mSurfaceTexture;
    private ISurfaceTextureHost mSurfaceTextureHost;
    private boolean mReuseSurfaceTexture;
    private IMediaPlayer mBackEndMediaPlayer;
    private Surface mSurface;
    
    public TextureMediaPlayer(final IMediaPlayer mBackEndMediaPlayer) {
        super(mBackEndMediaPlayer);
        this.mBackEndMediaPlayer = mBackEndMediaPlayer;
    }
    
    public void releaseSurfaceTexture() {
        if (this.mSurfaceTexture != null && !this.mReuseSurfaceTexture) {
            if (this.mSurfaceTextureHost != null) {
                this.mSurfaceTextureHost.releaseSurfaceTexture(this.mSurfaceTexture);
            }
            else {
                this.mSurfaceTexture.release();
            }
            this.mSurfaceTexture = null;
        }
    }
    
    @Override
    public void reset() {
        super.reset();
        this.releaseSurfaceTexture();
    }
    
    @Override
    public void release() {
        super.release();
        this.releaseSurfaceTexture();
    }
    
    @Override
    public void setDisplay(final SurfaceHolder display) {
        if (this.mSurfaceTexture == null) {
            super.setDisplay(display);
        }
    }
    
    @Override
    public void setSurface(final Surface surface) {
        if (this.mSurfaceTexture == null) {
            super.setSurface(surface);
        }
        this.mSurface = surface;
    }
    
    @Override
    public Surface getSurface() {
        return super.getSurface();
    }
    
    @Override
    public void setSurfaceTexture(final SurfaceTexture mSurfaceTexture) {
        if (this.mSurfaceTexture == mSurfaceTexture) {
            return;
        }
        this.releaseSurfaceTexture();
        if ((this.mSurfaceTexture = mSurfaceTexture) == null) {
            super.setSurface(this.mSurface = null);
        }
        else {
            if (this.mSurface == null) {
                this.mSurface = new Surface(mSurfaceTexture);
            }
            super.setSurface(this.mSurface);
        }
    }
    
    @Override
    public SurfaceTexture getSurfaceTexture() {
        return this.mSurfaceTexture;
    }
    
    @Override
    public void setSurfaceTextureHost(final ISurfaceTextureHost mSurfaceTextureHost) {
        this.mSurfaceTextureHost = mSurfaceTextureHost;
    }
    
    public void setReuseSurfaceTexture(final boolean mReuseSurfaceTexture) {
        this.mReuseSurfaceTexture = mReuseSurfaceTexture;
    }
    
    public IMediaPlayer getBackEndMediaPlayer() {
        return this.mBackEndMediaPlayer;
    }
    
    @Override
    public int getBitrateIndex() {
        return this.mBackEndMediaPlayer.getBitrateIndex();
    }
    
    @Override
    public void setBitrateIndex(final int bitrateIndex) {
        this.mBackEndMediaPlayer.setBitrateIndex(bitrateIndex);
    }
    
    @Override
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return this.mBackEndMediaPlayer.getSupportedBitrates();
    }
}
