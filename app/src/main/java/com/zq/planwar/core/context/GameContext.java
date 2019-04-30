package com.zq.planwar.core.context;

import android.graphics.drawable.Drawable;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.role.Role;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public abstract class GameContext {

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract Drawable getDrawable(int drawableResID);

    public abstract long getCurrentTime();

    public boolean isOutOfVisibleRect(Role role) {
        Appearance appearance = role.getAppearance();
        if (appearance == null) {
            return false;
        }
        return appearance.getRight() < 0 || appearance.getLeft() > getWidth()
                || appearance.getTop() > getHeight() || appearance.getBottom() < 0;
    }

    public abstract float getXScaleFactor();

    public abstract float getYScaleFactor();
}
