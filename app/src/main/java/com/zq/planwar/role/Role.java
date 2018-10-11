package com.zq.planwar.role;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.zq.planwar.core.context.GameContext;

import java.util.Collection;

/**
 * Created by zhangqiang on 2018/9/12.
 */
public class Role {

    //孩子初始容量
    private static final int INIT_CHILD_CAPACITY = 100;
    //孩子容器增量
    private static final int CHILD_CAPACITY_INCREASE = 100;
    //孩子角色
    private Role[] childRoles = new Role[INIT_CHILD_CAPACITY];
    //孩子数量
    private int mChildCount;
    //父角色
    private Role parentRole;
    //是否被销毁
    private boolean isDestroyed;
    //标记位
    private Object tag;
    //role tree变化监听
    private OnRoleTreeChangeListener onRoleTreeChangeListener;

    public final void draw(@NonNull Canvas canvas, GameContext gameContext) {

        clearDestroyedChild();

        onDraw(canvas,gameContext);

        dispatchDraw(canvas,gameContext);
    }

    protected void onDraw(Canvas canvas, GameContext gameContext) {

    }

    protected final void dispatchDraw(Canvas canvas, GameContext gameContext) {

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            Role role = getChildAt(i);
            role.draw(canvas,gameContext);
        }
    }


    public Role getParentRole() {
        return parentRole;
    }

    private void setParentRole(Role parentRole) {
        if(this.parentRole != null && parentRole == null){
            onDetachFromParent();
        }else if(this.parentRole != parentRole){
            onAttachToParent();
        }
        this.parentRole = parentRole;
    }

    protected void onDetachFromParent() {

    }

    protected void onAttachToParent() {

    }

    public final void notifyRoleTreeChange() {

        Role parent = getParentRole();
        if (parent != null) {
            parent.notifyRoleTreeChange();
        }
        if(onRoleTreeChangeListener != null){
            onRoleTreeChangeListener.onRoleTreeChange();
        }
        onRoleTreeChange();
    }

    public void  onRoleTreeChange(){

    }

    /**
     * 添加进数组
     *
     * @param index index
     * @param role  role
     * @return 添加的数量
     */
    private int addInArray(int index, Role role) {

        if (index < 0 || index > mChildCount) {
            return -1;
        }
        final int length = childRoles.length;
        if (mChildCount == length - 1) {

            final Role[] oldRoles = childRoles;
            childRoles = new Role[length + CHILD_CAPACITY_INCREASE];
            System.arraycopy(oldRoles, 0, childRoles, 0, length);
        }
        if (index == mChildCount) {
            childRoles[index] = role;
        } else {

            System.arraycopy(childRoles, index, childRoles, index + 1, mChildCount - 1 - index);
            childRoles[index] = role;
        }
        mChildCount++;
        role.setParentRole(this);
        return 1;
    }

    /**
     * 从数组移除
     *
     * @param index index
     * @return 移除的数量
     */
    private Role removeFromArray(int index) {

        if (index < 0 || index > mChildCount - 1) {
            return null;
        }
        Role child = childRoles[index];
        child.setParentRole(null);
        if (index == mChildCount - 1) {
            childRoles[--mChildCount] = null;
        } else {
            System.arraycopy(childRoles, index + 1, childRoles, index, mChildCount - 1 - index);
            childRoles[--mChildCount] = null;
        }
        return child;
    }

    /**
     * 从数组移除
     *
     * @param start index 起点
     * @param count 移除的数量
     * @return 移除的数量
     */
    private int removeFromArray(int start, int count) {

        start = Math.max(0, start);
        int end = Math.min(mChildCount, start + count);

        if (start <= end) {
            return 0;
        }

        if (end == mChildCount) {

            for (int i = start; i < end; i++) {
                childRoles[i].setParentRole(null);
                childRoles[i] = null;
            }
        } else {

            for (int i = start; i < end; i++) {
                childRoles[i].setParentRole(null);
            }

            System.arraycopy(childRoles, end, childRoles, start, mChildCount - end);

            for (int i = mChildCount; i < mChildCount - (end - start); i++) {
                childRoles[i] = null;
            }
        }
        mChildCount -= (end - start);
        return end - start;
    }

    private int indexOfChild(Role role) {

        if (role == null) {
            return -1;
        }

        for (int i = 0; i < mChildCount; i++) {

            if (role.equals(childRoles[i])) {
                return i;
            }
        }
        return -1;
    }

    public int getChildCount() {
        return mChildCount;
    }

    public void addChild(Role role) {

        int count = addInArray(mChildCount, role);
        if (count > 0) {
            notifyRoleTreeChange();
        }
    }

    public void addChild(Role[] roles) {
        if (roles == null || roles.length <= 0) {
            return;
        }
        for (Role role : roles) {
            addChild(role);
        }
    }

    public void addChildList(Collection<Role> roles) {

        if (roles == null || roles.isEmpty()) {
            return;
        }
        for (Role role : roles) {
            addChild(role);
        }
    }

    public void removeAllChild() {
        int count = removeFromArray(0, mChildCount);
        if (count > 0) {
            notifyRoleTreeChange();
        }
    }

    public void removeChild(Role child) {

        Role role = removeFromArray(indexOfChild(child));
        if (role != null) {
            notifyRoleTreeChange();
        }
    }

    public Role removeChildAt(int index) {

        Role role = removeFromArray(index);
        if (role != null) {
            notifyRoleTreeChange();
        }
        return role;
    }

    public void removeChild(int start, int count) {

        int removeCount = removeFromArray(start, count);
        if (removeCount > 0) {
            notifyRoleTreeChange();
        }
    }

    public Role getChildAt(int position) {

        return getChildInArray(position);
    }

    private Role getChildInArray(int index) {

        if (index < 0 || index >= mChildCount) {
            return null;
        }
        return childRoles[index];
    }

    public Role[] getAllChildRoles() {

        int childCount = getChildCount();

        Role[] roles = new Role[childCount];

        for (int i = 0; i < childCount; i++) {
            Role child = getChildAt(i);
            roles[i] = child;
        }
        return roles;
    }

    public final void destroy() {
        if(isDestroyed){
            return;
        }
        onDestroy();
        isDestroyed = true;
    }

    protected void onDestroy() {
        destroyChildren();
    }

    private void destroyChildren(){
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Role child = getChildAt(i);
            child.destroy();
        }
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * 清除没有父亲的role
     */
    private void clearDestroyedChild(){

        for (int i = 0; i < getChildCount(); i++) {
            Role role = getChildAt(i);
            if (role.isDestroyed()) {
                removeChild(role);
            }
        }
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public interface OnRoleTreeChangeListener{

        void onRoleTreeChange();
    }

    public void setOnRoleTreeChangeListener(OnRoleTreeChangeListener onRoleTreeChangeListener) {
        this.onRoleTreeChangeListener = onRoleTreeChangeListener;
    }

    public void reset(){
        setParentRole(null);
        isDestroyed = false;
    }
}
