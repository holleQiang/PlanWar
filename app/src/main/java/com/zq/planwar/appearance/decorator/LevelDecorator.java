package com.zq.planwar.appearance.decorator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.property.Level;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public class LevelDecorator implements Decorator {

    private float width;
    private float height;
    private float space;
    private int color;
    private RectF rectF = new RectF();
    private Paint mPaint = new Paint();
    private Level level;

    public LevelDecorator(float width, float height, float space, int color, Level level) {
        this.width = width;
        this.height = height;
        this.space = space;
        this.color = color;
        this.level = level;
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas, GameContext gameContext, RectF bounds) {

        int level = this.level.getLevel();

        for (int i = 0; i < level; i++) {
            float x = bounds.centerX() - (width * level + space * (level - 1)) / 2 + (width + space) * i;
            float y = bounds.bottom + space;
            rectF.set(x, y, x + width, y + height);
            mPaint.setColor(color);
            canvas.drawRect(rectF, mPaint);
        }
    }
}
