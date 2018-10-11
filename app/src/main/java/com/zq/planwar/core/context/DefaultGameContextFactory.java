package com.zq.planwar.core.context;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;

import com.zq.planwar.utils.DensityUtils;

/**
 * Created by zhangqiang on 2018/9/28.
 */
public class DefaultGameContextFactory extends GameContextFactory {

    private Context context;

    public DefaultGameContextFactory(Context context) {
        this.context = context;
    }

    @Override
    public GameContext createGameContext() {

        return new GameContext(720, 1280) {

            @Override
            public Drawable getDrawable(int drawableResId) {


                int density = getScaleDensityDpi(context.getResources().getDisplayMetrics());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    return context.getResources().getDrawableForDensity(drawableResId, density, context.getTheme());
                } else {
                    return context.getResources().getDrawableForDensity(drawableResId, density);
                }
            }

            @Override
            public int toRealWidth(float gameWidth) {

                return Math.round((float) getRealWidth() / getGameViewWidth() * gameWidth);
            }

            @Override
            public int toRealHeight(float gameHeight) {
                return Math.round((float) getRealHeight() / getGameViewHeight() * gameHeight);
            }

            @Override
            public float toGameWidth(float px) {
                return getGameViewWidth() * px / getRealWidth();
            }

            @Override
            public float toGameHeight(float px) {
                return getGameViewWidth() * px / getRealWidth();
            }

            @Override
            public Bitmap decodeResource(int resId) {
                return BitmapFactory.decodeResource(context.getResources(), resId);
            }

            @Override
            public int getRealWidth() {
                return context.getResources().getDisplayMetrics().widthPixels;
            }

            @Override
            public int getRealHeight() {
                return context.getResources().getDisplayMetrics().heightPixels;
            }

            @Override
            public float dipToGameSize(float dip) {
                return pxToGameSize(DensityUtils.dp2px(context, dip));
            }

            @Override
            public float pxToGameSize(int px) {
                return px * getScaleFactor(context.getResources().getDisplayMetrics());
            }

            private float getScaleFactor(DisplayMetrics displayMetrics) {

                int widthPixels = displayMetrics.widthPixels;
                int heightPixels = displayMetrics.heightPixels;

                int gameViewWidth = getGameViewWidth();
                int gameViewHeight = getGameViewHeight();

                return Math.max((float) gameViewWidth / widthPixels, (float) gameViewHeight / heightPixels);
            }

            private int getScaleDensityDpi(DisplayMetrics displayMetrics) {

                return Math.round(displayMetrics.densityDpi * getScaleFactor(displayMetrics));
            }
        };
    }


}
