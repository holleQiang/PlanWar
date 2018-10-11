package com.zq.planwar.appearance.impl;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.ext.AppearanceWrapper;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.IBounds;
import com.zq.planwar.ext.ILocation;
import com.zq.planwar.ext.ILocationUpdate;

/**
 * Created by zhangqiang on 2018/9/13.
 */
public class LocationAppearance implements Appearance, ILocation,
        ILocationUpdate, AppearanceWrapper, IBounds {

    @NonNull
    private Appearance wrapper;
    private float locationX;
    private float locationY;

    public LocationAppearance(float locationX, float locationY, @NonNull Appearance wrapper) {
        if (!(wrapper instanceof IBounds)) {
            throw new IllegalArgumentException("wrapper : " + wrapper.getClass() + "must be instance of IBounds");
        }
        this.wrapper = wrapper;
        this.locationX = locationX;
        this.locationY = locationY;
        updateBounds();
    }

    @Override
    public float getLocationX() {
        return getBounds().centerX();
    }

    @Override
    public float getLocationY() {
        return getBounds().centerY();
    }

    @Override
    public void setLocationX(float x) {
        locationX = x;
        updateBounds();
    }

    @Override
    public void setLocationY(float y) {
        locationY = y;
        updateBounds();
    }

    @Override
    public void offset(float dx, float dy) {
        locationX += dx;
        locationY += dy;
        updateBounds();
    }

    @NonNull
    @Override
    public Appearance getWrapper() {
        return wrapper;
    }

    @NonNull
    @Override
    public RectF getBounds() {
        return ((IBounds) wrapper).getBounds();
    }

    private void updateBounds() {

        RectF bounds = getBounds();
        bounds.offset(locationX - bounds.centerX(), locationY - bounds.centerY());
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext) {
        wrapper.draw(canvas, gameContext);
    }
}
