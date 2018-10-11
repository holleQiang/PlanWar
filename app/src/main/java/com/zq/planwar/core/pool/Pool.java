package com.zq.planwar.core.pool;

import android.support.annotation.Nullable;

import com.zq.planwar.game.plane.equipment.bullet.Bullet;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zhangqiang on 2018/9/30.
 */
public class Pool {

    private static final Pool instance = new Pool();

    private Pool() {
    }

    public static Pool getInstance() {
        return instance;
    }

    private Queue<Bullet> bulletQueue = new LinkedList<>();


    public void recycleBullet(Bullet bullet){
//        bulletQueue.offer(bullet);
    }

    @Nullable
    public Bullet takeBullet(){
//        return bulletQueue.poll();
        return null;
    }
}
