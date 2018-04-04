package com.sgnatiuk.extensions

import java.math.BigInteger

inline fun <T> Iterable<T>.multiplyAll(intValue: T.() -> Int ) : BigInteger {
    var temp = 1.BigInt
    this.forEach {
        temp *= it.intValue().BigInt
    }
    return temp
}
