package com.tencent.liteav.b;

import android.annotation.*;
import com.tencent.liteav.l.*;
import android.content.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;

@TargetApi(17)
public class j
{
    private a a;
    private List<com.tencent.liteav.i.a.a> b;
    private int c;
    private int d;
    
    public j(final Context context) {
        this.a = new a(context);
    }
    
    public void a(final List<com.tencent.liteav.i.a.a> b, final int c, final int d) {
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public int a(final ArrayList<f> list) {
        if (list == null || this.b.size() != list.size()) {
            TXCLog.e("TXCombineProcess", "join picture must has same TXAbsoluteRect!!!");
            return -1;
        }
        final com.tencent.liteav.basic.e.a[] array = new com.tencent.liteav.basic.e.a[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            final com.tencent.liteav.i.a.a a = this.b.get(i);
            final com.tencent.liteav.basic.e.a a2 = new com.tencent.liteav.basic.e.a();
            a2.a = list.get(i).a;
            a2.b = 0;
            if (list.get(i).b != null) {
                a2.c = list.get(i).b.m();
                a2.d = list.get(i).b.n();
            }
            else {
                a2.c = a.c;
                a2.d = a.d;
            }
            a2.f = com.tencent.liteav.basic.util.f.a(a2.c, a2.d, a.c, a.d);
            a2.g = new com.tencent.liteav.basic.c.a(a.a, a.b, a.c, a.d);
            array[i] = a2;
        }
        this.a.a(this.c, this.d);
        this.a.b(this.c, this.d);
        return this.a.a(array, 0);
    }
    
    public void a() {
        TXCLog.i("TXCombineProcess", "destroy!");
        if (this.a != null) {
            this.a.a();
        }
    }
}
