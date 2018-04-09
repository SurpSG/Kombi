package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class ListCombinationTest {
    private val listOf = listOf("1", "2", "3")
    private val combinationsCount = 2.pow(listOf.size)
    private val listCombination = ListCombination(listOf)

    @Test
    fun `verify combinations count calculated properly`() {
        val expectedCombiCount = combinationsCount - 1
        assertEquals(expectedCombiCount, listCombination.combinationsNumber)
    }

    @Test
    fun `verify expected all combinations are generated`() {
        val combinations = HashSet<List<String>>()
        listCombination.forEach {
            assertTrue(it.isNotEmpty())
            assertTrue(listOf.containsAll(it))
            combinations += it
        }
        val expectedCombiCount = combinationsCount - 1
        assertEquals(expectedCombiCount, combinations.size)
    }
}