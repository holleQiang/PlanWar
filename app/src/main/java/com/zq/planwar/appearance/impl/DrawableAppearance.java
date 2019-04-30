package com.zq.planwar.appearance.impl;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.core.context.GameContext;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class DrawableAppearance extends Appearance {

    private Drawable drawable;

    public DrawableAppearance(GameContext context, int drawableResId) {
        this(context, context.getDrawable(drawableResId));
    }

    public DrawableAppearance(GameContext context, @NonNull Drawable drawable) {
        super(context);
        this.drawable = drawable;
        updateBounds();
    }


    private void updateBounds() {

        if (drawable == null) {
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        setWidth(intrinsicWidth);
        setHeight(intrinsicHeight);
        int left = -getWidth() / 2;
        int top = -getHeight() / 2;
        int right = getWidth() / 2;
        int bottom = getHeight() / 2;
        drawable.setBounds(left, top, right, bottom);
    }


    @Nullable
    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(@NonNull Drawable drawable) {
        this.drawable = drawable;
        updateBounds();
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        if (drawable == null) {
            return;
        }

        drawable.draw(canvas);
    }
}
