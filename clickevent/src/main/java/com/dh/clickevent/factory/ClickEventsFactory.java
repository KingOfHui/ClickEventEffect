package com.dh.clickevent.factory;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;

import com.dh.clickevent.type.ClickEventsWholeType;

public class ClickEventsFactory {
    public static void initClickEventEffects(@NonNull Activity activity){
        LayoutInflaterCompat.setFactory2(activity.getLayoutInflater(),new ClickEventsInflaterFactory());
    }

    public static void initClickEventEffects(@NonNull Activity activity, ClickEventsWholeType clickEventsWholeType){
        LayoutInflaterCompat.setFactory2(activity.getLayoutInflater(),new ClickEventsInflaterFactory(clickEventsWholeType));
    }
}
