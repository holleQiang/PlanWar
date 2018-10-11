package com.zq.planwar.game.plane;

import android.support.annotation.Nullable;

import com.zq.planwar.role.Role;

/**
 * Created by zhangqiang on 2018/9/26.
 */
public class PlaneUtils {

    @Nullable
    public static Airplane findPlane(Object o) {

        if (!(o instanceof Role)) {
            return null;
        }
        Role temp = (Role) o;
        while (temp != null) {

            if (temp instanceof Airplane) {
                return (Airplane) temp;
            }
            temp = temp.getParentRole();
        }
        return null;
    }
}
