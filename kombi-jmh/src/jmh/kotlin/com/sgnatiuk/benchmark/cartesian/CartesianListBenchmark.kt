package com.sgnatiuk.benchmark.cartesian

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import com.sgnatiuk.cartesian.CartesianBuilder.cartesianProductOf
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class CartesianListBenchmark {

    @Param("3", "5", "7", "11")
    var itemsQuantity: Int = 0

    lateinit var listOfSets: List<Set<Int>>
    lateinit var listOfLists: List<List<Int>>

    @Setup(Level.Trial)
    fun doSetup() {
        listOfLists = List(itemsQuantity) { i ->
            List(i + 1) { it }
        }
        listOfSets = listOfLists.map { it.toSet() }
    }

    @Benchmark
    fun Kombi_cartesianProduct_Lists(blackhole: Blackhole) {
        for (combination in cartesianProductOf(listOfLists, false)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }

    @Benchmark
    fun Kombi_cartesianProduct_Lists_keepingOrder(blackhole: Blackhole) {
        for (combination in cartesianProductOf(listOfSets, true)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }

    @Benchmark
    fun Guava_cartesianProduct_Sets(blackhole: Blackhole) {
        for (combination in Sets.cartesianProduct(listOfSets)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }

    @Benchmark
    fun Guava_cartesianProduct_Lists(blackhole: Blackhole) {
        for (combination in Lists.cartesianProduct(listOfLists)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }
}
