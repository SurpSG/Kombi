package com.sgnatiuk.cartesian

import com.sgnatiuk.cartesian.CartesianBuilder.cartesianProductOf
import com.sgnatiuk.listOfLists
import com.sgnatiuk.mapOfLists
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    printCartesianProductFromList(listOfLists)
    printCartesianProductFromMap(mapOfLists)
    println()
    parallelPrintCartesianProduct(listOfLists)
    parallelPrintCartesianProduct(mapOfLists)

}

fun <T> printCartesianProductFromList(inputData: List<List<T>>) {
    println("CartesianProduct of $inputData")
    cartesianProductOf(inputData).forEach(::println)
}

fun <T> parallelPrintCartesianProduct(inputData: List<List<T>>) {
    println("Split CartesianProduct of $inputData")
    parallelPrintCartesianProduct(cartesianProductOf(inputData))
}

fun <T> printCartesianProductFromMap(inputData: Map<T, List<T>>) {
    println("CartesianProduct of $inputData")
    cartesianProductOf(inputData).forEach(::println)
}

fun <T> parallelPrintCartesianProduct(inputData: Map<T, List<T>>) {
    println("Split CartesianProduct of $inputData")
    parallelPrintCartesianProduct(cartesianProductOf(inputData))
}

fun <T> parallelPrintCartesianProduct(cartesianProduct: CartesianProduct<T>) {
    cartesianProduct.split(2).map { subProduct ->
        thread {
            subProduct.forEach(::println)
        }
    }.forEach(Thread::join)
}
