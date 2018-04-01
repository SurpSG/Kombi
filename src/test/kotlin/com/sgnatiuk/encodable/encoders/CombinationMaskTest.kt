package com.sgnatiuk.encodable.encoders

import com.sgnatiuk.encodable.TestData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

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
}