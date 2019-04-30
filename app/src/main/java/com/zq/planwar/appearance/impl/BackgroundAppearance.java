package com.zq.planwar.appearance.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.zq.planwar.R;
import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.core.context.GameContext;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class BackgroundAppearance extends Appearance {

    private Bitmap bgBitmap;
    private Paint mPaint = new Paint();
    //速度（px/每秒）
    private static final float VELOCITY = 200;
    private long startTime;

    public BackgroundAppearance(GameContext context) {
        super(context);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
    }


    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {

        if (bgBitmap == null) {
            bgBitmap = ((BitmapDrawable) gameContext.getDrawable(R.drawable.bg4)).getBitmap();
            float scaleX = (float) gameContext.getWidth() / bgBitmap.getWidth();
            int realHeight = (int) (bgBitmap.getHeight() * scaleX);
            bgBitmap = Bitmap.createScaledBitmap(bgBitmap, gameContext.getWidth(), realHeight, true);
        }

        int bHeight = bgBitmap.getHeight();

        long currentTime = gameContext.getCurrentTime();

        if (startTime > 0) {

            float bgOffset = (float) (currentTime - startTime) / 1000 * VELOCITY % (bHeight);

            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setFilterBitmap(true);

            int save1 = canvas.save();
            canvas.translate(0, bgOffset);
            canvas.drawBitmap(bgBitmap, 0, 0, mPaint);
            canvas.translate(0, -bHeight + 1);
            canvas.drawBitmap(bgBitmap, 0, 0, mPaint);
            canvas.restoreToCount(save1);
        } else {
            startTime = currentTime;
        }
        invalidate();
    }
}
