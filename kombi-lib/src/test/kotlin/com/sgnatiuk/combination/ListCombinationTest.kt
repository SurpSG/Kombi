package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.stream.Collectors

internal class ListCombinationTest {
    private val listOf = listOf("1", "2", "3")
    private val combinationsCount = 2.pow(listOf.size) - 1
    private val range = Range(1, 3)
    private val listCombination = CombinationsBuilder.combinationsOf(listOf) as AbstractCombination

    @Test
    fun `verify combinations count calculated properly`() {
        assertEquals(combinationsCount, listCombination.combinationsNumber())
    }

    @Test
    fun `verify all expected combinations are generated`() {
        val combinations = HashSet<List<String>>()
        listCombination.forEach {
            assertTrue(it.isNotEmpty())
            assertTrue(listOf.containsAll(it))
            combinations += it
        }
        assertEquals(combinationsCount, combinations.size.toLong())
    }

    @Test
    fun `verify split generates all combinations`() {
        val expected = listCombination.map { it.toSet() }.toSet()
        val actual: Set<Set<String>> = listCombination.split(2).flatten().map { it.toSet() }.toSet()
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun `verify split by zero throws exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            listCombination.split(0)
        }
    }

    @Test
    fun `verify split by value less then zero throws exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            listCombination.split(-1)
        }
    }

    @Test
    fun `verify split generates chunks`() {
        val combinationsNumber = listCombination.combinationsNumber().toInt()
        repeat(combinationsNumber) {
            val splitFactor = it + 1
            val splitCombi = listCombination.split(splitFactor)
            assertEquals(splitFactor, splitCombi.size)
        }
    }

    @Test
    fun `verify subCombination creates combination with expected size`() {

        val subCombination = listCombination.subCombination(range)
        assertEquals(range.length(), subCombination.combinationsNumber())
    }

    @Test
    fun `verify subCombination creates sub combination`() {
        val allCombinations: Set<Set<String>> = listCombination.map { it.toSet() }.toSet()
        val subCombination = listCombination.subCombination(range)
        assertTrue(subCombination.combinationsNumber() <= listCombination.combinationsNumber())
        subCombination.map { it.toSet() }.forEach {
            allCombinations.contains(it)
        }
    }

    @Test
    fun `stream should return stream of all items`() {
        val expected = listCombination.map { it.toSet() }.toSet()
        val actual = listCombination.stream()
            .map { it.toSet() }
            .collect(Collectors.toSet())
        assertEquals(expected, actual)
    }

    @Test
    fun `stream should return stream able to be process items parallel`() {
        val expected = listCombination.map { it.toSet() }.toSet()
        val actual = listCombination.stream()
            .parallel()
            .map { it.toSet() }
            .collect(Collectors.toSet())
        assertEquals(expected, actual)
    }
}
