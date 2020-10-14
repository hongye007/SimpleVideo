package com.tencent.liteav.j;

import android.graphics.*;
import com.tencent.liteav.basic.log.*;

public class a
{
    public static Bitmap a(final float n, final Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        final Matrix matrix = new Matrix();
        matrix.postRotate(n);
        final Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap2 != bitmap && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmap2;
    }
    
    public static Bitmap a(final Bitmap bitmap, final int alpha) {
        final Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap2);
        final Paint paint = new Paint();
        paint.setAlpha(alpha);
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRect(rectF, paint);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap2;
    }
    
    public static float a(final int n, final long n2) {
        final long a = a(n);
        final long b = b(n);
        final long n3 = a + b;
        final long n4 = n2 - n2 / n3 * n3;
        float n5;
        if (n4 >= 0L && n4 <= a) {
            n5 = 0.0f;
            TXCLog.d("BitmapUtil", "setBitmapsAndDisplayRatio, in stay status, cropOffsetRatio = 0, remainTimeMs = " + n4);
        }
        else {
            n5 = (n4 - a) / (float)b;
            TXCLog.d("BitmapUtil", "setBitmapsAndDisplayRatio, in motion status, cropOffsetRatio = " + n5 + ", remainTimeMs = " + n4);
        }
        return n5;
    }
    
    public static float b(final int n, final long n2) {
        float n3 = 1.0f;
        final long a = a(n);
        final long b = b(n);
        final long n4 = a + b;
        final long n5 = n2 - n2 / n4 * n4;
        switch (n) {
            case 4: {
                if (n5 >= 0L && n5 <= a) {
                    n3 = 1.0f;
                    break;
                }
                if (n5 > a && n5 < n4) {
                    n3 = 1.0f + 0.1f * (n5 - a) / b;
                    break;
                }
                break;
            }
            case 5: {
                if (n5 >= 0L && n5 <= a) {
                    n3 = 1.1f;
                    break;
                }
                if (n5 > a && n5 <= n4) {
                    n3 = 1.1f - 0.1f * (n5 - a) / b;
                    break;
                }
                break;
            }
            case 3: {
                if (n5 >= 0L && n5 <= a) {
                    n3 = 1.0f;
                    break;
                }
                if (n5 > a && n5 <= n4) {
                    n3 = 1.0f - (n5 - a) / (float)b;
                    break;
                }
                break;
            }
        }
        return n3;
    }
    
    public static float c(final int n, final long n2) {
        float n3 = 1.0f;
        final long a = a(n);
        final long b = b(n);
        final long n4 = a + b;
        final long n5 = n2 - n2 / n4 * n4;
        switch (n) {
            case 6: {
                if (n5 >= 0L && n5 <= a) {
                    n3 = 1.0f;
                    break;
                }
                if (n5 > a && n5 <= n4) {
                    n3 = 1.0f - (n5 - a) / (float)b;
                    break;
                }
                break;
            }
            case 4:
            case 5: {
                if (n5 >= 0L && n5 <= a + b * 0.8) {
                    n3 = 1.0f;
                    break;
                }
                if (n5 > a + b * 0.8 && n5 <= n4) {
                    n3 = 1.0f - (n5 - a - b * 0.8f) / (b * 0.2f);
                    break;
                }
                break;
            }
        }
        return n3;
    }
    
    public static int d(final int n, final long n2) {
        int n3 = 0;
        final long a = a(n);
        final long b = b(n);
        final long n4 = a + b;
        final long n5 = n2 - n2 / n4 * n4;
        switch (n) {
            case 3: {
                if (n5 > 0L && n5 <= a) {
                    n3 = 0;
                    break;
                }
                if (n5 > a && n5 <= n4) {
                    n3 = (int)((n5 - a) / (float)b * 360.0f);
                    break;
                }
                break;
            }
        }
        return n3;
    }
    
    public static long a(final int n) {
        long n2 = 1000L;
        switch (n) {
            case 1:
            case 2: {
                n2 = 1000L;
                break;
            }
            case 4:
            case 5: {
                n2 = 100L;
                break;
            }
            case 3:
            case 6: {
                n2 = 1500L;
                break;
            }
        }
        return n2;
    }
    
    public static long b(final int n) {
        long n2 = 500L;
        switch (n) {
            case 1:
            case 2: {
                n2 = 500L;
                break;
            }
            case 4:
            case 5: {
                n2 = 2500L;
                break;
            }
            case 3: {
                n2 = 1000L;
                break;
            }
            case 6: {
                n2 = 1500L;
                break;
            }
        }
        return n2;
    }
}
