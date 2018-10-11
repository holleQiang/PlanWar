package com.zq.planwar.game.plane.property;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public final class CollingTime {

    private long minCollingTime,maxCollingTime;

    public CollingTime(long minCollingTime, long maxCollingTime) {
        this.minCollingTime = minCollingTime;
        this.maxCollingTime = maxCollingTime;
    }

    public long getMinCollingTime() {
        return minCollingTime;
    }

    public void setMinCollingTime(long minCollingTime) {
        this.minCollingTime = minCollingTime;
    }

    public long getMaxCollingTime() {
        return maxCollingTime;
    }

    public void setMaxCollingTime(long maxCollingTime) {
        this.maxCollingTime = maxCollingTime;
    }

    public long getCurrentCollingTime(float factor){
        return (long) (getMaxCollingTime() - (getMaxCollingTime() - getMinCollingTime()) * factor);
    }
}
