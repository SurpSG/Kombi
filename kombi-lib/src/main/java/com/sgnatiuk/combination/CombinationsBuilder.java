package com.sgnatiuk.combination;

import java.util.List;
import java.util.Map;

public class CombinationsBuilder {

    private CombinationsBuilder() {
    }

    public static <K, V> Combination<Map<K, V>> combinationsOf(Map<K, V> map) {
        return new MapCombination<>(map);
    }

    public static <T> Combination<List<T>> combinationsOf(List<T> list) {
        return new ListCombination<>(list);
    }
}