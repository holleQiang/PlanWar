package com.zq.planwar.game.plane.equipment.bullet;

import com.zq.planwar.R;
import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.equipment.gun.Gun;
import com.zq.planwar.game.plane.property.HP;

/**
 * Created by zhangqiang on 2018/9/21.
 */
public class HeroBulletFactory extends BulletFactory {

    private static final int BULLET_WIDTH = 40;

    private GameContext context;

    public HeroBulletFactory(GameContext context) {
        this.context = context;
    }

    @Override
    public Bullet createBullet(Gun gun, int bulletCount, int index) {
        int shootX = computeX(gun,BULLET_WIDTH,bulletCount,index);

        DrawableAppearance drawableAppearance = new DrawableAppearance(context, R.drawable.zidan1);
        drawableAppearance.setRotate(180);

        int shootY = computeY(gun,drawableAppearance,90);
        drawableAppearance.setX(shootX);
        drawableAppearance.setY(shootY);
        return new Bullet(context, drawableAppearance, new HP(10, 10, 1), 10);
    }
}
