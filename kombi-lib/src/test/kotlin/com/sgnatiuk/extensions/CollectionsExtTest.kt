package com.sgnatiuk.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

internal class CollectionsExtTest {
    private val listOf = listOf(1, 2, 3)

    @Test
    fun `multiply all test`() {
        val multiplyAllRes = listOf.multiplyAll { toInt() }
        assertEquals(6.BigInt, multiplyAllRes)
    }

    @Test
    fun `verify multiply all returns zero for empty collection`() {
        val multiplyAllRes = listOf<Int>().multiplyAll { toInt() }
        assertEquals(0.BigInt, multiplyAllRes)
    }
}