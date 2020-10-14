package com.tencent.liteav.network;

import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.d.*;
import org.json.*;
import android.content.*;

public class m
{
    protected static m a;
    private Context b;
    private long c;
    
    public static m a() {
        return m.a;
    }
    
    protected m() {
        this.b = null;
        this.c = 3L;
    }
    
    public void a(final Context context) {
        if (this.b == null) {
            this.b = context.getApplicationContext();
        }
    }
    
    public String b() {
        try {
            if (this.b != null) {
                final int e = f.e(this.b);
                if (e == 0) {
                    return "";
                }
                if (e == 1) {
                    return "wifi:";
                }
                if (e == 2) {
                    return "4g:";
                }
                if (e == 3) {
                    return "3g:";
                }
                if (e == 4) {
                    return "2g:";
                }
                if (e == 5) {
                    return "ethernet:";
                }
                return "xg:";
            }
        }
        catch (Exception ex) {
            TXCLog.e("UploadQualityData", "get network type failed." + ex.getMessage());
        }
        return "";
    }
    
    public void a(final String s, final long n, final long n2, final long n3, final float n4, final float n5, final float n6) {
        if (com.tencent.liteav.basic.d.b.a().a("Network", "QualityDataCacheCount") <= 0L) {
            return;
        }
        TXCLog.e("UploadQualityData", String.format("updateQualityData: accessID = %s serverType = %d totalTime = %d networkRTT = %d avgBlockCnt = %f avgVideoQue = %f avgAudioQue = %f", s, n, n2, n3, n4, n5, n6));
        if (this.b(s)) {
            return;
        }
        try {
            final SharedPreferences sharedPreferences = this.b.getSharedPreferences("com.tencent.liteav.network", 0);
            final JSONObject c = this.c(sharedPreferences.getString("34238512-C08C-4931-A000-40E1D8B5BA5B", ""));
            JSONObject optJSONObject = c.optJSONObject(s);
            if (optJSONObject == null) {
                optJSONObject = new JSONObject();
            }
            final String s2 = (n == 3L) ? "DomainArrayData" : "OriginArrayData";
            JSONArray optJSONArray = optJSONObject.optJSONArray(s2);
            if (optJSONArray == null) {
                optJSONArray = new JSONArray();
            }
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalTime", n2);
            jsonObject.put("networkRTT", n3);
            jsonObject.put("avgBlockCnt", (double)n4);
            jsonObject.put("avgVideoQue", (double)n5);
            jsonObject.put("avgAudioQue", (double)n6);
            optJSONArray.put((Object)jsonObject);
            final int length = optJSONArray.length();
            if (length > this.c) {
                final JSONArray jsonArray = new JSONArray();
                for (int i = (int)(length - this.c); i < length; ++i) {
                    jsonArray.put(optJSONArray.get(i));
                }
                optJSONArray = jsonArray;
            }
            optJSONObject.put(s2, (Object)optJSONArray);
            c.put(s, (Object)optJSONObject);
            sharedPreferences.edit().putString("34238512-C08C-4931-A000-40E1D8B5BA5B", c.toString()).commit();
        }
        catch (Exception ex) {
            TXCLog.e("UploadQualityData", "build json object failed.", ex);
        }
    }
    
    public boolean c() {
        this.d();
        final String b = this.b();
        String s = "isDomainAddressBetter: accessID = " + b + " minQualityDataCount = " + this.c;
        final a a = this.a(b, true);
        final a a2 = this.a(b, false);
        if (a != null) {
            s = String.format("%s \n isDomainAddressBetter\uff1adomainQualityData count = %d avgNetworkRTT = %f avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", s, a.e, a.a, a.b, a.c, a.d);
        }
        if (a2 != null) {
            s = String.format("%s \n isDomainAddressBetter\uff1aoriginQualityData count = %d avgNetworkRTT = %f avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", s, a2.e, a2.a, a2.b, a2.c, a2.d);
        }
        TXCLog.e("UploadQualityData", s);
        return a != null && a.e >= this.c && a2 != null && a2.e >= this.c && (a.b < a2.b && a.c < a2.c && a.d < a2.d);
    }
    
    private a a(final String s, final boolean b) {
        if (this.b(s)) {
            return null;
        }
        try {
            final String string = this.b.getSharedPreferences("com.tencent.liteav.network", 0).getString("34238512-C08C-4931-A000-40E1D8B5BA5B", "");
            if (this.b(string)) {
                return null;
            }
            final JSONObject jsonObject = new JSONObject(string);
            if (jsonObject == null) {
                return null;
            }
            final JSONObject optJSONObject = jsonObject.optJSONObject(s);
            if (optJSONObject == null) {
                return null;
            }
            final JSONArray optJSONArray = optJSONObject.optJSONArray(b ? "DomainArrayData" : "OriginArrayData");
            if (optJSONArray == null) {
                return null;
            }
            final long e = optJSONArray.length();
            if (e == 0L) {
                return null;
            }
            String format = "";
            float n = 0.0f;
            float n2 = 0.0f;
            float n3 = 0.0f;
            float n4 = 0.0f;
            for (int n5 = 0; n5 < e; ++n5) {
                final JSONObject jsonObject2 = optJSONArray.getJSONObject(n5);
                n += jsonObject2.optLong("networkRTT");
                n2 += (float)jsonObject2.optDouble("avgBlockCnt");
                n3 += (float)jsonObject2.optDouble("avgVideoQue");
                n4 += (float)jsonObject2.optDouble("avgAudioQue");
                format = String.format("%s \n isDomainAddressBetter\uff1aitemData domain = %b NetworkRTT = %d avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", format, b, jsonObject2.optLong("networkRTT"), jsonObject2.optDouble("avgBlockCnt"), jsonObject2.optDouble("avgVideoQue"), jsonObject2.optDouble("avgAudioQue"));
            }
            final float a = n / e;
            final float b2 = n2 / e;
            final float c = n3 / e;
            final float d = n4 / e;
            final a a2 = new a();
            a2.a = a;
            a2.b = b2;
            a2.c = c;
            a2.d = d;
            a2.e = e;
            return a2;
        }
        catch (Exception ex) {
            TXCLog.e("UploadQualityData", "get quality data failed.", ex);
            return null;
        }
    }
    
    private boolean b(final String s) {
        return s == null || s.length() == 0;
    }
    
    private JSONObject c(final String s) {
        if (!this.b(s)) {
            try {
                return new JSONObject(s);
            }
            catch (Exception ex) {
                TXCLog.e("UploadQualityData", "failed to parse json string", ex);
            }
        }
        return new JSONObject();
    }
    
    private void d() {
        this.c = com.tencent.liteav.basic.d.b.a().a("Network", "QualityDataCacheCount");
        if (this.c == -1L || this.c < 3L) {
            this.c = 3L;
        }
    }
    
    public long a(final String s) {
        if (this.b != null) {
            return this.b.getSharedPreferences("com.tencent.liteav.network", 0).getLong(s, 0L);
        }
        return 0L;
    }
    
    public void a(final String s, final long n) {
        if (this.b != null) {
            this.b.getSharedPreferences("com.tencent.liteav.network", 0).edit().putLong(s, n).commit();
        }
    }
    
    static {
        m.a = new m();
    }
    
    protected class a
    {
        public float a;
        public float b;
        public float c;
        public float d;
        public long e;
        
        protected a() {
            this.a = 0.0f;
            this.b = 0.0f;
            this.c = 0.0f;
            this.d = 0.0f;
            this.e = 0L;
        }
    }
}
