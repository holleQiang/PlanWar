package com.zq.planwar.core.fps;

import android.graphics.Canvas;

/**
 * Created by zhangqiang on 2018/10/11.
 */
public class FpsComputeHelper {

    private long lastFrameTime;
    private int frameIndex;
    private int fps;

    public void invalidate(){

        long currentTimeMillis = System.currentTimeMillis();
        if(lastFrameTime <= 0){
            this.lastFrameTime = currentTimeMillis;
            return;
        }
        long time = currentTimeMillis - lastFrameTime;
        frameIndex ++;
        if(time < 2000){
            return;
        }
        fps = frameIndex / 2;
        lastFrameTime = currentTimeMillis;
        frameIndex = 0;
    }

    public int getFps() {
        return fps;
    }
}
