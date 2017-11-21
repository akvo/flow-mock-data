package org.akvo.mockcaddisfly.data;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Random;

public class RandomTestResultGenerator {

    public static final Double DEFAULT_VALUE = 0.0;

    public RandomTestResultGenerator() {
    }

    @NonNull
    public Double getResultValue(String ranges) {
        if (TextUtils.isEmpty(ranges)) {
            return DEFAULT_VALUE;
        } else {
            String[] split = ranges.split(",");
            if (split.length == 0) {
                return DEFAULT_VALUE;
            } else if (split.length == 1) {
                return Double.parseDouble(split[0]);
            } else {
                double max = Double.parseDouble(split[1]);
                double randomDouble = new Random().nextInt((int) max);
                double min = Double.parseDouble(split[0]);
                randomDouble = Math.min(Math.max(randomDouble, min), max);
                return randomDouble;
            }
        }
    }
}