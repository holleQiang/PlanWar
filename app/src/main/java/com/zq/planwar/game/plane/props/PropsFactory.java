package com.zq.planwar.game.plane.props;

/**
 * Created by zhangqiang on 2018/9/26.
 */
public class PropsFactory {

    public static final int TYPE_WEAPON_LEVEL_UP = 0;
    public static final int TYPE_HP_ADD_P_50 = 1;
    public static final int TYPE_HP_FULL = 2;

    public static Props weaponLevelUp(){

        return new Props(TYPE_WEAPON_LEVEL_UP);
    }

    public static Props hpp50(){

        return new Props(TYPE_HP_ADD_P_50);
    }

    public static Props hpFull(){

        return new Props(TYPE_HP_FULL);
    }
}
