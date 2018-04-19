package com.sgnatiuk.cartesian

import com.sgnatiuk.cartesian.encodable.EncodableCartesianProduct
import com.sgnatiuk.cartesian.encodable.decoders.MaskDecoderList
import java.io.Serializable

internal class CartesianProductSet<T> (
        data: Collection<Collection<T>>,
        keepOrder: Boolean = false
) : EncodableCartesianProduct<List<T>>(), Serializable {

    override val values = convertToFixedOrderMap(data, keepOrder)
    override val decoder = MaskDecoderList(values)

    private fun convertToFixedOrderMap(
            data: Collection<Collection<T>>,
            keepOrder: Boolean
    ): List<List<T>>  = if (keepOrder) {
        data.map {
            ArrayList(it)
        }
    } else {
        sortByValuesCount(data)
    }

    private fun sortByValuesCount(
            data: Collection<Collection<T>>,
            comparator: Comparator<Collection<T>> = ValuesCountAsc()
    ) : List<List<T>> {
        return data.sortedWith(comparator).map { collection ->
            collection.map { it }
        }
    }

    override fun split(n: Int): List<CartesianProductSet<T>> {
        val splitList = ArrayList<CartesianProductSet<T>>(n)
        val descSortedData = sortByValuesCount(values, ValuesCountAsc<T>().reversed())

        val firstFieldValues = descSortedData.first()
        val parts = Math.min(n, firstFieldValues.size)

        var from = 0
        repeat(parts){ i ->
            val valuesPerChunk = (firstFieldValues.size - from) / (parts - i)
            val to = from + valuesPerChunk
            val newData = ArrayList(descSortedData)
            newData[0] = firstFieldValues.subList(from, to)
            splitList += CartesianProductSet(newData)
            from = to
        }

        return splitList
    }

    internal class ValuesCountAsc<T> : Comparator<Collection<T>>{
        override fun compare(
                o1: Collection<T>,
                o2: Collection<T>
        ): Int = o1.size - o2.size
    }
}


