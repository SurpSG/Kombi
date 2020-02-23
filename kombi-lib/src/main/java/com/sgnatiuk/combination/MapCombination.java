package com.sgnatiuk.combination;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

class MapCombination<K, V> extends AbstractCombination<Map<K, V>> {

    private final Map<K, V> originData;

    protected MapCombination(Map<K, V> originData) {
        this(
                originData,
                new Range(1, CombinationKt.calculateCombinationsNumber(originData.size()))
        );
    }

    protected MapCombination(Map<K, V> originData, Range range) {
        super(range);
        this.originData = originData;
    }


    @Override
    Combination<Map<K, V>> subCombination(Range range) {
        return new MapCombination<>(originData, range);
    }

    @NotNull
    @Override
    public Iterator<Map<K, V>> iterator() {
        return new BinaryMaskIterator<>(
                range,
                new MapBuilder<>(
                        originData,
                        new ArrayList<>(originData.keySet())
                )
        );
    }
}
