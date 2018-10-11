package com.zq.planwar;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.zq.planwar.core.context.DefaultGameContextFactory;
import com.zq.planwar.dagger.ComponentHolder;
import com.zq.planwar.dagger.component.AppComponent;
import com.zq.planwar.dagger.component.DaggerAppComponent;
import com.zq.planwar.dagger.module.ContextModule;
import com.zq.planwar.game.scene.CollisionHandlerImpl;
import com.zq.planwar.game.scene.SkyScene;
import com.zq.planwar.core.GameEngine;

public class MainActivity extends AppCompatActivity{


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView mGameView = findViewById(R.id.m_game_view);
        final GameEngine gameEngine = new GameEngine(new DefaultGameContextFactory(this));
        gameEngine.setCollisionHandler(new CollisionHandlerImpl(gameEngine.getGameContext()));
        AppComponent appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this,gameEngine.getGameContext()))
                .build();
        ComponentHolder.init(appComponent);
        gameEngine.setScene(new SkyScene());
        mGameView.getHolder().addCallback(gameEngine);
        mGameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return gameEngine.onTouch(event);
            }
        });
    }

}
