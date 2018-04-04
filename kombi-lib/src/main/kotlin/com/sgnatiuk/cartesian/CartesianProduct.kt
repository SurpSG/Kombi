package com.sgnatiuk.cartesian

import java.math.BigInteger
//TODO implement spliterator for streams
interface CartesianProduct<T> : Iterable<T> {
    val combinationsCount: BigInteger
}
