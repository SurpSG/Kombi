package com.sgnatiuk.combination

import com.sgnatiuk.Splittable
import com.sgnatiuk.extensions.pow
import com.sgnatiuk.extensions.rangeLength
import com.sgnatiuk.extensions.split

interface Combination<T> : Iterable<T>, Splittable<Combination<T>> {
    val combinationsNumber: Long
}

internal abstract class AbstractCombination<T>(
        protected val range: LongRange
) : Combination<T> {

    override val combinationsNumber = range.rangeLength()

    override fun split(n: Int): List<Combination<T>> {
        return range.split(n).map {
            subCombination(it)
        }
    }

    internal abstract fun subCombination(range: LongRange) : Combination<T>
}

internal fun Int.calculateCombinationsNumber() : Long {
    return 2.pow(this) - 1
}
