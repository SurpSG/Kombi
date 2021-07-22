package com.sgnatiuk.combination

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class KnCombinationTest {

    private val listOfData = listOf(1, 2, 3, 4)

    @ParameterizedTest
    @CsvSource(
        value = [
            "1, 4",
            "2, 6",
            "3, 4",
            "4, 1"
        ]
    )
    fun `combinationsNumber must return expected combinations count`(combinationSize: Int, expectedCount: Long) {
        assertEquals(
            expectedCount,
            KnCombination(listOfData, combinationSize).combinationsNumber()
        )
    }

    @Test
    fun `KnCombination must return all combinations`() {
        val expectedCombinations = setOf(
            setOf(1, 2),
            setOf(1, 3),
            setOf(1, 4),
            setOf(2, 3),
            setOf(2, 4),
            setOf(3, 4),
        )

        val actualCombinations = KnCombination(listOfData, 2).toList()

        assertEquals(expectedCombinations.size, actualCombinations.size)
        actualCombinations.forEach {
            assertTrue(expectedCombinations.contains(it.toSet()), "Unexpected combination $it")
        }
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "2, -1",
            "5, 0",
            "3, 4",
            "0, 0",
        ]
    )
    fun `KnCombination must throw if requested combinations size greater than provided list`(
        listSize: Int,
        requestedCombinationSize: Int
    ) {
        val list = Array(listSize) { it }.toList()

        assertThrows(IllegalArgumentException::class.java) {
            KnCombination(list, requestedCombinationSize)
        }
    }
}
