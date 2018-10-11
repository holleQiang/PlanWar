package com.zq.planwar.game.plane.property;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public final class Level {

    private int level,maxLevel,minLevel;

    public Level(int level, int maxLevel, int minLevel) {
        this.level = level;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(getMinLevel(),Math.min(getMaxLevel(),level));
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public float getFactor(){
        return Math.min(1,(float) getLevel() / (getMaxLevel() - getMinLevel()));
    }
}
