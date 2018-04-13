package com.sgnatiuk

val dataList: List<List<Int>> = listOf(
        listOf(1, 2, 3),
        listOf(4),
        listOf(5, 6)
)

val expectedCartesianList = listOf(
        listOf(1,4,5),
        listOf(2,4,5),
        listOf(3,4,5),
        listOf(1,4,6),
        listOf(2,4,6),
        listOf(3,4,6)
)

val dataMap: Map<Int, List<Int>> = dataList.mapIndexed(::Pair).toMap()

val expectedCartesianMap: List<Map<Int, Int>> = expectedCartesianList.map {
    it.mapIndexed { index, item ->
        index to item
    }.toMap()
}