package com.zq.planwar.game.plane.equipment;

import android.graphics.Canvas;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.AppearanceHolder;
import com.zq.planwar.role.Role;

/**
 * Created by zhangqiang on 2018/9/13.
 */
public class Equipment extends Role implements AppearanceHolder {

    private Appearance appearance;

    public Equipment(Appearance appearance) {
        this.appearance = appearance;
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        super.onDraw(canvas, gameContext);
        if (appearance != null) {
            appearance.draw(canvas, gameContext);
        }
    }

    @Override
    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }


}
