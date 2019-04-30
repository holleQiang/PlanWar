package com.zq.planwar.game.plane.equipment.bullet;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.ILocation;
import com.zq.planwar.game.plane.equipment.Equipment;
import com.zq.planwar.game.plane.property.HP;
import com.zq.planwar.role.ext.IAttacker;
import com.zq.planwar.role.ext.ICollision;
import com.zq.planwar.role.ext.IDefender;
import com.zq.planwar.role.ext.ILife;
import com.zq.planwar.utils.Logger;

/**
 * Created by zhangqiang on 2018/9/13.
 */
public class Bullet extends Equipment implements ILife,IAttacker,IDefender,ICollision,ILocation {

    private boolean isOutOfCanvas;
    private HP hp;
    private int attackValue;

    public Bullet(GameContext context,Appearance appearance, HP hp, int attackValue) {
        super(context,appearance);
        this.hp = hp;
        this.attackValue = attackValue;
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        super.onDraw(canvas, gameContext);

        float x = AppearanceUtils.getLocationX(getAppearance());
        float y = AppearanceUtils.getLocationY(getAppearance());
        isOutOfCanvas = gameContext.isOutOfVisibleRect(this);
        Logger.i(Bullet.class, "====Bullet=====x===" + x + "===y=====" + y);
    }


    @Override
    public boolean isAlive() {
        return !isOutOfCanvas && hp.isHealthy();
    }

    @Override
    public void kill() {
        destroy();
    }

    @Override
    public long attackValue() {
        return attackValue;
    }

    @Override
    public HP getHp() {
        return hp;
    }

    @NonNull
    @Override
    public RectF getBounds() {
        return AppearanceUtils.getBounds(getAppearance());
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
    protected void onDestroyed() {
        super.onDestroyed();
    }

    public void setHp(HP hp) {
        this.hp = hp;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

}
