package com.zq.planwar.dagger.module;

import com.zq.planwar.game.plane.property.HP;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqiang on 2018/9/20.
 */
@Module
public class PropertyModule {

    public static final String HERO_HP = "HERO_HP";
    public static final String ENEMY_HP = "ENEMY_HP";
    public static final String ENEMY_BOSS_HP = "ENEMY_BOSS_HP";

    @Named(HERO_HP)
    @Provides
    HP provide1(){

        return new HP(100,100,1);
    }

    @Named(ENEMY_HP)
    @Provides
    HP provide2(){

        return new HP(100,100,1);
    }

    @Named(ENEMY_BOSS_HP)
    @Provides
    HP provide3(){

        return new HP(10000,10000,1);
    }
}
