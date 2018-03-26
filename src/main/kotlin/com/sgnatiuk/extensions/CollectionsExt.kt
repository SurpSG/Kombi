package com.sgnatiuk.extensions

import java.math.BigInteger

fun <K, V> Map<K, V>.getOrThrow(key: K) : V = this[key]!!

inline fun <T> Iterable<T>.multiplyAll(intValue: T.() -> Int ) : BigInteger {
    var temp = 1.BigInt
    this.forEach {
        temp *= it.intValue().BigInt
    }
    return temp
}
