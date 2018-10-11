package com.zq.planwar.appearance.impl;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.appearance.ext.AppearanceWrapper;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.ILocationUpdate;

/**
 * Created by zhangqiang on 2018/9/13.
 */
public class MoveAppearanceImpl implements ILocationUpdate,Appearance,AppearanceWrapper{

    //角度
    private int degree;
    //速度
    private int velocity;
    @NonNull
    private Appearance wrapper;
    private long lastTime;

    public MoveAppearanceImpl(int degree, int velocity, @NonNull Appearance wrapper) {
        this.degree = degree;
        this.velocity = velocity;
        this.wrapper = wrapper;
        AppearanceUtils.checkIBounds(wrapper);
    }

    @Override
    public float getLocationX() {
        return AppearanceUtils.getLocationX(getWrapper());
    }

    @Override
    public float getLocationY() {
        return AppearanceUtils.getLocationY(getWrapper());
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext) {

        long timeMillis = gameContext.getPastTime();
        if (lastTime <= 0) {
            lastTime = timeMillis;
        }
        long pastTime = timeMillis - lastTime;
        float distance = (float) pastTime / 1000 * velocity;
        int degree = this.degree % 360;
        double radians = Math.toRadians(degree);
        float dx = (float) (Math.cos(radians) * distance);
        float dy = -(float) (Math.sin(radians) * distance);

        offset(dx, dy);
        wrapper.draw(canvas, gameContext);

        lastTime = timeMillis;
    }

    @NonNull
    @Override
    public Appearance getWrapper() {
        return wrapper;
    }


    public void setDegree(int degree) {
        this.degree = degree;
    }

    @Override
    public void setLocationX(float x) {
        AppearanceUtils.setLocationX(getWrapper(),x);
    }

    @Override
    public void setLocationY(float y) {
        AppearanceUtils.setLocationY(getWrapper(),y);
    }

    @Override
    public void offset(float dx, float dy) {
        AppearanceUtils.offset(getWrapper(),dx,dy);
    }
}
