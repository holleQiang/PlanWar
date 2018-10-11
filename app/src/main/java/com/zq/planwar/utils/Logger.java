package com.zq.planwar.utils;

import android.util.Log;

/**
 * Created by zhangqiang on 2018/9/19.
 */
public class Logger {

    public static void i(Class clazz,String log){
        Log.i(clazz.getSimpleName(),"===========" + log);
    }

    public static void e(Class clazz,String log){
        Log.e(clazz.getSimpleName(),"===========" + log);
    }
}
