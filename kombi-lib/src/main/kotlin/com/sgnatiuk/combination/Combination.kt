package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow

interface Combination<T> : Iterable<T> {
    val combinationsNumber: Int
}

internal abstract class AbstractCombination<T>(
        originDataSize: Int
) : Combination<T> {
    override val combinationsNumber = 2.pow(originDataSize) - 1
}