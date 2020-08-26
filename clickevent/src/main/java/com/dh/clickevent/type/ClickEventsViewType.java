package com.dh.clickevent.type;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({ClickEventsViewType.ALL,
        ClickEventsViewType.TextView,
        ClickEventsViewType.Button,
        ClickEventsViewType.ImageView,
        ClickEventsViewType.ImageButton,
        ClickEventsViewType.FrameLayout,
        ClickEventsViewType.LinearLayout,
        ClickEventsViewType.RelativeLayout,
        ClickEventsViewType.ConstraintLayout})
public @interface ClickEventsViewType {

    String ALL = "ALL";
    String TextView = "TextView";
    String Button = "Button";
    String ImageView = "ImageView";
    String ImageButton = "ImageButton";
    String FrameLayout = "FrameLayout";
    String LinearLayout = "LinearLayout";
    String RelativeLayout = "RelativeLayout";
    String ConstraintLayout = "android.support.constraint.ConstraintLayout";

}
