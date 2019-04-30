package com.zq.planwar.appearance.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.utils.Logger;

public class AppearanceAnimations {

    public static ObjectAnimator leftToRight(Appearance appearance) {

        return ObjectAnimator.ofPropertyValuesHolder(appearance,
                PropertyValuesHolder.ofInt("x", 0, appearance.getContext().getWidth()));
    }


    private static float getRandomX(float currX, int width) {

        if (currX < width / 2) {

            return (float) ((width - currX) * Math.random()) + currX;
        } else {
            return currX - (float) (currX * Math.random());
        }
    }

    private static float getRandomY(float currY, int height) {

        if (currY < height / 2) {

            return (float) ((height - currY) * Math.random()) + currY;
        } else {
            return currY - (float) (currY * Math.random());
        }
    }

    private long getRandomDuration() {
        return (long) (Math.random() * 2000 + 3000);
    }

    private Animator randomMove(final Appearance appearance, GameContext gameContext) {

        int width = gameContext.getWidth();
        int height = gameContext.getHeight() / 2;
        int locationX = appearance.getX();
        int locationY = appearance.getY();

        AnimatorSet animatorSet = new AnimatorSet();

        float randomX = getRandomX(locationX, width);
        ValueAnimator xAnimator = ValueAnimator.ofFloat(locationX, randomX);
        xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                appearance.setX((Integer) animation.getAnimatedValue());
            }
        });

        float randomY = getRandomY(locationY, height);
        ValueAnimator yAnimator = ValueAnimator.ofFloat(locationY, randomY);
        yAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                appearance.setY((Integer) animation.getAnimatedValue());
            }
        });

        animatorSet.playTogether(xAnimator, yAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        long randomDuration = getRandomDuration();
        animatorSet.setDuration(randomDuration);
        Logger.i(AppearanceAnimations.class, "randomX ：" + randomX + "   randomY：" + randomY + "   randomDuration：" + randomDuration);
        return animatorSet;
    }
}
