package com.xma.task2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TextTransformer {
    public static String transform(String text) {
        return Arrays.stream(text.split("[^a-zA-Zа-яА-Я]+"))
                .filter(x -> x.length() >= 3)
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .collect(Collectors.joining(" "));
    }
}
