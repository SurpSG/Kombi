package com.sgnatiuk.combination;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
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
}
