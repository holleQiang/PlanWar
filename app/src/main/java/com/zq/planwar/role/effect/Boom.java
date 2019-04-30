package com.zq.planwar.role.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.ext.ILife;

/**
 * Created by zhangqiang on 2018/9/21.
 */
public class Boom extends Role implements ILife,ValueAnimator.AnimatorUpdateListener{

    private Appearance appearance;
    private ValueAnimator animator;
    private boolean isAnimatorEnd;

    public Boom(GameContext context,Appearance appearance) {
        super(context);
        this.appearance = appearance;
        animator = ValueAnimator.ofInt(255,0).setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(this);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimatorEnd = true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        super.onDraw(canvas, gameContext);
        if(!isAnimatorEnd && !animator.isStarted() ){
            animator.start();
        }
        if (appearance != null) {
            appearance.draw(canvas, gameContext);
        }
    }

    @Override
    public boolean isAlive() {
        return !isAnimatorEnd;
    }

    @Override
    public void kill() {
        destroy();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Drawable drawable = AppearanceUtils.getDrawable(appearance);
        if (drawable != null) {
            drawable.setAlpha((Integer) animation.getAnimatedValue());
        }
    }

    @Override
    protected void onDestroyed() {
        super.onDestroyed();
        animator.end();
        animator = null;
    }
}
