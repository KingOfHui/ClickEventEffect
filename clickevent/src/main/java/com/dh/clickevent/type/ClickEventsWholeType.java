package com.dh.clickevent.type;

public enum ClickEventsWholeType implements ClickEventsType {
    NONE,           //不自动为控件添加效果
    SCALE,          //为系统控件添加缩放效果
    RIPPLE,         //为系统控件添加点击水波纹效果
    STATE,          //为系统控件添加普通点击响应效果
}
