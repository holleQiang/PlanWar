package com.zq.planwar.dagger.module;

import android.graphics.Color;

import com.zq.planwar.appearance.decorator.Decorator;
import com.zq.planwar.appearance.decorator.LevelDecorator;
import com.zq.planwar.appearance.impl.BoundsAppearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.equipment.bullet.BossBulletFactory;
import com.zq.planwar.game.plane.equipment.bullet.EnemyBulletFactory;
import com.zq.planwar.game.plane.equipment.bullet.HeroBulletFactory;
import com.zq.planwar.game.plane.equipment.gun.Gun;
import com.zq.planwar.game.plane.property.CollingTime;
import com.zq.planwar.game.plane.property.Level;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqiang on 2018/9/21.
 */
@Module
public class EquipmentModule {

    public static final String GUN_HERO = "gun_hero";
    public static final String GUN_ENEMY = "gun_enemy";
    public static final String GUN_BOSS = "GUN_BOSS";

    @Named(GUN_HERO)
    @Provides
    Gun provide(GameContext context) {
        Level level = new Level(1, 10, 1);
        Gun gun = new Gun(new Decorator[]{
                new LevelDecorator(context.dipToGameSize(2),
                        context.dipToGameSize(5),
                        context.dipToGameSize(2),
                        Color.RED,
                        level)}, new HeroBulletFactory(context),
                level,
                new CollingTime(200, 2000));
        gun.setMaxBulletCount(5);
        gun.setMaxBulletVelocity(1200);
        gun.setMinBulletVelocity(800);
        level.setLevel(level.getMaxLevel());
        return gun;
    }

    @Named(GUN_ENEMY)
    @Provides
    Gun provide2(GameContext context) {
        Level level = new Level(1, 10, 1);
        Gun gun = new Gun(new Decorator[]{
                new LevelDecorator(context.dipToGameSize(2),
                        context.dipToGameSize(5),
                        context.dipToGameSize(2),
                        Color.RED,
                        level)},
                new EnemyBulletFactory(context),
                level,
                new CollingTime(500, 2000));
        gun.setMaxBulletCount(3);
        gun.setMinBulletCount(1);
        level.setLevel(level.getMaxLevel());
        return gun;
    }

    @Named(GUN_BOSS)
    @Provides
    Gun provide3(GameContext context) {
        Level level = new Level(1, 10, 1);
        Gun gun = new Gun(new Decorator[]{
                new LevelDecorator(context.dipToGameSize(2),
                        context.dipToGameSize(5),
                        context.dipToGameSize(2),
                        Color.RED,
                        level)},
                new BossBulletFactory(context),
                level,
                new CollingTime(500, 2000));
        gun.getLevel().setLevel(level.getMaxLevel());
        gun.setMaxBulletCount(10);
        return gun;
    }
}
