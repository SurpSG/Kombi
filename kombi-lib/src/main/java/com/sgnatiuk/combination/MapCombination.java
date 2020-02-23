package com.sgnatiuk.combination;

import org.jetbrains.annotations.NotNull;

import java.util.*;

class MapCombination<K, V> extends AbstractCombination<Map<K, V>> {

    private final Map<K, V> originData;

    protected MapCombination(Map<K, V> originData) {
        this(
                originData,
                new Range(1, combinationsByItemsCount(originData.size()))
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
                new CollectionBuilder<Map<K, V>>() {
                    List<K> dataKeys = new ArrayList<>(originData.keySet());

                    @Override
                    public Map<K, V> newCollection(int initialCapacity) {
                        return new HashMap<>(initialCapacity);
                    }

                    @Override
                    public void addItemByIndex(Map<K, V> collection, int index) {
                        K key = dataKeys.get(index);
                        collection.put(key, originData.get(key));
                    }
                }
        );
    }
}
