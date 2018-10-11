package com.zq.planwar;

import android.app.Application;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class PlanWarApplication extends Application {
    static PlanWarApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static PlanWarApplication get() {
        return application;
    }
}
