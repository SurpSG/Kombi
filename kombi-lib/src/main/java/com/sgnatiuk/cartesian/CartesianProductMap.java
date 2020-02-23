package com.sgnatiuk.cartesian;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

class CartesianProductMap<K, V> extends EncodableCartesianProduct<Map<K, V>> implements Serializable {

    private final Map<K, ArrayList<V>> values;
    private final ArrayList<K> dataKeys;

    CartesianProductMap(Map<K, ? extends Collection<V>> values) {
        this(values, false);
    }

    CartesianProductMap(Map<K, ? extends Collection<V>> values, boolean keepOrder) {
        this.values = copyWithOrder(
                values,
                keepOrder ? (o1, o2) -> 0 : new ValuesCountDesc<>()
        );
        this.dataKeys = new ArrayList<>(this.values.keySet());
    }

    private Map<K, ArrayList<V>> copyWithOrder(
            Map<K, ? extends Collection<V>> data,
            Comparator<Map.Entry<K, ? extends Collection<V>>> comparator
    ) {
        return data.entrySet()
                .stream()
                .sorted(comparator)
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(),
                        new ArrayList<>(entry.getValue())
                ))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        (v1, v2) -> {
                            throw new IllegalStateException("Unexpected key duplication in data:" + data);
                        },
                        LinkedHashMap::new
                ));
    }


    @Override
    protected MaskDecoder<Map<K, V>> maskDecoder() {
        return encoded -> {
            HashMap<K, V> decoded = new HashMap<>();
            for (int i = 0; i < encoded.length; i++) {
                K fieldKey = dataKeys.get(i);
                V value = values.get(fieldKey)
                        .get(encoded[i]);
                decoded.put(fieldKey, value);
            }
            return decoded;
        };
    }

    @Override
    protected Collection<? extends Collection<?>> values() {
        return values.values();
    }

    @Override
    public List<CartesianProduct<Map<K, V>>> split(int n) {

        List<CartesianProduct<Map<K, V>>> splitList = new ArrayList<>(n);
        Map<K, ArrayList<V>> descSortedData = copyWithOrder(values, new ValuesCountDesc<>());
        Map.Entry<K, ArrayList<V>> firstEntry = descSortedData.entrySet().stream().findFirst().orElseThrow(
                () -> new IllegalStateException("Expected at least one item in: " + descSortedData)
        );
        ArrayList<V> firstValue = firstEntry.getValue();
        int parts = Math.min(n, firstValue.size());

        int from = 0;
        for (int i = 0; i < parts; i++) {
            int valuesPerChunk = (firstValue.size() - from) / (parts - i);
            int to = from + valuesPerChunk;

            LinkedHashMap<K, List<V>> newData = new LinkedHashMap<>(descSortedData);
            newData.put(
                    firstEntry.getKey(),
                    firstValue.subList(from, to)
            );
            splitList.add(new CartesianProductMap<>(newData));
            from = to;
        }
        return splitList;
    }


    private static class ValuesCountDesc<K, V> implements Comparator<Map.Entry<K, ? extends Collection<V>>> {

        @Override
        public int compare(
                Map.Entry<K, ? extends Collection<V>> o1,
                Map.Entry<K, ? extends Collection<V>> o2
        ) {
            return o2.getValue().size() - o1.getValue().size();
        }
    }
}