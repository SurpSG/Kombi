package com.sgnatiuk.cartesian

fun main(args: Array<String>) {
    val inputData = listOf(
            listOf(1, 2, 3),
            listOf(4),
            listOf(5, 6)
    )
    printCartesianProductFromList(inputData)
    printCartesianProductFromMap(inputData)
}

fun <T> printCartesianProductFromList(inputData: List<List<T>>) {
    println("CartesianProduct of $inputData")
    CartesianProduct(inputData).forEach(::println)
}

fun <T> printCartesianProductFromMap(inputData: List<List<T>>) {
    val inputDataMap: Map<Int, List<T>> = inputData.mapIndexed(::Pair).toMap()
    println("CartesianProduct of $inputDataMap")
    CartesianProduct(inputDataMap).forEach(::println)
}