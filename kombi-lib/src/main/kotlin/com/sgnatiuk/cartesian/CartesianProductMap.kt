package com.sgnatiuk.cartesian

import com.sgnatiuk.Splittable
import com.sgnatiuk.cartesian.encodable.EncodableCartesianProduct
import com.sgnatiuk.cartesian.encodable.decoders.MaskDecoderMap
import kotlin.collections.Map.Entry

internal class CartesianProductMap<K, V> (
        data: Map<K, Collection<V>>,
        keepOrder: Boolean = false
) : EncodableCartesianProduct<Map<K, V>>(), Splittable<CartesianProductMap<K, V>> {


    private val internalData: Map<K, List<V>> = convertToFixedOrderMap(data, keepOrder)
    private val dataKeys = ArrayList<K>(internalData.keys)

    override val decoder = MaskDecoderMap(internalData, dataKeys)
    override val values
        get() = internalData.values


    private fun convertToFixedOrderMap(
            data: Map<K, Collection<V>>,
            keepOrder: Boolean
    ): Map<K, List<V>>  = if (keepOrder) {
        data.mapValues {
            ArrayList(it.value)
        }
    } else {
        sortByValuesCount(data)
    }

    private fun sortByValuesCount(
            data: Map<K, Collection<V>>,
            comparator: Comparator<Map.Entry<K, Collection<V>>> = ValuesCountAsc()
    ) : Map<K, List<V>> {
        val keepOrderMap: MutableMap<K, List<V>> = LinkedHashMap()
        data.entries.sortedWith(comparator).forEach {
            keepOrderMap[it.key] = ArrayList(it.value)
        }
        return keepOrderMap
    }

    //implement more fair split
    override fun split(n: Int): List<CartesianProductMap<K, V>> {
        val coders = ArrayList<CartesianProductMap<K, V>>()
        val descSortedData = sortByValuesCount(
                internalData,
                ValuesCountAsc<K, V>().reversed()
        )
        val firstFieldEntry = descSortedData.entries.first()
        val firstFieldValues = firstFieldEntry.value
        val parts = Math.min(n, firstFieldValues.size)

        var from = 0
        repeat(parts){ i ->
            val fieldValuesPerChunk = (firstFieldValues.size - from) / (parts - i)
            val to = from + fieldValuesPerChunk
            val newData = LinkedHashMap(descSortedData)
            newData[firstFieldEntry.key] = firstFieldValues.subList(from, to)
            coders += CartesianProductMap(newData)
            from = to
        }

        return coders
    }

    class ValuesCountAsc<K, T> : Comparator<Map.Entry<K, Collection<T>>>{

        override fun compare(
                o1: Entry<K, Collection<T>>,
                o2: Entry<K, Collection<T>>
        ) : Int = o1.value.size - o2.value.size
    }
}