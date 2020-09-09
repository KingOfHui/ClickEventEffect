package com.dh.clickevent.view;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.dh.clickevent.BaseEventsProxy;

/**
 * @ClassName ClickEventsImageView
 * @Description
 * @Author dinghui
 * @Date 2020/8/17 0017 14:50
 */
public class ClickEventsImageView extends AppCompatImageView {

    private BaseEventsProxy mEffectsProxy;

    public ClickEventsImageView(Context context) {
        this(context,null);
    }

    public ClickEventsImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickEventsImageView(Context context, @Nullable AttributeSet attrs, BaseEventsProxy effectsProxy) {
        super(context, attrs,0);
//        ImageViewStateUtil.setImageViewState(this);
        mEffectsProxy = effectsProxy;
        mEffectsProxy.initAttr(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mEffectsProxy.measuredSize(getMeasuredWidth(),getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mEffectsProxy.getAdapter().runAnimator(this,canvas);
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mEffectsProxy.getAdapter().dispatchDraw(this,canvas);
        }
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        mEffectsProxy.getAdapter().drawForeground(this,canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mOnClickListener == null && mOnLongClickListener == null || !isEnabled()){
            return super.onTouchEvent(event);
        }
        return mEffectsProxy.getAdapter().onTouch(this,event,mOnClickListener,mOnLongClickListener);
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
            mEffectsProxy.getAdapter().createLongClick(this,mOnLongClickListener);
        }
    }

}

