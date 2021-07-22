package com.sgnatiuk.cartesian

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CombinationMaskTest {
    private val testData = TestData(3)

    @Test
    fun `verify hasNext can be called multiple times`() {
        val combinationMask = CombinationMask(testData.bases)
        repeat(testData.expectedCombinations) {
            val hasNext = combinationMask.hasNext() && combinationMask.hasNext()
            assertTrue(hasNext)
            combinationMask.next()
        }
    }

    @Test
    fun `verify all possible combinations are generated`() {
        val combinationSet = HashSet<List<Int>>()
        CombinationMask(testData.bases).forEach {
            combinationSet.add(it.toList())
        }
        assertEquals(testData.expectedCombinations, combinationSet.size)
    }

    @Test
    fun `verify exception is thrown when passed empty array to constructor`() {
        assertThrows(IllegalArgumentException::class.java) {
            CombinationMask(intArrayOf())
        }
    }
}

class TestData(val bases: IntArray) {
    constructor(itemsCount: Int = 3) : this(createIntArray(itemsCount))

    val expectedCombinations: Int by lazy {
        bases.reduce { acc, i -> acc * i }
    }
}

private fun createIntArray(size: Int): IntArray {
    return IntArray(size) { it + 1 }.sortedArrayDescending()
}
