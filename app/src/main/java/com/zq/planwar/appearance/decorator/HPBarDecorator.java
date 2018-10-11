package com.zq.planwar.appearance.decorator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.property.HP;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class HPBarDecorator implements Decorator {

    private Paint paint = new Paint();
    private RectF rectF = new RectF();
    private float width;
    private float height;
    private float border;
    private int borderColor;
    private int solidColor;
    private HP hp;

    public HPBarDecorator(float width, float height, float border) {
        this.width = width;
        this.height = height;
        this.border = border;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getBorder() {
        return border;
    }

    public void setBorder(float border) {
        this.border = border;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getSolidColor() {
        return solidColor;
    }

    public void setSolidColor(int solidColor) {
        this.solidColor = solidColor;
    }

    public HP getHp() {
        return hp;
    }

    public void setHp(HP hp) {
        this.hp = hp;
    }

    @Override
    public void draw(Canvas canvas, GameContext gameContext, RectF bounds) {

        if(width <= 0){
            width = bounds.width();
        }

        int save = canvas.save();
        canvas.translate(bounds.left,bounds.top);

        //画背景
        rectF.set(border / 2, border / 2, width - border / 2, height - border / 2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(border);
        paint.setColor(getBorderColor());
        canvas.drawRect(rectF, paint);

        //画hp
        if (hp == null || hp.getMaxHP() <= 0) {
            return;
        }

        float factor = (float) hp.getHp() / hp.getMaxHP();
        float left = border / 2;
        float top = border / 2;
        float right = left + width * factor;
        float bottom = height - border / 2;
        this.rectF.set(left, top, right, bottom);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getSolidColor());
        canvas.drawRect(rectF, paint);

        canvas.restoreToCount(save);
    }
}
