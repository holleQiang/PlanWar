package com.zq.planwar.appearance.decorator;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.zq.planwar.core.context.GameContext;

/**
 * Created by zhangqiang on 2018/9/21.
 */
public interface Decorator {

    void draw(Canvas canvas, GameContext gameContext, RectF bounds);
}
