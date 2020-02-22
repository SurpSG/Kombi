package com.sgnatiuk.cartesian.encodable

import com.sgnatiuk.cartesian.CartesianProduct
import com.sgnatiuk.cartesian.encodable.decoders.MaskDecoder
import com.sgnatiuk.cartesian.encodable.encoders.CombinationMask
import com.sgnatiuk.extensions.multiplyAll
import java.math.BigInteger
import java.util.*
import java.util.Collections.emptyIterator
import java.util.function.Consumer
import java.util.stream.Stream
import java.util.stream.StreamSupport

internal abstract class EncodableCartesianProduct<T> : CartesianProduct<T> {

    internal abstract val decoder : MaskDecoder<T>
    internal abstract val values: Collection<Collection<*>>

    internal val bases: IntArray by lazy { radixes(values) }

    override fun iterator(): MutableIterator<T> {
        return if(values.isNotEmpty()){
            DecodableIterator(this)
        } else{
            emptyIterator()
        }
    }

    override fun combinationsCount() : BigInteger {
        return values.multiplyAll { size }
    }

    override fun stream(): Stream<T> {
        return StreamSupport.stream(CartesianProductSpliterator(this), false)
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
) : MutableIterator<T> {

    private val sequenceDecoder = cartesianProduct.decoder
    private val dataEncoder = CombinationMask(cartesianProduct.bases)

    override fun hasNext() = dataEncoder.hasNext()
    override fun next() : T = sequenceDecoder.decode(dataEncoder.next())
    override fun remove() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

internal class CartesianProductSpliterator<T>(
        private var cartesianProduct: CartesianProduct<T>
): Spliterator<T> {

    private var cartesianProductIterator: Iterator<T> = cartesianProduct.iterator()
    private var isSizeKnown: Boolean
    private var size: Long

    override fun estimateSize(): Long = size

    init {
        val (isSizeKnown, size) = computeSize()
        this.isSizeKnown = isSizeKnown
        this.size = size
    }

    override fun characteristics(): Int {
        return (Spliterator.CONCURRENT or Spliterator.IMMUTABLE or Spliterator.ORDERED).let {
            if (isSizeKnown)
                it or Spliterator.SIZED or Spliterator.SUBSIZED
            else
                it
        }
    }

    override fun tryAdvance(action: Consumer<in T>): Boolean {
        return if(cartesianProductIterator.hasNext()) {
            action.accept(cartesianProductIterator.next())
            true
        } else {
            false
        }
    }

    override fun trySplit(): Spliterator<T> {
        val cartesianProductParts = cartesianProduct.split(2)

        cartesianProduct = cartesianProductParts[1]
        cartesianProductIterator = cartesianProduct.iterator()
        val (isSizeKnown, size) = computeSize()
        this.isSizeKnown = isSizeKnown
        this.size = size

        return CartesianProductSpliterator(cartesianProductParts[0])
    }

    private fun computeSize(): Pair<Boolean, Long> {
        return if(cartesianProduct.combinationsCount() > BigInteger.valueOf(Long.MAX_VALUE))
            Pair(false, Long.MAX_VALUE)
        else
            Pair(true, cartesianProduct.combinationsCount().longValueExact())
    }
}