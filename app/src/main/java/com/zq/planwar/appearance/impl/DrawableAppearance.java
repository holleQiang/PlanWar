package com.zq.planwar.appearance.impl;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.zq.planwar.appearance.AbstractAppearance;
import com.zq.planwar.appearance.decorator.Decorator;
import com.zq.planwar.core.context.GameContext;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class DrawableAppearance extends AbstractAppearance {

    @Nullable
    private Drawable drawable;
    private Rect drawableBounds = new Rect();
    private int rotationDegree;

    public DrawableAppearance(GameContext context, int drawableResId) {
        this(context.getDrawable(drawableResId));
    }

    public DrawableAppearance(Decorator[] decorators, Drawable drawable) {
        super(decorators);
        setDrawable(drawable);
    }

    public DrawableAppearance(Drawable drawable) {
        this(null,drawable);
    }

    private void updateBounds() {

        RectF bounds = getBounds();

        if (drawable == null) {
            bounds.setEmpty();
            return;
        }

        float centerX = bounds.centerX();
        float centerY = bounds.centerY();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        bounds.set(centerX - intrinsicWidth / 2,
                centerY - intrinsicHeight / 2,
                centerX + intrinsicWidth / 2,
                centerY + intrinsicHeight / 2);
        drawableBounds.set(Math.round(bounds.left),
                Math.round(bounds.top),
                Math.round(bounds.right),
                Math.round(bounds.bottom));
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        if (drawable == null) {
            return;
        }
        updateBounds();
        drawable.setBounds(drawableBounds);
        int save = canvas.save();
        canvas.rotate(rotationDegree,drawableBounds.centerX(),drawableBounds.centerY());
        drawable.draw(canvas);
        canvas.restoreToCount(save);
    }


    @Nullable
    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(@Nullable Drawable drawable) {
        this.drawable = drawable;
        updateBounds();
    }

    public int getRotationDegree() {
        return rotationDegree;
    }

    public void setRotationDegree(int rotationDegree) {
        this.rotationDegree = rotationDegree;
    }
}
