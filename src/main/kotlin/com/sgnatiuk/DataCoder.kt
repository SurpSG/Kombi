package com.sgnatiuk

import com.sgnatiuk.extensions.multiplyAll
import java.io.Serializable
import java.math.BigInteger
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

import kotlin.collections.Map.Entry

internal class DataCoder<K, V> (
        data: Map<K, Collection<V>>,
        keepOrder: Boolean = false
) : Iterable<IntArray>, Splittable<DataCoder<K, V>>, Serializable {

    private val internalData: Map<K, List<V>> = convertToFixedOrderMap(data, keepOrder)
    private val bases = radixes(internalData)
    private val dataKeys = ArrayList<K>(internalData.keys)

    val combiCount : BigInteger by lazy {
        internalData.values.multiplyAll { size }
    }

    val decoder: DataDecoder<K, V>
        get() = DefaultDecoder(internalData, dataKeys)

    override fun iterator(): Iterator<IntArray> = DataEncoder(bases)

    private fun radixes(data : Map<K, List<V>>): IntArray {
        val dataValuesIterator = data.values.iterator()
        return IntArray(data.size) {
            dataValuesIterator.next().size
        }
    }

    private fun convertToFixedOrderMap(
            data: Map<K, Collection<V>>,
            keepOrder: Boolean
    ): Map<K, List<V>>  = if (keepOrder) {
        data.mapValues {
            ArrayList(it.value)
        }
    } else {
        sortListByEntriesValuesCount(data)
    }

    private fun sortListByEntriesValuesCount(
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
    override fun split(n: Int): List<DataCoder<K, V>> {
        val coders = ArrayList<DataCoder<K, V>>()
        val descSortedData = sortListByEntriesValuesCount(
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
            coders += DataCoder(newData)
            from = to
        }

        return coders
    }

    override fun toString() = "DataCoder{combinationsCount=$combiCount}"

    class ValuesCountAsc<K, T> : Comparator<Map.Entry<K, Collection<T>>>{

        override fun compare(
                o1: Entry<K, Collection<T>>,
                o2: Entry<K, Collection<T>>
        ) : Int = o1.value.size - o2.value.size
    }
}
