package com.dh.clickevent.view;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.dh.clickevent.BaseEventsProxy;
import com.dh.clickevent.ClickEventsManager;
import com.dh.clickevent.adapter.EventStateAdapter;

/**
 * @ClassName ClickEventsRelativeLayout
 * @Description
 * @Author dinghui
 * @Date 2020/8/18 0018 10:56
 */
public class ClickEventsRelativeLayout extends RelativeLayout {

    private BaseEventsProxy mEventsProxy;

    public ClickEventsRelativeLayout(Context context) {
        this(context,null);
    }

    public ClickEventsRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, new BaseEventsProxy(new EventStateAdapter(ClickEventsManager.getColorBean())));
    }

    public ClickEventsRelativeLayout(Context context, @Nullable AttributeSet attrs, BaseEventsProxy eventsProxy) {
        super(context, attrs,0);
        setWillNotDraw(false);
        mEventsProxy = eventsProxy;
        mEventsProxy.initAttr(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mEventsProxy.measuredSize(getMeasuredWidth(),getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mEventsProxy.getAdapter().runAnimator(this,canvas);
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mEventsProxy.getAdapter().dispatchDraw(this,canvas);
        }
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        mEventsProxy.getAdapter().drawForeground(this,canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mOnClickListener == null && mOnLongClickListener == null || !isEnabled()){
            return super.onTouchEvent(event);
        }
        return mEventsProxy.getAdapter().onTouch(this,event,mOnClickListener,mOnLongClickListener);
    }


    public OnClickListener mOnClickListener;
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    public OnLongClickListener mOnLongClickListener;
    @Override
    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
        if(mOnLongClickListener != null){
            mEventsProxy.getAdapter().createLongClick(this,mOnLongClickListener);
        }
    }

}
