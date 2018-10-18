package com.zq.planwar.game.plane.equipment.bullet;

import com.zq.planwar.R;
import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.appearance.impl.LocationAppearance;
import com.zq.planwar.appearance.impl.MoveAppearanceImpl;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.equipment.gun.Gun;
import com.zq.planwar.game.plane.property.HP;

/**
 * Created by zhangqiang on 2018/9/21.
 */
public class EnemyBulletFactory extends BulletFactory {

    private static final int BULLET_WIDTH = 40;

    private GameContext context;

    public EnemyBulletFactory(GameContext context) {
        this.context = context;
    }

    @Override
    public Bullet createBullet(Gun gun, int bulletCount, int index) {

        float shootX = computeX(gun, BULLET_WIDTH, bulletCount, index);

        DrawableAppearance drawableAppearance = new DrawableAppearance(context, R.drawable.zidan1);
        float shootY = computeY(gun, drawableAppearance, 270);

        Appearance appearance = drawableAppearance;
        appearance = new LocationAppearance(shootX, shootY, appearance);
        appearance = new MoveAppearanceImpl(270, gun.getCurrentBulletVelocity(), appearance);

        return new Bullet(appearance, new HP(1, 1, 1), 1);
    }

}
