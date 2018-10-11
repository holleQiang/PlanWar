package com.zq.planwar.game.plane;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.AppearanceHolder;
import com.zq.planwar.ext.ILocationUpdate;
import com.zq.planwar.game.plane.property.HP;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.ext.IAttacker;
import com.zq.planwar.role.ext.ICollision;
import com.zq.planwar.role.ext.IDefender;
import com.zq.planwar.role.ext.ILife;
import com.zq.planwar.game.plane.equipment.Equipment;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class Airplane extends Role implements ILife,
        ICollision,
        ILocationUpdate,
        IDefender,
        IAttacker,
        AppearanceHolder{

    //生命值
    private HP hp;
    //外观
    private Appearance appearance;
    //武器
    private Equipment weapon;

    public Airplane(HP hp, Appearance appearance, Equipment weapon) {
        this.hp = hp;
        this.appearance = appearance;
        setWeapon(weapon);
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

    public Equipment getWeapon() {
        return weapon;
    }

    public void setWeapon(Equipment weapon) {

        if (weapon == null || this.weapon == weapon) {
            return;
        }
        if (this.weapon != null) {
            removeChild(this.weapon);
        }
        this.weapon = weapon;
        addChild(weapon);
    }

    @Override
    public boolean isAlive() {
        return hp != null
                && hp.getHp() > 0;
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
    protected void onDestroy() {
        super.onDestroy();
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
        return hp;
    }

    @Override
    public long attackValue() {
        return 10;
    }

}
