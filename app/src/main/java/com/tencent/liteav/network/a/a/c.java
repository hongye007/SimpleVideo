package com.tencent.liteav.network.a.a;

import java.util.*;
import com.tencent.liteav.network.a.*;
import java.io.*;
import java.net.*;

public final class c implements com.tencent.liteav.network.a.c
{
    private static final Random b;
    final InetAddress a;
    private final int c;
    
    public c(final InetAddress inetAddress) {
        this(inetAddress, 10);
    }
    
    public c(final InetAddress a, final int c) {
        this.a = a;
        this.c = c;
    }
    
    @Override
    public e[] a(final b b, final d d) throws IOException {
        final int n;
        synchronized (com.tencent.liteav.network.a.a.c.b) {
            n = (com.tencent.liteav.network.a.a.c.b.nextInt() & 0xFF);
        }
        final byte[] a = this.a(b.a(b.a, n));
        if (a == null) {
            throw new a(b.a, "cant get answer");
        }
        return b.a(a, n, b.a);
    }
    
    private byte[] a(final byte[] array) throws IOException {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            final DatagramPacket datagramPacket = new DatagramPacket(array, array.length, this.a, 53);
            datagramSocket.setSoTimeout(this.c * 1000);
            datagramSocket.send(datagramPacket);
            final DatagramPacket datagramPacket2 = new DatagramPacket(new byte[1500], 1500);
            datagramSocket.receive(datagramPacket2);
            return datagramPacket2.getData();
        }
        finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
    
    static {
        b = new Random();
    }
}
