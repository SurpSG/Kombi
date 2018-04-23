package com.sgnatiuk.extensions

internal fun LongRange.split(n: Int) : Iterable<LongRange> {
    if(n < 1) throw IllegalArgumentException("Cannot split by $n. Valid values are greater 0")
    return object: Iterable<LongRange>{
        override fun iterator(): Iterator<LongRange> {
            return object : Iterator<LongRange>{

                var first : Long = start
                var chunk : Int = 0

                override fun next(): LongRange {
                    val rangeLength = (first..last).rangeLength()
                    val valuesPerChunk = rangeLength / (n - chunk++)
                    val to = first + valuesPerChunk
                    val subRange = first until to
                    first = to
                    return subRange
                }

                override fun hasNext() = chunk < n
            }
        }
    }
}

internal fun LongRange.rangeLength() : Long {
    return this.last - this.first + 1
}