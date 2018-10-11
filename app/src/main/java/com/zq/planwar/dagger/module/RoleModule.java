package com.zq.planwar.dagger.module;

import com.zq.planwar.R;
import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.decorator.Decorator;
import com.zq.planwar.appearance.decorator.HPBarDecorator;
import com.zq.planwar.appearance.decorator.StateDecorator;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.appearance.impl.LeftRightMoveAppearance;
import com.zq.planwar.appearance.impl.LocationAppearance;
import com.zq.planwar.appearance.impl.RandomMoveAppearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.Airplane;
import com.zq.planwar.game.plane.equipment.gun.Gun;
import com.zq.planwar.game.plane.property.HP;
import com.zq.planwar.game.plane.props.PropsRole;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqiang on 2018/9/13.
 */
@Module
public class RoleModule {

    public static final String GROUP_HERO = "hero";
    public static final String GROUP_ENEMY = "enemy";

    public static final String PLAN_ENEMY_AUTO_MOVE = "plan_enemy_auto_move";
    public static final String PLAN_ENEMY_BOSS = "plan_enemy_boss";
    public static final String PROPS = "props_gun_level_up";

    @Provides
    Airplane provideHeroPlan(GameContext gameContext,@Named(PropertyModule.HERO_HP) HP hpProperty,
                             @Named(AppearanceModule.HERO_PLANE) DrawableAppearance appearance,
                             @Named(DecoratorModule.HERO_HP_BAR )HPBarDecorator hpBarDecorator,
                             @Named(EquipmentModule.GUN_HERO) Gun gun) {

        hpBarDecorator.setHp(hpProperty);
        appearance.setDecorators(new Decorator[]{hpBarDecorator,new StateDecorator()});
        int locationX = gameContext.getGameViewWidth() / 2;
        float locationY = gameContext.getGameViewHeight() * 0.9f - appearance.getBounds().height();
        Appearance target = new LocationAppearance(locationX, locationY,appearance);
        Airplane airplane = new Airplane(hpProperty, target, gun);
        airplane.setTag(GROUP_HERO);
        return airplane;
    }

    @Named(PLAN_ENEMY_AUTO_MOVE)
    @Provides
    Airplane provideEnemyPlan2(@Named(PropertyModule.ENEMY_HP) HP hpProperty,
                               @Named(AppearanceModule.ENEMY_PLANE_1) DrawableAppearance appearance,
                               @Named(DecoratorModule.ENEMY_HP_BAR )HPBarDecorator hpBarDecorator,
                               @Named(EquipmentModule.GUN_ENEMY) Gun gun) {

        hpBarDecorator.setHp(hpProperty);
        appearance.setDecorators(new Decorator[]{hpBarDecorator,new StateDecorator()});
        Appearance target = new RandomMoveAppearance(new LocationAppearance(0,0,appearance));
        Airplane airPlane = new Airplane(hpProperty, target, gun);
        airPlane.setTag(GROUP_ENEMY);
        return airPlane;
    }

    @Named(PLAN_ENEMY_BOSS)
    @Provides
    Airplane provideEnemyPlan3(@Named(PropertyModule.ENEMY_BOSS_HP) HP hpProperty,
                               @Named(AppearanceModule.ENEMY_PLANE_BOSS) DrawableAppearance appearance,
                               @Named(DecoratorModule.ENEMY_BOSS_HP_BAR )HPBarDecorator hpBarDecorator,
                               @Named(EquipmentModule.GUN_BOSS) Gun gun) {
        hpBarDecorator.setHp(hpProperty);
        appearance.setDecorators(new Decorator[]{hpBarDecorator,new StateDecorator()});
        Appearance target = new LeftRightMoveAppearance(new LocationAppearance(0,appearance.getBounds().height(),appearance));
        Airplane airPlane = new Airplane(hpProperty, target, gun);
        airPlane.setTag(GROUP_ENEMY);
        return airPlane;
    }


    @Named(PROPS)
    @Provides
    PropsRole provide3(GameContext gameContext) {
        DrawableAppearance wrapper = new DrawableAppearance(gameContext, R.drawable.props_1);
        LocationAppearance locationAppearance = new LocationAppearance(gameContext.getRandomX(), gameContext.getRandomY(), wrapper);
        return new PropsRole(new RandomMoveAppearance(locationAppearance));
    }
}
