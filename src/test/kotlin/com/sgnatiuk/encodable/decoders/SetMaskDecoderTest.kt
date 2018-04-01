package com.sgnatiuk.encodable.decoders

import com.sgnatiuk.encodable.encoders.CombinationMask
import org.junit.Assert.*
import org.junit.Test

internal class SetMaskDecoderTest {

    private val data: List<List<Int>> = listOf(
            listOf(1,2,3),
            listOf(4,5),
            listOf(6)
    )
    private val bases: IntArray = data.map { it.size }.toIntArray()

    @Test
    fun `test decode`() {
        val maskDecoder = SetMaskDecoder(data)
        CombinationMask(bases).forEach { encoded ->
            maskDecoder.decode(encoded).forEach { decodedItem ->
                val itemInData: List<Int>? = data.find { it.contains(decodedItem) }
                assertNotNull(itemInData)
            }
        }
    }
}