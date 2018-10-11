package com.zq.planwar.ext;

/**
 * 位置可更新的
 * Created by zhangqiang on 2018/9/20.
 */
public interface ILocationUpdate extends ILocation{

    void setLocationX(float x);

    void setLocationY(float y);

    void offset(float dx,float dy);
}
