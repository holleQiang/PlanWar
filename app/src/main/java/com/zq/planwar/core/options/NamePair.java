package com.zq.planwar.core.options;

public class NamePair {

    private String group;
    private String name;

    public NamePair(String group, String name) {
        this.group = group;
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getGroup() + getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamePair) {
            String group = ((NamePair) obj).getGroup();
            String name = ((NamePair) obj).getName();
            return group != null && name != null && group.equals(getGroup())
                    && name.equals(getName());
        }
        return false;
    }
}
