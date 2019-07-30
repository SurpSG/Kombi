package com.sgnatiuk.cartesian;

import com.sgnatiuk.TestData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartesianUsage {

    public static void main(String[] args) {

        printCartesianProductFromList(TestData.getListOfLists());
        printCartesianProductFromMap(TestData.getMapOfLists());

        System.out.println("\nGet cartesian products in parallel");

        parallelPrintCartesianProduct(TestData.getListOfLists());
        parallelPrintCartesianProduct(TestData.getMapOfLists());

        System.out.println("\nGet cartesian product as stream");
        CartesianBuilder.cartesianProductOf(TestData.getListOfLists())
                .stream()
                .parallel()
                .forEach(System.out::println);
    }

    public static <T> void printCartesianProductFromList(List<List<T>> inputData) {
        System.out.println("Cartesian product of " + inputData);
        CartesianBuilder.cartesianProductOf(inputData)
                .forEach(System.out::println);
    }

    public static <K, V> void printCartesianProductFromMap(Map<K, List<V>> inputData) {
        System.out.println("Cartesian product of " + inputData);
        CartesianBuilder.cartesianProductOf(inputData)
                .forEach(System.out::println);
    }

    public static <T> void parallelPrintCartesianProduct(List<List<T>> inputData) {
        System.out.println("Split Cartesian product of " + inputData);
        parallelPrintCartesianProduct(CartesianBuilder.cartesianProductOf(inputData));
    }

    public static <K, V> void parallelPrintCartesianProduct(Map<K, List<V>> inputData) {
        System.out.println("Split Cartesian product of " + inputData);
        parallelPrintCartesianProduct(CartesianBuilder.cartesianProductOf(inputData));
    }

    public static <T> void parallelPrintCartesianProduct(CartesianProduct<T> cartesianProduct) {
        List<Thread> threadList = cartesianProduct.split(2).stream()
                .map(subProduct -> (Runnable) () ->
                        subProduct.forEach(System.out::println)
                ).map(Thread::new).collect(Collectors.toList());

        threadList.forEach(Thread::start);
        threadList.forEach(CartesianUsage::join);
    }

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
