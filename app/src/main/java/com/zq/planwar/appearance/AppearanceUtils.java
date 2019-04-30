package com.zq.planwar.appearance;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zq.planwar.appearance.decorator.Decorator;
import com.zq.planwar.appearance.decorator.StateDecorator;
import com.zq.planwar.appearance.ext.AppearanceWrapper;
import com.zq.planwar.appearance.ext.Releaseable;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.ext.AppearanceHolder;
import com.zq.planwar.ext.IBounds;
import com.zq.planwar.ext.ILocation;
import com.zq.planwar.ext.ILocationUpdate;
import com.zq.planwar.game.plane.property.State;
import com.zq.planwar.role.Role;

/**
 * Created by zhangqiang on 2018/9/20.
 */
public class AppearanceUtils {

    /**
     * 获取x值
     *
     * @param appearance appearance
     * @return f
     */
    public static float getLocationX(Appearance appearance) {

        float locationX = 0;

        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof ILocation) {
                locationX = ((ILocation) temp).getLocationX();
                break;
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
        return locationX;
    }

    /**
     * 获取y值
     *
     * @param appearance appearance
     * @return f
     */
    public static float getLocationY(Appearance appearance) {

        float locationY = 0;

        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof ILocation) {
                locationY = ((ILocation) temp).getLocationY();
                break;
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
        return locationY;
    }

    /**
     * 获取最大范围
     *
     * @param appearance appearance
     * @return
     */
    @NonNull
    public static RectF getBounds(Appearance appearance) {

        RectF bounds = new RectF();

        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof IBounds) {
                bounds.set(((IBounds) temp).getBounds());
                break;
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
        return bounds;
    }

    /**
     * 设置x值
     *
     * @param appearance appearance
     */
    public static void setLocationX(Appearance appearance, float x) {

        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof ILocationUpdate) {
                ((ILocationUpdate) temp).setLocationX(x);
                break;
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
    }

    /**
     * 设置y值
     *
     * @param appearance appearance
     */
    public static void setLocationY(Appearance appearance, float y) {

        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof ILocationUpdate) {
                ((ILocationUpdate) temp).setLocationY(y);
                break;
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
    }

    /**
     * 偏移
     *
     * @param appearance appearance
     * @param deltaX     deltaX
     * @param deltaY     deltaY
     */
    public static void offset(Appearance appearance, float deltaX, float deltaY) {

        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof ILocationUpdate) {
                ((ILocationUpdate) temp).offset(deltaX, deltaY);
                break;
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
    }

    public static void checkIBounds(Appearance appearance) {
        if (!(appearance instanceof IBounds)) {
            throw new IllegalArgumentException("appearance : " + appearance.getClass() + "must be instance of IBounds");
        }
    }

    @Nullable
    public static Drawable getDrawable(Appearance appearance) {

        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof DrawableAppearance) {
                return ((DrawableAppearance) temp).getDrawable();
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
        return null;
    }

    public static void release(Appearance appearance) {
        Appearance temp = appearance;

        while (temp != null) {

            if (temp instanceof Releaseable) {
                ((Releaseable) temp).release();
            }
            if (temp instanceof AppearanceWrapper) {
                temp = ((AppearanceWrapper) temp).getWrapper();
            } else {
                temp = null;
            }
        }
    }

    public static Decorator[] findDecorators(Appearance appearance) {

        Appearance temp = appearance;

//        while (temp != null) {
//
//            if (temp instanceof AbstractAppearance) {
//                return ((AbstractAppearance) temp).getDecorators();
//            }
//            if (temp instanceof AppearanceWrapper) {
//                temp = ((AppearanceWrapper) temp).getWrapper();
//            } else {
//                temp = null;
//            }
//        }
        return null;
    }

    public static void pushStateToDecorator(Object object, State state) {

        if (object instanceof AppearanceHolder) {
            Appearance appearance = ((AppearanceHolder) object).getAppearance();

            Decorator[] decorators = findDecorators(appearance);
            if (decorators == null || decorators.length <= 0) {
                return;
            }
            for (Decorator decorator : decorators) {
                if (decorator instanceof StateDecorator) {
                    ((StateDecorator) decorator).add(state);
                }
            }
        }
    }
}
