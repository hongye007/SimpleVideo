package com.tencent.liteav.basic.d;

import com.tencent.liteav.basic.util.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;
import java.net.*;
import android.util.*;
import java.util.zip.*;
import java.util.*;
import java.io.*;
import org.json.*;
import android.content.*;

public class b
{
    protected String a;
    private static Context g;
    SharedPreferences b;
    SharedPreferences.Editor c;
    String d;
    long e;
    private boolean h;
    private boolean i;
    protected static b f;
    private a j;
    
    public b() {
        this.d = "";
        this.e = 0L;
        this.h = false;
        this.i = false;
        this.j = new a();
    }
    
    public static b a() {
        if (b.f == null) {
            synchronized (b.class) {
                if (b.f == null) {
                    b.f = new b();
                }
            }
        }
        return b.f;
    }
    
    public void a(final Context context) {
        if (com.tencent.liteav.basic.d.b.g == null) {
            com.tencent.liteav.basic.d.b.g = context.getApplicationContext();
            final File externalFilesDir = com.tencent.liteav.basic.d.b.g.getExternalFilesDir((String)null);
            if (externalFilesDir != null) {
                this.a = externalFilesDir.getAbsolutePath() + "/txrtmp/";
            }
        }
    }
    
    public String b() {
        this.i();
        if (this.j.b.isEmpty()) {
            this.a("{\n        \"InfoList\": [\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 90,\n            \"MachineType\": \"MI 4W\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 94,\n            \"MachineType\": \"MI 5\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndsp {\\r\\npost {\\r\\npostAGCmoddB -6.0\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 96,\n            \"MachineType\": \"PLK-AL10\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 97,\n            \"MachineType\": \"SM-G9350\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 98,\n            \"MachineType\": \"MX4 Pro\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 99,\n            \"MachineType\": \"A31\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 118,\n            \"MachineType\": \"MI 4LTE\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 132,\n            \"MachineType\": \"OPPO R9tm\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 156,\n            \"MachineType\": \"Mi-4c\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 166,\n            \"MachineType\": \"OPPO A33\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 174,\n            \"MachineType\": \"OPPO A33m\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 191,\n            \"MachineType\": \"m1\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 213,\n            \"MachineType\": \"vivo X6Plus D\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 226,\n            \"MachineType\": \"vivo X5Pro V\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 251,\n            \"MachineType\": \"vivo X5pro L\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 256,\n            \"MachineType\": \"EVA-DL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 257,\n            \"MachineType\": \"EVA-TL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 260,\n            \"MachineType\": \"EVA-CL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 270,\n            \"MachineType\": \"EVA-AL10\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 307,\n            \"MachineType\": \"SM-N9108V\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 500,\n            \"MachineType\": \"MI 5s Plus\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncloseOpensl y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1078,\n            \"MachineType\": \"PRO 7-H\"\n          },\n          {\n            \"Factory\": \"alps\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 989,\n            \"MachineType\": \"M10\"\n          },\n          {\n            \"Factory\": \"GiONEE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 388,\n            \"MachineType\": \"F103\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 394,\n            \"MachineType\": \"OPPO A33t\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndsp {\\r\\npost {\\r\\npostAGCmoddB -6.0\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 400,\n            \"MachineType\": \"OPPO R7\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 401,\n            \"MachineType\": \"OPPO R9m\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 406,\n            \"MachineType\": \"HUAWEI MT7-CL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 407,\n            \"MachineType\": \"HUAWEI MT7-TL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 421,\n            \"MachineType\": \"HUAWEI NXT-AL10\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 473,\n            \"MachineType\": \"SM-N9200\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 481,\n            \"MachineType\": \"MI 4C\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 485,\n            \"MachineType\": \"Redmi Note 3\"\n          },\n          {\n            \"Factory\": \"DOOV\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 518,\n            \"MachineType\": \"DOOV L5M\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 530,\n            \"MachineType\": \"MI 3\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 554,\n            \"MachineType\": \"MX6\"\n          },\n          {\n            \"Factory\": \"LENOVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1041,\n            \"MachineType\": \"Lenovo K900\"\n          },\n          {\n            \"Factory\": \"M5\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 985,\n            \"MachineType\": \"X5 R1\"\n          },\n          {\n            \"Factory\": \"ZTE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 983,\n            \"MachineType\": \"ZTE N928Dt\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nplayRec {\\r\\ndelay 426\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 778,\n            \"MachineType\": \"OPPO R9s\"\n          },\n          {\n            \"Factory\": \"JTY\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 986,\n            \"MachineType\": \"KT096H\"\n          },\n          {\n            \"Factory\": \"TCL\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 984,\n            \"MachineType\": \"TCL P501M\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1050,\n            \"MachineType\": \"STF-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 857,\n            \"MachineType\": \"EVA-AL00\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 798,\n            \"MachineType\": \"OPPO R9km\"\n          },\n          {\n            \"Factory\": \"Haier\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 990,\n            \"MachineType\": \"HT-L700T\"\n          },\n          {\n            \"Factory\": \"Lenovo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 988,\n            \"MachineType\": \"Lenovo A916\"\n          },\n          {\n            \"Factory\": \"alps\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nplay {\\r\\nhw_sr 44100\\r\\nhw_ch 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 998,\n            \"MachineType\": \"em_ts809_p3mme\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nplayRec {\\r\\ndelay 372\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 999,\n            \"MachineType\": \"vivo X5Pro D\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 858,\n            \"MachineType\": \"OPPO A59s\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 884,\n            \"MachineType\": \"Nexus 5\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 971,\n            \"MachineType\": \"Nexus 5X\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\nhevc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.6\\r\\n}\\r\\n}\\r\\nhevc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.6\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 975,\n            \"MachineType\": \"MI 6\"\n          },\n          {\n            \"Factory\": \"koobee\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 20.0\\r\\npreAGCvvolmax 30.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 991,\n            \"MachineType\": \"koobee M9 Plus\"\n          },\n          {\n            \"Factory\": \"\\u8054\\u60f3\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 992,\n            \"MachineType\": \"S6000\"\n          },\n          {\n            \"Factory\": \"DOOV\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1045,\n            \"MachineType\": \"DOOV L5\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncloseOpensl y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1079,\n            \"MachineType\": \"PRO 7 Plus\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndsp {\\r\\npost {\\r\\npostAGCmoddB -12.0\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1075,\n            \"MachineType\": \"vivo Xplay6\"\n          },\n          {\n            \"Factory\": \"HTC\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nmode 0\\r\\ncap {\\r\\nsource 0\\r\\n}\\r\\nplay {\\r\\nstreamtype 3\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1047,\n            \"MachineType\": \"HTC S720e\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1049,\n            \"MachineType\": \"A33w\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1051,\n            \"MachineType\": \"STF-AL10\"\n          },\n          {\n            \"Factory\": \"unknown\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nmode 0\\r\\ncap {\\r\\nsource 0\\r\\n}\\r\\nplay {\\r\\nstreamtype 3\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1073,\n            \"MachineType\": \"msm8909\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1074,\n            \"MachineType\": \"OPPO A57\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1075,\n            \"MachineType\": \"OPPO R11s\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1076,\n            \"MachineType\": \"OPPO R11\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1077,\n            \"MachineType\": \"OPPO A83\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1078,\n            \"MachineType\": \"OPPO A73\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1079,\n            \"MachineType\": \"SM-G9006V\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1081,\n            \"MachineType\": \"vivo Y83A\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1083,\n            \"MachineType\": \"OPPO R17\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1084,\n            \"MachineType\": \"OPPO R15\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1085,\n            \"MachineType\": \"OPPO A37M\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1086,\n            \"MachineType\": \"OPPO A7X\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1087,\n            \"MachineType\": \"OPPO A3\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1088,\n            \"MachineType\": \"OPPO A5\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1089,\n            \"MachineType\": \"OPPO A83T\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1090,\n            \"MachineType\": \"OPPO R11T\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1091,\n            \"MachineType\": \"OPPO R9S PLUS\"\n          },\n          {\n            \"Factory\": \"VIVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1092,\n            \"MachineType\": \"vivo Y51A\"\n          },\n          {\n            \"Factory\": \"VIVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1093,\n            \"MachineType\": \"vivo V1813A\"\n          },\n          {\n            \"Factory\": \"VIVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1095,\n            \"MachineType\": \"vivo Y67\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1097,\n            \"MachineType\": \"MHA-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1098,\n            \"MachineType\": \"ALP-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1099,\n            \"MachineType\": \"VTR-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1100,\n            \"MachineType\": \"BND-AL10\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1101,\n            \"MachineType\": \"COL-AL10\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1102,\n            \"MachineType\": \"BKL-AL20\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1103,\n            \"MachineType\": \"PRA-AL00X\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1104,\n            \"MachineType\": \"MI 6X\"\n          }\n        ]    }", this.j);
        }
        return this.j.b;
    }
    
    public int c() {
        this.i();
        return this.j.a;
    }
    
    public int d() {
        this.i();
        return this.j.c;
    }
    
    public int e() {
        this.i();
        return this.j.n;
    }
    
    public boolean f() {
        this.i();
        return this.j.q == 1;
    }
    
    public int g() {
        this.i();
        return this.j.m;
    }
    
    public boolean a(final float n, final float n2, final float n3) {
        this.i();
        return n2 >= this.j.k || n3 <= this.j.l || (n >= this.j.i && n3 <= this.j.j);
    }
    
    public long a(final String s, final String s2) {
        this.i();
        long n = this.a(TXCCommonUtil.getAppID(), s, s2);
        if (n == -1L) {
            n = this.a("Global", s, s2);
        }
        return n;
    }
    
    private long a(final String s, final String s2, final String s3) {
        long optLong = -1L;
        if (!TextUtils.isEmpty((CharSequence)s) && this.j.o != null) {
            final JSONObject optJSONObject = this.j.o.optJSONObject(s);
            if (optJSONObject != null) {
                final JSONObject optJSONObject2 = optJSONObject.optJSONObject(s2);
                if (optJSONObject2 != null) {
                    optLong = optJSONObject2.optLong(s3, -1L);
                }
            }
        }
        return optLong;
    }
    
    public boolean a(final String s) {
        this.i();
        if (this.j.o != null) {
            final JSONObject optJSONObject = this.j.o.optJSONObject("Global");
            if (optJSONObject != null) {
                final JSONObject optJSONObject2 = optJSONObject.optJSONObject("DNSWhiteList");
                if (optJSONObject2 != null) {
                    final JSONArray optJSONArray = optJSONObject2.optJSONArray("InfoList");
                    if (optJSONArray != null) {
                        for (int i = 0; i < optJSONArray.length(); ++i) {
                            try {
                                if (s.contains(optJSONArray.getString(i))) {
                                    return true;
                                }
                            }
                            catch (Exception ex) {
                                TXCLog.e("CloudConfig", "get string from json failed.", ex);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private void i() {
        this.j();
        this.h();
    }
    
    private synchronized void j() {
        if (this.l()) {
            this.b(true);
            if (!this.n()) {
                this.o();
            }
            TXCLog.i("CloudConfig", "load config(system aec):" + this.j.d + "," + this.j.e + "," + this.j.g + "," + this.j.h + ", model = " + Build.MODEL + ", manufacturer = " + Build.MANUFACTURER + "\uff0c board = " + Build.BOARD);
        }
    }
    
    private void a(final a a) {
        if (this.c == null && com.tencent.liteav.basic.d.b.g != null) {
            final SharedPreferences sharedPreferences = com.tencent.liteav.basic.d.b.g.getSharedPreferences("cloud_config", 0);
            if (sharedPreferences != null) {
                this.c = sharedPreferences.edit();
            }
        }
        if (this.c != null) {
            this.c.putLong("expired_time", this.e);
            this.c.putInt("hw_config", a.a);
            this.c.putInt("ExposureCompensation", a.c);
            this.c.putInt("UGCSWMuxerConfig", a.n);
            this.c.putInt("CPU", a.i);
            this.c.putInt("FPS", a.j);
            this.c.putInt("CPU_MAX", a.k);
            this.c.putInt("FPS_MIN", a.l);
            this.c.putInt("CheckCount", a.m);
            this.c.putInt("PreGenerateSW", a.q);
            this.c.putString("trae_config", a.b);
            if (a.o != null) {
                this.c.putString("AppIDConfig", a.o.toString());
            }
            String s = "0";
            if (a.f != null) {
                s = "" + a.f[0];
                for (int i = 1; i < a.f.length; ++i) {
                    s = s + "|" + a.f[i];
                }
            }
            if (a.h == null) {
                a.h = "";
            }
            this.c.putString("system_aec_config", a.d + "," + a.e + "," + s + "," + a.g + "," + a.h);
            this.c.commit();
        }
    }
    
    public void h() {
        if (this.k()) {
            this.a(true);
            new Thread() {
                @Override
                public void run() {
                    com.tencent.liteav.basic.d.b.this.m();
                }
            }.start();
        }
    }
    
    private synchronized boolean k() {
        return !this.h && this.e <= System.currentTimeMillis();
    }
    
    private synchronized boolean l() {
        return !this.i;
    }
    
    private synchronized void a(final boolean h) {
        this.h = h;
    }
    
    private synchronized void b(final boolean i) {
        this.i = i;
    }
    
    private synchronized void a(int n) {
        if (n < 1) {
            n = 1;
        }
        this.e = System.currentTimeMillis() + n * 24 * 60 * 60 * 1000;
        TXCLog.i("CloudConfig", "setExpiredtime: days=" + n + " expiredTime=" + this.e);
    }
    
    private void m() {
        try {
            TXCLog.w("CloudConfig", "update server config ");
            final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("http://dldir1.qq.com/hudongzhibo/liteavsvrcfg/serverconfig_en.zip").openConnection();
            if (!this.d.isEmpty()) {
                httpURLConnection.addRequestProperty("If-Modified-Since", this.d);
            }
            final String headerField = httpURLConnection.getHeaderField("Last-Modified");
            final int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                if (headerField != null && !headerField.isEmpty()) {
                    this.d = headerField;
                    if (this.c != null) {
                        this.c.putString("last_modify", this.d);
                    }
                }
                final InputStream inputStream = httpURLConnection.getInputStream();
                final byte[] array = new byte[1024];
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read(array)) != -1) {
                    byteArrayOutputStream.write(array, 0, read);
                }
                final byte[] a = com.tencent.liteav.basic.d.a.a(byteArrayOutputStream.toByteArray(), com.tencent.liteav.basic.d.a.a(Base64.decode(TXCCommonUtil.getConfigCenterKey().getBytes("UTF-8"), 2)));
                File file = null;
                synchronized (b.class) {
                    if (!TextUtils.isEmpty((CharSequence)this.a)) {
                        file = new File(this.a, "serverconfig_dec.zip");
                        final FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(a);
                        fileOutputStream.close();
                    }
                }
                this.b(a(file));
            }
            else if (responseCode == 304) {
                this.a(1);
                final Context g = com.tencent.liteav.basic.d.b.g;
                if (g != null) {
                    final SharedPreferences sharedPreferences = g.getSharedPreferences("cloud_config", 0);
                    if (sharedPreferences != null) {
                        TXCLog.i("CloudConfig", "updateInternal: return 304(code), update expiredTime into file. expiredTime=" + this.e);
                        sharedPreferences.edit().putLong("expired_time", this.e).commit();
                    }
                }
                this.a(false);
                TXCLog.d("CloudConfig", ("fetch config Not-Modified-Since " + this.d == null) ? "" : this.d);
            }
        }
        catch (Exception ex) {
            TXCLog.e("CloudConfig", "fetch config catch exception " + ex);
            this.a(false);
        }
    }
    
    private static String a(final File file) throws IOException {
        synchronized (b.class) {
            final ZipFile zipFile = new ZipFile(file);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry zipEntry = (ZipEntry)entries.nextElement();
                if (zipEntry.isDirectory()) {
                    continue;
                }
                final InputStream inputStream = zipFile.getInputStream(zipEntry);
                final byte[] array = new byte[1048576];
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read(array)) > 0) {
                    byteArrayOutputStream.write(array, 0, read);
                }
                inputStream.close();
                zipFile.close();
                return byteArrayOutputStream.toString();
            }
            zipFile.close();
        }
        return "";
    }
    
    private void b(final String s) {
        this.a(false);
        try {
            final JSONObject jsonObject = new JSONObject(s);
            int int1 = 1;
            if (jsonObject.has("UpdateFrequency")) {
                int1 = jsonObject.getInt("UpdateFrequency");
            }
            this.a(int1);
            final a a = this.a(jsonObject);
            if (a != null) {
                this.a(this.j = a);
            }
        }
        catch (JSONException ex) {
            TXCLog.w("CloudConfig", "parseRespon catch ecxeption" + ex);
        }
    }
    
    private void a(final JSONObject jsonObject, final a a) throws JSONException {
        a.d = 0;
        a.e = 0;
        a.f = null;
        a.g = 0;
        a.h = "";
        String optString = null;
        if (jsonObject.has("SystemAECConfig")) {
            final JSONObject jsonObject2 = jsonObject.getJSONObject("SystemAECConfig");
            if (jsonObject2.has("InfoList")) {
                final JSONArray jsonArray = jsonObject2.getJSONArray("InfoList");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    if (jsonObject3 != null && jsonObject3.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER)) {
                        boolean b = false;
                        boolean b2 = false;
                        final JSONArray optJSONArray = jsonObject3.optJSONArray("WhiteList");
                        if (optJSONArray != null) {
                            for (int j = 0; j < optJSONArray.length(); ++j) {
                                final JSONObject optJSONObject = optJSONArray.optJSONObject(j);
                                if (optJSONObject != null) {
                                    final String optString2 = optJSONObject.optString("Model");
                                    if (optString2 != null && Build.MODEL.equals(optString2)) {
                                        b = true;
                                        break;
                                    }
                                }
                            }
                        }
                        final JSONArray optJSONArray2 = jsonObject3.optJSONArray("BlackList");
                        if (optJSONArray2 != null) {
                            for (int k = 0; k < optJSONArray2.length(); ++k) {
                                final JSONObject optJSONObject2 = optJSONArray2.optJSONObject(k);
                                if (optJSONObject2 != null) {
                                    final String optString3 = optJSONObject2.optString("Model");
                                    if (optString3 != null && Build.MODEL.equals(optString3)) {
                                        b2 = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (b) {
                            try {
                                a.d = jsonObject3.optInt("SystemAEC", 0);
                                a.e = jsonObject3.optInt("AGC", 0);
                                optString = jsonObject3.optString("SampleRate", "");
                                if (!optString.isEmpty()) {
                                    final String[] split = optString.split("\\|");
                                    a.f = new int[split.length];
                                    for (int l = 0; l < split.length; ++l) {
                                        a.f[l] = Integer.valueOf(split[l].trim());
                                    }
                                }
                                a.g = jsonObject3.optInt("HWAACCodec", 0);
                                a.h = jsonObject3.optString("SceneType", "");
                            }
                            catch (Exception ex) {
                                TXCLog.e("CloudConfig", "parseSysAECConfig Exception: " + ex.getMessage());
                            }
                        }
                        if (b2) {
                            a.d = 0;
                            a.e = 0;
                            a.f = null;
                            a.g = 0;
                            a.h = "";
                        }
                    }
                }
            }
        }
        TXCLog.i("CloudConfig", "system aec config1:" + a.d + "," + a.e + "," + optString + "," + a.g + "," + a.h + ", model = " + Build.MODEL + ", manufacturer = " + Build.MANUFACTURER + "\uff0c board = " + Build.BOARD);
    }
    
    private void b(final JSONObject jsonObject, final a a) throws JSONException {
        if (jsonObject.has("TraeConfig")) {
            final JSONObject jsonObject2 = jsonObject.getJSONObject("TraeConfig");
            if (jsonObject2.has("InfoList")) {
                final JSONArray jsonArray = jsonObject2.getJSONArray("InfoList");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    if (jsonObject3 != null && jsonObject3.getString("MachineType").equals(Build.MODEL)) {
                        a.b = jsonObject3.getString("ConfigValue");
                        TXCLog.d("CloudConfig", "parseTRAEConfig get TRAE config: " + a.b);
                        break;
                    }
                }
            }
        }
    }
    
    private void c(final JSONObject jsonObject, final a a) throws JSONException {
        if (jsonObject.has("WeiXinConfig")) {
            final JSONObject jsonObject2 = jsonObject.getJSONObject("WeiXinConfig");
            if (jsonObject2.has("InfoList")) {
                final JSONArray jsonArray = jsonObject2.getJSONArray("InfoList");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    if (jsonObject3 != null) {
                        final String string = jsonObject3.getString("Manufacture");
                        final String string2 = jsonObject3.getString("Model");
                        if (string.equals(Build.MANUFACTURER) && string2.equals(Build.MODEL)) {
                            a.p = Integer.parseInt(jsonObject3.getString("SampleRate"));
                            TXCLog.d("CloudConfig", "parseWeiXinConfig get WeiXin config: record sample rate = " + a.p);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private void d(final JSONObject jsonObject, final a a) throws JSONException {
        a.i = 60;
        a.j = 70;
        a.k = 80;
        a.l = 50;
        a.m = 10;
        if (jsonObject.has("HWWhiteList")) {
            final JSONObject jsonObject2 = jsonObject.getJSONObject("HWWhiteList");
            if (jsonObject2.has("SWToHWThreshold")) {
                final JSONObject jsonObject3 = jsonObject2.getJSONObject("SWToHWThreshold");
                if (jsonObject3.has("CPU")) {
                    a.i = jsonObject3.getInt("CPU");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.CPU:" + a.i);
                }
                if (jsonObject3.has("FPS")) {
                    a.j = jsonObject3.getInt("FPS");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.FPS:" + a.j);
                }
                if (jsonObject3.has("CPU_MAX")) {
                    a.k = jsonObject3.getInt("CPU_MAX");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.CPU:" + a.k);
                }
                if (jsonObject3.has("FPS_MIN")) {
                    a.l = jsonObject3.getInt("FPS_MIN");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.FPS:" + a.l);
                }
                if (jsonObject3.has("CheckCount")) {
                    a.m = jsonObject3.getInt("CheckCount");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.CheckCount:" + a.m);
                }
            }
        }
    }
    
    private void e(final JSONObject jsonObject, final a a) throws JSONException {
        if (jsonObject.has("ExposureWhiteConfig")) {
            final JSONArray jsonArray = jsonObject.getJSONObject("ExposureWhiteConfig").getJSONArray("InfoList");
            for (int i = 0; i < jsonArray.length(); ++i) {
                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                if (jsonObject2.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER) && jsonObject2.getString("Model").equalsIgnoreCase(Build.MODEL)) {
                    a.c = jsonObject2.getInt("ExposureCompensation");
                    TXCLog.d("CloudConfig", "parseExposureConfig get exposure config: " + a.c);
                    break;
                }
            }
            if (com.tencent.liteav.basic.d.b.g != null) {
                com.tencent.liteav.basic.d.b.g.sendBroadcast(new Intent("com.tencent.liteav.basic.serverconfig.get"));
            }
        }
    }
    
    private void f(final JSONObject jsonObject, final a a) throws JSONException {
        if (jsonObject.has("HWBlackConfig")) {
            final JSONObject jsonObject2 = jsonObject.getJSONObject("HWBlackConfig");
            int int1 = 16;
            if (jsonObject2.has("HWMiniSupportAPI")) {
                int1 = jsonObject2.getInt("HWMiniSupportAPI");
            }
            if (Build.VERSION.SDK_INT < int1) {
                a.a = 0;
            }
            else {
                final JSONArray jsonArray = jsonObject2.getJSONArray("InfoList");
                int i = 0;
                while (i < jsonArray.length()) {
                    final JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    if (jsonObject3.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER) && jsonObject3.getString("Model").equalsIgnoreCase(Build.MODEL)) {
                        if (jsonObject3.has("profile")) {
                            a.a = 1;
                            TXCLog.d("CloudConfig", "parseHWBlackConfig get HWBlack config: " + a.a);
                            break;
                        }
                        a.a = 0;
                        TXCLog.d("CloudConfig", "parseHWBlackConfig get HWBlack config: " + a.a);
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
    }
    
    private void a(final String s, final a a) {
        try {
            final JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("InfoList")) {
                final JSONArray jsonArray = jsonObject.getJSONArray("InfoList");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    if (jsonObject2 != null && jsonObject2.getString("MachineType").equals(Build.MODEL)) {
                        a.b = jsonObject2.getString("ConfigValue");
                        break;
                    }
                }
            }
        }
        catch (JSONException ex) {
            TXCLog.w("CloudConfig", "parseTraeLocalConfig catch ecxeption" + ex);
        }
    }
    
    private void g(final JSONObject jsonObject, final a a) throws JSONException {
        if (jsonObject.has("UGCSWMuxerConfig")) {
            final JSONArray jsonArray = jsonObject.getJSONObject("UGCSWMuxerConfig").getJSONArray("InfoList");
            for (int i = 0; i < jsonArray.length(); ++i) {
                if (jsonArray.getJSONObject(i).getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER)) {
                    a.n = 1;
                    break;
                }
            }
        }
    }
    
    private void h(final JSONObject jsonObject, final a a) throws JSONException {
        if (jsonObject.has("UGCConfig")) {
            final JSONObject jsonObject2 = jsonObject.getJSONObject("UGCConfig");
            if (jsonObject2 != null) {
                if (jsonObject2.has("PreGenerateALLSW") && jsonObject2.getInt("PreGenerateALLSW") == 1) {
                    a.q = 1;
                    return;
                }
                if (jsonObject2.has("PreGenerateSW")) {
                    final JSONObject jsonObject3 = jsonObject2.getJSONObject("PreGenerateSW");
                    if (jsonObject3 != null) {
                        final JSONArray jsonArray = jsonObject3.getJSONArray("InfoList");
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            final JSONObject jsonObject4 = jsonArray.getJSONObject(i);
                            if (jsonObject4.has("Manufacture") && jsonObject4.has("Model") && jsonObject4.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER) && jsonObject4.getString("Model").equalsIgnoreCase(Build.MODEL)) {
                                a.q = 1;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void i(final JSONObject jsonObject, final a a) throws JSONException {
        if (jsonObject.has("AppIDConfig")) {
            final JSONObject optJSONObject = jsonObject.optJSONObject("AppIDConfig");
            if (optJSONObject != null) {
                a.o = optJSONObject;
                TXCLog.w("CloudConfig", "parse global config : " + a.o);
            }
        }
    }
    
    private boolean n() {
        if (com.tencent.liteav.basic.d.b.g == null) {
            return false;
        }
        this.b = com.tencent.liteav.basic.d.b.g.getSharedPreferences("cloud_config", 0);
        if (this.b != null && this.b.contains("expired_time")) {
            TXCLog.w("CloudConfig", "loadLocalFileConfig ");
            this.c = this.b.edit();
            try {
                this.d = this.b.getString("last_modify", "");
                this.e = this.b.getLong("expired_time", System.currentTimeMillis());
                this.j.a = this.b.getInt("hw_config", 2);
                this.j.c = this.b.getInt("ExposureCompensation", 0);
                this.j.n = this.b.getInt("UGCSWMuxerConfig", 0);
                this.j.i = this.b.getInt("CPU", 60);
                this.j.j = this.b.getInt("FPS", 70);
                this.j.k = this.b.getInt("CPU_MAX", 80);
                this.j.l = this.b.getInt("FPS_MIN", 50);
                this.j.m = this.b.getInt("CheckCount", 10);
                this.j.b = this.b.getString("trae_config", "");
                this.j.q = this.b.getInt("PreGenerateSW", 1);
                final String string = this.b.getString("system_aec_config", "");
                final String string2 = this.b.getString("AppIDConfig", "");
                if (!TextUtils.isEmpty((CharSequence)string2)) {
                    this.j.o = new JSONObject(string2);
                }
                TXCLog.i("CloudConfig", "system aec config:" + string);
                if (!string.isEmpty()) {
                    final String[] split = string.split(",");
                    if (split != null && split.length >= 5) {
                        this.j.d = Integer.valueOf(split[0]);
                        this.j.e = Integer.valueOf(split[1]);
                        final String[] split2 = split[2].split("\\|");
                        if (split2 != null) {
                            this.j.f = new int[split2.length];
                            for (int i = 0; i < split2.length; ++i) {
                                this.j.f[i] = Integer.valueOf(split2[i].trim());
                            }
                        }
                        this.j.g = Integer.valueOf(split[3]);
                        this.j.h = split[4];
                    }
                }
            }
            catch (Exception ex) {
                this.c.clear();
                this.c.commit();
                TXCLog.d("CloudConfig", "loadLocalFileConfig catch exception " + ex);
                return false;
            }
            return true;
        }
        return false;
    }
    
    private void o() {
        try {
            TXCLog.w("CloudConfig", "loadDefaultConfig ");
            final a a = this.a(new JSONObject("{\n    \"version\": 3,\n    \"UpdateFrequency\": 1,\n    \"AppIDConfig\": {\n        \"Global\": {\n            \"DataReport\": {\n                \"UserPortrait\":0\n            },\n            \"DNSWhiteList\": {\n              \"InfoList\": [\n                \"qq\",\n                \"tencent\"\n              ]\n            },\n            \"Audio\": {\n                \"RTC_JitterCycle\":240,\n                \"LIVE_JitterCycle\":500,\n                \"LoadingThreshold\":800,\n                \"SmoothModeAdjust\":0,\n                \"SmoothAdjust\":10,\n                \"RtcPlayHungryTimeThreshold\":60,\n                \"BlockThreshold\": 100,\n                \"SmoothSpeed\":1.2\n            },\n            \"Network\": {\n                \"EnableRouteOptimize\":0,\n                \"RouteSamplingMaxCount\":10,\n                \"QualityDataCacheCount\":0,\n                \"NACKRetryCnt\": 8,\n                \"AccRetryCountWithoutSecret\":1\n            },\n            \"QUICMode\": {\n                \"Live\":2,\n                \"LinkMain\":2,\n                \"LinkSub\":2,\n                \"RTC\":2,\n                \"AVRoom\":0\n            },\n            \"ILiveRoom\": {\n               \"Live_FecRatioAudio\":0,\n               \"Live_FecRatioVideoI\":0,\n               \"Live_FecRatioVideoP\":0,\n               \"Chat_FecRatioAudio\":0,\n               \"Chat_FecRatioVideoI\":0,\n               \"Chat_FecRatioVideoP\":0,\n               \"Rtc_MinCacheTime\":300,\n               \"Rtc_MaxCacheTime\":1000,\n               \"MinCacheTime\":700,\n               \"MaxCacheTime\":2000\n           },\n           \"Video\": {\n               \"CheckVideoEncDownBps\":1\n           }\n       },\n       \"1252463788\":{\n            \"Network\": {\n                \"EnableRouteOptimize\":1,\n                \"RouteSamplingMaxCount\":100,\n                \"QualityDataCacheCount\":10\n            }\n       }\n    },\n    \"PlayModeConfig\":{\n        \"SmoothModeOffset\":300\n    },\n    \"UGCSWMuxerConfig\": {\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"HUAWEI\"\n            }\n        ]\n    },\n    \"UGCConfig\": {\n        \"PreGenerateALLSW\":0,\n        \"PreGenerateSW\": {\n                \"InfoList\": [\n                    {\n                        \"Manufacture\": \"OPPO\",\n                        \"Model\": \"OPPO A57\"\n                    }\n                ]\n            }\n         },\n    \"HWBlackConfig\": {\n        \"HWMiniSupportAPI\": 17,\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"P6-U06\"\n            },\n            {\n                \"Manufacture\": \"Samsung\",\n                \"Model\": \"SCH-I939(S3)\"\n            },\n            {\n                \"Manufacture\": \"VIVO\",\n                \"Model\": \"vivo X5Pro D\"\n            },\n            {\n                \"Manufacture\": \"\u91d1\u7acb\",\n                \"Model\": \"GN9006\"\n            },\n            {\n                \"Manufacture\": \"Samsung\",\n                \"Model\": \"A7000\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"HUAWEI NXT-AL10\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"HUAWEI MHA-AL00\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"EVA-AL00\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"EVA-AL10\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-L29\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-L00\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-TL00\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n             {\n                 \"Manufacture\": \"HUAWEI\",\n                 \"Model\": \"HUAWEI GRA-UL00\"\n             }\n        ]\n    },\n    \"ExposureWhiteConfig\": {\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"Meizu\",\n                \"Model\": \"MX4 Pro\",\n                \"ExposureCompensation\": 1\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"Model\": \"MI 3\",\n                \"ExposureCompensation\": 30\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"Model\": \"MI 3C\",\n                \"ExposureCompensation\": 30\n            }\n        ]\n    },\n    \"SystemAECConfig\": {\n        \"__comment__\":\"SceneType(\u5f00\u542f\u7cfb\u7edfaec\u7684\u573a\u666f\uff0c1\u4e3a\u8fde\u9ea6\uff0c2\u4e3a\u901a\u8bdd)\",\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"vivo\",\n                \"WhiteList\": [{\"Model\":\"vivo X9\"}],\n                \"BlackList\": [],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"vivo\",\n                \"WhiteList\": [{\"Model\":\"vivo X9Plus\"},{\"Model\":\"vivo X7Plus\"},{\"Model\":\"vivo X7\"}],\n                \"BlackList\": [{\"Model\":\"vivo Y51A\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"WhiteList\": [{\"Model\":\"VTR-TL00\"},{\"Model\":\"HUAWEI GRA-UL00\"},{\"Model\":\"HUAWEI NXT-AL10\"},{\"Model\":\"PLK-AL10\"},{\"Model\":\"PLK-UL00\"},{\"Model\":\"EVA-AL10\"},{\"Model\":\"HUAWEI MT7-TL10\"}],\n                \"BlackList\": [{\"Model\":\"MHA-AL00\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"Redmi Note 2\"},{\"Model\":\"Redmi Note 4\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"Redmi Note 3\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"MI 4\"},{\"Model\":\"MI 3C\"},{\"Model\":\"Mi-4c\"}],\n                \"BlackList\": [{\"Model\":\"MI 6\"},{\"Model\":\"Redmi 4A\"},{\"Model\":\"MI 5X\"},{\"Model\":\"MI 5\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Google\",\n                \"WhiteList\": [{\"Model\":\"Pixel XL\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"samsung\",\n                \"WhiteList\": [{\"Model\":\"SM-G9350\"},{\"Model\":\"SM-G9500\"},{\"Model\":\"SM-G950U\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                 \"Manufacture\": \"LeMobile\",\n                 \"WhiteList\": [{\"Model\":\"X620\"}],\n                 \"SystemAEC\": 1,\n                 \"AGC\": 0,\n                 \"SampleRate\": \"48000|16000\",\n                 \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n             {\n                  \"Manufacture\": \"asus\",\n                  \"WhiteList\": [{\"Model\":\"ASUS_Z00ADB\"}],\n                  \"SystemAEC\": 1,\n                  \"AGC\": 0,\n                  \"SampleRate\": \"48000|16000\",\n                  \"HWAACCodec\": 1,\n                 \"SceneType\": \"1|2\"\n             }\n        ]\n    },\n    \"WeiXinConfig\": {\n        \"InfoList\":[\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"EVA-AL00\",\n                \"SampleRate\": \"16000\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"ALP-AL00\",\n                \"SampleRate\": \"16000\"\n            }\n        ]\n    },\n    \"HWWhiteList\": {\n        \"SWToHWThreshold\": {\n            \"CPU_MAX\": 80,\n            \"FPS_MIN\": 50,\n            \"CPU\": 20,\n            \"FPS\": 70,\n            \"CheckCount\": 10,\n            \"__comment__\": \"\u8f6f\u7f16\u6839\u636e\u6027\u80fd\u5207\u786c\u7f16\u7684\u9608\u503c\uff0cavgTotalCPU >= CPU_MAX || avgFPS <= FPS_MIN || (avgAppCPU >= CPU && avgFPS <= FPS) \u6027\u80fd\u6307\u6807\u6ee1\u8db3\u4e0a\u8ff0\u6761\u4ef6\u5219\u5207\u786c\u7f16\u3002CheckCount\u8868\u793a\u9700\u8981\u505aX\u6b21\u6709\u6548\u6027\u80fd\u6570\u636e\u91c7\u96c6\u3002\"\n\t\t}\n    },\n    \"TraeConfig\": {\n        \"InfoList\": [\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 90,\n            \"MachineType\": \"MI 4W\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 94,\n            \"MachineType\": \"MI 5\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndsp {\\r\\npost {\\r\\npostAGCmoddB -6.0\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 96,\n            \"MachineType\": \"PLK-AL10\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 97,\n            \"MachineType\": \"SM-G9350\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 98,\n            \"MachineType\": \"MX4 Pro\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 99,\n            \"MachineType\": \"A31\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 118,\n            \"MachineType\": \"MI 4LTE\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 132,\n            \"MachineType\": \"OPPO R9tm\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 156,\n            \"MachineType\": \"Mi-4c\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 166,\n            \"MachineType\": \"OPPO A33\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 174,\n            \"MachineType\": \"OPPO A33m\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 191,\n            \"MachineType\": \"m1\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 213,\n            \"MachineType\": \"vivo X6Plus D\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 226,\n            \"MachineType\": \"vivo X5Pro V\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 251,\n            \"MachineType\": \"vivo X5pro L\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 256,\n            \"MachineType\": \"EVA-DL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 257,\n            \"MachineType\": \"EVA-TL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 260,\n            \"MachineType\": \"EVA-CL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 270,\n            \"MachineType\": \"EVA-AL10\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 307,\n            \"MachineType\": \"SM-N9108V\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 500,\n            \"MachineType\": \"MI 5s Plus\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncloseOpensl y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1078,\n            \"MachineType\": \"PRO 7-H\"\n          },\n          {\n            \"Factory\": \"alps\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 989,\n            \"MachineType\": \"M10\"\n          },\n          {\n            \"Factory\": \"GiONEE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 388,\n            \"MachineType\": \"F103\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 394,\n            \"MachineType\": \"OPPO A33t\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndsp {\\r\\npost {\\r\\npostAGCmoddB -6.0\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 400,\n            \"MachineType\": \"OPPO R7\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 401,\n            \"MachineType\": \"OPPO R9m\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 406,\n            \"MachineType\": \"HUAWEI MT7-CL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 407,\n            \"MachineType\": \"HUAWEI MT7-TL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 421,\n            \"MachineType\": \"HUAWEI NXT-AL10\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 473,\n            \"MachineType\": \"SM-N9200\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 481,\n            \"MachineType\": \"MI 4C\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 485,\n            \"MachineType\": \"Redmi Note 3\"\n          },\n          {\n            \"Factory\": \"DOOV\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 518,\n            \"MachineType\": \"DOOV L5M\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 530,\n            \"MachineType\": \"MI 3\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 554,\n            \"MachineType\": \"MX6\"\n          },\n          {\n            \"Factory\": \"LENOVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1041,\n            \"MachineType\": \"Lenovo K900\"\n          },\n          {\n            \"Factory\": \"M5\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 985,\n            \"MachineType\": \"X5 R1\"\n          },\n          {\n            \"Factory\": \"ZTE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 983,\n            \"MachineType\": \"ZTE N928Dt\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nplayRec {\\r\\ndelay 426\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 778,\n            \"MachineType\": \"OPPO R9s\"\n          },\n          {\n            \"Factory\": \"JTY\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 986,\n            \"MachineType\": \"KT096H\"\n          },\n          {\n            \"Factory\": \"TCL\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 984,\n            \"MachineType\": \"TCL P501M\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1050,\n            \"MachineType\": \"STF-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 857,\n            \"MachineType\": \"EVA-AL00\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 798,\n            \"MachineType\": \"OPPO R9km\"\n          },\n          {\n            \"Factory\": \"Haier\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 990,\n            \"MachineType\": \"HT-L700T\"\n          },\n          {\n            \"Factory\": \"Lenovo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 988,\n            \"MachineType\": \"Lenovo A916\"\n          },\n          {\n            \"Factory\": \"alps\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nplay {\\r\\nhw_sr 44100\\r\\nhw_ch 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 998,\n            \"MachineType\": \"em_ts809_p3mme\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nplayRec {\\r\\ndelay 372\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 999,\n            \"MachineType\": \"vivo X5Pro D\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 858,\n            \"MachineType\": \"OPPO A59s\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 884,\n            \"MachineType\": \"Nexus 5\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 971,\n            \"MachineType\": \"Nexus 5X\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\nhevc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.6\\r\\n}\\r\\n}\\r\\nhevc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.6\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 975,\n            \"MachineType\": \"MI 6\"\n          },\n          {\n            \"Factory\": \"koobee\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 20.0\\r\\npreAGCvvolmax 30.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 991,\n            \"MachineType\": \"koobee M9 Plus\"\n          },\n          {\n            \"Factory\": \"\\u8054\\u60f3\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 992,\n            \"MachineType\": \"S6000\"\n          },\n          {\n            \"Factory\": \"DOOV\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1045,\n            \"MachineType\": \"DOOV L5\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncloseOpensl y\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1079,\n            \"MachineType\": \"PRO 7 Plus\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndsp {\\r\\npost {\\r\\npostAGCmoddB -12.0\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1075,\n            \"MachineType\": \"vivo Xplay6\"\n          },\n          {\n            \"Factory\": \"HTC\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nmode 0\\r\\ncap {\\r\\nsource 0\\r\\n}\\r\\nplay {\\r\\nstreamtype 3\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1047,\n            \"MachineType\": \"HTC S720e\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nfiAcc {\\r\\nring_buf_mp3 50\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1049,\n            \"MachineType\": \"A33w\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1051,\n            \"MachineType\": \"STF-AL10\"\n          },\n          {\n            \"Factory\": \"unknown\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nmode 0\\r\\ncap {\\r\\nsource 0\\r\\n}\\r\\nplay {\\r\\nstreamtype 3\\r\\n}\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1073,\n            \"MachineType\": \"msm8909\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1074,\n            \"MachineType\": \"OPPO A57\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1075,\n            \"MachineType\": \"OPPO R11s\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1076,\n            \"MachineType\": \"OPPO R11\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1077,\n            \"MachineType\": \"OPPO A83\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1078,\n            \"MachineType\": \"OPPO A73\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1079,\n            \"MachineType\": \"SM-G9006V\"\n          },\n          {\n            \"Factory\": \"vivo\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1081,\n            \"MachineType\": \"vivo Y83A\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1083,\n            \"MachineType\": \"OPPO R17\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1084,\n            \"MachineType\": \"OPPO R15\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1085,\n            \"MachineType\": \"OPPO A37M\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1086,\n            \"MachineType\": \"OPPO A7X\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1087,\n            \"MachineType\": \"OPPO A3\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1088,\n            \"MachineType\": \"OPPO A5\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1089,\n            \"MachineType\": \"OPPO A83T\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1090,\n            \"MachineType\": \"OPPO R11T\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1091,\n            \"MachineType\": \"OPPO R9S PLUS\"\n          },\n          {\n            \"Factory\": \"VIVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1092,\n            \"MachineType\": \"vivo Y51A\"\n          },\n          {\n            \"Factory\": \"VIVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1093,\n            \"MachineType\": \"vivo V1813A\"\n          },\n          {\n            \"Factory\": \"VIVO\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1095,\n            \"MachineType\": \"vivo Y67\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1097,\n            \"MachineType\": \"MHA-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1098,\n            \"MachineType\": \"ALP-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1099,\n            \"MachineType\": \"VTR-AL00\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1100,\n            \"MachineType\": \"BND-AL10\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1101,\n            \"MachineType\": \"COL-AL10\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 1\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1102,\n            \"MachineType\": \"BKL-AL20\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1103,\n            \"MachineType\": \"PRA-AL00X\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nlow_latency 0\\r\\n}\\r\\n}\\r\\n}\",\n            \"id\": 1104,\n            \"MachineType\": \"MI 6X\"\n          }\n        ]\n    }\n}\n"));
            if (a != null) {
                this.j = a;
            }
        }
        catch (JSONException ex) {
            TXCLog.w("CloudConfig", "loadDefaultConfig catch exception " + ex);
            this.j = new a();
        }
    }
    
    private a a(final JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        a a = new a();
        try {
            this.f(jsonObject, a);
            this.e(jsonObject, a);
            this.d(jsonObject, a);
            this.a(jsonObject, a);
            this.b(jsonObject, a);
            this.c(jsonObject, a);
            this.g(jsonObject, a);
            this.i(jsonObject, a);
            this.h(jsonObject, a);
        }
        catch (JSONException ex) {
            TXCLog.w("CloudConfig", "parse config catch exception " + ex.toString());
            a = null;
        }
        return a;
    }
    
    static {
        b.g = null;
    }
    
    private static class a
    {
        private int a;
        private String b;
        private int c;
        private int d;
        private int e;
        private int[] f;
        private int g;
        private String h;
        private int i;
        private int j;
        private int k;
        private int l;
        private int m;
        private int n;
        private JSONObject o;
        private int p;
        private int q;
        
        private a() {
            this.a = 2;
            this.b = "";
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = null;
            this.g = 0;
            this.h = "";
            this.i = 60;
            this.j = 70;
            this.k = 80;
            this.l = 50;
            this.m = 10;
            this.n = 0;
            this.o = new JSONObject();
            this.p = -1;
            this.q = 0;
        }
    }
}
