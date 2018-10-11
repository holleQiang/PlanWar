package com.zq.planwar.game.plane.property;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public final class HP {

    private long hp, maxHP, minHP;

    public HP(long hp, long maxHP, long minHP) {
        this.hp = hp;
        this.maxHP = maxHP;
        this.minHP = minHP;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = Math.min(getMaxHP(), hp);
    }

    public long getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(long maxHP) {
        this.maxHP = maxHP;
    }

    public long getMinHP() {
        return minHP;
    }

    public void setMinHP(long minHP) {
        this.minHP = minHP;
    }

    public void reduce(long hp) {
        setHp(getHp() - hp);
    }

    public void addPercent10() {
        setHp((long) (getHp() * 1.1f));
    }

    public void full() {
        setHp(getMaxHP());
    }

    public void addPercent50() {
        setHp((long) (getHp() * 1.5f));
    }

    public boolean isHealthy(){
        return getHp() >= getMinHP();
    }
}
