package com.sgnatiuk.cartesian;

import java.util.Iterator;

public class CombinationMask implements Iterator<int[]> {

    private final int[] bases;
    private final int[] mask;
    private boolean hasNext = true;

    public CombinationMask(int[] bases) {
        if (bases.length == 0) {
            throw new IllegalArgumentException("Expected non empty array");
        }
        this.bases = bases;
        this.mask = new int[bases.length];
    }

    @Override
    public boolean hasNext() {
        return hasNext || (hasNext = !increment());
    }

    @Override
    public int[] next() {
        hasNext = false;
        return mask;
    }

    /**
     * Computes next encoded combination
     *
     * @return true if encoded was overflowed, otherwise false
     */
    private boolean increment() {
        int overflow = 1;
        int index = 0;

        do {
            int incrementedCell = mask[index] + overflow;
            mask[index] = incrementedCell % bases[index];
            overflow = incrementedCell / bases[index++];
        } while (overflow != 0 && index < bases.length);

        return overflow > 0 && index == bases.length;
    }
}
