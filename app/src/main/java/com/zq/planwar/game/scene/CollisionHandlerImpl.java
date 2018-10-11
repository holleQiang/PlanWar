package com.zq.planwar.game.scene;

import com.zq.planwar.appearance.AppearanceUtils;
import com.zq.planwar.core.context.GameContext;
import com.zq.planwar.core.collision.CollisionHandler;
import com.zq.planwar.game.plane.Airplane;
import com.zq.planwar.game.plane.PlaneUtils;
import com.zq.planwar.game.plane.equipment.Equipment;
import com.zq.planwar.game.plane.equipment.gun.Gun;
import com.zq.planwar.game.plane.property.HP;
import com.zq.planwar.game.plane.property.Level;
import com.zq.planwar.game.plane.property.State;
import com.zq.planwar.game.plane.props.Props;
import com.zq.planwar.game.plane.props.PropsFactory;
import com.zq.planwar.game.plane.props.PropsRole;
import com.zq.planwar.role.Role;
import com.zq.planwar.role.RoleUtils;
import com.zq.planwar.role.effect.EffectUtils;
import com.zq.planwar.role.ext.IAttacker;
import com.zq.planwar.role.ext.ICollision;
import com.zq.planwar.role.ext.IDefender;

import javax.inject.Inject;

/**
 * Created by zhangqiang on 2018/9/27.
 */
public class CollisionHandlerImpl implements CollisionHandler {

    private GameContext context;

    @Inject
    public CollisionHandlerImpl(GameContext context) {
        this.context = context;
    }

    @Override
    public void onHandCollision(ICollision target1, ICollision target2) {

        if (target1 instanceof IAttacker && target2 instanceof IDefender) {

            IAttacker attacker = (IAttacker) target1;
            IDefender defender = (IDefender) target2;
            handAttack(attacker, defender);
        }
        if (target1 instanceof IDefender && target2 instanceof IAttacker) {

            IAttacker attacker = (IAttacker) target2;
            IDefender defender = (IDefender) target1;
            handAttack(attacker, defender);
        }
        if (target1 instanceof Airplane && target2 instanceof PropsRole) {
            handProps((Airplane) target1, (PropsRole) target2);
        }
        if (target1 instanceof PropsRole && target2 instanceof Airplane) {
            handProps((Airplane) target2, (PropsRole) target1);
        }
    }

    private void handAttack(IAttacker attacker, IDefender defender) {

        if (attacker instanceof Role && defender instanceof Role) {
            Object attackerTag = RoleUtils.findTag((Role) attacker);
            Object defenderTag = RoleUtils.findTag((Role) defender);
            if (attackerTag != null && attackerTag.equals(defenderTag)) {
                return;
            }
        }

        long attackValue = attacker.attackValue();
        HP hp = defender.getHp();
        hp.reduce(attackValue);
        if (hp.getHp() < hp.getMinHP()) {
            EffectUtils.boom(context, (Role) defender);

            Airplane airplane = PlaneUtils.findPlane(attacker);
            if (airplane != null) {
                AppearanceUtils.pushStateToDecorator(airplane, new State(0, "击杀+1"));
            }
        }
    }

    private void handProps(Airplane airplane, PropsRole propsRole) {

        if (!propsRole.isAlive()) {
            return;
        }
        Props props = propsRole.getProps();
        if (props == null) {
            return;
        }
        int type = props.getType();
        switch (type) {
            case PropsFactory.TYPE_WEAPON_LEVEL_UP:
                Equipment weapon = airplane.getWeapon();
                if (weapon instanceof Gun) {
                    Level level = ((Gun) weapon).getLevel();
                    level.setLevel(level.getLevel() + 1);
                    propsRole.kill();

                    AppearanceUtils.pushStateToDecorator(airplane, new State(1, "武器升级"));
                }
                break;
            case PropsFactory.TYPE_HP_ADD_P_50:
                propsRole.kill();
                HP hp = airplane.getHp();
                hp.addPercent50();
                AppearanceUtils.pushStateToDecorator(airplane, new State(2, "生命+50%"));
                break;
            case PropsFactory.TYPE_HP_FULL:
                propsRole.kill();
                hp = airplane.getHp();
                hp.full();
                AppearanceUtils.pushStateToDecorator(airplane, new State(2, "生命+100%"));
                break;
        }

    }
}
