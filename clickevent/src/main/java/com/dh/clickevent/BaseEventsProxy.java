package com.dh.clickevent;


import android.content.Context;
import android.util.AttributeSet;

import com.dh.clickevent.adapter.EventsAdapter;

public class BaseEventsProxy {

    protected EventsAdapter mAdapter;
    protected AttributeSet mAttributeSet;
    protected Context mContext;

    public BaseEventsProxy(EventsAdapter adapter) {
        mAdapter = adapter;
    }

    public void replaceEffectAdapter(EventsAdapter adapter){
        mAdapter = adapter;
    }

    public EventsAdapter getAdapter() {
        return mAdapter;
    }

    public void initAttr(Context context, AttributeSet attrs){
        mContext = context;
        mAttributeSet = attrs;
        if(mAdapter != null){
            mAdapter.initAttr(context,attrs);
        }
    }

    public void measuredSize(int measuredWidth, int measuredHeight) {
        if(mAdapter != null){
            mAdapter.measuredSize(measuredWidth,measuredHeight);
        }
    }
}
