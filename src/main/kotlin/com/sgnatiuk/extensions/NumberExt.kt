package com.sgnatiuk.extensions

import java.math.BigInteger

val Int.BigInt
    get() : BigInteger = BigInteger.valueOf(this.toLong())