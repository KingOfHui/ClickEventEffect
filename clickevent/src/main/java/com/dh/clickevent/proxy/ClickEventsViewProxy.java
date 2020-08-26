package com.dh.clickevent.proxy;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.dh.clickevent.impl.ClickEventsViewSubject;

public class ClickEventsViewProxy implements ClickEventsViewSubject {

    private ClickEventsViewSubject mClickEventsViewSubject;


    public ClickEventsViewProxy(ClickEventsViewSubject clickEventsViewSubject) {
        mClickEventsViewSubject = clickEventsViewSubject;
    }

    @Override
    public View createView(View parent, String name, Context context, AttributeSet attrs) {
        return mClickEventsViewSubject.createView(parent,name,context,attrs);
    }
}
