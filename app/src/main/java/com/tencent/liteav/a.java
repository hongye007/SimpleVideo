package com.tencent.liteav;

import com.tencent.liteav.audio.*;
import com.tencent.liteav.basic.module.*;
import java.util.*;

public class a
{
    public static void a(final String s, final ArrayList<String> list) {
        final StatusBucket status = TXCAudioEngine.getInstance().getStatus(1);
        TXCStatus.a(s, 14003, (Object)status.getIntStatus("18446744073709551615", 10001));
        TXCStatus.a(s, 8005, (Object)status.getIntStatus("18446744073709551615", 10002));
        TXCStatus.a(s, 14017, (Object)status.getIntStatus("18446744073709551615", 10000));
        TXCStatus.a(s, 14002, (Object)status.getIntStatus("18446744073709551615", 10003));
        TXCStatus.a(s, 14006, (Object)status.getIntStatus("18446744073709551615", 10004));
        TXCStatus.a(s, 14018, (Object)status.getIntStatus("18446744073709551615", 10005));
        TXCStatus.a(s, 18029, (Object)status.getIntStatus("18446744073709551615", 10006));
        if (list == null) {
            return;
        }
        for (final String s2 : list) {
            final int intStatus = status.getIntStatus(s2, 10114);
            final int intStatus2 = status.getIntStatus(s2, 10115);
            TXCStatus.a(s2, 18031, (Object)intStatus);
            TXCStatus.a(s2, 18032, (Object)intStatus2);
            TXCStatus.a(s2, 2001, (Object)((intStatus2 > 0) ? (intStatus / intStatus2) : 0));
            TXCStatus.a(s2, 2002, (Object)intStatus2);
            TXCStatus.a(s2, 2005, (Object)intStatus2);
            TXCStatus.a(s2, 2004, (Object)status.getIntStatus(s2, 10300));
            TXCStatus.a(s2, 2008, (Object)status.getIntStatus(s2, 10112));
            TXCStatus.a(s2, 2010, (Object)status.getIntStatus(s2, 10106));
            TXCStatus.a(s2, 2007, (Object)status.getIntStatus(s2, 10105));
            TXCStatus.a(s2, 2011, (Object)status.getIntStatus(s2, 10107));
            TXCStatus.a(s2, 18001, (Object)status.getIntStatus(s2, 10103));
            TXCStatus.a(s2, 18002, (Object)status.getIntStatus(s2, 10104));
            TXCStatus.a(s2, 18006, (Object)status.getIntStatus(s2, 10202));
            TXCStatus.a(s2, 18007, (Object)status.getIntStatus(s2, 10203));
            TXCStatus.a(s2, 18008, (Object)status.getIntStatus(s2, 10204));
            TXCStatus.a(s2, 18015, (Object)status.getIntStatus(s2, 10205));
            TXCStatus.a(s2, 18013, (Object)status.getIntStatus(s2, 10206));
            final int intStatus3 = status.getIntStatus(s2, 10203);
            final int intStatus4 = status.getIntStatus(s2, 10202);
            TXCStatus.a(s2, 18014, (Object)((intStatus4 > 0) ? (intStatus3 * 100 / intStatus4) : 0));
            TXCStatus.a(s2, 18023, (Object)status.getIntStatus(s2, 10200));
            TXCStatus.a(s2, 18026, (Object)status.getIntStatus(s2, 10110));
            TXCStatus.a(s2, 18027, (Object)status.getIntStatus(s2, 10111));
            TXCStatus.a(s2, 18028, (Object)status.getIntStatus(s2, 10201));
            TXCStatus.a(s2, 18030, (Object)status.getIntStatus(s2, 10113));
            TXCStatus.a(s2, 18003, (Object)status.getIntStatus(s2, 10100));
            TXCStatus.a(s2, 2019, (Object)status.getIntStatus(s2, 10100));
            TXCStatus.a(s2, 2020, (Object)status.getIntStatus(s2, 10101));
            TXCStatus.a(s2, 18016, (Object)status.getIntStatus(s2, 10102));
            TXCStatus.a(s2, 18009, (Object)status.getIntStatus(s2, 10207));
            TXCStatus.a(s2, 18010, (Object)status.getIntStatus(s2, 10208));
            TXCStatus.a(s2, 18012, (Object)status.getIntStatus(s2, 10209));
            TXCStatus.a(s2, 2021, (Object)status.getIntStatus(s2, 10109));
        }
    }
}
