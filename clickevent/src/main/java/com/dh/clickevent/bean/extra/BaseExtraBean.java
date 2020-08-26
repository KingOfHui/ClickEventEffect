package com.dh.clickevent.bean.extra;


import com.dh.clickevent.type.ClickEventsWholeType;

public class BaseExtraBean {

    protected ClickEventsWholeType mWholeType;

    public void setWholeType(ClickEventsWholeType wholeType) {
        mWholeType = wholeType;
    }

    public ClickEventsWholeType getWholeType() {
        return mWholeType;
    }
}
