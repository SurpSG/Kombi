package com.sgnatiuk.cartesian

import org.junit.Assert.assertTrue
import org.junit.Test

internal class CartesianProductSetTest {
    private val data = listOf(
            listOf(1, 2, 3),
            listOf(4, 5),
            listOf(6)
    )
    private val expectedCombies = listOf(
            listOf(1,4,6),
            listOf(2,4,6),
            listOf(3,4,6),
            listOf(1,5,6),
            listOf(2,5,6),
            listOf(3,5,6)
    )

    @Test
    fun `verify empty collection is returned when passed empty collection`() {
        val emptyCollection = ArrayList<List<Int>>()
        CartesianProductSet(emptyCollection).forEach {
            throw RuntimeException("expected empty collection")
        }
    }

    @Test
    fun `verify Cartesian product set returns all possible combinations`() {
        val result: List<List<Int>> = CartesianProductSet(data, false).map { it }
        assertContainsAll(expectedCombies, result)
    }

    @Test
    fun `verify Cartesian product set with keep order returns all possible combinations`() {
        val result: List<List<Int>> = CartesianProductSet(data, true).map { it }
        assertContainsAll(expectedCombies, result)
        expectedCombies.forEach {
            assertTrue(result.contains(it))
        }
    }

    fun <T> assertContainsAll(
            expected: Collection<Collection<T>>,
            actual: Collection<Collection<T>>
    ) {
        expected.forEach { expectedCombination ->
            var foundCombination = false
            for (computed in actual){
                if(computed.containsAll(expectedCombination)){
                    foundCombination = true
                    break
                }
            }
            assertTrue("Expected $expectedCombination, but not found in $actual", foundCombination)
        }
    }
}