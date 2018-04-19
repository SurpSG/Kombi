package com.sgnatiuk.cartesian

import com.sgnatiuk.Splittable
import java.math.BigInteger
//TODO implement spliterator for streams
interface CartesianProduct<T> : Iterable<T>, Splittable<CartesianProduct<T>> {
    val combinationsCount: BigInteger
}
