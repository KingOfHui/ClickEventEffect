package com.dh.clickevent.util;

import android.os.Handler;

/**
 * @ClassName SingleHandler
 * @Description
 * @Author dinghui
 * @Date 2020/8/17 0017 14:58
 */
public class SingleHandler extends Handler {

    private static SingleHandler mInstance;

    private SingleHandler() {
    }

    public static SingleHandler getInstance() {
        if (mInstance == null) {
            synchronized (SingleHandler.class) {
                if (mInstance == null) {
                    mInstance = new SingleHandler();
                }
            }
        }

        return mInstance;
    }

}
