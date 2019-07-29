package com.sgnatiuk.combination

import com.sgnatiuk.Splittable
import com.sgnatiuk.extensions.pow
import com.sgnatiuk.extensions.rangeLength
import com.sgnatiuk.extensions.split
import java.util.*
import java.util.function.Consumer
import java.util.stream.Stream
import java.util.stream.StreamSupport

interface Combination<T> : Iterable<T>, Splittable<Combination<T>> {
    val combinationsNumber: Long

    fun stream(): Stream<T>
}

internal abstract class AbstractCombination<T>(
        protected val range: LongRange
) : Combination<T> {

    override val combinationsNumber = range.rangeLength()

    override fun split(n: Int): List<Combination<T>> {
        return range.split(n).map(this::subCombination)
    }

    internal abstract fun subCombination(range: LongRange) : Combination<T>

    override fun stream(): Stream<T> {
        return StreamSupport.stream(
                CombinationSpliterator(this),
                false
        )
    }
}

internal class CombinationSpliterator<T>(
        private var combination: Combination<T>
): Spliterator<T> {

    private var combinationIterator: Iterator<T> = combination.iterator()

    override fun estimateSize(): Long = combination.combinationsNumber

    override fun characteristics(): Int {
        return (Spliterator.SIZED or Spliterator.SUBSIZED or Spliterator.CONCURRENT)
                .or(Spliterator.IMMUTABLE or Spliterator.ORDERED)
    }

    override fun tryAdvance(action: Consumer<in T>): Boolean {
        return if(combinationIterator.hasNext()) {
            action.accept(combinationIterator.next())
            true
        } else {
            false
        }
    }

    override fun trySplit(): Spliterator<T> {
        val combinationParts = combination.split(2)
        combination = combinationParts[1]
        combinationIterator = combination.iterator()
        return CombinationSpliterator(combinationParts[0])
    }
}

internal fun Int.calculateCombinationsNumber() : Long = 2.pow(this) - 1
