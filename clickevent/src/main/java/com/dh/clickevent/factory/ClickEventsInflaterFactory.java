package com.dh.clickevent.factory;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.dh.clickevent.ClickEventsManager;
import com.dh.clickevent.proxy.ClickEventsCreateViewPageSubject;
import com.dh.clickevent.proxy.ClickEventsViewProxy;
import com.dh.clickevent.type.ClickEventsWholeType;

public class ClickEventsInflaterFactory implements LayoutInflater.Factory2 {

    private ClickEventsWholeType mClickEventsWholeType;
    private ClickEventsViewProxy mClickEventsViewProxy;

    public ClickEventsInflaterFactory() {
    }

    public ClickEventsInflaterFactory(ClickEventsWholeType clickEventsWholeType) {
        mClickEventsWholeType = clickEventsWholeType;
        mClickEventsViewProxy = new ClickEventsViewProxy(new ClickEventsCreateViewPageSubject(mClickEventsWholeType));
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if(mClickEventsViewProxy != null){
            return mClickEventsViewProxy.createView(parent,name,context,attrs);
        }
        return ClickEventsManager.getViewSubject().createView(parent,name,context,attrs);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if(mClickEventsViewProxy != null){
            return mClickEventsViewProxy.createView(null,name,context,attrs);
        }
        return ClickEventsManager.getViewSubject().createView(null,name,context,attrs);
    }
}
