package com.tencent.liteav.g;

import java.util.*;
import com.tencent.liteav.basic.log.*;

public class j
{
    private List<i> a;
    private int b;
    private int c;
    
    public j() {
        this.b = 0;
        this.c = 0;
        this.a = new ArrayList<i>();
    }
    
    public List<i> a() {
        TXCLog.i("VideoExtractListConfig", "getAllVideoExtractConfig mVideoExtractConfigList:" + this.a);
        return this.a;
    }
    
    public i b() {
        TXCLog.i("VideoExtractListConfig", "getCurrentVideoExtractConfig mCurrentVideoIndex:" + this.b);
        return this.a.get(this.b);
    }
    
    public i c() {
        TXCLog.i("VideoExtractListConfig", "getCurrentAudioExtractConfig mCurrentAudioIndex:" + this.c);
        return this.a.get(this.c);
    }
    
    public boolean d() {
        ++this.b;
        TXCLog.i("VideoExtractListConfig", "nextVideo mCurrentVideoIndex:" + this.b);
        if (this.b >= this.a.size()) {
            TXCLog.i("VideoExtractListConfig", "nextVideo get fail");
            return false;
        }
        TXCLog.i("VideoExtractListConfig", "nextVideo get succ");
        return true;
    }
    
    public boolean e() {
        ++this.c;
        TXCLog.i("VideoExtractListConfig", "nextAudio mCurrentAudioIndex:" + this.c);
        if (this.c >= this.a.size()) {
            TXCLog.i("VideoExtractListConfig", "nextAudio get fail");
            return false;
        }
        TXCLog.i("VideoExtractListConfig", "nextAudio get succ");
        return true;
    }
    
    public boolean f() {
        return this.b == this.a.size() - 1;
    }
    
    public boolean g() {
        return this.c == this.a.size() - 1;
    }
    
    public void h() {
        this.b = 0;
        this.c = 0;
    }
    
    public void a(final List<i> list) {
        for (int i = 0; i < list.size(); ++i) {
            final i j = list.get(i);
            final i k = new i();
            k.a(k.a = j.a);
            k.b();
            this.a.add(k);
        }
    }
}
