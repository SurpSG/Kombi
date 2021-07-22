package com.sgnatiuk.combination;

import java.util.List;
import java.util.Map;

public class CombinationsBuilder {

    private CombinationsBuilder() {
    }

    /**
     * Generates all possible combinations of all sizes from map source
     * https://en.wikipedia.org/wiki/Combination
     *
     * @param map - source data from which combinations are generated
     * @param <K> - type of key
     * @param <V> - type of value
     * @return iterable container that generates combination maps
     */
    public static <K, V> Combination<Map<K, V>> combinationsOf(Map<K, V> map) {
        return new MapCombination<>(map);
    }

    /**
     * Generates all possible combinations of all sizes from list source
     * https://en.wikipedia.org/wiki/Combination
     *
     * @param list - source data from which combinations are generated
     * @param <T>  - type of data
     * @return iterable container that generates combination lists
     */
    public static <T> Combination<List<T>> combinationsOf(List<T> list) {
        return new ListCombination<>(list);
    }

    /**
     * Generates all possible combinations with size {@param combinationSize} from list source
     * https://en.wikipedia.org/wiki/Combination
     *
     * @param list            - source data from which combinations are generated
     * @param combinationSize - target combination size
     * @param <T>             - type of data
     * @return iterable container that generates combination lists
     */
    public static <T> Combination<List<T>> combinationsOf(List<T> list, int combinationSize) {
        return new KnCombination<>(list, combinationSize);
    }
}
