package com.zq.planwar.core.options;

import com.zq.planwar.utils.SharedPreferenceUtils;

public abstract class BaseOptions<T> {

    private NamePair mNamePair;
    private T defaultValue;

    public BaseOptions(String group, String name, T defaultValue) {
        mNamePair = new NamePair(group, name);
        this.defaultValue = defaultValue;
    }

    public void put(T value) {
        SharedPreferenceUtils.save(mNamePair,convertToString(value));
    }

    protected abstract String convertToString(T value);

    protected abstract T parseToValue(String s);

    public T get() {
        return parseToValue(SharedPreferenceUtils.get(mNamePair,convertToString(defaultValue)));
    }
}
