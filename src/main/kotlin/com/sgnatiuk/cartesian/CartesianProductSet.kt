package com.sgnatiuk.cartesian

import com.sgnatiuk.encodable.EncodableCartesianProduct
import com.sgnatiuk.encodable.decoders.MaskDecoderList
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

    private fun sortByValuesCount(data: Collection<Collection<T>>) : List<List<T>> {
        return data.sortedBy {
            it.size
        }.map { collection ->
            collection.map { it }
        }
    }
}



