package com.tencent.liteav.beauty;

import android.graphics.*;

public interface TXBeautyManager
{
    void setPreprocessor(final e p0);
    
    void setBeautyStyle(final int p0);
    
    void setFilter(final Bitmap p0);
    
    void setFilterStrength(final float p0);
    
    void setGreenScreenFile(final String p0);
    
    void setBeautyLevel(final int p0);
    
    void setWhitenessLevel(final int p0);
    
    void enableSharpnessEnhancement(final boolean p0);
    
    void setRuddyLevel(final int p0);
    
    void setEyeScaleLevel(final int p0);
    
    void setFaceSlimLevel(final int p0);
    
    void setFaceVLevel(final int p0);
    
    void setChinLevel(final int p0);
    
    void setFaceShortLevel(final int p0);
    
    void setNoseSlimLevel(final int p0);
    
    void setEyeLightenLevel(final int p0);
    
    void setToothWhitenLevel(final int p0);
    
    void setWrinkleRemoveLevel(final int p0);
    
    void setPounchRemoveLevel(final int p0);
    
    void setSmileLinesRemoveLevel(final int p0);
    
    void setForeheadLevel(final int p0);
    
    void setEyeDistanceLevel(final int p0);
    
    void setEyeAngleLevel(final int p0);
    
    void setMouthShapeLevel(final int p0);
    
    void setNoseWingLevel(final int p0);
    
    void setNosePositionLevel(final int p0);
    
    void setLipsThicknessLevel(final int p0);
    
    void setFaceBeautyLevel(final int p0);
    
    void setMotionTmpl(final String p0);
    
    void setMotionMute(final boolean p0);
}
