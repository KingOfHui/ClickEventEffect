package com.dh.clickevent.util;


import com.dh.clickevent.ClickEventsManager;
import com.dh.clickevent.bean.extra.BaseExtraBean;
import com.dh.clickevent.type.ClickEventsExtraType;
import com.dh.clickevent.type.ClickEventsViewType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeUtils {

    public static List<String> getAllViewTypes(){
        ArrayList<String> viewTypes = new ArrayList<>();
        viewTypes.add(ClickEventsViewType.TextView);
        viewTypes.add(ClickEventsViewType.Button);
        viewTypes.add(ClickEventsViewType.ImageView);
        viewTypes.add(ClickEventsViewType.ImageButton);
        viewTypes.add(ClickEventsViewType.FrameLayout);
        viewTypes.add(ClickEventsViewType.LinearLayout);
        viewTypes.add(ClickEventsViewType.RelativeLayout);
        viewTypes.add(ClickEventsViewType.ConstraintLayout);
        return viewTypes;
    }

    public static boolean isContainsExtraType(ClickEventsExtraType extraType){
        HashMap<ClickEventsExtraType, BaseExtraBean> map = ClickEventsManager.getExtraTypeMap();
        return map.containsKey(extraType);
    }

}
