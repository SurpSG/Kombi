package com.sgnatiuk.cartesian;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CartesianBuilder {
    private CartesianBuilder() {}

    public static <K, V> CartesianProduct<Map<K, V>> cartesianProductOf(Map<K, ? extends Collection<V>> map) {
        return cartesianProductOf(map, false);
    }

    public static <K, V> CartesianProduct<Map<K, V>> cartesianProductOf(
            Map<K, ? extends Collection<V>> map,
            boolean keepOrder
    ) {
        return new CartesianProductMap<>(map, keepOrder);
    }

    public static <T> CartesianProduct<List<T>> cartesianProductOf(Collection<? extends Collection<T>> data) {
        return cartesianProductOf(data, false);
    }

    public static <T> CartesianProduct<List<T>> cartesianProductOf(
            Collection<? extends Collection<T>> data,
            boolean keepOrder
    ) {
        return new CartesianProductSet<>(data, keepOrder);
    }
}
