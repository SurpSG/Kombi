package com.sgnatiuk.combination

import com.sgnatiuk.extensions.bitCount

internal class BinaryMaskIterator<T>(
        combinationsRange: LongRange,
        private val collectionBuilder: CollectionBuilder<T>
) : Iterator<T> {

    private val lastValue: Long = combinationsRange.last

    private var combinationMask : Long = combinationsRange.first

    override fun hasNext() : Boolean {
        return combinationMask <= lastValue
    }

    override fun next(): T {
        val collection = collectionBuilder.newCollection(combinationMask.bitCount())
        var tempMask = combinationMask
        var index = 0
        do {
            if(tempMask and 1 == 1L) {
                collectionBuilder.addItemByIndex(collection, index)
            }
            index++
            tempMask = tempMask shr 1
        } while (tempMask > 0)
        combinationMask++
        return collection
    }
}
