package com.sgnatiuk.encodable

class TestData(
        val bases: IntArray
){
    constructor(itemsCount: Int = 3) : this(createIntArray(itemsCount))

    val itemsCount: Int
        get() = bases.size

    val expectedCombinations: Int by lazy {
        var res = 1
        bases.forEach { res *= it }
        res
    }
}

private fun createIntArray(size: Int): IntArray{
    return IntArray(size) { it + 1 }
}