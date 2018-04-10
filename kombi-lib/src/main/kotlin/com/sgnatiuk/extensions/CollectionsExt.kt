package com.sgnatiuk.extensions

import java.math.BigInteger

inline fun <T> Iterable<T>.multiplyAll(intValue: T.() -> Int ) : BigInteger {
    var temp = 1.BigInt
    var collectionEmpty = true
    this.forEach {
        collectionEmpty = false
        temp *= it.intValue().BigInt
    }
    return if (collectionEmpty) 0.BigInt else temp
}
