package com.tencent.liteav.basic.util;

import com.tencent.liteav.basic.log.*;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.*;
import java.lang.ref.*;
import android.os.*;

public class TXHttpRequest
{
    private static final String TAG = "TXHttpRequest";
    private static final int CON_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private long mNativeHttps;
    
    public TXHttpRequest(final long mNativeHttps) {
        this.mNativeHttps = 0L;
        this.mNativeHttps = mNativeHttps;
    }
    
    public static byte[] getHttpPostRsp(final Map<String, String> map, final String s, final byte[] array) throws Exception {
        TXCLog.i("TXHttpRequest", "getHttpPostRsp->request: " + s);
        TXCLog.i("TXHttpRequest", "getHttpPostRsp->data size: " + array.length);
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s.replace(" ", "%20")).openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        httpURLConnection.setRequestMethod("POST");
        if (map != null) {
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.write(array);
        dataOutputStream.flush();
        dataOutputStream.close();
        final int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            final InputStream inputStream = httpURLConnection.getInputStream();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final byte[] array2 = new byte[1024];
            int read;
            while ((read = inputStream.read(array, 0, array.length)) != -1) {
                byteArrayOutputStream.write(array, 0, read);
            }
            byteArrayOutputStream.flush();
            inputStream.close();
            httpURLConnection.disconnect();
            TXCLog.i("TXHttpRequest", "getHttpsPostRsp->rsp size: " + byteArrayOutputStream.size());
            return byteArrayOutputStream.toByteArray();
        }
        TXCLog.i("TXHttpRequest", "getHttpPostRsp->response code: " + responseCode);
        throw new Exception("response: " + responseCode);
    }
    
    public int sendHttpsRequest(final Map<String, String> map, final String s, final byte[] array) {
        TXCLog.i("TXHttpRequest", "sendHttpsRequest->enter action: " + s + ", data size: " + array.length);
        this.asyncPostRequest(map, s.getBytes(), array);
        return 0;
    }
    
    public int sendHttpsRequest(final String s, final byte[] array) {
        TXCLog.i("TXHttpRequest", "sendHttpsRequest->enter action: " + s + ", data size: " + array.length);
        this.asyncPostRequest(null, s.getBytes(), array);
        return 0;
    }
    
    public static byte[] getHttpsPostRsp(final Map<String, String> map, final String s, final byte[] array) throws Exception {
        TXCLog.i("TXHttpRequest", "getHttpsPostRsp->request: " + s);
        TXCLog.i("TXHttpRequest", "getHttpsPostRsp->data: " + array.length);
        final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL(s.replace(" ", "%20")).openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setConnectTimeout(5000);
        httpsURLConnection.setReadTimeout(5000);
        httpsURLConnection.setRequestMethod("POST");
        if (map != null) {
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                httpsURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        final DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
        dataOutputStream.write(array);
        dataOutputStream.flush();
        dataOutputStream.close();
        final int responseCode = httpsURLConnection.getResponseCode();
        if (responseCode == 200) {
            final InputStream inputStream = httpsURLConnection.getInputStream();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final byte[] array2 = new byte[1024];
            int read;
            while ((read = inputStream.read(array, 0, array.length)) != -1) {
                byteArrayOutputStream.write(array, 0, read);
            }
            byteArrayOutputStream.flush();
            inputStream.close();
            httpsURLConnection.disconnect();
            TXCLog.i("TXHttpRequest", "getHttpsPostRsp->rsp size: " + byteArrayOutputStream.size());
            return byteArrayOutputStream.toByteArray();
        }
        TXCLog.i("TXHttpRequest", "getHttpsPostRsp->response code: " + responseCode);
        throw new Exception("response: " + responseCode);
    }
    
    public void asyncPostRequest(final Map<String, String> map, final byte[] array, final byte[] array2) {
        new a(this, map).execute((Object[])new byte[][] { array, array2 });
    }
    
    private native void nativeOnRecvMessage(final long p0, final int p1, final byte[] p2);
    
    static class b
    {
        public int a;
        public String b;
        public byte[] c;
        
        b() {
            this.a = 1;
            this.b = "";
            this.c = "".getBytes();
        }
    }
    
    static class a extends AsyncTask<byte[], Void, b>
    {
        private WeakReference<TXHttpRequest> a;
        private Handler b;
        private Map<String, String> c;
        
        public a(final TXHttpRequest txHttpRequest, final Map<String, String> c) {
            this.b = null;
            this.c = c;
            this.a = new WeakReference<TXHttpRequest>(txHttpRequest);
            final Looper myLooper = Looper.myLooper();
            if (myLooper != null) {
                this.b = new Handler(myLooper);
            }
            else {
                this.b = null;
            }
        }
        
        protected b a(final byte[]... array) {
            final b b = new b();
            try {
                if (new String(array[0]).startsWith("https")) {
                    b.c = TXHttpRequest.getHttpsPostRsp(this.c, new String(array[0]), array[1]);
                }
                else {
                    b.c = TXHttpRequest.getHttpPostRsp(this.c, new String(array[0]), array[1]);
                }
                b.a = 0;
            }
            catch (Exception ex) {
                b.b = ex.toString();
                b.a = 1;
            }
            TXCLog.i("TXHttpRequest", "TXPostRequest->result: " + b.a + "|" + b.b);
            return b;
        }
        
        protected void a(final b b) {
            super.onPostExecute((Object)b);
            final TXHttpRequest txHttpRequest = this.a.get();
            if (txHttpRequest != null && txHttpRequest.mNativeHttps != 0L) {
                if (this.b != null) {
                    this.b.post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            TXCLog.i("TXHttpRequest", "TXPostRequest->recvMsg: " + b.a + "|" + b.b);
                            txHttpRequest.nativeOnRecvMessage(txHttpRequest.mNativeHttps, b.a, b.c);
                        }
                    });
                }
                else {
                    TXCLog.i("TXHttpRequest", "TXPostRequest->recvMsg: " + b.a + "|" + b.b);
                    txHttpRequest.nativeOnRecvMessage(txHttpRequest.mNativeHttps, b.a, b.c);
                }
            }
        }
    }
}
