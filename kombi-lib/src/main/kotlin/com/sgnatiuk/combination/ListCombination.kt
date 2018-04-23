package com.sgnatiuk.combination

internal class ListCombination<T>(
        private val originData: List<T>,
        range: LongRange = 1..originData.size.calculateCombinationsNumber()
) : AbstractCombination<List<T>>(range) {

    override fun iterator() : Iterator<List<T>> {
        return BinaryMaskIterator(
                range,
                ListBuilder(originData)
        )
    }

    override fun subCombination(range: LongRange): Combination<List<T>> {
        return ListCombination(originData, range)
    }
}
