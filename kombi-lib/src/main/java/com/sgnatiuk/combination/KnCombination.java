package com.sgnatiuk.combination;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static com.sgnatiuk.combination.MathUtils.binomial;

class KnCombination<T> implements Combination<List<T>> {

    private final int combinationSize;
    private final T[] items;

    KnCombination(List<T> items, int combinationSize) {
        validateParameters(items.size(), combinationSize);
        this.items = (T[]) items.toArray();
        this.combinationSize = combinationSize;
    }

    private void validateParameters(int itemsNumber, int combinationSize) {
        if (combinationSize > itemsNumber || combinationSize < 1) {
            throw new IllegalArgumentException(String.format(
                    "Arguments must be in rage '0 < combinationSize <= listSize' but combinationSize=%s, listSize=%s'",
                    combinationSize,
                    itemsNumber
            ));
        }
    }

    @Override
    public List<Combination<List<T>>> split(int n) {
        return null;
    }

    @Override
    public long combinationsNumber() {
        return binomial(items.length, combinationSize);
    }

    @Override
    public Stream<List<T>> stream() {
        return null;
    }

    @Override
    public Iterator<List<T>> iterator() {
        return new KnCombinationIterator<>(items, combinationSize);
    }

}
