package com.zq.planwar.core.options;

public class BooleanOptions extends BaseOptions<Boolean> {

    public BooleanOptions(String group, String name, Boolean defaultValue) {
        super(group, name, defaultValue);
    }

    @Override
    protected String convertToString(Boolean value) {
        return value.toString();
    }

    @Override
    protected Boolean parseToValue(String s) {
        return Boolean.valueOf(s);
    }
}
