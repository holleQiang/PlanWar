package com.zq.planwar.appearance.impl;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.zq.planwar.appearance.AbstractAppearance;
import com.zq.planwar.appearance.decorator.Decorator;
import com.zq.planwar.core.context.GameContext;

/**
 * Created by zhangqiang on 2018/10/9.
 */
public class BoundsAppearance extends AbstractAppearance {


    public BoundsAppearance(Decorator[] decorators,RectF bounds) {
        super(decorators);
        if (bounds != null) {
            getBounds().set(bounds);
        }
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {

    }


}
