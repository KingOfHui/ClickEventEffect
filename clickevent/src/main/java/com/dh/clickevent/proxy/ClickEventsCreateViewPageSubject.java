package com.dh.clickevent.proxy;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.dh.clickevent.BaseEventsProxy;
import com.dh.clickevent.ClickEventsManager;
import com.dh.clickevent.R;
import com.dh.clickevent.adapter.EventStateAdapter;
import com.dh.clickevent.adapter.EventsAdapter;
import com.dh.clickevent.bean.ColorBean;
import com.dh.clickevent.bean.ScaleBean;
import com.dh.clickevent.impl.ClickEventsViewSubject;
import com.dh.clickevent.type.ClickEventsSingleType;
import com.dh.clickevent.type.ClickEventsType;
import com.dh.clickevent.type.ClickEventsWholeType;
import com.dh.clickevent.view.ClickEventsButton;
import com.dh.clickevent.view.ClickEventsConstraintLayout;
import com.dh.clickevent.view.ClickEventsImageButton;
import com.dh.clickevent.view.ClickEventsImageView;
import com.dh.clickevent.view.ClickEventsLinearLayout;
import com.dh.clickevent.view.ClickEventsRelativeLayout;
import com.dh.clickevent.view.ClickEventsTextView;

/**
 * 页面中单独设置Type的Subject，全局设置及列表设置无效
 */
public class ClickEventsCreateViewPageSubject implements ClickEventsViewSubject {


    private ClickEventsWholeType mClickEventsWholeType;
    private ScaleBean mScaleBean;
    private ColorBean mColorBean;

    public ClickEventsCreateViewPageSubject(ClickEventsWholeType ClickEventsWholeType) {
        mClickEventsWholeType = ClickEventsWholeType;
    }

    public ClickEventsCreateViewPageSubject(ClickEventsWholeType ClickEventsWholeType, ScaleBean scaleBean) {
        mClickEventsWholeType = ClickEventsWholeType;
        mScaleBean = scaleBean;
    }

    public ClickEventsCreateViewPageSubject(ClickEventsWholeType ClickEventsWholeType, ColorBean colorBean) {
        mClickEventsWholeType = ClickEventsWholeType;
        mColorBean = colorBean;
    }

    @Override
    public View createView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.contains(".") && !name.startsWith("android")) {//不处理自定义控件
            return view;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClickEventsView);
        int type = ta.getInt(R.styleable.ClickEventsView_touch_effects_type, ClickEventsManager.NONE_TYPE);
        boolean isForbid = ta.getBoolean(R.styleable.ClickEventsView_touch_effects_forbid, false);
        ta.recycle();
        if (isForbid) {//如果设置了禁用,则优先级最高，不处理
            return view;
        }
        if (type != ClickEventsManager.NONE_TYPE) {//只要在View中设置了type，除非是none，否则不论全局何种模式，都优先根据type
            if (type == ClickEventsManager.RIPPLE_TYPE) {
                view = parseTypeView(ClickEventsWholeType.RIPPLE, parent, name, context, attrs);
            }else if (type == ClickEventsManager.STATE_TYPE) {
                view = parseTypeView(ClickEventsWholeType.STATE, parent, name, context, attrs);
            } else if (type == ClickEventsManager.SHAKE_TYPE) {
                view = parseTypeView(ClickEventsSingleType.SHAKE, parent, name, context, attrs);
            } else {
                view = parseTypeView(ClickEventsWholeType.SCALE, parent, name, context, attrs);
            }
        } else {
            view = parseTypeView(mClickEventsWholeType, parent, name, context, attrs);
        }
        return view;
    }

    private View parseTypeView(ClickEventsType type, View parent, String name, Context context, AttributeSet attrs) {
        View view = parseEffectsView(parent, type, name, context, attrs);
        return view;
    }

    /**
     * 解析缩放模式下的View
     *
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View parseEffectsView(View parent, ClickEventsType wholeType, String name, Context context, AttributeSet attrs) {
        return findSystemViewChange(name, wholeType, context, attrs);
    }

    /**
     * 如果是支持的View，则转换成TouchShake系列的View
     *
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View findSystemViewChange(String name, ClickEventsType wholeType, Context context, AttributeSet attrs) {
        View view = null;
        EventsAdapter adapter = null;
        if (wholeType != null) {
            adapter = getAdapter(wholeType);
        }
        if (adapter == null) {
            return null;
        }
        BaseEventsProxy baseEffectsProxy;
//        if(TypeUtils.isContainsExtraType(ClickEventsExtraType.AspectRatio)){
//            baseEffectsProxy = new AspectRatioEffectsProxy(adapter);
//        }else{
        baseEffectsProxy = new BaseEventsProxy(adapter);
//        }

        view = checkViewName(name, context, attrs, baseEffectsProxy);
        return view;
    }

    private View checkViewName(String name, Context context, AttributeSet attrs, BaseEventsProxy effectsProxy) {
        if (name.startsWith("androidx.")) {
            return checkExtendView(name, context, attrs, effectsProxy);
        } else {
            return checkView(name, context, attrs, effectsProxy);
        }
    }

    private View checkView(String name, Context context, AttributeSet attrs, BaseEventsProxy eventsProxy) {
        View view = null;
        switch (name) {
            case "TextView":
                view = new ClickEventsTextView(context, attrs, eventsProxy);
                break;
            case "Button":
                view = new ClickEventsButton(context, attrs, eventsProxy);
                break;
            case "ImageView":
                view = new ClickEventsImageView(context, attrs, eventsProxy);
                break;
            case "ImageButton":
                view = new ClickEventsImageButton(context, attrs, eventsProxy);
                break;
        }
        if (name.contains("ClickEventsLinearLayout")) {
            return new ClickEventsLinearLayout(context, attrs,eventsProxy);
        }
        if (name.contains("ClickEventsRelativeLayout")) {
            return new ClickEventsRelativeLayout(context, attrs, eventsProxy);
        }
        if (name.contains("ClickEventsConstraintLayout")) {
            return new ClickEventsConstraintLayout(context, attrs, eventsProxy);
        }
        return view;
    }

    private View checkExtendView(String name, Context context, AttributeSet attrs, BaseEventsProxy eventsProxy) {
        if (name.contains("TextView")) {
            return new ClickEventsTextView(context, attrs, eventsProxy);
        }
        if (name.contains("Button")) {
            return new ClickEventsButton(context, attrs, eventsProxy);
        }
        if (name.contains("ImageView")) {
            return new ClickEventsImageView(context, attrs, eventsProxy);
        }
        if (name.contains("ImageButton")) {
            return new ClickEventsImageButton(context, attrs, eventsProxy);
        }
        if (name.contains("ClickEventsLinearLayout")) {
            return new ClickEventsLinearLayout(context, attrs,eventsProxy);
        }
        if (name.contains("ClickEventsRelativeLayout")) {
            return new ClickEventsRelativeLayout(context, attrs, eventsProxy);
        }
        if (name.contains("ClickEventsConstraintLayout")) {
            return new ClickEventsConstraintLayout(context, attrs, eventsProxy);
        }
        return null;
    }

    private EventsAdapter getAdapter(ClickEventsType type) {
        // TODO: 2020/8/18 0018 此处可以根据不同的设置效果类型配置不同的Adapter
        if (type instanceof ClickEventsSingleType) {
            ClickEventsSingleType singleType = (ClickEventsSingleType) type;
            switch (singleType) {
                case SHAKE:
            }
        } else {
            ClickEventsWholeType wholeType = (ClickEventsWholeType) type;
            switch (wholeType) {
                case SCALE:
                case RIPPLE:
                case STATE:
                    return new EventStateAdapter(ClickEventsManager.getColorBean());
            }
        }
        return new EventStateAdapter(ClickEventsManager.getColorBean());
    }
}
