package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow
import java.util.*
import java.util.function.Consumer

internal class CombinationSpliterator<T>(
        private var combination: Combination<T>
): Spliterator<T> {

    private var combinationIterator: Iterator<T> = combination.iterator()

    override fun estimateSize(): Long = combination.combinationsNumber()

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
