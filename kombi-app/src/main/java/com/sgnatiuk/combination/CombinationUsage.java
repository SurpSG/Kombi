package com.sgnatiuk.combination;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class CombinationUsage {

    public static void main(String[] args) {
        List<String> inputData = Arrays.asList("A", "B", "C");
        printCartesianProductFromList(inputData);
        printCartesianProductFromMap(inputData);
    }

    public static <T> void printCartesianProductFromList(List<T> inputData) {
        System.out.println("Combinations of " + inputData);
        CombinationsBuilder.combinationsOf(inputData)
                .forEach(System.out::println);
    }

    public static <T> void printCartesianProductFromMap(List<T> inputData) {
        Map<Integer, T> inputDataMap = IntStream.range(0, inputData.size())
                .boxed().collect(
                        toMap(i -> i, inputData::get)
                );
        System.out.println("Combinations of " + inputDataMap);
        CombinationsBuilder.combinationsOf(inputDataMap)
                .forEach(System.out::println);
    }
}
