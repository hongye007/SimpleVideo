package com.tencent.liteav.editer;

import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import android.net.*;
import java.io.*;
import android.content.*;
import android.os.*;
import android.media.*;

public class p
{
    public static MediaExtractor a(final String dataSource) {
        TXCLog.i("MediaModuleHelper", "generateMediaExtractor -> path = " + dataSource);
        if (Build.VERSION.SDK_INT < 16) {
            TXCLog.e("MediaModuleHelper", "generateMediaExtractor -> un support android version  = " + Build.VERSION.SDK_INT);
            return null;
        }
        final Context appContext = TXCCommonUtil.getAppContext();
        final MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            if (c(dataSource)) {
                TXCLog.i("MediaModuleHelper", "generateMediaExtractor -> set uri data source");
                final ParcelFileDescriptor openFileDescriptor = appContext.getContentResolver().openFileDescriptor(Uri.parse(dataSource), "r");
                mediaExtractor.setDataSource(openFileDescriptor.getFileDescriptor());
                openFileDescriptor.close();
            }
            else {
                TXCLog.i("MediaModuleHelper", "generateMediaExtractor -> set path data source");
                final File file = new File(dataSource);
                final boolean b = file.exists() && file.canRead();
                TXCLog.i("MediaModuleHelper", "generateMediaExtractor -> path is valid = " + b);
                if (!b) {
                    throw new IllegalArgumentException("path is invalid.");
                }
                mediaExtractor.setDataSource(dataSource);
            }
        }
        catch (Exception ex) {
            TXCLog.i("MediaModuleHelper", "generateMediaExtractor -> exception happen ");
            ex.printStackTrace();
            try {
                mediaExtractor.release();
            }
            catch (Exception ex2) {}
            return null;
        }
        return mediaExtractor;
    }
    
    public static MediaMetadataRetriever b(final String dataSource) {
        TXCLog.i("MediaModuleHelper", "generateMediaMetadataRetriever -> path = " + dataSource);
        if (Build.VERSION.SDK_INT < 16) {
            TXCLog.e("MediaModuleHelper", "generateMediaMetadataRetriever -> un support android version  = " + Build.VERSION.SDK_INT);
            return null;
        }
        final Context appContext = TXCCommonUtil.getAppContext();
        final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            if (c(dataSource)) {
                TXCLog.i("MediaModuleHelper", "generateMediaMetadataRetriever -> set uri data source");
                mediaMetadataRetriever.setDataSource(appContext, Uri.parse(dataSource));
            }
            else {
                TXCLog.i("MediaModuleHelper", "generateMediaMetadataRetriever -> set path data source");
                final File file = new File(dataSource);
                final boolean b = file.exists() && file.canRead();
                TXCLog.i("MediaModuleHelper", "generateMediaMetadataRetriever -> path is valid = " + b);
                if (!b) {
                    throw new IllegalArgumentException("path is invalid.");
                }
                mediaMetadataRetriever.setDataSource(dataSource);
            }
        }
        catch (Exception ex) {
            TXCLog.i("MediaModuleHelper", "generateMediaMetadataRetriever -> exception happen ");
            ex.printStackTrace();
            try {
                mediaMetadataRetriever.release();
            }
            catch (Exception ex2) {}
            return null;
        }
        return mediaMetadataRetriever;
    }
    
    public static boolean c(final String s) {
        return s.startsWith("content://");
    }
}
