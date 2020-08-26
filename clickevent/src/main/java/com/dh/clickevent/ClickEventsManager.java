package com.dh.clickevent;


import android.support.annotation.NonNull;

import com.dh.clickevent.bean.ColorBean;
import com.dh.clickevent.bean.ScaleBean;
import com.dh.clickevent.bean.extra.BaseExtraBean;
import com.dh.clickevent.proxy.ClickEventsCreateViewSubject;
import com.dh.clickevent.proxy.ClickEventsViewProxy;
import com.dh.clickevent.type.ClickEventsExtraType;
import com.dh.clickevent.type.ClickEventsViewType;
import com.dh.clickevent.type.ClickEventsWholeType;
import com.dh.clickevent.util.TypeUtils;

import java.util.HashMap;

/**
 * @ClassName ClickEventsManager
 * @Description
 * @Author dinghui
 * @Date 2020/8/17 0017 13:52
 */
public class ClickEventsManager {

    public static final int NONE_TYPE = -1;
    public static final int SCALE_TYPE = 0;
    public static final int RIPPLE_TYPE = 1;
    public static final int STATE_TYPE = 2;
    public static final int SHAKE_TYPE = 3;
    public static final int RIPPLE_NORMAL_TYPE = 4;

    private static volatile ClickEventsManager mInstance;
    private static ClickEventsWholeType mClickEventsWholeType;
    private static HashMap<String, ClickEventsWholeType> mViewTypes;
    private static ClickEventsViewProxy mViewSubject;
    private static ColorBean mColorBean;
    private static ScaleBean mScaleBean;
    private static ClickEventsWholeType mListWholeType;
    private static HashMap<ClickEventsExtraType, BaseExtraBean> mExtraTypeMap = new HashMap<>();


    private ClickEventsManager() {
        this.mViewTypes = new HashMap<>();
        mViewSubject = new ClickEventsViewProxy(new ClickEventsCreateViewSubject(mClickEventsWholeType,mColorBean));
    }

    private static ClickEventsManager getInstance(){
        if(mInstance == null){
            synchronized (ClickEventsManager.class){
                if(mInstance == null){
                    mInstance = new ClickEventsManager();
                }
            }
        }
        return mInstance;
    }

    public static ClickEventsManager buildDefault() {
        return build(ClickEventsWholeType.STATE)
                .addViewType(ClickEventsViewType.ALL)
                .setListWholeType(ClickEventsWholeType.STATE);
    }
    /**
     * 创建Manager，初始化
     * @param clickEventsWholeType 全局使用什么模式{@link ClickEventsWholeType}
     * @return
     */
    public static ClickEventsManager build(@NonNull ClickEventsWholeType clickEventsWholeType){
        mClickEventsWholeType = clickEventsWholeType;
        if(mColorBean == null){
            mColorBean = new ColorBean(0x3D000000,0x3D000000);
        }
        return getInstance();
    }

    /**
     * 创建Manager，初始化
     * @param clickEventsWholeType 全局使用什么模式{@link ClickEventsWholeType}
     * @return
     */
    public static ClickEventsManager build(@NonNull ClickEventsWholeType clickEventsWholeType, int normalColor, int pressedColor){
        mClickEventsWholeType = clickEventsWholeType;
        if(mColorBean == null){
            mColorBean = new ColorBean(normalColor,pressedColor);
        }else{
            mColorBean.setNormalColor(normalColor);
            mColorBean.setPressedColor(pressedColor);
        }
        return getInstance();
    }

    /**
     * 添加支持的视图的类型
     * @param viewTypes 支持的类型 {@link ClickEventsViewType}
     * @return
     */
    public ClickEventsManager addViewTypes(@ClickEventsViewType String... viewTypes){
        addViewTypes(mClickEventsWholeType,viewTypes);
        return mInstance;
    }

    /**
     * 添加支持的视图的类型
     * @param wholeType 支持的类型中适用于什么模式（如TextView使用EFFECTS,Button使用RIPPLE）{@link ClickEventsWholeType}
     * @param viewTypes 支持的类型 {@link ClickEventsViewType}
     * @return
     */
    public ClickEventsManager addViewTypes(ClickEventsWholeType wholeType, @ClickEventsViewType String... viewTypes){
        for(String viewType:viewTypes){
            addViewType(wholeType,viewType);
        }
        return mInstance;
    }

    /**
     * 添加支持的视图的类型
     * @param viewType 支持的类型 {@link ClickEventsViewType}
     * @return
     */
    public ClickEventsManager addViewType(@ClickEventsViewType String viewType){
        addViewType(mClickEventsWholeType,viewType);
        return mInstance;
    }

    /**
     * 添加支持的视图的类型
     * @param wholeType 支持的类型中适用于什么模式（如TextView使用EFFECTS,Button使用RIPPLE）{@link ClickEventsWholeType}
     * @param viewType 支持的类型 {@link ClickEventsViewType}
     * @return
     */
    public ClickEventsManager addViewType(ClickEventsWholeType wholeType, @ClickEventsViewType String viewType){
        if(viewType.equals(ClickEventsViewType.ALL)){
            mViewTypes.clear();
            for(String viewType1: TypeUtils.getAllViewTypes()){
                mViewTypes.put(viewType1,wholeType);
            }
        }else{
            mViewTypes.put(viewType,wholeType);
        }
        return mInstance;
    }

    public ClickEventsManager setListWholeType(ClickEventsWholeType listWholeType) {
        mListWholeType = listWholeType;
        return mInstance;
    }


    public static HashMap<ClickEventsExtraType, BaseExtraBean> getExtraTypeMap() {
        return mExtraTypeMap;
    }

    public static ClickEventsWholeType getListWholeType() {
        return mListWholeType;
    }

    /**
     * 获取所有设置的类型
     */
    public static HashMap<String, ClickEventsWholeType> getViewTypes() {
        if(mViewTypes == null){
            throw new RuntimeException("please initialize in Application");
        }
        return mViewTypes;
    }

    public static ClickEventsViewProxy getViewSubject() {
        return mViewSubject;
    }

    public static ColorBean getColorBean() {
        return mColorBean;
    }

    public static ScaleBean getScaleBean() {
        return mScaleBean;
    }
}
