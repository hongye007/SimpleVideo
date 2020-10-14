package com.tencent.liteav.j;

import android.content.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class f
{
    public static String a(final Context context, final int n) {
        if (context == null) {
            return null;
        }
        final File externalFilesDir = context.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            return null;
        }
        final File file = new File(externalFilesDir + File.separator + "txrtmp");
        if (!file.exists()) {
            file.mkdirs();
        }
        final String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Long.valueOf(String.valueOf(System.currentTimeMillis() / 1000L) + "000")));
        String s = null;
        if (n == 0) {
            s = String.format("TXVideo_%s_reverse.mp4", format);
        }
        else if (n == 1) {
            s = String.format("TXVideo_%s_process.mp4", format);
        }
        return file + "/" + s;
    }
}
