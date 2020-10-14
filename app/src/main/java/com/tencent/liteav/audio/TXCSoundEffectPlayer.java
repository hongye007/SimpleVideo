package com.tencent.liteav.audio;

import java.lang.ref.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import java.io.*;
import android.text.*;
import com.tencent.liteav.basic.util.*;

public class TXCSoundEffectPlayer implements TXAudioEffectManager.TXMusicPlayObserver
{
    private static final String TAG = "AudioCenter:TXCSoundEffectPlayer";
    private static WeakReference<com.tencent.liteav.audio.a> mWeakSoundEffectListener;
    private List<Integer> mShortEffectorIDList;
    
    public static TXCSoundEffectPlayer getInstance() {
        return a.a;
    }
    
    public TXCSoundEffectPlayer() {
        this.mShortEffectorIDList = new ArrayList<Integer>();
    }
    
    public boolean playEffectWithId(final int n, final String s, final boolean publish, final int loopCount) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "playEffectWithId -> effect id = " + n + ", path = " + s + ", loop = " + loopCount);
        final TXAudioEffectManager.AudioMusicParam audioMusicParam = new TXAudioEffectManager.AudioMusicParam(n, this.checkIfAssetsFile(s));
        audioMusicParam.publish = publish;
        audioMusicParam.loopCount = loopCount;
        audioMusicParam.isShortFile = true;
        final boolean startPlayMusic = TXAudioEffectManagerImpl.getCacheInstance().startPlayMusic(audioMusicParam);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(n, this);
        this.mShortEffectorIDList.add(n);
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "playEffectWithId ->effect id = " + n + ", startPlayMusic result = " + startPlayMusic);
        return startPlayMusic;
    }
    
    public void stopEffectWithId(final int n) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "stopEffectWithId -> effect id = " + n);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(n, null);
        TXAudioEffectManagerImpl.getCacheInstance().stopPlayMusic(n);
        final int index = this.mShortEffectorIDList.indexOf(n);
        if (index >= 0) {
            this.mShortEffectorIDList.remove(index);
        }
    }
    
    public void stopAllEffect() {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "stopAllEffect -> start");
        final Iterator<Integer> iterator = this.mShortEffectorIDList.iterator();
        while (iterator.hasNext()) {
            TXAudioEffectManagerImpl.getCacheInstance().stopPlayMusic(iterator.next());
        }
        this.mShortEffectorIDList.clear();
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "stopAllEffect -> finish");
    }
    
    public void setEffectsVolume(final float n) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "setEffectsVolume -> volume = " + n);
        final Iterator<Integer> iterator = this.mShortEffectorIDList.iterator();
        while (iterator.hasNext()) {
            TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(iterator.next(), (int)(n * 100.0f));
        }
    }
    
    public void setVolumeOfEffect(final int n, final float n2) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "setVolumeOfEffect -> effect id = " + n + ", volume = " + n2);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(n, (int)(n2 * 100.0f));
    }
    
    public void pauseEffectWithId(final int n) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "pauseEffectWithId -> effect id = " + n);
        TXAudioEffectManagerImpl.getCacheInstance().pausePlayMusic(n);
    }
    
    public void resumeEffectWithId(final int n) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "resumeEffectWithId -> effect id = " + n);
        TXAudioEffectManagerImpl.getCacheInstance().resumePlayMusic(n);
    }
    
    public void setSoundEffectListener(final com.tencent.liteav.audio.a a) {
        if (a == null) {
            TXCSoundEffectPlayer.mWeakSoundEffectListener = null;
        }
        else {
            TXCSoundEffectPlayer.mWeakSoundEffectListener = new WeakReference<com.tencent.liteav.audio.a>(a);
        }
    }
    
    @Override
    public void onPlayProgress(final int n, final long n2, final long n3) {
    }
    
    @Override
    public void onStart(final int n, final int n2) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "onStart -> effect id = " + n + ", errCode = " + n2);
        if (TXCSoundEffectPlayer.mWeakSoundEffectListener != null && TXCSoundEffectPlayer.mWeakSoundEffectListener.get() != null) {
            TXCSoundEffectPlayer.mWeakSoundEffectListener.get().onEffectPlayStart(n, n2);
        }
    }
    
    @Override
    public void onComplete(final int n, final int n2) {
        TXCLog.i("AudioCenter:TXCSoundEffectPlayer", "onMusicPlayFinish -> effect id = " + n);
        if (TXCSoundEffectPlayer.mWeakSoundEffectListener != null && TXCSoundEffectPlayer.mWeakSoundEffectListener.get() != null) {
            TXCSoundEffectPlayer.mWeakSoundEffectListener.get().onEffectPlayFinish(n);
        }
    }
    
    public void clearCache() {
        if (TXCCommonUtil.getAppContext() == null) {
            return;
        }
        try {
            final File file = new File(this.getEffectCachePath());
            if (file.exists() && file.isDirectory() && c.a(file) > 52428800) {
                final File[] listFiles = file.listFiles();
                for (int length = listFiles.length, i = 0; i < length; ++i) {
                    listFiles[i].delete();
                }
            }
        }
        catch (Exception ex) {
            TXCLog.w("AudioCenter:TXCSoundEffectPlayer", "clearCache error " + ex.toString());
        }
    }
    
    private String getEffectCachePath() {
        if (TXCCommonUtil.getAppContext() == null) {
            return "";
        }
        return TXCCommonUtil.getAppContext().getCacheDir() + File.separator + "liteav_effect";
    }
    
    private String checkIfAssetsFile(final String s) {
        if (TextUtils.isEmpty((CharSequence)s) || TXCCommonUtil.getAppContext() == null) {
            return s;
        }
        if (!s.startsWith("/assets/")) {
            return s;
        }
        String s2 = s;
        final String substring = s2.substring("/assets/".length());
        try {
            long length;
            try {
                length = TXCCommonUtil.getAppContext().getAssets().openFd(substring).getLength();
            }
            catch (Exception ex) {
                TXCLog.e("AudioCenter:TXCSoundEffectPlayer", "playAudioEffect openFd error " + ex.toString());
                length = 0L;
            }
            final String effectCachePath = this.getEffectCachePath();
            final File file = new File(effectCachePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            else if (file.isFile()) {
                file.delete();
                file.mkdirs();
            }
            final int lastIndex = substring.lastIndexOf(File.separatorChar);
            if (lastIndex != -1) {
                s2 = effectCachePath + File.separator + length + "_" + substring.substring(lastIndex + 1);
            }
            else {
                s2 = effectCachePath + File.separator + length + "_" + substring;
            }
            if (!c.a(s2)) {
                c.a(TXCCommonUtil.getAppContext(), substring, s2);
            }
        }
        catch (Exception ex2) {
            TXCLog.e("AudioCenter:TXCSoundEffectPlayer", "playAudioEffect error " + ex2.toString());
        }
        return s2;
    }
    
    static {
        f.f();
    }
    
    private static class a
    {
        private static final TXCSoundEffectPlayer a;
        
        static {
            a = new TXCSoundEffectPlayer();
        }
    }
}
