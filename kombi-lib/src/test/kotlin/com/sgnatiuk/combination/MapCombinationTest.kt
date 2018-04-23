package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow
import com.sgnatiuk.extensions.rangeLength
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class MapCombinationTest {
    private val map = mapOf(
            1 to "1",
            2 to "2",
            3 to "3"
    )
    private val combinationsCount = 2.pow(map.size) - 1
    private val mapCombination = MapCombination(map)

    @Test
    fun `verify combinations count calculated properly`() {
        assertEquals(combinationsCount, mapCombination.combinationsNumber)
    }

    @Test
    fun `verify expected all combinations are generated`() {
        val combinations = HashSet<Map<Int, String>>()
        mapCombination.forEach {
            assertTrue(it.isNotEmpty())
            it.forEach { key, value ->
                assertEquals(map[key], value)
            }
            combinations += it
        }
        assertEquals(combinationsCount, combinations.size.toLong())
    }

    @Test
    fun `verify split generates all combinations`() {
        val expected = mapCombination.toSet()
        val actual = mapCombination.split(2).flatten().toSet()
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `verify split by zero throws exception`() {
        mapCombination.split(0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `verify split by value less then zero throws exception`() {
        mapCombination.split(-1)
    }

    @Test
    fun `verify split generates chunks`() {
        val combinationsNumber = mapCombination.combinationsNumber.toInt()
        repeat(combinationsNumber) {
            val splitFactor = it + 1
            val splitCombi = mapCombination.split(splitFactor)
            assertEquals(splitFactor, splitCombi.size)
        }
    }

    @Test
    fun `verify subCombination creates combination with expected size`() {
        val range = 1L..3
        val subCombination = mapCombination.subCombination(range)
        assertEquals(range.rangeLength(), subCombination.combinationsNumber)
    }

    @Test
    fun `verify subCombination creates sub combination`() {
        val range = 1L..3
        val allCombinations = mapCombination.toSet()
        val subCombination = mapCombination.subCombination(range)
        assertTrue(subCombination.combinationsNumber <= mapCombination.combinationsNumber)
        subCombination.forEach {
            allCombinations.contains(it)
        }
    }
}