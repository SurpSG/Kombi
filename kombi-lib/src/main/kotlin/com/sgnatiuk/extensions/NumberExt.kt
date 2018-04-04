package com.sgnatiuk.extensions

import java.math.BigInteger
import kotlin.math.pow

val Int.BigInt
    get() : BigInteger = BigInteger.valueOf(this.toLong())

fun Int.pow(n: Int) = this.toDouble().pow(n).toInt()

fun Int.bitCount(): Int = Integer.bitCount(this)