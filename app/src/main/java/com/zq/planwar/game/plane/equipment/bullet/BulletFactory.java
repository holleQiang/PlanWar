package com.zq.planwar.game.plane.equipment.bullet;

import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.game.plane.equipment.gun.Gun;

/**
 * Created by zhangqiang on 2018/9/13.
 */
public abstract class BulletFactory {


    int computeX(Gun gun, int bulletWidth, int bulletCount, int index) {
        int locationX = (int) gun.getShootX();
        return locationX - bulletCount * bulletWidth / 2 + bulletWidth / 2 + index * bulletWidth;
    }

    int computeY(Gun gun, DrawableAppearance appearance, int degree) {

        int locationY = gun.getShootY();
        int shootDegree = degree % 360;
        if (shootDegree >= 0 && shootDegree < 180) {
            return (int) (locationY - gun.getBounds().height() / 2 - appearance.getHeight() / 2);
        } else {
            return (int) (locationY + gun.getBounds().height() / 2 + appearance.getWidth() / 2);
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
