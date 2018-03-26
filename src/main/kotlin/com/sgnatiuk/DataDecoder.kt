package com.sgnatiuk

import com.sgnatiuk.extensions.getOrThrow
import java.util.HashMap

interface DataDecoder<K, V> {
    fun decode(encoded: IntArray): Map<K, V>
}

internal class DefaultDecoder<K, V>(
        private val data: Map<K, List<V>>,
        private val dataKeys: List<K>
) : DataDecoder<K, V> {

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
            decoded[fieldKey] = data.valueByIndex(fieldKey, valueIndex)
        }
        return decoded
    }

    override fun decode(encoded: IntArray): Map<K, V> {
        return decodeToMap(encoded, HashMap())
    }

    private fun keyByIndex(index: Int): K {
        return dataKeys[index]
    }

    private fun Map<K, List<V>>.valueByIndex(key: K, index: Int) = this[key]!![index]
}

