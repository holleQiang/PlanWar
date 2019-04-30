package com.zq.planwar.game.plane.equipment.bullet;

import com.zq.planwar.R;
import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.equipment.gun.Gun;
import com.zq.planwar.game.plane.property.HP;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public class BossBulletFactory extends BulletFactory {

    private GameContext context;

    public BossBulletFactory(GameContext context) {
        this.context = context;
    }

    @Override
    public Bullet createBullet(Gun gun, int bulletCount, int index) {

        int degree = 180 + 180 / bulletCount * index;
        DrawableAppearance drawableAppearance = new DrawableAppearance(context, R.drawable.zidan1);
        float shootY = computeY(gun,drawableAppearance,degree);

        Appearance appearance = drawableAppearance;

        return new Bullet(context,appearance, new HP(1, 1, 1), 1);
    }
}
