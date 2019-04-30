package com.zq.planwar.appearance;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.zq.planwar.core.context.GameContext;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public abstract class Appearance {

    private int x, y;
    private int width, height;
    private float rotate = 0;
    private float scaleX = 1, scaleY = 1;
    private GameContext context;
    private AppearanceCallback callback;

    public Appearance(@NonNull GameContext context) {
        this.context = context;
    }

    public final void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext) {
        int save = canvas.save();
        canvas.translate(x, y);
        canvas.rotate(rotate);
        canvas.scale(scaleX, scaleY);
        onDraw(canvas, gameContext);
        canvas.restoreToCount(save);
    }

    protected abstract void onDraw(Canvas canvas, GameContext gameContext);

    public int getX() {
        return x;
    }

    public Appearance setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Appearance setY(int y) {
        this.y = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Appearance setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Appearance setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getLeft() {
        return x - width / 2;
    }

    public int getTop() {
        return y - height / 2;
    }

    public int getRight() {
        return x + width / 2;
    }

    public int getBottom() {
        return y + height / 2;
    }

    public GameContext getContext() {
        return context;
    }

    public float getRotate() {
        return rotate;
    }

    public Appearance setRotate(float rotate) {
        this.rotate = rotate;
        return this;
    }

    public float getScaleX() {
        return scaleX;
    }

    public Appearance setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public float getScaleY() {
        return scaleY;
    }

    public Appearance setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public Appearance setContext(GameContext context) {
        this.context = context;
        return this;
    }

    public boolean isPointWithinBounds(float x, float y) {
        return x >= getLeft() && x <= getRight()
                && y >= getTop() && y <= getBottom();
    }

    public AppearanceCallback getCallback() {
        return callback;
    }

    public Appearance setCallback(AppearanceCallback callback) {
        this.callback = callback;
        return this;
    }

    public void invalidate(){
        if (callback != null) {
            callback.onInvalidate();
        }
    }
}
