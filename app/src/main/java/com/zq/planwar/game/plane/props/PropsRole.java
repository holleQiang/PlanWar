package com.zq.planwar.game.plane.props;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.ext.AppearanceHolder;
import com.zq.planwar.game.plane.property.HP;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.ext.ICollision;
import com.zq.planwar.role.ext.ILife;

/**
 * Created by zhangqiang on 2018/9/25.
 */
public class PropsRole extends Role implements ILife,AppearanceHolder,ICollision{

    private Appearance appearance;
    private HP hp = new HP(1,1,1);
    private Props props;

    public PropsRole(GameContext context,Appearance appearance) {
        super(context);
        this.appearance = appearance;
    }

    @Override
    protected void onDraw(Canvas canvas, GameContext gameContext) {
        super.onDraw(canvas, gameContext);
        if(appearance != null){
            appearance.draw(canvas, gameContext);
        }
    }

    @Override
    public boolean isAlive() {
        return hp.getHp() >= hp.getMinHP();
    }

    @Override
    public void kill() {
        destroy();
    }

    @Override
    protected void onDestroyed() {
        super.onDestroyed();
        AppearanceUtils.release(getAppearance());
    }

    @Override
    public Appearance getAppearance() {
        return appearance;
    }

    @NonNull
    @Override
    public RectF getBounds() {
        return AppearanceUtils.getBounds(getAppearance());
    }

    public Props getProps() {
        return props;
    }

    public void setProps(Props props) {
        this.props = props;
    }
}
