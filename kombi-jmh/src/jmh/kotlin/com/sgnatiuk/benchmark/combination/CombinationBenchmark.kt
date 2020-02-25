package com.sgnatiuk.benchmark.combination

import com.sgnatiuk.combination.CombinationsBuilder.combinationsOf
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole


@State(Scope.Benchmark)
open class CombinationBenchmark {

    @Param("11", "19")
    var itemsQuantity: Int = 0

    lateinit var list: List<Int>
    lateinit var map: Map<Int, String>

    @Setup(Level.Trial)
    fun doSetup() {
        list = List(itemsQuantity){ it }
        map = (1..itemsQuantity).map { it to it.toString() }.toMap()
    }

    @Benchmark
    fun Kombi_combinations_list(blackhole: Blackhole) {
        for (nextCombination in combinationsOf(list)) {
            blackhole.consume(nextCombination)
        }
    }

    @Benchmark
    fun Kombi_combinations_map(blackhole: Blackhole) {
        for (nextCombination in combinationsOf(map)) {
            blackhole.consume(nextCombination)
        }
    }
}
