package com.sgnatiuk.cartesian

import com.sgnatiuk.dataMap
import com.sgnatiuk.expectedCartesianMap
import com.sgnatiuk.extensions.BigInt
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.stream.Collectors

internal class CartesianProductMapTest {

    @Test
    fun `verify empty collection is returned when passed empty collection`() {
        val emptyCollection = ArrayList<List<Int>>()
        CartesianProductSet(emptyCollection).forEach {
            throw RuntimeException("expected empty collection")
        }
    }

    @Test
    fun `verify Cartesian product set returns all possible combinations`() {
        val result: List<Map<Int, Int>> = CartesianProductMap(dataMap, false).map { it }
        expectedCartesianMap.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun `verify Cartesian product set with keep order returns all possible combinations`() {
        val result: List<Map<Int, Int>> = cartesianProductOf(dataMap, true).map { it }
        expectedCartesianMap.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun `verify cartesian product calculates combinations number properly`() {
        var result = 1.BigInt
        val cartesianProductMap = cartesianProductOf(dataMap)
        dataMap.values.forEach { result *= it.size.BigInt }
        assertEquals(result, cartesianProductMap.combinationsCount)
        assertEquals(expectedCartesianMap.size.BigInt, cartesianProductMap.combinationsCount)
    }

    @Test
    fun `test split by two produces two items list of cartesian products`() {
        val cartesianProductMap = cartesianProductOf(dataMap)
        val splitFactor = 2
        val splitCartesian = cartesianProductMap.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)
    }

    @Test
    fun `verify split with factor 1 produces the same cartesian product`() {
        val cartesianProductMap = cartesianProductOf(dataMap)
        val notSplitCartesianSetResult = cartesianProductMap.toSet()

        val splitFactor = 1
        val splitCartesian = cartesianProductMap.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)

        val collectedCartesianProduct = splitCartesian.flatten().toSet()

        assertEquals(notSplitCartesianSetResult, collectedCartesianProduct)
    }

    @Test
    fun `verify split and simple cartesian produces the same combinations`() {
        val cartesianProductMap = cartesianProductOf(dataMap)
        val notSplitCartesianMapResult = cartesianProductMap.map { it }

        val splitFactor = 2
        val splitCartesian = cartesianProductMap.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)

        val collectedCartesianProduct = splitCartesian.flatten().toSet()

        assertTrue(collectedCartesianProduct.containsAll(notSplitCartesianMapResult))
    }

    @Test
    fun `stream should generate all items`() {
        val cartesianProduct = cartesianProductOf(dataMap)
        val actual = cartesianProduct.stream()
                .collect(Collectors.toSet())

        assertEquals(expectedCartesianMap.toSet(), actual)
    }

    @Test
    fun `parallel stream should generate all items`() {
        val threads = mutableSetOf<Long>()
        val cartesianProduct = cartesianProductOf(dataMap)
        val actual = cartesianProduct.stream()
                .parallel()
                .peek{ threads += Thread.currentThread().id }
                .collect(Collectors.toSet())

        assertEquals(expectedCartesianMap.toSet(), actual)
        assertTrue(threads.size > 1)
    }
}