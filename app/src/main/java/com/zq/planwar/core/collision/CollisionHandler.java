package com.zq.planwar.core.collision;

import com.zq.planwar.role.ext.ICollision;

/**
 * Created by zhangqiang on 2018/9/27.
 */
public interface CollisionHandler {

    void onHandCollision(ICollision target1, ICollision target2);
}
