package com.tencent.liteav.network;

import com.tencent.liteav.basic.log.*;
import java.util.*;
import org.json.*;

public class i
{
    protected JSONObject a;
    
    public i(final JSONObject a) {
        this.a = a;
    }
    
    public String a() {
        if (this.e() != null) {
            return this.e().a;
        }
        if (this.c().size() != 0) {
            final List<Integer> j = this.j();
            if (j != null) {
                for (final j i : this.c()) {
                    if (j.contains(i.a())) {
                        return i.a;
                    }
                }
            }
            return this.c().get(0).a;
        }
        if (this.d() != null) {
            return this.d().a;
        }
        return null;
    }
    
    public String b() {
        try {
            final JSONObject jsonObject = this.a.getJSONObject("coverInfo");
            if (jsonObject != null) {
                return jsonObject.getString("coverUrl");
            }
        }
        catch (JSONException ex) {
            TXCLog.e("TXPlayInfoResponse", "get cover url failed.", (Throwable)ex);
        }
        return null;
    }
    
    public List<j> c() {
        final ArrayList<j> list = new ArrayList<j>();
        try {
            final JSONArray jsonArray = this.a.getJSONObject("videoInfo").getJSONArray("transcodeList");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    final j j = new j();
                    j.a = jsonObject.getString("url");
                    j.e = jsonObject.getInt("duration");
                    j.c = jsonObject.getInt("width");
                    j.b = jsonObject.getInt("height");
                    j.d = Math.max(jsonObject.getInt("totalSize"), jsonObject.getInt("size"));
                    j.f = jsonObject.getInt("bitrate");
                    j.i = jsonObject.getInt("definition");
                    j.g = jsonObject.getString("container");
                    j.h = jsonObject.getString("templateName");
                    list.add(j);
                }
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public j d() {
        try {
            final JSONObject jsonObject = this.a.getJSONObject("videoInfo").getJSONObject("sourceVideo");
            final j j = new j();
            j.a = jsonObject.getString("url");
            j.e = jsonObject.getInt("duration");
            j.c = jsonObject.getInt("width");
            j.b = jsonObject.getInt("height");
            j.d = Math.max(jsonObject.getInt("size"), jsonObject.getInt("totalSize"));
            j.f = jsonObject.getInt("bitrate");
            return j;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public j e() {
        try {
            final JSONObject jsonObject = this.a.getJSONObject("videoInfo").getJSONObject("masterPlayList");
            final j j = new j();
            j.a = jsonObject.getString("url");
            return j;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public String f() {
        try {
            final JSONObject jsonObject = this.a.getJSONObject("videoInfo").getJSONObject("basicInfo");
            if (jsonObject != null) {
                return jsonObject.getString("name");
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String g() {
        try {
            final JSONObject jsonObject = this.a.getJSONObject("videoInfo").getJSONObject("basicInfo");
            if (jsonObject != null) {
                return jsonObject.getString("description");
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String h() {
        try {
            return this.a.getJSONObject("playerInfo").getString("defaultVideoClassification");
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<a> i() {
        try {
            final ArrayList<a> list = new ArrayList<a>();
            final JSONArray jsonArray = this.a.getJSONObject("playerInfo").getJSONArray("videoClassification");
            for (int i = 0; i < jsonArray.length(); ++i) {
                final a a = new a();
                a.a = jsonArray.getJSONObject(i).getString("id");
                a.b = jsonArray.getJSONObject(i).getString("name");
                a.c = new ArrayList<Integer>();
                final JSONArray jsonArray2 = jsonArray.getJSONObject(i).getJSONArray("definitionList");
                for (int j = 0; j < jsonArray2.length(); ++j) {
                    a.c.add(jsonArray2.getInt(j));
                }
                list.add(a);
            }
            return list;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<Integer> j() {
        final List<a> i = this.i();
        final String h = this.h();
        if (h != null && i != null) {
            for (final a a : i) {
                if (a.a.equals(h)) {
                    return a.c;
                }
            }
        }
        return null;
    }
    
    public j a(final String s, final String s2) {
        final List<a> i = this.i();
        List<Integer> c = null;
        if (s != null && i != null) {
            for (final a a : i) {
                if (a.a.equals(s)) {
                    c = a.c;
                    break;
                }
            }
        }
        if (c != null) {
            for (final j j : this.c()) {
                if (c.contains(j.i)) {
                    if (j.e() == null) {
                        return j;
                    }
                    if (j.e().contains(s2)) {
                        return j;
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    public j b(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        for (final j j : this.c()) {
            if (s.equals(j.f())) {
                if (j.e() == null) {
                    return j;
                }
                if (j.e().contains(s2)) {
                    return j;
                }
                continue;
            }
        }
        return null;
    }
    
    public static class a
    {
        public String a;
        public String b;
        public List<Integer> c;
    }
}
