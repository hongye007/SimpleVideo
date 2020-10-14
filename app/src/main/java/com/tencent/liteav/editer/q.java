package com.tencent.liteav.editer;

import com.tencent.liteav.basic.log.*;
import java.io.*;
import com.tencent.liteav.videoediter.ffmpeg.jni.*;
import java.util.*;

public class q
{
    private static q a;
    private final i b;
    
    public static q a() {
        if (q.a == null) {
            q.a = new q();
        }
        return q.a;
    }
    
    private q() {
        this.b = i.a();
    }
    
    public void b() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.b.b()) {
            return;
        }
        if (!this.b.c()) {
            return;
        }
        final File file = new File(i.a().i);
        final File file2 = new File(file.getParentFile(), "moov_tmp.mp4");
        if (file2.exists()) {
            file2.delete();
        }
        try {
            file2.createNewFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            TXCLog.e("MoovHeaderProcessor", "moov: create moov tmp file error!");
            return;
        }
        final TXFFQuickJointerJNI txffQuickJointerJNI = new TXFFQuickJointerJNI();
        txffQuickJointerJNI.setDstPath(file2.getAbsolutePath());
        final ArrayList<String> srcPaths = new ArrayList<String>();
        srcPaths.add(file.getAbsolutePath());
        txffQuickJointerJNI.setSrcPaths(srcPaths);
        final boolean b = txffQuickJointerJNI.start() == 0;
        txffQuickJointerJNI.stop();
        txffQuickJointerJNI.destroy();
        if (!b) {
            TXCLog.e("MoovHeaderProcessor", "moov: change to moov type video file error!!");
            return;
        }
        if (!file.delete()) {
            TXCLog.e("MoovHeaderProcessor", "moov: delete original file error!");
            return;
        }
        TXCLog.i("MoovHeaderProcessor", "moov: rename file success = " + file2.renameTo(file));
        TXCLog.d("MoovHeaderProcessor", "doProcessMoovHeader cost time:" + String.valueOf(System.currentTimeMillis() - currentTimeMillis));
    }
}
