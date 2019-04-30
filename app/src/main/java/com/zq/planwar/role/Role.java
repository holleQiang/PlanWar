package com.zq.planwar.role;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.zq.planwar.appearance.Appearance;
import com.zq.planwar.appearance.AppearanceCallback;
import com.zq.planwar.core.context.GameContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public abstract class Role {

    private final List<Role> childList = new ArrayList<>();
    private Role parent;
    private GameContext context;
    private Appearance appearance;

    public Role(@NonNull GameContext context) {
        this.context = context;
    }

    public void addChild(Role role) {
        if (role == null) {
            return;
        }
        childList.add(role);
        role.attachToParent(this);
    }

    public void addChildList(List<Role> childList) {
        if (childList == null || childList.isEmpty()) {
            return;
        }
        this.childList.addAll(childList);
        for (Role role : childList) {
            role.attachToParent(this);
        }
    }

    public void removeChild(Role role) {
        if (role == null) {
            return;
        }
        this.childList.remove(role);
        role.detachFromParent(this);
    }

    public List<Role> getChildList() {
        return Collections.unmodifiableList(childList);
    }

    public int getChildCount() {
        return this.childList.size();
    }

    public Role getParent() {
        return parent;
    }

    public void draw(Canvas canvas) {
        clean();
        onDraw(canvas, getContext());
        dispatchDraw(canvas);
    }

    protected void dispatchDraw(Canvas canvas) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            Role child = getChildAt(i);
            child.draw(canvas);
        }
    }

    protected void onDraw(Canvas canvas, GameContext context) {
        if (appearance != null) {
            appearance.draw(canvas,getContext());
        }
    }

    public Role getChildAt(int index) {
        return this.childList.get(index);
    }


    public final void destroy() {
        detachFromParent(getParent());
        onDestroyed();
    }


    protected void onDestroyed() {

    }

    public final void invalidate() {
        onInvalidate();
        notifyParentChildRequestInvalidate(this);
    }

    protected void onInvalidate() {

    }

    private static void notifyParentChildRequestInvalidate(Role role){

        Role parent;
        while ((parent = role.getParent()) != null){
            parent.childRequestInvalidate(role);
            role = parent;
        }
    }

    private void childRequestInvalidate(Role child) {
        onChildRequestInvalidate(child);
    }

    protected void onChildRequestInvalidate(Role child) {
        Role parent = getParent();
        if (parent == null) {
            return;
        }
        parent.childRequestInvalidate(this);
    }

    public GameContext getContext() {
        return context;
    }

    private void attachToParent(Role parent) {
        this.parent = parent;
        invalidate();
    }

    private void detachFromParent(Role parent) {
        this.parent = null;
    }

    public final void clean() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            Role child = getChildAt(i);
            if (child.getParent() == null) {
                removeChild(child);
            }
        }
    }

    public RootRole getRootRole() {
        Role parent = getParent();
        while (parent != null) {
            if (parent instanceof RootRole) {
                return (RootRole) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
        if (this.appearance != null) {
            this.appearance.setCallback(new AppearanceCallback() {
                @Override
                public void onInvalidate() {
                    invalidate();
                }
            });
        }
    }
}
