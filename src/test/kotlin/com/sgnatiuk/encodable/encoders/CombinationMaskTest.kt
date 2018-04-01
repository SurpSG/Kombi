package com.sgnatiuk.encodable.encoders

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class CombinationMaskTest {
    private val itemsCount = 3
    private val bases = IntArray(itemsCount) { it + 1 }
    private val expectedCombinations = bases.let { baseItem ->
        var res = 1
        baseItem.forEach { res *= it }
        res
    }

    @Test
    fun `verify hasNext can be called multiple times`(){
        val combinationMask = CombinationMask(bases)
        repeat(expectedCombinations){
            val hasNext = combinationMask.hasNext() && combinationMask.hasNext()
            assertTrue(hasNext)
            combinationMask.next()
        }
    }

    @Test
    fun `verify all possible combinations are generated`() {
        val combinationSet = HashSet<List<Int>>()
        CombinationMask(bases).forEach {
            combinationSet.add(it.toList())
        }
        assertEquals(expectedCombinations, combinationSet.size)
    }
}