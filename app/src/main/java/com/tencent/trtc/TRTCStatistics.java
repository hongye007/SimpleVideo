package com.tencent.trtc;

import java.util.*;

public class TRTCStatistics
{
    public int appCpu;
    public int systemCpu;
    public int rtt;
    public int upLoss;
    public int downLoss;
    public long sendBytes;
    public long receiveBytes;
    public ArrayList<TRTCLocalStatistics> localArray;
    public ArrayList<TRTCRemoteStatistics> remoteArray;
    
    public static class TRTCRemoteStatistics
    {
        public String userId;
        public int finalLoss;
        public int width;
        public int height;
        public int frameRate;
        public int videoBitrate;
        public int audioSampleRate;
        public int audioBitrate;
        public int jitterBufferDelay;
        public int streamType;
    }
    
    public static class TRTCLocalStatistics
    {
        public int width;
        public int height;
        public int frameRate;
        public int videoBitrate;
        public int audioSampleRate;
        public int audioBitrate;
        public int streamType;
    }
}
