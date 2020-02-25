package com.sgnatiuk.combination;

import java.util.Iterator;

class Range {

    private final long first;
    private final long last;

    public Range(long first, long last) {
        this.first = first;
        this.last = last;
    }

    public long getFirst() {
        return first;
    }

    public long getLast() {
        return last;
    }

    public long length() {
        return last - first + 1;
    }

    Iterable<Range> split(int n) {
        if(n < 1)
            throw new IllegalArgumentException("Cannot split by $n. Valid values are greater 0");

        return () -> new Iterator<Range>() {
            long first = Range.this.first;
            int chunk = 0;

            @Override
            public boolean hasNext() {
                return chunk < n;
            }

            @Override
            public Range next() {
                long rangeLength = last - first + 1;
                long valuesPerChunk = rangeLength / (n - chunk++);
                long to = first + valuesPerChunk;
                Range subRange = new Range(first, to - 1);
                first = to;
                return subRange;
            }
        };
    }
}
