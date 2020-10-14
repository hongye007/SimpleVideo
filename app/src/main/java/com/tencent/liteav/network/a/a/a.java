package com.tencent.liteav.network.a.a;

import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.io.*;
import java.lang.reflect.*;
import com.tencent.liteav.network.a.*;

public final class a
{
    public static InetAddress[] a() {
        try {
            final LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("getprop").getInputStream()));
            final ArrayList<InetAddress> list = new ArrayList<InetAddress>(5);
            String line;
            while ((line = lineNumberReader.readLine()) != null) {
                final int index = line.indexOf("]: [");
                if (index == -1) {
                    continue;
                }
                final String substring = line.substring(1, index);
                final String substring2 = line.substring(index + 4, line.length() - 1);
                if (!substring.endsWith(".dns") && !substring.endsWith(".dns1") && !substring.endsWith(".dns2") && !substring.endsWith(".dns3") && !substring.endsWith(".dns4")) {
                    continue;
                }
                final InetAddress byName = InetAddress.getByName(substring2);
                if (byName == null) {
                    continue;
                }
                final String hostAddress = byName.getHostAddress();
                if (hostAddress == null) {
                    continue;
                }
                if (hostAddress.length() == 0) {
                    continue;
                }
                list.add(byName);
            }
            if (list.size() > 0) {
                return list.toArray(new InetAddress[list.size()]);
            }
        }
        catch (IOException ex) {
            Logger.getLogger("AndroidDnsServer").log(Level.WARNING, "Exception in findDNSByExec", ex);
        }
        return null;
    }
    
    public static InetAddress[] b() {
        try {
            final Method method = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            final ArrayList<InetAddress> list = new ArrayList<InetAddress>(5);
            final String[] array = { "net.dns1", "net.dns2", "net.dns3", "net.dns4" };
            for (int length = array.length, i = 0; i < length; ++i) {
                final String s = (String)method.invoke(null, array[i]);
                if (s != null) {
                    if (s.length() != 0) {
                        final InetAddress byName = InetAddress.getByName(s);
                        if (byName != null) {
                            final String hostAddress = byName.getHostAddress();
                            if (hostAddress != null) {
                                if (hostAddress.length() != 0) {
                                    if (!list.contains(byName)) {
                                        list.add(byName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (list.size() > 0) {
                return list.toArray(new InetAddress[list.size()]);
            }
        }
        catch (Exception ex) {
            Logger.getLogger("AndroidDnsServer").log(Level.WARNING, "Exception in findDNSByReflection", ex);
        }
        return null;
    }
    
    public static c c() {
        return new c() {
            @Override
            public e[] a(final b b, final d d) throws IOException {
                InetAddress[] array = a.b();
                if (array == null) {
                    array = a.a();
                }
                if (array == null) {
                    throw new IOException("cant get local dns server");
                }
                return new com.tencent.liteav.network.a.a.c(array[0]).a(b, d);
            }
        };
    }
}
