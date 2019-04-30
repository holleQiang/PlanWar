package com.zq.planwar;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zq.planwar.appearance.animation.AppearanceAnimations;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.core.GameEngine;
import com.zq.planwar.core.context.DefaultGameContextFactory;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.game.plane.Airplane;
import com.zq.planwar.game.plane.equipment.bullet.HeroBulletFactory;
import com.zq.planwar.game.plane.equipment.gun.Gun;
import com.zq.planwar.game.plane.property.CollingTime;
import com.zq.planwar.game.plane.property.HP;
import com.zq.planwar.game.plane.property.Level;

public class MainActivity extends AppCompatActivity{


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView mGameView = findViewById(R.id.m_game_view);
        final GameEngine gameEngine = new GameEngine(new DefaultGameContextFactory(mGameView));
        gameEngine.attachToSurfaceView(mGameView);
        gameEngine.setCallback(new GameEngine.Callback() {
            @Override
            public void onGameStart() {
                GameContext gameContext = gameEngine.getGameContext();
                Airplane airplane = new Airplane(gameContext);
                DrawableAppearance appearance = new DrawableAppearance(gameContext, R.drawable.hero_plane);
                appearance.setY(gameContext.getHeight() - appearance.getHeight());
                appearance.setX(gameContext.getWidth()/2);
                airplane.setAppearance(appearance);
                gameEngine.getRootRole().addChild(airplane);
            }

            @Override
            public void onGameStop() {

            }
        });
    }

}
