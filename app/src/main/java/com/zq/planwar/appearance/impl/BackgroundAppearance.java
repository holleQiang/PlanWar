package com.zq.planwar.appearance.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.zq.planwar.R;
import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.core.context.GameContext;

import javax.inject.Inject;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class BackgroundAppearance implements Appearance {

    private Bitmap bgBitmap;
    private GameContext context;
    private Paint mPaint = new Paint();
    //速度（px/每秒）
    private float velocity = 200;

    @Inject
    public BackgroundAppearance(GameContext context) {
        this.context = context;
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext) {
        int width = canvas.getWidth();

        if (bgBitmap == null) {
            bgBitmap = gameContext.decodeResource( R.drawable.bg4);
            int bWidth = bgBitmap.getWidth();
            int bHeight = bgBitmap.getHeight();
            float sizeScaleFactor = (float) width / bWidth;
            float scaleBitmapHeight = bHeight * sizeScaleFactor;
            bgBitmap = Bitmap.createScaledBitmap(bgBitmap, width, (int) scaleBitmapHeight, true);
        }

        int bHeight = bgBitmap.getHeight();

        float bgOffset = (float) gameContext.getPastTime() / 1000 * velocity % (bHeight);

        mPaint.reset();
        mPaint.setAntiAlias(true);

        int save1 = canvas.save();
        canvas.translate(0, -bgOffset);
        canvas.drawBitmap(bgBitmap, 0, 0, mPaint);
        canvas.translate(0, bHeight);
        canvas.drawBitmap(bgBitmap, 0, 0, mPaint);
        canvas.restoreToCount(save1);
    }
}
