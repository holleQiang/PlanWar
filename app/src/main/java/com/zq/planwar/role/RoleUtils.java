package com.zq.planwar.role;

import android.graphics.RectF;
import android.support.annotation.Nullable;

import com.zq.planwar.ext.IBounds;
import com.zq.planwar.ext.ILocation;
import com.zq.planwar.ext.ILocationUpdate;
import com.zq.planwar.role.ext.ICollision;

/**
 * Created by zhangqiang on 2018/9/20.
 */
public class RoleUtils {

    @Nullable
    public static Role findChildRoleAt(Role parentRole, float x, float y) {

        int childCount = parentRole.getChildCount();
        for (int i = 0; i < childCount; i++) {

            Role role = parentRole.getChildAt(i);
            RectF roleBounds = getBounds(role);
            if (roleBounds == null) {
                continue;
            }
            boolean contains = roleBounds.contains(x, y);
            if (contains) {
                return role;
            }
        }
        return null;
    }

    /**
     * 获取x值
     *
     * @param role role
     * @return f
     */
    public static float getLocationX(Object role) {

        float locationX = 0;
        if (role instanceof ILocation) {
            locationX += ((ILocation) role).getLocationX();
        }
        return locationX;
    }

    /**
     * 获取y值
     *
     * @param role role
     * @return f
     */
    public static float getLocationY(Object role) {

        float locationY = 0;
        if (role instanceof ILocation) {
            locationY += ((ILocation) role).getLocationY();
        }
        return locationY;
    }

    /**
     * 获取最大范围
     *
     * @param role role
     * @return
     */
    @Nullable
    public static RectF getBounds(Role role) {

        if (role instanceof IBounds) {
            return ((IBounds) role).getBounds();
        }
        return null;
    }

    /**
     * 获取x值
     *
     * @param role role
     * @return f
     */
    public static void setLocationX(Role role, float x) {

        if (role instanceof ILocationUpdate) {
            ((ILocationUpdate) role).setLocationX(x);
        }
    }

    /**
     * 获取x值
     *
     * @param role role
     * @return f
     */
    public static void setLocationY(Role role, float y) {

        if (role instanceof ILocationUpdate) {
            ((ILocationUpdate) role).setLocationY(y);
        }
    }

    public static void offset(Role role, float deltaX, float deltaY) {

        if (role instanceof ILocationUpdate) {
            ((ILocationUpdate) role).offset(deltaX, deltaY);
        }
    }



    public static boolean isCollision(Role src, Role dest) {

        if(!(src instanceof ICollision)){
            return false;
        }

        if(!(dest instanceof ICollision)){
            return false;
        }

        RectF bounds1 = ((ICollision) src).getBounds();
        if (bounds1.isEmpty()) {
            return false;
        }
        RectF bounds2 = ((ICollision) dest).getBounds();
        if (bounds2.isEmpty()) {
            return false;
        }
        return RectF.intersects(bounds1, bounds2);
    }
}
