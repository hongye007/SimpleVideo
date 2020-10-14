package com.tencent.rtmp.downloader;

import com.tencent.ijk.media.player.*;
import com.tencent.liteav.basic.log.*;
import java.io.*;
import com.tencent.liteav.network.*;
import com.tencent.rtmp.*;
import java.util.*;
import android.net.*;
import android.text.*;
import java.security.*;

public class TXVodDownloadManager
{
    private static final String TAG = "TXVodDownloadManager";
    public static final int DOWNLOAD_SUCCESS = 0;
    public static final int DOWNLOAD_AUTH_FAILED = -5001;
    public static final int DOWNLOAD_NO_FILE = -5003;
    public static final int DOWNLOAD_FORMAT_ERROR = -5004;
    public static final int DOWNLOAD_DISCONNECT = -5005;
    public static final int DOWNLOAD_HLS_KEY_ERROR = -5006;
    public static final int DOWNLOAD_PATH_ERROR = -5007;
    private static final int IJKDM_EVT_NET_DISCONNECT = 1001;
    private static final int IJKDM_EVT_FILE_OPEN_ERROR = 1008;
    private static final int IJKDM_EVT_HLS_KEY_ERROR = 1008;
    protected IjkDownloadCenter mDownloadCenter;
    protected String mDownloadPath;
    protected ArrayList<TXVodDownloadMediaInfo> mMediaInfoArray;
    protected ITXVodDownloadListener mListener;
    protected Map<String, String> mHeaders;
    private static TXVodDownloadManager instance;
    IjkDownloadCenter.OnDownloadListener mDownloadCenterListener;
    
    private TXVodDownloadManager() {
        this.mDownloadCenterListener = new IjkDownloadCenter.OnDownloadListener() {
            @Override
            public void downloadBegin(final IjkDownloadCenter ijkDownloadCenter, final IjkDownloadMedia ijkDownloadMedia) {
                final TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
                if (convertMedia != null) {
                    TXCLog.i("TXVodDownloadManager", "downloadBegin " + convertMedia.playPath);
                    TXVodDownloadManager.this.mListener.onDownloadStart(convertMedia);
                    if (new File(convertMedia.playPath).isFile()) {
                        TXCLog.d("TXVodDownloadManager", "file state ok");
                    }
                    else {
                        TXCLog.e("TXVodDownloadManager", "file not create!");
                    }
                }
            }
            
            @Override
            public void downloadEnd(final IjkDownloadCenter ijkDownloadCenter, final IjkDownloadMedia ijkDownloadMedia) {
                final TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
                if (convertMedia != null) {
                    TXCLog.i("TXVodDownloadManager", "downloadEnd " + convertMedia.playPath);
                    TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                    TXVodDownloadManager.this.mListener.onDownloadStop(convertMedia);
                }
            }
            
            @Override
            public void downloadFinish(final IjkDownloadCenter ijkDownloadCenter, final IjkDownloadMedia ijkDownloadMedia) {
                final TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
                if (convertMedia != null) {
                    TXCLog.i("TXVodDownloadManager", "downloadFinish " + convertMedia.playPath);
                    TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                    if (new File(convertMedia.playPath).isFile()) {
                        TXVodDownloadManager.this.mListener.onDownloadFinish(convertMedia);
                    }
                    else {
                        TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, -5003, "The file has been deleted");
                    }
                }
            }
            
            @Override
            public void downloadError(final IjkDownloadCenter ijkDownloadCenter, final IjkDownloadMedia ijkDownloadMedia, final int n, final String s) {
                final TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
                if (convertMedia != null) {
                    TXCLog.e("TXVodDownloadManager", "downloadError " + convertMedia.playPath + " " + s);
                    TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                    if (convertMedia.isStop) {
                        TXVodDownloadManager.this.mListener.onDownloadStop(convertMedia);
                    }
                    else if (n == 1008) {
                        TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, -5006, s);
                    }
                    else {
                        TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, -5005, s);
                    }
                }
            }
            
            @Override
            public void downloadProgress(final IjkDownloadCenter ijkDownloadCenter, final IjkDownloadMedia ijkDownloadMedia) {
                final TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
                if (convertMedia != null) {
                    TXVodDownloadManager.this.mListener.onDownloadProgress(convertMedia);
                }
            }
            
            @Override
            public int hlsKeyVerify(final IjkDownloadCenter ijkDownloadCenter, final IjkDownloadMedia ijkDownloadMedia, final String s, final byte[] array) {
                final TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
                if (convertMedia != null) {
                    return TXVodDownloadManager.this.mListener.hlsKeyVerify(convertMedia, s, array);
                }
                return 0;
            }
        };
        (this.mDownloadCenter = IjkDownloadCenter.getInstance()).setListener(this.mDownloadCenterListener);
        this.mMediaInfoArray = new ArrayList<TXVodDownloadMediaInfo>();
    }
    
    public static TXVodDownloadManager getInstance() {
        if (TXVodDownloadManager.instance == null) {
            TXVodDownloadManager.instance = new TXVodDownloadManager();
        }
        return TXVodDownloadManager.instance;
    }
    
    public void setDownloadPath(final String mDownloadPath) {
        if (mDownloadPath == null) {
            return;
        }
        new File(mDownloadPath).mkdirs();
        this.mDownloadPath = mDownloadPath;
    }
    
    public void setHeaders(final Map<String, String> map) {
        this.mHeaders = map;
        this.mDownloadCenter.setHeaders(map);
    }
    
    public void setListener(final ITXVodDownloadListener mListener) {
        this.mListener = mListener;
    }
    
    public TXVodDownloadMediaInfo startDownloadUrl(final String url) {
        final TXVodDownloadMediaInfo txVodDownloadMediaInfo = new TXVodDownloadMediaInfo();
        txVodDownloadMediaInfo.url = url;
        this.mMediaInfoArray.add(txVodDownloadMediaInfo);
        this.downloadMedia(txVodDownloadMediaInfo);
        return txVodDownloadMediaInfo;
    }
    
    public TXVodDownloadMediaInfo startDownload(final TXVodDownloadDataSource dataSource) {
        final TXVodDownloadMediaInfo txVodDownloadMediaInfo = new TXVodDownloadMediaInfo();
        txVodDownloadMediaInfo.dataSource = dataSource;
        if (dataSource.authBuilder != null) {
            final TXPlayerAuthBuilder authBuilder = dataSource.authBuilder;
            final f netApi = new f();
            netApi.a(authBuilder.isHttps());
            netApi.a(new g() {
                @Override
                public void onNetSuccess(final f f) {
                    if (txVodDownloadMediaInfo.isStop) {
                        TXVodDownloadManager.this.mMediaInfoArray.remove(txVodDownloadMediaInfo);
                        if (TXVodDownloadManager.this.mListener != null) {
                            TXVodDownloadManager.this.mListener.onDownloadStop(txVodDownloadMediaInfo);
                        }
                        TXCLog.w("TXVodDownloadManager", "Download task canceled");
                        return;
                    }
                    final i a = f.a();
                    j j = null;
                    if (dataSource.quality != 1000) {
                        j = TXVodDownloadManager.this.getClassificationSource(a, dataSource.quality);
                    }
                    else if (dataSource.templateName != null) {
                        j = TXVodDownloadManager.this.getTemplateSource(a, dataSource.templateName);
                    }
                    if (j == null) {
                        TXVodDownloadManager.this.mMediaInfoArray.remove(txVodDownloadMediaInfo);
                        if (TXVodDownloadManager.this.mListener != null) {
                            TXVodDownloadManager.this.mListener.onDownloadError(txVodDownloadMediaInfo, -5003, "No such resolution");
                        }
                        return;
                    }
                    txVodDownloadMediaInfo.url = j.b();
                    txVodDownloadMediaInfo.size = j.d();
                    txVodDownloadMediaInfo.duration = j.c();
                    TXVodDownloadManager.this.downloadMedia(txVodDownloadMediaInfo);
                }
                
                @Override
                public void onNetFailed(final f f, final String s, final int n) {
                    TXVodDownloadManager.this.mMediaInfoArray.remove(txVodDownloadMediaInfo);
                    if (TXVodDownloadManager.this.mListener != null) {
                        TXVodDownloadManager.this.mListener.onDownloadError(txVodDownloadMediaInfo, -5001, s);
                    }
                }
            });
            if (netApi.a(authBuilder.getAppId(), authBuilder.getFileId(), authBuilder.getTimeout(), authBuilder.getUs(), authBuilder.getExper(), authBuilder.getSign()) == 0) {
                txVodDownloadMediaInfo.netApi = netApi;
                this.mMediaInfoArray.add(txVodDownloadMediaInfo);
                return txVodDownloadMediaInfo;
            }
            TXCLog.e("TXVodDownloadManager", "unable to getPlayInfo");
        }
        return null;
    }
    
    public void stopDownload(final TXVodDownloadMediaInfo txVodDownloadMediaInfo) {
        if (txVodDownloadMediaInfo == null) {
            return;
        }
        txVodDownloadMediaInfo.isStop = true;
        if (txVodDownloadMediaInfo.tid < 0) {
            TXCLog.w("TXVodDownloadManager", "stop download not start task");
            return;
        }
        this.mDownloadCenter.stop(txVodDownloadMediaInfo.tid);
        TXCLog.d("TXVodDownloadManager", "stop download " + txVodDownloadMediaInfo.url);
    }
    
    public boolean deleteDownloadFile(final String s) {
        TXCLog.d("TXVodDownloadManager", "delete file " + s);
        for (final TXVodDownloadMediaInfo txVodDownloadMediaInfo : this.mMediaInfoArray) {
            if (txVodDownloadMediaInfo.playPath != null && txVodDownloadMediaInfo.playPath.equals(s)) {
                TXCLog.e("TXVodDownloadManager", "file is downloading, can not be delete");
                return false;
            }
        }
        new File(s).delete();
        TXCLog.e("TXVodDownloadManager", "delete success");
        return true;
    }
    
    protected void downloadMedia(final TXVodDownloadMediaInfo txVodDownloadMediaInfo) {
        String s = txVodDownloadMediaInfo.url;
        if (s == null) {
            return;
        }
        if (!Uri.parse(s).getPath().endsWith(".m3u8")) {
            TXCLog.e("TXVodDownloadManager", "format error: " + s);
            if (this.mListener != null) {
                this.mListener.onDownloadError(txVodDownloadMediaInfo, -5004, "No support format");
            }
            return;
        }
        txVodDownloadMediaInfo.playPath = this.makePlayPath(s);
        if (txVodDownloadMediaInfo.playPath == null) {
            if (this.mListener != null) {
                this.mListener.onDownloadError(txVodDownloadMediaInfo, -5007, "Failed to create local path");
            }
            return;
        }
        if (txVodDownloadMediaInfo.dataSource != null && txVodDownloadMediaInfo.dataSource.token != null) {
            final String[] split = s.split("/");
            if (split.length > 0) {
                final int lastIndex = s.lastIndexOf(split[split.length - 1]);
                s = s.substring(0, lastIndex) + "voddrm.token." + txVodDownloadMediaInfo.dataSource.token + "." + s.substring(lastIndex);
            }
        }
        TXCLog.d("TXVodDownloadManager", "download hls " + s + " to " + txVodDownloadMediaInfo.playPath);
        txVodDownloadMediaInfo.tid = this.mDownloadCenter.downloadHls(s, txVodDownloadMediaInfo.playPath);
        if (txVodDownloadMediaInfo.tid < 0) {
            TXCLog.e("TXVodDownloadManager", "start download failed");
            if (this.mListener != null) {
                this.mListener.onDownloadError(txVodDownloadMediaInfo, -5004, "Internal error");
            }
        }
    }
    
    protected String makePlayPath(final String s) {
        final String string = this.mDownloadPath + "/txdownload";
        final File file = new File(string);
        if ((!file.exists() || !file.isDirectory()) && !file.mkdir()) {
            TXCLog.e("TXVodDownloadManager", "Failed to create download path" + string);
            return null;
        }
        if (Uri.parse(s).getPath().endsWith(".m3u8")) {
            return string + "/" + md5(s) + ".m3u8.sqlite";
        }
        TXCLog.e("TXVodDownloadManager", "Unsupported format");
        return null;
    }
    
    protected static String md5(final String s) {
        if (TextUtils.isEmpty((CharSequence)s)) {
            return "";
        }
        try {
            final byte[] digest = MessageDigest.getInstance("MD5").digest(s.getBytes());
            String string = "";
            final byte[] array = digest;
            for (int length = array.length, i = 0; i < length; ++i) {
                String s2 = Integer.toHexString(array[i] & 0xFF);
                if (s2.length() == 1) {
                    s2 = "0" + s2;
                }
                string += s2;
            }
            return string;
        }
        catch (NoSuchAlgorithmException ex) {
            TXCLog.e("TXVodDownloadManager", "md5 failed.", ex);
            return "";
        }
    }
    
    j getClassificationSource(final i i, final int n) {
        j j;
        if (n == 0) {
            j = i.d();
        }
        else {
            j = i.a(TXVodDownloadDataSource.qualityToId(n), "hls");
        }
        return j;
    }
    
    j getTemplateSource(final i i, final String s) {
        return i.b(s, "hls");
    }
    
    TXVodDownloadMediaInfo convertMedia(final IjkDownloadMedia ijkDownloadMedia) {
        for (final TXVodDownloadMediaInfo txVodDownloadMediaInfo : this.mMediaInfoArray) {
            if (txVodDownloadMediaInfo.tid == ijkDownloadMedia.tid) {
                txVodDownloadMediaInfo.downloadSize = ijkDownloadMedia.downloadSize;
                if (txVodDownloadMediaInfo.size == 0) {
                    txVodDownloadMediaInfo.size = ijkDownloadMedia.size;
                }
                return txVodDownloadMediaInfo;
            }
        }
        return null;
    }
    
    static {
        TXVodDownloadManager.instance = null;
    }
}
