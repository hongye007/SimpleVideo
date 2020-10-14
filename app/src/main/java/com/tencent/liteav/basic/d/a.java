package com.tencent.liteav.basic.d;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.spec.*;

public final class a
{
    private static String a;
    
    public static byte[] a(final byte[] array, final PrivateKey privateKey) throws Exception {
        final Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(2, privateKey);
        int n = 0;
        final int length = array.length;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (length - n > 0) {
            byte[] array2;
            if (length - n >= instance.getBlockSize()) {
                array2 = instance.doFinal(array, n, instance.getBlockSize());
            }
            else {
                array2 = instance.doFinal(array, n, length - n);
            }
            byteArrayOutputStream.write(array2);
            n += instance.getBlockSize();
        }
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }
    
    public static PrivateKey a(final byte[] array) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance(com.tencent.liteav.basic.d.a.a).generatePrivate(new PKCS8EncodedKeySpec(array));
    }
    
    static {
        com.tencent.liteav.basic.d.a.a = "RSA";
    }
}
