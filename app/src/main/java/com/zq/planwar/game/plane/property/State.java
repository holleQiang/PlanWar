package com.zq.planwar.game.plane.property;

/**
 * Created by zhangqiang on 2018/9/26.
 */
public final class State {

    private int type;

    private String info;

    public State(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
