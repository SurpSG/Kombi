package com.sgnatiuk

class Combinations<K, V> private constructor(
        private val dataCoder: DataCoder<K, V>
) : Iterable<Map<K, V>>, Splittable<Combinations<K,V>> {

    constructor(
            data: Map<K, Collection<V>>,
            keepOrder: Boolean = false
    ) : this(
            DataCoder(data, keepOrder)
    )

    override fun iterator() : Iterator<Map<K, V>> = DecodedCombination(dataCoder)

    override fun split(n: Int) = dataCoder.split(n).map {
        Combinations(it)
    }

    override fun toString() = "Combinations(combinationsCount=${dataCoder.combiCount})"

    private class DecodedCombination<K, V>(
            dataCoder: DataCoder<K, V>
    ) : Iterator<Map<K, V>> {

        private val dataEncoder = dataCoder.iterator()
        private val dataDecoder = dataCoder.decoder

        override fun hasNext() = dataEncoder.hasNext()

        override fun next() : Map<K, V> = dataDecoder.decode(dataEncoder.next())
    }
}

