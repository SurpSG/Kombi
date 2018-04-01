package com.sgnatiuk.encodable.encoders

import java.util.*

internal class CombinationMask(
        private val bases: IntArray
) : Iterator<IntArray> {

    private val encoded: IntArray = IntArray(bases.size)
    private var hasNextElement : Boolean = true


    override fun hasNext(): Boolean {
        if(hasNextElement) return true

        hasNextElement = !encoded.increment()
        return hasNextElement
    }

    override fun next() = encoded.apply {
        hasNextElement = false
    }

    /**
     * Computes next encoded combination
     * @return true if encoded was overflowed, otherwise false
     */
    private fun IntArray.increment(): Boolean {
        var extra = 1
        var index = this.size - 1
        do {
            val temp = this[index] + extra
            this[index] = temp % bases[index]
            extra = temp / bases[index]
            index--
        } while (extra != 0 && index >= 0)
        return index < 0 && extra > 0
    }

    private fun copy(arr: IntArray): IntArray {
        return Arrays.copyOf(arr, arr.size)
    }
}