package com.sgnatiuk.cartesian

import com.sgnatiuk.cartesian.CartesianBuilder.cartesianProductOf
import com.sgnatiuk.dataList
import com.sgnatiuk.expectedCartesianList
import com.sgnatiuk.extensions.BigInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*
import java.util.stream.Collectors

internal class CartesianProductSetTest {

    @Test
    fun `verify empty collection is returned when passed empty collection`() {
        val emptyCollection = emptyList<List<Int>>()
        checkCartesianProductIsEmpty(emptyCollection)
    }

    @Test
    fun `cartesian product of single zero length collection should be empty`() {
        val emptyCollection = listOf(emptyList<Int>())
        checkCartesianProductIsEmpty(emptyCollection)
    }

    @Test
    fun `cartesian product of list with at least one empty collection should be empty`() {
        val emptyCollection = listOf(
            emptyList(),
            listOf(1),
            listOf(1, 2)
        )
        checkCartesianProductIsEmpty(emptyCollection)
    }

    private fun checkCartesianProductIsEmpty(list: List<List<Int>>) {
        cartesianProductOf(list).apply {
            assertEquals(0, combinationsCount().longValueExact())
        }.forEach { _ ->
            throw RuntimeException("expected empty collection")
        }
    }

    @Test
    fun `stream of cartesian product of single zero length collection should be empty`() {
        val emptyCollection = listOf(emptyList<Int>())
        val count = cartesianProductOf(emptyCollection)
            .stream()
            .flatMap { it.stream() }
            .count()
        assertEquals(0, count)
    }

    @Test
    fun `parallel stream of cartesian product of single zero length collection should be empty`() {
        val emptyCollection = listOf(emptyList<Int>())
        val count = cartesianProductOf(emptyCollection)
            .stream()
            .parallel()
            .flatMap { it.stream() }
            .count()
        assertEquals(0, count)
    }

    @Test
    fun `verify Cartesian product set returns all possible combinations`() {
        val result: List<List<Int>> = cartesianProductOf(dataList, false).toList()
        assertContainsAll(expectedCartesianList, result)
    }

    @Test
    fun `toArray on cartesian item must return array of items`() {
        val result: List<List<Int>> = cartesianProductOf(dataList, true).toList()

        val itemsAsArray: List<Array<Any>> = result.asSequence()
            .map { it as AbstractList }.map { it.toArray() }
            .toList()

        expectedCartesianList.asSequence()
            .map { it as AbstractList }.map { it.toArray() }
            .forEach { expectedCombination: Array<Any> ->
                var foundCombination = false
                for (computed in itemsAsArray) {
                    if (computed contentEquals expectedCombination) {
                        foundCombination = true
                        break
                    }
                }
                assertTrue(
                    foundCombination,
                    "Expected ${expectedCombination.contentToString()}, but not found in " +
                            itemsAsArray.joinToString { it.contentToString() }
                )
            }
    }

    @Test
    fun `verify Cartesian product set with keep order returns all possible combinations`() {
        val result: List<List<Int>> = cartesianProductOf(dataList, true).toList()
        assertContainsAllWithOrder(expectedCartesianList, result)
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

    @Test
    fun `parallel stream should keep order`() {
        val cartesianProductSet = cartesianProductOf(dataList, true)
        val actual = cartesianProductSet.stream()
            .parallel()
            .collect(Collectors.toSet())

        assertContainsAllWithOrder(expectedCartesianList, actual)
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
            assertTrue(foundCombination, "Expected $expectedCombination, but not found in $actual")
        }
    }

    private fun <T> assertContainsAllWithOrder(
        expected: Collection<Collection<T>>,
        actual: Collection<Collection<T>>
    ) {
        expected.forEach { expectedCombination ->
            if (!actual.contains(expectedCombination)) {
                throw AssertionError("Expected $expectedCombination in the same order, but not found in $actual")
            }
        }
    }
}
