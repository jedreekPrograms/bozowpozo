package util;

import java.util.List;

public class Statistics {

    public static double mean(List<Double> values) {
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

    public static double min(List<Double> values) {
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0);
    }
}