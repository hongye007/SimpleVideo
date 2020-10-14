package com.tencent.liteav.txcvodplayer;

import android.os.*;
import com.tencent.ijk.media.player.*;
import android.util.*;

public class b
{
    private IMediaPlayer a;
    private Handler b;
    
    public b() {
        this.b = new Handler() {
            public void handleMessage(final Message message) {
                switch (message.what) {
                    case 1: {
                        final b a = com.tencent.liteav.txcvodplayer.b.this;
                        IjkMediaPlayer ijkMediaPlayer = null;
                        if (com.tencent.liteav.txcvodplayer.b.this.a == null) {
                            break;
                        }
                        if (com.tencent.liteav.txcvodplayer.b.this.a instanceof IjkMediaPlayer) {
                            ijkMediaPlayer = (IjkMediaPlayer)com.tencent.liteav.txcvodplayer.b.this.a;
                        }
                        else if (com.tencent.liteav.txcvodplayer.b.this.a instanceof MediaPlayerProxy) {
                            final IMediaPlayer internalMediaPlayer = ((MediaPlayerProxy)com.tencent.liteav.txcvodplayer.b.this.a).getInternalMediaPlayer();
                            if (internalMediaPlayer != null && internalMediaPlayer instanceof IjkMediaPlayer) {
                                ijkMediaPlayer = (IjkMediaPlayer)internalMediaPlayer;
                            }
                        }
                        if (ijkMediaPlayer == null) {
                            break;
                        }
                        final float rate = ijkMediaPlayer.getRate();
                        if (Math.abs(ijkMediaPlayer.getAVDiff()) > 0.5f && rate > 1.0f) {
                            final float rate2 = (float)(rate - Math.min((rate - 1.0) / 2.0, 0.25));
                            ijkMediaPlayer.setRate(rate2);
                            Log.w("RateHelper", "downside rate " + rate2);
                            com.tencent.liteav.txcvodplayer.b.this.b.removeMessages(1);
                            com.tencent.liteav.txcvodplayer.b.this.b.sendEmptyMessageDelayed(1, 3000L);
                            break;
                        }
                        com.tencent.liteav.txcvodplayer.b.this.b.removeMessages(1);
                        com.tencent.liteav.txcvodplayer.b.this.b.sendEmptyMessageDelayed(1, 500L);
                        break;
                    }
                }
            }
        };
    }
    
    public void a(final IMediaPlayer a) {
        this.a = a;
        if (this.a != null) {
            this.b.sendEmptyMessageDelayed(1, 500L);
        }
        else {
            this.b.removeMessages(1);
        }
    }
}
