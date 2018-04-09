package com.sgnatiuk.cartesian.encodable

import com.sgnatiuk.cartesian.CartesianProduct
import com.sgnatiuk.cartesian.encodable.decoders.MaskDecoder
import com.sgnatiuk.cartesian.encodable.encoders.CombinationMask
import com.sgnatiuk.extensions.multiplyAll
import java.math.BigInteger
import java.util.Collections.emptyIterator

internal abstract class EncodableCartesianProduct<T> : CartesianProduct<T> {

    internal abstract val decoder : MaskDecoder<T>
    internal abstract val values: Collection<Collection<*>>

    internal val bases: IntArray by lazy { radixes(values) }

    override fun iterator(): Iterator<T> {
        return if(values.isNotEmpty()){
            DecodableIterator(this)
        } else{
            emptyIterator()
        }
    }

    override val combinationsCount : BigInteger by lazy {
        values.multiplyAll { size }
    }

    private fun radixes(values : Collection<Collection<*>>): IntArray {
        val dataValuesIterator = values.iterator()
        return IntArray(values.size) {
            dataValuesIterator.next().size
        }
    }
}

private class DecodableIterator<T>(
        cartesianProduct: EncodableCartesianProduct<T>
) : Iterator<T> {

    private val sequenceDecoder = cartesianProduct.decoder
    private val dataEncoder = CombinationMask(cartesianProduct.bases)

    override fun hasNext() = dataEncoder.hasNext()
    override fun next() : T = sequenceDecoder.decode(dataEncoder.next())
}
