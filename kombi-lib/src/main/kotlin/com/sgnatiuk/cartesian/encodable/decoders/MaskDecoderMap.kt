package com.sgnatiuk.cartesian.encodable.decoders

import java.util.*

internal class MaskDecoderMap<K, V>(
        private val data: Map<K, List<V>>,
        private val dataKeys: List<K>
) : MaskDecoder<Map<K, V>> {

    /**
     *
     * @param encoded - encoded data
     * @param decoded - the map where decoded values will be put.
     * @return - param 'decoded' with decoded values
     */
    private fun decodeToMap(
            encoded: IntArray,
            decoded: MutableMap<K, V>
    ): Map<K, V> {
        for (i in encoded.indices) {
            val fieldKey = keyByIndex(i)
            val valueIndex = encoded[i]
            decoded[fieldKey] = data[fieldKey, valueIndex]
        }
        return decoded
    }

    override fun decode(encoded: IntArray): Map<K, V> {
        return decodeToMap(encoded, HashMap())
    }

    private fun keyByIndex(index: Int): K {
        return dataKeys[index]
    }

    private operator fun Map<K, List<V>>.get(key: K, index: Int) = this[key]!![index]
}

