package com.sgnatiuk.extensions

import java.math.BigInteger
import kotlin.math.pow

val Int.BigInt
    get() : BigInteger = BigInteger.valueOf(this.toLong())

fun Int.pow(n: Int) : Long = this.toDouble().pow(n).toLong()

fun Long.bitCount(): Int = java.lang.Long.bitCount(this)