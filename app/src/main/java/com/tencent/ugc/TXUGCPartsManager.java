package com.tencent.ugc;

import android.content.*;
import java.util.concurrent.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import android.os.*;
import java.io.*;

public class TXUGCPartsManager
{
    private final String TAG = "TXUGCPartsManager";
    private final Context mContext;
    private CopyOnWriteArrayList<PartInfo> mPartsList;
    private int mDuration;
    private ArrayList<IPartsManagerListener> iPartsManagerObservers;
    
    public TXUGCPartsManager(final Context mContext) {
        this.mContext = mContext;
        this.mPartsList = new CopyOnWriteArrayList<PartInfo>();
        this.iPartsManagerObservers = new ArrayList<IPartsManagerListener>();
    }
    
    public synchronized void setPartsManagerObserver(final IPartsManagerListener partsManagerListener) {
        if (partsManagerListener != null && !this.iPartsManagerObservers.contains(partsManagerListener)) {
            this.iPartsManagerObservers.add(partsManagerListener);
        }
    }
    
    public synchronized void removePartsManagerObserver(final IPartsManagerListener partsManagerListener) {
        if (partsManagerListener != null) {
            this.iPartsManagerObservers.remove(partsManagerListener);
        }
    }
    
    public void addClipInfo(final PartInfo partInfo) {
        this.mPartsList.add(partInfo);
        this.mDuration += (int)partInfo.getDuration();
    }
    
    public void insertPart(final String path, final int n) {
        if (TextUtils.isEmpty((CharSequence)path)) {
            TXCLog.e("TXUGCPartsManager", "insertPart, videoPath is empty, ignore");
            return;
        }
        final long duration = TXVideoInfoReader.getInstance(this.mContext).getDuration(path);
        if (duration <= 0L) {
            TXCLog.e("TXUGCPartsManager", "insertPart, duration = " + duration);
            return;
        }
        this.mDuration += (int)duration;
        final PartInfo partInfo = new PartInfo();
        partInfo.setPath(path);
        partInfo.setDuration(duration);
        this.mPartsList.add(n, partInfo);
    }
    
    public int getDuration() {
        return this.mDuration;
    }
    
    public List<String> getPartsPathList() {
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<PartInfo> iterator = this.mPartsList.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getPath());
        }
        return list;
    }
    
    public void deleteLastPart() {
        if (this.mPartsList.size() != 0) {
            final PartInfo partInfo = this.mPartsList.remove(this.mPartsList.size() - 1);
            this.mDuration -= (int)partInfo.getDuration();
            this.deleteFile(partInfo.getPath());
            this.callbackDeleteLastPart();
        }
    }
    
    public void deletePart(final int n) {
        if (n <= 0) {
            return;
        }
        if (this.mPartsList.size() == 0) {
            return;
        }
        final PartInfo partInfo = this.mPartsList.remove(n - 1);
        this.mDuration -= (int)partInfo.getDuration();
        this.deleteFile(partInfo.getPath());
    }
    
    public void deleteAllParts() {
        final Iterator<PartInfo> iterator = this.mPartsList.iterator();
        while (iterator.hasNext()) {
            this.deleteFile(iterator.next().getPath());
        }
        this.mPartsList.clear();
        this.mDuration = 0;
        this.callbackDeleteAllParts();
    }
    
    private void callbackDeleteLastPart() {
        synchronized (this) {
            final Iterator<IPartsManagerListener> iterator = this.iPartsManagerObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().onDeleteLastPart();
            }
        }
    }
    
    private void callbackDeleteAllParts() {
        synchronized (this) {
            final Iterator<IPartsManagerListener> iterator = this.iPartsManagerObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().onDeleteAllParts();
            }
        }
    }
    
    private void deleteFile(final String s) {
        new AsyncTask() {
            protected Object doInBackground(final Object[] array) {
                final File file = new File(s);
                if (file.exists()) {
                    file.delete();
                }
                return null;
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Object[0]);
    }
    
    public interface IPartsManagerListener
    {
        void onDeleteLastPart();
        
        void onDeleteAllParts();
    }
}
