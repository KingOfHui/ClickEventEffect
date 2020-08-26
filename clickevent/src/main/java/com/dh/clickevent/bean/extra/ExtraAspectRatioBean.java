package com.dh.clickevent.bean.extra;


import com.dh.clickevent.type.ClickEventsWholeType;

public class ExtraAspectRatioBean extends BaseExtraBean {

    private float mWidth;
    private float mHeight;

    public ExtraAspectRatioBean(ClickEventsWholeType wholeType, float width, float height) {
        mWholeType = wholeType;
        mWidth = width;
        mHeight = height;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }
}
