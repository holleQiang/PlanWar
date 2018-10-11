package com.zq.planwar.appearance;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.zq.planwar.core.context.GameContext;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public interface Appearance {

    void draw(@NonNull Canvas canvas, @NonNull GameContext gameContext);
}
