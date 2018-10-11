package com.zq.planwar.appearance.impl;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.appearance.ext.AppearanceWrapper;
import com.zq.planwar.appearance.ext.Releaseable;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.IBounds;
import com.zq.planwar.ext.ILocationUpdate;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public class LeftRightMoveAppearance implements Appearance,ILocationUpdate,AppearanceWrapper,Releaseable,IBounds{

    private Animator animator;
    private Appearance wrapper;

    public LeftRightMoveAppearance(Appearance wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext) {
        if(animator == null){
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, gameContext.getGameViewWidth());
            valueAnimator.setDuration(5000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setLocationX((Float) animation.getAnimatedValue());
                }
            });
            valueAnimator.setRepeatCount(-1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.start();
            animator = valueAnimator;
        }
        getWrapper().draw(canvas, gameContext);
    }

    @NonNull
    @Override
    public Appearance getWrapper() {
        return wrapper;
    }

    @Override
    public void release() {
        animator.end();
        animator = null;
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

    @Override
    public float getLocationX() {
        return AppearanceUtils.getLocationX(getWrapper());
    }

    @Override
    public float getLocationY() {
        return AppearanceUtils.getLocationY(getWrapper());
    }

    @NonNull
    @Override
    public RectF getBounds() {
        return AppearanceUtils.getBounds(getWrapper());
    }
}
