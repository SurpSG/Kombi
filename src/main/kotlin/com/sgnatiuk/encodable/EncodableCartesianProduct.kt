package com.sgnatiuk.encodable

import com.sgnatiuk.cartesian.CartesianProduct
import com.sgnatiuk.encodable.decoders.MaskDecoder
import com.sgnatiuk.encodable.encoders.CombinationMask
import com.sgnatiuk.extensions.multiplyAll
import java.math.BigInteger

internal abstract class EncodableCartesianProduct<T> : CartesianProduct<T> {

    abstract val decoder : MaskDecoder<T>
    abstract val values: Collection<Collection<*>>

    val bases: IntArray by lazy { radixes(values) }

    override fun iterator(): Iterator<T> = DecodableIterator(this)

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
