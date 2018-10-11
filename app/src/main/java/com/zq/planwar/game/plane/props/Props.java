package com.zq.planwar.game.plane.props;

/**
 * Created by zhangqiang on 2018/9/26.
 */
public class Props {

    private int type;
    private String extra;

    public Props(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
