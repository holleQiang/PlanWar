package com.zq.planwar.core.context;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public abstract class GameContext {

    private long startTime;
    private int gameViewWidth;
    private int gameViewHeight;
    private RectF visibleRange = new RectF();
    private RectF realBounds = new RectF();

    public GameContext(int gameViewWidth, int gameViewHeight) {
        this.gameViewWidth = gameViewWidth;
        this.gameViewHeight = gameViewHeight;
        startTime = getCurrentTime();
        visibleRange.set(0,0,gameViewWidth,gameViewHeight);
    }

    public long getPastTime() {

        return getCurrentTime() - startTime;
    }

    public int getGameViewWidth() {
        return gameViewWidth;
    }

    public int getGameViewHeight() {
        return gameViewHeight;
    }

    private static long getCurrentTime() {
        return (long) (System.currentTimeMillis());
    }

    public boolean isBeyondVisibleRange(float x, float y) {
        return x > 0 && x < getGameViewWidth() && y > 0 && y < getGameViewHeight();
    }


    public boolean isBeyondVisibleRange(RectF bounds) {
        return !RectF.intersects(visibleRange,bounds);
    }

    public int getRandomX(){

        return (int) (Math.random() * getGameViewWidth());
    }

    public int getRandomY(){

        return (int) (Math.random() * getGameViewHeight());
    }

    public abstract Drawable getDrawable(int drawableResId);

    public abstract int toRealWidth(float gameWidth);

    public abstract int toRealHeight(float gameHeight);

    public abstract float toGameWidth(float px);

    public abstract float toGameHeight(float px);

    public abstract Bitmap decodeResource(int bg4);

    public abstract int getRealWidth();

    public abstract int getRealHeight();

    public abstract float dipToGameSize(float dip);

    public abstract float pxToGameSize(int px);

    public float getScaleX() {
        return (float) getGameViewWidth()/getRealWidth();
    }

    public float getScaleY() {
        return (float)getGameViewHeight()/getRealHeight();
    }

    public RectF getRealBounds() {
        realBounds.set(0,0,getRealWidth(),getRealHeight());
        return realBounds;
    }
}
