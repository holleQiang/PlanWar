package com.zq.planwar.game.plane;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.AppearanceHolder;
import com.zq.planwar.ext.ILocationUpdate;
import com.zq.planwar.game.plane.equipment.Equipment;
import com.zq.planwar.game.plane.property.HP;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.ext.IAttacker;
import com.zq.planwar.role.ext.ICollision;
import com.zq.planwar.role.ext.IDefender;
import com.zq.planwar.role.ext.ILife;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class Airplane extends Role implements ILife,
        ICollision,
        ILocationUpdate,
        IDefender,
        IAttacker{

    public Airplane(GameContext context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext context) {
        super.onDraw(canvas, context);
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void kill() {
        destroy();
    }

    @NonNull
    @Override
    public RectF getBounds() {

        return AppearanceUtils.getBounds(getAppearance());
    }

    @Override
    protected void onDestroyed() {
        super.onDestroyed();
        AppearanceUtils.release(getAppearance());
    }

    @Override
    public float getLocationX() {
        return AppearanceUtils.getLocationX(getAppearance());
    }

    @Override
    public float getLocationY() {
        return AppearanceUtils.getLocationY(getAppearance());
    }

    @Override
    public void setLocationX(float x) {
        AppearanceUtils.setLocationX(getAppearance(), x);
    }

    @Override
    public void setLocationY(float y) {
        AppearanceUtils.setLocationY(getAppearance(), y);
    }

    @Override
    public void offset(float dx, float dy) {
        AppearanceUtils.offset(getAppearance(), dx, dy);
    }


    @Override
    public HP getHp() {
        return null;
    }

    @Override
    public long attackValue() {
        return 10;
    }

}
