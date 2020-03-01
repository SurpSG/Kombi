package com.sgnatiuk.benchmark.cartesian

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import com.sgnatiuk.cartesian.CartesianBuilder.cartesianProductOf
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(2)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
open class CartesianListBenchmark {

    @Param("3", "5", "7", "11")
    var itemsQuantity: Int = 0

    lateinit var listOfSets: List<Set<Int>>
    lateinit var listOfLists: List<List<Int>>

    @Setup(Level.Trial)
    fun doSetup() {
        listOfLists = List(itemsQuantity) { i ->
            List(i + 1) { it + 1 }
        }
        listOfSets = listOfLists.map { it.toSet() }
        println("\n=================================================")
        println("combinationsQuantity=${cartesianProductOf(listOfLists).combinationsCount()}")
        println("Data:")
        listOfLists.forEach {
            println(it)
        }
        println("=================================================\n")
    }

    @Benchmark
    fun Kombi_cartesianProduct_Lists(blackhole: Blackhole) {
        for (combination in cartesianProductOf(listOfLists, false)) {
            iterateWithIterator(combination, blackhole)
        }
    }

    @Benchmark
    fun Kombi_cartesianProduct_Lists_keepingOrder(blackhole: Blackhole) {
        for (combination in cartesianProductOf(listOfSets, true)) {
            iterateWithIterator(combination, blackhole)
        }
    }

    @Benchmark
    fun Guava_cartesianProduct_Sets(blackhole: Blackhole) {
        for (combination in Sets.cartesianProduct(listOfSets)) {
            iterateWithIterator(combination, blackhole)
        }
    }

    @Benchmark
    fun Guava_cartesianProduct_Lists(blackhole: Blackhole) {
        for (combination in Lists.cartesianProduct(listOfLists)) {
            iterateWithIterator(combination, blackhole)
        }
    }

    private fun iterateWithIterator(combination: MutableList<Int>, blackhole: Blackhole) {
        for (combinationItem in combination) {
            blackhole.consume(combinationItem)
        }
    }
}
