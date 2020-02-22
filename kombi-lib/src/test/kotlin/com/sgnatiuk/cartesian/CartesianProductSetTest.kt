package com.sgnatiuk.cartesian

import com.sgnatiuk.dataList
import com.sgnatiuk.expectedCartesianList
import com.sgnatiuk.extensions.BigInt
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.stream.Collectors

internal class CartesianProductSetTest {

    @Test
    fun `verify empty collection is returned when passed empty collection`() {
        val emptyCollection = ArrayList<List<Int>>()
        cartesianProductOf(emptyCollection).forEach {
            throw RuntimeException("expected empty collection")
        }
    }

    @Test
    fun `verify Cartesian product set returns all possible combinations`() {
        val result: List<List<Int>> = cartesianProductOf(dataList, false).toList()
        assertContainsAll(expectedCartesianList, result)
    }

    @Test
    fun `verify Cartesian product set with keep order returns all possible combinations`() {
        val result: List<List<Int>> = cartesianProductOf(dataList, true).toList()
        assertContainsAll(expectedCartesianList, result)
        expectedCartesianList.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun `verify cartesian product calculates combinations number properly`() {
        var result = 1.BigInt
        val cartesianProductSet = cartesianProductOf(dataList)
        dataList.forEach { result *= it.size.BigInt }
        assertEquals(result, cartesianProductSet.combinationsCount())
        assertEquals(expectedCartesianList.size.BigInt, cartesianProductSet.combinationsCount())
    }

    @Test
    fun `test split by two produces two items list of cartesian products`() {
        val cartesianProductSet = cartesianProductOf(dataList)
        val splitFactor = 2
        val splitCartesian = cartesianProductSet.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)
    }

    @Test
    fun `verify split with factor 1 produces the same cartesian product`() {
        val cartesianProductSet = cartesianProductOf(dataList)
        val notSplitCartesianSetResult = cartesianProductSet.toSet()

        val splitFactor = 1
        val splitCartesian = cartesianProductSet.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)

        val collectedCartesianProduct = splitCartesian.flatten().toSet()

        assertEquals(notSplitCartesianSetResult, collectedCartesianProduct)
    }

    @Test
    fun `verify split and simple cartesian produces the same combinations`() {
        val cartesianProductSet = cartesianProductOf(dataList)
        val notSplitCartesianSetResult = cartesianProductSet.map { it.toSet() }.toSet()

        val splitFactor = 2
        val splitCartesian = cartesianProductSet.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)

        val collectedCartesianProduct = splitCartesian.flatten().map(Iterable<Int>::toSet).toSet()
        assertTrue(collectedCartesianProduct.containsAll(notSplitCartesianSetResult))
    }

    @Test
    fun `parallel stream should generate all items`() {
        val cartesianProductSet = cartesianProductOf(dataList)
        val threads = mutableSetOf<Long>()
        val actual = cartesianProductSet.stream()
                .parallel()
                .peek { threads += Thread.currentThread().id }
                .collect(Collectors.toSet())

        assertContainsAll(expectedCartesianList, actual)
        assertTrue(threads.size > 1)
    }

    private fun <T> assertContainsAll(
            expected: Collection<Collection<T>>,
            actual: Collection<Collection<T>>
    ) {
        expected.forEach { expectedCombination ->
            var foundCombination = false
            for (computed in actual) {
                if (computed.containsAll(expectedCombination)) {
                    foundCombination = true
                    break
                }
            }
            assertTrue("Expected $expectedCombination, but not found in $actual", foundCombination)
        }
    }
}