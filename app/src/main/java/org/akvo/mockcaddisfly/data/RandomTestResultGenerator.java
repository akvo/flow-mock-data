package org.akvo.mockcaddisfly.data;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Random;

import timber.log.Timber;

public class RandomTestResultGenerator {

    public static final Double DEFAULT_VALUE = 0.0;

    public RandomTestResultGenerator() {
    }

    @NonNull
    public Double getResultValue(String ranges) {
        Timber.i("ranges "+ranges);
        if (TextUtils.isEmpty(ranges)) {
            return DEFAULT_VALUE;
        } else {
            String[] split = ranges.split(",");
            if (split.length == 0) {
                return DEFAULT_VALUE;
            } else if (split.length == 1) {
                return Double.parseDouble(split[0]);
            } else {
                double max = Double.parseDouble(split[split.length - 1]);
                int bound = (int) Math.max(max, 1); //cannot be less than 1
                double randomDouble = new Random().nextInt(bound);
                double min = Double.parseDouble(split[0]);
                randomDouble = Math.min(Math.max(randomDouble, min), max);
                return randomDouble;
            }
        }
    }
}