package com.sgnatiuk.encodable.decoders

import com.sgnatiuk.encodable.encoders.CombinationMask
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

internal class MaskDecoderMapTest {

    private val data: Map<Int, List<Int>> = mapOf(
            1 to listOf(1,2,3),
            2 to listOf(4,5),
            3 to listOf(6)
    )
    private val bases: IntArray = data.values.map { it.size }.toIntArray()

    @Test
    fun `test decode to map`() {
        val maskDecoder = MaskDecoderMap(data, data.keys.toList())
        CombinationMask(bases).forEach { encoded ->
            val decodedMap = maskDecoder.decode(encoded)
            decodedMap.forEach { key, value ->
                assertNotNull(data[key])
                assertTrue(data[key]?.contains(value) ?: false)
            }
        }
    }
}