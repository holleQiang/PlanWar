package com.zq.planwar.game.scene;

import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.zq.planwar.core.GameEngine;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.RoleUtils;

/**
 * Created by zhangqiang on 2018/9/27.
 */
public class Scene extends Role{

    private Role mTouchTarget;
    private float mLastX;
    private float mLastY;
    @Nullable
    private GameEngine gameEngine;

    public boolean onTouch(GameContext context, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mTouchTarget = null;
                mLastX = event.getX();
                mLastY = event.getY();
                mTouchTarget = RoleUtils.findChildRoleAt(this,context.toGameWidth(mLastX) , context.toGameHeight(mLastY));
                break;
            case MotionEvent.ACTION_MOVE:

                float x = event.getX();
                float y = event.getY();
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                RoleUtils.offset(mTouchTarget, context.toGameWidth(deltaX), context.toGameHeight(deltaY));
                mLastX = x;
                mLastY = y;
                break;
        }
        return mTouchTarget != null;
    }

    @Override
    public void onRoleTreeChange() {
        super.onRoleTreeChange();
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        super.onDraw(canvas, gameContext);
    }

    public void attachToEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void detachFromEngine() {

    }

    @Nullable
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void sendTaskDelay(Runnable task, long delay){
        GameEngine gameEngine = getGameEngine();
        if (gameEngine != null) {
            gameEngine.postDelay(task,delay);
        }
    }

    public void onGameStop() {

    }

    public void onGameStart() {

    }
}
