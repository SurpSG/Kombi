package com.sgnatiuk.combination;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class ListCombination<T> extends AbstractCombination<List<T>> {

    private final List<T> originData;

    protected ListCombination(List<T> originData) {
        this(
                originData,
                new Range(1, combinationsByItemsCount(originData.size()))
        );
    }

    protected ListCombination(List<T> originData, Range range) {
        super(range);
        this.originData = originData;
    }

    @Override
    Combination<List<T>> subCombination(Range range) {
        return new ListCombination<>(originData, range);
    }

    @NotNull
    @Override
    public Iterator<List<T>> iterator() {
        return new BinaryMaskIterator<>(
                range,
                new CollectionBuilder<List<T>>() {
                    @Override
                    public List<T> newCollection(int initialCapacity) {
                        return new ArrayList<>(initialCapacity);
                    }

                    @Override
                    public void addItemByIndex(List<T> collection, int index) {
                        collection.add(originData.get(index));
                    }
                }
        );
    }
}
