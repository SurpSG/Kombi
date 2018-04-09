package com.sgnatiuk.combination

internal class MapCombination<K, V>(
        private val originData: Map<K, V>
) : AbstractCombination<Map<K, V>>(originData.size) {

    override fun iterator() : Iterator<Map<K, V>> {
        return BinaryMaskIterator(
                combinationsNumber,
                MapBuilder(
                        originData,
                        originData.keys.toList()
                )
        )
    }
}