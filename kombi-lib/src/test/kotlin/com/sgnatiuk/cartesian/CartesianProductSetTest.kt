package com.sgnatiuk.cartesian

import com.sgnatiuk.dataList
import com.sgnatiuk.expectedCartesianList
import com.sgnatiuk.extensions.BigInt
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class CartesianProductSetTest {

    @Test
    fun `verify empty collection is returned when passed empty collection`() {
        val emptyCollection = ArrayList<List<Int>>()
        CartesianProductSet(emptyCollection).forEach {
            throw RuntimeException("expected empty collection")
        }
    }

    @Test
    fun `verify Cartesian product set returns all possible combinations`() {
        val result: List<List<Int>> = CartesianProductSet(dataList, false).map { it }
        assertContainsAll(expectedCartesianList, result)
    }

    @Test
    fun `verify Cartesian product set with keep order returns all possible combinations`() {
        val result: List<List<Int>> = CartesianProductSet(dataList, true).map { it }
        assertContainsAll(expectedCartesianList, result)
        expectedCartesianList.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun `verify cartesian product calculates combinations number properly`(){
        var result = 1.BigInt
        val cartesianProductSet = CartesianProductSet(dataList)
        dataList.forEach { result *= it.size.BigInt }
        Assert.assertEquals(result, cartesianProductSet.combinationsCount)
        Assert.assertEquals(expectedCartesianList.size.BigInt, cartesianProductSet.combinationsCount)
    }

    @Test
    fun `test split by two produces two items list of cartesian products`(){
        val cartesianProductSet = CartesianProductSet(dataList)
        val splitFactor = 2
        val splitCartesian = cartesianProductSet.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)
    }

    @Test
    fun `verify split with factor 1 produces the same cartesian product`(){
        val cartesianProductSet = CartesianProductSet(dataList)
        val notSplitCartesianSetResult = cartesianProductSet.toSet()

        val splitFactor = 1
        val splitCartesian = cartesianProductSet.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)

        val collectedCartesianProduct = splitCartesian.flatMap { it }.toSet()

        assertEquals(notSplitCartesianSetResult, collectedCartesianProduct)
    }

    @Test
    fun `verify split and simple cartesian produces the same combinations`() {
        val cartesianProductSet = CartesianProductSet(dataList)
        val notSplitCartesianSetResult = cartesianProductSet.map{ it.toSet() }.toSet()

        val splitFactor = 2
        val splitCartesian = cartesianProductSet.split(splitFactor)
        assertEquals(splitFactor, splitCartesian.size)

        val collectedCartesianProduct = splitCartesian.flatten().map(Iterable<Int>::toSet).toSet()
        assertTrue(collectedCartesianProduct.containsAll(notSplitCartesianSetResult))
    }

    private fun <T> assertContainsAll(
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