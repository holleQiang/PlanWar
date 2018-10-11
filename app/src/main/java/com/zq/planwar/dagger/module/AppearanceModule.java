package com.zq.planwar.dagger.module;

import com.zq.planwar.R;
import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.impl.BackgroundAppearance;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.core.context.GameContext;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqiang on 2018/9/12.
 */
@Module
public class AppearanceModule {

    public static final String GAME_BG = "game_bg";
    public static final String HERO_PLANE = "hero_plane";
    public static final String ENEMY_PLANE_1 = "enemy_plane_1";
    public static final String ENEMY_PLANE_BOSS = "enemy_plane_boss";

    @Named(GAME_BG)
    @Provides
    Appearance provide1(BackgroundAppearance appearance) {
        return appearance;
    }

    @Named(HERO_PLANE)
    @Provides
    DrawableAppearance provide3(GameContext context) {

        //飞机
        return new DrawableAppearance(context, R.drawable.hero_plane);
    }

    @Named(ENEMY_PLANE_1)
    @Provides
    DrawableAppearance provide4(GameContext context) {

        //飞机
        return new DrawableAppearance(context, R.drawable.plane_enemy_1);
    }

    @Named(ENEMY_PLANE_BOSS)
    @Provides
    DrawableAppearance provide5(GameContext context) {

        //飞机
        return new DrawableAppearance(context, R.drawable.plane_boss);
    }
}
