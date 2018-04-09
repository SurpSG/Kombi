package com.sgnatiuk.combination

internal class ListCombination<T>(
        private val originData: List<T>
) : AbstractCombination<List<T>>(originData.size) {

    override fun iterator() : Iterator<List<T>> {
        return BinaryMaskIterator(
                combinationsNumber,
                ListBuilder(originData)
        )
    }
}
