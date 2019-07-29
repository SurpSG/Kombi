package com.sgnatiuk.cartesian

import com.sgnatiuk.Splittable
import java.math.BigInteger
import java.util.stream.Stream

interface CartesianProduct<T> : Iterable<T>, Splittable<CartesianProduct<T>> {
    val combinationsCount: BigInteger

    fun stream(): Stream<T>
}
