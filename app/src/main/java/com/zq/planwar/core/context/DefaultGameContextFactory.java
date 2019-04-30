package com.zq.planwar.core.context;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.View;

import com.zq.planwar.utils.DensityUtils;

/**
 * Created by zhangqiang on 2018/9/28.
 */
public class DefaultGameContextFactory extends GameContextFactory {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1920;

    private View gameView;
    private Context context;

    public DefaultGameContextFactory(View gameView) {
        this.gameView = gameView;
        this.context = gameView.getContext();
    }

    @Override
    public GameContext createGameContext() {

        final int viewWidth = gameView.getWidth();
        final int viewHeight = gameView.getHeight();
        return new GameContext() {

            @Override
            public int getWidth() {
                return WIDTH;
            }

            @Override
            public int getHeight() {
                return HEIGHT;
            }

            @Override
            public Drawable getDrawable(int drawableResId) {

                int density = getScaleDensityDpi(context.getResources().getDisplayMetrics());
                return ResourcesCompat.getDrawableForDensity(context.getResources(), drawableResId, density, context.getTheme());
            }

            @Override
            public long getCurrentTime() {
                return System.currentTimeMillis();
            }

            @Override
            public float getXScaleFactor() {
                return (float) getWidth()/viewWidth;
            }

            @Override
            public float getYScaleFactor() {
                return (float) getHeight()/viewHeight;
            }

            private float getScaleFactor(DisplayMetrics displayMetrics) {

                int widthPixels = displayMetrics.widthPixels;
                int heightPixels = displayMetrics.heightPixels;

                int gameViewWidth = getWidth();
                int gameViewHeight = getHeight();

                return Math.max((float) gameViewWidth / widthPixels, (float) gameViewHeight / heightPixels);
            }

            private int getScaleDensityDpi(DisplayMetrics displayMetrics) {

                return Math.round(displayMetrics.densityDpi * getScaleFactor(displayMetrics));
            }
        };
    }


}
