package com.sgnatiuk.cartesian

import java.math.BigInteger

interface CartesianProduct<T> : Iterable<T> {
    val combinationsCount: BigInteger
}
