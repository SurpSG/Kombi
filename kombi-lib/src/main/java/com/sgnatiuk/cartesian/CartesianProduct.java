package com.sgnatiuk.cartesian;

import com.sgnatiuk.Splittable;

import java.math.BigInteger;
import java.util.stream.Stream;

public interface CartesianProduct<T> extends Iterable<T>, Splittable<CartesianProduct<T>> {
    BigInteger combinationsCount();
    Stream<T> stream();
}
