package com.zq.planwar.core.options;

public class IntegerOptions extends BaseOptions<Integer> {

    public IntegerOptions(String group, String name, Integer defaultValue) {
        super(group, name, defaultValue);
    }

    @Override
    protected String convertToString(Integer value) {
        return String.valueOf(value);
    }

    @Override
    protected Integer parseToValue(String s) {
        return Integer.valueOf(s);
    }
}
