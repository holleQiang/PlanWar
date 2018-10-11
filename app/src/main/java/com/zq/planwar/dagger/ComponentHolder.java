package com.zq.planwar.dagger;

import com.zq.planwar.dagger.component.AppComponent;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class ComponentHolder {

    private static AppComponent component;

    private ComponentHolder() {
    }

    public static synchronized void init(AppComponent appComponent) {
        if (component == null) {
            component = appComponent;
        }
    }

    public static AppComponent get() {
        return component;
    }
}
