package com.tencent.liteav.network.a.a;

import com.tencent.liteav.network.a.b.*;
import com.tencent.liteav.network.a.*;
import java.io.*;
import java.util.*;
import java.net.*;

public final class b
{
    public static byte[] a(final String s, final int n) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        final a a = new a();
        a.a(8);
        try {
            dataOutputStream.writeShort((short)n);
            dataOutputStream.writeShort((short)a.a());
            dataOutputStream.writeShort(1);
            dataOutputStream.writeShort(0);
            dataOutputStream.writeShort(0);
            dataOutputStream.writeShort(0);
            dataOutputStream.flush();
            b(byteArrayOutputStream, s);
        }
        catch (IOException ex) {
            throw new AssertionError((Object)ex);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    private static void a(final OutputStream outputStream, final String s) throws IOException {
        final String[] split = s.split("[.\u3002\uff0e\uff61]");
        for (int length = split.length, i = 0; i < length; ++i) {
            final byte[] bytes = IDN.toASCII(split[i]).getBytes();
            outputStream.write(bytes.length);
            outputStream.write(bytes, 0, bytes.length);
        }
        outputStream.write(0);
    }
    
    private static void b(final OutputStream outputStream, final String s) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        a(outputStream, s);
        dataOutputStream.writeShort(1);
        dataOutputStream.writeShort(1);
    }
    
    public static e[] a(final byte[] array, final int n, final String s) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(array));
        final int unsignedShort = dataInputStream.readUnsignedShort();
        if (unsignedShort != n) {
            throw new com.tencent.liteav.network.a.a(s, "the answer id " + unsignedShort + " is not match " + n);
        }
        final int unsignedShort2 = dataInputStream.readUnsignedShort();
        final boolean b = (unsignedShort2 >> 8 & 0x1) == 0x1;
        if ((unsignedShort2 >> 7 & 0x1) != 0x1 || !b) {
            throw new com.tencent.liteav.network.a.a(s, "the dns server cant support recursion ");
        }
        final int unsignedShort3 = dataInputStream.readUnsignedShort();
        final int unsignedShort4 = dataInputStream.readUnsignedShort();
        dataInputStream.readUnsignedShort();
        dataInputStream.readUnsignedShort();
        a(dataInputStream, array, unsignedShort3);
        return b(dataInputStream, array, unsignedShort4);
    }
    
    private static String a(final DataInputStream dataInputStream, final byte[] array) throws IOException {
        final int unsignedByte = dataInputStream.readUnsignedByte();
        if ((unsignedByte & 0xC0) == 0xC0) {
            final int n = ((unsignedByte & 0x3F) << 8) + dataInputStream.readUnsignedByte();
            final HashSet<Integer> set = new HashSet<Integer>();
            set.add(n);
            return a(array, n, set);
        }
        if (unsignedByte == 0) {
            return "";
        }
        final byte[] array2 = new byte[unsignedByte];
        dataInputStream.readFully(array2);
        String s = IDN.toUnicode(new String(array2));
        final String a = a(dataInputStream, array);
        if (a.length() > 0) {
            s = s + "." + a;
        }
        return s;
    }
    
    private static String a(final byte[] array, final int n, final HashSet<Integer> set) throws IOException {
        final int n2 = array[n] & 0xFF;
        if ((n2 & 0xC0) == 0xC0) {
            final int n3 = ((n2 & 0x3F) << 8) + (array[n + 1] & 0xFF);
            if (set.contains(n3)) {
                throw new com.tencent.liteav.network.a.a("", "Cyclic offsets detected.");
            }
            set.add(n3);
            return a(array, n3, set);
        }
        else {
            if (n2 == 0) {
                return "";
            }
            String string = new String(array, n + 1, n2);
            final String a = a(array, n + 1 + n2, set);
            if (a.length() > 0) {
                string = string + "." + a;
            }
            return string;
        }
    }
    
    private static void a(final DataInputStream dataInputStream, final byte[] array, int n) throws IOException {
        while (n-- > 0) {
            a(dataInputStream, array);
            dataInputStream.readUnsignedShort();
            dataInputStream.readUnsignedShort();
        }
    }
    
    private static e[] b(final DataInputStream dataInputStream, final byte[] array, int n) throws IOException {
        int n2 = 0;
        final e[] array2 = new e[n];
        while (n-- > 0) {
            array2[n2++] = b(dataInputStream, array);
        }
        return array2;
    }
    
    private static e b(final DataInputStream dataInputStream, final byte[] array) throws IOException {
        a(dataInputStream, array);
        final int unsignedShort = dataInputStream.readUnsignedShort();
        dataInputStream.readUnsignedShort();
        final long n = ((long)dataInputStream.readUnsignedShort() << 16) + dataInputStream.readUnsignedShort();
        final int unsignedShort2 = dataInputStream.readUnsignedShort();
        String s = null;
        switch (unsignedShort) {
            case 1: {
                final byte[] array2 = new byte[4];
                dataInputStream.readFully(array2);
                s = InetAddress.getByAddress(array2).getHostAddress();
                break;
            }
            case 5: {
                s = a(dataInputStream, array);
                break;
            }
            default: {
                s = null;
                for (int i = 0; i < unsignedShort2; ++i) {
                    dataInputStream.readByte();
                }
                break;
            }
        }
        if (s == null) {
            throw new UnknownHostException("no record");
        }
        return new e(s, unsignedShort, (int)n, System.currentTimeMillis() / 1000L);
    }
}
