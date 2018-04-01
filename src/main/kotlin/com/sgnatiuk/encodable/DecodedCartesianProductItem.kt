package com.sgnatiuk.encodable

import com.sgnatiuk.encodable.encoders.CombinationMask

internal class DecodedCartesianProductItem<T>(
        private val encodableCartesianProduct: EncodableCartesianProduct<T>
) : Iterable<T> {

    override fun iterator() : Iterator<T> = DecodedCombination(encodableCartesianProduct)

    private class DecodedCombination<T>(
            encodableCartesianProduct: EncodableCartesianProduct<T>
    ) : Iterator<T> {

        private val sequenceDecoder = encodableCartesianProduct.decoder
        private val dataEncoder = CombinationMask(encodableCartesianProduct.bases)

        override fun hasNext() = dataEncoder.hasNext()
        override fun next() : T = sequenceDecoder.decode(dataEncoder.next())
    }
}

