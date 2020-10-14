package com.tencent.ijk.media.player;

import android.graphics.*;

public final class IjkTimedText
{
    private Rect mTextBounds;
    private String mTextChars;
    
    public IjkTimedText(final Rect mTextBounds, final String mTextChars) {
        this.mTextBounds = null;
        this.mTextChars = null;
        this.mTextBounds = mTextBounds;
        this.mTextChars = mTextChars;
    }
    
    public Rect getBounds() {
        return this.mTextBounds;
    }
    
    public String getText() {
        return this.mTextChars;
    }
}
