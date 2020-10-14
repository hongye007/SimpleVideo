package com.tencent.liteav.txcvodplayer.a;

import java.util.*;
import android.net.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import java.security.*;
import android.util.*;
import java.io.*;

public class b
{
    private static final String a;
    private static b b;
    private ArrayList<a> c;
    private HashSet<a> d;
    private String e;
    private int f;
    private String g;
    
    public static b a() {
        return com.tencent.liteav.txcvodplayer.a.b.b;
    }
    
    public void a(final int f) {
        this.f = f;
    }
    
    public void a(final String g) {
        this.g = g;
    }
    
    public void b(String e) {
        if (e.endsWith("/")) {
            e = e.concat("txvodcache");
        }
        else {
            e = e.concat("/txvodcache");
        }
        if (this.e != null && this.e.equals(e)) {
            return;
        }
        this.e = e;
        if (this.e == null) {
            return;
        }
        new File(this.e).mkdirs();
        if (!this.b()) {
            this.e();
        }
    }
    
    public String c(String s) {
        final String s2 = "voddrm.token.";
        final int index = s.indexOf(s2);
        if (index > -1) {
            final int index2 = s.indexOf(".", index + s2.length());
            if (index2 > -1 && index2 < s.length() - 1) {
                s = s.substring(0, index) + s.substring(index2 + 1);
            }
        }
        final int index3 = s.indexOf("?");
        if (index3 > -1) {
            s = s.substring(0, index3);
        }
        return s;
    }
    
    public com.tencent.liteav.txcvodplayer.a.a d(String c) {
        if (this.e == null || c == null) {
            return null;
        }
        c = this.c(c);
        final File file = new File(this.e);
        if (!file.mkdirs() && !file.isDirectory()) {
            return null;
        }
        for (final a a : this.c) {
            if (a.url.equals(c)) {
                this.a(a);
                this.d.add(a);
                return new com.tencent.liteav.txcvodplayer.a.a(a.path, this.e, a.fileType);
            }
        }
        final Iterator<a> iterator2 = this.c.iterator();
        while (iterator2.hasNext() && this.c.size() > this.f) {
            final a a2 = iterator2.next();
            if (!this.d.contains(a2)) {
                this.b(a2);
                iterator2.remove();
            }
        }
        final a f = this.f(c);
        if (f != null) {
            this.d.add(f);
            return new com.tencent.liteav.txcvodplayer.a.a(f.path, this.e, f.fileType);
        }
        return null;
    }
    
    public boolean e(final String s) {
        final Uri parse = Uri.parse(s);
        return parse != null && parse.getPath() != null && parse.getScheme() != null && parse.getScheme().startsWith("http") && (parse.getPath().endsWith(".mp4") || parse.getPath().endsWith("m3u8") || parse.getPath().endsWith(".MP4") || parse.getPath().endsWith("M3U8"));
    }
    
    boolean b() {
        this.c = new ArrayList<a>();
        this.d = new HashSet<a>();
        final String string = this.e + "/" + "tx_cache.xml";
        try {
            for (Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(string)).getElementsByTagName("caches").item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
                final a a = new a();
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    if (node2.getNodeName().equals("path")) {
                        a.b(node2.getFirstChild().getNodeValue());
                    }
                    else if (node2.getNodeName().equals("time")) {
                        a.a(Long.parseLong(node2.getFirstChild().getNodeValue()));
                    }
                    else if (node2.getNodeName().equals("url")) {
                        a.a(node2.getFirstChild().getNodeValue());
                    }
                    else if (node2.getNodeName().equals("fileType")) {
                        a.c(node2.getFirstChild().getNodeValue());
                    }
                }
                this.c.add(a);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    void c() {
        try {
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            final Element element = document.createElement("caches");
            document.appendChild(element);
            for (final a a : this.c) {
                final Element element2 = document.createElement("cache");
                element.appendChild(element2);
                final Element element3 = document.createElement("path");
                element3.appendChild(document.createTextNode(a.b()));
                element2.appendChild(element3);
                final Element element4 = document.createElement("time");
                element4.appendChild(document.createTextNode(a.c().toString()));
                element2.appendChild(element4);
                final Element element5 = document.createElement("url");
                element5.appendChild(document.createTextNode(a.a()));
                element2.appendChild(element5);
                final Element element6 = document.createElement("fileType");
                element6.appendChild(document.createTextNode(a.d()));
                element2.appendChild(element6);
            }
            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            final DOMSource domSource = new DOMSource(document);
            final StreamResult streamResult = new StreamResult();
            streamResult.setSystemId(new File(this.e, "tx_cache.xml").getAbsolutePath());
            transformer.transform(domSource, streamResult);
            System.out.println("File saved!");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    void a(final a a) {
        a.time = System.currentTimeMillis();
        this.c.remove(a);
        this.c.add(a);
        this.c();
    }
    
    a f(final String url) {
        final a a = new a();
        a.url = url;
        a.time = System.currentTimeMillis();
        final String g = g(url);
        final Uri parse = Uri.parse(url);
        if (parse.getPath().endsWith(".mp4") || parse.getPath().endsWith(".MP4")) {
            if (this.g != null) {
                a.b(g + "." + this.g);
            }
            else {
                a.b(g + ".mp4");
            }
            a.c("mp4");
        }
        else {
            if (!parse.getPath().endsWith(".m3u8") && !parse.getPath().endsWith(".M3U8")) {
                return null;
            }
            a.b(g + ".m3u8.sqlite");
            a.c("m3u8");
        }
        this.c.add(a);
        this.c();
        return a;
    }
    
    public static String g(final String s) {
        byte[] digest;
        try {
            digest = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (UnsupportedEncodingException ex2) {
            ex2.printStackTrace();
            return null;
        }
        final StringBuilder sb = new StringBuilder(digest.length * 2);
        for (final byte b : digest) {
            if ((b & 0xFF) < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(b & 0xFF));
        }
        return sb.toString();
    }
    
    private void e() {
        if (new File(this.e).listFiles().length > 0) {
            Log.w(com.tencent.liteav.txcvodplayer.a.b.a, "!!!Warning: TXVodPlayer cache directory is not empty " + this.e + "!!!");
        }
    }
    
    private void a(final String s, final String s2) {
        new Thread(new Runnable() {
            final /* synthetic */ String a = com.tencent.liteav.txcvodplayer.a.b.this.e + "/" + s;
            
            @Override
            public void run() {
                new File(this.a).delete();
                if (s2.equals("mp4")) {
                    new File(this.a.concat(".info")).delete();
                }
                Log.w(com.tencent.liteav.txcvodplayer.a.b.a, "delete " + this.a);
            }
        }, "vodCacheMgrDelfile").start();
    }
    
    private void b(final a a) {
        this.a(a.b(), a.d());
    }
    
    public void a(final String s, final boolean b) {
        final Iterator<a> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            final a a = iterator.next();
            if (a.b().equals(s)) {
                iterator.remove();
                if (!b) {
                    continue;
                }
                this.b(a);
                this.c.remove(a);
                this.c();
            }
        }
    }
    
    static {
        a = b.class.getSimpleName();
        com.tencent.liteav.txcvodplayer.a.b.b = new b();
    }
    
    static class a implements Serializable
    {
        String url;
        String path;
        Long time;
        String fileType;
        
        public String a() {
            return this.url;
        }
        
        public void a(final String url) {
            this.url = url;
        }
        
        public String b() {
            return this.path;
        }
        
        public void b(final String path) {
            this.path = path;
        }
        
        public Long c() {
            return this.time;
        }
        
        public void a(final Long time) {
            this.time = time;
        }
        
        public void c(final String fileType) {
            this.fileType = fileType;
        }
        
        public String d() {
            if (this.fileType == null && this.path != null) {
                if (this.path.endsWith("mp4")) {
                    return "mp4";
                }
                if (this.path.endsWith("m3u8.sqlite")) {
                    return "m3u8";
                }
            }
            return this.fileType;
        }
    }
}
