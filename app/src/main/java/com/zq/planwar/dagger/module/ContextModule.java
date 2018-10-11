package com.zq.planwar.dagger.module;

import android.content.Context;

import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.core.collision.CollisionHandler;
import com.zq.planwar.game.scene.CollisionHandlerImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqiang on 2018/9/21.
 */
@Module
public class ContextModule {

    private Context context;
    private GameContext gameContext;

    public ContextModule(Context context, GameContext gameContext) {
        this.context = context;
        this.gameContext = gameContext;
    }

    @Provides
    GameContext provideGameContext(){
        return gameContext;
    }

    @Provides
    CollisionHandler provideCollisionHandler(CollisionHandlerImpl collisionHandler){
        return collisionHandler;
    }
}
