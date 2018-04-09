package com.sgnatiuk.cartesian;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class CartesianUsage {

    public static void main(String[] args) {
        List<List<Integer>> inputData = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4),
                Arrays.asList(5, 6)
        );
        printCartesianProductFromList(inputData);
        printCartesianProductFromMap(inputData);
    }

    public static <T> void printCartesianProductFromList(List<List<T>> inputData) {
        System.out.println("Cartesian product of " + inputData);
        CartesianBuilder.cartesianProductOf(inputData)
                .forEach(System.out::println);
    }

    public static <T> void printCartesianProductFromMap(List<List<T>> inputData) {
        Map<Integer, List<T>> inputDataMap = IntStream.range(0, inputData.size())
                .boxed().collect(
                        toMap(i -> i, inputData::get)
                );
        System.out.println("Cartesian product of " + inputDataMap);
        CartesianBuilder.cartesianProductOf(inputDataMap)
                .forEach(System.out::println);
    }
}
