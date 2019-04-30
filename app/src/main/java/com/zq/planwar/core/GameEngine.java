package com.zq.planwar.core;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.core.context.GameContextFactory;
import com.zq.planwar.core.motionevent.GameMotionEvent;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.RootRole;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public final class GameEngine implements SurfaceHolder.Callback, Handler.Callback, View.OnTouchListener {

    private static final int MSG_RUNNING = 0;
    private static final int MSG_TOUCH_EVENT = 1;

    private GameContext gameContext;
    private GameContextFactory gameContextFactory;
    @Nullable
    private SurfaceHolder mSurfaceHolder;
    private RootRole mRootRole;
    private HandlerThread mGameThread;
    private Handler mHandler;
    private boolean isRunning;
    private Callback mCallback;
    private SurfaceView mSurfaceView;

    public GameEngine(GameContextFactory gameContextFactory) {
        this.gameContextFactory = gameContextFactory;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void attachToSurfaceView(SurfaceView surfaceView) {
        mSurfaceView = surfaceView;

        surfaceView.getHolder().addCallback(this);
        surfaceView.setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameContext = gameContextFactory.createGameContext();
        mRootRole = new RootRole(gameContext);
        mSurfaceHolder = holder;
        start();
    }

    private void start() {
        isRunning = true;
        mGameThread = new HandlerThread("game", Thread.MAX_PRIORITY);
        mGameThread.start();
        mHandler = new Handler(mGameThread.getLooper(), this);
        Message message = Message.obtain();
        message.what = MSG_RUNNING;
        mHandler.sendMessage(message);
        if (mCallback != null) {
            mCallback.onGameStart();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
        mSurfaceHolder = null;
    }

    public GameContext getGameContext() {
        return gameContext;
    }


    private void drawRole(Role role) {
        if (mSurfaceHolder == null) {
            return;
        }
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int gameContextWidth = gameContext.getWidth();
        int gameContextHeight = gameContext.getHeight();
        float scaleX = (float) width / gameContextWidth;
        float scaleY = (float) height / gameContextHeight;
        int save = canvas.save();
        canvas.scale(scaleX, scaleY, 0, 0);
        role.draw(canvas);
        canvas.restoreToCount(save);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void stop() {
        isRunning = false;
        mHandler.removeMessages(MSG_RUNNING);
        mGameThread.quit();
        mGameThread = null;
        if (mCallback != null) {
            mCallback.onGameStop();
        }
    }

    public RootRole getRootRole() {
        return mRootRole;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_RUNNING:
                if (mRootRole.shouldInvalidate()) {
                    drawRole(mRootRole);
                }
                if (isRunning) {
                    mHandler.sendMessage(Message.obtain(mHandler, MSG_RUNNING));
                }
                return true;
            case MSG_TOUCH_EVENT:
                GameMotionEvent gameMotionEvent = (GameMotionEvent) msg.obj;
                mRootRole.handTouchEvent(gameMotionEvent);
                return true;
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Message message = Message.obtain(mHandler,MSG_TOUCH_EVENT);
        message.obj = new GameMotionEvent(getGameContext())
                .setX(event.getX())
                .setY(event.getY())
                .setAction(event.getAction());
        mHandler.sendMessage(message);
        return true;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {

        void onGameStart();

        void onGameStop();
    }
}
