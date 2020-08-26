package com.dh.clickevent.adapter;


import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.clickevent.R;
import com.dh.clickevent.bean.ColorBean;
import com.dh.clickevent.util.ViewStateUtil;

public class EventStateAdapter extends EventsAdapter {
    private final int TRANSPARENT_COLOR = 0x00000000;
    private int mPressedColor;
    private int mNormalColor;
    private int mCurrentColor;

    private float mRadius;
    private ColorBean mColorBean;

    private float mColorValue;
    private Paint mPaint;
    private RectF mRect;
    private int mOriginTextColor;
    private SparseIntArray mChildColorMap = new SparseIntArray();// 存储ViewGroup子控件中所有TextView的原本色值


    public EventStateAdapter(ColorBean colorBean) {
        mColorBean = colorBean;
    }

    @Override
    public void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClickEventsView);
        mPressedColor = ta.getColor(R.styleable.ClickEventsView_touch_effects_pressed_color, mColorBean != null ? mColorBean.getPressedColor() : 0);
        mNormalColor = ta.getColor(R.styleable.ClickEventsView_touch_effects_normal_color, mColorBean != null ? mColorBean.getNormalColor() : 0);
        if (mNormalColor == 0) {
            mNormalColor = mPressedColor;
        }
        mRadius = ta.getDimension(R.styleable.ClickEventsView_touch_effects_radius, 0);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        ta.recycle();
    }

    @Override
    public void measuredSize(int width, int height) {
        super.measuredSize(width, height);
        mRect = new RectF(0, 0, width, height);

    }

    @Override
    public void runAnimator(View view, Canvas canvas) {

    }

    @Override
    public void dispatchDraw(View view, Canvas canvas) {
        mPaint.setColor(mCurrentColor);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawRoundRect(mRect,mRadius,mRadius,mPaint);
    }

    @Override
    public void drawForeground(View view, Canvas canvas) {
        dispatchDraw(view, canvas);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            if (childCount > 0) {
                for (int i = 0; i < childCount; i++) {
                    View childView = ((ViewGroup) view).getChildAt(i);
                    childView.setDuplicateParentStateEnabled(true);
                    boolean b = touchChildView(childView, event, onClickListener);
                }
            }
        }
        return touchView(view, event, onClickListener);
    }

    protected boolean touchChildView(View view, MotionEvent event, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (view instanceof TextView) {
                    mChildColorMap.put(System.identityHashCode(view), ((TextView) view).getCurrentTextColor());
                }
            case MotionEvent.ACTION_MOVE:
                if (view instanceof ImageView) {
                    ViewStateUtil.changeLight(((ImageView) view), -50);
                } else if (view instanceof TextView) {//Button也是继承TextView，所以不需要单独处理button
                    Drawable[] compoundDrawables = ((TextView) view).getCompoundDrawables();
                    if (compoundDrawables.length > 0) {
                        for (Drawable drawable : compoundDrawables) {
                            if (drawable != null) {
                                ViewStateUtil.changeLight(drawable, -50);
                            }
                        }
                    }
                    ((TextView) view).setTextColor(ViewStateUtil.getTextViewDarkColor(mChildColorMap.get(System.identityHashCode(view))));
                }
                (view).setPressed(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (view instanceof ImageView) {
                    ViewStateUtil.changeLight(((ImageView) view), 0);
                } else if (view instanceof TextView) {
                    Drawable[] compoundDrawables = ((TextView) view).getCompoundDrawables();
                    if (compoundDrawables.length > 0) {
                        for (Drawable drawable : compoundDrawables) {
                            if (drawable != null) {
                                ViewStateUtil.changeLight(drawable, 0);
                            }
                        }
                    }
                    ((TextView) view).setTextColor(mChildColorMap.get(System.identityHashCode(view)));
                }
                (view).setPressed(false);
                break;
        }
        return false;
    }

    @Override
    protected boolean touchView(View view, MotionEvent event, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (view instanceof TextView) {//Button也是继承TextView，所以不需要单独处理button
                    mOriginTextColor = ((TextView) view).getCurrentTextColor();
                }
            case MotionEvent.ACTION_MOVE:
                if (view instanceof ImageView) {
                    ViewStateUtil.changeLight(((ImageView) view), -50);
                } else if (view instanceof TextView) {
                    ((TextView) view).setTextColor(ViewStateUtil.getTextViewDarkColor(mOriginTextColor));
                }
//                if (backgroundDrawable instanceof StateListDrawable) {
                (view).setPressed(true);
//                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (view instanceof ImageView) {
                    ViewStateUtil.changeLight(((ImageView) view), 0);
                } else if (view instanceof TextView) {
                    ((TextView) view).setTextColor(mOriginTextColor);
                }
//                if (backgroundDrawable instanceof StateListDrawable) {
                (view).setPressed(false);
//                }
                break;
        }
        return super.touchView(view, event, onClickListener);
    }

    @Override
    protected Animator createEngineAnimator(View view) {
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();//渐变色计算类
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(850);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mColorValue = (float) animation.getAnimatedValue();
                mCurrentColor = (int) (argbEvaluator.evaluate(mColorValue, TRANSPARENT_COLOR, mPressedColor));
                view.invalidate();
            }
        });
        return valueAnimator;
    }

    @Override
    protected Animator createExtinctAnimator(View view) {
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();//渐变色计算类
        ValueAnimator valueAnimator;
        if (mColorValue < 0.5f) {
            valueAnimator = ValueAnimator.ofFloat(mColorValue, 0.8f, 0.6f, 0.4f, 0.2f, 0f);
        } else {
            valueAnimator = ValueAnimator.ofFloat(mColorValue, 0f);
        }
        valueAnimator.setDuration(450);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mColorValue = (float) animation.getAnimatedValue();
                mCurrentColor = (int) (argbEvaluator.evaluate(mColorValue, TRANSPARENT_COLOR, mNormalColor));
                view.invalidate();
            }
        });
        return valueAnimator;
    }
}