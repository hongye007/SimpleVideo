package com.tencent.rtmp.video;

import android.app.*;
import android.annotation.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.screencapture.*;
import android.media.projection.*;
import android.content.*;

public class TXScreenCapture
{
    @TargetApi(21)
    public static class TXScreenCaptureAssistantActivity extends Activity
    {
        private static final String TAG = "TXScreenCaptureAssistantActivity";
        private static final int REQUEST_CODE = 100;
        private MediaProjectionManager mMediaProjectionManager;
        
        public void onCreate(final Bundle bundle) {
            super.onCreate(bundle);
            TXCLog.i("TXScreenCaptureAssistantActivity", "onCreate " + this);
            this.requestWindowFeature(1);
            this.mMediaProjectionManager = (MediaProjectionManager)this.getApplicationContext().getSystemService("media_projection");
            final Intent screenCaptureIntent = this.mMediaProjectionManager.createScreenCaptureIntent();
            try {
                this.startActivityForResult(screenCaptureIntent, 100);
            }
            catch (Exception ex) {
                TXCLog.e("TXScreenCaptureAssistantActivity", "start permission activity failed. " + ex);
                c.a((Context)this).a((MediaProjection)null);
                this.finish();
            }
        }
        
        public void onActivityResult(final int n, final int n2, final Intent intent) {
            TXCLog.i("TXScreenCaptureAssistantActivity", "onActivityResult " + this);
            c.a((Context)this).a(this.mMediaProjectionManager.getMediaProjection(n2, intent));
            this.finish();
        }
        
        protected void onDestroy() {
            super.onDestroy();
            TXCLog.i("TXScreenCaptureAssistantActivity", "onDestroy " + this);
        }
    }
}
