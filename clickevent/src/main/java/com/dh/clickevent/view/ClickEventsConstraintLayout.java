package com.dh.clickevent.view;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dh.clickevent.BaseEventsProxy;
import com.dh.clickevent.ClickEventsManager;
import com.dh.clickevent.adapter.EventStateAdapter;

/**
 * @ClassName ClickEventsConstraintLayout
 * @Description
 * @Author dinghui
 * @Date 2020/8/18 0018 10:56
 */
public class ClickEventsConstraintLayout extends ConstraintLayout {

    private BaseEventsProxy mEventsProxy;

    public ClickEventsConstraintLayout(Context context) {
        this(context,null);
    }

    public ClickEventsConstraintLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, new BaseEventsProxy(new EventStateAdapter(ClickEventsManager.getColorBean())));
    }

    public ClickEventsConstraintLayout(Context context, @Nullable AttributeSet attrs, BaseEventsProxy eventsProxy) {
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
    public void dispatchDraw(Canvas canvas) {
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


    public View.OnClickListener mOnClickListener;
    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        mOnClickListener = l;
    }

    public View.OnLongClickListener mOnLongClickListener;
    @Override
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
        if(mOnLongClickListener != null){
            mEventsProxy.getAdapter().createLongClick(this,mOnLongClickListener);
        }
    }


}
