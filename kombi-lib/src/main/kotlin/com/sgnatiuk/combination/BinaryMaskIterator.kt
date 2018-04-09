package com.sgnatiuk.combination

import com.sgnatiuk.extensions.bitCount

internal class BinaryMaskIterator<T>(
        private val combinationNumber: Int,
        private val collectionBuilder: CollectionBuilder<T>
) : Iterator<T> {

    private var combinationMask : Int = 1

    override fun hasNext() : Boolean {
        return combinationMask <= combinationNumber
    }

    override fun next(): T {
        val collection = collectionBuilder.newCollection(combinationMask.bitCount())
        var tempMask = combinationMask
        var index = 0
        do {
            if(tempMask and 1 == 1) {
                collectionBuilder.addItemByIndex(collection, index)
            }
            index++
            tempMask = tempMask shr 1
        } while (tempMask > 0)
        combinationMask++
        return collection
    }
}
