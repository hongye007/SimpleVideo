package com.tencent.rtmp.a;

import java.util.*;
import android.graphics.*;

public interface a
{
    void setVTTUrlAndImageUrls(final String p0, final List<String> p1);
    
    Bitmap getThumbnail(final float p0);
    
    void release();
}
