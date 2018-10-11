package com.zq.planwar.role.effect;

import com.zq.planwar.R;
import com.zq.planwar.appearance.impl.DrawableAppearance;
import com.zq.planwar.appearance.impl.LocationAppearance;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.RoleUtils;

/**
 * Created by zhangqiang on 2018/9/21.
 */
public class EffectUtils {

    public static void boom(GameContext context, Role role) {

        Role parentRole = role.getParentRole();
        parentRole.addChild(new Boom(new LocationAppearance(RoleUtils.getLocationX(role),
                RoleUtils.getLocationY(role),
                new DrawableAppearance(context, R.drawable.boom))));
    }
}
