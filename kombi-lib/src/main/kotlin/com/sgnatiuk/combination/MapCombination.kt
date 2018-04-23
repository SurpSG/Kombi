package com.sgnatiuk.combination

internal class MapCombination<K, V>(
        private val originData: Map<K, V>,
        range: LongRange = 1..originData.size.calculateCombinationsNumber()
) : AbstractCombination<Map<K, V>>(range) {

    override fun iterator() : Iterator<Map<K, V>> {
        return BinaryMaskIterator(
                range,
                MapBuilder(
                        originData,
                        originData.keys.toList()
                )
        )
    }

    override fun subCombination(range: LongRange): Combination<Map<K, V>> {
        return MapCombination(originData, range)
    }
}