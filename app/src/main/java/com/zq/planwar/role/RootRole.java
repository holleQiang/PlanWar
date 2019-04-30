package com.zq.planwar.role;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.impl.BackgroundAppearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.core.motionevent.GameMotionEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class RootRole extends Role {

    private int invalidateRequestCount;
    private long lastTime;
    private int count = 0;
    private static final int SECOND_COUNT = 3;
    private float mDownX, mDownY, mLastX, mLastY;
    private Queue<Role> mTempQueue = new LinkedList<>();
    private Role mTouchTarget;

    public RootRole(@NonNull GameContext context) {
        super(context);
        setAppearance(new BackgroundAppearance(context));
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext context) {
        super.onDraw(canvas, context);
        long currentTime = context.getCurrentTime();
        if (currentTime - lastTime > SECOND_COUNT * 1000) {
            int perSecondCount = count / SECOND_COUNT;
            Log.i("Test", "===================" + perSecondCount);
            count = 0;
            lastTime = currentTime;
        } else {
            count++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (shouldInvalidate()) {
            invalidateRequestCount = 0;
            super.draw(canvas);
        }
    }

    @Override
    protected void onChildRequestInvalidate(Role child) {
        super.onChildRequestInvalidate(child);
        invalidateRequestCount++;
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        invalidateRequestCount++;
    }

    public void handTouchEvent(GameMotionEvent event) {
        int action = event.getAction();
        Log.i("Test","=======handTouchEvent=====action===" + action);
        switch (action) {
            case GameMotionEvent.ACTION_DOWN:
                mDownX = mLastX = event.getX();
                mDownY = mLastY = event.getY();
                mTouchTarget = findTouchTarget(mDownX, mDownY);
                Log.i("Test","=======mTouchTarget========" + mTouchTarget);
                break;
            case GameMotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                if (mTouchTarget != null) {
                    Appearance appearance = mTouchTarget.getAppearance();
                    appearance.setX(Math.round(x));
                    appearance.setY(Math.round(y));
                    mTouchTarget.invalidate();
                }
                Log.i("Test","=======mTouchTarget========" + mTouchTarget);
                break;
            case GameMotionEvent.ACTION_UP:
            case GameMotionEvent.ACTION_CANCEL:
                mTouchTarget = null;
                break;
        }
    }

    private Role findTouchTarget(float x, float y) {

        mTempQueue.clear();
        Role tempRole;
        mTempQueue.offer(this);
        while ((tempRole = mTempQueue.poll()) != null) {


            Appearance appearance = tempRole.getAppearance();
            if (appearance != null) {
                if (appearance.isPointWithinBounds(x, y)) {
                    return tempRole;
                }
            }
            List<Role> childList = tempRole.getChildList();
            if (childList != null && !childList.isEmpty()) {
                for (Role role : childList) {
                    mTempQueue.offer(role);
                }
            }
        }
        return null;
    }

    public boolean shouldInvalidate(){
        return invalidateRequestCount > 0;
    }
}
