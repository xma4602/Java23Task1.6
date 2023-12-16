package com.xma.task1;

import java.util.NoSuchElementException;
import java.util.stream.DoubleStream;

public class DoubleStreamProcessor {
    public static long zerosCount(DoubleStream stream) {
        return stream.filter(x -> x == 0.0).count();
    }

    public static boolean hasFractionalNumber(DoubleStream stream) {
        return stream.anyMatch(x -> x % 1 != 0.0);
    }

    public static double rangeOfValues(DoubleStream stream) {
        var stat = stream.summaryStatistics();
        if (stat.getCount() == 0) {
            throw new EmptyStreamException();
        }
        return Math.abs(stat.getMax() - stat.getMin());
    }

    public static double[] biggerThenLimit(DoubleStream stream, double limit) {
        return stream.filter(x -> x > limit).toArray();
    }

    public static double mostLongString(DoubleStream stream) {
        return stream
                .mapToObj(Double::toString)
                .reduce((s1, s2) -> s1.length() >= s2.length() ? s1 : s2)
                .map(Double::valueOf)
                .orElseThrow(EmptyStreamException::new);
    }

    private static class EmptyStreamException extends NoSuchElementException {
        public EmptyStreamException() {
            super("В потоке ни одного элемента");
        }
    }
}
