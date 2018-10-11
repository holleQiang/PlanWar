package com.zq.planwar.dagger.component;

import com.zq.planwar.dagger.module.AppearanceModule;
import com.zq.planwar.dagger.module.ContextModule;
import com.zq.planwar.dagger.module.DecoratorModule;
import com.zq.planwar.dagger.module.EquipmentModule;
import com.zq.planwar.dagger.module.RoleModule;
import com.zq.planwar.dagger.module.PropertyModule;
import com.zq.planwar.game.PlanWar;
import com.zq.planwar.game.scene.SkyScene;

import dagger.Component;

/**
 * Created by zhangqiang on 2018/9/12.
 */
@Component(modules = {ContextModule.class,
        AppearanceModule.class,
        RoleModule.class,
        DecoratorModule.class,
        EquipmentModule.class,
        PropertyModule.class})
public interface AppComponent {

    void inject(PlanWar target);

    void inject(SkyScene.Level1Task level1Task);

    void inject(SkyScene.BossTask bossTask);

    void inject(SkyScene.GunLevelUpPropsTask gunLevelUpPropsTask);

    void inject(SkyScene scene1);

    void inject(SkyScene.HpPropsTask hpPropsTask);
}
