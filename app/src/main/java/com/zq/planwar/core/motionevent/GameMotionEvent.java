package com.zq.planwar.core.motionevent;

import android.view.MotionEvent;

import com.zq.planwar.core.context.GameContext;

public class GameMotionEvent {

    public static final int ACTION_DOWN = MotionEvent.ACTION_DOWN;
    public static final int ACTION_MOVE = MotionEvent.ACTION_MOVE;
    public static final int ACTION_UP = MotionEvent.ACTION_UP;
    public static final int ACTION_CANCEL = MotionEvent.ACTION_CANCEL;
    private GameContext context;
    private int action;
    private float x,y;

    public GameMotionEvent(GameContext context) {
        this.context = context;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getAction() {
        return action;
    }

    public GameMotionEvent setAction(int action) {
        this.action = action;
        return this;
    }

    public GameMotionEvent setX(float x) {
        this.x = x * context.getXScaleFactor();
        return this;
    }

    public GameMotionEvent setY(float y) {
        this.y = y * context.getYScaleFactor();
        return this;
    }
}
