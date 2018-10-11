package com.zq.planwar.appearance.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.appearance.ext.AppearanceWrapper;
import com.zq.planwar.appearance.ext.Releaseable;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.ILocationUpdate;
import com.zq.planwar.utils.Logger;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public class RandomMoveAppearance implements Appearance, AppearanceWrapper, ILocationUpdate, Releaseable {

    private Appearance wrapper;
    private Animator animator;
    private boolean isAnimatorEnd;

    public RandomMoveAppearance(Appearance wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext) {
        if (isAnimatorEnd || animator == null) {
            animator = createAnimator(gameContext);
            animator.start();
            isAnimatorEnd = false;
        }
        Appearance wrapper = getWrapper();
        wrapper.draw(canvas, gameContext);
    }

    private float getRandomX(float currX, int width) {

        if (currX < width / 2) {

            return (float) ((width - currX) * Math.random()) + currX;
        } else {
            return currX - (float) (currX * Math.random());
        }
    }

    private float getRandomY(float currY, int height) {

        if (currY < height / 2) {

            return (float) ((height - currY) * Math.random()) + currY;
        } else {
            return currY - (float) (currY * Math.random());
        }
    }

    private long getRandomDuration() {
        return (long) (Math.random() * 2000 + 3000);
    }

    private Animator createAnimator(GameContext gameContext) {

        int width = gameContext.getGameViewWidth();
        int height = gameContext.getGameViewHeight()/2;
        float locationX = getLocationX();
        float locationY = getLocationY();

        AnimatorSet animatorSet = new AnimatorSet();

        float randomX = getRandomX(locationX, width);
        ValueAnimator xAnimator = ValueAnimator.ofFloat(locationX,randomX);
        xAnimator.addUpdateListener(xUpdateListener);

        float randomY = getRandomY(locationY, height);
        ValueAnimator yAnimator = ValueAnimator.ofFloat(locationY,randomY);
        yAnimator.addUpdateListener(yUpdateListener);

        animatorSet.playTogether(xAnimator, yAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimatorEnd = true;
            }
        });
        long randomDuration = getRandomDuration();
        animatorSet.setDuration(randomDuration);
        Logger.i(RandomMoveAppearance.class, "randomX ：" + randomX + "   randomY：" + randomY + "   randomDuration：" + randomDuration);
        return animatorSet;
    }

    private ValueAnimator.AnimatorUpdateListener xUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            setLocationX((Float) animation.getAnimatedValue());
        }
    };

    private ValueAnimator.AnimatorUpdateListener yUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            setLocationY((Float) animation.getAnimatedValue());
        }
    };

    @NonNull
    @Override
    public Appearance getWrapper() {
        return wrapper;
    }

    @Override
    public void setLocationX(float x) {
        AppearanceUtils.setLocationX(getWrapper(), x);
    }

    @Override
    public void setLocationY(float y) {
        AppearanceUtils.setLocationY(getWrapper(), y);
    }

    @Override
    public void offset(float dx, float dy) {
        AppearanceUtils.offset(getWrapper(), dx, dy);
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
    public void release() {
        if (animator != null) {
            animator.end();
            animator = null;
        }
    }
}
