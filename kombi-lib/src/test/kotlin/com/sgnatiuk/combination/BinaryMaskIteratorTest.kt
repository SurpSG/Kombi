package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow
import org.junit.Assert.assertEquals
import org.junit.Test

internal class BinaryMaskIteratorTest {
    private val listOf = listOf(1, 2, 3)
    private val listBuilder = ListBuilder(listOf)
    private val combinationsCount = 2.pow(listOf.size) - 1

    @Test
    fun `verify expected combinations count are generated`() {
        val binaryMaskIterator = BinaryMaskIterator(
                Range(1, combinationsCount),
                listBuilder
        )
        val combinations = HashSet<List<Int>>()
        binaryMaskIterator.forEach {
            combinations += it
        }
        assertEquals(combinationsCount, combinations.size.toLong())
    }
}