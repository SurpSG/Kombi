package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class MapCombinationTest {
    private val map = mapOf(
            1 to "1",
            2 to "2",
            3 to "3"
    )
    private val combinationsCount = 2.pow(map.size)
    private val listCombination = MapCombination(map)

    @Test
    fun `verify combinations count calculated properly`() {
        val expectedCombiCount = combinationsCount - 1
        assertEquals(expectedCombiCount, listCombination.combinationsNumber)
    }

    @Test
    fun `verify expected all combinations are generated`() {
        val combinations = HashSet<Map<Int, String>>()
        listCombination.forEach {
            assertTrue(it.isNotEmpty())
            it.forEach { key, value ->
                assertEquals(map[key], value)
            }
            combinations += it
        }
        val expectedCombiCount = combinationsCount - 1
        assertEquals(expectedCombiCount, combinations.size)
    }
}