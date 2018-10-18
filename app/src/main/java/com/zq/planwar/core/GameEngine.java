package com.zq.planwar.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.zq.planwar.core.collision.CollisionHandler;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.core.context.GameContextFactory;
import com.zq.planwar.core.fps.FpsComputeHelper;
import com.zq.planwar.game.scene.Scene;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.ext.ICollision;
import com.zq.planwar.role.ext.ILife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public final class GameEngine implements SurfaceHolder.Callback, Runnable {

    private Paint mPaint = new Paint();
    private final GameContext gameContext;
    @Nullable
    private HandlerThread gameThread;
    @Nullable
    private Handler gameHandler;
    @Nullable
    private SurfaceHolder mSurfaceHolder;

    private Scene scene;
    private FpsComputeHelper fpsComputeHelper = new FpsComputeHelper();

    private List<Role> roleList = new ArrayList<>();

    private Queue<Role> queue = new LinkedList<>();

    private static final int ROW_COUNT = 10;
    private static final int COLUMN_COUNT = 10;

    private CollisionHandler collisionHandler;
    private Map<Integer, List<ICollision>> collisionRows = new HashMap<>();
    private Bitmap bitmap;
    private Canvas canvas;
    private final RectF[] gameViewBlocks = new RectF[ROW_COUNT * COLUMN_COUNT];

    public GameEngine(GameContextFactory gameContextFactory) {
        gameContext = gameContextFactory.createGameContext();

        bitmap = Bitmap.createBitmap(gameContext.getGameViewWidth(), gameContext.getGameViewHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        float gameViewWidth = gameContext.getGameViewWidth();
        float gameViewHeight = gameContext.getGameViewHeight();

        //分块
        float perWidth = gameViewWidth / COLUMN_COUNT;
        float perHeight = gameViewHeight / ROW_COUNT;
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                RectF rectF = new RectF();
                gameViewBlocks[i * COLUMN_COUNT + j] = rectF;
                float left = perWidth * i;
                float top = perHeight * i;
                float right = left + perWidth;
                float bottom = top + perHeight;
                rectF.set(left, top, right, bottom);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new HandlerThread("game-draw-thread", Thread.MAX_PRIORITY);
        gameThread.start();
        gameHandler = new Handler(gameThread.getLooper());
        gameHandler.post(this);
        mSurfaceHolder = holder;
        Scene scene = getScene();
        if (scene != null) {
            scene.onGameStart();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (gameThread != null) {
            gameThread.quit();
            gameThread = null;
        }
        gameHandler = null;
        mSurfaceHolder = null;
        Scene scene = getScene();
        if (scene != null) {
            scene.onGameStop();
        }
    }


    public boolean onTouch(MotionEvent event) {
        if (getScene() != null) {
            return getScene().onTouch(getGameContext(), event);
        }
        return false;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        if(this.scene != null){
            this.scene.detachFromEngine();
        }
        this.scene = scene;
        this.scene.attachToEngine(this);
        if (mSurfaceHolder != null) {
            this.scene.onGameStart();
        }
    }

    public GameContext getGameContext() {
        return gameContext;
    }


    private void drawFrame(@Nullable SurfaceHolder mSurfaceHolder, Bitmap frame) {
        if (mSurfaceHolder == null) {
            return;
        }
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (canvas == null) {
            return;
        }
        fpsComputeHelper.invalidate();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(frame, null, gameContext.getRealBounds(), mPaint);
        drawFps(canvas, fpsComputeHelper.getFps());
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }


    private void drawFps(Canvas canvas, int fps) {

        String format = String.format(Locale.CHINESE, "%d%s", fps, "fps");
        mPaint.reset();
        mPaint.setTextSize(24);
        mPaint.setColor(Color.RED);
        float length = mPaint.measureText(format);
        int padding = 20;
        float x = canvas.getWidth() - length - padding;
        float y = padding - mPaint.ascent();
        canvas.drawText(format, x, y, mPaint);
    }

    @Override
    public void run() {

        for (Map.Entry<Integer, List<ICollision>> entry : collisionRows.entrySet()) {
            List<ICollision> value = entry.getValue();
            if (value != null) {
                value.clear();
            }
        }

        Scene scene = getScene();
        if (scene == null) {
            return;
        }

        List<Role> allRoles = getAllRoles(scene);
        int roleCount = allRoles == null ? 0 : allRoles.size();

        for (int i = 0; i < roleCount; i++) {

            Role src = allRoles.get(i);

            if (src instanceof ILife) {
                ILife living = (ILife) src;
                boolean alive = living.isAlive();
                if (!alive) {
                    living.kill();
                    continue;
                }
            }
            if (src instanceof ICollision) {

                ICollision iCollision = (ICollision) src;

                if(roleCount < 100){
                    putCollision(0,iCollision);
                }else {
                    RectF bounds = iCollision.getBounds();
                    for (int j = 0; j < gameViewBlocks.length; j++) {
                        RectF gameViewBlock = gameViewBlocks[j];
                        if (RectF.intersects(gameViewBlock, bounds)) {
                            putCollision(j,iCollision);
                        }
                    }
                }
            }
        }

        for (Map.Entry<Integer, List<ICollision>> entry : collisionRows.entrySet()) {
            computeCollision(entry.getValue());
        }
        scene.draw(canvas, gameContext);

//        for (int i = 0; i < roleCount; i++) {
//            allRoles.get(i).draw(backCanvas,gameContext);
//        }
        drawFrame(mSurfaceHolder,bitmap);

        if (gameHandler != null) {
            gameHandler.post(this);
        }
    }


    private List<Role> getAllRoles(Role role) {

        roleList.clear();
        queue.offer(role);

        while (!queue.isEmpty()) {

            Role pollRole = queue.poll();

            roleList.add(pollRole);

            if (pollRole != null) {
                Role[] childRoles = pollRole.getAllChildRoles();
                if (childRoles != null) {
                    for (Role childRole : childRoles) {
                        queue.offer(childRole);
                    }
                }
            }

        }
        return roleList;
    }

    private void computeCollision(List<ICollision> collisionList) {

        if (collisionList == null || collisionList.isEmpty() || collisionHandler == null) {
            return ;
        }
        int size = collisionList.size();
        for (int i = 0; i < size; i++) {
            ICollision srcCollision = collisionList.get(i);
            for (int j = 0; j < size; j++) {
                ICollision destCollision = collisionList.get(j);
                if (RectF.intersects(srcCollision.getBounds(), destCollision.getBounds())) {
                    collisionHandler.onHandCollision(srcCollision, destCollision);
                }
            }
        }
    }

    public void setCollisionHandler(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    public void post(Runnable runnable){
        if (gameHandler == null) {
            return;
        }
        gameHandler.post(runnable);
    }

    public void postDelay(Runnable runnable,long delay){
        if (gameHandler == null) {
            return;
        }
        gameHandler.postDelayed(runnable,delay);
    }

    private void putCollision(int blockIndex,ICollision collision){

        List<ICollision> collisionList = collisionRows.get(blockIndex);
        if (collisionList == null) {
            collisionList = new ArrayList<>();
            collisionRows.put(blockIndex, collisionList);
        }
        collisionList.add(collision);
    }
}
