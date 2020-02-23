package com.sgnatiuk.combination;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

abstract class AbstractCombination<T> implements Combination<T> {

    final Range range;

    protected AbstractCombination(Range range) {
        this.range = range;
    }

    @Override
    public List<Combination<T>> split(int n) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        range.split(n).iterator(),
                        Spliterator.ORDERED
                ),
                false
        ).map(this::subCombination).collect(Collectors.toList());
    }

    abstract Combination<T> subCombination(Range range);

    @Override
    public long combinationsNumber() {
        return range.length();
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(
                new CombinationSpliterator<>(this),
                false
        );
    }

    static long combinationsByItemsCount(int items) {
        return (long) (Math.pow(2, items) - 1);
    }
}

class CombinationSpliterator<T> implements Spliterator<T> {
    private Iterator<T> combinationIterator;
    private Combination<T> combination;

    CombinationSpliterator(Combination<T> combination) {
        this.combination = combination;
        combinationIterator = this.combination.iterator();
    }

    public long estimateSize() {
        return combination.combinationsNumber();
    }

    public int characteristics() {
        return Spliterator.SIZED | Spliterator.SUBSIZED
                | Spliterator.CONCURRENT | Spliterator.IMMUTABLE | Spliterator.ORDERED;
    }

    public boolean tryAdvance(Consumer<? super T> action) {
        if (combinationIterator.hasNext()) {
            action.accept(this.combinationIterator.next());
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public Spliterator<T> trySplit() {
        List<Combination<T>> combinationParts = combination.split(2);
        this.combination = combinationParts.get(1);
        this.combinationIterator = combination.iterator();
        return new CombinationSpliterator<>(combinationParts.get(0));
    }


}
