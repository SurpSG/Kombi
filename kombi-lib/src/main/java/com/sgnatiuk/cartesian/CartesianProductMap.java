package com.sgnatiuk.cartesian;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

class CartesianProductMap<K, V> extends EncodableCartesianProduct<Map<K, V>> implements Serializable {

    private final Map<K, Object[]> values;
    private final Object[] dataKeys;
    private final boolean keepOrder;

    CartesianProductMap(Map<K, ? extends Collection<V>> values, boolean keepOrder) {
        this.values = copyWithOrder(
                values,
                keepOrder ? (o1, o2) -> 0 : new ValuesCountDesc<>()
        );
        this.dataKeys = this.values.keySet().toArray();
        this.keepOrder = keepOrder;
    }

    private CartesianProductMap(boolean keepOrder, Map<K, Object[]> values) {
        this.values = values;
        this.dataKeys = this.values.keySet().toArray();
        this.keepOrder = keepOrder;
    }

    private Map<K, Object[]> copyWithOrder(
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
                        pair -> pair.getValue().toArray(),
                        (v1, v2) -> {
                            throw new IllegalArgumentException("Unexpected key duplication in data:" + data);
                        },
                        LinkedHashMap::new
                ));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MaskDecoder<Map<K, V>> maskDecoder() {
        return encoded -> {
            HashMap<K, V> decoded = new HashMap<>(encoded.length);
            for (int i = 0; i < encoded.length; i++) {
                K fieldKey = (K) dataKeys[i];
                V value = (V) values.get(fieldKey)[encoded[i]];
                decoded.put(fieldKey, value);
            }
            return decoded;
        };
    }

    @Override
    protected Object[][] values() {
        Object[][] data = new Object[values.size()][];
        int index = 0;
        for (Object[] value : values.values()) {
            data[index++] = value;
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CartesianProduct<Map<K, V>>> split(int n) {
        if (n < 2) {
            return Collections.singletonList(this);
        }
        List<CartesianProduct<Map<K, V>>> splitList = new ArrayList<>(n);

        K keyOfMaxLengthArr = (K) dataKeys[0];
        Object[] arrWithMaxLength = values.get(keyOfMaxLengthArr);
        for (int i = 1; i < dataKeys.length; i++) {
            Object[] nextArr = values.get(dataKeys[i]);
            if (nextArr.length > arrWithMaxLength.length) {
                keyOfMaxLengthArr = (K) dataKeys[i];
                arrWithMaxLength = nextArr;
            }
        }
        int parts = Math.min(n, arrWithMaxLength.length);

        int from = 0;
        for (int i = 0; i < parts; i++) {
            int valuesPerChunk = (arrWithMaxLength.length - from) / (parts - i);
            int to = from + valuesPerChunk;

            Map<K, Object[]> nd = new HashMap<>(values);
            nd.put(
                    keyOfMaxLengthArr,
                    Arrays.copyOfRange(arrWithMaxLength, from, to)
            );
            splitList.add(new CartesianProductMap<>(keepOrder, nd));
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