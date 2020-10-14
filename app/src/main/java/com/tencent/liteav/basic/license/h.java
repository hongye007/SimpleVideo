package com.tencent.liteav.basic.license;

import java.security.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class h
{
    public static final byte[] a;
    
    public static byte[] a(final byte[] array, final byte[] array2) throws Exception {
        final PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(array2));
        final Cipher instance = Cipher.getInstance("RSA/None/PKCS1Padding");
        instance.init(2, generatePrivate);
        return instance.doFinal(array);
    }
    
    public static byte[] b(final byte[] array, final byte[] array2) throws Exception {
        final int length = h.a.length;
        if (length <= 0) {
            return a(array, array2);
        }
        final int length2 = array.length;
        final ArrayList<Byte> list = new ArrayList<Byte>(1024);
        int n = 0;
        for (int i = 0; i < length2; ++i) {
            final byte b = array[i];
            boolean b2 = false;
            if (i == length2 - 1) {
                final byte[] array3 = new byte[length2 - n];
                System.arraycopy(array, n, array3, 0, array3.length);
                final byte[] a = a(array3, array2);
                for (int length3 = a.length, j = 0; j < length3; ++j) {
                    list.add(a[j]);
                }
                n = i + length;
                i = n - 1;
            }
            else if (b == h.a[0]) {
                if (length > 1) {
                    if (i + length < length2) {
                        for (int n2 = 1; n2 < length && h.a[n2] == array[i + n2]; ++n2) {
                            if (n2 == length - 1) {
                                b2 = true;
                            }
                        }
                    }
                }
                else {
                    b2 = true;
                }
            }
            if (b2) {
                final byte[] array4 = new byte[i - n];
                System.arraycopy(array, n, array4, 0, array4.length);
                final byte[] a2 = a(array4, array2);
                for (int length4 = a2.length, k = 0; k < length4; ++k) {
                    list.add(a2[k]);
                }
                n = i + length;
                i = n - 1;
            }
        }
        final byte[] array5 = new byte[list.size()];
        int n3 = 0;
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            array5[n3++] = iterator.next();
        }
        return array5;
    }
    
    static {
        a = "#PART#".getBytes();
    }
}
