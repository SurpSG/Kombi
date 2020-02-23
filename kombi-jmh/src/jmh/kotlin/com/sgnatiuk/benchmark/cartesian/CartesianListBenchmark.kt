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

    lateinit var listOfLists: List<Set<Int>>

    @Setup(Level.Trial)
    fun doSetup() {
        listOfLists = List(itemsQuantity) { i ->
            List(i + 1) { it }.toSet()
        }
    }

    @Benchmark
    fun cartesianProductList(blackhole: Blackhole) {
        for (combination in cartesianProductOf(listOfLists, false)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }

    @Benchmark
    fun cartesianProductListKeepOrder(blackhole: Blackhole) {
        for (combination in cartesianProductOf(listOfLists, true)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }

    @Benchmark
    fun cartesianProductListsGuava(blackhole: Blackhole) {
        for (combination in Sets.cartesianProduct(listOfLists)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }

    @Benchmark
    fun cartesianProductSetsGuava(blackhole: Blackhole) {
        for (combination in Lists.cartesianProduct(listOfLists)) {
            for (combinationItem in combination) {
                blackhole.consume(combinationItem)
            }
        }
    }
}
