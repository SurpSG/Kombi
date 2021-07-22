package com.sgnatiuk.combination

import com.sgnatiuk.extensions.pow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BinaryMaskIteratorTest {
    private val listOf = listOf(1, 2, 3)
    private val combinationsCount = 2.pow(listOf.size) - 1

    @Test
    fun `verify expected combinations count are generated`() {
        val binaryMaskIterator = BinaryMaskIterator(
            Range(1, combinationsCount),
            object : CollectionBuilder<MutableList<Int>> {
                override fun newCollection(initialCapacity: Int) = ArrayList<Int>()
                override fun addItemByIndex(collection: MutableList<Int>, index: Int) {
                    collection += listOf[index]
                }
            }
        )
        val combinations = HashSet<List<Int>>()
        binaryMaskIterator.forEach {
            combinations += it
        }
        assertEquals(combinationsCount, combinations.size.toLong())
    }
}
