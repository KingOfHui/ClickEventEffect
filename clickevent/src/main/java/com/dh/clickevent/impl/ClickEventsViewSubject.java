package com.dh.clickevent.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public interface ClickEventsViewSubject {

    View createView(View parent, String name, Context context, AttributeSet attrs);

}
