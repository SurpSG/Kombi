package com.sgnatiuk.combination;

import com.sgnatiuk.Splittable;

import java.util.stream.Stream;

public interface Combination<T> extends Iterable<T>, Splittable<Combination<T>> {
    long combinationsNumber();
    Stream<T> stream();
}
