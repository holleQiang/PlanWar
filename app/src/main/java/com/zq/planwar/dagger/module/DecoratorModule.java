package com.zq.planwar.dagger.module;

import android.graphics.Color;

import com.zq.planwar.appearance.decorator.HPBarDecorator;
import com.zq.planwar.core.context.GameContext;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqiang on 2018/9/21.
 */
@Module
public class DecoratorModule {

    public static final String HERO_HP_BAR = "hero_hp_bar";
    public static final String ENEMY_HP_BAR = "enemy_hp_bar";
    public static final String ENEMY_BOSS_HP_BAR = "enemy_boss_hp_bar";

    @Named(HERO_HP_BAR)
    @Provides
    HPBarDecorator provideHPBar(GameContext context) {

        //血条
        HPBarDecorator hpBar = new HPBarDecorator(0,
                context.dipToGameSize(5),
                context.dipToGameSize(1));
        hpBar.setBorderColor(Color.RED);
        hpBar.setSolidColor(Color.RED);
        return hpBar;
    }


    @Named(ENEMY_HP_BAR)
    @Provides
    HPBarDecorator provideHPBar2(GameContext context) {

        //血条
        HPBarDecorator hpBar = new HPBarDecorator(0,
                context.dipToGameSize(5),
                context.dipToGameSize(1));
        hpBar.setBorderColor(Color.BLUE);
        hpBar.setSolidColor(Color.BLUE);
        return hpBar;
    }

    @Named(ENEMY_BOSS_HP_BAR)
    @Provides
    HPBarDecorator provideHPBar3(GameContext context) {

        //血条
        HPBarDecorator hpBar = new HPBarDecorator(0,
                context.dipToGameSize(5),
                context.dipToGameSize(1));
        hpBar.setBorderColor(Color.BLUE);
        hpBar.setSolidColor(Color.BLUE);
        return hpBar;
    }
}
