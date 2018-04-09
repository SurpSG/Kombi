package com.sgnatiuk.cartesian.encodable.encoders

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.RuntimeException

internal class CombinationMaskTest {
    private val testData = TestData(3)

    @Test
    fun `verify hasNext can be called multiple times`(){
        val combinationMask = CombinationMask(testData.bases)
        repeat(testData.expectedCombinations){
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

    @Test(expected = RuntimeException::class)
    fun `verify exception is thrown when passed empty array to constructor`() {
        CombinationMask(intArrayOf())
    }
}

class TestData(
        val bases: IntArray
){
    constructor(itemsCount: Int = 3) : this(createIntArray(itemsCount))

    val expectedCombinations: Int by lazy {
        var res = 1
        bases.forEach { res *= it }
        res
    }
}

private fun createIntArray(size: Int): IntArray{
    return IntArray(size) { it + 1 }
}