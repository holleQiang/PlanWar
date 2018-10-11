package com.zq.planwar.game.scene;

import android.graphics.Canvas;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.core.collision.CollisionHandler;
import com.zq.planwar.dagger.ComponentHolder;
import com.zq.planwar.dagger.module.AppearanceModule;
import com.zq.planwar.dagger.module.RoleModule;
import com.zq.planwar.game.plane.Airplane;
import com.zq.planwar.game.plane.props.PropsFactory;
import com.zq.planwar.game.plane.props.PropsRole;
import com.zq.planwar.role.Role;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by zhangqiang on 2018/9/27.
 */
public class SkyScene extends Scene {

    //每5秒一个小怪
    public static final int LEVEL1_INTERVAL = 5 * 1000;
    //boss出现延时
    public static final int BOSS_DELAY = 0 * 1000;
    //BOSS出现间隔
    public static final int BOSS_INTERVAL = 60 * 1000;
    //每5秒一个武器升级
    public static final int WEAPON_LEVEL_UP_INTERVAL = 5 * 1000;
    //每10秒一个满血
    public static final int HP_FULL_INTERVAL = 5 * 1000;

    @Named(AppearanceModule.GAME_BG)
    @Inject
    Appearance appearance;
    @Inject
    Airplane heroAirplane;

    @Inject
    CollisionHandler collisionHandler;

    private Runnable level1Enemy = new Level1Task();
    private Runnable bossTask = new BossTask();
    private Runnable gunLevelUpTask = new GunLevelUpPropsTask();
    private Runnable hpFullTask = new HpPropsTask();

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        super.onDraw(canvas, gameContext);
        if (appearance != null) {
            appearance.draw(canvas, gameContext);
        }
    }

    @Override
    public void onGameStart() {
        super.onGameStart();
        ComponentHolder.get().inject(this);
        addChild(heroAirplane);
        sendTaskDelay(level1Enemy,LEVEL1_INTERVAL);
        sendTaskDelay(bossTask, BOSS_DELAY);
        sendTaskDelay(gunLevelUpTask,WEAPON_LEVEL_UP_INTERVAL);
        sendTaskDelay(hpFullTask,HP_FULL_INTERVAL);
    }

    public class Level1Task implements Runnable {

        @Named(RoleModule.PLAN_ENEMY_AUTO_MOVE)
        @Inject
        Airplane enemyPlane;

        @Override
        public void run() {

            final int count = 3;
            Role[] roles = new Role[count];
            for (int i = 0; i < count; i++) {
                ComponentHolder.get().inject(this);
                roles[i] = enemyPlane;
            }
            addChild(roles);
            sendTaskDelay(this, LEVEL1_INTERVAL);
        }
    }

    public class GunLevelUpPropsTask implements Runnable {

        @Named(RoleModule.PROPS)
        @Inject
        PropsRole props;

        @Override
        public void run() {

            ComponentHolder.get().inject(this);
            props.setProps(PropsFactory.weaponLevelUp());
            addChild(props);
            sendTaskDelay(this, WEAPON_LEVEL_UP_INTERVAL);
        }
    }

    public class HpPropsTask implements Runnable {

        @Named(RoleModule.PROPS)
        @Inject
        PropsRole propsRole;

        @Override
        public void run() {

            ComponentHolder.get().inject(this);
            propsRole.setProps(PropsFactory.hpFull());
            addChild(propsRole);
            sendTaskDelay(this, HP_FULL_INTERVAL);
        }
    }



    public class BossTask implements Runnable {

        @Named(RoleModule.PLAN_ENEMY_BOSS)
        @Inject
        Airplane enemyPlane;

        @Override
        public void run() {

            ComponentHolder.get().inject(this);
            addChild(enemyPlane);
            sendTaskDelay(this, BOSS_INTERVAL);
        }
    }


}
