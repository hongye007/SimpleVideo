package com.tencent.liteav.network;

import com.tencent.liteav.basic.log.*;
import java.io.*;
import com.tencent.liteav.basic.util.*;
import java.net.*;
import org.json.*;
import java.util.regex.*;
import java.util.*;

class c
{
    private final String c = "https://tcdns.myqcloud.com/queryip";
    private final String d = "https://tcdnsipv6.myqcloud.com/queryip";
    private final String e = "forward_stream";
    private final String f = "forward_num";
    private final String g = "request_type";
    private final String h = "sdk_version";
    private final String i = "51451748-d8f2-4629-9071-db2983aa7251";
    private final int j = 5;
    private final int k = 2;
    public b a;
    public int b;
    private Thread l;
    
    c() {
        this.a = null;
        this.b = 5;
        this.l = null;
    }
    
    public void a(final String s, final int n) {
        (this.l = new Thread("TXCPushRoute") {
            @Override
            public void run() {
                if (com.tencent.liteav.network.c.this.a == null) {
                    return;
                }
                ArrayList<a> a = new ArrayList<a>();
                for (int n = 7, i = 0; i < n; ++i) {
                    try {
                        String s;
                        if (i < 5) {
                            s = "https://tcdns.myqcloud.com/queryip";
                        }
                        else {
                            s = "https://tcdnsipv6.myqcloud.com/queryip";
                        }
                        final String a2 = com.tencent.liteav.network.c.this.a(s, n, s);
                        try {
                            final JSONObject jsonObject = new JSONObject(a2);
                            if (jsonObject.has("use") && jsonObject.getInt("use") == 0) {
                                break;
                            }
                        }
                        catch (Exception ex) {
                            TXCLog.e("TXCIntelligentRoute", "get value from json failed.", ex);
                        }
                        a = com.tencent.liteav.network.c.this.a(a2);
                        if (a != null && a.size() > 0) {
                            break;
                        }
                        Thread.sleep(1000L, 0);
                    }
                    catch (Exception ex2) {
                        TXCLog.e("TXCIntelligentRoute", "get json string failed.", ex2);
                    }
                }
                com.tencent.liteav.network.c.this.a.onFetchDone(0, a);
            }
        }).start();
    }
    
    private String a(final String s, final int n, final String s2) {
        final StringBuffer sb = new StringBuffer("");
        try {
            final InputStream b = this.b(s, n, s2);
            if (b == null) {
                return "";
            }
            String line;
            while ((line = new BufferedReader(new InputStreamReader(b)).readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException ex) {
            TXCLog.e("TXCIntelligentRoute", "get json string from url failed.", ex);
        }
        return sb.toString();
    }
    
    private InputStream b(final String s, final int n, final String s2) throws IOException {
        InputStream inputStream = null;
        final URLConnection openConnection = new URL(s2).openConnection();
        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection)openConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("forward_stream", s);
            httpURLConnection.setRequestProperty("forward_num", "2");
            httpURLConnection.setRequestProperty("sdk_version", TXCCommonUtil.getSDKVersionStr());
            if (n == 1) {
                httpURLConnection.setRequestProperty("request_type", "1");
            }
            else if (n == 2) {
                httpURLConnection.setRequestProperty("request_type", "2");
            }
            else {
                httpURLConnection.setRequestProperty("request_type", "3");
            }
            if (this.b > 0) {
                httpURLConnection.setConnectTimeout(this.b * 1000);
                httpURLConnection.setReadTimeout(this.b * 1000);
            }
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCIntelligentRoute", "http failed.", ex);
        }
        return inputStream;
    }
    
    private ArrayList<a> a(final String s) {
        ArrayList<a> list = new ArrayList<a>();
        try {
            final JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.getInt("state") != 0) {
                return null;
            }
            final JSONArray jsonArray = jsonObject.getJSONObject("content").getJSONArray("list");
            if (jsonArray == null) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); ++i) {
                final a a = this.a((JSONObject)jsonArray.opt(i));
                if (a != null && a.c) {
                    list.add(a);
                }
            }
            for (int j = 0; j < jsonArray.length(); ++j) {
                final a a2 = this.a((JSONObject)jsonArray.opt(j));
                if (a2 != null && !a2.c) {
                    list.add(a2);
                }
            }
            if (com.tencent.liteav.basic.d.b.a().a("Network", "EnableRouteOptimize") == 1L && m.a().c()) {
                list = this.a(list, true);
                this.a(list);
                return list;
            }
            final long a3 = com.tencent.liteav.basic.d.b.a().a("Network", "RouteSamplingMaxCount");
            if (a3 >= 1L) {
                final long a4 = m.a().a("51451748-d8f2-4629-9071-db2983aa7251");
                if (a4 <= a3) {
                    list = this.a(list, false);
                    m.a().a("51451748-d8f2-4629-9071-db2983aa7251", a4 + 1L);
                }
            }
            this.a(list);
            return list;
        }
        catch (JSONException ex) {
            TXCLog.e("TXCIntelligentRoute", "get records from json string failed.", (Throwable)ex);
            return list;
        }
    }
    
    private a a(final JSONObject jsonObject) {
        final a a = new a();
        try {
            a.a = jsonObject.getString("ip");
            a.b = jsonObject.getString("port");
            a.e = 0;
            a.c = false;
            a.d = this.c(a.a);
            if (jsonObject.has("type") && jsonObject.getInt("type") == 2) {
                a.c = true;
            }
        }
        catch (JSONException ex) {
            TXCLog.e("TXCIntelligentRoute", "get ip record from json object failed.", (Throwable)ex);
            return null;
        }
        return a;
    }
    
    private boolean b(final String s) {
        return s.split(":").length > 1;
    }
    
    private boolean c(final String s) {
        if (this.b(s)) {
            return false;
        }
        if (s != null) {
            final String[] split = s.split("[.]");
            for (int length = split.length, i = 0; i < length; ++i) {
                if (!this.d(split[i])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean d(final String s) {
        return Pattern.compile("[0-9]*").matcher(s).matches();
    }
    
    private ArrayList<a> a(final ArrayList<a> list, final boolean b) {
        if (list == null || list.size() == 0) {
            return null;
        }
        a a = null;
        final ArrayList<a> list2 = new ArrayList<a>();
        final ArrayList<a> list3 = new ArrayList<a>();
        for (final a a2 : list) {
            if (a2.c) {
                list2.add(a2);
            }
            else if (a2.d) {
                a = a2;
            }
            else {
                list3.add(a2);
            }
        }
        final ArrayList<a> list4 = new ArrayList<a>();
        while (list2.size() > 0 || list3.size() > 0) {
            if (b) {
                if (a != null) {
                    list4.add(a);
                }
                if (list2.size() > 0) {
                    list4.add(list2.get(0));
                    list2.remove(0);
                }
            }
            else {
                if (list2.size() > 0) {
                    list4.add(list2.get(0));
                    list2.remove(0);
                }
                if (a != null) {
                    list4.add(a);
                }
            }
            if (list3.size() > 0) {
                list4.add(list3.get(0));
                list3.remove(0);
            }
        }
        final int size = list4.size();
        if (size > 0) {
            final a a3 = list4.get(size - 1);
            if (a3 != null && !this.c(a3.a) && a != null) {
                list4.add(a);
            }
        }
        return list4;
    }
    
    private void a(final ArrayList<a> list) {
        if (list != null && list.size() > 0) {
            String string = "";
            for (final a a : list) {
                string = string + " \n Nearest IP: " + a.a + " Port: " + a.b + " Q Channel: " + a.c;
            }
            TXCLog.e("TXCIntelligentRoute", string);
        }
    }
}
