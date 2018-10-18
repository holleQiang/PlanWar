package com.zq.planwar.game.plane.equipment.bullet;

import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.game.plane.equipment.gun.Gun;

/**
 * Created by zhangqiang on 2018/9/13.
 */
public abstract class BulletFactory {


    float computeX(Gun gun, int bulletWidth, int bulletCount, int index) {
        float locationX = gun.getShootX();
        return locationX - bulletCount * bulletWidth / 2 + bulletWidth / 2 + index * bulletWidth;
    }

    float computeY(Gun gun, DrawableAppearance appearance, int degree) {

        float locationY = gun.getShootY();
        int shootDegree = degree % 360;
        if (shootDegree >= 0 && shootDegree < 180) {
            return locationY - gun.getBounds().height() / 2 - appearance.getBounds().height() / 2;
        } else {
            return locationY + gun.getBounds().height() / 2 + appearance.getBounds().height() / 2;
        }
    }

    public Bullet[] createBullets(Gun gun) {
        final int bulletCount = gun.getCurrentBulletCount();

        Bullet[] bullets = new Bullet[bulletCount];
        for (int i = 0; i < bulletCount; i++) {
            bullets[i] = createBullet(gun, bulletCount, i);
        }
        return bullets;
    }

    protected abstract Bullet createBullet(Gun gun, int bulletCount, int i);
}
