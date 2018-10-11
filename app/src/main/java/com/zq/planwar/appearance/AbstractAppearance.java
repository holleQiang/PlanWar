package com.zq.planwar.appearance;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.decorator.Decorator;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.IBounds;

/**
 * Created by zhangqiang on 2018/9/21.
 */
public abstract class AbstractAppearance implements Appearance,IBounds{

    private Decorator[] decorators;
    private RectF bounds = new RectF();

    public AbstractAppearance(Decorator[] decorators) {
        this.decorators = decorators;
    }

    @Override
    public final void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext) {

        onDraw(canvas,gameContext);
        drawDecorator(canvas,gameContext,getBounds());
    }

    protected abstract void onDraw(Canvas canvas, GameContext gameContext);

    private void drawDecorator(Canvas canvas, GameContext gameContext, RectF bounds) {
        if (decorators == null || decorators.length <= 0) {
            return;
        }
        for (Decorator decorator : decorators) {
            decorator.draw(canvas,gameContext,bounds);
        }
    }

    @NonNull
    @Override
    public RectF getBounds() {
        return bounds;
    }

    public Decorator[] getDecorators() {
        return decorators;
    }

    public void setDecorators(Decorator[] decorators) {
        this.decorators = decorators;
    }
}
