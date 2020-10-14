package com.tencent.liteav.network;

import android.os.*;
import android.content.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import com.tencent.liteav.basic.d.*;
import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import org.json.*;

public class k
{
    private final int a = 5;
    private final int b = 2;
    private String c;
    private String d;
    private int e;
    private String f;
    private Handler g;
    
    public k(final Context context) {
        this.c = "";
        this.d = "";
        this.e = 0;
        this.f = "";
        if (context != null) {
            this.g = new Handler(context.getMainLooper());
        }
    }
    
    public String a() {
        return this.c;
    }
    
    public String b() {
        return this.d;
    }
    
    public int c() {
        return this.e;
    }
    
    public String d() {
        return this.f;
    }
    
    public int a(final String s, final int n, final a a) {
        this.c = "";
        this.d = "";
        this.e = 0;
        this.f = "";
        if (s == null || s.isEmpty()) {
            return -1;
        }
        final String streamIDByStreamUrl = TXCCommonUtil.getStreamIDByStreamUrl(s);
        if (streamIDByStreamUrl == null || streamIDByStreamUrl.isEmpty()) {
            return -2;
        }
        final String a2 = this.a("bizid", s);
        final String a3 = this.a("txSecret", s);
        final String a4 = this.a("txTime", s);
        if (!this.a(true, a2, a4, a3)) {
            return -3;
        }
        this.a(streamIDByStreamUrl, a2, a3, a4, n, new a() {
            @Override
            public void a(final int n, final String s, final Vector<e> vector) {
                k.this.c = streamIDByStreamUrl;
                k.this.d = a2;
                k.this.e = n;
                k.this.f = s;
                if (vector != null && !vector.isEmpty()) {
                    final Vector<e> vector2 = new Vector<e>();
                    for (final e e : vector) {
                        String s2 = e.a;
                        if (s2.indexOf("?") != -1) {
                            s2 = s2.substring(0, s2.indexOf("?"));
                        }
                        vector2.add(new e(s2 + "?txSecret=" + a3 + "&txTime=" + a4 + "&bizid=" + a2, e.b));
                    }
                    if (a != null) {
                        for (final e e2 : vector2) {
                            TXCLog.e("TXRTMPAccUrlFetcher", "accurl = " + e2.a + " quic = " + e2.b);
                        }
                        a.a(n, s, vector2);
                    }
                }
                else if (a != null) {
                    a.a(n, s, null);
                }
            }
        });
        return 0;
    }
    
    private boolean a(final boolean b, final String s, final String s2, final String s3) {
        if (b) {
            return s != null && !s.isEmpty() && s2 != null && !s2.isEmpty() && s3 != null && !s3.isEmpty();
        }
        return s != null && s2 != null && s3 != null;
    }
    
    private long e() {
        return com.tencent.liteav.basic.d.b.a().a("Network", "AccRetryCountWithoutSecret");
    }
    
    private void a(final String s, final String s2, final String s3, final String s4, final int n, final a a) {
        new Thread("getRTMPAccUrl") {
            @Override
            public void run() {
                int int1 = -1;
                String string = "";
                int intValue = 0;
                if (s2 != null && !s2.isEmpty()) {
                    intValue = Integer.valueOf(s2);
                }
                int n = 5;
                int n2 = 2;
                if (!k.this.a(true, s2, s4, s3)) {
                    n = (int)k.this.e();
                    if (n <= 0) {
                        n = 1;
                        n2 = 1;
                    }
                }
                for (int i = n + n2; i >= 1; --i) {
                    try {
                        final JSONObject jsonObject = new JSONObject();
                        jsonObject.put("bizid", intValue);
                        jsonObject.put("stream_id", (Object)s);
                        jsonObject.put("txSecret", (Object)s3);
                        jsonObject.put("txTime", (Object)s4);
                        jsonObject.put("type", 1);
                        final String string2 = jsonObject.toString();
                        String s;
                        if (i > n2) {
                            s = "https://livepull.myqcloud.com/getpulladdr";
                        }
                        else {
                            s = "https://livepullipv6.myqcloud.com/getpulladdr";
                        }
                        final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL(s).openConnection();
                        httpsURLConnection.setDoOutput(true);
                        httpsURLConnection.setDoInput(true);
                        httpsURLConnection.setUseCaches(false);
                        httpsURLConnection.setConnectTimeout(5000);
                        httpsURLConnection.setReadTimeout(5000);
                        httpsURLConnection.setRequestMethod("POST");
                        httpsURLConnection.setRequestProperty("Charsert", "UTF-8");
                        httpsURLConnection.setRequestProperty("Content-Type", "text/plain;");
                        httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(string2.length()));
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: sendHttpRequest[ " + string2 + "] retryIndex = " + i);
                        new DataOutputStream(httpsURLConnection.getOutputStream()).write(string2.getBytes());
                        String string3 = "";
                        String line;
                        while ((line = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream())).readLine()) != null) {
                            string3 += line;
                        }
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: receive response, strResponse = " + string3);
                        final JSONObject jsonObject2 = (JSONObject)new JSONTokener(string3).nextValue();
                        if (jsonObject2.has("code")) {
                            int1 = jsonObject2.getInt("code");
                        }
                        if (int1 != 0) {
                            if (jsonObject2.has("message")) {
                                string = jsonObject2.getString("message");
                            }
                            TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: errorCode = " + int1 + " errorMessage = " + string);
                        }
                        if (jsonObject2.has("pull_addr")) {
                            final Vector<e> vector = new Vector<e>();
                            final Vector<e> vector2 = new Vector<e>();
                            final JSONArray jsonArray = jsonObject2.getJSONArray("pull_addr");
                            if (jsonArray == null || jsonArray.length() == 0) {
                                TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: no pull_addr");
                            }
                            else {
                                for (int j = 0; j < jsonArray.length(); ++j) {
                                    final JSONObject jsonObject3 = (JSONObject)jsonArray.get(j);
                                    if (jsonObject3 != null) {
                                        final String string4 = jsonObject3.getString("rtmp_url");
                                        final boolean b = jsonObject3.getInt("proto") == 1;
                                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: streamUrl = " + string4 + " Q channel = " + b);
                                        final String streamIDByStreamUrl = TXCCommonUtil.getStreamIDByStreamUrl(string4);
                                        if (streamIDByStreamUrl != null && streamIDByStreamUrl.equalsIgnoreCase(s)) {
                                            if (b) {
                                                vector.add(new e(string4, b));
                                            }
                                            else {
                                                vector2.add(new e(string4, b));
                                            }
                                        }
                                    }
                                }
                            }
                            if (n == 1) {
                                if (vector2.size() > 0) {
                                    k.this.g.post((Runnable)new Runnable() {
                                        @Override
                                        public void run() {
                                            if (a != null) {
                                                a.a(0, "Success", vector2);
                                            }
                                        }
                                    });
                                    return;
                                }
                            }
                            else if (n == 2) {
                                if (vector.size() > 0) {
                                    k.this.g.post((Runnable)new Runnable() {
                                        @Override
                                        public void run() {
                                            if (a != null) {
                                                a.a(0, "Success", vector);
                                            }
                                        }
                                    });
                                    return;
                                }
                            }
                            else {
                                final Iterator<e> iterator = vector2.iterator();
                                while (iterator.hasNext()) {
                                    vector.add(iterator.next());
                                }
                                if (vector.size() > 0) {
                                    k.this.g.post((Runnable)new Runnable() {
                                        @Override
                                        public void run() {
                                            if (a != null) {
                                                a.a(0, "Success", vector);
                                            }
                                        }
                                    });
                                    return;
                                }
                            }
                        }
                    }
                    catch (Exception ex) {
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception:" + ex.toString());
                    }
                    try {
                        Thread.sleep(1000L, 0);
                    }
                    catch (Exception ex2) {
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception sleep");
                    }
                }
                k.this.g.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (a != null) {
                            a.a(int1, string, null);
                        }
                    }
                });
            }
        }.start();
    }
    
    private String a(String lowerCase, final String s) {
        if (lowerCase == null || lowerCase.length() == 0 || s == null || s.length() == 0) {
            return null;
        }
        lowerCase = lowerCase.toLowerCase();
        for (final String s2 : s.split("[?&]")) {
            if (s2.indexOf("=") != -1) {
                final String[] split2 = s2.split("[=]");
                if (split2.length == 2) {
                    final String s3 = split2[0];
                    final String s4 = split2[1];
                    if (s3 != null && s3.toLowerCase().equalsIgnoreCase(lowerCase)) {
                        return s4;
                    }
                }
            }
        }
        return "";
    }
    
    public interface a
    {
        void a(final int p0, final String p1, final Vector<e> p2);
    }
}
