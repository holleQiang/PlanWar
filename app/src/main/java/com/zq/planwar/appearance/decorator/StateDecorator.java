package com.zq.planwar.appearance.decorator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;

import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.property.State;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by zhangqiang on 2018/9/26.
 */
public class StateDecorator implements Decorator {

    private TextPaint textPaint = new TextPaint();
    private int animatorDuration = 1000;
    private int maxAnimatorSize = 5;
    private List<AnimatorState<State>> currentAnimators = new ArrayList<>();
    private Queue<AnimatorState<State>> stateQueue = new LinkedList<>();
    private int index = 0;

    @Override
    public void draw(Canvas canvas, GameContext gameContext, RectF bounds) {

        index = 0;
        while (index < maxAnimatorSize && !stateQueue.isEmpty()){
            index ++;
            AnimatorState<State> animatorState = stateQueue.poll();
            if (!animatorState.isStarted()) {
                animatorState.start();
            }
            if (!animatorState.isEnd()) {
                stateQueue.offer(animatorState);
            }
            String info = animatorState.getData().getInfo();
            float textWidth = textPaint.measureText(info);
            float x = bounds.centerX() - textWidth / 2;
            float y = bounds.top - textPaint.descent() + animatorState.getTranslateY();
            textPaint.setColor(Color.RED);
            textPaint.setAlpha(animatorState.getAlpha());
            textPaint.setTextSize(15);
            canvas.drawText(info, x, y, textPaint);
        }
    }

    public void add(State state) {
//        stateQueue.offer(new AnimatorState<>(state));
    }

    class AnimatorState<T> {

        private float translateY;
        private int alpha;
        private T data;
        private Animator animator;
        private boolean isStarted;

        public AnimatorState(T data) {
            this.data = data;
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator alphaAnimator = ObjectAnimator.ofInt(this, "alpha", 255, 60);
            ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(this, "translateY", 0, -60);
            animatorSet.playTogether(alphaAnimator,
                    translateYAnimator);
            animatorSet.setDuration(animatorDuration);
            animator =animatorSet;
        }

        public float getTranslateY() {
            return translateY;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setTranslateY(float translateY) {
            this.translateY = translateY;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public void start(){
            if (!isStarted) {
                animator.start();
                isStarted = true;
            }
        }

        public boolean isEnd(){
            return isStarted && !animator.isRunning();
        }

        public boolean isStarted() {
            return isStarted;
        }
    }

    private Animator createAnimator(final AnimatorState<State> state) {

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofInt(state, "alpha", 255, 60);
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(state, "translateY", 0, -60);
        animatorSet.playTogether(alphaAnimator,
                translateYAnimator);
        animatorSet.setDuration(animatorDuration);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentAnimators.remove(state);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                currentAnimators.add(state);
            }
        });
        return animatorSet;
    }
}
