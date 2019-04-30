package com.zq.planwar.core.options;

public class FloatOptions extends BaseOptions<Float> {

    public FloatOptions(String group, String name, Float defaultValue) {
        super(group, name, defaultValue);
    }

    @Override
    protected String convertToString(Float value) {
        return value.toString();
    }

    @Override
    protected Float parseToValue(String s) {
        return Float.valueOf(s);
    }
}
