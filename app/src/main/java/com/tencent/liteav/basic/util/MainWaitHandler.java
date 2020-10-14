package com.tencent.liteav.basic.util;

import android.os.*;
import java.util.concurrent.*;

public class MainWaitHandler extends Handler
{
    public MainWaitHandler(final Looper looper) {
        super(looper);
    }
    
    public boolean a(final Runnable runnable) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean post = this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                runnable.run();
                countDownLatch.countDown();
            }
        });
        if (post) {
            try {
                countDownLatch.await();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        return post;
    }
}
