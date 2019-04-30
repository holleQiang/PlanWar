package com.zq.planwar.core.options;

public class IntergerRangeOptions extends BaseOptions<Integer> {

    private int maxValue;
    private int minValue;

    public IntergerRangeOptions(String group, String name, Integer defaultValue, int maxValue, int minValue) {
        super(group, name, computeValue(defaultValue,maxValue,minValue));
        this.maxValue = maxValue;
        this.minValue = minValue;
    }


    @Override
    protected String convertToString(Integer value) {
        return String.valueOf(computeValue(value,maxValue,minValue));
    }

    @Override
    protected Integer parseToValue(String s) {
        return Integer.valueOf(s);
    }

    private static int computeValue(int value,int maxValue,int minValue){
        return Math.max(Math.min(value,maxValue),minValue);
    }
}
