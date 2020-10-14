package com.tencent.liteav.network;

import android.os.*;
import com.tencent.liteav.basic.log.*;
import java.net.*;
import java.io.*;
import org.json.*;

public class f
{
    private final String c = "http://playvideo.qcloud.com/getplayinfo/v2";
    private final String d = "https://playvideo.qcloud.com/getplayinfo/v2";
    private final int e = 0;
    private final int f = 1;
    protected g a;
    protected i b;
    private Thread g;
    private boolean h;
    private Handler i;
    private String j;
    
    public f() {
        this.i = new Handler(Looper.getMainLooper()) {
            public void handleMessage(final Message message) {
                if (com.tencent.liteav.network.f.this.a == null) {
                    return;
                }
                switch (message.what) {
                    case 0: {
                        com.tencent.liteav.network.f.this.a.onNetSuccess(com.tencent.liteav.network.f.this);
                        break;
                    }
                    case 1: {
                        com.tencent.liteav.network.f.this.a.onNetFailed(com.tencent.liteav.network.f.this, (String)message.obj, message.arg1);
                        break;
                    }
                }
            }
        };
        this.j = "{\"code\":0,\"message\":\"\",\"playerInfo\":{\"playerId\":\"0\",\"name\":\"\u521d\u59cb\u64ad\u653e\u5668\",\"defaultVideoClassification\":\"SD\",\"videoClassification\":[{\"id\":\"FLU\",\"name\":\"\u6d41\u7545\",\"definitionList\":[10,510,210,610,10046,710]},{\"id\":\"SD\",\"name\":\"\u6807\u6e05\",\"definitionList\":[20,520,220,620,10047,720]},{\"id\":\"HD\",\"name\":\"\u9ad8\u6e05\",\"definitionList\":[30,530,230,630,10048,730]},{\"id\":\"FHD\",\"name\":\"\u5168\u9ad8\u6e05\",\"definitionList\":[40,540,240,640,10049,740]},{\"id\":\"2K\",\"name\":\"2K\",\"definitionList\":[70,570,270,670,370,770]},{\"id\":\"4K\",\"name\":\"4K\",\"definitionList\":[80,580,280,680,380,780]}],\"logoLocation\":\"1\",\"logoPic\":\"\",\"logoUrl\":\"\"},\"coverInfo\":{\"coverUrl\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/coverBySnapshot/1513156403_1311093072.100_0.jpg?t=5c08d9fa&us=someus&sign=95f34beb353fe32cfe7f8b5e79cc28b1\"},\"imageSpriteInfo\":{\"imageSpriteList\":[{\"definition\":10,\"height\":80,\"width\":142,\"totalCount\":4,\"imageUrls\":[\"http://1255566655.vod2.myqcloud.com/ca754badvodgzp1255566655/8f5fbff14564972818519602447/imageSprite/1513156058_533711271_00001.jpg?t=5c08d9fa&us=someus&sign=79449db4e1fb05a3becfa096613659c3\"],\"webVttUrl\":\"http://1255566655.vod2.myqcloud.com/ca754badvodgzp1255566655/8f5fbff14564972818519602447/imageSprite/1513156058_533711271.vtt?t=5c08d9fa&us=someus&sign=79449db4e1fb05a3becfa096613659c3\"}]},\"videoInfo\":{\"sourceVideo\":{\"url\":\"http://1255566655.vod2.myqcloud.com/ca754badvodgzp1255566655/8f5fbff14564972818519602447/uAnXX0OMLSAA.wmv?t=5c08d9fa&us=someus&sign=659af5dd3f27eb92dc4ed74eb561daa4\",\"definition\":0,\"duration\":30,\"floatDuration\":30.093000411987305,\"size\":26246026,\"bitrate\":6134170,\"height\":720,\"width\":1280,\"container\":\"asf\",\"md5\":\"\",\"videoStreamList\":[{\"bitrate\":5942130,\"height\":720,\"width\":1280,\"codec\":\"vc1\",\"fps\":29}],\"audioStreamList\":[{\"samplingRate\":44100,\"bitrate\":192040,\"codec\":\"wmav2\"}]},\"mas©terPlayList1\":{\"idrAligned\":false,\"url\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/master_playlist.m3u8?t=5c08d9fa&us=someus&sign=66290475b7182c89193f03b8f74a979d\",\"definition\":10000,\"md5\":\"23ecc2cfe4cb7c8f87af41993ba8c09c\"},\"transcodeList\":[{\"url\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/v.f220.m3u8?t=5c08d9fa&us=someus&sign=66290475b7182c89193f03b8f74a979d\",\"definition\":220,\"duration\":30,\"floatDuration\":30.08329963684082,\"size\":796,\"bitrate\":646036,\"height\":360,\"width\":640,\"container\":\"hls,applehttp\",\"md5\":\"dce044407826b4d809c16b6d1a9e9f13\",\"videoStreamList\":[{\"bitrate\":607449,\"height\":360,\"width\":640,\"codec\":\"h264\",\"fps\":24}],\"audioStreamList\":[{\"samplingRate\":44100,\"bitrate\":38587,\"codec\":\"aac\"}]},{\"url\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/v.f230.m3u8?t=5c08d9fa&us=someus&sign=66290475b7182c89193f03b8f74a979d\",\"definition\":230,\"duration\":30,\"floatDuration\":30.04170036315918,\"size\":802,\"bitrate\":1224776,\"height\":720,\"width\":1280,\"container\":\"hls,applehttp\",\"md5\":\"f07bb0be9e2fee967d87b6c310d3c036\",\"videoStreamList\":[{\"bitrate\":1186189,\"height\":720,\"width\":1280,\"codec\":\"h264\",\"fps\":24}],\"audioStreamList\":[{\"samplingRate\":44100,\"bitrate\":38587,\"codec\":\"aac\"}]},{\"url\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/v.f240.m3u8?t=5c08d9fa&us=someus&sign=66290475b7182c89193f03b8f74a979d\",\"definition\":240,\"duration\":30,\"floatDuration\":0,\"size\":809,\"bitrate\":2866685,\"height\":1080,\"width\":1920,\"container\":\"hls,applehttp\",\"md5\":\"ff8190cf07afceb8ed053b198453e954\",\"videoStreamList\":[{\"bitrate\":2828098,\"height\":1080,\"width\":1920,\"codec\":\"h264\",\"fps\":24}],\"audioStreamList\":[{\"samplingRate\":44100,\"bitrate\":38587,\"codec\":\"aac\"}]},{\"url\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/v.f210.m3u8?t=5c08d9fa&us=someus&sign=66290475b7182c89193f03b8f74a979d\",\"definition\":210,\"duration\":30,\"floatDuration\":30.08329963684082,\"size\":788,\"bitrate\":358908,\"height\":180,\"width\":320,\"container\":\"hls,applehttp\",\"md5\":\"5fa784e0a588c51dc2d7428ad6787079\",\"videoStreamList\":[{\"bitrate\":320321,\"height\":180,\"width\":320,\"codec\":\"h264\",\"fps\":24}],\"audioStreamList\":[{\"samplingRate\":44100,\"bitrate\":38587,\"codec\":\"aac\"}]},{\"url\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/v.f10.mp4?t=5c08d9fa&us=someus&sign=66290475b7182c89193f03b8f74a979d\",\"definition\":10,\"duration\":30,\"floatDuration\":30.139400482177734,\"size\":1169168,\"bitrate\":303916,\"height\":180,\"width\":320,\"container\":\"mov,mp4,m4a,3gp,3g2,mj2\",\"md5\":\"85002ed20125acf150d014b192cf39a0\",\"videoStreamList\":[{\"bitrate\":255698,\"height\":180,\"width\":320,\"codec\":\"h264\",\"fps\":24}],\"audioStreamList\":[{\"samplingRate\":44100,\"bitrate\":48218,\"codec\":\"aac\"}]},{\"url\":\"http://1255566655.vod2.myqcloud.com/7e9cee55vodtransgzp1255566655/8f5fbff14564972818519602447/v.f20.mp4?t=5c08d9fa&us=someus&sign=66290475b7182c89193f03b8f74a979d\",\"definition\":20,\"duration\":30,\"floatDuration\":30.139400482177734,\"size\":2158411,\"bitrate\":566647,\"height\":360,\"width\":640,\"container\":\"mov,mp4,m4a,3gp,3g2,mj2\",\"md5\":\"cba3630e5f92325041da7fee336246b6\",\"videoStreamList\":[{\"bitrate\":518429,\"height\":360,\"width\":640,\"codec\":\"h264\",\"fps\":24}],\"audioStreamList\":[{\"samplingRate\":44100,\"bitrate\":48218,\"codec\":\"aac\"}]}]}}";
    }
    
    public int a(final int n, final String s, final String s2, final String s3, final int n2, final String s4) {
        if (n == 0 || s == null) {
            return -1;
        }
        if ((s2 != null || n2 > 0) && s4 == null) {
            return -1;
        }
        (this.g = new Thread("getPlayInfo") {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    String s;
                    if (com.tencent.liteav.network.f.this.h) {
                        s = String.format("%s/%d/%s", "https://playvideo.qcloud.com/getplayinfo/v2", n, s);
                    }
                    else {
                        s = String.format("%s/%d/%s", "http://playvideo.qcloud.com/getplayinfo/v2", n, s);
                    }
                    final String a = com.tencent.liteav.network.f.this.a(s2, s3, n2, s4);
                    if (a != null) {
                        s = s + "?" + a;
                    }
                    final URL url = new URL(s);
                    TXCLog.d("TXCVodPlayerNetApi", "getplayinfo: " + s);
                    final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {
                        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        final StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line);
                        }
                        com.tencent.liteav.network.f.this.a(sb.toString());
                    }
                    else {
                        com.tencent.liteav.network.f.this.a("Request failed", -1);
                    }
                }
                catch (JSONException ex2) {
                    com.tencent.liteav.network.f.this.a("Incorrect format", -2);
                }
                catch (Exception ex) {
                    TXCLog.d("TXCVodPlayerNetApi", "http exception: " + ex.getMessage());
                    com.tencent.liteav.network.f.this.a("The request was exceptional", -2);
                }
            }
        }).start();
        return 0;
    }
    
    private String a(final String s, final String s2, final int n, final String s3) {
        final StringBuilder sb = new StringBuilder();
        if (s != null) {
            sb.append("t=" + s + "&");
        }
        if (s2 != null) {
            sb.append("us=" + s2 + "&");
        }
        if (s3 != null) {
            sb.append("sign=" + s3 + "&");
        }
        if (n >= 0) {
            sb.append("exper=" + n + "&");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    protected void a(final String obj, final int arg1) {
        final Message message = new Message();
        message.what = 1;
        message.arg1 = arg1;
        message.obj = obj;
        this.i.sendMessage(message);
    }
    
    private void a(final String s) throws JSONException {
        final JSONObject jsonObject = new JSONObject(s);
        final int int1 = jsonObject.getInt("code");
        if (int1 != 0) {
            final String string = jsonObject.getString("message");
            TXCLog.e("TXCVodPlayerNetApi", string);
            this.a(string, int1);
            return;
        }
        this.b = new i(jsonObject);
        if (this.b.a() == null) {
            this.a("No playback address", -3);
        }
        else {
            this.i.sendEmptyMessage(0);
        }
    }
    
    public void a(final g a) {
        this.a = a;
    }
    
    public i a() {
        return this.b;
    }
    
    public void a(final boolean h) {
        this.h = h;
    }
}
